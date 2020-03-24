package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;
import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;
import com.codersteam.alwin.core.api.model.issue.OperatedIssueDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.issue.IssueConfigurationService;
import com.codersteam.alwin.core.api.service.issue.IssueCreateResult;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.codersteam.aida.core.api.model.CompanySegment.A;
import static com.codersteam.alwin.core.api.model.issue.IssueStateDto.CANCELED;
import static com.codersteam.alwin.core.api.model.issue.IssueStateDto.IN_PROGRESS;
import static com.codersteam.alwin.core.api.model.issue.IssueStateDto.WAITING_FOR_TERMINATION;
import static com.codersteam.alwin.core.api.service.issue.TerminateIssueResult.ISSUE_TERMINATION_REQUEST_CREATED;
import static com.codersteam.alwin.core.api.service.issue.TerminateIssueResult.ISSUE_WAS_TERMINATED;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertOk;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.ISSUE_PAGE;
import static com.codersteam.alwin.integration.common.ResponseType.OPERATED_ISSUE;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.CurrencyExchangeRateServiceMock.EUR_EXCHANGE_RATE_BY_DATE;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENT;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENTS;
import static com.codersteam.alwin.integration.mock.SegmentServiceMock.COMPANIES_SEGMENT;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.REMARK;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.COMMENT;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2;
import static com.codersteam.alwin.testdata.BalanceTestData.EUR_EXCHANGE_RATE;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_12;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_18;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_23;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_24;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_25;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_26;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_27;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_3;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_5;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_6;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_8;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.ISSUE_TERMINATION_REQUEST_ID_1;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.ID_1;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.issueTypeConfigurationWithAndOperatorTypeDto;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.modifiedIssueTypeConfigurationWithOperatorTypeDto;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_4;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto15;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto16;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto2;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_2;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10;
import static com.codersteam.alwin.testdata.aida.AidaCustomerInvolvementTestData.customerInvolvementDto1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice-my-work.json", "test-activity.json",
        "test-issue-termination-request.json", "test-issue-type-configuration.json", "test-default-activity-issue-type-2.json"})
public class IssueResourceWriteTestIT extends WriteTestBase {

    private static final char QUOTATION_MARK = '"';

    @EJB
    private IssueConfigurationService issueConfigurationService;

    @EJB
    private ActivityService activityService;

    @EJB
    private OperatorService operatorService;

    @Test
    public void shouldFindWorkForUser() throws Exception {
        // given
        final String loginToken = loggedInWithIssues();

        // and
        final JsonElement request = request("empty.json");

        // when
        final JsonElement response = sendHttpPost("issues/work", request, loginToken);

        // then
        assertResponseEquals("issuesWork.json", response);
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json"})
    public void shouldNotFindWorkForUser() throws Exception {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // and
        final JsonElement request = request("empty.json");

        // when
        final int responseCode = checkHttpPostCode("issues/work", request, loginToken);

        // then
        assertNoContent(responseCode);
    }

    @Test
    public void shouldCreateIssuePhoneDebtCollection1() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_3, aidaCompanyDto10());

        // and
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_3, INVOLVEMENT_2);

        // and
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_3, A);

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_3, dueInvoicesWithCorrections());

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_3, singletonList(customerInvolvementDto1()));

        // and
        final JsonElement request = request("issue/create/issueCreate.json");

        // when
        final IssueCreateResult response = sendHttpPost("issues", request, loginToken, IssueCreateResult.class);

        // then
        assertNotNull(response);
        assertNotNull(response.getIssueId());

        final Page activitiesForIssue = sendHttpGet(String.format("activities/issue/%s?firstResult=0&maxResults=6", response.getIssueId()), loginToken, Page.class);
        assertThat(activitiesForIssue.getValues().size()).isEqualTo(0);
    }

    @Test
    public void shouldCreateIssuePhoneDebtCollection2() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_3, aidaCompanyDto10());

        // and
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_3, INVOLVEMENT_2);

        // and
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_3, A);

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_3, dueInvoicesWithCorrections());

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_3, singletonList(customerInvolvementDto1()));

        // and
        final JsonElement request = request("issue/create/issueCreateWithType2.json");

        // when
        final IssueCreateResult response = sendHttpPost("issues", request, loginToken, IssueCreateResult.class);

        // then
        assertNotNull(response);
        assertNotNull(response.getIssueId());

        final Page activitiesForIssue = sendHttpGet(String.format("activities/issue/%s?firstResult=0&maxResults=6", response.getIssueId()), loginToken, Page.class);
        assertThat(activitiesForIssue.getValues().size()).isEqualTo(2);

    }

    @Test
    public void shouldCreateIssueWithoutAssignmentAndExpirationDate() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_3, aidaCompanyDto10());

        // and
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_3, INVOLVEMENT_2);

        // and
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_3, A);

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_3, dueInvoicesWithCorrections());

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_3, singletonList(customerInvolvementDto1()));

        // and
        final JsonElement request = request("issue/create/issueCreateWithoutAssigneeAndExpirationDate.json");

        // when
        final IssueCreateResult response = sendHttpPost("issues", request, loginToken, IssueCreateResult.class);

        // then
        assertNotNull(response);
        assertNotNull(response.getIssueId());
    }

    @Test
    public void shouldTerminateIssue() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();
        //and
        final List<ActivityDto> allIssueActivitiesBefore = activityService.findAllIssueActivities(ISSUE_ID_1);
        // and
        final JsonElement request = request("issue/terminate/terminateIssue.json");

        // when
        final HttpResponse response = checkHttpPost("issues/terminate/" + ISSUE_ID_1, request, loginToken);

        // then
        assertOk(response);
        assertEquals(QUOTATION_MARK + ISSUE_WAS_TERMINATED.getMessage() + QUOTATION_MARK, responseMessage(response));
        assertIssueState(loginToken, CANCELED);
        assertCommentActivity(allIssueActivitiesBefore);
    }

    private void assertCommentActivity(final List<ActivityDto> allIssueActivitiesBefore) {
        final List<ActivityDto> allIssueActivitiesAfter = activityService.findAllIssueActivities(ISSUE_ID_1);
        assertEquals(1, allIssueActivitiesAfter.size() - allIssueActivitiesBefore.size());
        allIssueActivitiesAfter.removeAll(allIssueActivitiesBefore);
        assertEquals(1, allIssueActivitiesAfter.size());
        final ActivityDto commentActivityDto = allIssueActivitiesAfter.get(0);
        assertEquals(COMMENT.name(), commentActivityDto.getActivityType().getKey());
        assertEquals(1, commentActivityDto.getActivityDetails().size());
        final ActivityDetailDto activityDetailDto = commentActivityDto.getActivityDetails().get(0);
        assertEquals(REMARK.name(), activityDetailDto.getDetailProperty().getKey());
        assertEquals("termination cause 1", activityDetailDto.getValue());
    }

    private void assertIssueState(final String loginToken, final IssueStateDto issueState) throws IOException {
        final JsonElement response = sendHttpGet("issues/" + ISSUE_ID_1, loginToken);
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        final IssueDto issue = builder.create().fromJson(response.toString(), OperatedIssueDto.class).getIssue();
        assertEquals(issueState.getKey(), issue.getIssueState().getKey());
    }

    @Test
    public void shouldCreateTerminateIssueRequest() throws IOException {
        // given
        final String loginToken = loggedInWithIssues();

        //and
        final List<ActivityDto> allIssueActivitiesBefore = activityService.findAllIssueActivities(ISSUE_ID_1);

        // and
        final JsonElement request = request("issue/terminate/terminateIssue.json");

        // when
        final HttpResponse response = checkHttpPost("issues/terminate/" + ISSUE_ID_1, request, loginToken);

        // then
        assertOk(response);
        assertEquals(QUOTATION_MARK + ISSUE_TERMINATION_REQUEST_CREATED.getMessage() + QUOTATION_MARK, responseMessage(response));
        assertIssueState(loginToken, WAITING_FOR_TERMINATION);
        assertCommentActivity(allIssueActivitiesBefore);
    }

    @Test
    public void shouldAcceptTerminationRequest() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // and
        final JsonElement request = request("issue/termination/acceptTerminationRequest.json");

        // when
        final int responseCode = checkHttpPostCode("issues/termination/accept/" + ISSUE_TERMINATION_REQUEST_ID_1, request, loginToken);

        // then
        assertOk(responseCode);
        assertIssueState(loginToken, CANCELED);
    }

    @Test
    public void shouldRejectTerminationRequest() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // and
        final JsonElement request = request("issue/termination/rejectTerminationRequest.json");

        // when
        final int responseCode = checkHttpPostCode("issues/termination/reject/" + ISSUE_TERMINATION_REQUEST_ID_1, request, loginToken);

        // then
        assertOk(responseCode);
        assertIssueState(loginToken, IN_PROGRESS);
    }

    @Test
    public void shouldUpdateIssueInvoicesExclusion() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // and
        final JsonElement request = request("issue/exclusion/updateExclusion.json");

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_3, dueInvoicesWithCorrections());

        // when
        final int responseCode = checkHttpPatchCode("issues/exclusion", request, loginToken);

        // then
        assertAccepted(responseCode);
    }

    @Test
    public void shouldUpdatePriorityForRequestedIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=5", loginToken);
        assertResponseEquals("issue/priority/updateSelected/prioritiesBeforeUpdate.json", currentIssues, ISSUE_PAGE);

        // and
        final JsonElement requestBody = request("issue/priority/updateIssuePriority.json");

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/NORMAL", requestBody, loginToken);

        // then
        assertAccepted(responseCode);
        final JsonElement afterIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=5", loginToken);
        assertResponseEquals("issue/priority/updateSelected/updatedPriorities.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdatePriorityForAllFilteredIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=20", loginToken);
        assertResponseEquals("issue/priority/updateAll/prioritiesBeforeUpdate.json", currentIssues, ISSUE_PAGE);

        // and
        final JsonElement requestBody = new JsonObject();

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/HIGH?updateAll=true", requestBody, loginToken);

        // then
        final JsonElement afterIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=20", loginToken);
        assertAccepted(responseCode);
        assertResponseEquals("issue/priority/updateAll/updatedPriorities.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdatePriorityForIssuesFilteredByExtCompanyIds() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=30", loginToken);
        assertResponseEquals("issue/priority/prioritiesBeforeUpdateFilteredByExtCompanyIds.json", currentIssues, ISSUE_PAGE);

        // and
        final JsonElement requestBody = new JsonObject();

        //and
        final String extCompanyIdParams =
                extCompanyIdParams(EXT_COMPANY_ID_2, EXT_COMPANY_ID_3, EXT_COMPANY_ID_8, EXT_COMPANY_ID_12, EXT_COMPANY_ID_18, EXT_COMPANY_ID_6);

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/HIGH?updateAll=true&" + extCompanyIdParams, requestBody, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues/managed?firstResult=0&maxResults=30", loginToken);
        assertResponseEquals("issue/priority/prioritiesAfterUpdateFilteredByExtCompanyIds.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdatePriorityForIssuesFilteredByIssueTypeId() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesBeforeUpdate.json", currentIssues, ISSUE_PAGE);

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/HIGH?updateAll=true&issueTypeId=1", loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesAfterUpdateFilteredByIssueTypeId.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdatePriorityForIssuesFilteredBySegmentA() throws Exception {

        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesBeforeUpdate.json", currentIssues, ISSUE_PAGE);

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/HIGH?updateAll=true&segment=A", loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesAfterUpdateFilteredBySegmentA.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdatePriorityForIssuesFilteredBySegmentB() throws Exception {

        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesBeforeUpdate.json", currentIssues, ISSUE_PAGE);

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/NORMAL?updateAll=true&segment=B", loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesAfterUpdateFilteredBySegmentB.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdatePriorityForIssuesFilteredByExtCompanyIdsAndSegmentA() throws Exception {

        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesBeforeUpdate.json", currentIssues, ISSUE_PAGE);

        //and
        final String extCompanyIdParams =
                extCompanyIdParams(EXT_COMPANY_ID_2, EXT_COMPANY_ID_3, EXT_COMPANY_ID_8, EXT_COMPANY_ID_12, EXT_COMPANY_ID_18, EXT_COMPANY_ID_6);

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/HIGH?updateAll=true&segment=A&" + extCompanyIdParams, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesAfterUpdateFilteredByExtCompanyIdsAndSegmentA.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdatePriorityForIssuesFilteredByExtCompanyIdsAndSegmentB() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesBeforeUpdate.json", currentIssues, ISSUE_PAGE);

        //and
        final String extCompanyIdParams = extCompanyIdParams(EXT_COMPANY_ID_23, EXT_COMPANY_ID_24, EXT_COMPANY_ID_25, EXT_COMPANY_ID_26, EXT_COMPANY_ID_27);

        // when
        final int responseCode = checkHttpPatchCode("issues/priority/NORMAL?updateAll=true&segment=B&" + extCompanyIdParams, loginToken);

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement afterIssues = sendHttpGet("issues?firstResult=0&maxResults=40", loginToken);
        assertResponseEquals("issue/priority/prioritiesAfterUpdateFilteredByExtCompanyIdsAndSegmentB.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldAssignOnlyNotYetAssignTags() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement issueResponseBeforeUpdate = sendHttpGet("issues/" + ISSUE_ID_2, loginToken);

        // and
        assertResponseEquals("findIssueById2.json", issueResponseBeforeUpdate, OPERATED_ISSUE);

        // and
        final JsonElement request = request("tag/assignTags.json");

        // when
        final int responseCode = checkHttpPatchCode("issues/" + ISSUE_ID_2 + "/tags", request, loginToken);
        commitTrx();

        // then
        assertAccepted(responseCode);

        // and
        final JsonElement issueResponseAfterUpdate = sendHttpGet("issues/" + ISSUE_ID_2, loginToken);

        // and
        assertResponseEquals("findUpdatedIssueById2.json", issueResponseAfterUpdate, OPERATED_ISSUE);
    }

    @Test
    public void shouldUpdateTagsForAllFilteredIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues/managed?operatorId=1&firstResult=0&maxResults=5", loginToken);
        assertResponseEquals("issue/tag/beforeUpdateTags.json", currentIssues, ISSUE_PAGE);

        // and
        final JsonElement request = request("tag/assignTagsToIssues.json");

        // when
        final int responseCode = checkHttpPatchCode("issues/tags?operatorId=1&updateAll=true", request, loginToken);

        // then
        final JsonElement afterIssues = sendHttpGet("issues/managed?operatorId=1&firstResult=0&maxResults=5", loginToken);
        assertAccepted(responseCode);
        assertResponseEquals("issue/tag/updatedTags.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdateTagsForGivenIssues() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement currentIssues = sendHttpGet("issues/managed?operatorId=1&firstResult=0&maxResults=5", loginToken);
        assertResponseEquals("issue/tag/beforeUpdateSelectedTags.json", currentIssues, ISSUE_PAGE);

        // and
        final JsonElement request = request("tag/assignTagsToGivenIssues.json");

        // when
        final int responseCode = checkHttpPatchCode("issues/tags?operatorId=1", request, loginToken);

        // then
        final JsonElement afterIssues = sendHttpGet("issues/managed?operatorId=1&firstResult=0&maxResults=5", loginToken);
        assertAccepted(responseCode);
        assertResponseEquals("issue/tag/updatedSelectedTags.json", afterIssues, ISSUE_PAGE);
    }

    @Test
    public void shouldUpdateExistingConfiguration() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<IssueTypeConfigurationDto> configurations = issueConfigurationService.findAllIssueTypeConfigurations();

        // and
        assertManagedOperators();

        // and
        assertThat(issueTypeConfigurationWithAndOperatorTypeDto()).isEqualToComparingFieldByFieldRecursively(configurations.get(0));

        // and
        final JsonElement request = request("issue/configuration/updateExistingIssueTypeConfiguration.json");

        // when
        final int responseCode = checkHttpPatchCode("issues/configuration/" + ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<IssueTypeConfigurationDto> updatedConfigurations = issueConfigurationService.findAllIssueTypeConfigurations();

        // then
        assertAccepted(responseCode);
        assertThat(updatedConfigurations.get(0)).isEqualToComparingFieldByFieldRecursively(modifiedIssueTypeConfigurationWithOperatorTypeDto());
        assertManagedOperators();
    }

    private void assertManagedOperators() {
        final List<OperatorDto> managedOperatorsBefore = operatorService.findManagedOperators(OPERATOR_ID_4, null);
        assertEquals(3L, managedOperatorsBefore.size());
        assertThat(managedOperatorsBefore).isEqualToComparingFieldByFieldRecursively(asList(testOperatorDto2(), testOperatorDto15(), testOperatorDto16()));
    }

    @Test
    public void shouldBeUnauthorizedToGetOperatedIssueWithBalanceUpdate() throws Exception {
        // when
        final int responseCode = checkHttpPatchCode("issues/" + ISSUE_ID_1, null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldUpdateBalanceAndReturnOperatedIssue() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2018-08-23";

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_5, dueInvoicesWithCorrections());

        // and
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), EUR_EXCHANGE_RATE);

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_5, singletonList(customerInvolvementDto1()));

        // when
        final JsonElement response = sendHttpPatch("issues/" + ISSUE_ID_2, loginToken);

        // then
        assertResponseEquals("issue/operatedIssueWIthUpdatedBalance.json", response, OPERATED_ISSUE);
    }

    @Test
    public void shouldFindEditableIssueWithUpdatedBalanceById() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and current date after issue 1 balance update (2017-07-10)
        CURRENT_DATE = "2017-07-11";

        // and
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), new BigDecimal("4.12"));

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, singletonList(customerInvolvementDto1()));

        // when
        final JsonElement response = sendHttpPatch("issues/" + ISSUE_ID_1, loginToken);

        // then
        assertResponseEquals("issue/findIssueWithUpdatedBalanceById.json", response, OPERATED_ISSUE);
    }

    @Test
    public void shouldFindNotEditableIssueWithUpdatedBalanceById() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // and current date after issue 1 balance update (2017-07-10)
        CURRENT_DATE = "2017-07-11";

        // and
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), new BigDecimal("4.12"));

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, singletonList(customerInvolvementDto1()));

        // when
        final JsonElement response = sendHttpPatch("issues/" + ISSUE_ID_1, loginToken);

        // then
        assertResponseEquals("issue/findNotEditableIssueWithUpdatedBalanceById.json", response, OPERATED_ISSUE);
    }
}
