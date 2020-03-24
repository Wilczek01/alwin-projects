package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesService;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.db.dao.LastDataSyncDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.LastDataSync;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.testdata.assertion.CompanyAssert;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Date;
import java.util.Optional;

import static com.codersteam.alwin.core.service.AlwinParameters.SYNC_DATA_DAY_START;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_DATE_FROM;
import static com.codersteam.alwin.integration.mock.PersonServiceMock.PERSONS_BY_DATE_FROM;
import static com.codersteam.alwin.jpa.type.LastDataSyncType.UPDATE_COMPANY_DATA;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.*;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.aidaPerson1;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.importedAidaPersonDto;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * @author Piotr Naroznik
 */
@UsingDataSet({"test-permission.json", "test-data.json"})
public class SyncDataServiceImplTestIT extends CoreTestBase {

    @Inject
    private UpdateCompaniesService syncDataService;

    @Inject
    private CompanyDao companyDao;

    @Inject
    private DateProvider dateProvider;

    @Inject
    private LastDataSyncDao lastDataSyncDao;


    @Test
    public void shouldSynchronizeCompanyData() {
        //given
        final Date fromDate = dateProvider.getDateStartOfDayMinusDays(SYNC_DATA_DAY_START);

        //and
        final Date toDate = dateProvider.getCurrentDate();

        //and
        COMPANIES_BY_DATE_FROM.put(fromDate, asList(aidaCompanyDto10(), importedAidaCompanyDto()));

        //and
        PERSONS_BY_DATE_FROM.put(fromDate, asList(aidaPerson1(), importedAidaPersonDto()));

        //when
        syncDataService.syncCompanyData();

        //then
        assertCompany();

        //and
        assertLastDataSyncEntityCreation(fromDate, toDate);
    }

    private void assertCompany() {
        final Optional<Company> company = companyDao.findCompanyByExtId(EXT_COMPANY_ID_1);
        assertTrue(company.isPresent());
        CompanyAssert.assertEqualsWithoutOrder(company.get(), expectedUpdatedCompany());
    }

    private void assertLastDataSyncEntityCreation(final Date fromDate, final Date toDate) {
        final Optional<LastDataSync> lastDataSync = lastDataSyncDao.findByType(UPDATE_COMPANY_DATA);
        assertTrue(lastDataSync.isPresent());
        assertNotNull(lastDataSync.get().getId());
        assertEquals(toDate, lastDataSync.get().getToDate());
        assertEquals(fromDate, lastDataSync.get().getFromDate());
        assertEquals(UPDATE_COMPANY_DATA, lastDataSync.get().getType());
    }
}
