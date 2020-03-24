package com.codersteam.alwin.common.termination;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Status wypowiedzenia umowy
 *
 * @author Dariusz Rackowski
 */
public enum ContractTerminationState {

    /**
     * Wypowiedzenie utworzone
     */
    NEW,

    /**
     * Wypowiedzenie w trakcie procesowania
     */
    WAITING,

    /**
     * Wypowiedzenie wysłane
     */
    ISSUED,

    /**
     * Umowa aktywowana
     */
    CONTRACT_ACTIVATED,

    /**
     * Wypowiedzenie odroczone
     */
    POSTPONED,

    /**
     * Wypowiedzenie odrzucone
     */
    REJECTED,

    /**
     * Niepowodzenie przetwarzania
     */
    FAILED;

    public static final List<ContractTerminationState> OPEN_CONTRACT_TERMINATION_STATES = asList(NEW, POSTPONED, WAITING, FAILED);

    public static final List<ContractTerminationState> CLOSED_CONTRACT_TERMINATION_STATES = asList(ISSUED, CONTRACT_ACTIVATED, REJECTED);

    }
