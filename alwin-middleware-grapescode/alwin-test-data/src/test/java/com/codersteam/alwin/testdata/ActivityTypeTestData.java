package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDetailPropertyDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeWithDetailPropertiesDto;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.activity.ActivityTypeDetailProperty;
import com.codersteam.alwin.jpa.type.ActivityTypeKey;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.jpa.type.ActivityTypeKey.COMMENT;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.CONTRACT_TERMINATION;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.FAILED_PHONE_CALL_ATTEMPT;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.FIELD_VISIT;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.FIRST_DEMAND_PAYMENT;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.FRAUD_SUSPECTED;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.INCOMING_PHONE_CALL;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.LAST_DEMAND_PAYMENT;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.OUTGOING_PHONE_CALL;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.OUTGOING_SMS;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.PAYMENT_COLLECTED_CONFIRMATION;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.RETURN_SUBJECT_DEMAND;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.SUBJECT_COLLECTED;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_10;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyTestData.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public final class ActivityTypeTestData {

    public static final Long ACTIVITY_TYPE_ID_1 = 1L;
    public static final String ACTIVITY_TYPE_NAME_1 = "Telefon wychodzący";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_1 = OUTGOING_PHONE_CALL;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_1 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_1 = new BigDecimal("0.00");
    public static final BigDecimal MODIFIED_ACTIVITY_TYPE_CHARGE_MIN_1 = new BigDecimal("100.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_1 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1 = true;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_1 = true;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_1 = true;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_1 = true;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_1 = new HashSet<>(asList(activityTypeDetailProperty1(),
            activityTypeDetailProperty2(), activityTypeDetailProperty3(), activityTypeDetailProperty4(), activityTypeDetailProperty14(),
            activityTypeDetailProperty15(), activityTypeDetailProperty16(), activityTypeDetailProperty17(), activityTypeDetailProperty18()));
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_1 = new HashSet<>(asList(activityTypeDetailPropertyDto1(),
            activityTypeDetailPropertyDto2(), activityTypeDetailPropertyDto3(), activityTypeDetailPropertyDto4(), activityTypeDetailPropertyDto14(),
            activityTypeDetailPropertyDto15(), activityTypeDetailPropertyDto16(), activityTypeDetailPropertyDto17(), activityTypeDetailPropertyDto18()));

    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_UPDATED_DETAIL_PROPERTY_DTOS_1 = new HashSet<>(asList(activityTypeDetailPropertyDto1(),
            activityTypeDetailPropertyDto2(), activityTypeDetailPropertyDto3(), activityTypeDetailPropertyDto4(), updatedActivityTypeDetailPropertyDto14(),
            activityTypeDetailPropertyDto15(), activityTypeDetailPropertyDto16(), activityTypeDetailPropertyDto17(), activityTypeDetailPropertyDto18()));

    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_UPDATED_DETAIL_PROPERTY_WITH_DICTIONARY_VALUES_DTOS_1 = new HashSet<>(asList(activityTypeDetailPropertyDto1(),
            activityTypeDetailPropertyDto2(), activityTypeDetailPropertyDto3(), updatedActivityTypeDetailPropertyDto4(), activityTypeDetailPropertyDto14(),
            activityTypeDetailPropertyDto15(), activityTypeDetailPropertyDto16(), updatedActivityTypeDetailPropertyDto17(), updatedActivityTypeDetailPropertyDto18()));

    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_ADDED_DETAIL_PROPERTY_DTOS_1 = new HashSet<>(asList(activityTypeDetailPropertyDto1(),
            activityTypeDetailPropertyDto2(), activityTypeDetailPropertyDto3(), activityTypeDetailPropertyDto4(), activityTypeDetailPropertyDto14(),
            activityTypeDetailPropertyDto15(), activityTypeDetailPropertyDto16(), activityTypeDetailPropertyDto17(), activityTypeDetailPropertyDto18(),
            activityTypeDetailPropertyDto100()));

    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_DELETED_DETAIL_PROPERTY_DTOS_1 =
            new HashSet<>(asList(activityTypeDetailPropertyDto1(), activityTypeDetailPropertyDto2(), activityTypeDetailPropertyDto3(),
                    activityTypeDetailPropertyDto4(), activityTypeDetailPropertyDto14(), activityTypeDetailPropertyDto15(), activityTypeDetailPropertyDto18()));

    public static final Long ACTIVITY_TYPE_ID_2 = 2L;
    public static final String ACTIVITY_TYPE_NAME_2 = "Wezwanie do zapłaty (podstawowe)";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_2 = FIRST_DEMAND_PAYMENT;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_2 = true;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_2 = new BigDecimal("50.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_2 = new BigDecimal("203.33");
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_2 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_2 = true;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_2 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_2 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_2 = new HashSet<>(asList(activityTypeDetailProperty5(),
            activityTypeDetailProperty10()));
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_2 = new HashSet<>(asList(activityTypeDetailPropertyDto5(),
            activityTypeDetailPropertyDto10()));

    public static final Long ACTIVITY_TYPE_ID_3 = 3L;
    public static final String ACTIVITY_TYPE_NAME_3 = "Wezwanie do zapłaty (ostateczne)";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_3 = LAST_DEMAND_PAYMENT;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_3 = true;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_3 = new BigDecimal("150.33");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_3 = new BigDecimal("100.99");
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_3 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_3 = true;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_3 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_3 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_3 = new HashSet<>(asList(activityTypeDetailProperty5()));
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_3 = new HashSet<>(asList(activityTypeDetailPropertyDto5()));

    public static final Long ACTIVITY_TYPE_ID_4 = 4L;
    public static final String ACTIVITY_TYPE_NAME_4 = "Wiadomość SMS wychodząca";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_4 = OUTGOING_SMS;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_4 = true;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_4 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_4 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_4 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_4 = true;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_4 = true;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_4 = true;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_4 = new HashSet<>(asList(activityTypeDetailProperty1(),
            activityTypeDetailProperty8()));
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_4 = new HashSet<>(asList(activityTypeDetailPropertyDto1(),
            activityTypeDetailPropertyDto8()));

    public static final Long ACTIVITY_TYPE_ID_5 = 5L;
    public static final String ACTIVITY_TYPE_NAME_5 = "Telefon przychodzący";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_5 = INCOMING_PHONE_CALL;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_5 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_5 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_5 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_5 = true;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_5 = true;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_5 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_5 = true;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_5 = new HashSet<>(asList(activityTypeDetailProperty1(),
            activityTypeDetailProperty3(), activityTypeDetailProperty4()));
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_5 = new HashSet<>(asList(activityTypeDetailPropertyDto1(),
            activityTypeDetailPropertyDto3(), activityTypeDetailPropertyDto4()));

    public static final Long ACTIVITY_TYPE_ID_6 = 6L;
    public static final String ACTIVITY_TYPE_NAME_6 = "Komentarz";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_6 = COMMENT;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_6 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_6 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_6 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_6 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_6 = false;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_6 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_6 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_6 = new HashSet<>();
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_6 = new HashSet<>();

    public static final Long ACTIVITY_TYPE_ID_7 = 7L;
    public static final String ACTIVITY_TYPE_NAME_7 = "Wizyta terenowa";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_7 = FIELD_VISIT;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_7 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_7 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_7 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_7 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_7 = false;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_7 = true;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_7 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_7 = new HashSet<>();
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_7 = new HashSet<>();

    public static final Long ACTIVITY_TYPE_ID_8 = 8L;
    public static final String ACTIVITY_TYPE_NAME_8 = "Wypowiedzenie warunkowe umowy";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_8 = CONTRACT_TERMINATION;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_8 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_8 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_8 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_8 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_8 = false;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_8 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_8 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_8 = new HashSet<>();
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_8 = new HashSet<>();

    public static final Long ACTIVITY_TYPE_ID_9 = 9L;
    public static final String ACTIVITY_TYPE_NAME_9 = "Wezwanie do zwrotu przedmiotu";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_9 = RETURN_SUBJECT_DEMAND;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_9 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_9 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_9 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_9 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_9 = false;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_9 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_9 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_9 = new HashSet<>();
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_9 = new HashSet<>();

    public static final Long ACTIVITY_TYPE_ID_10 = 10L;
    public static final String ACTIVITY_TYPE_NAME_10 = "Podejrzenie fraudu";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_10 = FRAUD_SUSPECTED;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_10 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_10 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_10 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_10 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_10 = false;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_10 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_10 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_10 = new HashSet<>();
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_10 = new HashSet<>();

    public static final Long ACTIVITY_TYPE_ID_11 = 11L;
    public static final String ACTIVITY_TYPE_NAME_11 = "Odbiór przedmiotu leasingu";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_11 = SUBJECT_COLLECTED;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_11 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_11 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_11 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_11 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_11 = false;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_11 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_11 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_11 = new HashSet<>();
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_11 = new HashSet<>();

    public static final Long ACTIVITY_TYPE_ID_12 = 12L;
    public static final String ACTIVITY_TYPE_NAME_12 = "Potwierdzenie wpłaty klienta";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_12 = PAYMENT_COLLECTED_CONFIRMATION;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_12 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_12 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_12 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_12 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_12 = false;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_12 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_12 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_12 = new HashSet<>();
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_12 = new HashSet<>();

    public static final Long ACTIVITY_TYPE_ID_14 = 14L;
    public static final String ACTIVITY_TYPE_NAME_14 = "Nieudana próba kontaktu tel.";
    public static final ActivityTypeKey ACTIVITY_TYPE_KEY_14 = FAILED_PHONE_CALL_ATTEMPT;
    public static final Boolean ACTIVITY_TYPE_MAY_BE_AUTOMATED_14 = false;
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MIN_14 = new BigDecimal("0.00");
    public static final BigDecimal ACTIVITY_TYPE_CHARGE_MAX_14 = null;
    public static final Boolean ACTIVITY_TYPE_MAY_HAVE_DECLARATION_14 = false;
    public static final Boolean ACTIVITY_TYPE_SPECIFIC_14 = true;
    public static final Boolean ACTIVITY_TYPE_CAN_BE_PLANNED_14 = false;
    public static final Boolean ACTIVITY_TYPE_CUSTOMER_CONTACT_14 = false;
    public static final Set<ActivityTypeDetailProperty> ACTIVITY_TYPE_DETAIL_PROPERTIES_14 = new HashSet<>(singletonList(activityTypeDetailProperty21()));
    public static final Set<ActivityTypeDetailPropertyDto> ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_14 = new HashSet<>(asList(activityTypeDetailPropertyDto21()));

    public static final ActivityType TEST_ACTIVITY_TYPE_1 = activityType1();

    public static ActivityType activityType1() {
        return activityType(ACTIVITY_TYPE_ID_1, ACTIVITY_TYPE_NAME_1, ACTIVITY_TYPE_MAY_BE_AUTOMATED_1, ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1,
                ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1, ACTIVITY_TYPE_CAN_BE_PLANNED_1, ACTIVITY_TYPE_DETAIL_PROPERTIES_1,
                ACTIVITY_TYPE_CUSTOMER_CONTACT_1, ACTIVITY_TYPE_KEY_1);
    }

    public static final ActivityType TEST_ACTIVITY_TYPE_2 = activityType(ACTIVITY_TYPE_ID_2, ACTIVITY_TYPE_NAME_2, ACTIVITY_TYPE_MAY_BE_AUTOMATED_2,
            ACTIVITY_TYPE_CHARGE_MIN_2, ACTIVITY_TYPE_CHARGE_MAX_2, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_2, ACTIVITY_TYPE_SPECIFIC_2,
            ACTIVITY_TYPE_CAN_BE_PLANNED_2, ACTIVITY_TYPE_DETAIL_PROPERTIES_2, ACTIVITY_TYPE_CUSTOMER_CONTACT_2, ACTIVITY_TYPE_KEY_2);
    public static final ActivityType TEST_ACTIVITY_TYPE_3 = activityType(ACTIVITY_TYPE_ID_3, ACTIVITY_TYPE_NAME_3, ACTIVITY_TYPE_MAY_BE_AUTOMATED_3,
            ACTIVITY_TYPE_CHARGE_MIN_3, ACTIVITY_TYPE_CHARGE_MAX_3, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_3, ACTIVITY_TYPE_SPECIFIC_3,
            ACTIVITY_TYPE_CAN_BE_PLANNED_3, ACTIVITY_TYPE_DETAIL_PROPERTIES_3, ACTIVITY_TYPE_CUSTOMER_CONTACT_3, ACTIVITY_TYPE_KEY_3);
    public static final ActivityType TEST_ACTIVITY_TYPE_4 = activityType4();
    public static final ActivityType TEST_ACTIVITY_TYPE_14 = activityType14();

    public static final ActivityType TEST_ACTIVITY_TYPE_7 = activityType(ACTIVITY_TYPE_ID_7, ACTIVITY_TYPE_NAME_7, ACTIVITY_TYPE_MAY_BE_AUTOMATED_7,
            ACTIVITY_TYPE_CHARGE_MIN_7, ACTIVITY_TYPE_CHARGE_MAX_7, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_7, ACTIVITY_TYPE_SPECIFIC_7,
            ACTIVITY_TYPE_CAN_BE_PLANNED_7, ACTIVITY_TYPE_DETAIL_PROPERTIES_7, ACTIVITY_TYPE_CUSTOMER_CONTACT_7, ACTIVITY_TYPE_KEY_7);

    public static ActivityType activityType4() {
        return activityType(ACTIVITY_TYPE_ID_4, ACTIVITY_TYPE_NAME_4, ACTIVITY_TYPE_MAY_BE_AUTOMATED_4,
                ACTIVITY_TYPE_CHARGE_MIN_4, ACTIVITY_TYPE_CHARGE_MAX_4, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_4, ACTIVITY_TYPE_SPECIFIC_4,
                ACTIVITY_TYPE_CAN_BE_PLANNED_4, ACTIVITY_TYPE_DETAIL_PROPERTIES_4, ACTIVITY_TYPE_CUSTOMER_CONTACT_4, ACTIVITY_TYPE_KEY_4);
    }

    public static final ActivityType TEST_ACTIVITY_TYPE_5 = activityType(ACTIVITY_TYPE_ID_5, ACTIVITY_TYPE_NAME_5, ACTIVITY_TYPE_MAY_BE_AUTOMATED_5,
            ACTIVITY_TYPE_CHARGE_MIN_5, ACTIVITY_TYPE_CHARGE_MAX_5, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_5, ACTIVITY_TYPE_SPECIFIC_5,
            ACTIVITY_TYPE_CAN_BE_PLANNED_5, ACTIVITY_TYPE_DETAIL_PROPERTIES_5, ACTIVITY_TYPE_CUSTOMER_CONTACT_5, ACTIVITY_TYPE_KEY_5);

    public static final ActivityTypeDto TEST_ACTIVITY_TYPE_DTO_1 = activityTypeDto(ACTIVITY_TYPE_ID_1, ACTIVITY_TYPE_NAME_1, ACTIVITY_TYPE_KEY_1.name(),
            ACTIVITY_TYPE_MAY_BE_AUTOMATED_1,
            ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1,
            ACTIVITY_TYPE_CAN_BE_PLANNED_1, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_1, ACTIVITY_TYPE_CUSTOMER_CONTACT_1);

    public static final ActivityTypeWithDetailPropertiesDto TEST_ACTIVITY_TYPE_WITH_DETAIL_PROPERTIES_DTO_1 = activityTypeWithDetailPropertiesDto(ACTIVITY_TYPE_ID_1,
            ACTIVITY_TYPE_NAME_1, ACTIVITY_TYPE_KEY_1.name(), ACTIVITY_TYPE_MAY_BE_AUTOMATED_1,
            ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1,
            ACTIVITY_TYPE_CAN_BE_PLANNED_1, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_1, ACTIVITY_TYPE_CUSTOMER_CONTACT_1, singletonList(UPDATED_ACTIVITY_DETAIL_PROPERTY_DTO_10));

    public static final ActivityTypeWithDetailPropertiesDto TEST_ACTIVITY_TYPE_WITHOUT_DETAIL_PROPERTIES_DTO_1 = activityTypeWithDetailPropertiesDto(ACTIVITY_TYPE_ID_1,
            ACTIVITY_TYPE_NAME_1, ACTIVITY_TYPE_KEY_1.name(), ACTIVITY_TYPE_MAY_BE_AUTOMATED_1,
            ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1,
            ACTIVITY_TYPE_CAN_BE_PLANNED_1, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_1, ACTIVITY_TYPE_CUSTOMER_CONTACT_1, emptyList());

    public static final ActivityTypeDto MODIFIED_TEST_ACTIVITY_TYPE_DTO_1 = activityTypeDto(ACTIVITY_TYPE_ID_1, ACTIVITY_TYPE_NAME_1,
            ACTIVITY_TYPE_KEY_1.name(), ACTIVITY_TYPE_MAY_BE_AUTOMATED_1, MODIFIED_ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1,
            !ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1, !ACTIVITY_TYPE_CAN_BE_PLANNED_1, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_1,
            !ACTIVITY_TYPE_CUSTOMER_CONTACT_1);

    public static final ActivityTypeDto MODIFIED_TEST_ACTIVITY_TYPE_WITH_UPDATED_DETAIL_PROPERTY_DTO_1 = activityTypeDto(ACTIVITY_TYPE_ID_1, ACTIVITY_TYPE_NAME_1,
            ACTIVITY_TYPE_KEY_1.name(), ACTIVITY_TYPE_MAY_BE_AUTOMATED_1, MODIFIED_ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1,
            !ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1, !ACTIVITY_TYPE_CAN_BE_PLANNED_1,
            ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_UPDATED_DETAIL_PROPERTY_DTOS_1,
            !ACTIVITY_TYPE_CUSTOMER_CONTACT_1);

    public static final ActivityTypeDto MODIFIED_TEST_ACTIVITY_TYPE_WITH_ADDED_DETAIL_PROPERTY_DTO_1 = activityTypeDto(ACTIVITY_TYPE_ID_1, ACTIVITY_TYPE_NAME_1,
            ACTIVITY_TYPE_KEY_1.name(), ACTIVITY_TYPE_MAY_BE_AUTOMATED_1, MODIFIED_ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1,
            !ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1, !ACTIVITY_TYPE_CAN_BE_PLANNED_1,
            ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_ADDED_DETAIL_PROPERTY_DTOS_1,
            !ACTIVITY_TYPE_CUSTOMER_CONTACT_1);

    public static final ActivityTypeDto MODIFIED_TEST_ACTIVITY_TYPE_WITH_DELETED_DETAIL_PROPERTY_DTO_1 = activityTypeDto(ACTIVITY_TYPE_ID_1,
            ACTIVITY_TYPE_NAME_1, ACTIVITY_TYPE_KEY_1.name(), ACTIVITY_TYPE_MAY_BE_AUTOMATED_1, MODIFIED_ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1,
            !ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1, !ACTIVITY_TYPE_CAN_BE_PLANNED_1,
            ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_DELETED_DETAIL_PROPERTY_DTOS_1,
            !ACTIVITY_TYPE_CUSTOMER_CONTACT_1);

    public static final ActivityTypeDto MODIFIED_TEST_ACTIVITY_TYPE_WITH_UPDATED_DETAIL_PROPERTY_WITH_DICTIONARY_VALUES_DTO_1 = activityTypeDto(ACTIVITY_TYPE_ID_1, ACTIVITY_TYPE_NAME_1,
            ACTIVITY_TYPE_KEY_1.name(), ACTIVITY_TYPE_MAY_BE_AUTOMATED_1, MODIFIED_ACTIVITY_TYPE_CHARGE_MIN_1, ACTIVITY_TYPE_CHARGE_MAX_1,
            !ACTIVITY_TYPE_MAY_HAVE_DECLARATION_1, ACTIVITY_TYPE_SPECIFIC_1, !ACTIVITY_TYPE_CAN_BE_PLANNED_1,
            ACTIVITY_TYPE_DETAIL_PROPERTY_WITH_UPDATED_DETAIL_PROPERTY_WITH_DICTIONARY_VALUES_DTOS_1,
            !ACTIVITY_TYPE_CUSTOMER_CONTACT_1);

    public static final ActivityTypeDto TEST_ACTIVITY_TYPE_DTO_2 = activityTypeDto(ACTIVITY_TYPE_ID_2, ACTIVITY_TYPE_NAME_2, ACTIVITY_TYPE_KEY_2.name(),
            ACTIVITY_TYPE_MAY_BE_AUTOMATED_2,
            ACTIVITY_TYPE_CHARGE_MIN_2, ACTIVITY_TYPE_CHARGE_MAX_2, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_2, ACTIVITY_TYPE_SPECIFIC_2,
            ACTIVITY_TYPE_CAN_BE_PLANNED_2, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_2, ACTIVITY_TYPE_CUSTOMER_CONTACT_2);

    public static final ActivityTypeDto TEST_ACTIVITY_TYPE_DTO_3 = activityTypeDto(ACTIVITY_TYPE_ID_3, ACTIVITY_TYPE_NAME_3, ACTIVITY_TYPE_KEY_3.name(),
            ACTIVITY_TYPE_MAY_BE_AUTOMATED_3,
            ACTIVITY_TYPE_CHARGE_MIN_3, ACTIVITY_TYPE_CHARGE_MAX_3, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_3, ACTIVITY_TYPE_SPECIFIC_3,
            ACTIVITY_TYPE_CAN_BE_PLANNED_3, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_3, ACTIVITY_TYPE_CUSTOMER_CONTACT_3);

    public static final ActivityTypeDto TEST_ACTIVITY_TYPE_DTO_4 = activityTypeDto(ACTIVITY_TYPE_ID_4, ACTIVITY_TYPE_NAME_4, ACTIVITY_TYPE_KEY_4.name(),
            ACTIVITY_TYPE_MAY_BE_AUTOMATED_4,
            ACTIVITY_TYPE_CHARGE_MIN_4, ACTIVITY_TYPE_CHARGE_MAX_4, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_4, ACTIVITY_TYPE_SPECIFIC_4,
            ACTIVITY_TYPE_CAN_BE_PLANNED_4, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_4, ACTIVITY_TYPE_CUSTOMER_CONTACT_4);

    public static final ActivityTypeDto TEST_ACTIVITY_TYPE_DTO_5 = activityTypeDto(ACTIVITY_TYPE_ID_5, ACTIVITY_TYPE_NAME_5, ACTIVITY_TYPE_KEY_5.name(),
            ACTIVITY_TYPE_MAY_BE_AUTOMATED_5,
            ACTIVITY_TYPE_CHARGE_MIN_5, ACTIVITY_TYPE_CHARGE_MAX_5, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_5, ACTIVITY_TYPE_SPECIFIC_5,
            ACTIVITY_TYPE_CAN_BE_PLANNED_5, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_5, ACTIVITY_TYPE_CUSTOMER_CONTACT_5);

    public static final ActivityTypeDto TEST_ACTIVITY_TYPE_DTO_6 = activityTypeDto(ACTIVITY_TYPE_ID_6, ACTIVITY_TYPE_NAME_6, ACTIVITY_TYPE_KEY_6.name(),
            ACTIVITY_TYPE_MAY_BE_AUTOMATED_6,
            ACTIVITY_TYPE_CHARGE_MIN_6, ACTIVITY_TYPE_CHARGE_MAX_6, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_6, ACTIVITY_TYPE_SPECIFIC_6,
            ACTIVITY_TYPE_CAN_BE_PLANNED_6, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_6, ACTIVITY_TYPE_CUSTOMER_CONTACT_6);

    public static final List<ActivityType> ALL_ACTIVITY_TYPES = asList(TEST_ACTIVITY_TYPE_1, TEST_ACTIVITY_TYPE_2, TEST_ACTIVITY_TYPE_3,
            TEST_ACTIVITY_TYPE_4, TEST_ACTIVITY_TYPE_5, activityType6(), activityType7(), activityType8(), activityType9(), activityType10(),
            activityType11(), activityType12(), activityType14());

    public static final List<ActivityTypeDto> ALL_ACTIVITY_TYPE_DTOS = asList(TEST_ACTIVITY_TYPE_DTO_1, TEST_ACTIVITY_TYPE_DTO_2,
            TEST_ACTIVITY_TYPE_DTO_3, TEST_ACTIVITY_TYPE_DTO_4, TEST_ACTIVITY_TYPE_DTO_5, TEST_ACTIVITY_TYPE_DTO_6, activityTypeDto7(), activityTypeDto8(),
            activityTypeDto9(), activityTypeDto10(), activityTypeDto11(), activityTypeDto12(), activityTypeDto14());

    public static ActivityType activityType6() {
        return activityType(ACTIVITY_TYPE_ID_6, ACTIVITY_TYPE_NAME_6, ACTIVITY_TYPE_MAY_BE_AUTOMATED_6,
                ACTIVITY_TYPE_CHARGE_MIN_6, ACTIVITY_TYPE_CHARGE_MAX_6, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_6, ACTIVITY_TYPE_SPECIFIC_6,
                ACTIVITY_TYPE_CAN_BE_PLANNED_6, ACTIVITY_TYPE_DETAIL_PROPERTIES_6, ACTIVITY_TYPE_CUSTOMER_CONTACT_6, ACTIVITY_TYPE_KEY_6);
    }

    public static ActivityType activityType7() {
        return activityType(ACTIVITY_TYPE_ID_7, ACTIVITY_TYPE_NAME_7, ACTIVITY_TYPE_MAY_BE_AUTOMATED_7,
                ACTIVITY_TYPE_CHARGE_MIN_7, ACTIVITY_TYPE_CHARGE_MAX_7, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_7, ACTIVITY_TYPE_SPECIFIC_7,
                ACTIVITY_TYPE_CAN_BE_PLANNED_7, ACTIVITY_TYPE_DETAIL_PROPERTIES_7, ACTIVITY_TYPE_CUSTOMER_CONTACT_7, ACTIVITY_TYPE_KEY_7);
    }

    public static ActivityType activityType8() {
        return activityType(ACTIVITY_TYPE_ID_8, ACTIVITY_TYPE_NAME_8, ACTIVITY_TYPE_MAY_BE_AUTOMATED_8,
                ACTIVITY_TYPE_CHARGE_MIN_8, ACTIVITY_TYPE_CHARGE_MAX_8, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_8, ACTIVITY_TYPE_SPECIFIC_8,
                ACTIVITY_TYPE_CAN_BE_PLANNED_8, ACTIVITY_TYPE_DETAIL_PROPERTIES_8, ACTIVITY_TYPE_CUSTOMER_CONTACT_8, ACTIVITY_TYPE_KEY_8);
    }

    public static ActivityType activityType9() {
        return activityType(ACTIVITY_TYPE_ID_9, ACTIVITY_TYPE_NAME_9, ACTIVITY_TYPE_MAY_BE_AUTOMATED_9,
                ACTIVITY_TYPE_CHARGE_MIN_9, ACTIVITY_TYPE_CHARGE_MAX_9, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_9, ACTIVITY_TYPE_SPECIFIC_9,
                ACTIVITY_TYPE_CAN_BE_PLANNED_9, ACTIVITY_TYPE_DETAIL_PROPERTIES_9, ACTIVITY_TYPE_CUSTOMER_CONTACT_9, ACTIVITY_TYPE_KEY_9);
    }

    public static ActivityType activityType10() {
        return activityType(ACTIVITY_TYPE_ID_10, ACTIVITY_TYPE_NAME_10, ACTIVITY_TYPE_MAY_BE_AUTOMATED_10,
                ACTIVITY_TYPE_CHARGE_MIN_10, ACTIVITY_TYPE_CHARGE_MAX_10, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_10, ACTIVITY_TYPE_SPECIFIC_10,
                ACTIVITY_TYPE_CAN_BE_PLANNED_10, ACTIVITY_TYPE_DETAIL_PROPERTIES_10, ACTIVITY_TYPE_CUSTOMER_CONTACT_10, ACTIVITY_TYPE_KEY_10);
    }

    public static ActivityType activityType11() {
        return activityType(ACTIVITY_TYPE_ID_11, ACTIVITY_TYPE_NAME_11, ACTIVITY_TYPE_MAY_BE_AUTOMATED_11,
                ACTIVITY_TYPE_CHARGE_MIN_11, ACTIVITY_TYPE_CHARGE_MAX_11, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_11, ACTIVITY_TYPE_SPECIFIC_11,
                ACTIVITY_TYPE_CAN_BE_PLANNED_11, ACTIVITY_TYPE_DETAIL_PROPERTIES_11, ACTIVITY_TYPE_CUSTOMER_CONTACT_11, ACTIVITY_TYPE_KEY_11);
    }

    public static ActivityType activityType12() {
        return activityType(ACTIVITY_TYPE_ID_12, ACTIVITY_TYPE_NAME_12, ACTIVITY_TYPE_MAY_BE_AUTOMATED_12,
                ACTIVITY_TYPE_CHARGE_MIN_12, ACTIVITY_TYPE_CHARGE_MAX_12, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_12, ACTIVITY_TYPE_SPECIFIC_12,
                ACTIVITY_TYPE_CAN_BE_PLANNED_12, ACTIVITY_TYPE_DETAIL_PROPERTIES_12, ACTIVITY_TYPE_CUSTOMER_CONTACT_12, ACTIVITY_TYPE_KEY_12);
    }

    public static ActivityType activityType14() {
        return activityType(ACTIVITY_TYPE_ID_14, ACTIVITY_TYPE_NAME_14, ACTIVITY_TYPE_MAY_BE_AUTOMATED_14,
                ACTIVITY_TYPE_CHARGE_MIN_14, ACTIVITY_TYPE_CHARGE_MAX_14, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_14, ACTIVITY_TYPE_SPECIFIC_14,
                ACTIVITY_TYPE_CAN_BE_PLANNED_14, ACTIVITY_TYPE_DETAIL_PROPERTIES_14, ACTIVITY_TYPE_CUSTOMER_CONTACT_14, ACTIVITY_TYPE_KEY_14);
    }

    public static ActivityTypeDto activityTypeDto7() {
        return activityTypeDto(ACTIVITY_TYPE_ID_7, ACTIVITY_TYPE_NAME_7, ACTIVITY_TYPE_KEY_7.name(),
                ACTIVITY_TYPE_MAY_BE_AUTOMATED_7,
                ACTIVITY_TYPE_CHARGE_MIN_7, ACTIVITY_TYPE_CHARGE_MAX_7, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_7, ACTIVITY_TYPE_SPECIFIC_7,
                ACTIVITY_TYPE_CAN_BE_PLANNED_7, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_7, ACTIVITY_TYPE_CUSTOMER_CONTACT_7);
    }

    public static ActivityTypeDto activityTypeDto8() {
        return activityTypeDto(ACTIVITY_TYPE_ID_8, ACTIVITY_TYPE_NAME_8, ACTIVITY_TYPE_KEY_8.name(),
                ACTIVITY_TYPE_MAY_BE_AUTOMATED_8,
                ACTIVITY_TYPE_CHARGE_MIN_8, ACTIVITY_TYPE_CHARGE_MAX_8, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_8, ACTIVITY_TYPE_SPECIFIC_8,
                ACTIVITY_TYPE_CAN_BE_PLANNED_8, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_8, ACTIVITY_TYPE_CUSTOMER_CONTACT_8);
    }

    public static ActivityTypeDto activityTypeDto9() {
        return activityTypeDto(ACTIVITY_TYPE_ID_9, ACTIVITY_TYPE_NAME_9, ACTIVITY_TYPE_KEY_9.name(),
                ACTIVITY_TYPE_MAY_BE_AUTOMATED_9,
                ACTIVITY_TYPE_CHARGE_MIN_9, ACTIVITY_TYPE_CHARGE_MAX_9, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_9, ACTIVITY_TYPE_SPECIFIC_9,
                ACTIVITY_TYPE_CAN_BE_PLANNED_9, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_9, ACTIVITY_TYPE_CUSTOMER_CONTACT_9);
    }

    public static ActivityTypeDto activityTypeDto10() {
        return activityTypeDto(ACTIVITY_TYPE_ID_10, ACTIVITY_TYPE_NAME_10, ACTIVITY_TYPE_KEY_10.name(),
                ACTIVITY_TYPE_MAY_BE_AUTOMATED_10,
                ACTIVITY_TYPE_CHARGE_MIN_10, ACTIVITY_TYPE_CHARGE_MAX_10, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_10, ACTIVITY_TYPE_SPECIFIC_10,
                ACTIVITY_TYPE_CAN_BE_PLANNED_10, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_10, ACTIVITY_TYPE_CUSTOMER_CONTACT_10);
    }

    public static ActivityTypeDto activityTypeDto11() {
        return activityTypeDto(ACTIVITY_TYPE_ID_11, ACTIVITY_TYPE_NAME_11, ACTIVITY_TYPE_KEY_11.name(),
                ACTIVITY_TYPE_MAY_BE_AUTOMATED_11,
                ACTIVITY_TYPE_CHARGE_MIN_11, ACTIVITY_TYPE_CHARGE_MAX_11, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_11, ACTIVITY_TYPE_SPECIFIC_11,
                ACTIVITY_TYPE_CAN_BE_PLANNED_11, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_11, ACTIVITY_TYPE_CUSTOMER_CONTACT_11);
    }

    public static ActivityTypeDto activityTypeDto12() {
        return activityTypeDto(ACTIVITY_TYPE_ID_12, ACTIVITY_TYPE_NAME_12, ACTIVITY_TYPE_KEY_12.name(),
                ACTIVITY_TYPE_MAY_BE_AUTOMATED_12,
                ACTIVITY_TYPE_CHARGE_MIN_12, ACTIVITY_TYPE_CHARGE_MAX_12, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_12, ACTIVITY_TYPE_SPECIFIC_12,
                ACTIVITY_TYPE_CAN_BE_PLANNED_12, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_12, ACTIVITY_TYPE_CUSTOMER_CONTACT_12);
    }

    public static ActivityTypeDto activityTypeDto14() {
        return activityTypeDto(ACTIVITY_TYPE_ID_14, ACTIVITY_TYPE_NAME_14, ACTIVITY_TYPE_KEY_14.name(),
                ACTIVITY_TYPE_MAY_BE_AUTOMATED_14,
                ACTIVITY_TYPE_CHARGE_MIN_14, ACTIVITY_TYPE_CHARGE_MAX_14, ACTIVITY_TYPE_MAY_HAVE_DECLARATION_14, ACTIVITY_TYPE_SPECIFIC_14,
                ACTIVITY_TYPE_CAN_BE_PLANNED_14, ACTIVITY_TYPE_DETAIL_PROPERTY_DTOS_14, ACTIVITY_TYPE_CUSTOMER_CONTACT_14);
    }

    private static ActivityType activityType(final Long id, final String name, final Boolean mayBeAutomated, final BigDecimal chargeMin,
                                             final BigDecimal chargeMax, final Boolean mayHaveDeclaration, final Boolean specific, final Boolean canBePlanned,
                                             final Set<ActivityTypeDetailProperty> activityTypeDetailProperties, final Boolean customerContact,
                                             final ActivityTypeKey key) {

        final ActivityType activityType = new ActivityType();
        activityType.setId(id);
        activityType.setName(name);
        activityType.setMayBeAutomated(mayBeAutomated);
        activityType.setChargeMin(chargeMin);
        activityType.setChargeMax(chargeMax);
        activityType.setMayHaveDeclaration(mayHaveDeclaration);
        activityType.setSpecific(specific);
        activityType.setCanBePlanned(canBePlanned);
        for (final ActivityTypeDetailProperty activityTypeDetailProperty : activityTypeDetailProperties) {
            activityTypeDetailProperty.setActivityType(activityType);
        }
        activityType.setActivityTypeDetailProperties(activityTypeDetailProperties);
        activityType.setCustomerContact(customerContact);
        activityType.setKey(key);
        return activityType;
    }

    private static ActivityTypeDto activityTypeDto(final Long id, final String name, final String key, final Boolean mayBeAutomated, final BigDecimal chargeMin,
                                                   final BigDecimal chargeMax, final Boolean mayHaveDeclaration, final Boolean specific,
                                                   final Boolean canBePlanned, final Set<ActivityTypeDetailPropertyDto> activityTypeDetailProperties, final
                                                   Boolean customerContact) {

        final ActivityTypeDto activityTypeDto = new ActivityTypeDto();
        activityTypeDto.setId(id);
        activityTypeDto.setName(name);
        activityTypeDto.setKey(key);
        activityTypeDto.setMayBeAutomated(mayBeAutomated);
        activityTypeDto.setChargeMin(chargeMin);
        activityTypeDto.setChargeMax(chargeMax);
        activityTypeDto.setMayHaveDeclaration(mayHaveDeclaration);
        activityTypeDto.setSpecific(specific);
        activityTypeDto.setCanBePlanned(canBePlanned);
        activityTypeDto.setActivityTypeDetailProperties(activityTypeDetailProperties);

        activityTypeDto.setCustomerContact(customerContact);
        return activityTypeDto;
    }

    private static ActivityTypeWithDetailPropertiesDto activityTypeWithDetailPropertiesDto(final Long id, final String name, final String key, final Boolean mayBeAutomated,
                                                                                           final BigDecimal chargeMin,
                                                                                           final BigDecimal chargeMax, final Boolean mayHaveDeclaration, final Boolean specific,
                                                                                           final Boolean canBePlanned, final Set<ActivityTypeDetailPropertyDto> activityTypeDetailProperties,
                                                                                           final Boolean customerContact, final List<ActivityDetailPropertyDto> detailProperties) {

        final ActivityTypeWithDetailPropertiesDto activityTypeDto = new ActivityTypeWithDetailPropertiesDto();
        activityTypeDto.setId(id);
        activityTypeDto.setName(name);
        activityTypeDto.setKey(key);
        activityTypeDto.setMayBeAutomated(mayBeAutomated);
        activityTypeDto.setChargeMin(chargeMin);
        activityTypeDto.setChargeMax(chargeMax);
        activityTypeDto.setMayHaveDeclaration(mayHaveDeclaration);
        activityTypeDto.setSpecific(specific);
        activityTypeDto.setCanBePlanned(canBePlanned);
        activityTypeDto.setActivityTypeDetailProperties(activityTypeDetailProperties);
        activityTypeDto.setCustomerContact(customerContact);
        activityTypeDto.setDetailProperties(detailProperties);
        return activityTypeDto;
    }
}
