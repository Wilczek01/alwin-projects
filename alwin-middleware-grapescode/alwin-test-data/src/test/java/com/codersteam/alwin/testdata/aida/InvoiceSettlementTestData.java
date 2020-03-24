package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.InvoiceSettlementDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.SettlementTestData.SETTLEMENT_ID_1;
import static com.codersteam.alwin.testdata.aida.SettlementTestData.SETTLEMENT_ID_2;
import static com.codersteam.alwin.testdata.aida.SettlementTestData.SETTLEMENT_ID_3;
import static com.codersteam.alwin.testdata.aida.SettlementTestData.SETTLEMENT_ID_4;
import static com.codersteam.alwin.testdata.aida.SettlementTestData.SETTLEMENT_ID_5;
import static java.util.Arrays.asList;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public class InvoiceSettlementTestData {

    private static final Long INVOICE_SETTLEMENT_SETTLEMENT_ID_1 = SETTLEMENT_ID_1;
    private static final BigDecimal INVOICE_SETTLEMENT_AMOUNT_SETTLED_1 = new BigDecimal("307.50");
    private static final String INVOICE_SETTLEMENT_SIDE_1 = "WN";
    private static final Date INVOICE_SETTLEMENT_SET_DATE_1 = parse("2018-10-12");
    private static final String INVOICE_SETTLEMENT_TITLE_1 = "626 Rata 2 - 001980/15/1";

    private static final Long INVOICE_SETTLEMENT_SETTLEMENT_ID_2 = SETTLEMENT_ID_2;
    private static final BigDecimal INVOICE_SETTLEMENT_AMOUNT_SETTLED_2 = new BigDecimal("5197.49");
    private static final String INVOICE_SETTLEMENT_SIDE_2 = "MA";
    private static final Date INVOICE_SETTLEMENT_SET_DATE_2 = parse("2018-11-02");
    private static final String INVOICE_SETTLEMENT_TITLE_2 = "czynsze w PLN";

    private static final Long INVOICE_SETTLEMENT_SETTLEMENT_ID_3 = SETTLEMENT_ID_3;
    private static final BigDecimal INVOICE_SETTLEMENT_AMOUNT_SETTLED_3 = new BigDecimal("65.50");
    private static final String INVOICE_SETTLEMENT_SIDE_3 = "WN";
    private static final Date INVOICE_SETTLEMENT_SET_DATE_3 = parse("2018-10-14");
    private static final String INVOICE_SETTLEMENT_TITLE_3 = "624 Wplata poczatkowa 000100/15/1";

    private static final Long INVOICE_SETTLEMENT_SETTLEMENT_ID_4 = SETTLEMENT_ID_4;
    private static final BigDecimal INVOICE_SETTLEMENT_AMOUNT_SETTLED_4 = new BigDecimal("309.69");
    private static final String INVOICE_SETTLEMENT_SIDE_4 = "MA";
    private static final Date INVOICE_SETTLEMENT_SET_DATE_4 = parse("2018-10-02");
    private static final String INVOICE_SETTLEMENT_TITLE_4 = "672 Zakup 002418/16/1";

    private static final Long INVOICE_SETTLEMENT_SETTLEMENT_ID_5 = SETTLEMENT_ID_5;
    private static final BigDecimal INVOICE_SETTLEMENT_AMOUNT_SETTLED_5 = new BigDecimal("715.45");
    private static final String INVOICE_SETTLEMENT_SIDE_5 = "WN";
    private static final Date INVOICE_SETTLEMENT_SET_DATE_5 = parse("2018-10-11");
    private static final String INVOICE_SETTLEMENT_TITLE_5 = "679 Inne fv - menager";


    public static List<InvoiceSettlementDto> invoiceSettlementDtos() {
        return asList(invoiceSettlementDto4(), invoiceSettlementDto5(), invoiceSettlementDto1(), invoiceSettlementDto3(), invoiceSettlementDto2());
    }

    public static InvoiceSettlementDto invoiceSettlementDto1() {
        return invoiceSettlementDto(INVOICE_SETTLEMENT_SETTLEMENT_ID_1, INVOICE_SETTLEMENT_AMOUNT_SETTLED_1, INVOICE_SETTLEMENT_SIDE_1,
                INVOICE_SETTLEMENT_SET_DATE_1, INVOICE_SETTLEMENT_TITLE_1);
    }

    public static InvoiceSettlementDto invoiceSettlementDto2() {
        return invoiceSettlementDto(INVOICE_SETTLEMENT_SETTLEMENT_ID_2, INVOICE_SETTLEMENT_AMOUNT_SETTLED_2, INVOICE_SETTLEMENT_SIDE_2,
                INVOICE_SETTLEMENT_SET_DATE_2, INVOICE_SETTLEMENT_TITLE_2);
    }

    public static InvoiceSettlementDto invoiceSettlementDto3() {
        return invoiceSettlementDto(INVOICE_SETTLEMENT_SETTLEMENT_ID_3, INVOICE_SETTLEMENT_AMOUNT_SETTLED_3, INVOICE_SETTLEMENT_SIDE_3,
                INVOICE_SETTLEMENT_SET_DATE_3, INVOICE_SETTLEMENT_TITLE_3);
    }

    public static InvoiceSettlementDto invoiceSettlementDto4() {
        return invoiceSettlementDto(INVOICE_SETTLEMENT_SETTLEMENT_ID_4, INVOICE_SETTLEMENT_AMOUNT_SETTLED_4, INVOICE_SETTLEMENT_SIDE_4,
                INVOICE_SETTLEMENT_SET_DATE_4, INVOICE_SETTLEMENT_TITLE_4);
    }

    public static InvoiceSettlementDto invoiceSettlementDto5() {
        return invoiceSettlementDto(INVOICE_SETTLEMENT_SETTLEMENT_ID_5, INVOICE_SETTLEMENT_AMOUNT_SETTLED_5, INVOICE_SETTLEMENT_SIDE_5,
                INVOICE_SETTLEMENT_SET_DATE_5, INVOICE_SETTLEMENT_TITLE_5);
    }

    private static InvoiceSettlementDto invoiceSettlementDto(final Long settlementId, final BigDecimal amountSettled, final String side, final Date setDate,
                                                             final String title) {
        final InvoiceSettlementDto invoiceSettlementDto = new InvoiceSettlementDto();
        invoiceSettlementDto.setSettlementId(settlementId);
        invoiceSettlementDto.setAmountSettled(amountSettled);
        invoiceSettlementDto.setSide(side);
        invoiceSettlementDto.setSetDate(setDate);
        invoiceSettlementDto.setTitle(title);
        return invoiceSettlementDto;
    }
}
