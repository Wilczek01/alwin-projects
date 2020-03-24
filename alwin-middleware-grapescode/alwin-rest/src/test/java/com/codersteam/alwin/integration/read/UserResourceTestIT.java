package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

import static com.codersteam.alwin.core.api.service.auth.JwtService.BEARER_HEADER_PREFIX;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.testdata.UserTestData.TEST_NOT_EXISTING_USER_ID;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_ID_3;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Michal Horowic
 */
public class UserResourceTestIT extends ReadTestBase {

    @Test
    public void shouldSuccessfullyLogIn() throws IOException {
        // given
        final JsonElement request = request("user/validLogin.json");

        // when
        final HttpResponse response = sendHttpPostForResponse("users/login", request);

        // then
        final String token = extractHeader(HttpHeaders.AUTHORIZATION, response);
        assertNotNull(token);
        assertTrue(token.startsWith(BEARER_HEADER_PREFIX));

        // and
        assertResponseEquals("user/login.json", response);
    }

    @Test
    public void shouldNotLogInWithWrongPassword() throws IOException {
        // given
        final JsonElement request = request("user/invalidLogin.json");

        // when
        final int responseCode = checkHttpPostCode("users/login", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetFirstPageOfAllUsers() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("users?firstResult=0&maxResults=2", loginToken);

        // then
        assertResponseEquals("user/usersFirstPage.json", response);
    }

    @Test
    public void shouldGetSecondPageOfAllUsers() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("users?firstResult=2&maxResults=2", loginToken);

        // then
        assertResponseEquals("user/usersSecondPage.json", response);
    }

    @Test
    public void shouldGetPageOfUsersFilteredByName() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("users?firstResult=0&maxResults=2&searchText=Adam%20Mick", loginToken);

        // then
        assertResponseEquals("user/usersFilteredByName.json", response);
    }

    @Test
    public void shouldGetPageOfUsersFilteredByLogin() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("users?firstResult=0&maxResults=2&searchText=jslowacki", loginToken);

        // then
        assertResponseEquals("user/usersFilteredByLogin.json", response);
    }

    @Test
    public void shouldNotBeAllowedToGetAllUsers() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("users?firstResult=0&maxResults=2", loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotBeAllowedToCreateUser() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("user/newUser.json");

        // when
        final int responseCode = checkHttpPostCode("users", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotBeAllowedToUpdateUser() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("user/updateUser.json");

        // when
        final int responseCode = checkHttpPatchCode("users/" + TEST_USER_ID_3, request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldValidateUpdatedUserId() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("user/updateUserWithNotExistingId.json");

        // when
        final int responseCode = checkHttpPatchCode("users/" + TEST_NOT_EXISTING_USER_ID, request, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldValidateMessedUpUpdatedUserId() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("user/updateUser.json");

        // when
        final int responseCode = checkHttpPatchCode("users/" + TEST_NOT_EXISTING_USER_ID, request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldGetUserWithOperatorsById() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("users/" + TEST_USER_ID_3, loginToken);

        // then
        assertResponseEquals("user/getUser.json", response);
    }

    @Test
    public void shouldNotBeAllowedToGetUserWithOperators() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("users/" + TEST_USER_ID_3, loginToken);

        // then
        assertForbidden(responseCode);
    }
}
