package com.codersteam.alwin.common.search.criteria;

import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Kryteria wyszukiwania wypowiedzeń
 *
 * @author Michal Horowic
 */
public class ContractTerminationSearchCriteria extends SearchCriteria {

    private String companyName;
    private Long extCompanyId;
    private Date startTerminationDate;
    private Date endTerminationDate;
    private ContractTerminationType type;
    private String contractNo;
    private ContractTerminationState state;
    private String remark;
    private String generatedBy;
    private BigDecimal resumptionCostMin;
    private BigDecimal resumptionCostMax;
    private Date startDueDateInDemandForPayment;
    private Date endDueDateInDemandForPayment;
    private BigDecimal amountInDemandForPaymentPLNMin;
    private BigDecimal amountInDemandForPaymentPLNMax;
    private BigDecimal amountInDemandForPaymentEURMin;
    private BigDecimal amountInDemandForPaymentEURMax;
    private BigDecimal totalPaymentsSinceDemandForPaymentPLNMin;
    private BigDecimal totalPaymentsSinceDemandForPaymentPLNMax;
    private BigDecimal totalPaymentsSinceDemandForPaymentEURMin;
    private BigDecimal totalPaymentsSinceDemandForPaymentEURMax;
    private String initialInvoiceNo;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public Date getStartTerminationDate() {
        return startTerminationDate;
    }

    public void setStartTerminationDate(final Date startTerminationDate) {
        this.startTerminationDate = startTerminationDate;
    }

    public Date getEndTerminationDate() {
        return endTerminationDate;
    }

    public void setEndTerminationDate(final Date endTerminationDate) {
        this.endTerminationDate = endTerminationDate;
    }

    public ContractTerminationType getType() {
        return type;
    }

    public void setType(final ContractTerminationType type) {
        this.type = type;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public ContractTerminationState getState() {
        return state;
    }

    public void setState(final ContractTerminationState state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(final String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public BigDecimal getResumptionCostMin() {
        return resumptionCostMin;
    }

    public void setResumptionCostMin(final BigDecimal resumptionCostMin) {
        this.resumptionCostMin = resumptionCostMin;
    }

    public BigDecimal getResumptionCostMax() {
        return resumptionCostMax;
    }

    public void setResumptionCostMax(final BigDecimal resumptionCostMax) {
        this.resumptionCostMax = resumptionCostMax;
    }

    public Date getStartDueDateInDemandForPayment() {
        return startDueDateInDemandForPayment;
    }

    public void setStartDueDateInDemandForPayment(final Date startDueDateInDemandForPayment) {
        this.startDueDateInDemandForPayment = startDueDateInDemandForPayment;
    }

    public Date getEndDueDateInDemandForPayment() {
        return endDueDateInDemandForPayment;
    }

    public void setEndDueDateInDemandForPayment(final Date endDueDateInDemandForPayment) {
        this.endDueDateInDemandForPayment = endDueDateInDemandForPayment;
    }

    public BigDecimal getAmountInDemandForPaymentPLNMin() {
        return amountInDemandForPaymentPLNMin;
    }

    public void setAmountInDemandForPaymentPLNMin(final BigDecimal amountInDemandForPaymentPLNMin) {
        this.amountInDemandForPaymentPLNMin = amountInDemandForPaymentPLNMin;
    }

    public BigDecimal getAmountInDemandForPaymentPLNMax() {
        return amountInDemandForPaymentPLNMax;
    }

    public void setAmountInDemandForPaymentPLNMax(final BigDecimal amountInDemandForPaymentPLNMax) {
        this.amountInDemandForPaymentPLNMax = amountInDemandForPaymentPLNMax;
    }

    public BigDecimal getAmountInDemandForPaymentEURMin() {
        return amountInDemandForPaymentEURMin;
    }

    public void setAmountInDemandForPaymentEURMin(final BigDecimal amountInDemandForPaymentEURMin) {
        this.amountInDemandForPaymentEURMin = amountInDemandForPaymentEURMin;
    }

    public BigDecimal getAmountInDemandForPaymentEURMax() {
        return amountInDemandForPaymentEURMax;
    }

    public void setAmountInDemandForPaymentEURMax(final BigDecimal amountInDemandForPaymentEURMax) {
        this.amountInDemandForPaymentEURMax = amountInDemandForPaymentEURMax;
    }

    public BigDecimal getTotalPaymentsSinceDemandForPaymentPLNMin() {
        return totalPaymentsSinceDemandForPaymentPLNMin;
    }

    public void setTotalPaymentsSinceDemandForPaymentPLNMin(final BigDecimal totalPaymentsSinceDemandForPaymentPLNMin) {
        this.totalPaymentsSinceDemandForPaymentPLNMin = totalPaymentsSinceDemandForPaymentPLNMin;
    }

    public BigDecimal getTotalPaymentsSinceDemandForPaymentPLNMax() {
        return totalPaymentsSinceDemandForPaymentPLNMax;
    }

    public void setTotalPaymentsSinceDemandForPaymentPLNMax(final BigDecimal totalPaymentsSinceDemandForPaymentPLNMax) {
        this.totalPaymentsSinceDemandForPaymentPLNMax = totalPaymentsSinceDemandForPaymentPLNMax;
    }

    public BigDecimal getTotalPaymentsSinceDemandForPaymentEURMin() {
        return totalPaymentsSinceDemandForPaymentEURMin;
    }

    public void setTotalPaymentsSinceDemandForPaymentEURMin(final BigDecimal totalPaymentsSinceDemandForPaymentEURMin) {
        this.totalPaymentsSinceDemandForPaymentEURMin = totalPaymentsSinceDemandForPaymentEURMin;
    }

    public BigDecimal getTotalPaymentsSinceDemandForPaymentEURMax() {
        return totalPaymentsSinceDemandForPaymentEURMax;
    }

    public void setTotalPaymentsSinceDemandForPaymentEURMax(final BigDecimal totalPaymentsSinceDemandForPaymentEURMax) {
        this.totalPaymentsSinceDemandForPaymentEURMax = totalPaymentsSinceDemandForPaymentEURMax;
    }

    public String getInitialInvoiceNo() {
        return initialInvoiceNo;
    }

    public void setInitialInvoiceNo(final String initialInvoiceNo) {
        this.initialInvoiceNo = initialInvoiceNo;
    }
}
