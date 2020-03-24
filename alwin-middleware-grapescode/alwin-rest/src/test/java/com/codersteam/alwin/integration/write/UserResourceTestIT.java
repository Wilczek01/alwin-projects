package com.codersteam.alwin.integration.write;

import com.google.gson.JsonElement;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_ID_3;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json"})
public class UserResourceTestIT extends WriteTestBase {

    @Test
    public void shouldCreateNewUser() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("user/newUser.json");

        // when
        final int responseCode = checkHttpPostCode("users", request, loginToken);

        // then
        assertCreated(responseCode);
    }

    @Test
    public void shouldUpdateExistingUser() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("user/updateUser.json");

        // when
        final int responseCode = checkHttpPatchCode("users/" + TEST_USER_ID_3, request, loginToken);

        // then
        assertAccepted(responseCode);
    }
}
