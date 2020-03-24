package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.core.api.service.issue.TagService;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseType.TAG_LIST;
import static com.codersteam.alwin.testdata.TagTestData.ADDED_ID;
import static com.codersteam.alwin.testdata.TagTestData.ID_1;
import static com.codersteam.alwin.testdata.TagTestData.ID_3;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
public class TagResourceTestIT extends WriteTestBase {

    @EJB
    private TagService tagService;


    @Test
    public void shouldCreateTag() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        assertFalse(tagService.checkIfTagExists(ADDED_ID));

        // and
        final JsonElement request = request("tag/createTag.json");

        // when
        final int responseCode = checkHttpPostCode("tags", request, loginToken);

        // then
        assertCreated(responseCode);
        assertTrue(tagService.checkIfTagExists(ADDED_ID));
    }

    @Test
    public void shouldUpdateExistingTag() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("tag/updateTag.json");

        // when
        final int responseCode = checkHttpPatchCode("tags/" + ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final JsonElement response = sendHttpGet("tags", loginToken);

        // then
        assertAccepted(responseCode);

        // and
        assertResponseEquals("tag/allTagsUpdated.json", response, TAG_LIST);
        assertResponseNotEquals("tag/allTags.json", response, TAG_LIST);
    }


    @Test
    public void shouldDeleteTag() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        assertTrue(tagService.checkIfTagExists(ID_1));

        // when
        final HttpResponse response = checkHttpDelete("tags/" + ID_1, loginToken);

        // then
        assertNoContent(response);

        // and
        assertFalse(tagService.checkIfTagExists(ID_1));
    }

    @Test
    public void shouldNotDeletePredefinedTag() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        assertTrue(tagService.checkIfTagExists(ID_3));

        // when
        final HttpResponse response = checkHttpDelete("tags/" + ID_3, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/deletePredefinedTag.json", response);

        // and
        assertTrue(tagService.checkIfTagExists(ID_1));
    }
}
