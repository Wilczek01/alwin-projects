package com.codersteam.alwin.core.api.service.email;

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;

import javax.ejb.Local;

/**
 * Serwis do kolejkowania wysyłki maili
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface EmailSendMessageEnqueueService {

    /**
     * Zakolejkowanie informacji o dokumentach do utworzenia pominiętych zleceń
     *
     * @param missingIssuesEmailMessage dane dotyczące dokumentów przekraczających limit rozpoczęcia windykacji
     */
    void enqueueMissingIssuesEmail(MissingIssuesEmailMessage missingIssuesEmailMessage);

    /**
     * Zakolejkowanie informacji o nierozwiązanych zleceniach podczas windykacji telefonicznej, których czas obsługi dobiegł końca
     *
     * @param unresolvedIssuesEmailMessage dane dotyczące nierozwiązanych zleceń podczas windykacji telefonicznej
     */
    void enqueueUnresolvedIssuesEmail(UnresolvedIssuesEmailMessage unresolvedIssuesEmailMessage);
}
