package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.mock.ContractSubjectServiceMock.SUBJECTS_BY_CONTRACT_NO;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_21;
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.NON_EXISTING_AIDA_COMPANY_ID;
import static com.codersteam.alwin.testdata.aida.AidaContractSubjectTestData.TEST_CONTRACT_SUBJECT_DTO_1090;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_1;
import static java.util.Collections.singletonList;

/**
 * @author Tomasz Sliwinski
 */
public class ContractResourceTestIT extends ReadTestBase {

    @Before
    public void setup() {
        CURRENT_DATE = "2024-07-12";
        SUBJECTS_BY_CONTRACT_NO.put(CONTRACT_NO_1, singletonList(TEST_CONTRACT_SUBJECT_DTO_1090));
    }

    @Test
    public void shouldBeUnauthorizedToGetCompanyContracts() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("contracts/company/" + COMPANY_ID_10);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldReturnEmptyContractsForUnknownCompanyId() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contracts/company/" + NON_EXISTING_AIDA_COMPANY_ID, loginToken);

        // then
        assertEmptyCollectionResponse(response);
    }

    @Test
    public void shouldReturnContractsWithSubjectsAndSchedules() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contracts/company/" + COMPANY_ID_10, loginToken);

        // then
        assertResponseEquals("contract/companyContracts.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetSimpleCompanyContracts() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("contracts/simple/company/" + COMPANY_ID_10);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldReturnContractsByCompanyId() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2017-07-25";

        // when
        final JsonElement response = sendHttpGet("contracts/simple/company/" + COMPANY_ID_10, loginToken);

        // then
        assertResponseEquals("contract/simpleCompanyContracts.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetSimpleFormalDebtCollectionCompanyContracts() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("contracts/simple/company/" + COMPANY_ID_10 + "/formalDebtCollection");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldReturnFormalDebtCollectionContractsByCompanyId() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        CURRENT_DATE = "2017-07-25";

        // when
        final JsonElement response = sendHttpGet("contracts/simple/company/" + COMPANY_ID_10 + "/formalDebtCollection", loginToken);

        // then
        assertResponseEquals("contract/formalDebtCollection/simpleCompanyContracts.json", response);
    }

    @Test
    public void shouldBeAuthorizedToGetIssueContractFinancialSummaries() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("contracts/issue/" + ISSUE_ID_21 + "/summary?notPaidOnly=false");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldReturnAllIssueContractFinancialSummaries() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contracts/issue/" + ISSUE_ID_21 + "/summary", loginToken);

        // then
        assertResponseEquals("contract/allIssueContractFinancialSummaries.json", response);
    }

    @Test
    public void shouldReturnOnlyNotPaidIssueContractFinancialSummaries() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contracts/issue/" + ISSUE_ID_21 + "/summary?notPaidOnly=true", loginToken);

        // then
        assertResponseEquals("contract/notPaidIssueContractFinancialSummaries.json", response);
    }

    @Test
    public void shouldReturnOnlyIssueSubjectContractFinancialSummaries() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contracts/issue/" + ISSUE_ID_21 + "/summary?issueSubjectOnly=true", loginToken);

        // then
        assertResponseEquals("contract/issueSubjectContractFinancialSummaries.json", response);
    }

    @Test
    public void shouldReturnEmptyIssueContractFinancialSummariesForNotExistingIssueId() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contracts/issue/" + NOT_EXISTING_ISSUE_ID + "/summary?notPaidOnly=false", loginToken);

        // then
        assertEmptyCollectionResponse(response);
    }

    @Test
    public void shouldReturnContractSubjectsByContractNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("contracts/subjects?contractNo=" + CONTRACT_NO_1, loginToken);

        // then
        assertResponseEquals("contract/contractSubjects.json", response);
    }

    @Test
    public void shouldNotReturnContractSubjectsWithoutContractNo() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("contracts/subjects", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("contract/missingContractNoForSubjects.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetContractSubjectsByContractNo() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("contracts/subjects?contractNo=" + CONTRACT_NO_1);

        // then
        assertUnauthorized(responseCode);
    }
}
