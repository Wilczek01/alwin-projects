package com.codersteam.alwin.core.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.util.DateUtils.dateToLocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Klasa utilowa do wyznaczania DPD
 *
 * @author Tomasz Sliwinski
 */
public final class DpdUtils {

    private DpdUtils() {
    }

    public static Long calculateInvoiceDpd(final BigDecimal balance, final Date dueDate, final Date lastPaymentDate, final Date forDate) {
        final LocalDate dueLocalDate = dateToLocalDate(dueDate);
        if (!documentPaid(balance)) {
            final LocalDate forLocalDate = dateToLocalDate(forDate);
            return DAYS.between(dueLocalDate, forLocalDate);
        }
        if (lastPaymentDate == null) {
            return 0L;
        }
        final LocalDate lastPaymentLocalDate = dateToLocalDate(lastPaymentDate);
        return DAYS.between(dueLocalDate, lastPaymentLocalDate);
    }

    private static boolean documentPaid(final BigDecimal balance) {
        return balance == null || ZERO.compareTo(balance) <= 0;
    }
}
