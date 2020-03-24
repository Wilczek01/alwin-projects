package com.codersteam.alwin.jpa.issue;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Powiązanie faktury ze zleceniem
 *
 * @author Adam Stepnowski
 */
@Entity
@Table(name = "issue_invoice")
@AssociationOverrides({
        @AssociationOverride(name = "pk.invoice",
                joinColumns = @JoinColumn(name = "invoice_id")),
        @AssociationOverride(name = "pk.issue",
                joinColumns = @JoinColumn(name = "issue_id"))})
public class IssueInvoice {

    @EmbeddedId
    private final IssueInvoiceId pk = new IssueInvoiceId();

    /**
     * Data dodania powiązania
     */
    @Column(name = "addition_date", nullable = false)
    private Date additionDate;

    /**
     * Saldo faktury na dzień utworzenia zlecenia
     */
    @Column(name = "inclusion_balance")
    private BigDecimal inclusionBalance;

    /**
     * Saldo faktury na dzień zakończenia zlecenia
     */
    @Column(name = "final_balance")
    private BigDecimal finalBalance;

    /**
     * Czy faktura stanowi przedmiot zlecenia
     */
    @Column(name = "issue_subject", nullable = false)
    private Boolean issueSubject;

    /**
     * Czy faktura jest wyłączona z obsługi
     */
    @Column(name = "excluded", nullable = false)
    private Boolean excluded;

    public Issue getIssue() {
        return getPk().getIssue();
    }

    public void setIssue(final Issue issue) {
        getPk().setIssue(issue);
    }

    public Invoice getInvoice() {
        return getPk().getInvoice();
    }

    public void setInvoice(final Invoice invoice) {
        getPk().setInvoice(invoice);
    }

    public IssueInvoiceId getPk() {
        return pk;
    }

    public Date getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(final Date additionDate) {
        this.additionDate = additionDate;
    }

    public BigDecimal getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(final BigDecimal finalBalance) {
        this.finalBalance = finalBalance;
    }

    public Boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(final Boolean excluded) {
        this.excluded = excluded;
    }

    public BigDecimal getInclusionBalance() {
        return inclusionBalance;
    }

    public void setInclusionBalance(final BigDecimal inclusionBalance) {
        this.inclusionBalance = inclusionBalance;
    }

    public Boolean getIssueSubject() {
        return issueSubject;
    }

    public void setIssueSubject(final Boolean issueSubject) {
        this.issueSubject = issueSubject;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IssueInvoice that = (IssueInvoice) o;
        return Objects.equals(pk, that.pk) &&
                Objects.equals(additionDate, that.additionDate) &&
                Objects.equals(inclusionBalance, that.inclusionBalance) &&
                Objects.equals(finalBalance, that.finalBalance) &&
                Objects.equals(issueSubject, that.issueSubject) &&
                Objects.equals(excluded, that.excluded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, additionDate, inclusionBalance, finalBalance, issueSubject, excluded);
    }
}
