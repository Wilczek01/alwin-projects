package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.InvoiceEntryDto;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Testowe pozycje faktury
 *
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public class AidaInvoiceEntryTestData {

    private static final Long INVOICE_ID_1_1 = 1L;
    private static final Long POSITION_NUMBER_1_1 = 1L;
    private static final String QUANTITY_UNIT_1_1 = "szt.";
    private static final BigDecimal QUANTITY_1_1 = new BigDecimal("1");
    private static final String VAT_RATE_1_1 = "23";
    private static final BigDecimal NET_AMOUNT_1_1 = new BigDecimal("1752.31");
    private static final BigDecimal VAT_AMOUNT_1_1 = new BigDecimal("403.03");
    private static final String PAYMENT_TITLE_1_1 = "Rata leasingowa nr 10 - Odsetki";

    private static final Long INVOICE_ID_1_2 = 1L;
    private static final Long POSITION_NUMBER_1_2 = 2L;
    private static final String QUANTITY_UNIT_1_2 = "szt.";
    private static final BigDecimal QUANTITY_1_2 = new BigDecimal("2");
    private static final String VAT_RATE_1_2 = "ZW";
    private static final BigDecimal NET_AMOUNT_1_2 = new BigDecimal("100.00");
    private static final BigDecimal VAT_AMOUNT_1_2 = new BigDecimal("0.00");
    private static final String PAYMENT_TITLE_1_2 = "Wysłanie wezwania do zapłaty";

    private static final Long INVOICE_ID_1_3 = 1L;
    private static final Long POSITION_NUMBER_1_3 = 3L;
    private static final String QUANTITY_UNIT_1_3 = "szt.";
    private static final BigDecimal QUANTITY_1_3 = new BigDecimal("3");
    private static final String VAT_RATE_1_3 = null;
    private static final BigDecimal NET_AMOUNT_1_3 = new BigDecimal("150.00");
    private static final BigDecimal VAT_AMOUNT_1_3 = new BigDecimal("0.00");
    private static final String PAYMENT_TITLE_1_3 = "Opłata za sprawdzenie w CRZ";

    public static InvoiceEntryDto invoiceEntryDto_1_1() {
        return invoiceEntryDto(INVOICE_ID_1_1, POSITION_NUMBER_1_1, PAYMENT_TITLE_1_1, QUANTITY_UNIT_1_1, QUANTITY_1_1, NET_AMOUNT_1_1, VAT_RATE_1_1,
                VAT_AMOUNT_1_1);
    }

    public static InvoiceEntryDto invoiceEntryDto_1_2() {
        return invoiceEntryDto(INVOICE_ID_1_2, POSITION_NUMBER_1_2, PAYMENT_TITLE_1_2, QUANTITY_UNIT_1_2, QUANTITY_1_2, NET_AMOUNT_1_2, VAT_RATE_1_2,
                VAT_AMOUNT_1_2);
    }

    public static InvoiceEntryDto invoiceEntryDto_1_3() {
        return invoiceEntryDto(INVOICE_ID_1_3, POSITION_NUMBER_1_3, PAYMENT_TITLE_1_3, QUANTITY_UNIT_1_3, QUANTITY_1_3, NET_AMOUNT_1_3, VAT_RATE_1_3,
                VAT_AMOUNT_1_3);
    }

    public static List<InvoiceEntryDto> invoiceEntries() {
        return asList(invoiceEntryDto_1_1(), invoiceEntryDto_1_2(), invoiceEntryDto_1_3());
    }

    private static InvoiceEntryDto invoiceEntryDto(final Long invoiceId, final Long positionNo, final String paymentTitle, final String quantityUnit,
                                                   final BigDecimal quantity, final BigDecimal netAmount, final String vatRate, final BigDecimal vatAmount) {
        final InvoiceEntryDto invoiceEntry = new InvoiceEntryDto();
        invoiceEntry.setInvoiceId(invoiceId);
        invoiceEntry.setPositionNumber(positionNo);
        invoiceEntry.setPaymentTitle(paymentTitle);
        invoiceEntry.setQuantityUnit(quantityUnit);
        invoiceEntry.setQuantity(quantity);
        invoiceEntry.setNetAmount(netAmount);
        invoiceEntry.setVatRate(vatRate);
        invoiceEntry.setVatAmount(vatAmount);
        return invoiceEntry;
    }
}
