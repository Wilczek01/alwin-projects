package com.codersteam.alwin.core.service.impl.notice


import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerService
import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService
import com.codersteam.alwin.db.dao.DemandForPaymentDao
import com.codersteam.alwin.testdata.TestDateUtils
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.DemandForPaymentTestData.demandForPayment7
import static com.codersteam.alwin.testdata.FormalDebtCollectionContractOutOfServiceTestData.contractOutOfServiceDto1
import static com.codersteam.alwin.testdata.FormalDebtCollectionCustomerOutOfServiceTestData.customerOutOfService1
import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class ManualDemandForPaymentMessageServiceImplTest extends Specification {

    @Subject
    private ManualDemandForPaymentMessageServiceImpl service

    private FormalDebtCollectionService formalDebtCollectionService = Mock(FormalDebtCollectionService)
    private DemandForPaymentDao demandForPaymentDao = Mock(DemandForPaymentDao)
    private CustomerService customerService = Mock(CustomerService)
    private DateProvider dateProvider = Mock(DateProvider)

    def setup() {
        service = Spy(ManualDemandForPaymentMessageServiceImpl,
                constructorArgs: [formalDebtCollectionService, demandForPaymentDao, customerService, dateProvider]) as ManualDemandForPaymentMessageServiceImpl
    }

    def "should not prepare messages for manual demand for payment when no demand with given id found"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.empty()
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.isEmpty()
    }

    def "should not prepare messages for manual demand for payment when no formal debt process pending"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.of(manualDemandForPayment)
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(manualDemandForPayment.contractNumber) >> false
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.isEmpty()
    }

    def "should prepare messages for manual demand for payment when demand breaks pending process"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.of(manualDemandForPayment)
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(manualDemandForPayment.contractNumber) >> true
        and:
            formalDebtCollectionService.findIssuedSucceedingDemandForPaymentIssueDate(manualDemandForPayment.type.key,
                    manualDemandForPayment.contractNumber) >> parse("2017-08-22 15:30:00.000000")
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.size() == 1
            messages.contains("Uwaga: umowa jest już windykowana – wysłano wezwanie wyższego stopnia dnia 2017-08-22. Wysłanie spowoduje rozpoczęcie procesu od nowa")
    }

    def "should prepare messages for manual demand for payment when demand not breaks process but demand with given type already issued"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.of(manualDemandForPayment)
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(manualDemandForPayment.contractNumber) >> true
        and:
            formalDebtCollectionService.findIssuedSucceedingDemandForPaymentIssueDate(manualDemandForPayment.type.key,
                    manualDemandForPayment.contractNumber) >> null
        and:
            formalDebtCollectionService.findIssuedDemandForPaymentIssueDate(manualDemandForPayment.type.key,
                    manualDemandForPayment.contractNumber) >> parse("2017-08-22 15:30:00.000000")
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.size() == 1
            messages.contains("Uwaga: umowa jest już windykowana – wysłano wezwanie danego stopnia dnia 2017-08-22")
    }

    def "should prepare messages for manual demand for payment when previous demand not overdue"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.of(manualDemandForPayment)
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(manualDemandForPayment.contractNumber) >> true
        and:
            formalDebtCollectionService.findIssuedSucceedingDemandForPaymentIssueDate(manualDemandForPayment.type.key,
                    manualDemandForPayment.contractNumber) >> null
        and:
            formalDebtCollectionService.findIssuedDemandForPaymentIssueDate(manualDemandForPayment.type.key,
                    manualDemandForPayment.contractNumber) >> parse("2017-08-22 15:30:00.000000")
        and:
            formalDebtCollectionService.isLatestDemandForPaymentOverdue(manualDemandForPayment.contractNumber) >> false
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.size() == 2
            messages.contains("Uwaga: umowa jest już windykowana – wysłano wezwanie danego stopnia dnia 2017-08-22")
            messages.contains("Uwaga: nie upłynął termin zapłaty ostatniego wezwania wystawionego do tej umowy!")
    }

    def "should prepare messages for manual demand for payment when customer is out of service of formal debt collection"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.of(manualDemandForPayment)
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(manualDemandForPayment.contractNumber) >> true
        and:
            def currentDate = TestDateUtils.parse("2018-10-10")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
            customerService.isFormalDebtCollectionCustomerOutOfService(manualDemandForPayment.getExtCompanyId(), currentDate, manualDemandForPayment.type.key) >> true
            customerService.findFormalDebtCollectionCustomersOutOfService(manualDemandForPayment.extCompanyId, true) >> [customerOutOfService1()]
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.size() == 1
            messages.contains("Klient posiada aktywną blokadę windykacji formalnej")
    }

    def "should prepare messages for manual demand for payment when contract is out of service of formal debt collection"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.of(manualDemandForPayment)
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(manualDemandForPayment.contractNumber) >> true
        and:
            def contractOutOfServiceDto = contractOutOfServiceDto1()
            contractOutOfServiceDto.contractNo = manualDemandForPayment.contractNumber
            contractOutOfServiceDto.demandForPaymentType = manualDemandForPayment.type.key
        and:
            customerService.findActiveFormalDebtCollectionContractsOutOfService(manualDemandForPayment.extCompanyId) >> [contractOutOfServiceDto]
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.size() == 1
            messages.contains("Umowa posiada aktywną blokadę windykacji formalnej")
    }

    def "should not prepare messages for manual demand for payment when contract is not out of service of formal debt collection"() {
        given:
            def manualDemandForPayment = demandForPayment7()
        and:
            demandForPaymentDao.get(manualDemandForPayment.id) >> Optional.of(manualDemandForPayment)
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(manualDemandForPayment.contractNumber) >> true
        and:
            def contractOutOfServiceDto = contractOutOfServiceDto1()
            contractOutOfServiceDto.contractNo = "not this contract"
        and:
            customerService.findActiveFormalDebtCollectionContractsOutOfService(manualDemandForPayment.extCompanyId) >> [contractOutOfServiceDto]
        when:
            def messages = service.determineManualDemandForPaymentMessages(manualDemandForPayment.id)
        then:
            messages.isEmpty()
    }
}
