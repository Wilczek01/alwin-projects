package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import com.codersteam.alwin.testdata.aida.AidaInvoiceTestData;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;

/**
 * Dane testowe faktur w procesie windykacji formalnej
 */
@SuppressWarnings("WeakerAccess")
public class FormalDebtCollectionInvoiceTestData {

    public static final Long ID_1 = 101L;
    public static final Long ID_2 = 102L;
    public static final Long ID_3 = 103L;
    public static final Long ID_4 = 104L;
    public static final Long ID_5 = 105L;
    public static final Long ID_10 = 10L;
    public static final Long ID_11 = 11L;
    public static final Long ID_15 = 15L;
    public static final Long ID_16 = 16L;
    public static final Long ID_17 = 17L;
    public static final Long ID_18 = 18L;
    public static final Long ID_25 = 25L;
    public static final Long ID_100 = 100L;

    public static final String INVOICE_NUMBER_1 = "FV/001/2017";
    public static final String INVOICE_NUMBER_2 = "FV/002/2017";
    public static final String INVOICE_NUMBER_3 = "FV/2018/104";
    public static final String INVOICE_NUMBER_4 = "FV/001/2018";

    public static final String CONTRACT_NUMBER_1 = "CONTRACT/01";
    public static final String CONTRACT_NUMBER_2 = "CONTRACT/02";
    public static final String CONTRACT_NUMBER_3 = "CONTRACT/03";
    public static final String CONTRACT_NUMBER_4 = "CONTRACT/04";

    public static final Date ISSUE_DATE_1 = parse("2017-11-10");
    public static final Date ISSUE_DATE_2 = parse("2017-03-10");
    public static final Date ISSUE_DATE_3 = parse("2017-06-10");
    public static final Date ISSUE_DATE_4 = parse("2018-11-10");
    public static final Date ISSUE_DATE_10 = parse("2018-11-10");
    public static final Date ISSUE_DATE_11 = parse("2018-11-12");
    public static final Date ISSUE_DATE_15 = parse("2018-11-15");
    public static final Date ISSUE_DATE_16 = parse("2018-11-12");
    public static final Date ISSUE_DATE_17 = parse("2018-11-15");
    public static final Date ISSUE_DATE_18 = parse("2018-11-10");
    public static final Date ISSUE_DATE_25 = parse("2018-11-16");

    public static final Date DUE_DATE_1 = parse("2017-11-24");
    public static final Date DUE_DATE_2 = parse("2017-03-24");
    public static final Date DUE_DATE_3 = parse("2017-06-24");
    public static final Date DUE_DATE_4 = parse("2018-11-24");

    public static final String CURRENCY_1 = "PLN";
    public static final String CURRENCY_2 = "PLN";
    public static final String CURRENCY_3 = "PLN";
    public static final String CURRENCY_4 = "PLN";

    public static final BigDecimal NET_AMOUNT_1 = new BigDecimal("1000.00");
    public static final BigDecimal NET_AMOUNT_2 = new BigDecimal("10000.00");
    public static final BigDecimal NET_AMOUNT_3 = new BigDecimal("5000.00");
    public static final BigDecimal NET_AMOUNT_4 = new BigDecimal("100000.00");

    public static final BigDecimal GROSS_AMOUNT_1 = new BigDecimal("1230.00");
    public static final BigDecimal GROSS_AMOUNT_2 = new BigDecimal("12300.00");
    public static final BigDecimal GROSS_AMOUNT_3 = new BigDecimal("6150.00");
    public static final BigDecimal GROSS_AMOUNT_4 = new BigDecimal("123000.00");

    public static final BigDecimal CURRENT_BALANCE_1 = new BigDecimal("500.00");
    public static final BigDecimal CURRENT_BALANCE_2 = new BigDecimal("9000.00");
    public static final BigDecimal CURRENT_BALANCE_3 = new BigDecimal("3000.00");
    public static final BigDecimal CURRENT_BALANCE_4 = new BigDecimal("80000.00");

    public static final Long LEO_ID_1 = 1101L;
    public static final Long LEO_ID_2 = 1102L;
    public static final Long LEO_ID_3 = 1103L;
    public static final Long LEO_ID_4 = 1104L;
    public static final Long LEO_ID_5 = 5L;
    public static final Long LEO_ID_10 = 11010L;
    public static final Long LEO_ID_11 = 11011L;
    public static final Long LEO_ID_15 = 11015L;
    public static final Long LEO_ID_16 = 11011L;
    public static final Long LEO_ID_17 = 11015L;
    public static final Long LEO_ID_18 = 11010L;
    public static final Long LEO_ID_25 = 11025L;

    public static final List<FormalDebtCollectionInvoice> ALL_FORMAL_DEBT_COLLECTION_INVOICES = asList(
            formalDebtCollectionInvoice1(), formalDebtCollectionInvoice2(), formalDebtCollectionInvoice3(), formalDebtCollectionInvoice4(),
            formalDebtCollectionInvoice10(), formalDebtCollectionInvoice11(), formalDebtCollectionInvoice15(), formalDebtCollectionInvoice16(),
            formalDebtCollectionInvoice17(), formalDebtCollectionInvoice18(), formalDebtCollectionInvoice25());

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice1() {
        return formalDebtCollectionInvoice(ID_1, INVOICE_NUMBER_1, CONTRACT_NUMBER_1, ISSUE_DATE_1, DUE_DATE_1, CURRENCY_1,
                NET_AMOUNT_1, GROSS_AMOUNT_1, CURRENT_BALANCE_1, LEO_ID_1);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto1() {
        return formalDebtCollectionInvoiceDto(ID_1, INVOICE_NUMBER_1, CONTRACT_NUMBER_1, ISSUE_DATE_1, DUE_DATE_1, CURRENCY_1,
                NET_AMOUNT_1, GROSS_AMOUNT_1, CURRENT_BALANCE_1, LEO_ID_1);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice2() {
        return formalDebtCollectionInvoice(ID_2, INVOICE_NUMBER_2, CONTRACT_NUMBER_2, ISSUE_DATE_2, DUE_DATE_2, CURRENCY_2,
                NET_AMOUNT_2, GROSS_AMOUNT_2, CURRENT_BALANCE_2, LEO_ID_2);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto2() {
        return formalDebtCollectionInvoiceDto(ID_2, INVOICE_NUMBER_2, CONTRACT_NUMBER_2, ISSUE_DATE_2, DUE_DATE_2, CURRENCY_2,
                NET_AMOUNT_2, GROSS_AMOUNT_2, CURRENT_BALANCE_2, LEO_ID_2);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice3() {
        return formalDebtCollectionInvoice(ID_3, INVOICE_NUMBER_3, CONTRACT_NUMBER_3, ISSUE_DATE_3, DUE_DATE_3, CURRENCY_3,
                NET_AMOUNT_3, GROSS_AMOUNT_3, CURRENT_BALANCE_3, LEO_ID_3);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto3() {
        return formalDebtCollectionInvoiceDto(ID_3, INVOICE_NUMBER_3, CONTRACT_NUMBER_3, ISSUE_DATE_3, DUE_DATE_3, CURRENCY_3,
                NET_AMOUNT_3, GROSS_AMOUNT_3, CURRENT_BALANCE_3, LEO_ID_3);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice4() {
        return formalDebtCollectionInvoice(ID_4, INVOICE_NUMBER_4, CONTRACT_NUMBER_4, ISSUE_DATE_4, DUE_DATE_4, CURRENCY_4,
                NET_AMOUNT_4, GROSS_AMOUNT_4, CURRENT_BALANCE_4, LEO_ID_4);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto4() {
        return formalDebtCollectionInvoiceDto(ID_4, INVOICE_NUMBER_4, CONTRACT_NUMBER_4, ISSUE_DATE_4, DUE_DATE_4, CURRENCY_4,
                NET_AMOUNT_4, GROSS_AMOUNT_4, CURRENT_BALANCE_4, LEO_ID_4);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice5() {
        return formalDebtCollectionInvoice(ID_5, AidaInvoiceTestData.INVOICE_NUMBER_5, AidaInvoiceTestData.INVOICE_CONTRACT_NO_5,
                AidaInvoiceTestData.ISSUE_DATE_5, AidaInvoiceTestData.DUE_DATE_5, AidaInvoiceTestData.CURRENCY_5, AidaInvoiceTestData.NET_AMOUNT_5,
                AidaInvoiceTestData.GROSS_AMOUNT_5, AidaInvoiceTestData.INVOICE_BALANCE_5, LEO_ID_5);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice10() {
        return formalDebtCollectionInvoice(ID_10, InvoiceTestData.INVOICE_NUMBER_10, InvoiceTestData.CONTRACT_NUMBER_INVOICE_10,
                ISSUE_DATE_10, InvoiceTestData.DUE_DATE_INVOICE_10, InvoiceTestData.CURRENCY_INVOICE_10,
                InvoiceTestData.NET_AMOUNT_INVOICE_10, InvoiceTestData.GROSS_AMOUNT_INVOICE_10, InvoiceTestData.CURRENT_BALANCE_INVOICE_10, LEO_ID_10);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice11() {
        return formalDebtCollectionInvoice(ID_11, InvoiceTestData.INVOICE_NUMBER_11, InvoiceTestData.CONTRACT_NUMBER_INVOICE_11,
                ISSUE_DATE_11, InvoiceTestData.DUE_DATE_INVOICE_11, InvoiceTestData.CURRENCY_INVOICE_11,
                InvoiceTestData.NET_AMOUNT_INVOICE_11, InvoiceTestData.GROSS_AMOUNT_INVOICE_11, InvoiceTestData.CURRENT_BALANCE_INVOICE_11, LEO_ID_11);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto11() {
        return formalDebtCollectionInvoiceDto(ID_11, InvoiceTestData.INVOICE_NUMBER_11, InvoiceTestData.CONTRACT_NUMBER_INVOICE_11,
                ISSUE_DATE_11, InvoiceTestData.DUE_DATE_INVOICE_11, InvoiceTestData.CURRENCY_INVOICE_11,
                InvoiceTestData.NET_AMOUNT_INVOICE_11, InvoiceTestData.GROSS_AMOUNT_INVOICE_11, InvoiceTestData.CURRENT_BALANCE_INVOICE_11, LEO_ID_11);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice15() {
        return formalDebtCollectionInvoice(ID_15, InvoiceTestData.INVOICE_NUMBER_15, InvoiceTestData.CONTRACT_NUMBER_INVOICE_15,
                ISSUE_DATE_15, InvoiceTestData.DUE_DATE_INVOICE_15, InvoiceTestData.CURRENCY_INVOICE_15,
                InvoiceTestData.NET_AMOUNT_INVOICE_15, InvoiceTestData.GROSS_AMOUNT_INVOICE_15, InvoiceTestData.CURRENT_BALANCE_INVOICE_15, LEO_ID_15);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto15() {
        return formalDebtCollectionInvoiceDto(ID_15, InvoiceTestData.INVOICE_NUMBER_15, InvoiceTestData.CONTRACT_NUMBER_INVOICE_15,
                ISSUE_DATE_15, InvoiceTestData.DUE_DATE_INVOICE_15, InvoiceTestData.CURRENCY_INVOICE_15,
                InvoiceTestData.NET_AMOUNT_INVOICE_15, InvoiceTestData.GROSS_AMOUNT_INVOICE_15, InvoiceTestData.CURRENT_BALANCE_INVOICE_15, LEO_ID_15);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice16() {
        return formalDebtCollectionInvoice(ID_16, InvoiceTestData.INVOICE_NUMBER_11, InvoiceTestData.CONTRACT_NUMBER_INVOICE_11,
                ISSUE_DATE_16, InvoiceTestData.DUE_DATE_INVOICE_11, InvoiceTestData.CURRENCY_INVOICE_11,
                InvoiceTestData.NET_AMOUNT_INVOICE_11, InvoiceTestData.GROSS_AMOUNT_INVOICE_11, InvoiceTestData.CURRENT_BALANCE_INVOICE_11, LEO_ID_16);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto16() {
        return formalDebtCollectionInvoiceDto(ID_16, InvoiceTestData.INVOICE_NUMBER_11, InvoiceTestData.CONTRACT_NUMBER_INVOICE_11,
                ISSUE_DATE_16, InvoiceTestData.DUE_DATE_INVOICE_11, InvoiceTestData.CURRENCY_INVOICE_11,
                InvoiceTestData.NET_AMOUNT_INVOICE_11, InvoiceTestData.GROSS_AMOUNT_INVOICE_11, InvoiceTestData.CURRENT_BALANCE_INVOICE_11, LEO_ID_16);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice17() {
        return formalDebtCollectionInvoice(ID_17, InvoiceTestData.INVOICE_NUMBER_15, InvoiceTestData.CONTRACT_NUMBER_INVOICE_15,
                ISSUE_DATE_17, InvoiceTestData.DUE_DATE_INVOICE_15, InvoiceTestData.CURRENCY_INVOICE_15,
                InvoiceTestData.NET_AMOUNT_INVOICE_15, InvoiceTestData.GROSS_AMOUNT_INVOICE_15, InvoiceTestData.CURRENT_BALANCE_INVOICE_15, LEO_ID_17);
    }

    public static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto17() {
        return formalDebtCollectionInvoiceDto(ID_17, InvoiceTestData.INVOICE_NUMBER_15, InvoiceTestData.CONTRACT_NUMBER_INVOICE_15,
                ISSUE_DATE_17, InvoiceTestData.DUE_DATE_INVOICE_15, InvoiceTestData.CURRENCY_INVOICE_15,
                InvoiceTestData.NET_AMOUNT_INVOICE_15, InvoiceTestData.GROSS_AMOUNT_INVOICE_15, InvoiceTestData.CURRENT_BALANCE_INVOICE_15, LEO_ID_17);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice18() {
        return formalDebtCollectionInvoice(ID_18, InvoiceTestData.INVOICE_NUMBER_10, InvoiceTestData.CONTRACT_NUMBER_INVOICE_10,
                ISSUE_DATE_18, InvoiceTestData.DUE_DATE_INVOICE_10, InvoiceTestData.CURRENCY_INVOICE_10,
                InvoiceTestData.NET_AMOUNT_INVOICE_10, InvoiceTestData.GROSS_AMOUNT_INVOICE_10, InvoiceTestData.CURRENT_BALANCE_INVOICE_10, LEO_ID_18);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice25() {
        return formalDebtCollectionInvoice(ID_25, InvoiceTestData.INVOICE_NUMBER_25, InvoiceTestData.CONTRACT_NUMBER_INVOICE_25,
                ISSUE_DATE_25, InvoiceTestData.DUE_DATE_INVOICE_25, InvoiceTestData.CURRENCY_INVOICE_25,
                InvoiceTestData.NET_AMOUNT_INVOICE_25, InvoiceTestData.GROSS_AMOUNT_INVOICE_25, InvoiceTestData.CURRENT_BALANCE_INVOICE_25, LEO_ID_25);
    }

    public static FormalDebtCollectionInvoice formalDebtCollectionInvoice100() {
        return formalDebtCollectionInvoice(ID_100, AidaInvoiceTestData.INVOICE_NUMBER_1, AidaInvoiceTestData.INVOICE_CONTRACT_NO_1,
                AidaInvoiceTestData.ISSUE_DATE_1, AidaInvoiceTestData.DUE_DATE_1, AidaInvoiceTestData.CURRENCY_1,
                AidaInvoiceTestData.NET_AMOUNT_1, AidaInvoiceTestData.GROSS_AMOUNT_1, AidaInvoiceTestData.INVOICE_BALANCE_1, AidaInvoiceTestData.INVOICE_ID_1);
    }

    private static FormalDebtCollectionInvoice formalDebtCollectionInvoice(final Long id, final String invoiceNumber, final String contractNumber,
                                                                           final Date issueDate, final Date dueDate, final String currency,
                                                                           final BigDecimal netAmount, final BigDecimal grossAmount,
                                                                           final BigDecimal currentBalance, final Long leoId) {
        final FormalDebtCollectionInvoice invoice = new FormalDebtCollectionInvoice();
        invoice.setId(id);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setContractNumber(contractNumber);
        invoice.setIssueDate(issueDate);
        invoice.setRealDueDate(dueDate);
        invoice.setCurrency(currency);
        invoice.setNetAmount(netAmount);
        invoice.setGrossAmount(grossAmount);
        invoice.setCurrentBalance(currentBalance);
        invoice.setLeoId(leoId);
        return invoice;
    }

    private static FormalDebtCollectionInvoiceDto formalDebtCollectionInvoiceDto(final Long id, final String invoiceNumber, final String contractNumber,
                                                                                 final Date issueDate, final Date dueDate, final String currency,
                                                                                 final BigDecimal netAmount, final BigDecimal grossAmount,
                                                                                 final BigDecimal currentBalance, final Long leoId) {
        final FormalDebtCollectionInvoiceDto invoice = new FormalDebtCollectionInvoiceDto();
        invoice.setId(id);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setContractNumber(contractNumber);
        invoice.setIssueDate(issueDate);
        invoice.setRealDueDate(dueDate);
        invoice.setCurrency(currency);
        invoice.setNetAmount(netAmount);
        invoice.setGrossAmount(grossAmount);
        invoice.setCurrentBalance(currentBalance);
        invoice.setLeoId(leoId);
        return invoice;
    }

}
