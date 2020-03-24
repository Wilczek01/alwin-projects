package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.google.gson.JsonElement;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;

import static com.codersteam.alwin.integration.common.ResponseType.ALL_WALLETS_TYPE;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.IssueTestData.EXCLUDE_FROM_STATS;
import static com.codersteam.alwin.testdata.IssueTestData.EXCLUSION_CAUSE;
import static com.codersteam.alwin.testdata.IssueTestData.TERMINATION_CAUSE;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_3;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json", "test-issue-type-configuration.json"})
public class WalletResourceWriteTestIT extends WriteTestBase {

    @EJB
    private IssueService issueService;

    @Test
    public void shouldFindAllWallets() throws Exception {
        // given
        setWaitingForTerminationIssue();

        final String loginToken = loggedInAdmin();

        // and
        CURRENT_DATE = "2018-08-10";

        // when
        final JsonElement response = sendHttpGet("wallets/all", loginToken);

        // then
        assertResponseEquals("wallet/allWallets.json", response, ALL_WALLETS_TYPE);
    }

    private void setWaitingForTerminationIssue() throws Exception {
        issueService.terminateIssue(ISSUE_ID_1, OPERATOR_ID_3, TERMINATION_CAUSE, EXCLUDE_FROM_STATS, EXCLUSION_CAUSE);
        commitTrx();
    }
}
