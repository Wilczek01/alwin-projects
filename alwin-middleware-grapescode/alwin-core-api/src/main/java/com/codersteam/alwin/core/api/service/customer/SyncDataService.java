package com.codersteam.alwin.core.api.service.customer;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.model.AidaPersonDto;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Piotr Naroznik
 */
@Local
public interface SyncDataService {

    /**
     * Aktualizacja danych firmy na podstawie parametrów
     * Oprócz danych firmy aktualizowane są również dane adresowe, kontaktowe oraz osoby.
     *
     * @param aidaCompany  - dane firmy z systemu AIDA
     * @param aidaPersons  - dane osób firmy z systemu AIDA
     * @param extCompanyId - zewnętrzny identyfikator firmy do aktualizacji
     */
    void updateCompanyIfExist(AidaCompanyDto aidaCompany, List<AidaPersonDto> aidaPersons, Long extCompanyId);
}
