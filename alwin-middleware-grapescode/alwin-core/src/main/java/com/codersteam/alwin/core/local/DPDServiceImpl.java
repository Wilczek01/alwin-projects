package com.codersteam.alwin.core.local;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.IssueInvoice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.currency.Currency.forStringValue;

@Stateless
public class DPDServiceImpl implements DPDService {

    private CustomerService customerService;
    private CustomerVerifierService customerVerifierService;

    public DPDServiceImpl() {
    }

    @Inject
    public DPDServiceImpl(final CustomerService customerService, final CustomerVerifierService customerVerifierService) {
        this.customerService = customerService;
        this.customerVerifierService = customerVerifierService;
    }

    @Override
    public Date calculateDPDStartDateForChildIssue(List<IssueInvoice> issueInvoices, Long extCompanyId, BigDecimal minBalanceStart) {
        Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);

        return issueInvoices.stream()
                .filter(IssueInvoice::getIssueSubject)
                .filter(issueInvoice -> !issueInvoice.isExcluded())
                .filter(issueInvoice -> issueInvoice.getInvoice().getRealDueDate() != null)
                .map(IssueInvoice::getInvoice)
                .filter(invoice -> !contractsOutOfService.contains(invoice.getContractNumber()))
                .filter(invoice -> isBalanceEnoughToCreateIssue(invoice, minBalanceStart))
                .map(Invoice::getRealDueDate)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    @Override
    public Date calculateDPDStartDateForNewIssue(List<AidaInvoiceWithCorrectionsDto> startInvoices, Long extCompanyId, BigDecimal minBalanceStart) {
        final Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);

        return startInvoices.stream()
                .filter(invoice -> customerVerifierService.isBalanceEnoughToCreateIssue(invoice.getBalanceOnDocument(), Currency.valueOf(invoice.getCurrency()),
                        invoice.getExchangeRate(), minBalanceStart))
                .filter(invoice -> !contractsOutOfService.contains(invoice.getContractNo()))
                .map(AidaInvoiceWithCorrectionsDto::getRealDueDate)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private boolean isBalanceEnoughToCreateIssue(final Invoice invoice, final BigDecimal minBalanceStart) {
        final BigDecimal balance = invoice.getCurrentBalance() == null ? ZERO : invoice.getCurrentBalance();
        final Currency currency = forStringValue(invoice.getCurrency(), invoice.getNumber());
        return customerVerifierService.isBalanceEnoughToCreateIssue(balance, currency, invoice.getExchangeRate(), minBalanceStart);
    }
}
