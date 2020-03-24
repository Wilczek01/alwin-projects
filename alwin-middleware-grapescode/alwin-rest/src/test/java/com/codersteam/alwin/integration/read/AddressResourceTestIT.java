package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseType.ADDRESS_LIST;
import static com.codersteam.alwin.integration.common.ResponseType.ADDRESS_TYPE_LIST;
import static com.codersteam.alwin.testdata.AddressTestData.ADDRESS_ID_1;
import static com.codersteam.alwin.testdata.AddressTestData.NON_EXISTING_ADDRESS_ID;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID;
import static com.codersteam.alwin.testdata.PersonTestData.NON_EXISTING_PERSON_ID;

/**
 * @author Piotr Naroznik
 */
public class AddressResourceTestIT extends ReadTestBase {

    @Test
    public void shouldFindAllAddressesForCompanyById() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("addresses/company/" + COMPANY_ID_1, loginToken);

        // then
        assertResponseEquals("address/findAllAddressesForCompany.json", response, ADDRESS_LIST);
    }

    @Test
    public void shouldNotCreateAddressIfCompanyNotExist() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("address/createNewAddressForCompany.json");

        // when
        final int responseCode = checkHttpPostCode("addresses/company/" + NON_EXISTING_COMPANY_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldFindAllAddressesForPersonById() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("addresses/person/" + COMPANY_ID_1, loginToken);

        // then
        assertResponseEquals("address/findAllAddressesForPerson.json", response, ADDRESS_LIST);
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
    public void shouldNotUpdateAddressWhenImportedFromAida() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("address/updateAddress.json");

        // when
        final HttpResponse response = checkHttpPatch("addresses/" + ADDRESS_ID_1, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("address/addressFromAidaUpdate.json", response);
    }

    @Test
    public void shouldNotUpdateAddressIfAddressNotExist() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("address/notUpdateAddressIfAddressNotExist.json");

        // when
        final int responseCode = checkHttpPatchCode("addresses/" + NON_EXISTING_ADDRESS_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldFindAllAddressesTypes() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("addresses/types", loginToken);

        // then
        assertResponseEquals("address/findAllAddressesTypes.json", response, ADDRESS_TYPE_LIST);
    }
}
