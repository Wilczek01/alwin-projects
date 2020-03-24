package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.service.impl.notice.CreateDemandForPaymentService;
import com.codersteam.alwin.db.dao.DemandForPaymentDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Ignore;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.NEW;
import static com.codersteam.alwin.integration.mock.CurrencyExchangeRateServiceMock.EUR_EXCHANGE_RATE_BY_DATE;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.CURRENT_EUR_EXCHANGE_RATE;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMANDS_IN_NEW_STATE_COUNT;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-demand-for-payment-type.json", "test-demand-for-payment.json"})
@Ignore("TODO #29016")
public class DemandForPaymentServiceImplTestIT extends CoreTestBase {

    @Inject
    private CreateDemandForPaymentService service;

    @Inject
    private DemandForPaymentDao demandForPaymentDao;

    @Test
    public void shouldPrepareDemandsForPayments() {
        // given
        CURRENT_DATE = "2020-12-01";

        // and
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), CURRENT_EUR_EXCHANGE_RATE);

        // and
        final int demandsForPaymentsBeforeCount = demandForPaymentDao.all().size();

        // when
        service.prepareDemandsForPayment();

        // then
        final int preparedDemands = demandForPaymentDao.all().size() - demandsForPaymentsBeforeCount;
        assertEquals(6, preparedDemands);

        // and
        final List<DemandForPaymentWithCompanyName> demandsForPaymentsInNewState = demandForPaymentDao.findAllDemandsForPaymentByState(NEW);
        assertEquals(preparedDemands + DEMANDS_IN_NEW_STATE_COUNT, demandsForPaymentsInNewState.size());
        demandsForPaymentsInNewState.forEach(this::assertDemandForPayment);
    }

    private void assertDemandForPayment(final DemandForPayment demandForPayment) {
        final List<String> invoiceNumbers = demandForPayment.getInvoices().stream().map(FormalDebtCollectionInvoice::getInvoiceNumber).collect(Collectors.toList());
        assertTrue(invoiceNumbers.contains(demandForPayment.getInitialInvoiceNo()));
        demandForPayment.getInvoices().forEach(invoice -> assertTrue(ZERO.compareTo(invoice.getCurrentBalance()) > 0));
    }
}
