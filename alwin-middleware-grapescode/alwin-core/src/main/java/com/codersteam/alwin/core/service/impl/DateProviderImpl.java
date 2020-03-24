package com.codersteam.alwin.core.service.impl;

import com.codersteam.alwin.core.api.service.DateProvider;

import javax.ejb.Singleton;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * @author Tomasz Sliwinski
 */
@Singleton
public class DateProviderImpl implements DateProvider {

    /**
     * Zwraca aktualną datę według według zegara systemowego w domyślnej strefie czasowej.
     */
    protected LocalDate getCurrentLocalDate() {
        return LocalDate.now();
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
        return new Date();
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
