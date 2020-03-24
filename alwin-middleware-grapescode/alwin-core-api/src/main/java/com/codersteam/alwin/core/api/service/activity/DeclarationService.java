package com.codersteam.alwin.core.api.service.activity;

import javax.ejb.Local;

/**
 * Serwis do aktualizacji spłaconych kwot wszystkich deklaracji zlecenia
 *
 * @author Piotr Naroznik
 */
@Local
public interface DeclarationService {

    /**
     * Aktualizacja spłaconych kwot wszystkich deklaracji zlecenia
     *
     * @param issueId - identyfikator zlecenia
     */
    void updateIssueDeclarations(final Long issueId);
}
