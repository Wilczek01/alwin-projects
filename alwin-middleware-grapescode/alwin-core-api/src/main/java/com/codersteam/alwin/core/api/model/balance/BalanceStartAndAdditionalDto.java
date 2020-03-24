package com.codersteam.alwin.core.api.model.balance;

import java.math.BigDecimal;

/**
 * Klasa przechowująca saldo wymagane i niewymagane
 *
 * @author Tomasz Sliwinski
 */
public class BalanceStartAndAdditionalDto {

    /**
     * Suma sald dokumentów do momentu rozpoczęcia zlecenia (wymagane)
     */
    private BigDecimal balanceStart;

    /**
     * Suma sald dokumentów zafakturowanych w trakcie trwania zlecenia (niewymagane)
     */
    private BigDecimal balanceAdditional;

    public BalanceStartAndAdditionalDto(final BigDecimal balanceStart, final BigDecimal balanceAdditional) {
        this.balanceStart = balanceStart;
        this.balanceAdditional = balanceAdditional;
    }

    public BigDecimal getBalanceStart() {
        return balanceStart;
    }

    public void setBalanceStart(final BigDecimal balanceStart) {
        this.balanceStart = balanceStart;
    }

    public BigDecimal getBalanceAdditional() {
        return balanceAdditional;
    }

    public void setBalanceAdditional(final BigDecimal balanceAdditional) {
        this.balanceAdditional = balanceAdditional;
    }
}
