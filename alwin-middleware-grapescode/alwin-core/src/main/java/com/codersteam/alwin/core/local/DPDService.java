package com.codersteam.alwin.core.local;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.alwin.jpa.issue.IssueInvoice;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Serwis do wyznaczania DPD zlecenia
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface DPDService {

    /**
     * Wyznaczenie daty, od której będzie liczone DPD dla zlecenia utworzonego na podstawie poprzedniego zlecenia, w ramach którego nie udało się wyegzekwować
     * całej zaległej kwoty
     *
     * @param issueInvoices   - kolekcja faktur zlecenia potomnego
     * @param extCompanyId    - identyfikator firmy (AIDA)
     * @param minBalanceStart - progowa wartość salda do rozpoczęcia zlecenia
     * @return data, od której należy liczyć DPD dla zlecenia potomnego, null jeśli żaden z dokumentów nie spełnia warunku kwotowego do rozpoczęcia zlecenia
     */
    Date calculateDPDStartDateForChildIssue(List<IssueInvoice> issueInvoices, Long extCompanyId, BigDecimal minBalanceStart);

    /**
     * Wyznaczenie daty, od której będzie liczone DPD dla nowo tworzonego zlecenia (automatycznie lub manualnie)
     *
     * @param startInvoices     - faktury z datą płatności przed datą rozpoczęcia zlecenia (now)
     * @param extCompanyId      - identyfikator firmy (AIDA)
     * @param minBalanceStart
     * @return data, od której należy liczyć DPD dla zlecenia, null jeśli żaden z dokumentów nie spełnia warunku kwotowego do rozpoczęcia zlecenia (manualne)
     */
    Date calculateDPDStartDateForNewIssue(List<AidaInvoiceWithCorrectionsDto> startInvoices, Long extCompanyId, BigDecimal minBalanceStart);
}
