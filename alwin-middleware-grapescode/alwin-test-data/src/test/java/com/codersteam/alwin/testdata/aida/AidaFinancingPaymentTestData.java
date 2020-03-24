package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.FinancingPaymentDto;

import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_ID_5;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_ID_6;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_ID_7;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_ID_8;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_ID_9;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("ALL")
public final class AidaFinancingPaymentTestData {

    private static final Long FINANCING_PAYMENT_ID_4 = 4L;
    private static final Long FINANCING_PAYMENT_ID_5 = 5L;
    private static final Long FINANCING_PAYMENT_ID_6 = 6L;
    private static final Long FINANCING_PAYMENT_ID_7 = 7L;
    private static final Long FINANCING_PAYMENT_ID_8 = 8L;

    public static final FinancingPaymentDto FINANCING_PAYMENT_DTO_4 = financingPaymentDto(FINANCING_PAYMENT_ID_4, INVOICE_ID_5);
    public static final FinancingPaymentDto FINANCING_PAYMENT_DTO_5 = financingPaymentDto(FINANCING_PAYMENT_ID_5, INVOICE_ID_6);
    public static final FinancingPaymentDto FINANCING_PAYMENT_DTO_6 = financingPaymentDto(FINANCING_PAYMENT_ID_6, INVOICE_ID_7);
    public static final FinancingPaymentDto FINANCING_PAYMENT_DTO_7 = financingPaymentDto(FINANCING_PAYMENT_ID_7, INVOICE_ID_8);
    public static final FinancingPaymentDto FINANCING_PAYMENT_DTO_8 = financingPaymentDto(FINANCING_PAYMENT_ID_8, INVOICE_ID_9);

    private AidaFinancingPaymentTestData() {
    }

    private static FinancingPaymentDto financingPaymentDto(final Long id, final Long invoiceId) {
        final FinancingPaymentDto financingPayment = new FinancingPaymentDto();
        financingPayment.setId(id);
        financingPayment.setInvoiceId(invoiceId);
        return financingPayment;
    }
}
