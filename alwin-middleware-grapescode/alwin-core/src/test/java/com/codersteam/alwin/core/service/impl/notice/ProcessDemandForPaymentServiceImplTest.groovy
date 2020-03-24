package com.codersteam.alwin.core.service.impl.notice

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey
import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.db.dao.DemandForPaymentDao
import com.codersteam.alwin.efaktura.PaymentRequestClient
import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentRequestDto
import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentResponseDto
import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentStatus
import com.codersteam.alwin.efaktura.model.payment.PaymentRequestException
import com.codersteam.alwin.jpa.customer.Address
import com.codersteam.alwin.jpa.notice.DemandForPayment
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.demand.DemandForPaymentState.FAILED
import static com.codersteam.alwin.common.demand.DemandForPaymentState.ISSUED
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.SECOND
import static com.codersteam.alwin.core.service.impl.notice.ProcessDemandForPaymentServiceImpl.CONTRACT_TERMINATION_PENDING_FOR_CONTRACT_MESSAGE
import static com.codersteam.alwin.core.service.impl.notice.ProcessDemandForPaymentServiceImpl.DEMAND_FOR_PAYMENT_ABORTED
import static com.codersteam.alwin.testdata.AddressTestData.*
import static com.codersteam.alwin.testdata.ContactDetailTestData.*
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.*
import static java.util.Collections.emptySet
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Michal Horowic
 */
class ProcessDemandForPaymentServiceImplTest extends Specification {

    @Subject
    private ProcessDemandForPaymentServiceImpl service

    DemandForPaymentDao demandForPaymentDao = Mock(DemandForPaymentDao)
    PaymentRequestClient paymentRequestClient = Mock(PaymentRequestClient)
    CompanyDao companyDao = Mock(CompanyDao)
    FormalDebtCollectionService formalDebtCollectionService = Mock(FormalDebtCollectionService)

    def "setup"() {
        service = Spy(ProcessDemandForPaymentServiceImpl, constructorArgs: [demandForPaymentDao, companyDao, paymentRequestClient,
                                                                            formalDebtCollectionService]) as ProcessDemandForPaymentServiceImpl
    }

    def "should send request, update demand status, attachment ref and clear processing message"() {
        given:
            def demand = demandForPaymentDto1()
            def demandFromDb = demandForPaymentWithCompanyName1()

        and:
            def operatorFullName = "Jan Kowalski"

        and:
            def preparedRequest = new GeneratePaymentRequestDto()

        and:
            demandForPaymentDao.get(demand.id) >> Optional.of(demandFromDb)

        and:
            service.prepareDemand(demandFromDb, operatorFullName) >> preparedRequest

        when:
            service.sendDemand(demand, operatorFullName)

        then:
            1 * paymentRequestClient.generatePaymentRequest(preparedRequest) >> new GeneratePaymentResponseDto(GeneratePaymentStatus.CREATED_WITH_PDF.name(), DEMAND_FOR_PAYMENT_ATTACHMENT_REF_1)
            1 * demandForPaymentDao.update(_ as DemandForPayment) >> { args ->
                def demandForPayment = (DemandForPayment) args[0]
                assertThat(demandForPayment).isEqualToComparingFieldByFieldRecursively(demandFromDb)
                assert demandForPayment.state == ISSUED
                assert demandForPayment.attachmentRef == DEMAND_FOR_PAYMENT_ATTACHMENT_REF_1
                assert demandForPayment.processingMessage.length() == 0
            }
    }

    def "should not send request, update demand status and processing message"() {
        given:
            def demand = demandForPaymentDto1()
            def demandFromDb = demandForPaymentWithCompanyName1()

        and:
            def operatorFullName = "Jan Kowalski"

        and:
            def errorMessage = "This is error message"

        and:
            def preparedRequest = new GeneratePaymentRequestDto()

        and:
            demandForPaymentDao.get(demand.id) >> Optional.of(demandFromDb)

        and:
            service.prepareDemand(demandFromDb, operatorFullName) >> preparedRequest

        when:
            service.sendDemand(demand, operatorFullName)

        then:
            1 * paymentRequestClient.generatePaymentRequest(preparedRequest) >> { throw new PaymentRequestException(errorMessage) }
            1 * demandForPaymentDao.update(_ as DemandForPayment) >> { args ->
                def demandForPayment = (DemandForPayment) args[0]
                assertThat(demandForPayment).isEqualToComparingFieldByFieldRecursively(demandFromDb)
                assert demandForPayment.state == FAILED
                assert demandForPayment.processingMessage == errorMessage
            }
    }

    def "should not send request, update demand status and processing message if preparing request failed"() {
        given:
            def demand = demandForPaymentDto1()
            def demandFromDb = demandForPaymentWithCompanyName1()

        and:
            def operatorFullName = "Jan Kowalski"

        and:
            def errorMessage = "This is error message"

        and:
            demandForPaymentDao.get(demand.id) >> Optional.of(demandFromDb)

        and:
            service.prepareDemand(demandFromDb, operatorFullName) >> { throw new RuntimeException(errorMessage) }

        when:
            service.sendDemand(demand, operatorFullName)

        then:
            0 * paymentRequestClient.generatePaymentRequest(_ as GeneratePaymentRequestDto)
            1 * demandForPaymentDao.update(_ as DemandForPayment) >> { args ->
                def demandForPayment = (DemandForPayment) args[0]
                assertThat(demandForPayment).isEqualToComparingFieldByFieldRecursively(demandFromDb)
                assert demandForPayment.state == FAILED
                assert demandForPayment.processingMessage == errorMessage
            }
    }

    def "should send request, but update demand status and processing message if response status was not successful"() {
        given:
            def demand = demandForPaymentDto1()
            def demandFromDb = demandForPaymentWithCompanyName1()

        and:
            def operatorFullName = "Jan Kowalski"

        and:
            def unsuccessfulStatus = "TEST123"

        and:
            def errorMessage = "This is error message"

        and:
            def preparedRequest = new GeneratePaymentRequestDto()

        and:
            demandForPaymentDao.get(demand.id) >> Optional.of(demandFromDb)

        and:
            service.prepareDemand(demandFromDb, operatorFullName) >> preparedRequest

        when:
            service.sendDemand(demand, operatorFullName)

        then:
            1 * paymentRequestClient.generatePaymentRequest(preparedRequest) >> new GeneratePaymentResponseDto(unsuccessfulStatus, null, errorMessage)
            1 * demandForPaymentDao.update(_ as DemandForPayment) >> { args ->
                def demandForPayment = (DemandForPayment) args[0]
                assertThat(demandForPayment).isEqualToComparingFieldByFieldRecursively(demandFromDb)
                assert demandForPayment.state == FAILED
                assert demandForPayment.processingMessage == "$unsuccessfulStatus - $errorMessage"
            }
    }

    def "should build file name of first demand for payment"() {
        when:
            def name = service.buildFileName(FIRST, 'to/jest/test', parse("2018-10-10"))

        then:
            name == 'Wezwanie_1_to-jest-test_2018-10-10'

    }

    def "should build file name of second demand for payment"() {
        when:
            def name = service.buildFileName(DemandForPaymentTypeKey.SECOND, 'to/jest/test', parse("2018-10-10"))

        then:
            name == 'Wezwanie_2_to-jest-test_2018-10-10'

    }

    def "should build company street"(String streetPrefix, String street, String houseNo, String flatNo, String expectedStreet) {
        given:
            def address = new Address()
            address.streetPrefix = streetPrefix
            address.streetName = street
            address.houseNumber = houseNo
            address.flatNumber = flatNo

        when:
            def actualStreet = service.buildStreet(address)

        then:
            actualStreet == expectedStreet

        where:
            streetPrefix | street         | houseNo | flatNo | expectedStreet
            "ul."        | "Testowa"      | "5b"    | "123"  | "ul. Testowa 5b/123"
            "ul."        | "Testowa"      | "5b"    | null   | "ul. Testowa 5b"
            null         | "Testowa"      | "5b"    | "123"  | "Testowa 5b/123"
            null         | "Testowa"      | "5b"    | null   | "Testowa 5b"
            null         | "Testowa 8/11" | null    | null   | "Testowa 8/11"
            null         | "Testowa 8"    | null    | "11"   | "Testowa 8/11"
    }

    def "should prepare company contact data with document email"() {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyContact(request, contactsSetWithAllEmails())

        then:
            request.recipientEmail == DOCUMENTS_EMAIL_10
            request.recipientPhoneNo1 == PHONE_NUMBER_1_10
            request.recipientPhoneNo2 == PHONE_NUMBER_2_10
    }

    def "should prepare company contact data with contact email"() {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyContact(request, contactsSetWithoutDocumentEmail())

        then:
            request.recipientEmail == EMAIL_10
            request.recipientPhoneNo1 == PHONE_NUMBER_1_10
            request.recipientPhoneNo2 == PHONE_NUMBER_2_10
    }

    def "should prepare company contact data with office email"() {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyContact(request, contactsSetWithoutDocumentAndContactEmail())

        then:
            request.recipientEmail == OFFICE_EMAIL_10
            request.recipientPhoneNo1 == PHONE_NUMBER_1_10
            request.recipientPhoneNo2 == PHONE_NUMBER_2_10
    }

    def "should not prepare company contact data if non was provided"(Set contacts) {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyContact(request, contacts)

        then:
            def exception = thrown(IllegalStateException)

        and:
            exception.message == "Brak danych kontaktowych dla klienta"

        where:
            contacts << [null, emptySet()]
    }

    def "should not prepare company contact data if no emails were provided"() {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyContact(request, contactsSetWithNoEmails())

        then:
            def exception = thrown(IllegalStateException)

        and:
            exception.message == "Brak któregokolwiek z adresów email"
    }

    def "should prepare company contact data with no phone 1 provided"() {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyContact(request, contactsSetWithoutPhone1())

        then:
            request.recipientPhoneNo1 == null
    }

    def "should not prepare company contact data with no phone 2 provided"() {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyContact(request, contactsSetWithoutPhone2())

        then:
            request.recipientPhoneNo2 == null
    }

    def "should prepare company address data with correspondence address"() {
        given:
            def request = new GeneratePaymentRequestDto()

        and:
            def street = "ul. Testowa 10B/15"

        and:
            service.buildStreet(_ as Address) >> street

        when:
            service.prepareCompanyAddress(request, correspondenceAndResidenceAddresses())

        then:
            request.recipientStreet == street
            request.recipientPostalCode == ADDRESS_POSTAL_CODE_1
            request.recipientCity == ADDRESS_CITY_1
    }

    def "should prepare company address data with no correspondence address but residence"() {
        given:
            def request = new GeneratePaymentRequestDto()

        and:
            def street = "ul. Testowa 10B/15"

        and:
            service.buildStreet(_ as Address) >> street

        when:
            service.prepareCompanyAddress(request, onlyResidenceAddress())

        then:
            request.recipientStreet == street
            request.recipientPostalCode == ADDRESS_POSTAL_CODE_2
            request.recipientCity == ADDRESS_CITY_2
    }

    def "should not prepare company address data if non was provided"(Set addresses) {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyAddress(request, addresses)

        then:
            def exception = thrown(IllegalStateException)

        and:
            exception.message == "Brak adresów dla klienta"

        where:
            addresses << [null, emptySet()]
    }

    def "should not prepare company address data if correspondence or residence address was not provided"() {
        given:
            def request = new GeneratePaymentRequestDto()

        when:
            service.prepareCompanyAddress(request, onlyOtherAddress())

        then:
            def exception = thrown(IllegalStateException)

        and:
            exception.message == "Brak adresu korespondencyjnego lub siedziby dla klienta"
    }

    def "should fail for manual demand for payment and termination pending"() {
        given:
            def demandForPaymentDto = demandForPaymentDto1()
        and:
            def demandForPayment = demandForPayment1()
            demandForPayment.createdManually = true
        and:
            demandForPaymentDao.get(demandForPaymentDto.id) >> Optional.of(demandForPayment)
        and:
            formalDebtCollectionService.isContractTerminationPending(demandForPayment.contractNumber) >> true
        when:
            service.sendDemand(demandForPaymentDto, null)
        then:
            1 * demandForPaymentDao.update(demandForPayment) >> { List args ->
                def failedDemand = args[0] as DemandForPayment
                assert failedDemand.state == FAILED
                assert failedDemand.processingMessage == CONTRACT_TERMINATION_PENDING_FOR_CONTRACT_MESSAGE
            }
        and:
            0 * paymentRequestClient.generatePaymentRequest(_ as GeneratePaymentRequestDto)
    }

    def "should fail for already aborted demand for payment"() {
        given:
            def demandForPaymentDto = demandForPaymentDto1()
        and:
            def demandForPayment = demandForPayment1()
            demandForPayment.aborted = true
        and:
            demandForPaymentDao.get(demandForPaymentDto.id) >> Optional.of(demandForPayment)
        when:
            service.sendDemand(demandForPaymentDto, null)
        then:
            1 * demandForPaymentDao.update(demandForPayment) >> { List args ->
                def failedDemand = args[0] as DemandForPayment
                assert failedDemand.state == FAILED
                assert failedDemand.processingMessage == DEMAND_FOR_PAYMENT_ABORTED
            }
        and:
            0 * paymentRequestClient.generatePaymentRequest(_ as GeneratePaymentRequestDto)
    }

    def "should modify process if needed for manually created demand for payment"() {
        given:
            def demandForPaymentDto = demandForPaymentDto1()
        and:
            def demandForPayment = demandForPayment1()
            demandForPayment.createdManually = true
        and:
            demandForPaymentDao.get(demandForPaymentDto.id) >> Optional.of(demandForPayment)
        and:
            formalDebtCollectionService.isContractTerminationPending(demandForPayment.contractNumber) >> false
        when:
            service.sendDemand(demandForPaymentDto, null)
        then:
            1 * service.alterProcessIfNeededForManualDemandForPayment(_ as DemandForPayment)
    }

    def "should not alter process for manual demand for payment when no active formal debt collection process pending"() {
        given:
            def demandForPayment = demandForPayment1()
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(demandForPayment.contractNumber) >> false
        when:
            service.alterProcessIfNeededForManualDemandForPayment(demandForPayment)
        then:
            0 * formalDebtCollectionService.markDemandForPaymentAborted(_ as Long)
    }

    def "should alter process for manual demand for payment for SECOND demand of payment and previously existing second demand"() {
        given:
            def demandForPayment = demandForPayment1()
            demandForPayment.type.key = SECOND
            demandForPayment.precedingDemandForPaymentId = null
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(demandForPayment.contractNumber) >> true
        and:
            formalDebtCollectionService.findDemandsForPaymentExcludingGiven(SECOND, demandForPayment.contractNumber, demandForPayment.initialInvoiceNo,
                    demandForPayment.id) >> [demandForPaymentDto4()]
        and:
            formalDebtCollectionService.findDemandsForPayment(FIRST, demandForPayment.contractNumber, demandForPayment.initialInvoiceNo) >> [demandForPaymentDto3()]
        when:
            service.alterProcessIfNeededForManualDemandForPayment(demandForPayment)
        then:
            1 * formalDebtCollectionService.markDemandForPaymentAborted(demandForPaymentDto4().id)
            demandForPayment.precedingDemandForPaymentId == demandForPaymentDto3().id
    }

    def "should alter process for manual demand for payment for FIRST demand of payment and previously existing second demand"() {
        given:
            def demandForPayment = demandForPayment1()
            demandForPayment.type.key = FIRST
        and:
            formalDebtCollectionService.isContractUnderActiveFormalDebtCollection(demandForPayment.contractNumber) >> true
        and:
            formalDebtCollectionService.findDemandsForPaymentExcludingGiven(FIRST, demandForPayment.contractNumber, demandForPayment.initialInvoiceNo,
                    demandForPayment.id) >> [demandForPaymentDto4()]
            formalDebtCollectionService.findDemandsForPayment(SECOND, demandForPayment.contractNumber,
                    demandForPayment.initialInvoiceNo) >> [demandForPaymentDto3()]
        when:
            service.alterProcessIfNeededForManualDemandForPayment(demandForPayment)
        then:
            1 * formalDebtCollectionService.markDemandForPaymentAborted(demandForPaymentDto4().id)
            1 * formalDebtCollectionService.markDemandForPaymentAborted(demandForPaymentDto3().id)
    }
}
