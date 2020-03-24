package com.codersteam.alwin.core.api.model.issue;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * @author Dariusz Rackowski
 */
public final class IssuePriorityDto {

    private static final String NORMAL_KEY = "NORMAL";
    private static final String HIGH_KEY = "HIGH";

    public static final IssuePriorityDto NORMAL = new IssuePriorityDto(NORMAL_KEY, "Normalny");
    public static final IssuePriorityDto HIGH = new IssuePriorityDto(HIGH_KEY, "Wysoki");

    public static final List<IssuePriorityDto> PRIORITIES = asList(NORMAL, HIGH);

    private final String key;
    private final String label;

    private IssuePriorityDto(final String key, final String label) {
        this.key = key;
        this.label = label;
    }

    public static IssuePriorityDto valueOf(final Integer priority) {
        switch (priority) {
            case 0:
                return NORMAL;
            case 1:
                return HIGH;
            default:
                throw new IllegalArgumentException("Unable to map priority " + priority);
        }
    }

    public static int priorityByKey(final String key) {
        switch (key) {
            case NORMAL_KEY:
                return 0;
            case HIGH_KEY:
                return 1;
            default:
                throw new IllegalArgumentException("Unable to map " + key);
        }
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IssuePriorityDto that = (IssuePriorityDto) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, label);
    }

    @Override
    public String toString() {
        return "IssuePriorityDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
