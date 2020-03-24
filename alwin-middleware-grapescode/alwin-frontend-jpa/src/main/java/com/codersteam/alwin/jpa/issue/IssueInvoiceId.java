package com.codersteam.alwin.jpa.issue;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Adam Stepnowski
 */
@Embeddable
public class IssueInvoiceId implements Serializable {

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Issue issue;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(final Issue issue) {
        this.issue = issue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IssueInvoiceId that = (IssueInvoiceId) o;
        return Objects.equals(invoice, that.invoice) &&
                Objects.equals(issue, that.issue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice, issue);
    }
}
