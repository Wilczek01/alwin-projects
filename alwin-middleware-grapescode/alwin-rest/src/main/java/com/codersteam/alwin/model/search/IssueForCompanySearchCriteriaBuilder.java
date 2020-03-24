package com.codersteam.alwin.model.search;

import com.codersteam.alwin.common.search.criteria.IssueForCompanySearchCriteria;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.util.DateParamUtils;

/**
 * @author Adam Stepnowski
 */
public final class IssueForCompanySearchCriteriaBuilder {

    private IssueForCompanySearchCriteria criteriaToBuild;

    private IssueForCompanySearchCriteriaBuilder() {
        criteriaToBuild = new IssueForCompanySearchCriteria();
    }

    public static IssueForCompanySearchCriteriaBuilder aSearchCriteria() {
        return new IssueForCompanySearchCriteriaBuilder();
    }

    public IssueForCompanySearchCriteria build() {
        final IssueForCompanySearchCriteria builtCriteria = criteriaToBuild;
        criteriaToBuild = new IssueForCompanySearchCriteria();
        return builtCriteria;
    }

    public IssueForCompanySearchCriteriaBuilder withStartDate(final DateParam startDate) {
        this.criteriaToBuild.setActivityDateFrom(DateParamUtils.getDateNullSafe(startDate));
        return this;
    }

    public IssueForCompanySearchCriteriaBuilder withEndDate(final DateParam endDate) {
        this.criteriaToBuild.setActivityDateTo(DateParamUtils.getDateNullSafe(endDate));
        return this;
    }

    public IssueForCompanySearchCriteriaBuilder withIssueType(final Long issueTypeId) {
        this.criteriaToBuild.setIssueTypeId(issueTypeId);
        return this;
    }

    public IssueForCompanySearchCriteriaBuilder withActivityType(final Long activityTypeId) {
        this.criteriaToBuild.setActivityTypeId(activityTypeId);
        return this;
    }

    public IssueForCompanySearchCriteriaBuilder withMaxResults(final Integer maxResults) {
        this.criteriaToBuild.setMaxResults(maxResults);
        return this;
    }

    public IssueForCompanySearchCriteriaBuilder withFirstResult(final Integer firstResult) {
        this.criteriaToBuild.setFirstResult(firstResult);
        return this;
    }
}
