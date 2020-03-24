package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.api.model.scheduler.SchedulerTaskTypeDto
import spock.lang.Specification
import spock.lang.Subject

class SchedulerTaskTypeConverterTest extends Specification {

    @Subject
    def converter = new SchedulerTaskTypeConverter()

    def "should convert SchedulerTaskTypeDto to SchedulerTaskType"() {
        expect:
            converter.convert(stateDto, null, null) == state
        where:
            stateDto                                                                    | state
            SchedulerTaskTypeDto.UPDATE_ISSUES_BALANCE                                  | SchedulerTaskType.UPDATE_ISSUES_BALANCE
            SchedulerTaskTypeDto.GENERATE_ISSUES                                        | SchedulerTaskType.GENERATE_ISSUES
            SchedulerTaskTypeDto.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED | SchedulerTaskType.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED
            SchedulerTaskTypeDto.UPDATE_COMPANY_DATA                                    | SchedulerTaskType.UPDATE_COMPANY_DATA
            SchedulerTaskTypeDto.UPDATE_COMPANIES_INVOLVEMENT                           | SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT
            SchedulerTaskTypeDto.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL           | SchedulerTaskType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL
            SchedulerTaskTypeDto.CREATE_OUTGOING_CALL_ACTIVITY                          | SchedulerTaskType.CREATE_OUTGOING_CALL_ACTIVITY
            SchedulerTaskTypeDto.TAG_OVERDUE_ISSUES                                     | SchedulerTaskType.TAG_OVERDUE_ISSUES
            SchedulerTaskTypeDto.UPDATE_DECLARATIONS_BALANCE                            | SchedulerTaskType.UPDATE_DECLARATIONS_BALANCE
            SchedulerTaskTypeDto.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION   | SchedulerTaskType.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION
    }
}
