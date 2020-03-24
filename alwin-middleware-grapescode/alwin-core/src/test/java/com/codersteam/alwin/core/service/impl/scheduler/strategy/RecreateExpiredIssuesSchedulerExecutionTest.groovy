package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.issue.CloseExpiredIssuesService

/**
 * @author Michal Horowic
 */
class RecreateExpiredIssuesSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    CloseExpiredIssuesService issueService

    def "setup"() {
        issueService = Mock(CloseExpiredIssuesService)
        schedulerExecution = new RecreateExpiredIssuesSchedulerExecution(schedulerExecutionService, issueService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED, false) >> execution

        and:
            1 * issueService.closeExpiredIssuesAndCreateChildIssuesIfNeeded()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED, false) >> execution

        and:
            1 * issueService.closeExpiredIssuesAndCreateChildIssuesIfNeeded() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
