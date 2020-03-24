package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto
import com.codersteam.aida.core.api.service.InvoiceService
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.local.LocalInvoiceService
import com.codersteam.alwin.db.dao.InvoiceDao
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.db.dao.IssueInvoiceDao
import com.codersteam.alwin.jpa.issue.Invoice
import com.codersteam.alwin.jpa.issue.Issue
import com.codersteam.alwin.jpa.issue.IssueInvoice
import com.codersteam.alwin.testdata.aida.AidaInvoiceTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.core.util.IssueUtils.getExtCompanyId
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice1
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_NUMBER_1
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoiceWithCorrections1
import static java.util.Collections.emptyList

/**
 * @author Adam Stepnowski
 */
class InvoiceBalanceUpdaterServiceImplTest extends Specification {

    @Subject
    private InvoiceBalanceUpdaterServiceImpl service

    private InvoiceDao invoiceDao = Mock(InvoiceDao)
    private IssueDao issueDao = Mock(IssueDao)
    private InvoiceService invoiceService = Mock(InvoiceService)
    private IssueInvoiceDao issueInvoiceDao = Mock(IssueInvoiceDao)
    private LocalInvoiceService localInvoiceService = Mock(LocalInvoiceService)
    private DateProvider dateProvider = Mock(DateProvider)

    def "setup"() {
        def aidaService = Mock(AidaService)
        aidaService.getInvoiceService() >> invoiceService

        //noinspection GroovyAssignabilityCheck
        service = Spy(InvoiceBalanceUpdaterServiceImpl, constructorArgs: [invoiceDao, issueDao, aidaService, issueInvoiceDao, localInvoiceService,
                                                                          dateProvider])
    }

    def "should have default constructor for EJB"() {
        when:
            def service = new InvoiceBalanceUpdaterServiceImpl()
        then:
            service
    }

    def "should update invoices balance"() {
        given:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
        and:
            def aidaInvoice = dueInvoiceWithCorrections1()
            def invoiceIssueDate = parse("2017-02-12")
            aidaInvoice.setIssueDate(invoiceIssueDate)
            invoiceService.findInvoicesWithActiveContractByCompanyId(getExtCompanyId(issue2())) >> [aidaInvoice]
        and:
            invoiceDao.findInvoiceForIssueIdAndInvoiceNo(issue2().getId(), INVOICE_NUMBER_1) >> Optional.of(testInvoice1())
        and:
            issueInvoiceDao.findInvoicesOutOfService(_ as Long) >> emptyList()
        when:
            service.updateIssueInvoicesBalance(ISSUE_ID_2)
        then:
            1 * invoiceDao.update(_ as Invoice) >> {
                List args ->
                    def invoice = (Invoice) args[0]
                    assert invoice.paid != null
                    assert invoice.currentBalance != null
                    assert invoice.issueDate == invoiceIssueDate
                    assert invoice.lastPaymentDate == AidaInvoiceTestData.LAST_PAYMENT_DATE_1
            }
        and:
            0 * invoiceDao.create(_ as Invoice)
        and:
            1 * issueInvoiceDao.updateIssueInvoicesFinalBalance(ISSUE_ID_2)
    }

    def "should update invoices balance with excluded invoice and set issue date"() {
        given:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
        and:
            def aidaInvoice = dueInvoiceWithCorrections1()
            def invoiceIssueDate = parse("2017-02-12")
            aidaInvoice.setIssueDate(invoiceIssueDate)
            invoiceService.findInvoicesWithActiveContractByCompanyId(getExtCompanyId(issue2())) >> [aidaInvoice]
        and:
            def invoice1 = testInvoice1()
            invoice1.paid = null
            invoice1.currentBalance = null
            invoice1.issueDate = null
            invoiceDao.findInvoiceForIssueIdAndInvoiceNo(issue2().getId(), INVOICE_NUMBER_1) >> Optional.of(invoice1)
        and:
            issueInvoiceDao.findInvoicesOutOfService(_ as Long) >> Arrays.asList(testInvoice1())
        when:
            service.updateIssueInvoicesBalance(ISSUE_ID_2)
        then:
            1 * invoiceDao.update(_ as Invoice) >> {
                List args ->
                    def invoice = (Invoice) args[0]
                    assert invoice.paid == null
                    assert invoice.currentBalance == null
                    assert invoice.issueDate == invoiceIssueDate
                    assert invoice.lastPaymentDate == invoice1.lastPaymentDate
            }
        and:
            0 * invoiceDao.create(_ as Invoice)
        and:
            1 * issueInvoiceDao.updateIssueInvoicesFinalBalance(ISSUE_ID_2)
    }

    def "should create invoice when no data found during update"() {
        given:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
        and:
            invoiceService.getBalanceStartInvoicesByCompanyId(getExtCompanyId(issue2()), issue2().getStartDate(), false) >> []
        and:
            invoiceService.findInvoicesWithActiveContractByCompanyId(getExtCompanyId(issue2())) >>
                    [dueInvoiceWithCorrections1()]
        and:
            invoiceDao.findInvoiceForIssueIdAndInvoiceNo(issue2().getId(), INVOICE_NUMBER_1) >> Optional.empty()
        and:
            def includeDueInvoicesDuringIssue = false
        and:
            def issueInvoice = new IssueInvoice()
            issueInvoice.setInvoice(testInvoice1())
            localInvoiceService.prepareIssueInvoice(_ as Issue, _ as AidaInvoiceWithCorrectionsDto, includeDueInvoicesDuringIssue) >> issueInvoice
        when:
            service.updateIssueInvoicesBalance(ISSUE_ID_2)
        then:
            0 * invoiceDao.update(_ as Invoice)
        and:
            1 * invoiceDao.create(_ as Invoice) >> {
                List args ->
                    def invoice = (Invoice) args[0]
                    assert invoice.paid != null
                    assert invoice.currentBalance != null
            }
        and:
            1 * issueInvoiceDao.updateIssueInvoicesFinalBalance(ISSUE_ID_2)
    }

    def "should throw an exception when issue for update invoices balance not found"() {
        given:
            issueDao.get(NOT_EXISTING_ISSUE_ID) >> Optional.empty()
        when:
            service.updateIssueInvoicesBalance(NOT_EXISTING_ISSUE_ID)
        then:
            thrown(EntityNotFoundException)
    }

    def "should not set invoice as issue subject when configuration not allows to"() {
        given:
            def invoice = testInvoice1()
            def issue = issue2()
            def isIncludeDueInvoicesDuringIssue = false
        when:
            service.setIssueSubjectIfNeeded(invoice, issue, isIncludeDueInvoicesDuringIssue)
        then:
            def issueInvoice = invoice.issueInvoices.find { issueInvoice -> (issueInvoice.getIssue().getId() == issue.id) }
            !issueInvoice.issueSubject
    }

    def "should set invoice as issue subject when configuration allows to"() {
        given: 'invoice with negative balance'
            def invoice = testInvoice1()
            invoice.setCurrentBalance(-1.00)
        and:
            def issue = issue2()
            def isIncludeDueInvoicesDuringIssue = true
        and: 'day after invoice due date'
            dateProvider.getCurrentDateStartOfDay() >> parse("2015-12-10")
        when:
            service.setIssueSubjectIfNeeded(invoice, issue, isIncludeDueInvoicesDuringIssue)
        then:
            def issueInvoice = invoice.issueInvoices.find { issueInvoice -> (issueInvoice.getIssue().getId() == issue.id) }
            issueInvoice.issueSubject
    }
}