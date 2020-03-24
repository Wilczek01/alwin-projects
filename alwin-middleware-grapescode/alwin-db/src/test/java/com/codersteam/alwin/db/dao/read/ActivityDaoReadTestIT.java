package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;
import com.codersteam.alwin.db.dao.ActivityDao;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.Declaration;
import com.codersteam.alwin.testdata.CompanyTestData;
import com.codersteam.alwin.testdata.OperatorTestData;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.jpa.type.ActivityState.EXECUTED;
import static com.codersteam.alwin.jpa.type.ActivityState.PLANNED;
import static com.codersteam.alwin.testdata.ActivityTestData.ACTIVITY_ID_100;
import static com.codersteam.alwin.testdata.ActivityTestData.activity21;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_20;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_123;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_124;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_128;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_LAST_NAME_1;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_LAST_NAME_2;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSearchCriteriaTestData.activitiesInPeriodSearchCriteria;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSearchCriteriaTestData.activitiesSearchCriteria;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSearchCriteriaTestData.activitiesWithRemarkSearchCriteria;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSearchCriteriaTestData.activitiesWithStatesSearchCriteria;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSearchCriteriaTestData.companyActivitiesSearchCriteria;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSearchCriteriaTestData.operatorActivitiesSearchCriteria;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSortTestData.emptySortCriteria;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSortTestData.sortByOperatorFullNameAscending;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSortTestData.sortByOperatorLastNameAscending;
import static com.codersteam.alwin.testdata.criteria.ActivitiesSortTestData.sortByOperatorLastNameDecending;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomasz Sliwinski
 */
public class ActivityDaoReadTestIT extends ReadTestBase {

    @EJB
    private ActivityDao activityDao;

    @Test
    public void shouldFindAllActivitiesByIssueId() {
        // when
        final List<Activity> actions = activityDao.findAllIssueActivities(ISSUE_ID_1);

        // then
        assertFalse(actions.isEmpty());
        assertEquals(5, actions.size());
    }

    @Test
    public void shouldFindOldestPlannedActivityForIssue() {
        // given
        final Date maxPlannedDate = parse("2017-08-22");

        // when
        final Optional<Activity> activity = activityDao.findOldestPlannedActivityForIssue(ISSUE_ID_2, maxPlannedDate);

        // then
        assertTrue(activity.isPresent());
        assertThat(activity.get())
                .usingComparatorForFields(SKIP_COMPARATOR, "issue", "operator", "activityType", "activityDetails", "declarations")
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "activityDate", "creationDate", "plannedDate")
                .isEqualToComparingFieldByFieldRecursively(activity21());
    }

    @Test
    public void shouldNotFindOldestPlannedActivityForIssue() {
        // given
        final Date maxPlannedDate = parse("2017-07-13");

        // when
        final Optional<Activity> activity = activityDao.findOldestPlannedActivityForIssue(ISSUE_ID_2, maxPlannedDate);

        // then
        assertFalse(activity.isPresent());
    }

    @Test
    public void shouldFindActiveActivitiesByIssueId() {
        final Date maxPlannedDate = parse("2017-08-22");
        // when
        final List<Activity> activities = activityDao.findActiveIssueActivities(ISSUE_ID_1, maxPlannedDate);

        // then
        final List<Long> activityIds = activities.stream().map(Activity::getId).collect(toList());
        assertEquals(asList(100L, 11L, 12L, 13L), activityIds);
    }

    @Test
    public void shouldFindAllPaginatedActivitiesForIssueId() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(0, 5);

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, emptySortCriteria());

        // then
        assertEquals(5, activities.size());
    }

    @Test
    public void shouldFindAllPaginatedActivitiesForIssueIdOrderedByLastNameDesc() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(0, 5);

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, sortByOperatorLastNameDecending());

        // then
        assertEquals(5, activities.size());
        assertEquals(TEST_USER_LAST_NAME_2, activities.get(0).getOperator().getUser().getLastName());
        assertEquals(TEST_USER_LAST_NAME_1, activities.get(1).getOperator().getUser().getLastName());
    }

    @Test
    public void shouldFindAllPaginatedActivitiesForIssueIdOrderedByLastNameAsc() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(0, 5);

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, sortByOperatorLastNameAscending());

        // then
        assertEquals(5, activities.size());
        assertEquals(TEST_USER_LAST_NAME_1, activities.get(3).getOperator().getUser().getLastName());
        assertEquals(TEST_USER_LAST_NAME_2, activities.get(4).getOperator().getUser().getLastName());
    }

    @Test
    public void shouldFindAllPaginatedActivitiesForIssueIdOrderedByOperatorFullNameAsc() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(0, 5);

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, sortByOperatorFullNameAscending());

        // then
        assertEquals(5, activities.size());
        assertEquals(TEST_USER_LAST_NAME_1, activities.get(3).getOperator().getUser().getLastName());
        assertEquals(TEST_USER_LAST_NAME_2, activities.get(4).getOperator().getUser().getLastName());
    }

    @Test
    public void shouldFindPaginatedActivitiesForCompanyId() {
        // given
        final ActivitiesSearchCriteria searchCriteria = companyActivitiesSearchCriteria(5, 5, CompanyTestData.COMPANY_ID_1);

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, emptySortCriteria());

        // then
        assertEquals(3, activities.size());
    }

    @Test
    public void shouldFindPaginatedActivitiesForOperator() {
        // given
        final ActivitiesSearchCriteria searchCriteria = operatorActivitiesSearchCriteria(0, 5, OperatorTestData.OPERATOR_ID_2);

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, emptySortCriteria());

        // then
        assertEquals(1, activities.size());
        assertEquals(OperatorTestData.OPERATOR_ID_2, activities.get(0).getOperator().getId());
    }

    @Test
    public void shouldFindPaginatedActivitiesCreatedBetweenDates() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesInPeriodSearchCriteria(0, 5, "2017-07-04", "2017-07-06");

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, emptySortCriteria());

        // then
        assertEquals(1, activities.size());
        assertEquals(ACTIVITY_ID_100, activities.get(0).getId());
    }

    @Test
    public void shouldFindPaginatedActivitiesWithRemark() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesWithRemarkSearchCriteria(0, 5, "menta");

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, emptySortCriteria());

        // then
        assertEquals(1, activities.size());
    }

    @Test
    public void shouldFindFilteredActivitiesCount() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesWithRemarkSearchCriteria(0, 5, "menta");

        // when
        final long count = activityDao.findAllIssueActivitiesCount(ISSUE_ID_1, searchCriteria);

        // then
        assertEquals(1, count);
    }

    @Test
    public void shouldFindIssueDeclarations() {
        // when
        final List<Declaration> declarations = activityDao.findIssueDeclarations(ISSUE_ID_1);

        // then
        assertEquals(3, declarations.size());
        assertThat(declarations)
                .usingComparatorForFields(SKIP_COMPARATOR, "activity")
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "declaredPaymentDate", "declarationDate")
                .isEqualToComparingFieldByFieldRecursively(asList(DECLARATION_123, DECLARATION_124, DECLARATION_128));
    }

    @Test
    public void shouldFindExecutedActivitiesCount() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesWithStatesSearchCriteria(EXECUTED.name());

        // when
        final long executedActivitiesCount = activityDao.findAllIssueActivitiesCount(ISSUE_ID_20, searchCriteria);

        // then
        assertEquals(1, executedActivitiesCount);
    }

    @Test
    public void shouldFindPaginatedExecutedActivities() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesWithStatesSearchCriteria(EXECUTED.name());

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_20, searchCriteria, emptySortCriteria());

        // then
        assertFalse(activities.isEmpty());
        activities.forEach(activity -> assertEquals(EXECUTED, activity.getState()));
    }

    @Test
    public void shouldFindPlannedActivitiesCount() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesWithStatesSearchCriteria(PLANNED.name());

        // when
        final long executedActivitiesCount = activityDao.findAllIssueActivitiesCount(ISSUE_ID_1, searchCriteria);

        // then
        assertEquals(5, executedActivitiesCount);
    }

    @Test
    public void shouldFindPaginatedPlannedActivities() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesWithStatesSearchCriteria(PLANNED.name());

        // when
        final List<Activity> activities = activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, emptySortCriteria());

        // then
        assertFalse(activities.isEmpty());
        activities.forEach(activity -> assertEquals(PLANNED, activity.getState()));
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionForUnknownSearchCriterionState() {
        // given
        final ActivitiesSearchCriteria searchCriteria = activitiesWithStatesSearchCriteria("unknown_state");

        // when
        activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, emptySortCriteria());
    }

    @Test
    public void shouldNotFindIssueDeclarations() {
        // when
        final List<Declaration> declarations = activityDao.findIssueDeclarations(ISSUE_ID_2);

        // then
        assertEquals(0, declarations.size());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Activity> type = activityDao.getType();

        // then
        assertEquals(Activity.class, type);
    }
}
