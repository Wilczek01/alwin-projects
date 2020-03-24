package com.codersteam.alwin.core.service.impl.scheduler.strategy

import com.codersteam.alwin.core.api.model.scheduler.SchedulerExecutionInfoDto
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService
import spock.lang.Specification
import spock.lang.Subject

/**
 * @author Michal Horowic
 */
abstract class SchedulerExecutionSpecificationTest extends Specification {

    @Subject
    SchedulerExecution schedulerExecution

    SchedulerExecutionInfoService schedulerExecutionService
    def static final EXECUTION_ID = 1234
    SchedulerExecutionInfoDto execution

    def "setup"() {
        schedulerExecutionService = Mock(SchedulerExecutionInfoService)
        execution = Mock(SchedulerExecutionInfoDto)
        execution.getId() >> EXECUTION_ID
    }

}
