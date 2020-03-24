package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.CustomerOutOfServiceDao;
import com.codersteam.alwin.jpa.customer.CustomerOutOfService;
import com.codersteam.alwin.testdata.CustomerTestData;
import com.codersteam.alwin.testdata.TestDateUtils;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.ID_1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.NOT_EXISTING_ID;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.customersOutOfServiceForExtCompanyId1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomasz Sliwinski
 */
public class CustomerOutOfServiceDaoTestIT extends ReadTestBase {

    @EJB
    private CustomerOutOfServiceDao dao;

    @Test
    public void shouldReturnFalseWhenCustomerHasNoExclusionInformation() {
        // given: user without exclusion settings
        final Long customerId = 1213L;
        final Date operationDate = TestDateUtils.parse("2017-07-11 00:00:00.000000");

        // when
        final boolean customerOutOfService = dao.isCustomerOutOfService(customerId, operationDate);

        // then
        assertFalse(customerOutOfService);
    }

    @Test
    public void shouldReturnFalseWhenCustomerIsNotExcludedAtTheTime() {
        // given: user with exclusio not in operationDate
        final Long customerId = CustomerTestData.ID_1;
        final Date operationDate = TestDateUtils.parse("2017-07-10 00:00:00.000000");

        // when
        final boolean customerOutOfService = dao.isCustomerOutOfService(customerId, operationDate);

        // then
        assertFalse(customerOutOfService);
    }

    @Test
    public void shouldFindCustomersOutOfService() {
        // given
        final long extCompanyId = EXT_COMPANY_ID_1;

        // and
        final Date today = parse("2017-07-16");

        // when
        final List<CustomerOutOfService> customersOutOfService = dao.findCustomersOutOfService(extCompanyId, today);

        // then
        AssertionsForClassTypes.assertThat(customersOutOfService)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate", "blockingOperator.user.creationDate",
                        "blockingOperator.user.updateDate", "customer.person.birthDate", "customer.person.idDocSignDate",
                        "customer.company.externalDBAgreementDate", "customer.company.ratingDate", "customer.accountManager.user.updateDate",
                        "customer.accountManager.user.creationDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "blockingOperator.user.operators", "customer.accountManager.user.operators")
                .isEqualToComparingFieldByFieldRecursively(customersOutOfServiceForExtCompanyId1());
    }

    @Test
    public void shouldCheckThatCustomerOutOfServiceExists() {
        // when
        final int customerOutOfService = dao.checkIfCustomerOutOfServiceExists(ID_1);

        // then
        assertEquals(1, customerOutOfService);
    }

    @Test
    public void shouldCheckThatCustomerOutOfServiceDoesNotExist() {
        // when
        final int customerOutOfService = dao.checkIfCustomerOutOfServiceExists(NOT_EXISTING_ID);

        // then
        assertEquals(0, customerOutOfService);
    }

    @Test
    public void shouldCustomerBeExcluded() {
        // given
        final Long customerId = CustomerTestData.ID_1;
        final Date operationDateStart = TestDateUtils.parse("2017-07-11 00:00:00.000000");
        final Date operationDateWithinRange = TestDateUtils.parse("2017-07-17 00:00:00.000000");
        final Date operationDateEnd = TestDateUtils.parse("2017-07-20 00:00:00.000000");

        // when
        final boolean customerOutOfServiceStart = dao.isCustomerOutOfService(customerId, operationDateStart);
        final boolean customerOutOfServiceMiddle = dao.isCustomerOutOfService(customerId, operationDateWithinRange);
        final boolean customerOutOfServiceEnd = dao.isCustomerOutOfService(customerId, operationDateEnd);

        // then
        assertTrue(customerOutOfServiceStart);
        assertTrue(customerOutOfServiceEnd);
        assertTrue(customerOutOfServiceMiddle);
    }
}
