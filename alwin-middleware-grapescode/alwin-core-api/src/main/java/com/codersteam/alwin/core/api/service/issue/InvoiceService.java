package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.model.InvoiceEntryDto;
import com.codersteam.aida.core.api.model.InvoiceTypeDto;
import com.codersteam.alwin.common.search.criteria.InvoiceSearchCriteria;
import com.codersteam.alwin.common.sort.InvoiceSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.invoice.InvoicesWithTotalBalanceDto;
import com.codersteam.alwin.core.api.model.issue.InvoiceDto;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Stepnowski
 */
@Local
public interface InvoiceService {

    /**
     * Pobieranie faktur po identyfikatorze zlecenia z sumą salda
     *
     * @param issueId        - identyfikator zlecenia
     * @param searchCriteria - kryteria filtrowania
     * @param sortCriteria   - kryteria sortowania
     * @return faktury
     */
    InvoicesWithTotalBalanceDto findInvoicesWithTotalBalance(long issueId, InvoiceSearchCriteria searchCriteria,
                                                             Map<InvoiceSortField, SortOrder> sortCriteria);

    /**
     * Pobiera wszystkie typy faktur
     *
     * @return lista typów faktur
     */
    List<InvoiceTypeDto> findAllInvoiceTypes();

    /**
     * Pobieranie wszystkich faktur po identyfikatorze zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return faktury
     */
    List<InvoiceDto> findInvoicesForIssueId(Long issueId);

    /**
     * Pobranie pozycji faktury
     *
     * @param invoiceNo - numer faktury
     * @return lista pozycji
     */
    List<InvoiceEntryDto> findInvoiceEntries(String invoiceNo);

    /**
     * Pobiera nieopłacone faktury z zaległą datą płatności z AIDA po numerze umowy
     *
     * @param extCompanyId - numer klienta
     * @param contractNo   - numer umowy
     * @param dueDate      - data do której należało opłacić dokument
     * @return lista faktur
     */
    List<AidaInvoiceWithCorrectionsDto> getContractDueInvoices(Long extCompanyId, String contractNo, Date dueDate);
}
