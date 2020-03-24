package com.codersteam.alwin.core.api.model.issue;

/**
 * @author Adam Stepnowski
 */
public class ExclusionRequestDto {

    private Long issueId;
    private Long invoiceId;
    private Boolean excluded;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(final Boolean excluded) {
        this.excluded = excluded;
    }
}
