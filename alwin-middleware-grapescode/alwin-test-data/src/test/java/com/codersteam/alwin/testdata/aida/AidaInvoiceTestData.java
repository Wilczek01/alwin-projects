package com.codersteam.alwin.testdata.aida;


import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.model.AidaSimpleInvoiceDto;
import com.codersteam.aida.core.api.model.InvoiceTypeDto;

import java.math.BigDecimal;
import java.util.*;

import static com.codersteam.alwin.testdata.CompanyTestData.*;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.EXCEPTION_CAUSING_AIDA_COMPANY_ID;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class AidaInvoiceTestData {

    public static final InvoiceTypeDto NOT_RENT_INVOICE_TYPE = getInvoiceTypeDto(5L, "Oplata dodatkowa");

    public static final String DUE_DATE = "2017-07-10 00:00:00.000000";
    public static final String EXPIRATION_DATE = "2017-08-30 00:00:00.000000";

    public static final String INVOICE_NUMBER_1 = "Invoice 1";
    public static final String INVOICE_CONTRACT_NUMBER_1 = "1/1/18";
    public static final BigDecimal INVOICE_BALANCE_1 = new BigDecimal("-99234.35");
    public static final BigDecimal INVOICE_BALANCE_EUR_1 = new BigDecimal("-24808.59");
    public static final BigDecimal INVOICE_PAID_1 = new BigDecimal("134.35");

    public static final String INVOICE_NUMBER_2 = "Invoice 2";
    public static final String INVOICE_CONTRACT_NUMBER_2 = "2/1/18";
    public static final BigDecimal INVOICE_BALANCE_2 = new BigDecimal("-100.00");
    public static final BigDecimal INVOICE_PAID_2 = new BigDecimal("0.00");

    public static final String INVOICE_NUMBER_3 = "Invoice 3";
    public static final String INVOICE_CONTRACT_NUMBER_3 = "3/1/18";
    public static final BigDecimal INVOICE_BALANCE_3 = new BigDecimal("9935235.23");
    public static final BigDecimal INVOICE_PAID_3 = new BigDecimal("32324.23");

    public static final String INVOICE_NUMBER_4 = "000015/12/2015/TO";
    public static final BigDecimal INVOICE_BALANCE_4 = new BigDecimal("35235.23");
    public static final BigDecimal INVOICE_PAID_4 = new BigDecimal("35235.23");

    public static final String INVOICE_NUMBER_5 = "Invoice 5";
    public static final BigDecimal INVOICE_BALANCE_5 = new BigDecimal("-3225.23");
    public static final BigDecimal INVOICE_PAID_5 = new BigDecimal("0.00");

    public static final String INVOICE_NUMBER_6 = "Invoice 6";
    public static final BigDecimal INVOICE_BALANCE_6 = new BigDecimal("-1123.23");
    public static final BigDecimal INVOICE_PAID_6 = new BigDecimal("0.00");

    public static final String INVOICE_NUMBER_7 = "Invoice 7";
    public static final BigDecimal INVOICE_BALANCE_7 = new BigDecimal("-132.23");
    public static final BigDecimal INVOICE_PAID_7 = new BigDecimal("0.00");

    public static final Long INVOICE_ID_1 = 1L;
    public static final String INVOICE_CONTRACT_NO_1 = "TEST 12/34/56";
    public static final BigDecimal NET_AMOUNT_1 = new BigDecimal("300.00");
    public static final BigDecimal GROSS_AMOUNT_1 = new BigDecimal("369.00");
    public static final String CURRENCY_1 = "PLN";
    public static final BigDecimal EXCHANGE_RATE_1 = new BigDecimal("1.0000");
    public static final Date ISSUE_DATE_1 = parse("2017-07-01 00:00:00.000000");
    public static final Date DUE_DATE_1 = parse("2017-07-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_1 = parse("2017-07-11 00:00:00.000000");
    public static final InvoiceTypeDto INVOICE_TYPE_1 = getInvoiceTypeDto(5L, "Oplata dodatkowa");

    public static final Long INVOICE_ID_2 = 2L;
    public static final String INVOICE_CONTRACT_NO_2 = "TEST 20/43/12";
    public static final BigDecimal NET_AMOUNT_2 = new BigDecimal("375.00");
    public static final BigDecimal GROSS_AMOUNT_2 = new BigDecimal("461.25");
    public static final String CURRENCY_2 = "PLN";
    public static final BigDecimal EXCHANGE_RATE_2 = new BigDecimal("1.0000");
    public static final Date ISSUE_DATE_2 = parse("2017-07-01 00:00:00.000000");
    public static final Date DUE_DATE_2 = parse("2017-07-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_2 = parse("2017-07-11 00:00:00.000000");
    public static final InvoiceTypeDto INVOICE_TYPE_2 = getInvoiceTypeDto(5L, "Oplata dodatkowa");

    public static final Long INVOICE_ID_3 = 3L;
    public static final String INVOICE_CONTRACT_NO_3 = "TEST 12/34/56";
    public static final BigDecimal NET_AMOUNT_3 = new BigDecimal("490.73");
    public static final BigDecimal GROSS_AMOUNT_3 = new BigDecimal("490.73");
    public static final String CURRENCY_3 = "EUR";
    public static final BigDecimal EXCHANGE_RATE_3 = new BigDecimal("4.2041");
    public static final Date ISSUE_DATE_3 = parse("2017-08-01 00:00:00.000000");
    public static final Date DUE_DATE_3 = parse("2017-08-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_3 = parse("2017-07-11 00:00:00.000000");
    public static final InvoiceTypeDto INVOICE_TYPE_3 = getInvoiceTypeDto(3L, "Oplata okresowa");

    public static final Long INVOICE_ID_4 = 4L;
    public static final String INVOICE_CONTRACT_NO_4 = "TEST 12/34/56";
    public static final BigDecimal NET_AMOUNT_4 = new BigDecimal("490.73");
    public static final BigDecimal GROSS_AMOUNT_4 = new BigDecimal("490.73");
    public static final String CURRENCY_4 = "EUR";
    public static final BigDecimal EXCHANGE_RATE_4 = new BigDecimal("4.1023");
    public static final Date ISSUE_DATE_4 = parse("2017-07-01 00:00:00.000000");
    public static final Date DUE_DATE_4 = parse("2017-07-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_4 = parse("2017-07-11 00:00:00.000000");
    public static final InvoiceTypeDto INVOICE_TYPE_4 = getInvoiceTypeDto(3L, "Oplata okresowa");

    public static final Long INVOICE_ID_5 = 5L;
    public static final String INVOICE_CONTRACT_NO_5 = "TEST 5/34/56";
    public static final BigDecimal NET_AMOUNT_5 = new BigDecimal("2622.14");
    public static final BigDecimal GROSS_AMOUNT_5 = new BigDecimal("3225.23");
    public static final String CURRENCY_5 = "PLN";
    public static final BigDecimal EXCHANGE_RATE_5 = new BigDecimal("1.0000");
    public static final Date ISSUE_DATE_5 = parse("2017-07-01 00:00:00.000000");
    public static final Date DUE_DATE_5 = parse("2017-07-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_5 = parse("2017-07-11 00:00:00.000000");
    public static final InvoiceTypeDto INVOICE_TYPE_5 = getInvoiceTypeDto(5L, "Oplata dodatkowa");

    public static final Long INVOICE_ID_6 = 6L;
    public static final String INVOICE_CONTRACT_NO_6 = "TEST 6/34/56";
    public static final BigDecimal NET_AMOUNT_6 = new BigDecimal("913.20");
    public static final BigDecimal GROSS_AMOUNT_6 = new BigDecimal("1123.23");
    public static final String CURRENCY_6 = "PLN";
    public static final BigDecimal EXCHANGE_RATE_6 = new BigDecimal("1.0000");
    public static final Date ISSUE_DATE_6 = parse("2017-07-01 00:00:00.000000");
    public static final Date DUE_DATE_6 = parse("2017-07-10 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_6 = parse("2017-07-11 00:00:00.000000");
    public static final InvoiceTypeDto INVOICE_TYPE_6 = getInvoiceTypeDto(5L, "Oplata dodatkowa");

    public static final Long INVOICE_ID_7 = 7L;
    public static final String INVOICE_CONTRACT_NO_7 = "TEST 7/34/56";
    public static final BigDecimal NET_AMOUNT_7 = new BigDecimal("107.50");
    public static final BigDecimal GROSS_AMOUNT_7 = new BigDecimal("132.23");
    public static final String CURRENCY_7 = "EUR";
    public static final BigDecimal EXCHANGE_RATE_7 = new BigDecimal("4.3123");
    public static final Date ISSUE_DATE_7 = parse("2017-07-02 00:00:00.000000");
    public static final Date DUE_DATE_7 = parse("2017-07-12 00:00:00.000000");
    public static final Date LAST_PAYMENT_DATE_7 = null;
    public static final InvoiceTypeDto INVOICE_TYPE_7 = getInvoiceTypeDto(5L, "Oplata dodatkowa");

    public static final Long INVOICE_ID_8 = 8L;
    public static final Long INVOICE_ID_9 = 9L;

    public static List<InvoiceTypeDto> allInvoiceTypeDtos() {
        final List<InvoiceTypeDto> types = new ArrayList<>();
        types.add(invoiceTypeDto(0L, "Nieokreslony"));
        types.add(invoiceTypeDto(1L, "Oplata wstepna"));
        types.add(invoiceTypeDto(2L, "Oplata manipulacyjna"));
        types.add(invoiceTypeDto(3L, "Oplata okresowa"));
        types.add(invoiceTypeDto(4L, "Refaktura polisy"));
        types.add(invoiceTypeDto(5L, "Oplata dodatkowa"));
        return types;
    }

    public static Map<Long, String> allTypes() {
        final Map<Long, String> allTypes = new HashMap<>();
        allTypes.put(0L, "Nieokreslony");
        allTypes.put(1L, "Oplata wstepna");
        allTypes.put(2L, "Oplata manipulacyjna");
        allTypes.put(3L, "Oplata okresowa");
        allTypes.put(4L, "Refaktura polisy");
        allTypes.put(5L, "Oplata dodatkowa");
        return allTypes;
    }

    private static InvoiceTypeDto invoiceTypeDto(final long id, final String label) {
        final InvoiceTypeDto invoiceTypeDto = new InvoiceTypeDto();
        invoiceTypeDto.setId(id);
        invoiceTypeDto.setLabel(label);
        return invoiceTypeDto;
    }

    public static List<AidaInvoiceDto> dueInvoices() {
        return asList(dueInvoice1(), dueInvoice2(), dueInvoice3());
    }

    public static List<AidaSimpleInvoiceDto> dueSimpleInvoices() {
        return asList(dueSimpleInvoice1(), dueSimpleInvoice2(), dueSimpleInvoice3());
    }

    public static List<AidaSimpleInvoiceDto> dueSimpleInvoicesWithExceptionCausingInvoice() {
        return asList(dueSimpleInvoice1(), dueSimpleInvoice2(), issueCreationExceptionCausingSimpleInvoice(), dueSimpleInvoice3(), dueSimpleInvoice5(), dueSimpleInvoice6());
    }

    public static List<AidaSimpleInvoiceDto> dueSimpleInvoicesToCreate3Issues() {
        return asList(dueSimpleInvoice1(), dueSimpleInvoice2(), dueSimpleInvoice3(), dueSimpleInvoice5(), dueSimpleInvoice6());
    }

    public static List<AidaInvoiceWithCorrectionsDto> dueInvoicesWithCorrections() {
        return asList(dueInvoiceWithCorrections1(), dueInvoiceWithCorrections2(), dueInvoiceWithCorrections3());
    }

    public static AidaInvoiceDto dueInvoice1() {
        return invoiceDto(EXT_COMPANY_ID_1, INVOICE_NUMBER_1, INVOICE_BALANCE_1, INVOICE_ID_1, INVOICE_CONTRACT_NO_1, NET_AMOUNT_1, GROSS_AMOUNT_1, CURRENCY_1,
                ISSUE_DATE_1, DUE_DATE_1, LAST_PAYMENT_DATE_1, INVOICE_TYPE_1, INVOICE_PAID_1, EXCHANGE_RATE_1);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoice1() {
        return simpleInvoiceDto(EXT_COMPANY_ID_1, INVOICE_CONTRACT_NO_1, INVOICE_NUMBER_1, INVOICE_ID_1);
    }

    public static AidaInvoiceWithCorrectionsDto dueInvoiceWithCorrections1() {
        return invoiceWithCorrectionsDto(EXT_COMPANY_ID_1, INVOICE_NUMBER_1, INVOICE_BALANCE_1, INVOICE_ID_1, INVOICE_CONTRACT_NO_1, NET_AMOUNT_1, GROSS_AMOUNT_1,
                CURRENCY_1, ISSUE_DATE_1, DUE_DATE_1, LAST_PAYMENT_DATE_1, INVOICE_TYPE_1, INVOICE_PAID_1, EXCHANGE_RATE_1, emptyList());
    }

    public static AidaInvoiceDto dueInvoice2() {
        return invoiceDto(EXT_COMPANY_ID_2, INVOICE_NUMBER_2, INVOICE_BALANCE_2, INVOICE_ID_2, INVOICE_CONTRACT_NO_2, NET_AMOUNT_2, GROSS_AMOUNT_2, CURRENCY_2,
                ISSUE_DATE_2, DUE_DATE_2, LAST_PAYMENT_DATE_2, INVOICE_TYPE_2, INVOICE_PAID_2, EXCHANGE_RATE_2);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoice2() {
        return simpleInvoiceDto(EXT_COMPANY_ID_2, INVOICE_CONTRACT_NO_2, INVOICE_NUMBER_2, INVOICE_ID_2);
    }

    public static AidaInvoiceWithCorrectionsDto dueInvoiceWithCorrections2() {
        return invoiceWithCorrectionsDto(EXT_COMPANY_ID_2, INVOICE_NUMBER_2, INVOICE_BALANCE_2, INVOICE_ID_2, INVOICE_CONTRACT_NO_2, NET_AMOUNT_2, GROSS_AMOUNT_2,
                CURRENCY_2, ISSUE_DATE_2, DUE_DATE_2, LAST_PAYMENT_DATE_2, INVOICE_TYPE_2, INVOICE_PAID_2, EXCHANGE_RATE_2, emptyList());
    }

    public static AidaInvoiceDto dueInvoice3() {
        return invoiceDto(EXT_COMPANY_ID_3, INVOICE_NUMBER_3, INVOICE_BALANCE_3, INVOICE_ID_3, INVOICE_CONTRACT_NO_3, NET_AMOUNT_3, GROSS_AMOUNT_3, CURRENCY_3,
                ISSUE_DATE_3, DUE_DATE_3, LAST_PAYMENT_DATE_3, INVOICE_TYPE_3, INVOICE_PAID_3, EXCHANGE_RATE_3);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoice3() {
        return simpleInvoiceDto(EXT_COMPANY_ID_3, INVOICE_CONTRACT_NO_3, INVOICE_NUMBER_3, INVOICE_ID_3);
    }

    public static AidaInvoiceWithCorrectionsDto dueInvoiceWithCorrections3() {
        return invoiceWithCorrectionsDto(EXT_COMPANY_ID_3, INVOICE_NUMBER_3, INVOICE_BALANCE_3, INVOICE_ID_3, INVOICE_CONTRACT_NO_3, NET_AMOUNT_3, GROSS_AMOUNT_3,
                CURRENCY_3, ISSUE_DATE_3, DUE_DATE_3, LAST_PAYMENT_DATE_3, INVOICE_TYPE_3, INVOICE_PAID_3, EXCHANGE_RATE_3, singletonList(dueInvoice4()));
    }

    public static AidaInvoiceDto dueInvoice4() {
        return invoiceDto(EXT_COMPANY_ID_4, INVOICE_NUMBER_4, INVOICE_BALANCE_4, INVOICE_ID_4, INVOICE_CONTRACT_NO_4, NET_AMOUNT_4, GROSS_AMOUNT_4, CURRENCY_4,
                ISSUE_DATE_4, DUE_DATE_4, LAST_PAYMENT_DATE_4, INVOICE_TYPE_4, INVOICE_PAID_4, EXCHANGE_RATE_4);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoice4() {
        return simpleInvoiceDto(EXT_COMPANY_ID_4, INVOICE_CONTRACT_NO_4, INVOICE_NUMBER_4, INVOICE_ID_4);
    }

    public static AidaInvoiceWithCorrectionsDto dueInvoiceWithCorrections4() {
        return invoiceWithCorrectionsDto(EXT_COMPANY_ID_4, INVOICE_NUMBER_4, INVOICE_BALANCE_4, INVOICE_ID_4, INVOICE_CONTRACT_NO_4, NET_AMOUNT_4, GROSS_AMOUNT_4,
                CURRENCY_4, ISSUE_DATE_4, DUE_DATE_4, LAST_PAYMENT_DATE_4, INVOICE_TYPE_4, INVOICE_PAID_4, EXCHANGE_RATE_4, emptyList());
    }

    public static AidaInvoiceDto dueInvoice5() {
        return invoiceDto(EXT_COMPANY_ID_5, INVOICE_NUMBER_5, INVOICE_BALANCE_5, INVOICE_ID_5, INVOICE_CONTRACT_NO_5, NET_AMOUNT_5, GROSS_AMOUNT_5, CURRENCY_5,
                ISSUE_DATE_5, DUE_DATE_5, LAST_PAYMENT_DATE_5, INVOICE_TYPE_5, INVOICE_PAID_5, EXCHANGE_RATE_5);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoice5() {
        return simpleInvoiceDto(EXT_COMPANY_ID_5, INVOICE_CONTRACT_NO_5, INVOICE_NUMBER_5, INVOICE_ID_5);
    }

    public static AidaInvoiceWithCorrectionsDto dueInvoiceWithCorrections5() {
        return invoiceWithCorrectionsDto(EXT_COMPANY_ID_5, INVOICE_NUMBER_5, INVOICE_BALANCE_5, INVOICE_ID_5, INVOICE_CONTRACT_NO_5, NET_AMOUNT_5, GROSS_AMOUNT_5,
                CURRENCY_5, ISSUE_DATE_5, DUE_DATE_5, LAST_PAYMENT_DATE_5, INVOICE_TYPE_5, INVOICE_PAID_5, EXCHANGE_RATE_5, emptyList());
    }

    public static AidaInvoiceDto dueInvoice6() {
        return invoiceDto(EXT_COMPANY_ID_6, INVOICE_NUMBER_6, INVOICE_BALANCE_6, INVOICE_ID_6, INVOICE_CONTRACT_NO_6, NET_AMOUNT_6, GROSS_AMOUNT_6, CURRENCY_6,
                ISSUE_DATE_6, DUE_DATE_6, LAST_PAYMENT_DATE_6, INVOICE_TYPE_6, INVOICE_PAID_6, EXCHANGE_RATE_6);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoice6() {
        return simpleInvoiceDto(EXT_COMPANY_ID_6, INVOICE_CONTRACT_NO_6, INVOICE_NUMBER_6, INVOICE_ID_6);
    }

    public static AidaInvoiceWithCorrectionsDto dueInvoiceWithCorrections6() {
        return invoiceWithCorrectionsDto(EXT_COMPANY_ID_6, INVOICE_NUMBER_6, INVOICE_BALANCE_6, INVOICE_ID_6, INVOICE_CONTRACT_NO_6, NET_AMOUNT_6, GROSS_AMOUNT_6,
                CURRENCY_6, ISSUE_DATE_6, DUE_DATE_6, LAST_PAYMENT_DATE_6, INVOICE_TYPE_6, INVOICE_PAID_6, EXCHANGE_RATE_6, emptyList());
    }

    public static AidaInvoiceWithCorrectionsDto dueInvoiceWithCorrections7() {
        return invoiceWithCorrectionsDto(EXT_COMPANY_ID_6, INVOICE_NUMBER_7, INVOICE_BALANCE_7, INVOICE_ID_7, INVOICE_CONTRACT_NO_7, NET_AMOUNT_7, GROSS_AMOUNT_7,
                CURRENCY_7, ISSUE_DATE_7, DUE_DATE_7, LAST_PAYMENT_DATE_7, INVOICE_TYPE_7, INVOICE_PAID_7, EXCHANGE_RATE_7, emptyList());
    }

    public static AidaSimpleInvoiceDto issueCreationExceptionCausingSimpleInvoice() {
        final AidaSimpleInvoiceDto invoice = dueSimpleInvoice6();
        invoice.setCompanyId(EXCEPTION_CAUSING_AIDA_COMPANY_ID);
        return invoice;
    }

    public static AidaInvoiceWithCorrectionsDto invoiceWithCorrectionsWithoutBalance() {
        final AidaInvoiceWithCorrectionsDto invoice = dueInvoiceWithCorrections1();
        invoice.setBalanceOnDocument(null);
        invoice.setPaid(null);
        return invoice;
    }

    private static AidaSimpleInvoiceDto simpleInvoiceDto(final Long companyId, final String contractNumber, final String invoiceNumber, final Long id) {
        final AidaSimpleInvoiceDto invoiceDto = new AidaSimpleInvoiceDto();
        invoiceDto.setId(id);
        invoiceDto.setCompanyId(companyId);
        invoiceDto.setContractNo(contractNumber);
        invoiceDto.setNumber(invoiceNumber);
        return invoiceDto;
    }

    private static AidaInvoiceDto invoiceDto(final long clientId, final String invoiceNumber, final BigDecimal invoiceBalance, final Long id, final String
            invoiceContractNo, final BigDecimal netAmount, final BigDecimal grossAmount, final String currency, final Date issueDate, final Date realDueDate,
                                             final Date lastPaymentDate, final InvoiceTypeDto invoiceType, final BigDecimal paid, final BigDecimal exchangeRate) {
        final AidaInvoiceDto invoiceDto = new AidaInvoiceDto();
        invoiceDto.setCompanyId(clientId);
        invoiceDto.setNumber(invoiceNumber);
        invoiceDto.setBalanceOnDocument(invoiceBalance);
        prepareInvoiceDto(id, invoiceContractNo, netAmount, grossAmount, currency, issueDate, realDueDate, lastPaymentDate, invoiceType, paid, exchangeRate, invoiceDto);
        return invoiceDto;
    }

    private static void prepareInvoiceDto(final Long id, final String invoiceContractNo, final BigDecimal netAmount, final BigDecimal grossAmount,
                                          final String currency, final Date issueDate, final Date realDueDate, final Date lastPaymentDate,
                                          final InvoiceTypeDto invoiceType, final BigDecimal paid, final BigDecimal exchangeRate, final AidaInvoiceDto invoiceDto) {
        invoiceDto.setId(id);
        invoiceDto.setContractNo(invoiceContractNo);
        invoiceDto.setNetAmount(netAmount);
        invoiceDto.setGrossAmount(grossAmount);
        invoiceDto.setCurrency(currency);
        invoiceDto.setIssueDate(issueDate);
        invoiceDto.setRealDueDate(realDueDate);
        invoiceDto.setLastPaymentDate(lastPaymentDate);
        invoiceDto.setInvoiceType(invoiceType);
        invoiceDto.setPaid(paid);
        invoiceDto.setExchangeRate(exchangeRate);
    }

    private static AidaInvoiceWithCorrectionsDto invoiceWithCorrectionsDto(final long clientId, final String invoiceNumber, final BigDecimal invoiceBalance,
                                                                           final Long id, final String invoiceContractNo, final BigDecimal netAmount,
                                                                           final BigDecimal grossAmount, final String currency, final Date issueDate,
                                                                           final Date realDueDate, final Date lastPaymentDate, final InvoiceTypeDto invoiceType,
                                                                           final BigDecimal paid, final BigDecimal exchangeRate, final List<AidaInvoiceDto> invoiceCorrections) {
        final AidaInvoiceWithCorrectionsDto invoiceDto = new AidaInvoiceWithCorrectionsDto();
        invoiceDto.setCompanyId(clientId);
        invoiceDto.setNumber(invoiceNumber);
        invoiceDto.setBalanceOnDocument(invoiceBalance);
        prepareInvoiceDto(id, invoiceContractNo, netAmount, grossAmount, currency, issueDate, realDueDate, lastPaymentDate, invoiceType, paid, exchangeRate, invoiceDto);
        invoiceDto.setInvoiceCorrections(invoiceCorrections);
        return invoiceDto;
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoiceWithBalanceLimitReached() {
        return simpleInvoiceDto(EXT_COMPANY_ID_1, INVOICE_CONTRACT_NUMBER_1, INVOICE_NUMBER_1, INVOICE_ID_1);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoiceWIthBalanceLimitAtThreshold() {
        return simpleInvoiceDto(EXT_COMPANY_ID_2, INVOICE_CONTRACT_NUMBER_2, INVOICE_NUMBER_2, INVOICE_ID_2);
    }

    public static AidaSimpleInvoiceDto dueSimpleInvoiceWithNoDebt() {
        return simpleInvoiceDto(EXT_COMPANY_ID_3, INVOICE_CONTRACT_NUMBER_3, INVOICE_NUMBER_3, INVOICE_ID_3);
    }

    private static InvoiceTypeDto getInvoiceTypeDto(final long id, final String label) {
        final InvoiceTypeDto invoiceTypeDto = new InvoiceTypeDto();
        invoiceTypeDto.setId(id);
        invoiceTypeDto.setLabel(label);
        return invoiceTypeDto;
    }
}
