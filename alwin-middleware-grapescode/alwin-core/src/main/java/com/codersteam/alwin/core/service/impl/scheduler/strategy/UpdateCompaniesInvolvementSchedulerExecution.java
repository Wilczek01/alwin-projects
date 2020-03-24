package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesInvolvementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne uaktualnienia zaangażowania firm z otwartym zleceniem windykacyjnym
 *
 * @author Michal Horowic
 */
public class UpdateCompaniesInvolvementSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Update company involvement";
    private final UpdateCompaniesInvolvementService involvementService;

    public UpdateCompaniesInvolvementSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService, final UpdateCompaniesInvolvementService involvementService) {
        super(schedulerExecutionService, SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT);
        this.involvementService = involvementService;
    }

    @Override
    protected void executeScheduler() {
        involvementService.updateCompaniesInvolvement();
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
