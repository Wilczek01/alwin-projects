package com.codersteam.alwin.core.service.impl.notice

import com.codersteam.aida.core.api.model.AidaInvoiceDto
import com.codersteam.aida.core.api.model.AidaSimpleInvoiceDto
import com.codersteam.aida.core.api.model.InvoiceTypeDto
import com.codersteam.aida.core.api.service.ContractService
import com.codersteam.aida.core.api.service.CurrencyExchangeRateService
import com.codersteam.aida.core.api.service.InvoiceService
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.common.prop.AlwinPropertyKey
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerService
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentCreatorService
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentInitialData
import com.codersteam.alwin.db.dao.DemandForPaymentTypeConfigurationDao
import com.codersteam.alwin.testdata.AlwinPropertiesTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2
import static com.codersteam.alwin.testdata.DemandForPaymentInitialDataTestData.demandForPaymentInitialData1
import static com.codersteam.alwin.testdata.DemandForPaymentInitialDataTestData.demandForPaymentInitialData2
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class CreateDemandForPaymentServiceImplTest extends Specification {

    @Subject
    private CreateDemandForPaymentService service

    private DemandForPaymentTypeConfigurationDao demandForPaymentTypeDao = Mock(DemandForPaymentTypeConfigurationDao)
    private CurrencyExchangeRateService currencyExchangeRateService = Mock(CurrencyExchangeRateService)
    private DemandForPaymentCreatorService demandForPaymentCreatorService = Mock(DemandForPaymentCreatorService)
    private InvoiceService aidaInvoiceService = Mock(InvoiceService)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)
    private DateProvider dateProvider = Mock(DateProvider)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private ContractService aidaContractService = Mock(ContractService)
    private CustomerService customerService = Mock(CustomerService)
    private com.codersteam.alwin.core.api.service.contract.ContractService contractService = Mock(com.codersteam.alwin.core.api.service.contract.ContractService)

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "setup"() {
        AidaService aidaService = Mock(AidaService)
        aidaService.getCurrencyExchangeRateService() >> currencyExchangeRateService
        aidaService.getInvoiceService() >> aidaInvoiceService
        aidaService.getContractService() >> aidaContractService
        alwinProperties.getProperty(AlwinPropertyKey.DMS_DOCUMENT_URL) >> AlwinPropertiesTestData.DMS_DOCUMENT_URL
        service = Spy(CreateDemandForPaymentService, constructorArgs: [demandForPaymentTypeDao, demandForPaymentCreatorService,
                                                                       customerVerifierService, aidaService, dateProvider,
                                                                       customerService, contractService])
    }

    def "should continue prepare demands for payments even when error occurs for one type NEW"() {
        given:
            demandForPaymentTypeDao.findAllDemandForPaymentTypes() >> [demandForPaymentTypeConfigurationFirstSegmentA(), demandForPaymentTypeConfigurationSecondSegmentA(), demandForPaymentTypeConfigurationFirstSegmentB(),
                                                                       demandForPaymentTypeConfigurationSecondSegmentB()]
        and:
            service.prepareDemandsForPaymentsForType(demandForPaymentTypeConfigurationSecondSegmentA()) >> {
                throw new RuntimeException("Not so good - error occurred")
            }
        when:
            service.prepareDemandsForPayment()
        then:
            noExceptionThrown()
            1 * service.prepareDemandsForPaymentsForType(demandForPaymentTypeConfigurationFirstSegmentA()) >> {}
            1 * service.prepareDemandsForPaymentsForType(demandForPaymentTypeConfigurationFirstSegmentB()) >> {}
            1 * service.prepareDemandsForPaymentsForType(demandForPaymentTypeConfigurationSecondSegmentB()) >> {}
    }

    def "should not prepare demands for payment of given type if no invoices found for given DPD day"() {
        given:
            def demandForPaymentType = demandForPaymentTypeConfigurationFirstSegmentA()
        and:
            def dpdStartDate = parse("2019-10-23")
            dateProvider.getDateStartOfDayMinusDays(demandForPaymentType.dpdStart) >> dpdStartDate
        and:
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(dpdStartDate, dpdStartDate) >> []
        when:
            service.prepareDemandsForPaymentsForType(demandForPaymentType)
        then:
            0 * demandForPaymentCreatorService.prepareDemandForPayment(_ as DemandForPaymentInitialData)
    }

    def "should not prepare demands for payment of given type if no due invoices for given DPD day"() {
        given:
            def demandForPaymentType = demandForPaymentTypeConfigurationFirstSegmentA()
        and:
            def dpdStartDate = parse("2019-10-23")
            dateProvider.getDateStartOfDayMinusDays(demandForPaymentType.dpdStart) >> dpdStartDate
        and:
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(dpdStartDate, dpdStartDate) >> [dueSimpleInvoice1(), dueSimpleInvoice2()]
        and: 'no document qualifying to create demand for payment'
            customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(_ as AidaSimpleInvoiceDto, _ as BigDecimal) >> false
        when:
            service.prepareDemandsForPaymentsForType(demandForPaymentType)
        then:
            0 * demandForPaymentCreatorService.prepareDemandForPayment(_ as DemandForPaymentInitialData)
    }

    def "should prepare demands for payment of given type"() {
        given:
            def demandForPaymentType = demandForPaymentTypeConfigurationFirstSegmentA()
        and:
            def dpdStartDate = parse("2019-10-23")
            dateProvider.getDateStartOfDayMinusDays(demandForPaymentType.dpdStart) >> dpdStartDate
        and:
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(dpdStartDate, dpdStartDate, _) >> [dueSimpleInvoice1(), dueSimpleInvoice2()]
        and: 'all documents qualifying to create demand for payment'
            customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(_ as AidaSimpleInvoiceDto, _ as BigDecimal) >> true
        when:
            service.prepareDemandsForPaymentsForType(demandForPaymentType)
        then:
            2 * demandForPaymentCreatorService.prepareDemandForPayment(_ as DemandForPaymentInitialData)
    }

    def "should prepare demands for payment of given type only for not excluded company"() {
        given:
            def demandForPaymentType = demandForPaymentTypeConfigurationFirstSegmentA()
        and:
            def dpdStartDate = parse("2019-10-23")
            dateProvider.getDateStartOfDayMinusDays(demandForPaymentType.dpdStart) >> dpdStartDate
        and:
            def currentDate = parse("2019-10-30")
            dateProvider.getCurrentDate() >> currentDate
        and:
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(dpdStartDate, dpdStartDate, _) >> [dueSimpleInvoice1(), dueSimpleInvoice2()]
        and: 'all documents qualifying to create demand for payment'
            customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(_ as AidaSimpleInvoiceDto, _ as BigDecimal) >> true
        and: 'customer out of service'
            customerService.isFormalDebtCollectionCustomerOutOfService(EXT_COMPANY_ID_1, currentDate, demandForPaymentType.key) >> true
            customerService.isFormalDebtCollectionCustomerOutOfService(EXT_COMPANY_ID_2, currentDate, demandForPaymentType.key) >> false
        when:
            service.prepareDemandsForPaymentsForType(demandForPaymentType)
        then:
            1 * demandForPaymentCreatorService.prepareDemandForPayment(_ as DemandForPaymentInitialData)
    }

    def "should continue preparing demands for payment of given type even if error occurs"() {
        given:
            def demandForPaymentType = demandForPaymentTypeConfigurationFirstSegmentA()
        and:
            def dpdStartDate = parse("2019-10-23")
            dateProvider.getDateStartOfDayMinusDays(demandForPaymentType.dpdStart) >> dpdStartDate
        and:
        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(dpdStartDate, dpdStartDate,_) >> [dueSimpleInvoice1(), dueSimpleInvoice2()]
        and: 'all documents qualifying to create demand for payment'
            customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(_ as AidaSimpleInvoiceDto, _ as BigDecimal) >> true
        and:
            2 * demandForPaymentCreatorService.prepareDemandForPayment(_ as DemandForPaymentInitialData) >> {
                throw new RuntimeException("ERROR")
            }
        when:
            service.prepareDemandsForPaymentsForType(demandForPaymentType)
        then:
            noExceptionThrown()
    }

    def "should transform AidaSimpleInvoiceDto into DemandForPaymentInitialData"() {
        given:
            def simpleInvoices = [dueSimpleInvoice1(), dueSimpleInvoice2()]
        and:
            service.filterMultipleContractInitialInvoices(simpleInvoices, FIRST) >> simpleInvoices
        when:
            def demandForPaymentInitialDataList = service.transformInvoicesToDemandForPaymentInitialData(demandForPaymentTypeConfigurationFirstSegmentA().getId(), simpleInvoices, FIRST)
        then:
            assertThat(demandForPaymentInitialDataList).isEqualToComparingFieldByFieldRecursively([demandForPaymentInitialData1(),
                                                                                                   demandForPaymentInitialData2()])
    }

    def "should filter multiple contract invoices"() {
        given: 'invoice 2 contract is TEST 20/43/12, for invoices 1, 3, 4 contract is TEST 12/34/56'
            def simpleInvoices = [dueSimpleInvoice1(), dueSimpleInvoice2(), dueSimpleInvoice3(), dueSimpleInvoice4()]
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice1().getId()) >> dueInvoice1()
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice3().getId()) >> dueInvoice3() // rent invoice
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice4().getId()) >> dueInvoice4()
        when:
            def initialInvoices = service.filterMultipleContractInitialInvoices(simpleInvoices, FIRST)
        then:
            assertThat(initialInvoices).isEqualToComparingFieldByFieldRecursively([dueSimpleInvoice2(), dueSimpleInvoice3()])
            0 * aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice2().getId())
    }

    def "should filter multiple contract invoices if one contract is out of service"() {
        given: 'invoice 2 contract is TEST 20/43/12, for invoices 1, 3, 4 contract is TEST 12/34/56'
            def simpleInvoices = [dueSimpleInvoice1(), dueSimpleInvoice2(), dueSimpleInvoice3(), dueSimpleInvoice4()]
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice1().getId()) >> dueInvoice1()
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice3().getId()) >> dueInvoice3() // rent invoice
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice4().getId()) >> dueInvoice4()
        and:
            def currentDate = parse("2019-10-30")
            dateProvider.getCurrentDate() >> currentDate
            contractService.isFormalDebtCollectionContractOutOfService(INVOICE_CONTRACT_NO_1, currentDate, FIRST) >> true
        when:
            def initialInvoices = service.filterMultipleContractInitialInvoices(simpleInvoices, FIRST)
        then:
            assertThat(initialInvoices).isEqualToComparingFieldByFieldRecursively([dueSimpleInvoice2()])
            0 * aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice2().getId())
    }

    def "should determine rent invoice as initial invoice"() {
        given: 'invoice 3 is rent invoice'
            def simpleInvoices = [dueSimpleInvoice1(), dueSimpleInvoice3()]
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice1().getId()) >> dueInvoice1()
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice3().getId()) >> dueInvoice3() // rent invoice
        when:
            def initialInvoice = service.determineInitialInvoice(simpleInvoices)
        then:
            initialInvoice == dueSimpleInvoice3()
    }

    def "should determine initial invoice with lowest balance when rent invoice is not present"() {
        given:
            def simpleInvoices = [dueSimpleInvoice1(), dueSimpleInvoice2(), dueSimpleInvoice3(), dueSimpleInvoice4()]
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice1().getId()) >> prepareInvoice(dueInvoice1(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-01")
        and: 'invoice 2 has lowest balance'
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice2().getId()) >> prepareInvoice(dueInvoice2(), -400.00, NOT_RENT_INVOICE_TYPE, "2019-10-12")
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice3().getId()) >> prepareInvoice(dueInvoice3(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-01")
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice4().getId()) >> prepareInvoice(dueInvoice4(), -399.99, NOT_RENT_INVOICE_TYPE, "2019-10-01")
        when:
            def initialInvoice = service.determineInitialInvoice(simpleInvoices)
        then:
            initialInvoice == dueSimpleInvoice2()
    }

    def "should determine initial invoice with earliest issue date when balance is equal when rent invoice is not present"() {
        given:
            def simpleInvoices = [dueSimpleInvoice1(), dueSimpleInvoice2(), dueSimpleInvoice3(), dueSimpleInvoice4()]
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice1().getId()) >> prepareInvoice(dueInvoice1(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-03")
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice2().getId()) >> prepareInvoice(dueInvoice2(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-02")
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice3().getId()) >> prepareInvoice(dueInvoice3(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-01")
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice4().getId()) >> prepareInvoice(dueInvoice4(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-12")
        when:
            def initialInvoice = service.determineInitialInvoice(simpleInvoices)
        then:
            initialInvoice == dueSimpleInvoice3()
    }

    def "should determine initial invoice with lowest id when balance is equal and issue date is the same when rent invoice is not present"() {
        given:
            def simpleInvoices = [dueSimpleInvoice1(), dueSimpleInvoice2(), dueSimpleInvoice3(), dueSimpleInvoice4()]
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice1().getId()) >> prepareInvoice(dueInvoice1(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-03")
        and: 'invoice 2 has lowest balance'
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice2().getId()) >> prepareInvoice(dueInvoice2(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-03")
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice3().getId()) >> prepareInvoice(dueInvoice3(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-03")
        and:
            aidaInvoiceService.getInvoiceWithBalance(dueSimpleInvoice4().getId()) >> prepareInvoice(dueInvoice4(), -200.00, NOT_RENT_INVOICE_TYPE, "2019-10-03")
        when:
            def initialInvoice = service.determineInitialInvoice(simpleInvoices)
        then:
            initialInvoice == dueSimpleInvoice1()
    }

    def prepareInvoice(AidaInvoiceDto invoice, BigDecimal balance, InvoiceTypeDto invoiceType, String issueDate) {
        invoice.balanceOnDocument = balance
        invoice.invoiceType = invoiceType
        invoice.issueDate = parse(issueDate)
        invoice
    }
}
