package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto;
import com.codersteam.alwin.core.api.model.activity.DefaultIssueActivityDto;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService;
import com.codersteam.alwin.core.api.service.activity.DefaultIssueActivityService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.EXECUTED;
import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.PLANNED;
import static com.codersteam.alwin.core.api.model.issue.IssueStateDto.IN_PROGRESS;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.testdata.ActivityTestData.ACTIVITY_ID_100;
import static com.codersteam.alwin.testdata.ActivityTestData.DB_ACTIVITY_ID_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.ACTIVITY_TYPE_ID_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.MODIFIED_TEST_ACTIVITY_TYPE_DTO_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.MODIFIED_TEST_ACTIVITY_TYPE_WITH_ADDED_DETAIL_PROPERTY_DTO_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.MODIFIED_TEST_ACTIVITY_TYPE_WITH_DELETED_DETAIL_PROPERTY_DTO_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.MODIFIED_TEST_ACTIVITY_TYPE_WITH_UPDATED_DETAIL_PROPERTY_DTO_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.MODIFIED_TEST_ACTIVITY_TYPE_WITH_UPDATED_DETAIL_PROPERTY_WITH_DICTIONARY_VALUES_DTO_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_DTO_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_3;
import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.DEFAULT_ISSUE_ACTIVITY_FOR_UPDATE_DTO_1;
import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.DEFAULT_ISSUE_ACTIVITY_ID_1;
import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.UPDATED_DEFAULT_ISSUE_ACTIVITY_DTO_1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.NAME_1;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1;
import static com.codersteam.alwin.testdata.TagTestData.testTagDto3;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json", "test-issue-type-activity-type.json", "test-default-activity.json"})
public class ActivityResourceWriteTestIT extends WriteTestBase {

    @EJB
    private IssueService issueService;

    @EJB
    private ActivityService activityService;

    @EJB
    private ActivityTypeService activityTypeService;

    @EJB
    private DefaultIssueActivityService defaultIssueActivityService;

    @Test
    public void shouldUpdateAndSetExecutedIssueActivityForIssue() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/updateIssueActivity.json");

        // and
        final ActivityDto activityBefore = getActivity(ISSUE_ID_1, ACTIVITY_ID_100);

        // when
        final int responseCode = checkHttpPatchCode("activities/issue/executed", request, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        commitTrx();
        assertEquals(IN_PROGRESS, issueService.findIssue(ISSUE_ID_1).getIssueState());

        // and
        final ActivityDto activityAfter = getActivity(ISSUE_ID_1, ACTIVITY_ID_100);
        assertNotEquals(EXECUTED.getKey(), activityBefore.getState().getKey());
        assertEquals(EXECUTED.getKey(), activityAfter.getState().getKey());
        assertEquals(OPERATOR_ID_1, activityAfter.getOperator().getId());
    }

    @Test
    public void shouldCreateIssueActivityAndRemoveOverdueTagForIssue() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        CURRENT_DATE = "2017-09-28";

        // and
        assertTrue(issueService.findIssue(ISSUE_ID_3).getTags().contains(testTagDto3()));

        // and
        final JsonElement request = request("activity/createIssueActivityWithRemovingOverdueTag.json");

        // when
        final int responseCode = checkHttpPostCode("activities/issue/planned", request, loginToken);
        commitTrx();

        // then
        assertCreated(responseCode);
        assertEquals(IN_PROGRESS, issueService.findIssue(ISSUE_ID_3).getIssueState());

        // and
        assertFalse(issueService.findIssue(ISSUE_ID_3).getTags().contains(testTagDto3()));
    }

    @Test
    public void shouldUpdateIssueActivityAndRemoveOverdueTagForIssue() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        CURRENT_DATE = "2017-08-01";

        // and
        assertTrue(issueService.findIssue(ISSUE_ID_3).getTags().contains(testTagDto3()));

        // and
        final JsonElement request = request("activity/updateIssueActivityWithRemovingOverdueTag.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/issue/executed", request, loginToken);
        commitTrx();

        // then
        assertAccepted(responseCode);

        // and
        assertFalse(issueService.findIssue(ISSUE_ID_3).getTags().contains(testTagDto3()));
    }

    @Test
    public void shouldCreateExecutedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("activity/createIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/issue/executed", request, loginToken);

        // then
        assertCreated(responseCode);
        assertEquals(IN_PROGRESS, issueService.findIssue(ISSUE_ID_2).getIssueState());

        // and
        final ActivityDto newActivity = getActivity(ISSUE_ID_2, DB_ACTIVITY_ID_1);
        assertEquals(EXECUTED.getKey(), newActivity.getState().getKey());
        assertEquals(OPERATOR_ID_1, newActivity.getOperator().getId());
    }

    @Test
    public void shouldCreatePlannedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        CURRENT_DATE = "2017-09-28";

        // and
        final JsonElement request = request("activity/createIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/issue/planned", request, loginToken);

        // then
        assertCreated(responseCode);
        assertEquals(IN_PROGRESS, issueService.findIssue(ISSUE_ID_2).getIssueState());

        // and
        final ActivityDto newActivity = getActivity(ISSUE_ID_2, DB_ACTIVITY_ID_1);
        assertEquals(PLANNED.getKey(), newActivity.getState().getKey());
        assertNull(newActivity.getOperator());
    }

    @Test
    public void shouldCreateManagedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2017-09-28";

        // and
        final JsonElement request = request("activity/createManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed", request, loginToken);

        // then
        assertCreated(responseCode);
    }

    @Test
    public void shouldCreateManagedExecutedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("activity/createManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed/executed", request, loginToken);

        // then
        assertCreated(responseCode);

        // and
        final ActivityDto newActivity = getActivity(ISSUE_ID_2, DB_ACTIVITY_ID_1);
        assertEquals(EXECUTED.getKey(), newActivity.getState().getKey());
        assertEquals(OPERATOR_ID_1, newActivity.getOperator().getId());
    }

    @Test
    public void shouldCreateNotAssignedManagedExecutedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("activity/createNotAssignedManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed/executed", request, loginToken);

        // then
        assertCreated(responseCode);

        // and
        final ActivityDto newActivity = getActivity(ISSUE_ID_2, DB_ACTIVITY_ID_1);
        assertEquals(EXECUTED.getKey(), newActivity.getState().getKey());
        assertNull(newActivity.getOperator());
    }

    @Test
    public void shouldCreateManagedPlannedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2017-09-28";

        // and
        final JsonElement request = request("activity/createManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed/planned", request, loginToken);

        // then
        assertCreated(responseCode);

        // and
        final ActivityDto newActivity = getActivity(ISSUE_ID_2, DB_ACTIVITY_ID_1);
        assertEquals(PLANNED.getKey(), newActivity.getState().getKey());
        assertEquals(OPERATOR_ID_1, newActivity.getOperator().getId());
    }

    @Test
    public void shouldCreateNotAssignedManagedPlannedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2017-09-28";

        // and
        final JsonElement request = request("activity/createNotAssignedManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPostCode("activities/managed/planned", request, loginToken);

        // then
        assertCreated(responseCode);

        // and
        final ActivityDto newActivity = getActivity(ISSUE_ID_2, DB_ACTIVITY_ID_1);
        assertEquals(PLANNED.getKey(), newActivity.getState().getKey());
        assertNull(newActivity.getOperator());
    }

    @Test
    public void shouldUpdateManagedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2017-08-01";

        // and
        final JsonElement request = request("activity/updateManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/managed", request, loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldUpdateAndSetExecutedManagedIssueActivityForIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("activity/updateManagedIssueActivity.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/managed/executed", request, loginToken);

        // then
        assertAccepted(responseCode);
    }

    private ActivityDto getActivity(final Long issueId, final Long activityId) {
        final List<ActivityDto> activities = activityService.findAllIssueActivities(issueId);
        final Optional<ActivityDto> first = activities.stream().filter(activity -> activityId.equals(activity.getId())).findFirst();
        assertTrue(first.isPresent());
        return first.get();
    }

    @Test
    public void shouldUpdateActivityType() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<ActivityTypeDto> activityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);
        // and
        assertThat(TEST_ACTIVITY_TYPE_DTO_1).isEqualToComparingFieldByFieldRecursively(activityTypeByIssueType.get(0));

        // and
        final JsonElement request = request("activity/updateExistingActivityType.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/types/" + ACTIVITY_TYPE_ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<ActivityTypeDto> updatedActivityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);

        // then
        assertAccepted(responseCode);
        assertThat(MODIFIED_TEST_ACTIVITY_TYPE_DTO_1).isEqualToComparingFieldByFieldRecursively(updatedActivityTypeByIssueType.get(0));
    }

    @Test
    public void shouldUpdateActivityTypeWithAddedDetailProperty() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<ActivityTypeDto> activityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);
        // and
        assertThat(TEST_ACTIVITY_TYPE_DTO_1).isEqualToComparingFieldByFieldRecursively(activityTypeByIssueType.get(0));

        // and
        final JsonElement request = request("activity/updateExistingActivityTypeWithAddedDetailProperty.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/types/" + ACTIVITY_TYPE_ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<ActivityTypeDto> updatedActivityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);

        // then
        assertAccepted(responseCode);
        assertThat(MODIFIED_TEST_ACTIVITY_TYPE_WITH_ADDED_DETAIL_PROPERTY_DTO_1).isEqualToComparingFieldByFieldRecursively(updatedActivityTypeByIssueType.get(0));
    }

    @Test
    public void shouldUpdateActivityTypeWithDeletedDetailProperty() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<ActivityTypeDto> activityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);
        // and
        assertThat(TEST_ACTIVITY_TYPE_DTO_1).isEqualToComparingFieldByFieldRecursively(activityTypeByIssueType.get(0));

        // and
        final JsonElement request = request("activity/updateExistingActivityTypeWithDeletedDetailProperty.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/types/" + ACTIVITY_TYPE_ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<ActivityTypeDto> updatedActivityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);

        // then
        assertAccepted(responseCode);
        assertThat(MODIFIED_TEST_ACTIVITY_TYPE_WITH_DELETED_DETAIL_PROPERTY_DTO_1).isEqualToComparingFieldByFieldRecursively(updatedActivityTypeByIssueType.get(0));
    }

    @Test
    public void shouldUpdateActivityTypeWithUpdatedDetailProperty() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<ActivityTypeDto> activityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);
        // and
        assertThat(TEST_ACTIVITY_TYPE_DTO_1).isEqualToComparingFieldByFieldRecursively(activityTypeByIssueType.get(0));

        // and
        final JsonElement request = request("activity/updateExistingActivityTypeWithUpdatedDetailProperty.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/types/" + ACTIVITY_TYPE_ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<ActivityTypeDto> updatedActivityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);

        // then
        assertAccepted(responseCode);
        assertThat(MODIFIED_TEST_ACTIVITY_TYPE_WITH_UPDATED_DETAIL_PROPERTY_DTO_1).isEqualToComparingFieldByFieldRecursively(updatedActivityTypeByIssueType.get(0));
    }

    @Test
    public void shouldUpdateActivityTypeWithUpdatedDetailPropertyWithDictionaryValues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<ActivityTypeDto> activityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);
        // and
        assertThat(TEST_ACTIVITY_TYPE_DTO_1).isEqualToComparingFieldByFieldRecursively(activityTypeByIssueType.get(0));

        // and
        final JsonElement request = request("activity/updateExistingActivityTypeWithUpdatedDetailPropertyWithDictionaryValues.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/types/" + ACTIVITY_TYPE_ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<ActivityTypeDto> updatedActivityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);

        // then
        assertAccepted(responseCode);
        assertThat(MODIFIED_TEST_ACTIVITY_TYPE_WITH_UPDATED_DETAIL_PROPERTY_WITH_DICTIONARY_VALUES_DTO_1).isEqualToComparingFieldByFieldRecursively(updatedActivityTypeByIssueType.get(0));
    }

    @Test
    public void shouldNotUpdateActivityTypeWithNotExistingDetailProperty() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<ActivityTypeDto> activityTypeByIssueType = activityTypeService.findActivityTypes(NAME_1, null);
        // and
        assertThat(TEST_ACTIVITY_TYPE_DTO_1).isEqualToComparingFieldByFieldRecursively(activityTypeByIssueType.get(0));

        // and
        final JsonElement request = request("activity/updateExistingActivityTypeWithUpdatedDetailPropertyWithNotExistingDictionaryValues.json");

        // when
        final HttpResponse response = checkHttpPatch("activities/types/" + ACTIVITY_TYPE_ID_1, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("activity/updateExistingActivityTypeWithUpdatedDetailPropertyWithNotExistingDictionaryValues.json", response);
    }

    @Test
    public void shouldUpdateDefaultActivity() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<DefaultIssueActivityDto> defaultIssueActivities = defaultIssueActivityService.findDefaultIssueActivities(ISSUE_TYPE_ID_1);
        // and
        assertThat(DEFAULT_ISSUE_ACTIVITY_FOR_UPDATE_DTO_1).isEqualToComparingFieldByFieldRecursively(defaultIssueActivities.get(0));

        // and
        final JsonElement request = request("activity/updateExistingDefaultIssueActivity.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/default/" + DEFAULT_ISSUE_ACTIVITY_ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<DefaultIssueActivityDto> modifiedDefaultIssueActivities = defaultIssueActivityService.findDefaultIssueActivities(ISSUE_TYPE_ID_1);

        // then
        assertAccepted(responseCode);
        assertThat(UPDATED_DEFAULT_ISSUE_ACTIVITY_DTO_1).isEqualToComparingFieldByFieldRecursively(modifiedDefaultIssueActivities.get(1));
    }

    @Test
    public void shouldNotUpdateActivityWhenDeclarationDateIsBeforePreviousWorkingDay() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2017-08-01";

        // and
        final JsonElement request = request("activity/updateActivityWithDeclarationDateBeforePreviousWorkingDay.json");

        // when
        final int responseCode = checkHttpPatchCode("activities/managed", request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

}
