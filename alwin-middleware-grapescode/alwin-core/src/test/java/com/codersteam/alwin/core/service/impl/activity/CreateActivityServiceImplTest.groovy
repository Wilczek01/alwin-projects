package com.codersteam.alwin.core.service.impl.activity

import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService
import com.codersteam.alwin.core.service.impl.DateProviderImpl
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.*
import com.codersteam.alwin.jpa.activity.Declaration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.testdata.ActivityTestData.*
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static org.junit.Assert.assertEquals

class CreateActivityServiceImplTest extends Specification {

    @Subject
    private CreateActivityServiceImpl service

    private IssueDao issueDao = Mock(IssueDao)
    private TagDao tagDao = Mock(TagDao)
    private ActivityDao activityDao = Mock(ActivityDao)
    private ActivityTypeService activityTypeService = Mock(ActivityTypeService)
    private AlwinMapper alwinMapper = new AlwinMapper()
    private DateProvider dateProvider
    private ActivityService activityService

    private ActivityTypeDao activityTypeDao = Mock(ActivityTypeDao)
    private OperatorDao operatorDao = Mock(OperatorDao)
    private DefaultIssueActivityDao defaultIssueActivityDao = Mock(DefaultIssueActivityDao)
    private ActivityDetailPropertyDao activityDetailPropertyDao = Mock(ActivityDetailPropertyDao)

    def "setup"() {
        dateProvider = new DateProviderImpl()
        activityService = new ActivityServiceImpl(issueDao, activityDao, activityTypeService, activityTypeDao, operatorDao, alwinMapper, dateProvider, defaultIssueActivityDao
                , tagDao, activityDetailPropertyDao)
        service = new CreateActivityServiceImpl(issueDao, alwinMapper, dateProvider, activityService)
    }

    def "should not create outgoing call activity when issue has not activites"() {
        when:
            def validForCreation = service.isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issue2())
        then:
            validForCreation == false
    }

    def "should not create outgoing call activity when issue has planned outgoing call"() {
        when:
            def validForCreation = service.isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issueWithActivitiesAndOutstandingSmsActivity())
        then:
            validForCreation == false
    }

    def "should create outgoing call activity when issue has unpaid declaration from yestarday"() {
        when:
            def validForCreation = service.isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issueWithIncomingCallActivityAndOutstandingSmsActivity())
        then:
            validForCreation == true
    }

    def "should create outgoing call activity when issue has not any declaration, call for payment and sms activity "() {
        when:
            def validForCreation = service.isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issueWithIncomingCallActivity())
        then:
            validForCreation == true
    }

    def "should create outgoing call activity when issue has not future declaration, call for payment and sms activity "() {
        when:
            def validForCreation = service.isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issueWithIncomingCallActivityWithDeclaration())
        then:
            validForCreation == true
    }

    def "should not create outgoing call activity when issue has not future declaration, sms activity but has call for payment "() {
        when:
            def validForCreation = service.isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issueWithCallForPaymentActivity())
        then:
            validForCreation == false
    }

    def "should not create outgoing call activity when issue has not any declaration, sms activity but has call for payment "() {
        when:
            def validForCreation = service.isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issueWithCallForPaymentActivityWithoutDeclarations())
        then:
            validForCreation == false
    }

    def "should not create outgoing call activity when issue has positive balance"() {
        when:
            def validForCreation = service.hasIssueOverallOverdueAndPaidDeclaration([], issue4())
        then:
            !validForCreation
    }

    def "should not create outgoing call activity when issue has no activities"() {
        when:
            def validForCreation = service.hasIssueOverallOverdueAndPaidDeclaration([], issue3())
        then:
            !validForCreation
    }

    def "should not create outgoing call activity when issue has no declarations"() {
        when:
            def validForCreation = service.hasIssueOverallOverdueAndPaidDeclaration([activity1()], issue3())
        then:
            !validForCreation
    }

    def "should create outgoing call activity when issue has paid declarations"() {
        when:
            def validForCreation = service.hasIssueOverallOverdueAndPaidDeclaration([activityWithPaidDeclaration()], issue3())
        then:
            validForCreation
    }

    def "should create outgoing call activity when issue don't have open outgoing call activity"() {
        when:
            def hasIssueOutgoingCallActivity = service.hasIssueOutgoingCallActivityAlreadyDefined(activitiesWithoutOpenOutgoingCall())
        then:
            !hasIssueOutgoingCallActivity
    }

    def "should create outgoing call activity when issue have planned outgoing call activity"() {
        when:
            def hasIssueOutgoingCallActivity = service.hasIssueOutgoingCallActivityAlreadyDefined(activitiesWithPlannedOutgionCall())
        then:
            hasIssueOutgoingCallActivity
    }

    def "should create outgoing call activity when issue have postponed outgoing call activity"() {
        when:
            def hasIssueOutgoingCallActivity = service.hasIssueOutgoingCallActivityAlreadyDefined(activitiesWithPostponedOutgionCall())
        then:
            hasIssueOutgoingCallActivity
    }

    def "should create new outgoing call activity"() {
        when:
            def activity = service.createNewOutgoingCallActivity(issueWithIncomingCallActivityWithDeclaration())
        then:
            assertEquals(activity, activity)
    }

    @Unroll
    def "should check if declaration was paid for id #id"() {
        given:
            def declaration = new Declaration()
            declaration.id = id
            declaration.cashPaidPLN = cashPaidPLN
            declaration.cashPaidEUR = cashPaidEUR
            declaration.declaredPaymentAmountPLN = declaredPaymentAmountPLN
            declaration.declaredPaymentAmountEUR = declaredPaymentAmountEUR
        when:
            def declarationPaid = service.hasPaidDeclaration(declaration)
        then:
            declarationPaid == expectedDeclarationPaid
        where:
            id | cashPaidPLN | declaredPaymentAmountPLN | cashPaidEUR | declaredPaymentAmountEUR | expectedDeclarationPaid
            1  | 0.00        | 100.00                   | 0.00        | 0.00                     | false
            2  | 0.00        | 0.00                     | 0.00        | 100.00                   | false
            3  | 0.00        | 100.00                   | 0.00        | 100.00                   | false
            4  | 99.00       | 100.00                   | 99.00       | 100.00                   | false
            5  | 101.00      | 100.00                   | 99.00       | 100.00                   | false
            6  | 99.00       | 100.00                   | 101.00      | 100.00                   | false
            7  | 100.00      | 100.00                   | 100.00      | 100.00                   | true
            8  | 101.00      | 100.00                   | 101.00      | 100.00                   | true
            9  | 100.00      | 100.00                   | 0.00        | 0.00                     | true
            10 | 0.00        | 0.00                     | 100.00      | 100.00                   | true
            11 | null        | 100.00                   | null        | 100.00                   | false
    }

    @Unroll
    def "should check if planned date [#plannedDate] is before given date"() {
        given:
            def date = parse('2017-08-20')
        expect:
            service.isPlanedBefore(parse(plannedDate), date) == expected
        where:
            plannedDate  | expected
            '2017-08-21' | false
            '2017-08-20' | false
            '2017-08-19' | true
    }
}
