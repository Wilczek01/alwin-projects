package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.jpa.customer.Company;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.companyToUpdate;
import static com.codersteam.alwin.testdata.assertion.CompanyAssert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */

@UsingDataSet({"test-permission.json", "test-data.json"})
public class CompanyDaoTestIT extends WriteTestBase {

    @EJB
    private CompanyDao companyDao;

    @Test
    public void shouldUpdateCompany() {
        //given
        final Company companyToUpdate = companyToUpdate();

        //when
        companyDao.update(companyToUpdate);

        //then
        assertUpdatedCompany();
    }

    private void assertUpdatedCompany() {
        final Optional<Company> company = companyDao.get(COMPANY_ID_2);
        assertTrue(company.isPresent());
        assertEquals(company.get(), companyToUpdate());
    }
}
