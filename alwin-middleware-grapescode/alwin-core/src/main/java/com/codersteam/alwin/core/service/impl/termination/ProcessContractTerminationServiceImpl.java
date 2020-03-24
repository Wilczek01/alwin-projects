package com.codersteam.alwin.core.service.impl.termination;

import com.codersteam.aida.core.api.service.ContractSubjectService;
import com.codersteam.alwin.common.AlwinConstants;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.core.api.model.termination.TerminationContractDto;
import com.codersteam.alwin.core.api.model.termination.TerminationContractSubjectDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.termination.ProcessContractTerminationService;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.ContractTerminationDao;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.efaktura.ContractTerminationClient;
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationException;
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationRequestDto;
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationResponseDto;
import com.codersteam.alwin.efaktura.model.termination.data.*;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.jpa.termination.ContractTerminationAttachment;
import com.codersteam.alwin.jpa.termination.ContractTerminationSubject;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import com.codersteam.alwin.jpa.type.ActivityState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.issue.IssueTypeName.DIRECT_DEBT_COLLECTION;
import static com.codersteam.alwin.common.util.MandatoryUtils.mandatory;
import static com.codersteam.alwin.jpa.type.ContactImportedType.*;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Stateless
public class ProcessContractTerminationServiceImpl implements ProcessContractTerminationService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ContractTerminationDao contractTerminationDao;
    private ContractTerminationClient contractTerminationClient;
    private CompanyDao companyDao;
    private IssueDao issueDao;
    private ContractSubjectService contractSubjectService;

    public ProcessContractTerminationServiceImpl() {
    }

    @Inject
    public ProcessContractTerminationServiceImpl(final ContractTerminationDao contractTerminationDao,
                                                 final ContractTerminationClient contractTerminationClient,
                                                 final CompanyDao companyDao,
                                                 final IssueDao issueDao,
                                                 final AidaService aidaService) {
        this.contractTerminationDao = contractTerminationDao;
        this.contractTerminationClient = contractTerminationClient;
        this.companyDao = companyDao;
        this.issueDao = issueDao;
        contractSubjectService = aidaService.getContractSubjectService();
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void sendTermination(final TerminationDto terminationDto, final String loggedOperatorFullName) {
        final Set<Long> contractTerminationIds = toContractTerminationIds(terminationDto);
        final List<ContractTermination> contractTerminations = contractTerminationDao.findByIdsIn(contractTerminationIds);
        try {
            updateContractTerminationWithValuesFromDto(contractTerminations, terminationDto);
            final ContractTerminationRequestDto request = buildContractTerminationRequest(loggedOperatorFullName, contractTerminations);
            final ContractTerminationResponseDto response = contractTerminationClient.generateContractTermination(request);
            if (StringUtils.isBlank(response.getError())) {
                final List<ContractTerminationAttachment> attachments = generateTerminationResponses(response);
                contractTerminations.forEach(termination -> termination.setContractTerminationAttachments(attachments));
            } else {
                contractTerminations.forEach(markAsFailed(response.getError()));
            }

        } catch (ContractTerminationException | RuntimeException e) {
            contractTerminations.forEach(markAsFailed(e.getMessage()));
            LOG.warn("Sending contract termination to eInvoice failed", e);
        }
        contractTerminations.forEach(contractTerminationDao::update);
    }

    private Set<Long> toContractTerminationIds(final TerminationDto terminationDto) {
        return terminationDto.getContracts().stream()
                .map(TerminationContractDto::getContractTerminationId)
                .collect(Collectors.toSet());
    }

    /**
     * Ustawia nowe wartości dla encji wypowiedzeń umów
     */
    private void updateContractTerminationWithValuesFromDto(final List<ContractTermination> contractTerminations, final TerminationDto terminationDto) {
        final Map<Long, TerminationContractDto> contractTerminationDtosById = toContractTerminationsMapById(terminationDto);
        contractTerminations.forEach(contractTermination -> {
            final TerminationContractDto contractTerminationDto = contractTerminationDtosById.get(contractTermination.getId());
            contractTermination.setState(ContractTerminationState.ISSUED);
            contractTermination.setType(contractTerminationDto.getType());
            final Map<Long, TerminationContractSubjectDto> contractTerminationSubjectDtosById = toContractTerminationSubjectsMapById(contractTerminationDto);
            contractTermination.getSubjects().forEach(subject -> {
                final TerminationContractSubjectDto subjectDto = contractTerminationSubjectDtosById.get(subject.getId());
                updateGpsInstallation(subject, subjectDto);
            });
        });

    }

    private void updateGpsInstallation(final ContractTerminationSubject subject, final TerminationContractSubjectDto subjectDto) {
        if (Boolean.FALSE.equals(subject.getGpsInstalled())) {
            final Boolean gpsToInstall = Boolean.TRUE.equals(subjectDto.getGpsToInstall());
            subject.setGpsToInstall(gpsToInstall);
            final Boolean gpsIncreasedFee = Boolean.TRUE.equals(subjectDto.getGpsIncreasedFee());
            subject.setGpsIncreasedFee(gpsIncreasedFee);
        }
    }

    private Map<Long, TerminationContractSubjectDto> toContractTerminationSubjectsMapById(final TerminationContractDto contractTerminationDto) {
        return contractTerminationDto.getSubjects().stream()
                .collect(Collectors.toMap(TerminationContractSubjectDto::getId, Function.identity()));
    }

    private Map<Long, TerminationContractDto> toContractTerminationsMapById(final TerminationDto terminationDto) {
        return terminationDto.getContracts().stream()
                .collect(Collectors.toMap(TerminationContractDto::getContractTerminationId, Function.identity()));
    }

    private List<ContractTerminationAttachment> generateTerminationResponses(final ContractTerminationResponseDto response) {
        return response.getDocumentHashes().stream()
                .map(hash -> {
                    final ContractTerminationAttachment attachment = new ContractTerminationAttachment();
                    attachment.setReference(hash);
                    return attachment;
                })
                .collect(Collectors.toList());
    }

    private Consumer<ContractTermination> markAsFailed(final String error) {
        return termination -> {
            termination.setProcessingMessage(error);
            termination.setState(ContractTerminationState.FAILED);
        };
    }

    private ContractTerminationRequestDto buildContractTerminationRequest(final String loggedOperatorFullName,
                                                                          final List<ContractTermination> contractTerminations) {

        final Long extCompanyId = mandatory(singleFromList(contractTerminations, ContractTermination::getExtCompanyId, "Nr klienta"), "Nr klienta");

        final ContractTerminationRequestDto request = new ContractTerminationRequestDto();

        findOperatorOfLatestOpenActivity(extCompanyId).ifPresent(operator -> {
            request.setContactEmail(operator.getUser().getEmail());
            request.setContactPhoneNo(operator.getUser().getPhoneNumber());
        });

        request.setIssuingOperator(loggedOperatorFullName);
        request.setContracts(buildContracts(contractTerminations));
        request.setCustomer(buildCustomer(extCompanyId, contractTerminations));
        request.setSuspensionDate(singleFromList(contractTerminations, ContractTermination::getTerminationDate, "Data wypowiedzenia"));
        request.setTerminationType(mandatory(toTerminationTypeDto(contractTerminations), "Typ wypowiedzenia"));
        return request;
    }

    private Optional<Operator> findOperatorOfLatestOpenActivity(final Long extCompanyId) {
        return issueDao.findCompanyActiveIssueId(extCompanyId)
                .flatMap(issueDao::get)
                .flatMap(this::toIssueOperatorOrActivityOperator);
    }

    private Optional<Operator> toIssueOperatorOrActivityOperator(final Issue issue) {
        if (DIRECT_DEBT_COLLECTION.equals(issue.getIssueType().getName())) {
            final Optional<Operator> issueOperator = Optional.ofNullable(issue.getAssignee());
            if (issueOperator.isPresent()) {
                return issueOperator;
            } else {
                return operatorFromIssueActivitiesWithState(issue, ActivityState.ALL_STATES);
            }

        } else {
            return operatorFromIssueActivitiesWithState(issue, ActivityState.OPEN_STATES);
        }
    }

    private Optional<Operator> operatorFromIssueActivitiesWithState(final Issue issue, final List<ActivityState> allowedStates) {
        return Optional.of(issue)
                .map(Issue::getActivities)
                .flatMap(activities -> activities.stream()
                        .filter(activity -> allowedStates.contains(activity.getState()))
                        .filter(activity -> activity.getOperator() != null)
                        .max(Comparator.comparing(Activity::getPlannedDate))
                        .map(Activity::getOperator));
    }

    private List<ContractTerminationContractDto> buildContracts(final List<ContractTermination> contractTerminations) {
        return contractTerminations.stream()
                .map(contractTermination -> {
                    final ContractTerminationContractDto contract = new ContractTerminationContractDto();
                    contract.setActivationFee(contractTermination.getResumptionCost());
                    contract.setContractNo(contractTermination.getContractNumber());
                    contract.setContractType(mandatory(toContractType(contractTermination), "Typ finansowania"));
                    contract.setInvoices(buildInvoices(contractTermination.getFormalDebtCollectionInvoices()));
                    contract.setSubjects(buildSubjects(contractTermination));
                    return contract;
                })
                .collect(Collectors.toList());
    }

    private List<ContractSubjectDto> buildSubjects(final ContractTermination contractTermination) {
        final Map<Long, com.codersteam.aida.core.api.model.ContractSubjectDto> aidaContractSubjectsById = contractSubjectService
                .findContractSubjectByContractNo(contractTermination.getContractNumber()).stream()
                .collect(Collectors.toMap(com.codersteam.aida.core.api.model.ContractSubjectDto::getSubjectId, Function.identity()));

        return contractTermination.getSubjects().stream()
                .map(subject -> {
                    final com.codersteam.aida.core.api.model.ContractSubjectDto aidaSubject = mandatory(aidaContractSubjectsById.get(subject.getSubjectId()), "Przedmiot leasingu");
                    final ContractSubjectDto contractSubjectDto = new ContractSubjectDto();
                    contractSubjectDto.setGpsFee(determineGpsFee(subject));
                    contractSubjectDto.setHasGps(subject.getGpsToInstall());
                    contractSubjectDto.setPlateNo(aidaSubject.getRegistrationNumber());
                    contractSubjectDto.setSerialNo(aidaSubject.getSerialNumber());
                    contractSubjectDto.setSubject(aidaSubject.getName());
                    return contractSubjectDto;
                })
                .collect(Collectors.toList());
    }

    protected BigDecimal determineGpsFee(final ContractTerminationSubject subject) {
        if (Boolean.FALSE.equals(subject.getGpsToInstall())) {
            return AlwinConstants.ZERO;
        }
        if (subject.getGpsIncreasedFee()) {
            return subject.getGpsIncreasedInstallationCharge();
        }
        return subject.getGpsInstallationCharge();
    }

    private List<InvoiceDto> buildInvoices(final List<FormalDebtCollectionInvoice> contractTerminationInvoice) {
        return contractTerminationInvoice.stream()
                .map(invoice -> {
                    final InvoiceDto invoiceDto = new InvoiceDto();
                    invoiceDto.setContractNo(invoice.getContractNumber());
                    invoiceDto.setCurrency(invoice.getCurrency());
                    invoiceDto.setDueDate(invoice.getRealDueDate());
                    invoiceDto.setInvoiceBalance(invoice.getCurrentBalance().negate());
                    invoiceDto.setInvoiceNo(invoice.getInvoiceNumber());
                    invoiceDto.setInvoiceSum(invoice.getGrossAmount());
                    invoiceDto.setIssueDate(invoice.getIssueDate());
                    invoiceDto.setLeoId(invoice.getLeoId());
                    return invoiceDto;
                })
                .collect(Collectors.toList());
    }

    private ContractTypeDto toContractType(final ContractTermination contractTermination) {
        switch (contractTermination.getContractType()) {
            case OPERATIONAL_LEASING:
                return ContractTypeDto.OPERATIONAL_LEASING;
            case FINANCIAL_LEASING:
                return ContractTypeDto.FINANCIAL_LEASING;
            case LOAN:
                return ContractTypeDto.LOAN;
            default:
                return null;
        }
    }

    private TerminationTypeDto toTerminationTypeDto(final List<ContractTermination> contractTerminations) {
        switch (singleFromList(contractTerminations, ContractTermination::getType, "Typ wypowiedzenia")) {
            case CONDITIONAL:
                return TerminationTypeDto.CONDITIONAL;
            case IMMEDIATE:
                return TerminationTypeDto.IMMEDIATE;
        }
        return null;
    }

    private ContractCustomerDto buildCustomer(final Long extCompanyId, final List<ContractTermination> contractTerminations) {
        final Address address = singleFromList(contractTerminations, ContractTermination::getCompanyAddress, "Adres siedziby");

        final Company company = companyDao.findCompanyByExtId(extCompanyId)
                .orElseThrow(() -> new IllegalStateException(String.format("Klient o nr z leo [%s] nie istnieje", extCompanyId)));
        final ContractCustomerDto contractCustomer = new ContractCustomerDto();
        final Long companyId = singleFromList(contractTerminations, ContractTermination::getExtCompanyId, "Nr klienta");
        contractCustomer.setNo(companyId != null ? companyId.toString() : null);
        contractCustomer.setName(company.getCompanyName());
        contractCustomer.setNip(company.getNip());

        contractCustomer.setCity(address.getCity());
        contractCustomer.setPostalCode(address.getPostalCode());
        contractCustomer.setStreet(address.getStreetName());
        final Address correspondenceAddress = singleFromList(contractTerminations, ContractTermination::getCompanyCorrespondenceAddress, "Adres korespondencyjny");
        if (correspondenceAddress != null) {
            contractCustomer.setMailCity(correspondenceAddress.getCity());
            contractCustomer.setMailPostalCode(correspondenceAddress.getPostalCode());
            contractCustomer.setMailStreet(correspondenceAddress.getStreetName());
        }
        prepareCompanyContact(contractCustomer, company.getContactDetails());
        return contractCustomer;
    }

    private void prepareCompanyContact(final ContractCustomerDto request, final Set<ContactDetail> contactDetails) {
        if (isEmpty(contactDetails)) {
            throw new IllegalStateException("Brak danych kontaktowych dla klienta");
        }
        final ContactDetail email = contactDetails.stream()
                .filter(contact -> contact.getImportedType() == DOCUMENT_E_MAIL).findFirst()
                .orElseGet(() -> contactDetails.stream().filter(contact -> contact.getImportedType() == E_MAIL).findFirst()
                        .orElseGet(() -> contactDetails.stream().filter(contact -> contact.getImportedType() == OFFICE_E_EMAIL).findFirst()
                                .orElse(null)));
        if (email != null) {
            request.setEmail(email.getContact());
        }
        contactDetails.stream()
                .filter(contact -> contact.getImportedType() == PHONE_NUMBER_1).findFirst()
                .ifPresent(phone1 -> request.setPhoneNo1(phone1.getContact()));
        contactDetails.stream()
                .filter(contact -> contact.getImportedType() == PHONE_NUMBER_2).findFirst()
                .ifPresent(phone2 -> request.setPhoneNo2(phone2.getContact()));
    }

    /**
     * Zwraca pojedynczą wartość z listy według podanej funkcji pobierania wartości. W przypadku pustej listy zwraca null.
     * Rzuca wyjątek jeśli wartość nie jest unikalna.
     *
     * @param contractTerminations - lista obiektów z których ma zostać wyłuskana wartość
     * @param valueProvider        - funkcja pobierania wartości z listy obiektów
     * @param name                 - nazwa pobieranego pola z obiektu
     * @return null jeśli lista wartości jest pusta, wartość pobrana według podanej funkcji lub wyjątek w przypadku nie unikalnej wartości
     */
    private <T, V> V singleFromList(final List<T> contractTerminations, final Function<T, V> valueProvider, final String name) {
        final Set<V> set = contractTerminations.stream().map(valueProvider).collect(Collectors.toSet());
        if (set.size() > 1) {
            throw new IllegalArgumentException(String.format("Pobierana wartość '%s' nie jest unikalna dla wszystkich elementów listy", name));
        }
        return set.stream().findFirst().orElse(null);
    }

}
