package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.FormalDebtCollectionCustomerOutOfServiceDao;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionCustomerOutOfService;
import com.codersteam.alwin.testdata.CustomerTestData;
import com.codersteam.alwin.testdata.TestDateUtils;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.ID_1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.NOT_EXISTING_ID;
import static com.codersteam.alwin.testdata.FormalDebtCollectionCustomerOutOfServiceTestData.customersOutOfServiceForExtCompanyId1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class FormalDebtCollectionCustomerOutOfServiceDaoReadTestIT extends ReadTestBase {

    @EJB
    private FormalDebtCollectionCustomerOutOfServiceDao dao;

    @Test
    public void shouldReturnFalseWhenCustomerHasNoExclusionInformationForFormalDebtCollection() {
        // given: user without exclusion settings
        final Long customerId = 1213L;
        final Date operationDate = TestDateUtils.parse("2017-07-11 00:00:00.000000");

        // when
        final boolean customerOutOfService = dao.isCustomerOutOfFormalDebtCollection(customerId, operationDate);

        // then
        assertFalse(customerOutOfService);
    }

    @Test
    public void shouldReturnFalseWhenCustomerIsNotExcludedForFormalDebtCollectionAtTheTime() {
        // given: user with exclusion not in operationDate
        final Long customerId = CustomerTestData.ID_1;
        final Date operationDate = TestDateUtils.parse("2017-07-10 00:00:00.000000");

        // when
        final boolean customerOutOfService = dao.isCustomerOutOfFormalDebtCollection(customerId, operationDate);

        // then
        assertFalse(customerOutOfService);
    }

    @Test
    public void shouldReturnFalseWhenCustomerHasNoExclusionInformationForFirstFormalDebtCollection() {
        // given: user without exclusion settings
        final Long customerId = 1213L;
        final Date operationDate = TestDateUtils.parse("2017-07-11 00:00:00.000000");

        // when
        final boolean customerOutOfService = dao.isCustomerOutOfFormalDebtCollection(customerId, operationDate, FIRST);

        // then
        assertFalse(customerOutOfService);
    }

    @Test
    public void shouldReturnFalseWhenCustomerIsNotExcludedForFirstFormalDebtCollectionAtTheTime() {
        // given: user with exclusion not in operationDate
        final Long customerId = CustomerTestData.ID_1;
        final Date operationDate = TestDateUtils.parse("2017-07-10 00:00:00.000000");

        // when
        final boolean customerOutOfService = dao.isCustomerOutOfFormalDebtCollection(customerId, operationDate, FIRST);

        // then
        assertFalse(customerOutOfService);
    }

    @Test
    public void shouldFindFormalDebtCollectionCustomersOutOfService() {
        // given
        final long extCompanyId = EXT_COMPANY_ID_1;

        // and
        final Date today = parse("2017-07-16");

        // when
        final List<FormalDebtCollectionCustomerOutOfService> customersOutOfService = dao.findFormalDebtCollectionCustomersOutOfService(extCompanyId, today);

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
    public void shouldCustomerBeExcludedForFormalDebtCollection() {
        // given
        final Long customerId = CustomerTestData.ID_1;
        final Date operationDateStart = TestDateUtils.parse("2017-07-11");
        final Date operationDateWithinRange = TestDateUtils.parse("2017-07-17");
        final Date operationDateEnd = TestDateUtils.parse("2017-07-20");

        // when
        final boolean customerOutOfServiceStart = dao.isCustomerOutOfFormalDebtCollection(customerId, operationDateStart);
        final boolean customerOutOfServiceMiddle = dao.isCustomerOutOfFormalDebtCollection(customerId, operationDateWithinRange);
        final boolean customerOutOfServiceEnd = dao.isCustomerOutOfFormalDebtCollection(customerId, operationDateEnd);

        // then
        assertTrue(customerOutOfServiceStart);
        assertTrue(customerOutOfServiceEnd);
        assertTrue(customerOutOfServiceMiddle);
    }

    @Test
    public void shouldCheckThatCustomerOutOfServiceExists() {
        // when
        final boolean customerOutOfService = dao.checkIfFormalDebtCollectionCustomerOutOfServiceExists(ID_1);

        // then
        assertTrue(customerOutOfService);
    }

    @Test
    public void shouldCheckThatCustomerOutOfServiceDoesNotExist() {
        // when
        final boolean customerOutOfService = dao.checkIfFormalDebtCollectionCustomerOutOfServiceExists(NOT_EXISTING_ID);

        // then
        assertFalse(customerOutOfService);
    }
}
