package com.codersteam.alwin.core.api.model.invoice;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.InvoiceDto;

import java.math.BigDecimal;

/**
 * @author Adam Stepnowski
 */
public class InvoicesWithTotalBalanceDto {
    private final Page<InvoiceDto> invoices;
    private final BigDecimal balance;

    public InvoicesWithTotalBalanceDto(final Page<InvoiceDto> invoicesForIssueId, final BigDecimal balance) {
        this.invoices = invoicesForIssueId;
        this.balance = balance;
    }

    public Page<InvoiceDto> getInvoices() {
        return invoices;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
