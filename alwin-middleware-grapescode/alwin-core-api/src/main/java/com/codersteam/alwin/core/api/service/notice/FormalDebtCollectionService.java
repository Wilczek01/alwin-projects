package com.codersteam.alwin.core.api.service.notice;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Serwis do obsługi ścieżki wezwań formalnych
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface FormalDebtCollectionService {

    /**
     * Metoda zwracająca informację, czy dla danej umowy należy utworzyć drugie wezwanie do zapłaty (ostateczne)
     *
     * @param contractNo - numer umowy
     * @return <code>true</code> jeśli należy w momencie sprawdzania utworzyć wezwanie ostateczne
     */
    boolean shouldGenerateSecondDemandForPaymentForContract(String contractNo);

    /**
     * Metoda zwracająca informację, czy dana umowa jest w aktywnym procesie windykacji formalnej
     *
     * @param contractNo - numer umowy
     * @return <code>true</code> jeśli umowa jest już w aktywnym procesie windykacji formalnej
     */
    boolean isContractUnderActiveFormalDebtCollection(String contractNo);

    /**
     * Zebranie wstępnych informacji o umowach dla których należy przygotować wypowiedzenia warunkowe
     *
     * @return lista wstępnych informacji do przygotowania danych wypowiedzeń warunkowych
     */
    List<ContractTerminationInitialData> findConditionalContractTerminationsToCreate();

    /**
     * Zebranie wstępnych informacji o umowach dla których należy przygotować wypowiedzenia natychmiastowe
     *
     * @return lista wstępnych informacji do przygotowania danych wypowiedzeń natychmiastowych
     */
    List<ContractTerminationInitialData> findImmediateContractTerminationsToCreate();

    /**
     * Pobranie identyfikatora poprzedzającego wezwania dla wezwania ostatecznego
     *
     * @param contractNo - numer umowy
     * @return identyfikator wezwania poprzedzającego
     */
    Long findPrecedingDemandForPaymentId(String contractNo);

    /**
     * Pobranie numeru faktury inicjującej z aktywnego procesu windykacji formalnej
     *
     * @param contractNo - numer umowy
     * @return numer faktury inicjującej jeśli aktywny proces dla umowy istnieje
     */
    String findActiveProcessInitialInvoiceNumberByContractNumber(String contractNo);

    /**
     * Pobranie daty wystawionego wezwania do zapłaty danego typu
     *
     * @param demandForPaymentTypeKey - klucz typu wezwania
     * @param contractNumber          - numer umowy
     * @return data wystawienia wezwania jeśli zostało wysłane
     */
    Date findIssuedDemandForPaymentIssueDate(DemandForPaymentTypeKey demandForPaymentTypeKey, String contractNumber);

    /**
     * Pobranie daty wystawionego wezwania do zapłaty następującego typu
     *
     * @param demandForPaymentTypeKey - klucz typu wezwania dla sprawdzanego wezwania do zapłaty
     * @param contractNumber          - numer umowy
     * @return data wystawienia wezwania wyższego typu niż wezwania sprawdzanego jeśli zostało wysłane
     */
    Date findIssuedSucceedingDemandForPaymentIssueDate(DemandForPaymentTypeKey demandForPaymentTypeKey, String contractNumber);

    /**
     * Sprawdzenie czy ostatnie wystawione wezwanie ma przekroczoną datę płatności
     *
     * @param contractNumber - numer umowy
     * @return <code>true</code> jeśli termin zapłaty wezwania minął, null jeśli dla umowy nie ma wystawionego wezwania
     */
    Boolean isLatestDemandForPaymentOverdue(String contractNumber);

    /**
     * Sprawdzenie czy umowa jest na aktywnym etapie wypowiedzeń
     *
     * @param contractNo - numer umowy
     * @return <code>true</code> jeśli umowa jest już na aktywnym etapie wypowiedzeń
     */
    boolean isContractTerminationPending(String contractNo);

    /**
     * Pobranie wezwań danego typu z wyłączeniem wskazanego
     * Używane do wyznaczenia wezwań zastępowanych w procesie ręcznym
     *
     * @param typeKey          - typ wezwania
     * @param contractNumber   - numer umowy
     * @param initialInvoiceNo - numer faktury inicjującej
     * @param excludedDemandId - identyfikator wezwania do pominięcia (wezwanie zastępujące)
     * @return wezwania do zapłaty
     */
    List<DemandForPaymentDto> findDemandsForPaymentExcludingGiven(DemandForPaymentTypeKey typeKey, String contractNumber, String initialInvoiceNo,
                                                                  Long excludedDemandId);

    /**
     * Pobranie wezwania danego typu
     * Używane do wyznaczenia wezwań wyższego typu do zamknięcia procesu (wezwania ręczne)
     *
     * @param typeKey          - typ wezwania
     * @param contractNumber   - numer umowy
     * @param initialInvoiceNo - numer faktury inicjującej
     * @return wezwania do zapłaty
     */
    List<DemandForPaymentDto> findDemandsForPayment(DemandForPaymentTypeKey typeKey, String contractNumber, String initialInvoiceNo);

    /**
     * Wyłączenie danego wezwania z procesu
     *
     * @param demandForPaymentId - identyfikator wezwania do wyłączenia z procesu
     */
    void markDemandForPaymentAborted(Long demandForPaymentId);
}
