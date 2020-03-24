package com.codersteam.alwin.core.service.impl.notice;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.core.api.service.issue.InvoiceService;
import com.codersteam.alwin.core.api.service.notice.*;
import com.codersteam.alwin.core.service.impl.customer.CustomerServiceImpl;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.db.dao.DemandForPaymentTypeConfigurationDao;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResult.*;
import static java.util.Comparator.comparing;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Stateless
public class DemandForPaymentCreatorServiceImpl implements DemandForPaymentCreatorService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final BigDecimal INITIAL_INVOICE_BALANCE_THRESHOLD_FOR_MANUAL_CREATED_DEMAND_FOR_PAYMENT = new BigDecimal("-100.00");

    private DemandForPaymentTypeConfigurationDao demandForPaymentTypeConfigurationDao;
    private DateProvider dateProvider;
    private DemandForPaymentDao demandForPaymentDao;
    private FormalDebtCollectionService formalDebtCollectionService;
    private InvoiceService invoiceService;
    private CustomerVerifierService customerVerifierService;
    private AlwinMapper mapper;
    private CustomerServiceImpl customerService;

    public DemandForPaymentCreatorServiceImpl() {
    }

    @Inject
    public DemandForPaymentCreatorServiceImpl(DemandForPaymentTypeConfigurationDao demandForPaymentTypeConfigurationDao,
                                              DateProvider dateProvider,
                                              DemandForPaymentDao demandForPaymentDao,
                                              FormalDebtCollectionService formalDebtCollectionService,
                                              InvoiceService invoiceService,
                                              CustomerVerifierService customerVerifierService,
                                              AlwinMapper mapper,
                                              CustomerServiceImpl customerService) {
        this.demandForPaymentTypeConfigurationDao = demandForPaymentTypeConfigurationDao;
        this.dateProvider = dateProvider;
        this.demandForPaymentDao = demandForPaymentDao;
        this.formalDebtCollectionService = formalDebtCollectionService;
        this.invoiceService = invoiceService;
        this.customerVerifierService = customerVerifierService;
        this.mapper = mapper;
        this.customerService = customerService;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void prepareDemandForPayment(final DemandForPaymentInitialData demandForPaymentInitialData) {
        LOG.info("Creating demand for payment for {}", demandForPaymentInitialData);

        final DemandForPaymentTypeConfiguration demandForPaymentType = getDemandForPaymentTypeConfiguration(demandForPaymentInitialData.getDemandForPaymentTypeId());

        // sprawdzamy czy segment klienta odpowiada segmentowi z typu wezwania
        final Segment customerSegment = getCompanySegment(demandForPaymentInitialData.getExtCompanyId());
        if (customerSegment != demandForPaymentType.getSegment()) {
            // w przypadku niewłaściwego segmentu wezwanie nie jest tworzone
            LOG.info("Demand for payment for extCompanyId={} not created due improper segment: companySegment={}, demandForPaymentTypeSegment={}",
                    demandForPaymentInitialData.getExtCompanyId(), customerSegment, demandForPaymentType.getSegment());
            return;
        }

        // sprawdzamy czy nie ma już aktywnego wezwania/wypowiedzenia do tej umowy jeśli tak, to nie tworzymy wezwań (aktywny proces windykacji formalnej)
        final String contractNo = demandForPaymentInitialData.getContractNo();
        if ((demandForPaymentType.getKey() == DemandForPaymentTypeKey.FIRST) && formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(contractNo)) {
            LOG.info("Contract {} already has active formal debt collection process", contractNo);
            return;
        }

        // jeśli wezwanie 2 to sprawdzamy czy uprzednio wygenerowano wezwanie 1 (później dojdzie warunek flagi na "rozruch" #29059)
        if ((demandForPaymentType.getKey() == DemandForPaymentTypeKey.SECOND)) {
            if (formalDebtCollectionService.shouldGenerateSecondDemandForPaymentForContract(contractNo)) {
                demandForPaymentInitialData.setPrecedingDemandForPaymentId(formalDebtCollectionService.findPrecedingDemandForPaymentId(contractNo));
            } else {
                LOG.warn("Could not create SECOND demand for payment without FIRST one for {}", demandForPaymentInitialData);
                return;
            }
        }

        final List<FormalDebtCollectionInvoice> formalDebtCollectionInvoices = getFormalDebtCollectionInvoices(demandForPaymentInitialData.getExtCompanyId(),
                contractNo);

        Date currentDateStartOfDay = dateProvider.getCurrentDateStartOfDay();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDateStartOfDay);
        calendar.add(Calendar.DATE, -1);
        Date dateChecker;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -2);
            dateChecker = calendar.getTime();
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
            dateChecker = calendar.getTime();
        } else {
            dateChecker = calendar.getTime();
        }

        Date finalDateChecker = dateChecker;
        List<FormalDebtCollectionInvoice> collect = formalDebtCollectionInvoices.stream().filter(invoice -> invoice.getRealDueDate().before(finalDateChecker)).collect(Collectors.toList());
        prepareAndStoreNewDemandForPaymentForContract(demandForPaymentType, demandForPaymentInitialData, collect, false);

        LOG.info("Demand for payment for {} successfully created", demandForPaymentInitialData);
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public ManualPrepareDemandForPaymentResult prepareManualDemandForPayment(final ManualDemandForPaymentInitialData initialData) {
        LOG.info("Creating manual demand for payment for {}", initialData);

        final Long extCompanyId = initialData.getExtCompanyId();
        final Segment companySegment = getCompanySegment(extCompanyId);
        final DemandForPaymentTypeConfiguration demandForPaymentType =
                demandForPaymentTypeConfigurationDao.findDemandForPaymentTypeConfigurationByKeyAndSegment(initialData.getTypeKey(), companySegment);

        final String contractNo = initialData.getContractNo();
        final List<AidaInvoiceWithCorrectionsDto> contractDueInvoices = getContractDueInvoices(extCompanyId, contractNo);
        if (isEmpty(contractDueInvoices)) {
            LOG.warn("No due invoices for contract {}. Manual demand for payment not created!", contractNo);
            return noDueInvoicesForContract(contractNo);
        }

        if (formalDebtCollectionService.isContractTerminationPending(contractNo)) {
            LOG.warn("Contract terminations already pending for contract {}. Manual demand for payment not created!", contractNo);
            return contractAlreadyInTerminationStage(contractNo);
        }

        final List<FormalDebtCollectionInvoice> formalDebtCollectionInvoices = mapper.mapAsList(contractDueInvoices, FormalDebtCollectionInvoice.class);
        final DemandForPaymentInitialData demandForPaymentInitialData = mapper.map(initialData, DemandForPaymentInitialData.class);

        demandForPaymentInitialData.setInitialInvoiceNo(findInitialInvoiceNumberForManualCreation(contractNo, contractDueInvoices));

        prepareAndStoreNewDemandForPaymentForContract(demandForPaymentType, demandForPaymentInitialData, formalDebtCollectionInvoices, true);

        LOG.info("Manual Demand for payment for {} successfully created", initialData);
        return successful(contractNo);
    }

    protected String findInitialInvoiceNumberForManualCreation(final String contractNo, final List<AidaInvoiceWithCorrectionsDto> dueInvoices) {
        final String initialInvoiceNo = formalDebtCollectionService.findActiveProcessInitialInvoiceNumberByContractNumber(contractNo);
        if (initialInvoiceNo != null) {
            return initialInvoiceNo;
        }
        return determineInitialInvoiceForManualCreationWhenNoActiveProcessPending(dueInvoices);
    }

    /**
     * Wyznaczenie numeru faktury inicjującej ręcznie tworzonego wezwania w przypadku braku aktywnego procesu windykacji formalnej
     * 1. wybieramy najbardziej przeterminowaną fakturę z saldem < -100 (lub równowartość)
     * 2. jeśli nie ma faktur z saldem < -100, to wybieramy najbardziej przeterminowaną
     *
     * @param dueInvoices - zaległe faktury kontraktu
     * @return numer faktury inicjującej w przypadku braku aktywnego procesu windykacji formalnej
     */
    protected String determineInitialInvoiceForManualCreationWhenNoActiveProcessPending(
            final List<AidaInvoiceWithCorrectionsDto> dueInvoices) {

        return dueInvoices
                .stream()
                .filter(this::isBalanceBelowThreshold)
                .min(comparing(AidaInvoiceWithCorrectionsDto::getRealDueDate))
                .map(AidaInvoiceWithCorrectionsDto::getNumber)
                .orElseGet(() -> dueInvoices.stream()
                        .min(comparing(AidaInvoiceWithCorrectionsDto::getRealDueDate))
                        .map(AidaInvoiceWithCorrectionsDto::getNumber).orElse(null)
                );
    }

    private boolean isBalanceBelowThreshold(final AidaInvoiceWithCorrectionsDto invoice) {
        return customerVerifierService.isBalanceEnoughToCreateIssue(invoice.getBalanceOnDocument(), Currency.valueOf(invoice.getCurrency()),
                invoice.getExchangeRate(), INITIAL_INVOICE_BALANCE_THRESHOLD_FOR_MANUAL_CREATED_DEMAND_FOR_PAYMENT);
    }

    /**
     * Pobranie wszystkich dłużnych dokumentów umowy będących przedmiotem wezwania
     *
     * @param extCompanyId - identyfikator firmy w systemie zewnętrznym
     * @param contractNo   - numer umowy
     * @return lista dłużnych dokumentów
     */
    private List<FormalDebtCollectionInvoice> getFormalDebtCollectionInvoices(final Long extCompanyId, final String contractNo) {
        final List<AidaInvoiceWithCorrectionsDto> contractDueInvoices = getContractDueInvoices(extCompanyId, contractNo);
        return mapper.mapAsList(contractDueInvoices, FormalDebtCollectionInvoice.class);
    }

    private List<AidaInvoiceWithCorrectionsDto> getContractDueInvoices(final Long extCompanyId, final String contractNo) {
        return invoiceService.getContractDueInvoices(extCompanyId, contractNo, dateProvider.getCurrentDateStartOfDay());
    }

    private DemandForPaymentTypeConfiguration getDemandForPaymentTypeConfiguration(final Long demandForPaymentTypeId) {
        return demandForPaymentTypeConfigurationDao.get(demandForPaymentTypeId).orElseThrow(() -> new EntityNotFoundException(demandForPaymentTypeId));
    }

    private Segment getCompanySegment(final Long extCompanyId) {
        final Customer customer = customerService.getCustomerWithAdditionalData(extCompanyId);
        return customer.getCompany().getSegment();
    }

    protected void prepareAndStoreNewDemandForPaymentForContract(final DemandForPaymentTypeConfiguration demandForPaymentTypeConfiguration,
                                                                 final DemandForPaymentInitialData demandForPaymentInitialData,
                                                                 final List<FormalDebtCollectionInvoice> contractDueInvoices, final boolean createdManually) {
        final DemandForPayment demandForPayment = new DemandForPayment();
        demandForPayment.setInitialInvoiceNo(demandForPaymentInitialData.getInitialInvoiceNo());
        demandForPayment.setExtCompanyId(demandForPaymentInitialData.getExtCompanyId());
        demandForPayment.setType(demandForPaymentTypeConfiguration);
        demandForPayment.setInvoices(new HashSet<>(contractDueInvoices));
        demandForPayment.setState(DemandForPaymentState.NEW);
        demandForPayment.setContractNumber(demandForPaymentInitialData.getContractNo());
        demandForPayment.setIssueDate(dateProvider.getCurrentDateStartOfDay());
        demandForPayment.setDueDate(dateProvider.getDateStartOfDayPlusDays(demandForPaymentTypeConfiguration.getNumberOfDaysToPay()));
        demandForPayment.setPrecedingDemandForPaymentId(demandForPaymentInitialData.getPrecedingDemandForPaymentId());
        demandForPayment.setCreatedManually(createdManually);
        demandForPaymentDao.createDemandForPayment(demandForPayment);
    }
}
