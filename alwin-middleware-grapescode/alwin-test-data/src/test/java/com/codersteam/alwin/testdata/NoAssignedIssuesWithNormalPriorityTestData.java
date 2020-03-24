package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssuePriorityDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.type.IssueState;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue24;
import static com.codersteam.alwin.testdata.ContractTestData.contract13;
import static com.codersteam.alwin.testdata.ContractTestData.contract16;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto13;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Collections.emptyList;

@SuppressWarnings("WeakerAccess")
public class NoAssignedIssuesWithNormalPriorityTestData extends AbstractIssuesTestData {

    public static final long ISSUE_ID_15 = 15;
    private static final Date START_DATE_15 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_15 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_15 = "testowy powód 15";
    private static final IssueState ISSUE_STATE_15 = IssueState.NEW;
    private static final IssueStateDto ISSUE_STATE_DTO_15 = IssueStateDto.NEW;
    private static final BigDecimal RPB_PLN_15 = new BigDecimal("293.00");
    private static final BigDecimal BALANCE_START_PLN_15 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_15 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_15 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_15 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_15 = new BigDecimal("-2320.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_15 = new BigDecimal("-230.00");
    private static final BigDecimal PAYMENTS_EUR_15 = new BigDecimal("30.00");
    private static final List<Invoice> INVOICES_15 = emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_15 = emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_15 = true;
    private static final Integer PRIORITY_15 = 0;
    private static final IssuePriorityDto PRIORITY_DTO_15 = IssuePriorityDto.NORMAL;
    private static final Date BALANCE_UPDATE_DATE_15 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_15 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_15 = new BigDecimal("35152.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_15 = new BigDecimal("553722.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_15 = new BigDecimal("667774.23");
    private static final Date DPD_START_DATE_15 = parse("2023-07-05 00:00:00.000000");
    private static final Long DPD_START_15 = 5L;
    private static final Long DPD_ESTIMATED_15 = 402L;

    public static final long ISSUE_ID_16 = 16;
    public static final long ISSUE_ID_17 = 17;

    public static final long ISSUE_ID_18 = 18;
    private static final Date START_DATE_18 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_18 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_18 = "testowy powód 18";
    private static final IssueState ISSUE_STATE_18 = IssueState.IN_PROGRESS;
    private static final BigDecimal RPB_PLN_18 = new BigDecimal("183.00");
    private static final BigDecimal BALANCE_START_PLN_18 = new BigDecimal("-44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_18 = new BigDecimal("-554642.67");
    private static final BigDecimal PAYMENTS_PLN_18 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_18 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_18 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_18 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_18 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_18 = emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_18 = true;
    private static final Integer PRIORITY_18 = 0;
    private static final Date BALANCE_UPDATE_DATE_18 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_18 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_18 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_18 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_18 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_18 = parse("2023-07-05 00:00:00.000000");

    public static Issue issue15() {
        return newEntity(ISSUE_ID_15, null, null, contract13(), START_DATE_15, EXPIRATION_DATE_15, TERMINATION_CAUSE_15, issueType1(),
                ISSUE_STATE_15, RPB_PLN_15, BALANCE_START_PLN_15, CURRENT_BALANCE_PLN_15, PAYMENTS_PLN_15, INVOICES_15, EXCLUDED_FROM_STATS_15, PRIORITY_15,
                BALANCE_UPDATE_DATE_15, CREATED_MANUALLY_15, null, null, null, null, RPB_EUR_15, BALANCE_START_EUR_15, CURRENT_BALANCE_EUR_15, PAYMENTS_EUR_15, null,
                TOTAL_BALANCE_START_PLN_15, TOTAL_CURRENT_BALANCE_PLN_15, TOTAL_PAYMENTS_PLN_15, DPD_START_DATE_15);
    }

    public static Issue issue18() {
        return newEntity(ISSUE_ID_18, null, null, contract16(), START_DATE_18, EXPIRATION_DATE_18, TERMINATION_CAUSE_18, issueType1(),
                ISSUE_STATE_18, RPB_PLN_18, BALANCE_START_PLN_18, CURRENT_BALANCE_PLN_18, PAYMENTS_PLN_18, INVOICES_18, EXCLUDED_FROM_STATS_18, PRIORITY_18,
                BALANCE_UPDATE_DATE_18, CREATED_MANUALLY_18, issue24(), null, null, null, RPB_EUR_18, BALANCE_START_EUR_18, CURRENT_BALANCE_EUR_18,
                PAYMENTS_EUR_18, null, TOTAL_BALANCE_START_PLN_18, TOTAL_CURRENT_BALANCE_PLN_18, TOTAL_PAYMENTS_PLN_18, DPD_START_DATE_18);
    }

    public static IssueDto unassignedIssueDto15() {
        return newDto(ISSUE_ID_15, null, null, contractDto13(), START_DATE_15, EXPIRATION_DATE_15, TERMINATION_CAUSE_15,
                issueTypeDto1(), ISSUE_STATE_DTO_15, RPB_PLN_15, BALANCE_START_PLN_15, CURRENT_BALANCE_PLN_15, PAYMENTS_PLN_15, INVOICES_DTO_15, EXCLUDED_FROM_STATS_15,
                BALANCE_UPDATE_DATE_15, PRIORITY_DTO_15, null, null, null, RPB_EUR_15, BALANCE_START_EUR_15, CURRENT_BALANCE_EUR_15, PAYMENTS_EUR_15,
                TOTAL_BALANCE_START_PLN_15, TOTAL_CURRENT_BALANCE_PLN_15, TOTAL_PAYMENTS_PLN_15, DPD_START_15, DPD_ESTIMATED_15);
    }
}
