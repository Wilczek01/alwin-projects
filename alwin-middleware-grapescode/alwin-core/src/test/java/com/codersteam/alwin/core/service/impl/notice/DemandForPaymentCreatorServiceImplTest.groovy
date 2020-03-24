package com.codersteam.alwin.core.service.impl.notice

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto
import com.codersteam.alwin.common.demand.DemandForPaymentState
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import com.codersteam.alwin.core.api.service.issue.InvoiceService
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentInitialData
import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService
import com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResultCode
import com.codersteam.alwin.core.service.impl.customer.CustomerServiceImpl
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.*
import com.codersteam.alwin.jpa.notice.DemandForPayment
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice
import com.codersteam.alwin.testdata.CustomerTestData
import com.codersteam.alwin.testdata.ManualDemandForPaymentInitialDataTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer1
import static com.codersteam.alwin.testdata.DemandForPaymentInitialDataTestData.demandForPaymentInitialData1
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.demandForPaymentWithCompanyName1
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationFirstSegmentA
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationSecondSegmentA
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice1
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice2
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*

class DemandForPaymentCreatorServiceImplTest extends Specification {

    @Subject
    private DemandForPaymentCreatorServiceImpl service

    private DemandForPaymentTypeConfigurationDao demandForPaymentTypeDao = Mock(DemandForPaymentTypeConfigurationDao)
    private DateProvider dateProvider = Mock(DateProvider)
    private DemandForPaymentDao demandForPaymentDao = Mock(DemandForPaymentDao)
    private AlwinMapper mapper = new AlwinMapper()
    private CustomerServiceImpl customerService = Mock(CustomerServiceImpl)
    private FormalDebtCollectionService formalDebtCollectionService = Mock(FormalDebtCollectionService)
    private InvoiceService invoiceService = Mock(InvoiceService)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)

    @SuppressWarnings("GroovyAssignabilityCheck")
    def setup() {
        service = Spy(DemandForPaymentCreatorServiceImpl, constructorArgs: [demandForPaymentTypeDao, dateProvider, demandForPaymentDao,formalDebtCollectionService,
                                                                            invoiceService, customerVerifierService, mapper,customerService])
    }

    def "should fill new demand for payment for contract and store in db"() {
        given:
            def demandForPaymentTypeConfiguration = demandForPaymentTypeConfigurationFirstSegmentA()
            def demandForPaymentInitialData = demandForPaymentInitialData1()
            demandForPaymentInitialData.setPrecedingDemandForPaymentId(demandForPaymentWithCompanyName1().id)
            def dueInvoices = [formalDebtCollectionInvoice1(), formalDebtCollectionInvoice2()]
        and:
            def issueDate = parse("2018-10-10")
            dateProvider.currentDateStartOfDay >> issueDate
        and:
            def dueDate = parse("2018-10-13")
            dateProvider.getDateStartOfDayPlusDays(demandForPaymentTypeConfiguration.numberOfDaysToPay) >> dueDate
        when:
            service.prepareAndStoreNewDemandForPaymentForContract(demandForPaymentTypeConfiguration, demandForPaymentInitialData, dueInvoices, false)
        then:
            1 * demandForPaymentDao.createDemandForPayment(_ as DemandForPayment) >> { List args ->
                def demandForPayment = (DemandForPayment) args[0]
                assert demandForPayment.state == DemandForPaymentState.NEW
                assert demandForPayment.initialInvoiceNo == demandForPaymentInitialData1().initialInvoiceNo
                assert demandForPayment.extCompanyId == demandForPaymentInitialData1().extCompanyId
                assert demandForPayment.type == demandForPaymentTypeConfigurationFirstSegmentA()
                assert demandForPayment.contractNumber == demandForPaymentInitialData1().contractNo
                assert demandForPayment.invoices.containsAll(dueInvoices)
                assert demandForPayment.issueDate == issueDate
                assert demandForPayment.dueDate == dueDate
                assert demandForPayment.precedingDemandForPaymentId == demandForPaymentWithCompanyName1().id
                assert demandForPayment.createdManually == false
            }
    }

    def "should prepare demand for payment"() {
        given:
            demandForPaymentTypeDao.get(demandForPaymentInitialData1().demandForPaymentTypeId) >> Optional.of(demandForPaymentTypeConfigurationFirstSegmentA())
        and:
            def currentDate = parse("2019-10-12")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            invoiceService.getContractDueInvoices(demandForPaymentInitialData1().extCompanyId, INVOICE_CONTRACT_NO_1, currentDate) >> [dueInvoiceWithCorrections1()]
        and:
            invoiceService.getContractDueInvoices(demandForPaymentInitialData1().extCompanyId, demandForPaymentInitialData1().contractNo, currentDate) >> []
        and:
            customerService.getCustomerWithAdditionalData(demandForPaymentInitialData1().extCompanyId) >> testCustomer1()
        when:
            service.prepareDemandForPayment(demandForPaymentInitialData1())
        then:
            1 * service.prepareAndStoreNewDemandForPaymentForContract(_ as DemandForPaymentTypeConfiguration, _ as DemandForPaymentInitialData, _ as List, false)
    }

    def "should prepare demand for payment with proper leoId for invoices"() {
        given:
            demandForPaymentTypeDao.get(demandForPaymentInitialData1().demandForPaymentTypeId) >> Optional.of(demandForPaymentTypeConfigurationFirstSegmentA())
        and:
            def currentDate = parse("2019-10-12")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            invoiceService.getContractDueInvoices(demandForPaymentInitialData1().extCompanyId, INVOICE_CONTRACT_NO_1, currentDate) >> [dueInvoiceWithCorrections1()]
        and:
        customerService.getCustomerWithAdditionalData(demandForPaymentInitialData1().extCompanyId) >> testCustomer1()
        when:
            service.prepareDemandForPayment(demandForPaymentInitialData1())
        then:
            1 * service.prepareAndStoreNewDemandForPaymentForContract(_ as DemandForPaymentTypeConfiguration, _ as DemandForPaymentInitialData, _ as List, false) >> {
                List args ->
                    def invoices = (List<FormalDebtCollectionInvoice>) args[2]
                    // only one invoice (proper contract) is passed to create demand for payment
                    assert invoices.size() == 1
                    assert invoices[0].leoId == dueInvoiceWithCorrections1().id
            }
    }

    def "should not prepare demand for payment if company segment don't match"() {
        given: 'demand for payment type for segment A'
            demandForPaymentTypeDao.get(demandForPaymentInitialData1().demandForPaymentTypeId) >> Optional.of(demandForPaymentTypeConfigurationFirstSegmentA())
        and:
            def currentDate = parse("2019-10-12")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and: 'customer from segment B'
        customerService.getCustomerWithAdditionalData(demandForPaymentInitialData1().extCompanyId) >> CustomerTestData.testCustomer4()
        when:
            service.prepareDemandForPayment(demandForPaymentInitialData1())
        then:
            0 * service.prepareAndStoreNewDemandForPaymentForContract(_ as DemandForPaymentTypeConfiguration, _ as DemandForPaymentInitialData, _ as List, false)
    }

    def "should not create first demand for payment if formal debt collection pending for given contract"() {
        given: 'first demand for payment type'
            demandForPaymentTypeDao.get(demandForPaymentInitialData1().demandForPaymentTypeId) >> Optional.of(demandForPaymentTypeConfigurationFirstSegmentA())
        and:
            customerService.getCustomerWithAdditionalData(demandForPaymentInitialData1().extCompanyId) >> testCustomer1()
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(demandForPaymentInitialData1().contractNo) >> true
        when:
            service.prepareDemandForPayment(demandForPaymentInitialData1())
        then:
            0 * service.prepareAndStoreNewDemandForPaymentForContract(_ as DemandForPaymentTypeConfiguration, _ as DemandForPaymentInitialData, _ as List, false)
    }

    def "should not create second demand for payment if no first demand issued"() {
        given: 'second demand for payment type'
            demandForPaymentTypeDao.get(demandForPaymentInitialData1().demandForPaymentTypeId) >> Optional.of(demandForPaymentTypeConfigurationSecondSegmentA())
        and:
            customerService.getCustomerWithAdditionalData(demandForPaymentInitialData1().extCompanyId) >> testCustomer1()
        and:
            formalDebtCollectionService.shouldGenerateSecondDemandForPaymentForContract(demandForPaymentInitialData1().contractNo) >> false
        when:
            service.prepareDemandForPayment(demandForPaymentInitialData1())
        then:
            0 * service.prepareAndStoreNewDemandForPaymentForContract(_ as DemandForPaymentTypeConfiguration, _ as DemandForPaymentInitialData, _ as List, false)
    }

    def "should create manual demand for payment"() {
        given:
            def initialData = ManualDemandForPaymentInitialDataTestData.manualDemandForPaymentInitialData1()
        and:
            def customer = testCustomer1()
            customerService.getCustomerWithAdditionalData(initialData.extCompanyId) >> customer
        and:
            def demandForPaymentTypeConfiguration = demandForPaymentTypeConfigurationFirstSegmentA()
            demandForPaymentTypeDao.findDemandForPaymentTypeConfigurationByKeyAndSegment(initialData.typeKey, customer.company.segment) >> demandForPaymentTypeConfiguration
        and:
            def currentDate = parse("2019-10-12")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            def dueDate = parse("2018-10-13")
            dateProvider.getDateStartOfDayPlusDays(demandForPaymentTypeConfiguration.numberOfDaysToPay) >> dueDate
        and:
            invoiceService.getContractDueInvoices(initialData.extCompanyId, initialData.contractNo, currentDate) >> [dueInvoiceWithCorrections1()]
        when:
            def manualCreationResult = service.prepareManualDemandForPayment(initialData)
        then:
            manualCreationResult.resultCode == ManualPrepareDemandForPaymentResultCode.SUCCESSFUL
        and:
            1 * demandForPaymentDao.createDemandForPayment(_ as DemandForPayment) >> { List args ->
                def demandForPayment = (DemandForPayment) args[0]
                assert demandForPayment.state == DemandForPaymentState.NEW
                assert demandForPayment.extCompanyId == initialData.extCompanyId
                assert demandForPayment.type == demandForPaymentTypeConfiguration
                assert demandForPayment.contractNumber == initialData.contractNo
                assert demandForPayment.issueDate == currentDate
                assert demandForPayment.dueDate == dueDate
                assert demandForPayment.initialInvoiceNo == dueInvoiceWithCorrections1().number
                assert demandForPayment.createdManually == true
            }
    }

    def "should not create manual demand for payment when no due invoices present"() {
        given:
            def initialData = ManualDemandForPaymentInitialDataTestData.manualDemandForPaymentInitialData1()
        and:
            def customer = testCustomer1()
            customerService.getCustomerWithAdditionalData(initialData.extCompanyId) >> customer
        and:
            def demandForPaymentTypeConfiguration = demandForPaymentTypeConfigurationFirstSegmentA()
            demandForPaymentTypeDao.findDemandForPaymentTypeConfigurationByKeyAndSegment(initialData.typeKey, customer.company.segment) >> demandForPaymentTypeConfiguration
        and:
            def currentDate = parse("2019-10-12")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            def dueDate = parse("2018-10-13")
            dateProvider.getDateStartOfDayPlusDays(demandForPaymentTypeConfiguration.numberOfDaysToPay) >> dueDate
        and:
            invoiceService.getContractDueInvoices(initialData.extCompanyId, initialData.contractNo, currentDate) >> []
        when:
            def manualCreationResult = service.prepareManualDemandForPayment(initialData)
        then:
            manualCreationResult.resultCode == ManualPrepareDemandForPaymentResultCode.NO_DUE_INVOICES_FOR_CONTRACT
            0 * demandForPaymentDao.createDemandForPayment(_ as DemandForPayment)
    }

    def "should find initial invoice number for manual creation when active process pending"() {
        given:
            formalDebtCollectionService.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1) >> dueInvoiceWithCorrections1().number
        when:
            def initialInvoiceNo = service.findInitialInvoiceNumberForManualCreation(INVOICE_CONTRACT_NO_1, [dueInvoiceWithCorrections1()])
        then:
            initialInvoiceNo == dueInvoiceWithCorrections1().number
    }

    def "should determine initial invoice for manual creation when no active process pending and no invoice with balance < -100"() {
        given:
            def contractDueInvoices = [
                    prepareInvoice(dueInvoiceWithCorrections1(), -11.30, parse("2018-10-10")),
                    prepareInvoice(dueInvoiceWithCorrections2(), -9.30, parse("2018-10-09")),
                    prepareInvoice(dueInvoiceWithCorrections3(), -31.30, parse("2017-12-31")),
                    prepareInvoice(dueInvoiceWithCorrections4(), -99.30, parse("2018-03-10")),
            ]
        and:
            customerVerifierService.isBalanceEnoughToCreateIssue(_ as BigDecimal, _ as Currency, _ as BigDecimal, _ as BigDecimal) >> false
        when:
            def initialInvoiceNo = service.determineInitialInvoiceForManualCreationWhenNoActiveProcessPending(contractDueInvoices)
        then:
            initialInvoiceNo == dueInvoiceWithCorrections3().number
    }

    def "should determine initial invoice for manual creation when no active process pending and one invoice with balance < -100"() {
        given:
            def contractDueInvoices = [
                    prepareInvoice(dueInvoiceWithCorrections1(), -11.30, parse("2018-10-10")),
                    prepareInvoice(dueInvoiceWithCorrections2(), -100.01, parse("2018-10-09")),
                    prepareInvoice(dueInvoiceWithCorrections3(), -31.30, parse("2017-12-31")),
                    prepareInvoice(dueInvoiceWithCorrections4(), -99.30, parse("2018-03-10")),
            ]
        when:
            def initialInvoiceNo = service.determineInitialInvoiceForManualCreationWhenNoActiveProcessPending(contractDueInvoices)
        then:
            initialInvoiceNo == dueInvoiceWithCorrections2().number
        and:
            4 * customerVerifierService.isBalanceEnoughToCreateIssue(_, _, _, _) >>> [false, true, false, false]
    }

    def "should determine initial invoice for manual creation when no active process pending and all invoices with balance < -100"() {
        given:
            def contractDueInvoices = [
                    prepareInvoice(dueInvoiceWithCorrections1(), -111.30, parse("2018-10-10")),
                    prepareInvoice(dueInvoiceWithCorrections2(), -100.01, parse("2018-10-09")),
                    prepareInvoice(dueInvoiceWithCorrections3(), -311.30, parse("2017-12-31")),
                    prepareInvoice(dueInvoiceWithCorrections4(), -919.30, parse("2018-03-10")),
            ]
        and:
            customerVerifierService.isBalanceEnoughToCreateIssue(_ as BigDecimal, _ as Currency, _ as BigDecimal, _ as BigDecimal) >> true
        when:
            def initialInvoiceNo = service.determineInitialInvoiceForManualCreationWhenNoActiveProcessPending(contractDueInvoices)
        then:
            initialInvoiceNo == dueInvoiceWithCorrections3().number
    }

    def "should not prepare manual demand for payment when contract termination pending"() {
        given:
            def initialData = ManualDemandForPaymentInitialDataTestData.manualDemandForPaymentInitialData1()
        and:
            def customer = testCustomer1()
            customerService.getCustomerWithAdditionalData(initialData.extCompanyId) >> customer
        and:
            def demandForPaymentTypeConfiguration = demandForPaymentTypeConfigurationFirstSegmentA()
            demandForPaymentTypeDao.findDemandForPaymentTypeConfigurationByKeyAndSegment(initialData.typeKey, customer.company.segment) >> demandForPaymentTypeConfiguration
        and:
            def currentDate = parse("2019-10-12")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            def dueDate = parse("2018-10-13")
            dateProvider.getDateStartOfDayPlusDays(demandForPaymentTypeConfiguration.numberOfDaysToPay) >> dueDate
        and:
            invoiceService.getContractDueInvoices(initialData.extCompanyId, initialData.contractNo, currentDate) >> [dueInvoiceWithCorrections1()]
        and:
            formalDebtCollectionService.isContractTerminationPending(initialData.contractNo) >> true
        when:
            def manualCreationResult = service.prepareManualDemandForPayment(initialData)
        then:
            manualCreationResult.resultCode == ManualPrepareDemandForPaymentResultCode.CONTRACT_ALREADY_IN_TERMINATION_STAGE
            0 * demandForPaymentDao.createDemandForPayment(_ as DemandForPayment)
    }

    def prepareInvoice(AidaInvoiceWithCorrectionsDto invoice, BigDecimal invoiceBalance, Date realDueDate) {
        invoice.balanceOnDocument = invoiceBalance
        invoice.realDueDate = realDueDate
        invoice
    }
}
