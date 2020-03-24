package com.codersteam.alwin.core.api.model.issue;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Tomasz Sliwinski
 */
public class MissingIssueDto implements Serializable {

    /**
     * identyfikator klienta w AIDA
     */
    private final Long extCustomerId;

    /**
     * Dokument dla którego zadłużenie przekracza próg
     */
    private final String invoiceNo;

    /**
     * Saldo na zadłużonym dokumencie
     */
    private final BigDecimal balance;

    public MissingIssueDto(final Long extCustomerId, final String invoiceNo, final BigDecimal balance) {
        this.extCustomerId = extCustomerId;
        this.invoiceNo = invoiceNo;
        this.balance = balance;
    }

    public Long getExtCustomerId() {
        return extCustomerId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
