package com.codersteam.alwin.core.api.service.termination;

import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria;
import com.codersteam.alwin.common.sort.ContractTerminationSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.termination.ActivateContractTerminationDto;
import com.codersteam.alwin.core.api.model.termination.TerminationContractDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Serwis do zarządzania wypowiedzeniami umów
 *
 * @author Dariusz Rackowski
 */
@Local
public interface ContractTerminationService {

    /**
     * Zwraca stronę z listą wypowiedzeń umów dla podanego statusu.
     * Wypowiedzenia posortowane według daty wypowiedzenia malejąco (najpierw najnowsze) i id klienta rosnąco
     *
     * @param criteria     - kryteria filtrowania
     * @param sortCriteria - kryteria sortowania
     * @return strona z wypowiedzeniami umów
     */
    Page<TerminationDto> findTerminations(final ContractTerminationSearchCriteria criteria, Map<ContractTerminationSortField, SortOrder> sortCriteria);

    /**
     * Zwraca wypowiedzenię umowy dla podanego id
     *
     * @return opcjonalne wypowiedzenie umowy
     */
    Optional<TerminationContractDto> findTermination(long id);

    /**
     * Zwraca listą wszystkich wypowiedzeń umów do których należy wykonać akcję.
     * Wypowiedzenia posortowane według daty wypowiedzenia malejąco (najpierw najnowsze) i id klienta rosnąco
     *
     * @return lista z wypowiedzeniami umów
     */
    List<TerminationDto> findTerminationsToProcess();

    /**
     * Odrzuca przekazane w parametrze wypowiedzenia umów, zapisując status i komentarze
     *
     * @param terminationsToReject lista umów do odrzucenia pogrupowane według klientów
     */
    void rejectContractTerminations(List<TerminationDto> terminationsToReject);

    /**
     * Odracza przekazane w parametrze wypowiedzenia umów, zapisując status i nową datę wypowiedzeń
     *
     * @param terminationsToPostpone lista umów do odroczenia pogrupowane według klientów
     */
    void postponeContractTerminations(List<TerminationDto> terminationsToPostpone);

    /**
     * Oznacza wypowiedzenia umów jako procesowane.
     * Uruchamia proces wysyłania wypowiedzeń do eFaktury.
     *
     * @param terminationsToSend     - wypowiedzenia umów
     * @param loggedOperatorFullName
     */
    void sendContractTerminations(List<TerminationDto> terminationsToSend, final String loggedOperatorFullName);

    /**
     * Znajduje spośród przekazanych identyfikatorów wypowiedzeń umów te, które nie istnieją
     *
     * @param contractTerminationIds - identyfikatory wypowiedzeń umów
     * @return nieistniejące identyfikatory wypowiedzeń umów
     */
    List<Long> findNotExistingContractTerminationIds(Set<Long> contractTerminationIds);

    /**
     * Znajduje spośród przekazanych identyfikatorów wypowiedzeń umów te, które nie mogą być aktualnie przetwarzane
     *
     * @param contractTerminationIds - identyfikatory wypowiedzeń umów
     * @return nieistniejące identyfikatory wypowiedzeń umów
     */
    List<Long> findNotReadyToProcessContractTerminations(Set<Long> contractTerminationIds);

    /**
     * Znajduje numery klientów przypisane do przekazanych identyfikatorów wypowiedzeń
     *
     * @param contractTerminationIds - identyfikatory wypowiedzeń umów
     * @return mapa identyfikatorów wypowiedzeń umów wskazujących na odpowiednie nr klientów
     */
    Map<Long, Long> findContractIdToCompanyIds(Set<Long> contractTerminationIds);

    /**
     * Znajduje typy wypowiedzeń umów przypisane do przekazanych identyfikatorów wypowiedzeń
     *
     * @param contractTerminationIds - identyfikatory wypowiedzeń umów
     * @return mapa identyfikatorów wypowiedzeń umów wskazujących na odpowiednie typy wypowiedzeń
     */
    Map<Long, ContractTerminationType> findContractIdToContractType(Set<Long> contractTerminationIds);

    /**
     * Aktywuje wypowiedzenie umowy ustawiając datę aktywacji, opłatę za wznowienie oraz komentarz
     *
     * @param contractTerminationId          - identyfikator wypowiedzenia umowy
     * @param activateContractTerminationDto - parametry do ustawienia
     */
    void activateContractTermination(long contractTerminationId, ActivateContractTerminationDto activateContractTerminationDto);
}
