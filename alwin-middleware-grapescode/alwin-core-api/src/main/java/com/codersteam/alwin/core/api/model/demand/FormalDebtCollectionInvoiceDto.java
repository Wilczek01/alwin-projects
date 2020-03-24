package com.codersteam.alwin.core.api.model.demand;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Faktura w procesie windykacji formalnej
 *
 * @author Tomasz Sliwinski
 */
public class FormalDebtCollectionInvoiceDto {

    private Long id;
    private String invoiceNumber;
    private String contractNumber;
    private Date issueDate;
    private Date realDueDate;
    private String currency;
    private BigDecimal netAmount;
    private BigDecimal grossAmount;
    private BigDecimal currentBalance;
    private Long leoId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(final String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return realDueDate;
    }

    public void setDueDate(final Date dueDate) {
        this.realDueDate = dueDate;
    }

    public Date getRealDueDate() {
        return realDueDate;
    }

    public void setRealDueDate(final Date realDueDate) {
        this.realDueDate = realDueDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(final BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(final BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(final BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Long getLeoId() {
        return leoId;
    }

    public void setLeoId(final Long leoId) {
        this.leoId = leoId;
    }
}
