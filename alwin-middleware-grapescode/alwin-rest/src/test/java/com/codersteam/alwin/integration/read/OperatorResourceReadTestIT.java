package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.LIST_OPERATOR_TYPE;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_12;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_18;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_3;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_6;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_8;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1;

/**
 * @author Michal Horowic
 */
public class OperatorResourceReadTestIT extends ReadTestBase {

    @Test
    public void shouldByUnauthorizedToGetManagedOperators() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("operators/managed");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetManagedOperatorsOfLoggedInOperator() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("operators/managed", loginToken);

        // then
        assertResponseEquals("operator/managed/phoneDebtCollectorManagerOperators.json", response);
    }

    @Test
    public void shouldByUnauthorizedToGetAccountManagers() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("operators/accountManagers");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetAccountManagers() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("operators/accountManagers", loginToken);

        // then
        assertResponseEquals("operator/accountManager/allAccountManagers.json", response, LIST_OPERATOR_TYPE);
    }

    @Test
    public void shouldByUnauthorizedToGetOperatorsAssignToCompanyActivities() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("operators/company/" + COMPANY_ID_1 + "/activities");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetOperatorsAssignToCompanyActivities() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("operators/company/" + COMPANY_ID_1 + "/activities", loginToken);

        // then
        assertResponseEquals("operator/company/companyActivitiesOperators.json", response);
    }

    @Test
    public void shouldByUnauthorizedToGetOperatorTypes() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("operators/types");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetAllOperatorTypes() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("operators/types", loginToken);

        // then
        assertResponseEquals("operator/type/allTypes.json", response);
    }

    @Test
    public void shouldGetPageOfOperatorsFilteredByName() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("operators?firstResult=0&maxResults=2&searchText=Cyprian%20Kamil%20Nor", loginToken);

        // then
        assertResponseEquals("operator/operatorsFilteredByName.json", response);
    }

    @Test
    public void shouldGetPageOfOperatorsFilteredByLogin() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("operators?firstResult=0&maxResults=2&searchText=jslowacki", loginToken);

        // then
        assertResponseEquals("operator/operatorsFilteredByLogin.json", response);
    }

    @Test
    public void shouldByUnauthorizedToResetPasswordForOperator() throws Exception {
        // given
        final JsonElement request = request("operator/changePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/" + OPERATOR_ID_1, request, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldByForbiddenToResetPasswordForOperator() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("operator/changePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/" + OPERATOR_ID_1, request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotBeAllowedToResetPasswordToEmptyOne() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("operator/illegalChangePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/" + OPERATOR_ID_1, request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldByUnauthorizedToChangePasswordForOperator() throws Exception {
        // given
        final JsonElement request = request("operator/changePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/change", request, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotBeAllowedToChangePasswordIfNotForced() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("operator/secondChangePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/change", request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotBeAllowedToChangePasswordToEmptyOne() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("operator/illegalChangePassword.json");

        // when
        final int responseCode = checkHttpPatchCode("operators/password/change", request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldBeUnauthorizedToGetFieldDebtCollectors() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("operators/fields?firstResult=0&maxResults=5");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldBeForbiddenToGetFieldDebtCollectors() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("operators/fields?firstResult=0&maxResults=5", loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldGetFieldDebtCollectors() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("operators/fields?firstResult=0&maxResults=5", loginToken);

        // then
        assertResponseEquals("operator/fields/fieldDebtCollectors.json", response);
    }

    @Test
    public void shouldFindOperatorsForIssue() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("operator/managed/assignOperator3.json");

        // when
        final JsonElement response = sendHttpPost("operators/managed/issues", request, loginToken);

        // then
        assertResponseEquals("operator/managed/issueIds.json", response, LIST_OPERATOR_TYPE);
    }

    @Test
    public void shouldFindOperatorsForAllFilteredIssueByOperatorId() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("empty.json");

        // when
        final JsonElement response = sendHttpPost("operators/managed/issues?updateAll=true&operatorId=" + OPERATOR_ID_1, request, loginToken);

        // then
        assertResponseEquals("operator/managed/issueOperator.json", response, LIST_OPERATOR_TYPE);
    }

    @Test
    public void shouldFindOperatorsForAllIssuesFilteredByExtCompanyIds() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("empty.json");

        // when
        final JsonElement response = sendHttpPost("operators/managed/issues?updateAll=true" +
                "&extCompanyId=" + EXT_COMPANY_ID_2 +
                "&extCompanyId=" + EXT_COMPANY_ID_3 +
                "&extCompanyId=" + EXT_COMPANY_ID_8 +
                "&extCompanyId=" + EXT_COMPANY_ID_12 +
                "&extCompanyId=" + EXT_COMPANY_ID_18 +
                "&extCompanyId=" + EXT_COMPANY_ID_6, request, loginToken);

        // then
        assertResponseEquals("operator/managed/issueExtCompanyId.json", response, LIST_OPERATOR_TYPE);
    }

    @Test
    public void shouldFindOperatorsForAllOpenIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("empty.json");

        // when
        final JsonElement response = sendHttpPost("operators/managed/issues?updateAll=true", request, loginToken);

        // then
        assertResponseEquals("operator/managed/issueOpen.json", response, LIST_OPERATOR_TYPE);
    }

}
