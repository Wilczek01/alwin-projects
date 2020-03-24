package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.activity.ActivityType_;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueTypeActivityType;
import com.codersteam.alwin.jpa.issue.IssueTypeActivityType_;
import com.codersteam.alwin.jpa.issue.IssueType_;
import com.codersteam.alwin.jpa.type.ActivityTypeKey;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do typów czynności windykacyjnych
 *
 * @author Tomasz Sliwinski
 */
@Stateless
public class ActivityTypeDao extends AbstractAuditDao<ActivityType> {

    /**
     * Pobiera listę typów czynności filtrowaną po typie zlecenia lub/i czy może mieć deklaracje
     *
     * @param issueTypeName       - typ zlecenia windykacyjnego
     * @param mayHaveDeclarations - czy typ zlecenia może mieć deklaracje
     * @return lista typów czynności
     */
    public List<ActivityType> findActivityTypes(IssueTypeName issueTypeName, Boolean mayHaveDeclarations) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ActivityType> criteriaQuery = cb.createQuery(ActivityType.class);
        Root<ActivityType> activityType = criteriaQuery.from(ActivityType.class);
        Join<IssueTypeActivityType, IssueType> issueType = activityType.join(ActivityType_.issueTypeActivityTypes, JoinType.LEFT).join(IssueTypeActivityType_.issueType, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if (issueTypeName != null) {
            predicates.add(cb.equal(issueType.get(IssueType_.name), issueTypeName));
        }
        if (mayHaveDeclarations != null) {
            predicates.add(cb.equal(activityType.get(ActivityType_.mayHaveDeclaration), mayHaveDeclarations));
        }

        criteriaQuery.select(activityType)
                .distinct(true)
                .orderBy(cb.asc(activityType.get(ActivityType_.id)));
        return entityManager.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[0]))).getResultList();
    }

    /**
     * Pobieranie typu czynności windykacyjnej po kluczu
     *
     * @param key - klucz czynności windykacyjnej
     * @return typ czynności
     */
    public Optional<ActivityType> findByKey(final ActivityTypeKey key) {
        final String sql = "SELECT a FROM ActivityType a WHERE a.key=:key";
        final List<ActivityType> resultList = entityManager.createQuery(sql, ActivityType.class).
                setParameter("key", key).getResultList();
        return checkForSingleResult(resultList);
    }

    /**
     * Sprawdza czy czynność o podanym identyfikatorze jest dozwolona dla typu zlecenia o podanym identyfikatorze
     *
     * @param issueTypeId    - identyfikator typu zlecenia
     * @param activityTypeId - identyfikator typu czynności
     * @return true jeżeli czynność o podanym identyfikatorze jest dozwolona dla typu zlecenia o podanym identyfikatorze, false w przeciwnym razie
     */
    public boolean checkIfActivityTypeIsAllowedForIssueType(final Long issueTypeId, final Long activityTypeId) {
        final String queryString = "SELECT count(itat) FROM IssueTypeActivityType itat WHERE itat.issueType.id = :issueTypeId and itat.activityType.id = :activityTypeId";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("issueTypeId", issueTypeId);
        query.setParameter("activityTypeId", activityTypeId);
        return query.getSingleResult() != 0;
    }

    @Override
    public Class<ActivityType> getType() {
        return ActivityType.class;
    }
}
