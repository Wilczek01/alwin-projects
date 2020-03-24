package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssuePriorityDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.core.api.model.issue.IssueTypeDto;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseType.ISSUE_PAGE;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_3;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_12;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_18;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_3;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_6;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_8;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDtoPhoneDebtCollection1;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json"})
public class OperatorResourceWriteTestIT extends WriteTestBase {

    @EJB
    private IssueService issueService;

    @EJB
    private ActivityService activityService;


    @Test
    public void shouldAssignOperatorToIssue() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("operator/managed/assignOperator2.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/" + testOperator2().getId() + "/assignment", request, loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldRemoveAssignmentFromIssue() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("operator/managed/assignOperator2.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/unassignment", request, loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldRemoveAssignmentFromAllIssuesFilteredByExtCompanyIds() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=30", loginToken);
        assertResponseEquals("issue/assignment/issueRemoveAssignmentBeforeUpdateFilteredByExtCompanyIds.json", currentIssues, ISSUE_PAGE);

        // and
        final JsonElement request = request("operator/managed/assignOperator1.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/unassignment?updateAll=true" +
                "&extCompanyId=" + EXT_COMPANY_ID_2 +
                "&extCompanyId=" + EXT_COMPANY_ID_3 +
                "&extCompanyId=" + EXT_COMPANY_ID_8 +
                "&extCompanyId=" + EXT_COMPANY_ID_12 +
                "&extCompanyId=" + EXT_COMPANY_ID_18 +
                "&extCompanyId=" + EXT_COMPANY_ID_6, request, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=30", loginToken);
        assertResponseEquals("issue/assignment/issueRemoveAssignmentAfterUpdateFilteredByExtCompanyIds.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldAssignOperatorToAllFilteredIssue() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpPatchCode("operators/" + testOperator2().getId() + "/assignment?updateAll=true&operatorId="
                + testOperator1().getId(), loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldAssignOperatorToAllIssuesFilteredByExtCompanyIds() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=30", loginToken);
        assertResponseEquals("issue/assignment/issueAssignmentBeforeUpdateFilteredByExtCompanyIds.json", currentIssues, ISSUE_PAGE);

        // when
        final int responseCode = checkHttpPatchCode("operators/" + testOperator2().getId() + "/assignment?updateAll=true" +
                "&extCompanyId=" + EXT_COMPANY_ID_2 +
                "&extCompanyId=" + EXT_COMPANY_ID_3 +
                "&extCompanyId=" + EXT_COMPANY_ID_8 +
                "&extCompanyId=" + EXT_COMPANY_ID_12 +
                "&extCompanyId=" + EXT_COMPANY_ID_18 +
                "&extCompanyId=" + EXT_COMPANY_ID_6, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=30", loginToken);
        assertResponseEquals("issue/assignment/issueAssignmentAfterUpdateFilteredByExtCompanyIds.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldAssignOperatorToAllOpenIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final Long operatorToAssignId = testOperator2().getId();

        // and there are issues not assigned to testOperator2
        final List<IssueDto> allActiveIssuesBeforeAssignment = issueService.findAllActiveIssues();
        assertAtLeastOneIssueNotAssignedTo(operatorToAssignId, allActiveIssuesBeforeAssignment);

        // and there are activities from active issues not assigned to testOperator2
        assertAtLeastOneIssueActivityNotAssignedTo(operatorToAssignId, allActiveIssuesBeforeAssignment);

        // when assign all active issues to testOperator2
        final int responseCode = checkHttpPatchCode("operators/" + operatorToAssignId + "/assignment?updateAll=true", loginToken);
        commitTrx();

        // then
        assertAccepted(responseCode);

        // and all issues with of PHONE_DEBT_COLLECTION_1 type are assigned to testOperator2 (this is due to PHONE_DEBT_COLLECTOR config in test DB)
        final List<IssueDto> allActiveIssuesPhoneDebtCollection1 = findAllActiveIssuesOfType(issueTypeDtoPhoneDebtCollection1());
        assertAllIssuesAssignedTo(operatorToAssignId, allActiveIssuesPhoneDebtCollection1);

        // and all activities from issues of PHONE_DEBT_COLLECTION_1 type are assigned to testOperator2
        assertAllIssueOpenActivitiesAssignedTo(operatorToAssignId, allActiveIssuesPhoneDebtCollection1);

        // and issue 3 (PHONE_DEBT_COLLECTION_2) is still assigned to testOperator1
        final IssueDto phoneDebtCollection2Issue = issueService.findIssue(ISSUE_ID_3);
        assertEquals(testOperator1().getId(), phoneDebtCollection2Issue.getAssignee().getId());

        // and issue 3 activities (PHONE_DEBT_COLLECTION_2) are still assigned to testOperator1
        activityService.findAllIssueActivities(ISSUE_ID_3).forEach(activity -> {
            assertEquals(testOperator1().getId(), activity.getOperator().getId());
        });
    }

    @Test
    public void shouldAssignOperatorToAllIssuesWithHighPriority() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final Long operatorToAssignId = testOperator2().getId();

        // and there are issues not assigned to testOperator2
        final List<IssueDto> allActiveIssuesBeforeAssignment = issueService.findAllActiveIssues();
        assertAtLeastOneIssueActivityNotAssignedTo(operatorToAssignId, allActiveIssuesBeforeAssignment);

        // and there are issues with high priority issues not assigned to testOperator2
        assertAtLeastOneIssueActivityNotAssignedTo(operatorToAssignId, allActiveIssuesBeforeAssignment);

        // when assign all high priority issues to testOperator2
        final int responseCode = checkHttpPatchCode("operators/" + operatorToAssignId + "/assignment?updateAll=true&priorityKey=" +
                IssuePriorityDto.HIGH.getKey(), loginToken);
        commitTrx();

        // then
        assertAccepted(responseCode);

        // and all issues with of PHONE_DEBT_COLLECTION_1 type are assigned to testOperator2 (this is due to PHONE_DEBT_COLLECTOR config in test DB)
        final List<IssueDto> allHighPriorityActiveIssuesPhoneDebtCollection1 = findAllHighPriorityActiveIssuesOfType(issueTypeDtoPhoneDebtCollection1());
        assertAllIssuesAssignedTo(operatorToAssignId, allHighPriorityActiveIssuesPhoneDebtCollection1);

        // and all activities from issues of PHONE_DEBT_COLLECTION_1 type are assigned to testOperator2
        assertAllIssueOpenActivitiesAssignedTo(operatorToAssignId, allHighPriorityActiveIssuesPhoneDebtCollection1);

        // and issue 3 (PHONE_DEBT_COLLECTION_2) is still assigned to testOperator1
        final IssueDto phoneDebtCollection2Issue = issueService.findIssue(ISSUE_ID_3);
        assertEquals(testOperator1().getId(), phoneDebtCollection2Issue.getAssignee().getId());

        // and issue 3 activities (PHONE_DEBT_COLLECTION_2) are still assigned to testOperator1
        activityService.findAllIssueActivities(ISSUE_ID_3).forEach(activity -> {
            assertEquals(testOperator1().getId(), activity.getOperator().getId());
        });
    }

    @Test
    public void shouldAssignOperatorToAllNewIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final Long operatorToAssignId = testOperator2().getId();

        // and there are issues not assigned to testOperator2
        final List<IssueDto> allNewIssuesBeforeAssignment = findAllIssuesWithState(IssueStateDto.NEW);
        assertAtLeastOneIssueNotAssignedTo(operatorToAssignId, allNewIssuesBeforeAssignment);

        // and there are activities from active issues not assigned to testOperator2
        assertAtLeastOneIssueActivityNotAssignedTo(operatorToAssignId, allNewIssuesBeforeAssignment);

        // when assign all NEW issues to testOperator2
        final int responseCode = checkHttpPatchCode("operators/" + testOperator2().getId() + "/assignment?updateAll=true&state=" +
                IssueStateDto.NEW.getKey(), loginToken);
        commitTrx();

        // then
        assertAccepted(responseCode);

        // and all NEW issues of PHONE_DEBT_COLLECTION_1 type are assigned to testOperator2 (this is due to PHONE_DEBT_COLLECTOR config in test DB)
        final List<IssueDto> allNewIssuesPhoneDebtCollection1 = findAllNewIssuesOfType(issueTypeDtoPhoneDebtCollection1());
        assertAllIssuesAssignedTo(operatorToAssignId, allNewIssuesPhoneDebtCollection1);

        // and all activities from issues of PHONE_DEBT_COLLECTION_1 type are assigned to testOperator2
        assertAllIssueOpenActivitiesAssignedTo(operatorToAssignId, allNewIssuesPhoneDebtCollection1);

        // and all IN_PROGRESS issues are not assigned to testOperator2
        final List<IssueDto> allInProgressIssues = findAllIssuesWithState(IssueStateDto.IN_PROGRESS);
        assertAllIssuesNotAssignedTo(operatorToAssignId, allInProgressIssues);

        // and all IN_PROGRESS issue activities are not assigned to testOperator2
        assertAllIssueActivitiesNotAssignedTo(operatorToAssignId, allInProgressIssues);
    }

    @Test
    public void shouldResetPasswordForOperator() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("operator/changePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/" + OPERATOR_ID_1, request, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement loginRequest = request("user/changedLogin.json");

        // and
        final HttpResponse response = sendHttpPostForResponse("users/login", loginRequest);

        // and
        assertResponseEquals("operator/changePassword.json", response);
    }

    @Test
    public void shouldChangePasswordForOperator() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement resetRequest = request("operator/changePassword.json");

        // and
        assertAccepted(checkHttpPatchCode("operators/password/" + OPERATOR_ID_1, resetRequest, loginToken));

        // and
        final JsonElement request = request("operator/secondChangePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/change", request, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement loginRequest = request("user/secondChangedLogin.json");

        // and
        final HttpResponse response = sendHttpPostForResponse("users/login", loginRequest);

        // and
        assertResponseEquals("operator/secondChangePassword.json", response);
    }

    // assertions

    private void assertAllIssueActivitiesNotAssignedTo(final Long operatorToAssignId, final List<IssueDto> issues) {
        issues.forEach(issue -> {
            assertThat(activityService.findAllIssueActivities(issue.getId())).allMatch(activity -> activity.getOperator() == null ||
                    !operatorToAssignId.equals(activity.getOperator().getId()));
        });
    }

    private void assertAllIssuesNotAssignedTo(final Long operatorId, final List<IssueDto> issues) {
        assertThat(issues).allMatch(issue -> issue.getAssignee() == null || !operatorId.equals(issue.getAssignee().getId()));
    }

    private List<IssueDto> findAllIssuesWithState(final IssueStateDto issueState) {
        return issueService.findAllActiveIssues().stream().filter(issue -> issueState.getKey().equals(issue.getIssueState().getKey())).collect(toList());
    }

    private void assertAllIssueOpenActivitiesAssignedTo(final Long operatorId, final List<IssueDto> issues) {
        issues.forEach(issue -> {
            final List<ActivityDto> allIssueActivities = activityService.findAllIssueActivities(issue.getId());
            final List<ActivityDto> openActivities = allIssueActivities.stream().filter(activity -> !activity.getState().isClosed()).collect(toList());
            assertThat(openActivities).allMatch(activity -> operatorId.equals(activity.getOperator().getId()));
        });
    }

    private void assertAllIssuesAssignedTo(final Long operatorId, final List<IssueDto> issues) {
        assertThat(issues).allMatch(issue -> operatorId.equals(issue.getAssignee().getId()));
    }

    private void assertAtLeastOneIssueActivityNotAssignedTo(final Long operatorId, final List<IssueDto> issues) {
        final int[] notAssignedToGivenOperatorCount = {0};
        issues.forEach(issue -> {
            final List<ActivityDto> allIssueActivities = activityService.findAllIssueActivities(issue.getId());
            notAssignedToGivenOperatorCount[0] += allIssueActivities.stream().filter(activity -> activity.getOperator() == null || !operatorId.equals
                    (activity.getOperator().getId())).count();
        });
        assertTrue(notAssignedToGivenOperatorCount[0] != 0);
    }

    private List<IssueDto> findAllHighPriorityActiveIssuesOfType(final IssueTypeDto issueTypeDto) {
        return issueService.findAllActiveIssues().stream()
                .filter(issue -> issue.getIssueType().equals(issueTypeDto))
                .filter(issue -> issue.getPriority().equals(IssuePriorityDto.HIGH))
                .collect(toList());
    }

    private List<IssueDto> findAllActiveIssuesOfType(final IssueTypeDto issueTypeDto) {
        return issueService.findAllActiveIssues().stream().filter(issue -> issue.getIssueType().equals(issueTypeDto)).collect(toList());
    }

    private List<IssueDto> findAllNewIssuesOfType(final IssueTypeDto issueTypeDto) {
        return findAllIssuesWithState(IssueStateDto.NEW).stream().filter(issue -> issue.getIssueType().equals(issueTypeDto)).collect(toList());
    }

    private void assertAtLeastOneIssueNotAssignedTo(final Long operatorId, final List<IssueDto> issues) {
        assertThat(issues.stream().filter(issue -> issue.getAssignee() == null ||
                !operatorId.equals(issue.getAssignee().getId())).collect(toList())).isNotEmpty();
    }
}
