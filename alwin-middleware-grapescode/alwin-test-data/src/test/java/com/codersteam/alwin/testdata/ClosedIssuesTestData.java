package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.issue.CompanyIssueDto;
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

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_3;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_4;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_5;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_6;
import static com.codersteam.alwin.testdata.ContractTestData.contract17;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto17;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer3;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer5;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto3;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto5;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType3;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator3;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto3;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;

@SuppressWarnings("WeakerAccess")
public class ClosedIssuesTestData extends AbstractIssuesTestData {

    public static final long ISSUE_ID_19 = 19;
    private static final Date START_DATE_19 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_19 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_19 = "to zlecenie zostało zakończone - status DONE";
    private static final Date TERMINATION_DATE_19 = parse("2023-07-15 00:00:00.000000");
    private static final IssueState ISSUE_STATE_19 = IssueState.DONE;
    private static final IssueStateDto ISSUE_STATE_DTO_19 = IssueStateDto.DONE;
    private static final BigDecimal RPB_PLN_19 = new BigDecimal("333432.12");
    private static final BigDecimal BALANCE_START_PLN_19 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_19 = new BigDecimal("554642.67");
    private static final BigDecimal PAYMENTS_PLN_19 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_19 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_19 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_19 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_19 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_19 = Collections.emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_19 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_19 = true;
    private static final Integer PRIORITY_19 = 1;
    private static final IssuePriorityDto PRIORITY_DTO_19 = IssuePriorityDto.HIGH;
    private static final Date BALANCE_UPDATE_DATE_19 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_19 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_19 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_19 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_19 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_19 = parse("2023-07-05 00:00:00.000000");
    private static final Long DPD_START_19 = 5L;
    private static final Long DPD_ESTIMATED_19 = 402L;

    public static final long ISSUE_ID_20 = 20;
    private static final Date START_DATE_20 = parse("2023-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_20 = parse("2024-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_20 = "to zlecenie zostało anulowane - status CANCELED";
    private static final Date TERMINATION_DATE_20 = parse("2023-07-15 00:00:00.000000");
    private static final IssueState ISSUE_STATE_20 = IssueState.CANCELED;
    private static final IssueStateDto ISSUE_STATE_DTO_20 = IssueStateDto.CANCELED;
    private static final BigDecimal RPB_PLN_20 = new BigDecimal("333432.12");
    private static final BigDecimal BALANCE_START_PLN_20 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_20 = new BigDecimal("554643.67");
    private static final BigDecimal PAYMENTS_PLN_20 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_20 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_20 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_20 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_20 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_20 = Collections.emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_20 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_20 = true;
    private static final Integer PRIORITY_20 = 0;
    private static final IssuePriorityDto PRIORITY_DTO_20 = IssuePriorityDto.NORMAL;
    private static final Date BALANCE_UPDATE_DATE_20 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_20 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_20 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_20 = new BigDecimal("554643.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_20 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_20 = parse("2023-07-05 00:00:00.000000");
    private static final Long DPD_START_20 = 5L;
    private static final Long DPD_ESTIMATED_20 = 402L;

    public static final long ISSUE_ID_23 = 23;
    private static final Date START_DATE_23 = parse("2023-06-09 00:00:00.000000");
    private static final Date EXPIRATION_DATE_23 = parse("2024-07-08 00:00:00.000000");
    private static final String TERMINATION_CAUSE_23 = "testowy powód 23";
    private static final IssueState ISSUE_STATE_23 = IssueState.CANCELED;
    private static final BigDecimal RPB_PLN_23 = new BigDecimal("283.00");
    private static final BigDecimal BALANCE_START_PLN_23 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_23 = new BigDecimal("554642.67");
    private static final BigDecimal PAYMENTS_PLN_23 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_23 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_23 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_23 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_23 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_23 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_23 = true;
    private static final Integer PRIORITY_23 = 0;
    private static final Date BALANCE_UPDATE_DATE_23 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_23 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_23 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_23 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_23 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_23 = parse("2023-06-04 00:00:00.000000");

    public static final long ISSUE_ID_24 = 24;
    private static final Date START_DATE_24 = parse("2023-06-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_24 = parse("2024-07-09 00:00:00.000000");
    private static final String TERMINATION_CAUSE_24 = "testowy powód 24";
    private static final IssueState ISSUE_STATE_24 = IssueState.CANCELED;
    private static final BigDecimal RPB_PLN_24 = new BigDecimal("183.00");
    private static final BigDecimal BALANCE_START_PLN_24 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_24 = new BigDecimal("554643.67");
    private static final BigDecimal PAYMENTS_PLN_24 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_24 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_24 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_24 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_24 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_24 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_24 = true;
    private static final Integer PRIORITY_24 = 0;
    private static final Date BALANCE_UPDATE_DATE_24 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_24 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_24 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_24 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_24 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_24 = parse("2023-06-05 00:00:00.000000");

    public static final long ISSUE_ID_25 = 25;
    private static final Date START_DATE_25 = parse("2023-06-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_25 = parse("2024-07-09 00:00:00.000000");
    private static final String TERMINATION_CAUSE_25 = "to zlecenie zostało anulowane - status CANCELED";
    private static final Date TERMINATION_DATE_25 = parse("2023-07-15 00:00:00.000000");
    private static final IssueState ISSUE_STATE_25 = IssueState.CANCELED;
    private static final BigDecimal RPB_PLN_25 = new BigDecimal("333432.12");
    private static final BigDecimal BALANCE_START_PLN_25 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_25 = new BigDecimal("554643.67");
    private static final BigDecimal PAYMENTS_PLN_25 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_25 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_25 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_25 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_25 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_25 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_25 = true;
    private static final Integer PRIORITY_25 = 0;
    private static final Date BALANCE_UPDATE_DATE_25 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_25 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_25 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_25 = new BigDecimal("554643.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_25 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_25 = parse("2023-06-05 00:00:00.000000");

    public static final long ISSUE_ID_26 = 26;
    private static final Date START_DATE_26 = parse("2023-06-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_26 = parse("2024-07-09 00:00:00.000000");
    private static final String TERMINATION_CAUSE_26 = "testowy powód 26";
    private static final IssueState ISSUE_STATE_26 = IssueState.CANCELED;
    private static final BigDecimal RPB_PLN_26 = new BigDecimal("333432.12");
    private static final BigDecimal BALANCE_START_PLN_26 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_26 = new BigDecimal("554643.67");
    private static final BigDecimal PAYMENTS_PLN_26 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_26 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_26 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_26 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_26 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_26 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_26 = true;
    private static final Integer PRIORITY_26 = 0;
    private static final Date BALANCE_UPDATE_DATE_26 = parse("2023-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_26 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_26 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_26 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_26 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_26 = parse("2023-06-05 00:00:00.000000");

    public static final long ISSUE_ID_27 = 27;
    private static final Date START_DATE_27 = parse("2018-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_27 = parse("2018-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_27 = "to zlecenie zostało zakończone - status DONE";
    private static final Date TERMINATION_DATE_27 = parse("2018-07-15 00:00:00.000000");
    private static final IssueState ISSUE_STATE_27 = IssueState.DONE;
    private static final BigDecimal RPB_PLN_27 = new BigDecimal("333432.12");
    private static final BigDecimal BALANCE_START_PLN_27 = new BigDecimal("44432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_27 = new BigDecimal("554642.67");
    private static final BigDecimal PAYMENTS_PLN_27 = new BigDecimal("667654.23");
    private static final BigDecimal RPB_EUR_27 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_27 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_27 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_27 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_27 = Collections.emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_27 = true;
    private static final Integer PRIORITY_27 = 1;
    private static final Date BALANCE_UPDATE_DATE_27 = parse("2018-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_27 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_27 = new BigDecimal("44432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_27 = new BigDecimal("554642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_27 = new BigDecimal("667654.23");
    private static final Date DPD_START_DATE_27 = parse("2018-07-05 00:00:00.000000");

    public static final long ISSUE_ID_28 = 28;
    public static final long ISSUE_ID_29 = 29;
    public static final long ISSUE_ID_31 = 31;

    public static final List<Long> ISSUE_TO_CLOSE_IDS = asList(ISSUE_ID_1, ISSUE_ID_2, ISSUE_ID_3, ISSUE_ID_4, ISSUE_ID_5, ISSUE_ID_6, ISSUE_ID_28,
            ISSUE_ID_29, ISSUE_ID_31);

    public static Issue issue19() {
        return newEntity(ISSUE_ID_19, null, testCustomer3(), null, START_DATE_19, EXPIRATION_DATE_19, TERMINATION_CAUSE_19, issueType1(),
                ISSUE_STATE_19, RPB_PLN_19, BALANCE_START_PLN_19, CURRENT_BALANCE_PLN_19, PAYMENTS_PLN_19, INVOICES_19, EXCLUDED_FROM_STATS_19, PRIORITY_19,
                BALANCE_UPDATE_DATE_19, CREATED_MANUALLY_19, null, TERMINATION_DATE_19, testOperator3(), null, RPB_EUR_19, BALANCE_START_EUR_19,
                CURRENT_BALANCE_EUR_19, PAYMENTS_EUR_19, null, TOTAL_BALANCE_START_PLN_19, TOTAL_CURRENT_BALANCE_PLN_19, TOTAL_PAYMENTS_PLN_19, DPD_START_DATE_19);
    }

    public static Issue issue20() {
        return newEntity(ISSUE_ID_20, testOperator1(), testCustomer5(), contract17(), START_DATE_20, EXPIRATION_DATE_20, TERMINATION_CAUSE_20, issueType1(),
                ISSUE_STATE_20, RPB_PLN_20, BALANCE_START_PLN_20, CURRENT_BALANCE_PLN_20, PAYMENTS_PLN_20, INVOICES_20, EXCLUDED_FROM_STATS_20, PRIORITY_20,
                BALANCE_UPDATE_DATE_20, CREATED_MANUALLY_20, issue25(), TERMINATION_DATE_20, testOperator3(), null, RPB_EUR_20, BALANCE_START_EUR_20,
                CURRENT_BALANCE_EUR_20, PAYMENTS_EUR_20, null, TOTAL_BALANCE_START_PLN_20, TOTAL_CURRENT_BALANCE_PLN_20, TOTAL_PAYMENTS_PLN_20, DPD_START_DATE_20);
    }

    public static Issue issue23() {
        return newEntity(ISSUE_ID_23, testOperator1(), testCustomer5(), contract17(), START_DATE_23, EXPIRATION_DATE_23, TERMINATION_CAUSE_23, issueType1(),
                ISSUE_STATE_23, RPB_PLN_23, BALANCE_START_PLN_23, CURRENT_BALANCE_PLN_23, PAYMENTS_PLN_23, INVOICES_23, EXCLUDED_FROM_STATS_23, PRIORITY_23,
                BALANCE_UPDATE_DATE_23, CREATED_MANUALLY_23, null, null, testOperator3(), null, RPB_EUR_23, BALANCE_START_EUR_23,
                CURRENT_BALANCE_EUR_23, PAYMENTS_EUR_23, null, TOTAL_BALANCE_START_PLN_23, TOTAL_CURRENT_BALANCE_PLN_23, TOTAL_PAYMENTS_PLN_23, DPD_START_DATE_23);
    }

    public static Issue issue24() {
        return newEntity(ISSUE_ID_24, testOperator1(), testCustomer5(), contract17(), START_DATE_24, EXPIRATION_DATE_24, TERMINATION_CAUSE_24, issueType1(),
                ISSUE_STATE_24, RPB_PLN_24, BALANCE_START_PLN_24, CURRENT_BALANCE_PLN_24, PAYMENTS_PLN_24, INVOICES_24, EXCLUDED_FROM_STATS_24, PRIORITY_24,
                BALANCE_UPDATE_DATE_24, CREATED_MANUALLY_24, null, null, testOperator3(), null, RPB_EUR_24, BALANCE_START_EUR_24,
                CURRENT_BALANCE_EUR_24, PAYMENTS_EUR_24, null, TOTAL_BALANCE_START_PLN_24, TOTAL_CURRENT_BALANCE_PLN_24, TOTAL_PAYMENTS_PLN_24, DPD_START_DATE_24);
    }

    public static Issue issue25() {
        return newEntity(ISSUE_ID_25, testOperator1(), testCustomer5(), contract17(), START_DATE_25, EXPIRATION_DATE_25, TERMINATION_CAUSE_25, issueType1(),
                ISSUE_STATE_25, RPB_PLN_25, BALANCE_START_PLN_25, CURRENT_BALANCE_PLN_25, PAYMENTS_PLN_25, INVOICES_25, EXCLUDED_FROM_STATS_25, PRIORITY_25,
                BALANCE_UPDATE_DATE_25, CREATED_MANUALLY_25, null, TERMINATION_DATE_25, testOperator3(), null, RPB_EUR_25, BALANCE_START_EUR_25,
                CURRENT_BALANCE_EUR_25, PAYMENTS_EUR_25, null, TOTAL_BALANCE_START_PLN_25, TOTAL_CURRENT_BALANCE_PLN_25, TOTAL_PAYMENTS_PLN_25, DPD_START_DATE_25);
    }

    public static Issue issue26() {
        return newEntity(ISSUE_ID_26, testOperator1(), testCustomer5(), contract17(), START_DATE_26, EXPIRATION_DATE_26, TERMINATION_CAUSE_26, issueType1(),
                ISSUE_STATE_26, RPB_PLN_26, BALANCE_START_PLN_26, CURRENT_BALANCE_PLN_26, PAYMENTS_PLN_26, INVOICES_26, EXCLUDED_FROM_STATS_26, PRIORITY_26,
                BALANCE_UPDATE_DATE_26, CREATED_MANUALLY_26, null, null, testOperator3(), null, RPB_EUR_26, BALANCE_START_EUR_26,
                CURRENT_BALANCE_EUR_26, PAYMENTS_EUR_26, null, TOTAL_BALANCE_START_PLN_26, TOTAL_CURRENT_BALANCE_PLN_26, TOTAL_PAYMENTS_PLN_26, DPD_START_DATE_26);
    }

    public static Issue issue27() {
        return newEntity(ISSUE_ID_27, null, testCustomer3(), null, START_DATE_27, EXPIRATION_DATE_27, TERMINATION_CAUSE_27, issueType3(),
                ISSUE_STATE_27, RPB_PLN_27, BALANCE_START_PLN_27, CURRENT_BALANCE_PLN_27, PAYMENTS_PLN_27, INVOICES_27, EXCLUDED_FROM_STATS_27, PRIORITY_27,
                BALANCE_UPDATE_DATE_27, CREATED_MANUALLY_27, null, TERMINATION_DATE_27, testOperator3(), null, RPB_EUR_27, BALANCE_START_EUR_27,
                CURRENT_BALANCE_EUR_27, PAYMENTS_EUR_27, null, TOTAL_BALANCE_START_PLN_27, TOTAL_CURRENT_BALANCE_PLN_27, TOTAL_PAYMENTS_PLN_27, DPD_START_DATE_27);
    }

    public static IssueDto issueDto19() {
        return newDto(ISSUE_ID_19, null, testCustomerDto3(), null, START_DATE_19, EXPIRATION_DATE_19, TERMINATION_CAUSE_19, issueTypeDto1(),
                ISSUE_STATE_DTO_19, RPB_PLN_19, BALANCE_START_PLN_19, CURRENT_BALANCE_PLN_19, PAYMENTS_PLN_19, INVOICES_DTO_19, EXCLUDED_FROM_STATS_19,
                BALANCE_UPDATE_DATE_19, PRIORITY_DTO_19, TERMINATION_DATE_19, testOperatorUserDto3(), null, RPB_EUR_19,
                BALANCE_START_EUR_19, CURRENT_BALANCE_EUR_19, PAYMENTS_EUR_19,
                TOTAL_BALANCE_START_PLN_19, TOTAL_CURRENT_BALANCE_PLN_19, TOTAL_PAYMENTS_PLN_19, DPD_START_19, DPD_ESTIMATED_19);
    }

    public static CompanyIssueDto companyIssueDto19() {
        return companyIssueDto(ISSUE_ID_19, START_DATE_19, EXPIRATION_DATE_19, TERMINATION_CAUSE_19, issueTypeDto1(),
                ISSUE_STATE_DTO_19, RPB_PLN_19, BALANCE_START_PLN_19, CURRENT_BALANCE_PLN_19, PAYMENTS_PLN_19, EXCLUDED_FROM_STATS_19,
                TERMINATION_DATE_19, testOperatorUserDto3(), RPB_EUR_19, BALANCE_START_EUR_19, CURRENT_BALANCE_EUR_19, PAYMENTS_EUR_19);
    }

    public static IssueDto issueDto20() {
        return newDto(ISSUE_ID_20, testOperatorUserDto1(), testCustomerDto5(), contractDto17(), START_DATE_20, EXPIRATION_DATE_20, TERMINATION_CAUSE_20,
                issueTypeDto1(), ISSUE_STATE_DTO_20, RPB_PLN_20, BALANCE_START_PLN_20, CURRENT_BALANCE_PLN_20, PAYMENTS_PLN_20, INVOICES_DTO_20,
                EXCLUDED_FROM_STATS_20, BALANCE_UPDATE_DATE_20, PRIORITY_DTO_20, TERMINATION_DATE_20, testOperatorUserDto3(), null, RPB_EUR_20,
                BALANCE_START_EUR_20, CURRENT_BALANCE_EUR_20, PAYMENTS_EUR_20,
                TOTAL_BALANCE_START_PLN_20, TOTAL_CURRENT_BALANCE_PLN_20, TOTAL_PAYMENTS_PLN_20, DPD_START_20, DPD_ESTIMATED_20);
    }

    public static CompanyIssueDto companyIssueDto20() {
        return companyIssueDto(ISSUE_ID_20, START_DATE_20, EXPIRATION_DATE_20, TERMINATION_CAUSE_20,
                issueTypeDto1(), ISSUE_STATE_DTO_20, RPB_PLN_20, BALANCE_START_PLN_20, CURRENT_BALANCE_PLN_20, PAYMENTS_PLN_20,
                EXCLUDED_FROM_STATS_20, TERMINATION_DATE_20, testOperatorUserDto3(), RPB_EUR_20,
                BALANCE_START_EUR_20, CURRENT_BALANCE_EUR_20, PAYMENTS_EUR_20);
    }
}
