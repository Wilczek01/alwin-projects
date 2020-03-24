package com.codersteam.alwin.jpa.type;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Status zlecenia
 *
 * @author Michal Horowic
 */
public enum IssueState {

    /**
     * Nowe
     */
    NEW,

    /**
     * Realizowane
     */
    IN_PROGRESS,

    /**
     * Czekające na potwierdzenie zamknięcia przez managera
     */
    WAITING_FOR_TERMINATION,

    /**
     * Zakończone
     */
    DONE,

    /**
     * Anulowane
     */
    CANCELED;

    public static final List<IssueState> OPEN_ISSUE_STATES = asList(NEW, IN_PROGRESS, WAITING_FOR_TERMINATION);

    public static final List<IssueState> NEW_AND_IN_PROGRESS_ISSUE_STATES = asList(NEW, IN_PROGRESS);

    public static final List<IssueState> WAITING_ISSUE_STATES = singletonList(WAITING_FOR_TERMINATION);

    public static final List<IssueState> CLOSED_ISSUE_STATES = asList(DONE, CANCELED);

    public static final List<IssueState> NONE = emptyList();
}
