package com.codersteam.alwin.testdata.criteria;

import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;

/**
 * @author Michal Horowic
 */
public class ActivitiesSearchCriteriaTestData {

    public static ActivitiesSearchCriteria activitiesSearchCriteria(final int firstResult, final int maxResults) {
        final ActivitiesSearchCriteria searchCriteria = new ActivitiesSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        return searchCriteria;
    }

    public static ActivitiesSearchCriteria companyActivitiesSearchCriteria(final int firstResult, final int maxResults, final Long companyId) {
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(firstResult, maxResults);
        searchCriteria.setCompanyId(companyId);
        return searchCriteria;
    }

    public static ActivitiesSearchCriteria operatorActivitiesSearchCriteria(final int firstResult, final int maxResults, final Long operatorId) {
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(firstResult, maxResults);
        searchCriteria.setOperatorId(operatorId);
        return searchCriteria;
    }

    public static ActivitiesSearchCriteria activitiesInPeriodSearchCriteria(final int firstResult, final int maxResults, final String startCreationDate,
                                                                            final String endCreationDate) {
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(firstResult, maxResults);
        searchCriteria.setStartCreationDate(parse(startCreationDate));
        searchCriteria.setEndCreationDate(parse(endCreationDate));
        return searchCriteria;
    }

    public static ActivitiesSearchCriteria activitiesWithRemarkSearchCriteria(final int firstResult, final int maxResults, final String remark) {
        final ActivitiesSearchCriteria searchCriteria = activitiesSearchCriteria(firstResult, maxResults);
        searchCriteria.setRemark(remark);
        return searchCriteria;
    }

    public static ActivitiesSearchCriteria activitiesWithStatesSearchCriteria(final String... states) {
        final ActivitiesSearchCriteria activitiesSearchCriteria = activitiesSearchCriteria(0, 5);
        activitiesSearchCriteria.setStates(asList(states));
        return activitiesSearchCriteria;
    }
}
