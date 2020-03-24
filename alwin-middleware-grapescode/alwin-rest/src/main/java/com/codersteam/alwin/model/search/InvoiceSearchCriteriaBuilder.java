package com.codersteam.alwin.model.search;

import com.codersteam.alwin.common.search.criteria.InvoiceSearchCriteria;
import com.codersteam.alwin.parameters.DateParam;

import java.util.Date;

import static com.codersteam.alwin.util.DateParamUtils.getDateNullSafe;

/**
 * @author Adam Stepnowski
 */
public final class InvoiceSearchCriteriaBuilder {

    private InvoiceSearchCriteria searchCriteria;

    private InvoiceSearchCriteriaBuilder() {
        searchCriteria = new InvoiceSearchCriteria();
    }

    public InvoiceSearchCriteria build() {
        final InvoiceSearchCriteria builtCriteria = searchCriteria;
        searchCriteria = new InvoiceSearchCriteria();
        return builtCriteria;
    }

    public static InvoiceSearchCriteriaBuilder aSearchCriteria() {
        return new InvoiceSearchCriteriaBuilder();
    }

    public InvoiceSearchCriteriaBuilder withContractNumber(final String contractNumber) {
        this.searchCriteria.setContractNumber(contractNumber);
        return this;
    }

    public InvoiceSearchCriteriaBuilder withTypeId(final Long typeId) {
        searchCriteria.setTypeId(typeId);
        return this;
    }

    public InvoiceSearchCriteriaBuilder withStartDueDate(final DateParam startDueDate) {
        searchCriteria.setStartDueDate(getDateNullSafe(startDueDate));
        return this;
    }

    public InvoiceSearchCriteriaBuilder withEndDueDate(final DateParam endDueDate) {
        this.searchCriteria.setEndDueDate(getDateNullSafe(endDueDate));
        return this;
    }

    public InvoiceSearchCriteriaBuilder withMaxResults(final Integer maxResults) {
        this.searchCriteria.setMaxResults(maxResults);
        return this;
    }

    public InvoiceSearchCriteriaBuilder withFirstResult(final Integer firstResult) {
        this.searchCriteria.setFirstResult(firstResult);
        return this;
    }

    public InvoiceSearchCriteriaBuilder withOverdueOnly(final boolean overdueOnly, final Date currentDate) {
        this.searchCriteria.setOverdueOnly(overdueOnly);
        this.searchCriteria.setCurrentDate(currentDate);
        return this;
    }

    public InvoiceSearchCriteriaBuilder withNotPaidOnly(final boolean overdueOnly) {
        this.searchCriteria.setNotPaidOnly(overdueOnly);
        return this;
    }

    public InvoiceSearchCriteriaBuilder withIssueSubjectOnly(final boolean issueSubjectOnly) {
        this.searchCriteria.setIssueSubjectOnly(issueSubjectOnly);
        return this;
    }
}
