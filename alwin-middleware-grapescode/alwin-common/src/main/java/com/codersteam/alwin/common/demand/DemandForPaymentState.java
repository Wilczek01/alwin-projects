package com.codersteam.alwin.common.demand;

/**
 * Status wezwania do zapłaty
 *
 * @author Tomasz Sliwinski
 */
public enum DemandForPaymentState {

    /**
     * Nowe
     */
    NEW,

    /**
     * Czekające na wystawienie
     */
    WAITING,

    /**
     * Wysłane z niepowodzeniem
     */
    FAILED,

    /**
     * Wystawione
     */
    ISSUED,

    /**
     * Wezwanie anulowane
     */
    CANCELED,

    /**
     * Odrzucone
     */
    REJECTED;

    /**
     * Sprawdza czy podany stan oznacza poprawne wysłanie lub w trakcie wysyłania
     *
     * @param state - stan
     * @return true, jeżeli podany stan oznacza poprawne wysłanie lub wezwanie jest w trakcie wysyłania, false w przeciwnym wypadku
     */
    public static boolean successfullyIssuedOrProcessing(final DemandForPaymentState state) {
        return state == ISSUED || state == WAITING;
    }
}
