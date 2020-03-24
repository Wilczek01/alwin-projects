package com.codersteam.alwin.core.api.service.notice;

import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;

import javax.ejb.Local;

/**
 * Serwis do obsługi procesowania wezwań do zapłaty
 *
 * @author Michal Horowic
 */
@Local
public interface ProcessDemandForPaymentService {

    /**
     * Przesyła w osobnej transakcji sugestię wezwania do zapłaty do eFaktury
     *
     * @param demandForPayment       - wezwanie do zapłaty
     * @param loggedOperatorFullName - imię i nazwisko zalogowanego operatora
     */
    void sendDemand(final DemandForPaymentDto demandForPayment, final String loggedOperatorFullName);
}
