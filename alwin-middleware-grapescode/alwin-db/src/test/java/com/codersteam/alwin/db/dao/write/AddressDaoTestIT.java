package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.AddressDao;
import com.codersteam.alwin.jpa.customer.Address;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.AddressTestData.ADDRESS_ID_2;
import static com.codersteam.alwin.testdata.AddressTestData.addressToUpdate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */

@UsingDataSet({"test-permission.json", "test-data.json"})
public class AddressDaoTestIT extends WriteTestBase {

    @EJB
    private AddressDao addressDao;

    @Test
    public void shouldUpdateAddress() {
        //given
        final Address addressToUpdate = addressToUpdate();

        //when
        addressDao.update(addressToUpdate);

        //then
        assertUpdatedAddress();
    }

    private void assertUpdatedAddress() {
        final Optional<Address> address = addressDao.get(ADDRESS_ID_2);
        assertTrue(address.isPresent());
        assertThat(address.get()).isEqualToComparingFieldByFieldRecursively(addressToUpdate());
    }
}
