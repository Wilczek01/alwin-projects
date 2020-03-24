package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.db.dao.ContractTerminationDao;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.testdata.ContractTerminationTestData;
import com.codersteam.alwin.testdata.TestDateUtils;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.codersteam.alwin.testdata.ContractTerminationTestData.COMPANY_ID_101;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.COMPANY_ID_105;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.CONTRACT_NUMBER_101;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.CONTRACT_TERMINATIONS_FOR_OFFSET_3_LIMIT_2;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.ID_101;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.ID_102;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.ID_104;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.ID_105;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.ID_130;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.ID_131;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.ID_133;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.NON_EXISTING_CONTRACT_TERMINATION_ID_1;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.TYPE_101;
import static com.codersteam.alwin.testdata.ContractTerminationTestData.TYPE_105;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_ID_1;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.NON_EXISTING_DEMAND_FOR_PAYMENT_ID_1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Dariusz Rackowski
 */
public class ContractTerminationDaoReadTestIT extends ReadTestBase {

    @EJB
    private ContractTerminationDao contractTerminationDao;

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<ContractTermination> type = contractTerminationDao.getType();

        // then
        assertEquals(ContractTermination.class, type);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void shouldReturnOne() {
        // when
        final Optional<ContractTermination> contractTermination = contractTerminationDao.get(ID_101);

        // then
        assertThat(contractTermination).isPresent();
        assertThat(contractTermination.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "terminationDate", "dueDateInDemandForPayment",
                        "formalDebtCollectionInvoices.realDueDate", "formalDebtCollectionInvoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, ContractTerminationTestData.SKIP_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(ContractTerminationTestData.contractTermination101());
    }

    @Test
    public void shouldReturnAllContractTerminationsForSpecificPageOfCompanyIdAndTerminationDate() {
        // given
        final int offset = 3;
        final int limit = 2;
        final ContractTerminationState state = ContractTerminationState.ISSUED;
        final ContractTerminationSearchCriteria searchCriteria = new ContractTerminationSearchCriteria();
        searchCriteria.setFirstResult(offset);
        searchCriteria.setMaxResults(limit);
        searchCriteria.setState(state);

        // when
        final List<ContractTermination> result = contractTerminationDao.findByStatePaginatedByExtCompanyIdAndTerminationDateAndType(searchCriteria, new LinkedHashMap<>());

        // then
        Assertions.assertThat(result).hasSize(2);
        assertThat(result)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ContractTerminationTestData.DATE_FIELDS_LIST)
                .usingComparatorForFields(SKIP_COMPARATOR, ContractTerminationTestData.SKIP_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(CONTRACT_TERMINATIONS_FOR_OFFSET_3_LIMIT_2);
    }

    @Test
    public void shouldReturnCountAllContractTerminations() {
        // given
        final ContractTerminationState state = ContractTerminationState.ISSUED;
        final ContractTerminationSearchCriteria searchCriteria = new ContractTerminationSearchCriteria();
        searchCriteria.setState(state);

        // when
        final int count = contractTerminationDao.countInStatePaginatedByExtCompanyIdAndTerminationDate(searchCriteria);

        // then
        Assertions.assertThat(count).isEqualTo(4);
    }

    @Test
    public void shouldReturnContractTerminationsByIds() {
        // given
        final List<Long> terminationIds = Arrays.asList(ID_101, ID_102, ID_130, ID_101);

        // when
        final List<ContractTermination> contractTerminations = contractTerminationDao.findByIdsIn(terminationIds);

        // then
        Assertions.assertThat(contractTerminations).hasSize(3);
        assertThat(contractTerminations)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ContractTerminationTestData.DATE_FIELDS_LIST)
                .usingComparatorForFields(SKIP_COMPARATOR, ContractTerminationTestData.SKIP_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(Arrays.asList(
                        ContractTerminationTestData.contractTermination101(),
                        ContractTerminationTestData.contractTermination102(),
                        ContractTerminationTestData.contractTermination130()
                ));
    }

    @Test
    public void shouldReturnIssuedConditionalContractTerminationsWithTerminationDate() {
        // given
        final Date dueDate = TestDateUtils.parse("2016-03-13");

        // when
        final List<ContractTermination> contractTerminations = contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(dueDate);

        // then
        assertThat(contractTerminations)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ContractTerminationTestData.DATE_FIELDS_LIST)
                .usingComparatorForFields(SKIP_COMPARATOR, ContractTerminationTestData.SKIP_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(Arrays.asList(
                        ContractTerminationTestData.contractTermination101(),
                        ContractTerminationTestData.contractTermination103()
                ));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void shouldSetContractTerminationsAsProcessingByIdsAndNotInClosedStates() {
        // given
        final Set<Long> contractTerminationIds = Sets.newHashSet(ID_101, ID_104, ID_105);
        final String loggedInOperatorName = "Janusz Nowakowski";

        // when
        final int updatedCount = contractTerminationDao.processContractTerminations(loggedInOperatorName, contractTerminationIds);

        // then
        assertThat(updatedCount).isEqualTo(2);
        final ContractTermination ct101 = contractTerminationDao.get(ID_101).get();
        assertThat(ct101.getState()).isEqualTo(ContractTerminationTestData.STATE_101);
        assertThat(ct101.getGeneratedBy()).isEqualTo(ContractTerminationTestData.GENERATED_BY_101);

        final ContractTermination ct104 = contractTerminationDao.get(ID_104).get();
        assertThat(ct104.getState()).isEqualTo(ContractTerminationState.WAITING);
        assertThat(ct104.getGeneratedBy()).isEqualTo(loggedInOperatorName);

        final ContractTermination ct105 = contractTerminationDao.get(ID_105).get();
        assertThat(ct105.getState()).isEqualTo(ContractTerminationState.WAITING);
        assertThat(ct105.getGeneratedBy()).isEqualTo(loggedInOperatorName);
    }

    @Test
    public void shouldReturnOnlyExistingContractIds() {
        // given
        final Set<Long> contractTerminationIds = Sets.newHashSet(ID_101, ID_102, ID_104, ID_105, 201L, 203L, 204L, 1002L);

        // when
        final List<Long> existingContractTerminationIds = contractTerminationDao.findExistingContractTerminationIds(contractTerminationIds);

        // then
        Assertions.assertThat(existingContractTerminationIds).containsExactlyInAnyOrder(ID_101, ID_102, ID_104, ID_105);
    }

    @Test
    public void shouldReturnTerminationIdsByStatusesAndTerminationIds() {
        // given
        final Set<Long> contractTerminationIds = Sets.newHashSet(ID_101, ID_102, ID_131, ID_133);
        final List<ContractTerminationState> states = Arrays.asList(ContractTerminationState.ISSUED, ContractTerminationState.REJECTED);

        // when
        final List<Long> result = contractTerminationDao.findByStatusesAndTerminationIds(states, contractTerminationIds);

        // then
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                ID_101, ID_102, ID_131, ID_133
        );
    }

    @Test
    public void shouldReturnMapContractIdToCompanyId() {
        // given
        final HashSet<Long> contractTerminationIds = Sets.newHashSet(ID_101, ID_105);

        // when
        final Map<Long, Long> contractIdToContractType = contractTerminationDao.findContractIdToCompanyIds(contractTerminationIds);

        // then
        final Map<Long, Long> map = new HashMap<>();
        map.put(ID_101, COMPANY_ID_101);
        map.put(ID_105, COMPANY_ID_105);
        assertThat(contractIdToContractType)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ContractTerminationTestData.DATE_FIELDS_LIST)
                .usingComparatorForFields(SKIP_COMPARATOR, ContractTerminationTestData.SKIP_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(map);
    }

    @Test
    public void shouldReturnMapContractIdToMap() {
        // given
        final HashSet<Long> contractTerminationIds = Sets.newHashSet(ID_101, ID_105);

        // when
        final Map<Long, ContractTerminationType> contractIdToContractType = contractTerminationDao.findContractIdToContractType(contractTerminationIds);

        // then
        final Map<Long, ContractTerminationType> map = new HashMap<>();
        map.put(ID_101, TYPE_101);
        map.put(ID_105, TYPE_105);
        assertThat(contractIdToContractType)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ContractTerminationTestData.DATE_FIELDS_LIST)
                .usingComparatorForFields(SKIP_COMPARATOR, ContractTerminationTestData.SKIP_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(map);
    }

    @Test
    public void shouldFindLatestContractTermination() {
        // when
        final Optional<ContractTermination> latestContractTermination = contractTerminationDao.findLatestContractTermination(CONTRACT_NUMBER_101);

        // then
        assertTrue(latestContractTermination.isPresent());
        assertThat(latestContractTermination.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ContractTerminationTestData.DATE_FIELDS_LIST)
                .usingComparatorForFields(SKIP_COMPARATOR, ContractTerminationTestData.SKIP_FIELDS_LIST)
                .isEqualToComparingFieldByFieldRecursively(ContractTerminationTestData.contractTermination101());
    }

    @Test
    public void shouldNotFindLatestContractTerminationIfNotPresent() {
        // when
        final Optional<ContractTermination> latestContractTermination = contractTerminationDao.findLatestContractTermination("not existing contract termination");

        // then
        assertFalse(latestContractTermination.isPresent());
    }

    @Test
    public void shouldFindIssuedConditionalContractTerminationsWithTerminationDate() {
        // when
        final Date terminationDate = parse("2016-03-13");
        final List<ContractTermination> issuedConditionalContractTerminationsWithDueDate =
                contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(terminationDate);

        // then
        assertFalse(issuedConditionalContractTerminationsWithDueDate.isEmpty());
        issuedConditionalContractTerminationsWithDueDate.forEach(contractTermination -> {
            assertEquals(terminationDate, contractTermination.getTerminationDate());
            assertEquals(ContractTerminationState.ISSUED, contractTermination.getState());
        });
    }

    @Test
    public void shouldNotFindIssuedConditionalContractTerminationsWithTerminationDate() {
        // when
        final Date terminationDate = parse("1988-02-27");
        final List<ContractTermination> issuedConditionalContractTerminationsWithDueDate =
                contractTerminationDao.findIssuedConditionalContractTerminationsWithTerminationDate(terminationDate);

        // then
        assertTrue(issuedConditionalContractTerminationsWithDueDate.isEmpty());
    }

    @Test
    public void shouldFindContractTerminationForPrecedingDemandForPayment() {
        // when
        final Optional<ContractTermination> contractTerminationForPrecedingDemandForPayment =
                contractTerminationDao.findContractTerminationForPrecedingDemandForPayment(DEMAND_FOR_PAYMENT_ID_1);

        // then
        assertTrue(contractTerminationForPrecedingDemandForPayment.isPresent());
    }

    @Test
    public void shouldNotFindContractTerminationForPrecedingDemandForPayment() {
        // when
        final Optional<ContractTermination> contractTerminationForPrecedingDemandForPayment =
                contractTerminationDao.findContractTerminationForPrecedingDemandForPayment(NON_EXISTING_DEMAND_FOR_PAYMENT_ID_1);

        // then
        assertFalse(contractTerminationForPrecedingDemandForPayment.isPresent());
    }

    @Test
    public void shouldFindContractTerminationForPrecedingContractTermination() {
        // when
        final Optional<ContractTermination> contractTerminationForPrecedingContractTermination =
                contractTerminationDao.findContractTerminationForPrecedingContractTermination(ID_101);

        // then
        assertTrue(contractTerminationForPrecedingContractTermination.isPresent());
    }

    @Test
    public void shouldNotFindContractTerminationForPrecedingContractTermination() {
        // when
        final Optional<ContractTermination> contractTerminationForPrecedingContractTermination =
                contractTerminationDao.findContractTerminationForPrecedingContractTermination(NON_EXISTING_CONTRACT_TERMINATION_ID_1);

        // then
        assertFalse(contractTerminationForPrecedingContractTermination.isPresent());
    }
}
