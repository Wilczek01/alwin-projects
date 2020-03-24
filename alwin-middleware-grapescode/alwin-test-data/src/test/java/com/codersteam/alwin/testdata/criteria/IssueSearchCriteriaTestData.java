package com.codersteam.alwin.testdata.criteria;


import com.codersteam.alwin.common.search.criteria.IssueSearchCriteria;
import com.codersteam.alwin.testdata.TestDateUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings("WeakerAccess")
public class IssueSearchCriteriaTestData {

    public static final Date START_DATE = TestDateUtils.parse("2023-07-10 00:00:00.000000");
    public static final Date EXPIRATION_DATE = TestDateUtils.parse("2024-08-10 00:00:00.000000");
    public static final String BALANCE = "554643.67";
    public static final Date ACTIVITY_DATE = TestDateUtils.parse("2017-07-10 00:00:00.000000");
    public static final Date PLANNED_DATE = TestDateUtils.parse("2017-08-03 00:00:00.000000");

    public static IssueSearchCriteria searchCriteria(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);

        return searchCriteria;
    }

    public static IssueSearchCriteria searchCriteriaByBalance(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setStartTotalCurrentBalancePLN(new BigDecimal(BALANCE));
        searchCriteria.setEndTotalCurrentBalancePLN(new BigDecimal(BALANCE));
        return searchCriteria;
    }

    public static IssueSearchCriteria searchCriteriaByStartDate(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setStartStartDate(START_DATE);
        searchCriteria.setEndStartDate(START_DATE);
        return searchCriteria;
    }

    public static IssueSearchCriteria searchCriteriaByExpirationDate(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setStartExpirationDate(EXPIRATION_DATE);
        searchCriteria.setEndExpirationDate(EXPIRATION_DATE);
        return searchCriteria;
    }

    public static IssueSearchCriteria searchCriteriaByActivityDate(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setEndActivityDate(ACTIVITY_DATE);
        return searchCriteria;
    }

    public static IssueSearchCriteria searchCriteriaByPlannedDate(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setStartPlannedDate(PLANNED_DATE);
        searchCriteria.setEndPlannedDate(PLANNED_DATE);
        return searchCriteria;
    }

    public static IssueSearchCriteria searchCriteriaByPlannedDateAndBalance(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setStartPlannedDate(PLANNED_DATE);
        searchCriteria.setStartTotalCurrentBalancePLN(new BigDecimal(BALANCE));
        return searchCriteria;
    }

    public static IssueSearchCriteria searchCriteriaByPlannedDateAndExpirationDate(final int firstResult, final int maxResults) {
        final IssueSearchCriteria searchCriteria = new IssueSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setStartPlannedDate(PLANNED_DATE);
        searchCriteria.setStartExpirationDate(EXPIRATION_DATE);
        return searchCriteria;
    }
}
