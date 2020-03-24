package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.InvoiceCorrectionDto;
import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue21;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class InvoiceTestData {

    public static final long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private static final long ID_3 = 3L;
    private static final long ID_4 = 4L;
    private static final long ID_5 = 5L;
    private static final long ID_6 = 6L;
    public static final long ID_10 = 10L;
    public static final long ID_11 = 11L;
    private static final long ID_12 = 12L;
    private static final long ID_13 = 13L;
    private static final long ID_14 = 14L;
    public static final long ID_15 = 15L;
    public static final long ID_25 = 25L;
    private static final long ID_26 = 26L;
    private static final long ID_27 = 27L;
    private static final long ID_28 = 28L;
    public static final long ID_29 = 29L;
    private static final long ID_30 = 30L;
    private static final long ID_31 = 31L;
    public static final long ID_100 = 100L;

    public static final Date LAST_PAYMENT_DATE_INVOICE_1 = parse("2015-12-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_INVOICE_2 = parse("2015-12-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_INVOICE_3 = parse("2015-11-13 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_INVOICE_4 = parse("2015-11-14 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_INVOICE_5 = parse("2015-11-14 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_INVOICE_6 = parse("2015-11-14 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_INVOICE_10 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_11 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_12 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_13 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_14 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_15 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_25 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_26 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_27 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_28 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_29 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_30 = null;
    public static final Date LAST_PAYMENT_DATE_INVOICE_31 = null;

    public static final BigDecimal NET_AMOUNT_INVOICE_1 = new BigDecimal("300.00");
    public static final BigDecimal NET_AMOUNT_INVOICE_2 = new BigDecimal("375.00");
    public static final BigDecimal NET_AMOUNT_INVOICE_3 = new BigDecimal("490.73");
    public static final BigDecimal NET_AMOUNT_INVOICE_4 = new BigDecimal("1490.73");
    public static final BigDecimal NET_AMOUNT_INVOICE_5 = new BigDecimal("190.73");
    public static final BigDecimal NET_AMOUNT_INVOICE_6 = new BigDecimal("1490.73");
    public static final BigDecimal NET_AMOUNT_INVOICE_10 = new BigDecimal("154.66");
    public static final BigDecimal NET_AMOUNT_INVOICE_11 = new BigDecimal("100.91");
    public static final BigDecimal NET_AMOUNT_INVOICE_12 = new BigDecimal("100.91");
    public static final BigDecimal NET_AMOUNT_INVOICE_13 = new BigDecimal("100.91");
    public static final BigDecimal NET_AMOUNT_INVOICE_14 = new BigDecimal("100.91");
    public static final BigDecimal NET_AMOUNT_INVOICE_15 = new BigDecimal("100.91");
    public static final BigDecimal NET_AMOUNT_INVOICE_25 = new BigDecimal("1009.10");
    public static final BigDecimal NET_AMOUNT_INVOICE_26 = new BigDecimal("162.60");
    public static final BigDecimal NET_AMOUNT_INVOICE_27 = new BigDecimal("162.60");
    public static final BigDecimal NET_AMOUNT_INVOICE_28 = new BigDecimal("81.30");
    public static final BigDecimal NET_AMOUNT_INVOICE_29 = new BigDecimal("290.00");
    public static final BigDecimal NET_AMOUNT_INVOICE_30 = new BigDecimal("0.00");
    public static final BigDecimal NET_AMOUNT_INVOICE_31 = new BigDecimal("310.00");

    public static final BigDecimal GROSS_AMOUNT_INVOICE_1 = new BigDecimal("369.00");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_2 = new BigDecimal("461.25");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_3 = new BigDecimal("490.73");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_4 = new BigDecimal("342.87");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_5 = new BigDecimal("43.87");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_6 = new BigDecimal("342.87");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_10 = new BigDecimal("190.23");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_11 = new BigDecimal("124.12");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_12 = new BigDecimal("124.12");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_13 = new BigDecimal("124.12");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_14 = new BigDecimal("124.12");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_15 = new BigDecimal("124.12");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_25 = new BigDecimal("1241.20");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_26 = new BigDecimal("200.00");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_27 = new BigDecimal("200.00");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_28 = new BigDecimal("100.00");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_29 = new BigDecimal("356.70");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_30 = new BigDecimal("0.00");
    public static final BigDecimal GROSS_AMOUNT_INVOICE_31 = new BigDecimal("381.30");

    public static final String INVOICE_NUMBER_1 = "000015/12/2015/TO";
    public static final String INVOICE_NUMBER_2 = "000016/12/2015/TO";
    public static final String INVOICE_NUMBER_3 = "000006/11/2015/RL/PO/LW";
    public static final String INVOICE_NUMBER_4 = "000004/12/2015/RL/PO/LW";
    public static final String INVOICE_NUMBER_5 = "005555/12/2015/RL/PO/LW";
    public static final String INVOICE_NUMBER_6 = "006666/12/2015/RL/PO/LW";
    public static final String INVOICE_NUMBER_10 = "000001/12/2020/RL/PO/LW";
    public static final String INVOICE_NUMBER_11 = "000001/10/2024/RL/PO/LW";
    public static final String INVOICE_NUMBER_12 = "000001/07/2020/RL/PO/LW";
    public static final String INVOICE_NUMBER_13 = "000001/08/2020/RL/PO/LW";
    public static final String INVOICE_NUMBER_14 = "000001/09/2020/RL/PO/LW";
    public static final String INVOICE_NUMBER_15 = "000001/10/2020/RL/PO/LW";
    public static final String INVOICE_NUMBER_25 = "000025/10/2024/RL/PO/LW";
    public static final String INVOICE_NUMBER_26 = "000026/10/2024/RL/PO/LW";
    public static final String INVOICE_NUMBER_27 = "000027/10/2024/RL/PO/LW";
    public static final String INVOICE_NUMBER_28 = "000028/10/2024/RL/PO/LW";
    public static final String INVOICE_NUMBER_29 = "000029/12/2015/KF";
    public static final String INVOICE_NUMBER_30 = "000030/12/2015/KF";
    public static final String INVOICE_NUMBER_31 = "000031/12/2015/KF";
    public static final String INVOICE_NUMBER_32 = "000032/12/2015/KF";

    public static final String CURRENCY_INVOICE_1 = "PLN";
    public static final String CURRENCY_INVOICE_2 = "PLN";
    public static final String CURRENCY_INVOICE_3 = "EUR";
    public static final String CURRENCY_INVOICE_4 = "EUR";
    public static final String CURRENCY_INVOICE_5 = "EUR";
    public static final String CURRENCY_INVOICE_6 = "EUR";
    public static final String CURRENCY_INVOICE_10 = "EUR";
    public static final String CURRENCY_INVOICE_11 = "EUR";
    public static final String CURRENCY_INVOICE_12 = "PLN";
    public static final String CURRENCY_INVOICE_13 = "PLN";
    public static final String CURRENCY_INVOICE_14 = "PLN";
    public static final String CURRENCY_INVOICE_15 = "PLN";
    public static final String CURRENCY_INVOICE_25 = "PLN";
    public static final String CURRENCY_INVOICE_26 = "EUR";
    public static final String CURRENCY_INVOICE_27 = "EUR";
    public static final String CURRENCY_INVOICE_28 = "EUR";
    public static final String CURRENCY_INVOICE_29 = "PLN";
    public static final String CURRENCY_INVOICE_30 = "PLN";
    public static final String CURRENCY_INVOICE_31 = "PLN";

    public static final BigDecimal EXCHANGE_RATE_1 = null;
    public static final BigDecimal EXCHANGE_RATE_2 = null;
    public static final BigDecimal EXCHANGE_RATE_3 = new BigDecimal("4.1000");
    public static final BigDecimal EXCHANGE_RATE_4 = new BigDecimal("4.2015");
    public static final BigDecimal EXCHANGE_RATE_5 = new BigDecimal("4.0000");
    public static final BigDecimal EXCHANGE_RATE_6 = new BigDecimal("4.0000");
    public static final BigDecimal EXCHANGE_RATE_10 = new BigDecimal("4.5012");
    public static final BigDecimal EXCHANGE_RATE_11 = new BigDecimal("3.9000");
    public static final BigDecimal EXCHANGE_RATE_12 = new BigDecimal("1.0000");
    public static final BigDecimal EXCHANGE_RATE_13 = null;
    public static final BigDecimal EXCHANGE_RATE_14 = null;
    public static final BigDecimal EXCHANGE_RATE_15 = new BigDecimal("1.0000");
    public static final BigDecimal EXCHANGE_RATE_25 = new BigDecimal("1.0000");
    public static final BigDecimal EXCHANGE_RATE_26 = new BigDecimal("4.3501");
    public static final BigDecimal EXCHANGE_RATE_27 = new BigDecimal("4.2400");
    public static final BigDecimal EXCHANGE_RATE_28 = null;
    public static final BigDecimal EXCHANGE_RATE_29 = null;
    public static final BigDecimal EXCHANGE_RATE_30 = new BigDecimal("1.0000");
    public static final BigDecimal EXCHANGE_RATE_31 = null;

    public static final BigDecimal CURRENT_BALANCE_INVOICE_1 = new BigDecimal("0.00");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_2 = new BigDecimal("0.00");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_3 = new BigDecimal("100.00");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_4 = new BigDecimal("-502.23");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_5 = new BigDecimal("-805.14");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_6 = new BigDecimal("228.00");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_10 = new BigDecimal("-190.23");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_11 = new BigDecimal("-124.12");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_12 = new BigDecimal("-124.12");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_13 = new BigDecimal("-124.12");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_14 = new BigDecimal("-124.12");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_15 = new BigDecimal("-124.12");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_25 = new BigDecimal("-1241.20");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_26 = new BigDecimal("-23.69");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_27 = new BigDecimal("-23.70");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_28 = new BigDecimal("-23.69");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_29 = new BigDecimal("0.00");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_30 = new BigDecimal("0.00");
    public static final BigDecimal CURRENT_BALANCE_INVOICE_31 = new BigDecimal("0.00");

    public static final BigDecimal PAID_INVOICE_1 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_2 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_3 = new BigDecimal("10.00");
    public static final BigDecimal PAID_INVOICE_4 = new BigDecimal("10.11");
    public static final BigDecimal PAID_INVOICE_5 = new BigDecimal("100.20");
    public static final BigDecimal PAID_INVOICE_6 = new BigDecimal("28.11");
    public static final BigDecimal PAID_INVOICE_10 = new BigDecimal("90.32");
    public static final BigDecimal PAID_INVOICE_11 = new BigDecimal("24.21");
    public static final BigDecimal PAID_INVOICE_12 = new BigDecimal("24.20");
    public static final BigDecimal PAID_INVOICE_13 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_14 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_15 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_25 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_26 = new BigDecimal("176.31");
    public static final BigDecimal PAID_INVOICE_27 = new BigDecimal("176.30");
    public static final BigDecimal PAID_INVOICE_28 = new BigDecimal("76.31");
    public static final BigDecimal PAID_INVOICE_29 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_30 = new BigDecimal("0.00");
    public static final BigDecimal PAID_INVOICE_31 = new BigDecimal("0.00");

    public static final String CONTRACT_NUMBER_INVOICE_1 = "001148/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_2 = "issue_21_00001/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_3 = "issue_21_00001/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_4 = "000121/15/3";
    public static final String CONTRACT_NUMBER_INVOICE_5 = "000121/15/3";
    public static final String CONTRACT_NUMBER_INVOICE_6 = "000121/15/3";
    public static final String CONTRACT_NUMBER_INVOICE_10 = "issue_21_00001/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_11 = "issue_21_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_12 = "issue_21_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_13 = "issue_21_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_14 = "issue_21_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_15 = "issue_21_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_25 = "issue_4_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_26 = "issue_4_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_27 = "issue_4_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_28 = "issue_4_00002/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_29 = "001148/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_30 = "001148/15/1";
    public static final String CONTRACT_NUMBER_INVOICE_31 = "001148/15/1";

    public static final Long INVOICE_TYPE_1 = 5L;
    public static final Long INVOICE_TYPE_2 = 5L;
    public static final Long INVOICE_TYPE_3 = 3L;
    public static final Long INVOICE_TYPE_4 = 3L;
    public static final Long INVOICE_TYPE_5 = 5L;
    public static final Long INVOICE_TYPE_6 = 5L;
    public static final Long INVOICE_TYPE_10 = 3L;
    public static final Long INVOICE_TYPE_11 = 3L;
    public static final Long INVOICE_TYPE_12 = 3L;
    public static final Long INVOICE_TYPE_13 = 3L;
    public static final Long INVOICE_TYPE_14 = 3L;
    public static final Long INVOICE_TYPE_15 = 3L;
    public static final Long INVOICE_TYPE_25 = 3L;
    public static final Long INVOICE_TYPE_26 = 3L;
    public static final Long INVOICE_TYPE_27 = 3L;
    public static final Long INVOICE_TYPE_28 = 3L;
    public static final Long INVOICE_TYPE_29 = 5L;
    public static final Long INVOICE_TYPE_30 = 5L;
    public static final Long INVOICE_TYPE_31 = 5L;

    public static final String INVOICE_TYPE_5_LABEL = "Oplata dodatkowa";

    public static final Date DUE_DATE_INVOICE_1 = parse("2015-12-09 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_2 = parse("2015-12-10 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_3 = parse("2015-11-10 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_4 = parse("2015-12-09 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_5 = parse("2015-12-09 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_6 = parse("2015-12-09 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_10 = parse("2020-12-01 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_11 = parse("2024-10-02 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_12 = parse("2020-07-02 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_13 = parse("2020-12-01 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_14 = parse("2020-12-02 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_15 = parse("2020-12-01 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_25 = parse("2019-10-02 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_26 = parse("2019-10-02 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_27 = parse("2019-10-02 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_28 = parse("2019-10-02 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_29 = parse("2015-10-29 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_30 = parse("2015-10-30 00:00:00.000000");
    public static final Date DUE_DATE_INVOICE_31 = parse("2015-10-31 00:00:00.000000");

    public static final Date ISSUE_DATE_INVOICE_2 = parse("2015-12-10 00:00:00.000000");
    public static final Date ISSUE_DATE_INVOICE_3 = parse("2015-12-01 00:00:00.000000");

    public static final Long DPD_INVOICE_1 = 1L;
    public static final Long DPD_INVOICE_2 = 0L;
    public static final Long DPD_INVOICE_3 = 3L;
    public static final Long DPD_INVOICE_4 = 0L;
    public static final Long DPD_INVOICE_5 = 0L;
    public static final Long DPD_INVOICE_6 = 0L;
    public static final Long DPD_INVOICE_10 = 3L;
    public static final Long DPD_INVOICE_11 = -2640L;
    public static final Long DPD_INVOICE_12 = -5L;
    public static final Long DPD_INVOICE_29 = 0L;
    public static final Long DPD_INVOICE_30 = 0L;
    public static final Long DPD_INVOICE_31 = 0L;

    public static final Integer CORRECTION_ORDER_29 = 2;
    public static final Integer CORRECTION_ORDER_30 = 3;
    public static final Integer CORRECTION_ORDER_31 = 1;

    public static final BigDecimal PREVIOUS_CORRECTION_GROSS_AMOUNT_DIFF_29 = new BigDecimal("-24.60");
    public static final BigDecimal ROOT_INVOICE_GROSS_AMOUNT_DIFF_29 = new BigDecimal("232.58");

    public static final BigDecimal PREVIOUS_CORRECTION_GROSS_AMOUNT_DIFF_30 = new BigDecimal("-356.70");
    public static final BigDecimal ROOT_INVOICE_GROSS_AMOUNT_DIFF_30 = new BigDecimal("-124.12");

    public static final BigDecimal PREVIOUS_CORRECTION_GROSS_AMOUNT_DIFF_31 = new BigDecimal("257.18");
    public static final BigDecimal ROOT_INVOICE_GROSS_AMOUNT_DIFF_31 = new BigDecimal("257.18");

    public static final int TOTAL_INVOICES = 3;

    public static final String NOT_EXISTING_INVOICE_ID = "NOT_EXISTING_INVOICE_ID";
    public static final String NOT_EXISTING_INVOICE_NUMBER = "noSuchInvoiceNumber";

    public static Invoice invoiceToCreate() {
        final Invoice invoice = testInvoice11();
        invoice.setIssueInvoices(emptyList());
        invoice.setId(null);
        invoice.getCorrections().forEach(invoice1 -> invoice1.setId(null));
        return invoice;
    }

    public static Invoice testInvoice() {
        final Invoice invoice = invoice(ID_1, LAST_PAYMENT_DATE_INVOICE_1, NET_AMOUNT_INVOICE_1, GROSS_AMOUNT_INVOICE_1, INVOICE_NUMBER_1,
                CURRENCY_INVOICE_1, EXCHANGE_RATE_1, CURRENT_BALANCE_INVOICE_1, PAID_INVOICE_1, CONTRACT_NUMBER_INVOICE_1, INVOICE_TYPE_1, DUE_DATE_INVOICE_1,
                false, emptyList(), null, null);
        final List<IssueInvoice> issueInvoices = asList(
                createIssueInvoice(invoice, true, issue2(), false),
                createIssueInvoice(invoice, false, issue3(), true));
        invoice.setIssueInvoices(issueInvoices);
        return invoice;
    }

    public static Invoice testInvoice1() {
        final Invoice invoice = invoice(ID_1, LAST_PAYMENT_DATE_INVOICE_1, NET_AMOUNT_INVOICE_1, GROSS_AMOUNT_INVOICE_1, INVOICE_NUMBER_1,
                CURRENCY_INVOICE_1, EXCHANGE_RATE_1, CURRENT_BALANCE_INVOICE_1, PAID_INVOICE_1, CONTRACT_NUMBER_INVOICE_1, INVOICE_TYPE_1, DUE_DATE_INVOICE_1,
                false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(asList(issue2(), issue3()), invoice, false));
        return invoice;
    }

    public static Invoice testInvoice2() {
        final Invoice invoice = invoice(ID_2, LAST_PAYMENT_DATE_INVOICE_2, NET_AMOUNT_INVOICE_2, GROSS_AMOUNT_INVOICE_2, INVOICE_NUMBER_2,
                CURRENCY_INVOICE_2, EXCHANGE_RATE_2, CURRENT_BALANCE_INVOICE_2, PAID_INVOICE_2, CONTRACT_NUMBER_INVOICE_2, INVOICE_TYPE_2, DUE_DATE_INVOICE_2,
                false, emptyList(), null, ISSUE_DATE_INVOICE_2);
        invoice.setIssueInvoices(createIssueInvoices(asList(issue2(), issue3()), invoice, false));
        return invoice;
    }

    public static Invoice testInvoice3() {
        final Invoice invoice = invoice(ID_3, LAST_PAYMENT_DATE_INVOICE_3, NET_AMOUNT_INVOICE_3, GROSS_AMOUNT_INVOICE_3, INVOICE_NUMBER_3,
                CURRENCY_INVOICE_3, EXCHANGE_RATE_3, CURRENT_BALANCE_INVOICE_3, PAID_INVOICE_3, CONTRACT_NUMBER_INVOICE_3, INVOICE_TYPE_3, DUE_DATE_INVOICE_3,
                false, emptyList(), null, ISSUE_DATE_INVOICE_3);
        invoice.setIssueInvoices(createIssueInvoices(asList(issue2(), issue3()), invoice, false));
        return invoice;
    }

    public static Invoice testInvoice4() {
        final Invoice invoice = invoice(ID_4, LAST_PAYMENT_DATE_INVOICE_4, NET_AMOUNT_INVOICE_4, GROSS_AMOUNT_INVOICE_4, INVOICE_NUMBER_4,
                CURRENCY_INVOICE_4, EXCHANGE_RATE_4, CURRENT_BALANCE_INVOICE_4, PAID_INVOICE_4, CONTRACT_NUMBER_INVOICE_4, INVOICE_TYPE_4, DUE_DATE_INVOICE_4,
                false, emptyList(), null, null);

        invoice.setIssueInvoices(createIssueInvoices(asList(issue2(), issue3()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice10() {
        final Invoice invoice = invoice(ID_10, LAST_PAYMENT_DATE_INVOICE_10, NET_AMOUNT_INVOICE_10, GROSS_AMOUNT_INVOICE_10, INVOICE_NUMBER_10,
                CURRENCY_INVOICE_10, EXCHANGE_RATE_10, CURRENT_BALANCE_INVOICE_10, PAID_INVOICE_10, CONTRACT_NUMBER_INVOICE_10, INVOICE_TYPE_10,
                DUE_DATE_INVOICE_10, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue21()), invoice, true));
        return invoice;
    }

    public static List<Invoice> invoiceCorrections11() {
        return asList(testInvoice31(), testInvoice29(), testInvoice30());
    }

    public static Invoice testInvoice11() {
        final Invoice invoice = invoice(ID_11, LAST_PAYMENT_DATE_INVOICE_11, NET_AMOUNT_INVOICE_11, GROSS_AMOUNT_INVOICE_11, INVOICE_NUMBER_11,
                CURRENCY_INVOICE_11, EXCHANGE_RATE_11, CURRENT_BALANCE_INVOICE_11, PAID_INVOICE_11, CONTRACT_NUMBER_INVOICE_11, INVOICE_TYPE_11,
                DUE_DATE_INVOICE_11, false, invoiceCorrections11(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue21()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice12() {
        final Invoice invoice = invoice(ID_12, LAST_PAYMENT_DATE_INVOICE_12, NET_AMOUNT_INVOICE_12, GROSS_AMOUNT_INVOICE_12, INVOICE_NUMBER_12,
                CURRENCY_INVOICE_12, EXCHANGE_RATE_12, CURRENT_BALANCE_INVOICE_12, PAID_INVOICE_12, CONTRACT_NUMBER_INVOICE_12, INVOICE_TYPE_12,
                DUE_DATE_INVOICE_12, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue21()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice13() {
        final Invoice invoice = invoice(ID_13, LAST_PAYMENT_DATE_INVOICE_13, NET_AMOUNT_INVOICE_13, GROSS_AMOUNT_INVOICE_13, INVOICE_NUMBER_13,
                CURRENCY_INVOICE_13, EXCHANGE_RATE_13, CURRENT_BALANCE_INVOICE_13, PAID_INVOICE_13, CONTRACT_NUMBER_INVOICE_13, INVOICE_TYPE_13,
                DUE_DATE_INVOICE_13, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue21()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice14() {
        final Invoice invoice = invoice(ID_14, LAST_PAYMENT_DATE_INVOICE_14, NET_AMOUNT_INVOICE_14, GROSS_AMOUNT_INVOICE_14, INVOICE_NUMBER_14,
                CURRENCY_INVOICE_14, EXCHANGE_RATE_14, CURRENT_BALANCE_INVOICE_14, PAID_INVOICE_14, CONTRACT_NUMBER_INVOICE_14, INVOICE_TYPE_14,
                DUE_DATE_INVOICE_14, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue21()), invoice, false));
        return invoice;
    }

    public static Invoice testInvoice15() {
        final Invoice invoice = invoice(ID_15, LAST_PAYMENT_DATE_INVOICE_15, NET_AMOUNT_INVOICE_15, GROSS_AMOUNT_INVOICE_15, INVOICE_NUMBER_15,
                CURRENCY_INVOICE_15, EXCHANGE_RATE_15, CURRENT_BALANCE_INVOICE_15, PAID_INVOICE_15, CONTRACT_NUMBER_INVOICE_15, INVOICE_TYPE_15,
                DUE_DATE_INVOICE_15, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue21()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice25() {
        final Invoice invoice = invoice(ID_25, LAST_PAYMENT_DATE_INVOICE_25, NET_AMOUNT_INVOICE_25, GROSS_AMOUNT_INVOICE_25, INVOICE_NUMBER_25,
                CURRENCY_INVOICE_25, EXCHANGE_RATE_25, CURRENT_BALANCE_INVOICE_25, PAID_INVOICE_25, CONTRACT_NUMBER_INVOICE_25, INVOICE_TYPE_25,
                DUE_DATE_INVOICE_25, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue4()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice26() {
        final Invoice invoice = invoice(ID_26, LAST_PAYMENT_DATE_INVOICE_26, NET_AMOUNT_INVOICE_26, GROSS_AMOUNT_INVOICE_26, INVOICE_NUMBER_26,
                CURRENCY_INVOICE_26, EXCHANGE_RATE_26, CURRENT_BALANCE_INVOICE_26, PAID_INVOICE_26, CONTRACT_NUMBER_INVOICE_26, INVOICE_TYPE_26,
                DUE_DATE_INVOICE_26, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue4()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice27() {
        final Invoice invoice = invoice(ID_27, LAST_PAYMENT_DATE_INVOICE_27, NET_AMOUNT_INVOICE_27, GROSS_AMOUNT_INVOICE_27, INVOICE_NUMBER_27,
                CURRENCY_INVOICE_27, EXCHANGE_RATE_27, CURRENT_BALANCE_INVOICE_27, PAID_INVOICE_27, CONTRACT_NUMBER_INVOICE_27, INVOICE_TYPE_27,
                DUE_DATE_INVOICE_27, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue4()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice28() {
        final Invoice invoice = invoice(ID_28, LAST_PAYMENT_DATE_INVOICE_28, NET_AMOUNT_INVOICE_28, GROSS_AMOUNT_INVOICE_28, INVOICE_NUMBER_28,
                CURRENCY_INVOICE_28, EXCHANGE_RATE_28, CURRENT_BALANCE_INVOICE_28, PAID_INVOICE_28, CONTRACT_NUMBER_INVOICE_28, INVOICE_TYPE_28,
                DUE_DATE_INVOICE_28, false, emptyList(), null, null);
        invoice.setIssueInvoices(createIssueInvoices(singletonList(issue4()), invoice, true));
        return invoice;
    }

    public static Invoice testInvoice29() {
        final Invoice invoice = invoice(ID_29, LAST_PAYMENT_DATE_INVOICE_29, NET_AMOUNT_INVOICE_29, GROSS_AMOUNT_INVOICE_29, INVOICE_NUMBER_29,
                CURRENCY_INVOICE_29, EXCHANGE_RATE_29, CURRENT_BALANCE_INVOICE_29, PAID_INVOICE_29, CONTRACT_NUMBER_INVOICE_29, INVOICE_TYPE_29,
                DUE_DATE_INVOICE_29, true, emptyList(), CORRECTION_ORDER_29, null);
        invoice.setIssueInvoices(emptyList());
        return invoice;
    }

    public static Invoice testInvoice30() {
        final Invoice invoice = invoice(ID_30, LAST_PAYMENT_DATE_INVOICE_30, NET_AMOUNT_INVOICE_30, GROSS_AMOUNT_INVOICE_30, INVOICE_NUMBER_30,
                CURRENCY_INVOICE_30, EXCHANGE_RATE_30, CURRENT_BALANCE_INVOICE_30, PAID_INVOICE_30, CONTRACT_NUMBER_INVOICE_30, INVOICE_TYPE_30,
                DUE_DATE_INVOICE_30, true, emptyList(), CORRECTION_ORDER_30, null);
        invoice.setIssueInvoices(emptyList());
        return invoice;
    }

    public static Invoice testInvoice31() {
        final Invoice invoice = invoice(ID_31, LAST_PAYMENT_DATE_INVOICE_31, NET_AMOUNT_INVOICE_31, GROSS_AMOUNT_INVOICE_31, INVOICE_NUMBER_31,
                CURRENCY_INVOICE_31, EXCHANGE_RATE_31, CURRENT_BALANCE_INVOICE_31, PAID_INVOICE_31, CONTRACT_NUMBER_INVOICE_31, INVOICE_TYPE_31,
                DUE_DATE_INVOICE_31, true, emptyList(), CORRECTION_ORDER_31, null);
        invoice.setIssueInvoices(emptyList());
        return invoice;
    }

    public static Invoice testInvoice32() {
        final Invoice invoice = invoice(null, LAST_PAYMENT_DATE_INVOICE_31, NET_AMOUNT_INVOICE_31, GROSS_AMOUNT_INVOICE_31, INVOICE_NUMBER_32,
                CURRENCY_INVOICE_31, EXCHANGE_RATE_31, CURRENT_BALANCE_INVOICE_31, PAID_INVOICE_31, CONTRACT_NUMBER_INVOICE_31, INVOICE_TYPE_31,
                DUE_DATE_INVOICE_31, true, emptyList(), CORRECTION_ORDER_31, null);
        invoice.setIssueInvoices(emptyList());
        return invoice;
    }

    public static InvoiceDto testInvoiceDto(final Boolean excluded, final Boolean issueSubject) {
        return invoiceDto(ID_1, LAST_PAYMENT_DATE_INVOICE_1, NET_AMOUNT_INVOICE_1, GROSS_AMOUNT_INVOICE_1, INVOICE_NUMBER_1, CURRENCY_INVOICE_1,
                CURRENT_BALANCE_INVOICE_1, PAID_INVOICE_1, CONTRACT_NUMBER_INVOICE_1, INVOICE_TYPE_1, DUE_DATE_INVOICE_1, null, excluded, DPD_INVOICE_1, false,
                null, emptyList(), issueSubject, null);
    }

    public static InvoiceDto testInvoiceDto1() {
        return invoiceDto(ID_1, LAST_PAYMENT_DATE_INVOICE_1, NET_AMOUNT_INVOICE_1, GROSS_AMOUNT_INVOICE_1, INVOICE_NUMBER_1, CURRENCY_INVOICE_1,
                CURRENT_BALANCE_INVOICE_1, PAID_INVOICE_1, CONTRACT_NUMBER_INVOICE_1, INVOICE_TYPE_1, DUE_DATE_INVOICE_1, null, false, DPD_INVOICE_1, false,
                null, emptyList(), false, null);
    }

    public static InvoiceDto testInvoiceDto2() {
        return invoiceDto(ID_2, LAST_PAYMENT_DATE_INVOICE_2, NET_AMOUNT_INVOICE_2, GROSS_AMOUNT_INVOICE_2, INVOICE_NUMBER_2, CURRENCY_INVOICE_2,
                CURRENT_BALANCE_INVOICE_2, PAID_INVOICE_2, CONTRACT_NUMBER_INVOICE_2, INVOICE_TYPE_2, DUE_DATE_INVOICE_2, null, false, DPD_INVOICE_2, false,
                null, emptyList(), false, ISSUE_DATE_INVOICE_2);
    }

    public static InvoiceDto testInvoiceDto3() {
        return invoiceDto(ID_3, LAST_PAYMENT_DATE_INVOICE_3, NET_AMOUNT_INVOICE_3, GROSS_AMOUNT_INVOICE_3, INVOICE_NUMBER_3, CURRENCY_INVOICE_3,
                CURRENT_BALANCE_INVOICE_3, PAID_INVOICE_3, CONTRACT_NUMBER_INVOICE_3, INVOICE_TYPE_3, DUE_DATE_INVOICE_3, null, false, DPD_INVOICE_3, false,
                null, emptyList(), false, ISSUE_DATE_INVOICE_3);
    }

    public static List<InvoiceCorrectionDto> invoiceCorrectionsDto11() {
        return asList(testInvoiceCorrectionDto31(), testInvoiceCorrectionDto29(), testInvoiceCorrectionDto30());
    }

    public static InvoiceCorrectionDto testInvoiceCorrectionDto29() {
        return invoiceCorrectionDto(ID_29, LAST_PAYMENT_DATE_INVOICE_29, NET_AMOUNT_INVOICE_29, GROSS_AMOUNT_INVOICE_29, INVOICE_NUMBER_29, CURRENCY_INVOICE_29,
                CURRENT_BALANCE_INVOICE_29, PAID_INVOICE_29, CONTRACT_NUMBER_INVOICE_29, INVOICE_TYPE_29, DUE_DATE_INVOICE_29, null, false, DPD_INVOICE_29,
                true, CORRECTION_ORDER_29, emptyList(), false, null, PREVIOUS_CORRECTION_GROSS_AMOUNT_DIFF_29, ROOT_INVOICE_GROSS_AMOUNT_DIFF_29);
    }

    public static InvoiceCorrectionDto testInvoiceCorrectionDto30() {
        return invoiceCorrectionDto(ID_30, LAST_PAYMENT_DATE_INVOICE_30, NET_AMOUNT_INVOICE_30, GROSS_AMOUNT_INVOICE_30, INVOICE_NUMBER_30, CURRENCY_INVOICE_30,
                CURRENT_BALANCE_INVOICE_30, PAID_INVOICE_30, CONTRACT_NUMBER_INVOICE_30, INVOICE_TYPE_30, DUE_DATE_INVOICE_30, null, false, DPD_INVOICE_30,
                true, CORRECTION_ORDER_30, emptyList(), false, null, PREVIOUS_CORRECTION_GROSS_AMOUNT_DIFF_30, ROOT_INVOICE_GROSS_AMOUNT_DIFF_30);
    }

    public static InvoiceCorrectionDto testInvoiceCorrectionDto31() {
        return invoiceCorrectionDto(ID_31, LAST_PAYMENT_DATE_INVOICE_31, NET_AMOUNT_INVOICE_31, GROSS_AMOUNT_INVOICE_31, INVOICE_NUMBER_31, CURRENCY_INVOICE_31,
                CURRENT_BALANCE_INVOICE_31, PAID_INVOICE_31, CONTRACT_NUMBER_INVOICE_31, INVOICE_TYPE_31, DUE_DATE_INVOICE_31, null, false, DPD_INVOICE_31,
                true, CORRECTION_ORDER_31, emptyList(), false, null, PREVIOUS_CORRECTION_GROSS_AMOUNT_DIFF_31, ROOT_INVOICE_GROSS_AMOUNT_DIFF_31);
    }

    public static Invoice invoiceWithBalance1() {
        return invoice(ID_1, LAST_PAYMENT_DATE_INVOICE_1, NET_AMOUNT_INVOICE_1, GROSS_AMOUNT_INVOICE_1, INVOICE_NUMBER_1,
                CURRENCY_INVOICE_1, EXCHANGE_RATE_1, CURRENT_BALANCE_INVOICE_4, PAID_INVOICE_4, CONTRACT_NUMBER_INVOICE_1, INVOICE_TYPE_5, DUE_DATE_INVOICE_1, false,
                emptyList(), null, null);
    }

    public static Invoice invoiceWithoutBalance() {
        final Invoice invoice = invoiceWithBalance1();
        invoice.setCurrentBalance(null);
        invoice.setPaid(null);
        return invoice;
    }

    public static InvoiceDto invoiceWithoutBalanceDto() {
        final InvoiceDto invoiceDto = invoiceWithBalanceDto1();
        invoiceDto.setCurrentBalance(null);
        invoiceDto.setPaid(null);
        return invoiceDto;
    }

    public static InvoiceDto invoiceWithBalanceDto1() {
        return invoiceDto(ID_1, LAST_PAYMENT_DATE_INVOICE_1, NET_AMOUNT_INVOICE_1, GROSS_AMOUNT_INVOICE_1, INVOICE_NUMBER_1, CURRENCY_INVOICE_1,
                CURRENT_BALANCE_INVOICE_4, PAID_INVOICE_4, CONTRACT_NUMBER_INVOICE_1, INVOICE_TYPE_1, DUE_DATE_INVOICE_1, INVOICE_TYPE_5_LABEL, false,
                DPD_INVOICE_1, false, null, emptyList(), false, null);
    }

    public static Invoice invoiceWithBalance2() {
        return invoice(ID_2, LAST_PAYMENT_DATE_INVOICE_2, NET_AMOUNT_INVOICE_2, GROSS_AMOUNT_INVOICE_2, INVOICE_NUMBER_2,
                CURRENCY_INVOICE_2, EXCHANGE_RATE_2, CURRENT_BALANCE_INVOICE_5, PAID_INVOICE_2, CONTRACT_NUMBER_INVOICE_2, INVOICE_TYPE_5, DUE_DATE_INVOICE_2,
                false, emptyList(), null, ISSUE_DATE_INVOICE_2);
    }

    public static InvoiceDto invoiceWithBalanceDto2() {
        return invoiceDto(ID_2, LAST_PAYMENT_DATE_INVOICE_2, NET_AMOUNT_INVOICE_2, GROSS_AMOUNT_INVOICE_2, INVOICE_NUMBER_2, CURRENCY_INVOICE_2,
                CURRENT_BALANCE_INVOICE_5, PAID_INVOICE_2, CONTRACT_NUMBER_INVOICE_2, INVOICE_TYPE_2, DUE_DATE_INVOICE_2, INVOICE_TYPE_5_LABEL, false,
                DPD_INVOICE_2, false, null, emptyList(), false, ISSUE_DATE_INVOICE_2);
    }

    public static InvoiceDto invoiceWithBalanceDto3() {
        return invoiceDto(ID_3, LAST_PAYMENT_DATE_INVOICE_3, NET_AMOUNT_INVOICE_3, GROSS_AMOUNT_INVOICE_3, INVOICE_NUMBER_3, CURRENCY_INVOICE_3,
                CURRENT_BALANCE_INVOICE_3, PAID_INVOICE_3, CONTRACT_NUMBER_INVOICE_3, INVOICE_TYPE_3, DUE_DATE_INVOICE_3, INVOICE_TYPE_5_LABEL, false,
                DPD_INVOICE_3, false, null, emptyList(), false, ISSUE_DATE_INVOICE_3);
    }

    public static InvoiceDto invoiceWithBalanceDto4() {
        return invoiceDto(ID_4, LAST_PAYMENT_DATE_INVOICE_4, NET_AMOUNT_INVOICE_4, GROSS_AMOUNT_INVOICE_4, INVOICE_NUMBER_4, CURRENCY_INVOICE_4,
                CURRENT_BALANCE_INVOICE_6, PAID_INVOICE_6, CONTRACT_NUMBER_INVOICE_4, INVOICE_TYPE_4, DUE_DATE_INVOICE_4, INVOICE_TYPE_5_LABEL, false,
                DPD_INVOICE_4, false, null, emptyList(), true, null);
    }

    public static Invoice invoiceWithBalance5() {
        final Invoice invoice = invoice(ID_5, LAST_PAYMENT_DATE_INVOICE_5, NET_AMOUNT_INVOICE_5, GROSS_AMOUNT_INVOICE_5, INVOICE_NUMBER_5,
                CURRENCY_INVOICE_5, EXCHANGE_RATE_5, CURRENT_BALANCE_INVOICE_6, PAID_INVOICE_6, CONTRACT_NUMBER_INVOICE_5, INVOICE_TYPE_5, DUE_DATE_INVOICE_5,
                false, emptyList(), null, null);
        final List<IssueInvoice> issueInvoices = asList(
                createIssueInvoice(invoice, true, issue21(), false),
                createIssueInvoice(invoice, false, issue2(), true));
        invoice.setIssueInvoices(issueInvoices);
        return invoice;
    }

    public static InvoiceDto invoiceWithBalanceDto5() {
        return invoiceDto(ID_5, LAST_PAYMENT_DATE_INVOICE_5, NET_AMOUNT_INVOICE_5, GROSS_AMOUNT_INVOICE_5, INVOICE_NUMBER_5, CURRENCY_INVOICE_5,
                CURRENT_BALANCE_INVOICE_6, PAID_INVOICE_6, CONTRACT_NUMBER_INVOICE_5, INVOICE_TYPE_5, DUE_DATE_INVOICE_5, INVOICE_TYPE_5_LABEL, true,
                DPD_INVOICE_5, false, null, emptyList(), false, null);
    }

    public static Invoice invoiceWithBalance6() {
        final Invoice invoice = invoice(ID_6, LAST_PAYMENT_DATE_INVOICE_6, NET_AMOUNT_INVOICE_6, GROSS_AMOUNT_INVOICE_6, INVOICE_NUMBER_6,
                CURRENCY_INVOICE_6, EXCHANGE_RATE_6, CURRENT_BALANCE_INVOICE_5, PAID_INVOICE_5, CONTRACT_NUMBER_INVOICE_6, INVOICE_TYPE_5, DUE_DATE_INVOICE_6,
                false, emptyList(), null, null);
        final List<IssueInvoice> issueInvoices = asList(
                createIssueInvoice(invoice, true, issue21(), false),
                createIssueInvoice(invoice, false, issue2(), true));
        invoice.setIssueInvoices(issueInvoices);
        return invoice;
    }

    public static InvoiceDto invoiceWithBalanceDto6() {
        return invoiceDto(ID_6, LAST_PAYMENT_DATE_INVOICE_6, NET_AMOUNT_INVOICE_6, GROSS_AMOUNT_INVOICE_6, INVOICE_NUMBER_6, CURRENCY_INVOICE_6,
                CURRENT_BALANCE_INVOICE_5, PAID_INVOICE_5, CONTRACT_NUMBER_INVOICE_6, INVOICE_TYPE_6, DUE_DATE_INVOICE_6, INVOICE_TYPE_5_LABEL, true,
                DPD_INVOICE_6, false, null, emptyList(), false, null);
    }

    public static InvoiceDto invoiceWithBalanceDto10() {
        return invoiceDto(ID_10, LAST_PAYMENT_DATE_INVOICE_10, NET_AMOUNT_INVOICE_10, GROSS_AMOUNT_INVOICE_10, INVOICE_NUMBER_10,
                CURRENCY_INVOICE_10, CURRENT_BALANCE_INVOICE_6, PAID_INVOICE_6, CONTRACT_NUMBER_INVOICE_10, INVOICE_TYPE_10, DUE_DATE_INVOICE_10,
                INVOICE_TYPE_5_LABEL, false,
                DPD_INVOICE_10, false, null, emptyList(), true, null);
    }

    public static InvoiceDto invoiceWithBalanceDto11() {
        return invoiceDto(ID_11, LAST_PAYMENT_DATE_INVOICE_11, NET_AMOUNT_INVOICE_11, GROSS_AMOUNT_INVOICE_11, INVOICE_NUMBER_11,
                CURRENCY_INVOICE_11, CURRENT_BALANCE_INVOICE_6, PAID_INVOICE_6, CONTRACT_NUMBER_INVOICE_11, INVOICE_TYPE_11, DUE_DATE_INVOICE_11,
                INVOICE_TYPE_5_LABEL, false,
                DPD_INVOICE_11, false, null, emptyList(), true, null);
    }

    public static InvoiceDto invoiceWithBalanceDto12() {
        return invoiceDto(ID_12, LAST_PAYMENT_DATE_INVOICE_12, NET_AMOUNT_INVOICE_12, GROSS_AMOUNT_INVOICE_12, INVOICE_NUMBER_12,
                CURRENCY_INVOICE_12, CURRENT_BALANCE_INVOICE_6, PAID_INVOICE_6, CONTRACT_NUMBER_INVOICE_12, INVOICE_TYPE_12, DUE_DATE_INVOICE_12,
                INVOICE_TYPE_5_LABEL, false,
                DPD_INVOICE_12, false, null, emptyList(), true, null);
    }

    public static List<InvoiceDto> invoiceDtos() {
        return asList(invoiceWithBalanceDto1(), invoiceWithBalanceDto2(), invoiceWithBalanceDto3(), invoiceWithBalanceDto4(), invoiceWithBalanceDto5(),
                invoiceWithBalanceDto6(), invoiceWithoutBalanceDto());
    }

    public static List<Invoice> expectedInvoicesForIssueFirstPage() {
        return asList(testInvoice1(), testInvoice2());
    }

    public static List<Invoice> expectedInvoicesForIssueSecondPage() {
        return singletonList(testInvoice3());
    }

    public static List<InvoiceDto> expectedInvoicesForIssueDtoFirstPage() {
        return asList(testInvoiceDto1(), testInvoiceDto2());
    }

    public static List<Invoice> expectedInvoicesWithCurrentBalance() {
        return asList(invoiceWithBalance1(), invoiceWithBalance2(), invoiceWithBalance5(), invoiceWithBalance6(), invoiceWithoutBalance());
    }

    public static Page<InvoiceDto> expectedPageWithInvoicesDtoWithCurrentBalance() {
        return new Page<>(asList(invoiceWithBalanceDto1(), invoiceWithBalanceDto2(), invoiceWithBalanceDto5(), invoiceWithBalanceDto6(),
                invoiceWithoutBalanceDto()), TOTAL_INVOICES);
    }

    public static List<InvoiceDto> expectedInvoicesForIssueDtoSecondPage() {
        return singletonList(testInvoiceDto3());
    }

    private static Invoice invoice(final Long id, final Date lastPaymentDate, final BigDecimal netAmount,
                                   final BigDecimal grossAmount, final String number, final String currency, final BigDecimal exchangeRate,
                                   final BigDecimal currentBalance, final BigDecimal paid, final String contractNumber, final Long typeId, final Date dueDate,
                                   final boolean correction, final List<Invoice> corrections, final Integer correctionOrder, final Date issueDate) {
        final Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setLastPaymentDate(lastPaymentDate);
        invoice.setNetAmount(netAmount);
        invoice.setGrossAmount(grossAmount);
        invoice.setNumber(number);
        invoice.setCurrency(currency);
        invoice.setExchangeRate(exchangeRate);
        invoice.setCurrentBalance(currentBalance);
        invoice.setPaid(paid);
        invoice.setContractNumber(contractNumber);
        invoice.setTypeId(typeId);
        invoice.setRealDueDate(dueDate);
        invoice.setCorrection(correction);
        invoice.setCorrections(corrections);
        invoice.setCorrectionOrder(correctionOrder);
        invoice.setIssueDate(issueDate);
        return invoice;
    }

    @SuppressWarnings("SameParameterValue")
    private static InvoiceDto invoiceDto(final Long id, final Date lastPaymentDate, final BigDecimal netAmount,
                                         final BigDecimal grossAmount, final String number, final String currency, final BigDecimal currentBalance,
                                         final BigDecimal paid, final String contractNumber, final Long typeId, final Date dueDate, final String typeLabel,
                                         final Boolean excluded, final Long dpd, final boolean correction, final Integer correctionOrder,
                                         final List<InvoiceCorrectionDto> corrections, final Boolean issueSubject, final Date issueDate) {
        final InvoiceDto invoice = new InvoiceDto();
        setInvoiceDtoFields(invoice, id, lastPaymentDate, netAmount, grossAmount, number, currency, currentBalance, paid, contractNumber, typeId, dueDate,
                typeLabel, excluded, dpd, correction, correctionOrder, corrections, issueSubject, issueDate);
        return invoice;
    }

    private static void setInvoiceDtoFields(final InvoiceDto invoice, final Long id, final Date lastPaymentDate, final BigDecimal netAmount,
                                            final BigDecimal grossAmount, final String number, final String currency, final BigDecimal currentBalance,
                                            final BigDecimal paid, final String contractNumber, final Long typeId, final Date realDueDate, final String typeLabel, final Boolean excluded,
                                            final Long dpd, final boolean correction, final Integer correctionOrder, final List<InvoiceCorrectionDto> corrections,
                                            final Boolean issueSubject, final Date issueDate) {
        invoice.setId(id);
        invoice.setLastPaymentDate(lastPaymentDate);
        invoice.setNetAmount(netAmount);
        invoice.setGrossAmount(grossAmount);
        invoice.setNumber(number);
        invoice.setCurrency(currency);
        invoice.setCurrentBalance(currentBalance);
        invoice.setPaid(paid);
        invoice.setContractNumber(contractNumber);
        invoice.setTypeId(typeId);
        invoice.setRealDueDate(realDueDate);
        invoice.setTypeLabel(typeLabel);
        invoice.setExcluded(excluded);
        invoice.setDpd(dpd);
        invoice.setCorrection(correction);
        invoice.setCorrectionOrder(correctionOrder);
        invoice.setCorrections(corrections);
        invoice.setIssueSubject(issueSubject);
        invoice.setIssueDate(issueDate);
    }

    @SuppressWarnings("SameParameterValue")
    private static InvoiceCorrectionDto invoiceCorrectionDto(final Long id, final Date lastPaymentDate, final BigDecimal netAmount, final BigDecimal grossAmount,
                                                             final String number, final String currency, final BigDecimal currentBalance, final BigDecimal paid,
                                                             final String contractNumber, final Long typeId, final Date dueDate, final String typeLabel,
                                                             final Boolean excluded, final Long dpd, final boolean correction, final Integer correctionOrder,
                                                             final List<InvoiceCorrectionDto> corrections, final Boolean issueSubject, final Date issueDate,
                                                             final BigDecimal previousCorrectionGrossAmountDiff, final BigDecimal rootInvoiceGrossAmountDiff) {

        final InvoiceCorrectionDto invoiceCorrectionDto = new InvoiceCorrectionDto();
        setInvoiceDtoFields(invoiceCorrectionDto, id, lastPaymentDate, netAmount, grossAmount, number, currency, currentBalance, paid, contractNumber, typeId,
                dueDate, typeLabel, excluded, dpd, correction, correctionOrder, corrections, issueSubject, issueDate);
        invoiceCorrectionDto.setPreviousCorrectionGrossAmountDiff(previousCorrectionGrossAmountDiff);
        invoiceCorrectionDto.setRootInvoiceGrossAmountDiff(rootInvoiceGrossAmountDiff);
        return invoiceCorrectionDto;
    }

    public static List<IssueInvoice> createIssueInvoices(final List<Issue> issues, final Invoice invoice, final Boolean excluded, final Boolean issueSubject) {
        final List<IssueInvoice> issueInvoices = new ArrayList<>();
        for (final Issue issue : issues) {
            issueInvoices.add(createIssueInvoice(invoice, excluded, issue, issueSubject));
        }
        return issueInvoices;
    }

    private static IssueInvoice createIssueInvoice(final Invoice invoice, final Boolean excluded, final Issue issue, final Boolean issueSubject) {
        final IssueInvoice issueInvoice = new IssueInvoice();
        issueInvoice.setIssue(issue);
        issueInvoice.setInvoice(invoice);
        issueInvoice.setExcluded(excluded);
        issueInvoice.setIssueSubject(issueSubject);
        return issueInvoice;
    }

    public static List<IssueInvoice> createIssueInvoices(final List<Issue> issues, final Invoice invoice, final Boolean issueSubject) {
        return createIssueInvoices(issues, invoice, false, issueSubject);
    }
}
