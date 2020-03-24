package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.api.service.issue.UnresolvedIssueService

/**
 * @author Michal Horowic
 */
class ReportUnresolvedIssuesSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    UnresolvedIssueService unresolvedIssueService

    def "setup"() {
        unresolvedIssueService = Mock(UnresolvedIssueService)
        schedulerExecution = new ReportUnresolvedIssuesSchedulerExecution(schedulerExecutionService, unresolvedIssueService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL, false) >> execution

        and:
            1 * unresolvedIssueService.findUnresolvedIssuesAndSendReportEmail()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL, false) >> execution

        and:
            1 * unresolvedIssueService.findUnresolvedIssuesAndSendReportEmail() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
