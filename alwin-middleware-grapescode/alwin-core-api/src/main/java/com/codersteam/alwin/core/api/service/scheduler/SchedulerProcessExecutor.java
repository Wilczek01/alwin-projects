package com.codersteam.alwin.core.api.service.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;

import javax.ejb.Local;

/**
 * Komponent do uruchamiania grup zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Local
public interface SchedulerProcessExecutor {

    /**
     * Metoda do synchronicznego uruchamiania procesu (grupy zadań) o określonym typu
     *
     * @param schedulerBatchProcessType - identyfikator grupy zadań
     * @param manual                    - czy zadanie zostało uruchomione ręcznie
     * @throws TaskIsRunningValidationException - gdy którekolwiek z zadań w grupie jest aktualnie w trakcie wykonywania
     */
    void processSchedulerTasks(SchedulerBatchProcessType schedulerBatchProcessType, final boolean manual) throws TaskIsRunningValidationException;

    /**
     * Metoda do asynchronicznego uruchamiania procesu (grupy zadań) o określonym typu
     *
     * @param schedulerBatchProcessType - identyfikator grupy zadań
     * @param manual                    - czy zadanie zostało uruchomione ręcznie
     * @throws TaskIsRunningValidationException - gdy którekolwiek z zadań w grupie jest aktualnie w trakcie wykonywania
     */
    void processSchedulerTasksAsync(SchedulerBatchProcessType schedulerBatchProcessType, final boolean manual) throws TaskIsRunningValidationException;

    class TaskIsRunningValidationException extends Exception {
        public TaskIsRunningValidationException(final String message) {
            super(message);
        }
    }

}
