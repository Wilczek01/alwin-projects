package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.service.notice.ManualDemandForPaymentInitialData;

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueSimpleInvoice1;

/**
 * @author Tomasz Sliwinski
 */
public final class ManualDemandForPaymentInitialDataTestData {

    private ManualDemandForPaymentInitialDataTestData() {
    }

    public static ManualDemandForPaymentInitialData manualDemandForPaymentInitialData1() {
        return new ManualDemandForPaymentInitialData(FIRST, dueSimpleInvoice1().getCompanyId(), dueSimpleInvoice1().getContractNo());
    }
}
