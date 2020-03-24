package com.codersteam.alwin.core.api.model.issue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Horowic
 */
public class InvoiceDto {

    private Long id;
    private Date lastPaymentDate;
    private BigDecimal netAmount;
    private BigDecimal grossAmount;
    private String number;
    private String currency;
    private BigDecimal currentBalance;
    private BigDecimal paid;
    private String contractNumber;
    private Long typeId;
    private Date realDueDate;
    private String typeLabel;
    private Boolean excluded;
    private Long dpd;
    private boolean correction;
    private List<InvoiceCorrectionDto> corrections;
    private Integer correctionOrder;
    private Boolean issueSubject;
    private Date issueDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(final Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(final BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(final String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(final Long typeId) {
        this.typeId = typeId;
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

    public void setRealDueDate(Date realDueDate) {
        this.realDueDate = realDueDate;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(final String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public Boolean getExcluded() {
        return excluded;
    }

    public void setExcluded(final Boolean excluded) {
        this.excluded = excluded;
    }

    public Long getDpd() {
        return dpd;
    }

    public void setDpd(final Long dpd) {
        this.dpd = dpd;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(final BigDecimal paid) {
        this.paid = paid;
    }

    public boolean isCorrection() {
        return correction;
    }

    public void setCorrection(final boolean correction) {
        this.correction = correction;
    }

    public List<InvoiceCorrectionDto> getCorrections() {
        return corrections;
    }

    public void setCorrections(final List<InvoiceCorrectionDto> corrections) {
        this.corrections = corrections;
    }

    public Integer getCorrectionOrder() {
        return correctionOrder;
    }

    public void setCorrectionOrder(final Integer correctionOrder) {
        this.correctionOrder = correctionOrder;
    }

    public Boolean getIssueSubject() {
        return issueSubject;
    }

    public void setIssueSubject(final Boolean issueSubject) {
        this.issueSubject = issueSubject;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "InvoiceDto{" +
                "id=" + id +
                ", lastPaymentDate=" + lastPaymentDate +
                ", netAmount=" + netAmount +
                ", grossAmount=" + grossAmount +
                ", number='" + number + '\'' +
                ", currency='" + currency + '\'' +
                ", currentBalance=" + currentBalance +
                ", paid=" + paid +
                ", contractNumber='" + contractNumber + '\'' +
                ", typeId=" + typeId +
                ", realDueDate=" + realDueDate +
                ", typeLabel='" + typeLabel + '\'' +
                ", excluded=" + excluded +
                ", dpd=" + dpd +
                ", correction=" + correction +
                ", correctionOrder=" + correctionOrder +
                ", corrections=" + corrections +
                ", issueSubject=" + issueSubject +
                ", issueDate=" + issueDate +
                '}';
    }
}
