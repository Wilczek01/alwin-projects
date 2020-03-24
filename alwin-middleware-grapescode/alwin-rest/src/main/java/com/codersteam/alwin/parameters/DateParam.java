package com.codersteam.alwin.parameters;

import com.codersteam.alwin.exception.AlwinValidationException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * @author Adam Stepnowski
 */
public class DateParam {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String WRONG_DATE_VALUE_MESSAGE = "Niepoprawna wartość dla daty '%s'";
    private Date dateFromString;

    public DateParam(final String dateAsString) {
        try {
            final LocalDate date = LocalDate.parse(dateAsString, FORMATTER);
            dateFromString = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (final DateTimeParseException e) {
            throw new AlwinValidationException(String.format(WRONG_DATE_VALUE_MESSAGE, dateAsString));
        } catch (final NullPointerException e) {
            dateFromString = null;
        }
    }

    public Date getDate() {
        return dateFromString;
    }

    @Override
    public String toString() {
        if (dateFromString != null) {
            return dateFromString.toString();
        } else {
            return "";
        }
    }
}
