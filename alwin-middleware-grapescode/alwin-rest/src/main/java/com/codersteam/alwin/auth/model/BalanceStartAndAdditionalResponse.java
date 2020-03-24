package com.codersteam.alwin.auth.model;

import com.codersteam.alwin.core.api.model.balance.BalanceStartAndAdditionalDto;

/**
 * @author Tomasz Sliwinski
 */
public class BalanceStartAndAdditionalResponse {

    private final BalanceStartAndAdditionalDto balanceStartAndAdditionalPLN;
    private final BalanceStartAndAdditionalDto balanceStartAndAdditionalEUR;

    public BalanceStartAndAdditionalResponse(final BalanceStartAndAdditionalDto balanceStartAndAdditionalPLN,
                                             final BalanceStartAndAdditionalDto balanceStartAndAdditionalEUR) {
        this.balanceStartAndAdditionalPLN = balanceStartAndAdditionalPLN;
        this.balanceStartAndAdditionalEUR = balanceStartAndAdditionalEUR;
    }

    public BalanceStartAndAdditionalDto getBalanceStartAndAdditionalPLN() {
        return balanceStartAndAdditionalPLN;
    }

    public BalanceStartAndAdditionalDto getBalanceStartAndAdditionalEUR() {
        return balanceStartAndAdditionalEUR;
    }
}
