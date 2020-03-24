package com.codersteam.alwin.core.api.model.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

/**
 * @author Piotr Naroznik
 */
public class ContactStateDto {

    private static final String ACTIVE_KEY = "ACTIVE";
    private static final String INACTIVE_KEY = "INACTIVE";
    private static final String PREFERRED_KEY = "PREFERRED";
    private static final String ACTIVE_STATE = "Aktywny";
    private static final String INACTIVE_STATE = "Nieaktywny";
    private static final String PREFERRED_STATE = "Preferowany";
    public static final ContactStateDto ACTIVE = new ContactStateDto(ACTIVE_KEY, "Aktywny");
    public static final ContactStateDto INACTIVE = new ContactStateDto(INACTIVE_KEY, "Nieaktywny");
    public static final ContactStateDto PREFERRED = new ContactStateDto(PREFERRED_KEY, "Preferowany");
    public static final List<ContactStateDto> ALL_CONTACT_STATES_WITH_ORDER = Arrays.asList(PREFERRED, ACTIVE, INACTIVE);

    private final String key;
    private final String label;

    @JsonCreator
    private ContactStateDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static ContactStateDto valueOf(final String name) {
        switch (name) {
            case PREFERRED_KEY:
                return PREFERRED;
            case INACTIVE_KEY:
                return INACTIVE;
            case ACTIVE_KEY:
                return ACTIVE;
            default:
                throw new IllegalArgumentException("Unable to map " + name);
        }
    }

    public static int suggestedOrder(final String name) {
        switch (name) {
            case PREFERRED_STATE:
                return 0;
            case ACTIVE_STATE:
                return 1;
            case INACTIVE_STATE:
                return 2;
            default:
                return 3;
        }
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }


    @Override
    public String toString() {
        return "ContactStateDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}