package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;
import com.codersteam.alwin.testdata.OperatorTestData;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.demand.DemandForPaymentState.*;
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST;
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.SECOND;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.*;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.*;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_15;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_15;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static com.google.common.primitives.Longs.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.*;

/**
 * @author Tomasz Sliwinski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-formal-debt-collection-invoice.json", "test-demand-for-payment-type.json",
        "test-demand-for-payment.json"})
public class DemandForPaymentDaoWriteTestIT extends WriteTestBase {

    @EJB
    private DemandForPaymentDao dao;

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-demand-for-payment-type.json"})
    public void shouldStoreNewDemandForPayment() {
        // given
        final DemandForPayment demandForPayment = demandForPaymentWithoutIds1();

        // when
        dao.createDemandForPayment(demandForPayment);

        // then
        final Optional<DemandForPayment> newDemandForPayment = dao.get(DEMAND_FOR_PAYMENT_ID_100);
        assertTrue(newDemandForPayment.isPresent());
        assertThat(newDemandForPayment.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "dueDate", "issueDate", "invoices.realDueDate", "company.ratingDate", "company.externalDBAgreementDate")
                .usingComparatorForFields(SKIP_COMPARATOR, "invoices.id")
                .isEqualToComparingFieldByFieldRecursively(demandForPayment100());
    }

    @Test
    public void shouldDeleteNotIssuedDemandsForPayment() throws Exception {
        // given
        assertTrue(dao.get(DEMAND_FOR_PAYMENT_ID_1).isPresent());

        // and
        assertTrue(dao.get(DEMAND_FOR_PAYMENT_ID_3).isPresent());

        // and
        final Long stateChangeOperatorId = OperatorTestData.OPERATOR_ID_1;
        final Date stateChangeDate = parse("2018-02-21");
        final String stateChangeReason = "Powód odrzucenia wezwania";

        // when
        final int result = dao.rejectDemandsForPayment(asList(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ID_3), stateChangeOperatorId, stateChangeDate, stateChangeReason);
        assertEquals(1, result);
        commitTrx();

        // then
        final Optional<DemandForPayment> demand1 = dao.get(DEMAND_FOR_PAYMENT_ID_1);
        assertTrue(demand1.isPresent());
        assertEquals(REJECTED, demand1.get().getState());
        assertEquals(stateChangeOperatorId, demand1.get().getStateChangeOperator().getId());
        assertEquals(stateChangeDate, demand1.get().getStateChangeDate());
        assertEquals(stateChangeReason, demand1.get().getStateChangeReason());

        // and
        final Optional<DemandForPayment> demand3 = dao.get(DEMAND_FOR_PAYMENT_ID_3);
        assertTrue(demand3.isPresent());
        assertEquals(ISSUED, demand3.get().getState());
        assertNull(demand3.get().getStateChangeReason());
        assertNull(demand3.get().getStateChangeDate());
        assertNull(demand3.get().getStateChangeOperator());
    }

    @Test
    public void shouldProcessNotIssuedDemandsForPayment() throws Exception {
        // given
        final Optional<DemandForPayment> demand1 = dao.get(DEMAND_FOR_PAYMENT_ID_1);
        assertTrue(demand1.isPresent());
        assertEquals(NEW, demand1.get().getState());

        // and
        final Optional<DemandForPayment> demand3 = dao.get(DEMAND_FOR_PAYMENT_ID_3);
        assertTrue(demand3.isPresent());
        assertEquals(ISSUED, demand3.get().getState());

        // when
        final int result = dao.processDemandsForPayment(asList(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ID_3));
        assertEquals(1, result);
        commitTrx();

        // then
        final Optional<DemandForPayment> updatedDemand1 = dao.get(DEMAND_FOR_PAYMENT_ID_1);
        assertTrue(updatedDemand1.isPresent());
        assertEquals(WAITING, updatedDemand1.get().getState());

        // and
        final Optional<DemandForPayment> updatedDemand3 = dao.get(DEMAND_FOR_PAYMENT_ID_3);
        assertTrue(updatedDemand3.isPresent());
        assertEquals(ISSUED, updatedDemand3.get().getState());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-demand-for-payment-type.json"})
    public void shouldFindIssuedSecondDemandsForPaymentWithDueDate() throws Exception {
        // given
        // no demands for payment before setup
        assertTrue(dao.all().isEmpty());

        // and
        setupDemandsForPaymentForFindIssuedSecondDemandsForPaymentWithDueDate();

        // 24 demands for payment after data setup
        assertEquals(24, dao.all().size());

        final Date dueDate = parse("2012-10-10");

        // when
        final List<DemandForPaymentWithCompanyName> issuedSecondDemandsForPaymentWithDueDate = dao.findIssuedSecondDemandsForPaymentWithDueDate(dueDate);

        // then
        assertEquals(2, issuedSecondDemandsForPaymentWithDueDate.size());
        issuedSecondDemandsForPaymentWithDueDate.forEach(demandForPayment -> {
            assertEquals(SECOND, demandForPayment.getType().getKey());
            assertEquals(dueDate, demandForPayment.getDueDate());
        });
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-demand-for-payment-type.json", "test-demand-for-payment-with-aborted.json"})
    public void shouldFindLatestDemandForPaymentForContractWithoutAborted() {
        // when
        final Optional<DemandForPaymentWithCompanyName> latestDemandForPayment = dao.findLatestDemandForPayment(CONTRACT_NUMBER_INVOICE_15);

        // then
        assertTrue(latestDemandForPayment.isPresent());
        final DemandForPaymentWithCompanyName demandForPaymentWithCompanyName = latestDemandForPayment.get();
        assertFalse(demandForPaymentWithCompanyName.isAborted());
        assertEquals(DEMAND_FOR_PAYMENT_ID_1001, demandForPaymentWithCompanyName.getId());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-demand-for-payment-type.json", "test-demand-for-payment-with-aborted.json"})
    public void shouldFindIssuedSecondDemandsForPaymentWithDueDateWithoutAborted() {
        // when
        final List<DemandForPaymentWithCompanyName> demands = dao.findIssuedSecondDemandsForPaymentWithDueDate(parse("2017-04-03"));

        // then
        assertEquals(1, demands.size());
        final DemandForPaymentWithCompanyName demandForPayment = demands.get(0);
        assertEquals(DEMAND_FOR_PAYMENT_ID_1003, demandForPayment.getId());
        assertFalse(demandForPayment.isAborted());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-demand-for-payment-type.json", "test-demand-for-payment-with-aborted.json"})
    public void shouldFindDemandsForPaymentExcludingGivenWithoutAborted() {
        // when
        final List<DemandForPayment> demands = dao.findDemandsForPaymentExcludingGiven(FIRST, CONTRACT_NUMBER_INVOICE_15,
                INVOICE_NUMBER_15, DEMAND_FOR_PAYMENT_ID_1005);

        // then
        assertEquals(1, demands.size());
        final DemandForPayment demandForPayment = demands.get(0);
        assertEquals(DEMAND_FOR_PAYMENT_ID_1001, demandForPayment.getId());
        assertFalse(demandForPayment.isAborted());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-demand-for-payment-type.json", "test-demand-for-payment-with-aborted.json"})
    public void shouldFindDemandsForPaymentWithoutAborted() {
        // when
        final List<DemandForPayment> demands = dao.findDemandsForPayment(FIRST, CONTRACT_NUMBER_INVOICE_15, INVOICE_NUMBER_15);

        // then
        assertEquals(2, demands.size());
        final List<Long> demandIds = demands.stream().map(DemandForPayment::getId).collect(Collectors.toList());
        assertTrue(demandIds.containsAll(asList(DEMAND_FOR_PAYMENT_ID_1001, DEMAND_FOR_PAYMENT_ID_1005)));
    }

    /**
     * Przygotowanie danych do testu dao pobierania wystawionych wezwań 2 z datą płatności
     */
    private void setupDemandsForPaymentForFindIssuedSecondDemandsForPaymentWithDueDate() throws Exception {
        // ISSUED, due date 2012-10-09
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentA(), ISSUED, "1", parse("2012-10-09")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentA(), ISSUED, "2", parse("2012-10-09")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentB(), ISSUED, "3", parse("2012-10-09")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentB(), ISSUED, "4", parse("2012-10-09")));

        // ISSUED, due date 2012-10-10
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentA(), ISSUED, "5", parse("2012-10-10")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentA(), ISSUED, "6", parse("2012-10-10")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentB(), ISSUED, "7", parse("2012-10-10")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentB(), ISSUED, "8", parse("2012-10-10")));

        // ISSUED, due date 2012-10-11
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentA(), ISSUED, "9", parse("2012-10-11")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentA(), ISSUED, "10", parse("2012-10-11")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentB(), ISSUED, "11", parse("2012-10-11")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentB(), ISSUED, "12", parse("2012-10-11")));

        // NEW, due date 2012-10-09
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentA(), NEW, "13", parse("2012-10-09")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentA(), NEW, "14", parse("2012-10-09")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentB(), NEW, "15", parse("2012-10-09")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentB(), NEW, "16", parse("2012-10-09")));

        // NEW, due date 2012-10-10
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentA(), NEW, "17", parse("2012-10-10")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentA(), NEW, "18", parse("2012-10-10")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentB(), NEW, "19", parse("2012-10-10")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentB(), NEW, "20", parse("2012-10-10")));

        // NEW, due date 2012-10-11
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentA(), NEW, "21", parse("2012-10-11")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentA(), NEW, "22", parse("2012-10-11")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationFirstSegmentB(), NEW, "23", parse("2012-10-11")));
        dao.createDemandForPayment(prepareDemandForPayment(demandForPaymentTypeConfigurationSecondSegmentB(), NEW, "24", parse("2012-10-11")));

        commitTrx();
    }
}
