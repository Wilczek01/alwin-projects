package com.codersteam.alwin.core.api.service.contract;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.contract.ContractFinancialSummaryDto;
import com.codersteam.alwin.core.api.model.contract.ContractWithExclusions;
import com.codersteam.alwin.core.api.model.contract.FormalDebtCollectionContractWithExclusions;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Serwis do obsługi konraktów
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface ContractService {

    /**
     * Wygenerowanie listy podsumowań finansowych per umowa w oparciu o identyfikator zlecenia
     *
     * @param issueId     - identyfikator zlecenia
     * @param overdueOnly - czy zwracać tylko po terminie płatności
     * @param notPaidOnly - czy zwracać tylko nieopłacone
     * @return lista podsumowań finansowych
     */
    List<ContractFinancialSummaryDto> calculateIssueContractFinancialSummaries(Long issueId, final boolean notPaidOnly, boolean overdueOnly);


    /**
     * Zwraca listę umów dla podanego klienta wraz z blokadami w windykacji nieformalnej
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista umów wraz z blokadami
     */
    List<ContractWithExclusions> findContractsWithExclusionsByCompanyId(final Long extCompanyId);

    /**
     * Zwraca listę umów dla podanego klienta wraz z blokadami w windykacji formalnej
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista umów wraz z blokadami
     */
    List<FormalDebtCollectionContractWithExclusions> findFormalDebtCollectionContractsWithExclusionsByCompanyId(Long extCompanyId);

    /**
     * Sprawdza czy podana umowa jest zablokowana w podanym czasie
     *
     * @param contractNumber          - numer umowy
     * @param inspectionDate          - data
     * @param demandForPaymentTypeKey - typ wezwania do zapłaty
     * @return true jeśli umowa jest zablokowana, false w przeciwnym przypadku
     */
    boolean isFormalDebtCollectionContractOutOfService(String contractNumber, Date inspectionDate, DemandForPaymentTypeKey demandForPaymentTypeKey);
}
