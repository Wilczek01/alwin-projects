package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.aida.core.api.service.InvoiceService
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerService
import com.codersteam.alwin.core.api.service.issue.IssueService
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.core.api.model.currency.Currency.*
import static com.codersteam.alwin.testdata.CompanyTestData.*
import static com.codersteam.alwin.testdata.CustomerTestData.*
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.MIN_BALANCE_START_1
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*

class CustomerVerifierServiceImplTest extends Specification {

    @Subject
    private CustomerVerifierServiceImpl service

    private CustomerService customerService = Mock(CustomerService)
    private IssueService issueService = Mock(IssueService)
    private DateProvider dateProvider = Mock(DateProvider)
    private AidaService aidaService = Mock(AidaService)
    private InvoiceService invoiceService = Mock(InvoiceService)

    def "setup"() {
        aidaService.getInvoiceService() >> invoiceService
        service = Spy(CustomerVerifierServiceImpl, constructorArgs: [customerService, issueService, dateProvider, aidaService])
    }

    @Unroll
    def "should filter companies out of service"() {
        given:
        customerService.findCustomerByExtId(EXT_COMPANY_ID_1) >> Optional.of(testCustomerDto1())
        customerService.findCustomerByExtId(EXT_COMPANY_ID_2) >> Optional.of(testCustomerDto2())
        customerService.findCustomerByExtId(EXT_COMPANY_ID_3) >> Optional.of(testCustomerDto3())

        and:
        customerService.isCustomerOutOfService(COMPANY_ID_1, _) >> false
        customerService.isCustomerOutOfService(COMPANY_ID_2, _) >> true
        customerService.isCustomerOutOfService(COMPANY_ID_3, _) >> true

        expect:
        service.isCompanyExcludedFromDebtCollection(companyId) == result

        where:
        companyId        | result
        EXT_COMPANY_ID_1 | false
        EXT_COMPANY_ID_2 | true
        EXT_COMPANY_ID_3 | true
    }

    def "should filter companies for issue creation"() {
        given:
        1 * service.hasNonExcludedInvoices(_, _) >> false
        0 * issueService.doesCompanyHaveActiveIssue(_) >> false
        0 * service.isCompanyExcludedFromDebtCollection(_) >> false

        expect:
        !service.filterCustomersForIssueCreation(COMPANY_ID_1, [])
    }

    @Unroll
    def "should filter customers by invoices excluded due to contract out of service"() {
        given:
        customerService.findActiveContractOutOfServiceNumbers(COMPANY_ID_1) >> [INVOICE_CONTRACT_NUMBER_1, INVOICE_CONTRACT_NUMBER_3]
        customerService.findActiveContractOutOfServiceNumbers(COMPANY_ID_2) >> [INVOICE_CONTRACT_NUMBER_3]
        customerService.findActiveContractOutOfServiceNumbers(COMPANY_ID_3) >> [INVOICE_CONTRACT_NUMBER_3]

        expect:
        service.hasNonExcludedInvoices(companyId, companyInvoices) == result

        where:
        companyId    | companyInvoices                                                               | result
        COMPANY_ID_1 | [dueSimpleInvoiceWithBalanceLimitReached(), dueSimpleInvoiceWithNoDebt()]     | false
        COMPANY_ID_2 | [dueSimpleInvoiceWithNoDebt(), dueSimpleInvoiceWIthBalanceLimitAtThreshold()] | true
        COMPANY_ID_3 | [dueSimpleInvoiceWithNoDebt()]                                                | false
    }

    @Unroll
    def "should check if invoice balance [#balanceOnDocument] in currency [#currancy]  with exchange rate [#exchangeRate] is enough to create issue "() {
        given:
        def service = new CustomerVerifierServiceImpl(null, null, null, aidaService)

        expect:
        service.isBalanceEnoughToCreateIssue(balanceOnDocument, currancy, exchangeRate, MIN_BALANCE_START_1) == expected

        where:
        balanceOnDocument | currancy    | exchangeRate | expected
        -100.0G           | PLN         | 1.0G         | false
        -100.1G           | PLN         | 1.0G         | true
        -25.0G            | EUR         | 4.0G         | false
        -25.1G            | EUR         | 4.0G         | true
        -1000.0G          | UNSUPPORTED | 0.1G         | false
        -1100.0G          | UNSUPPORTED | 0.1G         | true

    }
}
