package com.codersteam.alwin.core.api.service.notice;

import javax.ejb.Local;
import java.util.List;

/**
 * Serwis do wyznaczania komunikatów dla ręcznych wezwań do zapłaty (informacja o wpływie ręcznego wezwania na ewentualnie trwający proces)
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface ManualDemandForPaymentMessageService {

    /**
     * Wyznaczania komunikatów dotyczącego wezwania do zapłaty utworzonego ręcznie (informacja o wpływie ręcznego wezwania na ewentualnie trwający proces)
     *
     * @param demandForPaymentId - identyfikator wezwania do zapłaty
     * @return lista komunikatów dla operatora systemu
     */
    List<String> determineManualDemandForPaymentMessages(Long demandForPaymentId);

}
