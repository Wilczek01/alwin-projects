package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria;
import com.codersteam.alwin.common.sort.ContractTerminationSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.common.termination.ContractTerminationGroupKey;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.jpa.termination.ContractTermination_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.MapUtils.isEmpty;

/**
 * Klasa tworząca warunki potrzebne do filtrowania oraz sortowania wypowiedzeń
 *
 * @author Michal Horowic
 */
public final class ContractTerminationCriteriaBuilder {

    private ContractTerminationCriteriaBuilder() {
    }

    public static CriteriaQuery<ContractTerminationGroupKey> createQueryFindExtCompanyIdWithTerminationDatePaginated(CriteriaBuilder cb,
                                                                                                                     ContractTerminationSearchCriteria criteria,
                                                                                                                     Map<ContractTerminationSortField, SortOrder> sortCriteria) {
        CriteriaQuery<ContractTerminationGroupKey> query = cb.createQuery(ContractTerminationGroupKey.class);
        Root<ContractTermination> root = query.from(ContractTermination.class);
        List<Predicate> predicates = buildPredicates(cb, root, criteria);

        return query.multiselect(
                root.get(ContractTermination_.terminationDate),
                root.get(ContractTermination_.extCompanyId),
                root.get(ContractTermination_.type)
        )
                .where(predicates.toArray(new Predicate[0]))
                .groupBy(root.get(ContractTermination_.terminationDate),
                        root.get(ContractTermination_.extCompanyId),
                        root.get(ContractTermination_.type),
                        root.get(ContractTermination_.companyName))
                .orderBy(buildOrders(cb, root, sortCriteria));
    }

    public static CriteriaQuery<ContractTermination> createQueryFindByStatePaginatedByExtCompanyIdAndTerminationDateAndType(List<ContractTerminationGroupKey> resultList,
                                                                                                                            CriteriaBuilder criteriaBuilder,
                                                                                                                            ContractTerminationSearchCriteria criteria,
                                                                                                                            Map<ContractTerminationSortField, SortOrder> sortCriteria) {
        CriteriaQuery<ContractTermination> query = criteriaBuilder.createQuery(ContractTermination.class);
        Root<ContractTermination> root = query.from(ContractTermination.class);
        Predicate companyTerminationGroupKeyPredicates = criteriaBuilder.or(resultList.stream()
                .map(companyTerminationGroupKey -> {
                            final List<Predicate> predicates = buildPredicates(criteriaBuilder, root, criteria);
                            predicates.add(criteriaBuilder.equal(root.get(ContractTermination_.terminationDate), companyTerminationGroupKey.getTerminationDate()));
                            predicates.add(criteriaBuilder.equal(root.get(ContractTermination_.extCompanyId), companyTerminationGroupKey.getExtCompanyId()));
                            predicates.add(criteriaBuilder.equal(root.get(ContractTermination_.type), companyTerminationGroupKey.getType()));
                            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                        }
                ).toArray(Predicate[]::new));
        List<Order> orders = buildOrders(criteriaBuilder, root, sortCriteria);
        orders.add(criteriaBuilder.asc(root.get(ContractTermination_.id)));
        return query.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get(ContractTermination_.state), criteria.getState()),
                companyTerminationGroupKeyPredicates
        )).orderBy(orders);
    }

    /**
     * Tworzy zapytanie pobierające ilość wezwań do zapłaty filtrowanych po przekazanych kryteriach
     *
     * @param cb       - klasa budująca kryteria zapytania
     * @param criteria - kryteria filtrowania
     * @return zapytanie pobierające ilość wezwań do zapłaty
     */
    public static CriteriaQuery<Long> createCountInStatePaginatedByExtCompanyIdAndTerminationDateQuery(CriteriaBuilder cb,
                                                                                                       ContractTerminationSearchCriteria criteria) {
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<ContractTermination> contractTermination = criteriaQuery.from(ContractTermination.class);
        List<Predicate> predicates = buildPredicates(cb, contractTermination, criteria);
        return criteriaQuery.select(cb.countDistinct(contractTermination.get(ContractTermination_.distinct)))
                .where(predicates.toArray(new Predicate[0]));
    }

    private static List<Predicate> buildPredicates(CriteriaBuilder cb,
                                                   Root<ContractTermination> root,
                                                   ContractTerminationSearchCriteria criteria) {
        final List<Predicate> predicates = new ArrayList<>();
        if (criteria.getState() != null) {
            predicates.add(cb.equal(root.get(ContractTermination_.state), criteria.getState()));
        }
        if (criteria.getType() != null) {
            predicates.add(cb.equal(root.get(ContractTermination_.type), criteria.getType()));
        }
        if (criteria.getExtCompanyId() != null) {
            predicates.add(cb.equal(root.get(ContractTermination_.extCompanyId), criteria.getExtCompanyId()));
        }
        if (criteria.getStartTerminationDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ContractTermination_.terminationDate), criteria.getStartTerminationDate()));
        }
        if (criteria.getEndTerminationDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ContractTermination_.terminationDate), criteria.getEndTerminationDate()));
        }
        if (criteria.getCompanyName() != null) {
            predicates.add(cb.like(cb.lower(root.get(ContractTermination_.companyName)), String.format("%%%s%%", criteria.getCompanyName().toLowerCase())));
        }
        if (criteria.getContractNo() != null) {
            predicates.add(cb.like(cb.lower(root.get(ContractTermination_.contractNumber)), String.format("%%%s%%", criteria.getContractNo().toLowerCase())));
        }
        if (criteria.getRemark() != null) {
            predicates.add(cb.like(cb.lower(root.get(ContractTermination_.remark)), String.format("%%%s%%", criteria.getRemark().toLowerCase())));
        }
        if (criteria.getGeneratedBy() != null) {
            predicates.add(cb.like(cb.lower(root.get(ContractTermination_.generatedBy)), String.format("%%%s%%", criteria.getGeneratedBy().toLowerCase())));
        }
        if (criteria.getInitialInvoiceNo() != null) {
            predicates.add(cb.like(cb.lower(root.get(ContractTermination_.invoiceNumberInitiatingDemandForPayment)), String.format("%%%s%%", criteria.getInitialInvoiceNo().toLowerCase())));
        }
        if (criteria.getResumptionCostMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ContractTermination_.resumptionCost), criteria.getResumptionCostMin()));
        }
        if (criteria.getResumptionCostMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ContractTermination_.resumptionCost), criteria.getResumptionCostMax()));
        }
        if (criteria.getStartDueDateInDemandForPayment() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ContractTermination_.dueDateInDemandForPayment), criteria.getStartDueDateInDemandForPayment()));
        }
        if (criteria.getEndDueDateInDemandForPayment() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ContractTermination_.dueDateInDemandForPayment), criteria.getEndDueDateInDemandForPayment()));
        }
        if (criteria.getAmountInDemandForPaymentPLNMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ContractTermination_.amountInDemandForPaymentPLN), criteria.getAmountInDemandForPaymentPLNMin()));
        }
        if (criteria.getAmountInDemandForPaymentPLNMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ContractTermination_.amountInDemandForPaymentPLN), criteria.getAmountInDemandForPaymentPLNMax()));
        }
        if (criteria.getAmountInDemandForPaymentEURMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ContractTermination_.amountInDemandForPaymentEUR), criteria.getAmountInDemandForPaymentEURMin()));
        }
        if (criteria.getAmountInDemandForPaymentEURMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ContractTermination_.amountInDemandForPaymentEUR), criteria.getAmountInDemandForPaymentEURMax()));
        }
        if (criteria.getTotalPaymentsSinceDemandForPaymentPLNMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ContractTermination_.totalPaymentsSinceDemandForPaymentPLN), criteria.getTotalPaymentsSinceDemandForPaymentPLNMin()));
        }
        if (criteria.getTotalPaymentsSinceDemandForPaymentPLNMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ContractTermination_.totalPaymentsSinceDemandForPaymentEUR), criteria.getTotalPaymentsSinceDemandForPaymentPLNMax()));
        }
        if (criteria.getTotalPaymentsSinceDemandForPaymentEURMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ContractTermination_.totalPaymentsSinceDemandForPaymentEUR), criteria.getTotalPaymentsSinceDemandForPaymentEURMin()));
        }
        if (criteria.getTotalPaymentsSinceDemandForPaymentEURMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ContractTermination_.totalPaymentsSinceDemandForPaymentEUR), criteria.getTotalPaymentsSinceDemandForPaymentEURMax()));
        }
        return predicates;
    }

    private static List<Order> buildOrders(CriteriaBuilder cb,
                                           Root<ContractTermination> root,
                                           Map<ContractTerminationSortField, SortOrder> sortCriteria) {
        final List<Order> orders = new ArrayList<>();
        if (isEmpty(sortCriteria)) {
            orders.add(cb.desc(root.get(ContractTermination_.terminationDate)));
            orders.add(cb.asc(root.get(ContractTermination_.extCompanyId)));
            orders.add(cb.asc(root.get(ContractTermination_.type)));
        } else {
            sortCriteria.keySet()
                    .forEach(key -> {
                                Path sortKey;
                                switch (key) {
                                    case TYPE:
                                        sortKey = root.get(ContractTermination_.type);
                                        break;
                                    case COMPANY_NAME:
                                        sortKey = root.get(ContractTermination_.companyName);
                                        break;
                                    case EXT_COMPANY_ID:
                                        sortKey = root.get(ContractTermination_.extCompanyId);
                                        break;
                                    case TERMINATION_DATE:
                                        sortKey = root.get(ContractTermination_.terminationDate);
                                        break;
                                    case ID:
                                    default:
                                        sortKey = root.get(ContractTermination_.id);
                                        break;
                                }
                                orders.add(
                                        SortOrder.ASC.equals(sortCriteria.get(key)) ? cb.asc(sortKey) : cb.desc(sortKey)
                                );
                            }
                    );
        }
        return orders;
    }
}
