package com.codersteam.alwin.validator

import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification
import spock.lang.Subject

class SchedulerValidatorTest extends Specification {

    @Subject
    SchedulerValidator validator = new SchedulerValidator()

    def "should return validate exception for negative hour"() {
        given:
            def negativeHour = -1
        when:
            validator.validateTimeOfExecution(negativeHour)
        then:
            def e = thrown(AlwinValidationException)
            e.message == SchedulerValidator.HOUR_SHOULD_BE_BETWEEN_0_AND_23
    }

    def "should return validate exception for hour greater than 23"() {
        given:
            def hourGreaterThan23 = 25
        when:
            validator.validateTimeOfExecution(hourGreaterThan23)
        then:
            def e = thrown(AlwinValidationException)
            e.message == SchedulerValidator.HOUR_SHOULD_BE_BETWEEN_0_AND_23
    }

    def "should pass validation when hour is correct"() {
        given:
            def correctHour = 3
        when:
            validator.validateTimeOfExecution(correctHour)
        then:
            true
    }
}
