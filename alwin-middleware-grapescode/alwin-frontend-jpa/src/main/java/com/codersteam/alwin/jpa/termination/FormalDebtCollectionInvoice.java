package com.codersteam.alwin.jpa.termination;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Faktury w procesie windykacji formalnej
 *
 * @author Dariusz Rackowski
 */
@Audited
@Entity
@Table(name = "formal_debt_collection_invoice")
public class FormalDebtCollectionInvoice {

    public FormalDebtCollectionInvoice() {
    }

    public FormalDebtCollectionInvoice(final BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    /**
     * Identyfikator faktury w procesie windykacji formalnej
     */
    @SequenceGenerator(name = "formalDebtCollectionInvoiceSEQ", sequenceName = "formal_debt_collection_invoice_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formalDebtCollectionInvoiceSEQ")
    private Long id;

    /**
     * Nr faktury
     */
    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    /**
     * Nr umowy
     */
    @Column(name = "contract_number", nullable = false)
    private String contractNumber;

    /**
     * Data wystawienia
     */
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    /**
     * Termin płatności
     */
    @Column(name = "due_date", nullable = false)
    private Date realDueDate;

    /**
     * Waluta
     */
    @Column(name = "currency", nullable = false)
    private String currency;

    /**
     * Kwota netto dokumentu
     */
    @Column(name = "net_amount", nullable = false)
    private BigDecimal netAmount;

    /**
     * Kwota brutto dokumentu
     */
    @Column(name = "gross_amount", nullable = false)
    private BigDecimal grossAmount;

    /**
     * Saldo dokumentu
     */
    @Column(name = "current_balance", nullable = false)
    private BigDecimal currentBalance;

    /**
     * Identyfikator faktury z LEO
     */
    @Column(name = "leo_id", nullable = false)
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

    @Override
    public String toString() {
        return "FormalDebtCollectionInvoice{" +
                "id=" + id +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", contractNumber='" + contractNumber + '\'' +
                ", issueDate=" + issueDate +
                ", realDueDate=" + realDueDate +
                ", currency='" + currency + '\'' +
                ", netAmount=" + netAmount +
                ", grossAmount=" + grossAmount +
                ", currentBalance=" + currentBalance +
                ", leoId=" + leoId +
                '}';
    }
}
