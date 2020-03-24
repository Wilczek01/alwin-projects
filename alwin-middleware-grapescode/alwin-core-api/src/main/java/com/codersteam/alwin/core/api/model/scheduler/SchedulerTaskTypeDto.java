package com.codersteam.alwin.core.api.model.scheduler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Typ zadania cyklicznego
 *
 * @author Dariusz Rackowski
 */
public class SchedulerTaskTypeDto {

    private static final String UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES_KEY = "UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES";
    private static final String UPDATE_ISSUES_BALANCE_KEY = "UPDATE_ISSUES_BALANCE";
    private static final String GENERATE_ISSUES_KEY = "GENERATE_ISSUES";
    private static final String CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED_KEY = "CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED";
    private static final String FIND_MISSING_ISSUES_AND_SEND_REPORT_EMAIL_KEY = "FIND_MISSING_ISSUES_AND_SEND_REPORT_EMAIL";
    private static final String UPDATE_COMPANY_DATA_KEY = "UPDATE_COMPANY_DATA";
    private static final String UPDATE_COMPANIES_INVOLVEMENT_KEY = "UPDATE_COMPANIES_INVOLVEMENT";
    private static final String FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL_KEY = "FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL";
    private static final String CREATE_OUTGOING_CALL_ACTIVITY_KEY = "CREATE_OUTGOING_CALL_ACTIVITY";
    private static final String TAG_OVERDUE_ISSUES_KEY = "TAG_OVERDUE_ISSUES";
    private static final String UPDATE_DECLARATIONS_BALANCE_KEY = "UPDATE_DECLARATIONS_BALANCE";
    private static final String GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION_KEY = "GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION";

    public static final SchedulerTaskTypeDto UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES =
            new SchedulerTaskTypeDto(UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES_KEY, "Uaktualnienie sald otwartych zleceń windykacyjnych " +
                    "i powiązanych z nimi faktur oraz zamknięcie przeterminowanych zleceń i w razie potrzeby utworzenie nowych z odpowiednim typem");
    public static final SchedulerTaskTypeDto UPDATE_ISSUES_BALANCE =
            new SchedulerTaskTypeDto(UPDATE_ISSUES_BALANCE_KEY, "Uaktualnienie sald otwartych zleceń windykacyjnych oraz powiązanych z nimi faktur");
    public static final SchedulerTaskTypeDto GENERATE_ISSUES =
            new SchedulerTaskTypeDto(GENERATE_ISSUES_KEY, "Utworzenie nowych zleceń windykacyjnych");
    public static final SchedulerTaskTypeDto CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED =
            new SchedulerTaskTypeDto(CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED_KEY, "Zamknięcie przeterminowanych zleceń i w razie potrzeby utworzenie nowych z odpowiednim typem");
    public static final SchedulerTaskTypeDto FIND_MISSING_ISSUES_AND_SEND_REPORT_EMAIL =
            new SchedulerTaskTypeDto(FIND_MISSING_ISSUES_AND_SEND_REPORT_EMAIL_KEY, "Zebranie listy przeterminowanych dokumentów i wysłanie raportu e-mailem do managera");
    public static final SchedulerTaskTypeDto UPDATE_COMPANY_DATA =
            new SchedulerTaskTypeDto(UPDATE_COMPANY_DATA_KEY, "Uaktualnienie danych firm");
    public static final SchedulerTaskTypeDto UPDATE_COMPANIES_INVOLVEMENT =
            new SchedulerTaskTypeDto(UPDATE_COMPANIES_INVOLVEMENT_KEY, "Uaktualnienie zaangażowania firm");
    public static final SchedulerTaskTypeDto FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL =
            new SchedulerTaskTypeDto(FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL_KEY, "Zebranie listy nierozwiązanych zleceń i wysłanie raportu e-mailem do managera");
    public static final SchedulerTaskTypeDto CREATE_OUTGOING_CALL_ACTIVITY =
            new SchedulerTaskTypeDto(CREATE_OUTGOING_CALL_ACTIVITY_KEY, "Tworzenie czynności windykacyjnych typu 'telefon wychodzący'");
    public static final SchedulerTaskTypeDto TAG_OVERDUE_ISSUES =
            new SchedulerTaskTypeDto(TAG_OVERDUE_ISSUES_KEY, "Oznaczanie zaległych zleceń predefiniowaną etykietą");
    public static final SchedulerTaskTypeDto UPDATE_DECLARATIONS_BALANCE =
            new SchedulerTaskTypeDto(UPDATE_DECLARATIONS_BALANCE_KEY, "Aktualizacja sald deklaracji spłat");
    public static final SchedulerTaskTypeDto GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION =
            new SchedulerTaskTypeDto(GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION_KEY, "Utworzenie nowych wezwań do zapłaty oraz wypowiedzeń umów");

    private final String key;
    private final String label;

    @JsonCreator
    public SchedulerTaskTypeDto(@JsonProperty("key") final String key, @JsonProperty("label") final String label) {
        this.key = key;
        this.label = label;
    }

    public static SchedulerTaskTypeDto valueOf(final String name) {
        switch (name) {
            case UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES_KEY:
                return UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES;
            case UPDATE_ISSUES_BALANCE_KEY:
                return UPDATE_ISSUES_BALANCE;
            case GENERATE_ISSUES_KEY:
                return GENERATE_ISSUES;
            case CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED_KEY:
                return CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED;
            case FIND_MISSING_ISSUES_AND_SEND_REPORT_EMAIL_KEY:
                return FIND_MISSING_ISSUES_AND_SEND_REPORT_EMAIL;
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
        return "SchedulerTypeDto{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

}
