package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityStateDto;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.Balance;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.integration.common.CoreTestBase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.CANCELED;
import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.PLANNED;
import static com.codersteam.alwin.core.api.model.currency.Currency.EUR;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static com.codersteam.alwin.integration.mock.CurrencyExchangeRateServiceMock.EUR_EXCHANGE_RATE_BY_DATE;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENTS;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_STATE_DTO_2;
import static com.codersteam.alwin.testdata.BalanceTestData.balanceWithNegativeBalanceThatIssueShouldBeProceedForITTest;
import static com.codersteam.alwin.testdata.BalanceTestData.zeroBalance;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_5;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCustomerInvolvementTestData.customerInvolvementDto1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json",
        "test-issue-configuration-not-include-due-invoices-as-subjects.json"})
public class IssueBalanceUpdaterServiceImplTestIT extends CoreTestBase {

    @EJB
    private IssueBalanceUpdaterService issueBalanceUpdaterService;

    @EJB
    private IssueService issueService;

    @EJB
    private ActivityService activityService;

    @Test
    public void shouldUpdateIssueBalanceWithoutClosingIssue() {
        // given
        CURRENT_DATE = "2019-01-23";

        // and
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), new BigDecimal("4.12"));

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_5, dueInvoicesWithCorrections());

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_5, singletonList(customerInvolvementDto1()));

        // when
        issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_2);

        // then balances updated
        assertIssue(issueService.findIssue(ISSUE_ID_2), ISSUE_STATE_DTO_2, CURRENT_DATE, balanceWithNegativeBalanceThatIssueShouldBeProceedForITTest());

        // and activities not changed
        final List<ActivityDto> activities = activityService.findAllIssueActivities(ISSUE_ID_2);
        assertEquals(2, activities.size());
        activities.forEach(activity -> assertEquals(PLANNED, activity.getState()));
    }

    @Test
    public void shouldUpdateIssueBalanceWithClosingIssueAndActivities() {
        // given
        CURRENT_DATE = "2019-01-23";

        // and
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), new BigDecimal("4.12"));

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_5, emptyList());

        // when
        issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_2);

        // then balances updated and issue closed
        assertIssue(issueService.findIssue(ISSUE_ID_2), IssueStateDto.CANCELED, CURRENT_DATE, zeroBalance());

        // and activities closed with update date set
        final List<ActivityDto> activities = activityService.findAllIssueActivities(ISSUE_ID_2);
        assertActivities(activities, CANCELED, CURRENT_DATE);
    }

    private void assertIssue(final IssueDto issue, final IssueStateDto state, final String balanceUpdateDate, final Map<Currency, Balance> balances) {
        assertNotNull(issue);
        assertEquals(state.getKey(), issue.getIssueState().getKey());

        assertBalancePLN(issue, balances.get(PLN));
        assertBalanceEUR(issue, balances.get(EUR));

        assertEquals(parse(balanceUpdateDate), issue.getBalanceUpdateDate());
    }

    private void assertBalanceEUR(final IssueDto issue, final Balance balance) {
        assertEquals(balance.getRpb(), issue.getRpbEUR());
        assertEquals(balance.getBalanceStart(), issue.getBalanceStartEUR());
        assertEquals(balance.getCurrentBalance(), issue.getCurrentBalanceEUR());
        assertEquals(balance.getPayments(), issue.getPaymentsEUR());
    }

    private void assertBalancePLN(final IssueDto issue, final Balance balance) {
        assertEquals(balance.getRpb(), issue.getRpbPLN());
        assertEquals(balance.getBalanceStart(), issue.getBalanceStartPLN());
        assertEquals(balance.getCurrentBalance(), issue.getCurrentBalancePLN());
        assertEquals(balance.getPayments(), issue.getPaymentsPLN());
    }

    @SuppressWarnings("SameParameterValue")
    private void assertActivities(final List<ActivityDto> activities, final ActivityStateDto state, final String updateDate) {
        assertEquals(2, activities.size());
        activities.forEach(activity -> {
                    assertEquals(state, activity.getState());
                    assertEquals(parse(updateDate), activity.getActivityDate());
                }
        );
    }
}
