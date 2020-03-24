package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.api.service.notice.DemandForPaymentCreatorService;
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentInitialData;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.customer.Company;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.Optional;

import static com.codersteam.aida.core.api.model.CompanySegment.A;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.integration.mock.PersonServiceMock.PERSONS_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.SegmentServiceMock.COMPANIES_SEGMENT;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.DemandForPaymentInitialDataTestData.demandForPaymentInitialData1;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.expectedNewlyCreatedDemandsForPayment;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.TEST_EXT_PERSON_1;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.TEST_EXT_PERSON_2;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-demand-for-payment-type.json"})
public class DemandForPaymentCreatorServiceImplTestIT extends CoreTestBase {

    @EJB
    private DemandForPaymentCreatorService service;

    @Inject
    private DemandForPaymentDao demandForPaymentDao;

    @Inject
    private CompanyDao companyDao;

    @Test
    public void shouldCreateNewDemandForPaymentAndStoreCompanyData() {
        // given
        final DemandForPaymentInitialData demandForPaymentInitialData = demandForPaymentInitialData1();

        // and
        CURRENT_DATE = "2018-10-12";

        // and no demands for payment in db
        assertTrue(demandForPaymentDao.all().isEmpty());

        // and no company data in db
        final Long extCompanyId = demandForPaymentInitialData1().getExtCompanyId();
        assertFalse(companyDao.findCompanyByExtId(extCompanyId).isPresent());

        // and company data
        COMPANIES_BY_COMPANY_ID.put(extCompanyId, aidaCompanyDto(extCompanyId));
        PERSONS_BY_COMPANY_ID.put(extCompanyId, asList(TEST_EXT_PERSON_1, TEST_EXT_PERSON_2));
        // company segment match
        COMPANIES_SEGMENT.put(extCompanyId, A);

        // invoices data
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_1, dueInvoicesWithCorrections());

        // when
        service.prepareDemandForPayment(demandForPaymentInitialData);

        // then new demand for payment is created
        assertThat(demandForPaymentDao.allOrderedById())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "issueDate", "dueDate", "invoices.issueDate", "invoices.realDueDate")
                .isEqualToComparingFieldByFieldRecursively(expectedNewlyCreatedDemandsForPayment());

        // and company data is stored
        final Optional<Company> companyByExtId = companyDao.findCompanyByExtId(extCompanyId);
        assertTrue(companyByExtId.isPresent());
        final Company company = companyByExtId.get();
        assertEquals(extCompanyId, company.getExtCompanyId());
        assertFalse(company.getAddresses().isEmpty());
    }
}
