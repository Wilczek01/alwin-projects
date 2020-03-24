package com.codersteam.alwin.integration.read;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.alwin.testdata.ContractOutOfServiceTestData;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
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
 * @author Piotr Naroznik
 */
public class FormalOutOfServiceCustomerResourceReadTestIT extends ReadTestBase {


    @Test
    public void shouldBeUnauthorizedToGetFormalDebtCollectionCustomersOutOfService() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("customers/blocked/" + COMPANY_ID_10 + "/formalDebtCollection");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnFormalDebtCollectionCustomersOutOfServiceIfValidationFailedForNotExistingCompany() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("customers/blocked/" + NON_EXISTING_COMPANY_ID + "/formalDebtCollection", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/customersOutOfServiceNotExistingCompany.json", response);
    }

    @Test
    public void shouldReturnOnlyActiveFormalDebtCollectionCustomersOutOfService() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // when
        final JsonElement response = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1 + "/formalDebtCollection?active=true", loginToken);

        // then
        assertResponseEquals("customer/blocked/formalDebtCollection/activeCustomersOutOfService.json", response);
    }

    @Test
    public void shouldReturnFormalDebtCollectionCustomersOutOfService() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2020-07-16";

        // when
        final JsonElement response = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1 + "/formalDebtCollection", loginToken);

        // then
        assertResponseEquals("customer/blocked/formalDebtCollection/allCustomersOutOfService.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToGetFormalDebtCollectionContractsOutOfService() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("customers/blocked/" + COMPANY_ID_10 + "/contracts/formalDebtCollection");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnFormalDebtCollectionContractsOutOfServiceIfValidationFailedForNotExistingCompany() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpGet("customers/blocked/" + NON_EXISTING_COMPANY_ID + "/contracts/formalDebtCollection", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/customerContractsOutOfServiceNotExistingCompany.json", response);
    }

    @Test
    public void shouldReturnFormalDebtCollectionContractsOutOfService() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        COMPANIES_BY_COMPANY_ID.put(ContractOutOfServiceTestData.EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // when
        final JsonElement response = sendHttpGet("customers/blocked/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contracts/formalDebtCollection", loginToken);

        // then
        assertResponseEquals("customer/blocked/formalDebtCollection/customerContractsOutOfService.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToBlockFormalDebtCollectionCustomer() throws Exception {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/existingCustomer.json");

        // when
        final int responseCode = checkHttpPostCode("customers/block/" + COMPANY_ID_10 + "/formalDebtCollection", request);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionCustomerIfValidationFailedForMissingReason() throws IOException {
        // given
        final JsonElement request = request("customer/block/missingReason.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerMissingReason.json", response);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionCustomerIfValidationFailedForWrongBlockingPeriod() throws IOException {
        // given
        final JsonElement request = request("customer/block/wrongPeriod.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerWrongBlockingPeriod.json", response);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionCustomerIfValidationFailedForNotExistingCompany() throws IOException {
        // given
        final JsonElement request = request("customer/block/notExistingCompanyId.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + NON_EXISTING_COMPANY_ID + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerNotExistingCompany.json", response);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionCustomerIfValidationFailedForEmptyInput() throws IOException {
        // given
        final JsonElement request = request("empty.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerEmptyInput.json", response);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionCustomerIfValidationFailedForMissingReasonType() throws IOException {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/missingReasonType.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + EXT_COMPANY_ID_1 + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/formalDebtCollection/blockMissingReasonType.json", response);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionCustomerIfValidationFailedForMissingDemandForPaymentType() throws IOException {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/missingDemandForPaymentType.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + EXT_COMPANY_ID_1 + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/formalDebtCollection/blockMissingDemandForPaymentType.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToBlockCustomerContract() throws Exception {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/existingCustomer.json");

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final int responseCode = checkHttpPostCode("customers/block/" + COMPANY_ID_10 + "/contract/formalDebtCollection?contractNo=" + contractNo, request);

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
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/contract/formalDebtCollection?contractNo=" + contractNo, request, loginToken);

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
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/contract/formalDebtCollection?contractNo=" + contractNo, request, loginToken);

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
        final HttpResponse response = checkHttpPost("customers/block/" + NON_EXISTING_COMPANY_ID + "/contract/formalDebtCollection?contractNo=" + contractNo, request, loginToken);

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
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/contract/formalDebtCollection?contractNo=" + contractNo, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/blockCustomerContractEmptyInput.json", response);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionContractIfValidationFailedForMissingReasonType() throws IOException {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/missingReasonType.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + EXT_COMPANY_ID_1 + "/contract/formalDebtCollection?contractNo=" + contractNo, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/formalDebtCollection/blockMissingReasonType.json", response);
    }

    @Test
    public void shouldNotBlockFormalDebtCollectionContractIfValidationFailedForMissingDemandForPaymentType() throws IOException {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/missingDemandForPaymentType.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + EXT_COMPANY_ID_1 + "/contract/formalDebtCollection?contractNo=" + contractNo, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/formalDebtCollection/blockMissingDemandForPaymentType.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToDeleteFormalDebtCollectionCustomerOutOfService() throws Exception {
        // given
        final String loginToken = EMPTY;

        // when
        final int responseCode = checkHttpDeleteCode("customers/block/" + ID_1 + "/formalDebtCollection", loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotDeleteFormalDebtCollectionCustomerOutOfService() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + NOT_EXISTING_ID + "/formalDebtCollection", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/deleteCustomerOutOfService.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToDeleteFormalDebtCollectionContractOutOfService() throws Exception {
        // given
        final String loginToken = EMPTY;

        // when
        final int responseCode =
                checkHttpDeleteCode("customers/block/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contract/" + ContractOutOfServiceTestData.ID_1 + "/formalDebtCollection", loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotDeleteFormalDebtCollectionContractOutOfServiceForNotExistingContractOutOfService() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + EXT_COMPANY_ID_1 + "/contract/" + NOT_EXISTING_ID + "/formalDebtCollection", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/deleteContractOutOfServiceForNotExistingContractOutOfService.json", response);
    }

    @Test
    public void shouldNotDeleteFormalDebtCollectionContractOutOfServiceForNotExistingCustomer() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + NOT_EXISTING_ID + "/contract/" + ContractOutOfServiceTestData.ID_1 + "/formalDebtCollection", loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/deleteContractOutOfServiceForNotExistingCustomer.json", response);
    }

    @Test
    public void shouldBeUnauthorizedToUpdateFormalDebtCollectionCustomerOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/updateCustomerOutOfService.json");

        // and
        final String loginToken = EMPTY;

        // when
        final int responseCode = checkHttpPatchCode("customers/block/" + ID_1 + "/formalDebtCollection", request, loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldBeUnauthorizedToUpdateFormalDebtCollectionContractOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/updateContractOutOfService.json");

        // and
        final String loginToken = EMPTY;

        // when
        final int responseCode = checkHttpPatchCode("customers/block/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contract/" + ContractOutOfServiceTestData.ID_1 + "/formalDebtCollection", request, loginToken);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotUpdateFormalDebtCollectionContractOutOfServiceForNotExistingContractOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/updateContractOutOfService.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // when
        final HttpResponse response = checkHttpPatch("customers/block/" + EXT_COMPANY_ID_1 + "/contract/" + NOT_EXISTING_ID + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/updateContractOutOfServiceForNotExistingContractOutOfService.json", response);
    }

    @Test
    public void shouldNotUpdateFormalDebtCollectionContractOutOfServiceForNotExistingCustomer() throws Exception {
        // given
        final JsonElement request = request("customer/block/formalDebtCollection/updateContractOutOfService.json");

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPatch("customers/block/" + NOT_EXISTING_ID + "/contract/" + ContractOutOfServiceTestData.ID_1 + "/formalDebtCollection", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("customer/blocked/updateContractOutOfServiceForNotExistingCustomer.json", response);
    }
}