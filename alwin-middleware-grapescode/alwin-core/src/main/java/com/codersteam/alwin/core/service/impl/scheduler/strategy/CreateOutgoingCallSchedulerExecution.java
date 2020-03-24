package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.activity.OutgoingCallActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne utworzenia czynności windykacyjnych typu 'telefon wychodzący'
 */
public class CreateOutgoingCallSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Create outgoing call activity";
    private final OutgoingCallActivityService outgoingCallActivityService;

    public CreateOutgoingCallSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService, final OutgoingCallActivityService outgoingCallActivityService) {
        super(schedulerExecutionService, SchedulerTaskType.CREATE_OUTGOING_CALL_ACTIVITY);
        this.outgoingCallActivityService = outgoingCallActivityService;
    }

    @Override
    protected void executeScheduler() {
        outgoingCallActivityService.createOutgoingCallActivity();
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
