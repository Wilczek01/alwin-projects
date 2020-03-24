package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.INVOICE_ENTRY_LIST;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICE_ENTRIES;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_1;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_11;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceEntryTestData.invoiceEntries;

/**
 * @author Adam Stepnowski
 */
public class InvoiceResourceTestIT extends ReadTestBase {

    @Before
    public void setup() {
        CURRENT_DATE = "2024-07-12";
    }

    @Test
    public void shouldGetExcludedInvoice() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/1?firstResult=0&maxResults=5&contractNumber=000121/15/3", loginToken);

        // then
        assertResponseEquals("invoice/excludedInvoice.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoices() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5", loginToken);

        // then
        assertResponseEquals("invoice/allInvoice.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByIssueDateAscending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=issueDate", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByIssueDateAscending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByLastPaymentDateAscending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=lastPaymentDate", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByLastPaymentDateAscending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByCurrentBalanceAscending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=currentBalance", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByCurrentBalanceAscending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByPaidDescending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=-paid", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByPaidDescending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByExclusionsDescending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=-excluded", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByExclusionsDescending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByContractNumberAscending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=contractNumber", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByContractNoAscending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByDueDateAscending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=dueDate", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByDueDateAscending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfAllInvoicesSortedByDueDateDescending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&sort=-dueDate", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceSortedByDueDateDescending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfOverdueIssueInvoicesSortedByDueDateDescending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&notPaidOnly=true&sort=-dueDate", loginToken);

        // then
        assertResponseEquals("invoice/firstPageOfOverdueIssueInvoicesSortedByDueDateDescending.json", response);
    }

    @Test
    public void shouldGetFirstPageOfIssueSubjectIssueInvoicesSortedByDueDateDescending() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&issueSubjectOnly=true&sort=-dueDate", loginToken);

        // then
        assertResponseEquals("invoice/firstPageOfIssueSubjectIssueInvoicesSortedByDueDateDescending.json", response);
    }

    @Test
    public void shouldNotFindAnyInvoice() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/100?firstResult=0&maxResults=5", loginToken);

        // then
        assertResponseEquals("invoice/emptyInvoice.json", response);
    }

    @Test
    public void shouldGetFirstPageOfInvoiceWithRestrictiveMaxResults() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=1", loginToken);

        // then
        assertResponseEquals("invoice/invoiceFirstPage.json", response);
    }

    @Test
    public void shouldGetSecondPageOfInvoiceWithRestrictiveMaxResults() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=1&maxResults=1", loginToken);

        // then
        assertResponseEquals("invoice/invoiceSecondPage.json", response);
    }

    @Test
    public void shouldGetFirstPageOfInvoiceWithContractNumber() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&contractNumber=issue_21_00001/15/1", loginToken);

        // then
        assertResponseEquals("invoice/invoiceWithCriteriaFirstPage.json", response);
    }

    @Test
    public void shouldGetFirstPageOfInvoiceWithType() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&typeId=3", loginToken);

        // then
        assertResponseEquals("invoice/firstPageOfInvoiceWithType.json", response);
    }

    @Test
    public void shouldGetFirstPageOfInvoiceWithStartDueDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&startDueDate=2015-11-10", loginToken);

        // then
        assertResponseEquals("invoice/invoiceWithStartDueDateFirstPage.json", response);
    }

    @Test
    public void shouldGetFirstPageOfInvoiceWithStartDueDateAndEndDueDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?firstResult=0&maxResults=5&startDueDate=2015-11-10&endDueDate=2015-12-01", loginToken);

        // then
        assertResponseEquals("invoice/firstPageOfInvoiceWithStartDueDateAndEndDueDate.json", response);
    }

    @Test
    public void shouldFindAllInvoiceTypes() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/types", loginToken);

        // then
        assertResponseEquals("invoice/allInvoiceTypesFromExternalResource.json", response);
    }

    @Test
    public void shouldFindOnlyNotPaidAndOverdueInvoices() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("invoices/21?overdueOnly=true&notPaidOnly=true", loginToken);

        // then
        assertResponseEquals("invoice/firstPageOfInvoiceNotPaidAndOverdue.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetInvoiceEntries() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("invoices/entries?invoiceNo=" + INVOICE_NUMBER_11);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetInvoiceEntries() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // and
        INVOICE_ENTRIES.put(INVOICE_NUMBER_11, invoiceEntries());

        // when
        final JsonElement response = sendHttpGet("invoices/entries?invoiceNo=" + INVOICE_NUMBER_11, loginToken);

        // then
        assertResponseEquals("invoice/invoiceEntries.json", response, INVOICE_ENTRY_LIST);
    }

    @Test
    public void shouldGetEmptyCollectionOfInvoiceEntriesIfInvoiceHasNone() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // when
        final JsonElement response = sendHttpGet("invoices/entries?invoiceNo=" + INVOICE_NUMBER_1, loginToken);

        // then
        assertResponseEquals("emptyCollection.json", response, INVOICE_ENTRY_LIST);
    }

    @Test
    public void shouldReturnBadRequestWhenInvoiceNumberIsMissingForInvoiceEntries() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // when
        final HttpResponse response = checkHttpGet("invoices/entries", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("invoice/invoiceEntriesMissingInvoiceNo.json", response);
    }

    @Test
    public void shouldReturnCSVData() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final String response = sendHttpGetForTextResponse("invoices/report/21", loginToken);

        // then
        assertResponseEquals("invoice/CSVData.txt", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetCSVData() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("invoices/report/21");

        // then
        assertUnauthorized(responseCode);
    }
}
