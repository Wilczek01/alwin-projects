package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.HolidayDao;
import com.codersteam.alwin.jpa.holiday.Holiday;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.time.LocalDate;
import java.util.List;

import static com.codersteam.alwin.testdata.HolidayTestData.ADDED_DAY;
import static com.codersteam.alwin.testdata.HolidayTestData.ADDED_MONTH;
import static com.codersteam.alwin.testdata.HolidayTestData.DAY_3;
import static com.codersteam.alwin.testdata.HolidayTestData.ID_1;
import static com.codersteam.alwin.testdata.HolidayTestData.MONTH_2;
import static com.codersteam.alwin.testdata.HolidayTestData.MONTH_3;
import static com.codersteam.alwin.testdata.HolidayTestData.NOT_EXISTING_ID;
import static com.codersteam.alwin.testdata.HolidayTestData.YEAR_2;
import static com.codersteam.alwin.testdata.HolidayTestData.YEAR_3;
import static com.codersteam.alwin.testdata.HolidayTestData.testAddedHoliday1;
import static com.codersteam.alwin.testdata.HolidayTestData.testHoliday1;
import static com.codersteam.alwin.testdata.HolidayTestData.testHoliday2;
import static com.codersteam.alwin.testdata.HolidayTestData.testHoliday3;
import static com.codersteam.alwin.testdata.HolidayTestData.testHoliday4;
import static com.codersteam.alwin.testdata.HolidayTestData.testHolidayToAdd;
import static com.codersteam.alwin.testdata.HolidayTestData.testModifiedHoliday1;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_ID_1;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json"})
public class HolidayDaoTestIT extends WriteTestBase {

    @EJB
    private HolidayDao dao;

    @Test
    public void shouldFindAllHolidaysPerDay() {
        // when
        final List<Holiday> holidays = dao.findAllHolidaysPerDay(ADDED_DAY, ADDED_MONTH, LocalDate.now().getYear(), null);

        // and
        dao.create(testHolidayToAdd());
        final List<Holiday> newHolidays = dao.findAllHolidaysPerDay(ADDED_DAY, ADDED_MONTH, LocalDate.now().getYear(), null);

        // then
        assertTrue(holidays.isEmpty());

        // and
        assertThat(newHolidays).isEqualToComparingFieldByFieldRecursively(singletonList(testAddedHoliday1()));
    }

    @Test
    public void shouldFindAllHolidaysPerMonth() {
        // when
        final List<Holiday> holidays = dao.findAllHolidaysPerMonth(MONTH_2, YEAR_2, null);

        // and
        dao.update(testModifiedHoliday1());
        final List<Holiday> newHolidays = dao.findAllHolidaysPerMonth(MONTH_2, YEAR_2, null);

        // then
        assertThat(holidays).isEqualToComparingFieldByFieldRecursively(singletonList(testHoliday2()));

        // and
        assertThat(newHolidays)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "user.updateDate", "user.creationDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "user.operators")
                .isEqualToComparingFieldByFieldRecursively(asList(testModifiedHoliday1(), testHoliday2()));
    }

    @Test
    public void shouldFindAllHolidaysPerYear() {
        // when
        final List<Holiday> holidays = dao.findAllHolidaysPerYear(YEAR_2, null);

        // and
        dao.delete(testHoliday1().getId());
        final List<Holiday> newHolidays = dao.findAllHolidaysPerYear(YEAR_2, null);

        // then
        assertThat(holidays)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "user.updateDate", "user.creationDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "user.operators")
                .isEqualToComparingFieldByFieldRecursively(asList(testHoliday1(), testHoliday2(), testHoliday3(), testHoliday4()));

        // and
        assertThat(newHolidays)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "user.updateDate", "user.creationDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "user.operators")
                .isEqualToComparingFieldByFieldRecursively(asList(testHoliday2(), testHoliday3(), testHoliday4()));
    }

    @Test
    public void shouldFindAllHolidaysPerDayForUser() {
        // when
        final List<Holiday> holidays = dao.findAllHolidaysPerDay(DAY_3, MONTH_3, YEAR_3, TEST_USER_ID_1);

        // then
        assertThat(holidays)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "user.updateDate", "user.creationDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "user.operators")
                .isEqualToComparingFieldByFieldRecursively(singletonList(testHoliday3()));
    }

    @Test
    public void shouldFindAllHolidaysPerMonthForUser() {
        // when
        final List<Holiday> holidays = dao.findAllHolidaysPerMonth(MONTH_3, YEAR_3, TEST_USER_ID_1);

        // then
        assertThat(holidays)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "user.updateDate", "user.creationDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "user.operators")
                .isEqualToComparingFieldByFieldRecursively(asList(testHoliday1(), testHoliday3()));
    }

    @Test
    public void shouldFindAllHolidaysPerYearForUser() {
        // when
        final List<Holiday> holidays = dao.findAllHolidaysPerYear(YEAR_3, TEST_USER_ID_1);

        // then
        assertThat(holidays)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "user.updateDate", "user.creationDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "user.operators.user")
                .isEqualToComparingFieldByFieldRecursively(asList(testHoliday1(), testHoliday2(), testHoliday3()));
    }

    @Test
    public void shouldCheckIfHolidayExists() {
        // when
        final boolean result = dao.exists(ID_1);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldCheckIfHolidayDoesNotExists() {
        // when
        final boolean result = dao.exists(NOT_EXISTING_ID);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Holiday> type = dao.getType();

        // then
        assertEquals(Holiday.class, type);
    }
}
