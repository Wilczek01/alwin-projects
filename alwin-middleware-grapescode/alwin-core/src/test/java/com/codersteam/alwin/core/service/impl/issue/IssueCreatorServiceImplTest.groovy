package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.model.CompanySegment
import com.codersteam.aida.core.api.service.*
import com.codersteam.alwin.common.issue.Segment
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService
import com.codersteam.alwin.core.local.DPDService
import com.codersteam.alwin.core.local.LocalInvoiceService
import com.codersteam.alwin.core.service.impl.customer.CustomerServiceImpl
import com.codersteam.alwin.db.dao.*
import com.codersteam.alwin.jpa.issue.Issue
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.AlwinConstants.ZERO
import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_1
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN
import static com.codersteam.alwin.testdata.ActivityTypeTestData.ACTIVITY_TYPE_ID_1
import static com.codersteam.alwin.testdata.BalanceTestData.balanceMapPLN1
import static com.codersteam.alwin.testdata.BalanceTestData.balancePLN1
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.CompanyTestData.INVOLVEMENT_2
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer1
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.*
import static com.codersteam.alwin.testdata.IssueTypeTestData.*
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_2
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class IssueCreatorServiceImplTest extends Specification {

    @Subject
    IssueCreatorServiceImpl service

    private InvoiceService aidaInvoiceService = Mock(InvoiceService)
    private DateProvider dateProvider = Mock(DateProvider)
    private BalanceCalculatorService balanceCalculatorService = Mock(BalanceCalculatorService)
    private IssueDao issueDao = Mock(IssueDao)
    private IssueTypeDao issueTypeDao = Mock(IssueTypeDao)
    private OperatorDao operatorDao = Mock(OperatorDao)
    private CompanyService aidaCompanyService = Mock(CompanyService)
    private PersonService aidaPersonService = Mock(PersonService)
    private AidaService aidaService = Mock(AidaService)
    private ActivityService activityService = Mock(ActivityService)
    private CustomerServiceImpl customerService = Mock(CustomerServiceImpl)
    private InvolvementService aidaInvolvementService = Mock(InvolvementService)
    private SegmentService aidaSegmentService = Mock(SegmentService)
    private IssueTypeConfigurationDao issueTypeConfigurationDao = Mock(IssueTypeConfigurationDao)
    private LocalInvoiceService localInvoiceService = Mock(LocalInvoiceService)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)
    private DPDService dpdService = Mock(DPDService)
    private IssueServiceImpl issueService = Mock(IssueServiceImpl)

    def setup() {
        aidaService.getInvoiceService() >> aidaInvoiceService
        aidaService.getCompanyService() >> aidaCompanyService
        aidaService.getPersonService() >> aidaPersonService
        aidaService.getInvolvementService() >> aidaInvolvementService
        aidaService.getSegmentService() >> aidaSegmentService

        service = Spy(IssueCreatorServiceImpl, constructorArgs: [issueTypeDao, operatorDao, dateProvider, activityService,
                                                                 balanceCalculatorService, aidaService, customerService, localInvoiceService,
                                                                 issueTypeConfigurationDao, customerVerifierService, dpdService,issueDao, issueService])
    }

    def "should create issue manually with assignment"() {
        given:
            def expirationDate = parse("2017-10-10")
            def issueStartDate = parse("2017-09-22")
            def issueDPDStartDate = parse("2017-09-17")
        and:
            dateProvider.getCurrentDateStartOfDay() >> issueStartDate
        and:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.of(issueType1())
        and:
        customerService.getCustomerWithAdditionalData(EXT_COMPANY_ID_1) >> testCustomer1()
        and:
            customerService.findActiveContractOutOfServiceNumbers(_ as Long) >> []
        and:
            aidaInvoiceService.getBalanceStartInvoicesByCompanyId(EXT_COMPANY_ID_1, issueStartDate, true) >> [dueInvoiceWithCorrections1()]
            aidaInvoiceService.getBalanceAdditionalInvoicesWithActiveContractByCompanyId(EXT_COMPANY_ID_1, issueStartDate, expirationDate) >> [dueInvoiceWithCorrections2()]
        and:
            balanceCalculatorService.calculateBalancesFromIssueSubjectInvoices(null, EXT_COMPANY_ID_1, [dueInvoiceWithCorrections1()],
                    issueStartDate) >> balanceMapPLN1()
        and:
            operatorDao.get(OPERATOR_ID_2) >> Optional.of(testOperator2())
        when:
            service.createIssueManually(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, expirationDate, OPERATOR_ID_2)
        then:
            1 * issueDao.createIssue(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.expirationDate == expirationDate
                assert issue.dpdStartDate == issueDPDStartDate
                assert issue.createdManually
                assertThat(issue.assignee).isEqualToComparingFieldByFieldRecursively(testOperator2())
            }
            1 * activityService.createAndAssignDefaultActivitiesForIssue(null, ACTIVITY_TYPE_ID_1, OPERATOR_ID_2)
        1 * dpdService.calculateDPDStartDateForNewIssue(_ as List, EXT_COMPANY_ID_1, _) >> issueDPDStartDate
    }

    def "should create manually unassigned issue"() {
        given:
            def expirationDate = parse("2017-10-10")
            def issueStartDate = parse("2017-09-22")
            def issueDPDStartDate = parse("2017-09-17")
        and:
            dateProvider.getCurrentDateStartOfDay() >> issueStartDate
        and:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.of(issueType1())
        and:
        customerService.getCustomerWithAdditionalData(EXT_COMPANY_ID_1) >> testCustomer1()
        and:
            customerService.findActiveContractOutOfServiceNumbers(_ as Long) >> []
        and:
            aidaInvoiceService.getBalanceStartInvoicesByCompanyId(EXT_COMPANY_ID_1, issueStartDate, true) >> [dueInvoiceWithCorrections1()]
            aidaInvoiceService.getBalanceAdditionalInvoicesWithActiveContractByCompanyId(EXT_COMPANY_ID_1, issueStartDate, expirationDate) >> [dueInvoiceWithCorrections2()]
        and:
            balanceCalculatorService.calculateBalancesFromIssueSubjectInvoices(null, EXT_COMPANY_ID_1, [dueInvoiceWithCorrections1()],
                    issueStartDate) >> balanceMapPLN1()
        and:
            operatorDao.get(null) >> Optional.empty()
        when:
            service.createIssueManually(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, expirationDate, null)
        then:
            1 * issueDao.createIssue(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.expirationDate == expirationDate
                assert issue.dpdStartDate == issueDPDStartDate
                assert issue.assignee == null
                assert issue.createdManually
            }
            1 * activityService.createDefaultActivitiesForIssue(null, ACTIVITY_TYPE_ID_1)
        1 * dpdService.calculateDPDStartDateForNewIssue(_ as List, EXT_COMPANY_ID_1, _) >> issueDPDStartDate
    }

    def "should create issue with default expiration date"() {
        given:
            def expirationDate = parse("2017-10-10")
            def issueStartDate = parse("2017-09-22")
        and:
            dateProvider.getCurrentDateStartOfDay() >> issueStartDate
            dateProvider.getDateStartOfDayPlusDays(DURATION_B) >> expirationDate
        and:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.of(issueType1())
        and:
        def customer1 = testCustomer1()
        and:
        customer1.company.segment = Segment.B
        customerService.getCustomerWithAdditionalData(EXT_COMPANY_ID_1) >> customer1
        and:
            customerService.findActiveContractOutOfServiceNumbers(_ as Long) >> []
        and:
            aidaInvoiceService.getBalanceStartInvoicesByCompanyId(EXT_COMPANY_ID_1, issueStartDate, true) >> [dueInvoiceWithCorrections1()]
            aidaInvoiceService.getBalanceAdditionalInvoicesWithActiveContractByCompanyId(EXT_COMPANY_ID_1, issueStartDate, expirationDate) >> [dueInvoiceWithCorrections2()]
        and:
            balanceCalculatorService.calculateBalancesFromIssueSubjectInvoices(null, EXT_COMPANY_ID_1, [dueInvoiceWithCorrections1()],
                    issueStartDate) >> balanceMapPLN1()
        and:
            operatorDao.get(null) >> Optional.empty()
        and:
            dateProvider.getDateStartOfDayPlusDays(DURATION_A) >> expirationDate
        and:
            aidaSegmentService.findCompanySegment(EXT_COMPANY_ID_1) >> CompanySegment.B
        and:
            aidaInvolvementService.calculateCompanyInvolvement(EXT_COMPANY_ID_1) >> INVOLVEMENT_2
        and:
            issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, Segment.B) >> DURATION_B
        when:
            service.createIssueManually(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, null, null)
        then:
            1 * issueDao.createIssue(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.expirationDate == expirationDate
                assert issue.assignee == null
                assert issue.createdManually
            }
            1 * activityService.createDefaultActivitiesForIssue(null, ACTIVITY_TYPE_ID_1)
    }

    def "should get issue type by id"() {
        given:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.of(issueType1())
        when:
            def issueType = service.getIssueType(ISSUE_TYPE_ID_1)
        then:
            issueType == issueType1()
    }

    def "should throw an exception when getting issue type for unknown type id"() {
        given:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.empty()
        when:
            service.getIssueType(ISSUE_TYPE_ID_1)
        then:
            def e = thrown(IllegalArgumentException)
            e.message.contains(ISSUE_TYPE_ID_1.toString())
    }

    def "should create WT1 (PHONE_DEBT_COLLECTION_1) issue"() {
        given:
        def expirationDate = parse("2017-09-27")
            def issueStartDate = parse("2017-09-22")
            def issueDPDStartDate = parse("2017-09-17")
        and:
            dateProvider.getCurrentDateStartOfDay() >> issueStartDate
        and:
        def customer1 = testCustomer1()
        and:
        customer1.company.segment = Segment.B
        customerService.getCustomerWithAdditionalData(EXT_COMPANY_ID_1) >> customer1
        and:
            customerService.findActiveContractOutOfServiceNumbers(_ as Long) >> []
        and:
            aidaInvoiceService.getBalanceStartInvoicesByCompanyId(EXT_COMPANY_ID_1, issueStartDate, true) >> [dueInvoiceWithCorrections1()]
            aidaInvoiceService.getBalanceAdditionalInvoicesWithActiveContractByCompanyId(EXT_COMPANY_ID_1, issueStartDate, expirationDate) >> [dueInvoiceWithCorrections2()]
        and:
            balanceCalculatorService.calculateBalancesFromIssueSubjectInvoices(null, EXT_COMPANY_ID_1, [dueInvoiceWithCorrections1()],
                    issueStartDate) >> balanceMapPLN1()
        and:
            operatorDao.get(null) >> Optional.empty()
        and:
            dateProvider.getDateStartOfDayPlusDays(issueTypeConfigurationDto11().getDuration()) >> expirationDate
        and:
        dateProvider.getCurrentDate() >> issueStartDate
        and:
            aidaSegmentService.findCompanySegment(EXT_COMPANY_ID_1) >> CompanySegment.B
        and:
            aidaInvolvementService.calculateCompanyInvolvement(EXT_COMPANY_ID_1) >> INVOLVEMENT_2
        and:
            issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, Segment.B) >> DURATION_B
        and:
            issueTypeDao.findIssueTypeByName(PHONE_DEBT_COLLECTION_1) >> issueType1()
        when:
            service.createIssue(EXT_COMPANY_ID_1, issueTypeConfigurationDto11())
        then:
            1 * issueDao.createIssue(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.expirationDate == expirationDate
                assert issue.dpdStartDate == issueDPDStartDate
                assert issue.assignee == null
                assert !issue.createdManually
                assert issue.issueType.id == issueType1().id
                assert issue.balanceStartPLN == balancePLN1().balanceStart
                assert issue.currentBalancePLN == balancePLN1().currentBalance
                assert issue.balanceStartEUR == 0
                assert issue.currentBalanceEUR == 0
            }
            1 * activityService.createDefaultActivitiesForIssue(null, ACTIVITY_TYPE_ID_1)
        1 * dpdService.calculateDPDStartDateForNewIssue(_ as List, EXT_COMPANY_ID_1, _) >> issueDPDStartDate
    }

    def "should not create WT1 (PHONE_DEBT_COLLECTION_1) issue for not matching company segment"() {
        given:
        customerService.getCustomerWithAdditionalData(EXT_COMPANY_ID_1) >> testCustomer1()
        and: 'company segment not match issue type configuration segment (B)'
            aidaSegmentService.findCompanySegment(EXT_COMPANY_ID_1) >> CompanySegment.A
        when: 'create WT1 issue for segment B'
            service.createIssue(EXT_COMPANY_ID_1, issueTypeConfigurationDto11())
        then:
            0 * issueDao.create(_ as Issue)
            0 * activityService.createDefaultActivitiesForIssue(null, ACTIVITY_TYPE_ID_1)
    }

    def "should not create WT1 (PHONE_DEBT_COLLECTION_1) issue if company has more overdue invoices"() {
        given:
            def expirationDate = parse("2017-10-10")
            def issueStartDate = parse("2017-09-22")
        and:
            dateProvider.getCurrentDateStartOfDay() >> issueStartDate
            dateProvider.getDateStartOfDayPlusDays(DURATION_B) >> expirationDate
        and:
        def customer1 = testCustomer1()
        and:
        customer1.company.segment = Segment.B
        customerService.getCustomerWithAdditionalData(EXT_COMPANY_ID_1) >> customer1
        and:
            customerService.findActiveContractOutOfServiceNumbers(_ as Long) >> []
        and:
            aidaInvoiceService.getBalanceStartInvoicesByCompanyId(EXT_COMPANY_ID_1, issueStartDate, true) >> [dueInvoiceWithCorrections1()]
            aidaInvoiceService.getBalanceAdditionalInvoicesWithActiveContractByCompanyId(EXT_COMPANY_ID_1, issueStartDate, expirationDate) >> [dueInvoiceWithCorrections2()]
        and:
            balanceCalculatorService.calculateBalancesFromIssueSubjectInvoices(null, EXT_COMPANY_ID_1, [dueInvoiceWithCorrections1()],
                    issueStartDate) >> balanceMapPLN1()
        and:
            operatorDao.get(null) >> Optional.empty()
        and:
            dateProvider.getDateStartOfDayPlusDays(DURATION_A) >> expirationDate
        and:
            aidaSegmentService.findCompanySegment(EXT_COMPANY_ID_1) >> CompanySegment.B
        and:
            aidaInvolvementService.calculateCompanyInvolvement(EXT_COMPANY_ID_1) >> INVOLVEMENT_2
        and:
            issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, Segment.B) >> DURATION_B
        and:
            issueTypeDao.findIssueTypeByName(PHONE_DEBT_COLLECTION_1) >> issueType1()
        when:
            service.createIssue(EXT_COMPANY_ID_1, issueTypeConfigurationDto1())
        then:
            0 * issueDao.create(_ as Issue)
            0 * activityService.createDefaultActivitiesForIssue(null, ACTIVITY_TYPE_ID_1)
    }

    def "should null balance on document be not qualified to create issue"() {
        given:
            def invoice = dueInvoiceWithCorrections1()
            invoice.balanceOnDocument = null
        and:
            customerVerifierService.isBalanceEnoughToCreateIssue(ZERO, PLN, EXCHANGE_RATE_1, _ as BigDecimal) >> false
        expect:
            !service.isBalanceEnoughToCreateIssue(invoice, MIN_BALANCE_START_1)
    }
}
