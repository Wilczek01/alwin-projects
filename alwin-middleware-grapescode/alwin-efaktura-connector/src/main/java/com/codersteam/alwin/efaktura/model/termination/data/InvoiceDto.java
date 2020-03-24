package com.codersteam.alwin.efaktura.model.termination.data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Dariusz Rackowski
 */
public class InvoiceDto {

    /**
     * Nr umowy
     */
    private String contractNo;

    /**
     * Waluta
     */
    private String currency;

    /**
     * Termin płatności
     */
    private Date dueDate;

    /**
     * Saldo
     */
    private BigDecimal invoiceBalance;

    /**
     * Numer faktury
     */
    private String invoiceNo;

    /**
     * Kwota faktury
     */
    private BigDecimal invoiceSum;

    /**
     * Data wystawienia faktury
     */
    private Date issueDate;

    /**
     * ID faktury z LEO
     */
    private Long leoId;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(final Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getInvoiceBalance() {
        return invoiceBalance;
    }

    public void setInvoiceBalance(final BigDecimal invoiceBalance) {
        this.invoiceBalance = invoiceBalance;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(final String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(final BigDecimal invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    public Long getLeoId() {
        return leoId;
    }

    public void setLeoId(final Long leoId) {
        this.leoId = leoId;
    }
}
