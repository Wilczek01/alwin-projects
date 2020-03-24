package com.codersteam.alwin.core.util

import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier
import java.time.LocalDateTime

import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Piotr Naroznik
 */
class DateUtilsTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = DateUtils.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    @Unroll
    def "should add [#days] days to date"() {
        given:
            def date = parse("2017-10-10")
        expect:
            DateUtils.datePlusDays(date, days) == datePlusDays
        where:
            days | datePlusDays
            2    | parse("2017-10-12")
            2    | parse("2017-10-12")
            10   | parse("2017-10-20")
            30   | parse("2017-11-09")
            90   | parse("2018-01-08")
    }

    def "should return null when date is null during adding days to date "() {
        expect:
            DateUtils.datePlusDays(null, 2) == null
    }

    def "should add one day to date"() {
        given:
            def date = parse("2017-10-10")
        expect:
            DateUtils.datePlusOneDay(date) == parse("2017-10-11")
    }

    def "should days between #fromDate and #toDate should be #numberOfDays"() {
        expect:
            DateUtils.daysBetween(parse(fromDate), parse(toDate)) == numberOfDays
        where:
            fromDate     | toDate       | numberOfDays
            "2018-10-23" | "2018-10-27" | 4
            "2018-10-23" | "2018-10-15" | -8
    }

    def "should get format string from date one day to date"() {
        given:
            def date = parse("2017-10-10 10:22:33.000000")
            def pattern = "yyyy-MM-dd HH:mm:ss"
        expect:
            DateUtils.getFormattedStringFromDate(date, pattern) == '2017-10-10 10:22:33'
    }

    @Unroll
    def "should difference between #fromDate and #toDate be #diffInMinutes minutes"() {
        expect:
            DateUtils.diffInMinutes(parse(fromDate), parse(toDate)) == diffInMinutes
        where:
            fromDate                     | toDate                       | diffInMinutes
            "2017-07-10 00:00:00.000000" | "2017-07-10 00:15:00.000000" | 15
            "2017-07-10 01:00:00.000000" | "2017-07-10 02:00:00.000000" | 60
            "2017-07-10 02:00:00.000000" | "2017-07-10 01:00:00.000000" | -60
            "2017-07-10 12:25:00.000000" | "2017-07-11 12:25:00.000000" | 1440
    }

    def "should convert date to local date time"() {
        when:
            def dateAsLocalDateTime = DateUtils.dateToLocalDateTime(parse("2017-07-10 12:25:00.000000"))
        then:
            dateAsLocalDateTime instanceof LocalDateTime
    }

    def "should handle null date when converting to local date time"() {
        when:
            def dateAsLocalDateTime = DateUtils.dateToLocalDateTime(null)
        then:
            dateAsLocalDateTime == null
    }

    def "should return true if checking date is before or same day as given current date"() {
        expect:
            DateUtils.isBeforeOrSameDate(parse(date), parse(currentDate)) == beforeOrSameDate
        where:
            date                         | currentDate                  | beforeOrSameDate
            "2017-08-18 15:30:00.000000" | "2017-08-22 15:30:00.000000" | true
            "2017-08-22 10:30:00.000000" | "2017-08-22 15:30:00.000000" | true
            "2017-08-22 18:30:00.000000" | "2017-08-22 15:30:00.000000" | true
            "2017-08-23 00:00:00.000000" | "2017-08-22 15:30:00.000000" | false
            "2017-08-25 12:00:00.000000" | "2017-08-22 15:30:00.000000" | false
    }

    def "should return true if checking date is before current date"() {
        expect:
            DateUtils.isBefore(parse(date), parse(currentDate)) == beforeOrSameDate
        where:
            date                         | currentDate                  | beforeOrSameDate
            "2017-08-18 15:30:00.000000" | "2017-08-22 15:30:00.000000" | true
            "2017-08-22 10:30:00.000000" | "2017-08-22 15:30:00.000000" | false
            "2017-08-22 18:30:00.000000" | "2017-08-22 15:30:00.000000" | false
            "2017-08-23 00:00:00.000000" | "2017-08-22 15:30:00.000000" | false
            "2017-08-25 12:00:00.000000" | "2017-08-22 15:30:00.000000" | false
    }

    def "should format date to string without time"() {
        when:
            def dateAsString = DateUtils.dateToStringWithoutTime(parse("2017-08-22 15:30:00.000000"))
        then:
            dateAsString == "2017-08-22"
    }

    def "should format null date to string without time"() {
        when:
            def dateAsString = DateUtils.dateToStringWithoutTime(null)
        then:
            dateAsString == "b/d"
    }
}
