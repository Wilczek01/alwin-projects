package com.codersteam.alwin.core.api.model.issue;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Klasa obiektu przechowującego salda klienta
 *
 * @author Tomasz Sliwinski
 */
public class Balance {

    private final BigDecimal rpb;

    private final BigDecimal balanceStart;

    private final BigDecimal currentBalance;

    private final BigDecimal payments;

    /**
     * Pole przechowujące wartość balance start przeliczoną na walutę polską
     */
    private final BigDecimal balanceStartPln;

    /**
     * Pole przechowujące wartość current balance przeliczoną na walutę polską
     */
    private final BigDecimal currentBalancePln;

    /**
     * Pole przechowujące wartość payments przeliczoną na walutę polską
     */
    private final BigDecimal paymentsPln;


    public Balance(final BigDecimal rpb, final BigDecimal balanceStart, final BigDecimal currentBalance, final BigDecimal payments,
                   final BigDecimal balanceStartPln, final BigDecimal currentBalancePln, final BigDecimal paymentsPln) {
        this.rpb = rpb;
        this.balanceStart = balanceStart;
        this.currentBalance = currentBalance;
        this.payments = payments;
        this.balanceStartPln = balanceStartPln;
        this.currentBalancePln = currentBalancePln;
        this.paymentsPln = paymentsPln;
    }

    public BigDecimal getRpb() {
        return rpb;
    }

    public BigDecimal getBalanceStart() {
        return balanceStart;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getPayments() {
        return payments;
    }

    public BigDecimal getBalanceStartPln() {
        return balanceStartPln;
    }

    public BigDecimal getCurrentBalancePln() {
        return currentBalancePln;
    }

    public BigDecimal getPaymentsPln() {
        return paymentsPln;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Balance balance = (Balance) o;
        return Objects.equals(rpb, balance.rpb) &&
                Objects.equals(balanceStart, balance.balanceStart) &&
                Objects.equals(currentBalance, balance.currentBalance) &&
                Objects.equals(payments, balance.payments) &&
                Objects.equals(balanceStartPln, balance.balanceStartPln) &&
                Objects.equals(currentBalancePln, balance.currentBalancePln) &&
                Objects.equals(paymentsPln, balance.paymentsPln);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rpb, balanceStart, currentBalance, payments, balanceStartPln, currentBalancePln, paymentsPln);
    }

    @Override
    public String toString() {
        return "Balance{" +
                "rpb=" + rpb +
                ", balanceStart=" + balanceStart +
                ", currentBalance=" + currentBalance +
                ", payments=" + payments +
                ", balanceStartPln=" + balanceStartPln +
                ", currentBalancePln=" + currentBalancePln +
                ", paymentsPln=" + paymentsPln +
                '}';
    }
}
