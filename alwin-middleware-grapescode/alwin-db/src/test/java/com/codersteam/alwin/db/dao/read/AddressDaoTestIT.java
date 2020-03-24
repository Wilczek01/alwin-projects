package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.AddressDao;
import com.codersteam.alwin.jpa.customer.Address;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.AddressTestData.ADDRESS_ID_1;
import static com.codersteam.alwin.testdata.AddressTestData.NON_EXISTING_ADDRESS_ID;
import static com.codersteam.alwin.testdata.AddressTestData.address1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class AddressDaoTestIT extends ReadTestBase {

    @EJB
    private AddressDao addressDao;

    @Test
    public void shouldFindAddressById() {
        //when
        final Optional<Address> address = addressDao.get(ADDRESS_ID_1);

        //then
        assertTrue(address.isPresent());
        assertThat(address.get()).isEqualToComparingFieldByFieldRecursively(address1());
    }

    @Test
    public void shouldNotFindAddressByIdForNonExistingId() {
        //when
        final Optional<Address> address = addressDao.get(NON_EXISTING_ADDRESS_ID);

        //then
        assertFalse(address.isPresent());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Address> type = addressDao.getType();

        // then
        assertEquals(Address.class, type);
    }
}
