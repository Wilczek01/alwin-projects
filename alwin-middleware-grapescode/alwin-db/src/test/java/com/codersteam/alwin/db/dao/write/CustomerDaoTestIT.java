package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.CustomerDao;
import com.codersteam.alwin.jpa.customer.Customer;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.testdata.CustomerTestData.ID_1;
import static com.codersteam.alwin.testdata.CustomerTestData.ID_2;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_12;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@UsingDataSet({"test-permission.json", "test-data.json"})
public class CustomerDaoTestIT extends WriteTestBase {

    @EJB
    private CustomerDao customerDao;

    @Test
    public void shouldDeleteCustomerAccountManager() throws Exception {
        // given
        assertCustomerAccountManager(ID_1, OPERATOR_ID_12);

        // when
        int updatedCustomersCount = customerDao.updateCustomerAccountManager(ID_1, null);
        commitTrx();

        // then
        assertEquals(1, updatedCustomersCount);
        assertCustomerAccountManager(ID_1, null);
    }

    @Test
    public void shouldSetCustomerAccountManager() throws Exception {
        // given
        assertCustomerAccountManager(ID_2, null);

        // when
        int updatedCustomersCount = customerDao.updateCustomerAccountManager(ID_2, OPERATOR_ID_12);
        commitTrx();

        // then
        assertEquals(1, updatedCustomersCount);
        assertCustomerAccountManager(ID_2, OPERATOR_ID_12);
    }

    @Test
    public void shouldUpdateCustomerAccountManager() throws Exception {
        // given
        assertCustomerAccountManager(ID_1, OPERATOR_ID_12);

        // when
        int updatedCustomersCount = customerDao.updateCustomerAccountManager(ID_1, OPERATOR_ID_1);
        commitTrx();

        // then
        assertEquals(1, updatedCustomersCount);
        assertCustomerAccountManager(ID_1, OPERATOR_ID_1);
    }

    private void assertCustomerAccountManager(final long customerId, final Long accountManagerId) {
        final Optional<Customer> customer = customerDao.get(customerId);
        assertTrue(customer.isPresent());
        if (accountManagerId == null) {
            assertNull(customer.get().getAccountManager());
            return;
        }
        assertNotNull(customer.get().getAccountManager());
        assertEquals(accountManagerId, customer.get().getAccountManager().getId());
    }
}
