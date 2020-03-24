package com.codersteam.alwin.integration.mock;

import com.codersteam.alwin.core.api.service.DateProvider;

import javax.ejb.Singleton;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;

/**
 * @author Michal Horowic
 */
@SuppressWarnings("ALL")
@Singleton
public class DateProviderMock implements DateProvider {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String DEFAULT_DATE = "2012-12-12";
    /**
     * Dzisiejsza data na potrzeby testów, możliwa do ustawienia z poziomu klasy testu.
     * Format: yyyy-MM-dd
     */
    public static String CURRENT_DATE = DEFAULT_DATE;

    public static final void reset() {
        CURRENT_DATE = DEFAULT_DATE;
    }

    protected LocalDate getCurrentLocalDate() {
        return LocalDate.parse(CURRENT_DATE, FORMATTER);
    }

    @Override
    public Date getCurrentDateStartOfDay() {
        final LocalDate localDate = getCurrentLocalDate();
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Date getCurrentDateEndOfDay() {
        final LocalDate localDate = getCurrentLocalDate();
        return Date.from(localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Date getCurrentDate() {
        return parse(CURRENT_DATE);
    }

    @Override
    public Date getDateStartOfDayMinusDays(final int numberOfDays) {
        final LocalDate localDate = getCurrentLocalDate().minusDays(numberOfDays);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Date getDateStartOfDayPlusDays(final int numberOfDays) {
        final LocalDate localDate = getCurrentLocalDate().plusDays(numberOfDays);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public boolean isYesterdayOrOlder(final Date date) {
        final LocalDate localDate = toLocalDate(date);
        final LocalDate yesterday = getYesterday();
        return localDate.equals(yesterday) || localDate.isBefore(yesterday);
    }

    @Override
    public boolean isYesterday(final Date date) {
        final LocalDate localDate = toLocalDate(date);
        final LocalDate yesterday = getYesterday();
        return localDate.equals(yesterday);
    }

    @Override
    public Date getStartOfPreviousWorkingDay() {
        final LocalDate localDate = getCurrentLocalDate();
        final DayOfWeek currentDayOfWeek = DayOfWeek.of(localDate.get(ChronoField.DAY_OF_WEEK));
        final LocalDate resultDate;
        switch (currentDayOfWeek) {
            case MONDAY:
                resultDate = localDate.minusDays(3);
                break;
            case SUNDAY:
                resultDate = localDate.minusDays(2);
                break;
            default:
                resultDate = localDate.minusDays(1);
        }
        return Date.from(resultDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate getYesterday() {
        return getCurrentLocalDate().minusDays(1L);
    }

    private LocalDate toLocalDate(final Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
