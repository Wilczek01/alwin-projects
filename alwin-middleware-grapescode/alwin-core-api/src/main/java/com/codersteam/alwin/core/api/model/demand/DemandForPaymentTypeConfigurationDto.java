package com.codersteam.alwin.core.api.model.demand;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.issue.SegmentDto;

import java.math.BigDecimal;

/**
 * Typ wezwania do zapłaty
 *
 * @author Michal Horowic
 */
public class DemandForPaymentTypeConfigurationDto {

    private Long id;
    private DemandForPaymentTypeKey key;
    private Integer dpdStart;
    private Integer numberOfDaysToPay;
    private BigDecimal charge;
    private SegmentDto segment;
    private BigDecimal minDuePaymentValue;
    private BigDecimal minDuePaymentPercent;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public DemandForPaymentTypeKey getKey() {
        return key;
    }

    public void setKey(final DemandForPaymentTypeKey key) {
        this.key = key;
    }

    public Integer getDpdStart() {
        return dpdStart;
    }

    public void setDpdStart(final Integer dpdStart) {
        this.dpdStart = dpdStart;
    }

    public Integer getNumberOfDaysToPay() {
        return numberOfDaysToPay;
    }

    public void setNumberOfDaysToPay(final Integer numberOfDaysToPay) {
        this.numberOfDaysToPay = numberOfDaysToPay;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(final BigDecimal charge) {
        this.charge = charge;
    }

    public SegmentDto getSegment() {
        return segment;
    }

    public void setSegment(final SegmentDto segment) {
        this.segment = segment;
    }

    public BigDecimal getMinDuePaymentValue() {
        return minDuePaymentValue;
    }

    public void setMinDuePaymentValue(final BigDecimal minDuePaymentValue) {
        this.minDuePaymentValue = minDuePaymentValue;
    }

    public BigDecimal getMinDuePaymentPercent() {
        return minDuePaymentPercent;
    }

    public void setMinDuePaymentPercent(final BigDecimal minDuePaymentPercent) {
        this.minDuePaymentPercent = minDuePaymentPercent;
    }
}
