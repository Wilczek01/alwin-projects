package com.codersteam.alwin.model.search;

import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.parameters.DateParam;

import java.math.BigDecimal;

/**
 * @author Michal Horowic
 */
public final class ContractTerminationSearchCriteriaBuilder {

    private final ContractTerminationSearchCriteria criteria = new ContractTerminationSearchCriteria();

    private ContractTerminationSearchCriteriaBuilder() {
    }

    public static ContractTerminationSearchCriteriaBuilder aContractTerminationSearchCriteria() {
        return new ContractTerminationSearchCriteriaBuilder();
    }

    public ContractTerminationSearchCriteriaBuilder withState(final ContractTerminationState state) {
        if (state != null) {
            this.criteria.setState(state);
        }
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withContractNo(final String contractNo) {
        this.criteria.setContractNo(contractNo);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withType(final ContractTerminationType type) {
        this.criteria.setType(type);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withMaxResults(final Integer maxResults) {
        this.criteria.setMaxResults(maxResults);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withFirstResult(final Integer firstResult) {
        this.criteria.setFirstResult(firstResult);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withCompanyName(final String companyName) {
        this.criteria.setCompanyName(companyName);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withInitialInvoiceNo(final String initialInvoiceNo) {
        this.criteria.setInitialInvoiceNo(initialInvoiceNo);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withExtCompanyId(final Long extCompanyId) {
        this.criteria.setExtCompanyId(extCompanyId);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withStartTerminationDate(final DateParam startTerminationDate) {
        this.criteria.setStartTerminationDate(startTerminationDate != null ? startTerminationDate.getDate() : null);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withEndTerminationDate(final DateParam endTerminationDate) {
        this.criteria.setEndTerminationDate(endTerminationDate != null ? endTerminationDate.getDate() : null);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withRemark(final String remark) {
        this.criteria.setRemark(remark);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withGeneratedBy(final String generatedBy) {
        this.criteria.setGeneratedBy(generatedBy);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withResumptionCostMin(final BigDecimal resumptionCostMin) {
        this.criteria.setResumptionCostMin(resumptionCostMin);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withResumptionCostMax(final BigDecimal resumptionCostMax) {
        this.criteria.setResumptionCostMax(resumptionCostMax);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withStartDueDateInDemandForPayment(final DateParam startDueDateInDemandForPayment) {
        this.criteria.setStartDueDateInDemandForPayment(startDueDateInDemandForPayment != null ? startDueDateInDemandForPayment.getDate() : null);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withEndDueDateInDemandForPayment(final DateParam endDueDateInDemandForPayment) {
        this.criteria.setEndDueDateInDemandForPayment(endDueDateInDemandForPayment != null ? endDueDateInDemandForPayment.getDate() : null);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withAmountInDemandForPaymentPLNMin(final BigDecimal amountInDemandForPaymentPLNMin) {
        this.criteria.setAmountInDemandForPaymentPLNMax(amountInDemandForPaymentPLNMin);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withAmountInDemandForPaymentPLNMax(final BigDecimal amountInDemandForPaymentPLNMax) {
        this.criteria.setAmountInDemandForPaymentPLNMax(amountInDemandForPaymentPLNMax);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withAmountInDemandForPaymentEURMin(final BigDecimal amountInDemandForPaymentEURMin) {
        this.criteria.setAmountInDemandForPaymentEURMax(amountInDemandForPaymentEURMin);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withAmountInDemandForPaymentEURMax(final BigDecimal amountInDemandForPaymentEURMax) {
        this.criteria.setAmountInDemandForPaymentEURMax(amountInDemandForPaymentEURMax);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withTotalPaymentsSinceDemandForPaymentPLNMin(final BigDecimal totalPaymentsSinceDemandForPaymentPLNMin) {
        this.criteria.setTotalPaymentsSinceDemandForPaymentPLNMin(totalPaymentsSinceDemandForPaymentPLNMin);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withTotalPaymentsSinceDemandForPaymentPLNMax(final BigDecimal totalPaymentsSinceDemandForPaymentPLNMax) {
        this.criteria.setTotalPaymentsSinceDemandForPaymentPLNMax(totalPaymentsSinceDemandForPaymentPLNMax);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withTotalPaymentsSinceDemandForPaymentEURMin(final BigDecimal totalPaymentsSinceDemandForPaymentEURMin) {
        this.criteria.setTotalPaymentsSinceDemandForPaymentEURMin(totalPaymentsSinceDemandForPaymentEURMin);
        return this;
    }

    public ContractTerminationSearchCriteriaBuilder withTotalPaymentsSinceDemandForPaymentEURMax(final BigDecimal totalPaymentsSinceDemandForPaymentEURMax) {
        this.criteria.setTotalPaymentsSinceDemandForPaymentEURMax(totalPaymentsSinceDemandForPaymentEURMax);
        return this;
    }

    public ContractTerminationSearchCriteria build() {
        return this.criteria;
    }
}
