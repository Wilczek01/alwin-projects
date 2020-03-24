package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerExecutionInfoDto;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.transaction.Transactional;

/**
 * Szkielet uruchomienia zadania cyklicznego
 *
 * @author Michal Horowic
 */
public abstract class SchedulerExecution {

    private final SchedulerExecutionInfoService schedulerExecutionService;
    private final SchedulerTaskType schedulerType;

    SchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService, final SchedulerTaskType schedulerType) {
        this.schedulerExecutionService = schedulerExecutionService;
        this.schedulerType = schedulerType;
    }

    /**
     * Metoda uruchamiająca zadanie
     *
     * @param manual - czy zadanie zostało uruchomione ręcznie
     * @throws SchedulerExecutionException gdy w trakcie wykonywania zadania wystąpi błąd
     */
    public void execute(final boolean manual) throws SchedulerExecutionException {
        final SchedulerExecutionInfoDto execution = schedulerExecutionService.schedulerExecutionStarted(schedulerType, manual);
        try {
            getLogger().info("{} started", getDescription());
            final long start = System.currentTimeMillis();
            executeScheduler();
            getLogger().info("{} ended, took: {} ms", getDescription(), System.currentTimeMillis() - start);

            schedulerExecutionService.schedulerExecutionFinished(execution.getId());
        } catch (final RuntimeException exception) {
            handleSchedulerError(execution, exception);
        }
    }

    private void handleSchedulerError(final SchedulerExecutionInfoDto execution, final RuntimeException exception) throws SchedulerExecutionException {
        final String errorMessage = StringUtils.abbreviate(exception.getMessage(), SchedulerExecutionInfoService.ERROR_MESSAGE_MAX_LENGTH);
        schedulerExecutionService.schedulerExecutionFailed(execution.getId(), errorMessage);
        throw new SchedulerExecutionException(exception);
    }

    public static class SchedulerExecutionException extends Exception {
        SchedulerExecutionException(final Throwable cause) {
            super(cause);
        }
    }

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    protected abstract void executeScheduler();

    protected abstract Logger getLogger();

    protected abstract String getDescription();
}
