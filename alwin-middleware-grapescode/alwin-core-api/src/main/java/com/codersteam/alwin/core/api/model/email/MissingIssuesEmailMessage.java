package com.codersteam.alwin.core.api.model.email;

import com.codersteam.alwin.core.api.model.issue.MissingIssueDto;

import java.util.Date;
import java.util.List;

/**
 * Klasa komunikatu zawierająca zestawienie dokumentów, na podstawie których należy utworzyć zlecenia
 *
 * @author Tomasz Sliwinski
 */
public class MissingIssuesEmailMessage extends EmailData {

    private final List<MissingIssueDto> missingIssueDtos;
    private final Date dateFrom;
    private final Date dateTo;

    public MissingIssuesEmailMessage(final List<String> emailRecipients, final Date dateFrom, final Date dateTo, final List<MissingIssueDto> missingIssueDtos) {
        super(emailRecipients);
        this.missingIssueDtos = missingIssueDtos;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public List<MissingIssueDto> getMissingIssueDtos() {
        return missingIssueDtos;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    @Override
    public EmailType getEmailType() {
        return EmailType.MISSING_ISSUES;
    }
}
