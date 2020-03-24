package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.common.sort.ActivitySortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.criteria.ActivitiesCriteriaBuilder;
import com.codersteam.alwin.db.dao.criteria.IssuesCriteriaBuilder;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;
import com.codersteam.alwin.jpa.activity.ActivityType;
import com.codersteam.alwin.jpa.activity.Declaration;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.operator.Operator;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES;
import static com.codersteam.alwin.jpa.type.ActivityState.PLANNED;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.OUTGOING_PHONE_CALL;

/**
 * Klasa dostępu do czynności windykacyjnych dla zlecenia
 *
 * @author Michal Horowic
 */
@Stateless
public class ActivityDao extends AbstractAuditDao<Activity> {

    /**
     * Tworzy nową czynność dla zlecenia
     *
     * @param activity - czynność do utworzenia
     */
    public void createNewActivityForIssue(final Activity activity) {
        activity.setActivityType(entityManager.getReference(ActivityType.class, activity.getActivityType().getId()));
        if (activity.getOperator() != null) {
            activity.setOperator(entityManager.getReference(Operator.class, activity.getOperator().getId()));
        }
        activity.setIssue(entityManager.getReference(Issue.class, activity.getIssue().getId()));
        if (activity.getActivityDetails() != null) {
            activity.getActivityDetails().forEach(activityDetail ->
                    activityDetail.setDetailProperty(entityManager.getReference(ActivityDetailProperty.class, activityDetail.getDetailProperty().getId())));
        }
        create(activity);
    }

    /**
     * Pobiera wszystkie czynności windykacyjne dla danego zlecenia.
     * Czynności posortowane są po dacie zaplanowania.
     *
     * @param issueId - identyfikator zlecenia
     * @return lista czynności windykacyjnych
     */
    public List<Activity> findAllIssueActivities(final Long issueId) {
        final TypedQuery<Activity> query =
                entityManager.createQuery("SELECT a FROM Activity a WHERE a.issue.id = :issueId ORDER BY a.plannedDate, a.id", Activity.class);
        query.setParameter("issueId", issueId);
        return query.getResultList();
    }

    /**
     * Pobieranie aktywnych czynności z datą zaplanowania mniejszą lub równą od podanej.
     * Czynności posortowane są po dacie zaplanowania.
     *
     * @param issueId        - identyfikator zlecenia
     * @param maxPlannedDate - maksymalna data czynności zaplanowanej
     * @return lista czynności windykacyjnych
     */
    public List<Activity> findActiveIssueActivities(final Long issueId, final Date maxPlannedDate) {
        final String sql = "SELECT a FROM Activity a WHERE a.issue.id = :issueId and a.state in :openStates and a.plannedDate <= :maxPlannedDate " +
                "ORDER BY a.plannedDate, a.id";
        final TypedQuery<Activity> query = entityManager.createQuery(sql, Activity.class)
                .setParameter("issueId", issueId)
                .setParameter("openStates", OPEN_STATES)
                .setParameter("maxPlannedDate", maxPlannedDate);
        return query.getResultList();
    }

    /**
     * Pobiera stronicowane i filtrowane czynności dla zlecenia
     *
     * @param issueId        - identyfikator zlecenia
     * @param searchCriteria - kryteria filtrowania
     * @param sortCriteria   - kryteria sortowania
     * @return strona z listą czynności
     */
    public List<Activity> findAllIssueActivities(final Long issueId, final ActivitiesSearchCriteria searchCriteria,
                                                 Map<ActivitySortField, SortOrder> sortCriteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Activity> criteriaQuery = ActivitiesCriteriaBuilder.createQuery(issueId, searchCriteria, criteriaBuilder, sortCriteria);
        final TypedQuery<Activity> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(searchCriteria.getFirstResult());
        query.setMaxResults(searchCriteria.getMaxResults());
        return query.getResultList();
    }

    /**
     * Zwraca liczbę przefiltrowanych czynności
     *
     * @param issueId        - identyfikator zlecenia
     * @param searchCriteria - kryteria filtrowania
     * @return - ilość czynności
     */
    public Long findAllIssueActivitiesCount(final Long issueId, final ActivitiesSearchCriteria searchCriteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = ActivitiesCriteriaBuilder.createCountQuery(issueId, searchCriteria, criteriaBuilder);

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    /**
     * Przypisuje wszystkie czynności spełniające kryteria wyszukiwania do operatora o podanym identyfikatorze
     *
     * @param operatorId       - identyfikator operatora do przypisania
     * @param operatorTypeName - nazwa typu operatora zarządzającego zleceniami
     * @param criteria         - kryteria wyszukiwania zleceń do przypisania
     * @return ilość zaktualizowanych zleceń
     */
    public int updateActivityOperator(final Long operatorId, final String operatorTypeName, final IssuesSearchCriteria criteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        return entityManager.createQuery(IssuesCriteriaBuilder.createActivityUpdateQuery(operatorId, operatorTypeName, criteria,
                criteriaBuilder)).executeUpdate();
    }

    /**
     * Przypisuje czynności windykacyjnych należące do zleceń o podanych identyfikatorach, do operatora o podanym identyfikatorze
     *
     * @param operatorId - identyfikator operatora do przypisania
     * @param issueIds   - lista identyfikatorów zleceń
     * @return ilość zaktualizowanych final zleceń
     */
    public int updateActivityOperator(final Long operatorId, final List<Long> issueIds) {
        final String sql = "update Activity a set a.operator.id =:operatorId where a.issue.id  in (:issueIds) and a.state in :activityStates";
        final Query query = entityManager.createQuery(sql)
                .setParameter("issueIds", issueIds)
                .setParameter("operatorId", operatorId)
                .setParameter("activityStates", OPEN_STATES);
        return query.executeUpdate();
    }

    /**
     * Pobieranie deklaracji spłat dla zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return lista deklaracji spłaty
     */
    public List<Declaration> findIssueDeclarations(final Long issueId) {
        final String sql = "select d from Declaration d where d.activity.issue.id = :issueId order by d.id asc";
        return entityManager.createQuery(sql, Declaration.class)
                .setParameter("issueId", issueId)
                .getResultList();
    }

    /**
     * Pobieranie najstarszej zaplanowanej czynności 'telefon wychodzący' dla zlecenia
     * Data zaplanowanej czynności jest mniejsza lub równa od podanej daty
     *
     * @param issueId        - identyfikator zlecenia
     * @param maxPlannedDate - maksymalna data czynności zaplanowanej
     * @return najstarsza zaplanowana czynność typu 'telefon wychodzący' jeżeli istnieje
     */
    public Optional<Activity> findOldestPlannedActivityForIssue(final Long issueId, final Date maxPlannedDate) {
        final String sql = "SELECT a FROM Activity a WHERE a.issue.id = :issueId and a.state = :activityState and a.plannedDate <= :maxPlannedDate " +
                "and a.activityType.key = :activityTypeKey ORDER BY a.plannedDate, a.id";
        final TypedQuery<Activity> query = entityManager.createQuery(sql, Activity.class)
                .setParameter("issueId", issueId)
                .setParameter("activityState", PLANNED)
                .setParameter("activityTypeKey", OUTGOING_PHONE_CALL)
                .setParameter("maxPlannedDate", maxPlannedDate)
                .setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    @Override
    public Class<Activity> getType() {
        return Activity.class;
    }

    /**
     * Pobieranie aktywnych czynności.
     * Czynności posortowane są po dacie zaplanowania.
     *
     * @param issueId        - identyfikator zlecenia
     * @return lista czynności windykacyjnych
     */
    public List<Activity> findActiveIssueActivities(final Long issueId) {
        final String sql = "SELECT a FROM Activity a WHERE a.issue.id = :issueId and a.state in :openStates " +
                "ORDER BY a.plannedDate, a.id";
        final TypedQuery<Activity> query = entityManager.createQuery(sql, Activity.class)
                .setParameter("issueId", issueId)
                .setParameter("openStates", OPEN_STATES);
        return query.getResultList();
    }
}
