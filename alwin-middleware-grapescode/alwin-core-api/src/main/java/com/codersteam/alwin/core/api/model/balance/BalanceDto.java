package com.codersteam.alwin.core.api.model.balance;

import java.math.BigDecimal;

/**
 * @author Tomasz Sliwinski
 */
public class BalanceDto {

    private final BigDecimal balance;

    public BalanceDto(final BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
