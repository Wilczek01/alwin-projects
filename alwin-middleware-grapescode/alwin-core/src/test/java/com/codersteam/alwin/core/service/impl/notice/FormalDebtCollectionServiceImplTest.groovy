package com.codersteam.alwin.core.service.impl.notice

import com.codersteam.aida.core.api.service.ContractService
import com.codersteam.aida.core.api.service.InvoiceService
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.common.termination.ContractTerminationState
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.core.util.DateUtils
import com.codersteam.alwin.db.dao.ContractTerminationDao
import com.codersteam.alwin.db.dao.DemandForPaymentDao
import com.codersteam.alwin.jpa.notice.DemandForPayment
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.common.demand.DemandForPaymentState.ISSUED
import static com.codersteam.alwin.core.service.impl.notice.FormalDebtCollectionServiceImpl.NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE
import static com.codersteam.alwin.testdata.ContractTerminationInitialDataTestData.contractTerminationInitialData1
import static com.codersteam.alwin.testdata.ContractTerminationInitialDataTestData.contractTerminationInitialData2
import static com.codersteam.alwin.testdata.ContractTerminationTestData.*
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.*
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationSecondSegmentA
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationSecondSegmentB
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.*
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_15
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.simpleAidaContractDto1
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.simpleAidaContractDto2
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_CONTRACT_NO_1
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoice1
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class FormalDebtCollectionServiceImplTest extends Specification {

    @Subject
    private FormalDebtCollectionServiceImpl service

    private DemandForPaymentDao demandForPaymentDao = Mock(DemandForPaymentDao)
    private ContractTerminationDao contractTerminationDao = Mock(ContractTerminationDao)
    private InvoiceService aidaInvoiceService = Mock(InvoiceService)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)
    private DateProvider dateProvider = Mock(DateProvider)
    private ContractService contractService = Mock(ContractService)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper mapper = new AlwinMapper(dateProvider, alwinProperties)

    def "setup"() {
        def aidaService = Mock(AidaService)
        aidaService.invoiceService >> aidaInvoiceService
        aidaService.contractService >> contractService
        service = Spy(FormalDebtCollectionServiceImpl, constructorArgs: [demandForPaymentDao, contractTerminationDao, aidaService, customerVerifierService,
                                                                         dateProvider, mapper]) as FormalDebtCollectionServiceImpl
    }

    def "should not generate second demand for payment when no previous demands present"() {
        given:
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.empty()
        when:
            def shouldCreateSecondDemandForPayment = service.shouldGenerateSecondDemandForPaymentForContract(INVOICE_CONTRACT_NO_1)
        then:
            !shouldCreateSecondDemandForPayment
    }

    def "should not generate second demand for payment when no previous demand is not first demand"() {
        given: 'latest demand of payment of type SECOND'
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPaymentWithCompanyName2())
        when:
            def shouldCreateSecondDemandForPayment = service.shouldGenerateSecondDemandForPaymentForContract(INVOICE_CONTRACT_NO_1)
        then:
            !shouldCreateSecondDemandForPayment
    }

    def "should not generate second demand for payment when first demand is not issued"() {
        given: 'latest demand of payment of type FIRST and state NEW'
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPaymentWithCompanyName1())
        when:
            def shouldCreateSecondDemandForPayment = service.shouldGenerateSecondDemandForPaymentForContract(INVOICE_CONTRACT_NO_1)
        then:
            !shouldCreateSecondDemandForPayment
    }

    def "should not generate second demand for payment when first demand is issued but payment time not expired"() {
        given: 'latest demand of payment of type FIRST and state ISSUED'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            dateProvider.getCurrentDateStartOfDay() >> demandForPaymentWithCompanyName1().dueDate
        when:
            def shouldCreateSecondDemandForPayment = service.shouldGenerateSecondDemandForPaymentForContract(INVOICE_CONTRACT_NO_1)
        then:
            !shouldCreateSecondDemandForPayment
    }

    def "should not generate second demand for payment when first demand is issued but initial invoice is paid"() {
        given: 'latest demand of payment of type FIRST and state ISSUED'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            dateProvider.getCurrentDateStartOfDay() >> DateUtils.datePlusDays(demandForPaymentWithCompanyName1().dueDate, 1)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        when:
            def shouldCreateSecondDemandForPayment = service.shouldGenerateSecondDemandForPaymentForContract(INVOICE_CONTRACT_NO_1)
        then:
            !shouldCreateSecondDemandForPayment
    }

    def "should generate second demand for payment when first demand is issued and initial invoice is not paid"() {
        given: 'latest demand of payment of type FIRST and state ISSUED'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            dateProvider.getCurrentDateStartOfDay() >> DateUtils.datePlusDays(demandForPaymentWithCompanyName1().dueDate, 1)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> true
        when:
            def shouldCreateSecondDemandForPayment = service.shouldGenerateSecondDemandForPaymentForContract(INVOICE_CONTRACT_NO_1)
        then:
            shouldCreateSecondDemandForPayment
    }

    def "should contract not be under active formal debt collection when no demands issued"() {
        given:
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.empty()
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            !isContractUnderActiveFormalDebtCollection
    }

    def "should contract be under active formal debt collection when last demand waiting for issue"() {
        given: 'latest demand of payment in NEW state'
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPaymentWithCompanyName1())
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            isContractUnderActiveFormalDebtCollection
    }

    def "should contract be under active formal debt collection when last demand issued but initial invoice is not paid"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> true
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            isContractUnderActiveFormalDebtCollection
    }

    def "should contract not be under active formal debt collection when last demand issued and initial invoice is paid and no termination present"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.empty()
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            !isContractUnderActiveFormalDebtCollection
    }

    def "should contract not be under active formal debt collection when last contract termination is rejected"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.REJECTED
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            !isContractUnderActiveFormalDebtCollection
    }

    def "should contract be under active formal debt collection when last contract termination is not rejected and issued"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.NEW
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            isContractUnderActiveFormalDebtCollection
    }

    def "should contract be under active formal debt collection when last contract termination issued but not paid"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.ISSUED
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        and:
            service.isInitialInvoiceOverdueAtLeast40Percent(_ as Long) >> true
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            isContractUnderActiveFormalDebtCollection
    }

    def "should contract not be under active formal debt collection when last contract termination issued and paid"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.ISSUED
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        and:
            service.isInitialInvoiceOverdueAtLeast40Percent(_ as Long) >> false
        when:
            def isContractUnderActiveFormalDebtCollection = service.isContractUnderActiveFormalDebtCollection(INVOICE_CONTRACT_NO_1)
        then:
            !isContractUnderActiveFormalDebtCollection
    }

    def "should return leo invoice id from due invoices collection by invoice number"() {
        given:
            def invoices = new HashSet([formalDebtCollectionInvoice1(), formalDebtCollectionInvoice2(), formalDebtCollectionInvoice3()])
        when:
            def leoInvoiceId = service.findExtInvoiceId(invoices, formalDebtCollectionInvoice2().invoiceNumber)
        then:
            leoInvoiceId == formalDebtCollectionInvoice2().leoId
    }

    def "should consider initial invoice as NOT paid"() {
        given:
            def demandForPayment = demandForPaymentWithCompanyName1()
        and:
            aidaInvoiceService.getInvoiceWithBalance(LEO_ID_15) >> dueInvoice1()
        and:
            customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(dueInvoice1(), demandForPaymentWithCompanyName1().type.minDuePaymentValue) >> true
        when:
            def isInvoiceNotPaid = service.isInitialInvoiceNotPaid(demandForPayment)
        then:
            isInvoiceNotPaid
    }

    def "should consider initial invoice as paid"() {
        given:
            def demandForPayment = demandForPaymentWithCompanyName1()
        and:
            aidaInvoiceService.getInvoiceWithBalance(LEO_ID_15) >> dueInvoice1()
        and:
            customerVerifierService.isInvoiceBalanceBelowMinBalanceStart(dueInvoice1(), demandForPaymentWithCompanyName1().type.minDuePaymentValue) >> false
        when:
            def isInvoiceNotPaid = service.isInitialInvoiceNotPaid(demandForPayment)
        then:
            !isInvoiceNotPaid
    }

    def "should not find conditional contract terminations to create when no due second demands for payment present"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(1) >> dueDate
        and:
            demandForPaymentDao.findIssuedSecondDemandsForPaymentWithDueDate(dueDate) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findConditionalContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should not find conditional contract terminations to create when no contracts in proper state"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(1) >> dueDate
        and:
            demandForPaymentDao.findIssuedSecondDemandsForPaymentWithDueDate(dueDate) >> [
                    prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentA(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_1, parse("2012-10-09")),
                    prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentB(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_2, parse("2012-10-09"))
            ]
        and:
            service.filterDemandsByValidContractToCreateTermination(_ as List) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findConditionalContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should not find conditional contract terminations to create when no not paid contracts"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(1) >> dueDate
        and:
            def demandForPayments = [
                    prepareDemandForPaymentWithCompanyName(demandForPaymentTypeConfigurationSecondSegmentA(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_1, parse("2012-10-09")),
                    prepareDemandForPaymentWithCompanyName(demandForPaymentTypeConfigurationSecondSegmentB(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_2, parse("2012-10-09"))
            ]
        and:
            demandForPaymentDao.findIssuedSecondDemandsForPaymentWithDueDate(dueDate) >> demandForPayments
        and:
            service.filterDemandsByValidContractToCreateTermination(demandForPayments) >> demandForPayments
        and:
            service.filterDemandsForPaymentByNotPaid(demandForPayments) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findConditionalContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should not find conditional contract terminations to create when all terminations already created"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(1) >> dueDate
        and:
            def demandForPayments = [
                    prepareDemandForPaymentWithCompanyName(demandForPaymentTypeConfigurationSecondSegmentA(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_1, parse("2012-10-09")),
                    prepareDemandForPaymentWithCompanyName(demandForPaymentTypeConfigurationSecondSegmentB(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_2, parse("2012-10-09"))
            ]
        and:
            demandForPaymentDao.findIssuedSecondDemandsForPaymentWithDueDate(dueDate) >> demandForPayments
        and:
            service.filterDemandsByValidContractToCreateTermination(demandForPayments) >> demandForPayments
        and:
            service.filterDemandsForPaymentByNotPaid(demandForPayments) >> demandForPayments
        and:
            service.filterDemandsForPaymentByExistingContractTermination(demandForPayments) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findConditionalContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should find conditional contract terminations to create"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(1) >> dueDate
        and:
            def demandForPayments = [
                    prepareDemandForPaymentWithCompanyName(demandForPaymentTypeConfigurationSecondSegmentA(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_1, parse("2012-10-09")),
                    prepareDemandForPaymentWithCompanyName(demandForPaymentTypeConfigurationSecondSegmentB(), ISSUED, DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_2, parse("2012-10-09"))
            ]
        and:
            demandForPaymentDao.findIssuedSecondDemandsForPaymentWithDueDate(dueDate) >> demandForPayments
        and:
            service.filterDemandsByValidContractToCreateTermination(demandForPayments) >> demandForPayments
        and:
            service.filterDemandsForPaymentByNotPaid(demandForPayments) >> demandForPayments
        and:
            service.filterDemandsForPaymentByExistingContractTermination(demandForPayments) >> demandForPayments
        when:
            def conditionalContractTerminationsToCreate = service.findConditionalContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.size() == 2
            conditionalContractTerminationsToCreate.each { termination ->
                assert conditionalContractTerminationsToCreate.dueDateInDemandForPayment != null
            }
    }

    def "should not find immediate contract terminations to create when no issued conditional contract terminations present"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE) >> dueDate
        and:
            contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(dueDate) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findImmediateContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should not find immediate contract terminations to create when no contracts in proper state"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE) >> dueDate
        and:
            def conditionalContractTerminations = [
                    contractTermination101(),
                    contractTermination104()
            ]
        and:
            contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(dueDate) >> conditionalContractTerminations
        and:
            service.filterTerminationsByValidContractToCreateTermination(conditionalContractTerminations) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findImmediateContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should not find immediate contract terminations to create when no not paid conditional termination found"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE) >> dueDate
        and:
            def conditionalContractTerminations = [
                    contractTermination101(),
                    contractTermination104()
            ]
        and:
            contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(dueDate) >> conditionalContractTerminations
        and:
            service.filterTerminationsByValidContractToCreateTermination(conditionalContractTerminations) >> conditionalContractTerminations
        and:
            service.filterTerminationsByNotPaid(conditionalContractTerminations) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findImmediateContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should not find immediate contract terminations to create when all immediate contract terminations already created"() {
        given:
            def dueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE) >> dueDate
        and:
            def conditionalContractTerminations = [
                    contractTermination101(),
                    contractTermination104()
            ]
        and:
            contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(dueDate) >> conditionalContractTerminations
        and:
            service.filterTerminationsByValidContractToCreateTermination(conditionalContractTerminations) >> conditionalContractTerminations
        and:
            service.filterTerminationsByNotPaid(conditionalContractTerminations) >> conditionalContractTerminations
        and:
            service.filterContractTerminationsByExistingImmediateContractTermination(conditionalContractTerminations) >> []
        when:
            def conditionalContractTerminationsToCreate = service.findImmediateContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.isEmpty()
    }

    def "should find immediate contract terminations to create"() {
        given:
            def issueOverdueDate = parse("2018-10-10")
            dateProvider.getDateStartOfDayMinusDays(NUMBER_OF_DAYS_FOR_CONDITIONAL_TERMINATION_TO_BECOME_OVERDUE) >> issueOverdueDate
        and:
            def conditionalContractTerminations = [
                    contractTermination101(),
                    contractTermination104()
            ]
        and:
            contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(issueOverdueDate) >> conditionalContractTerminations
        and:
            service.filterTerminationsByValidContractToCreateTermination(conditionalContractTerminations) >> conditionalContractTerminations
        and:
            service.filterTerminationsByNotPaid(conditionalContractTerminations) >> conditionalContractTerminations
        and:
            service.filterContractTerminationsByExistingImmediateContractTermination(conditionalContractTerminations) >> conditionalContractTerminations
        when:
            def conditionalContractTerminationsToCreate = service.findImmediateContractTerminationsToCreate()
        then:
            conditionalContractTerminationsToCreate.size() == 2
            conditionalContractTerminationsToCreate.each { termination ->
                assert conditionalContractTerminationsToCreate.dueDateInDemandForPayment != null
            }
    }

    def "should filter demand for payment contract by state valid to contract termination"() {
        given:
            def demandForPayment1 = demandForPaymentWithCompanyName1()
            def demandForPayment2 = demandForPaymentWithCompanyName2()
            def demandForPayment3 = demandForPaymentWithCompanyName3()
            def demandsForPayment = [demandForPayment1, demandForPayment2, demandForPayment3]
        and:
            contractService.findActiveContractByContractNo(demandForPayment1.contractNumber) >> simpleAidaContractDto1()
            contractService.findActiveContractByContractNo(demandForPayment2.contractNumber) >> simpleAidaContractDto2()
            contractService.findActiveContractByContractNo(demandForPayment3.contractNumber) >> simpleAidaContractDto1()
        when:
            def demandsForPaymentFilteredByContract = service.filterDemandsByValidContractToCreateTermination(demandsForPayment)
        then:
            demandsForPaymentFilteredByContract.size() == 1
            demandsForPaymentFilteredByContract[0] == demandForPayment2
    }

    def "should filter contract termination contract by state valid to contract termination"() {
        given:
            def contractTermination1 = contractTermination101()
            def contractTermination2 = contractTermination104()
            def contractTerminations = [contractTermination1, contractTermination2]
        and:
            contractService.findActiveContractByContractNo(contractTermination1.contractNumber) >> simpleAidaContractDto1()
            contractService.findActiveContractByContractNo(contractTermination2.contractNumber) >> simpleAidaContractDto2()
        when:
            def demandsForPaymentFilteredByContract = service.filterTerminationsByValidContractToCreateTermination(contractTerminations)
        then:
            demandsForPaymentFilteredByContract.size() == 1
            demandsForPaymentFilteredByContract[0] == contractTermination2
    }

    @Unroll
    def "should contract state #contractState be #validMessage for contract termination"() {
        given:
            def contract = simpleAidaContractDto1()
            contract.stateSymbol = contractState
            contractService.findActiveContractByContractNo(demandForPaymentWithCompanyName1().contractNumber) >> contract
        expect:
            service.isContractValidToCreateTermination(demandForPaymentWithCompanyName1().contractNumber) == validForContractTermination
        where:
            contractState | validForContractTermination
            1             | false
            2             | true
            3             | false
            8             | true
            9             | true
            10            | true
            11            | false
            44            | true
            106           | true
            211           | true
            212           | true
            213           | false

            validMessage = validForContractTermination ? 'valid' : 'not valid'
    }

    @Unroll
    def "invoice with grossAmount=#grossAmount and currentBalance=#currentBalance #ovedueMessage at least 40%"() {
        given:
            def invoice = dueInvoice1()
            invoice.balanceOnDocument = currentBalance
            invoice.grossAmount = grossAmount
            aidaInvoiceService.getInvoiceWithBalance(LEO_ID_15) >> invoice
        expect:
            service.isInitialInvoiceOverdueAtLeast40Percent(LEO_ID_15) == isOverdueAtLeast40Percent
        where:
            grossAmount | currentBalance | isOverdueAtLeast40Percent
            100.00      | 0.00           | false
            100.00      | 20.00          | false
            100.00      | -50.00         | true
            100.00      | -40.00         | true
            100.00      | -40.01         | true
            100.00      | -39.99         | false
            100.00      | -10.00         | false
            100.00      | -60.00         | true

            ovedueMessage = isOverdueAtLeast40Percent ? "be overdue" : "not be overdue"
    }

    def "should filter out paid demands for payment"() {
        given:
            def demandsForPayment = [
                    demandForPaymentWithCompanyName1(),
                    demandForPaymentWithCompanyName2()
            ]
        and: 'demand 1 not paid'
            def invoiceId1 = service.findExtInvoiceId(demandForPaymentWithCompanyName1().invoices, demandForPaymentWithCompanyName1().initialInvoiceNo)
            service.isInitialInvoiceOverdueAtLeast40Percent(invoiceId1) >> true
        and: 'demand 2 paid'
            def invoiceId2 = service.findExtInvoiceId(demandForPaymentWithCompanyName2().invoices, demandForPaymentWithCompanyName2().initialInvoiceNo)
            service.isInitialInvoiceOverdueAtLeast40Percent(invoiceId2) >> false
        when:
            def notPaidDemands = service.filterDemandsForPaymentByNotPaid(demandsForPayment)
        then:
            notPaidDemands.size() == 1
            assertThat(notPaidDemands[0]).isEqualToComparingFieldByFieldRecursively(demandForPaymentWithCompanyName1())
    }

    def "should filter out paid contract terminations"() {
        given:
            def contractTerminations = [
                    contractTermination101(),
                    contractTermination104()
            ]
        and: 'contract termination 101 not paid'
            def invoiceId1 = service.findExtInvoiceId(contractTermination101().getFormalDebtCollectionInvoices(),
                    contractTermination101().getInvoiceNumberInitiatingDemandForPayment())
            service.isInitialInvoiceOverdueAtLeast40Percent(invoiceId1) >> true
        and: 'contract termination 104 paid'
            def invoiceId2 = service.findExtInvoiceId(contractTermination104().getFormalDebtCollectionInvoices(),
                    contractTermination104().getInvoiceNumberInitiatingDemandForPayment())
            service.isInitialInvoiceOverdueAtLeast40Percent(invoiceId2) >> false
        when:
            def notPaidContractTerminations = service.filterTerminationsByNotPaid(contractTerminations)
        then:
            notPaidContractTerminations.size() == 1
            assertThat(notPaidContractTerminations[0]).isEqualToComparingFieldByFieldRecursively(contractTermination101())
    }

    def "should contract be invalid for termination when not found"() {
        given:
            def notExistingContractNo = "not_existing"
        and:
            contractService.findActiveContractByContractNo(notExistingContractNo) >> null
        when:
            def isContractValidToCreateTermination = service.isContractValidToCreateTermination(notExistingContractNo)
        then:
            !isContractValidToCreateTermination
    }

    def "should fill dueDateInDemandForPayment from contract terminations"() {
        given:
            def termination101 = contractTermination101()
            termination101.dueDateInDemandForPayment = parse("2012-10-12")
        and:
            def termination104 = contractTermination104()
            termination104.dueDateInDemandForPayment = parse("2012-11-10")
        and:
            def contractTerminations = [termination101, termination104]
        and:
            def initialData1 = contractTerminationInitialData1()
            initialData1.dueDateInDemandForPayment = null
            initialData1.contractNumber = termination101.contractNumber
        and:
            def initialData2 = contractTerminationInitialData2()
            initialData2.dueDateInDemandForPayment = null
            initialData2.contractNumber = termination104.contractNumber
        and:
            def contractTerminationInitialData = [initialData1, initialData2]
        when:
            def contractTerminationInitialDataWithDueDateInDemandForPayment =
                    service.fillDueDatesInDemandForPaymentFromConditionalContractTerminations(contractTerminations, contractTerminationInitialData)
        then:
            contractTerminationInitialDataWithDueDateInDemandForPayment[0].dueDateInDemandForPayment == termination101.dueDateInDemandForPayment
            contractTerminationInitialDataWithDueDateInDemandForPayment[1].dueDateInDemandForPayment == termination104.dueDateInDemandForPayment
    }

    def "should not fill dueDateInDemandForPayment from contract terminations when data is incorrect"() {
        given:
            def contractTerminations = []
        and:
            def initialData1 = contractTerminationInitialData1()
            initialData1.dueDateInDemandForPayment = null
        and:
            def contractTerminationInitialData = [initialData1]
        when:
            def contractTerminationInitialDataWithDueDateInDemandForPayment =
                    service.fillDueDatesInDemandForPaymentFromConditionalContractTerminations(contractTerminations, contractTerminationInitialData)
        then:
            contractTerminationInitialDataWithDueDateInDemandForPayment[0].dueDateInDemandForPayment == null
    }

    def "should fill dueDateInDemandForPayment from demands for payment"() {
        given:
            def demandForPayment1 = demandForPaymentWithCompanyName1()
            demandForPayment1.dueDate = parse("2012-10-12")
        and:
            def demandForPayment2 = demandForPaymentWithCompanyName2()
            demandForPayment2.dueDate = parse("2012-11-10")
        and:
            def demandsForPayment = [demandForPayment1, demandForPayment2]
        and:
            def initialData1 = contractTerminationInitialData1()
            initialData1.dueDateInDemandForPayment = null
            initialData1.contractNumber = demandForPayment1.contractNumber
        and:
            def initialData2 = contractTerminationInitialData2()
            initialData2.dueDateInDemandForPayment = null
            initialData2.contractNumber = demandForPayment2.contractNumber
        and:
            def contractTerminationInitialData = [initialData1, initialData2]
        when:
            def contractTerminationInitialDataWithDueDateInDemandForPayment =
                    service.fillDueDatesInDemandForPaymentFromSecondDemandsForPayment(demandsForPayment, contractTerminationInitialData)
        then:
            contractTerminationInitialDataWithDueDateInDemandForPayment[0].dueDateInDemandForPayment == demandForPayment1.dueDate
            contractTerminationInitialDataWithDueDateInDemandForPayment[1].dueDateInDemandForPayment == demandForPayment2.dueDate
    }

    def "should not fill dueDateInDemandForPayment from demands for payment when data is incorrect"() {
        given:
            def demandsForPayment = []
        and:
            def initialData1 = contractTerminationInitialData1()
            initialData1.dueDateInDemandForPayment = null
        and:
            def contractTerminationInitialData = [initialData1]
        when:
            def contractTerminationInitialDataWithDueDateInDemandForPayment =
                    service.fillDueDatesInDemandForPaymentFromSecondDemandsForPayment(demandsForPayment, contractTerminationInitialData)
        then:
            contractTerminationInitialDataWithDueDateInDemandForPayment[0].dueDateInDemandForPayment == null
    }

    def "should not find preceding demand for payment id when no previous demands present"() {
        given:
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.empty()
        when:
            def precedingDemandForPaymentId = service.findPrecedingDemandForPaymentId(INVOICE_CONTRACT_NO_1)
        then:
            precedingDemandForPaymentId == null
    }

    def "should not find preceding demand for payment id when no previous demand is not first demand"() {
        given: 'latest demand of payment of type SECOND'
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPaymentWithCompanyName2())
        when:
            def precedingDemandForPaymentId = service.findPrecedingDemandForPaymentId(INVOICE_CONTRACT_NO_1)
        then:
            precedingDemandForPaymentId == null
    }

    def "should not find preceding demand for payment id when first demand is not issued"() {
        given: 'latest demand of payment of type FIRST and state NEW'
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPaymentWithCompanyName1())
        when:
            def precedingDemandForPaymentId = service.findPrecedingDemandForPaymentId(INVOICE_CONTRACT_NO_1)
        then:
            precedingDemandForPaymentId == null
    }

    def "should find preceding demand for payment id when first demand is issued and initial invoice is not paid"() {
        given: 'latest demand of payment of type FIRST and state ISSUED'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            dateProvider.getCurrentDateStartOfDay() >> DateUtils.datePlusDays(demandForPaymentWithCompanyName1().dueDate, 1)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> true
        when:
            def precedingDemandForPaymentId = service.findPrecedingDemandForPaymentId(INVOICE_CONTRACT_NO_1)
        then:
            precedingDemandForPaymentId == demandForPayment.id
    }

    def "should filter demands for payment by existing contract termination"() {
        given:
            def demandsForPayment = [demandForPayment1(), demandForPayment2()]
        and:
            contractTerminationDao.findContractTerminationForPrecedingDemandForPayment(demandForPayment1().id) >> Optional.empty()
            contractTerminationDao.findContractTerminationForPrecedingDemandForPayment(demandForPayment2().id) >> Optional.of(contractTermination102())
        when:
            def demandsForPaymentWithoutContractTermination = service.filterDemandsForPaymentByExistingContractTermination(demandsForPayment)
        then:
            assertThat(demandsForPaymentWithoutContractTermination).isEqualToComparingFieldByFieldRecursively([demandForPayment1()])
    }

    def "should filter contract terminations by existing immediate contract termination"() {
        given:
            def contractTerminations = [contractTermination104(), contractTermination101()]
        and:
            contractTerminationDao.findContractTerminationForPrecedingContractTermination(contractTermination101().id) >> Optional.empty()
            contractTerminationDao.findContractTerminationForPrecedingContractTermination(contractTermination104().id) >> Optional.of(contractTermination102())
        when:
            def contractTerminationsWithoutConditionalContractTermination = service.filterContractTerminationsByExistingImmediateContractTermination(contractTerminations)
        then:
            assertThat(contractTerminationsWithoutConditionalContractTermination).isEqualToComparingFieldByFieldRecursively([contractTermination101()])
    }

    def "should not find initial invoice number when no demands issued"() {
        given:
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.empty()
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == null
    }

    def "should find initial invoice number when last demand waiting for issue"() {
        given: 'latest demand of payment in NEW state'
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPaymentWithCompanyName1())
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo
    }

    def "should find initial invoice number when last demand issued but initial invoice is not paid"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> true
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == demandForPayment.initialInvoiceNo
    }

    def "should not find initial invoice number when last demand issued and initial invoice is paid and no termination present"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.empty()
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == null
    }

    def "should not find initial invoice number when last contract termination is rejected"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.REJECTED
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == null
    }

    def "should find initial invoice number when contract is activated for last contract termination and current date is before activation date"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.CONTRACT_ACTIVATED
            contractTermination.activationDate = DateUtils.datePlusDays(contractTermination.terminationDate, 14)
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        and:
            dateProvider.getCurrentDate() >> DateUtils.datePlusDays(contractTermination.terminationDate, 6)
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == contractTermination.invoiceNumberInitiatingDemandForPayment
    }

    def "should find initial invoice number when contract is activated for last contract termination and current date is after activation date"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.CONTRACT_ACTIVATED
            contractTermination.activationDate = DateUtils.datePlusDays(contractTermination.terminationDate, 14)
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        and:
            dateProvider.getCurrentDate() >> DateUtils.datePlusDays(contractTermination.terminationDate, 16)
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == null
    }

    def "should find initial invoice number when last contract termination is not rejected, contract activated and issued"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.NEW
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == contractTermination.invoiceNumberInitiatingDemandForPayment
    }

    def "should find initial invoice number when last contract termination issued but not paid"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.ISSUED
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        and:
            service.isInitialInvoiceOverdueAtLeast40Percent(_ as Long) >> true
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == contractTermination.invoiceNumberInitiatingDemandForPayment
    }

    def "should not find initial invoice number when last contract termination issued and paid"() {
        given: 'latest demand of payment in ISSUED state'
            def demandForPayment = demandForPaymentWithCompanyName1()
            demandForPayment.state = ISSUED
            demandForPaymentDao.findLatestDemandForPayment(INVOICE_CONTRACT_NO_1) >> Optional.of(demandForPayment)
        and:
            service.isInitialInvoiceNotPaid(demandForPayment) >> false
        and:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.ISSUED
            contractTerminationDao.findLatestContractTermination(INVOICE_CONTRACT_NO_1) >> Optional.of(contractTermination)
        and:
            service.isInitialInvoiceOverdueAtLeast40Percent(_ as Long) >> false
        when:
            def activeProcessInitialInvoiceNo = service.findActiveProcessInitialInvoiceNumberByContractNumber(INVOICE_CONTRACT_NO_1)
        then:
            activeProcessInitialInvoiceNo == null
    }

    def "should find issued demand for payment issue date"() {
        given:
            def demandForPayment = demandForPayment3()
        and:
            demandForPaymentDao.findLatestDemandForPaymentForContract(demandForPayment.type.key, demandForPayment.contractNumber,
                    ISSUED) >> Optional.of(demandForPayment)
        when:
            def issueDate = service.findIssuedDemandForPaymentIssueDate(demandForPayment.type.key, demandForPayment.contractNumber)
        then:
            issueDate == demandForPayment.issueDate
    }

    def "should not find issued demand for payment issue date"() {
        given:
            def demandForPayment = demandForPayment1()
        and:
            demandForPaymentDao.findLatestDemandForPaymentForContract(demandForPayment.type.key, demandForPayment.contractNumber,
                    ISSUED) >> Optional.empty()
        when:
            def issueDate = service.findIssuedDemandForPaymentIssueDate(demandForPayment.type.key, demandForPayment.contractNumber)
        then:
            issueDate == null
    }

    def "should find issued succeeding demand for payment issue date"() {
        given:
            def demandForPayment = demandForPayment1()
        and:
            def succeedingDemandForPayment = demandForPayment2()
        and:
            demandForPaymentDao.findLatestDemandForPaymentForContract(DemandForPaymentTypeKey.SECOND, demandForPayment.contractNumber,
                    ISSUED) >> Optional.of(succeedingDemandForPayment)
        when:
            def issueDate = service.findIssuedSucceedingDemandForPaymentIssueDate(demandForPayment.type.key, demandForPayment.contractNumber)
        then:
            issueDate == succeedingDemandForPayment.issueDate
    }

    def "should not find issued succeeding demand for payment issue date for second demand for payment"() {
        given:
            def demandForPayment = demandForPayment2()
            demandForPayment.type.key = DemandForPaymentTypeKey.SECOND
        when:
            def issueDate = service.findIssuedSucceedingDemandForPaymentIssueDate(demandForPayment.type.key, demandForPayment.contractNumber)
        then:
            issueDate == null
    }

    def "should not find issued succeeding demand for payment issue date when no succeeding demand for payment"() {
        given:
            def demandForPayment = demandForPayment1()
        and:
            demandForPaymentDao.findLatestDemandForPaymentForContract(DemandForPaymentTypeKey.SECOND, demandForPayment.contractNumber, ISSUED) >> Optional.empty()
        when:
            def issueDate = service.findIssuedSucceedingDemandForPaymentIssueDate(demandForPayment.type.key, demandForPayment.contractNumber)
        then:
            issueDate == null
    }

    def "should latest demand for payment be overdue"() {
        given:
            def demandForPayment = demandForPayment3()
        and: 'now is 1 day after due date'
            dateProvider.getCurrentDateStartOfDay() >> DateUtils.datePlusDays(demandForPayment.dueDate, 1)
        and:
            demandForPaymentDao.findLatestDemandForPaymentForContract(demandForPayment.contractNumber, ISSUED) >> Optional.of(demandForPayment)
        when:
            def isOverdue = service.isLatestDemandForPaymentOverdue(demandForPayment.contractNumber)
        then:
            isOverdue
    }

    def "should latest demand for payment not be overdue"() {
        given:
            def demandForPayment = demandForPayment3()
        and: 'now is at due date'
            dateProvider.getCurrentDateStartOfDay() >> demandForPayment.dueDate
        and:
            demandForPaymentDao.findLatestDemandForPaymentForContract(demandForPayment.contractNumber, ISSUED) >> Optional.of(demandForPayment)
        when:
            def isOverdue = service.isLatestDemandForPaymentOverdue(demandForPayment.contractNumber)
        then:
            !isOverdue
    }

    def "should return null as latest demand for payment overdue when no issued demands for payment for contract"() {
        given:
            def demandForPayment = demandForPayment3()
        and: 'now is at due date'
            dateProvider.getCurrentDateStartOfDay() >> demandForPayment.dueDate
        and:
            demandForPaymentDao.findLatestDemandForPaymentForContract(demandForPayment.contractNumber, ISSUED) >> Optional.empty()
        when:
            def isOverdue = service.isLatestDemandForPaymentOverdue(demandForPayment.contractNumber)
        then:
            isOverdue == null
    }

    def "should contract termination not be pending if none found for contract"() {
        given:
            contractTerminationDao.findLatestContractTermination(CONTRACT_NUMBER_INVOICE_15) >> Optional.empty()
        when:
            def isContractTerminationPending = service.isContractTerminationPending(CONTRACT_NUMBER_INVOICE_15)
        then:
            !isContractTerminationPending
    }

    def "should contract termination not be pending if latest contract termination activated"() {
        given:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.CONTRACT_ACTIVATED
        and:
            contractTerminationDao.findLatestContractTermination(CONTRACT_NUMBER_INVOICE_15) >> Optional.of(contractTermination)
        when:
            def isContractTerminationPending = service.isContractTerminationPending(CONTRACT_NUMBER_INVOICE_15)
        then:
            !isContractTerminationPending
    }

    def "should contract termination not be pending if latest contract termination issued and paid"() {
        given:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.ISSUED
        and:
            contractTerminationDao.findLatestContractTermination(CONTRACT_NUMBER_INVOICE_15) >> Optional.of(contractTermination)
        and:
            service.isInitialInvoiceOverdueAtLeast40Percent(_ as Long) >> false
        when:
            def isContractTerminationPending = service.isContractTerminationPending(CONTRACT_NUMBER_INVOICE_15)
        then:
            !isContractTerminationPending
    }

    def "should contract termination be pending if latest contract termination issued and not paid"() {
        given:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.ISSUED
        and:
            contractTerminationDao.findLatestContractTermination(CONTRACT_NUMBER_INVOICE_15) >> Optional.of(contractTermination)
        and:
            service.isInitialInvoiceOverdueAtLeast40Percent(_ as Long) >> true
        when:
            def isContractTerminationPending = service.isContractTerminationPending(CONTRACT_NUMBER_INVOICE_15)
        then:
            isContractTerminationPending
    }

    def "should contract termination not be pending if latest contract termination rejected"() {
        given:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.REJECTED
        and:
            contractTerminationDao.findLatestContractTermination(CONTRACT_NUMBER_INVOICE_15) >> Optional.of(contractTermination)
        when:
            def isContractTerminationPending = service.isContractTerminationPending(CONTRACT_NUMBER_INVOICE_15)
        then:
            !isContractTerminationPending
    }

    def "should contract termination be pending if latest contract termination not issued"() {
        given:
            def contractTermination = contractTermination101()
            contractTermination.state = ContractTerminationState.NEW
        and:
            contractTerminationDao.findLatestContractTermination(CONTRACT_NUMBER_INVOICE_15) >> Optional.of(contractTermination)
        when:
            def isContractTerminationPending = service.isContractTerminationPending(CONTRACT_NUMBER_INVOICE_15)
        then:
            isContractTerminationPending
    }

    def "should find demands for payment excluding given"() {
        given:
            def demandForPayment = demandForPayment4()
        and:
            demandForPaymentDao.findDemandsForPaymentExcludingGiven(demandForPayment.type.key, demandForPayment.contractNumber,
                    demandForPayment.initialInvoiceNo, demandForPayment.id) >> [demandForPayment1()]

        when:
            def demandsForPayment = service.findDemandsForPaymentExcludingGiven(demandForPayment.type.key, demandForPayment.contractNumber,
                    demandForPayment.initialInvoiceNo, demandForPayment.id)
        then:
            demandsForPayment.size() == 1
            demandsForPayment[0].id == demandForPayment1().id
    }

    def "should find demands for payment of given type for contract"() {
        given:
            def demandForPayment = demandForPayment4()
        and:
            demandForPaymentDao.findDemandsForPayment(demandForPayment.type.key, demandForPayment.contractNumber,
                    demandForPayment.initialInvoiceNo) >> [demandForPayment1(), demandForPayment4()]

        when:
            def demandsForPayment = service.findDemandsForPayment(demandForPayment.type.key, demandForPayment.contractNumber,
                    demandForPayment.initialInvoiceNo,)
        then:
            demandsForPayment.size() == 2
            demandsForPayment[0].id == demandForPayment1().id
            demandsForPayment[1].id == demandForPayment4().id
    }

    def "should mark demand for payment aborted"() {
        given:
            def demandForPayment = demandForPayment4()
        and:
            demandForPaymentDao.get(demandForPayment.id) >> Optional.of(demandForPayment)
        when:
            service.markDemandForPaymentAborted(demandForPayment.id)
        then:
            1 * demandForPaymentDao.update(_ as DemandForPayment) >> { List args ->
                def abortedDemandForPayment = args[0] as DemandForPayment
                assert abortedDemandForPayment.aborted
            }
    }
}
