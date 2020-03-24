package com.codersteam.alwin.core.service.impl.issue.preparator;

import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.Balance;
import com.codersteam.alwin.jpa.issue.Issue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;

/**
 * @author Tomasz Sliwinski
 */
public final class BalancePreparator {

    private BalancePreparator() {
    }

    public static Issue fillBalance(final Issue issue, final Map<Currency, Balance> balance) {
        fillBalancePLN(issue, balance.get(Currency.PLN));
        fillBalanceEUR(issue, balance.get(Currency.EUR));
        fillTotalBalancePLN(issue, balance.get(Currency.PLN), balance.get(Currency.EUR));
        return issue;
    }

    private static void fillBalanceEUR(final Issue issue, final Balance balance) {
        if (balance == null) {
            issue.setRpbEUR(ZERO);
            issue.setBalanceStartEUR(ZERO);
            issue.setCurrentBalanceEUR(ZERO);
            issue.setPaymentsEUR(ZERO);
        } else {
            issue.setRpbEUR(balance.getRpb());
            issue.setBalanceStartEUR(balance.getBalanceStart());
            issue.setCurrentBalanceEUR(balance.getCurrentBalance());
            issue.setPaymentsEUR(balance.getPayments());
        }
    }

    private static void fillBalancePLN(final Issue issue, final Balance balance) {
        if (balance == null) {
            issue.setRpbPLN(ZERO);
            issue.setBalanceStartPLN(ZERO);
            issue.setCurrentBalancePLN(ZERO);
            issue.setPaymentsPLN(ZERO);
        } else {
            issue.setRpbPLN(balance.getRpb());
            issue.setBalanceStartPLN(balance.getBalanceStart());
            issue.setCurrentBalancePLN(balance.getCurrentBalance());
            issue.setPaymentsPLN(balance.getPayments());
        }
    }

    private static void fillTotalBalancePLN(final Issue issue, final Balance... balances) {
        issue.setTotalBalanceStartPLN(sumOfValues(balances, Balance::getBalanceStartPln));
        issue.setTotalCurrentBalancePLN(sumOfValues(balances, Balance::getCurrentBalancePln));
        issue.setTotalPaymentsPLN(sumOfValues(balances, Balance::getPaymentsPln));
    }

    private static BigDecimal sumOfValues(final Balance[] balances, final Function<Balance, BigDecimal> valueProvider) {
        if (Arrays.stream(balances).anyMatch(Objects::isNull) || Arrays.stream(balances).map(valueProvider).anyMatch(Objects::isNull)) return null;
        return Arrays.stream(balances)
                .map(valueProvider)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
