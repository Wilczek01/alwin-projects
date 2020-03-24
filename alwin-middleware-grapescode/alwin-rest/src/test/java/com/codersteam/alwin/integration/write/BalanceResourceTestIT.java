package com.codersteam.alwin.integration.write;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.mock.CurrencyExchangeRateServiceMock.EUR_EXCHANGE_RATE_BY_DATE;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENTS;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.BalanceTestData.EUR_EXCHANGE_RATE;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_5;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCustomerInvolvementTestData.customerInvolvementDto1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static java.util.Collections.singletonList;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-issue-configuration-not-include-due-invoices-as-subjects.json"})
public class BalanceResourceTestIT extends WriteTestBase {

    @Test
    public void shouldUpdateIssueBalances() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2018-07-11";

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_5, dueInvoicesWithCorrections());

        // and
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), EUR_EXCHANGE_RATE);

        // and
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_5, singletonList(customerInvolvementDto1()));

        // when
        final int responseCode = checkHttpPatchCode("balances/issue/" + ISSUE_ID_2, loginToken);

        // then
        assertAccepted(responseCode);
    }
}
