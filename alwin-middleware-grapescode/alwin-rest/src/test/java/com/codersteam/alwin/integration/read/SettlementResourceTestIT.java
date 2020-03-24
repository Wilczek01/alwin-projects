package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.INVOICE_SETTLEMENT_TYPE_LIST;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_1;
import static com.codersteam.alwin.testdata.InvoiceTestData.NOT_EXISTING_INVOICE_NUMBER;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.NON_EXISTING_AIDA_COMPANY_ID;

/**
 * @author Tomasz Sliwinski
 */
public class SettlementResourceTestIT extends ReadTestBase {

    @Test
    public void shouldBeAuthorizedToGetCompanySettlements() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("settlements/company/" + COMPANY_ID_10);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetFirstPageOfCompanySettlements() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("settlements/company/" + COMPANY_ID_10 + "?firstResult=0&maxResults=2", loginToken);

        // then
        assertResponseEquals("settlement/companySettlementsFirstPage.json", response);
    }

    @Test
    public void shouldGetSecondPageOfCompanySettlements() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("settlements/company/" + COMPANY_ID_10 + "?firstResult=2&maxResults=2", loginToken);

        // then
        assertResponseEquals("settlement/companySettlementsSecondPage.json", response);
    }

    @Test
    public void shouldGetEmptySettlementsResponseWhenNoDataForCompany() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("settlements/company/" + NON_EXISTING_AIDA_COMPANY_ID + "?firstResult=0&maxResults=1", loginToken);

        // then
        assertResponseEquals("settlement/companySettlementsNoResults.json", response);
    }

    @Test
    public void shouldBeAuthorizedToGetSettlementsByInvoiceNo() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("settlements/invoice?invoiceNo=" + INVOICE_NUMBER_1);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetSettlementsByInvoiceNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("settlements/invoice?invoiceNo=" + INVOICE_NUMBER_1, loginToken);

        // then
        assertResponseEquals("settlement/invoiceSettlements.json", response, INVOICE_SETTLEMENT_TYPE_LIST);
    }

    @Test
    public void shouldGetEmptySettlementsResponseForNotExistingInvoiceNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("settlements/invoice?invoiceNo=" + NOT_EXISTING_INVOICE_NUMBER, loginToken);

        // then
        assertEmptyCollectionResponse(response);
    }
}
