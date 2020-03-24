package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType;
import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDetailPropertyDto;
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.activity.ActivityTypeDetailProperty;

import static com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType.EXECUTED;
import static com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType.PLANNED;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_2;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_3;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_4;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_5;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_8;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_1;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_10;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_11;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_2;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_3;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_4;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_5;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ACTIVITY_DETAIL_PROPERTY_DTO_8;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_10;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_4;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty1;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty10;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty11;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public final class ActivityTypeDetailPropertyTestData implements IdLongTestData {

    private ActivityTypeDetailPropertyTestData() {
    }

    //entity
    public static ActivityTypeDetailProperty activityTypeDetailProperty1() {
        return activityTypeDetailProperty(ID_1, activityDetailProperty1());
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty2() {
        return activityTypeDetailProperty(ID_2, ACTIVITY_DETAIL_PROPERTY_2);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty3() {
        return activityTypeDetailProperty(ID_3, ACTIVITY_DETAIL_PROPERTY_3);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty4() {
        return activityTypeDetailProperty(ID_4, ACTIVITY_DETAIL_PROPERTY_4);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty5() {
        return activityTypeDetailProperty(ID_5, ACTIVITY_DETAIL_PROPERTY_5);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty8() {
        return activityTypeDetailProperty(ID_8, ACTIVITY_DETAIL_PROPERTY_8, PLANNED, true);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty10() {
        return activityTypeDetailProperty(ID_10, activityDetailProperty10());
    }


    public static ActivityTypeDetailProperty activityTypeDetailProperty14() {
        return activityTypeDetailProperty(ID_14, activityDetailProperty1(), EXECUTED, TRUE);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty15() {
        return activityTypeDetailProperty(ID_15, ACTIVITY_DETAIL_PROPERTY_2, EXECUTED, FALSE);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty16() {
        return activityTypeDetailProperty(ID_16, ACTIVITY_DETAIL_PROPERTY_3, EXECUTED, FALSE);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty17() {
        return activityTypeDetailProperty(ID_17, activityDetailProperty10(), PLANNED, FALSE);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty18() {
        return activityTypeDetailProperty(ID_18, activityDetailProperty10(), EXECUTED, FALSE);
    }

    public static ActivityTypeDetailProperty activityTypeDetailProperty21() {
        return activityTypeDetailProperty(ID_21, activityDetailProperty11(), EXECUTED, TRUE);
    }

    //dto
    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto1() {
        return activityTypeDetailPropertyDto(ID_1, ACTIVITY_DETAIL_PROPERTY_DTO_1);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto2() {
        return activityTypeDetailPropertyDto(ID_2, ACTIVITY_DETAIL_PROPERTY_DTO_2);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto3() {
        return activityTypeDetailPropertyDto(ID_3, ACTIVITY_DETAIL_PROPERTY_DTO_3);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto4() {
        return activityTypeDetailPropertyDto(ID_4, ACTIVITY_DETAIL_PROPERTY_DTO_4);
    }

    public static ActivityTypeDetailPropertyDto updatedActivityTypeDetailPropertyDto4() {
        return activityTypeDetailPropertyDto(ID_4, UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_4);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto5() {
        return activityTypeDetailPropertyDto(ID_5, ACTIVITY_DETAIL_PROPERTY_DTO_5);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto8() {
        return activityTypeDetailPropertyDto(ID_8, ACTIVITY_DETAIL_PROPERTY_DTO_8, PLANNED, true);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto10() {
        return activityTypeDetailPropertyDto(ID_10, ACTIVITY_DETAIL_PROPERTY_DTO_10);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto14() {
        return activityTypeDetailPropertyDto(ID_14, ACTIVITY_DETAIL_PROPERTY_DTO_1, EXECUTED, TRUE);
    }

    public static ActivityTypeDetailPropertyDto updatedActivityTypeDetailPropertyDto14() {
        return activityTypeDetailPropertyDto(ID_14, ACTIVITY_DETAIL_PROPERTY_DTO_1, EXECUTED, FALSE);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto15() {
        return activityTypeDetailPropertyDto(ID_15, ACTIVITY_DETAIL_PROPERTY_DTO_2, EXECUTED, FALSE);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto16() {
        return activityTypeDetailPropertyDto(ID_16, ACTIVITY_DETAIL_PROPERTY_DTO_3, EXECUTED, FALSE);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto17() {
        return activityTypeDetailPropertyDto(ID_17, ACTIVITY_DETAIL_PROPERTY_DTO_10, PLANNED, FALSE);
    }

    public static ActivityTypeDetailPropertyDto updatedActivityTypeDetailPropertyDto17() {
        return activityTypeDetailPropertyDto(ID_17, UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_10, PLANNED, FALSE);
    }

    public static ActivityTypeDetailPropertyDto updatedActivityTypeDetailPropertyDto18() {
        return activityTypeDetailPropertyDto(ID_18, UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_10, EXECUTED, FALSE);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto18() {
        return activityTypeDetailPropertyDto(ID_18, ACTIVITY_DETAIL_PROPERTY_DTO_10, EXECUTED, FALSE);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto21() {
        return activityTypeDetailPropertyDto(ID_21, ACTIVITY_DETAIL_PROPERTY_DTO_11, EXECUTED, TRUE);
    }

    public static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto100() {
        return activityTypeDetailPropertyDto(ID_100, ACTIVITY_DETAIL_PROPERTY_DTO_8, PLANNED, TRUE);
    }

    private static ActivityTypeDetailProperty activityTypeDetailProperty(final Long id, final ActivityDetailProperty activityDetailProperty) {
        return activityTypeDetailProperty(id, null, activityDetailProperty, PLANNED, FALSE);
    }

    private static ActivityTypeDetailProperty activityTypeDetailProperty(final Long id, final ActivityDetailProperty activityDetailProperty,
                                                                         final ActivityTypeDetailPropertyType state, final Boolean required) {
        return activityTypeDetailProperty(id, null, activityDetailProperty, state, required);
    }

    private static ActivityTypeDetailProperty activityTypeDetailProperty(final Long id, final ActivityType activityType, final ActivityDetailProperty
            activityDetailProperty, final ActivityTypeDetailPropertyType state, final Boolean required) {
        final ActivityTypeDetailProperty property = new ActivityTypeDetailProperty();
        property.setActivityDetailProperty(activityDetailProperty);
        property.setActivityType(activityType);
        property.setRequired(required);
        property.setState(state);
        property.setId(id);
        return property;
    }

    private static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto(final Long id, final ActivityDetailPropertyDto activityDetailProperty) {
        return activityTypeDetailPropertyDto(id, activityDetailProperty, PLANNED, FALSE);
    }

    private static ActivityTypeDetailPropertyDto activityTypeDetailPropertyDto(final Long id, final ActivityDetailPropertyDto activityDetailProperty,
                                                                               final ActivityTypeDetailPropertyType state, final Boolean required) {
        final ActivityTypeDetailPropertyDto property = new ActivityTypeDetailPropertyDto();
        property.setActivityDetailProperty(activityDetailProperty);
        property.setRequired(required);
        property.setState(state);
        property.setId(id);
        return property;
    }
}
