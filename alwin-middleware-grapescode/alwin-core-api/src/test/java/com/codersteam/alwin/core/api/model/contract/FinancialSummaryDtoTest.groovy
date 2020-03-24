package com.codersteam.alwin.core.api.model.contract

import spock.lang.Specification

/**
 * @author Tomasz Sliwinski
 */
class FinancialSummaryDtoTest extends Specification {

    def "should calculate proper total value"() {
        given:
            def requiredPayment = 324.23
            def notRequiredPayment = 234.23
            def overpayment = 23.23
        when:
            def financialSummaryDto = new FinancialSummaryDto(requiredPayment, notRequiredPayment, overpayment)
        then:
            financialSummaryDto.total == requiredPayment + notRequiredPayment - overpayment
    }
}
