package com.codersteam.alwin.common.search.criteria;

import java.util.Date;

/**
 * @author Adam Stepnowski
 */
public class InvoiceSearchCriteria extends SearchCriteria {

    private String contractNumber;
    private Long typeId;
    private Date startDueDate;
    private Date endDueDate;
    private boolean notPaidOnly;
    private boolean overdueOnly;
    private Date currentDate;
    private boolean issueSubjectOnly;

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(final Date currentDate) {
        this.currentDate = currentDate;
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

    public Date getStartDueDate() {
        return startDueDate;
    }

    public void setStartDueDate(final Date startDueDate) {
        this.startDueDate = startDueDate;
    }

    public Date getEndDueDate() {
        return endDueDate;
    }

    public void setEndDueDate(final Date endDueDate) {
        this.endDueDate = endDueDate;
    }

    public boolean isNotPaidOnly() {
        return notPaidOnly;
    }

    public void setNotPaidOnly(final boolean notPaidOnly) {
        this.notPaidOnly = notPaidOnly;
    }

    public boolean isOverdueOnly() {
        return overdueOnly;
    }

    public void setOverdueOnly(final boolean overdueOnly) {
        this.overdueOnly = overdueOnly;
    }

    public void setIssueSubjectOnly(final boolean issueSubjectOnly) {
        this.issueSubjectOnly = issueSubjectOnly;
    }

    public boolean getIssueSubjectOnly() {
        return issueSubjectOnly;
    }
}
