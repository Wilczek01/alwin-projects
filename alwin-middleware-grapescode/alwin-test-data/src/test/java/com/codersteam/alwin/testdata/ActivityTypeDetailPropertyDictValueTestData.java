package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.activity.ActivityTypeDetailPropertyDictValueDto;
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;
import com.codersteam.alwin.jpa.activity.ActivityTypeDetailPropertyDictValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Dane testowe dla wartości słownikowych cechy dodatkowej czynności zlecenia
 */
public final class ActivityTypeDetailPropertyDictValueTestData {

    private static final Long ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_1 = 1l;
    private static final String ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_1 = "Nie odbiera";

    private static final Long ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_2 = 2l;
    private static final String ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_2 = "Tel. Wyłączony";

    private static final Long ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_3 = 3l;
    private static final String ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_3 = "Nie ma takiego numeru";

    private static final Long ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_4 = 4l;
    private static final String ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_4 = "Inna";

    private static final Long ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_100 = 100l;
    private static final String ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_100 = "Nowa wartość";

    private ActivityTypeDetailPropertyDictValueTestData() {
    }

    public static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue1() {
        return activityTypeDetailPropertyDictValue(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_1, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_1);
    }

    public static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue1(final ActivityDetailProperty activityDetailProperty) {
        return activityTypeDetailPropertyDictValue(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_1, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_1, activityDetailProperty);
    }

    public static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue2() {
        return activityTypeDetailPropertyDictValue(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_2, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_2);
    }

    public static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue2(final ActivityDetailProperty activityDetailProperty) {
        return activityTypeDetailPropertyDictValue(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_2, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_2, activityDetailProperty);
    }

    public static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue3() {
        return activityTypeDetailPropertyDictValue(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_3, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_3);
    }

    public static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue4() {
        return activityTypeDetailPropertyDictValue(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_4, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_4);
    }

    public static Set<ActivityTypeDetailPropertyDictValue> activityTypeDetailPropertyDictValuesForActivityDetailProperty11() {
        return new HashSet<>(Arrays.asList(activityTypeDetailPropertyDictValue1(), activityTypeDetailPropertyDictValue2(),
                activityTypeDetailPropertyDictValue3(), activityTypeDetailPropertyDictValue4()));
    }

    public static ActivityTypeDetailPropertyDictValueDto activityTypeDetailPropertyDictValueDto1() {
        return activityTypeDetailPropertyDictValueDto(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_1, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_1);
    }

    public static ActivityTypeDetailPropertyDictValueDto activityTypeDetailPropertyDictValueDto2() {
        return activityTypeDetailPropertyDictValueDto(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_2, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_2);
    }

    public static ActivityTypeDetailPropertyDictValueDto activityTypeDetailPropertyDictValueDto3() {
        return activityTypeDetailPropertyDictValueDto(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_3, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_3);
    }

    public static ActivityTypeDetailPropertyDictValueDto activityTypeDetailPropertyDictValueDto4() {
        return activityTypeDetailPropertyDictValueDto(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_4, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_4);
    }

    public static ActivityTypeDetailPropertyDictValueDto activityTypeDetailPropertyDictValueDto100() {
        return activityTypeDetailPropertyDictValueDto(ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_ID_100, ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_VALUE_100);
    }

    private static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue(final Long id, final String value) {
        return activityTypeDetailPropertyDictValue(id, value, null);
    }

    private static ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue(final Long id, final String value, final ActivityDetailProperty detailProperty) {
        final ActivityTypeDetailPropertyDictValue activityTypeDetailPropertyDictValue = new ActivityTypeDetailPropertyDictValue();
        activityTypeDetailPropertyDictValue.setId(id);
        activityTypeDetailPropertyDictValue.setValue(value);
        activityTypeDetailPropertyDictValue.setActivityDetailProperty(detailProperty);
        return activityTypeDetailPropertyDictValue;
    }

    private static ActivityTypeDetailPropertyDictValueDto activityTypeDetailPropertyDictValueDto(final Long id, final String value) {
        final ActivityTypeDetailPropertyDictValueDto activityTypeDetailPropertyDictValueDto = new ActivityTypeDetailPropertyDictValueDto();
        activityTypeDetailPropertyDictValueDto.setId(id);
        activityTypeDetailPropertyDictValueDto.setValue(value);
        return activityTypeDetailPropertyDictValueDto;
    }
}
