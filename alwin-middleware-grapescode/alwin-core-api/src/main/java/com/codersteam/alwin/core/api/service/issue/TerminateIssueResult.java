package com.codersteam.alwin.core.api.service.issue;

/**
 * @author Piotr Naroznik
 */
public enum TerminateIssueResult {

    /**
     * Zlecenie zostało zakończone przedterminowo.
     */
    ISSUE_WAS_TERMINATED("Zlecenie zostało zakończone przedterminowo"),

    /**
     * Brak uprawnień operatora do zakończenia zlecenia.
     * Żądanie o przedterminowe zakończenie zlecenia zostało utowrzone i czeka na akceptację managera.
     */
    ISSUE_TERMINATION_REQUEST_CREATED("Nie posiadasz uprawnień do zakończenia zlecenia. Żądanie o przedterminowe zakończenie zlecenia zostało utworzone, " +
            "czeka na akceptację managera");

    private final String message;

    TerminateIssueResult(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
