package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.FormalDebtCollectionContractOutOfServiceDao;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionContractOutOfService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST;
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.SECOND;
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.ID_1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.NOT_EXISTING_ID;
import static com.codersteam.alwin.testdata.FormalDebtCollectionContractOutOfServiceTestData.activeContractsOutOfServiceForExtCompanyId1;
import static com.codersteam.alwin.testdata.FormalDebtCollectionContractOutOfServiceTestData.allContractsOutOfServiceForExtCompanyId1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_2;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class FormalDebtCollectionContractOutOfServiceDaoReadTestIT extends ReadTestBase {

    @EJB
    private FormalDebtCollectionContractOutOfServiceDao dao;

    @Test
    public void shouldFindActiveFormalDebtCollectionContractsOutOfService() {
        // given
        final long extCompanyId = EXT_COMPANY_ID_1;

        // and
        final Date currentDate = parse("2017-07-15");

        // when
        final List<FormalDebtCollectionContractOutOfService> contractsOutOfService = dao.findActiveFormalDebtCollectionContractsOutOfService(extCompanyId, currentDate);

        // then
        AssertionsForClassTypes.assertThat(contractsOutOfService)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate", "blockingOperator.user.creationDate", "blockingOperator.user.updateDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "blockingOperator.user.operators")
                .isEqualToComparingFieldByFieldRecursively(activeContractsOutOfServiceForExtCompanyId1());
    }

    @Test
    public void shouldCheckThatFormalDebtCollectionContractIsOutOfService() {
        // given
        final Date currentDate = parse("2017-07-15");

        // when
        boolean outOfService = dao.isFormalDebtCollectionContractOutOfService(CONTRACT_NO_1, currentDate, FIRST);

        // then
        assertTrue(outOfService);
    }

    @Test
    public void shouldCheckThatFormalDebtCollectionContractIsNotOutOfService() {
        // given
        final Date currentDate = parse("2017-07-15");

        // when
        boolean outOfService = dao.isFormalDebtCollectionContractOutOfService(CONTRACT_NO_2, currentDate, SECOND);

        // then
        assertFalse(outOfService);
    }

    @Test
    public void shouldFindAllFormalDebtCollectionContractsOutOfService() {
        // given
        final long extCompanyId = EXT_COMPANY_ID_1;

        // when
        final List<FormalDebtCollectionContractOutOfService> contractsOutOfService = dao.findAllFormalDebtCollectionContractsOutOfService(extCompanyId);

        // then
        AssertionsForClassTypes.assertThat(contractsOutOfService)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate", "blockingOperator.user.creationDate", "blockingOperator.user.updateDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "blockingOperator.user.operators")
                .isEqualToComparingFieldByFieldRecursively(allContractsOutOfServiceForExtCompanyId1());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<FormalDebtCollectionContractOutOfService> type = dao.getType();

        // then
        assertEquals(FormalDebtCollectionContractOutOfService.class, type);
    }

    @Test
    public void shouldCheckThatContractOutOfServiceExists() {
        // when
        final boolean contractOutOfService = dao.checkIfFormalDebtCollectionContractOutOfServiceExists(ID_1, EXT_COMPANY_ID_1);

        // then
        assertTrue(contractOutOfService);
    }

    @Test
    public void shouldCheckThatContractOutOfServiceDoesNotExist() {
        // when
        final boolean contractOutOfService = dao.checkIfFormalDebtCollectionContractOutOfServiceExists(NOT_EXISTING_ID, EXT_COMPANY_ID_1);

        // then
        assertFalse(contractOutOfService);
    }

    @Test
    public void shouldCheckThatContractOutOfServiceDoesNotExistForUnknownExtCompanyId() {
        // when
        final boolean contractOutOfService = dao.checkIfFormalDebtCollectionContractOutOfServiceExists(ID_1, NOT_EXISTING_ID);

        // then
        assertFalse(contractOutOfService);
    }
}
