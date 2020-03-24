package com.codersteam.alwin.jpa.type;

import com.codersteam.aida.core.api.model.AidaCompanyDto;

/**
 * Typy zaimportowanych kontaktów
 *
 * @author Tomasz Śliwiński
 */
public enum ContactImportedType {

    /**
     * Osoba kontaktowa
     */
    CONTACT_PERSON {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getContactPerson();
        }
    },

    /**
     * Telefon komórkowy
     */
    MOBILE_PHONE {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getMobilePhoneNumber();
        }
    },

    /**
     * Numer telefonu 1
     */
    PHONE_NUMBER_1 {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getPhoneNumber1();
        }
    },

    /**
     * Numer telefonu 2
     */
    PHONE_NUMBER_2 {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getPhoneNumber2();
        }
    },

    /**
     * Numer telefonu 3
     */
    PHONE_NUMBER_3 {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getPhoneNumber3();
        }
    },

    /**
     * E-mail
     */
    E_MAIL {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getEmail();
        }
    },

    /**
     * E-mail dla dokumentów
     */
    DOCUMENT_E_MAIL {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getDocumentsEmail();
        }
    },

    /**
     * E-mail do biura
     */
    OFFICE_E_EMAIL {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getOfficeEmail();
        }
    },

    /**
     * Adres internetowy
     */
    INTERNET_ADDRESS {
        @Override
        public String getPropertyValue(final AidaCompanyDto aidaCompany) {
            return aidaCompany.getWebsiteAddress();
        }
    };

    public abstract String getPropertyValue(AidaCompanyDto aidaCompany);
}