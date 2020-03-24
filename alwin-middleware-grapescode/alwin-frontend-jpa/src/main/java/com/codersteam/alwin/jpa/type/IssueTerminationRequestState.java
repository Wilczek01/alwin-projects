package com.codersteam.alwin.jpa.type;

/**
 * Status żądania zamknięcie zlecenia
 *
 * @author Piotr Narożnik
 */
public enum IssueTerminationRequestState {
    /**
     * Nowe żądanie
     */
    NEW,

    /**
     * Zaakceptowane
     */
    ACCEPTED,

    /**
     * Odrzucone
     */
    REJECTED
}

