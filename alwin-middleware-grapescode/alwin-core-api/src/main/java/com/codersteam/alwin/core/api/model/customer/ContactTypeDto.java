package com.codersteam.alwin.core.api.model.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Piotr Naroznik
 */
public class ContactTypeDto {
    private static final String E_MAIL_KEY = "E_MAIL";
    private static final String PHONE_NUMBER_KEY = "PHONE_NUMBER";
    private static final String CONTACT_PERSON_KEY = "CONTACT_PERSON";
    private static final String OFFICE_E_EMAIL_KEY = "OFFICE_E_EMAIL";
    private static final String DOCUMENT_E_MAIL_KEY = "DOCUMENT_E_MAIL";
    private static final String INTERNET_ADDRESS_KEY = "INTERNET_ADDRESS";

    public static final ContactTypeDto E_MAIL = new ContactTypeDto(E_MAIL_KEY, "E-mail", false, false, true, false);
    public static final ContactTypeDto PHONE_NUMBER = new ContactTypeDto(PHONE_NUMBER_KEY, "Numer telefonu", true, true, false, false);
    public static final ContactTypeDto CONTACT_PERSON = new ContactTypeDto(CONTACT_PERSON_KEY, "Osoba kontaktowa", false, false, false, false);
    public static final ContactTypeDto OFFICE_E_EMAIL = new ContactTypeDto(OFFICE_E_EMAIL_KEY, "E-mail do biura", false, false, true, false);
    public static final ContactTypeDto DOCUMENT_E_MAIL = new ContactTypeDto(DOCUMENT_E_MAIL_KEY, "E-mail dla dokumentów", false, false, true, false);
    public static final ContactTypeDto INTERNET_ADDRESS = new ContactTypeDto(INTERNET_ADDRESS_KEY, "Adres internetowy", false, false, false, true);

    public static final List<ContactTypeDto> ALL_CONTACT_TYPES_WITH_ORDER =
            asList(E_MAIL, PHONE_NUMBER, CONTACT_PERSON, OFFICE_E_EMAIL, DOCUMENT_E_MAIL, INTERNET_ADDRESS);

    private final String key;
    private final String label;
    private final boolean callable;
    private final boolean textable;
    private final boolean emailable;
    private final boolean website;

    @JsonCreator
    private ContactTypeDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label, @JsonProperty("callable") final boolean callable,
                           @JsonProperty("textable") final boolean textable, @JsonProperty("emailable") final boolean emailable,
                           @JsonProperty("website") final boolean website) {
        this.key = key;
        this.label = label;
        this.callable = callable;
        this.textable = textable;
        this.emailable = emailable;
        this.website = website;
    }

    public static ContactTypeDto valueOf(final String name) {
        switch (name) {
            case E_MAIL_KEY:
                return E_MAIL;
            case PHONE_NUMBER_KEY:
                return PHONE_NUMBER;
            case CONTACT_PERSON_KEY:
                return CONTACT_PERSON;
            case OFFICE_E_EMAIL_KEY:
                return OFFICE_E_EMAIL;
            case DOCUMENT_E_MAIL_KEY:
                return DOCUMENT_E_MAIL;
            case INTERNET_ADDRESS_KEY:
                return INTERNET_ADDRESS;
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

    public boolean isCallable() {
        return callable;
    }

    public boolean isTextable() {
        return textable;
    }

    public boolean isEmailable() {
        return emailable;
    }

    public boolean isWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return "ContactTypeDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                ", callable=" + callable +
                ", textable=" + textable +
                ", emailable=" + emailable +
                ", website=" + website +
                '}';
    }
}