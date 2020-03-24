package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.CompanyIssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.type.IssueState;

import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_3;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_4;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue3;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue4;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto3;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto4;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_10;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_21;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_5;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_6;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_7;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_8;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_9;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue6;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issueDto6;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_28;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_29;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_31;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.companyIssueDto19;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.companyIssueDto20;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue19;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue20;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issueDto19;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issueDto20;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_11;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_12;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_13;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_14;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.ISSUE_ID_15;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.ISSUE_ID_16;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.ISSUE_ID_17;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.ISSUE_ID_18;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto2;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class IssueTestData {

    public static final Long NOT_EXISTING_ISSUE_ID = -1L;
    public static final String TERMINATION_CAUSE = "powód zamknięcia zlecenia";
    public static final String EXCLUSION_CAUSE = "powód wykluczenia zlecenia";
    public static final boolean EXCLUDE_FROM_STATS = true;

    public static final int TOTAL_VALUES = 32;
    public static final int TOTAL_ASSIGNED_NOT_CLOSED_VALUES = 2;
    public static final int TOTAL_ISSUES_FOR_COMPANY_EXCLUDING_GIVEN_ISSUE_VALUES = 2;
    public static final List<Long> ACTIVE_ISSUE_IDS = asList(ISSUE_ID_1, ISSUE_ID_2, ISSUE_ID_3, ISSUE_ID_4, ISSUE_ID_5, ISSUE_ID_6, ISSUE_ID_7, ISSUE_ID_8,
            ISSUE_ID_9, ISSUE_ID_10, ISSUE_ID_11, ISSUE_ID_12, ISSUE_ID_13, ISSUE_ID_14, ISSUE_ID_15, ISSUE_ID_16, ISSUE_ID_17, ISSUE_ID_18, ISSUE_ID_21,
            ISSUE_ID_28, ISSUE_ID_29, ISSUE_ID_31);
    public static final List<Long> ACTIVE_PHONE_DEBT_COLLECTION_ISSUE_IDS = asList(ISSUE_ID_1, ISSUE_ID_2, ISSUE_ID_3, ISSUE_ID_4, ISSUE_ID_5, ISSUE_ID_6,
            ISSUE_ID_7, ISSUE_ID_8, ISSUE_ID_9, ISSUE_ID_10, ISSUE_ID_11, ISSUE_ID_12, ISSUE_ID_13, ISSUE_ID_14, ISSUE_ID_15, ISSUE_ID_16, ISSUE_ID_17,
            ISSUE_ID_18, ISSUE_ID_21, ISSUE_ID_28, ISSUE_ID_29);
    public static final List<Issue> TEST_ACTIVE_ISSUES = asList(issue1(), issue2(), issue3(), issue1(), issue6());
    public static final List<IssueDto> TEST_ACTIVE_ISSUES_DTOS = asList(issueDto1(), issueDto2(), issueDto3(), issueDto1(), issueDto6());

    public static List<Issue> expectedAssignedNotClosedFirstPage() {
        return asList(issue19(), issue20());
    }

    public static List<Issue> expectedAssignedNotClosedFirstPageWithSearchCriteriaByBalance() {
        return asList(issue20());
    }

    public static List<Issue> expectedAssignedNotClosedFirstPageWithSearchCriteriaByPlannedDate() {
        return asList(issue19());
    }

    public static List<Issue> expectedAssignedNotClosedFirstPageWithSearchCriteriaByPlannedDateAndExpirationDate() {
        return asList(issue19());
    }

    public static List<Issue> expectedAssignedNotClosedFirstPageWithSearchCriteriaByExpirationDate() {
        return asList(issue19(), issue20());
    }

    public static List<Issue> expectedAssignedNotClosedFirstPageWithSearchCriteriaByStartDate() {
        return asList(issue19(), issue20());
    }

    public static List<Issue> expectedAssignedNotClosedSecondPage() {
        return emptyList();
    }

    public static List<Issue> expectedFirstPage() {
        return asList(issue3(), issue2(), issue4());
    }

    public static Page<IssueDto> expectedFirstPageDto() {
        return new Page<>(asList(issueDto3(), issueDto2(), issueDto4()), TOTAL_VALUES);
    }

    public static Page<IssueDto> expectedAssignedNotClosedFirstPageDto() {
        return new Page<>(asList(issueDto19(), issueDto20()), TOTAL_ASSIGNED_NOT_CLOSED_VALUES);
    }

    public static Page<IssueDto> expectedEmptyPage() {
        return new Page<>(emptyList(), 0);
    }

    public static List<Issue> expectedIssuesForCompanyFirstPage() {
        return asList(issue19(), issue20());
    }

    public static Page<CompanyIssueDto> expectedIssuesForCompanyFirstPageDto() {
        return new Page<>(asList(companyIssueDto19(), companyIssueDto20()), TOTAL_ASSIGNED_NOT_CLOSED_VALUES);
    }

    public static Issue issueWithState(final IssueState state) {
        final Issue issue = issue3();
        issue.setIssueState(state);
        return issue;
    }

    public static Issue terminatedIssue(final Date date) {
        final Issue issue = issue1();
        issue.setTerminationCause(TERMINATION_CAUSE);
        issue.setExcludedFromStats(EXCLUDE_FROM_STATS);
        issue.setExclusionCause(EXCLUSION_CAUSE);
        issue.setTerminationOperator(testOperator2());
        issue.setTerminationDate(date);
        issue.setIssueState(IssueState.CANCELED);
        return issue;
    }

    public static IssueDto terminatedIssueDto(final Date date) {
        final IssueDto issue = issueDto1();
        issue.setTerminationCause(TERMINATION_CAUSE);
        issue.setExcludedFromStats(EXCLUDE_FROM_STATS);
        issue.setExclusionCause(EXCLUSION_CAUSE);
        issue.setTerminationOperator(testOperatorUserDto2());
        issue.setTerminationDate(date);
        issue.setIssueState(IssueStateDto.CANCELED);
        return issue;
    }

    public static Issue waitingForTerminationIssue() {
        final Issue issue = issue1();
        issue.setIssueState(IssueState.WAITING_FOR_TERMINATION);
        return issue;
    }

    public static IssueDto waitingForTerminationIssueDto() {
        final IssueDto issue = issueDto1();
        issue.setIssueState(IssueStateDto.WAITING_FOR_TERMINATION);
        return issue;
    }
}
