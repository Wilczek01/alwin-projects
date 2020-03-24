package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.criteria.strategy.OrderIssue;
import com.codersteam.alwin.db.util.Lazy;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.Activity_;
import com.codersteam.alwin.jpa.customer.*;
import com.codersteam.alwin.jpa.issue.*;
import com.codersteam.alwin.jpa.operator.OperatorType;
import com.codersteam.alwin.jpa.operator.OperatorType_;
import com.codersteam.alwin.jpa.operator.Operator_;
import com.codersteam.alwin.jpa.type.ActivityState;
import com.codersteam.alwin.jpa.type.ContactType;
import com.codersteam.alwin.jpa.type.IssueState;
import com.codersteam.alwin.jpa.type.OperatorNameType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.search.util.ParamUtils.*;
import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES;
import static com.codersteam.alwin.jpa.type.IssueState.CLOSED_ISSUE_STATES;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * Klasa tworząca warunki potrzebne do filtrowania zleceń z obszaru operatora
 */
public final class IssuesCriteriaBuilder {


    private IssuesCriteriaBuilder() {
    }

    public static CriteriaUpdate<Issue> createUpdateQuery(Long operatorId,
                                                          String operatorTypeName,
                                                          IssuesSearchCriteria criteria,
                                                          CriteriaBuilder cb) {
        CriteriaUpdate<Issue> criteriaQuery = cb.createCriteriaUpdate(Issue.class);

        Root<Issue> issue = criteriaQuery.from(Issue.class);
        Subquery<Long> subQuery = prepareUpdateSubQuery(operatorTypeName, criteria, cb, criteriaQuery.subquery(Long.class));

        return criteriaQuery.set(issue.get(Issue_.assignee).get(Operator_.id), operatorId)
                .where(issue.in(subQuery));
    }

    public static CriteriaUpdate<Issue> createUpdatePriorityQuery(Integer priority,
                                                                  String operatorTypeName,
                                                                  IssuesSearchCriteria criteria,
                                                                  CriteriaBuilder cb) {
        CriteriaUpdate<Issue> criteriaQuery = cb.createCriteriaUpdate(Issue.class);

        Root<Issue> issue = criteriaQuery.from(Issue.class);
        Subquery<Long> subQuery = prepareUpdateSubQuery(operatorTypeName, criteria, cb, criteriaQuery.subquery(Long.class));

        return criteriaQuery.set(issue.get(Issue_.priority), priority)
                .where(issue.in(subQuery));
    }

    public static CriteriaUpdate<Activity> createActivityUpdateQuery(Long operatorId,
                                                                     String operatorTypeName,
                                                                     IssuesSearchCriteria criteria,
                                                                     CriteriaBuilder cb) {
        CriteriaUpdate<Activity> criteriaQuery = cb.createCriteriaUpdate(Activity.class);

        Root<Activity> activity = criteriaQuery.from(Activity.class);

        Subquery<Long> subQuery = prepareActivityUpdateSubQuery(operatorTypeName, criteria, cb, criteriaQuery.subquery(Long.class));

        return criteriaQuery.set(activity.get(Activity_.operator).get(Operator_.id), operatorId)
                .where(activity.in(subQuery));
    }

    public static CriteriaQuery<Issue> createQuery(String operatorTypeName,
                                                   IssuesSearchCriteria criteria,
                                                   List<IssueState> excludedIssueStates,
                                                   List<ActivityState> activityStates,
                                                   CriteriaBuilder cb,
                                                   Map<IssueSortField, SortOrder> sortCriteria) {
        CriteriaQuery<Issue> criteriaQuery = cb.createQuery(Issue.class);

        Root<Issue> issue = criteriaQuery.from(Issue.class);
        Lazy<Join<Customer, Company>> issueCustomerCompany = new Lazy<>(() -> issue.join(Issue_.customer, JoinType.LEFT).join(Customer_.company, JoinType.LEFT));
        Lazy<Join<Issue, IssueType>> issueType = new Lazy<>(() -> issue.join(Issue_.issueType));


        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getIssuePredicates(issue, criteria, excludedIssueStates, cb));
        predicates.addAll(getIssueTypePredicates(issue, operatorTypeName, criteria, cb));
        predicates.addAll(getIssueCustomerCompanyPredicates(criteriaQuery, issue, criteria, cb, issueCustomerCompany));
        predicates.addAll(getIssueActivityPredicates(criteriaQuery, issue, criteria, activityStates, cb));
        predicates.addAll(getTagPredicates(criteriaQuery, issue, criteria));
        predicates.addAll(getInvoicePredicates(criteriaQuery, issue, criteria, cb));

        List<Order> orders = buildSortOrders(cb, issue, sortCriteria, issueCustomerCompany, issueType);
        return criteriaQuery.select(issue)
                .orderBy(orders)
                .where(predicates.toArray(new Predicate[0]));
    }

    public static CriteriaQuery<IssueType> createOperatorTypeQuery(String operatorTypeName,
                                                                   IssuesSearchCriteria criteria,
                                                                   List<IssueState> excludedIssueStates,
                                                                   List<ActivityState> activityStates,
                                                                   CriteriaBuilder cb) {
        CriteriaQuery<IssueType> criteriaQuery = cb.createQuery(IssueType.class);

        Root<Issue> issue = criteriaQuery.from(Issue.class);
        Join<Issue, IssueType> issueType = issue.join(Issue_.issueType);
        Lazy<Join<Customer, Company>> issueCustomerCompany = new Lazy<>(() -> issue.join(Issue_.customer, JoinType.LEFT).join(Customer_.company, JoinType.LEFT));

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getIssuePredicates(issue, criteria, excludedIssueStates, cb));
        predicates.addAll(getIssueTypePredicates(issue, operatorTypeName, criteria, cb));
        predicates.addAll(getIssueCustomerCompanyPredicates(criteriaQuery, issue, criteria, cb, issueCustomerCompany));
        predicates.addAll(getIssueActivityPredicates(criteriaQuery, issue, criteria, activityStates, cb));
        predicates.addAll(getTagPredicates(criteriaQuery, issue, criteria));
        predicates.addAll(getInvoicePredicates(criteriaQuery, issue, criteria, cb));

        return criteriaQuery.select(issueType)
                .distinct(true)
                .where(predicates.toArray(new Predicate[0]));
    }

    public static CriteriaQuery<Long> createCountQuery(String operatorTypeName,
                                                       IssuesSearchCriteria criteria,
                                                       List<IssueState> excludedIssueStates,
                                                       List<ActivityState> activityStates,
                                                       CriteriaBuilder cb) {
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

        Root<Issue> issue = criteriaQuery.from(Issue.class);
        Lazy<Join<Customer, Company>> issueCustomerCompany = new Lazy<>(() -> issue.join(Issue_.customer, JoinType.LEFT).join(Customer_.company, JoinType.LEFT));

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getIssuePredicates(issue, criteria, excludedIssueStates, cb));
        predicates.addAll(getIssueTypePredicates(issue, operatorTypeName, criteria, cb));
        predicates.addAll(getIssueCustomerCompanyPredicates(criteriaQuery, issue, criteria, cb, issueCustomerCompany));
        predicates.addAll(getIssueActivityPredicates(criteriaQuery, issue, criteria, activityStates, cb));
        predicates.addAll(getTagPredicates(criteriaQuery, issue, criteria));
        predicates.addAll(getInvoicePredicates(criteriaQuery, issue, criteria, cb));

        return criteriaQuery.select(cb.countDistinct(issue))
                .where(predicates.toArray(new Predicate[0]));
    }

    private static Subquery<Long> prepareUpdateSubQuery(String operatorTypeName,
                                                        IssuesSearchCriteria criteria,
                                                        CriteriaBuilder cb,
                                                        Subquery<Long> subQuery) {
        Root<Issue> subIssue = subQuery.from(Issue.class);
        Lazy<Join<Customer, Company>> issueCustomerCompany = new Lazy<>(() -> subIssue.join(Issue_.customer, JoinType.LEFT).join(Customer_.company, JoinType.LEFT));

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getIssuePredicates(subIssue, criteria, CLOSED_ISSUE_STATES, cb));
        predicates.addAll(getIssueTypePredicates(subIssue, operatorTypeName, criteria, cb));
        predicates.addAll(getIssueCustomerCompanyPredicates(subQuery, subIssue, criteria, cb, issueCustomerCompany));
        predicates.addAll(getIssueActivityPredicates(subQuery, subIssue, criteria, null, cb));
        predicates.addAll(getTagPredicates(subQuery, subIssue, criteria));
        predicates.addAll(getInvoicePredicates(subQuery, subIssue, criteria, cb));

        return subQuery.select(subIssue.get(Issue_.id))
                .where(cb.and(predicates.toArray(new Predicate[0])));
    }

    private static Subquery<Long> prepareActivityUpdateSubQuery(String operatorTypeName,
                                                                IssuesSearchCriteria criteria,
                                                                CriteriaBuilder cb,
                                                                Subquery<Long> subQuery) {
        Root<Activity> activity = subQuery.from(Activity.class);
        Join<Activity, Issue> issue = activity.join(Activity_.issue);
        Lazy<Join<Customer, Company>> issueCustomerCompany = new Lazy<>(() -> issue.join(Issue_.customer, JoinType.LEFT).join(Customer_.company, JoinType.LEFT));

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getIssuePredicates(issue, criteria, CLOSED_ISSUE_STATES, cb));
        predicates.addAll(getIssueTypePredicates(issue, operatorTypeName, criteria, cb));
        predicates.addAll(getIssueCustomerCompanyPredicates(subQuery, issue, criteria, cb, issueCustomerCompany));
        predicates.addAll(getIssueActivityPredicates(subQuery, issue, criteria, null, cb));
        predicates.addAll(getTagPredicates(subQuery, issue, criteria));
        predicates.addAll(getInvoicePredicates(subQuery, issue, criteria, cb));
        predicates.add(activity.get(Activity_.state).in(OPEN_STATES));

        return subQuery.select(activity.get(Activity_.id))
                .where(cb.and(predicates.toArray(new Predicate[0])));
    }

    private static <X, Z> List<Predicate> getInvoicePredicates(AbstractQuery<X> query,
                                                               From<Z, Issue> issue,
                                                               IssuesSearchCriteria criteria,
                                                               CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(criteria.getInvoiceContractNo())) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Issue> from = subquery.from(Issue.class);
            ListJoin<Issue, IssueInvoice> issueIssueInvoices = from.join(Issue_.issueInvoices);
            subquery.select(from.get(Issue_.id))
                    .where(
                            cb.like(cb.lower(issueIssueInvoices.get(IssueInvoice_.pk).get(IssueInvoiceId_.invoice).get(Invoice_.contractNumber)), String.format("%%%s%%", criteria.getInvoiceContractNo().toLowerCase()))
                    );
            predicates.add(issue.in(subquery));
        }
        return predicates;
    }

    private static List<Order> buildSortOrders(CriteriaBuilder cb,
                                               Root<Issue> issue,
                                               Map<IssueSortField, SortOrder> sortCriteria,
                                               Lazy<Join<Customer, Company>> issueCustomerCompany,
                                               Lazy<Join<Issue, IssueType>> issueType) {
        List<Order> orders = new ArrayList<>();
        for (IssueSortField field : sortCriteria.keySet()) {
            switch (field) {
                case ID:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.id), sortCriteria.get(field)));
                    break;
                case CUSTOMER:
                    orders.add(buildSortOrder(cb, issueCustomerCompany.get().get(Company_.companyName), sortCriteria.get(field)));
                    break;
                case START_DATE:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.startDate), sortCriteria.get(field)));
                    break;
                case EXPIRATION_DATE:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.expirationDate), sortCriteria.get(field)));
                    break;
                case TYPE:
                    orders.add(buildSortOrder(cb, issueType.get().get(IssueType_.name), sortCriteria.get(field)));
                    break;
                case STATE:
                    Path<IssueState> statePath = issue.get(Issue_.issueState);

                    Expression<Object> stateCase = cb.selectCase()
                            .when(cb.isNull(statePath), 6)
                            .when(cb.equal(statePath, IssueState.NEW), 3)
                            .when(cb.equal(statePath, IssueState.CANCELED), 1)
                            .when(cb.equal(statePath, IssueState.DONE), 5)
                            .when(cb.equal(statePath, IssueState.IN_PROGRESS), 4)
                            .when(cb.equal(statePath, IssueState.WAITING_FOR_TERMINATION), 2)
                            .otherwise(6);
                    orders.add(buildSortOrder(cb, stateCase, sortCriteria.get(field)));
                    break;
                case PRIORITY_STATE:
                    Path<IssueState> priorityStatePath = issue.get(Issue_.issueState);

                    Expression<Object> priorityStateCase = cb.selectCase()
                            .when(cb.isNull(priorityStatePath), 6)
                            .when(cb.equal(priorityStatePath, IssueState.NEW), 2)
                            .when(cb.equal(priorityStatePath, IssueState.CANCELED), 4)
                            .when(cb.equal(priorityStatePath, IssueState.DONE), 5)
                            .when(cb.equal(priorityStatePath, IssueState.IN_PROGRESS), 1)
                            .when(cb.equal(priorityStatePath, IssueState.WAITING_FOR_TERMINATION), 3)
                            .otherwise(6);
                    orders.add(buildSortOrder(cb, priorityStateCase, sortCriteria.get(field)));
                    break;
                case RPB:
                    Expression rpb = cb.sum(
                            issue.get(Issue_.rpbPLN),
                            cb.prod(issue.get(Issue_.rpbEUR), OrderIssue.EUR_VALUE)
                    );
                    orders.add(buildSortOrder(cb, rpb, sortCriteria.get(field)));
                    break;
                case RPB_PLN:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.rpbPLN), sortCriteria.get(field)));
                    break;
                case RPB_EUR:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.rpbEUR), sortCriteria.get(field)));
                    break;
                case BALANCE_START:
                    Expression balanceStart = cb.sum(
                            issue.get(Issue_.balanceStartPLN),
                            cb.prod(issue.get(Issue_.balanceStartEUR), OrderIssue.EUR_VALUE)
                    );
                    orders.add(buildSortOrder(cb, balanceStart, sortCriteria.get(field)));
                    break;
                case BALANCE_START_PLN:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.balanceStartPLN), sortCriteria.get(field)));
                    break;
                case BALANCE_START_EUR:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.balanceStartEUR), sortCriteria.get(field)));
                    break;
                case CURRENT_BALANCE:
                    Expression currentBalance = cb.sum(
                            issue.get(Issue_.currentBalancePLN),
                            cb.prod(issue.get(Issue_.currentBalanceEUR), OrderIssue.EUR_VALUE)
                    );
                    orders.add(buildSortOrder(cb, currentBalance, sortCriteria.get(field)));
                    break;
                case CURRENT_BALANCE_PLN:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.currentBalancePLN), sortCriteria.get(field)));
                    break;
                case CURRENT_BALANCE_EUR:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.currentBalanceEUR), sortCriteria.get(field)));
                    break;
                case PAYMENTS:
                    Expression payments = cb.sum(
                            issue.get(Issue_.paymentsPLN),
                            cb.prod(issue.get(Issue_.paymentsEUR), OrderIssue.EUR_VALUE)
                    );
                    orders.add(buildSortOrder(cb, payments, sortCriteria.get(field)));
                    break;
                case PAYMENTS_PLN:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.paymentsPLN), sortCriteria.get(field)));
                    break;
                case PAYMENTS_EUR:
                    orders.add(buildSortOrder(cb, issue.get(Issue_.paymentsEUR), sortCriteria.get(field)));
                    break;
                case EXT_COMPANY_ID:
                    orders.add(buildSortOrder(cb, issueCustomerCompany.get().get(Company_.extCompanyId), sortCriteria.get(field)));
                    break;
            }
        }
        Order defaultOrder = cb.asc(issue.get(Issue_.id));
        orders.add(defaultOrder);
        return orders;
    }

    private static Order buildSortOrder(CriteriaBuilder cb,
                                        Expression path,
                                        SortOrder order) {
        return order == SortOrder.ASC ? cb.asc(path) : cb.desc(path);
    }

    private static <Z> List<Predicate> getIssuePredicates(From<Z, Issue> issue,
                                                          IssuesSearchCriteria criteria,
                                                          List<IssueState> excludedIssueStates,
                                                          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(addExcludedStatesPredicateIfNeeded(excludedIssueStates, issue, cb));

        if (criteria.getStartStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(issue.get(Issue_.startDate), criteria.getStartStartDate()));
        }
        if (criteria.getEndStartDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(issue.get(Issue_.startDate), criteria.getEndStartDate()));
        }

        if (isNotEmpty(criteria.getStates())) {
            predicates.add(issue.get(Issue_.issueState).in(criteria.getStates().stream().map(IssueState::valueOf).collect(Collectors.toList())));
        }
        if (criteria.getTotalCurrentBalancePLNMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(issue.get(Issue_.totalCurrentBalancePLN), criteria.getTotalCurrentBalancePLNMin()));
        }
        if (criteria.getTotalCurrentBalancePLNMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(issue.get(Issue_.totalCurrentBalancePLN), criteria.getTotalCurrentBalancePLNMax()));
        }

        if (criteria.getUnassigned() != null && criteria.getUnassigned()) {
            if (isNotEmpty(criteria.getOperatorIds())) {
                Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
                return singletonList(cb.or(cb.isNull(issue.get(Issue_.assignee)), cb.and(predicatesArray)));
            } else {
                predicates.add(cb.isNull(issue.get(Issue_.assignee)));
            }
        }
        if (criteria.getPriority() != null) {
            predicates.add(cb.equal(issue.get(Issue_.priority), criteria.getPriority()));
        }

        if (isNotEmpty(criteria.getAssignedTo())) {
            predicates.add(createIssueAssigneeInPredicate(issue, criteria.getAssignedTo()));
        }

        return predicates;
    }

    private static <X, Z> List<Predicate> getIssueActivityPredicates(AbstractQuery<X> query,
                                                                     From<Z, Issue> issue,
                                                                     IssuesSearchCriteria criteria,
                                                                     List<ActivityState> activityStates,
                                                                     CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getStartContactDate() != null) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Issue> from = subquery.from(Issue.class);
            ListJoin<Issue, Activity> activity = from.join(Issue_.activities);
            subquery.select(from.get(Issue_.id))
                    .where(cb.greaterThanOrEqualTo(activity.get(Activity_.creationDate), criteria.getStartContactDate()));
            predicates.add(issue.in(subquery));
        }
        if (criteria.getEndContactDate() != null) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Issue> from = subquery.from(Issue.class);
            ListJoin<Issue, Activity> activity = from.join(Issue_.activities);
            subquery.select(from.get(Issue_.id))
                    .where(cb.lessThanOrEqualTo(activity.get(Activity_.creationDate), criteria.getEndContactDate()));
            predicates.add(issue.in(subquery));
        }
        if (!isEmpty(criteria.getOperatorIds())) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Issue> from = subquery.from(Issue.class);
            ListJoin<Issue, Activity> activity = from.join(Issue_.activities);
            Predicate operator = createActivityOperatorPredicate(activity, criteria.getOperatorIds(), activityStates, cb);
            subquery.select(from.get(Issue_.id))
                    .where(operator);

            Predicate assignee = createIssueAssigneeInPredicate(issue, criteria.getOperatorIds());
            predicates.add(cb.or(assignee, issue.in(subquery)));
        }
        return predicates;
    }

    private static <X, Z> List<Predicate> getTagPredicates(AbstractQuery<X> query,
                                                           From<Z, Issue> issue,
                                                           IssuesSearchCriteria criteria) {
        List<Predicate> predicates = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(criteria.getTagIds())) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Issue> from = subquery.from(Issue.class);
            SetJoin<Issue, Tag> tags = from.join(Issue_.tags);
            subquery.select(from.get(Issue_.id))
                    .where(tags.get(Tag_.id).in(criteria.getTagIds()));
            predicates.add(issue.in(subquery));
        }
        return predicates;
    }

    private static <X, Z> List<Predicate> getIssueCustomerCompanyPredicates(AbstractQuery<X> query,
                                                                            From<Z, Issue> issue,
                                                                            IssuesSearchCriteria criteria,
                                                                            CriteriaBuilder cb,
                                                                            Lazy<Join<Customer, Company>> issueCustomerCompany) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(criteria.getCompanyName())) {
            predicates.add(cb.like(cb.lower(issueCustomerCompany.get().get(Company_.companyName)), String.format("%%%s%%", criteria.getCompanyName().toLowerCase())));
        }
        if (criteria.getNip() != null) {
            predicates.add(cb.equal(issueCustomerCompany.get().get(Company_.nip), criteria.getNip()));
        }
        if (criteria.getRegon() != null) {
            predicates.add(cb.equal(issueCustomerCompany.get().get(Company_.regon), criteria.getRegon()));
        }

        Optional<Predicate> customerExtCompanyIdPredicate = createExtCompanyIdAndSegmentPredicate(criteria, cb, issueCustomerCompany);
        Optional<Predicate> contractCustomerExtCompanyIdPredicate = createIssueContractCustomerCompanyPredicate(issue, criteria, cb);
        if (customerExtCompanyIdPredicate.isPresent() && contractCustomerExtCompanyIdPredicate.isPresent()) {
            predicates.add(cb.or(customerExtCompanyIdPredicate.get(), contractCustomerExtCompanyIdPredicate.get()));
        }


        if (StringUtils.isNotEmpty(criteria.getPersonName())) {
            String firstPartOfTheName = getFirstPartSeparatedBySpace(criteria.getPersonName()).toLowerCase();
            String lastPartOfTheName = getLastPartSeparatedBySpace(criteria.getPersonName()).toLowerCase();

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Issue> from = subquery.from(Issue.class);
            Join<CompanyPerson, Person> issueCustomerCompanyPersonsPerson = from.join(Issue_.customer).join(Customer_.company).join(Company_.companyPersons).join(CompanyPerson_.person);
            subquery.select(from.get(Issue_.id))
                    .where(cb.or(
                            cb.and(
                                    cb.equal(cb.lower(issueCustomerCompanyPersonsPerson.get(Person_.firstName)), firstPartOfTheName),
                                    cb.equal(cb.lower(issueCustomerCompanyPersonsPerson.get(Person_.lastName)), lastPartOfTheName)
                            ),
                            cb.and(
                                    cb.equal(cb.lower(issueCustomerCompanyPersonsPerson.get(Person_.lastName)), firstPartOfTheName),
                                    cb.equal(cb.lower(issueCustomerCompanyPersonsPerson.get(Person_.firstName)), lastPartOfTheName)
                            )
                    ));

            predicates.add(issue.in(subquery));
        }
        if (criteria.getPersonPesel() != null) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Issue> from = subquery.from(Issue.class);
            Join<CompanyPerson, Person> issueCustomerCompanyPersonsPerson = from.join(Issue_.customer).join(Customer_.company).join(Company_.companyPersons).join(CompanyPerson_.person);
            subquery.select(from.get(Issue_.id))
                    .where(cb.equal(issueCustomerCompanyPersonsPerson.get(Person_.pesel), criteria.getPersonPesel()));

            predicates.add(issue.in(subquery));
        }


        Subquery<Long> companyContactDetailsSubquery = query.subquery(Long.class);
        Root<Issue> from = companyContactDetailsSubquery.from(Issue.class);
        SetJoin<Company, ContactDetail> companyContactDetails = from.join(Issue_.customer).join(Customer_.company).join(Company_.contactDetails);
        List<Predicate> companyContactDetailsPredicates = createContactDetailsPredicates(companyContactDetails, criteria, cb);
        companyContactDetailsSubquery.select(from.get(Issue_.id))
                .where(companyContactDetailsPredicates.toArray(new Predicate[0]));


        Subquery<Long> personContactDetailsSubquery = query.subquery(Long.class);
        Root<Issue> from2 = personContactDetailsSubquery.from(Issue.class);
        SetJoin<Person, ContactDetail> personContactDetails = from2.join(Issue_.customer).join(Customer_.company).join(Company_.companyPersons).join(CompanyPerson_.person).join(Person_.contactDetails);
        List<Predicate> personContactDetailsPredicates = createContactDetailsPredicates(personContactDetails, criteria, cb);
        personContactDetailsSubquery.select(from2.get(Issue_.id))
                .where(personContactDetailsPredicates.toArray(new Predicate[0]));

        if (!personContactDetailsPredicates.isEmpty() || !companyContactDetailsPredicates.isEmpty()) {
            predicates.add(cb.or(issue.in(companyContactDetailsSubquery), issue.in(personContactDetailsSubquery)));
        }

        return predicates;
    }

    private static <Z> List<Predicate> createContactDetailsPredicates(From<Z, ContactDetail> contactDetails,
                                                                      IssuesSearchCriteria criteria,
                                                                      CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        List<ContactType> contactTypes = new ArrayList<>();

        if (StringUtils.isNotEmpty(criteria.getContactPhone())) {
            predicates.add(cb.like(cb.lower(contactDetails.get(ContactDetail_.contact)), String.format("%%%s%%", criteria.getContactPhone().toLowerCase())));
            contactTypes.add(ContactType.PHONE_NUMBER);
        }

        if (StringUtils.isNotEmpty(criteria.getContactEmail())) {
            predicates.add(cb.like(cb.lower(contactDetails.get(ContactDetail_.contact)), String.format("%%%s%%", criteria.getContactEmail().toLowerCase())));
            contactTypes.addAll(asList(ContactType.E_MAIL, ContactType.DOCUMENT_E_MAIL, ContactType.OFFICE_E_EMAIL));
        }

        if (StringUtils.isNotEmpty(criteria.getContactName())) {
            predicates.add(cb.or(
                    cb.like(cb.lower(contactDetails.get(ContactDetail_.contact)), String.format("%%%s%%", criteria.getContactName().toLowerCase())),
                    cb.like(cb.lower(contactDetails.get(ContactDetail_.contact)), String.format("%%%s%%", revertValueSeparatedByLastSpace(criteria.getContactName()).toLowerCase()))
            ));
            contactTypes.add(ContactType.CONTACT_PERSON);
        }
        if (CollectionUtils.isNotEmpty(contactTypes)) {
            predicates.add(contactDetails.get(ContactDetail_.contactType).in(contactTypes));
        }
        return predicates;
    }

    private static <Z> List<Predicate> getIssueTypePredicates(From<Z, Issue> issue,
                                                              String operatorTypeName,
                                                              IssuesSearchCriteria criteria,
                                                              CriteriaBuilder cb) {
        Join<Issue, IssueType> issueType = issue.join(Issue_.issueType);
        SetJoin<IssueType, OperatorType> operatorType = issueType.join(IssueType_.operatorTypes);
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getIssueTypeId() != null) {
            predicates.add(cb.equal(issueType.get(IssueType_.id), criteria.getIssueTypeId()));
        }
        predicates.add(cb.equal(operatorType.get(OperatorType_.typeName), OperatorNameType.valueOf(operatorTypeName)));
        return predicates;
    }

    private static <Z> Optional<Predicate> createIssueContractCustomerCompanyPredicate(From<Z, Issue> issue,
                                                                                       IssuesSearchCriteria criteria,
                                                                                       CriteriaBuilder cb) {
        Lazy<Join<Customer, Company>> contractCustomerCompany = new Lazy<>(() -> issue.join(Issue_.contract, JoinType.LEFT).join(Contract_.customer, JoinType.LEFT).join(Customer_.company, JoinType.LEFT));
        return createExtCompanyIdAndSegmentPredicate(criteria, cb, contractCustomerCompany);
    }

    private static Optional<Predicate> createExtCompanyIdAndSegmentPredicate(IssuesSearchCriteria criteria,
                                                                             CriteriaBuilder cb,
                                                                             Lazy<Join<Customer, Company>> customerCompany) {
        Optional<Predicate> segmentPredicate = Optional.empty();
        if (criteria.getSegment() != null) {
            segmentPredicate = Optional.of(cb.equal(customerCompany.get().get(Company_.segment), criteria.getSegment()));
        }
        Optional<Predicate> extCompanyIdPredicate = Optional.empty();
        if (CollectionUtils.isNotEmpty(criteria.getExtCompanyIds())) {
            extCompanyIdPredicate = Optional.of(customerCompany.get().get(Company_.extCompanyId).in(criteria.getExtCompanyIds()));
        }

        if (segmentPredicate.isPresent() && extCompanyIdPredicate.isPresent()) {
            return Optional.of(cb.and(segmentPredicate.get(), extCompanyIdPredicate.get()));
        }
        if (segmentPredicate.isPresent()) {
            return segmentPredicate;
        }
        return extCompanyIdPredicate;
    }

    private static <Z> Predicate createIssueAssigneeInPredicate(From<Z, Issue> issue, List<Long> assigneeIds) {
        return issue.get(Issue_.assignee).in(assigneeIds);
    }

    private static Predicate createActivityOperatorPredicate(Join<Issue, Activity> activity,
                                                             List<Long> operatorIds,
                                                             List<ActivityState> activityStates,
                                                             CriteriaBuilder cb) {
        Predicate activityOperator = activity.get(Activity_.operator).in(operatorIds);
        if (isEmpty(activityStates)) {
            return activityOperator;
        }
        Predicate activityState = activity.get(Activity_.state).in(OPEN_STATES);
        return cb.and(activityState, activityOperator);
    }

    private static <Z> List<Predicate> addExcludedStatesPredicateIfNeeded(List<IssueState> excludeIssueStates,
                                                                          From<Z, Issue> issue,
                                                                          CriteriaBuilder cb) {
        if (isNotEmpty(excludeIssueStates)) {
            return singletonList(cb.not(issue.get(Issue_.issueState).in(excludeIssueStates)));
        }
        return emptyList();
    }
}
