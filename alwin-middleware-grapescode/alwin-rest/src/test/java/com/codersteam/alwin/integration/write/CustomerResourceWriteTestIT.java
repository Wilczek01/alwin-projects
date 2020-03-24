package com.codersteam.alwin.integration.write;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.alwin.testdata.ContractOutOfServiceTestData;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import static com.codersteam.aida.core.api.model.CompanySegment.A;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.ContractServiceMock.CONTRACTS_BY_CONTRACT_NO;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENT;
import static com.codersteam.alwin.integration.mock.PersonServiceMock.PERSONS_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.SegmentServiceMock.COMPANIES_SEGMENT;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.ID_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_2;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.simpleAidaContractDto1;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.TEST_EXT_PERSON_1;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.TEST_EXT_PERSON_2;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-customer-out-of-service.json", "test-contract-out-of-service.json"})
public class CustomerResourceWriteTestIT extends WriteTestBase {

    @Test
    public void shouldBlockExistingCustomer() throws Exception {
        // given
        final JsonElement request = request("customer/block/existingCustomer.json");

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_1, INVOLVEMENT_2);

        // and
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_1, A);

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + EXT_COMPANY_ID_1, request, loginToken);

        // then
        assertCreated(response);
    }

    @Test
    public void shouldBlockCustomerFromAida() throws Exception {
        // given
        final JsonElement request = request("customer/block/customerFromAida.json");

        // and
        COMPANIES_BY_COMPANY_ID.put(COMPANY_ID_10, aidaCompanyDto10());

        // and
        PERSONS_BY_COMPANY_ID.put(COMPANY_ID_10, asList(TEST_EXT_PERSON_1, TEST_EXT_PERSON_2));

        // and
        COMPANIES_INVOLVEMENT.put(COMPANY_ID_10, INVOLVEMENT_2);

        // and
        COMPANIES_SEGMENT.put(COMPANY_ID_10, A);

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10, request, loginToken);

        // then
        assertCreated(response);
    }

    @Test
    public void shouldUpdateCustomerOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/updateCustomerOutOfService.json");

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement responseBeforeUpdate = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1, loginToken);

        // when
        final HttpResponse response = checkHttpPatch("customers/block/" + ID_1, request, loginToken);

        // and
        final JsonElement responseAfterUpdate = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1, loginToken);

        // then
        assertAccepted(response);
        assertNotEquals(responseBeforeUpdate, responseAfterUpdate);
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
    public void shouldUpdateContractOutOfService() throws Exception {
        // given
        final JsonElement request = request("customer/block/updateContractOutOfService.json");

        // and
        COMPANIES_BY_COMPANY_ID.put(ContractOutOfServiceTestData.EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement responseBeforeUpdate = sendHttpGet("customers/blocked/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contracts", loginToken);

        // when
        final HttpResponse response = checkHttpPatch("customers/block/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contract/" + ContractOutOfServiceTestData.ID_1, request, loginToken);

        // and
        final JsonElement responseAfterUpdate = sendHttpGet("customers/blocked/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contracts", loginToken);

        // then
        assertAccepted(response);
        assertNotEquals(responseBeforeUpdate, responseAfterUpdate);
    }

    @Test
    public void shouldBlockContractForExistingCustomer() throws Exception {
        // given
        final JsonElement request = request("customer/block/existingCustomer.json");

        // and
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + EXT_COMPANY_ID_1 + "/contract?contractNo=" + contractNo, request, loginToken);

        // then
        assertCreated(response);
    }

    @Test
    public void shouldBlockContractForCustomerFromAida() throws Exception {
        // given
        final JsonElement request = request("customer/block/customerFromAida.json");

        // and
        COMPANIES_BY_COMPANY_ID.put(COMPANY_ID_10, aidaCompanyDto10());

        // and
        PERSONS_BY_COMPANY_ID.put(COMPANY_ID_10, asList(TEST_EXT_PERSON_1, TEST_EXT_PERSON_2));

        // and
        COMPANIES_INVOLVEMENT.put(COMPANY_ID_10, INVOLVEMENT_2);

        // and
        COMPANIES_SEGMENT.put(COMPANY_ID_10, A);

        // and
        final AidaContractDto contract = simpleAidaContractDto1();
        final String contractNo = contract.getContractNo();

        // and
        CONTRACTS_BY_CONTRACT_NO.put(contractNo, contract);

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final HttpResponse response = checkHttpPost("customers/block/" + COMPANY_ID_10 + "/contract?contractNo=" + contractNo, request, loginToken);

        // then
        assertCreated(response);
    }

    @Test
    public void shouldDeleteContractOutOfService() throws Exception {
        // given
        COMPANIES_BY_COMPANY_ID.put(ContractOutOfServiceTestData.EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement responseBeforeUpdate = sendHttpGet("customers/blocked/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contracts", loginToken);

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contract/" + ContractOutOfServiceTestData.ID_1, loginToken);

        // and
        final JsonElement responseAfterUpdate = sendHttpGet("customers/blocked/" + ContractOutOfServiceTestData.EXT_COMPANY_ID_1 + "/contracts", loginToken);

        // then
        assertNoContent(response.getStatusLine().getStatusCode());
        assertNotEquals(responseBeforeUpdate, responseAfterUpdate);
    }

    @Test
    public void shouldDeleteCustomerOutOfService() throws Exception {
        // given
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto10());

        // and
        CURRENT_DATE = "2017-07-16";

        // and
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final JsonElement responseBeforeDelete = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1, loginToken);

        // when
        final HttpResponse response = checkHttpDelete("customers/block/" + ID_1, loginToken);

        // and
        final JsonElement responseAfterDelete = sendHttpGet("customers/blocked/" + EXT_COMPANY_ID_1, loginToken);

        // then
        assertNoContent(response);
        assertNotEquals(responseBeforeDelete, responseAfterDelete);
    }
}


