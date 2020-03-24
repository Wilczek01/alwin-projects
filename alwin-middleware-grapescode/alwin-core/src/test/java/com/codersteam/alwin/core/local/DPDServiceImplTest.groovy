package com.codersteam.alwin.core.local

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto
import com.codersteam.alwin.core.api.service.customer.CustomerService
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AbstractIssuesTestData.createIssueInvoices
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.contractOutOfServiceDto1
import static com.codersteam.alwin.testdata.InvoiceTestData.*
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.MIN_BALANCE_START_1
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*

class DPDServiceImplTest extends Specification {

    @Subject
    private DPDServiceImpl service

    private CustomerService customerService = Mock(CustomerService)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)

    def "setup"() {
        service = new DPDServiceImpl(customerService, customerVerifierService)
    }

    def "should return dpd start date for new issue created automatically"() {
        given:
        def i1 = new AidaInvoiceWithCorrectionsDto()
        i1.realDueDate = parse("2018-10-10")
        i1.currency = "PLN"
        def i2 = new AidaInvoiceWithCorrectionsDto()
        i2.realDueDate = parse("2018-10-11")
        i2.currency = "PLN"
        def startInvoices = [i1, i2]
        and:
            def dpdStartDate = parse("2018-10-10")
        and:
        customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >> true
        and:
        customerService.findActiveContractOutOfServiceNumbers(_) >> Collections.emptySet()
        when:
        def issueDpdStartDate = service.calculateDPDStartDateForNewIssue(startInvoices, EXT_COMPANY_ID_1, BigDecimal.ZERO)
        then:
        issueDpdStartDate == dpdStartDate
    }

    def "should return null dpd start date for new issue created manually with no invoices"() {
        given:
            def startInvoices = []
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        when:
        def issueDpdStartDate = service.calculateDPDStartDateForNewIssue(startInvoices, EXT_COMPANY_ID_1, BigDecimal.ZERO)
        then:
            issueDpdStartDate == null
    }

    def "should return dpd start date for new issue created manually"() {
        given: 'due invoice after invoice2 due date'
            def invoice1 = dueInvoiceWithCorrections1()
            invoice1.dueDate = parse("2017-10-10")
        and: 'oldest due invoice'
            def invoice2 = dueInvoiceWithCorrections2()
            invoice2.dueDate = parse("2017-09-12")
        and: 'oldest invoice with positive balance'
            def invoice3 = dueInvoiceWithCorrections3()
            invoice3.dueDate = parse("2017-08-20")
        and:
            def startInvoices = [invoice3, invoice2, invoice1]
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        and:
        customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >> [true, true, false]

        when:
        def issueDpdStartDate = service.calculateDPDStartDateForNewIssue(startInvoices, EXT_COMPANY_ID_1, BigDecimal.ZERO)
        then:
            issueDpdStartDate == invoice2.realDueDate
    }

    def "should return dpd start date for new issue created manually with contract exclusion"() {
        given: 'due invoice after invoice2 due date'
            def invoice1 = dueInvoiceWithCorrections1()
            invoice1.dueDate = parse("2017-10-10")
        and: 'oldest due invoice but excluded by contract'
            def invoice2 = dueInvoiceWithCorrections2()
            invoice2.dueDate = parse("2017-09-12")
            invoice2.contractNo = contractOutOfServiceDto1().contractNo
        and: 'oldest invoice with positive balance'
            def invoice3 = dueInvoiceWithCorrections3()
            invoice3.dueDate = parse("2017-08-20")
        and:
            def startInvoices = [invoice3, invoice2, invoice1]
        and:
        customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >> [true, true, false]
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> [contractOutOfServiceDto1().contractNo]
        when:
        def issueDpdStartDate = service.calculateDPDStartDateForNewIssue(startInvoices, EXT_COMPANY_ID_1, BigDecimal.ZERO)
        then:
            issueDpdStartDate == invoice1.realDueDate
    }

    def "should return null dpd start for child issue when no invoices"() {
        given:
            def issueInvoices = []
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        when:
            def issueDpdStartDate = service.calculateDPDStartDateForChildIssue(issueInvoices, EXT_COMPANY_ID_1, MIN_BALANCE_START_1)
        then:
            issueDpdStartDate == null
    }

    def "should return dpd start for child issue"() {
        given: 'due invoice issue subject'
            def invoice1 = invoiceWithBalance1()
        and: 'oldest due invoice issue subject'
            def invoice2 = invoiceWithBalance2()
        and: 'not issue subject'
            def invoice5 = invoiceWithBalance5()
        and:
            def issueInvoices = createIssueInvoices(issue1(), [invoice1, invoice2, invoice5])
        and:
            invoice1.realDueDate = parse("2017-10-10")
            invoice2.realDueDate = parse("2017-09-12")
            invoice5.realDueDate = parse("2017-08-20")
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        and:
            customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >> true
        when:
            def issueDpdStartDate = service.calculateDPDStartDateForChildIssue(issueInvoices, EXT_COMPANY_ID_1, MIN_BALANCE_START_1)
        then:
            issueDpdStartDate == invoice2.realDueDate
    }

    def "should return dpd start for child issue but oldest invoice not met min balance for create issue"() {
        given: 'due invoice issue subject'
            def invoice1 = invoiceWithBalance1()
        and: 'oldest due invoice issue subject'
            def invoice2 = invoiceWithBalance2()
        and: 'not issue subject'
            def invoice5 = invoiceWithBalance5()
        and:
            def issueInvoices = createIssueInvoices(issue1(), [invoice1, invoice2, invoice5])
        and:
            invoice1.realDueDate = parse("2017-10-10")
            invoice2.realDueDate = parse("2017-09-12")
            invoice5.realDueDate = parse("2017-08-20")
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        and:
            customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >>> [true, false]
        when:
            def issueDpdStartDate = service.calculateDPDStartDateForChildIssue(issueInvoices, EXT_COMPANY_ID_1, MIN_BALANCE_START_1)
        then:
            issueDpdStartDate == invoice1.realDueDate
    }

    def "should return dpd start for child issue but oldest invoice excluded"() {
        given: 'due invoice issue subject'
            def invoice1 = invoiceWithBalance1()
        and: 'oldest due invoice issue subject'
            def invoice2 = invoiceWithBalance2()
        and: 'not issue subject'
            def invoice5 = invoiceWithBalance5()
        and:
            def issueInvoices = createIssueInvoices(issue1(), [invoice1, invoice2, invoice5])
        and: 'oldest invoice (invoice2) excluded'
            issueInvoices[1].excluded = true
        and:
            invoice1.realDueDate = parse("2017-10-10")
            invoice2.realDueDate = parse("2017-09-12")
            invoice5.realDueDate = parse("2017-08-20")
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        and:
            customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >> true
        when:
            def issueDpdStartDate = service.calculateDPDStartDateForChildIssue(issueInvoices, EXT_COMPANY_ID_1, MIN_BALANCE_START_1)
        then:
            issueDpdStartDate == invoice1.realDueDate
    }

    def "should return dpd start for child issue but oldest invoice contract excluded"() {
        given: 'due invoice issue subject'
            def invoice1 = invoiceWithBalance1()
        and: 'oldest due invoice issue subject'
            def invoice2 = invoiceWithBalance2()
        and: 'not issue subject'
            def invoice5 = invoiceWithBalance5()
        and:
            def issueInvoices = createIssueInvoices(issue1(), [invoice1, invoice2, invoice5])
        and:
            invoice1.realDueDate = parse("2017-10-10")
            invoice2.realDueDate = parse("2017-09-12")
            invoice5.realDueDate = parse("2017-08-20")
        and:
            invoice2.contractNumber = contractOutOfServiceDto1().contractNo
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> [contractOutOfServiceDto1().contractNo]
        and:
            customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >> true
        when:
            def issueDpdStartDate = service.calculateDPDStartDateForChildIssue(issueInvoices, EXT_COMPANY_ID_1, MIN_BALANCE_START_1)
        then:
            issueDpdStartDate == invoice1.realDueDate
    }
}
