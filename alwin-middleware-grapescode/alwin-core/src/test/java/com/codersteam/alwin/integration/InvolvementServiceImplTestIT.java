package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesInvolvementService;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.testdata.assertion.CompanyAssert;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Optional;

import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENT;
import static com.codersteam.alwin.testdata.CompanyTestData.INVOLVEMENT_2;
import static com.codersteam.alwin.testdata.CompanyTestData.*;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_12;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_14;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_22;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_24;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_5;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_6;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_7;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_8;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_9;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.*;
import static org.junit.Assert.assertTrue;

@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
public class InvolvementServiceImplTestIT extends CoreTestBase {

    @Inject
    private UpdateCompaniesInvolvementService involvementService;

    @Inject
    private CompanyDao companyDao;

    @Test
    public void shouldSynchronizedCompaniesInvolvement() throws Exception {

        // given
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_1, INVOLVEMENT_1);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_2, INVOLVEMENT_2);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_5, INVOLVEMENT_5);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_6, INVOLVEMENT_6);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_7, INVOLVEMENT_7);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_8, INVOLVEMENT_8);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_9, INVOLVEMENT_9);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_12, INVOLVEMENT_12);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_14, INVOLVEMENT_14);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_22, INVOLVEMENT_22);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_23, INVOLVEMENT_23);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_24, INVOLVEMENT_24);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_26, INVOLVEMENT_26);

        // when
        involvementService.updateCompaniesInvolvement();
        commitTrx();

        // then
        assertCompany1();
        assertCompany5();
    }

    private void assertCompany1() {
        assertCompany(COMPANY_ID_1, expectedCompanyWithUpdatedInvolvement1());
    }

    private void assertCompany5() {
        assertCompany(COMPANY_ID_5, expectedCompanyWithUpdatedInvolvement2());
    }

    private void assertCompany(final Long companyId, final Company expectedCompany) {
        final Optional<Company> company = companyDao.get(companyId);
        assertTrue(company.isPresent());
        CompanyAssert.assertEqualsWithoutOrder(company.get(), expectedCompany);
    }
}
