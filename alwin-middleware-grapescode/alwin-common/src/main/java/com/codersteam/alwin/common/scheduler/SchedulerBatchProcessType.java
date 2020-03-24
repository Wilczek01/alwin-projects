package com.codersteam.alwin.common.scheduler;

/**
 * Identyfikator grupy zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
public enum SchedulerBatchProcessType {

    BASE_SCHEDULE_TASKS(
            SchedulerTaskType.UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES,
            SchedulerTaskType.GENERATE_ISSUES,
            SchedulerTaskType.CREATE_OUTGOING_CALL_ACTIVITY,
            SchedulerTaskType.UPDATE_COMPANY_DATA,
            SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT,
            SchedulerTaskType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL,
            SchedulerTaskType.TAG_OVERDUE_ISSUES,
            SchedulerTaskType.UPDATE_DECLARATIONS_BALANCE,
            SchedulerTaskType.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION
    ),
    UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES(
            SchedulerTaskType.UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES
    ),
    UPDATE_ISSUES_BALANCE(
            SchedulerTaskType.UPDATE_ISSUES_BALANCE
    ),
    GENERATE_ISSUES(
            SchedulerTaskType.GENERATE_ISSUES
    ),
    CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED(
            SchedulerTaskType.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED
    ),
    UPDATE_COMPANY_DATA(
            SchedulerTaskType.UPDATE_COMPANY_DATA
    ),
    UPDATE_COMPANIES_INVOLVEMENT(
            SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT
    ),
    FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL(
            SchedulerTaskType.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL
    ),
    CREATE_OUTGOING_CALL_ACTIVITY(
            SchedulerTaskType.CREATE_OUTGOING_CALL_ACTIVITY
    ),
    TAG_OVERDUE_ISSUES(
            SchedulerTaskType.TAG_OVERDUE_ISSUES
    ),
    UPDATE_DECLARATIONS_BALANCE(
            SchedulerTaskType.UPDATE_DECLARATIONS_BALANCE
    ),
    GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION(
            SchedulerTaskType.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION
    ),
    ;

    /**
     * Konstruktor inicjujący listę zadań cyklicznych do grupy zadań
     *
     * @param schedulerTypes - lista zadań cyklicznych należąca do procesu
     */
    SchedulerBatchProcessType(final SchedulerTaskType... schedulerTypes) {
        this.schedulerTypes = schedulerTypes;
    }

    private final SchedulerTaskType[] schedulerTypes;

    public SchedulerTaskType[] getSchedulerTypes() {
        return schedulerTypes;
    }
}
