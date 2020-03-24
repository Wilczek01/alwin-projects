package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.jpa.scheduler.SchedulerConfiguration;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do informacji o wykonywaniu zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Stateless
public class SchedulerConfigurationDao extends AbstractAuditDao<SchedulerConfiguration> {

    @Override
    protected Class<SchedulerConfiguration> getType() {
        return SchedulerConfiguration.class;
    }

    /**
     * Aktualizuje czas startu procesu
     *
     * @param batchProcess - identyfikator grupy zadań
     * @param updateDate   - data ostatniej aktualizacji
     * @param hour         - godzina uruchamiania procesu
     * @return ilość zaktualizowanych zleceń
     */
    public int updateHourAndUpdateDate(final SchedulerBatchProcessType batchProcess, final Date updateDate, final int hour) {
        final String sql = "update SchedulerConfiguration sc set sc.updateDate = :updateDate, sc.hour = :hour WHERE sc.batchProcess = :batchProcess";
        final Query query = entityManager.createQuery(sql)
                .setParameter("batchProcess", batchProcess)
                .setParameter("updateDate", updateDate)
                .setParameter("hour", hour);
        return query.executeUpdate();
    }

    /**
     * Zwaca konfigurację zadania cyklicznego po enumerowanych identyfikatorze
     *
     * @param batchProcess - identyfikator grupy zadań
     * @return konfiguracja zadania cyklicznego
     */
    public Optional<SchedulerConfiguration> findByBatchProcess(final SchedulerBatchProcessType batchProcess) {
        final String sql = "select sc FROM SchedulerConfiguration sc WHERE sc.batchProcess = :batchProcess";
        final List<SchedulerConfiguration> resultList = entityManager.createQuery(sql, SchedulerConfiguration.class)
                .setParameter("batchProcess", batchProcess)
                .getResultList();
        return checkForSingleResult(resultList);
    }

}
