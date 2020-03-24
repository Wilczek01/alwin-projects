package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.issue.CreateIssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne utworzenia nowych zleceń windykacyjnych
 */
public class GenerateIssuesSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Issue generator";
    private final CreateIssueService createIssueService;

    public GenerateIssuesSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService,
                                            final CreateIssueService createIssueService) {
        super(schedulerExecutionService, SchedulerTaskType.GENERATE_ISSUES);
        this.createIssueService = createIssueService;
    }

    @Override
    protected void executeScheduler() {
        createIssueService.createIssues();
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
