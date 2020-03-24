package com.codersteam.alwin.integration.read;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.ACTIVITY_PAGE;
import static com.codersteam.alwin.integration.common.ResponseType.ACTIVITY_TYPE_BY_STATE;
import static com.codersteam.alwin.integration.common.ResponseType.ACTIVITY_TYPE_LIST;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.jpa.type.ActivityState.EXECUTED;
import static com.codersteam.alwin.jpa.type.ActivityState.PLANNED;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_20;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;

/**
 * @author Tomasz Sliwinski
 */
public class ActivityResourceReadTestIT extends ReadTestBase {

    @Test
    public void shouldNotUpdateAndSetExecutedIssueActivityIfValidationFailedForIncorrectActivityId() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateIssueActivityWithIncorrectActivityId.json");

        // when
        final HttpResponse response = checkHttpPatch("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/updateAndSetExecutedIssueActivityIncorrectActivityId.json", response);
    }

    @Test
    public void shouldNotUpdateAndSetExecutedIssueActivityIfValidationFailedForIncorrectActivityState() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateIssueActivityWithIncorrectActivityState.json");

        // when
        final HttpResponse response = checkHttpPatch("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/updateAndSetExecutedIssueActivityIncorrectActivityState.json", response);
    }

    @Test
    public void shouldNotUpdateAndSetExecutedIssueActivityIfValidationFailedForIncorrectActivityType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateIssueActivityWithIncorrectActivityType.json");

        // when
        final HttpResponse response = checkHttpPatch("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/updateAndSetExecutedIssueActivityIncorrectActivityType.json", response);
    }

    @Test
    public void shouldNotCreateIssueActivityIfValidationFailedForPlannedDateAfterNext3Days() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithPlannedDateAfterNext7Days.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/planned", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createIssueActivityPlannedDateAfterNext7Days.json", response);
    }

    @Test
    public void shouldNotCreateExecutedIssueActivityIfValidationFailedForIncorrectPlannedDate() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithIncorrectPlannedDate.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createExecutedIssueActivityIncorrectPlannedDate.json", response);
    }

    @Test
    public void shouldNotCreatePlannedIssueActivityIfValidationFailedForIncorrectPlannedDate() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithIncorrectPlannedDate.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/planned", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createPlannedIssueActivityIncorrectPlannedDate.json", response);
    }

    @Test
    public void shouldReturnAllActivityTypes() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/types", loginToken);

        // then
        assertResponseEquals("activity/allActivityTypes.json", response, ACTIVITY_TYPE_LIST);
    }

    @Test
    public void shouldReturnAllActivityTypesFilteredByMayHaveDeclarations() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/types?mayHaveDeclarations=true", loginToken);

        // then
        assertResponseEquals("activity/allActivityTypesFilteredByMayHaveDeclarations.json", response, ACTIVITY_TYPE_LIST);
    }

    @Test
    public void shouldReturnAllActivityTypesGroupedByState() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/types/groupedByState", loginToken);

        // then
        assertResponseEquals("activity/allActivityTypesGroupedByState.json", response, ACTIVITY_TYPE_BY_STATE);
    }

    @Test
    public void shouldReturnActivityTypesByIssueType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/types?issueType=" + IssueTypeName.PHONE_DEBT_COLLECTION_1.name(), loginToken);

        // then
        assertResponseEquals("activity/activityTypesForPhoneDebtCollection1.json", response, ACTIVITY_TYPE_LIST);
    }

    @Test
    public void shouldReturnActivityTypesGroupedByStateForIssueType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/types/groupedByState?issueType=" + IssueTypeName.PHONE_DEBT_COLLECTION_1.name(), loginToken);

        // then
        assertResponseEquals("activity/activityTypesGroupedByStateForPhoneDebtCollection1.json", response, ACTIVITY_TYPE_BY_STATE);
    }

    @Test
    public void shouldNotReturnActivityTypesForIllegalIssueType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final HttpResponse response = checkHttpGet("activities/types?issueType=W_R_O_N_G", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/activityTypesForIllegalIssueType.json", response);
    }

    @Test
    public void shouldReturnActivityStatues() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/statuses", loginToken);

        // then
        assertResponseEquals("activity/activityStatuses.json", response);
    }

    @Test
    public void shouldNotCreateExecutedIssueActivityIfValidationFailedForValue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithWrongPropertyValue.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createExecutedIssueActivityInvalidValue.json", response);
    }

    @Test
    public void shouldNotCreatePlannedIssueActivityIfValidationFailedForValue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithWrongPropertyValue.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/planned", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createPlannedIssueActivityInvalidValue.json", response);
    }

    @Test
    public void shouldNotCreateExecutedIssueActivityIfValidationFailedForType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithWrongPropertyType.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createExecutedIssueActivityInvalidType.json", response);
    }

    @Test
    public void shouldNotCreatePlannedIssueActivityIfValidationFailedForType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithWrongPropertyType.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/planned", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createPlannedIssueActivityInvalidType.json", response);
    }

    @Test
    public void shouldNotCreateExecutedIssueActivityIfValidationFailedForMissingValue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithMissingPropertyValue.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createExecutedIssueActivityMissingValue.json", response);
    }

    @Test
    public void shouldNotCreatePlannedIssueActivityIfValidationFailedForMissingValue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithMissingPropertyValue.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/planned", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createPlannedIssueActivityMissingValue.json", response);
    }

    @Test
    public void shouldNotCreateIssueActivityWithIllegalActivityType() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        CURRENT_DATE = "2017-09-28";

        // and
        final JsonElement request = request("activity/createIssueActivityWithIllegalType.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/createIssueActivityWithIllegalType.json", response);
    }

    @Test
    public void shouldReturnPaginatedIssueActivities() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/issue/" + ISSUE_ID_1 + "?firstResult=0&maxResults=5", loginToken);

        // then
        assertResponseEquals("activity/paginatedIssueActivities.json", response, ACTIVITY_PAGE);
    }

    @Test
    public void shouldReturnPaginatedIssueActivitiesOrderedByActivityTypeDescending() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/issue/" + ISSUE_ID_1 + "?firstResult=0&maxResults=5&sort=-name", loginToken);

        // then
        assertResponseEquals("activity/paginatedIssueActivitiesOrderedByActivityType.json", response, ACTIVITY_PAGE);
    }

    @Test
    public void shouldReturnPaginatedIssueActivitiesOrderedByCreationDateDescending() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/issue/" + ISSUE_ID_1 + "?firstResult=0&maxResults=5&sort=-creationDate", loginToken);

        // then
        assertResponseEquals("activity/paginatedIssueActivitiesOrderedByCreationDate.json", response, ACTIVITY_PAGE);
    }

    @Test
    public void shouldReturnPaginatedIssueActivitiesByExecutedState() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/issue/" + ISSUE_ID_20 + "?firstResult=0&maxResults=5&state=" + EXECUTED.name(), loginToken);

        // then
        assertResponseEquals("activity/paginatedIssueActivitiesByExecutedState.json", response, ACTIVITY_PAGE);
    }

    @Test
    public void shouldReturnPaginatedIssueActivitiesByPlannedState() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/issue/" + ISSUE_ID_1 + "?firstResult=0&maxResults=5&state=" + PLANNED.name(), loginToken);

        // then
        assertResponseEquals("activity/paginatedIssueActivitiesByPlannedState.json", response, ACTIVITY_PAGE);
    }

    @Test
    public void shouldReturnEmptyIssueActivitiesForNotExistingIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final HttpResponse response = checkHttpGet("activities/issue/" + NOT_EXISTING_ISSUE_ID, loginToken);

        // then
        assertNotFound(response);
        assertResponseEquals("activity/notExistingIssue.json", response);
    }

    // ---------------------------------------------------------------------------------------
    // authorization checks
    // ---------------------------------------------------------------------------------------

    @Test
    public void shouldNotReturnAllActivityTypesForUnauthorizedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("activities/types");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnIssueActivitiesForUnauthorizedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("activities/issue/" + ISSUE_ID_1);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotUpdateAndSetExecutedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = null;

        // and
        final JsonElement request = request("activity/updateIssueActivity.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/issue/executed", request, loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotCreateExecutedIssueActivityForUnauthorizedUser() throws IOException {
        // given
        final JsonElement request = request("activity/createIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/issue/executed", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotCreatePlannedIssueActivityForUnauthorizedUser() throws IOException {
        // given
        final JsonElement request = request("activity/createIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/issue/planned", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnActivityStatuesForUnauthorizedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("activities/statuses");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotUpdateManagedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/managed", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotUpdateAndSetExecutedManagedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/managed/executed", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotCreateManagedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotCreateManagedExecutedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed/executed", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotCreateManagedPlannedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed/planned", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotReturnDefaultActivitiesForUnauthorizedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("activities/default");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldReturnDefaultActivities() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/default", loginToken);

        // then
        assertResponseEquals("activity/allDefaultActivities.json", response);
    }

    @Test
    public void shouldNotReturnActivityDetailPropertiesForUnauthorizedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("activities/properties");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldReturnAllActivityDetailProperties() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/properties", loginToken);

        // then
        assertResponseEquals("activity/allActivityDetailProperties.json", response);
    }

    @Test
    public void shouldFindOldestPlannedActivityForIssue() throws IOException {
        // given
        CURRENT_DATE = "2017-08-22";
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("activities/issue/" + ISSUE_ID_2 + "/oldest", loginToken);

        // then
        assertResponseEquals("activity/oldestPlannedIssueActivity.json", response, ActivityDto.class);
    }

    @Test
    public void shouldNotFindOldestPlannedActivityForIssue() throws IOException {
        // given
        CURRENT_DATE = "2017-07-13";
        final String loginToken = loggedInAdmin();

        // when
        final int responseCode = checkHttpGetCode("activities/issue/" + ISSUE_ID_2 + "/oldest", loginToken);

        // then
        assertNoContent(responseCode);
    }

    @Test
    public void shouldNotCreateIssueActivityWithNegativePLNDeclaredPayment() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithNegativeDeclarationPLNAmount.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/issueActivityWithNegativeDeclarationAmount.json", response);
    }

    @Test
    public void shouldNotCreateIssueActivityWithNegativeEURDeclaredPayment() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivityWithNegativeDeclarationEURAmount.json");

        // when
        final HttpResponse response = checkHttpPost("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/issueActivityWithNegativeDeclarationAmount.json", response);
    }

    @Test
    public void shouldNotUpdateIssueActivityWithNegativePLNDeclaredPayment() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateIssueActivityWithNegativeDeclarationPLNAmount.json");

        // when
        final HttpResponse response = checkHttpPatch("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/issueActivityWithNegativeDeclarationAmount.json", response);
    }

    @Test
    public void shouldNotUpdateIssueActivityWithNegativeEURDeclaredPayment() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateIssueActivityWithNegativeDeclarationEURAmount.json");

        // when
        final HttpResponse response = checkHttpPatch("activities/issue/executed", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/issueActivityWithNegativeDeclarationAmount.json", response);
    }
}
