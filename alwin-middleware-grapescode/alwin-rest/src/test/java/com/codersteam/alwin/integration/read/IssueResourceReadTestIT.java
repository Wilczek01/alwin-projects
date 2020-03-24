package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.ISSUE_PAGE;
import static com.codersteam.alwin.integration.common.ResponseType.ISSUE_TYPE_CONFIGURATION_LIST;
import static com.codersteam.alwin.integration.common.ResponseType.ISSUE_TYPE_WITH_OPERATOR_TYPE_LIST;
import static com.codersteam.alwin.integration.common.ResponseType.OPERATED_ISSUE;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto2;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_19;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_12;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_18;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_23;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_24;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_25;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_26;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_27;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_3;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_6;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_8;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_9;
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID;
import static com.codersteam.alwin.testdata.CompanyTestData.companyDto1;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.ISSUE_TERMINATION_REQUEST_ID_1;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.ID_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto11;


/**
 * @author Michal Horowic
 */
public class IssueResourceReadTestIT extends ReadTestBase {

    @Test
    public void shouldFindEditableIssueById() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/" + ISSUE_ID_1, loginToken);

        // then
        assertResponseEquals("findIssueById.json", response, OPERATED_ISSUE);
    }

    @Test
    public void shouldFindNotEditableIssueById() throws Exception {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // when
        final JsonElement response = sendHttpGet("issues/" + ISSUE_ID_1, loginToken);

        // then
        assertResponseEquals("findNotEditableIssueById.json", response, OPERATED_ISSUE);
    }

    @Test
    public void shouldBeUnauthorizedToFindIssueById() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("issues/" + ISSUE_ID_1);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldBeUnauthorizedToFindIssueWithUpdatedBalanceById() throws Exception {
        // when
        final int responseCode = checkHttpPatchCode("issues/" + ISSUE_ID_1, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetFirstPageOfAllIssues() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=3", loginToken);

        // then
        assertResponseEquals("issuesFirstPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldGetSecondPageOfAllIssues() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=3&maxResults=3", loginToken);

        // then
        assertResponseEquals("issuesSecondPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByBalance() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&totalCurrentBalancePLNMin=400000&totalCurrentBalancePLNMax=450000", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByBalance.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByCompanyNip() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&nip=6837458881", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByCompanyNip.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByCompanyRegon() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&regon=615557755", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByCompanyRegon.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByPartOfCompanyName() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&companyName=rma%2099", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByCompanyName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByInvoiceContractNo() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&invoiceContractNo=001148/15/1", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByInvoiceContractNo.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByPersonPesel() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&personPesel=88010101234", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByPersonPesel.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByLoweredPersonName() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&personName=jan%20kowalsky", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByPersonName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByRevertedLoweredPersonName() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=2&personName=kowalsky%20jan", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByRevertedPersonName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactName() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=3&contactName=kochanowski%20jan", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByContactName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactRevertedName() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=3&contactName=jan%20kochanowski", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByContactRevertedName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactDocumentEmail() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=3&contactEmail=documents@email.pl", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByContactDocumentEmail.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactOfficeEmail() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=3&contactEmail=test@test.pl", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByContactOfficeEmail.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactEmail() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=3&contactEmail=contat@company.pl", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByContactEmail.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactPhone() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=3&contactPhone=1234567", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByContactPhone.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByTotalCurrentBalancePLNMin() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&totalCurrentBalancePLNMin=150000", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByBalanceMin.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByTotalCurrentBalancePLNMax() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&totalCurrentBalancePLNMax=250000", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByBalanceMax.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&startStartDate=2023-07-10&endStartDate=2023-07-10", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByStartDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartOfStartDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&startStartDate=2023-07-10", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByStartOfStartDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByEndOfStartDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&endStartDate=2023-07-10", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByEndOfStartDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&startContactDate=2017-07-09&endContactDate=2017-07-11", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByContactDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartOfContactDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&startContactDate=2017-07-09", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByStartOfContactDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByEndOfContactDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&endContactDate=2017-07-11", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByEndOfContactDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByOperator() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&operatorId=1&operatorId=3", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByOperator.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByNotAssignedOperator() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&unassigned=true", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByNotAssignedOperator.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByState() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&state=NEW&state=IN_PROGRESS", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByState.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByPriority() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&priorityKey=NORMAL", loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByPriority.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByExtCompanyIds() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final String extCompanyIdParams =
                extCompanyIdParams(EXT_COMPANY_ID_2, EXT_COMPANY_ID_3, EXT_COMPANY_ID_8, EXT_COMPANY_ID_12, EXT_COMPANY_ID_18, EXT_COMPANY_ID_6);

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&" + extCompanyIdParams, loginToken);

        // then
        assertResponseEquals("issue/admin/paginatedIssuesForAdminFilteredByByExtCompanyIds.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldBeUnauthorizedToGetPaginatedIssues() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("issues?firstResult=0&maxResults=3");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetFirstPageOfOperatorIssues() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3", loginToken);

        // then
        assertResponseEquals("operatorIssuesFirstPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldGetSecondPageOfOperatorIssues() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=3&maxResults=3", loginToken);

        // then
        assertResponseEquals("operatorIssuesSecondPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldBeUnauthorizedToGetPaginatedOperatorIssues() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("issues/my?firstResult=0&maxResults=3");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssue() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/company/" + companyDto1().getId() + "?excludedIssueId=" + issueDto2().getId() +
                "&firstResult=0&maxResults=6", loginToken);

        // then
        assertResponseEquals("allIssuesForCompanyExcludingGivenIssue.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithRestrictiveMaxResults() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/company/" + companyDto1().getId() + "?excludedIssueId=" + issueDto2().getId() +
                "&firstResult=0&maxResults=2", loginToken);
        // then
        assertResponseEquals("allIssuesForCompanyExcludingGivenIssueWithRestrictiveMaxResults.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithIssueType() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/company/" + companyDto1().getId() + "?excludedIssueId=" + issueDto2().getId() +
                "&firstResult=0&maxResults=6&issueTypeId=1", loginToken);
        // then
        assertResponseEquals("allIssuesForCompanyExcludingGivenIssueWithIssueType.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithActivityType() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/company/" + companyDto1().getId() + "?excludedIssueId=" + issueDto2().getId() +
                "&firstResult=0&maxResults=6&activityTypeId=2", loginToken);
        // then
        assertResponseEquals("allIssuesForCompanyExcludingGivenIssueWithActivityType.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithActivityDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/company/" + companyDto1().getId() + "?excludedIssueId=" + issueDto2().getId() +
                "&firstResult=0&maxResults=6&activityDateFrom=2016-07-09&activityDateTo=2016-07-11", loginToken);
        // then
        assertResponseEquals("allIssuesForCompanyExcludingGivenIssueWithActivityDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldBeUnauthorizedToAllIssuesForCompanyExcludingGivenIssue() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("issues/company/1?excludedIssueId=0");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldForbiddenAccessToWorkResource() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final JsonElement request = request("empty.json");

        // when
        final int responseCode = checkHttpPostCode("issues/work", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldFindOperatorIssuesWithSearchCriteria() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3", loginToken);

        // then
        assertResponseEquals("operatorIssuesWithSearchCriteriaFirstPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindOperatorIssuesWithSearchCriteriaByStartDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3&startStartDate=2023-07-10&endStartDate=2023-07-10", loginToken);

        // then
        assertResponseEquals("operatorIssuesWithSearchCriteriaByStartDateFirstPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindOperatorIssuesWithSearchCriteriaByExpirationDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3&startExpirationDate=2024-08-10&endExpirationDate=2024-08-10",
                loginToken);

        // then
        assertResponseEquals("operatorIssuesWithSearchCriteriaByExpirationDateFirstPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldNotFindOperatorIssuesWithSearchCriteriaByBalanceSeparatedByComma() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final HttpResponse response = checkHttpGet("issues/my?firstResult=0&maxResults=3&startTotalCurrentBalancePLN=234642,67&endTotalCurrentBalancePLN=234642,67", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/admin/operatorIssuesWithSearchCriteriaByBalanceSeparatedByComma.json", response);
    }

    @Test
    public void shouldNotFindOperatorIssuesWithSearchCriteriaByBalanceInvalidNumber() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final HttpResponse response = checkHttpGet("issues/my?firstResult=0&maxResults=3&startTotalCurrentBalancePLN=not_number", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/admin/operatorIssuesWithSearchCriteriaByBalanceWithThreeDecimalPositions.json", response);
    }

    @Test
    public void shouldFindOperatorIssuesWithSearchCriteriaByBalance() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3&startTotalCurrentBalancePLN=554643.67&endTotalCurrentBalancePLN=554643.67", loginToken);

        // then
        assertResponseEquals("operatorIssuesWithSearchCriteriaByBalanceFirstPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindOperatorIssuesWithSearchCriteriaByExpirationDateAndBalance() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3&startTotalCurrentBalancePLN=554643.67&endTotalCurrentBalancePLN=554643.67" +
                        "&startExpirationDate=2024-08-10&endExpirationDate=2024-08-10",
                loginToken);

        // then
        assertResponseEquals("operatorIssuesWithSearchCriteriaByExpirationDateAndBalanceFirstPage.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldNotFindOperatorIssuesWithSearchCriteriaByPlannedDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final HttpResponse response = checkHttpGet("issues/my?firstResult=0&maxResults=3&startPlannedDate=20170714&endPlannedDate=20170714", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/admin/closedIssuesAssignedToUserWithSearchCriteriaByPlannedDate.json", response);
    }

    @Test
    public void shouldFindOperatorIssuesWithSearchCriteriaByPlannedDate() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3&startPlannedDate=2017-08-03&endPlannedDate=2017-08-03", loginToken);

        // then
        assertResponseEquals("operatorIssuesWithSearchCriteriaByPlannedDateFirstPage.json", response);
    }

    @Test
    public void shouldFindOperatorIssuesWithSearchByActivityDateCriteria() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/my?firstResult=0&maxResults=3&startActivityDate=2017-07-10&endActivityDate=2017-07-10", loginToken);

        // then
        assertResponseEquals("operatorIssuesWithSearchCriteriaFirstPage.json", response);
    }

    @Test
    public void shouldFindAllIssueTypes() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/types", loginToken);

        // then
        assertResponseEquals("allIssuesTypes.json", response, ISSUE_TYPE_WITH_OPERATOR_TYPE_LIST);
    }

    @Test
    public void shouldFindMyIssueTypes() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/types/my", loginToken);

        // then
        assertResponseEquals("myIssuesTypes.json", response);
    }

    @Test
    public void shouldFindAdminIssueTypes() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/types/my", loginToken);

        // then
        assertResponseEquals("adminIssuesTypes.json", response);
    }

    @Test
    public void shouldFindAllIssueStates() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/states", loginToken);

        // then
        assertResponseEquals("allIssuesStates.json", response);
    }

    @Test
    public void shouldFindAllIssuePriorities() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/priorities", loginToken);

        // then
        assertResponseEquals("allIssuesPriorities.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToFindAllIssueStates() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("issues/states");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldFindAllIssueActiveStates() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/states?onlyActive=true", loginToken);

        // then
        assertResponseEquals("allIssuesActiveStates.json", response);
    }

    @Test
    public void shouldFindAllIssueStatesWithActiveStatesFlagFalse() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/states?onlyActive=false", loginToken);

        // then
        assertResponseEquals("allIssuesStates.json", response);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByCompanyNameAscending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=CUSTOMER", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByCustomerNameAscending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByRpbAscending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=RPB", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByRpbAscending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByCurrentBalanceDescending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=-CURRENT_BALANCE", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByCurrentBalanceDescending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByBalanceStartDescending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=-BALANCE_START", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByBalanceStartDescending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByTypeNameDescending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=-TYPE", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByTypeNameDescending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByStartDateAscending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=START_DATE", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByStartDateAscending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByExpirationDateAscending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=EXPIRATION_DATE", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByExpirationDateAscending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByStateAscending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=STATE", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByStateAscending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByPriorityStateAscending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=PRIORITY_STATE", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByPriorityStateAscending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByPaymentsAscending() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&sort=PAYMENTS", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesSortedByPaymentsAscending.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByBalance() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&totalCurrentBalancePLNMin=400000&totalCurrentBalancePLNMax=450000", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByBalance.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByTotalCurrentBalancePLNMin() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=4&totalCurrentBalancePLNMin=150000", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByBalanceMin.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByTags() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=4&tagId=1&tagId=2", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByTags.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByOneTag() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=4&tagId=2", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByOneTag.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByTotalCurrentBalancePLNMax() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&totalCurrentBalancePLNMax=250000", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByBalanceMax.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartDate() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&startStartDate=2023-07-10&endStartDate=2023-07-10", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByStartDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartOfStartDate() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&startStartDate=2023-07-10", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByStartOfStartDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByEndOfStartDate() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&endStartDate=2023-07-10", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByEndOfStartDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactDate() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&startContactDate=2017-07-09&endContactDate=2017-07-11", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByContactDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartOfContactDate() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&startContactDate=2017-07-09", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByStartOfContactDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByEndOfContactDate() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&endContactDate=2017-07-11", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByEndOfContactDate.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByOperator() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&operatorId=1&operatorId=2", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByOperator.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByState() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&state=NEW&state=IN_PROGRESS", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByState.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByCompanyNip() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&nip=6837458881", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByCompanyNip.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByCompanyRegon() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&regon=615557755", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByCompanyRegon.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByPartOfCompanyName() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&companyName=rma%2099", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByCompanyName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByInvoiceContractNo() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&invoiceContractNo=001148/15/1", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByInvoiceContractNo.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByPersonPesel() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&personPesel=88010101234", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByPersonPesel.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByLoweredPersonName() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&personName=jan%20kowalsky", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByPersonName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByRevertedLoweredPersonName() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=2&personName=kowalsky%20jan", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByRevertedPersonName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactName() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&contactName=kochanowski%20jan", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByContactName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactRevertedName() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&contactName=jan%20kochanowski", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByContactRevertedName.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactDocumentEmail() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&contactEmail=documents@email.pl", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByContactDocumentEmail.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactOfficeEmail() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&contactEmail=test@test.pl", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByContactOfficeEmail.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactEmail() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&contactEmail=contat@company.pl", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByContactEmail.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactPhone() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&contactPhone=1234567", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByContactPhone.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldNotFindPaginatedManagedIssuesFilteredByNotExistingState() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("issues/managed?firstResult=0&maxResults=3&state=NEW&state=NOT_EXISTING_STATE", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/paginatedManagedIssuesFilteredByNotExistingState.json", response);
    }

    @Test
    public void shouldFindPaginatedUnassignedManagedIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=3&unassigned=true", loginToken);

        // then
        assertResponseEquals("issue/managed/unassignedMangedIssues.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedHighPriorityIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=5&priorityKey=HIGH", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByPriority.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesFilteredByExtCompanyIds() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=5" +
                "&extCompanyId=" + EXT_COMPANY_ID_2 +
                "&extCompanyId=" + EXT_COMPANY_ID_3 +
                "&extCompanyId=" + EXT_COMPANY_ID_8 +
                "&extCompanyId=" + EXT_COMPANY_ID_12 +
                "&extCompanyId=" + EXT_COMPANY_ID_18 +
                "&extCompanyId=" + EXT_COMPANY_ID_6, loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesByByExtCompanyIds.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldReturnNotFoundForIssuesFilteredByExtCompanyIdsWithMalformedExtCompanyId() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse httpResponse = checkHttpGet("issues/managed?firstResult=0&maxResults=5&extCompanyId=string", loginToken);

        // then
        assertNotFound(httpResponse);
    }

    @Test
    public void shouldHaveForbiddenAccessToManagedIssues() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final int responseCode = checkHttpGetCode("issues/managed?firstResult=0&maxResults=3", loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void unauthorizedShouldNotCreateIssue() throws IOException {
        // given
        final JsonElement request = request("issue/create/issueCreate.json");

        // when
        final int responseCode = checkHttpPostCode("issues", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldHaveProperRoleToCreateIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("issue/create/issueCreate.json");

        // when
        final int responseCode = checkHttpPostCode("issues", request, loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotCreateIssueWhenExtCompanyIdIsMissing() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("issue/create/issueCreateMissingExtCompanyId.json");

        // when
        final HttpResponse response = checkHttpPost("issues", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/createIssueMissingExtCompanyId.json", response);
    }

    @Test
    public void shouldNotCreateIssueWhenIssueTypeIdIsMissing() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("issue/create/issueCreateMissingIssueTypeId.json");

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_3, aidaCompanyDto10());

        // when
        final HttpResponse response = checkHttpPost("issues", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/createIssueMissingIssueTypeId.json", response);
    }

    @Test
    public void shouldNotCreateIssueWhenExtCompanyIdIsInvalid() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("issue/create/issueCreateInvalidExtCompanyId.json");

        // when
        final HttpResponse response = checkHttpPost("issues", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/createIssueInvalidExtCompanyId.json", response);
    }

    @Test
    public void shouldNotCreateIssueWhenIssueTypeIdIsInvalid() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_3, aidaCompanyDto10());

        // and
        final JsonElement request = request("issue/create/issueCreateInvalidIssueTypeId.json");

        // when
        final HttpResponse response = checkHttpPost("issues", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/createIssueInvalidIssueTypeId.json", response);
    }

    @Test
    public void shouldNotCreateIssueWhenCompanyAlreadyHasPendingIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_9, aidaCompanyDto11());

        // and
        final JsonElement request = request("issue/create/issueCreateForCompanyWithActiveIssue.json");

        // when
        final HttpResponse response = checkHttpPost("issues", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/createIssueCompanyAlreadyHasPendingIssue.json", response);
    }

    @Test
    public void shouldNotCreateIssueWhenOperatorTypeIsInvalid() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_3, aidaCompanyDto10());

        // and
        final JsonElement request = request("issue/create/issueCreateInvalidOperatorType.json");

        // when
        final HttpResponse response = checkHttpPost("issues", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/createIssueInvalidOperatorType.json", response);
    }

    @Test
    public void shouldNotCreateIssueWhenExpirationDateIsFromThePast() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_3, aidaCompanyDto10());

        // and
        final JsonElement request = request("issue/create/issueCreateInvalidIssueEndDate.json");

        // when
        final HttpResponse response = checkHttpPost("issues", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/createIssueExpirationDateFromThePast.json", response);
    }

    @Test
    public void shouldFindActiveIssueIdForCompany() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/active/company/" + EXT_COMPANY_ID_9, loginToken);

        // then
        assertResponseEquals("issue/activeIssueId.json", response);
    }

    @Test
    public void shouldNotFindActiveIssueIdForCompanyWithoutOne() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("issues/active/company/" + EXT_COMPANY_ID_3, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void shouldNotFindActiveIssueIdForNotExistingCompanyId() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("issues/active/company/" + NON_EXISTING_COMPANY_ID, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void unauthorizedShouldNotBeAbleToFindActiveIssueId() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("issues/active/company/" + EXT_COMPANY_ID_2);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void unauthorizedShouldNotTerminateIssue() throws IOException {
        // given
        final JsonElement request = request("issue/terminate/terminateIssue.json");

        // when
        final int responseCode = checkHttpPostCode("issues/terminate/" + ISSUE_ID_1, request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotTerminateNotExistingIssue() throws IOException {
        terminateIssue(NOT_EXISTING_ISSUE_ID, "issue/termination/terminateNotExistingIssue.json", "terminateIssue.json");
    }

    @Test
    public void shouldNotTerminateClosedIssue() throws IOException {
        terminateIssue(ISSUE_ID_19, "issue/termination/terminateClosedIssue.json", "terminateIssue.json");
    }

    @Test
    public void shouldNotTerminateIssueWhenTerminationCauseIsEmpty() throws IOException {
        terminateIssue(ISSUE_ID_1, "issue/termination/terminateIssueEmptyTerminationCause.json", "terminateIssueWithEmptyTerminationCause.json");
    }

    @Test
    public void shouldNotTerminateIssueWhenTerminationCauseIsTooLong() throws IOException {
        terminateIssue(ISSUE_ID_1, "issue/termination/terminateIssueTooLongTerminationCause.json", "terminateIssueWithTooLongTerminationCause.json");
    }

    @Test
    public void shouldNotTerminateIssueWhenExclusionFromStatsIsTooLong() throws IOException {
        terminateIssue(ISSUE_ID_1, "issue/termination/terminateIssueExclusionFromStatsIsTooLong.json", "terminateIssueWithTooLongExclusionFromStatsCause.json");
    }

    private void terminateIssue(final long issueId, final String validationResponse, final String fileName) throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final JsonElement request = request("issue/terminate/" + fileName);

        // when
        final HttpResponse response = checkHttpPost("issues/terminate/" + issueId, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals(validationResponse, response);
    }

    @Test
    public void unauthorizedShouldNotGetTerminationRequest() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("issues/termination/" + ISSUE_ID_1);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetTerminationRequest() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/termination/" + ISSUE_ID_1, loginToken);

        // then
        assertResponseEquals("issue/termination/issueTerminationResponse.json", response);
    }

    @Test
    public void shouldNotGetClosedTerminationRequest() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final int responseCode = checkHttpGetCode("issues/termination/" + ISSUE_ID_2, loginToken);

        // then
        assertNotFound(responseCode);
    }

    @Test
    public void unauthorizedShouldNotAcceptTerminateRequest() throws IOException {
        // when
        final JsonElement request = request("issue/termination/acceptTerminationRequest.json");

        // when
        final int responseCode = checkHttpPostCode("issues/termination/accept/" + ISSUE_TERMINATION_REQUEST_ID_1, request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotAcceptTerminationRequestWhenValidationErrorOccurs() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final JsonElement request = request("issue/termination/acceptTerminationRequest.json");

        // when
        final HttpResponse response = checkHttpPost("issues/termination/accept/" + NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/admin/acceptTerminationRequestValidationErrorOccurs.json", response);
    }

    @Test
    public void unauthorizedShouldNotRejectTerminateRequest() throws IOException {
        // given
        final JsonElement request = request("issue/termination/rejectTerminationRequest.json");

        // when
        final int responseCode = checkHttpPostCode("issues/termination/reject/" + ISSUE_TERMINATION_REQUEST_ID_1, request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotRejectTerminationRequestWhenValidationErrorOccurs() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final JsonElement request = request("issue/termination/rejectTerminationRequest.json");

        // when
        final HttpResponse response = checkHttpPost("issues/termination/reject/" + NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/admin/rejectTerminationRequestValidationError.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToUpdateIssueInvoicesExclusion() throws IOException {
        // given
        final JsonElement request = request("issue/exclusion/updateExclusion.json");

        // when
        final int responseCode = checkHttpPatchCode("issues/exclusion", request, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotUpdatePriorityForEmptyIdsList() throws IOException {
        //given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        //and
        final JsonElement requestBody = request("issue/priority/updateIssuePriorityEmptyList.json");

        //when
        final int responseCode = checkHttpPatchCode("issues/priority/NORMAL", requestBody, loginToken);

        //then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotUpdatePriorityWhenEmptyJsonGivenInBody() throws IOException {
        //given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        //and
        final JsonElement requestBody = new JsonObject();

        //when
        final int responseCode = checkHttpPatchCode("issues/priority/NORMAL", requestBody, loginToken);

        //then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotAssignNotExistingTag() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("tag/assignNotExistingTags.json");

        // when
        final HttpResponse response = checkHttpPatch("issues/" + ISSUE_ID_2 + "/tags", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("tag/missingTagValidationError.json", response);
    }

    @Test
    public void shouldFindAllIssueTypeConfiguration() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("issues/configurations", loginToken);

        // then
        assertResponseEquals("allIssueTypeConfiguration.json", response, ISSUE_TYPE_CONFIGURATION_LIST);
    }

    @Test
    public void shouldBeUnauthorizedToFindAllIssueTypeConfiguration() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("issues/configurations");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByIssueTypeId() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=4&issueTypeId=1", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredByIssueTypeId.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredBySegmentA() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=4&segment=A", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredBySegmentA.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredBySegmentB() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=4&segment=B", loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesFilteredBySegmentB.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesFilteredByExtCompanyIdsAndSegmentA() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        //and
        final String extCompanyIdParams =
                extCompanyIdParams(EXT_COMPANY_ID_2, EXT_COMPANY_ID_3, EXT_COMPANY_ID_8, EXT_COMPANY_ID_12, EXT_COMPANY_ID_18, EXT_COMPANY_ID_6);

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=5&segment=A&" + extCompanyIdParams, loginToken);

        // then
        assertResponseEquals("issue/managed/mangedIssuesByByExtCompanyIdsAndSegmentA.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindPaginatedIssuesFilteredByExtCompanyIdsAndSegmentB() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        //and
        final String extCompanyIdParams = extCompanyIdParams(EXT_COMPANY_ID_23, EXT_COMPANY_ID_24, EXT_COMPANY_ID_25, EXT_COMPANY_ID_26, EXT_COMPANY_ID_27);

        // when
        final JsonElement response = sendHttpGet("issues/managed?firstResult=0&maxResults=5&segment=B&" + extCompanyIdParams, loginToken);
        // then
        assertResponseEquals("issue/managed/mangedIssuesByByExtCompanyIdsAndSegmentB.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindAllPaginatedIssuesFilteredByIssueTypeId() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=4&issueTypeId=1", loginToken);

        // then
        assertResponseEquals("allIssuesFilteredByIssueTypeId.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindAllPaginatedIssuesFilteredBySegmentA() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=4&segment=A", loginToken);

        // then
        assertResponseEquals("allIssuesFilteredBySegmentA.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindAllPaginatedIssuesFilteredBySegmentB() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=4&segment=B", loginToken);

        // then
        assertResponseEquals("allIssuesFilteredBySegmentB.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindAllPaginatedIssuesFilteredByExtCompanyIdsAndSegmentA() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();
        //and
        final String extCompanyIdParams =
                extCompanyIdParams(EXT_COMPANY_ID_2, EXT_COMPANY_ID_3, EXT_COMPANY_ID_8, EXT_COMPANY_ID_12, EXT_COMPANY_ID_18, EXT_COMPANY_ID_6);
        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&segment=A&" + extCompanyIdParams, loginToken);

        // then
        assertResponseEquals("allIssuesByByExtCompanyIdsAndSegmentA.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldFindAllPaginatedIssuesFilteredByExtCompanyIdsAndSegmentB() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        //and
        final String extCompanyIdParams = extCompanyIdParams(EXT_COMPANY_ID_23, EXT_COMPANY_ID_24, EXT_COMPANY_ID_25, EXT_COMPANY_ID_26, EXT_COMPANY_ID_27);

        // when
        final JsonElement response = sendHttpGet("issues?firstResult=0&maxResults=5&segment=B&" + extCompanyIdParams, loginToken);

        // then
        assertResponseEquals("allIssuesByByExtCompanyIdsAndSegmentB.json", response, ISSUE_PAGE);
    }

    @Test
    public void shouldNotUpdateExistingIssueConfigurationWhenMinBalanceStartIsIvalid() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("issue/configuration/updateExistingIssueTypeConfigurationInvalidMinBalanceStart.json");

        // when
        final HttpResponse response = checkHttpPatch("issues/configuration/" + ID_1, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("issue/configuration/issueConfigurationInvalidMinBalanceStart.json", response);
    }
}
