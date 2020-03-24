package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.service.InvoiceService
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.core.api.model.issue.IssueDto
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.issue.IssueService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.InvoiceDao
import com.codersteam.alwin.testdata.TestDateUtils
import com.codersteam.alwin.testdata.aida.AidaInvoiceEntryTestData
import com.google.common.collect.Maps
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_21
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.DemandForPaymentInitialDataTestData.demandForPaymentInitialData1
import static com.codersteam.alwin.testdata.InvoiceTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO
import static com.codersteam.alwin.testdata.aida.AidaInvoiceEntryTestData.invoiceEntries
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR
import static com.codersteam.alwin.testdata.criteria.InvoiceSearchCriteriaTestData.invoiceSearchCriteria
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Adam Stepnowski
 */
class InvoiceServiceImplTest extends Specification {

    private InvoiceDao invoiceDao = Mock(InvoiceDao)
    private IssueService issueService = Mock(IssueService)
    private dateProvider = Mock(DateProvider)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper mapper = new AlwinMapper(dateProvider, alwinProperties)
    private InvoiceService invoiceService = Mock(InvoiceService)
    private currentDate = parse("2024-07-12")

    @Subject
    InvoiceServiceImpl service

    def setup() {
        def aidaService = Mock(AidaService)
        aidaService.getInvoiceService() >> invoiceService
        dateProvider.getCurrentDateStartOfDay() >> currentDate

        service = new InvoiceServiceImpl(invoiceDao, issueService, mapper, aidaService)
    }

    def "should return all invoices for issue"(int firstResult, int maxResult, def expectedPage, def expectedResult) {
        given: 'search criteria'
            def searchCriteria = invoiceSearchCriteria(firstResult, maxResult)
        and:
            invoiceDao.findInvoiceForIssueId(ISSUE_ID_21, searchCriteria, Maps.newLinkedHashMap()) >> expectedPage
            invoiceDao.findInvoiceForIssueIdCount(ISSUE_ID_21, searchCriteria) >> TOTAL_INVOICES
        when:
            def invoices = service.findInvoiceForIssueId(ISSUE_ID_21, searchCriteria, Maps.newLinkedHashMap())
        then:
            assertThat(invoices).isEqualToComparingFieldByFieldRecursively(expectedResult)
        where:
            firstResult | maxResult | expectedPage                         | expectedResult
            0           | 2         | expectedInvoicesForIssueFirstPage()  | expectedInvoicesForIssueDtoFirstPage()
            2           | 2         | expectedInvoicesForIssueSecondPage() | expectedInvoicesForIssueDtoSecondPage()
    }

    def "should return invoices with total balance"() {
        given:
            def issueId = ISSUE_ID_21
        and:
            def searchCriteria = invoiceSearchCriteria(0, 6)
            searchCriteria.endDueDate = TestDateUtils.parse("2017-08-09")
        and:
            def issue = new IssueDto()
            issue.expirationDate = TestDateUtils.parse("2017-08-10")
        and:
            issueService.findIssue(issueId) >> issue
        and:
            invoiceDao.findInvoiceForIssueId(issueId, searchCriteria, Maps.newLinkedHashMap()) >> expectedInvoicesWithCurrentBalance()
        and:
            invoiceDao.findInvoiceForIssueIdCount(issueId, searchCriteria) >> TOTAL_INVOICES
        and:
            invoiceService.findAllInvoiceTypes() >> allInvoiceTypeDtos()
        when:
            def invoicesWithTotalBalance = service.findInvoicesWithTotalBalance(issueId, searchCriteria, Maps.newLinkedHashMap())
        then:
            assertThat(invoicesWithTotalBalance.getInvoices())
                    .usingComparatorForFields(SKIP_COMPARATOR, "values.dpd")
                    .isEqualToComparingFieldByFieldRecursively(expectedPageWithInvoicesDtoWithCurrentBalance())
            invoicesWithTotalBalance.getBalance() == -1307.37
    }

    def "should return all invoice types"() {
        given:
            invoiceService.findAllInvoiceTypes() >> allInvoiceTypeDtos()
        when:
            def issueTypes = service.findAllInvoiceTypes()
        then:
            assertThat(issueTypes).isEqualToComparingFieldByFieldRecursively(allInvoiceTypeDtos())
    }

    def "should return all types"() {
        given:
            invoiceService.findAllInvoiceTypes() >> allInvoiceTypeDtos()
        when:
            def issueTypes = service.getAllTypes()
        then:
            assertThat(issueTypes).isEqualToComparingFieldByFieldRecursively(allTypes())
    }

    def "should return all invoices for issue"() {
        given:
            invoiceDao.findInvoicesByIssueId(ISSUE_ID_1) >> [testInvoice1()]
        when:
            def invoiceDtos = service.findInvoicesForIssueId(ISSUE_ID_1)
        then:
            invoiceDtos.size() == 1
            assertThat(invoiceDtos[0]).isEqualToComparingFieldByFieldRecursively(testInvoiceDto1())
    }

    def "should return empty collection of invoices for issue if none found"() {
        given:
            invoiceDao.findInvoicesByIssueId(ISSUE_ID_1) >> []
        when:
            def invoiceDtos = service.findInvoicesForIssueId(ISSUE_ID_1)
        then:
            invoiceDtos.size() == 0
    }

    def "should return invoice entries"() {
        given:
            invoiceService.findInvoiceEntries(INVOICE_NUMBER_14) >> invoiceEntries()
        when:
            def invoiceEntries = service.findInvoiceEntries(INVOICE_NUMBER_14)
        then:
            assertThat(invoiceEntries).isEqualToComparingFieldByFieldRecursively(AidaInvoiceEntryTestData.invoiceEntries())
    }

    def "should find contract due invoices"() {
        given:
            invoiceService.getBalanceStartInvoicesByCompanyId(demandForPaymentInitialData1().extCompanyId, currentDate, false) >> [dueInvoiceWithCorrections1(),
                                                                                                                            dueInvoiceWithCorrections2(),
                                                                                                                            dueInvoiceWithCorrections3(),
                                                                                                                            dueInvoiceWithCorrections4(),
                                                                                                                            dueInvoiceWithCorrections5()]
        when:
            def dueContractInvoices = service.getContractDueInvoices(EXT_COMPANY_ID_1, CONTRACT_NO, currentDate)
        then:
            dueContractInvoices.size() == 1
        and: 'only invoice 1 has proper contract number and balance below 0'
            dueContractInvoices[0].id == INVOICE_ID_1
            dueContractInvoices[0].contractNo == CONTRACT_NO
            dueContractInvoices[0].balanceOnDocument < 0
    }
}