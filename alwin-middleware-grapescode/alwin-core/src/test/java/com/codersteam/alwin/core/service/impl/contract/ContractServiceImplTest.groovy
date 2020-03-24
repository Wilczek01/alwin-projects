package com.codersteam.alwin.core.service.impl.contract

import com.codersteam.aida.core.api.service.ContractService
import com.codersteam.alwin.core.api.model.issue.InvoiceDto
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerService
import com.codersteam.alwin.core.api.service.issue.InvoiceService
import com.codersteam.alwin.core.api.service.issue.IssueService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.ContractOutOfServiceDao
import com.codersteam.alwin.db.dao.FormalDebtCollectionContractOutOfServiceDao
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST
import static com.codersteam.alwin.core.util.IssueUtils.getExtCompanyId
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto1
import static com.codersteam.alwin.testdata.ContractFinancialSummaryTestData.contractFinancialSummaryDto1
import static com.codersteam.alwin.testdata.ContractFinancialSummaryTestData.contractFinancialSummaryDto2
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.*
import static com.codersteam.alwin.testdata.InvoiceTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.*

/**
 * @author Tomasz Sliwinski
 */
class ContractServiceImplTest extends Specification {

    @Subject
    private ContractServiceImpl service

    private InvoiceService invoiceService = Mock(InvoiceService)
    private IssueService issueService = Mock(IssueService)
    private ContractOutOfServiceDao contractOutOfServiceDao = Mock(ContractOutOfServiceDao)
    private DateProvider dateProvider = Mock(DateProvider)
    private ContractService aidaContractService = Mock(ContractService)
    private CustomerService customerService = Mock(CustomerService)
    private AidaService aidaService = Mock(AidaService)
    private AlwinMapper mapper = new AlwinMapper()
    private FormalDebtCollectionContractOutOfServiceDao formalDebtCollectionContractOutOfServiceDao = Mock(FormalDebtCollectionContractOutOfServiceDao)

    def "setup"() {
        aidaService.getContractService() >> aidaContractService
        service = new ContractServiceImpl(invoiceService, issueService, contractOutOfServiceDao, dateProvider, aidaService, customerService, mapper, formalDebtCollectionContractOutOfServiceDao)
    }

    def "should group invoices by contract number"() {
        given:
            def invoices = invoiceDtos()
        when:
            def invoicesByContractNo = service.groupInvoicesByContractNo(invoices)
        then:
            invoicesByContractNo.size() == 3
            invoicesByContractNo.keySet().containsAll([
                    CONTRACT_NUMBER_INVOICE_1,
                    CONTRACT_NUMBER_INVOICE_2,
                    CONTRACT_NUMBER_INVOICE_3,
                    CONTRACT_NUMBER_INVOICE_4
            ])
    }

    def "should calculate required payment"() {
        given:
            def issueStartDate = parse("2015-12-10")
            def invoices = invoiceDtos()
        when:
            def requiredPaymentValue = service.calculateRequiredPayment(issueStartDate, invoices)
        then:
            requiredPaymentValue == CURRENT_BALANCE_INVOICE_4.abs()
    }

    def "should calculate not required payment value"() {
        given:
            def issueStartDate = parse("2015-12-10")
            def invoices = invoiceDtos()
        when:
            def notRequiredPaymentValue = service.calculateNotRequiredPayment(issueStartDate, invoices)
        then:
            notRequiredPaymentValue == CURRENT_BALANCE_INVOICE_5.abs()
    }

    def "should calculate overpayment"() {
        given:
            def invoices = invoiceDtos()
        when:
            def overpaymentValue = service.calculateOverpayment(invoices)
        then:
            overpaymentValue == CURRENT_BALANCE_INVOICE_6 + CURRENT_BALANCE_INVOICE_3
    }

    def "should check if financial summary contains overdue payments"() {
        when:
            def isPaid = service.isPaid(contractFinancialSummaryDto1())
        then:
            !isPaid
    }

    def "should check if financial summary NOT contains overdue payments"() {
        when:
            def isPaid = service.isPaid(contractFinancialSummaryDto2())
        then:
            isPaid
    }

    def "should return empty collection of issue contract financial summaries when no invoices for issue found"() {
        given:
            def issueId = ISSUE_ID_1
        and:
            invoiceService.findInvoicesForIssueId(issueId) >> []
        when:
            def summaries = service.calculateIssueContractFinancialSummaries(issueId, false, false)
        then:
            summaries.size() == 0
    }

    def "should throw an exception when cannot find issue for invoices"() {
        given:
            def issueId = ISSUE_ID_1
        and:
            invoiceService.findInvoicesForIssueId(issueId) >> invoiceDtos()
        and:
            issueService.findIssue(issueId) >> null
        when:
            service.calculateIssueContractFinancialSummaries(issueId, false, false)
        then:
            def e = thrown(IllegalStateException)
            e.message == 'Cannot find issue with id 1, but 7 invoices found!'
    }

    def "should calculate issue contract financial summaries all contracts"() {
        given:
            def issueId = ISSUE_ID_1
        and:
            invoiceService.findInvoicesForIssueId(issueId) >> invoiceDtos()
        and:
            def issue = issueDto1()
            issue.setStartDate(parse("2015-12-10"))
            issueService.findIssue(issueId) >> issue
        and:
            dateProvider.currentDate >> parse("2017-10-10")
        and:
            contractOutOfServiceDao.findActiveContractsOutOfService(getExtCompanyId(issue), parse("2017-10-10")) >> []
        when:
            def summaries = service.calculateIssueContractFinancialSummaries(issueId, false, false)
        then:
            summaries.size() == 3
    }

    def "should calculate issue contract financial summaries only not paid payment contracts"() {
        given:
            def issueId = ISSUE_ID_1
        and:
            invoiceService.findInvoicesForIssueId(issueId) >> invoiceDtos()
        and:
            def issue = issueDto1()
            issue.setStartDate(parse("2015-12-10"))
            issueService.findIssue(issueId) >> issue
        and:
            dateProvider.currentDate >> parse("2017-10-10")
        and:
            contractOutOfServiceDao.findActiveContractsOutOfService(getExtCompanyId(issue), parse("2017-10-10")) >> []
        when:
            def summaries = service.calculateIssueContractFinancialSummaries(issueId, true, false)
        then:
            summaries.size() == 2
    }

    def "should mark contract as excluded"() {
        given:
            def issueId = ISSUE_ID_1
        and:
            invoiceService.findInvoicesForIssueId(issueId) >> invoiceDtos()
        and:
            def issue = issueDto1()
            issue.setStartDate(parse("2015-12-10"))
            issueService.findIssue(issueId) >> issue
        and:
            dateProvider.currentDate >> parse("2017-10-10")
        and:
            contractOutOfServiceDao.findActiveContractsOutOfService(getExtCompanyId(issue), parse("2017-10-10")) >>
                    [contractOutOfService(CONTRACT_NUMBER_INVOICE_2)]
        when:
            def summaries = service.calculateIssueContractFinancialSummaries(issueId, true, false)
        then:
            def excludedContractSummary = summaries.find { contractSummary -> contractSummary.contractNo == CONTRACT_NUMBER_INVOICE_2 }
            excludedContractSummary.excluded
    }

    def "should return max dpd null from invoices when no due invoices"() {
        given:
            def invoice10 = prepareInvoice(invoiceWithBalanceDto10(), 23, null, parse("2020-12-01"))
        and:
            def invoice11 = prepareInvoice(invoiceWithBalanceDto11(), 0, 0.00, parse("2021-12-01"))
        and:
            def invoice12 = prepareInvoice(invoiceWithBalanceDto12(), -10, 10.12, parse("2022-12-01"))
        and:
            def invoices = [invoice10, invoice11, invoice12]
        when:
            def maxDpdValue = service.findMaxDpdValue(invoices)
        then:
            maxDpdValue == null
    }

    def "should find max dpd from invoices"() {
        given:
            def invoice10 = prepareInvoice(invoiceWithBalanceDto10(), 10, -25.0, parse("2020-12-01"))
        and:
            def invoice11 = prepareInvoice(invoiceWithBalanceDto11(), 12, -75.01, parse("2021-12-01"))
        and:
            def invoice12 = prepareInvoice(invoiceWithBalanceDto12(), 14, -1000.12, parse("2022-12-01"))
        and:
            def invoices = [invoice10, invoice11, invoice12]
        when:
            def maxDpdValue = service.findMaxDpdValue(invoices)
        then:
            maxDpdValue == 12
    }

    def "should find max dpd as negative value from invoices when invoice is not due but has negative balance"() {
        given:
            def invoice10 = prepareInvoice(invoiceWithBalanceDto10(), -10, -0.01, parse("2020-12-01"))
        and:
            def invoice11 = prepareInvoice(invoiceWithBalanceDto11(), 12, 0.00, parse("2020-12-01"))
        and:
            def invoice12 = prepareInvoice(invoiceWithBalanceDto12(), -9, -100.12, parse("2020-12-01"))
        and:
            def invoices = [invoice10, invoice11, invoice12]
        when:
            def maxDpdValue = service.findMaxDpdValue(invoices)
        then:
            maxDpdValue == -9
    }

    def "should return contracts with exclusions"() {
        given:
            def contracts = [simpleAidaContractDto1(), simpleAidaContractDto2()]
        and:
            aidaContractService.findContractsByCompanyId(EXT_COMPANY_ID_1) >> contracts
        and:
            customerService.findAllContractsOutOfService(EXT_COMPANY_ID_1) >>
                    [contractOutOfServiceDto1(), contractOutOfServiceDto2(), contractOutOfServiceDto3()]
        and:
            dateProvider.getCurrentDate() >> parse("2017-07-11 11:00:00.000000")
        when:
            def results = service.findContractsWithExclusionsByCompanyId(EXT_COMPANY_ID_1)
        then:
            results[0].excluded && results[1].excluded
            results[0].exclusions.size() == 2
            results[1].exclusions.size() == 1
    }

    @Unroll
    def "should determine excluded flag from exclusions for current date [#currentDate]"() {
        expect:
            service.determineExcluded(exclusions, currentDate) == excluded
        where:
            exclusions                                               | currentDate                         | excluded
            [contractOutOfServiceDto2(), contractOutOfServiceDto3()] | parse("2017-07-09 11:00:00.000000") | false
            [contractOutOfServiceDto2(), contractOutOfServiceDto3()] | parse("2017-07-10 11:00:00.000000") | true
            [contractOutOfServiceDto3()]                             | parse("2017-07-11 00:00:00.000000") | true
            [contractOutOfServiceDto2()]                             | parse("2017-07-15 11:00:00.000000") | false
    }

    def prepareInvoice(InvoiceDto invoice, Long dpd, BigDecimal currentBalance, Date dueDate) {
        invoice.realDueDate = dueDate
        invoice.currentBalance = currentBalance
        invoice.dpd = dpd
        invoice
    }

    @Unroll
    def "should check if contract is out of service"(boolean outOfService){
        given:
            def currentDate = parse("2019-10-30")
            dateProvider.getCurrentDate() >> currentDate
            formalDebtCollectionContractOutOfServiceDao.isFormalDebtCollectionContractOutOfService(CONTRACT_NO_1, currentDate, FIRST) >> outOfService
        expect:
            service.isFormalDebtCollectionContractOutOfService(CONTRACT_NO_1, currentDate, FIRST) == outOfService
        where:
            outOfService << [true, false]
    }
}
