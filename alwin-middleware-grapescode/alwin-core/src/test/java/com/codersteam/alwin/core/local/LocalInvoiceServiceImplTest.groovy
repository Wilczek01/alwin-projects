package com.codersteam.alwin.core.local

import com.codersteam.aida.core.api.model.CompanySegment
import com.codersteam.aida.core.api.service.SegmentService
import com.codersteam.alwin.common.issue.IssueTypeName
import com.codersteam.alwin.common.issue.Segment
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.core.util.IssueUtils
import com.codersteam.alwin.db.dao.InvoiceDao
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao
import com.codersteam.alwin.jpa.issue.Invoice
import com.codersteam.alwin.testdata.InvoiceTestData
import com.codersteam.alwin.testdata.aida.AidaInvoiceTestData
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.core.util.IssueUtils.getExtCompanyId
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue7
import static com.codersteam.alwin.testdata.InvoiceTestData.invoiceWithBalance1
import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class LocalInvoiceServiceImplTest extends Specification {

    @Subject
    private LocalInvoiceServiceImpl service

    private InvoiceDao invoiceDao = Mock(InvoiceDao)
    private IssueTypeConfigurationDao issueTypeConfigurationDao = Mock(IssueTypeConfigurationDao)
    private SegmentService segmentService = Mock(SegmentService)
    private AlwinMapper mapper = new AlwinMapper()
    private DateProvider dateProvider = Mock(DateProvider)

    def "setup"() {
        def aidaService = Mock(AidaService)
        aidaService.getSegmentService() >> segmentService
        //noinspection GroovyAssignabilityCheck
        service = Spy(LocalInvoiceServiceImpl, constructorArgs: [invoiceDao, issueTypeConfigurationDao, aidaService, mapper, dateProvider])
    }

    def "should prepare issue invoice"() {
        given:
            def invoice = invoiceWithBalance1()
            def invoiceDueDate = parse("2018-10-10")
            invoice.realDueDate = invoiceDueDate
        and:
            def invoiceBalance = -0.01
            invoice.currentBalance = invoiceBalance
        and:
            def issue = issue1()
            issue.startDate = parse("2018-10-11")
            issue.expirationDate = parse("2018-10-20")
        and:
            def includeDueInvoicesDuringIssue = false
        when:
            def issueInvoice = service.prepareIssueInvoice(issue, invoice, includeDueInvoicesDuringIssue)
        then:
            invoice.currentBalance < 0
        and:
            invoice.realDueDate.before(issue.startDate)
        and:
            issueInvoice.invoice.id == invoice.id
            issueInvoice.issue.id == issue.id
        and:
            !issueInvoice.excluded
        and:
            issueInvoice.additionDate != null
        and:
            issueInvoice.finalBalance == invoiceBalance
            issueInvoice.inclusionBalance == invoiceBalance
        and:
            issueInvoice.issueSubject
    }

    @SuppressWarnings(["GroovyPointlessBoolean", "GroovyAssignabilityCheck"])
    @Unroll
    def "should prepare issue invoice with balance #invoiceBalance, due date #invoiceDueDate, current date #currentDate, include due invoices during issue #isIncludeDueInvoicesDuringIssue, issue start date #issueStartDate and issue end date #issueExpirationDate as #issueSubjectMessage issue subject invoice"() {
        given:
            def invoice = invoiceWithBalance1()
            invoice.realDueDate = parse(invoiceDueDate)
        and:
            invoice.currentBalance = invoiceBalance
        and:
            def issue = issue1()
            issue.startDate = parse(issueStartDate)
            issue.expirationDate = parse(issueExpirationDate)
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse(currentDate)
        and:
            def issueInvoice = service.prepareIssueInvoice(issue, invoice, isIncludeDueInvoicesDuringIssue)
        expect:
            issueInvoice.issueSubject == isIssueSubject
        where: 'not include due invoices as issue subject during issue'
            invoiceBalance | invoiceDueDate | issueStartDate | issueExpirationDate | currentDate | isIncludeDueInvoicesDuringIssue || isIssueSubject
            -0.01          | "2018-10-10"   | "2018-10-11"   | "2018-10-20"        | null        | false                           || true
            0.01           | "2018-10-10"   | "2018-10-11"   | "2018-10-20"        | null        | false                           || false
            -0.01          | "2018-10-11"   | "2018-10-11"   | "2018-10-20"        | null        | false                           || false
            0.01           | "2018-10-11"   | "2018-10-11"   | "2018-10-20"        | null        | false                           || false
        and: 'include due invoices as issue subject during issue'
            -0.01 | "2018-10-10" | "2018-10-11" | "2018-10-20" | "2018-10-11" | true || true
            0.01  | "2018-10-10" | "2018-10-11" | "2018-10-20" | "2018-10-11" | true || false
            -0.01 | "2018-10-11" | "2018-10-11" | "2018-10-20" | "2018-10-11" | true || false
            0.01  | "2018-10-11" | "2018-10-11" | "2018-10-20" | "2018-10-12" | true || false
            -0.01 | "2018-10-11" | "2018-10-11" | "2018-10-20" | "2018-10-12" | true || true
            -0.01 | "2018-10-12" | "2018-10-11" | "2018-10-20" | "2018-10-13" | true || true
            -0.01 | "2018-10-20" | "2018-10-11" | "2018-10-20" | "2018-10-21" | true || true
            -0.01 | "2018-10-21" | "2018-10-11" | "2018-10-20" | "2018-10-22" | true || false
            0.01  | "2018-10-21" | "2018-10-11" | "2018-10-20" | "2018-10-22" | true || false
            null  | "2018-10-21" | "2018-10-11" | "2018-10-20" | "2018-10-22" | true || false
            0.00  | "2018-10-12" | "2018-10-11" | "2018-10-20" | "2018-10-13" | true || false
            0.00  | "2018-10-12" | "2018-10-11" | "2018-10-20" | "2018-10-13" | true || false

            issueSubjectMessage = isIssueSubject ? "" : "NOT"
    }

    def "should return if due invoices should be added as issue subjects during issue when company segment already set"() {
        given:
            def issue = issue7()
            def segment = IssueUtils.getCompanySegmentFromIssue(issue)
        when:
            def isIncludeDebtInvoicesDuringIssue = service.isIncludeDueInvoicesDuringIssue(issue)
        then:
            segment != null
        and:
            0 * segmentService.findCompanySegment(getExtCompanyId(issue))
        and:
            1 * issueTypeConfigurationDao.findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(issue.issueType.name, segment) >> { true }
        and:
            isIncludeDebtInvoicesDuringIssue
    }

    def "should return if due invoices should be added as issue subjects during issue when company segment not set"() {
        given:
            def issue = issue7()
        and: 'company segment not determined yet'
            issue.contract.customer.company.segment = null
        when:
            def isIncludeDebtInvoicesDuringIssue = service.isIncludeDueInvoicesDuringIssue(issue)
        then:
            1 * segmentService.findCompanySegment(getExtCompanyId(issue)) >> { CompanySegment.B }
        and:
            1 * issueTypeConfigurationDao.findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(issue.issueType.name, Segment.B) >> { true }
        and:
            isIncludeDebtInvoicesDuringIssue
    }

    def "should prepare issue invoices with existing exchange rate when new invoice from aida appeared"() {
        given:
            def aidaInvoiceDtos = [AidaInvoiceTestData.dueInvoiceWithCorrections3()]
        and:
            invoiceDao.findInvoiceByNumber(_ as String) >> Optional.empty()
        and:
            issueTypeConfigurationDao.findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(_ as IssueTypeName, _ as Segment) >> true
            dateProvider.getCurrentDateStartOfDay() >> new Date()
        when:
            def invoices = service.prepareInvoices(aidaInvoiceDtos, issue7(), Collections.emptySet())
        then:
            invoices.first().invoice.exchangeRate == AidaInvoiceTestData.EXCHANGE_RATE_3
    }

    def "should update issue invoices with existing exchange rate when new invoice aida contains exchange rate"() {
        given:
            invoiceDao.findInvoiceByNumber(_ as String) >> Optional.of(InvoiceTestData.testInvoice1())
        and:
            def aidaInvoiceDtos = [AidaInvoiceTestData.dueInvoiceWithCorrections1()]
        and:
            issueTypeConfigurationDao.findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(_ as IssueTypeName, _ as Segment) >> true
            dateProvider.getCurrentDateStartOfDay() >> new Date()
            1 * invoiceDao.update(_ as Invoice) >> {
                List args -> (Invoice) args[0]
            }
        when:
            def invoices = service.prepareInvoices(aidaInvoiceDtos, issue7(), Collections.emptySet())
        then:
            invoices.first().invoice.exchangeRate == AidaInvoiceTestData.EXCHANGE_RATE_1
    }

}
