package com.codersteam.alwin.core.api.model.activity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michal Horowic
 */
public class ActivityTypeDto {

    private Long id;
    private String key;
    private String name;
    private Boolean canBePlanned;
    private Boolean mayBeAutomated;
    private Boolean specific;
    private Boolean mayHaveDeclaration;
    private BigDecimal chargeMin;
    private BigDecimal chargeMax;
    private Boolean customerContact;
    private Set<ActivityTypeDetailPropertyDto> activityTypeDetailProperties;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getCanBePlanned() {
        return canBePlanned;
    }

    public void setCanBePlanned(final Boolean canBePlanned) {
        this.canBePlanned = canBePlanned;
    }

    public Boolean getMayBeAutomated() {
        return mayBeAutomated;
    }

    public void setMayBeAutomated(final Boolean mayBeAutomated) {
        this.mayBeAutomated = mayBeAutomated;
    }

    public Boolean getSpecific() {
        return specific;
    }

    public void setSpecific(final Boolean specific) {
        this.specific = specific;
    }

    public Boolean getMayHaveDeclaration() {
        return mayHaveDeclaration;
    }

    public void setMayHaveDeclaration(final Boolean mayHaveDeclaration) {
        this.mayHaveDeclaration = mayHaveDeclaration;
    }

    public BigDecimal getChargeMin() {
        return chargeMin;
    }

    public void setChargeMin(final BigDecimal chargeMin) {
        this.chargeMin = chargeMin;
    }

    public BigDecimal getChargeMax() {
        return chargeMax;
    }

    public void setChargeMax(final BigDecimal chargeMax) {
        this.chargeMax = chargeMax;
    }

    public Boolean getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(final Boolean customerContact) {
        this.customerContact = customerContact;
    }

    public Set<ActivityTypeDetailPropertyDto> getActivityTypeDetailProperties() {
        return activityTypeDetailProperties;
    }

    public void setActivityTypeDetailProperties(final Set<ActivityTypeDetailPropertyDto> activityTypeDetailProperties) {
        this.activityTypeDetailProperties = activityTypeDetailProperties;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityTypeDto that = (ActivityTypeDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(canBePlanned, that.canBePlanned) &&
                Objects.equals(mayBeAutomated, that.mayBeAutomated) &&
                Objects.equals(specific, that.specific) &&
                Objects.equals(mayHaveDeclaration, that.mayHaveDeclaration) &&
                Objects.equals(chargeMin, that.chargeMin) &&
                Objects.equals(chargeMax, that.chargeMax) &&
                Objects.equals(customerContact, that.customerContact) &&
                Objects.equals(activityTypeDetailProperties, that.activityTypeDetailProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, canBePlanned, mayBeAutomated, specific, mayHaveDeclaration, chargeMin, chargeMax, customerContact,
                activityTypeDetailProperties);
    }

    @Override
    public String toString() {
        return "ActivityTypeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", canBePlanned=" + canBePlanned +
                ", mayBeAutomated=" + mayBeAutomated +
                ", specific=" + specific +
                ", mayHaveDeclaration=" + mayHaveDeclaration +
                ", chargeMin=" + chargeMin +
                ", chargeMax=" + chargeMax +
                ", customerContact=" + customerContact +
                ", activityTypeDetailProperties=" + activityTypeDetailProperties +
                '}';
    }
}
