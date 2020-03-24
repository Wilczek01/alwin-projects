package com.codersteam.alwin.core.api.model.issue;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * @author Michal Horowic
 */
public final class IssueStateDto {

    private static final String NEW_KEY = "NEW";
    private static final String IN_PROGRESS_KEY = "IN_PROGRESS";
    private static final String DONE_KEY = "DONE";
    private static final String CANCELED_KEY = "CANCELED";
    private static final String WAITING_FOR_TERMINATION_KEY = "WAITING_FOR_TERMINATION";

    public static final IssueStateDto NEW = new IssueStateDto(NEW_KEY, "Nowe");
    public static final IssueStateDto IN_PROGRESS = new IssueStateDto(IN_PROGRESS_KEY, "Realizowane");
    public static final IssueStateDto DONE = new IssueStateDto(DONE_KEY, "Zakonczone");
    public static final IssueStateDto CANCELED = new IssueStateDto(CANCELED_KEY, "Anulowane");
    public static final IssueStateDto WAITING_FOR_TERMINATION = new IssueStateDto(WAITING_FOR_TERMINATION_KEY, "Czekające na zamknięcie");

    public static final List<IssueStateDto> STATES = asList(NEW, IN_PROGRESS, WAITING_FOR_TERMINATION, DONE, CANCELED);
    public static final List<IssueStateDto> ACTIVE_STATES = asList(NEW, IN_PROGRESS, WAITING_FOR_TERMINATION);
    public static final List<IssueStateDto> CLOSED_ISSUE_STATES = asList(DONE, CANCELED);

    private final String key;
    private final String label;

    private IssueStateDto(final String key, final String label) {
        this.key = key;
        this.label = label;
    }

    public static IssueStateDto valueOf(final String name) {
        switch (name) {
            case NEW_KEY:
                return NEW;
            case IN_PROGRESS_KEY:
                return IN_PROGRESS;
            case DONE_KEY:
                return DONE;
            case CANCELED_KEY:
                return CANCELED;
            case WAITING_FOR_TERMINATION_KEY:
                return WAITING_FOR_TERMINATION;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IssueStateDto that = (IssueStateDto) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, label);
    }

    @Override
    public String toString() {
        return "IssueStateDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
