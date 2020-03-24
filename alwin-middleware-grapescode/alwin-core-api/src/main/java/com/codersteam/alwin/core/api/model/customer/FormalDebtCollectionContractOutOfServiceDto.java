package com.codersteam.alwin.core.api.model.customer;

import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;

import java.util.Date;

/**
 * @author Piotr Naroznik
 */

public class FormalDebtCollectionContractOutOfServiceDto implements DateRange {

    private Long id;
    private String contractNo;
    private Long extCompanyId;
    private Date startDate;
    private Date endDate;
    private String blockingOperator;
    private String reason;
    private String remark;
    private ReasonType reasonType;
    private String reasonTypeLabel;
    private DemandForPaymentTypeKey demandForPaymentType;

    public FormalDebtCollectionContractOutOfServiceDto() {
    }

    public FormalDebtCollectionContractOutOfServiceDto(final String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public String getBlockingOperator() {
        return blockingOperator;
    }

    public void setBlockingOperator(final String blockingOperator) {
        this.blockingOperator = blockingOperator;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public ReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(final ReasonType reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasonTypeLabel() {
        return reasonTypeLabel;
    }

    public void setReasonTypeLabel(final String reasonTypeLabel) {
        this.reasonTypeLabel = reasonTypeLabel;
    }

    public DemandForPaymentTypeKey getDemandForPaymentType() {
        return demandForPaymentType;
    }

    public void setDemandForPaymentType(final DemandForPaymentTypeKey demandForPaymentType) {
        this.demandForPaymentType = demandForPaymentType;
    }
}
