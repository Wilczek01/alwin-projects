package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.issue.ActiveIssuesBalanceUpdaterService

/**
 * @author Michal Horowic
 */
class UpdateIssueBalanceSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService

    def "setup"() {
        activeIssuesBalanceUpdaterService = Mock(ActiveIssuesBalanceUpdaterService)
        schedulerExecution = new UpdateIssueBalanceSchedulerExecution(schedulerExecutionService, activeIssuesBalanceUpdaterService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_ISSUES_BALANCE, false) >> execution

        and:
            1 * activeIssuesBalanceUpdaterService.updateActiveIssuesBalance()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_ISSUES_BALANCE, false) >> execution

        and:
            1 * activeIssuesBalanceUpdaterService.updateActiveIssuesBalance() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
