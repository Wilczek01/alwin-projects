package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestDto;
import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestStateDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueTerminationRequest;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.IssueTerminationRequestState;

import java.util.Date;

import static com.codersteam.alwin.jpa.type.IssueState.CANCELED;
import static com.codersteam.alwin.jpa.type.IssueState.IN_PROGRESS;
import static com.codersteam.alwin.jpa.type.IssueTerminationRequestState.ACCEPTED;
import static com.codersteam.alwin.jpa.type.IssueTerminationRequestState.NEW;
import static com.codersteam.alwin.jpa.type.IssueTerminationRequestState.REJECTED;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_19;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issue19;
import static com.codersteam.alwin.testdata.IssueTestData.EXCLUDE_FROM_STATS;
import static com.codersteam.alwin.testdata.IssueTestData.EXCLUSION_CAUSE;
import static com.codersteam.alwin.testdata.IssueTestData.TERMINATION_CAUSE;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator3;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator4;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto3;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto4;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;

public class IssueTerminationRequestTestData {

    public static final long NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID = -1L;
    public static final long ISSUE_TERMINATION_REQUEST_ID_1 = 1L;
    public static final long ISSUE_TERMINATION_REQUEST_ID_2 = 2L;
    public static final long ISSUE_TERMINATION_REQUEST_ID_100 = 100L;
    public static final String REQUEST_CAUSE_1 = "przyczyna przerwania 1";
    public static final boolean EXCLUDE_FROM_STATS_1 = true;
    public static final String EXCLUDE_FROM_STATS_CAUSE_1 = "przyczyna wyłączenia ze statystyk 1";
    public static final Date CREATED_1 = parse("2016-01-01 01:01:01.000000");
    public static final Date UPDATED_1 = parse("2016-01-01 01:01:01.000000");
    public static final IssueTerminationRequestState STATE_1 = NEW;
    public static final IssueTerminationRequestStateDto STATE_DTO_1 = IssueTerminationRequestStateDto.NEW;

    public static final String REQUEST_CAUSE_2 = "przyczyna przerwania 2";
    public static final boolean EXCLUDE_FROM_STATS_2 = true;
    public static final String EXCLUDE_FROM_STATS_CAUSE_2 = "przyczyna wyłączenia ze statystyk 2";
    public static final Date CREATED_2 = parse("2016-02-02 02:02:02.000000");
    public static final Date UPDATED_2 = parse("2016-02-12 12:12:12.000000");
    public static final String COMMENT_2 = "zgadzdam się, ma to sens";
    public static final IssueTerminationRequestState STATE_2 = ACCEPTED;
    public static final IssueTerminationRequestStateDto STATE_DTO_2 = IssueTerminationRequestStateDto.ACCEPTED;

    public static final Date ACCEPT_DATE = parse("2017-03-04 05:06:07.000000");
    public static final String ACCEPT_COMMENT = "akceptuję";

    public static final Date REJECT_DATE = parse("2018-03-04 05:06:07.000000");
    public static final String REJECT_COMMENT = "odrzucam";

    public static IssueTerminationRequest issueTerminationRequest1() {
        return issueTerminationRequest(ISSUE_TERMINATION_REQUEST_ID_1, REQUEST_CAUSE_1, testOperator2(), EXCLUDE_FROM_STATS_1, EXCLUDE_FROM_STATS_CAUSE_1,
                STATE_1, null, null, CREATED_1, UPDATED_1, issue1());
    }

    public static IssueTerminationRequestDto issueTerminationRequestDto1() {
        return issueTerminationRequestDto(ISSUE_TERMINATION_REQUEST_ID_1, REQUEST_CAUSE_1, testOperatorUserDto2(), EXCLUDE_FROM_STATS_1,
                EXCLUDE_FROM_STATS_CAUSE_1, STATE_DTO_1, null, null, CREATED_1, UPDATED_1, ISSUE_ID_1);
    }

    public static IssueTerminationRequest issueTerminationRequest2() {
        return issueTerminationRequest(ISSUE_TERMINATION_REQUEST_ID_2, REQUEST_CAUSE_2, testOperator3(), EXCLUDE_FROM_STATS_2, EXCLUDE_FROM_STATS_CAUSE_2,
                STATE_2, testOperator4(), COMMENT_2, CREATED_2, UPDATED_2, issue19());
    }

    public static IssueTerminationRequestDto expectedIssueTerminationRequestDto(final Date date) {
        return issueTerminationRequestDto(ISSUE_TERMINATION_REQUEST_ID_100, TERMINATION_CAUSE, testOperatorUserDto3(), EXCLUDE_FROM_STATS,
                EXCLUSION_CAUSE, STATE_DTO_1, null, null, date, date, ISSUE_ID_1);
    }

    public static IssueTerminationRequestDto issueTerminationRequestDto2() {
        return issueTerminationRequestDto(ISSUE_TERMINATION_REQUEST_ID_2, REQUEST_CAUSE_2, testOperatorUserDto3(), EXCLUDE_FROM_STATS_2,
                EXCLUDE_FROM_STATS_CAUSE_2, STATE_DTO_2, testOperatorUserDto4(), COMMENT_2, CREATED_2, UPDATED_2, ISSUE_ID_19);
    }

    public static IssueTerminationRequest expectedIssueTerminationRequest(final Date date) {
        return issueTerminationRequest(null, TERMINATION_CAUSE, testOperator3(), EXCLUDE_FROM_STATS, EXCLUSION_CAUSE, NEW, null, null,
                date, date, issue1());
    }

    public static IssueTerminationRequest expectedAcceptedTerminationRequest() {
        final Issue issue = issue1();
        issue.setExcludedFromStats(EXCLUDE_FROM_STATS_1);
        issue.setExclusionCause(EXCLUDE_FROM_STATS_CAUSE_1);
        issue.setTerminationCause(REQUEST_CAUSE_1);
        issue.setTerminationOperator(testOperator3());
        issue.setTerminationDate(ACCEPT_DATE);
        issue.setIssueState(CANCELED);
        return issueTerminationRequest(ISSUE_TERMINATION_REQUEST_ID_1, REQUEST_CAUSE_1, testOperator2(), EXCLUDE_FROM_STATS_1, EXCLUDE_FROM_STATS_CAUSE_1,
                ACCEPTED, testOperator3(), ACCEPT_COMMENT, CREATED_1, ACCEPT_DATE, issue);
    }

    public static IssueTerminationRequest expectedRejectedTerminationRequest() {
        final Issue issue = issue1();
        issue.setIssueState(IN_PROGRESS);
        return issueTerminationRequest(ISSUE_TERMINATION_REQUEST_ID_1, REQUEST_CAUSE_1, testOperator2(), EXCLUDE_FROM_STATS_1, EXCLUDE_FROM_STATS_CAUSE_1,
                REJECTED, testOperator3(), REJECT_COMMENT, CREATED_1, REJECT_DATE, issue);
    }

    public static IssueTerminationRequestDto acceptedTerminationRequestDto() {
        final IssueTerminationRequestDto issueTerminationRequestDto = new IssueTerminationRequestDto();
        issueTerminationRequestDto.setRequestCause(REQUEST_CAUSE_1);
        issueTerminationRequestDto.setExcludedFromStats(EXCLUDE_FROM_STATS_1);
        issueTerminationRequestDto.setExclusionFromStatsCause(EXCLUDE_FROM_STATS_CAUSE_1);
        issueTerminationRequestDto.setComment(ACCEPT_COMMENT);
        return issueTerminationRequestDto;
    }

    public static IssueTerminationRequestDto rejectedTerminationRequestDto() {
        final IssueTerminationRequestDto issueTerminationRequestDto = new IssueTerminationRequestDto();
        issueTerminationRequestDto.setComment(REJECT_COMMENT);
        return issueTerminationRequestDto;
    }

    private static IssueTerminationRequest issueTerminationRequest(final Long id, final String requestCause, final Operator requestOperator,
                                                                   final Boolean excludeFromStats, final String exclusionFromStatsCause,
                                                                   final IssueTerminationRequestState state, final Operator managerOperator,
                                                                   final String comment, final Date created, final Date updated, final Issue issue) {
        final IssueTerminationRequest issueTerminationRequest = new IssueTerminationRequest();
        issueTerminationRequest.setId(id);
        issueTerminationRequest.setRequestCause(requestCause);
        issueTerminationRequest.setRequestOperator(requestOperator);
        issueTerminationRequest.setExcludedFromStats(excludeFromStats);
        issueTerminationRequest.setExclusionFromStatsCause(exclusionFromStatsCause);
        issueTerminationRequest.setState(state);
        issueTerminationRequest.setManagerOperator(managerOperator);
        issueTerminationRequest.setComment(comment);
        issueTerminationRequest.setCreated(created);
        issueTerminationRequest.setUpdated(updated);
        issueTerminationRequest.setIssue(issue);
        return issueTerminationRequest;
    }

    private static IssueTerminationRequestDto issueTerminationRequestDto(final Long id, final String requestCause, final OperatorUserDto requestOperator,
                                                                         final Boolean excludeFromStats, final String exclusionFromStatsCause,
                                                                         final IssueTerminationRequestStateDto state, final OperatorUserDto managerOperator,
                                                                         final String comment, final Date created, final Date updated, final Long issueId) {
        final IssueTerminationRequestDto issueTerminationRequestDto = new IssueTerminationRequestDto();
        issueTerminationRequestDto.setId(id);
        issueTerminationRequestDto.setRequestCause(requestCause);
        issueTerminationRequestDto.setRequestOperator(requestOperator);
        issueTerminationRequestDto.setExcludedFromStats(excludeFromStats);
        issueTerminationRequestDto.setExclusionFromStatsCause(exclusionFromStatsCause);
        issueTerminationRequestDto.setState(state);
        issueTerminationRequestDto.setManagerOperator(managerOperator);
        issueTerminationRequestDto.setComment(comment);
        issueTerminationRequestDto.setCreated(created);
        issueTerminationRequestDto.setUpdated(updated);
        issueTerminationRequestDto.setIssueId(issueId);
        return issueTerminationRequestDto;
    }
}
