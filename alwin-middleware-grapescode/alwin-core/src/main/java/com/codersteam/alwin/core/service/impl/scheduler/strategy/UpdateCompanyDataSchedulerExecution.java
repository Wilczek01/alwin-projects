package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Zadanie cykliczne uaktualnienia danych firm wraz z danymi adresowymi, kontaktowymi oraz osobami.
 *
 * @author Michal Horowic
 */
public class UpdateCompanyDataSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Update company data";
    private final UpdateCompaniesService syncDataService;

    public UpdateCompanyDataSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService, final UpdateCompaniesService syncDataService) {
        super(schedulerExecutionService, SchedulerTaskType.UPDATE_COMPANY_DATA);
        this.syncDataService = syncDataService;
    }

    @Override
    protected void executeScheduler() {
        syncDataService.syncCompanyData();
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
