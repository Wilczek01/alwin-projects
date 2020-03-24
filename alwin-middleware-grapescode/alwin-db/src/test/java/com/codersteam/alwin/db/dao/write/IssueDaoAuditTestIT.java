package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.common.RequestOperator;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import org.hibernate.envers.RevisionType;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.jpa.type.IssueState.DONE;
import static com.codersteam.alwin.testdata.AbstractIssuesTestData.createIssueInvoices;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.newIssue1;
import static com.codersteam.alwin.testdata.BalanceTestData.BALANCE_START_PLN_3;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice2;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
public class IssueDaoAuditTestIT extends WriteTestBase {

    @EJB
    private IssueDao issueDao;

    @Inject
    private RequestOperator requestOperator;

    @Test
    public void shouldDetectChangesInIssueEntity() throws Exception {
        // given
        requestOperator.setOperatorId(OPERATOR_ID_1);

        // and
        final Long issueId = createNewIssue();
        addNewInvoice(issueId);
        changeBalanceStart(issueId);
        changeState(issueId);

        // when
        final List<AuditLogEntry<Issue>> changes = issueDao.findChangesByEntityId(issueId);

        // then
        assertEquals(3, changes.size());
        assertEquals(RevisionType.ADD, changes.get(0).getRevisionType());
        assertEquals(RevisionType.MOD, changes.get(1).getRevisionType());
        assertEquals(RevisionType.MOD, changes.get(2).getRevisionType());

        // and
        assertCorrectChangingOperatorLogged(changes, OPERATOR_ID_1);
    }

    private void assertCorrectChangingOperatorLogged(final List<AuditLogEntry<Issue>> changes, final Long operatorId) {
        for (final AuditLogEntry<Issue> change : changes) {
            assertEquals(operatorId, change.getRevEntity().getOperator());
        }
    }

    private Long createNewIssue() throws Exception {
        final Issue issue = newIssue1();
        issueDao.create(issue);
        commitTrx();
        return issue.getId();
    }

    private void addNewInvoice(final Long issueId) throws Exception {
        final Issue issue = getIssue(issueId);
        final Invoice invoice = testInvoice2();
        invoice.setId(null);
        invoice.setIssueInvoices(createIssueInvoices(issue, singletonList(invoice)));
        issueDao.update(issue);
        commitTrx();
    }

    private void changeBalanceStart(final Long issueId) throws Exception {
        final Issue issue = getIssue(issueId);
        issue.setBalanceStartPLN(BALANCE_START_PLN_3);
        issueDao.update(issue);
        commitTrx();
    }

    private void changeState(final Long issueId) throws Exception {
        final Issue issue = getIssue(issueId);
        issue.setIssueState(DONE);
        issueDao.update(issue);
        commitTrx();
    }

    private Issue getIssue(final Long issueId) {
        final Optional<Issue> personOpt = issueDao.get(issueId);
        return personOpt.orElseThrow(() -> new IllegalStateException(String.format("Cannot load Issue with id %d", issueId)));
    }
}
