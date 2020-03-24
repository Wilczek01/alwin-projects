package com.codersteam.alwin.core.service.impl.holiday

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.HolidayDao
import com.codersteam.alwin.jpa.holiday.Holiday
import com.codersteam.alwin.testdata.UserTestData
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.testdata.HolidayTestData.*
import static java.util.Arrays.asList
import static java.util.Collections.singletonList
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Michal Horowic
 */
class HolidayServiceImplTest extends Specification {

    def dao = Mock(HolidayDao)

    @Subject
    private HolidayServiceImpl service

    def setup() {
        service = new HolidayServiceImpl(dao, new AlwinMapper())
    }

    def "should find all holidays per day"() {
        given:
            dao.findAllHolidaysPerDay(DAY_2, MONTH_2, YEAR_2, null) >> [testHoliday2()]

        when:
            def holidays = service.findAllHolidaysPerDay(DAY_2, MONTH_2, YEAR_2, null)

        then:
            assertThat(holidays).isEqualToComparingFieldByFieldRecursively(singletonList(testHolidayDto2()))

    }

    def "should not find holidays per day"() {
        given:
            dao.findAllHolidaysPerDay(DAY_2, MONTH_2, YEAR_2, null) >> []

        when:
            def holidays = service.findAllHolidaysPerDay(DAY_2, MONTH_2, YEAR_2, null)

        then:
            holidays.isEmpty()

    }

    def "should find all holidays per month"() {
        given:
            dao.findAllHolidaysPerMonth(MONTH_2, YEAR_2, UserTestData.TEST_USER_ID_1) >> [testHoliday2()]

        when:
            def holidays = service.findAllHolidaysPerMonth(MONTH_2, YEAR_2, UserTestData.TEST_USER_ID_1)

        then:
            assertThat(holidays).isEqualToComparingFieldByFieldRecursively(singletonList(testHolidayDto2()))
    }

    def "should not find holidays per month"() {
        given:
            dao.findAllHolidaysPerMonth(MONTH_2, YEAR_2, null) >> []

        when:
            def holidays = service.findAllHolidaysPerMonth(MONTH_2, YEAR_2, null)

        then:
            holidays.isEmpty()
    }

    def "should find all holidays per year"() {
        given:
            dao.findAllHolidaysPerYear(YEAR_2, null) >> [testHoliday1(), testHoliday2()]

        when:
            def holidays = service.findAllHolidaysPerYear(YEAR_2, null)

        then:
            assertThat(holidays).isEqualToComparingFieldByFieldRecursively(asList(testHolidayDto1(), testHolidayDto2()))
    }

    def "should not find holidays per year"() {
        given:
            dao.findAllHolidaysPerYear(YEAR_2, null) >> []

        when:
            def holidays = service.findAllHolidaysPerYear(YEAR_2, null)

        then:
            holidays.isEmpty()
    }

    def "should update existing holiday"() {
        when:
            service.updateHoliday(ID_1, testHolidayInputDto1())

        then:
            1 * dao.update(_ as Holiday) >> { args ->
                def holiday = (Holiday) args[0]
                assertThat(holiday).isEqualToComparingFieldByFieldRecursively(testHoliday1())
                holiday
            }
    }

    def "should create new holiday"() {
        when:
            service.createNewHoliday(testHolidayToAddDto())

        then:
            1 * dao.create(_ as Holiday) >> { args ->
                def holiday = (Holiday) args[0]
                assertThat(holiday).isEqualToComparingFieldByFieldRecursively(testHolidayToAdd())
                holiday
            }
    }

    def "should delete existing holiday"() {
        when:
            service.deleteHoliday(ID_1)

        then:
            1 * dao.delete(ID_1)
    }

    @Unroll
    def "should check if holiday exists"() {
        given:
            dao.exists(holidayId) >> exists
        when:
            def result = service.checkIfHolidayExists(holidayId)

        then:
            result == exists

        where:
            holidayId | exists
            ID_1      | true
            ID_2      | false
    }
}
