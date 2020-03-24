package com.codersteam.alwin.core.api.model.activity;

import java.util.Objects;

/**
 * @author Michal Horowic
 */
public class ActivityDetailDto {

    private Long id;
    private ActivityDetailPropertyDto detailProperty;
    private String value;
    private Boolean required;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ActivityDetailPropertyDto getDetailProperty() {
        return detailProperty;
    }

    public void setDetailProperty(final ActivityDetailPropertyDto detailProperty) {
        this.detailProperty = detailProperty;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
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
        final ActivityDetailDto that = (ActivityDetailDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(detailProperty, that.detailProperty) &&
                Objects.equals(value, that.value) &&
                Objects.equals(required, that.required);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, detailProperty, value, required);
    }

    @Override
    public String toString() {
        return "ActivityDetailDto{" +
                "id=" + id +
                ", detailProperty=" + detailProperty +
                ", value='" + value + '\'' +
                ", required=" + required +
                '}';
    }
}
