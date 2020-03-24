package com.codersteam.alwin.jpa.type;

/**
 * @author Adam Stepnowski
 */
public enum AddressType {
    RESIDENCE("Siedziby/zamieszkania"), CORRESPONDENCE("Korespondencyjny"), BUSINESS("Prowadzenia dzialalnosci"), OTHER("Inny");

    private final String label;

    AddressType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
