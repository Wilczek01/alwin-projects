package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria;
import com.codersteam.alwin.common.sort.ContractTerminationSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.common.termination.ContractTerminationGroupKey;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.jpa.termination.ContractTermination;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.search.param.ContractTerminationCriteriaParams.*;
import static com.codersteam.alwin.db.dao.criteria.ContractTerminationCriteriaBuilder.*;


/**
 * Klasa dostępu do wypowiedzeń umów
 *
 * @author Dariusz Rackowski
 */
@Stateless
public class ContractTerminationDao extends AbstractAuditDao<ContractTermination> {

    @Override
    public Class<ContractTermination> getType() {
        return ContractTermination.class;
    }

    /**
     * Zwraca wypowiedzenia umów według przekazanych statusów i daty wypowiedzenia mniejszej lub równej od podanej
     *
     * @param states             - lista statusów do uwzględnienia
     * @param terminationDateLte - data i godzina do porównania daty wypowiedzenia
     * @return lista wszystkich wypowiedzeń umów
     */
    public List<ContractTermination> findByStatesAndTerminationDateLte(final List<ContractTerminationState> states, final Date terminationDateLte) {
        final String sql = "select ct from ContractTermination ct " +
                "where ct.state in :states and ct.terminationDate <= :terminationDateLte " +
                "order by ct.terminationDate desc, ct.extCompanyId asc, ct.type asc, ct.id asc";
        return entityManager.createQuery(sql, ContractTermination.class)
                .setParameter(STATES.name, states)
                .setParameter("terminationDateLte", terminationDateLte)
                .getResultList();
    }

    /**
     * Zwraca wypowiedzenia umów (wszystkie dla wybranej strony unikatowych: id klienta i data wypowiedzenia)
     *
     * @param criteria - kryteria sortowania
     * @return lista wszystkich wypowiedzeń umów dla wybranej strony (id klientów i dat wypowiedzeń). Może być więcej rekordów niż maxResults.
     */
    public List<ContractTermination> findByStatePaginatedByExtCompanyIdAndTerminationDateAndType(final ContractTerminationSearchCriteria criteria,
                                                                                                 Map<ContractTerminationSortField, SortOrder> sortCriteria) {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final List<ContractTerminationGroupKey> resultList = findExtCompanyIdWithTerminationDatePaginated(criteriaBuilder, criteria, sortCriteria);

        final CriteriaQuery<ContractTermination> query = createQueryFindByStatePaginatedByExtCompanyIdAndTerminationDateAndType(resultList, criteriaBuilder, criteria,
                sortCriteria);

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Zwraca ilość rekordów dla stronicowanej listy unikatowych: id klienta i data wypowiedzenia
     *
     * @param criteria - kryteria sortowania
     * @return ilość rekordów
     */
    public int countInStatePaginatedByExtCompanyIdAndTerminationDate(final ContractTerminationSearchCriteria criteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> query = createCountInStatePaginatedByExtCompanyIdAndTerminationDateQuery(criteriaBuilder, criteria);
        return entityManager.createQuery(query).getSingleResult().intValue();
    }

    private List<ContractTerminationGroupKey> findExtCompanyIdWithTerminationDatePaginated(final CriteriaBuilder criteriaBuilder,
                                                                                           final ContractTerminationSearchCriteria criteria,
                                                                                           Map<ContractTerminationSortField, SortOrder> sortCriteria) {
        final CriteriaQuery<ContractTerminationGroupKey> query = createQueryFindExtCompanyIdWithTerminationDatePaginated(criteriaBuilder, criteria, sortCriteria);
        return entityManager.createQuery(query)
                .setFirstResult(criteria.getFirstResult())
                .setMaxResults(criteria.getMaxResults())
                .getResultList();
    }

    /**
     * Pobranie wszystkich wystawionych wypowiedzeń warunkowych z daną datą wystawienia
     *
     * @param terminationDate - data wystawienia
     * @return lista wypowiedzeń warunkowych, które zostały wystawione w danym dniu
     */
    public List<ContractTermination> findIssuedConditionalContractTerminationsWithTerminationDate(final Date terminationDate) {
        final TypedQuery<ContractTermination> query = entityManager.createQuery("SELECT ct FROM ContractTermination ct WHERE " +
                "ct.state = 'ISSUED' AND ct.type = 'CONDITIONAL' AND ct.terminationDate = :terminationDate order by ct.id", ContractTermination.class);
        query.setParameter(TERMINATION_DATE.name, terminationDate);
        return query.getResultList();

    }

    /**
     * Pobranie wypowiedzeń dla podanej kolekcji id
     *
     * @param terminationIds - kolekcja identyfikatorów wypowiedzeń umów
     * @return lista wypowiedzeń umów
     */
    public List<ContractTermination> findByIdsIn(final Collection<Long> terminationIds) {
        final String sql = "SELECT ct FROM ContractTermination ct WHERE ct.id IN (:terminationIds)";
        return entityManager.createQuery(sql, ContractTermination.class)
                .setParameter("terminationIds", terminationIds)
                .getResultList();
    }

    /**
     * Ustawia status procesownia dla wszystkich niewysłanych jeszcze wypowiedzeń umów o podanych identyfikatorach
     *
     * @param loggedOperatorFullName
     * @param terminationIds         - identyfikatory wypowiedzeń umów
     * @return liczba wypowiedzeń, którym został ustawiony status procesowania
     */
    public int processContractTerminations(final String loggedOperatorFullName, final Set<Long> terminationIds) {
        final Query query = entityManager.createQuery("update ContractTermination set state = :waitingState, generatedBy = :generatedBy " +
                "where state not in (:closedStates) and id in (:terminationIds)")
                .setParameter("waitingState", ContractTerminationState.WAITING)
                .setParameter("closedStates", ContractTerminationState.CLOSED_CONTRACT_TERMINATION_STATES)
                .setParameter("terminationIds", terminationIds)
                .setParameter("generatedBy", loggedOperatorFullName);
        return query.executeUpdate();

    }

    /**
     * Znajduje spośród przekazanych identyfikatorów wypowiedzeń umów te, które istnieją
     *
     * @param contractTerminationIds
     * @return
     */
    public List<Long> findExistingContractTerminationIds(final Set<Long> contractTerminationIds) {
        final String queryString = "SELECT ct.id FROM ContractTermination ct WHERE ct.id in (:contractTerminationIds)";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter(CONTRACT_TERMINATION_IDS.name, contractTerminationIds);
        return query.getResultList();
    }

    /**
     * Znajduje listę identyfikatorów wypowiedzeń umów, które mają podany status i znajdują się na liście identyfikatorów
     *
     * @param states                 - lista statusów
     * @param contractTerminationIds - lista identyfikatorów umów
     * @return lista identyfikatorów umów spełniających dowolny z przekazanych statusów
     */
    public List<Long> findByStatusesAndTerminationIds(final List<ContractTerminationState> states, final Set<Long> contractTerminationIds) {
        final String queryString = "SELECT ct.id FROM ContractTermination ct WHERE ct.id in (:contractTerminationIds) AND ct.state in (:states)";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter(CONTRACT_TERMINATION_IDS.name, contractTerminationIds);
        query.setParameter(STATES.name, states);
        return query.getResultList();
    }

    /**
     * Znajduje numery klientów przypisane do przekazanych identyfikatorów wypowiedzeń
     *
     * @param contractTerminationIds - identyfikatory wypowiedzeń umów
     * @return mapa identyfikatorów wypowiedzeń umów wskazujących na odpowiednie nr klientów
     */
    public Map<Long, Long> findContractIdToCompanyIds(final Set<Long> contractTerminationIds) {
        final String queryString = "SELECT ct.id, ct.extCompanyId FROM ContractTermination ct WHERE ct.id in (:contractTerminationIds)";
        return entityManager.createQuery(queryString, Object[].class)
                .setParameter(CONTRACT_TERMINATION_IDS.name, contractTerminationIds)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (Long) result[1]
                ));
    }

    /**
     * Znajduje typy wypowiedzeń umów przypisane do przekazanych identyfikatorów wypowiedzeń
     *
     * @param contractTerminationIds - identyfikatory wypowiedzeń umów
     * @return mapa identyfikatorów wypowiedzeń umów wskazujących na odpowiednie typy wypowiedzeń
     */
    public Map<Long, ContractTerminationType> findContractIdToContractType(final Set<Long> contractTerminationIds) {
        final String queryString = "SELECT ct.id, ct.type FROM ContractTermination ct WHERE ct.id in (:contractTerminationIds)";
        return entityManager.createQuery(queryString, Object[].class)
                .setParameter(CONTRACT_TERMINATION_IDS.name, contractTerminationIds)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (ContractTerminationType) result[1]
                ));
    }

    /**
     * Pobranie najnowszego wypowiedzenia umowy
     *
     * @param contractNo - numer umowy
     * @return najnowsze wypowiedzenie umowy
     */
    public Optional<ContractTermination> findLatestContractTermination(final String contractNo) {
        final TypedQuery<ContractTermination> query = entityManager.createQuery("SELECT ct FROM ContractTermination ct WHERE ct.contractNumber = :contractNo " +
                "ORDER BY ct.terminationDate DESC", ContractTermination.class);
        query.setParameter("contractNo", contractNo);
        query.setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    /**
     * Pobranie wypowiedzenia dla poprzedzającego wezwania
     *
     * @param precedingDemandForPaymentId - identyfikator poprzedzającego wezwania
     * @return wypowiedzenie warunkowe dla poprzedzającego wezwania (jeśli istnieje)
     */
    public Optional<ContractTermination> findContractTerminationForPrecedingDemandForPayment(final Long precedingDemandForPaymentId) {
        final TypedQuery<ContractTermination> query = entityManager.createQuery("SELECT ct FROM ContractTermination ct " +
                "WHERE ct.precedingDemandForPaymentId = :precedingDemandForPaymentId", ContractTermination.class);
        query.setParameter("precedingDemandForPaymentId", precedingDemandForPaymentId);
        query.setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    /**
     * Pobranie wypowiedzenia natychmiastowego dla poprzedzającego wypowiedzenia warunkowego
     *
     * @param precedingContractTerminationId - identyfikator poprzedzającego wypowiedzenia warunkowego
     * @return wypowiedzenie warunkowe dla poprzedzającego wezwania (jeśli istnieje)
     */
    public Optional<ContractTermination> findContractTerminationForPrecedingContractTermination(final Long precedingContractTerminationId) {
        final TypedQuery<ContractTermination> query = entityManager.createQuery("SELECT ct FROM ContractTermination ct " +
                "WHERE ct.precedingContractTerminationId = :precedingContractTerminationId", ContractTermination.class);
        query.setParameter("precedingContractTerminationId", precedingContractTerminationId);
        query.setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }
}
