package com.codersteam.alwin.model.search;

import com.codersteam.alwin.common.search.criteria.IssueSearchCriteria;
import com.codersteam.alwin.parameters.DateParam;

import java.math.BigDecimal;

import static com.codersteam.alwin.util.DateParamUtils.getDateNullSafe;

/**
 * @author Adam Stepnowski
 */
public final class IssueSearchCriteriaBuilder {

    private IssueSearchCriteria criteriaToBuild;

    private IssueSearchCriteriaBuilder() {
        criteriaToBuild = new IssueSearchCriteria();
    }

    public static IssueSearchCriteriaBuilder aSearchCriteria() {
        return new IssueSearchCriteriaBuilder();
    }

    public IssueSearchCriteria build() {
        final IssueSearchCriteria builtCriteria = criteriaToBuild;
        criteriaToBuild = new IssueSearchCriteria();
        return builtCriteria;
    }

    public IssueSearchCriteriaBuilder withStartStartDate(final DateParam startStartDate) {
        this.criteriaToBuild.setStartStartDate(getDateNullSafe(startStartDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withEndStartDate(final DateParam endStartDate) {
        this.criteriaToBuild.setEndStartDate(getDateNullSafe(endStartDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withStartExpirationDate(final DateParam startExpirationDate) {
        this.criteriaToBuild.setStartExpirationDate(getDateNullSafe(startExpirationDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withEndExpirationDate(final DateParam endExpirationDate) {
        this.criteriaToBuild.setEndExpirationDate(getDateNullSafe(endExpirationDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withStartTotalCurrentBalancePLN(final String startTotalCurrentBalancePLN) {
        if (startTotalCurrentBalancePLN != null) {
            this.criteriaToBuild.setStartTotalCurrentBalancePLN(new BigDecimal(startTotalCurrentBalancePLN));
        }
        return this;
    }

    public IssueSearchCriteriaBuilder withEndTotalCurrentBalancePLN(final String endTotalCurrentBalancePLN) {
        if (endTotalCurrentBalancePLN != null) {
            this.criteriaToBuild.setEndTotalCurrentBalancePLN(new BigDecimal(endTotalCurrentBalancePLN));
        }
        return this;
    }

    public IssueSearchCriteriaBuilder withStartActivityDate(final DateParam startActivityDate) {
        this.criteriaToBuild.setStartActivityDate(getDateNullSafe(startActivityDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withEndActivityDate(final DateParam endActivityDate) {
        this.criteriaToBuild.setEndActivityDate(getDateNullSafe(endActivityDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withStartPlannedDate(final DateParam startPlannedDate) {
        this.criteriaToBuild.setStartPlannedDate(getDateNullSafe(startPlannedDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withEndPlannedDate(final DateParam endPlannedDate) {
        this.criteriaToBuild.setEndPlannedDate(getDateNullSafe(endPlannedDate));
        return this;
    }

    public IssueSearchCriteriaBuilder withMaxResults(final Integer maxResults) {
        this.criteriaToBuild.setMaxResults(maxResults);
        return this;
    }

    public IssueSearchCriteriaBuilder withFirstResult(final Integer firstResult) {
        this.criteriaToBuild.setFirstResult(firstResult);
        return this;
    }
}
