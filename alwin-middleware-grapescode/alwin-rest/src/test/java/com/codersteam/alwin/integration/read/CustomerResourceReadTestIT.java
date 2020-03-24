package com.codersteam.alwin.integration.read;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.alwin.testdata.ContractOutOfServiceTestData;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.ContractServiceMock.CONTRACTS_BY_CONTRACT_NO;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.ID_1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.NOT_EXISTING_ID;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.simpleAidaContractDto1;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author Michal Horowic
 */
public class CustomerResourceReadTestIT extends ReadTestBase {

    @Test
    public void shouldBeUnauthorizedToFindAllSegments() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("customers/segments");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldFindAllSegments() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("customers/segments", loginToken);

        // then
        assertResponseEquals("customer/segments.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToBlockCustomer() throws Exception {
        // given
        final JsonElement request = request("customer/block/existingCustomer.json");

        // when
        final int responseCode = checkHttpPostCode("customers/block/" + COMPANY_ID_10, request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotBlockCustomerIfValidationFailedForMissingReason() throws IOException {
        // given
        final JsonElement request = request("customer/block/missingReason.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerMissingReason.json", response);
    }

    @Test
    public void shouldNotBlockCustomerIfValidationFailedForWrongBlockingPeriod() throws IOException {
        // given
        final JsonElement request = request("customer/block/wrongPeriod.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerWrongBlockingPeriod.json", response);
    }

    @Test
    public void shouldNotBlockCustomerIfValidationFailedForNotExistingCompany() throws IOException {
        // given
        final JsonElement request = request("customer/block/notExistingCompanyId.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + NON_EXISTING_COMPANY_ID, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerNotExistingCompany.json", response);
    }

    @Test
    public void shouldNotBlockCustomerIfValidationFailedForEmptyInput() throws IOException {
        // given
        final JsonElement request = request("empty.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerEmptyInput.json", response);
    }


    @Test
    public void shouldBeUnauthorizedToGetCustomersOutOfService() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("customers/blocked/" + COMPANY_ID_10);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnCustomersOutOfServiceIfValidationFailedForNotExistingCompany() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("customers/blocked/" + NON_EXISTING_COMPANY_ID, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/customersOutOfServiceNotExistingCompany.json", response);
    }

    @Test
    public void shouldReturnOnlyActiveCustomersOutOfService() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // when
        final JsonElement response = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1 + "?active=true", loginToken);

        // then
        assertResponseEquals("customer/blocked/activeCustomersOutOfService.json", response);
    }

    @Test
    public void shouldReturnCustomersOutOfService() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2020-07-16";

        // when
        final JsonElement response = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1, loginToken);

        // then
        assertResponseEquals("customer/blocked/allCustomersOutOfService.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToUpdateCustomerOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/updateCustomerOutOfService.json");

        // and
        final String loginToken = EMPTY;

        // when
        final int responseCode = checkHttpPatchCode("customers/block/" + ID_1, request, loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldBeUnauthorizedToUpdateContractOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/updateContractOutOfService.json");

        // and
        final String loginToken = EMPTY;

        // when
        final int responseCode = checkHttpPatchCode("customers/block/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contract/" + ContractOutOfServiceTestData.ID_1, request, loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotUpdateContractOutOfServiceForNotExistingContractOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/updateContractOutOfService.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // when
        final HttpResponse response = checkHttpPatch("customers/block/" + EXT_COMPANY_ID_1 + "/contract/" + NOT_EXISTING_ID, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/updateContractOutOfServiceForNotExistingContractOutOfService.json", response);
    }

    @Test
    public void shouldNotUpdateContractOutOfServiceForNotExistingCustomer() throws Exception {
        // given
        final JsonElement request = request("customer/block/updateContractOutOfService.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPatch("customers/block/" + NOT_EXISTING_ID + "/contract/" + ContractOutOfServiceTestData.ID_1, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/updateContractOutOfServiceForNotExistingCustomer.json", response);
    }

    @Test
    public void shouldNotUpdateCustomerOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/updateCustomerOutOfService.json");

        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPatch("customers/block/" + NOT_EXISTING_ID, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/updateCustomerOutOfService.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToBlockCustomerContract() throws Exception {
        // given
        final JsonElement request = request("customer/block/existingCustomer.json");

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final int responseCode = checkHttpPostCode("customers/block/" + COMPANY_ID_10 + "/contract?contractNo=" + contractNo, request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotBlockCustomerContractIfValidationFailedForMissingReason() throws IOException {
        // given
        final JsonElement request = request("customer/block/missingReason.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/contract?contractNo=" + contractNo, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerContractIMissingReason.json", response);
    }

    @Test
    public void shouldNotBlockCustomerContractIfValidationFailedForWrongBlockingPeriod() throws IOException {
        // given
        final JsonElement request = request("customer/block/wrongPeriod.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/contract?contractNo=" + contractNo, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerContractWrongBlockingPeriod.json", response);
    }

    @Test
    public void shouldNotBlockCustomerContractIfValidationFailedForNotExistingCompany() throws IOException {
        // given
        final JsonElement request = request("customer/block/notExistingCompanyId.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + NON_EXISTING_COMPANY_ID + "/contract?contractNo=" + contractNo, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerContractNotExistingCompany.json", response);
    }

    @Test
    public void shouldNotBlockCustomerContractIfValidationFailedForEmptyInput() throws IOException {
        // given
        final JsonElement request = request("empty.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/contract?contractNo=" + contractNo, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerContractEmptyInput.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetCustomerContractsOutOfService() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("customers/blocked/" + COMPANY_ID_10 + "/contracts");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnCustomerContractsOutOfServiceIfValidationFailedForNotExistingCompany() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("customers/blocked/" + NON_EXISTING_COMPANY_ID + "/contracts", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/customerContractsOutOfServiceNotExistingCompany.json", response);
    }

    @Test
    public void shouldReturnCustomerContractsOutOfService() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        COMPANIES_BY_COMPANY_ID.put(ContractOutOfServiceTestData.EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // when
        final JsonElement response = sendHttpGet("customers/blocked/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contracts", loginToken);

        // then
        assertResponseEquals("customer/blocked/customerContractsOutOfService.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToDeleteContractOutOfService() throws Exception {
        // given
        final String loginToken = EMPTY;

        // when
        final int responseCode = checkHttpDeleteCode("customers/block/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contract/" + ContractOutOfServiceTestData.ID_1, loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotDeleteContractOutOfServiceForNotExistingContractOutOfService() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + EXT_COMPANY_ID_1 + "/contract/" + NOT_EXISTING_ID, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/deleteContractOutOfServiceForNotExistingContractOutOfService.json", response);
    }

    @Test
    public void shouldNotDeleteContractOutOfServiceForNotExistingCustomer() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + NOT_EXISTING_ID + "/contract/" + ContractOutOfServiceTestData.ID_1, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/deleteContractOutOfServiceForNotExistingCustomer.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToDeleteCustomerOutOfService() throws Exception {
        // given
        final String loginToken = EMPTY;

        // when
        final int responseCode = checkHttpDeleteCode("customers/block/" + ID_1, loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotDeleteCustomerOutOfService() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + NOT_EXISTING_ID, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/deleteCustomerOutOfService.json", response);
    }

    @Test
    public void shouldBeForbiddenToUpdateCustomerAccountManager() throws Exception {
        // given
        final String loginToken = loggedInWithIssues();

        // when
        final int responseCode = checkHttpPatchCode("customers/" + ID_1 + "/accountManager", loginToken);

        // then
        assertForbidden(responseCode);
    }

    @Test
    public void shouldNotUpdateCustomerAccountManager() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPatch("customers/" + NOT_EXISTING_ID + "/accountManager", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/accountManager/customerNotExists.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToFindAllReasonTypes() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("customers/reasonTypes");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldFindAllReasonTypes() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("customers/reasonTypes", loginToken);

        // then
        assertResponseEquals("customer/reasonTypes.json", response);
    }
}


