package com.codersteam.alwin.testdata.criteria;

import com.codersteam.alwin.common.search.criteria.IssueForCompanySearchCriteria;
import com.codersteam.alwin.testdata.TestDateUtils;

import java.util.Date;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings("WeakerAccess")
public class IssueForCompanySearchCriteriaTestData {

    public static final Long ISSUE_TYPE_ID = 1L;
    public static final Long ACTIVITY_TYPE_ID = 2L;
    public static final Date ACTIVITY_DATE_FROM = TestDateUtils.parse("2018-07-09 00:00:00.000000");
    public static final Date ACTIVITY_DATE_TO = TestDateUtils.parse("2018-07-11 00:00:00.000000");

    public static IssueForCompanySearchCriteria issueForCompanySearchCriteria(final int firstResult, final int maxResults) {
        final IssueForCompanySearchCriteria searchCriteria = new IssueForCompanySearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        return searchCriteria;
    }

    public static IssueForCompanySearchCriteria issueForCompanySearchCriteriaByIssueType(final int firstResult, final int maxResults) {
        final IssueForCompanySearchCriteria searchCriteria = new IssueForCompanySearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setIssueTypeId(ISSUE_TYPE_ID);
        return searchCriteria;
    }

    public static IssueForCompanySearchCriteria issueForCompanySearchCriteriaByActivityType(final int firstResult, final int maxResults) {
        final IssueForCompanySearchCriteria searchCriteria = new IssueForCompanySearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setActivityTypeId(ACTIVITY_TYPE_ID);
        return searchCriteria;
    }

    public static IssueForCompanySearchCriteria issueForCompanySearchCriteriaByActivityDate(final int firstResult, final int maxResults) {
        final IssueForCompanySearchCriteria searchCriteria = new IssueForCompanySearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        searchCriteria.setActivityDateFrom(ACTIVITY_DATE_FROM);
        searchCriteria.setActivityDateTo(ACTIVITY_DATE_TO);
        return searchCriteria;
    }
}
