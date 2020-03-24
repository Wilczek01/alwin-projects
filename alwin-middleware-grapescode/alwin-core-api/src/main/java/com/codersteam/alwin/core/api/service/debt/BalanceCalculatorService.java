package com.codersteam.alwin.core.api.service.debt;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.alwin.core.api.model.balance.BalanceDto;
import com.codersteam.alwin.core.api.model.balance.BalanceStartAndAdditionalDto;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.Balance;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Tomasz Sliwinski
 */
@Local
public interface BalanceCalculatorService {

    /**
     * Pobranie aktualnego salda dla firmy
     *
     * @param extCompanyId - identyfikator firmy w systemie AIDA
     * @return aktualne saldo z rozbiciem na składowe per waluta
     */
    Map<Currency, BalanceDto> currentBalance(Long extCompanyId);

    /**
     * Wyznaczenie salda nowego zlecenia na podstawie faktur z systemu AIDA
     * Wejściowa kolekcja faktur jest filtrowana i salda są liczone wyłącznie na podstawie dokumentów będących przedmiotem zlecenia
     *
     * @param issueId            - identyfikator zlecenia
     * @param extCompanyId       - identyfikator klienta (AIDA)
     * @param issueStartInvoices - lista wszystkich faktur klienta do czasu utworzenia zlecenia
     * @param issueStartDate     - data rozpoczęcia zlecenia
     * @return saldo zlecenia z rozbiciem na składowe per waluta
     */
    Map<Currency, Balance> calculateBalancesFromIssueSubjectInvoices(Long issueId, Long extCompanyId, List<AidaInvoiceWithCorrectionsDto> issueStartInvoices,
                                                                     Date issueStartDate);

    /**
     * Wyliczenie salda istniejącego zlecenia na podstawie jego faktur
     * UWAGA: zakładamy, że przed wywołaniem zlecenie posiada aktualne salda na dokumentach
     *
     * @param issueId - identyfikator zlecenia
     * @return saldo zlecenia z rozbiciem na składowe per waluta
     */
    Map<Currency, Balance> recalculateBalanceForIssue(Long issueId);

    /**
     * Wyliczenie salda wymaganego i niewymaganego dla zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return saldo wymagane i niewymagane per waluta
     */
    Map<Currency, BalanceStartAndAdditionalDto> calculateBalanceStartAndAdditional(Long issueId);

}
