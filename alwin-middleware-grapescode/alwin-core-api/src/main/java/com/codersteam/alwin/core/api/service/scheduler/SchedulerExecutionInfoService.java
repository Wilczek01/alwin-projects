package com.codersteam.alwin.core.api.service.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerExecutionInfoDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Serwis do obsługi informacji o wykonywaniu zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Local
public interface SchedulerExecutionInfoService {

    int ERROR_MESSAGE_MAX_LENGTH = 500;

    /**
     * Metoda do zapisu informacji o wystartowanym zadaniu cyklicznym
     *
     * @param schedulerType - typ zadania
     * @param manual        - czy zadanie zostało wystartowane ręcznie
     * @return resultat zapisu informacji o wystartowanym zadaniu
     */
    SchedulerExecutionInfoDto schedulerExecutionStarted(SchedulerTaskType schedulerType, boolean manual);

    /**
     * Metoda do zapisu informacji o zakończeniu zadania cyklicznego
     *
     * @param schedulerInformationId - identyfikator zadania
     */
    void schedulerExecutionFinished(Long schedulerInformationId);

    /**
     * Metoda do zapisu informacji o błędzie podczas wykonywania zadania cyklicznego
     *
     * @param schedulerInformationId - identyfikator zadania
     * @param errorMessage           - informacja o błędzie (o maksymalnej długości {@value #ERROR_MESSAGE_MAX_LENGTH})
     */
    void schedulerExecutionFailed(Long schedulerInformationId, String errorMessage);

    /**
     * Zwraca stronę uruchomień zadań cyklicznych posortowanych po dacie uruchomienia
     *
     * @param firstResult - pierwszy element na liście
     * @param maxResults  - maksymalna ilość elementów na stronie
     * @return strona uruchomień zadań cyklicznych
     */
    Page<SchedulerExecutionInfoDto> findSchedulersExecutions(final int firstResult, final int maxResults);

    /**
     * Metoda do zamknięcia wszystkich otwartych zadań cyklicznych
     */
    void cleanupRunningSchedulerExecutions();

    /**
     * Metoda do sprawdzania czy zadanie cykliczne jest w trakcie wykonywania
     *
     * @param schedulerType - typ zadania
     * @return true jeśli zadanie jest oznaczone jako uruchomione, false w przeciwnym wypadku
     */
    boolean isSchedulerExecutionRunning(SchedulerTaskType schedulerType);

    /**
     * Metoda zwraca wszystkie dostępne typy grup zadań cyklicznych
     *
     * @return - lista typów grup zadań cyklicznych
     */
    List<SchedulerBatchProcessTypeDto> findAllBatchProcessTypes();

}
