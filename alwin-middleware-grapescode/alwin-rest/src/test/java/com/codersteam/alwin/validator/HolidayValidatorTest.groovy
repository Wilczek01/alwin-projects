package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.model.holiday.HolidayInputDto
import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification
import spock.lang.Subject

/**
 * @author Michal Horowic
 */
class HolidayValidatorTest extends Specification {

    @Subject
    HolidayValidator validator

    def setup() {
        validator = new HolidayValidator()
    }

    def "should check that holiday without description is invalid"() {
        when:
            validator.validate(new HolidayInputDto())

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == 'Brakujący opis dla dnia wolnego'
    }

    def "should check that holiday date is invalid"(int day, int month, int year, String message) {
        given:
            def holiday = new HolidayInputDto()
            holiday.description = 'test'
            holiday.day = day
            holiday.month = month
            holiday.year = year

        when:
            validator.validate(holiday)

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == message

        where:
            day | month | year || message
            1   | -1    | 2018 || 'Nieprawidłowy numer miesiąca [-1]'
            1   | 13    | 2018 || 'Nieprawidłowy numer miesiąca [13]'
            29  | 2     | 2018 || 'Nieprawidłowy dzień [29] w miesiącu 2'
    }

    def "should check that repeatable holiday for leap year is not allowed"() {
        given:
            def holiday = new HolidayInputDto()
            holiday.description = 'test'
            holiday.day = 29
            holiday.month = 2

        when:
            validator.validate(holiday)

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == 'Nie można utworzyć corocznego dnia wolnego w dzień [29.2] występujący tylko w lata przestępne'
    }

    def "should check that holiday is valid"(int day, int month, int year) {
        given:
            def holiday = new HolidayInputDto()
            holiday.description = 'test'
            holiday.day = day
            holiday.month = month
            holiday.year = year

        when:
            validator.validate(holiday)

        then:
            notThrown(AlwinValidationException)

        where:
            day | month | year
            28  | 2     | 2018
            29  | 2     | 1904
    }

    def "should check that repeatable holiday is valid"() {
        given:
            def holiday = new HolidayInputDto()
            holiday.description = 'test'
            holiday.day = 1
            holiday.month = 5

        when:
            validator.validate(holiday)

        then:
            notThrown(AlwinValidationException)
    }

    def "should check that holiday parameters are invalid"(Integer day, Integer month, int year, String message) {
        when:
            validator.validate(day, month, year)

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == message

        where:
            day  | month | year || message
            null | -1    | 2018 || 'Nieprawidłowy numer miesiąca [-1]'
            null | 13    | 2018 || 'Nieprawidłowy numer miesiąca [13]'
            29   | 2     | 2018 || 'Nieprawidłowy dzień [29] w miesiącu 2'
    }

    def "should check that holiday parameters are valid"(Integer day, Integer month, int year) {
        when:
            validator.validate(day, month, year)

        then:
            notThrown(AlwinValidationException)

        where:
            day  | month | year
            null | null  | 2018
            null | 5     | 2018
            5    | null  | 2018
            28   | 2     | 2018
            29   | 2     | 1904
    }
}
