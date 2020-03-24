package com.codersteam.alwin.integration.read;

import com.codersteam.alwin.auth.model.BalanceStartAndAdditionalResponse;
import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_21;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.NON_EXISTING_AIDA_COMPANY_ID;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static java.util.Collections.emptyList;

/**
 * @author Tomasz Sliwinski
 */
public class BalanceResourceTestIT extends ReadTestBase {

    @Test
    public void shouldBeAuthorizedToGetCompanyBalance() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("balances/company/" + COMPANY_ID_10);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldRetrieveCompanyBalance() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(COMPANY_ID_10, dueInvoicesWithCorrections());

        // when
        final JsonElement response = sendHttpGet("balances/company/" + COMPANY_ID_10, loginToken);

        // then
        assertResponseEquals("balance/companyBalance.json", response);
    }

    @Test
    public void shouldReturnZeroBalanceForNotExistingCompany() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(NON_EXISTING_AIDA_COMPANY_ID, emptyList());

        // when
        final JsonElement response = sendHttpGet("balances/company/" + NON_EXISTING_AIDA_COMPANY_ID, loginToken);

        // then
        assertResponseEquals("balance/notExistingCompanyBalance.json", response);
    }

    @Test
    public void shouldBeAuthorizedToUpdateIssueBalances() throws IOException {
        // when
        final int responseCode = checkHttpPatchCode("balances/issue/" + ISSUE_ID_1, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldFailToUpdateIssueBalancesWhenIssueNotExists() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpPatchCode("balances/issue/" + NOT_EXISTING_ISSUE_ID, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldBeAuthorizedToGetBalanceStartAndAdditional() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("balances/issue/" + ISSUE_ID_1 + "/overall");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldFailToGetBalanceStartAndAdditionalForNotExistingIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("balances/issue/" + NOT_EXISTING_ISSUE_ID + "/overall", loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldGetBalanceStartAndAdditional() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("balances/issue/" + ISSUE_ID_21 + "/overall", loginToken);

        // then
        assertResponseEquals("balance/balanceStartAndAdditionalIssue1.json", response, BalanceStartAndAdditionalResponse.class);
    }
}
