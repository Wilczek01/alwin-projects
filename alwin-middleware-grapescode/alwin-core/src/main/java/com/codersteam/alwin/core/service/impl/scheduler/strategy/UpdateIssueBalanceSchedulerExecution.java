package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.issue.ActiveIssuesBalanceUpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne uaktualnienia sald otwartych zleceń windykacyjnych oraz powiązanych z nimi faktur
 */
public class UpdateIssueBalanceSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Update issues balance";
    private final ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService;

    public UpdateIssueBalanceSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService,
                                                final ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService) {
        super(schedulerExecutionService, SchedulerTaskType.UPDATE_ISSUES_BALANCE);
        this.activeIssuesBalanceUpdaterService = activeIssuesBalanceUpdaterService;
    }

    @Override
    protected void executeScheduler() {
        activeIssuesBalanceUpdaterService.updateActiveIssuesBalance();
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected String getDescription() {
        return DESCRIPTION;
    }
}
