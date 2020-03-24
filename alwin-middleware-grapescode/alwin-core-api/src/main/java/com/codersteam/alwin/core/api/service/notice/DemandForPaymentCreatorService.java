package com.codersteam.alwin.core.api.service.notice;

import javax.ejb.Local;

/**
 * Serwis tworzący nowe wezwania do zapłaty
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface DemandForPaymentCreatorService {

    /**
     * Przygotowanie danych do rozpoczęcia procesu wystawienia wezwania do zapłaty dla danej umowy
     *
     * @param demandForPaymentInitialData - zestaw danych do rozpoczęcia procesu wystawienia wezwania określonego typu dla umowy
     */
    void prepareDemandForPayment(DemandForPaymentInitialData demandForPaymentInitialData);

    /**
     * Przygotowanie danych do rozpoczęcia ręcznego procesu wystawienia wezwania do zapłaty dla danej umowy
     *
     * @param manualDemandForPaymentInitialData - zestaw danych do rozpoczęcia ręcznego procesu wystawienia wezwania określonego typu dla umowy
     * @return rezultat operacji tworzenia wezwania
     */
    ManualPrepareDemandForPaymentResult prepareManualDemandForPayment(ManualDemandForPaymentInitialData manualDemandForPaymentInitialData);
}
