package com.codersteam.alwin.integration.read;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.sort.DemandForPaymentSortField;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.DEMANDS_FOR_PAYMENT_PAGE;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_4;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_ID_1;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_ID_3;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_ID_4;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_1;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.NON_EXISTING_DEMAND_FOR_PAYMENT_ID_1;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_2;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_15;

/**
 * @author Piotr Naroznik
 */
public class DemandForPaymentResourceReadTestIT extends ReadTestBase {

    @Test
    public void shouldNotMarkDemandForPaymentWhenNotExists() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPatch("demands/" + NON_EXISTING_DEMAND_FOR_PAYMENT_ID_1 + "/mark", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("demand/demandForPaymentNotExistValidationError.json", response);
    }

    @Test
    public void shouldNotMarkDemandForPaymentWhenStateIsNotNew() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPatch("demands/" + DEMAND_FOR_PAYMENT_ID_4 + "/mark", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("demand/demandForPaymentNotInNewStateValidationError.json", response);
    }

    @Test
    public void shouldNotReturnDemandsForPaymentForInvalidStateValue() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("demands?state=qwerty"
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("demand/demandForPaymentWithInvalidStateValueValidationError.json", response);
    }

    @Test
    public void shouldReturnDemandsForPaymentPageBothManualAndAutomaticallyCreatedAndBothAbortedAndNot() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?state=" + DemandForPaymentState.ISSUED, loginToken);

        // then
        assertResponseEquals("demand/demandsForPaymentPageBothManualAndAutomaticallyCreatedAndBothAbortedAndNot.json", response);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByNewAndWaitingState() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?state=" + DemandForPaymentState.NEW + "&state=" + DemandForPaymentState.WAITING, loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByNewAndWaitingState.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByIssuedState() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?state=" + DemandForPaymentState.ISSUED, loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByIssuedState.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByRejectedState() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?state=" + DemandForPaymentState.NEW + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByRejectedState.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfManuallyCreatedDemandsForPaymentFilteredByState() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?state=" + DemandForPaymentState.NEW + "&createdManually=true&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfManuallyCreatedDemandsForPaymentFilteredByState.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByChargeInvoiceNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&chargeInvoiceNo=" + DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_4
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByChargeInvoiceNo.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByCompanyName() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&companyName=Firmmy"
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByCompanyName.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByDueDate() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&startDueDate=2017-07-01&endDueDate=2017-07-29"
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByDueDate.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByExtCompanyId() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&extCompanyId=" + EXT_COMPANY_ID_2
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByExtCompanyId.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByInitialInvoiceNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&initialInvoiceNo=" + DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_1
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByInitialInvoiceNo.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByIssueDate() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&startIssueDate=2017-08-01&endIssueDate=2017-08-07"
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByIssueDate.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredBySegment() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&segment=" + DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_SEGMENT_2
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredBySegment.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedBySegment() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=-" + DemandForPaymentSortField.SEGMENT
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedBySegment.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedByIssueDate() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=-" + DemandForPaymentSortField.ISSUE_DATE
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedByIssueDate.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedByInitialInvoiceNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=" + DemandForPaymentSortField.INITIAL_INVOICE_NO
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedByInitialInvoiceNo.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedByExtCompanyId() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=" + DemandForPaymentSortField.EXT_COMPANY_ID
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedByExtCompanyId.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedByDueDate() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=" + DemandForPaymentSortField.DUE_DATE
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedByDueDate.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedByContractNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=-" + DemandForPaymentSortField.CONTRACT_NUMBER
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedByContractNo.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedByCompanyName() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=-" + DemandForPaymentSortField.COMPANY_NAME
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedByCompanyName.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentSortedByChargeInvoiceNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?firstResult=0&maxResults=5&sort=-" + DemandForPaymentSortField.CHARGE_INVOICE_NO
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentSortedByChargeInvoiceNo.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByContractNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?contractNo=" + CONTRACT_NUMBER_INVOICE_15
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByContractNo.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByType() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?type=" + FIRST + "&firstResult=0&maxResults=2"
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByType.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldFindPageOfDemandsForPaymentFilteredByStateAndContractNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("demands?state=" + DemandForPaymentState.NEW + "&contractNo=" + CONTRACT_NUMBER_INVOICE_15
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByStateAndContractNo.json", response, DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldNotFindPageOfDemandsForPaymentFilteredByInvalidType() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("demands?type=qwerty"
                + "&createdManually=false&aborted=false", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("demand/demandForPaymentInvalidTypeValidationError.json", response);
    }

    @Test
    public void shouldNotProcessDemandsWithNotExistingDemandForPaymentOnTheList() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("demand/processNotExistingDemandForPayment.json");

        // when
        final HttpResponse response = checkHttpPost("demands/process", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("demand/processNotExistingDemandForPayment.json", response);
    }

    @Test
    public void shouldNotProcessAlreadyIssuedDemandsOnTheList() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("demand/processAlreadyIssuedDemandForPayment.json");

        // when
        final HttpResponse response = checkHttpPost("demands/process", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("demand/processAlreadyIssuedDemandForPayment.json", response);
    }

    // ---------------------------------------------------------------------------------------
    // authorization checks
    // ---------------------------------------------------------------------------------------

    @Test
    public void shouldNotProcessDemandsForPaymentForNotAuthenticatedUser() throws IOException {
        // given
        final JsonElement request = request("demand/processNotExistingDemandForPayment.json");

        // when
        final int responseCode = checkHttpPostCode("demands/process", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotProcessDemandsForPaymentForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // and
        final JsonElement request = request("demand/processNotExistingDemandForPayment.json");

        // when
        final int responseCode = checkHttpPostCode("demands/process", request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotMarkDemandForPaymentForNotAuthenticatedUser() throws IOException {
        // when
        final int responseCode = checkHttpPatchCode("demands/" + DEMAND_FOR_PAYMENT_ID_1 + "/mark", null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotMarkDemandForPaymentForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // when
        final int responseCode = checkHttpPatchCode("demands/" + DEMAND_FOR_PAYMENT_ID_1 + "/mark", loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotReturnDemandsForPaymentForNotAuthenticatedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("demands", null);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnDemandsForPaymentForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollector();

        // when
        final int responseCode = checkHttpGetCode("demands", loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotCreateDemandsForPaymentForNotAuthenticatedUser() throws IOException {
        // given
        final JsonElement request = request("demand/createDemandsForPayment.json");

        // when
        final int responseCode = checkHttpPostCode("demands", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotCreateDemandsForPaymentForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInFieldDebtCollectorManager();

        // and
        final JsonElement request = request("demand/createDemandsForPayment.json");

        // when
        final int responseCode = checkHttpPostCode("demands", request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

    public void shouldReturnCorrectResponseForUpdateIssuedDemandForPaymentToCancel() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("demand/updateDemandForPaymentStateToCancel.json");

        // when
        final HttpResponse response = sendHttpPostForResponse("demands/cancel/" + DEMAND_FOR_PAYMENT_ID_3, request, loginToken);

        // then
        assertNoContent(response);
    }

    @Test
    public void shouldReturnCSVData() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final String response = sendHttpGetForTextResponse("demands/report", loginToken);

        // then
        assertResponseEquals("demand/CSVData.txt", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetCSVData() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("demands/report");

        // then
        assertUnauthorized(responseCode);
    }
}
