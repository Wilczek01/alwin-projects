package com.codersteam.alwin.core.api.model.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * Typ dokumentu tożsamości
 *
 * @author Michal Horowic
 */
public final class DocTypeDto {

    private static final int UNKNOWN_KEY = 0;
    private static final int ID_KEY = 1;
    private static final int PASSPORT_KEY = 2;
    private static final int TEMP_ID_KEY = 3;
    private static final int RESIDENCE_CARD_KEY = 4;
    private static final int OTHER_KEY = 5;


    public static final DocTypeDto UNKNOWN = new DocTypeDto(UNKNOWN_KEY, "Nieznany");
    public static final DocTypeDto ID = new DocTypeDto(ID_KEY, "Dowód osobisty");
    public static final DocTypeDto PASSPORT = new DocTypeDto(PASSPORT_KEY, "Paszport");
    public static final DocTypeDto TEMP_ID = new DocTypeDto(TEMP_ID_KEY, "Dowód tymczasowy");
    public static final DocTypeDto RESIDENCE_CARD = new DocTypeDto(RESIDENCE_CARD_KEY, "Karta pobytu");
    public static final DocTypeDto OTHER = new DocTypeDto(OTHER_KEY, "Inne");

    public static final List<DocTypeDto> ALL = asList(UNKNOWN, ID, PASSPORT, OTHER);

    private final int key;
    private final String label;

    @JsonCreator
    private DocTypeDto(@JsonProperty("key") final int key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static DocTypeDto valueOf(final Integer key) {
        if (key == null) {
            return null;
        }
        switch (key) {
            case UNKNOWN_KEY:
                return UNKNOWN;
            case ID_KEY:
                return ID;
            case PASSPORT_KEY:
                return PASSPORT;
            case TEMP_ID_KEY:
                return TEMP_ID;
            case RESIDENCE_CARD_KEY:
                return RESIDENCE_CARD;
            case OTHER_KEY:
                return OTHER;
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
        final DocTypeDto that = (DocTypeDto) o;
        return key == that.key &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, label);
    }

    @Override
    public String toString() {
        return "DocTypeDto{" +
                "key=" + key +
                ", label='" + label + '\'' +
                '}';
    }
}
