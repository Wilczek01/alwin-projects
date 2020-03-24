package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.jpa.customer.Company;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID;
import static com.codersteam.alwin.testdata.CompanyTestData.company1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.NON_EXISTING_AIDA_COMPANY_ID;
import static com.codersteam.alwin.testdata.assertion.CompanyAssert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class CompanyDaoTestIT extends ReadTestBase {

    @EJB
    private CompanyDao companyDao;

    @Test
    public void shouldFindCompanyById() {
        //when
        final Optional<Company> company = companyDao.get(COMPANY_ID_1);

        //then
        assertTrue(company.isPresent());
        assertEquals(company.get(), company1());
    }

    @Test
    public void shouldNotFindCompanyByIdForNonExistingId() {
        //when
        final Optional<Company> company = companyDao.get(NON_EXISTING_COMPANY_ID);

        //then
        assertFalse(company.isPresent());
    }

    @Test
    public void shouldFindCompanyByExtId() {
        //when
        final Optional<Company> company = companyDao.findCompanyByExtId(EXT_COMPANY_ID_1);

        //then
        assertTrue(company.isPresent());
        assertEquals(company.get(), company1());
    }

    @Test
    public void shouldNotFindCompanyByExtId() {
        //when
        final Optional<Company> company = companyDao.findCompanyByExtId(NON_EXISTING_AIDA_COMPANY_ID);

        //then
        assertFalse(company.isPresent());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Company> type = companyDao.getType();

        // then
        assertEquals(Company.class, type);
    }
}
