package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.DefaultIssueActivityDao;
import com.codersteam.alwin.jpa.activity.DefaultIssueActivity;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.ALL_DEFAULT_ISSUE_ACTIVITIES;
import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.DEFAULT_ISSUE_ACTIVITIES_WT1;
import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.DEFAULT_ISSUE_ACTIVITIES_WT2;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ID_2;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Tomasz Sliwinski
 */
public class DefaultIssueActivityDaoReadTestIT extends ReadTestBase {

    @EJB
    private DefaultIssueActivityDao defaultIssueActivityDao;

    @Test
    public void shouldReturnDefaultActivitiesForIssueType1() {
        // when
        final List<DefaultIssueActivity> defaultActivities = defaultIssueActivityDao.findDefaultActivities(ISSUE_TYPE_ID_1);

        // then
        assertThat(defaultActivities)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "creatingDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties", "activityType.issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(DEFAULT_ISSUE_ACTIVITIES_WT1);
    }

    @Test
    public void shouldReturnDefaultActivitiesForIssueType2() {
        // when
        final List<DefaultIssueActivity> defaultActivities = defaultIssueActivityDao.findDefaultActivities(ID_2);

        // then
        assertThat(defaultActivities)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "creatingDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties", "activityType.issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(DEFAULT_ISSUE_ACTIVITIES_WT2);
    }

    @Test
    public void shouldReturnAllDefaultActivities() {
        // when
        final List<DefaultIssueActivity> activities = defaultIssueActivityDao.findAllDefaultActivities();

        // then
        assertThat(activities)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "creatingDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties", "activityType.issueTypeActivityTypes")
                .isEqualToComparingFieldByFieldRecursively(ALL_DEFAULT_ISSUE_ACTIVITIES);
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<DefaultIssueActivity> type = defaultIssueActivityDao.getType();

        // then
        assertEquals(DefaultIssueActivity.class, type);
    }
}
