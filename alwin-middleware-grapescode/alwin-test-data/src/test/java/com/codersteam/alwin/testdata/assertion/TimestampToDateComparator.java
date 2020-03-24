package com.codersteam.alwin.testdata.assertion;

import java.util.Comparator;
import java.util.Date;

/**
 * @author Tomasz Sliwinski
 */
public final class TimestampToDateComparator implements Comparator<Date> {

    public static final TimestampToDateComparator TIMESTAMP_TO_DATE_COMPARATOR = new TimestampToDateComparator();

    private TimestampToDateComparator() {
    }

    @Override
    public int compare(final Date date1, final Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        }
        if (date1 == null || date2 == null) {
            return 1;
        }
        return date1.getTime() == date2.getTime() ? 0 : 1;
    }
}
