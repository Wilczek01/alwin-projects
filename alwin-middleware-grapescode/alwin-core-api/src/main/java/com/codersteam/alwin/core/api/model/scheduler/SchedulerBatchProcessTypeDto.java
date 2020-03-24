package com.codersteam.alwin.core.api.model.scheduler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Identyfikator grupy zadań cyklicznych
 */
public class SchedulerBatchProcessTypeDto {
    private static final String BASE_SCHEDULE_TASKS_KEY = "BASE_SCHEDULE_TASKS";
    private static final String UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES_KEY = "UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES";
    private static final String UPDATE_ISSUES_BALANCE_KEY = "UPDATE_ISSUES_BALANCE";
    private static final String GENERATE_ISSUES_KEY = "GENERATE_ISSUES";
    private static final String CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED_KEY = "CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED";
    private static final String UPDATE_COMPANY_DATA_KEY = "UPDATE_COMPANY_DATA";
    private static final String UPDATE_COMPANIES_INVOLVEMENT_KEY = "UPDATE_COMPANIES_INVOLVEMENT";
    private static final String FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL_KEY = "FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL";
    private static final String CREATE_OUTGOING_CALL_ACTIVITY_KEY = "CREATE_OUTGOING_CALL_ACTIVITY";
    private static final String TAG_OVERDUE_ISSUES_KEY = "TAG_OVERDUE_ISSUES";
    private static final String UPDATE_DECLARATIONS_BALANCE_KEY = "UPDATE_DECLARATIONS_BALANCE";
    private static final String GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION_KEY = "GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION";

    public static final SchedulerBatchProcessTypeDto BASE_SCHEDULE_TASKS = new SchedulerBatchProcessTypeDto(BASE_SCHEDULE_TASKS_KEY,
            "Aktualizacja sald, otwarcie nowych oraz zamknięcie przeterminowanych zleceń. " +
                    "Przetwarzanie przeterminowanych dokumentów i wysłanie raportu email. " +
                    "Aktualizacja firm: dane adresowe i kontaktowe, zaangażowanie firm. " +
                    "Wysłanie raportu o nierozwiązanych zleceniach. " +
                    "Utworzenie czynności windykacyjnych typu 'telefon wychodzący'. " +
                    "Utworzenie nowych wezwań do zapłaty oraz wypowiedzeń umów. " +
                    "Aktualizacja sald deklaracji spłat");
    public static final SchedulerBatchProcessTypeDto UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES = new SchedulerBatchProcessTypeDto(
            UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES_KEY, SchedulerTaskTypeDto.UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES.getLabel());
    public static final SchedulerBatchProcessTypeDto UPDATE_ISSUES_BALANCE = new SchedulerBatchProcessTypeDto(
            UPDATE_ISSUES_BALANCE_KEY, SchedulerTaskTypeDto.UPDATE_ISSUES_BALANCE.getLabel());
    public static final SchedulerBatchProcessTypeDto GENERATE_ISSUES = new SchedulerBatchProcessTypeDto(GENERATE_ISSUES_KEY,
            SchedulerTaskTypeDto.GENERATE_ISSUES.getLabel());
    public static final SchedulerBatchProcessTypeDto CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED = new SchedulerBatchProcessTypeDto(CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED_KEY,
            SchedulerTaskTypeDto.CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED.getLabel());
    public static final SchedulerBatchProcessTypeDto UPDATE_COMPANY_DATA = new SchedulerBatchProcessTypeDto(UPDATE_COMPANY_DATA_KEY,
            SchedulerTaskTypeDto.UPDATE_COMPANY_DATA.getLabel());
    public static final SchedulerBatchProcessTypeDto UPDATE_COMPANIES_INVOLVEMENT = new SchedulerBatchProcessTypeDto(UPDATE_COMPANIES_INVOLVEMENT_KEY,
            SchedulerTaskTypeDto.UPDATE_COMPANIES_INVOLVEMENT.getLabel());
    public static final SchedulerBatchProcessTypeDto FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL = new SchedulerBatchProcessTypeDto(FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL_KEY,
            SchedulerTaskTypeDto.FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL.getLabel());
    public static final SchedulerBatchProcessTypeDto CREATE_OUTGOING_CALL_ACTIVITY = new SchedulerBatchProcessTypeDto(CREATE_OUTGOING_CALL_ACTIVITY_KEY,
            SchedulerTaskTypeDto.CREATE_OUTGOING_CALL_ACTIVITY.getLabel());
    public static final SchedulerBatchProcessTypeDto TAG_OVERDUE_ISSUES = new SchedulerBatchProcessTypeDto(TAG_OVERDUE_ISSUES_KEY,
            SchedulerTaskTypeDto.TAG_OVERDUE_ISSUES.getLabel());
    public static final SchedulerBatchProcessTypeDto UPDATE_DECLARATIONS_BALANCE = new SchedulerBatchProcessTypeDto(UPDATE_DECLARATIONS_BALANCE_KEY,
            SchedulerTaskTypeDto.UPDATE_DECLARATIONS_BALANCE.getLabel());
    public static final SchedulerBatchProcessTypeDto GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION = new SchedulerBatchProcessTypeDto(GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION_KEY,
            SchedulerTaskTypeDto.GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION.getLabel());

    public static final List<SchedulerBatchProcessTypeDto> ALL_SCHEDULER_BATCH_PROCESSES = Arrays.asList(
            UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES,
            UPDATE_ISSUES_BALANCE,
            GENERATE_ISSUES,
            CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED,
            UPDATE_COMPANY_DATA,
            UPDATE_COMPANIES_INVOLVEMENT,
            FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL,
            CREATE_OUTGOING_CALL_ACTIVITY,
            TAG_OVERDUE_ISSUES,
            UPDATE_DECLARATIONS_BALANCE,
            GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION,
            BASE_SCHEDULE_TASKS
    );

    private final String key;
    private final String label;

    @JsonCreator
    public SchedulerBatchProcessTypeDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static SchedulerBatchProcessTypeDto valueOf(final String name) {
        switch (name) {
            case BASE_SCHEDULE_TASKS_KEY:
                return BASE_SCHEDULE_TASKS;
            case UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES_KEY:
                return UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES;
            case UPDATE_ISSUES_BALANCE_KEY:
                return UPDATE_ISSUES_BALANCE;
            case GENERATE_ISSUES_KEY:
                return GENERATE_ISSUES;
            case CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED_KEY:
                return CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED;
            case UPDATE_COMPANY_DATA_KEY:
                return UPDATE_COMPANY_DATA;
            case UPDATE_COMPANIES_INVOLVEMENT_KEY:
                return UPDATE_COMPANIES_INVOLVEMENT;
            case FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL_KEY:
                return FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL;
            case CREATE_OUTGOING_CALL_ACTIVITY_KEY:
                return CREATE_OUTGOING_CALL_ACTIVITY;
            case TAG_OVERDUE_ISSUES_KEY:
                return TAG_OVERDUE_ISSUES;
            case UPDATE_DECLARATIONS_BALANCE_KEY:
                return UPDATE_DECLARATIONS_BALANCE;
            case GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION_KEY:
                return GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION;
            default:
                throw new IllegalArgumentException("Unable to map " + name);
        }
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "SchedulerProcessDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

}
