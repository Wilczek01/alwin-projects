package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.CompanyPersonDao;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.CompanyPersonTestData.expectedCompanyPerson;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID;
import static com.codersteam.alwin.testdata.PersonTestData.NON_EXISTING_PERSON_ID;
import static com.codersteam.alwin.testdata.PersonTestData.PERSON_ID_2;
import static com.codersteam.alwin.testdata.assertion.CompanyPersonAssert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class CompanyPersonDaoTestIT extends ReadTestBase {

    @EJB
    private CompanyPersonDao companyPersonDao;

    @Test
    public void shouldFindCompanyPerson() {
        //when
        final Optional<CompanyPerson> companyPerson = companyPersonDao.findByPersonIdAndCompanyId(PERSON_ID_2, COMPANY_ID_2);

        //then
        assertTrue(companyPerson.isPresent());
        assertEquals(companyPerson.get(), expectedCompanyPerson());
    }

    @Test
    public void shouldNotFindCompanyPersonForNonExistingCompanyId() {
        //when
        final Optional<CompanyPerson> optional = companyPersonDao.findByPersonIdAndCompanyId(PERSON_ID_2, NON_EXISTING_COMPANY_ID);

        //then
        assertFalse(optional.isPresent());
    }

    @Test
    public void shouldNotFindCompanyPersonForNonExistingPersonId() {
        //when
        final Optional<CompanyPerson> optional = companyPersonDao.findByPersonIdAndCompanyId(NON_EXISTING_PERSON_ID, COMPANY_ID_2);

        //then
        assertFalse(optional.isPresent());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<CompanyPerson> type = companyPersonDao.getType();

        // then
        assertEquals(CompanyPerson.class, type);
    }
}