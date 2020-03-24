package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;
import com.codersteam.alwin.common.sort.DemandForPaymentSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.util.Lazy;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.Company_;
import com.codersteam.alwin.jpa.notice.*;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa tworząca warunki potrzebne do filtrowania wezwań do zapłaty
 *
 * @author Michal Horowic
 */
public final class DemandForPaymentCriteriaBuilder {

    private DemandForPaymentCriteriaBuilder() {
    }

    /**
     * Tworzy zapytanie pobierające wezwania do zapłaty filtrowane przekazanych kryteriach
     *
     * @param criteriaBuilder - klasa budująca kryteria zapytania
     * @param criteria        - kryteria filtrowania
     * @param sortCriteria    - kryterium sortowania
     * @return zapytanie pobierające wezwania do zapłaty
     */
    public static CriteriaQuery<DemandForPaymentWithCompanyName> createQuery(CriteriaBuilder criteriaBuilder,
                                                                             DemandForPaymentSearchCriteria criteria,
                                                                             Map<DemandForPaymentSortField, SortOrder> sortCriteria) {
        CriteriaQuery<DemandForPaymentWithCompanyName> criteriaQuery = criteriaBuilder.createQuery(DemandForPaymentWithCompanyName.class);

        Root<DemandForPayment> demandForPaymentQuery = criteriaQuery.from(DemandForPayment.class);
        Root<Company> companyQuery = criteriaQuery.from(Company.class);
        Lazy<Join<DemandForPayment, DemandForPaymentTypeConfiguration>> demandForPaymentType = new Lazy<>(() -> demandForPaymentQuery.join(DemandForPayment_.type, JoinType.LEFT));

        List<Predicate> predicates = buildPredicates(criteriaBuilder, criteria, demandForPaymentQuery, companyQuery, demandForPaymentType);
        List<Order> orders = buildSortOrders(criteriaBuilder, demandForPaymentQuery, companyQuery, sortCriteria, demandForPaymentType);

        return criteriaQuery
                .multiselect(demandForPaymentQuery, companyQuery.get(Company_.companyName))
                .orderBy(orders)
                .where(predicates.toArray(new Predicate[0]));
    }

    /**
     * Tworzy zapytanie pobierające ilość wezwań do zapłaty filtrowanych po przekazanych kryteriach
     *
     * @param criteriaBuilder - klasa budująca kryteria zapytania
     * @param criteria        - kryteria filtrowania
     * @return zapytanie pobierające ilość wezwań do zapłaty
     */
    public static CriteriaQuery<Long> createCountQuery(CriteriaBuilder criteriaBuilder,
                                                       DemandForPaymentSearchCriteria criteria) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<DemandForPayment> demandForPayment = criteriaQuery.from(DemandForPayment.class);
        Root<Company> companyQuery = criteriaQuery.from(Company.class);
        Lazy<Join<DemandForPayment, DemandForPaymentTypeConfiguration>> demandForPaymentType = new Lazy<>(() -> demandForPayment.join(DemandForPayment_.type, JoinType.LEFT));


        List<Predicate> predicates = buildPredicates(criteriaBuilder, criteria, demandForPayment, companyQuery, demandForPaymentType);

        return criteriaQuery.select(criteriaBuilder.countDistinct(demandForPayment))
                .where(predicates.toArray(new Predicate[0]));
    }

    private static List<Predicate> buildPredicates(CriteriaBuilder cb,
                                                   DemandForPaymentSearchCriteria criteria,
                                                   Root<DemandForPayment> demandForPayment,
                                                   Root<Company> companyQuery,
                                                   Lazy<Join<DemandForPayment, DemandForPaymentTypeConfiguration>> demandForPaymentType) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(demandForPayment.get(DemandForPayment_.extCompanyId), companyQuery.get(Company_.extCompanyId)));

        if (CollectionUtils.isNotEmpty(criteria.getState())) {
            predicates.add(demandForPayment.get(DemandForPayment_.state).in(criteria.getState()));
        }
        if (criteria.getContractNo() != null) {
            predicates.add(cb.equal(demandForPayment.get(DemandForPayment_.contractNumber), criteria.getContractNo()));
        }
        if (criteria.getExtCompanyId() != null) {
            predicates.add(cb.equal(demandForPayment.get(DemandForPayment_.extCompanyId), criteria.getExtCompanyId()));
        }
        if (criteria.getChargeInvoiceNo() != null) {
            predicates.add(cb.equal(demandForPayment.get(DemandForPayment_.chargeInvoiceNo), criteria.getChargeInvoiceNo()));
        }
        if (criteria.getInitialInvoiceNo() != null) {
            predicates.add(cb.equal(demandForPayment.get(DemandForPayment_.initialInvoiceNo), criteria.getInitialInvoiceNo()));
        }
        if (criteria.getStartIssueDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(demandForPayment.get(DemandForPayment_.issueDate), criteria.getStartIssueDate()));
        }
        if (criteria.getEndIssueDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(demandForPayment.get(DemandForPayment_.issueDate), criteria.getEndIssueDate()));
        }
        if (criteria.getStartDueDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(demandForPayment.get(DemandForPayment_.dueDate), criteria.getStartDueDate()));
        }
        if (criteria.getEndDueDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(demandForPayment.get(DemandForPayment_.dueDate), criteria.getEndDueDate()));
        }
        if (criteria.getCreatedManually() != null) {
            predicates.add(cb.equal(demandForPayment.get(DemandForPayment_.createdManually), criteria.getCreatedManually()));
        }
        if (criteria.getAborted() != null) {
            predicates.add(cb.equal(demandForPayment.get(DemandForPayment_.aborted), criteria.getAborted()));
        }
        if (criteria.getCompanyName() != null) {
            predicates.add(cb.like(cb.lower(companyQuery.get(Company_.companyName)), String.format("%%%s%%", criteria.getCompanyName().toLowerCase())));
        }
        if (CollectionUtils.isNotEmpty(criteria.getTypes())) {
            predicates.add(demandForPaymentType.get().get(DemandForPaymentTypeConfiguration_.key).in(criteria.getTypes()));
        }
        if (criteria.getSegment() != null) {
            predicates.add(cb.equal(demandForPaymentType.get().get(DemandForPaymentTypeConfiguration_.segment), criteria.getSegment()));
        }

        return predicates;
    }

    private static List<Order> buildSortOrders(CriteriaBuilder cb,
                                               Root<DemandForPayment> demandForPaymentQuery,
                                               Root<Company> companyQuery,
                                               Map<DemandForPaymentSortField, SortOrder> sortCriteria,
                                               Lazy<Join<DemandForPayment, DemandForPaymentTypeConfiguration>> demandForPaymentType) {
        List<Order> orders = new ArrayList<>();

        for (DemandForPaymentSortField field : sortCriteria.keySet()) {
            switch (field) {
                case COMPANY_NAME:
                    orders.add(buildSortOrder(cb, companyQuery.get(Company_.companyName), sortCriteria.get(field)));
                    break;
                case TYPE:
                    orders.add(buildSortOrder(cb, demandForPaymentType.get().get(DemandForPaymentTypeConfiguration_.key), sortCriteria.get(field)));
                    break;
                case SEGMENT:
                    orders.add(buildSortOrder(cb, demandForPaymentType.get().get(DemandForPaymentTypeConfiguration_.segment), sortCriteria.get(field)));
                    break;
                case ID:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.id), sortCriteria.get(field)));
                    break;
                case STATE:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.state), sortCriteria.get(field)));
                    break;
                case CONTRACT_NUMBER:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.contractNumber), sortCriteria.get(field)));
                    break;
                case ISSUE_DATE:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.issueDate), sortCriteria.get(field)));
                    break;
                case EXT_COMPANY_ID:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.extCompanyId), sortCriteria.get(field)));
                    break;
                case DUE_DATE:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.dueDate), sortCriteria.get(field)));
                    break;
                case CHARGE_INVOICE_NO:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.chargeInvoiceNo), sortCriteria.get(field)));
                    break;
                case INITIAL_INVOICE_NO:
                    orders.add(buildSortOrder(cb, demandForPaymentQuery.get(DemandForPayment_.initialInvoiceNo), sortCriteria.get(field)));
                    break;
            }
        }

        Order defaultOrder = cb.asc(demandForPaymentQuery.get(DemandForPayment_.id));
        orders.add(defaultOrder);
        return orders;
    }

    private static Order buildSortOrder(CriteriaBuilder cb,
                                        Path path,
                                        SortOrder order) {
        return order == SortOrder.ASC ? cb.asc(path) : cb.desc(path);
    }
}
