package com.codersteam.alwin.core.local;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Piotr Naroznik
 */
@Local
public interface LocalInvoiceService {
    /**
     * Tworzenie faktur z korektami i przypisywanie ich do zlecenia
     *
     * @param aidaInvoiceDtos       - lista faktur z korektami
     * @param issue                 - zlecenie
     * @param contractsOutOfService - umowy wyłączone z obsługi
     * @return lista faktur zlecenia
     */
    List<IssueInvoice> prepareInvoices(Collection<AidaInvoiceWithCorrectionsDto> aidaInvoiceDtos, Issue issue, Set<String> contractsOutOfService);

    /**
     * Tworzenie faktury z korektami i przypisywanie do zlecenia
     *
     * @param issue                         - zlecenie
     * @param invoiceDto                    - faktura z korektami
     * @param includeDueInvoicesDuringIssue - czy dołączać zaległe dokumenty w trakcie trwania zlecenia
     * @return faktura zlecenia
     */
    IssueInvoice prepareIssueInvoice(Issue issue, AidaInvoiceWithCorrectionsDto invoiceDto, boolean includeDueInvoicesDuringIssue);

    /**
     * Wyznaczenie czy dla danego zlecenia należy dołączać dłużne dokumenty jako przedmiot zlecenia w trakcie jego trwania
     *
     * @param issue - zlecenie
     * @return <code>true</code> jeśli należy dołączać zaległe dokumenty w trakcie trwania zlecenia jako przedmiot zlecenia
     */
    boolean isIncludeDueInvoicesDuringIssue(Issue issue);
}