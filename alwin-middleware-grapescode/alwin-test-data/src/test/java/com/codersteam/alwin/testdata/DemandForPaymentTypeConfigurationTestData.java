package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentTypeConfigurationDto;
import com.codersteam.alwin.core.api.model.issue.SegmentDto;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST;
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.SECOND;
import static com.codersteam.alwin.common.issue.Segment.A;
import static com.codersteam.alwin.common.issue.Segment.B;
import static java.util.Arrays.asList;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public final class DemandForPaymentTypeConfigurationTestData {

    public static final Long DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_1 = 1L;
    private static final DemandForPaymentTypeKey DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_1 = FIRST;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_1 = 7;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_1 = 3;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_1 = new BigDecimal("50.00");
    private static final Segment DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_1 = A;
    private static final SegmentDto DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_DTO_1 = SegmentDto.A;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_1 = new BigDecimal("-100.00");
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_1 = null;

    public static final Long DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_2 = 2L;
    private static final DemandForPaymentTypeKey DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_2 = SECOND;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_2 = 15;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_2 = 3;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_2 = new BigDecimal("99.00");
    public static final Segment DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_2 = A;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_2 = new BigDecimal("-100.00");
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_2 = null;

    public static final Long DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_3 = 3L;
    private static final DemandForPaymentTypeKey DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_3 = FIRST;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_3 = 7;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_3 = 3;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_3 = new BigDecimal("50.00");
    private static final Segment DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_3 = B;
    private static final SegmentDto DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_DTO_3 = SegmentDto.B;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_3 = new BigDecimal("-100.00");
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_3 = null;

    public static final Long DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_4 = 4L;
    private static final DemandForPaymentTypeKey DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_4 = SECOND;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_4 = 15;
    private static final Integer DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_4 = 3;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_4 = new BigDecimal("99.00");
    private static final Segment DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_4 = B;
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_4 = new BigDecimal("-100.00");
    private static final BigDecimal DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_4 = null;

    public static List<DemandForPaymentTypeConfiguration> allDemandForPaymentTypes() {
        return asList(demandForPaymentTypeConfigurationFirstSegmentA(), demandForPaymentTypeConfigurationSecondSegmentA(),
                demandForPaymentTypeConfigurationFirstSegmentB(), demandForPaymentTypeConfigurationSecondSegmentB());
    }

    public static DemandForPaymentTypeConfiguration demandForPaymentTypeConfigurationFirstSegmentA() {
        return demandForPaymentTypeConfiguration(DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_1,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_1,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_1,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_1);
    }

    public static DemandForPaymentTypeConfigurationDto demandForPaymentTypeConfigurationFirstSegmentADto() {
        return demandForPaymentTypeConfigurationDto(DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_1,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_1,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_DTO_1,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_1, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_1);
    }

    public static DemandForPaymentTypeConfiguration demandForPaymentTypeConfigurationSecondSegmentA() {
        return demandForPaymentTypeConfiguration(DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_2, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_2,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_2, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_2,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_2, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_2,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_2, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_2);
    }

    public static DemandForPaymentTypeConfiguration demandForPaymentTypeConfigurationFirstSegmentB() {
        return demandForPaymentTypeConfiguration(DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_3,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_3,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_3,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_3);
    }

    public static DemandForPaymentTypeConfigurationDto demandForPaymentTypeConfigurationFirstSegmentBDto() {
        return demandForPaymentTypeConfigurationDto(DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_3,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_3,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_DTO_3,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_3, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_3);
    }


    public static DemandForPaymentTypeConfiguration demandForPaymentTypeConfigurationSecondSegmentB() {
        return demandForPaymentTypeConfiguration(DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_ID_4, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_KEY_4,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DPD_START_4, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_NUMBER_OF_DAYS_TO_PAY_4,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_CHARGE_4, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_4,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_VALUE_4, DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_MIN_DUE_PAYMENT_PERCENT_4);
    }

    private DemandForPaymentTypeConfigurationTestData() {
    }

    private static DemandForPaymentTypeConfiguration demandForPaymentTypeConfiguration(final Long id, final DemandForPaymentTypeKey key, final Integer dpdStart,
                                                                                       final Integer numberOfDaysToPay, final BigDecimal charge, final Segment segment,
                                                                                       final BigDecimal minDuePaymentValue, final BigDecimal minDuePaymentPercent) {
        final DemandForPaymentTypeConfiguration demandForPaymentType = new DemandForPaymentTypeConfiguration();
        demandForPaymentType.setId(id);
        demandForPaymentType.setKey(key);
        demandForPaymentType.setDpdStart(dpdStart);
        demandForPaymentType.setNumberOfDaysToPay(numberOfDaysToPay);
        demandForPaymentType.setCharge(charge);
        demandForPaymentType.setSegment(segment);
        demandForPaymentType.setMinDuePaymentValue(minDuePaymentValue);
        demandForPaymentType.setMinDuePaymentPercent(minDuePaymentPercent);
        return demandForPaymentType;
    }

    private static DemandForPaymentTypeConfigurationDto demandForPaymentTypeConfigurationDto(final Long id, final DemandForPaymentTypeKey key,
                                                                                             final Integer dpdStart, final Integer numberOfDaysToPay,
                                                                                             final BigDecimal charge, final SegmentDto segment,
                                                                                             final BigDecimal minDuePaymentValue, final BigDecimal minDuePaymentPercent) {
        final DemandForPaymentTypeConfigurationDto demandForPaymentType = new DemandForPaymentTypeConfigurationDto();
        demandForPaymentType.setId(id);
        demandForPaymentType.setKey(key);
        demandForPaymentType.setDpdStart(dpdStart);
        demandForPaymentType.setNumberOfDaysToPay(numberOfDaysToPay);
        demandForPaymentType.setCharge(charge);
        demandForPaymentType.setSegment(segment);
        demandForPaymentType.setMinDuePaymentValue(minDuePaymentValue);
        demandForPaymentType.setMinDuePaymentPercent(minDuePaymentPercent);
        return demandForPaymentType;
    }
}
