package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.ActivityDao;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.ActivityState;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static com.codersteam.alwin.jpa.type.ActivityState.CANCELED;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.testdata.ActivityTestData.ACTIVITY_ID_1;
import static com.codersteam.alwin.testdata.ActivityTestData.TEST_ACTIVITY_3;
import static com.codersteam.alwin.testdata.ActivityTestData.TEST_ACTIVITY_WITHOUT_ID;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.ACTIVITY_TYPE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_21;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_14;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.ISSUE_ID_15;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData.searchCriteriaByOperator;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json"})
public class ActivityDaoWriteTestIT extends WriteTestBase {

    @EJB
    private ActivityDao activityDao;

    @Test
    public void shouldCreateNewActivity() {
        // given: no activities associated with issue
        final Long issueId = ISSUE_ID_21;
        final List<Activity> allIssueActivitiesBeforeCreateNewActivity = activityDao.findAllIssueActivities(issueId);

        // when
        activityDao.createNewActivityForIssue(TEST_ACTIVITY_WITHOUT_ID);

        // then
        // there was 1 activity for issue
        assertEquals(1, allIssueActivitiesBeforeCreateNewActivity.size());

        // and there is 1 new activity
        final List<Activity> allIssueActivities = activityDao.findAllIssueActivities(issueId);
        assertEquals(2, allIssueActivities.size());
        final Activity activity = allIssueActivities.get(1);
        assertThat(activity)
                .usingComparatorForFields(SKIP_COMPARATOR, "activityType", "operator", "user.operators", "issue", "activityDetails.detailProperty")
                .isEqualToComparingFieldByFieldRecursively(TEST_ACTIVITY_3);

        // and activity type is set
        final ActivityType activityType = activity.getActivityType();
        assertEquals(ACTIVITY_TYPE_ID_1, activityType.getId());

        // and operator is set
        final Operator operator = activity.getOperator();
        assertEquals(OPERATOR_ID_1, operator.getId());

        // and issue is set
        final Issue issue = activity.getIssue();
        assertEquals(ISSUE_ID_21, issue.getId().longValue());
    }

    @Test
    public void shouldStoreActionWithUpdatedState() {
        // given
        final Activity activity = activityDao.get(ACTIVITY_ID_1).get();
        final ActivityState originalState = activity.getState();

        // and: new state
        activity.setState(CANCELED);

        // when
        activityDao.update(activity);

        // then
        final Activity storedActivity = activityDao.get(ACTIVITY_ID_1).get();
        final ActivityState state = storedActivity.getState();
        assertNotSame(originalState, state);
        assertEquals(CANCELED, state);
    }

    @Test
    public void shouldAssignOperatorToAllFilteredIssue() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final int result = activityDao.updateActivityOperator(OPERATOR_ID_1, operatorType, searchCriteriaByOperator());

        // then
        assertEquals(21, result);
    }

    @Test
    public void shouldUnAssignOperatorToAllFilteredIssue() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final int result = activityDao.updateActivityOperator(null, operatorType, searchCriteriaByOperator());

        // then
        assertEquals(21, result);
    }

    @Test
    public void shouldAssignOperatorToActivitiesByIssueId() {
        // when
        final int result = activityDao.updateActivityOperator(OPERATOR_ID_1, asList(ISSUE_ID_14, ISSUE_ID_15));

        // then
        assertEquals(4, result);
    }
}
