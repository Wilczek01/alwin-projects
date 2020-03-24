package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.core.api.postalcode.PostalCodeService;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseType.POSTAL_CODES_PAGE;
import static com.codersteam.alwin.testdata.PostalCodeTestData.ID_1;
import static com.codersteam.alwin.testdata.PostalCodeTestData.ID_100;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
public class PostalCodeResourceTestIT extends WriteTestBase {

    @EJB
    private PostalCodeService postalCodeService;


    @Test
    public void shouldCreatePostalCode() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        assertFalse(postalCodeService.checkIfPostalCodeExists(ID_100));

        // and
        final JsonElement request = request("postalCodes/createPostalCode.json");

        // when
        final int responseCode = checkHttpPostCode("postalCodes", request, loginToken);

        // then
        assertCreated(responseCode);
        assertTrue(postalCodeService.checkIfPostalCodeExists(ID_100));
    }

    @Test
    public void shouldUpdateExistingPostalCode() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("postalCodes/updatePostalCode.json");

        // when
        final int responseCode = checkHttpPatchCode("postalCodes/" + ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final JsonElement response = sendHttpGet("postalCodes", loginToken);

        // then
        assertAccepted(responseCode);

        // and
        assertResponseEquals("postalCodes/pageOfAllPostalCodesUpdated.json", response, POSTAL_CODES_PAGE);
        assertResponseNotEquals("postalCodes/pageOfAllPostalCodes.json", response, POSTAL_CODES_PAGE);
    }


    @Test
    public void shouldDeletePostalCode() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        assertTrue(postalCodeService.checkIfPostalCodeExists(ID_1));

        // when
        final HttpResponse response = checkHttpDelete("postalCodes/" + ID_1, loginToken);

        // and
        commitTrx();

        // then
        assertNoContent(response);

        // and
        assertFalse(postalCodeService.checkIfPostalCodeExists(ID_1));
    }

}
