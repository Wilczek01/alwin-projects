package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.InvoiceDao;
import com.codersteam.alwin.jpa.issue.Invoice;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_21;
import static com.codersteam.alwin.testdata.InvoiceTestData.EXCHANGE_RATE_10;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_1;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_10;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_3;
import static com.codersteam.alwin.testdata.InvoiceTestData.NOT_EXISTING_INVOICE_ID;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice1;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice10;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice11;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice12;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice13;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice14;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice15;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice2;
import static com.codersteam.alwin.testdata.InvoiceTestData.testInvoice3;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;
import static com.codersteam.alwin.testdata.assertion.InvoiceAssert.assertInvoiceListEquals;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static com.codersteam.alwin.testdata.criteria.InvoiceSearchCriteriaTestData.invoiceSearchCriteria;
import static com.codersteam.alwin.testdata.criteria.InvoiceSearchCriteriaTestData.invoiceSearchCriteriaWithContractNumber;
import static com.codersteam.alwin.testdata.criteria.InvoiceSearchCriteriaTestData.invoiceSearchCriteriaWithOverdueOnly;
import static com.codersteam.alwin.testdata.criteria.InvoiceSearchCriteriaTestData.invoiceSearchCriteriaWithStartDueDate;
import static com.codersteam.alwin.testdata.criteria.InvoiceSearchCriteriaTestData.invoiceSearchCriteriaWithStartDueDateAndEndDueDate;
import static com.codersteam.alwin.testdata.criteria.InvoiceSearchCriteriaTestData.invoiceSearchCriteriaWithType;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("UnnecessaryLocalVariable")
public class InvoiceDaoTestIT extends ReadTestBase {

    private static final String NOT_IN_DB_INVOICE_NUMBER_1 = "1";
    private static final String NOT_IN_DB_INVOICE_NUMBER_2 = "2";
    private static final String NOT_IN_DB_INVOICE_NUMBER_3 = "44444";
    private static final String NOT_IN_DB_INVOICE_NUMBER_4 = "3333";
    private static final String NOT_IN_DB_INVOICE_NUMBER_5 = "33";

    @EJB
    private InvoiceDao invoiceDao;

    @Test
    public void shouldFindExistingInvoices() {
        // given
        final List<String> invoiceIdsToCheck = asList(NOT_IN_DB_INVOICE_NUMBER_1, NOT_IN_DB_INVOICE_NUMBER_2, INVOICE_NUMBER_1, INVOICE_NUMBER_3,
                NOT_IN_DB_INVOICE_NUMBER_3, NOT_IN_DB_INVOICE_NUMBER_4, NOT_IN_DB_INVOICE_NUMBER_5);

        // when
        final List<String> existingInvoiceIds = invoiceDao.findExistingInvoiceNumbers(invoiceIdsToCheck);

        // then
        assertEquals(2, existingInvoiceIds.size());
        assertTrue(existingInvoiceIds.containsAll(asList(INVOICE_NUMBER_1, INVOICE_NUMBER_3)));
    }

    @Test
    public void shouldReturnEmptyInvoiceCollection() {
        // given
        final List<String> invoiceIdsToCheck = null;

        // when
        final List<String> existingInvoiceIds = invoiceDao.findExistingInvoiceNumbers(invoiceIdsToCheck);

        // then
        assertTrue(existingInvoiceIds.isEmpty());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Invoice> type = invoiceDao.getType();

        // then
        assertEquals(Invoice.class, type);
    }

    @Test
    public void shouldNotFindInvoiceForIssueId() {
        // given
        final long issueId = NOT_EXISTING_ISSUE_ID;

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteria(0, 6), new LinkedHashMap<>());

        // then
        assertTrue(invoices.isEmpty());
    }

    @Test
    public void shouldFindAllInvoiceForIssueId() {
        // given
        final long issueId = ISSUE_ID_21;

        // and
        final List<Invoice> expected = asList(testInvoice2(), testInvoice3(), testInvoice10(), testInvoice11(), testInvoice12(), testInvoice13());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteria(0, 6), new LinkedHashMap<>());

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldFindOnlyOverdueInvoiceForIssueId() {
        // given
        final long issueId = ISSUE_ID_21;

        // and
        final List<Invoice> expected = asList(testInvoice10(), testInvoice11(), testInvoice12(), testInvoice13(), testInvoice14(), testInvoice15());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteriaWithOverdueOnly(0, 6), new LinkedHashMap<>());

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldFindAllInvoiceForIssueIdWithRestrictiveMaxResults() {
        // given
        final long issueId = ISSUE_ID_21;

        // and
        final List<Invoice> expected = singletonList(testInvoice2());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteria(0, 1), new LinkedHashMap<>());

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldFindAllInvoiceForIssueIdWithContractNumber() {
        // given
        final long issueId = ISSUE_ID_21;

        // and
        final List<Invoice> expected = asList(testInvoice11(), testInvoice12(), testInvoice13(), testInvoice14(), testInvoice15());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteriaWithContractNumber(0, 6), new LinkedHashMap<>());

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldFindAllInvoiceForIssueIdWithType() {
        // given
        final long issueId = ISSUE_ID_21;

        // and
        final List<Invoice> expected = asList(testInvoice3(), testInvoice10(), testInvoice11(), testInvoice12(), testInvoice13(), testInvoice14());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteriaWithType(0, 6), new LinkedHashMap<>());

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldFindAllInvoiceForIssueIdWithStartDueDate() {
        // given
        final long issueId = ISSUE_ID_21;

        // and
        final List<Invoice> expected = asList(testInvoice2(), testInvoice3(), testInvoice10(), testInvoice11(), testInvoice12(), testInvoice13());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteriaWithStartDueDate(0, 6), new LinkedHashMap<>());

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldFindAllInvoiceForIssueIdWithStartDueDateAndEndDueDate() {
        // given
        final long issueId = ISSUE_ID_21;

        // and
        final List<Invoice> expected = singletonList(testInvoice3());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, invoiceSearchCriteriaWithStartDueDateAndEndDueDate(0, 6), new LinkedHashMap<>());

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldNotFindExistingInvoice() {
        // given
        final String invoiceNo = NOT_EXISTING_INVOICE_ID;

        // when
        final Optional<Invoice> invoice = invoiceDao.findInvoiceByNumber(invoiceNo);

        // then
        assertTrue(!invoice.isPresent());
    }

    @Test
    public void shouldFindExistingInvoice() {
        // given
        final String invoiceNo = INVOICE_NUMBER_1;

        // when
        final Optional<Invoice> invoice = invoiceDao.findInvoiceByNumber(invoiceNo);

        // then
        assertTrue(invoice.isPresent());
        assertThat(invoice.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "realDueDate", "lastPaymentDate", "corrections.realDueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(testInvoice1());
    }

    @Test
    public void shouldFindAllIssueInvoices() {
        // given
        final List<Invoice> expected = asList(testInvoice2(), testInvoice3(), testInvoice10(), testInvoice11(), testInvoice12(), testInvoice13(),
                testInvoice14(), testInvoice15());

        // when
        final List<Invoice> invoices = invoiceDao.findInvoicesByIssueId(ISSUE_ID_21);

        // then
        assertInvoiceListEquals(invoices, expected);
    }

    @Test
    public void shouldReturnEmptyCollectionOfIssuesForNotExistingIssue() {
        // when
        final List<Invoice> invoices = invoiceDao.findInvoicesByIssueId(NOT_EXISTING_ISSUE_ID);

        // then
        assertTrue(invoices.isEmpty());
    }

    @Test
    public void shouldContainExchangeRateWithCorrectScale() {
        // when
        final Optional<Invoice> invoice = invoiceDao.findInvoiceByNumber(INVOICE_NUMBER_10);

        // then
        assertTrue(invoice.isPresent());
        assertThat(invoice.get().getExchangeRate().scale()).isEqualTo(4);
        assertThat(invoice.get().getExchangeRate()).isEqualTo(EXCHANGE_RATE_10);
    }

}
