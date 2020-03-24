package com.codersteam.alwin.core.api.model.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

/**
 * @author Piotr Naroznik
 */
public class AddressTypeDto {
    private static final String OTHER_KEY = "OTHER";
    private static final String BUSINESS_KEY = "BUSINESS";
    private static final String RESIDENCE_KEY = "RESIDENCE";
    private static final String CORRESPONDENCE_KEY = "CORRESPONDENCE";

    public static final AddressTypeDto OTHER = new AddressTypeDto(OTHER_KEY, "Inny");
    public static final AddressTypeDto BUSINESS = new AddressTypeDto(BUSINESS_KEY, "Prowadzenia działalności");
    public static final AddressTypeDto RESIDENCE = new AddressTypeDto(RESIDENCE_KEY, "Siedziby/zamieszkania");
    public static final AddressTypeDto CORRESPONDENCE = new AddressTypeDto(CORRESPONDENCE_KEY, "Korespondencyjny");

    public static final List<AddressTypeDto> ALL_ADDRESS_TYPES = Arrays.asList(OTHER, BUSINESS, RESIDENCE, CORRESPONDENCE);
    public static final List<AddressTypeDto> ADDRESS_TYPES_WITH_ORDER = Arrays.asList(RESIDENCE, CORRESPONDENCE, BUSINESS, OTHER);

    private final String key;
    private final String label;

    @JsonCreator
    private AddressTypeDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static AddressTypeDto valueOf(final String name) {
        switch (name) {
            case RESIDENCE_KEY:
                return RESIDENCE;
            case CORRESPONDENCE_KEY:
                return CORRESPONDENCE;
            case BUSINESS_KEY:
                return BUSINESS;
            case OTHER_KEY:
                return OTHER;
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
