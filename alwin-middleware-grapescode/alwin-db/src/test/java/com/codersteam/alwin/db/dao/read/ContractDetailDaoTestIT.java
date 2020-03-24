package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.ContactDetailDao;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.ContactDetailTestData.CONTACT_DETAIL_ID_1;
import static com.codersteam.alwin.testdata.ContactDetailTestData.NON_EXISTING_CONTACT_DETAIL_ID;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetail1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class ContractDetailDaoTestIT extends ReadTestBase {

    @EJB
    private ContactDetailDao contactDetailDao;

    @Test
    public void shouldFindContactDetailById() {
        //when
        final Optional<ContactDetail> contactDetail = contactDetailDao.get(CONTACT_DETAIL_ID_1);

        //then
        assertTrue(contactDetail.isPresent());
        assertThat(contactDetail.get()).isEqualToComparingFieldByFieldRecursively(contactDetail1());
    }

    @Test
    public void shouldNotFindContactDetailByIdForNonExistingId() {
        //when
        final Optional<ContactDetail> ContactDetail = contactDetailDao.get(NON_EXISTING_CONTACT_DETAIL_ID);

        //then
        assertFalse(ContactDetail.isPresent());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<ContactDetail> type = contactDetailDao.getType();

        // then
        assertEquals(ContactDetail.class, type);
    }
}
