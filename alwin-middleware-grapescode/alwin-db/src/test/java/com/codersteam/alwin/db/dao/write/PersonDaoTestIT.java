package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.PersonDao;
import com.codersteam.alwin.jpa.customer.Person;
import com.codersteam.alwin.testdata.PersonTestData;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.PersonTestData.personToUpdate;
import static com.codersteam.alwin.testdata.assertion.PersonAssert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */

@UsingDataSet({"test-permission.json", "test-data.json"})
public class PersonDaoTestIT extends WriteTestBase {

    @EJB
    private PersonDao personDao;

    @Test
    public void shouldUpdatePerson() {
        //given
        final Person personToUpdate = personToUpdate();

        //when
        personDao.update(personToUpdate);

        //then
        assertUpdatedPerson();
    }

    private void assertUpdatedPerson() {
        final Optional<Person> person = personDao.get(PersonTestData.PERSON_ID_2);
        assertTrue(person.isPresent());
        assertEquals(person.get(), personToUpdate());
    }
}
