package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Klasa dostępu do informacji o wykonywaniu zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Stateless
public class SchedulerExecutionInfoDao extends AbstractAuditDao<SchedulerExecutionInfo> {

    @Override
    protected Class<SchedulerExecutionInfo> getType() {
        return SchedulerExecutionInfo.class;
    }

    /**
     * Ustawia datę zakończenia wykonywania zadania
     *
     * @param id      - id zadania cyklicznegeo
     * @param endDate - data zakończenia
     * @return ilość zaktualizowanych zleceń
     */
    public int updateEndDate(final Long id, final Date endDate) {
        final String sql = "update SchedulerExecutionInfo se set se.endDate = :endDate where se.id = :id";
        final Query query = entityManager.createQuery(sql)
                .setParameter("id", id)
                .setParameter("endDate", endDate);
        return query.executeUpdate();
    }

    /**
     * Ustawia datę oraz status zakończenia wykonywania zadania
     *
     * @param id      - id zadania cyklicznegeo
     * @param endDate - data zakończenia
     * @param state   - stan zadania
     * @return ilość zaktualizowanych zleceń
     */
    public int updateEndDateAndState(final Long id, final Date endDate, final String state) {
        final String sql = "update SchedulerExecutionInfo se set se.endDate = :endDate, se.state = :state where se.id = :id";
        final Query query = entityManager.createQuery(sql)
                .setParameter("id", id)
                .setParameter("endDate", endDate)
                .setParameter("state", state);
        return query.executeUpdate();
    }

    /**
     * Zwraca stronę uruchomień zadań cyklicznych posortowanych po malejąco po dacie uruchomienia i id
     *
     * @param firstResult - pierwszy element na liście
     * @param maxResults  - maksymalna ilość elementów na stronie
     * @return strona uruchomień zadań cyklicznych
     */
    public List<SchedulerExecutionInfo> findSchedulersExecutions(final int firstResult, final int maxResults) {
        final String queryString = "select e from SchedulerExecutionInfo e order by e.startDate desc, e.id desc";
        return entityManager.createQuery(queryString, SchedulerExecutionInfo.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();

    }

    /**
     * Zwraca ilość wszystkich uruchomień zadań cyklicznych
     *
     * @return ilość wszystkich uruchomień zadań cyklicznych
     */
    public long findSchedulersExecutionsCount() {
        final String queryString = "select count(e) from SchedulerExecutionInfo e";
        return entityManager.createQuery(queryString, Long.class)
                .getSingleResult();
    }

    /**
     * Zwraca wszystkie niezakończone zadania cykliczne
     *
     * @return wszystkie niezakończone zadania cykliczne
     */
    public List<SchedulerExecutionInfo> findByEndDateIsNull() {
        final String sql = "select se from SchedulerExecutionInfo se where se.endDate is null";
        return entityManager.createQuery(sql, SchedulerExecutionInfo.class)
                .getResultList();
    }

    /**
     * Zwraca ilość uruchomionych zadań dla podanego typu
     *
     * @param type - typ zadania cyklicznego
     * @return ilość
     */
    public long findWithoutEndDateCount(final SchedulerTaskType type) {
        final String queryString = "select count(se) from SchedulerExecutionInfo se where se.type = :type and se.endDate is null";
        return entityManager.createQuery(queryString, Long.class)
                .setParameter("type", type)
                .getSingleResult();
    }

}
