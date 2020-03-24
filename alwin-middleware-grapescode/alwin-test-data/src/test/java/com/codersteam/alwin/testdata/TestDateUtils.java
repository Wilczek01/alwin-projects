package com.codersteam.alwin.testdata;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

import static com.codersteam.alwin.common.AlwinConstants.SYSTEM_DEFAULT_TIME_ZONE;

public final class TestDateUtils {

    private static final DateTimeFormatter DATE_FORMAT_WITHOUT_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(TimeZone.getTimeZone(SYSTEM_DEFAULT_TIME_ZONE).toZoneId());
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss.SSSSSS]").withZone(TimeZone.getTimeZone(SYSTEM_DEFAULT_TIME_ZONE).toZoneId());

    private TestDateUtils() {
    }

    public static Date parse(final String date) {
        if (date == null) {
            return null;
        }

        final TemporalAccessor temporal = DATE_FORMAT.parseBest(date, LocalDateTime::from, LocalDate::from);
        LocalDateTime dateTime;
        if (temporal instanceof LocalDateTime) {
            dateTime = (LocalDateTime) temporal;
        } else {
            dateTime = ((LocalDate) temporal).atStartOfDay();
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String formatWithoutTime(final Date date) {
        return DATE_FORMAT_WITHOUT_TIME.format(date.toInstant());
    }
}
