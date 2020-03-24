package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseType.LIST_PHONE_NUMBER_TYPE;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID;
import static com.codersteam.alwin.testdata.ContactDetailTestData.NON_EXISTING_CONTACT_DETAIL_ID;
import static com.codersteam.alwin.testdata.PersonTestData.NON_EXISTING_PERSON_ID;

/**
 * @author Piotr Naroznik
 */
public class ContactDetailResourceTestIT extends ReadTestBase {

    @Test
    public void shouldFindAllContactDetailsForCompany() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("contactDetails/company/" + COMPANY_ID_1, loginToken);

        // then
        assertResponseEquals("contactDetail/findAllContactDetailsForCompany.json", response);
    }

    @Test
    public void shouldNotCreateContactDetailIfCompanyNotExist() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("contactDetail/createNewContactDetailsForCompany.json");

        // when
        final int responseCode = checkHttpPostCode("contactDetails/company/" + NON_EXISTING_COMPANY_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldFindAllContactDetailsForPerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("contactDetails/person/" + COMPANY_ID_1, loginToken);

        // then
        assertResponseEquals("contactDetail/findAllContactDetailsForPerson.json", response);
    }

    @Test
    public void shouldNotCreateContactDetailIfPersonNotExist() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("contactDetail/createNewContactDetailsForPerson.json");

        // when
        final int responseCode = checkHttpPostCode("contactDetails/person/" + NON_EXISTING_PERSON_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldNotUpdateContactDetailIfContactDetailNotExist() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("contactDetail/notUpdateContactDetailIfContactDetailNotExist.json");

        // when
        final int responseCode = checkHttpPatchCode("contactDetails/" + NON_EXISTING_CONTACT_DETAIL_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldFindAllContactDetailsStates() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("contactDetails/states", loginToken);

        // then
        assertResponseEquals("contactDetail/findAllContactDetailsStates.json", response);
    }

    @Test
    public void shouldFindSuggestedPhoneNumbers() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("contactDetails/suggestedPhoneNumbers/" + COMPANY_ID_2, loginToken);

        // then
        assertResponseEquals("contactDetail/findSuggestedPhoneNumbers.json", response, LIST_PHONE_NUMBER_TYPE);
    }

    @Test
    public void shouldNotFindAnySuggestedPhoneNumbers() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("contactDetails/suggestedPhoneNumbers/" + NON_EXISTING_COMPANY_ID, loginToken);

        // then
        assertResponseEquals("contactDetail/suggestedPhoneNumbersEmptyResults.json", response);
    }
}
