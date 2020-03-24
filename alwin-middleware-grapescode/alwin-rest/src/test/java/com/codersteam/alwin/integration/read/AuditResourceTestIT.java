package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;

/**
 * @author Adam Stepnowski
 */
public class AuditResourceTestIT extends ReadTestBase {

    @Test
    public void shouldByUnauthorizedToGetOperatorsAssignToCompanyActivities() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("audit/issue/" + ISSUE_ID_1);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldReturnEmptyIssueAuditLogEntries() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("audit/issue/" + NOT_EXISTING_ISSUE_ID, loginToken);

        // then
        assertResponseEquals("audit/emptyIssueAuditLogEntries.json", response);
    }
}
