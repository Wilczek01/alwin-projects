package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.common.search.criteria.IssueForCompanySearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssueSearchCriteria;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.activity.ActivityType_;
import com.codersteam.alwin.jpa.activity.Activity_;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.customer.Customer_;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueType_;
import com.codersteam.alwin.jpa.issue.Issue_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES;

/**
 * Klasa tworząca warunki potrzebne do filtrowania zleceń danego klienta
 *
 * @author Michal Horowic
 */
public final class CompanyIssuesCriteriaBuilder {

    private CompanyIssuesCriteriaBuilder() {
    }

    public static List<Predicate> fillRequiredPredicates(Long assigneeId,
                                                         IssueSearchCriteria searchCriteria,
                                                         CriteriaBuilder cb,
                                                         Root<Issue> issue,
                                                         Root<Activity> activity) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(issue.get(Issue_.id), activity.get(Activity_.issue)));
        predicates.add(cb.equal(activity.get(Activity_.operator), assigneeId));
        predicates.add(cb.not(activity.get(Activity_.state).in(OPEN_STATES)));

        if (searchCriteria.getStartStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(issue.get(Issue_.startDate), searchCriteria.getStartStartDate()));
        }
        if (searchCriteria.getEndStartDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(issue.get(Issue_.startDate), searchCriteria.getEndStartDate()));
        }
        if (searchCriteria.getStartExpirationDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(issue.get(Issue_.expirationDate), searchCriteria.getStartExpirationDate()));
        }
        if (searchCriteria.getEndExpirationDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(issue.get(Issue_.expirationDate), searchCriteria.getEndExpirationDate()));
        }
        if (searchCriteria.getStartTotalCurrentBalancePLN() != null) {
            predicates.add(cb.greaterThanOrEqualTo(issue.get(Issue_.totalCurrentBalancePLN), searchCriteria.getStartTotalCurrentBalancePLN()));
        }
        if (searchCriteria.getEndTotalCurrentBalancePLN() != null) {
            predicates.add(cb.lessThanOrEqualTo(issue.get(Issue_.totalCurrentBalancePLN), searchCriteria.getEndTotalCurrentBalancePLN()));
        }
        if (searchCriteria.getStartActivityDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(activity.get(Activity_.activityDate), searchCriteria.getStartActivityDate()));
        }
        if (searchCriteria.getEndActivityDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(activity.get(Activity_.activityDate), searchCriteria.getEndActivityDate()));
        }
        if (searchCriteria.getStartPlannedDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(activity.get(Activity_.plannedDate), searchCriteria.getStartPlannedDate()));
        }
        if (searchCriteria.getEndPlannedDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(activity.get(Activity_.plannedDate), searchCriteria.getEndPlannedDate()));
        }
        return predicates;
    }

    public static List<Predicate> fillRequiredPredicatesForCompanyExcludingGivenIssue(Long companyId,
                                                                                      Long issueToExclude,
                                                                                      IssueForCompanySearchCriteria searchCriteria,
                                                                                      CriteriaBuilder cb,
                                                                                      Join<Activity, Issue> issue,
                                                                                      Root<Activity> activity) {

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.notEqual(issue.get(Issue_.id), issueToExclude));

        Join<Issue, Customer> customer = issue.join(Issue_.customer);
        predicates.add(cb.equal(customer.get(Customer_.company), companyId));

        if (searchCriteria.getActivityDateFrom() != null) {
            predicates.add(cb.greaterThan(activity.get(Activity_.activityDate), searchCriteria.getActivityDateFrom()));
        }

        if (searchCriteria.getActivityDateTo() != null) {
            predicates.add(cb.lessThan(activity.get(Activity_.activityDate), searchCriteria.getActivityDateTo()));
        }

        if (searchCriteria.getActivityTypeId() != null) {
            Join<Activity, ActivityType> activityTypeJoin = activity.join(Activity_.activityType);
            predicates.add(cb.equal(activityTypeJoin.get(ActivityType_.id), searchCriteria.getActivityTypeId()));
        }

        if (searchCriteria.getIssueTypeId() != null) {
            Join<Issue, IssueType> issueTypeJoin = issue.join(Issue_.issueType);
            predicates.add(cb.equal(issueTypeJoin.get(IssueType_.id), searchCriteria.getIssueTypeId()));
        }

        return predicates;
    }
}
