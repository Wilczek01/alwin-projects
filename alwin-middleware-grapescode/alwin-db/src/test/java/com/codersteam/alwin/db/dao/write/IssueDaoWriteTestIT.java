package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.ActivityDao;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.type.ActivityState;
import com.codersteam.alwin.jpa.type.IssueState;
import com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData;
import com.codersteam.alwin.testdata.TagTestData;
import com.codersteam.alwin.testdata.TestDateUtils;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Ignore;
import org.junit.Test;

import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION;
import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.testdata.ActivityTestData.ACTIVITY_ID_111;
import static com.codersteam.alwin.testdata.ActivityTestData.ACTIVITY_ID_181;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*;
import static com.codersteam.alwin.testdata.CustomerTestData.ID_1;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.ISSUE_ID_18;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static com.codersteam.alwin.testdata.TagTestData.*;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData.*;
import static com.google.common.primitives.Longs.asList;
import static java.lang.String.format;
import static java.util.Collections.*;
import static org.apache.commons.lang3.time.DateUtils.addDays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice-my-work.json", "test-activity.json"})
public class IssueDaoWriteTestIT extends WriteTestBase {

    private final LinkedHashMap<IssueSortField, SortOrder> NO_SORT_CRITERIA = new LinkedHashMap<>();
    @EJB
    private IssueDao issueDao;
    @EJB
    private ActivityDao activityDao;

    @Test
    public void shouldAssignedActivitiesToOperatorOnlyOnce() {
        // when
        final boolean assigned = issueDao.assignNotAssignedActivityToOperator(ACTIVITY_ID_111, ID_1);
        final boolean tryToAssignOneMoreTime = issueDao.assignNotAssignedActivityToOperator(ACTIVITY_ID_111, ID_1);

        // then
        assertTrue(assigned);
        assertFalse(tryToAssignOneMoreTime);
    }

    @Test
    public void shouldStoreIssueStateUpdate() {
        // given
        final Issue issue = issueDao.get(AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1).get();
        final IssueState originalIssueState = issue.getIssueState();

        // and: set new state
        issue.setIssueState(IssueState.CANCELED);

        // when
        issueDao.update(issue);

        // then
        final Issue storedIssue = issueDao.get(AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1).get();
        final IssueState issueState = storedIssue.getIssueState();
        assertTrue(originalIssueState != issueState);
        assertEquals(IssueState.CANCELED, issueState);
    }

    @Test
    public void shouldAssignOperatorToIssue() {
        // given
        final Issue issue = issue2();

        // and
        final List<Long> issueIds = singletonList(issue.getId());
        assertNotEquals(issue.getAssignee().getId(), testOperator2().getId());

        // when
        final int result = issueDao.updateIssuesAssignment(testOperator2().getId(), issueIds);

        // then
        assertEquals(1, result);
        assertEquals(issueDao.get(issue.getId()).get().getAssignee().getId(), testOperator2().getId());
    }

    @Test
    public void shouldAssignOperatorToAllFilteredIssues() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final int result = issueDao.updateIssuesAssignment(testOperator1().getId(), operatorType, searchCriteriaByOperator());

        // then
        assertEquals(9, result);
    }

    @Test
    public void shouldAssignOperatorToAllOpenIssues() throws Exception {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // and
        final Long assigneeId = testOperator2().getId();

        // and there are issues not assigned to testOperator2 on first page
        final List<Issue> allOpenedIssuesBeforeAssignmentFirstPage = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByIssueOpenStates(),
                emptyList(), OPEN_STATES, NO_SORT_CRITERIA);
        assertThat(allOpenedIssuesBeforeAssignmentFirstPage.stream().filter(issue ->
                !assigneeId.equals(issue.getAssignee().getId()))).isNotEmpty();

        // when
        final int result = issueDao.updateIssuesAssignment(assigneeId, operatorType, searchCriteriaByIssueOpenStates());
        commitTrx();

        // then
        assertEquals(19, result);

        // and all issues on first page are assigned to testOperator2
        final List<Issue> allOpenedIssuesAfterAssignmentFirstPage = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByIssueOpenStates(),
                emptyList(), OPEN_STATES, NO_SORT_CRITERIA);
        assertEquals(5, allOpenedIssuesAfterAssignmentFirstPage.size());
        assertThat(allOpenedIssuesAfterAssignmentFirstPage).allMatch(issue -> issue.getAssignee().getId().equals(assigneeId));
    }

    @Test
    public void shouldAssignOperatorToAllOpenIssuesByExtCompanyIds() throws Exception {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // and
        final Long assigneeId = testOperator2().getId();

        // and there are issues not assigned to testOperator2 on first page
        final List<Issue> allOpenedIssuesBeforeAssignmentFirstPage = issueDao.findAllFilteredIssues(operatorType,
                searchCriteriaByIssueOpenStatesAndExtCompanyIds(), emptyList(), OPEN_STATES, NO_SORT_CRITERIA);
        assertThat(allOpenedIssuesBeforeAssignmentFirstPage.stream().filter(issue ->
                issue.getAssignee() == null || !assigneeId.equals(issue.getAssignee().getId()))).isNotEmpty();

        // when
        final int result = issueDao.updateIssuesAssignment(assigneeId, operatorType, searchCriteriaByIssueOpenStatesAndExtCompanyIds());
        commitTrx();

        // then
        assertEquals(4, result);

        // and all issues on first page are assigned to testOperator2
        final List<Issue> allOpenedIssuesAfterAssignmentFirstPage = issueDao.findAllFilteredIssues(operatorType,
                searchCriteriaByIssueOpenStatesAndExtCompanyIds(), emptyList(), OPEN_STATES, NO_SORT_CRITERIA);
        assertEquals(4, allOpenedIssuesAfterAssignmentFirstPage.size());
        assertThat(allOpenedIssuesAfterAssignmentFirstPage).allMatch(issue -> assigneeId.equals(issue.getAssignee().getId()));
    }

    @Test
    public void shouldUnAssignOperatorToAllFilteredIssue() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final int result = issueDao.updateIssuesAssignment(null, operatorType, searchCriteriaByOperator());

        // then
        assertEquals(9, result);
    }

    @Test
    public void shouldSetPriorityToAllFilteredIssue() throws Exception {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();
        final Integer priorityToSet = 1;
        final List<Issue> allIssuesToUpdate = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByOperator(), emptyList(), OPEN_STATES, NO_SORT_CRITERIA);
        assertThat(allIssuesToUpdate.stream().filter(issue -> issue.getPriority() == 0)).isNotEmpty();

        // when
        final int result = issueDao.updateIssuesPriority(priorityToSet, operatorType, searchCriteriaByOperator());
        commitTrx();

        // then
        assertEquals(9, result);

        final List<Issue> updatedIssues = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByOperator(), emptyList(), OPEN_STATES, NO_SORT_CRITERIA);
        assertThat(updatedIssues).allMatch(issue -> Objects.equals(issue.getPriority(), priorityToSet));
    }

    @Test
    public void shouldSetPriorityToAllFilteredByExtCompanyIdsIssues() throws Exception {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();
        final Integer priorityToSet = 1;
        final List<Issue> allIssuesToUpdate = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByIssueOpenStatesAndExtCompanyIds(), emptyList(),
                OPEN_STATES, NO_SORT_CRITERIA);
        assertThat(allIssuesToUpdate.stream().filter(issue -> issue.getPriority() == 0)).isNotEmpty();

        // when
        final int result = issueDao.updateIssuesPriority(priorityToSet, operatorType, searchCriteriaByIssueOpenStatesAndExtCompanyIds());
        commitTrx();

        // then
        assertEquals(4, result);

        final List<Issue> updatedIssues = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByIssueOpenStatesAndExtCompanyIds(), emptyList(),
                OPEN_STATES, NO_SORT_CRITERIA);
        assertThat(updatedIssues).allMatch(issue -> Objects.equals(issue.getPriority(), priorityToSet));
    }

    @Test
    public void shouldAssignTagsToIssue() throws Exception {
        // given
        final Optional<Issue> issue = issueDao.get(ISSUE_ID_1);
        assertTrue(issue.isPresent());

        // and
        assertEquals(2, issue.get().getTags().size());
        assertTrue(issue.get().getTags().contains(testTag1()));
        assertTrue(issue.get().getTags().contains(testTag2()));

        // when
        issueDao.assignTags(ISSUE_ID_1, singletonList(TagTestData.ID_2));
        commitTrx();

        // then
        final Optional<Issue> updatedIssue = issueDao.get(ISSUE_ID_1);
        assertTrue(updatedIssue.isPresent());

        // and
        assertEquals(1, updatedIssue.get().getTags().size());
        assertTrue(updatedIssue.get().getTags().contains(testTag2()));
    }

    @Test
    public void shouldAssignTagsToAllFilteredIssues() throws Exception {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // and
        final Set<Long> tagIds = singleton(TagTestData.ID_2);

        // and
        final IssuesSearchCriteria criteria = searchCriteriaByOperator();

        // when
        issueDao.assignIssuesTags(tagIds, operatorType, criteria, emptyList(), OPEN_STATES);
        commitTrx();

        // then
        criteria.setMaxResults(Integer.MAX_VALUE);
        final List<Issue> issues = issueDao.findAllFilteredIssues(operatorType, criteria, emptyList(), OPEN_STATES, NO_SORT_CRITERIA);

        // and
        issues.forEach(issue -> {
            assertTrue(issue.getTags().contains(testTag2()));
        });
    }

    @Test
    public void shouldAssignTagsToGivenIssues() throws Exception {
        // given
        final List<Long> issueIds = asList(ISSUE_ID_1, ISSUE_ID_2, ISSUE_ID_3);

        // and
        final Optional<Issue> issue1 = issueDao.get(ISSUE_ID_1);
        assertTrue(issue1.isPresent());
        assertEquals(2, issue1.get().getTags().size());
        assertTrue(issue1.get().getTags().contains(testTag1()));
        assertTrue(issue1.get().getTags().contains(testTag2()));

        // and
        final Optional<Issue> issue2 = issueDao.get(ISSUE_ID_2);
        assertTrue(issue2.isPresent());
        assertEquals(1, issue2.get().getTags().size());
        assertTrue(issue2.get().getTags().contains(testTag1()));

        // and
        final Optional<Issue> issue3 = issueDao.get(ISSUE_ID_3);
        assertTrue(issue3.isPresent());
        assertEquals(2, issue3.get().getTags().size());
        assertTrue(issue3.get().getTags().contains(testTag3()));
        assertTrue(issue3.get().getTags().contains(testTag1()));

        // and
        final Set<Long> tagIds = singleton(TagTestData.ID_2);

        // when
        issueDao.assignIssuesTags(tagIds, issueIds);
        commitTrx();

        // then
        final Optional<Issue> issue1AfterUpdate = issueDao.get(ISSUE_ID_1);
        assertTrue(issue1AfterUpdate.isPresent());
        assertEquals(2, issue1AfterUpdate.get().getTags().size());
        assertTrue(issue1AfterUpdate.get().getTags().contains(testTag1()));
        assertTrue(issue1AfterUpdate.get().getTags().contains(testTag2()));

        // and
        final Optional<Issue> issue2AfterUpdate = issueDao.get(ISSUE_ID_2);
        assertTrue(issue2AfterUpdate.isPresent());
        assertEquals(2, issue2AfterUpdate.get().getTags().size());
        assertTrue(issue2AfterUpdate.get().getTags().contains(testTag1()));
        assertTrue(issue2AfterUpdate.get().getTags().contains(testTag2()));

        // and
        final Optional<Issue> issue3AfterUpdate = issueDao.get(ISSUE_ID_3);
        assertTrue(issue3AfterUpdate.isPresent());
        assertEquals(3, issue3AfterUpdate.get().getTags().size());
        assertTrue(issue3AfterUpdate.get().getTags().contains(testTag3()));
        assertTrue(issue3AfterUpdate.get().getTags().contains(testTag2()));
        assertTrue(issue3AfterUpdate.get().getTags().contains(testTag1()));
    }

    @Test
    public void shouldNotReturnIssueWithTodayExecutedActivityWhenFindingFutureIssuesForUserToWorkOn() throws Exception {
        // given
        final Long userId = ID_1;
        final Date inspectionDate = parse("2017-07-15");

        // and update issue 18 activity as executed for inspection date
        updateActivity(ACTIVITY_ID_181, inspectionDate, ActivityState.EXECUTED);

        // and
        assertThatIssueHasTwoActivities(ISSUE_ID_18);
        assertThatIssueHasPlannedActivityAfterInspectionDate(ISSUE_ID_18, inspectionDate);
        assertThatIssueHasExecutedActivityOnInspectionDate(ISSUE_ID_18, inspectionDate);

        // when
        Optional<Activity> activities = issueDao.findFutureActivityForUserToWorkOn(userId, PHONE_DEBT_COLLECTION, inspectionDate);

        // then
        assertTrue(activities.isPresent());

        // and should not contain issue 18 activity
        Optional<Long> issueIds = activities.map(activity -> activity.getIssue().getId());
        assertThat(issueIds.get()).isNotEqualTo(ISSUE_ID_18);
    }

    @Test
    @Ignore
    public void shouldReturnIssueWithYesterdayExecutedActivityWhenFindingFutureIssuesForUserToWorkOn() throws Exception {
        // given
        final Long userId = ID_1;
        final Date inspectionDate = parse("2017-07-15");

        // and update issue 18 activity as executed for inspection date
        final Date dayBeforeInspectionDate = addDays(inspectionDate, -1);
        updateActivity(ACTIVITY_ID_181, dayBeforeInspectionDate, ActivityState.EXECUTED);

        // and
        assertThatIssueHasTwoActivities(ISSUE_ID_18);
        assertThatIssueHasPlannedActivityAfterInspectionDate(ISSUE_ID_18, inspectionDate);
        assertThatIssueHasExecutedActivityBeforeInspectionDate(ISSUE_ID_18, inspectionDate);

        // when
        final Optional<Activity> activities = issueDao.findFutureActivityForUserToWorkOn(userId, PHONE_DEBT_COLLECTION, inspectionDate);

        // then
        assertTrue(activities.isPresent());

        // and should contains issue 18 activity
        Optional<Long> issueIds = activities.map(activity -> activity.getIssue().getId());
        assertThat(issueIds.get()).isEqualTo(ISSUE_ID_18);
    }

    @SuppressWarnings("SameParameterValue")
    private void updateActivity(final Long activityId, final Date activityDate, final ActivityState activityState) throws Exception {
        final Activity activity = activityDao.get(activityId)
                .orElseThrow(() -> new EntityNotFoundException(format("No activity with id %d found", activityId)));
        activity.setActivityDate(activityDate);
        activity.setState(activityState);
        activityDao.update(activity);
        commitTrx();
    }

    @SuppressWarnings("SameParameterValue")
    private void assertThatIssueHasTwoActivities(final long issueId) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(format("No issue with id %d found", issueId)));
        assertEquals(2, issue.getActivities().size());
    }

    @SuppressWarnings("SameParameterValue")
    private void assertThatIssueHasPlannedActivityAfterInspectionDate(final Long issueId, final Date inspectionDate) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(format("No issue with id %d found", issueId)));
        final List<Activity> plannedActivities = issue.getActivities().stream()
                .filter(activity -> activity.getState() == ActivityState.PLANNED && activity.getPlannedDate().after(inspectionDate))
                .collect(Collectors.toList());
        assertFalse(plannedActivities.isEmpty());
    }

    @SuppressWarnings("SameParameterValue")
    private void assertThatIssueHasExecutedActivityOnInspectionDate(final Long issueId, final Date inspectionDate) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(format("No issue with id %d found", issueId)));
        final List<Activity> executedActivities = issue.getActivities().stream()
                .filter(activity -> activity.getState() == ActivityState.EXECUTED && activity.getActivityDate().getTime() == inspectionDate.getTime())
                .collect(Collectors.toList());
        assertFalse(executedActivities.isEmpty());
    }

    @SuppressWarnings("SameParameterValue")
    private void assertThatIssueHasExecutedActivityBeforeInspectionDate(final Long issueId, final Date inspectionDate) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(format("No issue with id %d found", issueId)));
        final List<Activity> executedActivities = issue.getActivities().stream()
                .filter(activity -> activity.getState() == ActivityState.EXECUTED && activity.getActivityDate().before(inspectionDate))
                .collect(Collectors.toList());
        assertFalse(executedActivities.isEmpty());
    }

    @Test
    public void shouldFindFutureIssuesForUserToWorkOn() {
        // given
        final Long userId = ID_1;
        final Date inspectionDate = TestDateUtils.parse("2017-07-15");

        // when
        final Optional<Activity> activities = issueDao.findFutureActivityForUserToWorkOn(userId, PHONE_DEBT_COLLECTION, inspectionDate);

        // then
        Optional<Long> ids = activities.map(Activity::getId);
        assertTrue(ids.isPresent());
    }
}
