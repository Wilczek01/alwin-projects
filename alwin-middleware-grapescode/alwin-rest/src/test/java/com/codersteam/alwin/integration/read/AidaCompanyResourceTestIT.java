package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.AIDA_COMPANY_2_COMPANIES_NAME_PREFIX;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.NON_EXISTING_AIDA_COMPANY_ID;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.NOT_EXISTING_AIDA_COMPANY_NAME;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10;

/**
 * @author Tomasz Sliwinski
 */
public class AidaCompanyResourceTestIT extends ReadTestBase {

    @Test
    public void shouldBeAuthorizedToGetAidaCompanies() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("aidaCompanies");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldBeAuthorizedToGetAidaCompany() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("aidaCompanies/" + COMPANY_ID_10);

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldGetFirstPageOfAidaCompanies() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("aidaCompanies?firstResult=0&maxResults=1&companyName=" + AIDA_COMPANY_2_COMPANIES_NAME_PREFIX,
                loginToken);

        // then
        assertResponseEquals("aidaCompany/aidaCompaniesFirstPage.json", response);
    }

    @Test
    public void shouldGetSecondPageOfAidaCompanies() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("aidaCompanies?firstResult=1&maxResults=1&companyName=" + AIDA_COMPANY_2_COMPANIES_NAME_PREFIX,
                loginToken);

        // then
        assertResponseEquals("aidaCompany/aidaCompaniesSecondPage.json", response);
    }

    @Test
    public void shouldGetEmptyResponseWhenNoAidaCompaniesFound() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("aidaCompanies?firstResult=1&maxResults=1&companyName=" + NOT_EXISTING_AIDA_COMPANY_NAME, loginToken);

        // then
        assertResponseEquals("aidaCompany/aidaCompaniesNoResults.json", response);
    }

    @Test
    public void shouldGetAidaCompany() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        COMPANIES_BY_COMPANY_ID.put(COMPANY_ID_10, aidaCompanyDto10());

        // when
        final JsonElement response = sendHttpGet("aidaCompanies/" + COMPANY_ID_10, loginToken);

        // then
        assertResponseEquals("aidaCompany/aidaCompanyDefault.json", response);
    }

    @Test
    public void shouldNotGetAidaCompanyByNotExistingCompanyId() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("aidaCompanies/" + NON_EXISTING_AIDA_COMPANY_ID, loginToken);

        // then
        assertNotFound(responseCode);
    }
}
