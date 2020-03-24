package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.notice.CreateDemandForPaymentService;
import com.codersteam.alwin.core.service.impl.termination.CreateContractTerminationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Generuje nowe wezwania do zapłaty oraz wypowiedzenia umów do potwierdzenia prze managera
 */
public class GenerateDemandForPaymentAndContractTerminationSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Generate demand for payments and contract terminations";
    private final CreateDemandForPaymentService createDemandForPaymentService;
    private final CreateContractTerminationService createContractTerminationService;

    public GenerateDemandForPaymentAndContractTerminationSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService,
                                                                            final CreateDemandForPaymentService createDemandForPaymentService,
                                                                            final CreateContractTerminationService createContractTerminationService) {
        super(schedulerExecutionService, SchedulerTaskType.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION);
        this.createDemandForPaymentService = createDemandForPaymentService;
        this.createContractTerminationService = createContractTerminationService;
    }

    @Override
    protected void executeScheduler() {
        // wygenerowanie nowych wezwań do zapłaty
        createDemandForPaymentService.prepareDemandsForPayment();

        // wygenerowanie nowych wypowiedzeń umów
        createContractTerminationService.prepareContractTerminations();
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
