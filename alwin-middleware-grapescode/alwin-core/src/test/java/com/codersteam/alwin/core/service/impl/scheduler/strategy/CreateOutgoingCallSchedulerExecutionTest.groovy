package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.service.impl.activity.OutgoingCallActivityService

class CreateOutgoingCallSchedulerExecutionTest extends SchedulerExecutionSpecificationTest {

    OutgoingCallActivityService outgoingCallActivityService

    def "setup"() {
        outgoingCallActivityService = Mock(OutgoingCallActivityService)
        schedulerExecution = new CreateOutgoingCallSchedulerExecution(schedulerExecutionService, outgoingCallActivityService)
    }

    def "should execute scheduler"() {
        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.CREATE_OUTGOING_CALL_ACTIVITY, false) >> execution

        and:
            1 * outgoingCallActivityService.createOutgoingCallActivity()

        and:
            1 * schedulerExecutionService.schedulerExecutionFinished(EXECUTION_ID)
    }

    def "should handle run time error during scheduler execution"() {
        given:
            def errorMessage = "Processing failed"

        when:
            schedulerExecution.execute(false)

        then:
            1 * schedulerExecutionService.schedulerExecutionStarted(SchedulerTaskType.CREATE_OUTGOING_CALL_ACTIVITY, false) >> execution

        and:
            1 * outgoingCallActivityService.createOutgoingCallActivity() >> { throw new RuntimeException(errorMessage) }

        and:
            1 * schedulerExecutionService.schedulerExecutionFailed(EXECUTION_ID, errorMessage)

        and:
            thrown(SchedulerExecution.SchedulerExecutionException)
    }
}
