package com.codersteam.alwin.core.service.impl.scheduler;

import com.codersteam.alwin.core.api.model.scheduler.SchedulerConfigurationDto;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerConfigurationService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerProcessExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType.BASE_SCHEDULE_TASKS;

/**
 * Komponent zarządzający uruchamianiem podstawowej grupy zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Singleton
@Startup
public class BatchProcessScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource
    private TimerService timerService;
    @Inject
    private SchedulerExecutionInfoService schedulerExecutionInfoService;
    @Inject
    private SchedulerConfigurationService schedulerConfigurationService;
    @Inject
    private SchedulerProcessExecutor schedulerBatchTaskExecutor;

    private AtomicLong lastConfigurationChangedTimestamp = new AtomicLong(0L);
    private AtomicBoolean running = new AtomicBoolean(false);

    @PostConstruct
    public void setup() {
        schedulerExecutionInfoService.cleanupRunningSchedulerExecutions();
        configureTimerForSchedulerConfiguration();
    }

    @Schedule(hour = "*", minute = "50", persistent = false)
    @AccessTimeout(value = 120, unit = TimeUnit.MINUTES)
    public void configureTimerForSchedulerConfiguration() {
        schedulerConfigurationService.findByBatchProcessType(BASE_SCHEDULE_TASKS)
                .filter(this::isChangedSinceLastExecution)
                .filter(ignore -> isSchedulerNotRunning())
                .ifPresent(schedulerConfiguration -> {
                    lastConfigurationChangedTimestamp.set(schedulerConfiguration.getUpdateDate().getTime());

                    cancelExistingTimer(schedulerConfiguration);

                    final Timer calendarTimer = createTimer(schedulerConfiguration);
                    LOG.info("Created new scheduler for {}. Next timeout: {}", timerIdentifier(schedulerConfiguration), calendarTimer.getNextTimeout());
                });
    }

    @Timeout
    @AccessTimeout(value = 120, unit = TimeUnit.MINUTES)
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void performScheduledTasks() {
        running.set(true);
        try {
            schedulerBatchTaskExecutor.processSchedulerTasks(BASE_SCHEDULE_TASKS, false);
        } catch (SchedulerProcessExecutor.TaskIsRunningValidationException e) {
            LOG.error("Error occurred while executing process in scheduler", e);
        } finally {
            running.set(false);
        }
    }

    private boolean isChangedSinceLastExecution(final SchedulerConfigurationDto schedulerConfiguration) {
        return !(lastConfigurationChangedTimestamp.get() == schedulerConfiguration.getUpdateDate().getTime());
    }

    private boolean isSchedulerNotRunning() {
        return !running.get();
    }

    public Timer createTimer(final SchedulerConfigurationDto schedulerConfiguration) {
        TimerConfig config = timerConfig(schedulerConfiguration);
        ScheduleExpression expression = scheduleExpression(schedulerConfiguration);
        return timerService.createCalendarTimer(expression, config);
    }

    private ScheduleExpression scheduleExpression(final SchedulerConfigurationDto schedulerConfiguration) {
        ScheduleExpression expression = new ScheduleExpression();
        expression.hour(schedulerConfiguration.getHour());
        expression.minute(0);
        expression.second(0);
        return expression;
    }

    private TimerConfig timerConfig(final SchedulerConfigurationDto schedulerConfiguration) {
        TimerConfig config = new TimerConfig();
        config.setPersistent(false);
        config.setInfo(timerIdentifier(schedulerConfiguration));
        return config;
    }

    private void cancelExistingTimer(final SchedulerConfigurationDto schedulerConfigurationDto) {
        timerService.getAllTimers().stream()
                .filter(timer -> timerIdentifier(schedulerConfigurationDto).equals(timer.getInfo()))
                .forEach(Timer::cancel);
    }

    private String timerIdentifier(final SchedulerConfigurationDto schedulerConfiguration) {
        return schedulerConfiguration.getBatchProcess().getKey();
    }

}
