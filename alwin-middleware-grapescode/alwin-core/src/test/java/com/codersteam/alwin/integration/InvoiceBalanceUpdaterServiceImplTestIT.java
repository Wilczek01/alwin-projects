package com.codersteam.alwin.integration;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.alwin.core.service.impl.issue.InvoiceBalanceUpdaterServiceImpl;
import com.codersteam.alwin.db.dao.InvoiceDao;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoice4;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoiceWithCorrections4;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoiceWithCorrections5;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Adam Stepnowski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
public class InvoiceBalanceUpdaterServiceImplTestIT extends CoreTestBase {

    @EJB
    private InvoiceBalanceUpdaterServiceImpl invoiceBalanceUpdaterService;

    @Inject
    private IssueDao issueDao;

    @Inject
    private InvoiceDao invoiceDao;

    @Test
    public void shouldUpdateInvoiceBalance() {
        // given
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));

        // and
        final boolean isIncludeDueInvoicesDuringIssue = false;

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issue, dueInvoiceWithCorrections4(), isIncludeDueInvoicesDuringIssue);
        final Optional<Invoice> invoice = invoiceDao.findInvoiceForIssueIdAndInvoiceNo(issue.getId(), dueInvoice4().getNumber());

        // then balance updated
        assertInvoice(invoice);
    }

    @Test
    public void shouldCreateInvoiceWhenInvoiceWasImportedAfterIssueWasCreated() {
        // given
        final Issue issue = issueDao.get(ISSUE_ID_2).orElseThrow(() -> new RuntimeException("Issue not found"));

        // and
        final boolean isIncludeDueInvoicesDuringIssue = false;

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issue, dueInvoiceWithCorrections4(), isIncludeDueInvoicesDuringIssue);

        // then
        final Optional<Invoice> invoice = invoiceDao.findInvoiceForIssueIdAndInvoiceNo(issue.getId(), dueInvoice4().getNumber());
        assertTrue(invoice.isPresent());

        final Optional<Invoice> invoice1 = invoiceDao.findInvoiceForIssueIdAndInvoiceNo(ISSUE_ID_1, dueInvoice4().getNumber());
        assertTrue(invoice1.isPresent());

    }

    @Test
    public void shouldAddNewIssueInvoiceAfterNewInvoiceAdded() throws Exception {
        // given issue with 2 issue invoices
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        assertEquals(2, issue.getIssueInvoices().size());

        // and
        final boolean isIncludeDueInvoicesDuringIssue = false;

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issue, dueInvoiceWithCorrections5(), isIncludeDueInvoicesDuringIssue);
        commitTrx();

        // then issue has 3 issue invoices
        final Issue issueAfterInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        final List<IssueInvoice> issueInvoices = issueAfterInvoicesBalanceUpdate.getIssueInvoices();
        assertEquals(3, issueInvoices.size());
    }

    @Test
    public void shouldMarkInvoiceAddedAfterIssueCreationAsIssueSubject() throws Exception {
        // given
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));

        // and current time after issue creation
        CURRENT_DATE = "2017-07-12";

        // and
        final boolean isIncludeDueInvoicesDuringIssue = true;

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issue, dueInvoiceWithCorrections5(), isIncludeDueInvoicesDuringIssue);
        commitTrx();

        // then
        final Issue issueAfterInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        assertInvoiceHasNegativeBalanceAndIsIssueSubject(issueAfterInvoicesBalanceUpdate, dueInvoiceWithCorrections5().getNumber());
    }

    @Test
    public void shouldNotMarkInvoiceAddedAfterIssueCreationAsIssueSubject() throws Exception {
        // given
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));

        // and current time after issue creation
        CURRENT_DATE = "2017-07-12";

        // and
        final boolean isIncludeDueInvoicesDuringIssue = false;

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issue, dueInvoiceWithCorrections5(), isIncludeDueInvoicesDuringIssue);
        commitTrx();

        // then
        final Issue issueAfterInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        assertInvoiceHasNegativeBalanceAndIsNotIssueSubject(issueAfterInvoicesBalanceUpdate, dueInvoiceWithCorrections5().getNumber());
    }

    private Optional<IssueInvoice> getIssueInvoiceByNumber(final Issue issue, final String invoiceNumber) {
        return issue.getIssueInvoices()
                .stream().filter(issueInvoice -> issueInvoice.getInvoice().getNumber().equals(invoiceNumber)).findFirst();
    }

    @Test
    public void shouldUpdateIssueInvoiceBalanceAndMarkAsIssueSubject() throws Exception {
        // given
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));

        // and current time after issue creation
        CURRENT_DATE = "2017-07-12";

        // and include due invoices as issue subjects
        final boolean isIncludeDueInvoicesDuringIssue = true;

        // and invoice not due on addition date
        final AidaInvoiceWithCorrectionsDto dueInvoice = dueInvoiceWithCorrections5();
        final Date invoiceDueDate = parse("2017-07-13");
        dueInvoice.setDueDate(invoiceDueDate);

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issue, dueInvoice, isIncludeDueInvoicesDuringIssue);
        commitTrx();

        // then new invoice is not issue subject
        final Issue issueAfterFirstInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        assertInvoiceHasNegativeBalanceAndIsNotIssueSubject(issueAfterFirstInvoicesBalanceUpdate, dueInvoice.getNumber());

        // then given update date after invoice due date
        CURRENT_DATE = "2017-07-14";

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issueAfterFirstInvoicesBalanceUpdate, dueInvoice, isIncludeDueInvoicesDuringIssue);
        commitTrx();

        // then new invoice is issue subject
        final Issue issueAfterSecondInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        assertInvoiceHasNegativeBalanceAndIsIssueSubject(issueAfterSecondInvoicesBalanceUpdate, dueInvoice.getNumber());
    }

    @Test
    public void shouldUpdateIssueInvoiceBalanceAndNotMarkAsIssueSubject() throws Exception {
        // given
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));

        // and current time after issue creation
        CURRENT_DATE = "2017-07-12";

        // and do not include due invoices as issue subjects
        final boolean isIncludeDueInvoicesDuringIssue = false;

        // and invoice not due on addition date
        final AidaInvoiceWithCorrectionsDto dueInvoice = dueInvoiceWithCorrections5();
        final Date invoiceDueDate = parse("2017-07-13");
        dueInvoice.setDueDate(invoiceDueDate);

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issue, dueInvoice, isIncludeDueInvoicesDuringIssue);
        commitTrx();

        // then new invoice is not issue subject
        final Issue issueAfterFirstInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        assertInvoiceHasNegativeBalanceAndIsNotIssueSubject(issueAfterFirstInvoicesBalanceUpdate, dueInvoice.getNumber());

        // then given update date after invoice due date
        CURRENT_DATE = "2017-07-14";

        // when
        invoiceBalanceUpdaterService.updateBalanceForIssueIdAndInvoiceId(issueAfterFirstInvoicesBalanceUpdate, dueInvoice, isIncludeDueInvoicesDuringIssue);
        commitTrx();

        // then new invoice is not issue subject
        final Issue issueAfterSecondInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        assertInvoiceHasNegativeBalanceAndIsNotIssueSubject(issueAfterSecondInvoicesBalanceUpdate, dueInvoice.getNumber());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-issue-configuration-not-include-due-invoices-as-subjects.json"})
    public void shouldUpdateIssueInvoicesFinalBalanceAfterUpdateInvoicesBalance() throws Exception {
        // given: not every issue invoice has final balance set
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        final List<IssueInvoice> issueInvoices = issue.getIssueInvoices();
        assertThat(issueInvoices.stream().filter(issueInvoice -> issueInvoice.getFinalBalance() == null)).isNotEmpty();

        // and: AIDA returns invoice with balance
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_1, dueInvoicesWithCorrections());

        // when: balances updated
        invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_1);
        commitTrx();

        // then: all issue invoices has final balance set
        final Issue issueAfterInvoicesBalanceUpdate = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));
        final List<IssueInvoice> issueInvoicesAfterBalanceUpdate = issueAfterInvoicesBalanceUpdate.getIssueInvoices();
        assertEquals(5, issueInvoicesAfterBalanceUpdate.size());
        assertThat(issueInvoicesAfterBalanceUpdate).allMatch(issueInvoice -> issueInvoice.getFinalBalance() != null);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void assertInvoice(final Optional<Invoice> invoice) {
        assertTrue(invoice.isPresent());
        assertEquals(invoice.get().getCurrentBalance(), dueInvoice4().getBalanceOnDocument());
    }

    private void assertInvoiceHasNegativeBalanceAndIsNotIssueSubject(final Issue issue, final String aidaInvoiceNumber) {
        final Optional<IssueInvoice> newIssueInvoiceWithNegativeBalance = getIssueInvoiceByNumber(issue, aidaInvoiceNumber);
        assertTrue(newIssueInvoiceWithNegativeBalance.isPresent());
        assertTrue(newIssueInvoiceWithNegativeBalance.get().getFinalBalance().signum() < 0);
        assertFalse(newIssueInvoiceWithNegativeBalance.get().getIssueSubject());
    }

    private void assertInvoiceHasNegativeBalanceAndIsIssueSubject(final Issue issue, final String aidaInvoiceNumber) {
        final Optional<IssueInvoice> newIssueInvoiceWithNegativeBalance = getIssueInvoiceByNumber(issue, aidaInvoiceNumber);
        assertTrue(newIssueInvoiceWithNegativeBalance.isPresent());
        assertTrue(newIssueInvoiceWithNegativeBalance.get().getFinalBalance().signum() < 0);
        assertTrue(newIssueInvoiceWithNegativeBalance.get().getIssueSubject());
    }
}
