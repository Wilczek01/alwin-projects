package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;
import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto;
import com.codersteam.alwin.jpa.activity.ActivityDetail;
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;

import java.util.List;

import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_5;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_8;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_1;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_3;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_5;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty1;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty10;
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_MESSAGE;
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_PHONE_NUMBER;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@SuppressWarnings("WeakerAccess")
public final class ActivityDetailTestData {

    public static final String ACTIVITY_COMMENT = "To jest jakiś komentarz";

    private static final Long ACTIVITY_DETAIL_ID_1 = 1L;
    private static final String ACTIVITY_DETAIL_VALUE_1 = "22 234-234-234";

    private static final Long ACTIVITY_DETAIL_ID_2 = 2L;
    private static final String ACTIVITY_DETAIL_VALUE_2 = "2017-03-23";

    private static final Long ACTIVITY_DETAIL_ID_102 = 102L;
    private static final String ACTIVITY_DETAIL_VALUE_102 = "100";
    public static final Integer ACTIVITY_DETAIL_PARSED_VALUE_102 = 100;

    public static final ActivityDetail TEST_ACTIVITY_DETAIL_1 = activityDetail(ACTIVITY_DETAIL_ID_1, ACTIVITY_DETAIL_VALUE_1, activityDetailProperty1());
    public static final ActivityDetail TEST_ACTIVITY_DETAIL_WITHOUT_ID_1 = activityDetail(null, ACTIVITY_DETAIL_VALUE_1,
            activityDetailProperty1());
    public static final ActivityDetailDto TEST_ACTIVITY_DETAIL_DTO_1 = activityDetailDto(ACTIVITY_DETAIL_ID_1, ACTIVITY_DETAIL_VALUE_1,
            ACTIVITY_DETAIL_PROPERTY_DTO_1, null);
    public static final ActivityDetailDto TEST_ACTIVITY_DETAIL_DTO_REQUIRED = activityDetailDto(ACTIVITY_DETAIL_ID_1, ACTIVITY_DETAIL_VALUE_1,
            ACTIVITY_DETAIL_PROPERTY_DTO_1, true);
    public static final ActivityDetail TEST_ACTIVITY_DETAIL_2 = activityDetail(ACTIVITY_DETAIL_ID_2, ACTIVITY_DETAIL_VALUE_2, ACTIVITY_DETAIL_PROPERTY_5);
    public static final ActivityDetail TEST_ACTIVITY_DETAIL_WITHOUT_ID_2 = activityDetail(null, ACTIVITY_DETAIL_VALUE_2,
            ACTIVITY_DETAIL_PROPERTY_5);
    public static final ActivityDetailDto TEST_ACTIVITY_DETAIL_DTO_2 = activityDetailDto(ACTIVITY_DETAIL_ID_2, ACTIVITY_DETAIL_VALUE_2,
            ACTIVITY_DETAIL_PROPERTY_DTO_5, null);

    private ActivityDetailTestData() {
    }

    public static ActivityDetailDto activityDetailDtoWithIntegerValue() {
        return activityDetailDto(ACTIVITY_DETAIL_ID_102, ACTIVITY_DETAIL_VALUE_102, ACTIVITY_DETAIL_PROPERTY_DTO_3, null);
    }

    public static List<ActivityDetail> outstandingSmsActivityDetails() {
        return asList(activityDetail(null, SMS_PHONE_NUMBER, activityDetailProperty1()), activityDetail(null, SMS_MESSAGE, ACTIVITY_DETAIL_PROPERTY_8));
    }

    public static List<ActivityDetail> commentActivityDetails() {
        return singletonList(activityDetail(null, ACTIVITY_COMMENT, activityDetailProperty10()));
    }

    public static ActivityDetail activityDetail(final Long id, final String value, final ActivityDetailProperty detailProperty) {
        final ActivityDetail activityDetail = new ActivityDetail();
        activityDetail.setId(id);
        activityDetail.setValue(value);
        activityDetail.setDetailProperty(detailProperty);
        return activityDetail;
    }

    public static ActivityDetailDto activityDetailDto(final Long id, final String value, final ActivityDetailPropertyDto detailProperty, final Boolean required) {
        final ActivityDetailDto activityDetailDto = new ActivityDetailDto();
        activityDetailDto.setId(id);
        activityDetailDto.setValue(value);
        activityDetailDto.setDetailProperty(detailProperty);
        activityDetailDto.setRequired(required);
        return activityDetailDto;
    }
}
