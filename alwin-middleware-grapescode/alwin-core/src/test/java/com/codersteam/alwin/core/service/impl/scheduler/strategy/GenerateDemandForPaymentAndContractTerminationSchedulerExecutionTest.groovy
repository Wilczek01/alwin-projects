package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.notice.CreateDemandForPaymentService
import com.codersteam.alwin.core.service.impl.termination.CreateContractTerminationService

class GenerateDemandForPaymentAndContractTerminationSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    private CreateDemandForPaymentService createDemandForPaymentService = Mock(CreateDemandForPaymentService)
    private CreateContractTerminationService createContractTerminationService = Mock(CreateContractTerminationService)

    def "setup"() {
        schedulerExecution = new GenerateDemandForPaymentAndContractTerminationSchedulerExecution(schedulerExecutionService, createDemandForPaymentService, createContractTerminationService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION, false) >> execution

        and:
            1 * createDemandForPaymentService.prepareDemandsForPayment()
            1 * createContractTerminationService.prepareContractTerminations()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION, false) >> execution

        and:
            1 * createDemandForPaymentService.prepareDemandsForPayment() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
