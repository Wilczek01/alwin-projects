package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;
import com.codersteam.alwin.common.sort.ActivitySortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.util.Lazy;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.User_;
import com.codersteam.alwin.jpa.activity.*;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.Company_;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.customer.Customer_;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueType_;
import com.codersteam.alwin.jpa.issue.Issue_;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.operator.Operator_;
import com.codersteam.alwin.jpa.type.ActivityState;
import com.codersteam.alwin.jpa.type.ActivityTypeKey;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasa budująca zapytanie pobierające filtrowane i sortowane czynności zlecenia w formie stronicowanej
 *
 * @author Michal Horowic
 */
public class ActivitiesCriteriaBuilder {

    private ActivitiesCriteriaBuilder() {
    }

    public static CriteriaQuery<Activity> createQuery(Long issueId,
                                                      ActivitiesSearchCriteria searchCriteria,
                                                      CriteriaBuilder criteriaBuilder,
                                                      Map<ActivitySortField, SortOrder> sortCriteria) {
        CriteriaQuery<Activity> criteriaQuery = criteriaBuilder.createQuery(Activity.class);

        Root<Activity> root = criteriaQuery.from(Activity.class);
        List<Predicate> predicates = getPredicates(root, issueId, searchCriteria, criteriaBuilder);

        List<Order> orders = buildSortOrders(criteriaBuilder, root, sortCriteria);
        return criteriaQuery.select(root)
                .orderBy(orders)
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    }

    public static CriteriaQuery<Long> createCountQuery(Long issueId,
                                                       ActivitiesSearchCriteria searchCriteria,
                                                       CriteriaBuilder cb) {
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Activity> root = criteriaQuery.from(Activity.class);
        List<Predicate> predicates = getPredicates(root, issueId, searchCriteria, cb);

        return criteriaQuery.select(cb.countDistinct(root))
                .where(cb.and(predicates.toArray(new Predicate[0])));
    }

    private static List<Predicate> getPredicates(Root<Activity> root,
                                                 Long issueId,
                                                 ActivitiesSearchCriteria searchCriteria,
                                                 CriteriaBuilder cb) {

        final List<Predicate> predicates = new ArrayList<>();

        if (searchCriteria.showAllForCompany()) {
            Join<Customer, Company> company = root.join(Activity_.issue).join(Issue_.customer).join(Customer_.company);
            if (searchCriteria.getCompanyId() != null) {
                predicates.add(cb.equal(company.get(Company_.id), searchCriteria.getCompanyId()));
            }
            Join<Issue, IssueType> issueType = root.join(Activity_.issue).join(Issue_.issueType);
            if (searchCriteria.getIssueType() != null) {
                predicates.add(cb.equal(issueType.get(IssueType_.name), searchCriteria.getIssueType()));
            }
        } else {
            Join<Activity, Issue> issue = root.join(Activity_.issue);
            predicates.add(cb.equal(issue.get(Issue_.id), issueId));
        }
        if (searchCriteria.getOperatorId() != null) {
            Join<Activity, Operator> operator = root.join(Activity_.operator);
            predicates.add(cb.equal(operator.get(Operator_.id), searchCriteria.getOperatorId()));
        }
        if (searchCriteria.getStartCreationDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Activity_.creationDate), searchCriteria.getStartCreationDate()));
        }
        if (searchCriteria.getEndCreationDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Activity_.creationDate), searchCriteria.getEndCreationDate()));
        }
        if (searchCriteria.getType() != null) {
            Join<Activity, ActivityType> activityType = root.join(Activity_.activityType);
            predicates.add(cb.equal(activityType.get(ActivityType_.key), ActivityTypeKey.valueOf(searchCriteria.getType())));
        }

        if (CollectionUtils.isNotEmpty(searchCriteria.getStates())) {
            List<ActivityState> states = searchCriteria.getStates()
                    .stream()
                    .map(ActivityState::valueOf)
                    .collect(Collectors.toList());
            predicates.add(root.get(Activity_.state).in(states));
        }

        if (StringUtils.isNotEmpty(searchCriteria.getRemark())) {
            Join<Activity, ActivityRemarkView> remarkView = root.join(Activity_.remarkView);
            predicates.add(cb.like(cb.lower(remarkView.get(ActivityRemarkView_.remark)), String.format("%%%s%%", searchCriteria.getRemark().toLowerCase())));
        }
        return predicates;
    }

    private static List<Order> buildSortOrders(CriteriaBuilder cb,
                                               Root<Activity> root,
                                               Map<ActivitySortField, SortOrder> sortCriteria) {
        Order orderById = cb.asc(root.get(ActivitySortField.ID.getColumn()));
        if (MapUtils.isEmpty(sortCriteria)) {
            return Arrays.asList(cb.asc(root.get(Activity_.plannedDate)), orderById);
        }

        List<Order> orders = new ArrayList<>();
        Lazy<Join<Operator, User>> operatorJoin = new Lazy<>(() -> root.join(Activity_.operator).join(Operator_.user));
        for (final ActivitySortField field : sortCriteria.keySet()) {
            switch (field) {
                case OPERATOR_LAST_NAME:
                case OPERATOR_FIRST_NAME:
                    orders.add(buildSortOrder(cb, operatorJoin.get().get(User_.firstName), sortCriteria.get(field)));
                    break;
                case OPERATOR:
                    orders.addAll(buildOperatorSortOrders(cb, operatorJoin.get(), sortCriteria.get(field)));
                    break;
                case TYPE_NAME:
                    Join<Activity, ActivityType> activityType = root.join(Activity_.activityType);
                    orders.add(buildSortOrder(cb, activityType.get(ActivityType_.name), sortCriteria.get(field)));
                    break;
                case REMARK:
                    Join<Activity, ActivityRemarkView> remarkView = root.join(Activity_.remarkView);
                    orders.add(buildSortOrder(cb, remarkView.get(ActivityRemarkView_.remark), sortCriteria.get(field)));
                    break;
                default:
                    orders.add(buildSortOrder(cb, root.get(Activity_.id), sortCriteria.get(field)));
            }
        }
        orders.add(orderById);

        return orders;
    }

    private static List<Order> buildOperatorSortOrders(CriteriaBuilder cb,
                                                       Join<Operator, User> user,
                                                       SortOrder sortOrder) {
        return Arrays.asList(
                buildSortOrder(cb, user.get(User_.firstName), sortOrder),
                buildSortOrder(cb, user.get(User_.lastName), sortOrder)
        );
    }

    private static Order buildSortOrder(CriteriaBuilder cb,
                                        Path path,
                                        SortOrder order) {
        return order == SortOrder.ASC ? cb.asc(path) : cb.desc(path);
    }
}
