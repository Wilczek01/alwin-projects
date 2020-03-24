package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.search.criteria.InvoiceSearchCriteria;
import com.codersteam.alwin.common.sort.InvoiceSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.criteria.InvoiceCriteriaBuilder;
import com.codersteam.alwin.jpa.issue.Invoice;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Klasa dostępu do faktur
 *
 * @author Tomasz Sliwinski
 */
@Stateless
public class InvoiceDao extends AbstractAuditDao<Invoice> {

    /**
     * Zwraca listę numerów faktur, które istnieją w systemie
     *
     * @param invoiceNumbersToCheck lista numerów faktur do sprawdzenia
     * @return lista identyfikatorów, które istnieją w systemie
     */
    public List<String> findExistingInvoiceNumbers(final Collection<String> invoiceNumbersToCheck) {
        if (invoiceNumbersToCheck == null || invoiceNumbersToCheck.isEmpty()) {
            return emptyList();
        }

        return entityManager
                .createQuery("SELECT i.number FROM Invoice i WHERE i.number in (:invoiceNumbersToCheck)", String.class)
                .setParameter("invoiceNumbersToCheck", invoiceNumbersToCheck)
                .getResultList();
    }

    /**
     * Zwraca listę faktur po identyfikatorze zlecenia z dodatkowym filtrowaniem
     *
     * @param issueId        - identyfikator zlecenia
     * @param searchCriteria - kryteria filtrowania
     * @param sortCriteria   - kryteria sortowania
     * @return - lista faktur
     */
    public List<Invoice> findInvoiceForIssueId(long issueId,
                                               InvoiceSearchCriteria searchCriteria,
                                               Map<InvoiceSortField, SortOrder> sortCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Invoice> criteriaQuery = InvoiceCriteriaBuilder.createFindInvoiceForIssueIdQuery(issueId, searchCriteria, criteriaBuilder, sortCriteria);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(searchCriteria.getFirstResult())
                .setMaxResults(searchCriteria.getMaxResults())
                .getResultList();
    }

    /**
     * Zwraca liczbę faktur po identyfikatorze zlecenia z dodatkowym filtrowaniem
     *
     * @param issueId        - identyfikator zlecenia
     * @param searchCriteria - kryteria filtrowania
     * @return - lista faktur
     */
    public Long findInvoiceForIssueIdCount(Long issueId,
                                           InvoiceSearchCriteria searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = InvoiceCriteriaBuilder.createCountQuery(issueId, searchCriteria, criteriaBuilder);

        return entityManager.createQuery(countQuery).getSingleResult();

    }

    /**
     * Zwraca fakturę po identyfikatorze zlecenia i numerze faktury
     *
     * @param issueId       - identyfikator zlecenia
     * @param invoiceNumber - numer faktury
     * @return - faktura
     */
    public Optional<Invoice> findInvoiceForIssueIdAndInvoiceNo(final Long issueId, final String invoiceNumber) {
        final List<Invoice> query = entityManager
                .createQuery("SELECT i FROM Invoice i, IssueInvoice ii WHERE i.number = :invoiceNumber " +
                        "and ii.pk.issue.id = :issueId " +
                        "and ii.pk.invoice.id = i.id", Invoice.class)
                .setParameter("issueId", issueId)
                .setParameter("invoiceNumber", invoiceNumber)
                .getResultList();

        return checkForSingleResult(query);
    }

    /**
     * Zwraca listę faktur po identyfikatorze zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return - lista faktur
     */
    public List<Invoice> findInvoicesByIssueId(final Long issueId) {
        final TypedQuery<Invoice> query = entityManager
                .createQuery("SELECT i FROM Invoice i, IssueInvoice ii WHERE ii.pk.invoice.id = i.id AND ii.pk.issue.id = :issueId ORDER BY i.id",
                        Invoice.class)
                .setParameter("issueId", issueId);

        return query.getResultList();
    }

    /**
     * Zwraca fakturę po numerze faktury
     *
     * @param invoiceNo - numer faktury
     * @return - faktura
     */
    public Optional<Invoice> findInvoiceByNumber(final String invoiceNo) {

        final List<Invoice> invoice = entityManager.createQuery("SELECT i FROM Invoice i WHERE i.number = :invoiceNo", Invoice.class)
                .setParameter("invoiceNo", invoiceNo)
                .getResultList();

        return checkForSingleResult(invoice);
    }

    @Override
    public Class<Invoice> getType() {
        return Invoice.class;
    }
}
