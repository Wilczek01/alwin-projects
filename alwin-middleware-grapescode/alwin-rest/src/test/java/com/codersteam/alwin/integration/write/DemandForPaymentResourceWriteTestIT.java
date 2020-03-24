package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentService;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.aida.core.api.model.CompanySegment.A;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.NEW;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.WAITING;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.integration.common.ResponseType.DEMANDS_FOR_PAYMENT_PAGE;
import static com.codersteam.alwin.integration.mock.ContractServiceMock.CONTRACT_TO_EXT_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.integration.mock.SegmentServiceMock.COMPANIES_SEGMENT;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_CONTRACT_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_CONTRACT_NO_2;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoiceWithCorrections1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoiceWithCorrections2;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-formal-debt-collection-invoice.json", "test-demand-for-payment-type.json",
        "test-demand-for-payment.json"})
public class DemandForPaymentResourceWriteTestIT extends WriteTestBase {

    @EJB
    private DemandForPaymentService demandForPaymentService;

    @Test
    public void shouldMarkDemandsForPayment() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final Optional<DemandForPaymentDto> demandForPaymentBeforeUpdate = demandForPaymentService.find(DEMAND_FOR_PAYMENT_ID_1);
        assertTrue(demandForPaymentBeforeUpdate.isPresent());
        assertEquals(NEW, demandForPaymentBeforeUpdate.get().getState());

        // when
        final int responseCode = checkHttpPatchCode("demands/" + DEMAND_FOR_PAYMENT_ID_1 + "/mark", loginToken);
        commitTrx();

        // then
        assertAccepted(responseCode);

        // and
        final Optional<DemandForPaymentDto> demandForPaymentAfterUpdate = demandForPaymentService.find(DEMAND_FOR_PAYMENT_ID_1);
        assertTrue(demandForPaymentAfterUpdate.isPresent());
        assertEquals(WAITING, demandForPaymentAfterUpdate.get().getState());
    }

    @Test
    public void shouldProcessDemandsOnTheList() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByState.json", sendHttpGet("demands?state="
                + DemandForPaymentState.NEW + "&createdManually=false&aborted=false", loginToken), DEMANDS_FOR_PAYMENT_PAGE);

        // and
        final JsonElement request = request("demand/processDemandForPayment.json");

        // when
        final HttpResponse response = checkHttpPost("demands/process", request, loginToken);
        commitTrx();

        // then
        assertNoContent(response);
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByStateAfterProcessing.json", sendHttpGet("demands?state="
                + DemandForPaymentState.NEW + "&createdManually=false&aborted=false", loginToken), DEMANDS_FOR_PAYMENT_PAGE);
    }

    @Test
    public void shouldCreateManualDemandsForPayment() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByStateBeforeManualCreation.json",
                sendHttpGet("demands?state=" + DemandForPaymentState.NEW + "&createdManually=true", loginToken), DEMANDS_FOR_PAYMENT_PAGE);

        // and
        CONTRACT_TO_EXT_COMPANY_ID.put(INVOICE_CONTRACT_NO_1, EXT_COMPANY_ID_1);
        CONTRACT_TO_EXT_COMPANY_ID.put(INVOICE_CONTRACT_NO_2, EXT_COMPANY_ID_2);

        // and
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_1, A);
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_2, A);

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_1, singletonList(dueInvoiceWithCorrections1()));
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_2, singletonList(dueInvoiceWithCorrections2()));

        // and
        final JsonElement request = request("demand/createDemandsForPayment.json");

        // when
        final HttpResponse response = checkHttpPost("demands", request, loginToken);
        commitTrx();

        // then
        assertResponseEquals("demand/createDemandsForPayment.json", response);

        // and
        assertResponseEquals("demand/pageOfDemandsForPaymentFilteredByStateAfterManualCreation.json",
                sendHttpGet("demands?state=" + DemandForPaymentState.NEW + "&createdManually=true", loginToken), DEMANDS_FOR_PAYMENT_PAGE);
    }
}
