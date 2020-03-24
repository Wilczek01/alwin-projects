package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;
import com.codersteam.alwin.common.sort.DemandForPaymentSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.common.demand.DemandForPaymentState.ISSUED;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.NEW;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.*;
import static com.codersteam.alwin.testdata.InvoiceTestData.*;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static com.codersteam.alwin.testdata.criteria.DemandForPaymentSearchCriteriaTestData.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.*;

/**
 * @author Tomasz Sliwinski
 */
public class DemandForPaymentDaoReadTestIT extends ReadTestBase {

    private static final LinkedHashMap<DemandForPaymentSortField, SortOrder> NO_RESULT_SORTING = new LinkedHashMap<>();

    @EJB
    private DemandForPaymentDao dao;

    @Test
    public void shouldFindAllNewDemandsForPayment() {
        // when
        final List<DemandForPaymentWithCompanyName> newDemandsForPayment = dao.findAllDemandsForPaymentByState(NEW);

        // then
        assertThat(newDemandsForPayment)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "realDueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(demandsForPaymentInNewStateWithCreatedManually());
    }

    @Test
    public void shouldFindAllIssuedDemandsForPayment() {
        // when
        final List<DemandForPaymentWithCompanyName> issuedDemandsForPayment = dao.findAllDemandsForPaymentByState(ISSUED);

        // then
        assertThat(issuedDemandsForPayment)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "realDueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(demandsForPaymentInIssuedState());
    }

    @Test
    public void shouldFindAllAutomaticallyCreatedDemandsForPayment() {
        // when
        final List<DemandForPaymentWithCompanyName> firstPage = dao.findAllDemandsForPayment(automaticallyCreatedDemandForPaymentSearchCriteria(0, 3), NO_RESULT_SORTING);
        final List<DemandForPaymentWithCompanyName> secondPage = dao.findAllDemandsForPayment(automaticallyCreatedDemandForPaymentSearchCriteria(3, 3), NO_RESULT_SORTING);

        // then
        assertThat(firstPage)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "realDueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(allAutomaticallyCreatedDemandsForPaymentFirstPage());

        // and
        assertThat(secondPage)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "realDueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(allDemandsForPaymentSecondPage());
    }

    @Test
    public void shouldFindAllAutomaticallyCreatedDemandsForPaymentInNewState() {
        // when
        final List<DemandForPaymentWithCompanyName> newDemandsForPayment = dao.findAllDemandsForPayment(automaticallyCreatedDemandForPaymentSearchCriteriaByState(0, 2, NEW), NO_RESULT_SORTING);

        // then
        assertThat(newDemandsForPayment)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "realDueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(demandsForPaymentInNewState());
    }

    @Test
    public void shouldFindAllAutomaticallyCreatedDemandsForPaymentFilteredByContractNo() {
        // when
        final List<DemandForPaymentWithCompanyName> newDemandsForPayment = dao.findAllDemandsForPayment(automaticallyCreatedDemandForPaymentSearchCriteriaByContractNo(0, 3, CONTRACT_NUMBER_INVOICE_15), NO_RESULT_SORTING);

        // then
        assertThat(newDemandsForPayment)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "realDueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(demandsForPaymentFilteredByContractNo());
    }

    @Test
    public void shouldFindAllAutomaticallyCreatedDemandsForPaymentCountFilteredByContractNo() {
        // when
        final long count = dao.findAllDemandsForPaymentCount(automaticallyCreatedDemandForPaymentSearchCriteriaByContractNo(0, 3, CONTRACT_NUMBER_INVOICE_15));

        // then
        assertEquals(2, count);
    }

    @Test
    public void shouldFindAllAutomaticallyCreatedDemandsForPaymentCountFilteredByIssuedStateWithAborted() {
        // given
        final DemandForPaymentSearchCriteria criteria = automaticallyCreatedDemandForPaymentSearchCriteriaByState(0, 2, ISSUED);
        criteria.setAborted(null);

        // when
        final long count = dao.findAllDemandsForPaymentCount(criteria);

        // then
        assertEquals(2, count);
    }

    @Test
    public void shouldFindAllDemandsForPaymentCountFilteredByIssuedStateWithAborted() {
        // given
        final DemandForPaymentSearchCriteria criteria = demandForPaymentSearchCriteriaByState(0, 2, ISSUED);

        // when
        final long count = dao.findAllDemandsForPaymentCount(criteria);

        // then
        assertEquals(4, count);
    }

    @Test
    public void shouldFindAllAutomaticallyCreatedDemandsForPaymentCountFilteredByIssuedStateWithoutAborted() {
        // given
        final DemandForPaymentSearchCriteria criteria = automaticallyCreatedDemandForPaymentSearchCriteriaByState(0, 2, ISSUED);
        criteria.setAborted(false);

        // when
        final long count = dao.findAllDemandsForPaymentCount(criteria);

        // then
        assertEquals(1, count);
    }

    @Test
    public void shouldFindAllAutomaticallyCreatedDemandsForPaymentFilteredByType() {
        // when
        final List<DemandForPaymentWithCompanyName> newDemandsForPayment = dao.findAllDemandsForPayment(automaticallyCreatedDemandForPaymentSearchCriteriaByType(0, 2,
                DemandForPaymentTypeKey.FIRST), NO_RESULT_SORTING);

        // then
        assertThat(newDemandsForPayment)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(demandsForPaymentFilteredByType());
    }

    @Test
    public void shouldFindIssuedDemandsForPayment() {
        // given
        final List<Long> demandsIds =
                asList(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ID_2, DEMAND_FOR_PAYMENT_ID_3, DEMAND_FOR_PAYMENT_ID_4, DEMAND_FOR_PAYMENT_ID_5);
        // when
        final List<Long> issuedDemandsIds = dao.findIssuedOrProcessingDemandsForPayment(demandsIds);

        // then
        assertEquals(3, issuedDemandsIds.size());
        assertTrue(issuedDemandsIds.contains(DEMAND_FOR_PAYMENT_ID_3));
        assertTrue(issuedDemandsIds.contains(DEMAND_FOR_PAYMENT_ID_4));
        assertTrue(issuedDemandsIds.contains(DEMAND_FOR_PAYMENT_ID_5));

    }

    @Test
    public void shouldFindExistingDemandsForPayment() {
        // given
        final List<Long> demandsIds =
                asList(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ID_2, DEMAND_FOR_PAYMENT_ID_3, NON_EXISTING_DEMAND_FOR_PAYMENT_ID_1);
        // when
        final List<Long> existingDemandsIds = dao.findExistingDemandsForPayment(demandsIds);

        // then
        assertEquals(3, existingDemandsIds.size());
        assertTrue(existingDemandsIds.contains(DEMAND_FOR_PAYMENT_ID_1));
        assertTrue(existingDemandsIds.contains(DEMAND_FOR_PAYMENT_ID_2));
        assertTrue(existingDemandsIds.contains(DEMAND_FOR_PAYMENT_ID_3));

    }

    @Test
    public void shouldFindLatestDemandForPaymentForContract() {
        // when
        final Optional<DemandForPaymentWithCompanyName> latestDemandForPayment = dao.findLatestDemandForPayment(CONTRACT_NUMBER_INVOICE_15);

        // then
        assertTrue(latestDemandForPayment.isPresent());
        assertThat(latestDemandForPayment.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "realDueDate", "issueDate", "invoices.realDueDate", "invoices.issueDate")
                .isEqualToComparingFieldByFieldRecursively(demandForPaymentWithCompanyName4());
    }

    @Test
    public void shouldNotFindLatestDemandForPaymentForNotExistingContract() {
        // when
        final Optional<DemandForPaymentWithCompanyName> latestDemandForPayment = dao.findLatestDemandForPayment("not existing contract number");

        // then
        assertFalse(latestDemandForPayment.isPresent());
    }

    @Test
    public void shouldNotFindAnyIssuedSecondDemandsForPaymentWithDueDate() {
        // when
        final List<DemandForPaymentWithCompanyName> issuedSecondDemandsForPaymentWithDueDate = dao.findIssuedSecondDemandsForPaymentWithDueDate(parse("1999-12-12"));

        // then
        assertTrue(issuedSecondDemandsForPaymentWithDueDate.isEmpty());
    }

    @Test
    public void shouldFindNewManuallyCreatedDemandsForPaymentPage() {
        // when
        final List<DemandForPaymentWithCompanyName> newManualDemandsForPaymentPage =
                dao.findAllDemandsForPayment(manualDemandForPaymentSearchCriteriaByState(0, 2, NEW), NO_RESULT_SORTING);

        // then
        assertThat(newManualDemandsForPaymentPage)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices")
                .isEqualToComparingFieldByFieldRecursively(newManualDemandsForPayment());
    }

    @Test
    public void shouldFindAllNewManuallyCreatedDemandsForPaymentCount() {
        // when
        final long allNewManualDemandsForPaymentCount = dao.findAllDemandsForPaymentCount(manualDemandForPaymentSearchCriteriaByState(0, 2, NEW));

        // then
        assertEquals(3, allNewManualDemandsForPaymentCount);
    }

    @Test
    public void shouldFindLatestDemandForPaymentOfGivenTypeForContract() {
        // when
        final Optional<DemandForPayment> latestDemandForPaymentForContract = dao.findLatestDemandForPaymentForContract(DemandForPaymentTypeKey.FIRST,
                CONTRACT_NUMBER_INVOICE_15, DemandForPaymentState.NEW);

        // then
        assertTrue(latestDemandForPaymentForContract.isPresent());
        assertThat(latestDemandForPaymentForContract.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices")
                .isEqualToComparingFieldByFieldRecursively(demandForPayment1());
    }

    @Test
    public void shouldNotFindLatestDemandForPaymentOfGivenTypeForContract() {
        // when
        final Optional<DemandForPayment> latestDemandForPaymentForContract = dao.findLatestDemandForPaymentForContract(DemandForPaymentTypeKey.SECOND,
                CONTRACT_NUMBER_INVOICE_25, DemandForPaymentState.ISSUED);

        // then
        assertFalse(latestDemandForPaymentForContract.isPresent());
    }

    @Test
    public void shouldFindLatestDemandForPaymentForContact() {
        // when
        final Optional<DemandForPayment> latestDemandForPaymentForContract = dao.findLatestDemandForPaymentForContract(CONTRACT_NUMBER_INVOICE_15,
                DemandForPaymentState.WAITING);

        // then
        assertTrue(latestDemandForPaymentForContract.isPresent());
        assertThat(latestDemandForPaymentForContract.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices")
                .isEqualToComparingFieldByFieldRecursively(demandForPayment4());
    }

    @Test
    public void shouldNotFindLatestDemandForPaymentForContact() {
        // when
        final Optional<DemandForPayment> latestDemandForPaymentForContract = dao.findLatestDemandForPaymentForContract(CONTRACT_NUMBER_INVOICE_15,
                DemandForPaymentState.ISSUED);

        // then
        assertFalse(latestDemandForPaymentForContract.isPresent());
    }

    @Test
    public void shouldFindDemandsForPaymentExcludingGiven() {
        // given
        final DemandForPayment demandForPayment = demandForPayment4();

        // when
        final List<DemandForPayment> demandsForPayment = dao.findDemandsForPaymentExcludingGiven(demandForPayment.getType().getKey(),
                demandForPayment.getContractNumber(), demandForPayment.getInitialInvoiceNo(), DEMAND_FOR_PAYMENT_ID_1);

        // then
        assertThat(demandsForPayment)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices")
                .isEqualToComparingFieldByFieldRecursively(singletonList(demandForPayment4()));
    }

    @Test
    public void shouldNotFindDemandsForPaymentExcludingGiven() {
        // given
        final DemandForPayment demandForPayment = demandForPayment4();

        // when
        final List<DemandForPayment> demandsForPayment = dao.findDemandsForPaymentExcludingGiven(demandForPayment.getType().getKey(),
                demandForPayment.getContractNumber(), NOT_EXISTING_INVOICE_NUMBER, DEMAND_FOR_PAYMENT_ID_1);

        // then
        assertTrue(demandsForPayment.isEmpty());
    }

    @Test
    public void shouldFindDemandsForPaymentOfGivenTypeForContract() {
        // given
        final DemandForPayment demandForPayment = demandForPayment4();

        // when
        final List<DemandForPayment> demandsForPayment = dao.findDemandsForPayment(demandForPayment.getType().getKey(),
                demandForPayment.getContractNumber(), demandForPayment.getInitialInvoiceNo());

        // then
        assertThat(demandsForPayment)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "issueDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices")
                .isEqualToComparingFieldByFieldRecursively(asList(demandForPayment1(), demandForPayment4()));
    }

    @Test
    public void shouldNotFindDemandsForPaymentOfGivenTypeForContract() {
        // given
        final DemandForPayment demandForPayment = demandForPayment4();

        // when
        final List<DemandForPayment> demandsForPayment = dao.findDemandsForPayment(demandForPayment.getType().getKey(),
                demandForPayment.getContractNumber(), NOT_EXISTING_INVOICE_NUMBER);

        // then
        assertTrue(demandsForPayment.isEmpty());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<DemandForPayment> type = dao.getType();

        // then
        assertEquals(DemandForPayment.class, type);
    }
}
