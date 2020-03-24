package com.codersteam.alwin.validator

import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_1
import static com.codersteam.alwin.validator.InvoiceValidator.MISSING_INVOICE_NUMBER

/**
 * @author Tomasz Sliwinski
 */
class InvoiceValidatorTest extends Specification {

    @Subject
    private InvoiceValidator validator

    def "setup"() {
        validator = new InvoiceValidator()
    }

    def "should throw when invoice number is missing"() {
        when:
            validator.validateInvoiceNo(invoiceNo)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_INVOICE_NUMBER
        where:
            invoiceNo << [null, "", " "]
    }

    def "should check that invoice number is valid"() {
        when:
            validator.validateInvoiceNo(INVOICE_NUMBER_1)
        then:
            noExceptionThrown()
    }
}
