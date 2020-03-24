package com.codersteam.alwin.core.service.impl

import com.codersteam.alwin.integration.mock.DateProviderMock
import org.apache.commons.lang3.time.DateUtils
import spock.lang.Specification

import java.time.LocalDate

import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class DateProviderImplTest extends Specification {

    private DateProviderImpl dateProvider

    def setup() {
        dateProvider = new DateProviderImpl()
    }

    def "should return current date with time"() {
        given:
            def startDate = new Date()
        when:
            def date = dateProvider.getCurrentDate()
        then:
            date
            def endDate = new Date()
            def dateBefore = DateUtils.addSeconds(startDate, -1)
            def dateAfter = DateUtils.addSeconds(endDate, +1)
            date.before(dateAfter)
            date.after(dateBefore)
    }

    def "should return current date with start of day time"() {
        when:
            def date = dateProvider.getCurrentDateStartOfDay()
        then:
            date
            def calendar = date.toCalendar()
            calendar.get(Calendar.HOUR_OF_DAY) == 0
            calendar.get(Calendar.MINUTE) == 0
            calendar.get(Calendar.SECOND) == 0
    }

    def "should return current date with end of day time"() {
        when:
            def date = dateProvider.getCurrentDateEndOfDay()
        then:
            date
            def calendar = date.toCalendar()
            calendar.get(Calendar.HOUR_OF_DAY) == 23
            calendar.get(Calendar.MINUTE) == 59
            calendar.get(Calendar.SECOND) == 59
            calendar.get(Calendar.MILLISECOND) == 999
    }

    def "should return date with substracted days"() {
        when:
            def date = dateProvider.getDateStartOfDayMinusDays(3)
        then:
            date
            def dateInPast = DateUtils.addDays(new Date(), -3)
            def calendarDateInPast = dateInPast.toCalendar()
            def calendar = date.toCalendar()
            calendar.get(Calendar.DAY_OF_MONTH) == calendarDateInPast.get(Calendar.DAY_OF_MONTH)
            calendar.get(Calendar.MONTH) == calendarDateInPast.get(Calendar.MONTH)
            calendar.get(Calendar.YEAR) == calendarDateInPast.get(Calendar.YEAR)
            calendar.get(Calendar.HOUR_OF_DAY) == 0
            calendar.get(Calendar.MINUTE) == 0
            calendar.get(Calendar.SECOND) == 0
    }

    def "should return date with added days"() {
        when:
            def date = dateProvider.getDateStartOfDayPlusDays(10)
        then:
            date
            def dateInPast = DateUtils.addDays(new Date(), 10)
            def calendarDateInPast = dateInPast.toCalendar()
            def calendar = date.toCalendar()
            calendar.get(Calendar.DAY_OF_MONTH) == calendarDateInPast.get(Calendar.DAY_OF_MONTH)
            calendar.get(Calendar.MONTH) == calendarDateInPast.get(Calendar.MONTH)
            calendar.get(Calendar.YEAR) == calendarDateInPast.get(Calendar.YEAR)
            calendar.get(Calendar.HOUR_OF_DAY) == 0
            calendar.get(Calendar.MINUTE) == 0
            calendar.get(Calendar.SECOND) == 0
    }

    def "should return true for older date than yesterday"() {
        given:
            def date = parse("2017-10-10")
        expect:
            dateProvider.isYesterdayOrOlder(date)
    }

    def "should return false for future date"() {
        given:
            def date = parse(LocalDate.now().plusDays(1L).toString())
        expect:
            !dateProvider.isYesterdayOrOlder(date)
    }

    def "should return true for yesterday date"() {
        given:
            def date = parse(LocalDate.now().minusDays(1L).toString())
        expect:
            dateProvider.isYesterdayOrOlder(date)
    }

    def "should return start of previous working day"() {
        when:
            def date = dateProvider.getStartOfPreviousWorkingDay()
        then:
            def yesterday = DateUtils.addDays(new Date(), -1)
            def yesterdayDayOfWeek = yesterday.toCalendar().get(Calendar.DAY_OF_WEEK)
            switch (yesterdayDayOfWeek) {
                case Calendar.SATURDAY:
                    yesterday = DateUtils.addDays(yesterday, -1)
                    break
                case Calendar.SUNDAY:
                    yesterday = DateUtils.addDays(yesterday, -2)
                    break
            }
            def yesterdayCalendar = yesterday.toCalendar()
        expect:
            def calendar = date.toCalendar()
            calendar.get(Calendar.DAY_OF_MONTH) == yesterdayCalendar.get(Calendar.DAY_OF_MONTH)
            calendar.get(Calendar.MONTH) == yesterdayCalendar.get(Calendar.MONTH)
            calendar.get(Calendar.YEAR) == yesterdayCalendar.get(Calendar.YEAR)
            calendar.get(Calendar.HOUR_OF_DAY) == 0
            calendar.get(Calendar.MINUTE) == 0
            calendar.get(Calendar.SECOND) == 0
    }

    def "should return start of previous working day for mocked current date"() {
        given:
            def dateProviderMocked = new DateProviderMock()
            dateProviderMocked.CURRENT_DATE = currentDate
        when:
            def date = dateProviderMocked.getStartOfPreviousWorkingDay()
        then:
            def calendar = date.toCalendar()
            calendar.get(Calendar.MONTH) == month
            calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth
            calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek
            calendar.get(Calendar.YEAR) == 2018
            calendar.get(Calendar.HOUR_OF_DAY) == 0
            calendar.get(Calendar.MINUTE) == 0
            calendar.get(Calendar.SECOND) == 0
        where:
            currentDate  | month         | dayOfMonth | dayOfWeek
            "2018-07-01" | Calendar.JUNE | 29         | Calendar.FRIDAY
            "2018-07-13" | Calendar.JULY | 12         | Calendar.THURSDAY
            "2018-07-14" | Calendar.JULY | 13         | Calendar.FRIDAY
            "2018-07-15" | Calendar.JULY | 13         | Calendar.FRIDAY
            "2018-07-16" | Calendar.JULY | 13         | Calendar.FRIDAY
            "2018-07-17" | Calendar.JULY | 16         | Calendar.MONDAY
    }


}
