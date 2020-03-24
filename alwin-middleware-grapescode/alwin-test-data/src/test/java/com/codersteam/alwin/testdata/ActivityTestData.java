package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;
import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityStateDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto;
import com.codersteam.alwin.core.api.model.declaration.DeclarationDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.jpa.OperatorsQueue;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.ActivityDetail;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.activity.Declaration;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.ActivityState;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.jpa.type.ActivityState.CANCELED;
import static com.codersteam.alwin.jpa.type.ActivityState.EXECUTED;
import static com.codersteam.alwin.jpa.type.ActivityState.PLANNED;
import static com.codersteam.alwin.jpa.type.ActivityState.POSTPONED;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.TEST_ACTIVITY_DETAIL_1;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.TEST_ACTIVITY_DETAIL_2;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.TEST_ACTIVITY_DETAIL_DTO_1;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.TEST_ACTIVITY_DETAIL_DTO_2;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.TEST_ACTIVITY_DETAIL_DTO_REQUIRED;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.TEST_ACTIVITY_DETAIL_WITHOUT_ID_1;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.TEST_ACTIVITY_DETAIL_WITHOUT_ID_2;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.commentActivityDetails;
import static com.codersteam.alwin.testdata.ActivityDetailTestData.outstandingSmsActivityDetails;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_2;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_5;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_DTO_1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.activityType1;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.activityType4;
import static com.codersteam.alwin.testdata.ActivityTypeTestData.activityType6;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue21;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_1;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_1_DTO;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_1_WITHOUT_ID;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_2;
import static com.codersteam.alwin.testdata.DeclarationTestData.DECLARATION_2_WITHOUT_ID;
import static com.codersteam.alwin.testdata.DeclarationTestData.PAID_DECLARATION_1;
import static com.codersteam.alwin.testdata.DeclarationTestData.UNPAID_DECLARATION_1;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issue11;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator5;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorWithIdOnly;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@SuppressWarnings("WeakerAccess")
public final class ActivityTestData {

    public static final Date CURRENT_DATE = parse("2017-08-12 00:00:00.000000");

    /**
     * identyfikator nowej czynności w db (sekwencja od 1)
     */
    public static final Long DB_ACTIVITY_ID_1 = 1L;

    public static final boolean NOT_MANAGED = false;
    public static final boolean MANAGED = true;

    public static final Long ACTIVITY_ID_1 = 11L;
    public static final Long ACTIVITY_ID_100 = 100L;
    public static final Long ACTIVITY_ID_111 = 111L;
    public static final Long ACTIVITY_ID_2 = 21L;
    public static final Long ACTIVITY_ID_3 = 1L;
    public static final Long ACTIVITY_ID_11 = 11L;
    public static final Long ACTIVITY_ID_21 = 21L;
    public static final Long ACTIVITY_ID_181 = 181L;

    public static final List<Declaration> ACTIVITY_DECLARATIONS_WITHOUT_ID = asList(DECLARATION_1_WITHOUT_ID, DECLARATION_2_WITHOUT_ID);

    public static final BigDecimal CURRENT_BALANCE_PLN = new BigDecimal("234642.67");
    public static final BigDecimal CURRENT_BALANCE_EUR = new BigDecimal("0.00");
    private static final Issue ACTIVITY_ISSUE_1 = issue1();
    private static final Operator ACTIVITY_OPERATOR_1 = testOperator1();
    private static final ActivityType ACTIVITY_TYPE_1 = TEST_ACTIVITY_TYPE_1;
    private static final OperatorDto ACTIVITY_OPERATOR_DTO_1 = testOperatorDto1();
    private static final Date ACTIVITY_DATE_1 = parse("2017-08-12 00:00:00.000000");
    private static final Date ACTIVITY_PLANNED_DATE_1 = parse("2017-08-11 00:00:00.000000");
    private static final Date ACTIVITY_CREATING_DATE_1 = parse("2017-07-11 00:00:00.000000");
    private static final ActivityState ACTIVITY_STATE_1 = ActivityState.PLANNED;
    public static final ActivityTypeDto ACTIVITY_TYPE_DTO_1 = TEST_ACTIVITY_TYPE_DTO_1;
    private static final BigDecimal ACTIVITY_CHARGE_1 = new BigDecimal("100.23");
    private static final String ACTIVITY_INVOICE_ID_1 = "7777";
    private static final ActivityStateDto ACTIVITY_STATE_DTO_1 = ActivityStateDto.PLANNED;
    private static final List<Declaration> ACTIVITY_DECLARATIONS_1 = emptyList();
    public static final Activity TEST_ACTIVITY_1 = activity1();
    public static final OperatorsQueue TEST_OPERATORS_QUEUE_1 = operatorsQueue1();
    public static final OperatorsQueue TEST_OPERATORS_QUEUE_11 = operatorsQueue11();
    public static final OperatorsQueue TEST_OPERATORS_QUEUE_2 = operatorsQueue2();
    private static final Issue ACTIVITY_ISSUE_2 = issue1();
    private static final Operator ACTIVITY_OPERATOR_2 = testOperator2();
    private static final ActivityType ACTIVITY_TYPE_2 = TEST_ACTIVITY_TYPE_1;
    private static final Date ACTIVITY_DATE_2 = parse("2017-08-12 00:00:00.000000");
    private static final Date ACTIVITY_PLANNED_DATE_2 = parse("2017-08-11 00:00:00.000000");
    private static final Date ACTIVITY_CREATING_DATE_2 = parse("2017-07-11 00:00:00.000000");
    private static final ActivityState ACTIVITY_STATE_2 = ActivityState.PLANNED;
    private static final BigDecimal ACTIVITY_CHARGE_2 = new BigDecimal("100.23");
    private static final String ACTIVITY_INVOICE_ID_2 = "7777";
    private static final List<ActivityDetail> ACTIVITY_ACTIVITY_DETAILS_2 = asList(TEST_ACTIVITY_DETAIL_1, TEST_ACTIVITY_DETAIL_2);
    private static final List<Declaration> ACTIVITY_DECLARATIONS_2 = emptyList();
    public static final Activity TEST_ACTIVITY_2 = activity(ACTIVITY_ID_2, ACTIVITY_ISSUE_2, ACTIVITY_OPERATOR_2, ACTIVITY_TYPE_2,
            ACTIVITY_DATE_2, ACTIVITY_PLANNED_DATE_2, ACTIVITY_CREATING_DATE_2, ACTIVITY_STATE_2, ACTIVITY_CHARGE_2,
            ACTIVITY_INVOICE_ID_2, ACTIVITY_ACTIVITY_DETAILS_2, ACTIVITY_DECLARATIONS_2);
    private static final Issue ACTIVITY_ISSUE_3 = issue21();
    private static final Operator ACTIVITY_OPERATOR_3 = testOperator1();
    private static final ActivityType ACTIVITY_TYPE_3 = TEST_ACTIVITY_TYPE_1;
    private static final Date ACTIVITY_DATE_3 = parse("2017-08-12 00:00:00.000000");
    private static final Date ACTIVITY_PLANNED_DATE_3 = parse("2017-08-11 00:00:00.000000");
    private static final Date ACTIVITY_CREATING_DATE_3 = parse("2017-07-11 00:00:00.000000");
    private static final ActivityState ACTIVITY_STATE_3 = ActivityState.PLANNED;
    private static final BigDecimal ACTIVITY_CHARGE_3 = new BigDecimal("100.23");
    private static final String ACTIVITY_INVOICE_ID_3 = "7777";
    public static final Activity TEST_ACTIVITY_3 = activity3();
    private static final Issue ACTIVITY_ISSUE_11 = issue11();
    private static final Operator ACTIVITY_OPERATOR_11 = null;
    private static final ActivityType ACTIVITY_TYPE_11 = TEST_ACTIVITY_TYPE_1;
    private static final Date ACTIVITY_DATE_11 = parse("2017-08-12 00:00:00.000000");
    private static final Date ACTIVITY_PLANNED_DATE_11 = parse("2017-08-11 00:00:00.000000");
    private static final Date ACTIVITY_CREATING_DATE_11 = parse("2017-07-11 00:00:00.000000");
    private static final ActivityState ACTIVITY_STATE_11 = ActivityState.PLANNED;
    private static final BigDecimal ACTIVITY_CHARGE_11 = new BigDecimal("100.23");
    private static final String ACTIVITY_INVOICE_ID_11 = "7777";
    private static final List<ActivityDetail> ACTIVITY_ACTIVITY_DETAILS_11 = asList(TEST_ACTIVITY_DETAIL_1, TEST_ACTIVITY_DETAIL_2);
    private static final List<Declaration> ACTIVITY_DECLARATIONS_11 = emptyList();
    public static final Activity TEST_ACTIVITY_11 = activity(ACTIVITY_ID_11, ACTIVITY_ISSUE_11, ACTIVITY_OPERATOR_11, ACTIVITY_TYPE_11,
            ACTIVITY_DATE_11, ACTIVITY_PLANNED_DATE_11, ACTIVITY_CREATING_DATE_11, ACTIVITY_STATE_11, ACTIVITY_CHARGE_11,
            ACTIVITY_INVOICE_ID_11, ACTIVITY_ACTIVITY_DETAILS_11, ACTIVITY_DECLARATIONS_11);
    private static final List<ActivityDetail> ACTIVITY_ACTIVITY_DETAILS_WITHOUT_ID = asList(TEST_ACTIVITY_DETAIL_WITHOUT_ID_1,
            TEST_ACTIVITY_DETAIL_WITHOUT_ID_2);
    public static final Activity TEST_ACTIVITY_WITHOUT_ID = activity(null, ACTIVITY_ISSUE_3, testOperatorWithIdOnly(), ACTIVITY_TYPE_1,
            ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_1, ACTIVITY_CHARGE_1,
            ACTIVITY_INVOICE_ID_1, ACTIVITY_ACTIVITY_DETAILS_WITHOUT_ID, ACTIVITY_DECLARATIONS_WITHOUT_ID);
    private static final List<ActivityDetailDto> ACTIVITY_ACTIVITY_DETAIL_DTOS_1 = asList(TEST_ACTIVITY_DETAIL_DTO_1,
            TEST_ACTIVITY_DETAIL_DTO_2);
    private static final List<ActivityDetailDto> ACTIVITY_ACTIVITY_DETAIL_DTOS_REQUIRED = asList(TEST_ACTIVITY_DETAIL_DTO_REQUIRED,
            TEST_ACTIVITY_DETAIL_DTO_2);
    private static final List<DeclarationDto> ACTIVITY_DECLARATIONS_DTOS_1 = emptyList();
    public static final ActivityDto TEST_ACTIVITY_DTO_1 = activityDto(ACTIVITY_ID_1, ISSUE_ID_1, ACTIVITY_OPERATOR_DTO_1, ACTIVITY_TYPE_DTO_1,
            ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_DTO_1, ACTIVITY_CHARGE_1,
            ACTIVITY_INVOICE_ID_1, ACTIVITY_ACTIVITY_DETAIL_DTOS_1, ACTIVITY_DECLARATIONS_DTOS_1);
    public static final ActivityDto TEST_ACTIVITY_DTO_REQUIRED = activityDto(ACTIVITY_ID_1, ISSUE_ID_1, ACTIVITY_OPERATOR_DTO_1, ACTIVITY_TYPE_DTO_1,
            ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_DTO_1, ACTIVITY_CHARGE_1,
            ACTIVITY_INVOICE_ID_1, ACTIVITY_ACTIVITY_DETAIL_DTOS_REQUIRED, ACTIVITY_DECLARATIONS_DTOS_1);
    private static final List<DeclarationDto> ACTIVITY_DECLARATIONS_DTOS_2 = singletonList(DECLARATION_1_DTO);

    public ActivityTestData() {
    }

    public static ActivityDto activityDto(final Long activityId, final ActivityStateDto state, final ActivityTypeDto type) {
        final ActivityDto activityDto = new ActivityDto();
        activityDto.setId(activityId);
        activityDto.setState(state);
        activityDto.setActivityType(type);
        return activityDto;
    }

    public static Activity activity1() {
        return activity(ACTIVITY_ID_1, ACTIVITY_ISSUE_1, ACTIVITY_OPERATOR_1, ACTIVITY_TYPE_1,
                ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_1, ACTIVITY_CHARGE_1,
                ACTIVITY_INVOICE_ID_1, activityDetails(), ACTIVITY_DECLARATIONS_1);
    }

    public static OperatorsQueue operatorsQueue1(){
        return operatorsQueue(ACTIVITY_ID_1, ACTIVITY_ISSUE_1, ACTIVITY_OPERATOR_1, ACTIVITY_TYPE_1,
                ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_1, ACTIVITY_CHARGE_1,
                ACTIVITY_INVOICE_ID_1, activityDetails(), ACTIVITY_DECLARATIONS_1);
    }

    public static OperatorsQueue operatorsQueue11(){
        return operatorsQueue(ACTIVITY_ID_11, ACTIVITY_ISSUE_11, ACTIVITY_OPERATOR_11, ACTIVITY_TYPE_11,
                ACTIVITY_DATE_11, ACTIVITY_PLANNED_DATE_11, ACTIVITY_CREATING_DATE_11, ACTIVITY_STATE_11, ACTIVITY_CHARGE_11,
                ACTIVITY_INVOICE_ID_11, ACTIVITY_ACTIVITY_DETAILS_11, ACTIVITY_DECLARATIONS_11);
    }

    public static OperatorsQueue operatorsQueue2(){
        return operatorsQueue(ACTIVITY_ID_2, ACTIVITY_ISSUE_2, ACTIVITY_OPERATOR_2, ACTIVITY_TYPE_2,
                ACTIVITY_DATE_2, ACTIVITY_PLANNED_DATE_2, ACTIVITY_CREATING_DATE_2, ACTIVITY_STATE_2, ACTIVITY_CHARGE_2,
                ACTIVITY_INVOICE_ID_2, ACTIVITY_ACTIVITY_DETAILS_2, ACTIVITY_DECLARATIONS_2);
    }

    public static OperatorsQueue operatorsQueue(final Long activityId, final Issue issue, final Operator operator, final ActivityType activityType, final Date activityDate,
                                    final Date plannedDate, final Date creatingDate, final ActivityState state, final BigDecimal charge,
                                    final String invoiceId, final List<ActivityDetail> activityDetails, final List<Declaration> declarations) {
        final OperatorsQueue operatorsQueue = new OperatorsQueue();
        operatorsQueue.setActivityId(activityId);
        operatorsQueue.setActivityDate(activityDate);
        if(activityType != null) {
            operatorsQueue.setActivityTypeId(activityType.getId());
        }
        operatorsQueue.setCharge(charge);
        operatorsQueue.setCreationDate(creatingDate);
        operatorsQueue.setInvoiceId(invoiceId != null? Long.valueOf(invoiceId) : null);
        if(issue!=null) {
            operatorsQueue.setIssueId(issue.getId());
        }
        if(operator!=null) {
            operatorsQueue.setOperatorId(operator.getId());
        }
        operatorsQueue.setPlannedDate(plannedDate);
        operatorsQueue.setActivityState(state);
        if(issue != null && issue.getIssueType()!=null && issue.getIssueType().getName()!=null) {
            operatorsQueue.setIssueType(issue.getIssueType().getName());
        }
        return operatorsQueue;
    }

    public static Activity activity21() {
        return activity(ACTIVITY_ID_21, issue2(), ACTIVITY_OPERATOR_1, TEST_ACTIVITY_TYPE_1, TestDateUtils.parse("2017-07-10"), TestDateUtils.parse("2017-07-14"),
                TestDateUtils.parse("2017-07-10"), ACTIVITY_STATE_1, new BigDecimal("56432.23"), "fv1a/2017", emptyList(), emptyList());
    }

    public static Activity activityWithOverdueDeclaration() {
        return activity(ACTIVITY_ID_1, ACTIVITY_ISSUE_1, ACTIVITY_OPERATOR_1, TEST_ACTIVITY_TYPE_5,
                ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_1, ACTIVITY_CHARGE_1,
                ACTIVITY_INVOICE_ID_1, activityDetails(), activityWithUnpaidDeclaration());
    }

    public static Activity activityWithPaidDeclaration() {
        return activity(ACTIVITY_ID_1, ACTIVITY_ISSUE_1, ACTIVITY_OPERATOR_1, TEST_ACTIVITY_TYPE_5,
                ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_1, ACTIVITY_CHARGE_1,
                ACTIVITY_INVOICE_ID_1, activityDetails(), paidDeclaration());
    }

    public static List<Activity> activitiesWithoutOpenOutgoingCall() {
        return asList(activity(activityType1(), EXECUTED), activity(activityType1(), CANCELED), activity(activityType4(), PLANNED),
                activity(activityType4(), POSTPONED));
    }

    public static List<Activity> activitiesWithPlannedOutgionCall() {
        return asList(activity(activityType1(), EXECUTED), activity(activityType1(), CANCELED), activity(activityType4(), PLANNED),
                activity(activityType4(), POSTPONED), activity(activityType1(), PLANNED));
    }

    public static List<Activity> activitiesWithPostponedOutgionCall() {
        return asList(activity(activityType1(), EXECUTED), activity(activityType1(), CANCELED), activity(activityType4(), PLANNED),
                activity(activityType4(), POSTPONED), activity(activityType1(), POSTPONED));
    }

    public static Activity activityToUpdate() {
        return activity1();
    }

    public static Activity managedActivityToUpdate() {
        final Activity activity = activity1();
        activity.setOperator(testOperator5());
        return activity;
    }

    public static Activity managedActivityToUpdateWithDeclarationAndBalance() {
        final Activity activity = activity1();
        activity.setOperator(testOperator5());
        final Declaration declaration1 = DECLARATION_1;
        declaration1.setCurrentBalancePLN(CURRENT_BALANCE_PLN);
        declaration1.setCurrentBalanceEUR(CURRENT_BALANCE_EUR);
        activity.setDeclarations(asList(declaration1));
        return activity;
    }

    public static Activity outstandingSmsActivity() {
        return activity(null, issue1(), testOperator2(), activityType4(), CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EXECUTED, BigDecimal.ZERO, null,
                outstandingSmsActivityDetails(), null);
    }

    public static Activity commentActivity() {
        return activity(null, issue1(), testOperator2(), activityType6(), CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EXECUTED, BigDecimal.ZERO, null,
                commentActivityDetails(), null);
    }

    public static Activity incomingCallActivity() {
        return activity(null, issue1(), testOperator2(), TEST_ACTIVITY_TYPE_5, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EXECUTED, BigDecimal.ZERO,
                null,
                null, null);
    }

    public static Activity incomingCallActivityWithDeclaration() {
        return activity(null, issue1(), testOperator2(), TEST_ACTIVITY_TYPE_5, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EXECUTED, BigDecimal.ZERO,
                null,
                null, Arrays.asList(DECLARATION_1));
    }

    public static Activity callForPaymentActivity() {
        return activity(null, issue1(), testOperator2(), TEST_ACTIVITY_TYPE_2, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EXECUTED, BigDecimal.ZERO,
                null,
                null, Arrays.asList(PAID_DECLARATION_1));
    }

    public static Activity callForPaymentActivityWithoutDeclarations() {
        return activity(null, issue1(), testOperator2(), TEST_ACTIVITY_TYPE_2, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EXECUTED, BigDecimal.ZERO,
                null,
                null, null);
    }

    public static Activity activityWithDetails() {
        return activity1();
    }

    public static Activity activityWithDeclarations() {
        return activity(ACTIVITY_ID_1, ACTIVITY_ISSUE_1, ACTIVITY_OPERATOR_1, ACTIVITY_TYPE_1,
                ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_1, ACTIVITY_CHARGE_1,
                ACTIVITY_INVOICE_ID_1, new ArrayList<>(0), activityDeclarations());
    }

    public static Activity activity3() {
        return activity(ACTIVITY_ID_3, ACTIVITY_ISSUE_3, ACTIVITY_OPERATOR_3, ACTIVITY_TYPE_3,
                ACTIVITY_DATE_3, ACTIVITY_PLANNED_DATE_3, ACTIVITY_CREATING_DATE_3, ACTIVITY_STATE_3, ACTIVITY_CHARGE_3,
                ACTIVITY_INVOICE_ID_3, activityDetails(), activityDeclarations());
    }

    public static List<ActivityDetail> activityDetails() {
        return new ArrayList<>(asList(TEST_ACTIVITY_DETAIL_1, TEST_ACTIVITY_DETAIL_2));
    }

    public static List<Declaration> activityDeclarations() {
        return new ArrayList<>(asList(DECLARATION_1, DECLARATION_2));
    }

    public static List<Declaration> activityWithUnpaidDeclaration() {
        return new ArrayList<>(asList(UNPAID_DECLARATION_1, DECLARATION_2));
    }

    public static List<Declaration> paidDeclaration() {
        return new ArrayList<>(asList(PAID_DECLARATION_1, DECLARATION_2));
    }

    public static ActivityDto activityDto1() {
        return activityDto(ACTIVITY_ID_1, ACTIVITY_ISSUE_1.getId(), ACTIVITY_OPERATOR_DTO_1, ACTIVITY_TYPE_DTO_1,
                ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_DTO_1, ACTIVITY_CHARGE_1,
                ACTIVITY_INVOICE_ID_1, ACTIVITY_ACTIVITY_DETAIL_DTOS_1, ACTIVITY_DECLARATIONS_DTOS_1);
    }

    public static ActivityDto activityDto2() {
        return activityDto(ACTIVITY_ID_1, ACTIVITY_ISSUE_1.getId(), ACTIVITY_OPERATOR_DTO_1, ACTIVITY_TYPE_DTO_1,
                ACTIVITY_DATE_1, ACTIVITY_PLANNED_DATE_1, ACTIVITY_CREATING_DATE_1, ACTIVITY_STATE_DTO_1, ACTIVITY_CHARGE_1,
                ACTIVITY_INVOICE_ID_1, ACTIVITY_ACTIVITY_DETAIL_DTOS_1, ACTIVITY_DECLARATIONS_DTOS_2);
    }

    public static ActivityDto activityDto(final Long activityId, final String plannedDate) {
        final ActivityDto activityDto = activityDto1();
        activityDto.setId(activityId);
        activityDto.setPlannedDate(TestDateUtils.parse(plannedDate));
        return activityDto;
    }

    public static Activity activity(final ActivityType activityType, final ActivityState state) {
        final Activity activity = new Activity();
        activity.setActivityType(activityType);
        activity.setState(state);
        return activity;
    }

    public static Activity activity(final Long id, final Issue issue, final Operator operator, final ActivityType activityType, final Date activityDate,
                                    final Date plannedDate, final Date creatingDate, final ActivityState state, final BigDecimal charge,
                                    final String invoiceId, final List<ActivityDetail> activityDetails, final List<Declaration> declarations) {
        final Activity activity = new Activity();
        activity.setId(id);
        activity.setIssue(issue);
        activity.setOperator(operator);
        activity.setActivityType(activityType);
        activity.setActivityDate(activityDate);
        activity.setPlannedDate(plannedDate);
        activity.setCreationDate(creatingDate);
        activity.setState(state);
        activity.setCharge(charge);
        activity.setInvoiceId(invoiceId);
        activity.setActivityDetails(activityDetails);
        activity.setDeclarations(declarations);

        return activity;
    }

    public static ActivityDto activityDto(final Long id, final Long issueId, final OperatorDto operator, final ActivityTypeDto activityType,
                                          final Date activityDate, final Date plannedDate, final Date creatingDate, final ActivityStateDto state,
                                          final BigDecimal charge, final String invoiceId, final List<ActivityDetailDto> activityDetails,
                                          final List<DeclarationDto> declarations) {
        final ActivityDto activity = new ActivityDto();
        activity.setId(id);
        activity.setIssueId(issueId);
        activity.setOperator(operator);
        activity.setActivityType(activityType);
        activity.setActivityDate(activityDate);
        activity.setPlannedDate(plannedDate);
        activity.setCreationDate(creatingDate);
        activity.setState(state);
        activity.setCharge(charge);
        activity.setInvoiceId(invoiceId);
        activity.setActivityDetails(activityDetails);
        activity.setDeclarations(declarations);

        return activity;
    }
}
