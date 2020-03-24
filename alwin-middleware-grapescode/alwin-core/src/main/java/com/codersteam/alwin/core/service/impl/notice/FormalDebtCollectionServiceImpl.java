package com.codersteam.alwin.core.service.impl.notice;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.service.ContractService;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.ContractTerminationDao;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Pojedynczy blok procesu windykacji formalnej umowy może zakończyć się w zależności od swojego etapu w następujący sposób:
 * <ol>
 * <li>Po wysłaniu wezwania 1 (Monit):
 * <ol type="a">
 * <li>Spłata należności, do której wysłano wezwanie do zapłaty (faktura inicjująca) co najmniej do wartości progowej dla wysłania wezwania do zapłaty,</li>
 * <li>Odrzucenie wysłania drugiego wezwania do zapłaty,</li>
 * </ol>
 * </li>
 * <li>
 * Po wysłaniu wezwania 2 (Ostateczne wezwanie do zapłaty):
 * <ol type="a">
 * <li>Spłata należności, do której wysłano wezwanie do zapłaty (faktura inicjująca) co najmniej do wartości progowej dla wypowiedzenia umowy,</li>
 * <li>Odrzucenie wypowiedzenia umowy,</li>
 * </ol>
 * </li>
 * <li>
 * Po wysłaniu wypowiedzenia warunkowego (decyzja o wypowiedzeniu):
 * <ol type="a">
 * <li>Spłata należności, do której wysłano wypowiedzenie (wszystkie wskazane faktury) we wskazanym terminie,</li>
 * <li>Odrzucenie wypowiedzenia natychmiastowego (wezwanie do zwrotu przedmiotu) dla umowy,</li>
 * <li>Oznaczenie umowy jako aktywowana przez użytkownika</li>
 * </ol>
 * </li>
 * <li>
 * Po wysłaniu wypowiedzenia natychmiastowego (wezwanie do zwrotu przedmiotu):
 * <ol type="a">
 * <li>Oznaczenie umowy jako aktywowana przez użytkownika</li>
 * </ol>
 * </li>
 * </ol>
 *
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class FormalDebtCollectionServiceImpl implements FormalDebtCollectionService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final List<Long> CONTRACT_STATUS_VALID_TO_CREATE_TERMINATION = asList(2L, 106L, 44L, 8L, 9L, 10L, 211L, 212L);
    private static final BigDecimal FORTY_PERCENT = new BigDecimal("0.40");

    // ilość dni, po których (od wystawienia) wypowiedzenie warunkowe się przeterminowuje (14 dni na uregulowanie zaległości)
    protected static final Integer NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE = 15;

    private final DemandForPaymentDao demandForPaymentDao;
    private final ContractTerminationDao contractTerminationDao;
    private final InvoiceService aidaInvoiceService;
    private final CustomerVerifierService customerVerifierService;
    private final DateProvider dateProvider;
    private final ContractService contractService;
    private final AlwinMapper mapper;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public FormalDebtCollectionServiceImpl(final DemandForPaymentDao demandForPaymentDao, final ContractTerminationDao contractTerminationDao, final AidaService aidaService,
                                           final CustomerVerifierService customerVerifierService, final DateProvider dateProvider, final AlwinMapper mapper) {
        this.demandForPaymentDao = demandForPaymentDao;
        this.contractTerminationDao = contractTerminationDao;
        this.aidaInvoiceService = aidaService.getInvoiceService();
        this.contractService = aidaService.getContractService();
        this.customerVerifierService = customerVerifierService;
        this.dateProvider = dateProvider;
        this.mapper = mapper;
    }

    @Override
    public boolean shouldGenerateSecondDemandForPaymentForContract(final String contractNo) {
        final Optional<DemandForPaymentWithCompanyName> latestDemandForPayment = demandForPaymentDao.findLatestDemandForPayment(contractNo);

        // brak wezwań oznacza, iż nie można wystawić wezwania ostatecznego
        if (!latestDemandForPayment.isPresent()) {
            return false;
        }

        final DemandForPayment demandForPayment = latestDemandForPayment.get();

        // ostatni dokument nie jest wezwaniem typu 1 (monit)
        if (demandForPayment.getType().getKey() != DemandForPaymentTypeKey.FIRST) {
            return false;
        }

        if (demandForPayment.getState() != DemandForPaymentState.ISSUED) {
            // TODO #29111 - trzeba zakolejkować wezwanie ostateczne
            return false;
        }

        // wezwanie 1 wystawione, ale nie upłynął termin zapłaty
        if (!dateProvider.getCurrentDateStartOfDay().after(demandForPayment.getDueDate())) {
            // TODO #29111 - trzeba zakolejkować wezwanie ostateczne
            return false;
        }

        // jeśli faktura z monitu została opłacona, to nie wysyłamy wezwania ostatecznego
        return isInitialInvoiceNotPaid(demandForPayment);
    }

    @Override
    public Long findPrecedingDemandForPaymentId(final String contractNo) {
        final Optional<DemandForPaymentWithCompanyName> latestDemandForPayment = demandForPaymentDao.findLatestDemandForPayment(contractNo);

        if (!latestDemandForPayment.isPresent()) {
            return null;
        }

        final DemandForPayment demandForPayment = latestDemandForPayment.get();

        if (demandForPayment.getType().getKey() != DemandForPaymentTypeKey.FIRST) {
            return null;
        }

        if (demandForPayment.getState() != DemandForPaymentState.ISSUED) {
            return null;
        }

        return demandForPayment.getId();
    }

    @Override
    public String findActiveProcessInitialInvoiceNumberByContractNumber(final String contractNo) {
        final Optional<DemandForPaymentWithCompanyName> latestDemandForPayment = demandForPaymentDao.findLatestDemandForPayment(contractNo);
        // w przypadku braku wezwań brak procesu windykacji formalnej
        if (!latestDemandForPayment.isPresent()) {
            return null;
        }

        final DemandForPayment demandForPayment = latestDemandForPayment.get();

        // jeśli wezwanie nie zostało jeszcze wystawione, to proces windykacji formalnej w toku
        if (demandForPayment.getState() != DemandForPaymentState.ISSUED) {
            return demandForPayment.getInitialInvoiceNo();
        }

        if (isInitialInvoiceNotPaid(demandForPayment)) {
            return demandForPayment.getInitialInvoiceNo();
        }

        final Optional<ContractTermination> latestContractTermination = contractTerminationDao.findLatestContractTermination(contractNo);
        if (!latestContractTermination.isPresent()) {
            return null;
        }

        final ContractTermination contractTermination = latestContractTermination.get();

        // jeśli wypowiedzenie zostało odrzucone, to nie ma aktywnego procesu windykacji formalnej
        if (contractTermination.getState() == ContractTerminationState.REJECTED) {
            return null;
        }

        // umowa została aktywowana
        if (contractTermination.getState() == ContractTerminationState.CONTRACT_ACTIVATED) {
            // jeśli minął termin aktywacji umowy, nie ma aktywnego procesu windykacji formalnej, w przeciwnym wypadku proces w toku
            if (contractTermination.getActivationDate() != null && dateProvider.getCurrentDate().after(contractTermination.getActivationDate())) {
                return null;
            } else {
                return contractTermination.getInvoiceNumberInitiatingDemandForPayment();
            }
        }

        // jeśli wypowiedzenie nie zostało wystawione, to proces windykacji formalnej w toku
        if (contractTermination.getState() != ContractTerminationState.ISSUED) {
            return contractTermination.getInvoiceNumberInitiatingDemandForPayment();
        }

        final boolean initialInvoiceOverdueAtLeast40Percent = isInitialInvoiceOverdueAtLeast40Percent(findExtInvoiceId(contractTermination.getFormalDebtCollectionInvoices(),
                contractTermination.getInvoiceNumberInitiatingDemandForPayment()));
        if (initialInvoiceOverdueAtLeast40Percent) {
            return contractTermination.getInvoiceNumberInitiatingDemandForPayment();
        }

        return null;
    }

    @Override
    public boolean isContractUnderActiveFormalDebtCollection(final String contractNo) {
        return findActiveProcessInitialInvoiceNumberByContractNumber(contractNo) != null;
    }

    @Override
    public Date findIssuedDemandForPaymentIssueDate(final DemandForPaymentTypeKey demandForPaymentTypeKey, final String contractNumber) {
        final Optional<DemandForPayment> demandForPayment = demandForPaymentDao.findLatestDemandForPaymentForContract(demandForPaymentTypeKey, contractNumber,
                DemandForPaymentState.ISSUED);
        return demandForPayment.map(DemandForPayment::getIssueDate).orElse(null);
    }

    @Override
    public Date findIssuedSucceedingDemandForPaymentIssueDate(final DemandForPaymentTypeKey demandForPaymentTypeKey, final String contractNumber) {
        if (demandForPaymentTypeKey == DemandForPaymentTypeKey.SECOND) {
            return null;
        }
        final Optional<DemandForPayment> demandForPayment = demandForPaymentDao.findLatestDemandForPaymentForContract(DemandForPaymentTypeKey.SECOND, contractNumber,
                DemandForPaymentState.ISSUED);
        return demandForPayment.map(DemandForPayment::getIssueDate).orElse(null);
    }

    @Override
    public Boolean isLatestDemandForPaymentOverdue(final String contractNumber) {
        final Optional<DemandForPayment> latestIssuedDemandForPayment = demandForPaymentDao.findLatestDemandForPaymentForContract(contractNumber,
                DemandForPaymentState.ISSUED);

        if (!latestIssuedDemandForPayment.isPresent()) {
            return null;
        }
        final Date dueDate = latestIssuedDemandForPayment.get().getDueDate();
        return dateProvider.getCurrentDateStartOfDay().after(dueDate);
    }

    @Override
    public boolean isContractTerminationPending(final String contractNo) {
        final Optional<ContractTermination> latestContractTerminationOpt = contractTerminationDao.findLatestContractTermination(contractNo);
        if (!latestContractTerminationOpt.isPresent()) {
            return false;
        }
        final ContractTermination contractTermination = latestContractTerminationOpt.get();
        final ContractTerminationState contractTerminationState = contractTermination.getState();
        if (contractTerminationState == ContractTerminationState.ISSUED) {
            return isInitialInvoiceOverdueAtLeast40Percent(findExtInvoiceId(contractTermination.getFormalDebtCollectionInvoices(),
                    contractTermination.getInvoiceNumberInitiatingDemandForPayment()));
        }
        return !ContractTerminationState.CLOSED_CONTRACT_TERMINATION_STATES.contains(contractTerminationState);
    }

    @Override
    public List<DemandForPaymentDto> findDemandsForPaymentExcludingGiven(final DemandForPaymentTypeKey typeKey, final String contractNumber,
                                                                         final String initialInvoiceNo, final Long excludedDemandId) {
        final List<DemandForPayment> demandsForPayment = demandForPaymentDao.findDemandsForPaymentExcludingGiven(typeKey, contractNumber, initialInvoiceNo,
                excludedDemandId);
        return mapper.mapAsList(demandsForPayment, DemandForPaymentDto.class);
    }

    @Override
    public List<DemandForPaymentDto> findDemandsForPayment(final DemandForPaymentTypeKey typeKey, final String contractNumber, final String initialInvoiceNo) {
        final List<DemandForPayment> demandsForPayment = demandForPaymentDao.findDemandsForPayment(typeKey, contractNumber, initialInvoiceNo);
        return mapper.mapAsList(demandsForPayment, DemandForPaymentDto.class);
    }

    @Override
    public void markDemandForPaymentAborted(final Long demandForPaymentId) {
        final DemandForPayment demandForPayment = demandForPaymentDao.get(demandForPaymentId).orElseThrow(() -> new EntityNotFoundException(demandForPaymentId));
        demandForPayment.setAborted(true);
        demandForPaymentDao.update(demandForPayment);
    }

    @Override
    public List<ContractTerminationInitialData> findConditionalContractTerminationsToCreate() {
        final Date yesterday = dateProvider.getDateStartOfDayMinusDays(1);
        final List<DemandForPaymentWithCompanyName> issuedSecondDemandsWithDueDateYesterday = demandForPaymentDao.findIssuedSecondDemandsForPaymentWithDueDate(yesterday);

        if (isEmpty(issuedSecondDemandsWithDueDateYesterday)) {
            LOG.info("No issued second demands for payment with payment date yesterday");
            return emptyList();
        }

        final List<DemandForPayment> filteredByContractState = filterDemandsByValidContractToCreateTermination(issuedSecondDemandsWithDueDateYesterday);
        if (isEmpty(filteredByContractState)) {
            LOG.info("No contracts to create conditional terminations with proper state");
            return emptyList();
        }

        final List<DemandForPayment> demandsForPaymentNotPaid = filterDemandsForPaymentByNotPaid(filteredByContractState);
        if (isEmpty(demandsForPaymentNotPaid)) {
            LOG.info("No contracts not paid to create conditional terminations");
            return emptyList();
        }

        final List<DemandForPayment> demandsForPaymentWithoutContractTermination = filterDemandsForPaymentByExistingContractTermination(demandsForPaymentNotPaid);
        if (isEmpty(demandsForPaymentWithoutContractTermination)) {
            LOG.info("No contracts with not created contract terminations");
            return emptyList();
        }

        return fillDueDatesInDemandForPaymentFromSecondDemandsForPayment(issuedSecondDemandsWithDueDateYesterday,
                mapper.mapAsList(demandsForPaymentWithoutContractTermination, ContractTerminationInitialData.class));
    }

    @Override
    public List<ContractTerminationInitialData> findImmediateContractTerminationsToCreate() {
        final Date conditionalContractTerminationDueDate = dateProvider.getDateStartOfDayMinusDays(NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE);
        final List<ContractTermination> issuedConditionalContractTerminationsWithDueDateYesterday =
                contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(conditionalContractTerminationDueDate);

        if (isEmpty(issuedConditionalContractTerminationsWithDueDateYesterday)) {
            LOG.info("No issued conditional contract termination with payment date yesterday");
            return emptyList();
        }

        final List<ContractTermination> filteredByContractState =
                filterTerminationsByValidContractToCreateTermination(issuedConditionalContractTerminationsWithDueDateYesterday);
        if (isEmpty(filteredByContractState)) {
            LOG.info("No contracts to create immediate terminations with proper state");
            return emptyList();
        }

        final List<ContractTermination> conditionalTerminationsNotPaid = filterTerminationsByNotPaid(filteredByContractState);
        if (isEmpty(conditionalTerminationsNotPaid)) {
            LOG.info("No contracts not paid to create immediate terminations");
            return emptyList();
        }

        final List<ContractTermination> contractTerminationsWithoutImmediateContractTermination =
                filterContractTerminationsByExistingImmediateContractTermination(conditionalTerminationsNotPaid);
        if (isEmpty(contractTerminationsWithoutImmediateContractTermination)) {
            LOG.info("No contracts with not created immediate contract terminations");
            return emptyList();
        }

        return fillDueDatesInDemandForPaymentFromConditionalContractTerminations(issuedConditionalContractTerminationsWithDueDateYesterday,
                mapper.mapAsList(contractTerminationsWithoutImmediateContractTermination, ContractTerminationInitialData.class));
    }

    /**
     * Uzupełnienie terminu wskazanego do zapłaty na wezwaniu na podstawie wypowiedzeń warunkowych
     *
     * @param issuedContractTerminationsWithDueDateYesterday - lista wypowiedzeń warunkowych, na podstawie utworzono listę danych do utworzenia wypowiedzenia
     * @param contractTerminationInitialData                 - lista danych do utworzenia wypowiedzenia
     * @return lista danych do utworzenia wypowiedzenia z uzupełnioną datą płatności z wezwania
     */
    protected List<ContractTerminationInitialData> fillDueDatesInDemandForPaymentFromConditionalContractTerminations(final List<ContractTermination> issuedContractTerminationsWithDueDateYesterday,
                                                                                                                     final List<ContractTerminationInitialData> contractTerminationInitialData) {
        final Map<String, ContractTermination> contractNoToContractTermination = issuedContractTerminationsWithDueDateYesterday.stream()
                .collect(toMap(ContractTermination::getContractNumber, Function.identity()));
        contractTerminationInitialData.forEach(contractTerminationInitData -> {
                    final String contractNumber = contractTerminationInitData.getContractNumber();
                    final ContractTermination contractTermination = contractNoToContractTermination.get(contractNumber);
                    if (contractTermination != null) {
                        contractTerminationInitData.setDueDateInDemandForPayment(contractTermination.getDueDateInDemandForPayment());
                    } else {
                        LOG.warn("No issued conditional termination with due date yesterday present for contract no {}", contractNumber);
                    }
                }
        );
        return contractTerminationInitialData;
    }

    /**
     * Uzupełnienie terminu wskazanego do zapłaty na wezwaniu na podstawie wezwań ostatecznych
     *
     * @param issuedSecondDemandsForPaymentWithDueDateYesterday - lista wezwań ostatecznych, na podstawie których utworzono listę danych do utworzenia wypowiedzenia
     * @param contractTerminationInitialData                    - lista danych do utworzenia wypowiedzenia
     * @return lista danych do utworzenia wypowiedzenia z uzupełnioną datą płatności z wezwania
     */
    protected List<ContractTerminationInitialData> fillDueDatesInDemandForPaymentFromSecondDemandsForPayment(
            final List<DemandForPaymentWithCompanyName> issuedSecondDemandsForPaymentWithDueDateYesterday,
            final List<ContractTerminationInitialData> contractTerminationInitialData) {

        final Map<String, DemandForPayment> contractNoToDemandForPayment = issuedSecondDemandsForPaymentWithDueDateYesterday.stream()
                .collect(toMap(DemandForPayment::getContractNumber, Function.identity()));
        contractTerminationInitialData.forEach(contractTerminationInitData -> {
                    final String contractNumber = contractTerminationInitData.getContractNumber();
                    final DemandForPayment demandForPayment = contractNoToDemandForPayment.get(contractNumber);
                    if (demandForPayment != null) {
                        contractTerminationInitData.setDueDateInDemandForPayment(demandForPayment.getDueDate());
                    } else {
                        LOG.warn("No issued second demand for payment with due date yesterday present for contract no {}", contractNumber);
                    }
                }
        );
        return contractTerminationInitialData;
    }

    /**
     * Filtrowanie wezwań, gdzie należność (faktura), która spowodowała wysłanie wezwania do zapłaty pozostaje zaległa w 40% w stosunku do wartości początkowej
     *
     * @param demandsForPayment - lista wezwań do zapłaty
     * @return lista wezwań, które posiadają zaległość na fakturze inicjującej
     */
    protected List<DemandForPayment> filterDemandsForPaymentByNotPaid(final List<DemandForPayment> demandsForPayment) {
        return demandsForPayment.stream()
                .filter(demandForPayment ->
                        isInitialInvoiceOverdueAtLeast40Percent(findExtInvoiceId(demandForPayment.getInvoices(), demandForPayment.getInitialInvoiceNo())))
                .collect(toList());
    }

    /**
     * Filtrowanie wezwań, dla których już utworzono wypowiedzenie warunkowe
     *
     * @param demandsForPayment - lista wezwań do zapłaty
     * @return lista wezwań, które nie posiadają wypowiedzenia
     */
    protected List<DemandForPayment> filterDemandsForPaymentByExistingContractTermination(final List<DemandForPayment> demandsForPayment) {
        return demandsForPayment.stream()
                .filter(demandForPayment -> !isContractTerminationAlreadyCreatedForDemandForPayment(demandForPayment.getId()))
                .collect(toList());
    }

    private boolean isContractTerminationAlreadyCreatedForDemandForPayment(final Long precedingDemandForPaymentId) {
        return contractTerminationDao.findContractTerminationForPrecedingDemandForPayment(precedingDemandForPaymentId).isPresent();
    }

    /**
     * Filtrowanie wezwań, dla których już utworzono wypowiedzenie natychmiastowe
     *
     * @param contractTerminations - lista wypowiedzeń warunkowych
     * @return lista wypowiedzeń warunkowych, które nie posiadają wypowiedzenia natychmiastowego
     */
    protected List<ContractTermination> filterContractTerminationsByExistingImmediateContractTermination(final List<ContractTermination> contractTerminations) {
        return contractTerminations.stream()
                .filter(contractTermination -> !isImmediateContractTerminationAlreadyCreatedForConditionalContractTermination(contractTermination.getId()))
                .collect(toList());
    }

    private boolean isImmediateContractTerminationAlreadyCreatedForConditionalContractTermination(final Long precedingContractTerminationId) {
        return contractTerminationDao.findContractTerminationForPrecedingContractTermination(precedingContractTerminationId).isPresent();
    }

    /**
     * Filtrowanie wypowiedzeń, gdzie należność (faktura), która spowodowała wysłanie wypowiedzenia pozostaje zaległa w 40% w stosunku do wartości początkowej
     *
     * @param contractTerminations - lista wypowiedzeń
     * @return lista wypowiedzeń, które posiadają zaległość na fakturze inicjującej
     */
    protected List<ContractTermination> filterTerminationsByNotPaid(final List<ContractTermination> contractTerminations) {
        return contractTerminations.stream()
                .filter(contractTermination ->
                        isInitialInvoiceOverdueAtLeast40Percent(findExtInvoiceId(contractTermination.getFormalDebtCollectionInvoices(),
                                contractTermination.getInvoiceNumberInitiatingDemandForPayment())))
                .collect(toList());
    }

    /**
     * Czy faktura z wezwania zaległa przynajmniej 40% w stosunku do wartości początkowej
     *
     * @param extInvoiceId - identyfikator faktury inicjującej w AIDA
     * @return <code>true</code> jeśli faktura inicjująca zaległa przynajmniej 40% w stosunku do wartości początkowej
     */
    protected boolean isInitialInvoiceOverdueAtLeast40Percent(final Long extInvoiceId) {
        final AidaInvoiceDto invoiceWithBalance = aidaInvoiceService.getInvoiceWithBalance(extInvoiceId);
        final BigDecimal balanceOnDocument = invoiceWithBalance.getBalanceOnDocument();
        if (balanceOnDocument.signum() >= 0) {
            // faktura inicjująca spłacona
            return false;
        }
        final BigDecimal grossAmount = invoiceWithBalance.getGrossAmount();
        final BigDecimal leftToPay = balanceOnDocument.abs();

        final BigDecimal overduePercentage = BigDecimal.valueOf(leftToPay.doubleValue() / grossAmount.doubleValue());
        return FORTY_PERCENT.compareTo(overduePercentage) <= 0;
    }

    protected List<DemandForPayment> filterDemandsByValidContractToCreateTermination(final List<DemandForPaymentWithCompanyName> demandsForPayment) {
        return demandsForPayment.stream().filter(demandForPayment -> isContractValidToCreateTermination(demandForPayment.getContractNumber())).collect(toList());
    }

    protected List<ContractTermination> filterTerminationsByValidContractToCreateTermination(final List<ContractTermination> contractTerminations) {
        return contractTerminations.stream().filter(contractTermination -> isContractValidToCreateTermination(contractTermination.getContractNumber())).collect(toList());
    }

    /**
     * Sprawdzenie czy dla kontraktu zgodnie z regułami biznesowymi można wystawić wypowiedzenie
     *
     * @param contractNo - numer umowy
     * @return <code>true</code> jeśli kontrakt możliwy do wypowiedzenia
     */
    protected boolean isContractValidToCreateTermination(final String contractNo) {
        final AidaContractDto contract = contractService.findActiveContractByContractNo(contractNo);
        if (contract == null) {
            return false;
        }
        return CONTRACT_STATUS_VALID_TO_CREATE_TERMINATION.contains(contract.getStateSymbol());
    }

    protected boolean isInitialInvoiceNotPaid(final DemandForPayment demandForPayment) {
        final String initialInvoiceNo = demandForPayment.getInitialInvoiceNo();
        final Long initialExtInvoiceId = findExtInvoiceId(demandForPayment.getInvoices(), initialInvoiceNo);
        final AidaInvoiceDto initialInvoiceWithCurrentBalance = aidaInvoiceService.getInvoiceWithBalance(initialExtInvoiceId);
        return customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(initialInvoiceWithCurrentBalance,
                demandForPayment.getType().getMinDuePaymentValue());
    }

    /**
     * Pobranie identyfikatora faktury z kolekcji dłużnych faktur
     *
     * @param invoices  - dłużne faktury
     * @param invoiceNo - numer faktury
     * @return identyfikator faktury AIDA
     */
    protected Long findExtInvoiceId(final Collection<FormalDebtCollectionInvoice> invoices, final String invoiceNo) {
        return invoices.stream().filter(invoice -> invoice.getInvoiceNumber().equals(invoiceNo)).findFirst()
                .map(FormalDebtCollectionInvoice::getLeoId)
                .orElseThrow(() -> new IllegalStateException(format("No invoice with number %s within due invoices!!!", invoiceNo)));
    }
}
