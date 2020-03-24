package com.codersteam.alwin.core.api.model.email;

import com.codersteam.alwin.core.api.model.issue.UnresolvedIssueDto;

import java.util.Date;
import java.util.List;

/**
 * Klasa komunikatu zawierająca zestawienie nierozwiązanych zleceń podczas windykacji telefonicznej
 *
 * @author Piotr Naroznik
 */
public class UnresolvedIssuesEmailMessage extends EmailData {

    private final List<UnresolvedIssueDto> unresolvedIssues;
    private final Date dateFrom;
    private final Date dateTo;

    public UnresolvedIssuesEmailMessage(final List<String> emailRecipients, final Date dateFrom, final Date dateTo, final List<UnresolvedIssueDto> unresolvedIssues) {
        super(emailRecipients);
        this.unresolvedIssues = unresolvedIssues;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public List<UnresolvedIssueDto> getUnresolvedIssues() {
        return unresolvedIssues;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    @Override
    public EmailType getEmailType() {
        return EmailType.UNRESOLVED_ISSUES;
    }
}
