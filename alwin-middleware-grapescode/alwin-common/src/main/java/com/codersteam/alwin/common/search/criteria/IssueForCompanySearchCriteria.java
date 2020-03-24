package com.codersteam.alwin.common.search.criteria;

import java.util.Date;


/**
 * @author Adam Stepnowski
 */
public class IssueForCompanySearchCriteria extends SearchCriteria {

    private Date activityDateFrom;
    private Date activityDateTo;
    private Long issueTypeId;
    private Long activityTypeId;

    public Date getActivityDateFrom() {
        return activityDateFrom;
    }

    public void setActivityDateFrom(final Date activityDateFrom) {
        this.activityDateFrom = activityDateFrom;
    }

    public Date getActivityDateTo() {
        return activityDateTo;
    }

    public void setActivityDateTo(final Date activityDateTo) {
        this.activityDateTo = activityDateTo;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(final Long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public Long getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(final Long activityTypeId) {
        this.activityTypeId = activityTypeId;
    }
}

