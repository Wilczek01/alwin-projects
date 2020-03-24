package com.codersteam.alwin.core.api.service.activity;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeByStateDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Michal Horowic
 */
@Local
public interface ActivityTypeService {

    /**
     * Pobieranie wszystkich typów czynności windykacyjnych zgrupowanych po statusie (planowane/wykonane)
     *
     * @param mayHaveDeclarations - czy typ czynności może posiadać deklaracje
     * @return lista czynności windykacyjnych zgrupowanych po statusie (planowane/wykonane)
     */
    ActivityTypeByStateDto findAllActivityTypesGroupedByState(final Boolean mayHaveDeclarations);

    /**
     * Pobieranie typów czynności windykacyjnych filtrowanych po typie zlecenia i czy może posiadać deklaracje
     *
     * @param issueTypeName       - typ zlecenia windykacyjnego
     * @param mayHaveDeclarations - czy typ czynności może posiadać deklaracje
     * @return typy czynności windykacyjnych
     */
    List<ActivityTypeDto> findActivityTypes(final IssueTypeName issueTypeName, final Boolean mayHaveDeclarations);

    /**
     * Pobiera typy czynności dla danego typu zlecenia zgrupowane po statusie (planowane/wykonane)
     *
     * @param issueTypeName       - typ zlecenia windykacyjnego
     * @param mayHaveDeclarations - czy typ czynności może posiadać deklaracje
     * @return lista typów czynności zgrupowane po statusie (planowane/wykonane)
     */
    ActivityTypeByStateDto findActivityTypeByIssueTypeGroupedByState(IssueTypeName issueTypeName, Boolean mayHaveDeclarations);

    /**
     * Sprawdza czy czynność o podanym identyfikatorze jest dozwolona dla typu zlecenia o podanym identyfikatorze
     *
     * @param issueTypeId    - identyfikator typu zlecenia
     * @param activityTypeId - identyfikator typu czynności
     * @return true jeżeli czynność o podanym identyfikatorze jest dozwolona dla typu zlecenia o podanym identyfikatorze, false w przeciwnym razie
     */
    boolean checkIfActivityTypeIsAllowedForIssueType(final Long issueTypeId, final Long activityTypeId);
}
