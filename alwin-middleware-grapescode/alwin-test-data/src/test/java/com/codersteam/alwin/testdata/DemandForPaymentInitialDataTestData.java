package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.service.notice.DemandForPaymentInitialData;

import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationFirstSegmentA;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueSimpleInvoice1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueSimpleInvoice2;

/**
 * Dane testowe dla inicjalnych faktur do wystawienia wezwania
 *
 * @author Tomasz Sliwinski
 */
public final class DemandForPaymentInitialDataTestData {

    private DemandForPaymentInitialDataTestData() {
    }

    public static DemandForPaymentInitialData demandForPaymentInitialData1() {
        return new DemandForPaymentInitialData(demandForPaymentTypeConfigurationFirstSegmentA().getId(), dueSimpleInvoice1().getCompanyId(), dueSimpleInvoice1().getContractNo(),
                dueSimpleInvoice1().getNumber());
    }

    public static DemandForPaymentInitialData demandForPaymentInitialData2() {
        return new DemandForPaymentInitialData(demandForPaymentTypeConfigurationFirstSegmentA().getId(), dueSimpleInvoice2().getCompanyId(), dueSimpleInvoice2().getContractNo(),
                dueSimpleInvoice2().getNumber());
    }

}
