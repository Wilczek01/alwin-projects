package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.model.AidaSimpleInvoiceDto;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static com.codersteam.alwin.core.api.model.currency.Currency.forStringValue;

@Stateless
public class CustomerVerifierServiceImpl implements CustomerVerifierService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private CustomerService customerService;
    private IssueService issueService;
    private DateProvider dateProvider;
    private InvoiceService invoiceService;

    public CustomerVerifierServiceImpl() {
    }

    @Inject
    public CustomerVerifierServiceImpl(CustomerService customerService,
                                       IssueService issueService,
                                       DateProvider dateProvider,
                                       AidaService aidaService) {
        this.customerService = customerService;
        this.issueService = issueService;
        this.dateProvider = dateProvider;
        this.invoiceService = aidaService.getInvoiceService();
    }

    @Override
    public boolean filterCustomersForIssueCreation(Long customerId, List<AidaSimpleInvoiceDto> customerInvoices) {
        // Warunki filtrowania (za specyfikacja funkcjonalną, pkt 4.3.1
        // 1. posiada  dokument przeterminowany o ilość dni wyrażoną parametrem DPDStart
        // 2. zaległość na dokumencie jest większa niż kwota wyrażona parametrem MinBalanceStart
        // 3. umowa, z której pochodzi należność ma status „Aktywny” (patrz: Integracja z LEO - Pobranie danych o umowie i kliencie w rozdziale 2.)
        // spełnione w IssueService, pobierane są właśnie takie dokumenty

        // 6. klient posiada faktury po odfiltrowaniu tych, które dotyczą wykluczonych z obsługi kontraktów
        boolean hasNonExcludedInvoices = hasNonExcludedInvoices(customerId, customerInvoices);
        if (!hasNonExcludedInvoices) {
            LOG.info("filterCustomersForIssueCreation - Customer id: {} , hasNonExcludedInvoices: false", customerId);
            return false;
        }

        // 4. klient nie posiada aktywnego zlecenia windykacyjnego (całościowo dla klienta lub dla umowy, z której pochodzi przeterminowany dokument)
        boolean companyHasNoActiveIssue = !issueService.doesCompanyHaveActiveIssue(customerId);
        if (!companyHasNoActiveIssue) {
            LOG.info("filterCustomersForIssueCreation - Customer id: {} , companyHasNoActiveIssue: false", customerId);
            return false;
        }

        // 5. klient nie posiada wykluczenia z obsługi windykacyjnej (patrz: Obiekty biznesowe – Klient w tym samym rozdziale.
        boolean customerIsNotExcluded = !isCompanyExcludedFromDebtCollection(customerId);
        if (!customerIsNotExcluded) {
            LOG.info("filterCustomersForIssueCreation - Customer id: {} , customerIsNotExcluded: false", customerId);
            return false;
        }
        LOG.info("filterCustomersForIssueCreation - Customer id: {} , hasNonExcludedInvoices: true," +
                " companyHasNoActiveIssue: true, customerIsNotExcluded: true", customerId);
        return true;
    }

    @Override
    public boolean isCompanyExcludedFromDebtCollection(final Long extCustomerId) {
        return customerService.findCustomerByExtId(extCustomerId)
                .map(customerDto -> customerService.isCustomerOutOfService(customerDto.getId(), dateProvider.getCurrentDateStartOfDay()))
                .orElse(false);
    }

    @Override
    public boolean isBalanceEnoughToCreateIssue(final BigDecimal balanceOnDocument, final Currency currency, final BigDecimal exchangeRate, final BigDecimal minBalanceStart) {
        if (PLN.equals(currency)) {
            return minBalanceStart.compareTo(balanceOnDocument) >= 1;
        }
        return minBalanceStart.compareTo(balanceOnDocument.multiply(exchangeRate)) >= 1;
    }

    @Override
    public boolean isInvoiceBalanceBelowMinBalanceStart(final AidaSimpleInvoiceDto invoice, final BigDecimal minBalanceStart) {
        final AidaInvoiceDto invoiceWithBalance = invoiceService.getInvoiceWithBalance(invoice.getId());
        if (invoiceWithBalance == null) {
            LOG.error("Can not find invoice with balance for invoice id = {}", invoice.getId());
            return false;
        }
        return isInvoiceBalanceBelowMinBalanceStart(invoiceWithBalance, minBalanceStart);
    }

    @Override
    public boolean isInvoiceBalanceBelowMinBalanceStart(final AidaInvoiceDto invoice, final BigDecimal minBalanceStart) {
        final BigDecimal balance = invoice.getBalanceOnDocument() == null ? ZERO : invoice.getBalanceOnDocument();
        final Currency currency = forStringValue(invoice.getCurrency(), invoice.getNumber());
        return isBalanceEnoughToCreateIssue(balance, currency, invoice.getExchangeRate(), minBalanceStart);
    }

    protected boolean hasNonExcludedInvoices(Long extCompanyId, List<AidaSimpleInvoiceDto> customerInvoices) {
        Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);
        return customerInvoices.stream()
                .anyMatch(invoice -> !contractsOutOfService.contains(invoice.getContractNo()));
    }
}
