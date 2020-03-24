package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.service.*
import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import com.codersteam.alwin.core.api.service.issue.IssueConfigurationService
import com.codersteam.alwin.core.api.service.issue.IssueCreatorService
import com.codersteam.alwin.db.dao.CustomerDao
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.jpa.issue.Issue
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer1
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer4
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.*
import static com.codersteam.alwin.testdata.IssueTypeTestData.DURATION_B
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*

class CreateIssueServiceImplTest extends Specification {

    @Subject
    CreateIssueService service

    private InvoiceService aidaInvoiceService = Mock(InvoiceService)
    private DateProvider dateProvider = Mock(DateProvider)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)
    private IssueDao issueDao = Mock(IssueDao)
    private CustomerDao customerDao = Mock(CustomerDao)
    private CompanyService aidaCompanyService = Mock(CompanyService)
    private PersonService aidaPersonService = Mock(PersonService)
    private AidaService aidaService = Mock(AidaService)
    private InvolvementService aidaInvolvementService = Mock(InvolvementService)
    private SegmentService aidaSegmentService = Mock(SegmentService)
    private IssueCreatorService issueCreatorService = Mock(IssueCreatorService)
    private IssueConfigurationService issueConfigurationService = Mock(IssueConfigurationService)

    def setup() {
        aidaService.getInvoiceService() >> aidaInvoiceService
        aidaService.getCompanyService() >> aidaCompanyService
        aidaService.getPersonService() >> aidaPersonService
        aidaService.getInvolvementService() >> aidaInvolvementService
        aidaService.getSegmentService() >> aidaSegmentService

        dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")

        //noinspection GroovyAssignabilityCheck
        service = Spy(CreateIssueService, constructorArgs: [dateProvider, customerVerifierService, aidaService, issueCreatorService, issueConfigurationService])
    }

    def "should create issues when due invoices exists for given day"() {
        given: "issue creation for documents form date"
            def dueDate = parse(DUE_DATE)
            dateProvider.getDateStartOfDayMinusDays(issueTypeConfigurationDto1().dpdStart) >> dueDate
            dateProvider.getDateStartOfDayMinusDays(issueTypeConfigurationDto11().dpdStart) >> dueDate
        and: "expiration date"
            def expirationDate = parse(EXPIRATION_DATE)
            dateProvider.getDateStartOfDayMinusDays(DURATION_B) >> expirationDate
        and: "find invoices with given date"
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(_, _, _) >> dueSimpleInvoices()
        and: "find customer with given id"
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1) >> Optional.of(testCustomer1())
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_2) >> Optional.of(testCustomer4())
        and: "filter clients for issue create"
        customerVerifierService.filterCustomersForIssueCreation(_, _) >> true
        and:
            issueConfigurationService.findAllCreateAutomaticallyIssueTypeConfigurations() >> WT1_CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATION_DTOS
        when:
            service.createIssues()
        then:
        6 * issueCreatorService.createIssue(_ as Long, _ as IssueTypeConfigurationDto)
    }

    def "should not create issues when due invoices does not exist for given day"() {
        given: "no issues with given date"
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(_, _, _) >> []
        and:
            issueConfigurationService.findAllCreateAutomaticallyIssueTypeConfigurations() >> WT1_CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATION_DTOS
        when:
            service.createIssues()
        then:
            0 * issueDao.create(_ as Issue)
    }

    def "should continue create issue when exception occurs"() {
        given: "issue creation for documents form date"
            def dueDate = parse(DUE_DATE)
            dateProvider.getDateStartOfDayMinusDays(issueTypeConfigurationDto1().dpdStart) >> dueDate
            dateProvider.getDateStartOfDayMinusDays(issueTypeConfigurationDto11().dpdStart) >> dueDate
        and: "expiration date"
            def expirationDate = parse(EXPIRATION_DATE)
            dateProvider.getDateStartOfDayMinusDays(DURATION_B) >> expirationDate
        and: "find invoices with given date"
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(_, _,_) >> dueSimpleInvoices()
        and: "filter clients for issue create"
        customerVerifierService.filterCustomersForIssueCreation(_, _) >> true
        and:
        6 * issueCreatorService.createIssue(_ as Long, _ as IssueTypeConfigurationDto) >> {
                throw new RuntimeException("exception during issue creation")
            }
        and:
            issueConfigurationService.findAllCreateAutomaticallyIssueTypeConfigurations() >> WT1_CREATE_AUTOMATICALLY_ISSUE_TYPE_CONFIGURATION_DTOS
        when:
            service.createIssues()
        then:
            noExceptionThrown()
    }
}
