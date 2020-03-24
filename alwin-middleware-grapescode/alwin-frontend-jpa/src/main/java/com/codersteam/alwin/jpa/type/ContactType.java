package com.codersteam.alwin.jpa.type;

import static java.lang.String.format;

/**
 * Typy kontaktów
 *
 * @author Adam Stepnowski
 */
public enum ContactType {

    /**
     * Osoba kontaktowa
     */
    CONTACT_PERSON,

    /**
     * Numer telefonu
     */
    PHONE_NUMBER,

    /**
     * E-mail
     */
    E_MAIL,

    /**
     * E-mail dla dokumentów
     */
    DOCUMENT_E_MAIL,

    /**
     * E-mail do biura
     */
    OFFICE_E_EMAIL,

    /**
     * Adres internetowy
     */
    INTERNET_ADDRESS;

    public static ContactType forImportedType(final ContactImportedType contactImportedType) {
        switch (contactImportedType) {
            case CONTACT_PERSON:
                return CONTACT_PERSON;
            case MOBILE_PHONE:
            case PHONE_NUMBER_3:
            case PHONE_NUMBER_2:
            case PHONE_NUMBER_1:
                return PHONE_NUMBER;
            case E_MAIL:
                return E_MAIL;
            case DOCUMENT_E_MAIL:
                return DOCUMENT_E_MAIL;
            case OFFICE_E_EMAIL:
                return OFFICE_E_EMAIL;
            case INTERNET_ADDRESS:
                return INTERNET_ADDRESS;
            default:
                throw new IllegalArgumentException(format("Unsupported contact imported type %s", contactImportedType));
        }
    }
}
