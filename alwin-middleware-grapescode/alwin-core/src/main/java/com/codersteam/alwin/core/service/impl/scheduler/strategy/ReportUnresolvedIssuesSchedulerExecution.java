package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.issue.UnresolvedIssueService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne zebrania listy nierozwiązanych zleceń podczas windykacji telefonicznej, których czas obsługi minął i wysłania raportu e-mailem do managera
 *
 * @author Michal Horowic
 */
public class ReportUnresolvedIssuesSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Finding unresolved issues";
    private final UnresolvedIssueService unresolvedIssueService;

    public ReportUnresolvedIssuesSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService, final UnresolvedIssueService unresolvedIssueService) {
        super(schedulerExecutionService, SchedulerTaskType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL);
        this.unresolvedIssueService = unresolvedIssueService;
    }

    @Override
    protected void executeScheduler() {
        unresolvedIssueService.findUnresolvedIssuesAndSendReportEmail();
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
