package com.codersteam.alwin.core.api.model.issue;

import com.codersteam.alwin.common.issue.Segment;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * @author Piotr Naroznik
 */
public final class SegmentDto {

    private static final String A_KEY = "A";
    private static final String B_KEY = "B";

    public static final SegmentDto A = new SegmentDto(A_KEY, "A");
    public static final SegmentDto B = new SegmentDto(B_KEY, "B");

    public static final List<SegmentDto> SEGMENTS = asList(A, B);

    private final String key;
    private final String label;

    @JsonCreator
    private SegmentDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static SegmentDto valueOf(final String name) {
        switch (name) {
            case A_KEY:
                return A;
            case B_KEY:
                return B;
            default:
                throw new IllegalArgumentException("Unable to map " + name);
        }
    }

    public static Segment segmentByKey(final String key) {
        switch (key) {
            case A_KEY:
                return Segment.A;
            case B_KEY:
                return Segment.B;
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
        final SegmentDto that = (SegmentDto) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, label);
    }

    @Override
    public String toString() {
        return "SegmentDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}