package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.customer.ContactDetailDto;
import com.codersteam.alwin.core.api.model.customer.ContactStateDto;
import com.codersteam.alwin.core.api.model.customer.ContactTypeDto;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.type.ContactImportedType;
import com.codersteam.alwin.jpa.type.ContactState;
import com.codersteam.alwin.jpa.type.ContactType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.CONTACT_PERSON_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.DOCUMENTS_EMAIL_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.EMAIL_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.MOBILE_PHONE_NUMBER_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.OFFICE_EMAIL_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.PHONE_NUMBER_1_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.PHONE_NUMBER_2_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.PHONE_NUMBER_3_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.WEBSITE_ADDRESS_10;
import static java.util.Arrays.asList;

@SuppressWarnings("WeakerAccess")
public class ContactDetailTestData {

    public static final boolean SEND_AUTOMATIC_SMS_FALSE = false;
    public static final boolean SEND_AUTOMATIC_SMS_TRUE = true;

    public static final Long NON_EXISTING_CONTACT_DETAIL_ID = -1L;
    public static final Long CONTACT_DETAIL_ID_1 = 1L;
    private static final String CONTACT_DETAIL_REMARK_1 = "trudno się dodzownić";
    private static final String CONTACT_DETAIL_CONTACT_1 = "Jan Kochanowski";
    private static final ContactState CONTACT_DETAIL_STATE_1 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_1 = ContactType.CONTACT_PERSON;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_1 = ContactStateDto.ACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_1 = ContactTypeDto.CONTACT_PERSON;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_1 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_1 = ContactImportedType.CONTACT_PERSON;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_1 = contactDetailDto(CONTACT_DETAIL_ID_1, CONTACT_DETAIL_STATE_DTO_1, CONTACT_DETAIL_REMARK_1,
            CONTACT_DETAIL_CONTACT_1, CONTACT_DETAIL_CONTACT_TYPE_DTO_1, null);

    public static final Long CONTACT_DETAIL_ID_2 = 2L;
    private static final String CONTACT_DETAIL_REMARK_2 = "w godzinach 10-18";
    private static final String CONTACT_DETAIL_CONTACT_2 = "Adam Mickiewicz";
    private static final ContactState CONTACT_DETAIL_STATE_2 = ContactState.PREFERRED;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_2 = ContactType.CONTACT_PERSON;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_2 = ContactStateDto.PREFERRED;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_2 = ContactTypeDto.CONTACT_PERSON;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_2 = false;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_2 = null;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_2 = contactDetailDto(CONTACT_DETAIL_ID_2, CONTACT_DETAIL_STATE_DTO_2, CONTACT_DETAIL_REMARK_2,
            CONTACT_DETAIL_CONTACT_2, CONTACT_DETAIL_CONTACT_TYPE_DTO_2, null);

    private static final Long CONTACT_DETAIL_ID_3 = 3L;
    private static final String CONTACT_DETAIL_REMARK_3 = "nie odbiera";
    private static final String CONTACT_DETAIL_CONTACT_3 = "Juliusz Słowacki";
    private static final ContactState CONTACT_DETAIL_STATE_3 = ContactState.INACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_3 = ContactType.CONTACT_PERSON;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_3 = ContactStateDto.INACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_3 = ContactTypeDto.CONTACT_PERSON;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_3 = false;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_3 = null;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_3 = contactDetailDto(CONTACT_DETAIL_ID_3, CONTACT_DETAIL_STATE_DTO_3, CONTACT_DETAIL_REMARK_3,
            CONTACT_DETAIL_CONTACT_3, CONTACT_DETAIL_CONTACT_TYPE_DTO_3, null);

    private static final Long CONTACT_DETAIL_ID_4 = 4L;
    private static final String CONTACT_DETAIL_REMARK_4 = "Jan - trudno się dodzownić";
    private static final String CONTACT_DETAIL_CONTACT_4 = "0048123456790";
    private static final ContactState CONTACT_DETAIL_STATE_4 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_4 = ContactType.PHONE_NUMBER;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_4 = ContactStateDto.ACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_4 = ContactTypeDto.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_4 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_4 = ContactImportedType.MOBILE_PHONE;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_4 = contactDetailDto(CONTACT_DETAIL_ID_4, CONTACT_DETAIL_STATE_DTO_4, CONTACT_DETAIL_REMARK_4,
            CONTACT_DETAIL_CONTACT_4, CONTACT_DETAIL_CONTACT_TYPE_DTO_4, SEND_AUTOMATIC_SMS_TRUE);

    private static final Long CONTACT_DETAIL_ID_5 = 5L;
    private static final String CONTACT_DETAIL_REMARK_5 = "Adam -  w godzinach 10-18";
    private static final String CONTACT_DETAIL_CONTACT_5 = "0048123456791";
    private static final ContactState CONTACT_DETAIL_STATE_5 = ContactState.PREFERRED;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_5 = ContactType.PHONE_NUMBER;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_5 = ContactStateDto.PREFERRED;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_5 = ContactTypeDto.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_5 = false;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_5 = null;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_5 = contactDetailDto(CONTACT_DETAIL_ID_5, CONTACT_DETAIL_STATE_DTO_5, CONTACT_DETAIL_REMARK_5,
            CONTACT_DETAIL_CONTACT_5, CONTACT_DETAIL_CONTACT_TYPE_DTO_5, SEND_AUTOMATIC_SMS_FALSE);

    private static final Long CONTACT_DETAIL_ID_6 = 6L;
    private static final String CONTACT_DETAIL_REMARK_6 = "Juliusz - nie odbiera";
    private static final String CONTACT_DETAIL_CONTACT_6 = "0048123456789";
    private static final ContactState CONTACT_DETAIL_STATE_6 = ContactState.INACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_6 = ContactType.PHONE_NUMBER;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_6 = ContactStateDto.INACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_6 = ContactTypeDto.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_6 = false;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_6 = null;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_6 = contactDetailDto(CONTACT_DETAIL_ID_6, CONTACT_DETAIL_STATE_DTO_6, CONTACT_DETAIL_REMARK_6,
            CONTACT_DETAIL_CONTACT_6, CONTACT_DETAIL_CONTACT_TYPE_DTO_6, SEND_AUTOMATIC_SMS_TRUE);

    private static final Long CONTACT_DETAIL_ID_7 = 7L;
    private static final String CONTACT_DETAIL_REMARK_7 = null;
    private static final String CONTACT_DETAIL_CONTACT_7 = "220987654";
    private static final ContactState CONTACT_DETAIL_STATE_7 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_7 = ContactType.PHONE_NUMBER;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_7 = ContactStateDto.ACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_7 = ContactTypeDto.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_7 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_7 = ContactImportedType.PHONE_NUMBER_1;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_7 = contactDetailDto(CONTACT_DETAIL_ID_7, CONTACT_DETAIL_STATE_DTO_7, CONTACT_DETAIL_REMARK_7,
            CONTACT_DETAIL_CONTACT_7, CONTACT_DETAIL_CONTACT_TYPE_DTO_7, SEND_AUTOMATIC_SMS_FALSE);

    private static final Long CONTACT_DETAIL_ID_8 = 8L;
    private static final String CONTACT_DETAIL_REMARK_8 = null;
    private static final String CONTACT_DETAIL_CONTACT_8 = "221234567";
    private static final ContactState CONTACT_DETAIL_STATE_8 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_8 = ContactType.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_8 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_8 = ContactImportedType.PHONE_NUMBER_2;

    private static final Long CONTACT_DETAIL_ID_9 = 9L;
    private static final String CONTACT_DETAIL_REMARK_9 = null;
    private static final String CONTACT_DETAIL_CONTACT_9 = "225678901";
    private static final ContactState CONTACT_DETAIL_STATE_9 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_9 = ContactType.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_9 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_9 = ContactImportedType.PHONE_NUMBER_3;

    private static final Long CONTACT_DETAIL_ID_10 = 10L;
    private static final String CONTACT_DETAIL_REMARK_10 = null;
    public static final String CONTACT_DETAIL_CONTACT_10 = "contat@company.pl";
    private static final ContactState CONTACT_DETAIL_STATE_10 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_10 = ContactType.E_MAIL;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_10 = ContactStateDto.ACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_10 = ContactTypeDto.E_MAIL;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_10 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_10 = ContactImportedType.E_MAIL;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_10 = contactDetailDto(CONTACT_DETAIL_ID_10, CONTACT_DETAIL_STATE_DTO_10, CONTACT_DETAIL_REMARK_10,
            CONTACT_DETAIL_CONTACT_10, CONTACT_DETAIL_CONTACT_TYPE_DTO_10, null);

    private static final Long CONTACT_DETAIL_ID_11 = 11L;
    private static final String CONTACT_DETAIL_REMARK_11 = null;
    public static final String CONTACT_DETAIL_CONTACT_11 = "documents@email.pl";
    private static final ContactState CONTACT_DETAIL_STATE_11 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_11 = ContactType.DOCUMENT_E_MAIL;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_11 = ContactStateDto.ACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_11 = ContactTypeDto.DOCUMENT_E_MAIL;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_11 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_11 = ContactImportedType.DOCUMENT_E_MAIL;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_11 = contactDetailDto(CONTACT_DETAIL_ID_11, CONTACT_DETAIL_STATE_DTO_11, CONTACT_DETAIL_REMARK_11,
            CONTACT_DETAIL_CONTACT_11, CONTACT_DETAIL_CONTACT_TYPE_DTO_11, null);

    private static final Long CONTACT_DETAIL_ID_12 = 12L;
    private static final String CONTACT_DETAIL_REMARK_12 = null;
    public static final String CONTACT_DETAIL_CONTACT_12 = "test@test.pl";
    private static final ContactState CONTACT_DETAIL_STATE_12 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_12 = ContactType.OFFICE_E_EMAIL;
    private static final ContactStateDto CONTACT_DETAIL_STATE_DTO_12 = ContactStateDto.ACTIVE;
    private static final ContactTypeDto CONTACT_DETAIL_CONTACT_TYPE_DTO_12 = ContactTypeDto.OFFICE_E_EMAIL;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_12 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_12 = ContactImportedType.OFFICE_E_EMAIL;
    public static final ContactDetailDto CONTACT_DETAIL_DTO_12 = contactDetailDto(CONTACT_DETAIL_ID_12, CONTACT_DETAIL_STATE_DTO_12, CONTACT_DETAIL_REMARK_12,
            CONTACT_DETAIL_CONTACT_12, CONTACT_DETAIL_CONTACT_TYPE_DTO_12, null);

    private static final Long CONTACT_DETAIL_ID_13 = 13L;
    private static final String CONTACT_DETAIL_REMARK_13 = null;
    private static final String CONTACT_DETAIL_CONTACT_13 = "http://www.google.pl";
    private static final ContactState CONTACT_DETAIL_STATE_13 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_13 = ContactType.INTERNET_ADDRESS;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_13 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_13 = ContactImportedType.INTERNET_ADDRESS;

    private static final Long CONTACT_DETAIL_ID_21 = 21L;
    private static final String CONTACT_DETAIL_REMARK_21 = null;
    private static final String CONTACT_DETAIL_CONTACT_21 = "Juliusz Cezar";
    private static final ContactState CONTACT_DETAIL_STATE_21 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_21 = ContactType.CONTACT_PERSON;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_21 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_21 = ContactImportedType.CONTACT_PERSON;

    private static final Long CONTACT_DETAIL_ID_22 = 22L;
    private static final String CONTACT_DETAIL_REMARK_22 = null;
    private static final String CONTACT_DETAIL_CONTACT_22 = "contat@najlepszafirma.pl";
    private static final ContactState CONTACT_DETAIL_STATE_22 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_22 = ContactType.E_MAIL;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_22 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_22 = ContactImportedType.E_MAIL;

    private static final Long CONTACT_DETAIL_ID_23 = 23L;
    private static final String CONTACT_DETAIL_REMARK_23 = null;
    private static final String CONTACT_DETAIL_CONTACT_23 = "221297834";
    private static final ContactState CONTACT_DETAIL_STATE_23 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_23 = ContactType.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_23 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_23 = ContactImportedType.PHONE_NUMBER_1;

    private static final Long CONTACT_DETAIL_ID_24 = 24L;
    private static final String CONTACT_DETAIL_REMARK_24 = null;
    private static final String CONTACT_DETAIL_CONTACT_24 = "228901267";
    private static final ContactState CONTACT_DETAIL_STATE_24 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_24 = ContactType.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_24 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_24 = ContactImportedType.PHONE_NUMBER_2;

    private static final Long CONTACT_DETAIL_ID_25 = 25L;
    private static final String CONTACT_DETAIL_REMARK_25 = null;
    private static final String CONTACT_DETAIL_CONTACT_25 = "223247865";
    private static final ContactState CONTACT_DETAIL_STATE_25 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_25 = ContactType.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_25 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_25 = ContactImportedType.PHONE_NUMBER_3;

    private static final Long CONTACT_DETAIL_ID_26 = 26L;
    private static final String CONTACT_DETAIL_REMARK_26 = null;
    private static final String CONTACT_DETAIL_CONTACT_26 = "0048534978576";
    private static final ContactState CONTACT_DETAIL_STATE_26 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_26 = ContactType.PHONE_NUMBER;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_26 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_26 = ContactImportedType.MOBILE_PHONE;

    private static final Long CONTACT_DETAIL_ID_27 = 27L;
    private static final String CONTACT_DETAIL_REMARK_27 = null;
    private static final String CONTACT_DETAIL_CONTACT_27 = "documents@najlepszafirma.pl";
    private static final ContactState CONTACT_DETAIL_STATE_27 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_27 = ContactType.DOCUMENT_E_MAIL;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_27 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_27 = ContactImportedType.DOCUMENT_E_MAIL;

    private static final Long CONTACT_DETAIL_ID_28 = 28L;
    private static final String CONTACT_DETAIL_REMARK_28 = null;
    private static final String CONTACT_DETAIL_CONTACT_28 = "office@najlepszafirma.pl";
    private static final ContactState CONTACT_DETAIL_STATE_28 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_28 = ContactType.OFFICE_E_EMAIL;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_28 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_28 = ContactImportedType.OFFICE_E_EMAIL;

    private static final Long CONTACT_DETAIL_ID_29 = 29L;
    private static final String CONTACT_DETAIL_REMARK_29 = null;
    private static final String CONTACT_DETAIL_CONTACT_29 = "http://www.apple.pl";
    private static final ContactState CONTACT_DETAIL_STATE_29 = ContactState.ACTIVE;
    private static final ContactType CONTACT_DETAIL_CONTACT_TYPE_29 = ContactType.INTERNET_ADDRESS;
    private static final boolean CONTACT_DETAIL_IMPORTED_FROM_AIDA_29 = true;
    private static final ContactImportedType CONTACT_DETAIL_IMPORTED_TYPE_29 = ContactImportedType.INTERNET_ADDRESS;

    private static final Long CONTACT_DETAIL_ID_100 = 100L;

    public static ContactDetail contactDetail1() {
        return contactDetail(CONTACT_DETAIL_ID_1, CONTACT_DETAIL_STATE_1, CONTACT_DETAIL_REMARK_1,
                CONTACT_DETAIL_CONTACT_1, CONTACT_DETAIL_CONTACT_TYPE_1, CONTACT_DETAIL_IMPORTED_FROM_AIDA_1, CONTACT_DETAIL_IMPORTED_TYPE_1,
                null);
    }

    public static ContactDetail contactDetail2() {
        return contactDetail(CONTACT_DETAIL_ID_2, CONTACT_DETAIL_STATE_2, CONTACT_DETAIL_REMARK_2,
                CONTACT_DETAIL_CONTACT_2, CONTACT_DETAIL_CONTACT_TYPE_2, CONTACT_DETAIL_IMPORTED_FROM_AIDA_2, CONTACT_DETAIL_IMPORTED_TYPE_2,
                null);
    }

    public static ContactDetail contactDetail3() {
        return contactDetail(CONTACT_DETAIL_ID_3, CONTACT_DETAIL_STATE_3, CONTACT_DETAIL_REMARK_3,
                CONTACT_DETAIL_CONTACT_3, CONTACT_DETAIL_CONTACT_TYPE_3, CONTACT_DETAIL_IMPORTED_FROM_AIDA_3, CONTACT_DETAIL_IMPORTED_TYPE_3,
                null);
    }

    public static ContactDetail contactDetail4() {
        return contactDetail(CONTACT_DETAIL_ID_4, CONTACT_DETAIL_STATE_4, CONTACT_DETAIL_REMARK_4,
                CONTACT_DETAIL_CONTACT_4, CONTACT_DETAIL_CONTACT_TYPE_4, CONTACT_DETAIL_IMPORTED_FROM_AIDA_4, CONTACT_DETAIL_IMPORTED_TYPE_4,
                SEND_AUTOMATIC_SMS_TRUE);
    }

    public static ContactDetail contactDetail5() {
        return contactDetail(CONTACT_DETAIL_ID_5, CONTACT_DETAIL_STATE_5, CONTACT_DETAIL_REMARK_5,
                CONTACT_DETAIL_CONTACT_5, CONTACT_DETAIL_CONTACT_TYPE_5, CONTACT_DETAIL_IMPORTED_FROM_AIDA_5, CONTACT_DETAIL_IMPORTED_TYPE_5,
                SEND_AUTOMATIC_SMS_FALSE);
    }

    public static ContactDetail contactDetail6() {
        return contactDetail(CONTACT_DETAIL_ID_6, CONTACT_DETAIL_STATE_6, CONTACT_DETAIL_REMARK_6,
                CONTACT_DETAIL_CONTACT_6, CONTACT_DETAIL_CONTACT_TYPE_6, CONTACT_DETAIL_IMPORTED_FROM_AIDA_6, CONTACT_DETAIL_IMPORTED_TYPE_6,
                SEND_AUTOMATIC_SMS_TRUE);
    }

    public static ContactDetail contactDetail7() {
        return contactDetail(CONTACT_DETAIL_ID_7, CONTACT_DETAIL_STATE_7, CONTACT_DETAIL_REMARK_7,
                CONTACT_DETAIL_CONTACT_7, CONTACT_DETAIL_CONTACT_TYPE_7, CONTACT_DETAIL_IMPORTED_FROM_AIDA_7, CONTACT_DETAIL_IMPORTED_TYPE_7,
                SEND_AUTOMATIC_SMS_FALSE);
    }

    public static ContactDetail contactDetail8() {
        return contactDetail(CONTACT_DETAIL_ID_8, CONTACT_DETAIL_STATE_8, CONTACT_DETAIL_REMARK_8,
                CONTACT_DETAIL_CONTACT_8, CONTACT_DETAIL_CONTACT_TYPE_8, CONTACT_DETAIL_IMPORTED_FROM_AIDA_8, CONTACT_DETAIL_IMPORTED_TYPE_8,
                SEND_AUTOMATIC_SMS_FALSE);
    }

    public static ContactDetail contactDetail9() {
        return contactDetail(CONTACT_DETAIL_ID_9, CONTACT_DETAIL_STATE_9, CONTACT_DETAIL_REMARK_9,
                CONTACT_DETAIL_CONTACT_9, CONTACT_DETAIL_CONTACT_TYPE_9, CONTACT_DETAIL_IMPORTED_FROM_AIDA_9, CONTACT_DETAIL_IMPORTED_TYPE_9,
                SEND_AUTOMATIC_SMS_FALSE);
    }

    public static ContactDetail contactDetail10() {
        return contactDetail(CONTACT_DETAIL_ID_10, CONTACT_DETAIL_STATE_10, CONTACT_DETAIL_REMARK_10,
                CONTACT_DETAIL_CONTACT_10, CONTACT_DETAIL_CONTACT_TYPE_10, CONTACT_DETAIL_IMPORTED_FROM_AIDA_10, CONTACT_DETAIL_IMPORTED_TYPE_10,
                null);
    }

    public static ContactDetail contactDetail11() {
        return contactDetail(CONTACT_DETAIL_ID_11, CONTACT_DETAIL_STATE_11, CONTACT_DETAIL_REMARK_11,
                CONTACT_DETAIL_CONTACT_11, CONTACT_DETAIL_CONTACT_TYPE_11, CONTACT_DETAIL_IMPORTED_FROM_AIDA_11, CONTACT_DETAIL_IMPORTED_TYPE_11,
                null);
    }

    public static ContactDetail contactDetail12() {
        return contactDetail(CONTACT_DETAIL_ID_12, CONTACT_DETAIL_STATE_12, CONTACT_DETAIL_REMARK_12,
                CONTACT_DETAIL_CONTACT_12, CONTACT_DETAIL_CONTACT_TYPE_12, CONTACT_DETAIL_IMPORTED_FROM_AIDA_12, CONTACT_DETAIL_IMPORTED_TYPE_12,
                null);
    }

    public static ContactDetail contactDetail13() {
        return contactDetail(CONTACT_DETAIL_ID_13, CONTACT_DETAIL_STATE_13, CONTACT_DETAIL_REMARK_13,
                CONTACT_DETAIL_CONTACT_13, CONTACT_DETAIL_CONTACT_TYPE_13, CONTACT_DETAIL_IMPORTED_FROM_AIDA_13, CONTACT_DETAIL_IMPORTED_TYPE_13,
                null);
    }

    public static ContactDetail contactDetail21() {
        return contactDetail(CONTACT_DETAIL_ID_21, CONTACT_DETAIL_STATE_21, CONTACT_DETAIL_REMARK_21,
                CONTACT_DETAIL_CONTACT_21, CONTACT_DETAIL_CONTACT_TYPE_21, CONTACT_DETAIL_IMPORTED_FROM_AIDA_21, CONTACT_DETAIL_IMPORTED_TYPE_21,
                null);
    }

    public static ContactDetail contactDetail22() {
        return contactDetail(CONTACT_DETAIL_ID_22, CONTACT_DETAIL_STATE_22, CONTACT_DETAIL_REMARK_22,
                CONTACT_DETAIL_CONTACT_22, CONTACT_DETAIL_CONTACT_TYPE_22, CONTACT_DETAIL_IMPORTED_FROM_AIDA_22, CONTACT_DETAIL_IMPORTED_TYPE_22,
                null);
    }

    public static ContactDetail contactDetail23() {
        return contactDetail(CONTACT_DETAIL_ID_23, CONTACT_DETAIL_STATE_23, CONTACT_DETAIL_REMARK_23,
                CONTACT_DETAIL_CONTACT_23, CONTACT_DETAIL_CONTACT_TYPE_23, CONTACT_DETAIL_IMPORTED_FROM_AIDA_23, CONTACT_DETAIL_IMPORTED_TYPE_23,
                SEND_AUTOMATIC_SMS_FALSE);
    }

    public static ContactDetail contactDetail24() {
        return contactDetail(CONTACT_DETAIL_ID_24, CONTACT_DETAIL_STATE_24, CONTACT_DETAIL_REMARK_24,
                CONTACT_DETAIL_CONTACT_24, CONTACT_DETAIL_CONTACT_TYPE_24, CONTACT_DETAIL_IMPORTED_FROM_AIDA_24, CONTACT_DETAIL_IMPORTED_TYPE_24,
                SEND_AUTOMATIC_SMS_TRUE);
    }

    public static ContactDetail contactDetail25() {
        return contactDetail(CONTACT_DETAIL_ID_25, CONTACT_DETAIL_STATE_25, CONTACT_DETAIL_REMARK_25,
                CONTACT_DETAIL_CONTACT_25, CONTACT_DETAIL_CONTACT_TYPE_25, CONTACT_DETAIL_IMPORTED_FROM_AIDA_25, CONTACT_DETAIL_IMPORTED_TYPE_25,
                SEND_AUTOMATIC_SMS_FALSE);
    }

    public static ContactDetail contactDetail26() {
        return contactDetail(CONTACT_DETAIL_ID_26, CONTACT_DETAIL_STATE_26, CONTACT_DETAIL_REMARK_26,
                CONTACT_DETAIL_CONTACT_26, CONTACT_DETAIL_CONTACT_TYPE_26, CONTACT_DETAIL_IMPORTED_FROM_AIDA_26, CONTACT_DETAIL_IMPORTED_TYPE_26,
                SEND_AUTOMATIC_SMS_TRUE);
    }

    public static ContactDetail contactDetail27() {
        return contactDetail(CONTACT_DETAIL_ID_27, CONTACT_DETAIL_STATE_27, CONTACT_DETAIL_REMARK_27,
                CONTACT_DETAIL_CONTACT_27, CONTACT_DETAIL_CONTACT_TYPE_27, CONTACT_DETAIL_IMPORTED_FROM_AIDA_27, CONTACT_DETAIL_IMPORTED_TYPE_27,
                null);
    }

    public static ContactDetail contactDetail28() {
        return contactDetail(CONTACT_DETAIL_ID_28, CONTACT_DETAIL_STATE_28, CONTACT_DETAIL_REMARK_28,
                CONTACT_DETAIL_CONTACT_28, CONTACT_DETAIL_CONTACT_TYPE_28, CONTACT_DETAIL_IMPORTED_FROM_AIDA_28, CONTACT_DETAIL_IMPORTED_TYPE_28,
                null);
    }

    public static ContactDetail contactDetail29() {
        return contactDetail(CONTACT_DETAIL_ID_29, CONTACT_DETAIL_STATE_29, CONTACT_DETAIL_REMARK_29,
                CONTACT_DETAIL_CONTACT_29, CONTACT_DETAIL_CONTACT_TYPE_29, CONTACT_DETAIL_IMPORTED_FROM_AIDA_29, CONTACT_DETAIL_IMPORTED_TYPE_29,
                null);
    }

    public static ContactDetail contactDetailToUpdate() {
        return contactDetail(CONTACT_DETAIL_ID_2, CONTACT_DETAIL_STATE_1, CONTACT_DETAIL_REMARK_1, CONTACT_DETAIL_CONTACT_1, CONTACT_DETAIL_CONTACT_TYPE_1,
                CONTACT_DETAIL_IMPORTED_FROM_AIDA_1, CONTACT_DETAIL_IMPORTED_TYPE_1, null);
    }

    public static ContactDetail contactDetailToCreate() {
        return contactDetail(CONTACT_DETAIL_ID_100, CONTACT_DETAIL_STATE_1, CONTACT_DETAIL_REMARK_1, CONTACT_DETAIL_CONTACT_1, CONTACT_DETAIL_CONTACT_TYPE_1,
                CONTACT_DETAIL_IMPORTED_FROM_AIDA_1, CONTACT_DETAIL_IMPORTED_TYPE_1, null);
    }

    public static Set<ContactDetail> firstPersonContactDetails() {
        return new HashSet<>(asList(contactDetail1(), contactDetail2(), contactDetail3(), contactDetail4(), contactDetail5(), contactDetail6(),
                contactDetail7(), contactDetail8(), contactDetail9(), contactDetail10(), contactDetail11(), contactDetail12(), contactDetail13()));
    }

    public static Set<ContactDetail> secondPersonContactDetails() {
        return new HashSet<>(asList(contactDetail21(), contactDetail22(), contactDetail23(), contactDetail24(), contactDetail25(), contactDetail26(),
                contactDetail27(), contactDetail28(), contactDetail29()));
    }

    private static ContactDetail aidaCompany10ContactDetail1() {
        return contactDetail(null, ContactState.ACTIVE, null, CONTACT_PERSON_10, ContactType.CONTACT_PERSON, true,
                ContactImportedType.CONTACT_PERSON, null);
    }

    private static ContactDetail aidaCompany10ContactDetail2() {
        return contactDetail(null, ContactState.ACTIVE, null, MOBILE_PHONE_NUMBER_10, ContactType.PHONE_NUMBER, true,
                ContactImportedType.MOBILE_PHONE, null);
    }

    private static ContactDetail aidaCompany10ContactDetail3() {
        return contactDetail(null, ContactState.ACTIVE, null, EMAIL_10, ContactType.E_MAIL, true,
                ContactImportedType.E_MAIL, null);
    }

    private static ContactDetail aidaCompany10ContactDetail4() {
        return contactDetail(null, ContactState.ACTIVE, null, OFFICE_EMAIL_10, ContactType.OFFICE_E_EMAIL, true,
                ContactImportedType.OFFICE_E_EMAIL, null);
    }

    private static ContactDetail aidaCompany10ContactDetail5() {
        return contactDetail(null, ContactState.ACTIVE, null, PHONE_NUMBER_2_10, ContactType.PHONE_NUMBER, true,
                ContactImportedType.PHONE_NUMBER_2, null);
    }

    private static ContactDetail aidaCompany10ContactDetail6() {
        return contactDetail(null, ContactState.ACTIVE, null, DOCUMENTS_EMAIL_10, ContactType.DOCUMENT_E_MAIL, true,
                ContactImportedType.DOCUMENT_E_MAIL, null);
    }

    public static Set<ContactDetail> contactsSetWithAllEmails() {
        return new HashSet<>(asList(aidaCompany10ContactDetail3(), aidaCompany10ContactDetail4(), aidaCompany10ContactDetail6(),
                aidaCompany10ContactDetail5(), aidaCompany10ContactDetail8()));
    }

    public static Set<ContactDetail> contactsSetWithoutDocumentEmail() {
        return new HashSet<>(asList(aidaCompany10ContactDetail3(), aidaCompany10ContactDetail4(), aidaCompany10ContactDetail5(), aidaCompany10ContactDetail8()));
    }

    public static Set<ContactDetail> contactsSetWithoutDocumentAndContactEmail() {
        return new HashSet<>(asList(aidaCompany10ContactDetail4(), aidaCompany10ContactDetail5(), aidaCompany10ContactDetail8()));
    }

    public static Set<ContactDetail> contactsSetWithNoEmails() {
        return new HashSet<>(asList(aidaCompany10ContactDetail5(), aidaCompany10ContactDetail8()));
    }

    public static Set<ContactDetail> contactsSetWithoutPhone1() {
        return new HashSet<>(asList(aidaCompany10ContactDetail4(), aidaCompany10ContactDetail5()));
    }

    public static Set<ContactDetail> contactsSetWithoutPhone2() {
        return new HashSet<>(asList(aidaCompany10ContactDetail4(), aidaCompany10ContactDetail8()));
    }

    private static ContactDetail aidaCompany10ContactDetail7() {
        return contactDetail(null, ContactState.ACTIVE, null, WEBSITE_ADDRESS_10, ContactType.INTERNET_ADDRESS, true,
                ContactImportedType.INTERNET_ADDRESS, null);
    }

    private static ContactDetail aidaCompany10ContactDetail8() {
        return contactDetail(null, ContactState.ACTIVE, null, PHONE_NUMBER_1_10, ContactType.PHONE_NUMBER, true,
                ContactImportedType.PHONE_NUMBER_1, null);
    }

    private static ContactDetail aidaCompany10ContactDetail9() {
        return contactDetail(null, ContactState.ACTIVE, null, PHONE_NUMBER_3_10, ContactType.PHONE_NUMBER, true,
                ContactImportedType.PHONE_NUMBER_3, null);
    }

    public static List<ContactDetail> allAidaCompany10ContactDetails() {
        return asList(aidaCompany10ContactDetail1(), aidaCompany10ContactDetail2(), aidaCompany10ContactDetail3(), aidaCompany10ContactDetail4(),
                aidaCompany10ContactDetail5(), aidaCompany10ContactDetail6(), aidaCompany10ContactDetail7(), aidaCompany10ContactDetail8(),
                aidaCompany10ContactDetail9());
    }

    public static List<ContactDetailDto> contactDetailDtosUnsorted() {
        return Arrays.asList(CONTACT_DETAIL_DTO_6, CONTACT_DETAIL_DTO_7, CONTACT_DETAIL_DTO_5, CONTACT_DETAIL_DTO_4, CONTACT_DETAIL_DTO_3, CONTACT_DETAIL_DTO_2,
                CONTACT_DETAIL_DTO_1, CONTACT_DETAIL_DTO_12, CONTACT_DETAIL_DTO_10, CONTACT_DETAIL_DTO_11);
    }

    public static List<ContactDetailDto> contactDetailDtosSorted() {
        return Arrays.asList(CONTACT_DETAIL_DTO_5, CONTACT_DETAIL_DTO_2, CONTACT_DETAIL_DTO_10, CONTACT_DETAIL_DTO_4, CONTACT_DETAIL_DTO_7,
                CONTACT_DETAIL_DTO_1, CONTACT_DETAIL_DTO_12, CONTACT_DETAIL_DTO_11, CONTACT_DETAIL_DTO_6, CONTACT_DETAIL_DTO_3);
    }

    private static ContactDetail contactDetail(final Long id, final ContactState state, final String remark, final String contact, final ContactType
            contactType, final boolean importedFromAida, final ContactImportedType importedType, final Boolean sendAutomaticSms) {
        final ContactDetail contactDetail = new ContactDetail();
        contactDetail.setId(id);
        contactDetail.setState(state);
        contactDetail.setRemark(remark);
        contactDetail.setContact(contact);
        contactDetail.setContactType(contactType);
        contactDetail.setImportedFromAida(importedFromAida);
        contactDetail.setImportedType(importedType);
        contactDetail.setSendAutomaticSms(sendAutomaticSms);
        return contactDetail;
    }

    private static ContactDetailDto contactDetailDto(final Long id, final ContactStateDto state, final String remark, final String contact,
                                                     final ContactTypeDto contactType, final Boolean sendAutomaticSms) {
        final ContactDetailDto contactDetailDto = new ContactDetailDto();
        contactDetailDto.setId(id);
        contactDetailDto.setState(state);
        contactDetailDto.setRemark(remark);
        contactDetailDto.setContact(contact);
        contactDetailDto.setContactType(contactType);
        contactDetailDto.setSendAutomaticSms(sendAutomaticSms);
        return contactDetailDto;
    }
}
