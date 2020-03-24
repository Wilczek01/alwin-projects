package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.service.notice.DemandForPaymentService
import com.codersteam.alwin.core.api.service.operator.OperatorService
import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.DemandForPaymentTestData.*

/**
 * @author Adam Stepnowski
 */
class DemandForPaymentValidatorTest extends Specification {

    @Subject
    DemandForPaymentValidator validator

    private DemandForPaymentService demandForPaymentService = Mock(DemandForPaymentService)
    private OperatorService operatorService = Mock(OperatorService)

    def "setup"() {
        validator = new DemandForPaymentValidator(demandForPaymentService, operatorService)
    }

    def "should validate demand for payment"() {
        given:
            demandForPaymentService.find(DEMAND_FOR_PAYMENT_ID_3) >> Optional.of(demandForPaymentDto3())
        when:
            validator.validateIssuedDemandForPayment(DEMAND_FOR_PAYMENT_ID_3)
        then:
            noExceptionThrown()
    }

    def "should not validate when demand for payment not exists"() {
        given:
            demandForPaymentService.find(DEMAND_FOR_PAYMENT_ID_3) >> Optional.empty()
        when:
            validator.validateIssuedDemandForPayment(DEMAND_FOR_PAYMENT_ID_3)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Wezwanie do zapłaty o podanym identyfikatorze [3] nie istnieje"
    }


    def "should not validate when demand for payment in not issued"() {
        given:
            demandForPaymentService.find(DEMAND_FOR_PAYMENT_ID_1) >> Optional.of(demandForPaymentDto1())
        when:
            validator.validateIssuedDemandForPayment(DEMAND_FOR_PAYMENT_ID_1)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Nieprawidłowy status wezwania do zapłaty"
    }

}
