package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.IssueInvoiceDao;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_4;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice4;
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_1_INVOICE_1_ID;
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_ID_21;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;
import static com.codersteam.alwin.testdata.assertion.InvoiceAssert.assertInvoiceListEquals;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("UnnecessaryLocalVariable")
public class IssueInvoiceDaoTestIT extends ReadTestBase {

    @EJB
    private IssueInvoiceDao issueInvoiceDao;

    @Test
    public void shouldNotUpdateIssueInvoicesFinalBalanceForSpecificIssue() {
        //given
        final long issueId = ISSUE_ID_21;

        // when
        issueInvoiceDao.updateIssueInvoicesFinalBalance(issueId);

        // then
        final Optional<IssueInvoice> issue1Invoice1 = issueInvoiceDao.get(ISSUE_1_INVOICE_1_ID);
        assertTrue(issue1Invoice1.isPresent());
        assertEquals(issue1Invoice1.get().getFinalBalance(), null);
    }

    @Test
    public void shouldFindInvoicesOutOfService() {
        // when
        final List<Invoice> invoicesOutOfService = issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_1);

        // then
        final List<Invoice> expected = singletonList(testInvoice4());
        assertInvoiceListEquals(invoicesOutOfService, expected);
    }

    @Test
    public void shouldNotFindInvoicesOutOfService() {
        // when
        final List<Invoice> invoicesOutOfService = issueInvoiceDao.findInvoicesOutOfService(ISSUE_ID_4);

        // then
        assertEquals(0, invoicesOutOfService.size());
    }

    @Test
    public void shouldReturnEmptyListForFindInvoicesOutOfServiceWhenActiveIssueForCompanyNotExists() {
        // when
        final List<Invoice> invoicesOutOfService = issueInvoiceDao.findInvoicesOutOfService(NOT_EXISTING_ISSUE_ID);

        // then
        assertTrue(invoicesOutOfService.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForFindInvoicesOutOfServiceWhenIssueIdIsNull() {
        // when
        final List<Invoice> invoicesOutOfService = issueInvoiceDao.findInvoicesOutOfService(null);

        // then
        assertTrue(invoicesOutOfService.isEmpty());
    }
}