package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.issue.CreateTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne utworzenia czynności windykacyjnych typu 'telefon wychodzący'
 *
 * @author Michal Horowic
 */
public class TagOverdueIssuesSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Tag overdue issues";
    private final CreateTagService createTagService;

    public TagOverdueIssuesSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService, final CreateTagService createTagService) {
        super(schedulerExecutionService, SchedulerTaskType.TAG_OVERDUE_ISSUES);
        this.createTagService = createTagService;
    }

    @Override
    protected void executeScheduler() {
        createTagService.tagOverdueIssues();
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
