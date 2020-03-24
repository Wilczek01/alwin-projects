package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.balance.BalanceStartAndAdditionalDto;

import java.math.BigDecimal;

/**
 * @author Tomasz Sliwinski
 */
public final class BalanceStartAndAdditionalTestData {

    private static final BigDecimal BALANCE_START_PLN_1 = new BigDecimal("-496.48");
    private static final BigDecimal BALANCE_ADDITIONAL_PLN_1 = new BigDecimal("0.00");
    private static final BigDecimal BALANCE_START_EUR_1 = new BigDecimal("-90.23");
    private static final BigDecimal BALANCE_ADDITIONAL_EUR_1 = new BigDecimal("-124.12");

    private BalanceStartAndAdditionalTestData() {
    }

    public static BalanceStartAndAdditionalDto balanceStartAndAdditionalPLN1() {
        return balanceStartAndAdditionalDto(BALANCE_START_PLN_1, BALANCE_ADDITIONAL_PLN_1);
    }

    public static BalanceStartAndAdditionalDto balanceStartAndAdditionalEUR1() {
        return balanceStartAndAdditionalDto(BALANCE_START_EUR_1, BALANCE_ADDITIONAL_EUR_1);
    }

    private static BalanceStartAndAdditionalDto balanceStartAndAdditionalDto(final BigDecimal balanceStart, final BigDecimal balanceAdditional) {
        return new BalanceStartAndAdditionalDto(balanceStart, balanceAdditional);
    }
}
