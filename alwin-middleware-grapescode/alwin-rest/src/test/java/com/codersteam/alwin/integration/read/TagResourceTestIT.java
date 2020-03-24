package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.TAG_ICON_LIST;
import static com.codersteam.alwin.integration.common.ResponseType.TAG_LIST;
import static com.codersteam.alwin.testdata.TagTestData.ID_1;
import static com.codersteam.alwin.testdata.TagTestData.NOT_EXISTING_ID;

/**
 * @author Michal Horowic
 */
public class TagResourceTestIT extends ReadTestBase {

    @Test
    public void shouldReturnAllTags() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("tags", loginToken);

        // then
        assertResponseEquals("tag/allTags.json", response, TAG_LIST);
    }

    @Test
    public void shouldNotUpdateNotExistingTag() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("tag/updateTag.json");

        // when
        final HttpResponse response = checkHttpPatch("tags/" + NOT_EXISTING_ID, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/updateNotExistingTag.json", response);
    }

    @Test
    public void shouldNotDeleteNotExistingTag() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final HttpResponse response = checkHttpDelete("tags/" + NOT_EXISTING_ID, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/deleteNotExistingTag.json", response);
    }

    @Test
    public void shouldNotUpdateTagWithoutName() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("tag/tagWithMissingName.json");

        // when
        final HttpResponse response = checkHttpPatch("tags/" + ID_1, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/updateTagWithoutName.json", response);
    }

    @Test
    public void shouldNotUpdateTagWithoutColor() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("tag/tagWithMissingColor.json");

        // when
        final HttpResponse response = checkHttpPatch("tags/" + ID_1, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/updateTagWithoutColor.json", response);
    }

    @Test
    public void shouldNotUpdateTagWithInvalidColor() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("tag/tagWithInvalidColor.json");

        // when
        final HttpResponse response = checkHttpPatch("tags/" + ID_1, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/updateTagWithInvalidColor.json", response);
    }

    @Test
    public void shouldNotCreateTagWithoutName() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("tag/tagWithMissingName.json");

        // when
        final HttpResponse response = checkHttpPost("tags", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/createTagWithoutName.json", response);
    }

    @Test
    public void shouldNotCreateTagWithoutColor() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("tag/tagWithMissingColor.json");

        // when
        final HttpResponse response = checkHttpPost("tags", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/createTagWithoutColor.json", response);
    }

    @Test
    public void shouldNotCreateTagWithInvalidColor() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("tag/tagWithInvalidColor.json");

        // when
        final HttpResponse response = checkHttpPost("tags", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/createTagWithInvalidColor.json", response);
    }

    @Test
    public void shouldReturnAllTagIcons() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("tags/icons", loginToken);

        // then
        assertResponseEquals("tag/allTagIcons.json", response, TAG_ICON_LIST);
    }

    // ---------------------------------------------------------------------------------------
    // authorization checks
    // ---------------------------------------------------------------------------------------

    @Test
    public void shouldNotReturnTagsForNotAuthenticatedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("tags");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnTagsForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // when
        final int responseCode = checkHttpGetCode("tags", loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotCreateTagForNotAuthenticatedUser() throws IOException {
        // given
        final JsonElement request = request("tag/createTag.json");

        // when
        final int responseCode = checkHttpPostCode("tags", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotCreateTagForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // and
        final JsonElement request = request("tag/createTag.json");

        // when
        final int responseCode = checkHttpPostCode("tags", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotUpdateTagForNotAuthenticatedUser() throws IOException {
        // given
        final JsonElement request = request("tag/updateTag.json");

        // when
        final int responseCode = checkHttpPatchCode("tags/" + ID_1, request, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotUpdateTagForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // and
        final JsonElement request = request("tag/updateTag.json");

        // when
        final int responseCode = checkHttpPatchCode("tags/" + ID_1, request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotDeleteTagForNotAuthenticatedUser() throws IOException {
        // when
        final int responseCode = checkHttpDeleteCode("tags/" + ID_1, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotDeleteTagForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // when
        final int responseCode = checkHttpDeleteCode("tags/" + ID_1, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotReturnTagIconsForUnauthorizedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("tags/icons");

        // then
        assertUnauthorized(responseCode);
    }

}
