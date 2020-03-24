package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;
import com.codersteam.alwin.common.sort.DemandForPaymentSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.criteria.DemandForPaymentCriteriaBuilder;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.codersteam.alwin.common.demand.DemandForPaymentState.*;
import static java.util.Arrays.asList;

/**
 * Klasa dostępu do wezwań do zapłaty
 *
 * @author Tomasz Sliwinski
 */
@Stateless
public class DemandForPaymentDao extends AbstractDao<DemandForPayment> {

    /**
     * Pobiera wszystkie wezwania do zapłaty w danym statusie
     *
     * @param state status wezwania
     * @return lista wszystkich wezwań do zapłaty w danym statusie
     */
    public List<DemandForPaymentWithCompanyName> findAllDemandsForPaymentByState(final DemandForPaymentState state) {
        final TypedQuery<DemandForPaymentWithCompanyName> query = entityManager.createQuery("SELECT " +
                        "new com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName(dfp, c.companyName) " +
                        "FROM DemandForPayment dfp, Company c WHERE dfp.extCompanyId = c.extCompanyId AND dfp.state = :state " +
                        "ORDER BY dfp.id",
                DemandForPaymentWithCompanyName.class);
        query.setParameter("state", state);
        return query.getResultList();
    }

    /**
     * Pobiera stronę z wezwaniami do zapłaty filtrowanymi po przekazanych kryteriach
     *
     * @param criteria     - kryteria filtrowania
     * @param sortCriteria - kryteria sortowania
     * @return Strona z wezwaniami do zapłaty
     */
    public List<DemandForPaymentWithCompanyName> findAllDemandsForPayment(final DemandForPaymentSearchCriteria criteria, Map<DemandForPaymentSortField, SortOrder> sortCriteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<DemandForPaymentWithCompanyName> criteriaQuery = DemandForPaymentCriteriaBuilder.createQuery(criteriaBuilder, criteria, sortCriteria);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(criteria.getFirstResult())
                .setMaxResults(criteria.getMaxResults())
                .getResultList();
    }

    /**
     * Pobiera ilość wezwań do zapłaty filtrowanych po przekazanych kryteriach
     *
     * @param criteria - kryteria filtrowania
     * @return ilośc wezwań do zapłaty
     */
    public long findAllDemandsForPaymentCount(final DemandForPaymentSearchCriteria criteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = DemandForPaymentCriteriaBuilder.createCountQuery(criteriaBuilder, criteria);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Class<DemandForPayment> getType() {
        return DemandForPayment.class;
    }

    /**
     * Znajduje spośród przekazanych identyfikatorów wezwań do zapłaty te, które zostały już wysłane lub są w trakcie wysyłania
     *
     * @param demandsIds - identyfikatory wezwań do zapłaty
     * @return identyfikatory wezwań wysłanych lub w trakcie wysyłania
     */
    public List<Long> findIssuedOrProcessingDemandsForPayment(final List<Long> demandsIds) {
        final String queryString = "SELECT dfp.id FROM DemandForPayment dfp WHERE dfp.id in (:demandsIds) and dfp.state in (:state)";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("demandsIds", demandsIds);
        query.setParameter("state", asList(DemandForPaymentState.WAITING, DemandForPaymentState.ISSUED));
        return query.getResultList();
    }

    /**
     * Znajduje spośród przekazanych identyfikatorów wezwań do zapłaty te, które istnieją
     *
     * @param demandsIds - identyfikatory wezwań do zapłaty
     * @return identyfikatory istniejących wezwań
     */
    public List<Long> findExistingDemandsForPayment(final List<Long> demandsIds) {
        final String queryString = "SELECT dfp.id FROM DemandForPayment dfp WHERE dfp.id in (:demandsIds)";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("demandsIds", demandsIds);
        return query.getResultList();
    }

    /**
     * Usuwa wszystkie niewysłane jeszcze sugestie wezwań o podanych identyfikatorach
     *
     * @param demandsIds            - identyfikatory wezwań do zapłaty
     * @param stateChangeDate       - data zmiany statusu
     * @param stateChangeOperatorId - identyfikator operatora zmieniającego status
     * @param stateChangeReason     - powód zmiany statusu
     * @return liczba usuniętych wezwań
     */
    public int rejectDemandsForPayment(final List<Long> demandsIds, final Long stateChangeOperatorId, final Date stateChangeDate, final String stateChangeReason) {
        final Query query = entityManager.createQuery("update DemandForPayment set state = :newState, stateChangeOperatorId = :stateChangeOperatorId, " +
                "stateChangeDate = :stateChangeDate, stateChangeReason = :stateChangeReason where state != :issuedState and id in (:demandsIds)")
                .setParameter("newState", REJECTED)
                .setParameter("stateChangeOperatorId", stateChangeOperatorId)
                .setParameter("stateChangeDate", stateChangeDate)
                .setParameter("stateChangeReason", stateChangeReason)
                .setParameter("issuedState", ISSUED)
                .setParameter("demandsIds", demandsIds);
        return query.executeUpdate();
    }

    /**
     * Ustawia status procesowania dla wszystkich niewysłanych jeszcze sugestii wezwań o podanych identyfikatorach
     *
     * @param demandsIds - identyfikatory wezwań do zapłaty
     * @return liczba wezwań, którym został ustawiony status procesowania
     */
    public int processDemandsForPayment(final List<Long> demandsIds) {
        final Query query = entityManager.createQuery("update DemandForPayment set state = :waitingState where state != :issuedState and id in (:demandsIds)")
                .setParameter("issuedState", ISSUED)
                .setParameter("waitingState", WAITING)
                .setParameter("demandsIds", demandsIds);
        return query.executeUpdate();
    }

    /**
     * Utworzenie w bazie nowego wpisu wezwania do zapłaty z uprzednim pobraniem typu konfiguracji
     *
     * @param demandForPayment - wezwanie do zapłaty
     */
    public void createDemandForPayment(final DemandForPayment demandForPayment) {
        demandForPayment.setType(entityManager.getReference(DemandForPaymentTypeConfiguration.class, demandForPayment.getType().getId()));
        create(demandForPayment);
    }

    /**
     * Pobranie najnowszego wezwania do zapłaty dla umowy
     *
     * @param contractNo - numer umowy
     * @return najnowsze wezwanie do zapłaty
     */
    public Optional<DemandForPaymentWithCompanyName> findLatestDemandForPayment(final String contractNo) {
        final TypedQuery<DemandForPaymentWithCompanyName> query = entityManager.createQuery("SELECT " +
                "new com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName(dfp, c.companyName) " +
                "FROM DemandForPayment dfp, Company c WHERE dfp.extCompanyId = c.extCompanyId AND dfp.contractNumber = :contractNo " +
                "AND dfp.aborted = false AND dfp.state != :state " +
                "ORDER BY dfp.issueDate DESC", DemandForPaymentWithCompanyName.class);
        query.setParameter("contractNo", contractNo);
        query.setParameter("state", DemandForPaymentState.CANCELED);
        query.setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    /**
     * Pobranie wszystkich wystawionych wezwań typu drugiego, dla których termin płatności upływa w danym dniu
     *
     * @param dueDate - data płatności
     * @return lista wezwań do zapłaty typu drugiego, których termin płatności mija w podanym dniu
     */
    public List<DemandForPaymentWithCompanyName> findIssuedSecondDemandsForPaymentWithDueDate(final Date dueDate) {
        final TypedQuery<DemandForPaymentWithCompanyName> query = entityManager.createQuery("SELECT " +
                        "new com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName(dfp, c.companyName) " +
                        "FROM DemandForPayment dfp, Company c WHERE dfp.extCompanyId = c.extCompanyId " +
                        "AND dfp.state = 'ISSUED' AND dfp.type.key = 'SECOND' AND dfp.dueDate = :dueDate AND dfp.aborted = false AND dfp.state != :state ",
                DemandForPaymentWithCompanyName.class);
        query.setParameter("dueDate", dueDate);
        query.setParameter("state", DemandForPaymentState.CANCELED);
        return query.getResultList();
    }

    /**
     * Pobranie najstarszego wezwania do zapłaty danego typu utworzonego dla umowy
     *
     * @param demandForPaymentTypeKey - klucz typu wezwania
     * @param contractNumber          - numer umowy
     * @param state                   - status wezwania
     * @return wezwanie jeśli istnieje
     */
    public Optional<DemandForPayment> findLatestDemandForPaymentForContract(final DemandForPaymentTypeKey demandForPaymentTypeKey,
                                                                            final String contractNumber, final DemandForPaymentState state) {
        final TypedQuery<DemandForPayment> query = entityManager.createQuery("SELECT dfp FROM DemandForPayment dfp " +
                "WHERE dfp.type.key = :demandForPaymentTypeKey AND dfp.state = :state AND dfp.contractNumber = :contractNumber " +
                "ORDER BY dfp.issueDate DESC", DemandForPayment.class);
        query.setParameter("contractNumber", contractNumber);
        query.setParameter("state", state);
        query.setParameter("demandForPaymentTypeKey", demandForPaymentTypeKey);
        query.setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    /**
     * Pobranie najstarszego wezwania utworzonego dla umowy
     *
     * @param contractNumber - numer umowy
     * @param state          - status wezwania
     * @return wezwanie jeśli istnieje
     */
    public Optional<DemandForPayment> findLatestDemandForPaymentForContract(final String contractNumber, final DemandForPaymentState state) {
        final TypedQuery<DemandForPayment> query = entityManager.createQuery("SELECT dfp FROM DemandForPayment dfp " +
                "WHERE dfp.state = :state AND dfp.contractNumber = :contractNumber " +
                "ORDER BY dfp.issueDate DESC", DemandForPayment.class);
        query.setParameter("contractNumber", contractNumber);
        query.setParameter("state", state);
        query.setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    /**
     * Pobranie wezwań danego typu z wyłączeniem wskazanego
     * Używane do wyznaczenia wezwań zastępowanych w procesie ręcznym
     *
     * @param typeKey          - typ wezwania
     * @param contractNumber   - numer umowy
     * @param initialInvoiceNo - numer faktury inicjującej
     * @param excludedDemandId - identyfikator wezwania do pominięcia (wezwanie zastępujące)
     * @return wezwania do zapłaty
     */
    public List<DemandForPayment> findDemandsForPaymentExcludingGiven(final DemandForPaymentTypeKey typeKey, final String contractNumber,
                                                                      final String initialInvoiceNo, final Long excludedDemandId) {
        final TypedQuery<DemandForPayment> query = entityManager.createQuery("SELECT dfp FROM DemandForPayment dfp " +
                "WHERE dfp.type.key = :typeKey AND dfp.contractNumber = :contractNumber AND dfp.initialInvoiceNo = :initialInvoiceNo " +
                "AND id != :excludedDemandId AND dfp.aborted = false " +
                "ORDER BY dfp.id", DemandForPayment.class);
        query.setParameter("typeKey", typeKey);
        query.setParameter("contractNumber", contractNumber);
        query.setParameter("initialInvoiceNo", initialInvoiceNo);
        query.setParameter("excludedDemandId", excludedDemandId);
        return query.getResultList();
    }

    /**
     * Pobranie wezwania danego typu
     * Używane do wyznaczenia wezwania wyższego typu do zamknięcia procesu (wezwania ręczne)
     *
     * @param typeKey          - typ wezwania
     * @param contractNumber   - numer umowy
     * @param initialInvoiceNo - numer faktury inicjującej
     * @return wezwania do zapłaty
     */
    public List<DemandForPayment> findDemandsForPayment(final DemandForPaymentTypeKey typeKey, final String contractNumber, final String initialInvoiceNo) {
        final TypedQuery<DemandForPayment> query = entityManager.createQuery("SELECT dfp FROM DemandForPayment dfp " +
                "WHERE dfp.type.key = :typeKey AND dfp.contractNumber = :contractNumber AND dfp.initialInvoiceNo = :initialInvoiceNo " +
                "AND dfp.aborted = false " +
                "ORDER BY dfp.id", DemandForPayment.class);
        query.setParameter("typeKey", typeKey);
        query.setParameter("contractNumber", contractNumber);
        query.setParameter("initialInvoiceNo", initialInvoiceNo);
        return query.getResultList();
    }
}
