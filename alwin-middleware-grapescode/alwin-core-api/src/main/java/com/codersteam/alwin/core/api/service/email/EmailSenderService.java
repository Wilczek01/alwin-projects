package com.codersteam.alwin.core.api.service.email;

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;

import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 * @author Tomasz Sliwinski
 */
@Local
public interface EmailSenderService {

    /**
     * Wysłanie maila z informacją o brakujących zleceniach
     *
     * @param missingIssueEmail dane dokumentów, na których podsatwie należy założyć zlecenia
     */
    void sendMissingIssuesReportMail(MissingIssuesEmailMessage missingIssueEmail) throws MessagingException;

    /**
     * Wysłanie maila z informacją o nierozwiązanych zleceniach podczas windykacji telefonicznej
     *
     * @param unresolvedIssuesEmail dane nierozwiązanych zleceń podczas windykacji telefonicznej
     */
    void sendUnresolvedIssuesReportMail(UnresolvedIssuesEmailMessage unresolvedIssuesEmail) throws MessagingException;
}
