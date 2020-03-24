package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.IssueTerminationRequestDao;
import com.codersteam.alwin.jpa.issue.IssueTerminationRequest;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.ISSUE_TERMINATION_REQUEST_ID_2;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.issueTerminationRequest1;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.issueTerminationRequest2;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;
import static com.codersteam.alwin.testdata.assertion.IssueTerminationRequestAssert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class IssueTerminationRequestDaoTestIT extends ReadTestBase {

    @EJB
    private IssueTerminationRequestDao dao;

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<IssueTerminationRequest> type = dao.getType();

        // then
        assertEquals(IssueTerminationRequest.class, type);
    }

    @Test
    public void shouldReturnIssueTerminationRequest() {
        // when
        final Optional<IssueTerminationRequest> issueTerminationRequest = dao.get(ISSUE_TERMINATION_REQUEST_ID_2);

        // then
        assertTrue(issueTerminationRequest.isPresent());
        assertEquals(issueTerminationRequest.get(), issueTerminationRequest2());
    }

    @Test
    public void shouldNotReturnIssueTerminationRequest() {
        // when
        final Optional<IssueTerminationRequest> issueTerminationRequest = dao.get(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID);

        // then
        assertFalse(issueTerminationRequest.isPresent());
    }

    @Test
    public void shouldFindIssueTerminationRequestByIssueId() {
        // when
        final Optional<IssueTerminationRequest> issueTerminationRequest = dao.findIssueTerminationRequestByIssueId(ISSUE_ID_1);

        // then
        assertTrue(issueTerminationRequest.isPresent());
        assertEquals(issueTerminationRequest.get(), issueTerminationRequest1());
    }

    @Test
    public void shouldNotFindClosedIssueTerminationRequestByIssueId() {
        // when
        final Optional<IssueTerminationRequest> issueTerminationRequest = dao.findIssueTerminationRequestByIssueId(ISSUE_ID_2);

        // then
        assertFalse(issueTerminationRequest.isPresent());
    }

    @Test
    public void shouldNotFindIssueTerminationRequestByIssueId() {
        // when
        final Optional<IssueTerminationRequest> issueTerminationRequest = dao.findIssueTerminationRequestByIssueId(NOT_EXISTING_ISSUE_ID);

        // then
        assertFalse(issueTerminationRequest.isPresent());
    }
}