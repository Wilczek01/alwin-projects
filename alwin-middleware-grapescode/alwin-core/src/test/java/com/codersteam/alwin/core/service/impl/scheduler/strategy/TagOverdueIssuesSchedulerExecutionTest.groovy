package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.issue.CreateTagService

/**
 * @author Michal Horowic
 */
class TagOverdueIssuesSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    CreateTagService createTagService

    def "setup"() {
        createTagService = Mock(CreateTagService)
        schedulerExecution = new TagOverdueIssuesSchedulerExecution(schedulerExecutionService, createTagService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.TAG_OVERDUE_ISSUES, false) >> execution

        and:
            1 * createTagService.tagOverdueIssues()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.TAG_OVERDUE_ISSUES, false) >> execution

        and:
            1 * createTagService.tagOverdueIssues() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
