package com.codersteam.alwin.integration.write;

import com.google.gson.JsonElement;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.testdata.AddressTestData.ADDRESS_ID_3;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.PersonTestData.NON_EXISTING_PERSON_ID;

/**
 * @author Piotr Naroznik
 */
@UsingDataSet({"test-permission.json", "test-data.json"})
public class AddressResourceTestIT extends WriteTestBase {

    @Test
    public void shouldCreateNewAddressForCompany() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("address/createNewAddressForCompany.json");

        // when
        final int responseCode = checkHttpPostCode("addresses/company/" + COMPANY_ID_1, request, loginToken);

        // then
        assertCreated(responseCode);
    }

    @Test
    public void shouldCreateNewAddressForPerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("address/createNewAddressForPerson.json");

        // when
        final int responseCode = checkHttpPostCode("addresses/person/" + COMPANY_ID_1, request, loginToken);

        // then
        assertCreated(responseCode);
    }

    @Test
    public void shouldNotCreateAddressIfPersonNotExist() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("address/createNewAddressForPerson.json");

        // when
        final int responseCode = checkHttpPostCode("addresses/person/" + NON_EXISTING_PERSON_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldUpdateAddress() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("address/updateAddress.json");

        // when
        final int responseCode = checkHttpPatchCode("addresses/" + ADDRESS_ID_3, request, loginToken);

        // then
        assertAccepted(responseCode);
    }
}
