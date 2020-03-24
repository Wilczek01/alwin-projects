package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.issue.CreateIssueService

class GenerateIssuesSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    CreateIssueService createIssueService

    def "setup"() {
        createIssueService = Mock(CreateIssueService)
        schedulerExecution = new GenerateIssuesSchedulerExecution(schedulerExecutionService, createIssueService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.GENERATE_ISSUES, false) >> execution

        and:
            1 * createIssueService.createIssues()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.GENERATE_ISSUES, false) >> execution

        and:
            1 * createIssueService.createIssues() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
