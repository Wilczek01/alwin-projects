package com.codersteam.alwin.param

import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.parameters.DateParam
import spock.lang.Specification

/**
 * @author Adam Stepnowski
 */
class DateParamTest extends Specification {

    def "should throw an exception if date value is not in required format"() {
        given:
            def date = '20170909'

        when:
            new DateParam(date)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawna wartość dla daty '" + date + "'"
    }

    def "should pass for null date value "() {
        given:
            def date = null

        when:
            new DateParam(date)

        then:
            true
    }

    def "should pass for required date value "() {
        given:
            def date = '2017-09-09'

        when:
            new DateParam(date)

        then:
            true
    }
}