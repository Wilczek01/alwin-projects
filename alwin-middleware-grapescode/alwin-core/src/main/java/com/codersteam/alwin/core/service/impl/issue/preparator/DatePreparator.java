package com.codersteam.alwin.core.service.impl.issue.preparator;

import com.codersteam.alwin.jpa.issue.Issue;

import java.util.Date;

/**
 * @author Tomasz Sliwinski
 */
public final class DatePreparator {

    private DatePreparator() {
    }

    /**
     * Uzupełnienie dat w zleceniu
     *
     * @param issue          - zlecenie
     * @param startDate      - data rozpoczęcia zlecenia
     * @param expirationDate - data wygaśnięcia zlecenia
     * @param dpdStartDate   - data, od której jest liczone DPD zlecenia
     * @return zlecenie z uzupełnionymi datami
     */
    public static Issue fillDates(final Issue issue, final Date startDate, final Date expirationDate, final Date dpdStartDate) {
        issue.setStartDate(startDate);
        issue.setExpirationDate(expirationDate);
        issue.setDpdStartDate(dpdStartDate);
        return issue;
    }
}
