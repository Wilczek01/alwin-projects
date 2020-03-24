package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.POSTAL_CODES_PAGE;

/**
 * @author Michał Horowic
 */
public class PostalCodeResourceReadTestIT extends ReadTestBase {

    @Test
    public void shouldNotReturnPostalCodesForInvalidMask() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("postalCodes?mask=12-3x5", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/postalCodesWithInvalidMask.json", response);
    }

    @Test
    public void shouldFindPageOfAllPostalCodes() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("postalCodes", loginToken);

        // then
        assertResponseEquals("postalCodes/pageOfAllPostalCodes.json", response, POSTAL_CODES_PAGE);
    }

    @Test
    public void shouldFindPageOfPostalCodesFilteredByMask() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("postalCodes?mask=12-34x", loginToken);

        // then
        assertResponseEquals("postalCodes/pageOfPostalCodesFilteredByMask.json", response, POSTAL_CODES_PAGE);
    }

    @Test
    public void shouldNotCreatePostalCodesForMissingMask() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/missingPostalCodeMask.json");

        // when
        final HttpResponse response = checkHttpPost("postalCodes", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/missingPostalCodeMask.json", response);
    }

    @Test
    public void shouldNotCreatePostalCodesForMissingOperator() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/missingPostalCodeOperator.json");

        // when
        final HttpResponse response = checkHttpPost("postalCodes", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/missingPostalCodeOperator.json", response);
    }

    @Test
    public void shouldNotCreatePostalCodesForAlreadyExistingMask() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/alreadyExistingPostalCodeMask.json");

        // when
        final HttpResponse response = checkHttpPost("postalCodes", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/alreadyExistingPostalCodeMask.json", response);
    }

    @Test
    public void shouldNotCreatePostalCodesForInvalidMask() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/invalidPostalCodeMask.json");

        // when
        final HttpResponse response = checkHttpPost("postalCodes", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/invalidPostalCodeMask.json", response);
    }

    @Test
    public void shouldNotUpdatePostalCodesForMissingMask() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/missingPostalCodeMask.json");

        // when
        final HttpResponse response = checkHttpPatch("postalCodes/1", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/missingPostalCodeMask.json", response);
    }

    @Test
    public void shouldNotUpdatePostalCodesForMissingOperator() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/missingPostalCodeOperator.json");

        // when
        final HttpResponse response = checkHttpPatch("postalCodes/1", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/missingPostalCodeOperator.json", response);
    }

    @Test
    public void shouldNotUpdatePostalCodesForAlreadyExistingMask() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/alreadyExistingPostalCodeMask.json");

        // when
        final HttpResponse response = checkHttpPatch("postalCodes/1", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/alreadyExistingPostalCodeMask.json", response);
    }

    @Test
    public void shouldNotUpdatePostalCodesForInvalidMask() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/invalidPostalCodeMask.json");

        // when
        final HttpResponse response = checkHttpPatch("postalCodes/1", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/invalidPostalCodeMask.json", response);
    }

    @Test
    public void shouldNotUpdateUnexistingPostalCode() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/updatePostalCode.json");

        // when
        final HttpResponse response = checkHttpPatch("postalCodes/-1", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/unexistingPostalCodeMask.json", response);
    }

    @Test
    public void shouldNotDeleteUnexistingPostalCode() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpDelete("postalCodes/-1", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("postalCodes/unexistingPostalCodeMask.json", response);
    }

    // ---------------------------------------------------------------------------------------
    // authorization checks
    // ---------------------------------------------------------------------------------------

    @Test
    public void shouldNotReturnPostalCodesForNotAuthenticatedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("postalCodes");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnPostalCodesForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // when
        final int responseCode = checkHttpGetCode("postalCodes", loginToken);

        // then
        assertForbidden(responseCode);
    }

}
