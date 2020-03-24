package com.codersteam.alwin.core.service.impl.notice;

import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.model.AidaSimpleInvoiceDto;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.contract.ContractService;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentCreatorService;
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentInitialData;
import com.codersteam.alwin.db.dao.DemandForPaymentTypeConfigurationDao;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class CreateDemandForPaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateDemandForPaymentService.class.getName());

    /**
     * faktura czynszowa (3 - Opłata okresowa)
     */
    private static final Long PERIODIC_FEE_INVOICE_TYPE_ID = 3L;

    private final DemandForPaymentTypeConfigurationDao demandForPaymentTypeConfigurationDao;
    private final DemandForPaymentCreatorService demandForPaymentCreatorService;
    private final InvoiceService aidaInvoiceService;
    private final CustomerVerifierService customerVerifierService;
    private final DateProvider dateProvider;
    private final CustomerService customerService;
    private final com.codersteam.alwin.core.api.service.contract.ContractService contractService;

    @Inject
    public CreateDemandForPaymentService(DemandForPaymentTypeConfigurationDao demandForPaymentTypeConfigurationDao,
                                         DemandForPaymentCreatorService demandForPaymentCreatorService,
                                         CustomerVerifierService customerVerifierService,
                                         AidaService aidaService,
                                         DateProvider dateProvider,
                                         CustomerService customerService,
                                         ContractService contractService) {
        this.demandForPaymentTypeConfigurationDao = demandForPaymentTypeConfigurationDao;
        this.demandForPaymentCreatorService = demandForPaymentCreatorService;
        this.aidaInvoiceService = aidaService.getInvoiceService();
        this.customerVerifierService = customerVerifierService;
        this.dateProvider = dateProvider;
        this.customerService = customerService;
        this.contractService = contractService;
    }

    public void prepareDemandsForPayment() {
        final List<DemandForPaymentTypeConfiguration> demandForPaymentTypes = getAllDemandForPaymentTypes();
        demandForPaymentTypes.forEach(demandForPaymentType -> {
            try {
                prepareDemandsForPaymentsForType(demandForPaymentType);
            } catch (final Exception e) {
                LOG.error("Demands for payment prepare failed for {}", demandForPaymentType);
            }
        });
    }

    private List<DemandForPaymentTypeConfiguration> getAllDemandForPaymentTypes() {
        return demandForPaymentTypeConfigurationDao.findAllDemandForPaymentTypes();
    }

    protected void prepareDemandsForPaymentsForType(final DemandForPaymentTypeConfiguration demandForPaymentType) {
        Date dueDateTo = dateProvider.getDateStartOfDayMinusDays(demandForPaymentType.getDpdStart());
        Date dueDateFrom = dateProvider.getDateStartOfDayMinusDays(demandForPaymentType.getDpdStart());

        LOG.info("Creating demands for payment for type with id={} for due documents from {} started", demandForPaymentType.getId(), dueDateFrom);

        final List<AidaSimpleInvoiceDto> invoicesFromAIDA = aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(dueDateFrom, dueDateTo, demandForPaymentType.getMinDuePaymentValue());

        if (isEmpty(invoicesFromAIDA)) {
            LOG.info("No documents found to create demands for payment {} for date {}", demandForPaymentType, dueDateFrom);
            return;
        }

        final Date currentDate = dateProvider.getCurrentDate();
        final List<AidaSimpleInvoiceDto> invoicesToCreateDemandForPayment = invoicesFromAIDA.stream()
                .filter(invoice -> !customerService.isFormalDebtCollectionCustomerOutOfService(invoice.getCompanyId(), currentDate, demandForPaymentType.getKey()))
                .filter(invoice -> customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(invoice, demandForPaymentType.getMinDuePaymentValue()))
                .collect(toList());


        if (isEmpty(invoicesToCreateDemandForPayment)) {
            LOG.info("No documents qualifying to create demands for payment {} found for date {}", demandForPaymentType, dueDateFrom);
            return;
        }
        final List<DemandForPaymentInitialData> demandsForPaymentsToCreate = transformInvoicesToDemandForPaymentInitialData(demandForPaymentType.getId(),
                invoicesToCreateDemandForPayment, demandForPaymentType.getKey());

        demandsForPaymentsToCreate.forEach(demandForPaymentsToCreate -> {
                    try {
                        demandForPaymentCreatorService.prepareDemandForPayment(demandForPaymentsToCreate);
                    } catch (final Exception e) {
                        // TODO ponowienie tworzenia wezwania #29106
                        LOG.error("Create demand for payment failed for {}", demandForPaymentsToCreate, e);
                    }
                }
        );
        LOG.info("Creating demands for payment for type with id={} for due documents from {} finished", demandForPaymentType.getId(), dueDateFrom);
    }

    protected List<DemandForPaymentInitialData> transformInvoicesToDemandForPaymentInitialData(final Long demandForPaymentTypeId,
                                                                                               final List<AidaSimpleInvoiceDto> invoicesToCreateDemandForPayment,
                                                                                               final DemandForPaymentTypeKey demandForPaymentTypeKey) {

        final List<AidaSimpleInvoiceDto> initialInvoices = filterMultipleContractInitialInvoices(invoicesToCreateDemandForPayment, demandForPaymentTypeKey);

        return initialInvoices.stream()
                .map(invoice -> new DemandForPaymentInitialData(demandForPaymentTypeId, invoice.getCompanyId(), invoice.getContractNo(), invoice.getNumber()))
                .collect(toList());
    }

    protected List<AidaSimpleInvoiceDto> filterMultipleContractInitialInvoices(final List<AidaSimpleInvoiceDto> invoicesToCreateDemandForPayment,
                                                                               final DemandForPaymentTypeKey demandForPaymentTypeKey) {
        final Map<String, List<AidaSimpleInvoiceDto>> contractInvoices = invoicesToCreateDemandForPayment.stream()
                .collect(groupingBy(AidaSimpleInvoiceDto::getContractNo));

        final Date currentDate = dateProvider.getCurrentDate();
        return contractInvoices.entrySet().stream()
                .filter(entry -> !contractService.isFormalDebtCollectionContractOutOfService(entry.getKey(), currentDate, demandForPaymentTypeKey))
                .map(contractToInvoices -> determineInitialInvoice(contractToInvoices.getValue()))
                .collect(toList());
    }

    /**
     * Wyznaczenie faktury inicjującej dla kontraktu z uwzględnieniem kilku faktur z danego dnia
     * <p>
     * Jeśli jest w nich faktura czynszowa (3 - Opłata okresowa) – uznajemy fakturę czynszową jako inicjującą. Jeśli nie ma – ta z mniejszym saldem.
     * Jeśli salda są identyczne – ta pierwsza wystawiona, a jak i to jest identyczne, to pierwsza lepsza.
     */
    protected AidaSimpleInvoiceDto determineInitialInvoice(final List<AidaSimpleInvoiceDto> contractInvoices) {
        // jeśli jest tylko 1 faktura dla kontraktu, to jest ona zwracana
        if (contractInvoices.size() == 1) {
            return contractInvoices.get(0);
        }

        // pobranie pełnych danych faktur z AIDA
        final List<AidaInvoiceDto> invoices = getFullInvoiceData(contractInvoices);

        // jeśli jest faktura czynszowa, to jest ona fakturą inicjującą
        final Optional<AidaInvoiceDto> rentInvoice = findRentInvoice(invoices);

        if (rentInvoice.isPresent()) {
            return getInvoiceWithId(contractInvoices, rentInvoice.get().getId());
        }

        // Posortowanie faktury po: saldo (rosnąco), data wystawienia (rosnąco), id (rosnąco)
        final Optional<AidaInvoiceDto> initialInvoice = invoices.stream().min(comparing(AidaInvoiceDto::getBalanceOnDocument)
                .thenComparing(comparing(AidaInvoiceDto::getIssueDate).thenComparing(AidaInvoiceDto::getId)));

        // pobranie faktury inicjującej (najmniejsze saldo [największe zadłużenie] -> najwcześniej wystawiona -> najmniejsze id)
        return getInvoiceWithId(contractInvoices,
                initialInvoice.orElseThrow(() -> new IllegalStateException("No invoice in collection - should never happen")).getId());
    }

    private List<AidaInvoiceDto> getFullInvoiceData(final List<AidaSimpleInvoiceDto> contractInvoices) {
        return contractInvoices.stream().map(invoice -> aidaInvoiceService.getInvoiceWithBalance(invoice.getId())).collect(toList());
    }

    /**
     * Zwraca fakturę czynszową jeśli występuje
     *
     * @param invoices - kolekcja faktur
     * @return faktura czynszowa
     */
    private Optional<AidaInvoiceDto> findRentInvoice(final List<AidaInvoiceDto> invoices) {
        return invoices.stream().filter(invoice -> PERIODIC_FEE_INVOICE_TYPE_ID.equals(invoice.getInvoiceType().getId())).findFirst();
    }

    /**
     * Pobranie z kolekcji faktury o danym id
     *
     * @param invoices  - kolekcja faktur
     * @param invoiceId - identyfikator żądanej faktury
     * @return faktura o podanym id
     */
    private AidaSimpleInvoiceDto getInvoiceWithId(final List<AidaSimpleInvoiceDto> invoices, final Long invoiceId) {
        return invoices.stream().filter(invoice -> invoice.getId().equals(invoiceId)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("No invoice with id %d", invoiceId)));
    }

}
