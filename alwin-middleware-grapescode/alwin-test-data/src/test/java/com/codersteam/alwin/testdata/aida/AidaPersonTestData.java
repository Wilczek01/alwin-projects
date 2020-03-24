package com.codersteam.alwin.testdata.aida;


import com.codersteam.aida.core.api.model.AidaPersonDto;
import com.codersteam.aida.core.api.model.CountryDto;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.Person;

import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.CompanyPersonTestData.companyPerson;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_5;
import static com.codersteam.alwin.testdata.PersonTestData.PERSON_ID_100;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCountryTestData.TEST_COUNTRY_DTO;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;

/**
 * @author Michal Horowic
 */
public class AidaPersonTestData {

    public static final Long COMPANY_ID = 1L;
    public static final long PERSON_ID_1 = 8888L;
    private static final String FIRST_NAME = "Jan";
    private static final String LAST_NAME = "Kowalsky";
    private static final String PESEL = "88010101234";
    private static final Date BIRTH_DATE = parse("2016-07-28");
    private static final Date SIGNED_DATE = parse("2016-07-28");
    private static final String ADDRESS = "ul. Testowa 5";
    private static final boolean REPRESENTATIVE = true;
    private static final Integer MARITAL_STATUS = 0;
    private static final String ID_DOC_COUNTRY = "PL";
    private static final String ID_DOC_NUMBER = "AAA876543";
    private static final String ID_DOC_SIGNED_BY = "Adam Mickiewicz";
    private static final boolean POLITICIAN = false;
    private static final String FUNCTION = "stanowisko 1";
    private static final boolean REAL_BENEFICIARY = true;
    private static final Integer ID_DOC_TYPE = 1;
    private static final String COUNTRY = "Polska";

    private static final Long COMPANY_ID_9999 = EXT_COMPANY_ID_1;
    private static final long PERSON_ID_1_9999 = 1;
    private static final String FIRST_NAME_9999 = "Wiesław";
    private static final String LAST_NAME_9999 = "Wszywka";
    private static final String PESEL_9999 = "60071007090";
    private static final Date BIRTH_DATE_9999 = parse("1960-07-10");
    private static final Date SIGNED_DATE_9999 = parse("2014-10-12");
    private static final String ADDRESS_9999 = "ul. Testowa 9999";
    private static final boolean REPRESENTATIVE_9999 = true;
    private static final Integer MARITAL_STATUS_9999 = 0;
    private static final String ID_DOC_COUNTRY_9999 = "PL";
    private static final String ID_DOC_NUMBER_9999 = "ABC123456";
    private static final String ID_DOC_SIGNED_BY_9999 = "Janina Bąk";
    private static final boolean POLITICIAN_9999 = false;
    private static final String FUNCTION_9999 = "księgowy";
    private static final boolean REAL_BENEFICIARY_9999 = true;
    private static final Integer ID_DOC_TYPE_9999 = 1;

    private static final Long COMPANY_ID_9995 = EXT_COMPANY_ID_5;
    private static final long PERSON_ID_1_9995 = 1L;
    private static final String FIRST_NAME_9995 = "Zbigniew";
    private static final String LAST_NAME_9995 = "Boniek";
    private static final String PESEL_9995 = "38072716570";
    private static final Date BIRTH_DATE_9995 = parse("1938-07-27");
    private static final Date SIGNED_DATE_9995 = parse("2010-01-12");
    private static final String ADDRESS_9995 = "ul. Testowa 9995";
    private static final boolean REPRESENTATIVE_9995 = true;
    private static final Integer MARITAL_STATUS_9995 = 0;
    private static final String ID_DOC_COUNTRY_9995 = "PL";
    private static final String ID_DOC_NUMBER_9995 = "EFG123456";
    private static final String ID_DOC_SIGNED_BY_9995 = "Barnaba Polano";
    private static final boolean POLITICIAN_9995 = false;
    private static final String FUNCTION_9995 = "sekretarz";
    private static final boolean REAL_BENEFICIARY_9995 = false;
    private static final Integer ID_DOC_TYPE_9995 = 1;

    public static final long PERSON_ID_2 = 8899L;
    public static final long PERSON_ID_3 = 1199L;

    public static final AidaPersonDto TEST_EXT_PERSON_1 = aidaPerson1();
    public static final AidaPersonDto TEST_EXT_PERSON_2 = aidaPerson2();

    public static AidaPersonDto aidaPerson9999_1() {
        return aidaPerson(COMPANY_ID_9999, PERSON_ID_1_9999, FIRST_NAME_9999, LAST_NAME_9999, PESEL_9999, ADDRESS_9999, BIRTH_DATE_9999, REPRESENTATIVE_9999,
                MARITAL_STATUS_9999, ID_DOC_COUNTRY_9999, ID_DOC_NUMBER_9999, ID_DOC_SIGNED_BY_9999, SIGNED_DATE_9999, POLITICIAN_9999, FUNCTION_9999,
                REAL_BENEFICIARY_9999, ID_DOC_TYPE_9999, TEST_COUNTRY_DTO);
    }

    public static AidaPersonDto aidaPerson9995_1() {
        return aidaPerson(COMPANY_ID_9995, PERSON_ID_1_9995, FIRST_NAME_9995, LAST_NAME_9995, PESEL_9995, ADDRESS_9995, BIRTH_DATE_9995, REPRESENTATIVE_9995,
                MARITAL_STATUS_9995, ID_DOC_COUNTRY_9995, ID_DOC_NUMBER_9995, ID_DOC_SIGNED_BY_9995, SIGNED_DATE_9995, POLITICIAN_9995, FUNCTION_9995,
                REAL_BENEFICIARY_9995, ID_DOC_TYPE_9995, TEST_COUNTRY_DTO);
    }

    public static AidaPersonDto aidaPerson1() {
        return aidaPerson(COMPANY_ID, PERSON_ID_1, FIRST_NAME, LAST_NAME, PESEL, ADDRESS, BIRTH_DATE, REPRESENTATIVE, MARITAL_STATUS, ID_DOC_COUNTRY,
                ID_DOC_NUMBER, ID_DOC_SIGNED_BY, SIGNED_DATE, POLITICIAN, FUNCTION, REAL_BENEFICIARY, ID_DOC_TYPE, TEST_COUNTRY_DTO);
    }

    public static AidaPersonDto aidaPerson2() {
        final AidaPersonDto person = aidaPerson1();
        person.setPersonId(PERSON_ID_2);
        return person;
    }

    public static AidaPersonDto aidaPerson3() {
        final AidaPersonDto person = aidaPerson1();
        person.setPersonId(PERSON_ID_3);
        return person;
    }

    public static AidaPersonDto importedAidaPersonDto() {
        final AidaPersonDto aidaPersonDto = aidaPerson2();
        aidaPersonDto.setCompanyId(EXT_COMPANY_ID_1);
        return aidaPersonDto;
    }

    public static Person expectedPerson() {
        final Person person = new Person();
        person.setId(PERSON_ID_100);
        person.setPersonId(PERSON_ID_2);
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);
        person.setPesel(PESEL);
        person.setAddress(ADDRESS);
        person.setBirthDate(BIRTH_DATE);
        person.setRepresentative(REPRESENTATIVE);
        person.setMaritalStatus(MARITAL_STATUS);
        person.setIdDocCountry(ID_DOC_COUNTRY);
        person.setIdDocNumber(ID_DOC_NUMBER);
        person.setIdDocSignedBy(ID_DOC_SIGNED_BY);
        person.setIdDocSignDate(SIGNED_DATE);
        person.setPolitician(POLITICIAN);
        person.setJobFunction(FUNCTION);
        person.setRealBeneficiary(REAL_BENEFICIARY);
        person.setIdDocType(ID_DOC_TYPE);
        person.setCountry(COUNTRY);
        person.setAddresses(emptySet());
        person.setContactDetails(emptySet());
        return person;
    }

    private static AidaPersonDto aidaPerson(final Long companyId, final Long personId1, final String firstName, final String lastName, final String pesel,
                                            final String address, final Date birthDate, final Boolean representative, final Integer maritalStatus,
                                            final String idDocCountry, final String idDocNumber, final String idDocSignedBy, final Date signedDate,
                                            final Boolean politician, final String function, final Boolean realBeneficiary, final Integer idDocType,
                                            final CountryDto testCountryDto) {
        final AidaPersonDto person = new AidaPersonDto();
        person.setCompanyId(companyId);
        person.setPersonId(personId1);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPesel(pesel);
        person.setAddress(address);
        person.setBirthDate(birthDate);
        person.setRepresentative(representative);
        person.setMaritalStatus(maritalStatus);
        person.setIdDocCountry(idDocCountry);
        person.setIdDocNumber(idDocNumber);
        person.setIdDocSignedBy(idDocSignedBy);
        person.setIdDocSignDate(signedDate);
        person.setPolitician(politician);
        person.setFunction(function);
        person.setRealBeneficiary(realBeneficiary);
        person.setIdDocType(idDocType);
        person.setCountry(testCountryDto);
        return person;
    }

    static List<CompanyPerson> expectedCompanyPersons() {
        return singletonList(companyPerson(expectedPerson(), false));
    }
}
