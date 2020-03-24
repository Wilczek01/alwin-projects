package com.codersteam.alwin.core.api.model.issue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Piotr Naroznik
 */
public class IssueTerminationRequestStateDto {

    private static final String NEW_KEY = "NEW";
    private static final String ACCEPTED_KEY = "ACCEPTED";
    private static final String REJECTED_KEY = "REJECTED";

    public static final IssueTerminationRequestStateDto NEW = new IssueTerminationRequestStateDto(NEW_KEY, "Nowe");
    public static final IssueTerminationRequestStateDto ACCEPTED = new IssueTerminationRequestStateDto(ACCEPTED_KEY, "Realizowane");
    public static final IssueTerminationRequestStateDto REJECTED = new IssueTerminationRequestStateDto(REJECTED_KEY, "Zakończone");

    private final String key;
    private final String label;

    @JsonCreator
    private IssueTerminationRequestStateDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static IssueTerminationRequestStateDto valueOf(final String name) {
        switch (name) {
            case NEW_KEY:
                return NEW;
            case ACCEPTED_KEY:
                return ACCEPTED;
            case REJECTED_KEY:
                return REJECTED;
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
}