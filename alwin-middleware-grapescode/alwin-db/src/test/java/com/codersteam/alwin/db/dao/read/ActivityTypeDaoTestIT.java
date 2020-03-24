package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.db.dao.ActivityTypeDao;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.issue.IssueTypeActivityType;
import com.codersteam.alwin.jpa.type.ActivityTypeKey;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.ActivityTypeTestData.ALL_ACTIVITY_TYPES;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.activityType1;
import static com.codersteam.alwin.testdata.IssueTypeActivityTypeTestData.activityTypesByIssueType1;
import static com.codersteam.alwin.testdata.IssueTypeActivityTypeTestData.activityTypesByIssueType1AndMayHaveDeclaration;
import static com.codersteam.alwin.testdata.IssueTypeActivityTypeTestData.activityTypesByMayHaveDeclaration;
import static com.codersteam.alwin.testdata.IssueTypeActivityTypeTestData.issueTypeActivityType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.NOT_EXISTING_ISSUE_TYPE_ID;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomasz Sliwinski
 */
public class ActivityTypeDaoTestIT extends ReadTestBase {

    @EJB
    private ActivityTypeDao activityTypeDao;

    @Test
    public void shouldFindAll() {
        // when
        final List<ActivityType> allActivityTypes = activityTypeDao.findActivityTypes(null, null);

        // then
        assertEquals(ALL_ACTIVITY_TYPES.size(), allActivityTypes.size());
        assertThat(allActivityTypes)
                .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(ALL_ACTIVITY_TYPES);
    }

    @Test
    public void shouldFindActivityTypeByKey() {
        // when
        final Optional<ActivityType> activityType = activityTypeDao.findByKey(ActivityTypeKey.OUTGOING_PHONE_CALL);

        // then
        assertTrue(activityType.isPresent());
        assertThat(activityType.get())
                .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(activityType1());
    }

    @Test
    public void shouldFindActivityTypeByIssueType() {
        // when
        final List<ActivityType> activityTypes = activityTypeDao.findActivityTypes(IssueTypeName.PHONE_DEBT_COLLECTION_1, null);

        // then
        assertThat(activityTypes)
                .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(activityTypesByIssueType1());
    }

    @Test
    public void shouldFindActivityTypesByMayHaveDeclarations() {
        // when
        final List<ActivityType> activityTypes = activityTypeDao.findActivityTypes(null, true);

        // then
        assertThat(activityTypes)
                .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(activityTypesByMayHaveDeclaration());
    }

    @Test
    public void shouldFindActivityTypesByIssueTypeAndMayHaveDeclarations() {
        // when
        final List<ActivityType> activityTypes = activityTypeDao.findActivityTypes(IssueTypeName.PHONE_DEBT_COLLECTION_1, true);

        // then
        assertThat(activityTypes)
                .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(activityTypesByIssueType1AndMayHaveDeclaration());
    }

    @Test
    public void shouldCheckThatActivityTypeIsAllowedForIssueType() {
        // given
        final IssueTypeActivityType issueTypeActivityType = issueTypeActivityType1();

        // when
        final boolean result = activityTypeDao.checkIfActivityTypeIsAllowedForIssueType(issueTypeActivityType.getIssueType().getId(), issueTypeActivityType.getActivityType().getId());

        // then
        assertTrue(result);
    }

    @Test
    public void shouldCheckThatActivityTypeIsNotAllowedForIssueType() {
        // given
        final IssueTypeActivityType issueTypeActivityType = issueTypeActivityType1();

        // when
        final boolean result = activityTypeDao.checkIfActivityTypeIsAllowedForIssueType(NOT_EXISTING_ISSUE_TYPE_ID, issueTypeActivityType.getActivityType().getId());

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<ActivityType> type = activityTypeDao.getType();

        // then
        assertEquals(ActivityType.class, type);
    }
}
