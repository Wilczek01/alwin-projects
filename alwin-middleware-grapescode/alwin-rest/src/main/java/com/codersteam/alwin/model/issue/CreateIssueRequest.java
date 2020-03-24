package com.codersteam.alwin.model.issue;

import java.util.Date;

/**
 * @author Tomasz Sliwinski
 */
public class CreateIssueRequest {

    private Long extCompanyId;
    private Long issueTypeId;
    private Date expirationDate;
    private Long assigneeId;

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(final Long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(final Long assigneeId) {
        this.assigneeId = assigneeId;
    }

}
