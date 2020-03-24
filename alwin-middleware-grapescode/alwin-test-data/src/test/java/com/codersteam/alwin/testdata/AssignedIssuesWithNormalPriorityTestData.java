package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssuePriorityDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.type.IssueState;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue26;
import static com.codersteam.alwin.testdata.ContractTestData.contract18;
import static com.codersteam.alwin.testdata.ContractTestData.contract5;
import static com.codersteam.alwin.testdata.ContractTestData.contract6;
import static com.codersteam.alwin.testdata.ContractTestData.contract7;
import static com.codersteam.alwin.testdata.ContractTestData.contract8;
import static com.codersteam.alwin.testdata.ContractTestData.contract9;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto18;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto6;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto7;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer10;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer11;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer14;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer24;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto11;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto24;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator3;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto3;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings("WeakerAccess")
public class AssignedIssuesWithNormalPriorityTestData extends AbstractIssuesTestData {

    public static final Integer NORMAL_PRIORITY = 0;

    public static final long ISSUE_ID_5 = 5;
    private static final Date START_DATE_5 = parse("2021-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_5 = parse("2021-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_5 = "testowy powód 5";
    private static final IssueState ISSUE_STATE_5 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_5 = new BigDecimal("291.12");
    private static final BigDecimal BALANCE_START_PLN_5 = new BigDecimal("-65432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_5 = new BigDecimal("-234642.67");
    private static final BigDecimal PAYMENTS_PLN_5 = new BigDecimal("987654.23");
    private static final BigDecimal RPB_EUR_5 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_5 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_5 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_5 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_5 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_5 = false;
    private static final Integer PRIORITY_5 = NORMAL_PRIORITY;
    private static final Date BALANCE_UPDATE_DATE_5 = parse("2021-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_5 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_5 = new BigDecimal("65432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_5 = new BigDecimal("234642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_5 = new BigDecimal("987654.23");
    private static final Date DPD_START_DATE_5 = parse("2021-07-05 00:00:00.000000");

    public static final long ISSUE_ID_6 = 6;
    private static final Date START_DATE_6 = parse("2022-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_6 = parse("2022-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_6 = "testowy powód 6";
    private static final IssueState ISSUE_STATE_6 = IssueState.NEW;
    private static final IssueStateDto ISSUE_STATE_DTO_6 = IssueStateDto.NEW;
    private static final BigDecimal RPB_PLN_6 = new BigDecimal("191.12");
    private static final BigDecimal BALANCE_START_PLN_6 = new BigDecimal("-33432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_6 = new BigDecimal("-444642.67");
    private static final BigDecimal PAYMENTS_PLN_6 = new BigDecimal("557654.23");
    private static final BigDecimal RPB_EUR_6 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_6 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_6 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_6 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_6 = Collections.emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_6 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_6 = false;
    private static final Integer PRIORITY_6 = NORMAL_PRIORITY;
    private static final IssuePriorityDto PRIORITY_DTO_6 = IssuePriorityDto.NORMAL;
    private static final Date BALANCE_UPDATE_DATE_6 = parse("2022-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_6 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_6 = new BigDecimal("33432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_6 = new BigDecimal("444642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_6 = new BigDecimal("557654.23");
    private static final Date DPD_START_DATE_6 = parse("2022-07-05 00:00:00.000000");
    private static final Long DPD_START_6 = 5L;
    private static final Long DPD_ESTIMATED_6 = 36L;

    public static final long ISSUE_ID_7 = 7;
    private static final Date START_DATE_7 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_7 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_7 = "testowy powód 7";
    private static final IssueState ISSUE_STATE_7 = IssueState.NEW;
    private static final IssueStateDto ISSUE_STATE_DTO_7 = IssueStateDto.NEW;
    private static final BigDecimal RPB_PLN_7 = new BigDecimal("284.12");
    private static final BigDecimal BALANCE_START_PLN_7 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_7 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_7 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_7 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_7 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_7 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_7 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_7 = Collections.emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_7 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_7 = true;
    private static final Integer PRIORITY_7 = NORMAL_PRIORITY;
    private static final IssuePriorityDto PRIORITY_DTO_7 = IssuePriorityDto.NORMAL;
    private static final Date BALANCE_UPDATE_DATE_7 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_7 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_7 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_7 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_7 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_7 = parse("2023-07-05 00:00:00.000000");
    private static final Long DPD_START_7 = 0L;
    private static final Long DPD_ESTIMATED_7 = 0L;

    public static final long ISSUE_ID_8 = 8;
    private static final Date START_DATE_8 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_8 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_8 = "testowy powód 8";
    private static final IssueState ISSUE_STATE_8 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_8 = new BigDecimal("184.12");
    private static final BigDecimal BALANCE_START_PLN_8 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_8 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_8 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_8 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_8 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_8 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_8 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_8 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_8 = true;
    private static final Integer PRIORITY_8 = NORMAL_PRIORITY;
    private static final Date BALANCE_UPDATE_DATE_8 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_8 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_8 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_8 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_8 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_8 = parse("2023-07-05 00:00:00.000000");

    public static final long ISSUE_ID_9 = 9;
    private static final Date START_DATE_9 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_9 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_9 = "testowy powód 9";
    private static final IssueState ISSUE_STATE_9 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_9 = new BigDecimal("281.12");
    private static final BigDecimal BALANCE_START_PLN_9 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_9 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_9 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_9 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_9 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_9 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_9 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_9 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_9 = true;
    private static final Integer PRIORITY_9 = NORMAL_PRIORITY;
    private static final Date BALANCE_UPDATE_DATE_9 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_9 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_9 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_9 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_9 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_9 = parse("2023-07-05 00:00:00.000000");

    public static final long ISSUE_ID_10 = 10;
    private static final Date START_DATE_10 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_10 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_10 = "testowy powód 10";
    private static final IssueState ISSUE_STATE_10 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_10 = new BigDecimal("181.12");
    private static final BigDecimal BALANCE_START_PLN_10 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_10 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_10 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_10 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_10 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_10 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_10 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_10 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_10 = true;
    private static final Integer PRIORITY_10 = NORMAL_PRIORITY;
    private static final Date BALANCE_UPDATE_DATE_10 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_10 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_10 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_10 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_10 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_10 = parse("2023-07-05 00:00:00.000000");

    public static final long ISSUE_ID_21 = 21;
    private static final Date START_DATE_21 = parse("2024-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_21 = parse("2025-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_21 = "testowy powód 21";
    private static final IssueState ISSUE_STATE_21 = IssueState.NEW;
    private static final IssueStateDto ISSUE_STATE_DTO_21 = IssueStateDto.NEW;
    private static final BigDecimal RPB_PLN_21 = new BigDecimal("333432.12");
    private static final BigDecimal BALANCE_START_PLN_21 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_21 = new BigDecimal("554642.67");
    private static final BigDecimal PAYMENTS_PLN_21 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_21 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_21 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_21 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_21 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_21 = Collections.emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_21 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_21 = true;
    private static final Integer PRIORITY_21 = NORMAL_PRIORITY;
    private static final IssuePriorityDto PRIORITY_DTO_21 = IssuePriorityDto.NORMAL;
    private static final Date BALANCE_UPDATE_DATE_21 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_21 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_21 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_21 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_21 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_21 = parse("2024-07-05 00:00:00.000000");
    private static final Long DPD_START_21 = 5L;
    private static final Long DPD_ESTIMATED_21 = 402L;

    public static Issue issue5() {
        return newEntity(ISSUE_ID_5, testOperator1(), testCustomer10(), contract5(), START_DATE_5, EXPIRATION_DATE_5, TERMINATION_CAUSE_5, issueType1(),
                ISSUE_STATE_5, RPB_PLN_5, BALANCE_START_PLN_5, CURRENT_BALANCE_PLN_5, PAYMENTS_PLN_5, INVOICES_5, EXCLUDED_FROM_STATS_5, PRIORITY_5, BALANCE_UPDATE_DATE_5,
                CREATED_MANUALLY_5, null, null, null, null, RPB_EUR_5, BALANCE_START_EUR_5, CURRENT_BALANCE_EUR_5, PAYMENTS_EUR_5, null,
                TOTAL_BALANCE_START_PLN_5, TOTAL_CURRENT_BALANCE_PLN_5, TOTAL_PAYMENTS_PLN_5, DPD_START_DATE_5);
    }

    public static Issue issue6() {
        return newEntity(ISSUE_ID_6, testOperator1(), testCustomer11(), contract6(), START_DATE_6, EXPIRATION_DATE_6, TERMINATION_CAUSE_6, issueType1(),
                ISSUE_STATE_6, RPB_PLN_6, BALANCE_START_PLN_6, CURRENT_BALANCE_PLN_6, PAYMENTS_PLN_6, INVOICES_6, EXCLUDED_FROM_STATS_6, PRIORITY_6, BALANCE_UPDATE_DATE_6,
                CREATED_MANUALLY_6, null, null, null, null, RPB_EUR_6, BALANCE_START_EUR_6, CURRENT_BALANCE_EUR_6, PAYMENTS_EUR_6, null,
                TOTAL_BALANCE_START_PLN_6, TOTAL_CURRENT_BALANCE_PLN_6, TOTAL_PAYMENTS_PLN_6, DPD_START_DATE_6);
    }

    public static Issue issue7() {
        return newEntity(ISSUE_ID_7, testOperator3(), null, contract7(), START_DATE_7, EXPIRATION_DATE_7, TERMINATION_CAUSE_7, issueType1(),
                ISSUE_STATE_7, RPB_PLN_7, BALANCE_START_PLN_7, CURRENT_BALANCE_PLN_7, PAYMENTS_PLN_7, INVOICES_7, EXCLUDED_FROM_STATS_7, PRIORITY_7, BALANCE_UPDATE_DATE_7,
                CREATED_MANUALLY_7, null, null, null, null, RPB_EUR_7, BALANCE_START_EUR_7, CURRENT_BALANCE_EUR_7, PAYMENTS_EUR_7, null,
                TOTAL_BALANCE_START_PLN_7, TOTAL_CURRENT_BALANCE_PLN_7, TOTAL_PAYMENTS_PLN_7, DPD_START_DATE_7);
    }

    public static Issue issue8() {
        return newEntity(ISSUE_ID_8, testOperator1(), null, contract8(), START_DATE_8, EXPIRATION_DATE_8, TERMINATION_CAUSE_8, issueType1(),
                ISSUE_STATE_8, RPB_PLN_8, BALANCE_START_PLN_8, CURRENT_BALANCE_PLN_8, PAYMENTS_PLN_8, INVOICES_8, EXCLUDED_FROM_STATS_8, PRIORITY_8, BALANCE_UPDATE_DATE_8,
                CREATED_MANUALLY_8, null, null, null, null, RPB_EUR_8, BALANCE_START_EUR_8, CURRENT_BALANCE_EUR_8, PAYMENTS_EUR_8, null,
                TOTAL_BALANCE_START_PLN_8, TOTAL_CURRENT_BALANCE_PLN_8, TOTAL_PAYMENTS_PLN_8, DPD_START_DATE_8);
    }

    public static Issue issue9() {
        return newEntity(ISSUE_ID_9, testOperator1(), testCustomer14(), null, START_DATE_9, EXPIRATION_DATE_9, TERMINATION_CAUSE_9, issueType1(),
                ISSUE_STATE_9, RPB_PLN_9, BALANCE_START_PLN_9, CURRENT_BALANCE_PLN_9, PAYMENTS_PLN_9, INVOICES_9, EXCLUDED_FROM_STATS_9, PRIORITY_9, BALANCE_UPDATE_DATE_9,
                CREATED_MANUALLY_9, null, null, null, null, RPB_EUR_9, BALANCE_START_EUR_9, CURRENT_BALANCE_EUR_9, PAYMENTS_EUR_9, null,
                TOTAL_BALANCE_START_PLN_9, TOTAL_CURRENT_BALANCE_PLN_9, TOTAL_PAYMENTS_PLN_9, DPD_START_DATE_9);
    }

    public static Issue issue10() {
        return newEntity(ISSUE_ID_10, testOperator1(), null, contract9(), START_DATE_10, EXPIRATION_DATE_10, TERMINATION_CAUSE_10, issueType1(),
                ISSUE_STATE_10, RPB_PLN_10, BALANCE_START_PLN_10, CURRENT_BALANCE_PLN_10, PAYMENTS_PLN_10, INVOICES_10, EXCLUDED_FROM_STATS_10, PRIORITY_10,
                BALANCE_UPDATE_DATE_10, CREATED_MANUALLY_10, null, null, null, null, RPB_EUR_10, BALANCE_START_EUR_10, CURRENT_BALANCE_EUR_10, PAYMENTS_EUR_10, null,
                TOTAL_BALANCE_START_PLN_10, TOTAL_CURRENT_BALANCE_PLN_10, TOTAL_PAYMENTS_PLN_10, DPD_START_DATE_10);
    }

    public static Issue issue21() {
        return newEntity(ISSUE_ID_21, testOperator2(), testCustomer24(), contract18(), START_DATE_21, EXPIRATION_DATE_21, TERMINATION_CAUSE_21, issueType1(),
                ISSUE_STATE_21, RPB_PLN_21, BALANCE_START_PLN_21, CURRENT_BALANCE_PLN_21, PAYMENTS_PLN_21, INVOICES_21, EXCLUDED_FROM_STATS_21, PRIORITY_21,
                BALANCE_UPDATE_DATE_21, CREATED_MANUALLY_21, issue26(), null, null, null, RPB_EUR_21, BALANCE_START_EUR_21, CURRENT_BALANCE_EUR_21,
                PAYMENTS_EUR_21, null, TOTAL_BALANCE_START_PLN_21, TOTAL_CURRENT_BALANCE_PLN_21, TOTAL_PAYMENTS_PLN_21, DPD_START_DATE_21);
    }

    public static IssueDto issueDto6() {
        return newDto(ISSUE_ID_6, testOperatorUserDto1(), testCustomerDto11(), contractDto6(), START_DATE_6, EXPIRATION_DATE_6, TERMINATION_CAUSE_6,
                issueTypeDto1(), ISSUE_STATE_DTO_6, RPB_PLN_6, BALANCE_START_PLN_6, CURRENT_BALANCE_PLN_6, PAYMENTS_PLN_6, INVOICES_DTO_6, EXCLUDED_FROM_STATS_6,
                BALANCE_UPDATE_DATE_6, PRIORITY_DTO_6, null, null, null, RPB_EUR_6, BALANCE_START_EUR_6, CURRENT_BALANCE_EUR_6, PAYMENTS_EUR_6,
                TOTAL_BALANCE_START_PLN_6, TOTAL_CURRENT_BALANCE_PLN_6, TOTAL_PAYMENTS_PLN_6, DPD_START_6, DPD_ESTIMATED_6);
    }

    public static IssueDto issueDto7() {
        return newDto(ISSUE_ID_7, testOperatorUserDto3(), null, contractDto7(), START_DATE_7, EXPIRATION_DATE_7, TERMINATION_CAUSE_7,
                issueTypeDto1(), ISSUE_STATE_DTO_7, RPB_PLN_7, BALANCE_START_PLN_7, CURRENT_BALANCE_PLN_7, PAYMENTS_PLN_7, INVOICES_DTO_7, EXCLUDED_FROM_STATS_7,
                BALANCE_UPDATE_DATE_7, PRIORITY_DTO_7, null, null, null, RPB_EUR_7, BALANCE_START_EUR_7, CURRENT_BALANCE_EUR_7, PAYMENTS_EUR_7,
                TOTAL_BALANCE_START_PLN_7, TOTAL_CURRENT_BALANCE_PLN_7, TOTAL_PAYMENTS_PLN_7, DPD_START_7, DPD_ESTIMATED_7);
    }

    public static IssueDto issueDto21() {
        return newDto(ISSUE_ID_21, testOperatorUserDto2(), testCustomerDto24(), contractDto18(), START_DATE_21, EXPIRATION_DATE_21, TERMINATION_CAUSE_21,
                issueTypeDto1(), ISSUE_STATE_DTO_21, RPB_PLN_21, BALANCE_START_PLN_21, CURRENT_BALANCE_PLN_21, PAYMENTS_PLN_21, INVOICES_DTO_21, EXCLUDED_FROM_STATS_21,
                BALANCE_UPDATE_DATE_21, PRIORITY_DTO_21, null, null, null, RPB_EUR_21, BALANCE_START_EUR_21, CURRENT_BALANCE_EUR_21, PAYMENTS_EUR_21,
                TOTAL_BALANCE_START_PLN_21, TOTAL_CURRENT_BALANCE_PLN_21, TOTAL_PAYMENTS_PLN_21, DPD_START_21, DPD_ESTIMATED_21);
    }
}
