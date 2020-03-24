package com.codersteam.alwin.common.util

import spock.lang.Specification

/**
 * @author Michal Horowic
 */
class MandatoryUtilsTest extends Specification {

    def "should return mandatory values"(def value, String name) {
        when:
            def mandatoryValue = MandatoryUtils.mandatory(value, name)

        then:
            mandatoryValue == value

        where:
            value  | name
            1      | 'jakaś liczba'
            'test' | 'jakiś string'
            true   | 'wartość tak'
    }

    def "should not return missing value"() {
        given:
            def name = "To jest pusta wartość"

        when:
            MandatoryUtils.mandatory(null, name)

        then:
            def exception = thrown(IllegalArgumentException)

        and:
            exception.message == "Brakujące wartość dla: To jest pusta wartość"
    }
}
