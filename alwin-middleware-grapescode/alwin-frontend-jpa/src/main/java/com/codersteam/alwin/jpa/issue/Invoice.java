package com.codersteam.alwin.jpa.issue;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
@Audited
@Entity
@Table(name = "invoice")
public class Invoice {

    @SequenceGenerator(name = "invoiceSEQ", sequenceName = "invoice_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoiceSEQ")
    private Long id;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.invoice", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<IssueInvoice> issueInvoices;

    @Column(name = "last_payment_date")
    private Date lastPaymentDate;

    @Column(name = "net_amount", nullable = false)
    private BigDecimal netAmount;

    @Column(name = "gross_amount", nullable = false)
    private BigDecimal grossAmount;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "currency")
    private String currency;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Column
    private BigDecimal paid;

    @Column(name = "contract_number", nullable = false)
    private String contractNumber;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "due_date", nullable = false)
    private Date realDueDate;

    @Column(name = "correction", nullable = false)
    private boolean correction;

    @NotAudited
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    @OrderBy("correctionOrder ASC")
    private List<Invoice> corrections;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "exchange_rate", precision = 10, scale = 4)
    private BigDecimal exchangeRate;

    /**
     * Kolejność wystawianych korekt dla jednej faktury
     */
    @Column(name = "correction_order")
    private Integer correctionOrder;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<IssueInvoice> getIssueInvoices() {
        return issueInvoices;
    }

    public void setIssueInvoices(final List<IssueInvoice> issueInvoices) {
        this.issueInvoices = issueInvoices;
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

    public Date getRealDueDate() {
        return realDueDate;
    }

    public void setRealDueDate(Date realDueDate) {
        this.realDueDate = realDueDate;
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

    public List<Invoice> getCorrections() {
        return corrections;
    }

    public void setCorrections(final List<Invoice> corrections) {
        this.corrections = corrections;
    }

    public Integer getCorrectionOrder() {
        return correctionOrder;
    }

    public void setCorrectionOrder(final Integer correctionOrder) {
        this.correctionOrder = correctionOrder;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(final BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", issueInvoices=" + issueInvoices +
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
                ", correction=" + correction +
                ", corrections=" + corrections +
                ", issueDate=" + issueDate +
                ", exchangeRate=" + exchangeRate +
                ", correctionOrder=" + correctionOrder +
                '}';
    }
}


