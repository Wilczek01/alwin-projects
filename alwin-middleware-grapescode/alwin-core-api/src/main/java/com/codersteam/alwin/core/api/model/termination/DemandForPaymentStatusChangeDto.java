package com.codersteam.alwin.core.api.model.termination;

import com.codersteam.alwin.common.ReasonType;

import java.util.Date;

/**
 * Parametry zmiany stanu wezwania
 *
 * @author Adam Stepnowski
 */
public class DemandForPaymentStatusChangeDto {

    private Date stateChangeDate;
    private Long stateChangeOperatorId;
    private String stateChangeReason;
    private ReasonType reasonType;

    public Date getStateChangeDate() {
        return stateChangeDate;
    }

    public void setStateChangeDate(final Date stateChangeDate) {
        this.stateChangeDate = stateChangeDate;
    }

    public Long getStateChangeOperatorId() {
        return stateChangeOperatorId;
    }

    public void setStateChangeOperatorId(final Long stateChangeOperatorId) {
        this.stateChangeOperatorId = stateChangeOperatorId;
    }

    public String getStateChangeReason() {
        return stateChangeReason;
    }

    public void setStateChangeReason(final String stateChangeReason) {
        this.stateChangeReason = stateChangeReason;
    }

    public ReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(final ReasonType reasonType) {
        this.reasonType = reasonType;
    }
}
