package com.codersteam.alwin.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import static com.codersteam.alwin.common.AlwinConstants.SYSTEM_DEFAULT_TIME_ZONE;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Klasa utilowa do obsługi dat
 */
public class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT_WITHOUT_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(TimeZone.getTimeZone(SYSTEM_DEFAULT_TIME_ZONE).toZoneId());

    /**
     * Różnica dat równa jeden dzień
     */
    private static final int ONE_DAY = 1;

    private DateUtils() {
    }

    public static String getFormattedStringFromDate(final Date date, final String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * Dodawanie dni do podanej daty
     *
     * @param date - data
     * @param days - ilość dni do dodania
     * @return data przesunięta o podaną liczbę dni
     */
    public static Date datePlusDays(final Date date, final long days) {
        final LocalDate localDate = dateToLocalDate(date);
        return localDate != null ? Date.from(localDate.plusDays(days).atStartOfDay(SYSTEM_DEFAULT_TIME_ZONE).toInstant()) : null;
    }

    /**
     * Dodanie jednego dnia do podanej daty
     *
     * @param date - data
     * @return data przesunięta o jeden dzień
     */
    public static Date datePlusOneDay(final Date date) {
        return datePlusDays(date, ONE_DAY);
    }

    /**
     * Konwersja Date na LocalDate
     *
     * @param date - data klasy java.util.Date
     * @return data klasy java.time.LocalDate
     */
    public static LocalDate dateToLocalDate(final Date date) {
        return date != null ? date.toInstant().atZone(SYSTEM_DEFAULT_TIME_ZONE).toLocalDate() : null;
    }

    /**
     * Konwersja Date na LocalDateTime
     *
     * @param date - data klasy java.util.Date
     * @return data klasy java.time.LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(final Date date) {
        return date != null ? date.toInstant().atZone(SYSTEM_DEFAULT_TIME_ZONE).toLocalDateTime() : null;
    }

    /**
     * Obliczenie ilości dni pomiędzy podanymi datami
     *
     * @param fromDate - data początkowa
     * @param toDate   - data końcowa
     * @return liczba dni pomiędzy dwiema datami
     */
    public static Long daysBetween(final Date fromDate, final Date toDate) {
        final LocalDate fromLocalDate = dateToLocalDate(fromDate);
        final LocalDate toLocalDate = dateToLocalDate(toDate);
        return DAYS.between(fromLocalDate, toLocalDate);
    }

    /**
     * Obliczenie różnicy w minutach między dwiema datami
     *
     * @param fromDate - data od
     * @param toDate   - data do
     * @return różnica w minutach
     */
    public static long diffInMinutes(final Date fromDate, final Date toDate) {
        return MINUTES.between(dateToLocalDateTime(fromDate), dateToLocalDateTime(toDate));
    }

    /**
     * Sprawdza czy data jest równa dziś lub starsza
     *
     * @param date        - data do sprawdzenia
     * @param currentDate - data aktualna
     * @return
     */
    public static boolean isBeforeOrSameDate(final Date date, final Date currentDate) {
        return daysBetween(date, currentDate) >= 0;
    }

    /**
     * Sprawdza czy data jest starsza niż podana dzisiejsza
     *
     * @param date        - data do sprawdzenia
     * @param currentDate - data aktualna
     * @return
     */
    public static boolean isBefore(final Date date, final Date currentDate) {
        return daysBetween(date, currentDate) > 0;
    }

    /**
     * Data jako string bez godzin
     *
     * @param date - data
     * @return data jako string
     */
    public static String dateToStringWithoutTime(final Date date) {
        if (date == null) {
            return "b/d";
        }
        return DATE_FORMAT_WITHOUT_TIME.format(date.toInstant());
    }
}
