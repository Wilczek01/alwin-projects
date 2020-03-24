package com.codersteam.alwin.integration.read;

import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseType.TERMINATION_CONTRACT_PAGE;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.COMPANY_ID_132;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.COMPANY_NAME_132;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.CONTRACT_NUMBER_103;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.CONTRACT_NUMBER_132;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.GENERATED_BY_132;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_132;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.TYPE_132;

/**
 * @author Dariusz Rackowski
 */
public class ContractTerminationResourceTestIT extends ReadTestBase {

    @Before
    public void setup() {
        CURRENT_DATE = "2016-03-13";
    }

    @Test
    public void shouldReturnContractTerminationsToOperate() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations/open", loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsToOperate.json", response);
    }

    @Test
    public void shouldReturnOnePageOfIssuedContractTerminations() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?state=ISSUED&firstResult=1&maxResults=4", loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsIssued.json", response);
    }

    @Test
    public void shouldReturnOnePageOfIssuedContractTerminationsSortedByTerminationDateAscending() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?state=ISSUED&firstResult=0&maxResults=4&sort=TERMINATION_DATE", loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsIssuedSortedByTerminationDate.json", response, TERMINATION_CONTRACT_PAGE);
    }

    @Test
    public void shouldReturnOnePageOfIssuedContractTerminationsFilteredByContractNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?state=ISSUED&firstResult=0&maxResults=5&contractNo=" + CONTRACT_NUMBER_103, loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsIssuedFilteredByContractNo.json", response, TERMINATION_CONTRACT_PAGE);
    }

    @Test
    public void shouldReturnOnePageOfIssuedContractTerminationsFilteredByAllFields() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?firstResult=0&maxResults=5" +
                        "&state=" + ContractTerminationState.ISSUED +
                        "&contractNo=" + CONTRACT_NUMBER_132 +
                        "&type=" + TYPE_132 +
                        "&extCompanyId=" + COMPANY_ID_132 +
                        "&startTerminationDate=2016-04-10" +
                        "&endTerminationDate=2016-04-18" +
                        "&companyName=" + COMPANY_NAME_132.substring(1, 4) +
                        "&generatedBy=" + GENERATED_BY_132.substring(7, 9) +
                        "&resumptionCostMin=300" +
                        "&resumptionCostMax=600" +
                        "&startDueDateInDemandForPayment=2016-03-20" +
                        "&endDueDateInDemandForPayment=2016-03-30" +
                        "&amountInDemandForPaymentPLNMin=200" +
                        "&amountInDemandForPaymentPLNMax=400" +
                        "&amountInDemandForPaymentEURMin=200" +
                        "&amountInDemandForPaymentEURMax=400" +
                        "&totalPaymentsSinceDemandForPaymentPLNMin=900" +
                        "&totalPaymentsSinceDemandForPaymentPLNMax=1100" +
                        "&totalPaymentsSinceDemandForPaymentEURMin=900" +
                        "&totalPaymentsSinceDemandForPaymentEURMax=1100" +
                        "&initialInvoiceNo=" + INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_132.substring(1, 4)
                , loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsIssuedFilteredByAllFields.json", response, TERMINATION_CONTRACT_PAGE);
    }

    @Test
    public void shouldReturnOnePageOfIssuedContractTerminationsSortedByTypeDescending() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?state=ISSUED&firstResult=0&maxResults=4&sort=-TYPE", loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsIssuedSortedByTypeDesc.json", response, TERMINATION_CONTRACT_PAGE);
    }

    @Test
    public void shouldReturnOnePageOfIssuedContractTerminationsSortedByCompanyNameDescending() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?state=ISSUED&firstResult=0&maxResults=4&sort=-COMPANY_NAME", loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsIssuedSortedByCompanyNameDesc.json", response, TERMINATION_CONTRACT_PAGE);
    }

    @Test
    public void shouldReturnOnePageOfRejectedContractTerminations() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?state=REJECTED&firstResult=0&maxResults=2", loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsRejected.json", response);
    }

    @Test
    public void shouldReturnOnePageOfActivatedContractTerminations() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contractTerminations?state=CONTRACT_ACTIVATED&firstResult=0&maxResults=2", loginToken);

        // then
        assertResponseEquals("termination/contractTerminationsContractActivated.json", response);
    }

    @Test
    public void shouldReturnCorrectResponseForActivateContractTermination() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement request = request("termination/activateContractTermination.json");

        // when
        final HttpResponse response = sendHttpPostForResponse("contractTerminations/101/activate", request, loginToken);

        // then
        assertNoContent(response);
    }

}
