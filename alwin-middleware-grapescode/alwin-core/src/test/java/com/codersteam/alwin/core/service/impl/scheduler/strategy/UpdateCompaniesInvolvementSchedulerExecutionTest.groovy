package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesInvolvementService

/**
 * @author Michal Horowic
 */
class UpdateCompaniesInvolvementSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    UpdateCompaniesInvolvementService involvementService

    def "setup"() {
        involvementService = Mock(UpdateCompaniesInvolvementService)
        schedulerExecution = new UpdateCompaniesInvolvementSchedulerExecution(schedulerExecutionService, involvementService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT, false) >> execution

        and:
            1 * involvementService.updateCompaniesInvolvement()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT, false) >> execution

        and:
            1 * involvementService.updateCompaniesInvolvement() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
