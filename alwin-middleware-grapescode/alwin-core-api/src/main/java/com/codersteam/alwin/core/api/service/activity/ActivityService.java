package com.codersteam.alwin.core.api.service.activity;

import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;
import com.codersteam.alwin.common.sort.ActivitySortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeWithDetailPropertiesDto;
import com.codersteam.alwin.core.api.model.activity.DefaultIssueActivityDto;
import com.codersteam.alwin.core.api.model.common.Page;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Michal Horowic
 */
@Local
public interface ActivityService {

    /**
     * Tworzy nową czynność dla zlecenia
     *
     * @param activity - czynność do utworzenia
     */
    void createNewActivityForIssue(final ActivityDto activity);

    /**
     * Utworzenie domyślnych czynności windykacyjnych dla zlecenia
     *
     * @param issueId     - identyfikator zlecenia
     * @param issueTypeId - identyfikator typu zlecenia
     */
    void createDefaultActivitiesForIssue(final Long issueId, final Long issueTypeId);

    /**
     * Utworzenie domyślnych czynności windykacyjnych dla zlecenia wraz z przypisaniem do operatora
     *
     * @param issueId     - identyfikator zlecenia
     * @param issueTypeId - identyfikator typu zlecenia
     * @param assigneeId  - identyfikator operatora, do którego należy przypisać czynność
     */
    void createAndAssignDefaultActivitiesForIssue(final Long issueId, final Long issueTypeId, Long assigneeId);

    /**
     * Pobiera wszystkie czynności windykacyjne dla danego zlecenia.
     * Czynności posortowane są po dacie zaplanowania.
     *
     * @param issueId - identyfikator zlecenia
     * @return lista czynności windykacyjnych
     */
    List<ActivityDto> findAllIssueActivities(final Long issueId);

    /**
     * Pobieranie anktywnych czynności z datą zaplanowania mniejszą lub równą od podanej.
     * Czynności posortowane są po dacie zaplanowania.
     *
     * @param issueId        - identyfikator zlecenia
     * @param maxPlannedDate - maksymalna data czynności zaplanowanej
     * @return lista czynności windykacyjnych
     */
    List<ActivityDto> findActiveIssueActivities(final Long issueId, final Date maxPlannedDate);

    /**
     * Pobiera wszystkie czynności windykacyjne dla danego zlecenia.
     * Czynności posortowane są po dacie zaplanowania.
     *
     * @param issueId - identyfikator zlecenia
     * @return lista czynności windykacyjnych
     */
    Page<ActivityDto> findAllIssueActivities(final Long issueId, final ActivitiesSearchCriteria searchCriteria, Map<ActivitySortField, SortOrder> sortCriteria);

    /**
     * Zamknięcie czynności windykacyjnych dla zlecenia
     *
     * @param issueId identyfikator zlecenia
     */
    void closeIssueActivities(final Long issueId);


    /**
     * Aktualizuje czynność windykacyjną
     *
     * @param activity - czynność do utworzenia
     * @param managed  - czy zmiana wykonywana przez managera
     */
    void updateActivity(final ActivityDto activity, final boolean managed);

    /**
     * Pobiera czynność windykacyjną po id
     *
     * @param activityId - identyfikator czynności windykayjnej
     */
    Optional<ActivityDto> findActivityById(final Long activityId);

    /**
     * Tworzy czynność windykacyjną typu 'sms wychodzący'
     *
     * @param issueId     - identyfikator zlecenia
     * @param operatorId  - identyfikator operatora
     * @param phoneNumber - numer telefon na który wysyłany jest sms
     * @param smsMessage  - treść wiadomości sms
     * @return identyfikator utworzonej czynności
     */
    Long createOutgoingSmsActivity(final Long issueId, final Long operatorId, final String phoneNumber, final String smsMessage);

    /**
     * Aktualizuje domyślną czynność zlecenia windykacyjnego
     *
     * @param defaultIssueActivityId  - identyfikator domyślnej czynności
     * @param defaultIssueActivityDto - domyślna czynność zlecenia windykacyjnego
     */
    void updateDefaultIssueActivity(long defaultIssueActivityId, DefaultIssueActivityDto defaultIssueActivityDto);

    /**
     * Aktualizuje typ czynności zlecenia windykacyjnego
     *
     * @param activityTypeId  - identyfikator typu czynności
     * @param activityTypeDto - typ czynności zlecenia windykacyjnego
     */
    void updateActivityType(long activityTypeId, ActivityTypeWithDetailPropertiesDto activityTypeDto);

    /**
     * Tworzy czynność windykacyjną typu 'komentarz'
     *
     * @param issueId    - identyfikator zlecenia
     * @param operatorId - identyfikator operatora
     * @param comment    - treść komentarza
     */
    void createCommentActivity(final Long issueId, final Long operatorId, final String comment);

    /**
     * Pobieranie najstarszej zaplanowanej czynności 'telefon wychodzący' dla zlecenia
     * Data zaplanowanej czynności jest mniejsza lub równa od dzisiejszej daty
     *
     * @param issueId - identyfikator zlecenia
     * @return najstarsza zaplanowana czynność typu 'telefon wychodzący' jeżeli istnieje
     */
    Optional<ActivityDto> findOldestPlannedActivityForIssue(final Long issueId);
}
