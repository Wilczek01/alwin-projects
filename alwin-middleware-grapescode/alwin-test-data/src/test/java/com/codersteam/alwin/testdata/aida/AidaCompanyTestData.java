package com.codersteam.alwin.testdata.aida;


import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.model.LegalFormDto;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.customer.Person;
import com.codersteam.alwin.testdata.CompanyTestData;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.testdata.AddressTestData.address1;
import static com.codersteam.alwin.testdata.AddressTestData.address2;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_5;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_6;
import static com.codersteam.alwin.testdata.CompanyTestData.company;
import static com.codersteam.alwin.testdata.CompanyTestData.company1;
import static com.codersteam.alwin.testdata.CompanyTestData.company5;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail1;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail10;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail11;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail12;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail13;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail2;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail3;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail4;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail5;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail6;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail7;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail8;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail9;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaLegalFormTestData.legalFormDto1;
import static com.codersteam.alwin.testdata.aida.AidaLegalFormTestData.legalFormDto3;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.expectedCompanyPersons;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.expectedPerson;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * @author Michal Horowic
 */
public class AidaCompanyTestData {

    public static final String AIDA_COMPANY_2_COMPANIES_NAME_PREFIX = "ADH";
    public static final String NOT_EXISTING_AIDA_COMPANY_NAME = "CANNOT_FIND_ME";
    public static final Long NON_EXISTING_AIDA_COMPANY_ID = -1L;
    public static final Long EXCEPTION_CAUSING_AIDA_COMPANY_ID = -100L;

    public static final Long COMPANY_ID_10 = 10L;
    public static final String COMPANY_NIP_10 = "98768308";
    private static final String POSTAL_CODE_10 = "8308";
    private static final String CITY_10 = "ADHPoczta8308";
    private static final String PREFIX_10 = "pl.";
    private static final String STREET_10 = "ADHUlica28308";
    private static final String CORRESPONDENCE_POSTAL_CODE_10 = "33424";
    private static final String CORRESPONDENCE_CITY_10 = "ADHPoczta33424";
    private static final String CORRESPONDENCE_PREFIX_10 = "ul.";
    private static final String CORRESPONDENCE_STREET_10 = "ADHUlica33424";
    public static final String REGON_10 = "12348308";
    public static final String COMPANY_NAME_10 = "ADHNazwa8308";
    public static final String SHORT_NAME_10 = "ADH8308";
    public static final String RECIPIENT_NAME_10 = "JOANNA SZNAJDROWICZ USLUGI TRANSPORTOWE";
    public static final boolean EXTERNAL_DB_AGREEMENT_10 = false;
    public static final Date EXTERNAL_DB_AGREEMENT_DATE_10 = parse("2017-05-31");
    public static final String RATING_10 = "Q01";
    public static final String RATING_DATE_10 = "2015-12-31";
    public static final String KRS_10 = "krs";
    public static final String CONTACT_PERSON_10 = "Pani Halinka";
    public static final String EMAIL_10 = "kontakt@firma.example.zzz";
    public static final String DOCUMENTS_EMAIL_10 = "dokumenty@firma.example.zzz";
    public static final String OFFICE_EMAIL_10 = "biuro@firma.example.zzz";
    public static final String PHONE_NUMBER_1_10 = "tel 1";
    public static final String PHONE_NUMBER_2_10 = "tel 2";
    public static final String PHONE_NUMBER_3_10 = "tel 3";
    public static final String MOBILE_PHONE_NUMBER_10 = "mobile";
    public static final String WEBSITE_ADDRESS_10 = "http address 10";
    public static final Segment SEGMENT_10 = CompanyTestData.SEGMENT_1;
    private static final BigDecimal INVOLVEMENT_10 = CompanyTestData.INVOLVEMENT_1;
    public static final String COMPANY_PKD_CODE_10 = "28.41.Z";
    public static final String COMPANY_PKD_NAME_10 = "Produkcja maszyn do obróbki metalu";

    private static final Long COMPANY_ID_11 = 11L;
    private static final String COMPANY_NIP_11 = "98762305";
    private static final String POSTAL_CODE_11 = "2305";
    private static final String CITY_11 = "ADHPoczta2305";
    private static final String PREFIX_11 = "ul.";
    private static final String STREET_11 = "ADHUlica22305";
    private static final String CORRESPONDENCE_POSTAL_CODE_11 = "33305";
    private static final String CORRESPONDENCE_CITY_11 = "ADHPoczta33305";
    private static final String CORRESPONDENCE_PREFIX_11 = "ul.";
    private static final String CORRESPONDENCE_STREET_11 = "ADHUlica33305";
    private static final String REGON_11 = "12342305";
    private static final String COMPANY_NAME_11 = "ADHNazwa2305";
    private static final String SHORT_NAME_11 = "ADH2305";
    private static final String RECIPIENT_NAME_11 = "JAN KOWALSKI USLUGI DORADCZE";
    private static final boolean EXTERNAL_DB_AGREEMENT_11 = false;
    private static final Date EXTERNAL_DB_AGREEMENT_DATE_11 = parse("2017-05-31");
    private static final String RATING_11 = "Q01";
    private static final String RATING_DATE_11 = "2015-12-31";
    private static final String KRS_11 = "krs";
    private static final String CONTACT_PERSON_11 = "Pani Janina";
    private static final String EMAIL_11 = "kontakt@jan.example.zzz";
    private static final String DOCUMENTS_EMAIL_11 = "dokumenty@jan.example.zzz";
    private static final String OFFICE_EMAIL_11 = "biuro@jan.example.zzz";
    private static final String PHONE_NUMBER_1_11 = "tel 1305";
    private static final String PHONE_NUMBER_2_11 = "tel 2305";
    private static final String PHONE_NUMBER_3_11 = "tel 3305";
    private static final String MOBILE_PHONE_NUMBER_11 = "mobile 305";
    private static final String WEBSITE_ADDRESS_11 = "http address 11";
    public static final String COMPANY_PKD_CODE_11 = "90.04.Z";
    public static final String COMPANY_PKD_NAME_11 = "Działalność obiektów kulturalnych";

    public static final BigDecimal INVOLVEMENT_1 = new BigDecimal("19987.54");
    public static final BigDecimal INVOLVEMENT_2 = new BigDecimal("28876.43");
    public static final BigDecimal INVOLVEMENT_5 = new BigDecimal("1997.54");
    public static final BigDecimal INVOLVEMENT_6 = new BigDecimal("2876.43");
    public static final BigDecimal INVOLVEMENT_7 = new BigDecimal("9987.54");
    public static final BigDecimal INVOLVEMENT_8 = new BigDecimal("58876.43");
    public static final BigDecimal INVOLVEMENT_9 = new BigDecimal("17.14");
    public static final BigDecimal INVOLVEMENT_12 = new BigDecimal("288.93");
    public static final BigDecimal INVOLVEMENT_14 = new BigDecimal("288.45");
    public static final BigDecimal INVOLVEMENT_22 = new BigDecimal("44876.43");
    public static final BigDecimal INVOLVEMENT_23 = new BigDecimal("44876.43");
    public static final BigDecimal INVOLVEMENT_24 = new BigDecimal("44876.43");
    public static final BigDecimal INVOLVEMENT_26 = new BigDecimal("44876.43");

    public static AidaCompanyDto aidaCompanyDto10() {
        return companyDto(COMPANY_ID_10, POSTAL_CODE_10, CITY_10, PREFIX_10, STREET_10, CORRESPONDENCE_POSTAL_CODE_10, CORRESPONDENCE_CITY_10,
                CORRESPONDENCE_PREFIX_10, CORRESPONDENCE_STREET_10, COMPANY_NIP_10, REGON_10, COMPANY_NAME_10, SHORT_NAME_10, RECIPIENT_NAME_10,
                EXTERNAL_DB_AGREEMENT_10, EXTERNAL_DB_AGREEMENT_DATE_10, RATING_10, parse(RATING_DATE_10), KRS_10, CONTACT_PERSON_10, EMAIL_10,
                DOCUMENTS_EMAIL_10, OFFICE_EMAIL_10, PHONE_NUMBER_1_10, PHONE_NUMBER_2_10, PHONE_NUMBER_3_10, MOBILE_PHONE_NUMBER_10, legalFormDto3(),
                WEBSITE_ADDRESS_10, COMPANY_PKD_CODE_10, COMPANY_PKD_NAME_10);
    }

    public static AidaCompanyDto aidaCompanyDto(final Long extCompanyId) {
        final AidaCompanyDto aidaCompanyDto = aidaCompanyDto10();
        aidaCompanyDto.setId(extCompanyId);
        return aidaCompanyDto;
    }

    public static AidaCompanyDto aidaCompanyDto1() {
        final AidaCompanyDto aidaCompanyDto = aidaCompanyDto10();
        aidaCompanyDto.setId(EXT_COMPANY_ID_1);
        return aidaCompanyDto;
    }

    public static AidaCompanyDto aidaCompanyDto5() {
        final AidaCompanyDto aidaCompanyDto = aidaCompanyDto10();
        aidaCompanyDto.setId(EXT_COMPANY_ID_5);
        return aidaCompanyDto;
    }

    public static AidaCompanyDto aidaCompanyDto6() {
        final AidaCompanyDto aidaCompanyDto = aidaCompanyDto10();
        aidaCompanyDto.setId(EXT_COMPANY_ID_6);
        return aidaCompanyDto;
    }

    public static Company companyFromAida10() {
        return company(null, COMPANY_ID_10, COMPANY_NAME_10, SHORT_NAME_10, COMPANY_NIP_10, REGON_10, KRS_10, RECIPIENT_NAME_10, RATING_10,
                parse(RATING_DATE_10), EXTERNAL_DB_AGREEMENT_10, EXTERNAL_DB_AGREEMENT_DATE_10, null, null, null, null, null,
                COMPANY_PKD_CODE_10, COMPANY_PKD_NAME_10);
    }

    public static AidaCompanyDto aidaCompanyDto11() {
        return companyDto(COMPANY_ID_11, POSTAL_CODE_11, CITY_11, PREFIX_11, STREET_11, CORRESPONDENCE_POSTAL_CODE_11, CORRESPONDENCE_CITY_11,
                CORRESPONDENCE_PREFIX_11, CORRESPONDENCE_STREET_11, COMPANY_NIP_11, REGON_11, COMPANY_NAME_11, SHORT_NAME_11, RECIPIENT_NAME_11,
                EXTERNAL_DB_AGREEMENT_11, EXTERNAL_DB_AGREEMENT_DATE_11, RATING_11, parse(RATING_DATE_11), KRS_11, CONTACT_PERSON_11, EMAIL_11,
                DOCUMENTS_EMAIL_11, OFFICE_EMAIL_11, PHONE_NUMBER_1_11, PHONE_NUMBER_2_11, PHONE_NUMBER_3_11, MOBILE_PHONE_NUMBER_11, legalFormDto1(),
                WEBSITE_ADDRESS_11, COMPANY_PKD_CODE_11, COMPANY_PKD_NAME_11);
    }

    public static AidaCompanyDto importedAidaCompanyDto() {
        return companyDto(EXT_COMPANY_ID_1, POSTAL_CODE_10, CITY_10, PREFIX_10, STREET_10, CORRESPONDENCE_POSTAL_CODE_10, CORRESPONDENCE_CITY_10,
                CORRESPONDENCE_PREFIX_10, CORRESPONDENCE_STREET_10, COMPANY_NIP_10, REGON_10, COMPANY_NAME_10, SHORT_NAME_10, RECIPIENT_NAME_10,
                EXTERNAL_DB_AGREEMENT_10, EXTERNAL_DB_AGREEMENT_DATE_10, RATING_10, parse(RATING_DATE_10), KRS_10, CONTACT_PERSON_10, EMAIL_10,
                DOCUMENTS_EMAIL_10, OFFICE_EMAIL_10, PHONE_NUMBER_1_10, PHONE_NUMBER_2_10, PHONE_NUMBER_3_10, MOBILE_PHONE_NUMBER_10, legalFormDto3(),
                null, COMPANY_PKD_CODE_10, COMPANY_PKD_NAME_10);
    }

    public static Company expectedUpdatedCompany() {
        final List<CompanyPerson> companyPersons = expectedCompanyPersons();
        final Set<Address> addresses = expectedAddresses();
        final Set<ContactDetail> contacts = expectedContactDetails();
        return company(COMPANY_ID_1, EXT_COMPANY_ID_1, COMPANY_NAME_10, SHORT_NAME_10, COMPANY_NIP_10, REGON_10, KRS_10, RECIPIENT_NAME_10, RATING_10,
                parse(RATING_DATE_10), EXTERNAL_DB_AGREEMENT_10, EXTERNAL_DB_AGREEMENT_DATE_10, companyPersons, addresses, contacts, SEGMENT_10,
                INVOLVEMENT_10, COMPANY_PKD_CODE_10, COMPANY_PKD_NAME_10);
    }

    public static Company expectedCompanyWithUpdatedInvolvement1() {
        final Company company = company1();
        company.setInvolvement(INVOLVEMENT_1);
        return company;
    }

    public static Company expectedCompanyWithUpdatedInvolvement2() {
        final Company company = company5();
        company.setInvolvement(INVOLVEMENT_5);
        return company;
    }

    private static Set<Person> expectedPersons() {
        return new HashSet<>(singletonList(expectedPerson()));
    }

    private static Set<Address> expectedAddresses() {
        return new HashSet<>(asList(
                expectedAddress(CORRESPONDENCE_CITY_10, CORRESPONDENCE_POSTAL_CODE_10, CORRESPONDENCE_STREET_10, CORRESPONDENCE_PREFIX_10, address1()),
                expectedAddress(CITY_10, POSTAL_CODE_10, STREET_10, PREFIX_10, address2())));
    }

    private static Set<ContactDetail> expectedContactDetails() {
        return new HashSet<>(asList(expectedContact(CONTACT_PERSON_10, contactDetail1()), contactDetail2(), contactDetail3(),
                expectedContact(MOBILE_PHONE_NUMBER_10, contactDetail4()), contactDetail5(), contactDetail6(),
                expectedContact(PHONE_NUMBER_1_10, contactDetail7()), expectedContact(PHONE_NUMBER_2_10, contactDetail8()),
                expectedContact(PHONE_NUMBER_3_10, contactDetail9()), expectedContact(EMAIL_10, contactDetail10()),
                expectedContact(DOCUMENTS_EMAIL_10, contactDetail11()), expectedContact(OFFICE_EMAIL_10, contactDetail12()), contactDetail13()));
    }

    private static Address expectedAddress(final String city, final String postalCode, final String street, final String prefix, final Address address) {
        address.setPostalCode(postalCode);
        address.setStreetPrefix(prefix);
        address.setStreetName(street);
        address.setCity(city);
        return address;
    }

    private static ContactDetail expectedContact(final String contact, final ContactDetail contactDetail) {
        contactDetail.setContact(contact);
        return contactDetail;
    }

    private static AidaCompanyDto companyDto(final Long id, final String postalCode, final String city, final String prefix, final String street,
                                             final String correspondencePostalCode, final String correspondenceCity, final String correspondencePrefix,
                                             final String correspondenceStreet, final String nip, final String regon, final String companyName,
                                             final String shortName, final String recipientName, final Boolean externalDbAgreement,
                                             final Date externalDbAgreementDate, final String rating, final Date ratingDate, final String krs,
                                             final String contactPerson, final String email, final String documentsEmail, final String officeEmail,
                                             final String phoneNumber1, final String phoneNumber2, final String phoneNumber3, final String mobilePhoneNumber,
                                             final LegalFormDto legalForm, final String websiteAddress, final String pkdCode, final String pkdName) {
        final AidaCompanyDto company = new AidaCompanyDto();
        company.setId(id);
        company.setPostalCode(postalCode);
        company.setCity(city);
        company.setPrefix(prefix);
        company.setStreet(street);
        company.setCorrespondencePostalCode(correspondencePostalCode);
        company.setCorrespondenceCity(correspondenceCity);
        company.setCorrespondencePrefix(correspondencePrefix);
        company.setCorrespondenceStreet(correspondenceStreet);
        company.setNip(nip);
        company.setRegon(regon);
        company.setCompanyName(companyName);
        company.setShortName(shortName);
        company.setRecipientName(recipientName);
        company.setExternalDBAgreement(externalDbAgreement);
        company.setExternalDBAgreementDate(externalDbAgreementDate);
        company.setRating(rating);
        company.setRatingDate(ratingDate);
        company.setKrs(krs);
        company.setContactPerson(contactPerson);
        company.setEmail(email);
        company.setDocumentsEmail(documentsEmail);
        company.setOfficeEmail(officeEmail);
        company.setPhoneNumber1(phoneNumber1);
        company.setPhoneNumber2(phoneNumber2);
        company.setPhoneNumber3(phoneNumber3);
        company.setMobilePhoneNumber(mobilePhoneNumber);
        company.setLegalForm(legalForm);
        company.setWebsiteAddress(websiteAddress);
        company.setPkdCode(pkdCode);
        company.setPkdName(pkdName);
        return company;
    }
}
