package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.ContractOutOfServiceDao;
import com.codersteam.alwin.jpa.customer.ContractOutOfService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.ID_1;
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.activeContractsOutOfServiceForExtCompanyId1;
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.allContractsOutOfServiceForExtCompanyId1;
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.NOT_EXISTING_ID;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.junit.Assert.assertEquals;

/**
 * @author Michal Horowic
 */
public class ContractOutOfServiceDaoTestIT extends ReadTestBase {

    @EJB
    private ContractOutOfServiceDao dao;

    @Test
    public void shouldFindActiveContractsOutOfService() {
        // given
        final long extCompanyId = EXT_COMPANY_ID_1;

        // and
        final Date currentDate = parse("2017-07-15");

        // when
        final List<ContractOutOfService> contractsOutOfService = dao.findActiveContractsOutOfService(extCompanyId, currentDate);

        // then
        AssertionsForClassTypes.assertThat(contractsOutOfService)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate", "blockingOperator.user.creationDate", "blockingOperator.user.updateDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "blockingOperator.user.operators")
                .isEqualToComparingFieldByFieldRecursively(activeContractsOutOfServiceForExtCompanyId1());
    }

    @Test
    public void shouldFindAllContractsOutOfService() {
        // given
        final long extCompanyId = EXT_COMPANY_ID_1;

        // when
        final List<ContractOutOfService> contractsOutOfService = dao.findAllContractsOutOfService(extCompanyId);

        // then
        AssertionsForClassTypes.assertThat(contractsOutOfService)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate", "blockingOperator.user.creationDate", "blockingOperator.user.updateDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "blockingOperator.user.operators")
                .isEqualToComparingFieldByFieldRecursively(allContractsOutOfServiceForExtCompanyId1());
    }

    @Test
    public void shouldCheckThatContractOutOfServiceExists() {
        // when
        final int contractOutOfService = dao.checkIfContractOutOfServiceExists(ID_1, EXT_COMPANY_ID_1);

        // then
        assertEquals(1, contractOutOfService);
    }

    @Test
    public void shouldCheckThatContractOutOfServiceDoesNotExist() {
        // when
        final int contractOutOfService = dao.checkIfContractOutOfServiceExists(NOT_EXISTING_ID, EXT_COMPANY_ID_1);

        // then
        assertEquals(0, contractOutOfService);
    }

    @Test
    public void shouldCheckThatContractOutOfServiceDoesNotExistForUnknownExtCompanyId() {
        // when
        final int contractOutOfService = dao.checkIfContractOutOfServiceExists(ID_1, NOT_EXISTING_ID);

        // then
        assertEquals(0, contractOutOfService);
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<ContractOutOfService> type = dao.getType();

        // then
        assertEquals(ContractOutOfService.class, type);
    }
}
