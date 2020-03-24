package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueTypeActivityType;

import java.util.Collections;
import java.util.List;

import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_5;
import static java.util.Arrays.asList;

public class IssueTypeActivityTypeTestData {

    public static long ISSUE_TYPE_ACTIVITY_TYPE_ID_1 = 1;
    public static IssueType ISSUE_TYPE_1 = IssueTypeTestData.issueType1();
    public static ActivityType ACTIVITY_TYPE_1 = ActivityTypeTestData.TEST_ACTIVITY_TYPE_1;
    public static ActivityType ACTIVITY_TYPE_2 = ActivityTypeTestData.TEST_ACTIVITY_TYPE_2;
    public static ActivityType ACTIVITY_TYPE_3 = ActivityTypeTestData.TEST_ACTIVITY_TYPE_3;
    public static ActivityType ACTIVITY_TYPE_14 = ActivityTypeTestData.TEST_ACTIVITY_TYPE_14;

    public static IssueTypeActivityType issueTypeActivityType1() {
        return issueTypeActivityType(ISSUE_TYPE_ACTIVITY_TYPE_ID_1, ISSUE_TYPE_1, ACTIVITY_TYPE_1);
    }

    public static List<ActivityType> activityTypesByIssueType1() {
        return asList(ACTIVITY_TYPE_1, ACTIVITY_TYPE_2, ACTIVITY_TYPE_3, ACTIVITY_TYPE_14);
    }

    public static List<ActivityType> activityTypesByMayHaveDeclaration() {
        return asList(ACTIVITY_TYPE_1, TEST_ACTIVITY_TYPE_5);
    }

    public static List<ActivityType> activityTypesByIssueType1AndMayHaveDeclaration() {
        return Collections.singletonList(ACTIVITY_TYPE_1);
    }

    private static IssueTypeActivityType issueTypeActivityType(final Long id, final IssueType issueType, final ActivityType activityType) {
        final IssueTypeActivityType issueTypeActivityType = new IssueTypeActivityType();
        issueTypeActivityType.setId(id);
        issueTypeActivityType.setIssueType(issueType);
        issueTypeActivityType.setActivityType(activityType);
        return issueTypeActivityType;
    }

}
