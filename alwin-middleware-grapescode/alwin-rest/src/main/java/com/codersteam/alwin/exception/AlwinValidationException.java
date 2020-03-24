package com.codersteam.alwin.exception;

/**
 * Wyjątek mówiący o błędzie walidacji,automatycznie opakowywany
 *
 * @author Michal Horowic
 */
public class AlwinValidationException extends RuntimeException {

    public AlwinValidationException(final String message) {
        super(message);
    }
}
