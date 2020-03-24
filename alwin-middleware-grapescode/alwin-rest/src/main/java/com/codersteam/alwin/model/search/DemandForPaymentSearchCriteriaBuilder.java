package com.codersteam.alwin.model.search;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;
import com.codersteam.alwin.parameters.DateParam;

import java.util.List;

/**
 * @author Michal Horowic
 */
public final class DemandForPaymentSearchCriteriaBuilder {

    private final DemandForPaymentSearchCriteria criteria = new DemandForPaymentSearchCriteria();

    private DemandForPaymentSearchCriteriaBuilder() {
    }

    public static DemandForPaymentSearchCriteriaBuilder aDemandForPaymentSearchCriteria() {
        return new DemandForPaymentSearchCriteriaBuilder();
    }

    public DemandForPaymentSearchCriteriaBuilder withState(final List<DemandForPaymentState> state) {
        if (state != null) {
            this.criteria.setState(state);
        }
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withContractNo(final String contractNo) {
        this.criteria.setContractNo(contractNo);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withTypes(final List<DemandForPaymentTypeKey> types) {
        this.criteria.setTypes(types);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withMaxResults(final Integer maxResults) {
        this.criteria.setMaxResults(maxResults);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withFirstResult(final Integer firstResult) {
        this.criteria.setFirstResult(firstResult);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withChargeInvoiceNo(final String chargeInvoiceNo) {
        this.criteria.setChargeInvoiceNo(chargeInvoiceNo);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withCompanyName(final String companyName) {
        this.criteria.setCompanyName(companyName);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withInitialInvoiceNo(final String initialInvoiceNo) {
        this.criteria.setInitialInvoiceNo(initialInvoiceNo);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withStartIssueDate(final DateParam startIssueDate) {
        this.criteria.setStartIssueDate(startIssueDate != null ? startIssueDate.getDate() : null);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withEndIssueDate(final DateParam endIssueDate) {
        this.criteria.setEndIssueDate(endIssueDate != null ? endIssueDate.getDate() : null);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withStartDueDate(final DateParam startDueDate) {
        this.criteria.setStartDueDate(startDueDate != null ? startDueDate.getDate() : null);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withEndDueDate(final DateParam endDueDate) {
        this.criteria.setEndDueDate(endDueDate != null ? endDueDate.getDate() : null);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withSegment(final Segment segment) {
        this.criteria.setSegment(segment);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withExtCompanyId(final Long extCompanyId) {
        this.criteria.setExtCompanyId(extCompanyId);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withCreatedManually(final Boolean createdManually) {
        this.criteria.setCreatedManually(createdManually);
        return this;
    }

    public DemandForPaymentSearchCriteriaBuilder withAborted(final Boolean aborted) {
        this.criteria.setAborted(aborted);
        return this;
    }

    public DemandForPaymentSearchCriteria build() {
        return this.criteria;
    }
}
