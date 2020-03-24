package com.codersteam.alwin.core.service.impl.activity

import com.codersteam.alwin.common.issue.IssueTypeName
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.model.activity.ActivityStateDto
import com.codersteam.alwin.core.api.model.activity.ActivityTypeByStateDto
import com.codersteam.alwin.core.api.model.balance.BalanceDto
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.*
import com.codersteam.alwin.jpa.activity.Activity
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty
import com.codersteam.alwin.jpa.activity.ActivityType
import com.codersteam.alwin.jpa.activity.DefaultIssueActivity
import com.codersteam.alwin.jpa.issue.Issue
import com.codersteam.alwin.jpa.type.ActivityState
import com.codersteam.alwin.testdata.ActivityDetailPropertyTestData
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.REMARK
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.COMMENT
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.OUTGOING_SMS
import static com.codersteam.alwin.jpa.type.IssueState.*
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty10
import static com.codersteam.alwin.testdata.ActivityDetailTestData.ACTIVITY_COMMENT
import static com.codersteam.alwin.testdata.ActivityTestData.*
import static com.codersteam.alwin.testdata.ActivityTypeTestData.*
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_5
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.DeclarationTestData.declarationDto
import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.*
import static com.codersteam.alwin.testdata.IssueTestData.issueWithState
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_MESSAGE
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_PHONE_NUMBER
import static com.codersteam.alwin.testdata.OperatorTestData.*
import static com.codersteam.alwin.testdata.TagTestData.testTag3
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_BALANCE_1
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_BALANCE_EUR_1
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR
import static java.util.Collections.emptyList
import static java.util.Collections.singletonList
import static org.apache.commons.collections.CollectionUtils.isEmpty
import static org.assertj.core.api.AssertionsForClassTypes.assertThat
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse

/**
 * @author Tomasz Sliwinski
 */
class ActivityServiceImplTest extends Specification {

    @Subject
    private ActivityServiceImpl service

    private IssueDao issueDao = Mock(IssueDao)
    private ActivityDao activityDao = Mock(ActivityDao)
    private ActivityTypeDao activityTypeDao = Mock(ActivityTypeDao)
    private ActivityTypeService activityTypeService = Mock(ActivityTypeService)
    private DateProvider dateProvider = Mock(DateProvider)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper alwinMapper = new AlwinMapper(dateProvider, alwinProperties)
    private OperatorDao operatorDao = Mock(OperatorDao)
    private DefaultIssueActivityDao defaultIssueActivityDao = Mock(DefaultIssueActivityDao)
    private BalanceCalculatorService balanceCalculatorService = Mock(BalanceCalculatorService)
    private TagDao tagDao = Mock(TagDao)
    private ActivityDetailPropertyDao activityDetailPropertyDao = Mock(ActivityDetailPropertyDao)

    def "setup"() {
        service = new ActivityServiceImpl(issueDao, activityDao, activityTypeService, activityTypeDao, operatorDao, alwinMapper, dateProvider, defaultIssueActivityDao
                , tagDao, activityDetailPropertyDao)
        tagDao.findOverdueTag() >> testTag3()
    }

    def "should create new activity for issue"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        when:
            service.createNewActivityForIssue(activityDto1())
        then:
            1 * activityDao.createNewActivityForIssue(_ as Activity) >> { List arguments ->
                Activity activity = (Activity) arguments[0]
                assert activity.operator.id == OPERATOR_ID_1
                assert activity.id == TEST_ACTIVITY_1.id
            }
            1 * issueDao.update(_ as Issue)
    }

    @Unroll
    def "should not update issue state if issue state is #state when new activity was created"() {
        given:
            def issue = Optional.of(issueWithState(state))
        and:
            issueDao.get(ISSUE_ID_1) >> issue
        and:
            assert issue.get().tags.contains(testTag3())
        when:
            service.createNewActivityForIssue(activityDto1())
        then:
            1 * activityDao.createNewActivityForIssue(_ as Activity) >> { List arguments ->
                Activity activity = (Activity) arguments[0]
                assert activity.operator.id == OPERATOR_ID_1
                assert activity.id == TEST_ACTIVITY_1.id
            }
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issueToUpdate = (Issue) arguments[0]
                assert !issueToUpdate.tags.contains(testTag3())

            }
        where:
            state << [IN_PROGRESS, CANCELED, DONE]
    }

    def "should find all issue activities"() {
        given:
            activityDao.findAllIssueActivities(ISSUE_ID_1) >> [TEST_ACTIVITY_1]
        when:
            def activities = service.findAllIssueActivities(ISSUE_ID_1)
        then:
            assertThat(activities).isEqualToComparingFieldByFieldRecursively([TEST_ACTIVITY_DTO_1])
    }

    def "should find all active issue activities"() {
        given:
            def maxPlannedDate = parse("2017-10-10")
            activityDao.findActiveIssueActivities(ISSUE_ID_1, maxPlannedDate) >> [TEST_ACTIVITY_1]
        when:
            def activities = service.findActiveIssueActivities(ISSUE_ID_1, maxPlannedDate)
        then:
            assertThat(activities).isEqualToComparingFieldByFieldRecursively([TEST_ACTIVITY_DTO_1])
    }

    def "should find all paginated issue activities"() {
        given:
            def searchCriteria = new ActivitiesSearchCriteria()

        and:
            def sortCriteria = new LinkedHashMap()

        and:
            activityDao.findAllIssueActivities(ISSUE_ID_1, searchCriteria, sortCriteria) >> singletonList(TEST_ACTIVITY_1)
            activityDao.findAllIssueActivitiesCount(ISSUE_ID_1, searchCriteria) >> 1
            issueDao.get(_ as Long) >> Optional.of(issue1())
            activityTypeService.findActivityTypeByIssueTypeGroupedByState(_ as IssueTypeName, null) >> new ActivityTypeByStateDto(
                    emptyList(), singletonList(ACTIVITY_TYPE_DTO_1)
            )

        when:
            def activities = service.findAllIssueActivities(ISSUE_ID_1, searchCriteria, sortCriteria)

        then:
            assertThat(activities.values).isEqualToComparingFieldByFieldRecursively([TEST_ACTIVITY_DTO_REQUIRED])
            assertEquals(1, activities.totalValues)
    }

    def "should not find issue activities for not existing issue"() {
        given:
            def notExistingIssue = 44L
        and:
            activityDao.findAllIssueActivities(notExistingIssue) >> []
        when:
            def activities = service.findAllIssueActivities(notExistingIssue)
        then:
            isEmpty(activities)
    }

    def "should create and store default activities for issue"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            defaultIssueActivityDao.findDefaultActivities(ISSUE_TYPE_ID_1) >> [DEFAULT_ISSUE_ACTIVITY_1, DEFAULT_ISSUE_ACTIVITY_2, DEFAULT_ISSUE_ACTIVITY_4]
        when:
            service.createDefaultActivitiesForIssue(ISSUE_ID_1, ISSUE_TYPE_ID_1)
        then:
            3 * activityDao.create(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity.issue).usingComparatorForFields(SKIP_COMPARATOR, "issueInvoices").isEqualToComparingFieldByFieldRecursively(issue1())
                assert activity.operator == null
            }
    }

    def "should not create default activities for not exiting issue"() {
        given:
            def notExistingIssue = 44L
        and: "issue is not present"
            issueDao.get(notExistingIssue) >> Optional.empty()
        when:
            service.createDefaultActivitiesForIssue(notExistingIssue, ISSUE_TYPE_ID_1)
        then:
            0 * activityDao.create(_ as Activity)
    }


    def "should close activities"() {
        given:
            activityDao.findActiveIssueActivities(ISSUE_ID_5) >> [activity1(), TEST_ACTIVITY_2]
        and:
            def updateDate = parse("2017-07-10 00:00:00.000000")
            dateProvider.getCurrentDate() >> updateDate
        when:
            service.closeIssueActivities(ISSUE_ID_5)
        then:
            2 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assert activity.state == ActivityState.CANCELED
                assert activity.activityDate == updateDate
            }
    }

    def "should find activity by id"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(TEST_ACTIVITY_1)
        when:
            def activity = service.findActivityById(ACTIVITY_ID_1).get()
        then:
            assertThat(activity).isEqualToComparingFieldByFieldRecursively(TEST_ACTIVITY_DTO_REQUIRED)
    }

    def "should not find activity by id"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.empty()
        when:
            def activity = service.findActivityById(ACTIVITY_ID_1)
        then:
            assertFalse(activity.isPresent())
    }

    def "should not update activity if activity with given id not exists"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.empty()
        when:
            service.updateActivity(activityDto1(), NOT_MANAGED)
        then:
            0 * activityDao.update(_ as Activity)
    }

    def "should not update activity if activity type with given type id not exists"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activity1())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.empty()
        when:
            service.updateActivity(activityDto1(), NOT_MANAGED)
        then:
            0 * activityDao.update(_ as Activity)
    }

    def "should update activity with details"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDetails())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        when:
            service.updateActivity(activityDto1(), NOT_MANAGED)
        then:
            1 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity).usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties")
                        .isEqualToComparingFieldByFieldRecursively(activityToUpdate())
                activity
            }
            1 * issueDao.update(_ as Issue)
    }

    @Unroll
    def "should not update issue state if issue state is #state when activity was updated"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDetails())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            def issue = Optional.of(issueWithState(state))
        and:
            issueDao.get(ISSUE_ID_1) >> issue
        and:
            assert issue.get().tags.contains(testTag3())
        when:
            service.updateActivity(activityDto1(), NOT_MANAGED)
        then:
            1 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity)
                        .usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties")
                        .isEqualToComparingFieldByFieldRecursively(activityToUpdate())
                activity
            }
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issueToUpdate = (Issue) arguments[0]
                assert !issueToUpdate.tags.contains(testTag3())

            }
        where:
            state << [IN_PROGRESS, CANCELED, DONE]
    }

    def "should update activity with declarations"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDeclarations())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        when:
            service.updateActivity(activityDto1(), NOT_MANAGED)
        then:
            1 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity).usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties")
                        .isEqualToComparingFieldByFieldRecursively(activityToUpdate())
                activity
            }
            1 * issueDao.update(_ as Issue)
    }

    def "should not update managed activity if activity with given id not exists"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.empty()
        when:
            service.updateActivity(activityDto1(), MANAGED)
        then:
            0 * activityDao.update(_ as Activity)
    }

    def "should not update managed activity if activity type with given type id not exists"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activity1())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.empty()
        when:
            service.updateActivity(activityDto1(), MANAGED)
        then:
            0 * activityDao.update(_ as Activity)
    }

    def "should update managed activity with details"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDetails())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        when:
            service.updateActivity(activityDto1(), MANAGED)
        then:
            1 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity).usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties", "operator.user")
                        .isEqualToComparingFieldByFieldRecursively(managedActivityToUpdate())
                activity
            }
            1 * issueDao.update(_ as Issue)
    }

    def "should update managed activity with declarations"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDeclarations())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        when:
            service.updateActivity(activityDto1(), MANAGED)
        then:
            1 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity).usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties", "operator.user").isEqualToComparingFieldByFieldRecursively(managedActivityToUpdate())
                activity
            }
            1 * issueDao.update(_ as Issue)
    }

    def "should create, store and assign default activities for issue"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            def assigneeId = OPERATOR_ID_2
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())
        and:
            defaultIssueActivityDao.findDefaultActivities(ISSUE_TYPE_ID_1) >> [DEFAULT_ISSUE_ACTIVITY_1, DEFAULT_ISSUE_ACTIVITY_2, DEFAULT_ISSUE_ACTIVITY_4]
        when:
            service.createAndAssignDefaultActivitiesForIssue(ISSUE_ID_1, ISSUE_TYPE_ID_1, assigneeId)
        then:
            3 * activityDao.create(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity.issue).usingComparatorForFields(SKIP_COMPARATOR, "issueInvoices").isEqualToComparingFieldByFieldRecursively(issue1())
                assertThat(activity.operator).isEqualToComparingFieldByFieldRecursively(testOperator2())
            }
    }

    def "should throw an exception if issue not exists"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.empty()
        when:
            service.createOutgoingSmsActivity(ISSUE_ID_1, OPERATOR_ID_1, SMS_PHONE_NUMBER, SMS_MESSAGE)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == ISSUE_ID_1
    }

    def "should throw an exception if operator not exists"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.empty()
        when:
            service.createOutgoingSmsActivity(ISSUE_ID_1, OPERATOR_ID_2, SMS_PHONE_NUMBER, SMS_MESSAGE)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == OPERATOR_ID_2
    }

    def "should throw an exception if activity type not exists"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())
        and:
            activityTypeDao.findByKey(OUTGOING_SMS) >> Optional.empty()
        when:
            service.createOutgoingSmsActivity(ISSUE_ID_1, OPERATOR_ID_2, SMS_PHONE_NUMBER, SMS_MESSAGE)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == OUTGOING_SMS
    }

    def "should create outgoing sms activity"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())
        and:
            activityTypeDao.findByKey(OUTGOING_SMS) >> Optional.of(activityType4())
        and:
            dateProvider.getCurrentDate() >> CURRENT_DATE
        when:
            service.createOutgoingSmsActivity(ISSUE_ID_1, OPERATOR_ID_2, SMS_PHONE_NUMBER, SMS_MESSAGE)
        then:
            1 * activityDao.create(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity)
                        .usingComparatorForFields(SKIP_COMPARATOR, "issue.issueInvoices", "activityType.activityTypeDetailProperties")
                        .isEqualToComparingFieldByFieldRecursively(outstandingSmsActivity())
            }
    }

    def "should set balance for declarations"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDeclarations())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            service.updateActivity(activityDto2(), MANAGED)
        then:
            1 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity).usingComparatorForFields(SKIP_COMPARATOR, "activityType.activityTypeDetailProperties", "operator.user")
                        .isEqualToComparingFieldByFieldRecursively(managedActivityToUpdateWithDeclarationAndBalance())
                activity
            }
            1 * issueDao.update(_ as Issue)
    }

    @Unroll
    def "should not update activity planned date when #message"() {
        given:
            def activity = activityDto1()
            activity.state = state
            activity.declarations = declarations
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            activityDao.findActiveIssueActivities(ISSUE_ID_1, _ as Date) >> []
        and:
            def balancePLN = new BalanceDto(INVOICE_BALANCE_1)
            def balanceEUR = new BalanceDto(INVOICE_BALANCE_EUR_1)
        and:
            def balancePerCurrency = [:]
            balancePerCurrency.put(PLN, balancePLN)
            balancePerCurrency.put(EUR, balanceEUR)
        and:
            balanceCalculatorService.currentBalance(EXT_COMPANY_ID_1) >> balancePerCurrency
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            service.createNewActivityForIssue(activity)
        then:
            1 * activityDao.createNewActivityForIssue(_ as Activity) >> { List arguments ->
                Activity createdActivity = (Activity) arguments[0]
                assert createdActivity.operator.id == OPERATOR_ID_1
                assert createdActivity.id == TEST_ACTIVITY_1.id
            }
            1 * issueDao.update(_ as Issue)
            0 * activityDao.update(_ as Activity)

        where:
            state                     | declarations                   | message
            ActivityStateDto.PLANNED  | [declarationDto('2017-10-10')] | 'activity is in open state'
            ActivityStateDto.EXECUTED | []                             | 'activity have not declarations'
            ActivityStateDto.EXECUTED | [declarationDto('')]           | 'activity have declarations with empty payment date'
            ActivityStateDto.EXECUTED | [declarationDto('2017-10-10')] | 'issue not have other open activities before payment date'
    }

    def "should create activity and update other open activities planned date"() {
        given:
            def activity = activityDto1()
            activity.state = ActivityStateDto.EXECUTED
            activity.declarations = [declarationDto('2017-10-10'), declarationDto('2017-10-08')]
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            activityDao.findActiveIssueActivities(ISSUE_ID_1, parse('2017-10-08')) >> [activity3()]
        and:
            def balancePLN = new BalanceDto(INVOICE_BALANCE_1)
            def balanceEUR = new BalanceDto(INVOICE_BALANCE_EUR_1)
        and:
            def balancePerCurrency = [:]
            balancePerCurrency.put(PLN, balancePLN)
            balancePerCurrency.put(EUR, balanceEUR)
        and:
            balanceCalculatorService.currentBalance(EXT_COMPANY_ID_1) >> balancePerCurrency
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            service.createNewActivityForIssue(activity)
        then:
            1 * activityDao.createNewActivityForIssue(_ as Activity) >> { List arguments ->
                Activity createdActivity = (Activity) arguments[0]
                assert createdActivity.operator.id == OPERATOR_ID_1
                assert createdActivity.id == TEST_ACTIVITY_1.id
                assert createdActivity.declarations[0].currentBalancePLN == CURRENT_BALANCE_PLN
                assert createdActivity.declarations[1].currentBalancePLN == CURRENT_BALANCE_PLN
                assert createdActivity.declarations[0].currentBalanceEUR == CURRENT_BALANCE_EUR
                assert createdActivity.declarations[1].currentBalanceEUR == CURRENT_BALANCE_EUR
            }
            1 * issueDao.update(_ as Issue)
            2 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity updatedActivity = (Activity) arguments[0]
                assert updatedActivity.id == ACTIVITY_ID_3
                assert updatedActivity.plannedDate == parse('2017-10-09')
            }
    }

    def "should update activity and other open activities planned date"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDeclarations())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            def activity = activityDto1()
            activity.state = ActivityStateDto.EXECUTED
            activity.declarations = [declarationDto('2017-10-10'), declarationDto('2017-10-08')]
        and:
            activityDao.findActiveIssueActivities(ISSUE_ID_1, parse('2017-10-08')) >> [activity3()]
        and:
            def balancePLN = new BalanceDto(INVOICE_BALANCE_1)
            def balanceEUR = new BalanceDto(INVOICE_BALANCE_EUR_1)
        and:
            def balancePerCurrency = [:]
            balancePerCurrency.put(PLN, balancePLN)
            balancePerCurrency.put(EUR, balanceEUR)
        and:
            balanceCalculatorService.currentBalance(EXT_COMPANY_ID_1) >> balancePerCurrency
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            service.updateActivity(activity, NOT_MANAGED)
        then:
            3 * activityDao.update(_ as Activity)
            1 * issueDao.update(_ as Issue)
    }

    def "should create activity and update other open activities with planned date in the past"() {
        given:
            def activity = activityDto1()
            activity.state = ActivityStateDto.PLANNED
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            def currentDate = parse('2017-10-08')
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            activityDao.findActiveIssueActivities(ISSUE_ID_1, currentDate) >> [activity3()]
        and:
            balanceCalculatorService.currentBalance(EXT_COMPANY_ID_1) >> new BalanceDto(INVOICE_BALANCE_1)
        when:
            service.createNewActivityForIssue(activity)
        then:
            1 * activityDao.createNewActivityForIssue(_ as Activity) >> { List arguments ->
                Activity createdActivity = (Activity) arguments[0]
                assert createdActivity.operator.id == OPERATOR_ID_1
                assert createdActivity.id == TEST_ACTIVITY_1.id
            }
            1 * issueDao.update(_ as Issue)
            2 * activityDao.update(_ as Activity) >> { List arguments ->
                Activity updatedActivity = (Activity) arguments[0]
                assert updatedActivity.id == ACTIVITY_ID_3
                assert updatedActivity.plannedDate == parse('2017-10-09')
            }
    }

    def "should update activity and other open activities with planned date in the past"() {
        given:
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(activityWithDeclarations())
        and:
            activityTypeDao.get(ACTIVITY_TYPE_ID_1) >> Optional.of(activityType1())
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            def activity = activityDto1()
            activity.state = ActivityStateDto.EXECUTED
        and:
            def currentDate = parse('2017-10-08')
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            activityDao.findActiveIssueActivities(ISSUE_ID_1, currentDate) >> [activity3()]
        and:
            balanceCalculatorService.currentBalance(EXT_COMPANY_ID_1) >> new BalanceDto(INVOICE_BALANCE_1)
        when:
            service.updateActivity(activity, NOT_MANAGED)
        then:
            3 * activityDao.update(_ as Activity)
            1 * issueDao.update(_ as Issue)
    }

    def "should update default issue activity"() {
        given:
            defaultIssueActivityDao.get(DEFAULT_ISSUE_ACTIVITY_ID_1) >> Optional.of(DEFAULT_ISSUE_ACTIVITY_1)
        and:
            defaultIssueActivityDao.findDefaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_1) >> DEFAULT_ISSUE_ACTIVITY_1
        when:
            service.updateDefaultIssueActivity(DEFAULT_ISSUE_ACTIVITY_ID_1, DEFAULT_ISSUE_ACTIVITY_DTO_1)
        then:
            1 * defaultIssueActivityDao.create(_ as DefaultIssueActivity) >> { List arguments ->
                DefaultIssueActivity defaultIssueActivity = arguments[0] as DefaultIssueActivity
                assertThat(defaultIssueActivity).usingComparatorForFields(SKIP_COMPARATOR, "activityType")
                        .isEqualToComparingFieldByFieldRecursively(DEFAULT_ISSUE_ACTIVITY_1_V2)
            }
    }

    def "should update activity type"() {
        when:
            service.updateActivityType(ACTIVITY_TYPE_ID_1, TEST_ACTIVITY_TYPE_WITH_DETAIL_PROPERTIES_DTO_1)
        then:
            1 * activityDetailPropertyDao.update(_ as ActivityDetailProperty) >> { args ->
                def detailProperty = (ActivityDetailProperty) args[0]
                assertThat(detailProperty)
                        .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                        .isEqualToComparingFieldByFieldRecursively(ActivityDetailPropertyTestData.UPDATED_ACTIVITY_DETAIL_PROPERTY_10)
                detailProperty
            }
        and:
            1 * activityTypeDao.update(_ as ActivityType) >> { args ->
                def activityType = (ActivityType) args[0]
                assertThat(activityType)
                        .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                        .isEqualToComparingFieldByFieldRecursively(TEST_ACTIVITY_TYPE_1)
                activityType
            }
    }

    def "should not update activity type"() {
        when:
            service.updateActivityType(ACTIVITY_TYPE_ID_1, TEST_ACTIVITY_TYPE_WITHOUT_DETAIL_PROPERTIES_DTO_1)
        then:
            0 * activityDetailPropertyDao.update(_ as ActivityDetailProperty)
        and:
            1 * activityTypeDao.update(_ as ActivityType) >> { args ->
                def activityType = (ActivityType) args[0]
                assertThat(activityType)
                        .usingComparatorForFields(SKIP_COMPARATOR, "activityTypeDetailProperties", "issueTypeActivityTypes")
                        .isEqualToComparingFieldByFieldRecursively(TEST_ACTIVITY_TYPE_1)
                activityType
            }
    }

    def "should throw an exception if issue not exists during comment activity creation"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.empty()
        when:
            service.createCommentActivity(ISSUE_ID_1, OPERATOR_ID_1, ACTIVITY_COMMENT)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == ISSUE_ID_1
    }

    def "should throw an exception if operator not exists c"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.empty()
        when:
            service.createCommentActivity(ISSUE_ID_1, OPERATOR_ID_2, ACTIVITY_COMMENT)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == OPERATOR_ID_2
    }

    def "should throw an exception if activity type not exists during comment activity creation"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())
        and:
            activityTypeDao.findByKey(COMMENT) >> Optional.empty()
        when:
            service.createCommentActivity(ISSUE_ID_1, OPERATOR_ID_2, ACTIVITY_COMMENT)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == COMMENT
    }

    def "should throw an exception if activity property not exists during comment activity creation"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())
        and:
            activityTypeDao.findByKey(COMMENT) >> Optional.of(activityType6())
        and:
            activityDetailPropertyDao.findActivityDetailProperty(REMARK) >> Optional.empty()
        when:
            service.createCommentActivity(ISSUE_ID_1, OPERATOR_ID_2, ACTIVITY_COMMENT)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == REMARK
    }

    def "should create comment activity"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())
        and:
            activityTypeDao.findByKey(COMMENT) >> Optional.of(activityType6())
        and:
            activityDetailPropertyDao.findActivityDetailProperty(REMARK) >> Optional.of(activityDetailProperty10())
        and:
            dateProvider.getCurrentDate() >> CURRENT_DATE
        when:
            service.createCommentActivity(ISSUE_ID_1, OPERATOR_ID_2, ACTIVITY_COMMENT)
        then:
            1 * activityDao.create(_ as Activity) >> { List arguments ->
                Activity activity = arguments[0] as Activity
                assertThat(activity)
                        .usingComparatorForFields(SKIP_COMPARATOR, "issue.issueInvoices", "activityType.activityTypeDetailProperties")
                        .isEqualToComparingFieldByFieldRecursively(commentActivity())
            }
    }
}
