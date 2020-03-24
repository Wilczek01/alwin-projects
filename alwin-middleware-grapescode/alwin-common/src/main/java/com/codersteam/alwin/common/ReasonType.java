package com.codersteam.alwin.common;

/**
 * Typy przyczyn zablokowania/odrzucenia
 *
 * @author Piotr Naroznik
 */
public enum ReasonType {


    CLIENT_COMPLAINTS("Reklamacja Klienta – błąd po stronie AL."),

    CUSTOMER_SERVICE("Wniosek w DOK"),

    MISSING_DATA("Brak danych windykacyjnych"),

    CORRECTION("Korekta rozrachunku"),

    ZWB("W obsłudze ZWB"),

    OTHER("Inne");

    ReasonType(final String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}
