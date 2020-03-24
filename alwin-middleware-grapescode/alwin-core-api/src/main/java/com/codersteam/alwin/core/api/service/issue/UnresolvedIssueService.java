package com.codersteam.alwin.core.api.service.issue;

import javax.ejb.Local;

/**
 * Klasa do wyszukiwania nierozwiązanych zleceń podczas windykacji telefonicznej
 *
 * @author Piotr Naroznik
 */
@Local
public interface UnresolvedIssueService {

    /**
     * Wyszukanie i wysłanie do Managera listy nierozwiązanych zleceń podczas windykacji telefonicznej
     */
    void findUnresolvedIssuesAndSendReportEmail();
}