package com.codersteam.alwin.efaktura

import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentRequestDto
import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentResponseDto
import com.codersteam.alwin.efaktura.model.payment.OutstandingInvoiceDto
import pl.aliorleasing.ali.payment_request_api.ws.PaymentRequest
import spock.lang.Specification
import spock.lang.Subject

/**
 * @author Dariusz Rackowski
 */
class PaymentRequestClientMapperTest extends Specification {

    @Subject
    private PaymentRequestClientMapper alwinMapper

    def setup() {
        alwinMapper = new PaymentRequestClientMapper()
    }

    def "should map GeneratePaymentRequestDto to PaymentRequest"() {
        given:
            def request = new GeneratePaymentRequestDto()
            request.amount = new BigDecimal("100.00")
            request.contractNo = "CONTRACT/01"
            request.customerNo = "123"
            request.delayDays = 14
            request.fileName = "File name"
            request.outstandingInvoices = [new OutstandingInvoiceDto()]
//          request.outstandingInvoices[0].dueDate
//          request.outstandingInvoices[0].issueDate
            request.outstandingInvoices[0].contractNo = "contractNo"
            request.outstandingInvoices[0].currency = "currency"
            request.outstandingInvoices[0].invoiceBalance = new BigDecimal("123.45")
            request.outstandingInvoices[0].invoiceNo = "invoiceNo"
            request.outstandingInvoices[0].invoiceSum = new BigDecimal("300.10")
            request.outstandingInvoices[0].leoId = 1001
            request.issuingOperator = "issuingOperator"
            request.recipientCity = "recipientCity"
            request.recipientEmail = "recipientEmail"
            request.recipientName = "recipientName"
            request.recipientNip = "recipientNip"
            request.recipientPhoneNo1 = "recipientPhoneNo1"
            request.recipientPhoneNo2 = "recipientPhoneNo2"
            request.recipientPostalCode = "recipientPostalCode"
            request.recipientStreet = "recipientStreet"

        when:
            def pr = alwinMapper.map(request, PaymentRequest.class)

        then:
            pr.amount == new BigDecimal("100.00")
            pr.contractNo == "CONTRACT/01"
            pr.customerNo == "123"
            pr.delayDays == 14
            pr.fileName == "File name"
//            pr.outstandingInvoices.invoice[0].dueDate
//            pr.outstandingInvoices.invoice[0].issueDate
            pr.outstandingInvoices.invoice[0].contractNo == "contractNo"
            pr.outstandingInvoices.invoice[0].currency == "currency"
            pr.outstandingInvoices.invoice[0].invoiceBalance == new BigDecimal("123.45")
            pr.outstandingInvoices.invoice[0].invoiceNo == "invoiceNo"
            pr.outstandingInvoices.invoice[0].invoiceSum == new BigDecimal("300.10")
            pr.outstandingInvoices.invoice[0].leoId == 1001
            pr.issuingOperator == "issuingOperator"
            pr.recipientCity == "recipientCity"
            pr.recipientEmail == "recipientEmail"
            pr.recipientName == "recipientName"
            pr.recipientNip == "recipientNip"
            pr.recipientPhoneNo1 == "recipientPhoneNo1"
            pr.recipientPhoneNo2 == "recipientPhoneNo2"
            pr.recipientPostalCode == "recipientPostalCode"
            pr.recipientStreet == "recipientStreet"
            pr.requestNo == null
            pr.sendDate == null
            pr.status == null
            pr.url == null
            pr.error == null
            pr.id == null
            pr.creationDate == null
            pr.updateDate == null
    }


    def "should map PaymentRequest to GeneratePaymentResponseDto"() {
        given:
            def request = new PaymentRequest()
//            request.creationDate
//            request.sendDate
//            request.updateDate
            request.error = "error message"
            request.id = 1023
            request.requestNo = "requestNo"
            request.status = "status"
            request.url = "url"

        when:
            def response = alwinMapper.map(request, GeneratePaymentResponseDto.class)

        then:
//            response.creationDate
//            response.sendDate
//            response.updateDate
            response.error == "error message"
            response.id == 1023
            response.requestNo == "requestNo"
            response.status == "status"
            response.url == "url"
    }
}
