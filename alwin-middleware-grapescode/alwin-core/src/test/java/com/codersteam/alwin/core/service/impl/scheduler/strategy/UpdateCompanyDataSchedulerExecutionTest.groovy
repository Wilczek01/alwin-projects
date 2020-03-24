package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesService

/**
 * @author Michal Horowic
 */
class UpdateCompanyDataSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    UpdateCompaniesService syncDataService

    def "setup"() {
        syncDataService = Mock(UpdateCompaniesService)
        schedulerExecution = new UpdateCompanyDataSchedulerExecution(schedulerExecutionService, syncDataService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_COMPANY_DATA, false) >> execution

        and:
            1 * syncDataService.syncCompanyData()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_COMPANY_DATA, false) >> execution

        and:
            1 * syncDataService.syncCompanyData() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }

}
