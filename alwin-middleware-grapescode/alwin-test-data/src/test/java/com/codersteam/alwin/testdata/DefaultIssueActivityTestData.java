package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.activity.DefaultIssueActivityDto;
import com.codersteam.alwin.core.api.model.activity.IssueTypeWithDefaultActivities;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.activity.DefaultIssueActivity;
import com.codersteam.alwin.jpa.issue.IssueType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_2;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_3;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_7;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_2;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_3;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public final class DefaultIssueActivityTestData {

    public static final Long DEFAULT_ISSUE_ACTIVITY_ID_1 = 1L;
    private static final IssueType DEFAULT_ISSUE_ISSUE_TYPE_1 = ISSUE_TYPE_1;
    private static final ActivityType DEFAULT_ISSUE_ACTIVITY_TYPE_1 = TEST_ACTIVITY_TYPE_1;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_1 = 1;
    private static final Integer DEFAULT_ISSUE_VERSION_1 = 1;
    private static final Date DEFAULT_ISSUE_CREATING_DATE_1 = parse("2017-07-01 00:00:00.000000");
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_1 = parse("2017-10-02 00:00:00.000000");

    private static final Long DEFAULT_ISSUE_ACTIVITY_ID_2 = 2L;
    private static final IssueType DEFAULT_ISSUE_ISSUE_TYPE_2 = ISSUE_TYPE_1;
    private static final ActivityType DEFAULT_ISSUE_ACTIVITY_TYPE_2 = TEST_ACTIVITY_TYPE_3;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_2 = 7;
    private static final Integer DEFAULT_ISSUE_VERSION_2 = 2;
    private static final Date DEFAULT_ISSUE_CREATING_DATE_2 = parse("2017-07-04 00:00:00.000000");
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_2 = parse("2017-10-08 00:00:00.000000");

    private static final Long DEFAULT_ISSUE_ACTIVITY_ID_4 = 4L;
    private static final IssueType DEFAULT_ISSUE_ISSUE_TYPE_4 = ISSUE_TYPE_1;
    private static final ActivityType DEFAULT_ISSUE_ACTIVITY_TYPE_4 = TEST_ACTIVITY_TYPE_1;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_4 = 10;
    private static final Integer DEFAULT_ISSUE_VERSION_4 = 1;
    private static final Date DEFAULT_ISSUE_CREATING_DATE_4 = parse("2017-07-03 00:00:00.000000");
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_4 = parse("2017-10-11 00:00:00.000000");

    private static final Long DEFAULT_ISSUE_ACTIVITY_ID_5 = 5L;
    private static final IssueType DEFAULT_ISSUE_ISSUE_TYPE_5 = ISSUE_TYPE_1;
    private static final ActivityType DEFAULT_ISSUE_ACTIVITY_TYPE_5 = TEST_ACTIVITY_TYPE_2;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_5 = 3;
    private static final Integer DEFAULT_ISSUE_VERSION_5 = 2;
    private static final Date DEFAULT_ISSUE_CREATING_DATE_5 = parse("2017-07-10 00:00:00.000000");
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_5 = parse("2017-10-04 00:00:00.000000");

    private static final Long DEFAULT_ISSUE_ACTIVITY_ID_6 = 6L;
    private static final IssueType DEFAULT_ISSUE_ISSUE_TYPE_6 = ISSUE_TYPE_2;
    private static final ActivityType DEFAULT_ISSUE_ACTIVITY_TYPE_6 = TEST_ACTIVITY_TYPE_1;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_6 = 1;
    private static final Integer DEFAULT_ISSUE_VERSION_6 = 1;
    private static final Date DEFAULT_ISSUE_CREATING_DATE_6 = parse("2017-07-05 00:00:00.000000");
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_6 = parse("2017-10-02 00:00:00.000000");

    private static final Long DEFAULT_ISSUE_ACTIVITY_ID_7 = 7L;
    private static final IssueType DEFAULT_ISSUE_ISSUE_TYPE_7 = ISSUE_TYPE_2;
    private static final ActivityType DEFAULT_ISSUE_ACTIVITY_TYPE_7 = TEST_ACTIVITY_TYPE_3;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_7 = 7;
    private static final Integer DEFAULT_ISSUE_VERSION_7 = 1;
    private static final Date DEFAULT_ISSUE_CREATING_DATE_7 = parse("2017-07-06 00:00:00.000000");
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_7 = parse("2017-10-08 00:00:00.000000");

    private static final Long DEFAULT_ISSUE_ACTIVITY_ID_8 = 8L;
    private static final IssueType DEFAULT_ISSUE_ISSUE_TYPE_8 = ISSUE_TYPE_3;
    private static final ActivityType DEFAULT_ISSUE_ACTIVITY_TYPE_8 = TEST_ACTIVITY_TYPE_7;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_8 = 2;
    private static final Integer DEFAULT_ISSUE_VERSION_8 = 1;
    private static final Date DEFAULT_ISSUE_CREATING_DATE_8 = parse("2018-08-14 00:00:00.000000");
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_8 = parse("2017-10-03 00:00:00.000000");

    private static final Integer DEFAULT_ISSUE_VERSION_1_V2 = 2;

    private static final Long DEFAULT_ISSUE_ACTIVITY_ID_100 = 100L;
    private static final Integer DEFAULT_ISSUE_DEFAULT_DAY_100 = 5;
    private static final Integer DEFAULT_ISSUE_VERSION_100 = 2;
    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_100 = parse("2012-12-17 00:00:00.000000");

    private static final Date DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_FOR_UPDATE = parse("2012-12-13 00:00:00.000000");

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_1 = defaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_1, DEFAULT_ISSUE_ISSUE_TYPE_1,
            DEFAULT_ISSUE_ACTIVITY_TYPE_1, DEFAULT_ISSUE_DEFAULT_DAY_1, DEFAULT_ISSUE_VERSION_1, DEFAULT_ISSUE_CREATING_DATE_1);

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_2 = defaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_2, DEFAULT_ISSUE_ISSUE_TYPE_2,
            DEFAULT_ISSUE_ACTIVITY_TYPE_2, DEFAULT_ISSUE_DEFAULT_DAY_2, DEFAULT_ISSUE_VERSION_2, DEFAULT_ISSUE_CREATING_DATE_2);

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_4 = defaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_4, DEFAULT_ISSUE_ISSUE_TYPE_4,
            DEFAULT_ISSUE_ACTIVITY_TYPE_4, DEFAULT_ISSUE_DEFAULT_DAY_4, DEFAULT_ISSUE_VERSION_4, DEFAULT_ISSUE_CREATING_DATE_4);

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_5 = defaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_5, DEFAULT_ISSUE_ISSUE_TYPE_5,
            DEFAULT_ISSUE_ACTIVITY_TYPE_5, DEFAULT_ISSUE_DEFAULT_DAY_5, DEFAULT_ISSUE_VERSION_5, DEFAULT_ISSUE_CREATING_DATE_5);

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_6 = defaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_6, DEFAULT_ISSUE_ISSUE_TYPE_6,
            DEFAULT_ISSUE_ACTIVITY_TYPE_6, DEFAULT_ISSUE_DEFAULT_DAY_6, DEFAULT_ISSUE_VERSION_6, DEFAULT_ISSUE_CREATING_DATE_6);

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_7 = defaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_7, DEFAULT_ISSUE_ISSUE_TYPE_7,
            DEFAULT_ISSUE_ACTIVITY_TYPE_7, DEFAULT_ISSUE_DEFAULT_DAY_7, DEFAULT_ISSUE_VERSION_7, DEFAULT_ISSUE_CREATING_DATE_7);

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_8 = defaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_8, DEFAULT_ISSUE_ISSUE_TYPE_8,
            DEFAULT_ISSUE_ACTIVITY_TYPE_8, DEFAULT_ISSUE_DEFAULT_DAY_8, DEFAULT_ISSUE_VERSION_8, DEFAULT_ISSUE_CREATING_DATE_8);

    public static final DefaultIssueActivity DEFAULT_ISSUE_ACTIVITY_1_V2 = defaultIssueActivity(null, DEFAULT_ISSUE_ISSUE_TYPE_1,
            DEFAULT_ISSUE_ACTIVITY_TYPE_1, DEFAULT_ISSUE_DEFAULT_DAY_1, DEFAULT_ISSUE_VERSION_1_V2, DEFAULT_ISSUE_CREATING_DATE_1);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_DTO_1 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_1,
            DEFAULT_ISSUE_ACTIVITY_TYPE_1.getId(), DEFAULT_ISSUE_DEFAULT_DAY_1, DEFAULT_ISSUE_VERSION_1, DEFAULT_ISSUE_CREATING_DATE_1,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_1);

    public static final DefaultIssueActivityDto UPDATED_DEFAULT_ISSUE_ACTIVITY_DTO_1 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_100,
            DEFAULT_ISSUE_ACTIVITY_TYPE_1.getId(), DEFAULT_ISSUE_DEFAULT_DAY_100, DEFAULT_ISSUE_VERSION_100, DEFAULT_ISSUE_CREATING_DATE_1,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_100);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_FOR_UPDATE_DTO_1 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_1,
            DEFAULT_ISSUE_ACTIVITY_TYPE_1.getId(), DEFAULT_ISSUE_DEFAULT_DAY_1, DEFAULT_ISSUE_VERSION_1, DEFAULT_ISSUE_CREATING_DATE_1,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_FOR_UPDATE);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_DTO_2 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_2,
            DEFAULT_ISSUE_ACTIVITY_TYPE_2.getId(), DEFAULT_ISSUE_DEFAULT_DAY_2, DEFAULT_ISSUE_VERSION_2, DEFAULT_ISSUE_CREATING_DATE_2,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_2);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_DTO_4 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_4,
            DEFAULT_ISSUE_ACTIVITY_TYPE_4.getId(), DEFAULT_ISSUE_DEFAULT_DAY_4, DEFAULT_ISSUE_VERSION_4, DEFAULT_ISSUE_CREATING_DATE_4,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_4);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_DTO_5 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_5,
            DEFAULT_ISSUE_ACTIVITY_TYPE_5.getId(), DEFAULT_ISSUE_DEFAULT_DAY_5, DEFAULT_ISSUE_VERSION_5, DEFAULT_ISSUE_CREATING_DATE_5,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_5);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_DTO_6 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_6,
            DEFAULT_ISSUE_ACTIVITY_TYPE_6.getId(), DEFAULT_ISSUE_DEFAULT_DAY_6, DEFAULT_ISSUE_VERSION_6, DEFAULT_ISSUE_CREATING_DATE_6,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_6);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_DTO_7 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_7,
            DEFAULT_ISSUE_ACTIVITY_TYPE_7.getId(), DEFAULT_ISSUE_DEFAULT_DAY_7, DEFAULT_ISSUE_VERSION_7, DEFAULT_ISSUE_CREATING_DATE_7,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_7);

    public static final DefaultIssueActivityDto DEFAULT_ISSUE_ACTIVITY_DTO_8 = defaultIssueActivityDto(DEFAULT_ISSUE_ACTIVITY_ID_8,
            DEFAULT_ISSUE_ACTIVITY_TYPE_8.getId(), DEFAULT_ISSUE_DEFAULT_DAY_8, DEFAULT_ISSUE_VERSION_8, DEFAULT_ISSUE_CREATING_DATE_8,
            DEFAULT_ISSUE_ACTIVITY_EXECUTION_DATE_8);

    public static final List<DefaultIssueActivity> DEFAULT_ISSUE_ACTIVITIES_WT1 = Arrays.asList(
            DEFAULT_ISSUE_ACTIVITY_1, DEFAULT_ISSUE_ACTIVITY_5, DEFAULT_ISSUE_ACTIVITY_2, DEFAULT_ISSUE_ACTIVITY_4
    );

    public static final List<DefaultIssueActivity> DEFAULT_ISSUE_ACTIVITIES_WT2 = Arrays.asList(DEFAULT_ISSUE_ACTIVITY_6, DEFAULT_ISSUE_ACTIVITY_7);

    public static final List<DefaultIssueActivityDto> DEFAULT_ISSUE_ACTIVITY_WT1_DTOS = Arrays.asList(
            DEFAULT_ISSUE_ACTIVITY_DTO_1, DEFAULT_ISSUE_ACTIVITY_DTO_5, DEFAULT_ISSUE_ACTIVITY_DTO_2, DEFAULT_ISSUE_ACTIVITY_DTO_4
    );

    public static final List<DefaultIssueActivity> ALL_DEFAULT_ISSUE_ACTIVITIES = Arrays.asList(
            DEFAULT_ISSUE_ACTIVITY_1, DEFAULT_ISSUE_ACTIVITY_6, DEFAULT_ISSUE_ACTIVITY_8, DEFAULT_ISSUE_ACTIVITY_5, DEFAULT_ISSUE_ACTIVITY_7,
            DEFAULT_ISSUE_ACTIVITY_2, DEFAULT_ISSUE_ACTIVITY_4
    );

    public static final List<IssueTypeWithDefaultActivities> ALL_DEFAULT_ISSUE_ACTIVITIES_DTOS = Arrays.asList(
            new IssueTypeWithDefaultActivities(IssueTypeTestData.issueTypeDto1(), Arrays.asList(
                    DEFAULT_ISSUE_ACTIVITY_DTO_1, DEFAULT_ISSUE_ACTIVITY_DTO_5, DEFAULT_ISSUE_ACTIVITY_DTO_2, DEFAULT_ISSUE_ACTIVITY_DTO_4
            )),
            new IssueTypeWithDefaultActivities(IssueTypeTestData.issueTypeDto2(), Arrays.asList(
                    DEFAULT_ISSUE_ACTIVITY_DTO_6, DEFAULT_ISSUE_ACTIVITY_DTO_7
            )),
            new IssueTypeWithDefaultActivities(IssueTypeTestData.issueTypeDto3(), Arrays.asList(
                    DEFAULT_ISSUE_ACTIVITY_DTO_8
            ))
    );

    private DefaultIssueActivityTestData() {
    }

    public static DefaultIssueActivity defaultIssueActivity(final Long id, final IssueType issueType, final ActivityType activityType, final int defaultDay,
                                                            final int version, final Date creatingDate) {
        final DefaultIssueActivity defaultIssueActivity = new DefaultIssueActivity();
        defaultIssueActivity.setId(id);
        defaultIssueActivity.setIssueType(issueType);
        defaultIssueActivity.setActivityType(activityType);
        defaultIssueActivity.setDefaultDay(defaultDay);
        defaultIssueActivity.setVersion(version);
        defaultIssueActivity.setCreatingDate(creatingDate);
        return defaultIssueActivity;
    }

    public static DefaultIssueActivityDto defaultIssueActivityDto(final Long id, final Long activityTypeId, final int defaultDay,
                                                                  final int version, final Date creatingDate, final Date defaultExecutionDate) {
        final DefaultIssueActivityDto defaultIssueActivityDto = new DefaultIssueActivityDto();
        defaultIssueActivityDto.setId(id);
        defaultIssueActivityDto.setActivityTypeId(activityTypeId);
        defaultIssueActivityDto.setDefaultDay(defaultDay);
        defaultIssueActivityDto.setVersion(version);
        defaultIssueActivityDto.setCreatingDate(creatingDate);
        defaultIssueActivityDto.setDefaultExecutionDate(defaultExecutionDate);
        return defaultIssueActivityDto;
    }
}
