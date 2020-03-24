package com.codersteam.alwin.core.api.model.activity;

import com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType;

import java.util.Objects;

/**
 * @author Adam Stepnowski
 */
public class ActivityTypeDetailPropertyDto {

    private Long id;
    private ActivityDetailPropertyDto activityDetailProperty;
    private ActivityTypeDetailPropertyType state;
    private Boolean required;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ActivityDetailPropertyDto getActivityDetailProperty() {
        return activityDetailProperty;
    }

    public void setActivityDetailProperty(final ActivityDetailPropertyDto activityDetailProperty) {
        this.activityDetailProperty = activityDetailProperty;
    }

    public ActivityTypeDetailPropertyType getState() {
        return state;
    }

    public void setState(final ActivityTypeDetailPropertyType state) {
        this.state = state;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityTypeDetailPropertyDto that = (ActivityTypeDetailPropertyDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(activityDetailProperty, that.activityDetailProperty) &&
                Objects.equals(state, that.state) &&
                Objects.equals(required, that.required);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, activityDetailProperty, state, required);
    }

    @Override
    public String toString() {
        return "ActivityTypeDetailPropertyDto{" +
                "id=" + id +
                ", activityDetailProperty=" + activityDetailProperty +
                ", state=" + state +
                ", required=" + required +
                '}';
    }
}
