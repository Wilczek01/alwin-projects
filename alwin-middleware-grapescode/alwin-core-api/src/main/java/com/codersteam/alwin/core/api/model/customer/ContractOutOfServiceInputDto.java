package com.codersteam.alwin.core.api.model.customer;

import java.util.Date;

/**
 * @author Michal Horowic
 */

public class ContractOutOfServiceInputDto {

    private Date startDate;
    private Date endDate;
    private String reason;
    private String remark;

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
}
