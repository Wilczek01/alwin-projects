package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.ContactDetailDao;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.ContactDetailTestData.CONTACT_DETAIL_ID_2;
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetailToUpdate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */

@UsingDataSet({"test-permission.json", "test-data.json"})
public class ContractDetailDaoTestIT extends WriteTestBase {

    @EJB
    private ContactDetailDao contactDetailDao;

    @Test
    public void shouldUpdateContactDetail() {
        //given
        final ContactDetail ContactDetailToUpdate = contactDetailToUpdate();

        //when
        contactDetailDao.update(ContactDetailToUpdate);

        //then
        assertUpdatedContactDetail();
    }

    private void assertUpdatedContactDetail() {
        final Optional<ContactDetail> ContactDetail = contactDetailDao.get(CONTACT_DETAIL_ID_2);
        assertTrue(ContactDetail.isPresent());
        assertThat(ContactDetail.get()).isEqualToComparingFieldByFieldRecursively(contactDetailToUpdate());
    }
}
