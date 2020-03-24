package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.api.service.termination.ContractTerminationCreatorService;
import com.codersteam.alwin.db.dao.ContractTerminationDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;

import static com.codersteam.alwin.common.termination.ContractTerminationType.CONDITIONAL;
import static com.codersteam.alwin.integration.mock.ContractServiceMock.CONTRACTS_WITH_SUBJECTS_AND_SCHEDULES_BY_CONTRACT_NO;
import static com.codersteam.alwin.integration.mock.ContractServiceMock.GPS_INSTALLED_BY_CONTRACT_NO;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.testdata.ContractTerminationInitialDataTestData.contractTerminationInitialData1;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.DATE_FIELDS_LIST;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.expectedNewlyCreatedContractTerminations;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaContractWithSubjectsAndSchedulesTestData.contractWithSubjectsAndSchedulesDto1;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-formal-debt-collection-invoice.json", "test-demand-for-payment-type.json", "test-demand-for-payment.json"})
public class ContractTerminationCreatorServiceImplTestIT extends CoreTestBase {

    @EJB
    private ContractTerminationCreatorService service;

    @Inject
    private ContractTerminationDao contractTerminationDao;

    @Test
    public void shouldCreateAndStoreNewContractTermination() {
        // given
        assertTrue(contractTerminationDao.all().isEmpty());

        // and
        CURRENT_DATE = "2018-10-02";

        // and
        CONTRACTS_WITH_SUBJECTS_AND_SCHEDULES_BY_CONTRACT_NO.put(CONTRACT_NO, contractWithSubjectsAndSchedulesDto1());

        // and
        GPS_INSTALLED_BY_CONTRACT_NO.put(CONTRACT_NO_1, true);


        // when
        service.prepareContractTermination(contractTerminationInitialData1(), CONDITIONAL);

        // then
        assertThat(contractTerminationDao.allOrderedById())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, DATE_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(expectedNewlyCreatedContractTerminations());
    }
}
