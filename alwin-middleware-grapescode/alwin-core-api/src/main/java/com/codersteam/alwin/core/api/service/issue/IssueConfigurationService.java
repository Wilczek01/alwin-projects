package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
@Local
public interface IssueConfigurationService {

    /**
     * Pobiera wszystkie konfiguracje zlecenia windykacyjnego
     *
     * @return lista konfiguracji zlecenia windykacyjnego
     */
    List<IssueTypeConfigurationDto> findAllIssueTypeConfigurations();

    /**
     * Pobranie konfiguracji zlecenia windykacyjnego
     *
     * @param issueTypeName - nazwa typ zlecenia
     * @param segment       - segment
     * @return konfiguracja zlecenia windykacyjnego
     */
    IssueTypeConfigurationDto findIssueTypeConfiguration(IssueTypeName issueTypeName, Segment segment);

    /**
     * Uaktualnienie wybranej konfiguracji zlecenia windykacyjnego
     *
     * @param configurationId           - identyfikator konfiguracji
     * @param issueTypeConfigurationDto - dane do aktualizacji konfiguracji
     */
    void updateIssueTypeConfiguration(final long configurationId, IssueTypeConfigurationDto issueTypeConfigurationDto);

    /**
     * Pobranie wszystkich typów konfiguracji zleceń do tworzenia automatycznego
     * Zwracana konfiguracja typów posortowana jest po DPD (malejąco)
     *
     * @return lista konfiguracji zleceń windykacyjnych tworzonych automatycznie
     */
    List<IssueTypeConfigurationDto> findAllCreateAutomaticallyIssueTypeConfigurations();
}
