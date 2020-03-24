package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.IssueInvoiceDao;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.InvoiceTestData.CURRENT_BALANCE_INVOICE_2;
import static com.codersteam.alwin.testdata.InvoiceTestData.CURRENT_BALANCE_INVOICE_3;
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.INVOICE_ID_2;
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_21_INVOICE_2_ID;
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_21_INVOICE_3_ID;
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_ID_21;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("UnnecessaryLocalVariable")
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json"})
public class IssueInvoiceDaoTestIT extends WriteTestBase {

    @EJB
    private IssueInvoiceDao issueInvoiceDao;

    @Test
    public void shouldUpdateIssueInvoicesFinalBalanceValueZeroForSpecificIssue() {
        //given
        final long issueId = ISSUE_ID_21;

        // when
        issueInvoiceDao.updateIssueInvoicesFinalBalance(issueId);

        // then
        final Optional<IssueInvoice> issue21Invoice2 = issueInvoiceDao.get(ISSUE_21_INVOICE_2_ID);
        assertTrue(issue21Invoice2.isPresent());
        assertEquals(issue21Invoice2.get().getFinalBalance(), CURRENT_BALANCE_INVOICE_2);
    }

    @Test
    public void shouldUpdateIssueInvoicesFinalBalanceForSpecificIssue() {
        //given
        final long issueId = ISSUE_ID_21;

        // when
        issueInvoiceDao.updateIssueInvoicesFinalBalance(issueId);

        // then
        final Optional<IssueInvoice> issue21Invoice3 = issueInvoiceDao.get(ISSUE_21_INVOICE_3_ID);
        assertTrue(issue21Invoice3.isPresent());
        assertEquals(issue21Invoice3.get().getFinalBalance(), CURRENT_BALANCE_INVOICE_3);
    }

    @Test
    public void shouldUpdateIssueInvoicesExclusion() {
        // when
        issueInvoiceDao.updateIssueInvoicesExclusion(ISSUE_ID_21, INVOICE_ID_2, true);

        // then
        final Optional<IssueInvoice> issue1Invoice1 = issueInvoiceDao.get(ISSUE_21_INVOICE_2_ID);
        assertTrue(issue1Invoice1.isPresent());
        assertEquals(issue1Invoice1.get().isExcluded(), true);
    }
}