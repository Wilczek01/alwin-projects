package com.codersteam.alwin.efaktura

import com.codersteam.alwin.efaktura.model.termination.ContractTerminationRequestDto
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationResponseDto
import com.codersteam.alwin.efaktura.model.termination.data.*
import com.codersteam.alwin.testdata.TestDateUtils
import pl.aliorleasing.ali.contract_termination_api.ws.ContractTermination
import pl.aliorleasing.ali.contract_termination_api.ws.ContractType
import pl.aliorleasing.ali.contract_termination_api.ws.TerminationType
import spock.lang.Specification
import spock.lang.Subject

/**
 * @author Dariusz Rackowski
 */
class ContractTerminationClientMapperTest extends Specification {

    @Subject
    private ContractTerminationClientMapper alwinMapper

    def setup() {
        alwinMapper = new ContractTerminationClientMapper()
    }

    def "should convert ContractTermination to ContractTerminationResponseDto"() {
        given:
            def termination = new ContractTermination()
//            termination.creationDate = ...
//            termination.updateDate = ...
//            termination.sendDate = ...
            termination.documentHashes = new ContractTermination.DocumentHashes()
            termination.documentHashes.getHash().add("HASH001")
            termination.documentHashes.getHash().add("HASH002")
            termination.error = "Some error only for test mapping"

        when:
            def response = alwinMapper.map(termination, ContractTerminationResponseDto.class)

        then:
//            response.creationDate == TestDateUtils.parse("2018-09-01 23:11:23.000000")
//            response.updateDate == TestDateUtils.parse("2018-09-01 23:11:23.000000")
            response.documentHashes == ["HASH001", "HASH002"]
            response.error == "Some error only for test mapping"
//            response.sendDate == TestDateUtils.parse("2018-09-01 23:11:23.000000")
    }

    def "should convert ContractTerminationRequestDto to ContractTermination"() {
        given:
            ContractTerminationRequestDto request = buildExampleRequest()

        when:
            def ct = alwinMapper.map(request, ContractTermination.class)

        then:
            ct.contactEmail == "email@jakis.com"
            ct.contactPhoneNo == "+22 45 678 88 88"
            ct.contracts.contract[0].activationFee == new BigDecimal("123.00")
            ct.contracts.contract[0].contractNo == "CONTRACT/001"
            ct.contracts.contract[0].contractType == ContractType.FINANCIAL_LEASING
            ct.contracts.contract[0].outstandingInvoices.invoice[0].contractNo == "CONTRACT/001"
            ct.contracts.contract[0].outstandingInvoices.invoice[0].currency == "PLN"
//            ct.contracts.contract[0].outstandingInvoices.invoice[0].dueDate == TestDateUtils.parseWithoutTime("2018-01-20")
            ct.contracts.contract[0].outstandingInvoices.invoice[0].invoiceBalance == new BigDecimal("801.00")
            ct.contracts.contract[0].outstandingInvoices.invoice[0].invoiceNo == "Invoice 1"
            ct.contracts.contract[0].outstandingInvoices.invoice[0].invoiceSum == new BigDecimal("1201.00")
//            ct.contracts.contract[0].outstandingInvoices.invoice[0].issueDate == TestDateUtils.parseWithoutTime("2018-01-10")
            ct.contracts.contract[0].outstandingInvoices.invoice[0].leoId == 1001
            ct.contracts.contract[0].outstandingInvoices.invoice[1].contractNo == "CONTRACT/001"
            ct.contracts.contract[0].outstandingInvoices.invoice[1].currency == "PLN"
//            ct.contracts.contract[0].outstandingInvoices.invoice[1].dueDate == TestDateUtils.parseWithoutTime("2018-01-21")
            ct.contracts.contract[0].outstandingInvoices.invoice[1].invoiceBalance == new BigDecimal("802.00")
            ct.contracts.contract[0].outstandingInvoices.invoice[1].invoiceNo == "Invoice 2"
            ct.contracts.contract[0].outstandingInvoices.invoice[1].invoiceSum == new BigDecimal("1202.00")
//            ct.contracts.contract[0].outstandingInvoices.invoice[1].issueDate == TestDateUtils.parseWithoutTime("2018-01-11")
            ct.contracts.contract[0].outstandingInvoices.invoice[1].leoId == 1002
            ct.contracts.contract[0].contractSubjects.contractSubject[0].gpsFee == new BigDecimal("123.00")
            ct.contracts.contract[0].contractSubjects.contractSubject[0].hasGps == Boolean.TRUE
            ct.contracts.contract[0].contractSubjects.contractSubject[0].plateNo == "WI 12345"
            ct.contracts.contract[0].contractSubjects.contractSubject[0].serialNo == "DKJFSDHFSDUFIUSDFUIOSDFUOI123"
            ct.contracts.contract[0].contractSubjects.contractSubject[0].subject == "Wartburg"

            ct.contracts.contract[1].activationFee == new BigDecimal("123.00")
            ct.contracts.contract[1].contractNo == "CONTRACT/002"
            ct.contracts.contract[1].contractType == ContractType.FINANCIAL_LEASING
            ct.contracts.contract[1].outstandingInvoices.invoice[0].contractNo == "CONTRACT/002"
            ct.contracts.contract[1].outstandingInvoices.invoice[0].currency == "PLN"
//            ct.contracts.contract[1].outstandingInvoices.invoice[0].dueDate == TestDateUtils.parseWithoutTime("2018-02-21")
            ct.contracts.contract[1].outstandingInvoices.invoice[0].invoiceBalance == new BigDecimal("803.00")
            ct.contracts.contract[1].outstandingInvoices.invoice[0].invoiceNo == "Invoice 3"
            ct.contracts.contract[1].outstandingInvoices.invoice[0].invoiceSum == new BigDecimal("1203.00")
//            ct.contracts.contract[1].outstandingInvoices.invoice[0].issueDate == TestDateUtils.parseWithoutTime("2018-02-11")
            ct.contracts.contract[1].outstandingInvoices.invoice[0].leoId == 1003

            ct.creationDate == null
            ct.customerCity == "Warszawa"
            ct.customerEmail == "customer@email.com"
            ct.customerMailCity == "Poznań"
            ct.customerMailPostalCode == "22-345"
            ct.customerMailStreet == "Warszawska 54/321"
            ct.customerName == "Firma Janusza"
            ct.customerNip == "123-121-33-22"
            ct.customerNo == "211"
            ct.customerPhoneNo1 == "+48 123 45 67"
            ct.customerPhoneNo2 == null
            ct.customerPostalCode == "01-234"
            ct.customerStreet == "Powązkowska 123/45"
            ct.error == null
            ct.documentHashes == null
            ct.issuingOperator == "Jan Kowalski"
            ct.sendDate == null
//            ct.suspensionDate == TestDateUtils.parse("2018-09-01 23:11:23.000000")
            ct.terminationType == TerminationType.CONDITIONAL
            ct.updateDate == null
    }

    ContractTerminationRequestDto buildExampleRequest() {
        def customer = new ContractCustomerDto()
        customer.email = "customer@email.com"
        customer.city = "Warszawa"
        customer.postalCode = "01-234"
        customer.street = "Powązkowska 123/45"
        customer.mailCity = "Poznań"
        customer.mailPostalCode = "22-345"
        customer.mailStreet = "Warszawska 54/321"
        customer.name = "Firma Janusza"
        customer.nip = "123-121-33-22"
        customer.no = "211"
        customer.phoneNo1 = "+48 123 45 67"
        customer.phoneNo2 = null

        def invoice1 = new InvoiceDto()
        invoice1.contractNo = "CONTRACT/001"
        invoice1.currency = "PLN"
        invoice1.dueDate = TestDateUtils.parse("2018-01-20")
        invoice1.invoiceBalance = new BigDecimal("801.00")
        invoice1.invoiceNo = "Invoice 1"
        invoice1.invoiceSum = new BigDecimal("1201.00")
        invoice1.issueDate = TestDateUtils.parse("2018-01-10")
        invoice1.leoId = 1001

        def invoice2 = new InvoiceDto()
        invoice2.contractNo = "CONTRACT/001"
        invoice2.currency = "PLN"
        invoice2.dueDate = TestDateUtils.parse("2018-01-21")
        invoice2.invoiceBalance = new BigDecimal("802.00")
        invoice2.invoiceNo = "Invoice 2"
        invoice2.invoiceSum = new BigDecimal("1202.00")
        invoice2.issueDate = TestDateUtils.parse("2018-01-11")
        invoice2.leoId = 1002

        def subject1 = new ContractSubjectDto()
        subject1.gpsFee = new BigDecimal("123.00")
        subject1.hasGps = true
        subject1.plateNo = "WI 12345"
        subject1.serialNo = "DKJFSDHFSDUFIUSDFUIOSDFUOI123"
        subject1.subject = "Wartburg"

        def contract1 = new ContractTerminationContractDto()
        contract1.activationFee = new BigDecimal("123.00")
        contract1.contractNo = "CONTRACT/001"
        contract1.contractType = ContractTypeDto.FINANCIAL_LEASING
        contract1.invoices = [invoice1, invoice2]
        contract1.subjects = [subject1]

        def invoice3 = new InvoiceDto()
        invoice3.contractNo = "CONTRACT/002"
        invoice3.currency = "PLN"
        invoice3.dueDate = TestDateUtils.parse("2018-02-21")
        invoice3.invoiceBalance = new BigDecimal("803.00")
        invoice3.invoiceNo = "Invoice 3"
        invoice3.invoiceSum = new BigDecimal("1203.00")
        invoice3.issueDate = TestDateUtils.parse("2018-02-11")
        invoice3.leoId = 1003

        def contract2 = new ContractTerminationContractDto()
        contract2.activationFee = new BigDecimal("123.00")
        contract2.contractNo = "CONTRACT/002"
        contract2.contractType = ContractTypeDto.FINANCIAL_LEASING
        contract2.invoices = [invoice3]
        contract2.subjects = []

        def request = new ContractTerminationRequestDto()
        request.contactEmail = "email@jakis.com"
        request.contactPhoneNo = "+22 45 678 88 88"
        request.contracts = [contract1, contract2]
        request.customer = customer
        request.issuingOperator = "Jan Kowalski"
        request.suspensionDate = TestDateUtils.parse("2018-09-01 23:11:23.000000")
        request.terminationType = TerminationTypeDto.CONDITIONAL
        return request
    }
}
