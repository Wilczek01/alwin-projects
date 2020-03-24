package com.codersteam.alwin.core.api.service.notice;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;
import com.codersteam.alwin.common.sort.DemandForPaymentSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.model.termination.DemandForPaymentStatusChangeDto;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Serwis do obsługi wezwań do zapłaty
 *
 * @author Michal Horowic
 */
@Local
public interface DemandForPaymentService {

    /**
     * Pobieranie wezwania do zapłaty
     *
     * @param id - identyfikator wezwanie do zapłaty
     * @return wezwanie do zapłaty
     */
    Optional<DemandForPaymentDto> find(Long id);

    /**
     * Pobiera stronę z wezwaniami do zapłaty filtrowanymi po przekazanych kryteriach
     *
     * @param criteria     - kryteria filtrowania
     * @param sortCriteria - kryteria sortowania
     * @return strona z wezwaniami do zapłaty
     */
    Page<DemandForPaymentDto> findAllDemandsForPayment(final DemandForPaymentSearchCriteria criteria, Map<DemandForPaymentSortField, SortOrder> sortCriteria);

    /**
     * Pobiera listę z wezwaniami do zapłaty filtrowanymi po przekazanych kryteriach
     *
     * @param criteria     - kryteria filtrowania
     * @param sortCriteria - kryteria sortowania
     * @return strona z wezwaniami do zapłaty
     */
    List<DemandForPaymentDto> findDemandsForPayment(final DemandForPaymentSearchCriteria criteria,
                                                    Map<DemandForPaymentSortField, SortOrder> sortCriteria);

    /**
     * Odrzuca wybrane sugestie wezwań do zapłaty
     *
     * @param demandsToReject  - sugestie wezwań do zapłaty
     * @param loggedOperatorId - identyfikator zalogowanego operatora
     * @param rejectedReason   - powód odrzucenia
     */
    void rejectDemandsForPayment(List<DemandForPaymentDto> demandsToReject, Long loggedOperatorId, String rejectedReason);

    /**
     * Oznacza jako procesowane wybrane sugestie wezwań do zapłaty
     * Uruchamia proces wysyłania sugestii wezwań do zapłaty do eFaktury
     *
     * @param demandsToSend          - sugestie wezwań do zapłaty
     * @param loggedOperatorFullName - imię i nazwisko zalogowanego operatora
     */
    void processDemandsForPayment(List<DemandForPaymentDto> demandsToSend, final String loggedOperatorFullName);

    /**
     * Znajduje spośród przekazanych identyfikatorów wezwań do zapłaty te, które nie istnieją
     *
     * @param demandsIds - identyfikatory wezwań do zapłaty
     * @return nieistniejące identyfikatory wezwań
     */
    List<Long> findNotExistingDemandsForPayment(List<Long> demandsIds);

    /**
     * Znajduje  spośród przekazanych identyfikatorów wezwań do zapłaty te, które zostały już wysłane
     *
     * @param demandsIds - identyfikatory wezwań do zapłaty
     * @return identyfikatory wysłanych wezwań
     */
    List<Long> findIssuedDemandsForPayment(List<Long> demandsIds);

    /**
     * Ręczne utworzenie wezwań danego typu dla wskazanych umów
     *
     * @param type            - typ wezwania (monit/ostateczne)
     * @param contractNumbers - numery umów do utworzenia wezwań
     * @return komunikaty z wynikami utworzenia wezwań
     */
    List<ManualPrepareDemandForPaymentResult> createDemandsForPaymentManually(DemandForPaymentTypeKey type, List<String> contractNumbers);

    /**
     * Zmienia stan wezwania umowy
     *
     * @param demandForPaymentId              - identyfikator wezwania umowy
     * @param state                           - docelowy stan do ustawienia
     * @param demandForPaymentStatusChangeDto - parametry zmiany stanu wezwania
     */
    void updateDemandForPaymentState(long demandForPaymentId, final DemandForPaymentStatusChangeDto demandForPaymentStatusChangeDto,
                                     DemandForPaymentState state);
}
