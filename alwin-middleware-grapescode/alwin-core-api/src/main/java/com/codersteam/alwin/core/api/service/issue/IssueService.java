package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.common.search.criteria.IssueForCompanySearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssueSearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.CompanyIssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueForFieldDebtCollectorDto;
import com.codersteam.alwin.core.api.model.issue.OperatedIssueDto;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;

import javax.ejb.Local;
import java.util.*;

/**
 * Serwis do obsługi zleceń
 *
 * @author Michal Horowic
 */
@Local
public interface IssueService {

    /**
     * Pobiera stronę zleceń obsługiwanych przez danego operatora.
     * Pobiera zlecenia, w których operator ma choć jedną zamkniętą i przypisaną do siebie czynność windykacyjną.
     * Sortowane po identyfikatorze - z założenia im starsze tym wyższa wartość w sekwencji.
     *
     * @param assigneeId     - identyfikator użytkownika
     * @param searchCriteria - kryteria wyszukiwania
     * @return strona z listą zleceń
     */
    Page<IssueDto> findOperatorIssues(Long assigneeId, IssueSearchCriteria searchCriteria);

    /**
     * Pobiera stronę zleceń z obszaru danego operatora
     *
     * @param operatorType - typ operatora dla którego obszar bierzemy pod uwagę
     * @param criteria     - kryteria wyszukiwania
     * @param sortCriteria - kryteria sortowania
     * @return strona ze zleceniami
     */
    Page<IssueDto> findAllIssues(OperatorType operatorType, IssuesSearchCriteria criteria, Map<IssueSortField, SortOrder> sortCriteria);

    /**
     * Pobiera stronę zleceń z obszaru danego windykatora terenowego
     *
     * @param operatorType - typ operatora dla którego obszar bierzemy pod uwagę
     * @param criteria     - kryteria wyszukiwania
     * @param sortCriteria - kryteria sortowania
     * @return strona ze zleceniami
     */
    Page<IssueForFieldDebtCollectorDto> findAllFieldDebtIssues(final Long operatorId, final OperatorType operatorType,
                                                               final IssuesSearchCriteria criteria,
                                                               Map<IssueSortField, SortOrder> sortCriteria);

    /**
     * Pobranie zlecenia po identyfikatorze
     *
     * @param issueId identyfikator zlecenia
     * @return zlecenie lub null w przypadku braku zlecenia w bazie
     */
    IssueDto findIssue(Long issueId);
    Optional<IssueDto> findIssueToWorkOn(final Long operatorId);
    Optional<IssueDto> getRecalculatedIssueIfNotClosed(final Long issueId);

    /**
     * Pobranie zlecenia po identyfikatorze wraz z aktualizacją jego salda oraz uzupełnioną flagą, czy może je edytować zalogowany operator
     *
     * @param issueId      - identyfikator zlecenia
     * @param operatorType - typ zalogowanego operatora
     * @return zlecenie ze zaktualizowanym saldem
     */
    OperatedIssueDto findOperatedIssueAndUpdateBalance(Long issueId, OperatorType operatorType);

    /**
     * Sprawdzenie czy dla firmy istnieje aktywne zlecenie
     *
     * @param extCompanyId identyfikator firmy (AIDA)
     * @return <code>true</code> jeśli istnieje aktywne zlecenie
     */
    boolean doesCompanyHaveActiveIssue(Long extCompanyId);

    /**
     * Pobranie identyfikatora aktywnego zlecenia (jeśli istnieje) po identyfikatorze firmy
     *
     * @param extCompanyId - identyfikator firmy (AIDA)
     * @return identyfikator aktywnego zlecenia (jeśli istnieje)
     */
    Optional<Long> findCompanyActiveIssueId(Long extCompanyId);

    /**
     * Pobranie wszystkich aktywnych zleceń
     *
     * @return Lista aktywnych zleceń
     */
    List<IssueDto> findAllActiveIssues();

    /**
     * Pobiera liste wszystkich zleceń dla danego klienta z pominięciem zlecenia o podanym identyfikatorze
     *
     * @param companyId       - identyfikator klienta
     * @param excludedIssueId - identyfikator zlecenia do pominięcia
     * @param searchCriteria  - kryteria wyszukiwania
     * @return strona z pobraną lista zleceń i ilością wszystich możliwych zleceń do pobrania dla danego klienta
     */
    Page<CompanyIssueDto> findAllIssuesForCompanyExcludingGivenIssue(Long companyId, Long excludedIssueId, IssueForCompanySearchCriteria searchCriteria);

    /**
     * Pobieranie filtrowanych aktywnych zleceń z obszaru podanego operatora
     *
     * @param operatorType - typ operatora dla którego obszar bierzemy pod uwagę
     * @param criteria     - kryteria wyszukiwania
     * @param sortCriteria - kryteria sortowania
     * @return strona ze zleceniami
     */
    Page<IssueDto> findAllFilteredManagedIssues(OperatorType operatorType, IssuesSearchCriteria criteria, Map<IssueSortField, SortOrder> sortCriteria);

    /**
     * Znajduje w podanej liście identyfikatorów zleceń te, które nie mogą być zarządzane przez operatora o przekazanym typie
     *
     * @param operatorType - typ operatora zarządzającego zleceniami
     * @param issueIds     - lista identyfikatorów zleceń
     * @return lista identyfikatorów zleceń
     */
    List<Long> findNotManagedIssues(OperatorType operatorType, List<Long> issueIds);

    /**
     * Przypisuje zgłoszenia o podanych identyfikatorach do operatora o podanym identyfikatorze
     *
     * @param operatorId - identyfikator operatora do przypisania
     * @param issueIds   - lista zleceń do przypisania
     */
    void updateIssuesAssignment(Long operatorId, List<Long> issueIds);

    /**
     * Przypisuje wszystkie zlecenia spełniające kryteria wyszukiwania do operatora o podanym identyfikatorze
     *
     * @param operatorId   - identyfikator operatora do przypisania
     * @param operatorType - typ operatora zarządzającego zleceniami
     * @param criteria     - kryteria wyszukiwania zleceń do przypisania
     */
    void updateIssuesAssignment(Long operatorId, OperatorType operatorType, IssuesSearchCriteria criteria);

    /**
     * Przypisuje wszystkim zleceniom spełniającym kryteria wyszukiwania podany priorytet
     *
     * @param priority - priorytet zadania do ustawienia
     * @param criteria - kryteria wyszukiwania zleceń do ustawienia
     */
    void updateIssuesPriority(Integer priority, OperatorType operatorType, IssuesSearchCriteria criteria);

    /**
     * Przypisuje zleceniom o podanych identyfikatorach podany priorytet
     *
     * @param priority - priorytet zadania do ustawienia
     * @param issueIds - lista zleceń do aktualizacji
     */
    void updateIssuesPriority(Integer priority, List<Long> issueIds);

    /**
     * Przypisuje do odfiltrowanych zleceń etykiety o podanych identyfikatorach
     *
     * @param tagIds       - identyfikatory etykiet do przypisania
     * @param operatorType - typ operatora, któremu podlegają zlecenia
     * @param criteria     - kryteria filtrowania zleceń
     */
    void updateIssuesTags(Set<Long> tagIds, OperatorType operatorType, IssuesSearchCriteria criteria);

    /**
     * Przypisuje do podanych zleceń etykiety o podanych identyfikatorach
     *
     * @param tagIds   - identyfikatory etykiet do przypisania
     * @param issueIds - identyfikatory zleceń do których przypisane zostaną etykiety
     */
    void updateIssuesTags(Set<Long> tagIds, List<Long> issueIds);

    /**
     * Zakończenie przedterminowe zlecenia
     *
     * @param issueId           - identyfikator zlecenia
     * @param operatorId        - identyfikator operatora
     * @param terminationCause  - przyczyna przerwania
     * @param excludedFromStats - czy wyłączyć ze statystyk
     * @param exclusionCause    - przyczyna wyłączenia ze statystyk
     * @return status: zlecenie zakończone lub żądanie o przedterminowe zakończenie zlecenia zostało utowrzone
     */
    TerminateIssueResult terminateIssue(Long issueId, Long operatorId, String terminationCause, boolean excludedFromStats, String exclusionCause);

    /**
     * Wykluczanie i przywracanie faktury w obsłudze zlecenia
     *
     * @param issueId   - identyfikator zlecenia
     * @param invoiceId - identyfikator faktury
     * @param excluded  - czy wykluczyć fakturę z obsługi zlecenia
     */
    void updateIssueInvoicesExclusion(Long issueId, Long invoiceId, boolean excluded);

    /**
     * Przypisuje do zlecenia o podanym identyfikatorze listę etykiet o podanych identyfikatorach.
     * Etykieta jest ignorowana jeżeli była już przypisana do zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @param tags    - identyfikatory etykiet
     */
    void assignTags(long issueId, List<Long> tags);

    /**
     * Zamykanie zlecenia i tworzenie nowych zleceń na podstawie zamykanych, jeżeli spełniają dodatkowe warunki.
     */
    void closeIssueWithActivitiesAndCreateChildIssueIfNeeded(final Long issueId);

    /**
     * Pobiera listę identyfikatorów zleceń z niespłaconymi deklaracjami
     *
     * @param declaredPaymentDateStart - początek przedziału czasowego, w którym zadeklarowano spłatę
     * @param declaredPaymentDateEnd   - koniec przedziału czasowego, w którym zadeklarowano spłatę
     * @return lista identyfikatorów zleceń
     */
    List<Long> findIssueIdsWithUnpaidDeclarations(final Date declaredPaymentDateStart, final Date declaredPaymentDateEnd);

    /**
     * Pobieranie typów operatorów mających uprawnienia do obsługi zleceń o podanych identyfikatorach
     *
     * @param issuesIds - identyfikatory zleceń
     * @return lista typów operatorów
     */
    Set<OperatorTypeDto> findIssuesOperatorTypes(List<Long> issuesIds);

    /**
     * Pobieranie typów operatorów mających uprawnienia do obsługi zleceń o podanych identyfikatorach
     *
     * @param operatorType - typ zalogowanego operatora
     * @param criteria     - kryteria wyszukiwania zleceń do ustawienia
     * @return lista typów operatorów
     */
    Set<OperatorTypeDto> findIssuesOperatorTypes(OperatorType operatorType, IssuesSearchCriteria criteria);
}
