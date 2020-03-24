package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.DeclarationService
import com.codersteam.alwin.core.api.service.issue.IssueService
import spock.lang.Ignore

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2

/**
 * @author Michal Horowic
 */
class UpdateDeclarationsBalanceSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    IssueService issueService
    DeclarationService declarationService
    DateProvider dateProvider

    def "setup"() {
        issueService = Mock(IssueService)
        declarationService = Mock(DeclarationService)
        dateProvider = Mock(DateProvider)
        schedulerExecution = new UpdateDeclarationsBalanceSchedulerExecution(schedulerExecutionService, issueService, declarationService, dateProvider)
    }

    def "should execute scheduler"() {
        given:
            def endDate = new Date()
            def startDate = new Date()
            1 * dateProvider.getDateStartOfDayMinusDays(7) >> endDate
            1 * dateProvider.getCurrentDate() >> startDate

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_DECLARATIONS_BALANCE, false) >> execution

        and:
            1 * issueService.findIssueIdsWithUnpaidDeclarations(startDate, endDate) >> [ISSUE_ID_1, ISSUE_ID_2]

        and:
            1 * declarationService.updateIssueDeclarations(ISSUE_ID_1)
            1 * declarationService.updateIssueDeclarations(ISSUE_ID_2)

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should continue scheduler execution while updating failed for one issue"() {
        given:
            def endDate = new Date()
            def startDate = new Date()
            1 * dateProvider.getDateStartOfDayMinusDays(7) >> endDate
            1 * dateProvider.getCurrentDate() >> startDate

        and:
            1 * declarationService.updateIssueDeclarations(ISSUE_ID_1) >> { throw new IllegalStateException() }
            1 * declarationService.updateIssueDeclarations(ISSUE_ID_2)

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_DECLARATIONS_BALANCE, false) >> execution

        and:
            1 * issueService.findIssueIdsWithUnpaidDeclarations(startDate, endDate) >> [ISSUE_ID_1, ISSUE_ID_2]

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    @Ignore("Non deterministic")
    def "should handle runtime error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        and:
            def endDate = new Date()
            def startDate = new Date()
            1 * dateProvider.getDateStartOfDayMinusDays(7) >> endDate
            1 * dateProvider.getCurrentDate() >> startDate

        and:
            issueService.findIssueIdsWithUnpaidDeclarations(startDate, endDate) >> { throw new RuntimeException(errorMessage) }

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.UPDATE_DECLARATIONS_BALANCE, false) >> execution

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
