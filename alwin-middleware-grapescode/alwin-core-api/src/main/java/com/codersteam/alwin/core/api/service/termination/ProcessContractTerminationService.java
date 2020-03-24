package com.codersteam.alwin.core.api.service.termination;

import com.codersteam.alwin.core.api.model.termination.TerminationDto;

/**
 * Serwis do obsługi procesowania wypowiedzeń umów
 *
 * @author Dariusz Rackowski
 */
public interface ProcessContractTerminationService {

    /**
     * Przesyła w osobnej transakcji wypowiedzenie umowy do eFaktury
     *
     * @param terminationDto         - wypowiedzenie umowy
     * @param loggedOperatorFullName - pełna nazwa aktualnie zalogowanego operatora
     */
    void sendTermination(final TerminationDto terminationDto, final String loggedOperatorFullName);

}
