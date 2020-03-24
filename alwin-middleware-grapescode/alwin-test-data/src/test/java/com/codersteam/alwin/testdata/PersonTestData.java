package com.codersteam.alwin.testdata;


import com.codersteam.alwin.core.api.model.customer.DocTypeDto;
import com.codersteam.alwin.core.api.model.customer.MaritalStatusDto;
import com.codersteam.alwin.core.api.model.customer.PersonDto;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.customer.Person;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.testdata.AddressTestData.address1;
import static com.codersteam.alwin.testdata.AddressTestData.address2;
import static com.codersteam.alwin.testdata.AddressTestData.address3;
import static com.codersteam.alwin.testdata.AddressTestData.addressToCreate;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetailToCreate;
import static com.codersteam.alwin.testdata.ContactDetailTestData.firstPersonContactDetails;
import static com.codersteam.alwin.testdata.ContactDetailTestData.secondPersonContactDetails;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

@SuppressWarnings("WeakerAccess")
public class PersonTestData {

    public static final long NON_EXISTING_PERSON_ID = -1L;
    public static final long PERSON_ID_100 = 100L;

    public static final long PERSON_ID_1 = 1L;
    public static final Long EXT_PERSON_ID_1 = 8888L;
    public static final String PESEL_1 = "88010101234";
    public static final String NAME_1 = "Jan";
    public static final String LAST_NAME_1 = "Kowalsky";
    private static final Boolean REPRESENTATIVE_1 = true;
    private static final String ADDRESS_1 = "ul. Testowa 4/1";
    private static final MaritalStatusDto MARITAL_STATUS_DTO_1 = MaritalStatusDto.UNKNOWN;
    private static final Integer MARITAL_STATUS_1 = MARITAL_STATUS_DTO_1.getKey();
    private static final String ID_DOC_COUNTRY_1 = "PL";
    private static final String ID_DOC_NUMBER_1 = "QWE123456";
    private static final String ID_DOC_SIGNED_BY_1 = "Prezydent Miasta Radzionkowo";
    private static final Date ID_DOC_SIGN_DATE_1 = parse("2010-04-23 00:00:00.000000");
    private static final Boolean POLITICIAN_1 = false;
    private static final String JOB_FUNCTION_1 = "Konserwator nawierzchni";
    private static final Date BIRTH_DATE_1 = parse("1982-11-17 00:00:00.000000");
    private static final Boolean REAL_BENEFICIARY_1 = false;
    private static final DocTypeDto ID_DOC_TYPE_DTO_1 = DocTypeDto.ID;
    private static final Integer ID_DOC_TYPE_1 = ID_DOC_TYPE_DTO_1.getKey();
    private static final String COUNTRY_1 = "Polska";

    public static final long PERSON_ID_2 = 2L;
    private static final Long EXT_PERSON_ID_2 = 8889L;
    private static final String PESEL_2 = "81011101234";
    private static final String NAME_2 = "Zuzana";
    private static final String LAST_NAME_2 = "Fialová";
    private static final Boolean REPRESENTATIVE_2 = false;
    private static final String ADDRESS_2 = "Banská Bystrica, Sládkovičova 54";
    private static final MaritalStatusDto MARITAL_STATUS_DTO_2 = MaritalStatusDto.FREE;
    private static final Integer MARITAL_STATUS_2 = MARITAL_STATUS_DTO_2.getKey();
    private static final String ID_DOC_COUNTRY_2 = "SK";
    private static final String ID_DOC_NUMBER_2 = "SAA12456";
    private static final String ID_DOC_SIGNED_BY_2 = "Rodne číslo";
    private static final Date ID_DOC_SIGN_DATE_2 = parse("2011-02-11 00:00:00.000000");
    private static final Boolean POLITICIAN_2 = false;
    private static final String JOB_FUNCTION_2 = "Všeobecná zdravotná";
    private static final Date BIRTH_DATE_2 = parse("1981-01-12 00:00:00.000000");
    private static final Boolean REAL_BENEFICIARY_2 = false;
    private static final DocTypeDto ID_DOC_TYPE_DTO_2 = DocTypeDto.ID;
    private static final Integer ID_DOC_TYPE_2 = ID_DOC_TYPE_DTO_2.getKey();
    private static final String COUNTRY_2 = "Słowacja";

    public static List<PersonDto> testPersonDtosList() {
        return asList(personDto1(), personDto2());
    }

    public static Set<PersonDto> testPersonDtos() {
        return new HashSet<>(testPersonDtosList());
    }

    private static HashSet<Address> personAddresses1() {
        return new HashSet<>(asList(address1(), address2()));
    }

    private static HashSet<Address> personAddresses2() {
        return new HashSet<>(singletonList(address3()));
    }

    public static Person person1() {
        return person(PERSON_ID_1, EXT_PERSON_ID_1, PESEL_1, NAME_1, LAST_NAME_1, REPRESENTATIVE_1, ADDRESS_1, MARITAL_STATUS_1, ID_DOC_COUNTRY_1,
                ID_DOC_NUMBER_1, ID_DOC_SIGNED_BY_1, ID_DOC_SIGN_DATE_1, POLITICIAN_1, JOB_FUNCTION_1, BIRTH_DATE_1, REAL_BENEFICIARY_1, ID_DOC_TYPE_1,
                COUNTRY_1, personAddresses1(), firstPersonContactDetails());
    }

    public static Person person2() {
        return person(PERSON_ID_2, EXT_PERSON_ID_2, PESEL_2, NAME_2, LAST_NAME_2, REPRESENTATIVE_2, ADDRESS_2, MARITAL_STATUS_2, ID_DOC_COUNTRY_2,
                ID_DOC_NUMBER_2, ID_DOC_SIGNED_BY_2, ID_DOC_SIGN_DATE_2, POLITICIAN_2, JOB_FUNCTION_2, BIRTH_DATE_2, REAL_BENEFICIARY_2, ID_DOC_TYPE_2,
                COUNTRY_2, personAddresses2(), secondPersonContactDetails());
    }

    public static PersonDto personDto1() {
        return personDto(PERSON_ID_1, EXT_PERSON_ID_1, PESEL_1, NAME_1, LAST_NAME_1, REPRESENTATIVE_1, ADDRESS_1, MARITAL_STATUS_DTO_1, ID_DOC_COUNTRY_1,
                ID_DOC_NUMBER_1, ID_DOC_SIGNED_BY_1, ID_DOC_SIGN_DATE_1, POLITICIAN_1, JOB_FUNCTION_1, BIRTH_DATE_1, REAL_BENEFICIARY_1, ID_DOC_TYPE_DTO_1,
                COUNTRY_1, false);
    }

    public static List<Person> persons() {
        return asList(person1(), person2());
    }

    public static Person createPerson() {
        final Person person = person1();
        person.setId(null);
        person.setPersonId(null);
        person.setAddresses(null);
        person.setContactDetails(null);
        return person;
    }

    public static PersonDto createPersonDto() {
        final PersonDto person = personDto1();
        person.setId(null);
        person.setPersonId(null);
        return person;
    }

    private static PersonDto personDto2() {
        return personDto(PERSON_ID_2, EXT_PERSON_ID_2, PESEL_2, NAME_2, LAST_NAME_2, REPRESENTATIVE_2, ADDRESS_2, MARITAL_STATUS_DTO_2, ID_DOC_COUNTRY_2,
                ID_DOC_NUMBER_2, ID_DOC_SIGNED_BY_2, ID_DOC_SIGN_DATE_2, POLITICIAN_2, JOB_FUNCTION_2, BIRTH_DATE_2, REAL_BENEFICIARY_2, ID_DOC_TYPE_DTO_2,
                COUNTRY_2, true);
    }

    public static Person personToUpdate() {
        return person(PERSON_ID_2, EXT_PERSON_ID_1, PESEL_1, NAME_1, LAST_NAME_1, REPRESENTATIVE_1, ADDRESS_1, MARITAL_STATUS_1, ID_DOC_COUNTRY_1,
                ID_DOC_NUMBER_1, ID_DOC_SIGNED_BY_1, ID_DOC_SIGN_DATE_1, POLITICIAN_1, JOB_FUNCTION_1, BIRTH_DATE_1, REAL_BENEFICIARY_1, ID_DOC_TYPE_1,
                COUNTRY_1, singleton(addressToCreate()), singleton(contactDetailToCreate()));
    }

    private static Person person(final long id, final Long extPersonId, final String pesel, final String firstName, final String lastName,
                                 final Boolean representative, final String address, final Integer maritalStatus, final String idDocCountry,
                                 final String idDocNumber, final String idDocSignedBy, final Date idDocSignDate, final Boolean politician,
                                 final String jobFunction, final Date birthDate, final Boolean realBeneficiary, final Integer idDocType, final String country,
                                 final Set<Address> addresses, final Set<ContactDetail> contactDetails) {
        final Person person = new Person();
        person.setId(id);
        person.setPersonId(extPersonId);
        person.setPesel(pesel);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setRepresentative(representative);
        person.setAddress(address);
        person.setMaritalStatus(maritalStatus);
        person.setIdDocCountry(idDocCountry);
        person.setIdDocNumber(idDocNumber);
        person.setIdDocSignedBy(idDocSignedBy);
        person.setIdDocSignDate(idDocSignDate);
        person.setPolitician(politician);
        person.setJobFunction(jobFunction);
        person.setBirthDate(birthDate);
        person.setRealBeneficiary(realBeneficiary);
        person.setIdDocType(idDocType);
        person.setCountry(country);
        person.setAddresses(addresses);
        person.setContactDetails(contactDetails);
        return person;
    }

    private static PersonDto personDto(final long id, final Long extPersonId, final String pesel, final String firstName, final String lastName,
                                       final Boolean representative, final String address, final MaritalStatusDto maritalStatus, final String idDocCountry,
                                       final String idDocNumber, final String idDocSignedBy, final Date idDocSignDate, final Boolean politician,
                                       final String jobFunction, final Date birthDate, final Boolean realBeneficiary, final DocTypeDto idDocType,
                                       final String country, final Boolean contactPerson) {
        final PersonDto person = new PersonDto();
        person.setId(id);
        person.setPersonId(extPersonId);
        person.setPesel(pesel);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setRepresentative(representative);
        person.setAddress(address);
        person.setMaritalStatus(maritalStatus);
        person.setIdDocCountry(idDocCountry);
        person.setIdDocNumber(idDocNumber);
        person.setIdDocSignedBy(idDocSignedBy);
        person.setIdDocSignDate(idDocSignDate);
        person.setPolitician(politician);
        person.setJobFunction(jobFunction);
        person.setBirthDate(birthDate);
        person.setRealBeneficiary(realBeneficiary);
        person.setIdDocType(idDocType);
        person.setCountry(country);
        person.setContactPerson(contactPerson);
        return person;
    }
}
