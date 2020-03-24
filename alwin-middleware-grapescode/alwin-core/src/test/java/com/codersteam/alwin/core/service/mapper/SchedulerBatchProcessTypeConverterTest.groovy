package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto
import spock.lang.Specification
import spock.lang.Subject

class SchedulerBatchProcessTypeConverterTest extends Specification {

    @Subject
    def converter = new SchedulerBatchProcessTypeConverter()

    def "should convert SchedulerBatchProcessTypeDto to SchedulerBatchProcessType"() {
        expect:
            converter.convert(typeDto, null, null) == type
        where:
            typeDto                                                                             | type
            SchedulerBatchProcessTypeDto.BASE_SCHEDULE_TASKS                                    | SchedulerBatchProcessType.BASE_SCHEDULE_TASKS
            SchedulerBatchProcessTypeDto.UPDATE_ISSUES_BALANCE                                  | SchedulerBatchProcessType.UPDATE_ISSUES_BALANCE
            SchedulerBatchProcessTypeDto.GENERATE_ISSUES                                        | SchedulerBatchProcessType.GENERATE_ISSUES
            SchedulerBatchProcessTypeDto.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED | SchedulerBatchProcessType.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED
            SchedulerBatchProcessTypeDto.UPDATE_COMPANY_DATA                                    | SchedulerBatchProcessType.UPDATE_COMPANY_DATA
            SchedulerBatchProcessTypeDto.UPDATE_COMPANIES_INVOLVEMENT                           | SchedulerBatchProcessType.UPDATE_COMPANIES_INVOLVEMENT
            SchedulerBatchProcessTypeDto.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL           | SchedulerBatchProcessType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL
            SchedulerBatchProcessTypeDto.CREATE_OUTGOING_CALL_ACTIVITY                          | SchedulerBatchProcessType.CREATE_OUTGOING_CALL_ACTIVITY
            SchedulerBatchProcessTypeDto.TAG_OVERDUE_ISSUES                                     | SchedulerBatchProcessType.TAG_OVERDUE_ISSUES
            SchedulerBatchProcessTypeDto.UPDATE_DECLARATIONS_BALANCE                            | SchedulerBatchProcessType.UPDATE_DECLARATIONS_BALANCE
            SchedulerBatchProcessTypeDto.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION   | SchedulerBatchProcessType.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION
    }
}
