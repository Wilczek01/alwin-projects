package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDetailPropertyDictValueDto;
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;
import com.codersteam.alwin.jpa.activity.ActivityTypeDetailPropertyDictValue;
import com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.DATE_OF_PAYMENT;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.EMAIL_ADDRESS;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.FAILED_PHONE_CALL_REASON;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.MESSAGE_CONTENT;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.MESSAGE_LEFT;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.PHONE_CALL_LENGTH;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.PHONE_CALL_PERSON;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.PHONE_NUMBER;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.REMARK;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValue1;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValue2;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValueDto1;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValueDto2;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValueDto3;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValueDto100;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public final class ActivityDetailPropertyTestData {

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_1 = 1L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_1 = "Nr tel.";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_1 = "java.lang.String";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_1 = PHONE_NUMBER;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_2 = 2L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_2 = "Czy pozostawiono wiadomość";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_2 = "java.lang.Boolean";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_2 = MESSAGE_LEFT;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_3 = 3L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_3 = "Długość rozmowy";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_3 = "java.lang.Integer";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_3 = PHONE_CALL_LENGTH;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_4 = 4L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_4 = "Rozmówca";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_4 = "java.lang.String";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_4 = PHONE_CALL_PERSON;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_5 = 5L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_5 = "Termin zapłaty";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_5 = "java.util.Date";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_5 = DATE_OF_PAYMENT;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_8 = 8L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_8 = "Treść wiadomości";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_8 = "java.lang.String";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_8 = MESSAGE_CONTENT;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_9 = 9L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_9 = "Adres email";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_9 = "java.lang.String";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_9 = EMAIL_ADDRESS;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_10 = 10L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_10 = "Komentarz";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_10 = "java.lang.String";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_10 = REMARK;

    public static final Long ACTIVITY_DETAIL_PROPERTY_ID_11 = 11L;
    public static final String ACTIVITY_DETAIL_PROPERTY_NAME_11 = "Przyczyna nieudanego kontaktu tel.";
    public static final String ACTIVITY_DETAIL_PROPERTY_TYPE_11 = "java.lang.String";
    public static final ActivityDetailPropertyKey ACTIVITY_DETAIL_PROPERTY_KEY_11 = FAILED_PHONE_CALL_REASON;

    public static ActivityDetailProperty activityDetailProperty1() {
        return activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_1,
                ACTIVITY_DETAIL_PROPERTY_NAME_1, ACTIVITY_DETAIL_PROPERTY_TYPE_1, ACTIVITY_DETAIL_PROPERTY_KEY_1);
    }

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_1 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_1,
            ACTIVITY_DETAIL_PROPERTY_NAME_1, ACTIVITY_DETAIL_PROPERTY_TYPE_1, ACTIVITY_DETAIL_PROPERTY_KEY_1);

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_2 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_2,
            ACTIVITY_DETAIL_PROPERTY_NAME_2, ACTIVITY_DETAIL_PROPERTY_TYPE_2, ACTIVITY_DETAIL_PROPERTY_KEY_2);

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_3 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_3,
            ACTIVITY_DETAIL_PROPERTY_NAME_3, ACTIVITY_DETAIL_PROPERTY_TYPE_3, ACTIVITY_DETAIL_PROPERTY_KEY_3);

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_4 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_4,
            ACTIVITY_DETAIL_PROPERTY_NAME_4, ACTIVITY_DETAIL_PROPERTY_TYPE_4, ACTIVITY_DETAIL_PROPERTY_KEY_4);

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_5 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_5,
            ACTIVITY_DETAIL_PROPERTY_NAME_5, ACTIVITY_DETAIL_PROPERTY_TYPE_5, ACTIVITY_DETAIL_PROPERTY_KEY_5);

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_8 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_8,
            ACTIVITY_DETAIL_PROPERTY_NAME_8, ACTIVITY_DETAIL_PROPERTY_TYPE_8, ACTIVITY_DETAIL_PROPERTY_KEY_8);

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_9 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_9,
            ACTIVITY_DETAIL_PROPERTY_NAME_9, ACTIVITY_DETAIL_PROPERTY_TYPE_9, ACTIVITY_DETAIL_PROPERTY_KEY_9);

    public static final ActivityDetailProperty ACTIVITY_DETAIL_PROPERTY_10 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_10,
            ACTIVITY_DETAIL_PROPERTY_NAME_10, ACTIVITY_DETAIL_PROPERTY_TYPE_10, ACTIVITY_DETAIL_PROPERTY_KEY_10);

    public static final ActivityDetailProperty UPDATED_ACTIVITY_DETAIL_PROPERTY_10 = activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_10,
            ACTIVITY_DETAIL_PROPERTY_NAME_10, ACTIVITY_DETAIL_PROPERTY_TYPE_10, ACTIVITY_DETAIL_PROPERTY_KEY_10,
            new HashSet<>(asList(activityTypeDetailPropertyDictValue1(ACTIVITY_DETAIL_PROPERTY_10), activityTypeDetailPropertyDictValue2(ACTIVITY_DETAIL_PROPERTY_10))));

    public static ActivityDetailProperty activityDetailProperty10() {
        return activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_10, ACTIVITY_DETAIL_PROPERTY_NAME_10, ACTIVITY_DETAIL_PROPERTY_TYPE_10,
                ACTIVITY_DETAIL_PROPERTY_KEY_10);
    }

    public static ActivityDetailProperty activityDetailProperty11() {
        return activityDetailProperty(ACTIVITY_DETAIL_PROPERTY_ID_11, ACTIVITY_DETAIL_PROPERTY_NAME_11, ACTIVITY_DETAIL_PROPERTY_TYPE_11,
                ACTIVITY_DETAIL_PROPERTY_KEY_11);
    }

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_1 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_1,
            ACTIVITY_DETAIL_PROPERTY_NAME_1, ACTIVITY_DETAIL_PROPERTY_TYPE_1, ACTIVITY_DETAIL_PROPERTY_KEY_1.name());

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_2 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_2,
            ACTIVITY_DETAIL_PROPERTY_NAME_2, ACTIVITY_DETAIL_PROPERTY_TYPE_2, ACTIVITY_DETAIL_PROPERTY_KEY_2.name());

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_3 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_3,
            ACTIVITY_DETAIL_PROPERTY_NAME_3, ACTIVITY_DETAIL_PROPERTY_TYPE_3, ACTIVITY_DETAIL_PROPERTY_KEY_3.name());

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_4 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_4,
            ACTIVITY_DETAIL_PROPERTY_NAME_4, ACTIVITY_DETAIL_PROPERTY_TYPE_4, ACTIVITY_DETAIL_PROPERTY_KEY_4.name());

    public static final ActivityDetailPropertyDto UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_4 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_4,
            ACTIVITY_DETAIL_PROPERTY_NAME_4, ACTIVITY_DETAIL_PROPERTY_TYPE_4, ACTIVITY_DETAIL_PROPERTY_KEY_4.name(),
            asList(activityTypeDetailPropertyDictValueDto3(), activityTypeDetailPropertyDictValueDto100()));

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_5 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_5,
            ACTIVITY_DETAIL_PROPERTY_NAME_5, ACTIVITY_DETAIL_PROPERTY_TYPE_5, ACTIVITY_DETAIL_PROPERTY_KEY_5.name());

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_8 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_8,
            ACTIVITY_DETAIL_PROPERTY_NAME_8, ACTIVITY_DETAIL_PROPERTY_TYPE_8, ACTIVITY_DETAIL_PROPERTY_KEY_8.name());

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_9 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_9,
            ACTIVITY_DETAIL_PROPERTY_NAME_9, ACTIVITY_DETAIL_PROPERTY_TYPE_9, ACTIVITY_DETAIL_PROPERTY_KEY_9.name());

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_10 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_10,
            ACTIVITY_DETAIL_PROPERTY_NAME_10, ACTIVITY_DETAIL_PROPERTY_TYPE_10, ACTIVITY_DETAIL_PROPERTY_KEY_10.name());

    public static final ActivityDetailPropertyDto UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_10 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_10,
            ACTIVITY_DETAIL_PROPERTY_NAME_10, ACTIVITY_DETAIL_PROPERTY_TYPE_10, ACTIVITY_DETAIL_PROPERTY_KEY_10.name(),
            asList(activityTypeDetailPropertyDictValueDto1(), activityTypeDetailPropertyDictValueDto2()));

    public static final ActivityDetailPropertyDto ACTIVITY_DETAIL_PROPERTY_DTO_11 = activityDetailPropertyDto(ACTIVITY_DETAIL_PROPERTY_ID_11,
            ACTIVITY_DETAIL_PROPERTY_NAME_11, ACTIVITY_DETAIL_PROPERTY_TYPE_11, ACTIVITY_DETAIL_PROPERTY_KEY_11.name());

    public static final List<ActivityDetailProperty> ALL_ACTIVITY_DETAIL_PROPERTIES = asList(ACTIVITY_DETAIL_PROPERTY_1, ACTIVITY_DETAIL_PROPERTY_2,
            ACTIVITY_DETAIL_PROPERTY_3, ACTIVITY_DETAIL_PROPERTY_4, ACTIVITY_DETAIL_PROPERTY_5, ACTIVITY_DETAIL_PROPERTY_8, ACTIVITY_DETAIL_PROPERTY_9,
            activityDetailProperty10(), activityDetailProperty11());

    public static final List<ActivityDetailPropertyDto> ALL_ACTIVITY_DETAIL_PROPERTIES_DTOS = asList(ACTIVITY_DETAIL_PROPERTY_DTO_1,
            ACTIVITY_DETAIL_PROPERTY_DTO_2, ACTIVITY_DETAIL_PROPERTY_DTO_3, ACTIVITY_DETAIL_PROPERTY_DTO_4, ACTIVITY_DETAIL_PROPERTY_DTO_5,
            ACTIVITY_DETAIL_PROPERTY_DTO_8, ACTIVITY_DETAIL_PROPERTY_DTO_9, ACTIVITY_DETAIL_PROPERTY_DTO_10, ACTIVITY_DETAIL_PROPERTY_DTO_11);

    private ActivityDetailPropertyTestData() {
    }

    public static ActivityDetailProperty activityDetailProperty(final Long id, final String name, final String type, final ActivityDetailPropertyKey key,
                                                                final Set<ActivityTypeDetailPropertyDictValue> dictionaryValues) {
        final ActivityDetailProperty activityDetailProperty = new ActivityDetailProperty();
        activityDetailProperty.setId(id);
        activityDetailProperty.setName(name);
        activityDetailProperty.setType(type);
        activityDetailProperty.setKey(key);
        activityDetailProperty.setDictionaryValues(dictionaryValues);
        return activityDetailProperty;
    }

    public static ActivityDetailProperty activityDetailProperty(final Long id, final String name, final String type, final ActivityDetailPropertyKey key) {
        return activityDetailProperty(id, name, type, key, null);
    }

    private static ActivityDetailPropertyDto activityDetailPropertyDto(final Long id, final String name, final String type, final String key) {
        return activityDetailPropertyDto(id, name, type, key, emptyList());
    }

    private static ActivityDetailPropertyDto activityDetailPropertyDto(final Long id, final String name, final String type, final String key, final List<ActivityTypeDetailPropertyDictValueDto> dictionaryValues) {
        final ActivityDetailPropertyDto activityDetailPropertyDto = new ActivityDetailPropertyDto();
        activityDetailPropertyDto.setId(id);
        activityDetailPropertyDto.setName(name);
        activityDetailPropertyDto.setType(type);
        activityDetailPropertyDto.setKey(key);
        activityDetailPropertyDto.setDictionaryValues(dictionaryValues);
        return activityDetailPropertyDto;
    }
}
