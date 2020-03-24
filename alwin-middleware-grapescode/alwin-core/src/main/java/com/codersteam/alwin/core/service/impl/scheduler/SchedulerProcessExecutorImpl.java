package com.codersteam.alwin.core.service.impl.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerProcessExecutor;
import com.codersteam.alwin.core.service.impl.scheduler.strategy.SchedulerExecution;
import com.codersteam.alwin.core.service.impl.scheduler.strategy.SchedulerExecutionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dariusz Rackowski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class SchedulerProcessExecutorImpl implements SchedulerProcessExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource(lookup = "java:jboss/ee/concurrency/executor/alwinBatchProcess")
    private ManagedExecutorService executorService;

    private final SchedulerExecutionFactory schedulerExecutionFactory;
    private final SchedulerExecutionInfoService schedulerExecutionInfoService;


    @Inject
    public SchedulerProcessExecutorImpl(final SchedulerExecutionFactory schedulerExecutionFactory,
                                        final SchedulerExecutionInfoService schedulerExecutionInfoService) {
        this.schedulerExecutionFactory = schedulerExecutionFactory;
        this.schedulerExecutionInfoService = schedulerExecutionInfoService;
    }

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void processSchedulerTasksAsync(SchedulerBatchProcessType schedulerBatchProcessType, final boolean manual) throws TaskIsRunningValidationException {
        validateIfNoneTasksAreRunning(schedulerBatchProcessType);
        executorService.submit(() -> executeAllTasks(schedulerBatchProcessType, manual));
    }

    @Override
    @Lock(LockType.READ)
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void processSchedulerTasks(SchedulerBatchProcessType schedulerBatchProcessType, final boolean manual) throws TaskIsRunningValidationException {
        validateIfNoneTasksAreRunning(schedulerBatchProcessType);
        executeAllTasks(schedulerBatchProcessType, manual);
    }

    private void validateIfNoneTasksAreRunning(final SchedulerBatchProcessType schedulerBatchProcessType) throws TaskIsRunningValidationException {
        final Boolean anyTaskIsRunning = Arrays.stream(schedulerBatchProcessType.getSchedulerTypes())
                .map(schedulerExecutionInfoService::isSchedulerExecutionRunning)
                .filter(Boolean::booleanValue)
                .findFirst()
                .orElse(false);
        if (anyTaskIsRunning) {
            throw new TaskIsRunningValidationException(String.format("Niektóre z zadania z grupy zadań typu %s są w trakcie wykonywania", schedulerBatchProcessType.name()));
        }
    }

    /**
     * Uruchamia wszystkie zadania (według kolejności) z podanej grupy zadań
     *
     * @param schedulerBatchProcessType - identyfikator grupy zadań cyklicznych
     * @param manual                    - czy zadanie zostało uruchomione ręcznie
     */
    private void executeAllTasks(final SchedulerBatchProcessType schedulerBatchProcessType, final boolean manual) {
        final List<SchedulerExecution> schedulers = Arrays.stream(schedulerBatchProcessType.getSchedulerTypes())
                .map(schedulerExecutionFactory::getSchedulerExecution)
                .collect(Collectors.toList());
        for (SchedulerExecution schedulerExecution : schedulers) {
            try {
                schedulerExecution.execute(manual);
            } catch (final SchedulerExecution.SchedulerExecutionException e) {
                LOG.error("Error occurred while processing base scheduler tasks {}, running manually {}", schedulerBatchProcessType, manual, e);
            } catch (final Exception ex) {
                LOG.error("Error occurred while processing scheduler task {}, running manually {}", schedulerBatchProcessType, manual, ex);
            }
        }
    }

}
