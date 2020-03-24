package com.codersteam.alwin.model.search;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.util.DateParamUtils;

import java.util.List;

/**
 * @author Adam Stepnowski
 */
public final class ActivitySearchCriteriaBuilder {

    private final ActivitiesSearchCriteria criteriaToBuild;

    private ActivitySearchCriteriaBuilder() {
        criteriaToBuild = new ActivitiesSearchCriteria();
    }

    public ActivitiesSearchCriteria build() {
        return criteriaToBuild;
    }

    public static ActivitySearchCriteriaBuilder aSearchCriteria() {
        return new ActivitySearchCriteriaBuilder();
    }

    public ActivitySearchCriteriaBuilder withOperatorId(final Long operatorId) {
        this.criteriaToBuild.setOperatorId(operatorId);
        return this;
    }

    public ActivitySearchCriteriaBuilder withCompanyId(final Long companyId) {
        this.criteriaToBuild.setCompanyId(companyId);
        return this;
    }

    public ActivitySearchCriteriaBuilder withType(final String type) {
        this.criteriaToBuild.setType(type);
        return this;
    }

    public ActivitySearchCriteriaBuilder withRemark(final String remark) {
        this.criteriaToBuild.setRemark(remark);
        return this;
    }

    public ActivitySearchCriteriaBuilder withStartCreationDate(final DateParam startCreationDate) {
        this.criteriaToBuild.setStartCreationDate(DateParamUtils.getDateNullSafe(startCreationDate));
        return this;
    }

    public ActivitySearchCriteriaBuilder withEndCreationDate(final DateParam endCreationDate) {
        this.criteriaToBuild.setEndCreationDate(DateParamUtils.getDateNullSafe(endCreationDate));
        return this;
    }

    public ActivitySearchCriteriaBuilder withMaxResults(final Integer maxResults) {
        this.criteriaToBuild.setMaxResults(maxResults);
        return this;
    }

    public ActivitySearchCriteriaBuilder withFirstResult(final Integer firstResult) {
        this.criteriaToBuild.setFirstResult(firstResult);
        return this;
    }

    public ActivitySearchCriteriaBuilder withStates(final List<String> states) {
        this.criteriaToBuild.setStates(states);
        return this;
    }

    public ActivitySearchCriteriaBuilder withIssueType(final IssueTypeName issueType) {
        this.criteriaToBuild.setIssueType(issueType);
        return this;
    }
}
