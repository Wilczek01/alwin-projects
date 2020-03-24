package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.service.*
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria
import com.codersteam.alwin.common.sort.IssueSortField
import com.codersteam.alwin.common.sort.SortOrder
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.model.issue.IssueStateDto
import com.codersteam.alwin.core.api.postalcode.PostalCodeService
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService
import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService
import com.codersteam.alwin.core.api.service.issue.IssueConfigurationService
import com.codersteam.alwin.core.local.DPDService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.*
import com.codersteam.alwin.jpa.issue.Issue
import com.codersteam.alwin.jpa.issue.IssueTerminationRequest
import com.codersteam.alwin.testdata.TagTestData
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION
import static com.codersteam.alwin.core.api.model.user.OperatorType.*
import static com.codersteam.alwin.core.api.service.issue.TerminateIssueResult.ISSUE_TERMINATION_REQUEST_CREATED
import static com.codersteam.alwin.core.api.service.issue.TerminateIssueResult.ISSUE_WAS_TERMINATED
import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES
import static com.codersteam.alwin.jpa.type.IssueState.CLOSED_ISSUE_STATES
import static com.codersteam.alwin.jpa.type.IssueState.NONE
import static com.codersteam.alwin.testdata.ActivityTestData.*
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issueDto21
import static com.codersteam.alwin.testdata.CompanyTestData.*
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.INVOICE_ID_2
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.expectedIssueTerminationRequest
import static com.codersteam.alwin.testdata.IssueTestData.*
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_11
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issueDto11
import static com.codersteam.alwin.testdata.OperatorTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_ID_4
import static com.codersteam.alwin.testdata.assertion.IssueAssert.assertIssueEquals
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR
import static com.codersteam.alwin.testdata.criteria.IssueForCompanySearchCriteriaTestData.issueForCompanySearchCriteria
import static com.codersteam.alwin.testdata.criteria.IssueSearchCriteriaTestData.searchCriteria
import static com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData.*
import static java.util.Collections.emptyList
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class IssueServiceImplTest extends Specification {

    @Subject
    IssueServiceImpl service
    IssueAssignnmentServiceImpl issueAssignmentService

    private InvoiceService aidaInvoiceService = Mock(InvoiceService)
    private DateProvider dateProvider = Mock(DateProvider)
    private IssueDao issueDao = Mock(IssueDao)
    private ActivityDao activityDao = Mock(ActivityDao)
    private IssueTypeTransitionDao issueTypeTransitionDao = Mock(IssueTypeTransitionDao)
    private OperatorDao operatorDao = Mock(OperatorDao)
    private CompanyService aidaCompanyService = Mock(CompanyService)
    private PersonService aidaPersonService = Mock(PersonService)
    private AidaService aidaService = Mock(AidaService)
    private ActivityService activityService = Mock(ActivityService)
    private IssueTerminationRequestDao issueTerminationRequestDao = Mock(IssueTerminationRequestDao)
    private InvolvementService aidaInvolvementService = Mock(InvolvementService)
    private SegmentService aidaSegmentService = Mock(SegmentService)
    private IssueInvoiceDao issueInvoiceDao = Mock(IssueInvoiceDao)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper alwinMapper = new AlwinMapper(dateProvider, alwinProperties)
    private BalanceCalculatorService balanceCalculatorService = Mock(BalanceCalculatorService)
    private DPDService dpdService = Mock(DPDService)
    private IssueBalanceUpdaterService issueBalanceUpdaterService = Mock(IssueBalanceUpdaterService)
    private IssueConfigurationService issueConfigurationService = Mock(IssueConfigurationService)
    private PostalCodeService postalCodeService = Mock(PostalCodeService)
    private OperatorsQueueDao operatorsQueueDao = Mock(OperatorsQueueDao)

    private final LinkedHashMap<IssueSortField, SortOrder> NO_SORT_CRITERIA = new LinkedHashMap<>()

    def setup() {
        aidaService.getInvoiceService() >> aidaInvoiceService
        aidaService.getCompanyService() >> aidaCompanyService
        aidaService.getPersonService() >> aidaPersonService
        aidaService.getInvolvementService() >> aidaInvolvementService
        aidaService.getSegmentService() >> aidaSegmentService

        dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")

        service = Spy(IssueServiceImpl, constructorArgs: [issueDao, issueTypeTransitionDao, operatorDao, dateProvider, activityService,
                                                           issueTerminationRequestDao, issueInvoiceDao, activityDao,
                                                          balanceCalculatorService, dpdService, issueBalanceUpdaterService,
                                                          issueConfigurationService, postalCodeService, operatorsQueueDao, alwinMapper])

        issueAssignmentService = Spy(IssueAssignnmentServiceImpl, constructorArgs: [service])
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "should get page of all managed issues"() {
        given:
            issueDao.findAllFilteredIssues(DIRECT_DEBT_COLLECTION_MANAGER.name(), searchCriteriaByIssueOpenStates(), NONE, emptyList(), NO_SORT_CRITERIA) >> expectedFirstPage()
            issueDao.findAllFilteredIssuesCount(DIRECT_DEBT_COLLECTION_MANAGER.name(), searchCriteriaByIssueOpenStates(), NONE, emptyList()) >> TOTAL_VALUES
        when:
            def result = service.findAllIssues(DIRECT_DEBT_COLLECTION_MANAGER, searchCriteriaByIssueOpenStates(), NO_SORT_CRITERIA)

        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedFirstPageDto())
    }

    def "should find issue by id"() {
        given: "issue id"
            final Long issueId = 1L
        and:
            issueDao.get(issueId) >> Optional.of(issue1())
        when:
            def issue = service.findIssue(issueId)
        then:
            assertThat(issue).isEqualToComparingFieldByFieldRecursively(issueDto1())
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    @Unroll
    def "should return if active issue exist [#hasPendingIssue] for company #extCompanyId"() {
        given:
            issueDao.findCompanyActiveIssueId(extCompanyId) >> issueId
        expect:
            service.doesCompanyHaveActiveIssue(extCompanyId) == hasPendingIssue
        where:
            extCompanyId     | issueId                   | hasPendingIssue
            EXT_COMPANY_ID_1 | Optional.empty()          | false
            EXT_COMPANY_ID_2 | Optional.of(INVOICE_ID_4) | true
    }

    def "should find company's active issue id"() {
        given:
            issueDao.findCompanyActiveIssueId(EXT_COMPANY_ID_1) >> Optional.of(INVOICE_ID_4)
        when:
            def issueId = service.findCompanyActiveIssueId(EXT_COMPANY_ID_1)
        then:
            issueId.present
            issueId.get() == INVOICE_ID_4
    }

    def "should not find company's active issue id for not existing company"() {
        given:
            issueDao.findCompanyActiveIssueId(NON_EXISTING_COMPANY_ID) >> Optional.empty()
        when:
            def issueId = service.findCompanyActiveIssueId(NON_EXISTING_COMPANY_ID)
        then:
            !issueId.present
    }

    def "should return all active issues"() {
        given:
            issueDao.findAllActiveIssues() >> TEST_ACTIVE_ISSUES
        when:
            def activeIssues = service.findAllActiveIssues()
        then:
            assertThat(activeIssues).isEqualToComparingFieldByFieldRecursively(TEST_ACTIVE_ISSUES_DTOS)
    }

    def "should return all issues for company excluding given issue"(int firstResult, int maxResult, def expectedPage, def expectedResult) {
        given: 'search criteria'
            def criteria = issueForCompanySearchCriteria(firstResult, maxResult)
        and:
            issueDao.findAllIssuesForCompanyExcludingGivenIssue(COMPANY_ID_1, ISSUE_ID_1, criteria) >> expectedPage
            issueDao.findAllIssuesForCompanyExcludingGivenIssueCount(COMPANY_ID_1, ISSUE_ID_1, criteria) >> TOTAL_ISSUES_FOR_COMPANY_EXCLUDING_GIVEN_ISSUE_VALUES
        when:
            def issues = service.findAllIssuesForCompanyExcludingGivenIssue(COMPANY_ID_1, ISSUE_ID_1, criteria)
        then:
            assertThat(issues).isEqualToComparingFieldByFieldRecursively(expectedResult)
        where:
            firstResult | maxResult | expectedPage                        | expectedResult
            0           | 6         | expectedIssuesForCompanyFirstPage() | expectedIssuesForCompanyFirstPageDto()
            6           | 6         | []                                  | expectedEmptyPage()
    }

    def "should not found issue for user to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_1)
            activityDao.get(ACTIVITY_ID_1) >> Optional.empty()
            issueDao.findFutureActivityForUserToWorkOn(OPERATOR_ID_1, PHONE_DEBT_COLLECTION, inspectionDate) >> []
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then:
            !issue.isPresent()
    }

    def "should found assigned old issue for user to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(TEST_ACTIVITY_1)
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_1)
        and:
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_1) >> issueDto1()
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then:
            issue.isPresent()
            assertThat(issue.get()).isEqualToComparingFieldByFieldRecursively(issueDto1())
    }

    def "should found issue assigned by manager for user to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            activityDao.get(ACTIVITY_ID_2) >> Optional.of(TEST_ACTIVITY_2)
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_2, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_2)
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_2) >> PHONE_DEBT_COLLECTION
        and:
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_1) >> issueDto1()
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_2)
        then:
            issue.isPresent()
            assertThat(issue.get()).isEqualToComparingFieldByFieldRecursively(issueDto1())
    }


    def "should found assigned future issue for user to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_1)
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(TEST_ACTIVITY_1)
            issueDao.findFutureActivityForUserToWorkOn(OPERATOR_ID_1, PHONE_DEBT_COLLECTION, inspectionDate) >> [activity1()]
        and:
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_1) >> issueDto1()
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then:
            issue.isPresent()
            assertThat(issue.get()).isEqualToComparingFieldByFieldRecursively(issueDto1())
    }

    def "should found no assigned issue for user to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            activityDao.get(ACTIVITY_ID_11) >> Optional.of(TEST_ACTIVITY_11)
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_11)
            1 * issueDao.assignNotAssignedActivityToOperator(ACTIVITY_ID_11, OPERATOR_ID_1) >> true
        and:
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_11) >> issueDto11()
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then:
            issue.isPresent()
            assertThat(issue.get()).isEqualToComparingFieldByFieldRecursively(issueDto11())
    }

    def "should update issue balance when getting issue to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_1)
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(TEST_ACTIVITY_1)

        and:
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_1) >> issueDto1()
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then:
            issue.isPresent()
            assertThat(issue.get()).isEqualToComparingFieldByFieldRecursively(issueDto1())
    }

    @Ignore("Zapętla się na while(true)")
    def "should get another issue to work on if issue was closed after balance recalculation"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            activityDao.get(ACTIVITY_ID_1) >> Optional.of(TEST_ACTIVITY_1)
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_1)
        and: 'issue 1 canceled after balance update'
            def issue1 = issueDto1()
            issue1.issueState = IssueStateDto.CANCELED
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_1) >> issue1
        and:
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_21) >> issueDto21()
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then: 'issue 21 returned to work on'
            issue.isPresent()
            assertThat(issue.get()).isEqualToComparingFieldByFieldRecursively(issueDto21())
    }

    def "should not update issue balance when no issue returned to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            issueDao.findFutureActivityForUserToWorkOn(OPERATOR_ID_1, PHONE_DEBT_COLLECTION, inspectionDate) >> []

            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.empty()
            activityDao.get(ACTIVITY_ID_1) >> Optional.empty()
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then:
            !issue.isPresent()
        and:
            0 * issueBalanceUpdaterService.getIssueWithUpdatedBalance(_ as Long)
    }

    def "should retry to found issue for user to work on"() {
        given:
            def inspectionDate = new Date()
            dateProvider.getCurrentDate() >> inspectionDate
            operatorDao.findOperatorIssueTypes(OPERATOR_ID_1) >> PHONE_DEBT_COLLECTION
            operatorsQueueDao.getOperatorsQueue(OPERATOR_ID_1, PHONE_DEBT_COLLECTION) >> Optional.of(TEST_OPERATORS_QUEUE_11)
            activityDao.get(ACTIVITY_ID_11) >> Optional.of(TEST_ACTIVITY_11)
            issueDao.assignNotAssignedActivityToOperator(ACTIVITY_ID_11, OPERATOR_ID_1) >> false
        when:
            def issue = issueAssignmentService.findWorkForUser(OPERATOR_ID_1)
        then:
            !issue.isPresent()
            10 * issueDao.assignNotAssignedActivityToOperator(ACTIVITY_ID_11, OPERATOR_ID_1)
    }

    def "should assign not assigned activities to user"() {
        given:
            issueDao.assignNotAssignedActivityToOperator(ACTIVITY_ID_11, OPERATOR_ID_1) >> assignActivityToUser
        when:
            def result = service.assignNotAssignedIssueWithActivitiesToUser(ACTIVITY_ID_11, OPERATOR_ID_1)
        then:
            result == expectedResult
        where:
            assignActivityToUser | expectedResult
            false                | false
            true                 | true
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "should get page of operator issues by search criteria"(int firstResult, int maxResult, def expectedPage, def expectedResult) {
        given: 'user who wants to retrieve his issues'
            def userId = testOperator1().id
        and: 'search criteria'
            def criteria = searchCriteria(firstResult, maxResult)
        and: 'issue access object'
            issueDao.findOperatorIssues(userId, criteria) >> expectedPage
            issueDao.findOperatorIssuesCount(userId, criteria) >> TOTAL_ASSIGNED_NOT_CLOSED_VALUES

        when: 'finding first page of all issues'
            def result = service.findOperatorIssues(userId, criteria)

        then: 'issues were found'
            assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedResult)

        where:
            firstResult | maxResult | expectedPage                         | expectedResult
            0           | 6         | expectedAssignedNotClosedFirstPage() | expectedAssignedNotClosedFirstPageDto()
            6           | 6         | []                                   | expectedEmptyPage()
    }

    def "should find not managed issues"() {
        given:
            def operatorType = ANALYST

        and:
            def issueIds = [1L, 2L]

        and:
            issueDao.findNotManagedIssues(operatorType.name(), issueIds) >> [1L]

        when:
            def result = service.findNotManagedIssues(operatorType, issueIds)

        then:
            result == [1L]
    }

    def "should update issues assignment"() {
        given:
            def issueIds = [1L, 2L]

        and:
            def operatorId = 1L

        when:
            service.updateIssuesAssignment(operatorId, issueIds)

        then:
            1 * issueDao.updateIssuesAssignment(operatorId, issueIds)
            1 * activityDao.updateActivityOperator(operatorId, issueIds)
    }

    def "should update issues assignment with search criteria"() {
        given:
            def operatorType = ANALYST

        and:
            def operatorId = 1L

        and:
            def criteria = Mock(IssuesSearchCriteria)

        when:
            service.updateIssuesAssignment(operatorId, operatorType, criteria)

        then:
            1 * issueDao.updateIssuesAssignment(operatorId, operatorType.name(), criteria)
            1 * activityDao.updateActivityOperator(operatorId, operatorType.name(), criteria)
    }

    def "should update issues priorities with search criteria"() {
        given:
            def operatorType = ANALYST

        and:
            def priority = 1

        and:
            def criteria = Mock(IssuesSearchCriteria)

        when:
            service.updateIssuesPriority(priority, operatorType, criteria)

        then:
            1 * issueDao.updateIssuesPriority(priority, operatorType.name(), criteria)
    }

    def "should return empty collection for all issues with processing order"() {
        given:
            issueDao.findAllFilteredIssues(DIRECT_DEBT_COLLECTION_MANAGER.name(), searchCriteriaByIssueOpenStates(), NONE, emptyList(), NO_SORT_CRITERIA) >> []
        when:
            def issuesPage = service.findAllIssues(DIRECT_DEBT_COLLECTION_MANAGER, searchCriteriaByIssueOpenStates(), NO_SORT_CRITERIA)
        then:
            assertThat(issuesPage).isEqualToComparingFieldByFieldRecursively(expectedEmptyPage())
    }

    def "should return all filtered managed active issues"() {
        given:
            def operatorType = PHONE_DEBT_COLLECTOR_MANAGER
            def criteria = searchCriteriaByBalance()
        and:
            issueDao.findAllFilteredIssues(operatorType.name(), criteria, CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA) >> expectedFirstPage()
        and:
            issueDao.findAllFilteredIssuesCount(operatorType.name(), criteria, CLOSED_ISSUE_STATES, OPEN_STATES) >> TOTAL_VALUES
        when:
            def issuesPage = service.findAllFilteredManagedIssues(operatorType, criteria, NO_SORT_CRITERIA)
        then:
            assertThat(issuesPage).isEqualToComparingFieldByFieldRecursively(expectedFirstPageDto())
    }

    def "should return empty collection for filtered managed active issues"() {
        given:
            def operatorType = PHONE_DEBT_COLLECTOR_MANAGER
            def criteria = searchCriteriaByBalance()
        and:
            issueDao.findAllFilteredIssues(operatorType.name(), criteria, CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA) >> []
        when:
            def issuesPage = service.findAllFilteredManagedIssues(operatorType, criteria, NO_SORT_CRITERIA)
        then:
            assertThat(issuesPage).isEqualToComparingFieldByFieldRecursively(expectedEmptyPage())
    }

    def "should thrown exception if issue not exist when terminate issue "() {
        given:
            issueDao.get(COMPANY_ID_1) >> Optional.empty()

        when:
            service.terminateIssue(COMPANY_ID_1, null, null, false, null)

        then:
            def e = thrown(EntityNotFoundException)
            e.entityId == COMPANY_ID_1
            0 * issueDao.update(_ as Issue)
            0 * activityService.createCommentActivity(_ as Long, _ as Long, _ as String)
    }

    def "should thrown exception if operator not exist when terminate issue "() {
        given:
            issueDao.get(COMPANY_ID_1) >> Optional.of(issue1())
            operatorDao.get(COMPANY_ID_2) >> Optional.empty()

        when:
            service.terminateIssue(COMPANY_ID_1, COMPANY_ID_2, null, false, null)

        then:
            def e = thrown(EntityNotFoundException)
            e.entityId == COMPANY_ID_2
            0 * issueDao.update(_ as Issue)
            0 * activityService.createCommentActivity(_ as Long, _ as Long, _ as String)
    }

    def "should terminate issue "() {
        given:
            def today = new Date()
            dateProvider.getCurrentDate() >> today
            issueDao.get(COMPANY_ID_1) >> Optional.of(issue1())
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())

        when:
            def result = service.terminateIssue(COMPANY_ID_1, OPERATOR_ID_2, TERMINATION_CAUSE, EXCLUDE_FROM_STATS, EXCLUSION_CAUSE)

        then:
            result == ISSUE_WAS_TERMINATED
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assertIssueEquals(issue, terminatedIssue(today))
                issue
            }
            1 * activityService.createCommentActivity(ISSUE_ID_1, OPERATOR_ID_2, TERMINATION_CAUSE)
    }

    def "should create issue termination request"() {
        given:
            def today = new Date()
            dateProvider.getCurrentDate() >> today
            issueDao.get(COMPANY_ID_1) >> Optional.of(issue1())
            operatorDao.get(OPERATOR_ID_3) >> Optional.of(testOperator3())

        when:
            def result = service.terminateIssue(COMPANY_ID_1, OPERATOR_ID_3, TERMINATION_CAUSE, EXCLUDE_FROM_STATS, EXCLUSION_CAUSE)

        then:
            result == ISSUE_TERMINATION_REQUEST_CREATED
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assertIssueEquals(issue, waitingForTerminationIssue())
                issue
            }
            1 * issueTerminationRequestDao.create(_ as IssueTerminationRequest) >> { List arguments ->
                IssueTerminationRequest request = arguments[0] as IssueTerminationRequest
                assertThat(request)
                        .usingComparatorForFields(SKIP_COMPARATOR, "issue.issueInvoices")
                        .isEqualToComparingFieldByFieldRecursively(expectedIssueTerminationRequest(today))
                request
            }
            1 * activityService.createCommentActivity(ISSUE_ID_1, OPERATOR_ID_3, TERMINATION_CAUSE)
    }

    def "should update issue invoices exclusion"() {
        when:
            service.updateIssueInvoicesExclusion(ISSUE_ID_21, INVOICE_ID_2, true)
        then:
            1 * issueInvoiceDao.updateIssueInvoicesExclusion(ISSUE_ID_21, INVOICE_ID_2, true)
    }

    def "should assign tags to issue"() {
        when:
            service.assignTags(ISSUE_ID_2, [TagTestData.ID_1, TagTestData.ID_2])

        then:
            1 * issueDao.assignTags(ISSUE_ID_2, _ as List) >> { List arguments ->
                List<Long> tags = arguments[1] as List
                tags.get(0) == TagTestData.ID_1
                tags.get(1) == TagTestData.ID_2
            }
    }

    def "should assign tags to given issues"() {
        given:
            def tagIds = new HashSet([TagTestData.ID_1, TagTestData.ID_2])

        and:
            def issueIds = [ISSUE_ID_2, ISSUE_ID_1]

        when:
            service.updateIssuesTags(tagIds, issueIds)

        then:
            1 * issueDao.assignIssuesTags(tagIds, issueIds)
    }

    def "should assign tags to all filtered issues"() {
        given:
            def tagIds = new HashSet([TagTestData.ID_1, TagTestData.ID_2])

        and:
            def operatorType = PHONE_DEBT_COLLECTOR_MANAGER

        and:
            def filters = searchCriteriaByOperator()

        when:
            service.updateIssuesTags(tagIds, operatorType, filters)

        then:
            1 * issueDao.assignIssuesTags(tagIds, operatorType.name(), filters, CLOSED_ISSUE_STATES, OPEN_STATES)
    }

    def "should find unpaid declarations"() {
        given:
            def declaredPaymentDateStart = new Date()
            def declaredPaymentDateEnd = new Date()

        and:
            1 * issueDao.findIssueIdsWithUnpaidDeclarations(declaredPaymentDateStart, declaredPaymentDateEnd) >> [ISSUE_ID_1, ISSUE_ID_11]

        when:
            def issueIds = service.findIssueIdsWithUnpaidDeclarations(declaredPaymentDateStart, declaredPaymentDateEnd)

        then:
            issueIds == [ISSUE_ID_1, ISSUE_ID_11]
    }

    def "should update issue balance and return operated issue"() {
        given:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())
        and:
            issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_1) >> issueDto1()
        when:
            def operatedIssue = service.findOperatedIssueAndUpdateBalance(ISSUE_ID_1, PHONE_DEBT_COLLECTOR)
        then:
            assertThat(operatedIssue.issue).isEqualToComparingFieldByFieldRecursively(issueDto1())
            operatedIssue.editable
    }
}
