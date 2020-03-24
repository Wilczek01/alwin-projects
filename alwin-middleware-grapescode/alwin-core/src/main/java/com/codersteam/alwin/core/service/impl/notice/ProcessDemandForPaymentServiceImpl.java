package com.codersteam.alwin.core.service.impl.notice;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService;
import com.codersteam.alwin.core.api.service.notice.ProcessDemandForPaymentService;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.efaktura.PaymentRequestClient;
import com.codersteam.alwin.efaktura.model.payment.*;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.common.demand.DemandForPaymentState.FAILED;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.ISSUED;
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST;
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.SECOND;
import static com.codersteam.alwin.common.util.MandatoryUtils.mandatory;
import static com.codersteam.alwin.core.util.DateUtils.daysBetween;
import static com.codersteam.alwin.jpa.type.AddressType.CORRESPONDENCE;
import static com.codersteam.alwin.jpa.type.AddressType.RESIDENCE;
import static com.codersteam.alwin.jpa.type.ContactImportedType.*;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class ProcessDemandForPaymentServiceImpl implements ProcessDemandForPaymentService {

    protected static final String CONTRACT_TERMINATION_PENDING_FOR_CONTRACT_MESSAGE = "Umowa posiada aktywne dokumenty wypowiedzenia";
    protected static final String DEMAND_FOR_PAYMENT_ABORTED = "Wezwanie zostało zastąpione w procesie inny wezwaniem";
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FILE_NAME_FORMAT = "Wezwanie_%s_%s_%s";
    private static final String FILE_NAME_DATE_FORMAT = "yyyy-MM-dd";
    private final DemandForPaymentDao demandForPaymentDao;
    private final CompanyDao companyDao;
    private final PaymentRequestClient paymentRequestClient;
    private final FormalDebtCollectionService formalDebtCollectionService;

    @Inject
    public ProcessDemandForPaymentServiceImpl(final DemandForPaymentDao demandForPaymentDao, final CompanyDao companyDao,
                                              final PaymentRequestClient paymentRequestClient, final FormalDebtCollectionService formalDebtCollectionService) {
        this.demandForPaymentDao = demandForPaymentDao;
        this.companyDao = companyDao;
        this.paymentRequestClient = paymentRequestClient;
        this.formalDebtCollectionService = formalDebtCollectionService;
    }

    @Transactional(REQUIRES_NEW)
    public void sendDemand(final DemandForPaymentDto demandForPayment, final String loggedOperatorFullName) {
        final DemandForPayment demand = demandForPaymentDao.get(demandForPayment.getId()).orElseThrow(IllegalArgumentException::new);
        if (demand.isAborted()) {
            demandForPaymentFailed(demand, DEMAND_FOR_PAYMENT_ABORTED);
            return;
        }
        if (demand.isCreatedManually() && formalDebtCollectionService.isContractTerminationPending(demand.getContractNumber())) {
            demandForPaymentFailed(demand, CONTRACT_TERMINATION_PENDING_FOR_CONTRACT_MESSAGE);
            return;
        }
        if (demand.isCreatedManually()) {
            alterProcessIfNeededForManualDemandForPayment(demand);
        }
        sendDemand(loggedOperatorFullName, demand);
    }

    private void demandForPaymentFailed(final DemandForPayment demandForPayment, final String failureMessage) {
        demandForPayment.setProcessingMessage(failureMessage);
        demandForPayment.setState(FAILED);
        LOG.warn("Cannot send demand for payment {}, contract number {} because of {}!", demandForPayment.getId(),
                demandForPayment.getContractNumber(), failureMessage);
        demandForPaymentDao.update(demandForPayment);
    }

    private void sendDemand(final String loggedOperatorFullName, final DemandForPayment demand) {
        try {
            final GeneratePaymentRequestDto request = prepareDemand(demand, loggedOperatorFullName);
            final GeneratePaymentResponseDto response = paymentRequestClient.generatePaymentRequest(request);
            if (GeneratePaymentStatus.CREATED_WITH_PDF.name().equals(response.getStatus())) {
                demand.setProcessingMessage(StringUtils.EMPTY);
                demand.setState(ISSUED);
                demand.setAttachmentRef(response.getUrl());
            } else {
                demand.setProcessingMessage(String.format("%s - %s", response.getStatus(), response.getError()));
                demand.setState(FAILED);
                LOG.warn(String.format(
                        "Sending demand for payment to eInvoice failed with status %s and error message: %s",
                        response.getStatus(),
                        response.getError()
                ));
            }
        } catch (final PaymentRequestException | RuntimeException e) {
            demand.setProcessingMessage(e.getMessage());
            demand.setState(FAILED);
            LOG.warn("Sending demand for payment to eInvoice failed", e);
        }
        demandForPaymentDao.update(demand);
    }

    protected void alterProcessIfNeededForManualDemandForPayment(final DemandForPayment demandForPayment) {
        final String contractNumber = demandForPayment.getContractNumber();

        // Jeśli nie ma aktywnego procesu, to go nie modyfikujemy
        if (!formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(contractNumber)) {
            LOG.info("No active formal debt collection pending for contract {}, issuing manual demand for payment", contractNumber);
            return;
        }

        final Long demandForPaymentId = demandForPayment.getId();
        final String initialInvoiceNo = demandForPayment.getInitialInvoiceNo();

        if (isDemandOfPaymentOfType(demandForPayment, SECOND)) {
            final List<DemandForPaymentDto> secondDemandsForPayment = formalDebtCollectionService.findDemandsForPaymentExcludingGiven(SECOND,
                    contractNumber, initialInvoiceNo, demandForPaymentId);

            secondDemandsForPayment.forEach(this::abortDemandForPayment);
            demandForPayment.setPrecedingDemandForPaymentId(findPrecedingDemandForPaymentId(contractNumber, initialInvoiceNo));
            return;
        }

        if (isDemandOfPaymentOfType(demandForPayment, FIRST)) {
            final List<DemandForPaymentDto> secondDemandsForPayment = formalDebtCollectionService.findDemandsForPayment(SECOND, contractNumber, initialInvoiceNo);
            secondDemandsForPayment.forEach(this::abortDemandForPayment);

            final List<DemandForPaymentDto> firstDemandsForPayment = formalDebtCollectionService.findDemandsForPaymentExcludingGiven(FIRST, contractNumber,
                    initialInvoiceNo, demandForPaymentId);
            firstDemandsForPayment.forEach(this::abortDemandForPayment);
        }
    }

    private Long findPrecedingDemandForPaymentId(final String contractNumber, final String initialInvoiceNo) {
        final List<DemandForPaymentDto> firstDemandsForPayment = formalDebtCollectionService.findDemandsForPayment(FIRST, contractNumber, initialInvoiceNo);
        return firstDemandsForPayment.stream().findFirst().map(DemandForPaymentDto::getId).orElse(null);
    }

    protected void abortDemandForPayment(final DemandForPaymentDto demandForPaymentDto) {
        formalDebtCollectionService.markDemandForPaymentAborted(demandForPaymentDto.getId());
    }

    private boolean isDemandOfPaymentOfType(final DemandForPayment demand, final DemandForPaymentTypeKey typeKey) {
        return demand.getType().getKey() == typeKey;
    }

    protected GeneratePaymentRequestDto prepareDemand(final DemandForPayment demandForPayment, final String loggedOperatorFullName) throws PaymentRequestException {
        try {
            final GeneratePaymentRequestDto request = new GeneratePaymentRequestDto();
            request.setAmount(demandForPayment.getType().getCharge());
            request.setContractNo(mandatory(demandForPayment.getContractNumber(), "Numer umowy"));
            request.setCustomerNo(mandatory(demandForPayment.getExtCompanyId(), "Numer klienta").toString());
            request.setDelayDays(
                    daysBetween(
                            mandatory(demandForPayment.getIssueDate(), "Data wystawienia"),
                            mandatory(demandForPayment.getDueDate(), "Termin zapłaty")
                    ).intValue()
            );
            request.setFileName(
                    buildFileName(
                            mandatory(demandForPayment.getType().getKey(), "Numer wezwania"),
                            mandatory(demandForPayment.getContractNumber(), "Numer umowy"),
                            mandatory(demandForPayment.getIssueDate(), "Data wystawienia")
                    )
            );
            request.setOutstandingInvoices(prepareInvoices(demandForPayment.getInvoices()));
            request.setIssuingOperator(loggedOperatorFullName);
            final Company company = companyDao.findCompanyByExtId(demandForPayment.getExtCompanyId())
                    .orElseThrow(() -> new IllegalStateException(String.format("Klient o nr z leo [%s] nie istnieje", demandForPayment.getExtCompanyId())));
            request.setRecipientName(mandatory(company.getCompanyName(), "Nazwa firmy klienta"));
            request.setRecipientNip(company.getNip());
            prepareCompanyContact(request, company.getContactDetails());
            prepareCompanyAddress(request, company.getAddresses());
            return request;
        } catch (final Exception e) {
            throw new PaymentRequestException(e.getMessage());
        }
    }

    protected void prepareCompanyAddress(final GeneratePaymentRequestDto request, final Set<Address> addresses) {
        if (isEmpty(addresses)) {
            throw new IllegalStateException("Brak adresów dla klienta");
        }
        final Address address = addresses.stream()
                .filter(a -> CORRESPONDENCE == a.getAddressType())
                .findFirst().orElseGet(() -> addresses.stream()
                        .filter(a -> RESIDENCE == a.getAddressType())
                        .findFirst().orElseThrow(() -> new IllegalStateException("Brak adresu korespondencyjnego lub siedziby dla klienta")));

        request.setRecipientStreet(mandatory(buildStreet(address), "Ulica, numer domu i lokalu w adresie korespondencyjnym lub siedziby"));
        request.setRecipientPostalCode(mandatory(address.getPostalCode(), "Kod pocztowy w adresie korespondencyjnym lub siedziby"));
        request.setRecipientCity(mandatory(address.getCity(), "Miasto w adresie korespondencyjnym lub siedziby"));
    }

    protected String buildStreet(final Address address) {
        String street = mandatory(address.getStreetName(), "Nazwa ulicy");

        if (isNotEmpty(address.getHouseNumber())) {
            street = String.format("%s %s", street, address.getHouseNumber());
        }

        if (isNotEmpty(address.getFlatNumber())) {
            street = String.format("%s/%s", street, address.getFlatNumber());
        }

        if (isNotEmpty(address.getStreetPrefix())) {
            return String.format("%s %s", address.getStreetPrefix(), street);
        }
        return street;
    }

    protected void prepareCompanyContact(final GeneratePaymentRequestDto request, final Set<ContactDetail> contactDetails) {
        if (isEmpty(contactDetails)) {
            throw new IllegalStateException("Brak danych kontaktowych dla klienta");
        }
        final ContactDetail email = contactDetails.stream()
                .filter(contact -> contact.getImportedType() == DOCUMENT_E_MAIL).findFirst()
                .orElseGet(() -> contactDetails.stream().filter(contact -> contact.getImportedType() == E_MAIL).findFirst()
                        .orElseGet(() -> contactDetails.stream().filter(contact -> contact.getImportedType() == OFFICE_E_EMAIL).findFirst()
                                .orElseThrow(() -> new IllegalStateException("Brak któregokolwiek z adresów email"))));
        request.setRecipientEmail(email.getContact());
        contactDetails.stream()
                .filter(contact -> contact.getImportedType() == PHONE_NUMBER_1).findFirst()
                .ifPresent(phone1 -> request.setRecipientPhoneNo1(phone1.getContact()));
        contactDetails.stream()
                .filter(contact -> contact.getImportedType() == PHONE_NUMBER_2).findFirst()
                .ifPresent(phone2 -> request.setRecipientPhoneNo2(phone2.getContact()));
    }

    private List<OutstandingInvoiceDto> prepareInvoices(final Set<FormalDebtCollectionInvoice> invoices) {
        if (isEmpty(invoices)) {
            throw new IllegalStateException("Brak dłużnych faktur");
        }
        final List<OutstandingInvoiceDto> outstandingInvoices = new ArrayList<>(invoices.size());
        invoices.forEach(invoice -> {
            final OutstandingInvoiceDto outstandingInvoice = new OutstandingInvoiceDto();
            outstandingInvoice.setContractNo(mandatory(invoice.getContractNumber(), "Nr umowy"));
            outstandingInvoice.setCurrency(mandatory(invoice.getCurrency(), "Waluta"));
            outstandingInvoice.setDueDate(mandatory(invoice.getRealDueDate(), "Termin płatności"));
            outstandingInvoice.setInvoiceBalance(mandatory(invoice.getCurrentBalance(), "Saldo").abs());
            outstandingInvoice.setInvoiceNo(mandatory(invoice.getInvoiceNumber(), "Nr faktury"));
            outstandingInvoice.setInvoiceSum(mandatory(invoice.getGrossAmount(), "Kwota faktury"));
            outstandingInvoice.setIssueDate(mandatory(invoice.getIssueDate(), "Data wystawienia faktury"));
            outstandingInvoice.setLeoId(mandatory(invoice.getLeoId(), "Id faktury z LEO"));
            outstandingInvoices.add(outstandingInvoice);
        });
        return outstandingInvoices;
    }

    /**
     * Buduje nazwę wezwania.
     * Nazewnictwo wg wzorca: "Wezwanie_<1 lub 2>_<nr_umowy>_<Data_wezwania>" Przy czym zamiast ukośników w nrze umowy stosujmy myślniki.
     * Np.:  Wezwanie_1_012345-18-1_2018-01-01
     *
     * @param demandNo   - numer wezwania
     * @param contractNo - numer umowy
     * @param issueDate  - data wystawienia
     * @return nazwa wezwania
     */
    protected String buildFileName(final DemandForPaymentTypeKey demandNo, final String contractNo, final Date issueDate) {
        final String fileDemandNo;
        if (demandNo == FIRST) {
            fileDemandNo = "1";
        } else if (demandNo == SECOND) {
            fileDemandNo = "2";
        } else {
            throw new IllegalArgumentException("Nieobsługiwany typ wezwania");
        }
        final DateFormat dateFormat = new SimpleDateFormat(FILE_NAME_DATE_FORMAT);
        return String.format(FILE_NAME_FORMAT, fileDemandNo, contractNo.replaceAll("/", "-"), dateFormat.format(issueDate));
    }

}
