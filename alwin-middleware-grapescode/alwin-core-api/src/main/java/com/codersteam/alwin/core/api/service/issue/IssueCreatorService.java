package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;

import javax.ejb.Local;
import java.util.Date;

/**
 * Serwis do tworzenia zleceń
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface IssueCreatorService {

    /**
     * Utworzenie nowego zlecenia przez użytkownika systemu
     *
     * @param extCompanyId   - identyfikator firmy w systemie AIDA
     * @param issueTypeId    - identyfikator typu zlecenia
     * @param expirationDate - data zakończenia zlecenia
     * @param assigneeId     - identyfikator operatora, do którego ma być przypisane zlecenie
     * @return rezultat utworzenia zlecenia
     */
    IssueCreateResult createIssueManually(Long extCompanyId, Long issueTypeId, Date expirationDate, Long assigneeId);

    /**
     * Utworzenie nowego zlecenia odpowiedniego typu przez system
     *
     * @param extCompanyId       - identyfikator firmy w systemie AIDA
     * @param issueConfiguration - konfiguracja typu zlecenia
     */
    void createIssue(Long extCompanyId, IssueTypeConfigurationDto issueConfiguration);

}
