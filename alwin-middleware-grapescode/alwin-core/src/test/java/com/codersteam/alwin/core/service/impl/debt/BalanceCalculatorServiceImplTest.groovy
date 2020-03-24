package com.codersteam.alwin.core.service.impl.debt


import com.codersteam.aida.core.api.service.InvoiceService
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerRPBService
import com.codersteam.alwin.core.api.service.customer.CustomerService
import com.codersteam.alwin.core.util.IssueUtils
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.db.dao.IssueInvoiceDao
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.common.AlwinConstants.ZERO
import static com.codersteam.alwin.core.api.model.currency.Currency.EUR
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN
import static com.codersteam.alwin.testdata.AbstractIssuesTestData.createIssueInvoices
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1
import static com.codersteam.alwin.testdata.BalanceTestData.*
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.contractOutOfServiceDtoForInvoice10
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.contractOutOfServiceDtoForInvoice2
import static com.codersteam.alwin.testdata.InvoiceTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*
import static java.util.Arrays.asList

/**
 * @author Tomasz Sliwinski
 */
class BalanceCalculatorServiceImplTest extends Specification {

    @Subject
    private BalanceCalculatorServiceImpl service

    private DateProvider dateProvider = Mock(DateProvider)
    private InvoiceService invoiceService = Mock(InvoiceService)
    private CustomerService customerService = Mock(CustomerService)
    private IssueDao issueDao = Mock(IssueDao)
    private IssueInvoiceDao issueInvoiceDao = Mock(IssueInvoiceDao)
    private CustomerRPBService customerRPBService = Mock(CustomerRPBService)
    private AidaService aidaService = Mock(AidaService)

    def "setup"() {
        aidaService.getInvoiceService() >> invoiceService

        service = Spy(BalanceCalculatorServiceImpl,
                constructorArgs: [dateProvider, aidaService, customerService, issueDao, issueInvoiceDao, customerRPBService]) as BalanceCalculatorServiceImpl
    }

    def "should recalculate issue balance from invoices"() {
        given:
            def issue = issue1()
            def issueStartDate = parse("2020-01-10")
            issue.setStartDate(issueStartDate)
            issue.setIssueInvoices(createIssueInvoices(issue, asList(testInvoice1(), testInvoice3(), testInvoice4(), testInvoice10(), testInvoice14(),
                    testInvoice25(), testInvoice28(), testInvoice31())))
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue)
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> [contractOutOfServiceDtoForInvoice10().contractNo]
        and:
            issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1) >> [testInvoice1()]
        when:
            def balances = service.recalculateBalanceForIssue(ISSUE_ID_1)
        then:
            balances.get(PLN) == expectedBalancePLN()
            balances.get(EUR) == expectedBalanceEUR()
        and:
            1 * customerRPBService.calculateCompanyRPB(EXT_COMPANY_ID_1) >> RPB_PLN
    }

    def "should calculate issue balance in pln from invoices with all exchange rates and other amounts exists"() {
        given:
            def issue = issue1()
            def issueStartDate = parse("2020-01-10")
            issue.setStartDate(issueStartDate)
            issue.setIssueInvoices(createIssueInvoices(issue, asList(testInvoice1(), testInvoice3(), testInvoice4(), testInvoice10(), testInvoice11(),
                    testInvoice25())))
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue)
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> [contractOutOfServiceDtoForInvoice10().contractNo]
        and:
            issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1) >> [testInvoice1()]
        when:
            def balances = service.recalculateBalanceForIssue(ISSUE_ID_1)
        then:
            balances.get(PLN) == expectedBalancePLN()
            balances.get(EUR) == expectedBalanceEURWithAllExchangeRates()
        and:
            1 * customerRPBService.calculateCompanyRPB(EXT_COMPANY_ID_1) >> RPB_PLN
    }

    def "should recalculate issue balance from EUR only invoices"() {
        given:
            def issue = issue1()
            def issueStartDate = parse("2020-01-10")
            issue.setStartDate(issueStartDate)
            issue.setIssueInvoices(createIssueInvoices(issue, asList(testInvoice3(), testInvoice4(), testInvoice10(), testInvoice28())))
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue)
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> [contractOutOfServiceDtoForInvoice10().contractNo]
        and:
            issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1) >> [testInvoice1()]
        when:
            def balances = service.recalculateBalanceForIssue(ISSUE_ID_1)
        then:
            balances.get(PLN) == balanceZero()
            balances.get(EUR) == expectedBalanceEUR()
        and:
            1 * customerRPBService.calculateCompanyRPB(EXT_COMPANY_ID_1) >> ZERO
    }

    def "should calculate balance from issue subject invoices"() {
        given:
            def issueStartDate = parse("2017-09-01")
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> [contractOutOfServiceDtoForInvoice2().contractNo]
        and:
            issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1) >> [testInvoice1()]
        when:
            def balances = service.calculateBalancesFromIssueSubjectInvoices(ISSUE_ID_1, EXT_COMPANY_ID_1,
                    [dueInvoiceWithCorrections1(), dueInvoiceWithCorrections2(), dueInvoiceWithCorrections4(), dueInvoiceWithCorrections7(),
                     dueInvoiceWithCorrections3()], issueStartDate)
        then:
            balances.get(PLN) == expectedBalanceIssueSubjectInvoicesPLN()
            balances.get(EUR) == expectedBalanceIssueSubjectInvoicesEUR()
        and:
            1 * customerRPBService.calculateCompanyRPB(EXT_COMPANY_ID_1) >> RPB_PLN
    }

    def "should handle calculate balance from issue subject invoices when invoice balance is null"() {
        given:
            def issueStartDate = parse("2017-09-01")
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        and:
            issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1) >> []
        when:
            def balances = service.calculateBalancesFromIssueSubjectInvoices(ISSUE_ID_1, EXT_COMPANY_ID_1, [invoiceWithCorrectionsWithoutBalance()],
                    issueStartDate)
        then:
            balances.get(PLN) == balanceZero()
            balances.get(EUR) == balanceZero()
        and:
            1 * customerRPBService.calculateCompanyRPB(EXT_COMPANY_ID_1) >> ZERO
    }

    def "should calculate balance from EUR only issue subject invoices"() {
        given:
            def issueStartDate = parse("2017-09-01")
        and:
            customerService.findActiveContractOutOfServiceNumbers(EXT_COMPANY_ID_1) >> []
        and:
            issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1) >> []
        when:
            def balances = service.calculateBalancesFromIssueSubjectInvoices(ISSUE_ID_1, EXT_COMPANY_ID_1, [dueInvoiceWithCorrections7()], issueStartDate)
        then:
            balances.get(PLN) == balanceZero()
            balances.get(EUR) == expectedBalanceIssueSubjectInvoicesEUR()
        and:
            1 * customerRPBService.calculateCompanyRPB(EXT_COMPANY_ID_1) >> ZERO
    }

    @Unroll
    def "should calculate start and additional balance"() {
        given:
            def issue = issue1()
            issue.startDate = parse("2018-10-10")
            issue.expirationDate = parse("2018-10-30")
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue)
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-10-10")
        and:
            def invoice = issue.getIssueInvoices().get(0).getInvoice()
            invoice.setRealDueDate(parse(invoiceDueDate))
            invoice.setCurrentBalance(invoiceBalance)
            invoice.setCurrency(invoiceCurrency.getStringValue())
        and:
            customerService.findActiveContractOutOfServiceNumbers(IssueUtils.getExtCompanyId(issue)) >> []
        and:
            issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1) >> []
        and:
            def balances = service.calculateBalanceStartAndAdditional(ISSUE_ID_1)
        expect:
            balances.get(PLN).balanceStart == expectedBalanceStartPLN
            balances.get(PLN).balanceAdditional == expectedBalanceAdditionalPLN
            balances.get(EUR).balanceStart == expectedBalanceStartEUR
            balances.get(EUR).balanceAdditional == expectedBalanceAdditionalEUR
        where:
            invoiceDueDate | invoiceBalance | invoiceCurrency | expectedBalanceStartPLN | expectedBalanceAdditionalPLN | expectedBalanceStartEUR | expectedBalanceAdditionalEUR
            "2018-10-09"   | null           | PLN             | 0                       | 0                            | 0                       | 0
            "2018-10-09"   | -23.34         | PLN             | -23.34                  | 0                            | 0                       | 0
            "2018-10-10"   | -23.34         | PLN             | 0                       | -23.34                       | 0                       | 0
            "2018-10-10"   | null           | PLN             | 0                       | 0                            | 0                       | 0
            "2018-10-30"   | -23.34         | PLN             | 0                       | -23.34                       | 0                       | 0
            "2018-10-31"   | -23.34         | PLN             | 0                       | -23.34                       | 0                       | 0
            "2018-10-09"   | -23.34         | EUR             | 0                       | 0                            | -23.34                  | 0
            "2018-10-09"   | null           | EUR             | 0                       | 0                            | 0                       | 0
            "2018-10-10"   | -23.34         | EUR             | 0                       | 0                            | 0                       | -23.34
            "2018-10-10"   | null           | EUR             | 0                       | 0                            | 0                       | 0
            "2018-10-30"   | -23.34         | EUR             | 0                       | 0                            | 0                       | -23.34
            "2018-10-31"   | -23.34         | EUR             | 0                       | 0                            | 0                       | -23.34
    }
}
