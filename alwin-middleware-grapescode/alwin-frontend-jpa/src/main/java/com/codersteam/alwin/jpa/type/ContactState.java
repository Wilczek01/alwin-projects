package com.codersteam.alwin.jpa.type;

/**
 * @author Adam Stepnowski
 */
public enum ContactState {
    ACTIVE("Aktywny"),
    INACTIVE("Nieaktywny"),
    PREFERRED("Preferowany");

    String label;

    ContactState(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}