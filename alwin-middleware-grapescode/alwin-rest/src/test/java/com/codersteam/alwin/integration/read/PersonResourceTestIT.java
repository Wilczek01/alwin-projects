package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.DOC_TYPE_LIST;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID;
import static com.codersteam.alwin.testdata.PersonTestData.NON_EXISTING_PERSON_ID;

/**
 * @author Piotr Naroznik
 */
public class PersonResourceTestIT extends ReadTestBase {

    @Test
    public void shouldNotSetContactPersonIfPersonNotExist() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("person/setContactPerson.json");

        // when
        final int responseCode = checkHttpPatchCode("persons/" + NON_EXISTING_PERSON_ID + "/company/" + COMPANY_ID_2, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldReturnErrorIfCompanyNotExistsInCreatePerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("person/createPerson.json");

        // when
        final int responseCode = checkHttpPostCode("persons/company/" + NON_EXISTING_COMPANY_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldReturnValidationErrorsWhenMissingDataInCreatePerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("person/invalidCreatePerson.json");

        // when
        final HttpResponse response = checkHttpPost("persons/company/" + COMPANY_ID_2, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("person/invalidCreatePerson.json", response);
    }

    @Test
    public void shouldReturnMaritalStatuses() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("persons/maritalStatuses", loginToken);

        // then
        assertResponseEquals("person/maritalStatuses.json", response);
    }

    @Test
    public void shouldReturnDocumentTypes() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("persons/documentTypes", loginToken);

        // then
        assertResponseEquals("person/documentTypes.json", response, DOC_TYPE_LIST);
    }

    @Test
    public void shouldReturnCompanyPersons() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("persons/company/" + COMPANY_ID_2, loginToken);

        // then
        assertResponseEquals("person/companyPersons.json", response);
    }

    @Test
    public void shouldByUnauthorizedToGetCompanyPersons() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("persons/company/" + COMPANY_ID_2);

        // then
        assertUnauthorized(responseCode);
    }
}
