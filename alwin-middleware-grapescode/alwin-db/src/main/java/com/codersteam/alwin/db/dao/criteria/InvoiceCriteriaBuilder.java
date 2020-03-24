package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.common.search.criteria.InvoiceSearchCriteria;
import com.codersteam.alwin.common.sort.InvoiceSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.jpa.issue.*;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Stepnowski
 */
public final class InvoiceCriteriaBuilder {

    private InvoiceCriteriaBuilder() {
    }

    public static CriteriaQuery<Invoice> createFindInvoiceForIssueIdQuery(long issueId,
                                                                          InvoiceSearchCriteria searchCriteria,
                                                                          CriteriaBuilder criteriaBuilder,
                                                                          Map<InvoiceSortField, SortOrder> sortCriteria) {
        CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);

        Root<Invoice> invoice = criteriaQuery.from(Invoice.class);
        Join<Invoice, IssueInvoice> issueInvoices = invoice.join(Invoice_.issueInvoices);

        List<Predicate> predicates = getPredicates(issueInvoices, invoice, issueId, searchCriteria, criteriaBuilder);
        List<Order> orders = buildSortOrders(criteriaBuilder, invoice, issueInvoices, sortCriteria);

        return criteriaQuery.select(invoice)
                .orderBy(orders)
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    }

    private static List<Order> buildSortOrders(CriteriaBuilder criteriaBuilder,
                                               Root<Invoice> invoice,
                                               Join<Invoice, IssueInvoice> issueInvoices,
                                               Map<InvoiceSortField, SortOrder> sortCriteria) {
        List<Order> orders = new ArrayList<>();
        for (final InvoiceSortField field : sortCriteria.keySet()) {
            if (InvoiceSortField.EXCLUDED.equals(field)) {
                orders.add(buildOrder(criteriaBuilder, sortCriteria.get(field), issueInvoices.get(field.getColumn())));
            } else {
                orders.add(buildOrder(criteriaBuilder, sortCriteria.get(field), invoice.get(field.getColumn())));
            }
        }

        Order defaultOrder = criteriaBuilder.asc(invoice.get(Invoice_.id));
        orders.add(defaultOrder);

        return orders;
    }

    private static Order buildOrder(CriteriaBuilder cb,
                                    SortOrder sortOrder,
                                    Path<Object> path) {
        return SortOrder.ASC.equals(sortOrder) ? cb.asc(path) : cb.desc(path);
    }

    private static List<Predicate> getPredicates(Join<Invoice, IssueInvoice> issueInvoices,
                                                 Root<Invoice> invoice,
                                                 long issueId,
                                                 InvoiceSearchCriteria searchCriteria,
                                                 CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(issueInvoices.get(IssueInvoice_.pk).get(IssueInvoiceId_.issue).get(Issue_.id), issueId));

        if (searchCriteria.getContractNumber() != null) {
            predicates.add(cb.equal(invoice.get(Invoice_.contractNumber), searchCriteria.getContractNumber()));
        }
        if (searchCriteria.getTypeId() != null) {
            predicates.add(cb.equal(invoice.get(Invoice_.typeId), searchCriteria.getTypeId()));
        }
        if (searchCriteria.getStartDueDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(invoice.get(Invoice_.realDueDate), searchCriteria.getStartDueDate()));
        }
        if (searchCriteria.getEndDueDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(invoice.get(Invoice_.realDueDate), searchCriteria.getEndDueDate()));
        }
        if (searchCriteria.isNotPaidOnly()) {
            predicates.add(cb.lessThan(invoice.get(Invoice_.currentBalance), BigDecimal.ZERO));
        }
        if (searchCriteria.isOverdueOnly()) {
            predicates.add(cb.lessThanOrEqualTo(invoice.get(Invoice_.realDueDate), searchCriteria.getCurrentDate()));
        }
        if (searchCriteria.getIssueSubjectOnly()) {
            predicates.add(cb.equal(issueInvoices.get(IssueInvoice_.issueSubject), Boolean.TRUE));
        }
        return predicates;
    }

    public static CriteriaQuery<Long> createCountQuery(Long issueId,
                                                       InvoiceSearchCriteria searchCriteria,
                                                       CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Invoice> invoice = criteriaQuery.from(Invoice.class);
        Join<Invoice, IssueInvoice> issueInvoices = invoice.join(Invoice_.issueInvoices);

        List<Predicate> predicates = getPredicates(issueInvoices, invoice, issueId, searchCriteria, criteriaBuilder);

        return criteriaQuery.select(criteriaBuilder.countDistinct(invoice))
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

    }
}
