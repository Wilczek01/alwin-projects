package com.codersteam.alwin.core.api.service.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerConfigurationDto;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

/**
 * Serwis konfiguracji wykonywania grupy zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Local
public interface SchedulerConfigurationService {

    /**
     * Metoda do zmiany czasu startu grupy zadań cyklicznych
     *
     * @param batchProcessType - identyfikator grupy zadań
     * @param hour             - godzina rozpoczęcia (0-23)
     */
    void changeTimeOfExecution(SchedulerBatchProcessType batchProcessType, int hour);

    /**
     * Metoda zwraca konfigurację dla podanego typu grupy zadań
     *
     * @param batchProcessType - typ grupy zadań
     * @return - konfiguracja zadania cyklicznego
     */
    Optional<SchedulerConfigurationDto> findByBatchProcessType(SchedulerBatchProcessType batchProcessType);


    /**
     * Metoda zwraca wszystkie konfiguracje zadań cyklicznych
     *
     * @return - lista konfiguracji zadań cyklicznych
     */
    List<SchedulerConfigurationDto> findAll();

}
