package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.IssueInvoice;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa dostępu do tabeli wiążącej zlecenia z fakturami
 *
 * @author Adam Stepnowski
 */
@Stateless
public class IssueInvoiceDao extends AbstractDao<IssueInvoice> {

    /**
     * Aktualizuje saldo końcowe na podstawie danych z tabeli zawierającej faktury
     *
     * @param issueId - identyfikator zlecenia
     */
    public void updateIssueInvoicesFinalBalance(final Long issueId) {
        final Query query = entityManager.createQuery("UPDATE IssueInvoice ii SET ii.finalBalance = (SELECT i.currentBalance FROM Invoice i " +
                "WHERE ii.pk.invoice.id = i.id) WHERE ii.pk.issue.id = :issueId");

        query.setParameter("issueId", issueId);
        query.executeUpdate();
    }

    /**
     * Wykluczanie i przywracanie faktury w obsłudze zlecenia
     *
     * @param issueId   - identyfikator zlecenia
     * @param invoiceId - identyfikator faktury
     * @param excluded  - czy wykluczyć fakturę z obsługi zlecenia
     */
    public void updateIssueInvoicesExclusion(final Long issueId, final Long invoiceId, final boolean excluded) {
        final Query query = entityManager.createQuery("UPDATE IssueInvoice ii SET ii.excluded = :excluded WHERE ii.pk.issue.id = :issueId " +
                "and ii.pk.invoice.id = :invoiceId");

        query.setParameter("issueId", issueId);
        query.setParameter("invoiceId", invoiceId);
        query.setParameter("excluded", excluded);
        query.executeUpdate();
    }

    /**
     * Pobranie numerów wszystkich wykluczonych faktur z obsługi zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return Lista numerów wykluczonych faktur
     */
    public List<Invoice> findInvoicesOutOfService(final Long issueId) {
        final TypedQuery<Invoice> query = entityManager.createQuery("SELECT ii.pk.invoice FROM IssueInvoice ii  WHERE ii.pk.issue.id = :issueId " +
                "AND ii.excluded = true", Invoice.class);
        query.setParameter("issueId", issueId);
        return query.getResultList();
    }

    @Override
    public Class<IssueInvoice> getType() {
        return IssueInvoice.class;
    }
}
