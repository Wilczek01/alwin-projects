package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;
import com.codersteam.alwin.core.api.model.issue.IssueTypeDto;
import com.codersteam.alwin.core.api.model.issue.SegmentDto;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueTypeConfiguration;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.DEFAULT_ISSUE_ACTIVITY_WT1_DTOS;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType2;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType3;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType4;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType5;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType6;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType7;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType8;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto2;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_12;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_2;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_3;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@SuppressWarnings("WeakerAccess")
public class IssueTypeConfigurationTestData {

    // PHONE_DEBT_COLLECTION_1, segment A
    public static final long ID_1 = 1L;
    public static final IssueType ISSUE_TYPE_1 = issueType1();
    public static final Segment SEGMENT_1 = Segment.A;
    public static final int DURATION_1 = 14;
    public static final int DPD_START_1 = 1;
    public static final BigDecimal MIN_BALANCE_START_1 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_1 = true;

    // PHONE_DEBT_COLLECTION_2, segment A
    public static final long ID_2 = 2L;
    public static final IssueType ISSUE_TYPE_2 = issueType2();
    public static final Segment SEGMENT_2 = Segment.A;
    public static final int DURATION_2 = 17;
    public static final int DPD_START_2 = 16;
    public static final BigDecimal MIN_BALANCE_START_2 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_2 = true;

    // DIRECT_DEBT_COLLECTION, segment A
    public static final long ID_3 = 3L;
    public static final IssueType ISSUE_TYPE_3 = issueType3();
    public static final Segment SEGMENT_3 = Segment.A;
    public static final int DURATION_3 = 13;
    public static final int DPD_START_3 = 5;
    public static final BigDecimal MIN_BALANCE_START_3 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_3 = false;

    // LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM, segment A
    public static final long ID_4 = 4L;
    public static final IssueType ISSUE_TYPE_4 = issueType4();
    public static final Segment SEGMENT_4 = Segment.A;
    public static final int DURATION_4 = 14;
    public static final int DPD_START_4 = 5;
    public static final BigDecimal MIN_BALANCE_START_4 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_4 = false;

    // SUBJECT_TRANSPORT, segment A
    public static final long ID_5 = 5L;
    public static final IssueType ISSUE_TYPE_5 = issueType5();
    public static final Segment SEGMENT_5 = Segment.A;
    public static final int DURATION_5 = 15;
    public static final int DPD_START_5 = 5;
    public static final BigDecimal MIN_BALANCE_START_5 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_5 = false;

    // REALIZATION_OF_COLLATERAL, segment A
    public static final long ID_6 = 6L;
    public static final IssueType ISSUE_TYPE_6 = issueType6();
    public static final Segment SEGMENT_6 = Segment.A;
    public static final int DURATION_6 = 16;
    public static final int DPD_START_6 = 5;
    public static final BigDecimal MIN_BALANCE_START_6 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_6 = false;

    // LAW_DEBT_COLLECTION_MOTION_TO_PAY, segment A
    public static final long ID_7 = 7L;
    public static final IssueType ISSUE_TYPE_7 = issueType7();
    public static final Segment SEGMENT_7 = Segment.A;
    public static final int DURATION_7 = 17;
    public static final int DPD_START_7 = 5;
    public static final BigDecimal MIN_BALANCE_START_7 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_7 = false;

    // RESTRUCTURING, segment A
    public static final long ID_8 = 8L;
    public static final IssueType ISSUE_TYPE_8 = issueType8();
    public static final Segment SEGMENT_8 = Segment.A;
    public static final int DURATION_8 = 18;
    public static final int DPD_START_8 = 5;
    public static final BigDecimal MIN_BALANCE_START_8 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_8 = false;

    // PHONE_DEBT_COLLECTION_1, segment B
    public static final long ID_11 = 11L;
    public static final IssueType ISSUE_TYPE_11 = issueType1();
    public static final Segment SEGMENT_11 = Segment.B;
    public static final int DURATION_11 = 9;
    public static final int DPD_START_11 = 1;
    public static final BigDecimal MIN_BALANCE_START_11 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_11 = true;

    // PHONE_DEBT_COLLECTION_2, segment B
    public static final long ID_12 = 12L;
    public static final IssueType ISSUE_TYPE_12 = issueType2();
    public static final Segment SEGMENT_12 = Segment.B;
    public static final int DURATION_12 = 12;
    public static final int DPD_START_12 = 11;
    public static final BigDecimal MIN_BALANCE_START_12 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_12 = true;

    // DIRECT_DEBT_COLLECTION, segment B
    public static final long ID_13 = 13L;
    public static final IssueType ISSUE_TYPE_13 = issueType3();
    public static final Segment SEGMENT_13 = Segment.B;
    public static final int DURATION_13 = 23;
    public static final int DPD_START_13 = 5;
    public static final BigDecimal MIN_BALANCE_START_13 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_13 = false;

    // LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM, segment B
    public static final long ID_14 = 14L;
    public static final IssueType ISSUE_TYPE_14 = issueType4();
    public static final Segment SEGMENT_14 = Segment.B;
    public static final int DURATION_14 = 24;
    public static final int DPD_START_14 = 5;
    public static final BigDecimal MIN_BALANCE_START_14 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_14 = false;

    // SUBJECT_TRANSPORT, segment B
    public static final long ID_15 = 15L;
    public static final IssueType ISSUE_TYPE_15 = issueType5();
    public static final Segment SEGMENT_15 = Segment.B;
    public static final int DURATION_15 = 25;
    public static final int DPD_START_15 = 5;
    public static final BigDecimal MIN_BALANCE_START_15 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_15 = false;

    // REALIZATION_OF_COLLATERAL, segment B
    public static final long ID_16 = 16L;
    public static final IssueType ISSUE_TYPE_16 = issueType6();
    public static final Segment SEGMENT_16 = Segment.B;
    public static final int DURATION_16 = 26;
    public static final int DPD_START_16 = 5;
    public static final BigDecimal MIN_BALANCE_START_16 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_16 = false;

    // LAW_DEBT_COLLECTION_MOTION_TO_PAY, segment B
    public static final long ID_17 = 17L;
    public static final IssueType ISSUE_TYPE_17 = issueType7();
    public static final Segment SEGMENT_17 = Segment.B;
    public static final int DURATION_17 = 27;
    public static final int DPD_START_17 = 5;
    public static final BigDecimal MIN_BALANCE_START_17 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_17 = false;

    // RESTRUCTURING, segment B
    public static final long ID_18 = 18L;
    public static final IssueType ISSUE_TYPE_18 = issueType8();
    public static final Segment SEGMENT_18 = Segment.B;
    public static final int DURATION_18 = 28;
    public static final int DPD_START_18 = 5;
    public static final BigDecimal MIN_BALANCE_START_18 = new BigDecimal("-100.00");
    public static final boolean CREATE_AUTOMATICALLY_18 = false;

    public static final Boolean DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE = false;

    public static final List<IssueTypeConfiguration> ALL_ISSUE_TYPE_CONFIGURATIONS = asList(issueTypeConfiguration1(), issueTypeConfiguration2(),
            issueTypeConfiguration3(), issueTypeConfiguration4(), issueTypeConfiguration5(), issueTypeConfiguration6(), issueTypeConfiguration7(),
            issueTypeConfiguration8(), issueTypeConfiguration11(), issueTypeConfiguration12(), issueTypeConfiguration13(), issueTypeConfiguration14(),
            issueTypeConfiguration15(), issueTypeConfiguration16(), issueTypeConfiguration17(), issueTypeConfiguration18());

    public static final List<IssueTypeConfiguration> CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATIONS = asList(issueTypeConfiguration2(),
            issueTypeConfiguration12(), issueTypeConfiguration1(), issueTypeConfiguration11());

    public static final List<IssueTypeConfigurationDto> CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATION_DTOS = asList(issueTypeConfigurationDto2(),
            issueTypeConfigurationDto12(), issueTypeConfigurationDto1(), issueTypeConfigurationDto11());

    public static final List<IssueTypeConfigurationDto> WT1_CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATION_DTOS = asList(issueTypeConfigurationDto1(),
            issueTypeConfigurationDto11());

    public static IssueTypeConfiguration issueTypeConfiguration1() {
        return issueTypeConfiguration(ID_1, ISSUE_TYPE_1, DURATION_1, SEGMENT_1, CREATE_AUTOMATICALLY_1, DPD_START_1, MIN_BALANCE_START_1,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration2() {
        return issueTypeConfiguration(ID_2, ISSUE_TYPE_2, DURATION_2, SEGMENT_2, CREATE_AUTOMATICALLY_2, DPD_START_2, MIN_BALANCE_START_2,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration3() {
        return issueTypeConfiguration(ID_3, ISSUE_TYPE_3, DURATION_3, SEGMENT_3, CREATE_AUTOMATICALLY_3, DPD_START_3, MIN_BALANCE_START_3,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration4() {
        return issueTypeConfiguration(ID_4, ISSUE_TYPE_4, DURATION_4, SEGMENT_4, CREATE_AUTOMATICALLY_4, DPD_START_4, MIN_BALANCE_START_4,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration5() {
        return issueTypeConfiguration(ID_5, ISSUE_TYPE_5, DURATION_5, SEGMENT_5, CREATE_AUTOMATICALLY_5, DPD_START_5, MIN_BALANCE_START_5,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration6() {
        return issueTypeConfiguration(ID_6, ISSUE_TYPE_6, DURATION_6, SEGMENT_6, CREATE_AUTOMATICALLY_6, DPD_START_6, MIN_BALANCE_START_6,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration7() {
        return issueTypeConfiguration(ID_7, ISSUE_TYPE_7, DURATION_7, SEGMENT_7, CREATE_AUTOMATICALLY_7, DPD_START_7, MIN_BALANCE_START_7,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration8() {
        return issueTypeConfiguration(ID_8, ISSUE_TYPE_8, DURATION_8, SEGMENT_8, CREATE_AUTOMATICALLY_8, DPD_START_8, MIN_BALANCE_START_8,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration11() {
        return issueTypeConfiguration(ID_11, ISSUE_TYPE_11, DURATION_11, SEGMENT_11, CREATE_AUTOMATICALLY_11, DPD_START_11, MIN_BALANCE_START_11,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration12() {
        return issueTypeConfiguration(ID_12, ISSUE_TYPE_12, DURATION_12, SEGMENT_12, CREATE_AUTOMATICALLY_12, DPD_START_12, MIN_BALANCE_START_12,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration13() {
        return issueTypeConfiguration(ID_13, ISSUE_TYPE_13, DURATION_13, SEGMENT_13, CREATE_AUTOMATICALLY_13, DPD_START_13, MIN_BALANCE_START_13,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration14() {
        return issueTypeConfiguration(ID_14, ISSUE_TYPE_14, DURATION_14, SEGMENT_14, CREATE_AUTOMATICALLY_14, DPD_START_14, MIN_BALANCE_START_14,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration15() {
        return issueTypeConfiguration(ID_15, ISSUE_TYPE_15, DURATION_15, SEGMENT_15, CREATE_AUTOMATICALLY_15, DPD_START_15, MIN_BALANCE_START_15,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration16() {
        return issueTypeConfiguration(ID_16, ISSUE_TYPE_16, DURATION_16, SEGMENT_16, CREATE_AUTOMATICALLY_16, DPD_START_16, MIN_BALANCE_START_16,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration17() {
        return issueTypeConfiguration(ID_17, ISSUE_TYPE_17, DURATION_17, SEGMENT_17, CREATE_AUTOMATICALLY_17, DPD_START_17, MIN_BALANCE_START_17,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration18() {
        return issueTypeConfiguration(ID_18, ISSUE_TYPE_18, DURATION_18, SEGMENT_18, CREATE_AUTOMATICALLY_18, DPD_START_18, MIN_BALANCE_START_18,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfiguration issueTypeConfiguration(final Long id, final IssueType issueType, final Integer duration, final Segment segment,
                                                                final Boolean createAutomatically, final Integer dpd, final BigDecimal minBalanceStart,
                                                                final Boolean includeDebtInvoicesDuringIssue) {
        final IssueTypeConfiguration issueTypeConfiguration = new IssueTypeConfiguration();
        issueTypeConfiguration.setId(id);
        issueTypeConfiguration.setIssueType(issueType);
        issueTypeConfiguration.setDuration(duration);
        issueTypeConfiguration.setSegment(segment);
        issueTypeConfiguration.setCreateAutomatically(createAutomatically);
        issueTypeConfiguration.setDpdStart(dpd);
        issueTypeConfiguration.setMinBalanceStart(minBalanceStart);
        issueTypeConfiguration.setIncludeDebtInvoicesDuringIssue(includeDebtInvoicesDuringIssue);

        return issueTypeConfiguration;
    }

    public static IssueTypeConfiguration issueTypeConfigurationToMap() {
        return issueTypeConfiguration(ID_1, issueType1(), DURATION_1, SEGMENT_1, CREATE_AUTOMATICALLY_1, DPD_START_1, MIN_BALANCE_START_1,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationDto1() {
        return issueTypeConfigurationDto(ID_1, issueTypeDto1(), DURATION_1, SegmentDto.A, CREATE_AUTOMATICALLY_1, DPD_START_1, MIN_BALANCE_START_1,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationDto2() {
        return issueTypeConfigurationDto(ID_2, issueTypeDto2(), DURATION_2, SegmentDto.A, CREATE_AUTOMATICALLY_2, DPD_START_2, MIN_BALANCE_START_2,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationDto11() {
        return issueTypeConfigurationDto(ID_11, issueTypeDto1(), DURATION_11, SegmentDto.B, CREATE_AUTOMATICALLY_11, DPD_START_11, MIN_BALANCE_START_11,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationDto12() {
        return issueTypeConfigurationDto(ID_12, issueTypeDto2(), DURATION_12, SegmentDto.B, CREATE_AUTOMATICALLY_12, DPD_START_12, MIN_BALANCE_START_12,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationDto(final Long id, final IssueTypeDto issueTypeDto, final Integer duration,
                                                                      final SegmentDto segment, final Boolean createAutomatically, final Integer dpdStart, final BigDecimal minBalanceStart, final Boolean includeDebtInvoicesDuringIssue) {
        final IssueTypeConfigurationDto issueTypeConfigurationDto = new IssueTypeConfigurationDto();
        issueTypeConfigurationDto.setId(id);
        issueTypeConfigurationDto.setIssueType(issueTypeDto);
        issueTypeConfigurationDto.setDuration(duration);
        issueTypeConfigurationDto.setSegment(segment);
        issueTypeConfigurationDto.setCreateAutomatically(createAutomatically);
        issueTypeConfigurationDto.setDpdStart(dpdStart);
        issueTypeConfigurationDto.setMinBalanceStart(minBalanceStart);
        issueTypeConfigurationDto.setIncludeDebtInvoicesDuringIssue(includeDebtInvoicesDuringIssue);
        return issueTypeConfigurationDto;
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationWithDefaultIssueActivityAndOperatorTypeDto() {
        final IssueTypeConfigurationDto issueTypeConfigurationDto = issueTypeConfigurationWithOperatorTypeDto();
        issueTypeConfigurationDto.setDefaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_WT1_DTOS);
        return issueTypeConfigurationDto;
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationWithAndOperatorTypeDto() {
        final IssueTypeConfigurationDto issueTypeConfigurationDto = issueTypeConfigurationWithOperatorTypeDto();
        issueTypeConfigurationDto.setDefaultIssueActivity(emptyList());
        return issueTypeConfigurationDto;
    }

    public static IssueTypeConfigurationDto modifiedIssueTypeConfigurationWithOperatorTypeDto() {
        final IssueTypeConfigurationDto issueTypeConfigurationDto = issueTypeConfigurationWithOperatorTypeDto1();
        issueTypeConfigurationDto.setDpdStart(10);
        issueTypeConfigurationDto.setDefaultIssueActivity(emptyList());
        return issueTypeConfigurationDto;
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationWithOperatorTypeDto() {
        final IssueTypeConfigurationDto issueTypeConfigurationDto = issueTypeConfigurationDto1();
        issueTypeConfigurationDto.setOperatorTypes(new HashSet<>(asList(TEST_OPERATOR_TYPE_DTO, TEST_OPERATOR_TYPE_DTO_3, TEST_OPERATOR_TYPE_DTO_2,
                TEST_OPERATOR_TYPE_DTO_12)));
        return issueTypeConfigurationDto;
    }

    public static IssueTypeConfigurationDto issueTypeConfigurationWithOperatorTypeDto1() {
        final IssueTypeConfigurationDto issueTypeConfigurationDto = issueTypeConfigurationDto1();
        issueTypeConfigurationDto.setOperatorTypes(new HashSet<>(asList(TEST_OPERATOR_TYPE_DTO, TEST_OPERATOR_TYPE_DTO_2)));
        return issueTypeConfigurationDto;
    }

    public static IssueTypeConfiguration issueTypeConfigurationWithoutParentOperator() {
        return issueTypeConfiguration(ID_1, null, DURATION_1, SEGMENT_1, CREATE_AUTOMATICALLY_1, DPD_START_1, MIN_BALANCE_START_1,
                DO_NOT_INCLUDE_DEBT_INVOICES_DURING_ISSUE);
    }
}
