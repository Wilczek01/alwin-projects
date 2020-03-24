package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.exception.IssueBalanceUpdateWaitException;
import com.codersteam.alwin.core.api.model.issue.IssueDto;

import javax.ejb.Local;

/**
 * Serwis do aktualizacji sald
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface IssueBalanceUpdaterService {

    /**
     * Pobranie zlecenia po uprzednim zaktualizowaniu salda.
     * <p>
     * Saldo zlecenia nie jest aktualizowane, jeśli od ostatniego przeliczenia nie minęło więcej niż 15 minut.
     * Jeśli żądanie dotyczy zlecenia, które jest już aktualnie przeliczane proces czeka, aż się ono zakończy.
     *
     * @param issueId - identyfikator zlecenia
     * @return zlecenie ze zaktualizowanym saldem
     * @throws IssueBalanceUpdateWaitException w przypadku błędów z oczekiwaniem na przeliczenie salda
     */
    IssueDto getIssueWithUpdatedBalance(Long issueId);

    /**
     * Uaktualnienie salda zlecenia.
     *
     * @param issueId - identyfikator zlecenia
     */
    void updateIssueBalance(Long issueId);

}
