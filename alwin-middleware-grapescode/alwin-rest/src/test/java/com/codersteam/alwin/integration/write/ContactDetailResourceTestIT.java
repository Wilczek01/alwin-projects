package com.codersteam.alwin.integration.write;

import com.google.gson.JsonElement;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.ContactDetailTestData.CONTACT_DETAIL_ID_2;

/**
 * @author Piotr Naroznik
 */
@UsingDataSet({"test-permission.json", "test-data.json"})
public class ContactDetailResourceTestIT extends WriteTestBase {

    @Test
    public void shouldCreateNewContactDetailsForCompany() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final JsonElement request = request("contactDetail/createNewContactDetailsForCompany.json");

        // when
        final int responseCode = checkHttpPostCode("contactDetails/company/" + COMPANY_ID_1, request, loginToken);

        // then
        assertCreated(responseCode);
    }

    @Test
    public void shouldCreateNewContactDetailsForPerson() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final JsonElement request = request("contactDetail/createNewContactDetailsForPerson.json");

        // when
        final int responseCode = checkHttpPostCode("contactDetails/person/" + COMPANY_ID_1, request, loginToken);

        // then
        assertCreated(responseCode);
    }

    @Test
    public void shouldUpdateContactDetail() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final JsonElement request = request("contactDetail/updateContactDetail.json");

        // when
        final int responseCode = checkHttpPatchCode("contactDetails/" + CONTACT_DETAIL_ID_2, request, loginToken);

        assertAccepted(responseCode);
    }

    @Test
    public void shouldFindAllContactDetailsTypes() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("contactDetails/types", loginToken);

        // then
        assertResponseEquals("contactDetail/findAllContactDetailsTypes.json", response);
    }
}
