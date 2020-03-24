package com.codersteam.alwin.core.api.model.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * Stan cywilny osoby
 *
 * @author Michal Horowic
 */
public final class MaritalStatusDto {
    private static final int UNKNOWN_KEY = 0;
    private static final int FREE_KEY = 1;
    private static final int MARRIED_UNION_KEY = 2;
    private static final int MARRIED_KEY = 3;

    public static final MaritalStatusDto UNKNOWN = new MaritalStatusDto(UNKNOWN_KEY, "Nieznany");
    public static final MaritalStatusDto FREE = new MaritalStatusDto(FREE_KEY, "Wolny/wolna");
    public static final MaritalStatusDto MARRIED_UNION = new MaritalStatusDto(MARRIED_UNION_KEY, "Żonaty/zamężna ze wspólnotą majątkową");
    public static final MaritalStatusDto MARRIED = new MaritalStatusDto(MARRIED_KEY, "Żonaty/zamężna bez wspólnoty majątkowej");

    public static final List<MaritalStatusDto> ALL = asList(UNKNOWN, FREE, MARRIED_UNION, MARRIED);

    private final int key;
    private final String label;

    @JsonCreator
    private MaritalStatusDto(@JsonProperty("key") final int key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static MaritalStatusDto valueOf(final Integer key) {
        if (key == null) {
            return null;
        }
        switch (key) {
            case UNKNOWN_KEY:
                return UNKNOWN;
            case FREE_KEY:
                return FREE;
            case MARRIED_UNION_KEY:
                return MARRIED_UNION;
            case MARRIED_KEY:
                return MARRIED;
            default:
                throw new IllegalArgumentException("Unable to map " + key);
        }
    }

    public int getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MaritalStatusDto that = (MaritalStatusDto) o;
        return key == that.key &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, label);
    }

    @Override
    public String toString() {
        return "MaritalStatusDto{" +
                "key=" + key +
                ", label='" + label + '\'' +
                '}';
    }
}
