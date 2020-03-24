package com.codersteam.alwin.core.api.model.activity;

import java.util.Objects;

/**
 * @author Tomasz Sliwinski
 */
public class ActivityTypeDetailPropertyDictValueDto {

    private Long id;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityTypeDetailPropertyDictValueDto)) return false;
        final ActivityTypeDetailPropertyDictValueDto that = (ActivityTypeDetailPropertyDictValueDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "ActivityTypeDetailPropertyDictValueDto{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
