package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssuePriorityDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.core.api.model.issue.UnresolvedIssueDto;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.customer.Person;
import com.codersteam.alwin.jpa.issue.Contract;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.Tag;
import com.codersteam.alwin.jpa.type.IssueState;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.testdata.ActivityTestData.activity1;
import static com.codersteam.alwin.testdata.ActivityTestData.activityWithOverdueDeclaration;
import static com.codersteam.alwin.testdata.ActivityTestData.callForPaymentActivity;
import static com.codersteam.alwin.testdata.ActivityTestData.callForPaymentActivityWithoutDeclarations;
import static com.codersteam.alwin.testdata.ActivityTestData.incomingCallActivity;
import static com.codersteam.alwin.testdata.ActivityTestData.incomingCallActivityWithDeclaration;
import static com.codersteam.alwin.testdata.ActivityTestData.outstandingSmsActivity;
import static com.codersteam.alwin.testdata.ContractTestData.contract1;
import static com.codersteam.alwin.testdata.ContractTestData.contract2;
import static com.codersteam.alwin.testdata.ContractTestData.contract3;
import static com.codersteam.alwin.testdata.ContractTestData.contract4;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto1;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto2;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto3;
import static com.codersteam.alwin.testdata.ContractTestData.contractDto4;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer1;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer7;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer8;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer9;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto1;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto7;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto8;
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomerDto9;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_1;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice1;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoiceDto1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType2;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto1;
import static com.codersteam.alwin.testdata.TagTestData.testTag1;
import static com.codersteam.alwin.testdata.TagTestData.testTag2;
import static com.codersteam.alwin.testdata.TagTestData.testTag3;
import static com.codersteam.alwin.testdata.TagTestData.testTagDto1;
import static com.codersteam.alwin.testdata.TagTestData.testTagDto2;
import static com.codersteam.alwin.testdata.TagTestData.testTagDto3;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings("WeakerAccess")
public class AssignedIssuesWithHighPriorityTestData extends AbstractIssuesTestData {

    public static final Integer HIGH_PRIORITY = 1;

    public static final Long ISSUE_ID_1 = 1L;
    public static final Long ISSUE_ID_2 = 2L;
    public static final IssueStateDto ISSUE_STATE_DTO_2 = IssueStateDto.NEW;
    public static final Long ISSUE_ID_3 = 3L;
    public static final Long ISSUE_ID_4 = 4L;
    public static final Long ISSUE_ID_21 = 21L;

    public static final Date START_DATE_1 = parse("2017-07-10 00:00:00.000000");
    public static final Date EXPIRATION_DATE_1 = parse("2017-08-10 00:00:00.000000");
    public static final Date EXPIRATION_DATE_3 = parse("2019-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_1 = "testowy powód 1";
    private static final IssueState ISSUE_STATE_1 = IssueState.NEW;
    private static final IssueStateDto ISSUE_STATE_DTO_1 = IssueStateDto.NEW;
    private static final BigDecimal RPB_PLN_1 = new BigDecimal("290.12");
    private static final BigDecimal BALANCE_START_PLN_1 = new BigDecimal("65432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_1 = new BigDecimal("234642.67");
    private static final BigDecimal PAYMENTS_PLN_1 = new BigDecimal("100000.23");
    private static final BigDecimal RPB_EUR_1 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_1 = new BigDecimal("1230.12");
    private static final BigDecimal CURRENT_BALANCE_EUR_1 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_1 = new BigDecimal("0.00");
    private static final Boolean EXCLUDED_FROM_STATS_1 = false;
    private static final Integer PRIORITY_1 = HIGH_PRIORITY;
    private static final IssuePriorityDto PRIORITY_DTO_1 = IssuePriorityDto.HIGH;
    private static final Date BALANCE_UPDATE_DATE_1 = parse("2017-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_1 = false;
    public static final BigDecimal TOTAL_BALANCE_START_PLN_1 = new BigDecimal("70352.92");
    public static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_1 = new BigDecimal("234642.67");
    public static final BigDecimal TOTAL_PAYMENTS_PLN_1 = new BigDecimal("100000.23");
    public static final Date DPD_START_DATE_1 = parse("2017-07-05 00:00:00.000000");
    private static final Long DPD_START_1 = 5L;
    private static final Long DPD_ESTIMATED_1 = 36L;

    private static final Date START_DATE_2 = parse("2018-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_2 = parse("2018-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_2 = "testowy powód 2";
    private static final IssueState ISSUE_STATE_2 = IssueState.NEW;
    private static final BigDecimal RPB_PLN_2 = new BigDecimal("190.12");
    private static final BigDecimal BALANCE_START_PLN_2 = new BigDecimal("-65432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_2 = new BigDecimal("-234642.67");
    private static final BigDecimal PAYMENTS_PLN_2 = new BigDecimal("987654.23");
    private static final BigDecimal RPB_EUR_2 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_2 = new BigDecimal("230.23");
    private static final BigDecimal CURRENT_BALANCE_EUR_2 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_2 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_2 = emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_2 = emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_2 = false;
    private static final Integer PRIORITY_2 = HIGH_PRIORITY;
    private static final IssuePriorityDto PRIORITY_DTO_2 = IssuePriorityDto.HIGH;
    private static final Date BALANCE_UPDATE_DATE_2 = parse("2018-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_2 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_2 = new BigDecimal("66353.36");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_2 = new BigDecimal("234642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_2 = new BigDecimal("987654.23");
    private static final Date DPD_START_DATE_2 = parse("2018-07-05 00:00:00.000000");
    private static final Long DPD_START_2 = 5L;
    private static final Long DPD_ESTIMATED_2 = 36L;

    private static final Date START_DATE_3 = parse("2019-07-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_3 = "testowy powód 3";
    private static final IssueState ISSUE_STATE_3 = IssueState.IN_PROGRESS;
    private static final IssueStateDto ISSUE_STATE_DTO_3 = IssueStateDto.IN_PROGRESS;
    private static final BigDecimal RPB_PLN_3 = new BigDecimal("280.12");
    private static final BigDecimal BALANCE_START_PLN_3 = new BigDecimal("-65432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_3 = new BigDecimal("-234642.67");
    private static final BigDecimal PAYMENTS_PLN_3 = new BigDecimal("987654.23");
    private static final BigDecimal RPB_EUR_3 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_3 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_3 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_3 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_3 = emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_3 = emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_3 = true;
    private static final Integer PRIORITY_3 = HIGH_PRIORITY;
    private static final IssuePriorityDto PRIORITY_DTO_3 = IssuePriorityDto.HIGH;
    private static final Date BALANCE_UPDATE_DATE_3 = parse("2019-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_3 = true;
    private static final String EXCLUSION_CAUSE_3 = "powód wykluczenia ze statystyk 3";
    private static final BigDecimal TOTAL_BALANCE_START_PLN_3 = new BigDecimal("65432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_3 = new BigDecimal("234642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_3 = new BigDecimal("987654.23");
    private static final Date DPD_START_DATE_3 = parse("2019-07-05 00:00:00.000000");
    private static final Long DPD_START_3 = 5L;
    private static final Long DPD_ESTIMATED_3 = 36L;

    private static final Date START_DATE_4 = parse("2020-07-10 00:00:00.000000");
    private static final Date EXPIRATION_DATE_4 = parse("2020-08-10 00:00:00.000000");
    private static final String TERMINATION_CAUSE_4 = "testowy powód 4";
    private static final IssueState ISSUE_STATE_4 = IssueState.NEW;
    private static final IssueStateDto ISSUE_STATE_DTO_4 = IssueStateDto.NEW;
    private static final BigDecimal RPB_PLN_4 = new BigDecimal("180.12");
    private static final BigDecimal BALANCE_START_PLN_4 = new BigDecimal("65432.44");
    private static final BigDecimal CURRENT_BALANCE_PLN_4 = new BigDecimal("234642.67");
    private static final BigDecimal PAYMENTS_PLN_4 = new BigDecimal("987654.23");
    private static final BigDecimal RPB_EUR_4 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_4 = new BigDecimal("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_4 = new BigDecimal("0.00");
    private static final BigDecimal PAYMENTS_EUR_4 = new BigDecimal("0.00");
    private static final List<Invoice> INVOICES_4 = emptyList();
    private static final List<InvoiceDto> INVOICES_DTO_4 = emptyList();
    private static final Boolean EXCLUDED_FROM_STATS_4 = false;
    private static final Integer PRIORITY_4 = HIGH_PRIORITY;
    private static final IssuePriorityDto PRIORITY_DTO_4 = IssuePriorityDto.HIGH;
    private static final Date BALANCE_UPDATE_DATE_4 = parse("2020-07-10 00:00:00.000000");
    private static final boolean CREATED_MANUALLY_4 = false;
    private static final BigDecimal TOTAL_BALANCE_START_PLN_4 = new BigDecimal("65432.44");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_4 = new BigDecimal("234642.67");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_4 = new BigDecimal("987654.23");
    private static final Date DPD_START_DATE_4 = parse("2020-07-05 00:00:00.000000");
    private static final Long DPD_START_4 = 5L;
    private static final Long DPD_ESTIMATED_4 = 36L;

    public static Issue issue1() {
        final Issue issue = newEntity(ISSUE_ID_1, testOperator1(), testCustomer1(), contract1(), START_DATE_1, EXPIRATION_DATE_1, TERMINATION_CAUSE_1,
                issueType1(), ISSUE_STATE_1, RPB_PLN_1, BALANCE_START_PLN_1, CURRENT_BALANCE_PLN_1, PAYMENTS_PLN_1, singletonList(testInvoice1()), EXCLUDED_FROM_STATS_1,
                PRIORITY_1, BALANCE_UPDATE_DATE_1, CREATED_MANUALLY_1, null, null, null, null, RPB_EUR_1,
                BALANCE_START_EUR_1, CURRENT_BALANCE_EUR_1, PAYMENTS_EUR_1, null, new HashSet<>(asList(testTag1(), testTag2())),
                TOTAL_BALANCE_START_PLN_1, TOTAL_CURRENT_BALANCE_PLN_1, TOTAL_PAYMENTS_PLN_1, DPD_START_DATE_1);
        issue.setIssueInvoices(createIssueInvoices(issue, singletonList(testInvoice1())));
        return issue;
    }

    public static Issue newIssue1() {
        return clearIds(issue1());
    }

    public static Issue issue2() {
        return newEntity(ISSUE_ID_2, testOperator1(), testCustomer7(), contract2(), START_DATE_2, EXPIRATION_DATE_2, TERMINATION_CAUSE_2, issueType1(),
                ISSUE_STATE_2, RPB_PLN_2, BALANCE_START_PLN_2, CURRENT_BALANCE_PLN_2, PAYMENTS_PLN_2, INVOICES_2, EXCLUDED_FROM_STATS_2, PRIORITY_2, BALANCE_UPDATE_DATE_2,
                CREATED_MANUALLY_2, null, null, null, null, RPB_EUR_2, BALANCE_START_EUR_2, CURRENT_BALANCE_EUR_2,
                PAYMENTS_EUR_2, null, new HashSet<>(singletonList(testTag1())), TOTAL_BALANCE_START_PLN_2, TOTAL_CURRENT_BALANCE_PLN_2, TOTAL_PAYMENTS_PLN_2, DPD_START_DATE_2);
    }

    public static Issue issue3() {
        return newEntity(ISSUE_ID_3, testOperator1(), testCustomer8(), contract3(), START_DATE_3, EXPIRATION_DATE_3, TERMINATION_CAUSE_3, issueType2(),
                ISSUE_STATE_3, RPB_PLN_3, BALANCE_START_PLN_3, CURRENT_BALANCE_PLN_3, PAYMENTS_PLN_3, INVOICES_3, EXCLUDED_FROM_STATS_3, PRIORITY_3, BALANCE_UPDATE_DATE_3,
                CREATED_MANUALLY_3, null, null, null, EXCLUSION_CAUSE_3, RPB_EUR_3, BALANCE_START_EUR_3, CURRENT_BALANCE_EUR_3,
                PAYMENTS_EUR_3, null, new HashSet<>(asList(testTag3(), testTag1())), TOTAL_BALANCE_START_PLN_3, TOTAL_CURRENT_BALANCE_PLN_3, TOTAL_PAYMENTS_PLN_3, DPD_START_DATE_3);
    }

    public static Issue manuallyCreatedIssue() {
        return issue3();
    }

    public static Issue issue4() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4, null,
                TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static Issue issue4WithActivity() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                singletonList(activity1()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static Issue issueWithActivitiesAndOutstandingSmsActivity() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                asList(activity1(), outstandingSmsActivity()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static Issue issueWithIncomingCallActivityAndOutstandingSmsActivity() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                asList(activityWithOverdueDeclaration(), outstandingSmsActivity()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static Issue issueWithIncomingCallActivity() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                singletonList(incomingCallActivity()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static Issue issueWithIncomingCallActivityWithDeclaration() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                singletonList(incomingCallActivityWithDeclaration()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }


    public static Issue issueWithIncomingCallActivityWithDeclarationWithoutAssignee() {
        return newEntity(ISSUE_ID_4, null, testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                singletonList(incomingCallActivityWithDeclaration()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static Issue issueWithCallForPaymentActivity() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                singletonList(callForPaymentActivity()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static Issue issueWithCallForPaymentActivityWithoutDeclarations() {
        return newEntity(ISSUE_ID_4, testOperator1(), testCustomer9(), contract4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4, issueType1(),
                ISSUE_STATE_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_4, EXCLUDED_FROM_STATS_4, PRIORITY_4, BALANCE_UPDATE_DATE_4,
                CREATED_MANUALLY_4, null, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                singletonList(callForPaymentActivityWithoutDeclarations()), TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_DATE_4);
    }

    public static IssueDto issueDto1() {
        return newDto(ISSUE_ID_1, testOperatorUserDto1(), testCustomerDto1(), contractDto1(), START_DATE_1, EXPIRATION_DATE_1, TERMINATION_CAUSE_1, issueTypeDto1(),
                ISSUE_STATE_DTO_1, RPB_PLN_1, BALANCE_START_PLN_1, CURRENT_BALANCE_PLN_1, PAYMENTS_PLN_1, singletonList(testInvoiceDto1()), EXCLUDED_FROM_STATS_1,
                BALANCE_UPDATE_DATE_1, PRIORITY_DTO_1, null, null, null, RPB_EUR_1, BALANCE_START_EUR_1, CURRENT_BALANCE_EUR_1, PAYMENTS_EUR_1,
                TOTAL_BALANCE_START_PLN_1, TOTAL_CURRENT_BALANCE_PLN_1, TOTAL_PAYMENTS_PLN_1, asList(testTagDto1(), testTagDto2()), DPD_START_1, DPD_ESTIMATED_1);
    }

    public static IssueDto issueDto2() {
        return newDto(ISSUE_ID_2, testOperatorUserDto1(), testCustomerDto7(), contractDto2(), START_DATE_2, EXPIRATION_DATE_2, TERMINATION_CAUSE_2,
                issueTypeDto1(), ISSUE_STATE_DTO_2, RPB_PLN_2, BALANCE_START_PLN_2, CURRENT_BALANCE_PLN_2, PAYMENTS_PLN_2, INVOICES_DTO_2, EXCLUDED_FROM_STATS_2,
                BALANCE_UPDATE_DATE_2, PRIORITY_DTO_2, null, null, null, RPB_EUR_2, BALANCE_START_EUR_2, CURRENT_BALANCE_EUR_2, PAYMENTS_EUR_2,
                TOTAL_BALANCE_START_PLN_2, TOTAL_CURRENT_BALANCE_PLN_2, TOTAL_PAYMENTS_PLN_2, singletonList(testTagDto1()), DPD_START_2, DPD_ESTIMATED_2);
    }

    public static IssueDto issueDto3() {
        return newDto(ISSUE_ID_3, testOperatorUserDto1(), testCustomerDto8(), contractDto3(), START_DATE_3, EXPIRATION_DATE_3, TERMINATION_CAUSE_3,
                issueTypeDto2(), ISSUE_STATE_DTO_3, RPB_PLN_3, BALANCE_START_PLN_3, CURRENT_BALANCE_PLN_3, PAYMENTS_PLN_3, INVOICES_DTO_3, EXCLUDED_FROM_STATS_3,
                BALANCE_UPDATE_DATE_3, PRIORITY_DTO_3, null, null, EXCLUSION_CAUSE_3, RPB_EUR_3, BALANCE_START_EUR_3, CURRENT_BALANCE_EUR_3,
                PAYMENTS_EUR_3, TOTAL_BALANCE_START_PLN_3, TOTAL_CURRENT_BALANCE_PLN_3, TOTAL_PAYMENTS_PLN_3, asList(testTagDto1(), testTagDto3()),
                DPD_START_3, DPD_ESTIMATED_3);
    }

    public static IssueDto issueDto4() {
        return newDto(ISSUE_ID_4, testOperatorUserDto1(), testCustomerDto9(), contractDto4(), START_DATE_4, EXPIRATION_DATE_4, TERMINATION_CAUSE_4,
                issueTypeDto1(), ISSUE_STATE_DTO_4, RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4, INVOICES_DTO_4, EXCLUDED_FROM_STATS_4,
                BALANCE_UPDATE_DATE_4, PRIORITY_DTO_4, null, null, null, RPB_EUR_4, BALANCE_START_EUR_4, CURRENT_BALANCE_EUR_4, PAYMENTS_EUR_4,
                TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4, DPD_START_4, DPD_ESTIMATED_4);
    }

    public static UnresolvedIssueDto expectedUnresolvedIssueDto() {
        final UnresolvedIssueDto unresolvedIssueDto = new UnresolvedIssueDto();
        unresolvedIssueDto.setExtContractsNumbers(singleton(CONTRACT_NUMBER_INVOICE_1));
        unresolvedIssueDto.setCurrentBalancePLN(CURRENT_BALANCE_PLN_1);
        unresolvedIssueDto.setBalanceStartPLN(BALANCE_START_PLN_1);
        unresolvedIssueDto.setPaymentsPLN(PAYMENTS_PLN_1);
        unresolvedIssueDto.setIssueId(ISSUE_ID_1);
        unresolvedIssueDto.setRpbPLN(RPB_PLN_1);
        unresolvedIssueDto.setRpbEUR(RPB_EUR_1);
        unresolvedIssueDto.setBalanceStartEUR(BALANCE_START_EUR_1);
        unresolvedIssueDto.setCurrentBalanceEUR(CURRENT_BALANCE_EUR_1);
        unresolvedIssueDto.setPaymentsEUR(PAYMENTS_EUR_1);
        return unresolvedIssueDto;
    }

    private static Issue clearIds(final Issue issue) {
        issue.setId(null);
        final Contract contract = issue.getContract();
        contract.setId(null);
        clearCustomerIds(contract.getCustomer());
        clearCustomerIds(issue.getCustomer());
        clearTagIds(issue.getTags());
        return issue;
    }

    private static void clearTagIds(final Set<Tag> tags) {
        tags.forEach(tag -> tag.setId(null));
    }

    private static void clearCustomerIds(final Customer customer) {
        customer.setId(null);
        final Company company = customer.getCompany();
        company.setId(null);

        for (final Address address : company.getAddresses()) {
            address.setId(null);
        }
        for (final ContactDetail contactDetail : company.getContactDetails()) {
            contactDetail.setId(null);
        }
        final Person person = customer.getPerson();
        person.setId(null);
        for (final Address address : person.getAddresses()) {
            address.setId(null);
        }
        for (final ContactDetail contactDetail : person.getContactDetails()) {
            contactDetail.setId(null);
        }
    }
}
