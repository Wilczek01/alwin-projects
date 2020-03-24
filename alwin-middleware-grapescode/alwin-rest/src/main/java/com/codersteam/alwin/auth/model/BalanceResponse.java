package com.codersteam.alwin.auth.model;

import java.math.BigDecimal;

/**
 * @author Adam Stepnowski
 */
public class BalanceResponse {

    private final BigDecimal balancePLN;
    private final BigDecimal balanceEUR;

    public BalanceResponse(final BigDecimal balancePLN, final BigDecimal balanceEUR) {
        this.balancePLN = balancePLN;
        this.balanceEUR = balanceEUR;
    }

    public BigDecimal getBalancePLN() {
        return balancePLN;
    }

    public BigDecimal getBalanceEUR() {
        return balanceEUR;
    }
}
