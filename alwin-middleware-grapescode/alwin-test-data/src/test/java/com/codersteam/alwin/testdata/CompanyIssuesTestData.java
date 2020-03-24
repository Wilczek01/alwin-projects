package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.issue.Issue;

import java.util.List;

import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue19;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue27;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * @author Michal Horowic
 */
public class CompanyIssuesTestData {

    public static List<Issue> expectedCompanyIssuesExcludingFirst() {
        return asList(issue27(), issue19());
    }

    public static List<Issue> expectedIssuesForCompanyExcludingGivenIssueWithIssueType() {
        return singletonList(issue19());
    }

    public static List<Issue> expectedIssuesForCompanyExcludingGivenIssueWithActivityType() {
        return singletonList(issue27());
    }

    public static List<Issue> expectedIssuesForCompanyExcludingGivenIssueWithActivityDate() {
        return singletonList(issue27());
    }

    public static List<Issue> expectedIssuesForCompanyExcludingGivenIssueWithRestrictiveMaxResults() {
        return singletonList(issue27());
    }
}
