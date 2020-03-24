package com.codersteam.alwin.model.search;

import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.core.api.model.issue.IssuePriorityDto;
import com.codersteam.alwin.core.api.model.issue.SegmentDto;
import com.codersteam.alwin.parameters.DateParam;

import java.math.BigDecimal;
import java.util.List;

import static com.codersteam.alwin.util.DateParamUtils.getDateNullSafe;
import static java.util.Optional.ofNullable;

/**
 * @author Michal Horowic
 */
public final class IssuesSearchCriteriaBuilder {

    private final IssuesSearchCriteria criteria = new IssuesSearchCriteria();

    private IssuesSearchCriteriaBuilder() {
    }

    public static IssuesSearchCriteriaBuilder anIssuesSearchCriteria() {
        return new IssuesSearchCriteriaBuilder();
    }

    public IssuesSearchCriteriaBuilder withTotalCurrentBalancePLNMax(final BigDecimal totalCurrentBalancePLNMax) {
        this.criteria.setTotalCurrentBalancePLNMax(totalCurrentBalancePLNMax);
        return this;
    }

    public IssuesSearchCriteriaBuilder withTotalCurrentBalancePLNMin(final BigDecimal totalCurrentBalancePLNMin) {
        this.criteria.setTotalCurrentBalancePLNMin(totalCurrentBalancePLNMin);
        return this;
    }

    public IssuesSearchCriteriaBuilder withContactDate(final DateParam startContactDate, final DateParam endContactDate) {
        this.criteria.setStartContactDate(getDateNullSafe(startContactDate));
        this.criteria.setEndContactDate(getDateNullSafe(endContactDate));
        return this;
    }

    public IssuesSearchCriteriaBuilder withStartDate(final DateParam startStartDate, final DateParam endStartDate) {
        this.criteria.setStartStartDate(getDateNullSafe(startStartDate));
        this.criteria.setEndStartDate(getDateNullSafe(endStartDate));
        return this;
    }

    public IssuesSearchCriteriaBuilder withOperatorIds(final List<Long> operatorIds) {
        this.criteria.setOperatorIds(operatorIds);
        return this;
    }

    public IssuesSearchCriteriaBuilder withTagIds(final List<Long> tagIds) {
        this.criteria.setTagIds(tagIds);
        return this;
    }

    public IssuesSearchCriteriaBuilder withStates(final List<String> states) {
        this.criteria.setStates(states);
        return this;
    }

    public IssuesSearchCriteriaBuilder withUnassigned(final Boolean unassigned) {
        this.criteria.setUnassigned(unassigned);
        return this;
    }

    public IssuesSearchCriteriaBuilder withMaxResults(final Integer maxResults) {
        this.criteria.setMaxResults(maxResults);
        return this;
    }

    public IssuesSearchCriteriaBuilder withFirstResult(final Integer firstResult) {
        this.criteria.setFirstResult(firstResult);
        return this;
    }

    public IssuesSearchCriteriaBuilder withPriorityKey(final String priorityKey) {
        this.criteria.setPriority(ofNullable(priorityKey).map(IssuePriorityDto::priorityByKey).orElse(null));
        return this;
    }

    public IssuesSearchCriteriaBuilder withExtCompanyIds(final List<Long> extCompanyIds) {
        this.criteria.setExtCompanyIds(extCompanyIds);
        return this;
    }

    public IssuesSearchCriteriaBuilder withIssueTypeId(final Long issueTypeId) {
        this.criteria.setIssueTypeId(issueTypeId);
        return this;
    }

    public IssuesSearchCriteriaBuilder withSegment(final String segment) {
        this.criteria.setSegment(ofNullable(segment).map(SegmentDto::segmentByKey).orElse(null));
        return this;
    }

    public IssuesSearchCriteriaBuilder withNip(final String nip) {
        this.criteria.setNip(nip);
        return this;
    }

    public IssuesSearchCriteriaBuilder withRegon(final String regon) {
        this.criteria.setRegon(regon);
        return this;
    }

    public IssuesSearchCriteriaBuilder withComapnyName(final String comapnyName) {
        this.criteria.setCompanyName(comapnyName);
        return this;
    }

    public IssuesSearchCriteriaBuilder withInvoiceContractNo(final String invoiceContractNo) {
        this.criteria.setInvoiceContractNo(invoiceContractNo);
        return this;
    }

    public IssuesSearchCriteriaBuilder withPersonPesel(final String personPesel) {
        this.criteria.setPersonPesel(personPesel);
        return this;
    }

    public IssuesSearchCriteriaBuilder withPersonName(final String personName) {
        this.criteria.setPersonName(personName);
        return this;
    }

    public IssuesSearchCriteriaBuilder withContactPhone(final String withContactPhone) {
        this.criteria.setContactPhone(withContactPhone);
        return this;
    }

    public IssuesSearchCriteriaBuilder withContactEmail(final String withContactEmail) {
        this.criteria.setContactEmail(withContactEmail);
        return this;
    }

    public IssuesSearchCriteriaBuilder withContactName(final String withContactName) {
        this.criteria.setContactName(withContactName);
        return this;
    }

    public IssuesSearchCriteriaBuilder withAssignedTo(final List<Long> assignedTo) {
        this.criteria.setAssignedTo(assignedTo);
        return this;
    }

    public IssuesSearchCriteria build() {
        return this.criteria;
    }
}
