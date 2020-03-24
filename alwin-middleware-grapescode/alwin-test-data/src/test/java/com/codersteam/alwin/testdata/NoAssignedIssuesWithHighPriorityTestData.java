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

import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_29;
import static com.codersteam.alwin.testdata.ContractTestData.contract10;
import static com.codersteam.alwin.testdata.ContractTestData.contract11;
import static com.codersteam.alwin.testdata.ContractTestData.contract12;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer16;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer26;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer4;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto16;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;

@SuppressWarnings("WeakerAccess")
public class NoAssignedIssuesWithHighPriorityTestData extends AbstractIssuesTestData {

    public static final Long ISSUE_ID_11 = 11L;
    public static final Long ISSUE_ID_12 = 12L;
    public static final Long ISSUE_ID_13 = 13L;
    public static final Long ISSUE_ID_14 = 14L;

    private static final Date START_DATE_11 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_11 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_11 = "testowy powód 11";
    private static final IssueState ISSUE_STATE_11 = IssueState.NEW;
    private static final IssueStateDto ISSUE_STATE_DTO_11 = IssueStateDto.NEW;
    private static final BigDecimal RPB_PLN_11 = new BigDecimal("292.00");
    private static final BigDecimal BALANCE_START_PLN_11 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_11 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_11 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_11 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_11 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_11 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_11 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_11 = Collections.emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_11 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_11 = true;
    private static final Integer PRIORITY_11 = 1;
    private static final IssuePriorityDto PRIORITY_DTO_11 = IssuePriorityDto.HIGH;
    private static final Date BALANCE_UPDATE_DATE_11 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_11 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_11 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_11 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_11 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_11 = parse("2023-07-05 00:00:00.000000");
    private static final Long DPD_START_11 = 5L;
    private static final Long DPD_ESTIMATED_11 = 402L;

    private static final Date START_DATE_12 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_12 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_12 = "testowy powód 12";
    private static final IssueState ISSUE_STATE_12 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_12 = new BigDecimal("192.00");
    private static final BigDecimal BALANCE_START_PLN_12 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_12 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_12 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_12 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_12 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_12 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_12 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_12 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_12 = true;
    private static final Integer PRIORITY_12 = 1;
    private static final Date BALANCE_UPDATE_DATE_12 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_12 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_12 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_12 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_12 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_12 = parse("2023-07-05 00:00:00.000000");

    private static final Date START_DATE_13 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_13 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_13 = "testowy powód 13";
    private static final IssueState ISSUE_STATE_13 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_13 = new BigDecimal("282.00");
    private static final BigDecimal BALANCE_START_PLN_13 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_13 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_13 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_13 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_13 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_13 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_13 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_13 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_13 = true;
    private static final Integer PRIORITY_13 = 1;
    private static final Date BALANCE_UPDATE_DATE_13 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_13 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_13 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_13 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_13 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_13 = parse("2023-07-05 00:00:00.000000");

    private static final Date START_DATE_14 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_14 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_14 = "testowy powód 14";
    private static final IssueState ISSUE_STATE_14 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_14 = new BigDecimal("182.00");
    private static final BigDecimal BALANCE_START_PLN_14 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_14 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_14 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_14 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_14 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_14 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_14 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_14 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_14 = true;
    private static final Integer PRIORITY_14 = 1;
    private static final Date BALANCE_UPDATE_DATE_14 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_14 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_14 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_14 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_14 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_14 = parse("2023-07-05 00:00:00.000000");

    private static final Date START_DATE_29 = parse("2018-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_29 = parse("2018-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_29 = null;
    private static final IssueState ISSUE_STATE_29 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_29 = new BigDecimal("333432.12");
    private static final BigDecimal BALANCE_START_PLN_29 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_29 = new BigDecimal("554642.67");
    private static final BigDecimal PAYMENTS_PLN_29 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_29 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_29 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_29 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_29 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_29 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_29 = false;
    private static final Integer PRIORITY_29 = 1;
    private static final Date BALANCE_UPDATE_DATE_29 = parse("2018-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_29 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_29 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_29 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_29 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_29 = parse("2018-07-05 00:00:00.000000");

    public static Issue issue11() {
        return newEntity(ISSUE_ID_11, null, testCustomer16(), null, START_DATE_11, EXPIRATION_DATE_11, TERMINATION_CAUSE_11, issueType1(),
                ISSUE_STATE_11, RPB_PLN_11, BALANCE_START_PLN_11, CURRENT_BALANCE_PLN_11, PAYMENTS_PLN_11, INVOICES_11, EXCLUDED_FROM_STATS_11, PRIORITY_11,
                BALANCE_UPDATE_DATE_11, CREATED_MANUALLY_11, null, null, null, null, RPB_EUR_11, BALANCE_START_EUR_11, CURRENT_BALANCE_EUR_11,
                PAYMENTS_EUR_11, null, TOTAL_BALANCE_START_PLN_11, TOTAL_CURRENT_BALANCE_PLN_11, TOTAL_PAYMENTS_PLN_11, DPD_START_DATE_11);
    }

    public static Issue issue12() {
        return newEntity(ISSUE_ID_12, null, null, contract10(), START_DATE_12, EXPIRATION_DATE_12, TERMINATION_CAUSE_12, issueType1(),
                ISSUE_STATE_12, RPB_PLN_12, BALANCE_START_PLN_12, CURRENT_BALANCE_PLN_12, PAYMENTS_PLN_12, INVOICES_12, EXCLUDED_FROM_STATS_12, PRIORITY_12,
                BALANCE_UPDATE_DATE_12, CREATED_MANUALLY_12, null, null, null, null, RPB_EUR_12, BALANCE_START_EUR_12, CURRENT_BALANCE_EUR_12, PAYMENTS_EUR_12, null,
                TOTAL_BALANCE_START_PLN_12, TOTAL_CURRENT_BALANCE_PLN_12, TOTAL_PAYMENTS_PLN_12, DPD_START_DATE_12);
    }

    public static Issue issue13() {
        return newEntity(ISSUE_ID_13, null, testCustomer4(), contract11(), START_DATE_13, EXPIRATION_DATE_13, TERMINATION_CAUSE_13, issueType1(),
                ISSUE_STATE_13, RPB_PLN_13, BALANCE_START_PLN_13, CURRENT_BALANCE_PLN_13, PAYMENTS_PLN_13, INVOICES_13, EXCLUDED_FROM_STATS_13, PRIORITY_13,
                BALANCE_UPDATE_DATE_13, CREATED_MANUALLY_13, null, null, null, null, RPB_EUR_13, BALANCE_START_EUR_13, CURRENT_BALANCE_EUR_13, PAYMENTS_EUR_13, null,
                TOTAL_BALANCE_START_PLN_13, TOTAL_CURRENT_BALANCE_PLN_13, TOTAL_PAYMENTS_PLN_13, DPD_START_DATE_13);
    }

    public static Issue issue14() {
        return newEntity(ISSUE_ID_14, null, null, contract12(), START_DATE_14, EXPIRATION_DATE_14, TERMINATION_CAUSE_14, issueType1(),
                ISSUE_STATE_14, RPB_PLN_14, BALANCE_START_PLN_14, CURRENT_BALANCE_PLN_14, PAYMENTS_PLN_14, INVOICES_14, EXCLUDED_FROM_STATS_14, PRIORITY_14,
                BALANCE_UPDATE_DATE_14, CREATED_MANUALLY_14, null, null, null, null, RPB_EUR_14, BALANCE_START_EUR_14, CURRENT_BALANCE_EUR_14, PAYMENTS_EUR_14, null,
                TOTAL_BALANCE_START_PLN_14, TOTAL_CURRENT_BALANCE_PLN_14, TOTAL_PAYMENTS_PLN_14, DPD_START_DATE_14);
    }

    public static Issue issue29() {
        return newEntity(ISSUE_ID_29, null, testCustomer26(), null, START_DATE_29, EXPIRATION_DATE_29, TERMINATION_CAUSE_29, issueType1(),
                ISSUE_STATE_29, RPB_PLN_29, BALANCE_START_PLN_29, CURRENT_BALANCE_PLN_29, PAYMENTS_PLN_29, INVOICES_29, EXCLUDED_FROM_STATS_29, PRIORITY_29,
                BALANCE_UPDATE_DATE_29, CREATED_MANUALLY_29, null, null, null, null, RPB_EUR_29, BALANCE_START_EUR_29, CURRENT_BALANCE_EUR_29, PAYMENTS_EUR_29, null,
                TOTAL_BALANCE_START_PLN_29, TOTAL_CURRENT_BALANCE_PLN_29, TOTAL_PAYMENTS_PLN_29, DPD_START_DATE_29);
    }

    public static IssueDto issueDto11() {
        return newDto(ISSUE_ID_11, null, testCustomerDto16(), null, START_DATE_11, EXPIRATION_DATE_11, TERMINATION_CAUSE_11, issueTypeDto1(),
                ISSUE_STATE_DTO_11, RPB_PLN_11, BALANCE_START_PLN_11, CURRENT_BALANCE_PLN_11, PAYMENTS_PLN_11, INVOICES_DTO_11, EXCLUDED_FROM_STATS_11,
                BALANCE_UPDATE_DATE_11, PRIORITY_DTO_11, null, null, null, RPB_EUR_11, BALANCE_START_EUR_11, CURRENT_BALANCE_EUR_11, PAYMENTS_EUR_11,
                TOTAL_BALANCE_START_PLN_11, TOTAL_CURRENT_BALANCE_PLN_11, TOTAL_PAYMENTS_PLN_11, DPD_START_11, DPD_ESTIMATED_11);
    }
}
