package com.codersteam.alwin.core.api.service.termination;

import com.codersteam.alwin.common.termination.ContractTerminationType;

import javax.ejb.Local;

/**
 * Serwis tworzący nowe wypowiedzenia umów
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface ContractTerminationCreatorService {

    /**
     * Przygotowanie danych do rozpoczęcia procesu wystawienia wypowiedzenia danej umowy
     *
     * @param contractTerminationInitialData - zestaw danych do rozpoczęcia procesu wystawienia wypowiedzenia umowy
     * @param terminationType
     */
    void prepareContractTermination(ContractTerminationInitialData contractTerminationInitialData, final ContractTerminationType terminationType);

}
