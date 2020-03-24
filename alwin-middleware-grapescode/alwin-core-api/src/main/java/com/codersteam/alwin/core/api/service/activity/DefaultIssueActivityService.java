package com.codersteam.alwin.core.api.service.activity;

import com.codersteam.alwin.core.api.model.activity.DefaultIssueActivityDto;
import com.codersteam.alwin.core.api.model.activity.IssueTypeWithDefaultActivities;

import javax.ejb.Local;
import java.util.List;

/**
 * Serwis do pobrania domyślnych czynności windykacyjnych
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface DefaultIssueActivityService {

    /**
     * Pobranie domyślnych czynności dla typu zlecenia
     *
     * @param issueTypeid identyfikaor typu zlecenia
     * @return domyślne czynności windykacyjne dla typu zlecenia
     */
    List<DefaultIssueActivityDto> findDefaultIssueActivities(Long issueTypeid);

    /**
     * Pobranie domyślnych czynności dla wszystkich typów zlecenia
     *
     * @return domyślne czynności windykacyjne dla wszystkich typów zlecenia, zgrupowane według typu zlecenia
     */
    List<IssueTypeWithDefaultActivities> findAllDefaultIssueActivities();


}
