package com.codersteam.alwin.core.service.impl.notice

import com.codersteam.aida.core.api.model.AidaInvoiceDto
import com.codersteam.aida.core.api.model.AidaSimpleInvoiceDto
import com.codersteam.aida.core.api.model.InvoiceTypeDto
import com.codersteam.aida.core.api.service.ContractService
import com.codersteam.aida.core.api.service.CurrencyExchangeRateService
import com.codersteam.aida.core.api.service.InvoiceService
import com.codersteam.alwin.common.demand.DemandForPaymentState
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.common.prop.AlwinPropertyKey
import com.codersteam.alwin.core.api.model.termination.DemandForPaymentStatusChangeDto
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerService
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import com.codersteam.alwin.core.api.service.notice.*
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.DemandForPaymentDao
import com.codersteam.alwin.db.dao.DemandForPaymentTypeConfigurationDao
import com.codersteam.alwin.jpa.notice.DemandForPayment
import com.codersteam.alwin.testdata.AlwinPropertiesTestData
import com.codersteam.alwin.testdata.OperatorTestData
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.common.demand.DemandForPaymentState.CANCELED
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2
import static com.codersteam.alwin.testdata.DemandForPaymentInitialDataTestData.demandForPaymentInitialData1
import static com.codersteam.alwin.testdata.DemandForPaymentInitialDataTestData.demandForPaymentInitialData2
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.*
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.*
import static com.codersteam.alwin.testdata.InvoiceTestData.*
import static com.codersteam.alwin.testdata.ManualDemandForPaymentInitialDataTestData.manualDemandForPaymentInitialData1
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.NOT_EXISTING_CONTRACT_NO
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR
import static com.codersteam.alwin.testdata.criteria.DemandForPaymentSearchCriteriaTestData.automaticallyCreatedDemandForPaymentSearchCriteriaByContractNo
import static com.codersteam.alwin.testdata.criteria.DemandForPaymentSearchCriteriaTestData.manualDemandForPaymentSearchCriteriaByState
import static java.util.Arrays.asList
import static java.util.Collections.emptyList
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class DemandForPaymentServiceImplTest extends Specification {

    @Subject
    private DemandForPaymentServiceImpl service

    private DemandForPaymentTypeConfigurationDao demandForPaymentTypeDao = Mock(DemandForPaymentTypeConfigurationDao)
    private DemandForPaymentDao demandForPaymentDao = Mock(DemandForPaymentDao)
    private CurrencyExchangeRateService currencyExchangeRateService = Mock(CurrencyExchangeRateService)
    private DemandForPaymentCreatorService demandForPaymentCreatorService = Mock(DemandForPaymentCreatorService)
    private InvoiceService aidaInvoiceService = Mock(InvoiceService)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)
    private DateProvider dateProvider = Mock(DateProvider)
    private ProcessDemandForPaymentService processDemandForPaymentService = Mock(ProcessDemandForPaymentService)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper alwinMapper = new AlwinMapper(dateProvider, alwinProperties)
    private ContractService aidaContractService = Mock(ContractService)
    private ManualDemandForPaymentMessageService manualDemandForPaymentMessageService = Mock(ManualDemandForPaymentMessageService)
    private CustomerService customerService = Mock(CustomerService)
    private com.codersteam.alwin.core.api.service.contract.ContractService contractService = Mock(com.codersteam.alwin.core.api.service.contract.ContractService)

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "setup"() {
        AidaService aidaService = Mock(AidaService)
        aidaService.getCurrencyExchangeRateService() >> currencyExchangeRateService
        aidaService.getInvoiceService() >> aidaInvoiceService
        aidaService.getContractService() >> aidaContractService
        alwinProperties.getProperty(AlwinPropertyKey.DMS_DOCUMENT_URL) >> AlwinPropertiesTestData.DMS_DOCUMENT_URL
        service = Spy(DemandForPaymentServiceImpl, constructorArgs: [demandForPaymentDao, demandForPaymentCreatorService,
                                                                     aidaService, dateProvider, alwinMapper,
                                                                     processDemandForPaymentService, manualDemandForPaymentMessageService])
    }

    def "should map empty demand for payment"() {
        given:
            demandForPaymentDao.get(DEMAND_FOR_PAYMENT_ID_1) >> Optional.empty()
        when:
            def demand = service.find(DEMAND_FOR_PAYMENT_ID_1)
        then:
            !demand.isPresent()
    }

    def "should map demand for payment"() {
        given:
            demandForPaymentDao.get(DEMAND_FOR_PAYMENT_ID_1) >> Optional.of(new DemandForPayment())
        when:
            def demand = service.find(DEMAND_FOR_PAYMENT_ID_1)
        then:
            demand.isPresent()
    }

    def "should return filtered page of demand for payments"() {
        given:
            def criteria = automaticallyCreatedDemandForPaymentSearchCriteriaByContractNo(0, 3, CONTRACT_NUMBER_INVOICE_15)

        and:
            def currentDate = parse("2017-07-11 11:23:33.000000")
            dateProvider.getCurrentDateStartOfDay() >> currentDate

        and:
            demandForPaymentDao.findAllDemandsForPayment(criteria, new LinkedHashMap<>()) >> demandsForPaymentFilteredByContractNo()

        and:
            demandForPaymentDao.findAllDemandsForPaymentCount(criteria) >> 2

        when:
            def demandsForPaymentPage = service.findAllDemandsForPayment(criteria, new LinkedHashMap<>())
        then:
            assertThat(demandsForPaymentPage)
                    .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "values.dueDate", "values.issueDate", "values.invoices.realDueDate",
                    "values.invoices.corrections.dueDate").isEqualToComparingFieldByFieldRecursively(firstPageOfDemandsForPaymentsFilteredByContractNo())
        and: 'should not add messages for automatically created demands for payment'
            0 * manualDemandForPaymentMessageService.determineManualDemandForPaymentMessages(_ as Long)
    }

    def "should return filtered list of demand for payments"() {
        given:
            def criteria = automaticallyCreatedDemandForPaymentSearchCriteriaByContractNo(0, 3, CONTRACT_NUMBER_INVOICE_15)

        and:
            def currentDate = parse("2017-07-11 11:23:33.000000")
            dateProvider.getCurrentDateStartOfDay() >> currentDate

        and:
            demandForPaymentDao.findAllDemandsForPayment(criteria, new LinkedHashMap<>()) >> demandsForPaymentFilteredByContractNo()

        and:
            demandForPaymentDao.findAllDemandsForPaymentCount(criteria) >> 2

        when:
            def demandsForPayment = service.findDemandsForPayment(criteria, new LinkedHashMap<>())
        then:
            assertThat(demandsForPayment)
                    .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "values.dueDate", "values.issueDate", "values.invoices.realDueDate",
                    "values.invoices.corrections.dueDate").isEqualToComparingFieldByFieldRecursively(demandsForPaymentFilteredByContractNoDto())
        and: 'should not add messages for automatically created demands for payment'
            0 * manualDemandForPaymentMessageService.determineManualDemandForPaymentMessages(_ as Long)
    }

    def "should find not existing demands for payment ids"() {
        given:
            def allIds = asList(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ID_2, DEMAND_FOR_PAYMENT_ID_3)

        and:
            demandForPaymentDao.findExistingDemandsForPayment(allIds) >> asList(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ID_2)

        when:
            def result = service.findNotExistingDemandsForPayment(allIds)

        then:
            result.size() == 1

        and:
            result.get(0) == DEMAND_FOR_PAYMENT_ID_3
    }

    @Unroll
    def "should not find not existing demands for empty payment ids #demandIds"() {
        when:
            def result = service.findNotExistingDemandsForPayment(demandIds)
        then:
            result.isEmpty()
        where:
            demandIds << [null, []]
    }

    def "should reject selected demands for payment ids"() {
        given:
            def operatorId = OperatorTestData.OPERATOR_ID_1
            def currentDate = parse("2018-09-21")
            def rejectedReason = "Rejected reason"
        and:
            dateProvider.getCurrentDate() >> currentDate
        when:
            service.rejectDemandsForPayment(asList(demandForPaymentDto1(), demandForPaymentDto4()), operatorId, rejectedReason)
        then:
            1 * demandForPaymentDao.rejectDemandsForPayment(_ as List, _ as Long, _ as Date, _ as String) >> { args ->
                def ids = args[0] as List
                def stateChangeOperatorId = args[1] as Long
                def stateChangeDate = args[2] as Date
                def stateChangeReason = args[3] as String
                assert ids.size() == 2
                assert ids.get(0) == DEMAND_FOR_PAYMENT_ID_1
                assert ids.get(1) == DEMAND_FOR_PAYMENT_ID_4
                assert stateChangeOperatorId == operatorId
                assert stateChangeDate == currentDate
                assert stateChangeReason == rejectedReason
            }
    }

    def "should not delete not selected demands for payment ids"(List ids) {
        when:
            service.rejectDemandsForPayment(ids, OperatorTestData.OPERATOR_ID_1, null)
        then:
            0 * demandForPaymentDao.rejectDemandsForPayment(_ as List, _ as Long, _ as Date, _ as String)
        where:
            ids << [null, emptyList()]
    }

    def "should process selected demands for payment ids"() {
        when:
            service.processDemandsForPayment(asList(demandForPaymentDto1(), demandForPaymentDto4()), "Jan Kowalski")
        then:
            1 * demandForPaymentDao.processDemandsForPayment(_ as List) >> { args ->
                def ids = args[0] as List
                assert ids.size() == 2
                assert ids.get(0) == DEMAND_FOR_PAYMENT_ID_1
                assert ids.get(1) == DEMAND_FOR_PAYMENT_ID_4
            }
    }

    def "should not process not selected demands for payment ids"(List ids) {
        when:
            service.processDemandsForPayment(ids, "Jan Kowalski")
        then:
            0 * demandForPaymentDao.processDemandsForPayment(_ as List)
        where:
            ids << [null, emptyList()]
    }

    def "should find issued demands for payment"() {
        given:
            def demandIdsToCheck = [1L, 2L]
        and:
            def issuedDemandForPaymentIds = [1L]
        when:
            def foundIssuedDemandForPaymentIds = service.findIssuedDemandsForPayment(demandIdsToCheck)
        then:
            1 * demandForPaymentDao.findIssuedOrProcessingDemandsForPayment(demandIdsToCheck) >> issuedDemandForPaymentIds
        and:
            foundIssuedDemandForPaymentIds == issuedDemandForPaymentIds
    }

    @Unroll
    def "should not find issued demands for payment for empty demand for payment ids #demandIds"() {
        when:
            def foundIssuedDemandForPaymentIds = service.findIssuedDemandsForPayment(demandIds)
        then:
            foundIssuedDemandForPaymentIds.isEmpty()
        where:
            demandIds << [null, []]
    }

    def "should prepare manual demands for payment"() {
        given:
            def contractNumbers = [CONTRACT_NUMBER_INVOICE_1, CONTRACT_NUMBER_INVOICE_2]
        when:
            def manualCreationResults = service.createDemandsForPaymentManually(FIRST, contractNumbers)
        then:
            manualCreationResults.size() == 2
        and:
            2 * service.createDemandForPaymentManually(_, _) >> { args ->
                def typeKey = args[0] as DemandForPaymentTypeKey
                assert typeKey == FIRST
                def contractNo = args[1] as String
                assert contractNumbers.contains(contractNo)
            }
    }

    def "should not prepare manual demands for payment when contracts not provided"() {
        given:
            def contractNumbers = []
        when:
            service.createDemandsForPaymentManually(FIRST, contractNumbers)
        then:
            0 * service.createDemandForPaymentManually(_, _)
    }

    def "should prepare manual demands for payment even when error occurs"() {
        given:
            def contractNumbers = [CONTRACT_NUMBER_INVOICE_1, CONTRACT_NUMBER_INVOICE_2]
        and:
            2 * service.createDemandForPaymentManually(_, _) >> { throw new RuntimeException("ERROR") }
        when:
            service.createDemandsForPaymentManually(FIRST, contractNumbers)
        then:
            noExceptionThrown()
    }

    def "should prepare manual demand for payment for contract"() {
        given:
            def initialData = manualDemandForPaymentInitialData1()
        and:
            aidaContractService.findCompanyIdByContractNo(initialData.contractNo) >> initialData.extCompanyId
        when:
            def manualCreationResult = service.createDemandForPaymentManually(initialData.typeKey, initialData.contractNo)
        then:
            manualCreationResult.resultCode == ManualPrepareDemandForPaymentResultCode.SUCCESSFUL
        and:
            1 * demandForPaymentCreatorService.prepareManualDemandForPayment(_ as ManualDemandForPaymentInitialData) >> { args ->
                def preparedInitialData = args[0] as ManualDemandForPaymentInitialData
                assertThat(preparedInitialData).isEqualToComparingFieldByFieldRecursively(initialData)
                ManualPrepareDemandForPaymentResult.successful()
            }
    }

    def "should return proper information when contract not found for manual demand for payment creation"() {
        given:
            aidaContractService.findCompanyIdByContractNo(NOT_EXISTING_CONTRACT_NO) >> null
        when:
            def result = service.createDemandForPaymentManually(FIRST, NOT_EXISTING_CONTRACT_NO)
        then:
            result.resultCode == ManualPrepareDemandForPaymentResultCode.CONTRACT_NOT_FOUND
    }

    def "should add messages for operator for manually created demands for payment"() {
        given:
            def criteria = manualDemandForPaymentSearchCriteriaByState(0, 3, DemandForPaymentState.NEW)

        and:
            demandForPaymentDao.findAllDemandsForPayment(criteria, new LinkedHashMap<>()) >> newManualDemandsForPayment()

        when:
            service.findAllDemandsForPayment(criteria, new LinkedHashMap<>())

        then:
            2 * manualDemandForPaymentMessageService.determineManualDemandForPaymentMessages(_ as Long)
    }


    def "should update contract termination state"() {
        given:
            demandForPaymentDao.get(1L) >> Optional.of(demandForPaymentWithCompanyName3())
            def stateChangeDate = new Date()
            def stateChangeOperatorId = OPERATOR_ID_1
            def stateChangeReason = "state change reason"
        and:
            def demand = new DemandForPaymentStatusChangeDto()
            demand.stateChangeDate = stateChangeDate
            demand.stateChangeOperatorId = stateChangeOperatorId
            demand.stateChangeReason = stateChangeReason
        when:
            service.updateDemandForPaymentState(1L, demand, DemandForPaymentState.CANCELED)
        then:
            1 * demandForPaymentDao.update(_ as DemandForPayment) >> { List arguments ->
                DemandForPayment demandForPayment = arguments[0] as DemandForPayment
                assert demandForPayment.id == DEMAND_FOR_PAYMENT_ID_3
                assert demandForPayment.state == CANCELED
                assert demandForPayment.stateChangeDate == stateChangeDate
                assert demandForPayment.stateChangeOperatorId == stateChangeOperatorId
                assert demandForPayment.stateChangeReason == stateChangeReason
            }
    }

    def prepareInvoice(AidaInvoiceDto invoice, BigDecimal balance, InvoiceTypeDto invoiceType, String issueDate) {
        invoice.balanceOnDocument = balance
        invoice.invoiceType = invoiceType
        invoice.issueDate = parse(issueDate)
        invoice
    }
}
