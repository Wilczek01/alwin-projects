package com.codersteam.alwin.core.api.service;

import javax.ejb.Local;
import java.util.Date;

/**
 * @author Michal Horowic
 */
@Local
public interface DateProvider {

    Date getCurrentDateStartOfDay();

    Date getCurrentDateEndOfDay();

    Date getCurrentDate();

    Date getDateStartOfDayMinusDays(final int numberOfDays);

    Date getDateStartOfDayPlusDays(final int numberOfDays);

    /**
     * Zwraca początek poprzedniego dnia roboczego
     */
    Date getStartOfPreviousWorkingDay();

    /**
     * Sprawdza, czy podana data jest datą wczorajszą lub starszą
     *
     * @param date - data
     * @return czy data jest datą wczorajszą
     */
    boolean isYesterdayOrOlder(Date date);

    /**
     * Sprawdza, czy podana data jest datą wczorajszą
     *
     * @param date - data
     * @return czy data jest datą wczorajszą
     */
    boolean isYesterday(Date date);

}
