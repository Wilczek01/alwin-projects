package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.issue.CloseExpiredIssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne zamknięcia przeterminowanych zleceń i w razie potrzeby utworzenia nowych z odpowiednim typem
 *
 * @author Michal Horowic
 */
public class RecreateExpiredIssuesSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Close expired issues";
    private final CloseExpiredIssuesService issueService;

    public RecreateExpiredIssuesSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService, final CloseExpiredIssuesService issueService) {
        super(schedulerExecutionService, SchedulerTaskType.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED);
        this.issueService = issueService;
    }

    @Override
    protected void executeScheduler() {
        issueService.closeExpiredIssuesAndCreateChildIssuesIfNeeded();
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
