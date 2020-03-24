package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.PersonDao;
import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.customer.Person;
import org.hibernate.envers.RevisionType;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.AddressTestData.address2;
import static com.codersteam.alwin.testdata.PersonTestData.person1;
import static org.junit.Assert.assertEquals;

/**
 * @author Tomasz Sliwinski
 */
public class PersonDaoAuditTestIT extends WriteTestBase {

    @EJB
    private PersonDao personDao;

    @Test
    public void shouldDetectChangesInPersonEntity() throws Exception {
        // given
        final Long personId = insertPerson();
        updatePersonFirstAddress(personId);
        updatePersonFirstName(personId);
        addPersonAddress(personId);
        deletePerson(personId);

        // when
        final List<AuditLogEntry<Person>> changes = personDao.findChangesByEntityId(personId);

        // then
        assertEquals(4, changes.size());
        assertEquals(RevisionType.ADD, changes.get(0).getRevisionType());
        assertEquals(RevisionType.MOD, changes.get(1).getRevisionType());
        assertEquals(RevisionType.MOD, changes.get(2).getRevisionType());
        assertEquals(RevisionType.DEL, changes.get(3).getRevisionType());
    }

    private Long insertPerson() throws Exception {
        final Person person = person1();
        person.setId(null);
        for (final Address address : person.getAddresses()) {
            address.setId(null);
        }
        for (final ContactDetail contactDetail : person.getContactDetails()) {
            contactDetail.setId(null);
        }
        personDao.create(person);
        commitTrx();
        return person.getId();
    }

    private void updatePersonFirstAddress(final Long personId) throws Exception {
        final Person person = getPerson(personId);
        person.getAddresses().iterator().next().setCity("ALABAMA");
        personDao.update(person);
        commitTrx();
    }

    private void updatePersonFirstName(final Long personId) throws Exception {
        final Person person = getPerson(personId);
        person.setFirstName("John");
        personDao.update(person);
        commitTrx();
    }

    private void addPersonAddress(final Long personId) throws Exception {
        final Person person = getPerson(personId);
        final Address newAddress = address2();
        newAddress.setId(null);
        person.getAddresses().add(newAddress);
        personDao.update(person);
        commitTrx();
    }

    private void deletePerson(final Long personId) throws Exception {
        personDao.delete(personId);
        commitTrx();
    }

    private Person getPerson(final Long personId) {
        final Optional<Person> personOpt = personDao.get(personId);
        return personOpt.orElseThrow(() -> new IllegalStateException(String.format("Cannot load Person with id %d", personId)));
    }
}
