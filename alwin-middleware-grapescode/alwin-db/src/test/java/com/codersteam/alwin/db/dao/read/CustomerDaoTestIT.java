package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.CustomerDao;
import com.codersteam.alwin.jpa.customer.Customer;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CustomerTestData.NON_EXISTING_ID;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_ID_12;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomerDaoTestIT extends ReadTestBase {

    @EJB
    private CustomerDao customerDao;

    @Test
    public void shouldFindCustomerByExternalCompanyId() {
        // when
        final Optional<Customer> user = customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1);

        // then
        assertTrue(user.isPresent());
    }

    @Test
    public void shouldNotUpdateAccountManagerWhenCustomerNotExists() {
        // when
        final int updatedCustomersCount = customerDao.updateCustomerAccountManager(NON_EXISTING_ID, TEST_USER_ID_12);

        // then
        assertEquals(0, updatedCustomersCount);
    }

    @Test
    public void shouldNotFindCustomerById() {
        // when
        Optional<Customer> customer = customerDao.get(NON_EXISTING_ID);

        // then
        assertFalse(customer.isPresent());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Customer> type = customerDao.getType();

        // then
        assertEquals(Customer.class, type);
    }

}