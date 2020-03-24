package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.issue.ActiveIssuesBalanceUpdaterService;
import com.codersteam.alwin.core.service.impl.issue.CloseExpiredIssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne uaktualnienia sald otwartych zleceń windykacyjnych oraz powiązanych z nimi faktur.
 * Po udanej aktualizacji zadanie zamyka przeterminowane zlecenia i w razie potrzeby tworzy nowe z odpowiednim typem.
 */
public class UpdateBalanceCloseAndCreateIssuesSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Update issues balance and close issues";
    private final ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService;
    private final CloseExpiredIssuesService issueService;

    public UpdateBalanceCloseAndCreateIssuesSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService,
                                                               final ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService,
                                                               final CloseExpiredIssuesService issueService) {
        super(schedulerExecutionService, SchedulerTaskType.UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES);
        this.activeIssuesBalanceUpdaterService = activeIssuesBalanceUpdaterService;
        this.issueService = issueService;
    }

    @Override
    protected void executeScheduler() {
        LOG.info("[UpdateBalanceCloseAndCreateIssuesSchedulerExecution] step 1");
        activeIssuesBalanceUpdaterService.updateActiveIssuesBalance();
        LOG.info("[UpdateBalanceCloseAndCreateIssuesSchedulerExecution] step 2");
        issueService.closeExpiredIssuesAndCreateChildIssuesIfNeeded();
        LOG.info("[UpdateBalanceCloseAndCreateIssuesSchedulerExecution] step 3");
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
