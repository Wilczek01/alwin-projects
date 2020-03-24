package com.codersteam.alwin.core.api.model.termination;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Parametry aktywacji umowy dla wypowiedzenia
 *
 * @author Dariusz Rackowski
 */
public class ActivateContractTerminationDto {
    private Date activationDate;
    private BigDecimal resumptionCost;
    private String remark;

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(final Date activationDate) {
        this.activationDate = activationDate;
    }

    public BigDecimal getResumptionCost() {
        return resumptionCost;
    }

    public void setResumptionCost(final BigDecimal resumptionCost) {
        this.resumptionCost = resumptionCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }
}
