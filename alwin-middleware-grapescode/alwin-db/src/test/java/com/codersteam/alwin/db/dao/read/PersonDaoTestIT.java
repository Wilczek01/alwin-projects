package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.PersonDao;
import com.codersteam.alwin.jpa.customer.Person;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.PersonTestData.EXT_PERSON_ID_1;
import static com.codersteam.alwin.testdata.PersonTestData.NON_EXISTING_PERSON_ID;
import static com.codersteam.alwin.testdata.PersonTestData.PERSON_ID_1;
import static com.codersteam.alwin.testdata.PersonTestData.person1;
import static com.codersteam.alwin.testdata.assertion.PersonAssert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */

public class PersonDaoTestIT extends ReadTestBase {

    @EJB
    private PersonDao personDao;

    @Test
    public void shouldFindPersonById() {
        //when
        final Optional<Person> person = personDao.get(PERSON_ID_1);

        //then
        assertTrue(person.isPresent());
        assertEquals(person.get(), person1());
    }

    @Test
    public void shouldNotFindPersonByIdForNonExistingId() {
        //when
        final Optional<Person> person = personDao.get(NON_EXISTING_PERSON_ID);

        //then
        assertFalse(person.isPresent());
    }

    @Test
    public void shouldFindPersonByExtPersonIdAndExtCompanyId() {
        //when
        final Optional<Person> person = personDao.findByExtPersonIdAndExtCompanyId(EXT_PERSON_ID_1, EXT_COMPANY_ID_2);

        //then
        assertTrue(person.isPresent());
        assertEquals(person.get(), person1());
    }

    @Test
    public void shouldNotFindPersonForNotMatchingExtCompanyId() {
        //when
        final Optional<Person> person = personDao.findByExtPersonIdAndExtCompanyId(EXT_PERSON_ID_1, EXT_COMPANY_ID_1);

        //then
        assertFalse(person.isPresent());
    }

    @Test
    public void shouldNotFindPersonForNotMatchingExtPersonId() {
        //when
        final Optional<Person> person = personDao.findByExtPersonIdAndExtCompanyId(NON_EXISTING_PERSON_ID, EXT_COMPANY_ID_2);

        //then
        assertFalse(person.isPresent());
    }


    @Test
    public void shouldNotFindPersonForNullExtCompanyId() {
        //when
        final Optional<Person> person = personDao.findByExtPersonIdAndExtCompanyId(EXT_PERSON_ID_1, null);

        //then
        assertFalse(person.isPresent());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Person> type = personDao.getType();

        // then
        assertEquals(Person.class, type);
    }
}
