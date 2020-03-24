package com.codersteam.alwin.core.api.exception;

/**
 * Klasa wyjątku w przypadku błędów z oczekiwaniem na przeliczenie salda zlecenia
 *
 * @author Tomasz Sliwinski
 */
public class IssueBalanceUpdateWaitException extends RuntimeException {

    public IssueBalanceUpdateWaitException(final String message) {
        super(message);
    }

    public IssueBalanceUpdateWaitException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
