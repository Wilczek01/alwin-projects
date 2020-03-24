package com.codersteam.alwin.core.api.model.activity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * @author Michal Horowic
 */
public class ActivityStateDto {

    private static final String PLANNED_KEY = "PLANNED";
    private static final String EXECUTED_KEY = "EXECUTED";
    private static final String POSTPONED_KEY = "POSTPONED";
    private static final String CANCELED_KEY = "CANCELED";

    public static final ActivityStateDto PLANNED = new ActivityStateDto(PLANNED_KEY, "Zaplanowana", false);
    public static final ActivityStateDto EXECUTED = new ActivityStateDto(EXECUTED_KEY, "Wykonana", true);
    public static final ActivityStateDto POSTPONED = new ActivityStateDto(POSTPONED_KEY, "Przełożona", false);
    public static final ActivityStateDto CANCELED = new ActivityStateDto(CANCELED_KEY, "Anulowana", true);

    public static final List<ActivityStateDto> STATUSES = asList(PLANNED, POSTPONED, CANCELED, EXECUTED);
    public static final List<ActivityStateDto> CLOSED_STATUSES = asList(CANCELED, EXECUTED);

    private final String key;
    private final String label;
    private final Boolean closed;

    @JsonCreator
    private ActivityStateDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label, final @JsonProperty("closed") Boolean closed) {
        this.key = key;
        this.label = label;
        this.closed = closed;
    }

    public static ActivityStateDto valueOf(final String name) {
        switch (name) {
            case PLANNED_KEY:
                return PLANNED;
            case EXECUTED_KEY:
                return EXECUTED;
            case POSTPONED_KEY:
                return POSTPONED;
            case CANCELED_KEY:
                return CANCELED;
            default:
                throw new IllegalArgumentException("Unable to map " + name);
        }
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityStateDto that = (ActivityStateDto) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(label, that.label) &&
                Objects.equals(closed, that.closed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, label, closed);
    }

    @Override
    public String toString() {
        return "ActivityStateDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                ", closed=" + closed +
                '}';
    }
}
