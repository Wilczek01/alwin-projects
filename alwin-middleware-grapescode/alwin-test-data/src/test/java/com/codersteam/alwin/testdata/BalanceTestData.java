package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.Balance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.currency.Currency.EUR;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static com.codersteam.alwin.testdata.TestBigDecimalUtils.valueOf;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public final class BalanceTestData {

    public static final BigDecimal EUR_EXCHANGE_RATE = valueOf("4.00");

    private static final BigDecimal RPB_PLN_1 = valueOf("1223.34");
    private static final BigDecimal BALANCE_START_PLN_1 = valueOf("223.23");
    private static final BigDecimal CURRENT_BALANCE_PLN_1 = valueOf("23.23");
    private static final BigDecimal PAYMENTS_PLN_1 = valueOf("323.34");
    private static final BigDecimal TOTAL_BALANCE_START_PLN_1 = valueOf("223.23");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_1 = valueOf("23.23");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_1 = valueOf("323.34");

    private static final BigDecimal RPB_PLN_2 = valueOf("435.34");
    private static final BigDecimal BALANCE_START_PLN_2 = valueOf("324.23");
    private static final BigDecimal CURRENT_BALANCE_PLN_2 = valueOf("352.23");
    private static final BigDecimal PAYMENTS_PLN_2 = valueOf("234.34");
    private static final BigDecimal TOTAL_BALANCE_START_PLN_2 = valueOf("324.23");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_2 = valueOf("352.23");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_2 = valueOf("234.34");

    private static final BigDecimal RPB_PLN_3 = valueOf("435.34");
    public static final BigDecimal BALANCE_START_PLN_3 = valueOf("-100.00");
    private static final BigDecimal CURRENT_BALANCE_PLN_3 = valueOf("-0.01");
    private static final BigDecimal PAYMENTS_PLN_3 = valueOf("1234.34");
    public static final BigDecimal TOTAL_BALANCE_START_PLN_3 = valueOf("-100.00");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_3 = valueOf("-0.01");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_3 = valueOf("1234.34");

    private static final BigDecimal RPB_PLN_4 = valueOf("10.00");
    public static final BigDecimal BALANCE_START_PLN_4 = valueOf("-99334.35");
    private static final BigDecimal CURRENT_BALANCE_PLN_4 = valueOf("-99334.35");
    private static final BigDecimal PAYMENTS_PLN_4 = valueOf("0.00");
    public static final BigDecimal TOTAL_BALANCE_START_PLN_4 = valueOf("-99334.35");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_4 = valueOf("-99334.35");
    private static final BigDecimal TOTAL_PAYMENTS_PLN_4 = valueOf("0.00");

    private static final BigDecimal RPB_EUR_5 = valueOf("0.00");
    public static final BigDecimal BALANCE_START_EUR_5 = valueOf("0.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_5 = valueOf("0.00");
    private static final BigDecimal PAYMENTS_EUR_5 = valueOf("0.00");
    public static final BigDecimal TOTAL_BALANCE_START_EUR_5 = valueOf("0.00");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_EUR_5 = valueOf("0.00");
    private static final BigDecimal TOTAL_PAYMENTS_EUR_5 = valueOf("0.00");

    private static final BigDecimal RPB_PLN_6 = valueOf("10.00");
    public static final BigDecimal BALANCE_START_PLN_6 = valueOf("324.23");
    public static final BigDecimal CURRENT_BALANCE_PLN_6 = valueOf("352.23");
    public static final BigDecimal PAYMENTS_PLN_6 = valueOf("234.34");
    public static final BigDecimal TOTAL_BALANCE_START_PLN_6 = valueOf("324.23");
    public static final BigDecimal TOTAL_CURRENT_BALANCE_PLN_6 = valueOf("352.23");
    public static final BigDecimal TOTAL_PAYMENTS_PLN_6 = valueOf("234.34");

    private static final BigDecimal RPB_EUR_7 = valueOf("0.00");
    public static final BigDecimal BALANCE_START_EUR_7 = valueOf("435.34");
    public static final BigDecimal CURRENT_BALANCE_EUR_7 = valueOf("-100.00");
    public static final BigDecimal PAYMENTS_EUR_7 = valueOf("-0.01");
    public static final BigDecimal TOTAL_BALANCE_START_EUR_7 = null;
    public static final BigDecimal TOTAL_CURRENT_BALANCE_EUR_7 = null;
    public static final BigDecimal TOTAL_PAYMENTS_EUR_7 = null;

    public static final BigDecimal RPB_PLN = valueOf("21323.23");
    private static final BigDecimal BALANCE_START_PLN = valueOf("-1241.20");
    private static final BigDecimal CURRENT_BALANCE_PLN = valueOf("-1231.20");
    private static final BigDecimal PAYMENTS_PLN = valueOf("10.00");
    private static final BigDecimal TOTAL_BALANCE_START_PLN = valueOf("-1241.20");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_PLN = valueOf("-1231.20");
    private static final BigDecimal TOTAL_PAYMENTS_PLN = valueOf("10.00");

    public static final BigDecimal RPB_EUR = valueOf("0.00");
    private static final BigDecimal BALANCE_START_EUR = valueOf("-525.92");
    private static final BigDecimal CURRENT_BALANCE_EUR = valueOf("-505.92");
    private static final BigDecimal PAYMENTS_EUR = valueOf("20.00");
    private static final BigDecimal TOTAL_BALANCE_START_EUR = null;
    private static final BigDecimal TOTAL_CURRENT_BALANCE_EUR = null;
    private static final BigDecimal TOTAL_PAYMENTS_EUR = null;

    public static final BigDecimal ALL_EXCHANGE_RATES_EXISTS_RPB_EUR = valueOf("0.00");
    private static final BigDecimal ALL_EXCHANGE_RATES_EXISTS_BALANCE_START_EUR = valueOf("-502.23");
    private static final BigDecimal ALL_EXCHANGE_RATES_EXISTS_CURRENT_BALANCE_EUR = valueOf("-492.23");
    private static final BigDecimal ALL_EXCHANGE_RATES_EXISTS_PAYMENTS_EUR = valueOf("10.00");
    private static final BigDecimal ALL_EXCHANGE_RATES_EXISTS_TOTAL_BALANCE_START_EUR = valueOf("-2110.12");
    private static final BigDecimal ALL_EXCHANGE_RATES_EXISTS_TOTAL_CURRENT_BALANCE_EUR = valueOf("-2068.10");
    private static final BigDecimal ALL_EXCHANGE_RATES_EXISTS_TOTAL_PAYMENTS_EUR = valueOf("42.02");

    public static final BigDecimal ISSUE_SUBJECT_INVOICES_RPB_PLN = valueOf("21323.23");
    public static final BigDecimal ISSUE_SUBJECT_INVOICES_BALANCE_START_PLN = valueOf("-99234.35");
    public static final BigDecimal ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_PLN = valueOf("-99234.35");
    public static final BigDecimal ISSUE_SUBJECT_INVOICES_PAYMENTS_PLN = valueOf("0.00");
    public static final BigDecimal TOTAL_ISSUE_SUBJECT_INVOICES_BALANCE_START_PLN = valueOf("-99234.35");
    public static final BigDecimal TOTAL_ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_PLN = valueOf("-99234.35");
    public static final BigDecimal TOTAL_ISSUE_SUBJECT_INVOICES_PAYMENTS_PLN = valueOf("0.00");

    public static final BigDecimal ISSUE_SUBJECT_INVOICES_RPB_EUR = valueOf("0.00");
    public static final BigDecimal ISSUE_SUBJECT_INVOICES_BALANCE_START_EUR = valueOf("-132.23");
    public static final BigDecimal ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_EUR = valueOf("-132.23");
    public static final BigDecimal ISSUE_SUBJECT_INVOICES_PAYMENTS_EUR = valueOf("0.00");
    public static final BigDecimal TOTAL_ISSUE_SUBJECT_INVOICES_BALANCE_START_EUR = valueOf("-570.22");
    public static final BigDecimal TOTAL_ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_EUR = valueOf("-570.22");
    public static final BigDecimal TOTAL_ISSUE_SUBJECT_INVOICES_PAYMENTS_EUR = valueOf("0.00");

    private static final BigDecimal RPB_EUR_2 = valueOf("0.00");
    private static final BigDecimal BALANCE_START_EUR_2 = valueOf("-110.00");
    private static final BigDecimal CURRENT_BALANCE_EUR_2 = valueOf("-90.23");
    private static final BigDecimal PAYMENTS_EUR_2 = valueOf("32324.23");
    private static final BigDecimal TOTAL_BALANCE_START_EUR_2 = valueOf("-440.00");
    private static final BigDecimal TOTAL_CURRENT_BALANCE_EUR_2 = valueOf("39740940.92");
    private static final BigDecimal TOTAL_PAYMENTS_EUR_2 = valueOf("129296.92");

    public static Map<Currency, Balance> positiveBalance() {
        final Map<Currency, Balance> balances = new HashMap<>();
        balances.put(PLN, balancePLN2());
        balances.put(EUR, balanceZero());
        return balances;
    }

    public static Map<Currency, Balance> zeroBalance() {
        final Map<Currency, Balance> balances = new HashMap<>();
        balances.put(PLN, balanceZero());
        balances.put(EUR, balanceZero());
        return balances;
    }

    public static Map<Currency, Balance> balanceWithNegativeBalanceThatIssueShouldBeProceed() {
        final Map<Currency, Balance> balances = new HashMap<>();
        balances.put(PLN, balancePLN3());
        balances.put(EUR, expectedBalanceEUR());
        return balances;
    }

    public static Map<Currency, Balance> balanceWithNegativeBalanceThatIssueShouldBeProceedForITTest() {
        final Map<Currency, Balance> balances = new HashMap<>();
        balances.put(PLN, balancePLN4());
        balances.put(EUR, balanceEUR5());
        return balances;
    }

    public static Map<Currency, Balance> balanceWithNegativeBalanceEURThatIssueShouldBeProceed() {
        final Map<Currency, Balance> balances = new HashMap<>();
        balances.put(PLN, balancePLN3());
        balances.put(EUR, balanceEUR2());
        return balances;
    }

    public static Map<Currency, Balance> balanceMapPLN1() {
        final Map<Currency, Balance> balances = new HashMap<>();
        balances.put(PLN, balancePLN1());
        balances.put(EUR, balanceZero());
        return balances;
    }

    public static Balance balanceZero() {
        return new Balance(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO);
    }

    public static Balance balancePLN1() {
        return new Balance(RPB_PLN_1, BALANCE_START_PLN_1, CURRENT_BALANCE_PLN_1, PAYMENTS_PLN_1,
                TOTAL_BALANCE_START_PLN_1, TOTAL_CURRENT_BALANCE_PLN_1, TOTAL_PAYMENTS_PLN_1);
    }

    public static Balance balancePLN2() {
        return new Balance(RPB_PLN_2, BALANCE_START_PLN_2, CURRENT_BALANCE_PLN_2, PAYMENTS_PLN_2,
                TOTAL_BALANCE_START_PLN_2, TOTAL_CURRENT_BALANCE_PLN_2, TOTAL_PAYMENTS_PLN_2);
    }

    public static Balance balancePLN3() {
        return new Balance(RPB_PLN_3, BALANCE_START_PLN_3, CURRENT_BALANCE_PLN_3, PAYMENTS_PLN_3,
                TOTAL_BALANCE_START_PLN_3, TOTAL_CURRENT_BALANCE_PLN_3, TOTAL_PAYMENTS_PLN_3);
    }

    public static Balance balancePLN4() {
        return new Balance(RPB_PLN_4, BALANCE_START_PLN_4, CURRENT_BALANCE_PLN_4, PAYMENTS_PLN_4,
                TOTAL_BALANCE_START_PLN_4, TOTAL_CURRENT_BALANCE_PLN_4, TOTAL_PAYMENTS_PLN_4);
    }

    public static Balance balanceEUR5() {
        return new Balance(RPB_EUR_5, BALANCE_START_EUR_5, CURRENT_BALANCE_EUR_5, PAYMENTS_EUR_5,
                TOTAL_BALANCE_START_EUR_5, TOTAL_CURRENT_BALANCE_EUR_5, TOTAL_PAYMENTS_EUR_5);
    }

    public static Balance balancePLN6() {
        return new Balance(RPB_PLN_6, BALANCE_START_PLN_6, CURRENT_BALANCE_PLN_6, PAYMENTS_PLN_6,
                TOTAL_BALANCE_START_PLN_6, TOTAL_CURRENT_BALANCE_PLN_6, TOTAL_PAYMENTS_PLN_6);
    }

    public static Balance balanceEUR7() {
        return new Balance(RPB_EUR_7, BALANCE_START_EUR_7, CURRENT_BALANCE_EUR_7, PAYMENTS_EUR_7,
                TOTAL_BALANCE_START_EUR_7, TOTAL_CURRENT_BALANCE_EUR_7, TOTAL_PAYMENTS_EUR_7);
    }

    public static Balance expectedBalancePLN() {
        return new Balance(RPB_PLN, BALANCE_START_PLN, CURRENT_BALANCE_PLN, PAYMENTS_PLN,
                TOTAL_BALANCE_START_PLN, TOTAL_CURRENT_BALANCE_PLN, TOTAL_PAYMENTS_PLN);
    }

    public static Balance expectedBalanceIssueSubjectInvoicesPLN() {
        return new Balance(ISSUE_SUBJECT_INVOICES_RPB_PLN, ISSUE_SUBJECT_INVOICES_BALANCE_START_PLN, ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_PLN,
                ISSUE_SUBJECT_INVOICES_PAYMENTS_PLN, TOTAL_ISSUE_SUBJECT_INVOICES_BALANCE_START_PLN, TOTAL_ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_PLN,
                TOTAL_ISSUE_SUBJECT_INVOICES_PAYMENTS_PLN);
    }

    public static Balance expectedBalanceEUR() {
        return new Balance(RPB_EUR, BALANCE_START_EUR, CURRENT_BALANCE_EUR, PAYMENTS_EUR, TOTAL_BALANCE_START_EUR, TOTAL_CURRENT_BALANCE_EUR, TOTAL_PAYMENTS_EUR);
    }

    public static Balance expectedBalanceEURWithAllExchangeRates() {
        return new Balance(ALL_EXCHANGE_RATES_EXISTS_RPB_EUR, ALL_EXCHANGE_RATES_EXISTS_BALANCE_START_EUR, ALL_EXCHANGE_RATES_EXISTS_CURRENT_BALANCE_EUR,
                ALL_EXCHANGE_RATES_EXISTS_PAYMENTS_EUR, ALL_EXCHANGE_RATES_EXISTS_TOTAL_BALANCE_START_EUR, ALL_EXCHANGE_RATES_EXISTS_TOTAL_CURRENT_BALANCE_EUR,
                ALL_EXCHANGE_RATES_EXISTS_TOTAL_PAYMENTS_EUR);
    }

    public static Balance expectedBalanceIssueSubjectInvoicesEUR() {
        return new Balance(ISSUE_SUBJECT_INVOICES_RPB_EUR, ISSUE_SUBJECT_INVOICES_BALANCE_START_EUR, ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_EUR,
                ISSUE_SUBJECT_INVOICES_PAYMENTS_EUR, TOTAL_ISSUE_SUBJECT_INVOICES_BALANCE_START_EUR, TOTAL_ISSUE_SUBJECT_INVOICES_CURRENT_BALANCE_EUR,
                TOTAL_ISSUE_SUBJECT_INVOICES_PAYMENTS_EUR);
    }

    public static Balance balanceEUR2() {
        return new Balance(RPB_EUR_2, BALANCE_START_EUR_2, CURRENT_BALANCE_EUR_2, PAYMENTS_EUR_2,
                TOTAL_BALANCE_START_EUR_2, TOTAL_CURRENT_BALANCE_EUR_2, TOTAL_PAYMENTS_EUR_2);
    }

    private BalanceTestData() {
    }
}
