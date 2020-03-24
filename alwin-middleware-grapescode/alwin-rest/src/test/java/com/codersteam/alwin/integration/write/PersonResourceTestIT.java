package com.codersteam.alwin.integration.write;

import com.google.gson.JsonElement;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_2;
import static com.codersteam.alwin.testdata.PersonTestData.PERSON_ID_1;
import static com.codersteam.alwin.testdata.PersonTestData.PERSON_ID_2;

/**
 * @author Piotr Naroznik
 */
@UsingDataSet({"test-permission.json", "test-data.json"})
public class PersonResourceTestIT extends WriteTestBase {

    @Test
    public void shouldSetContactPerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("person/setContactPerson.json");

        // when
        final int responseCode = checkHttpPatchCode("persons/" + PERSON_ID_1 + "/company/" + COMPANY_ID_2, request, loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldUnsetContactPerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("person/unsetContactPerson.json");

        // when
        final int responseCode = checkHttpPatchCode("persons/" + PERSON_ID_2 + "/company/" + COMPANY_ID_2, request, loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldCreatePerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("person/createPerson.json");

        // when
        final int responseCode = checkHttpPostCode("persons/company/" + COMPANY_ID_2, request, loginToken);

        // then
        assertCreated(responseCode);
    }
}
