package com.codersteam.alwin.core.api.model.issue;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Piotr Naroznik
 */
public class UnresolvedIssueDto implements Serializable {

    /**
     * Identyfikator zlecenia
     */
    private Long issueId;

    /**
     * Identyfikator kontraktów w AIDA
     */
    private Set<String> extContractsNumbers;

    /**
     * Kapitał pozostały do spłaty (PLN)
     */
    private BigDecimal rpbPLN;

    /**
     * Saldo zlecenia (PLN)
     */
    private BigDecimal balanceStartPLN;

    /**
     * Aktualne saldo dokumentów, które są przedmiotem zlecenia (PLN)
     */
    private BigDecimal currentBalancePLN;

    /**
     * Suma dokonanych wpłat (PLN)
     */
    private BigDecimal paymentsPLN;

    /**
     * Kapitał pozostały do spłaty (EUR)
     */
    private BigDecimal rpbEUR;

    /**
     * Saldo zlecenia (EUR)
     */
    private BigDecimal balanceStartEUR;

    /**
     * Aktualne saldo dokumentów, które są przedmiotem zlecenia (EUR)
     */
    private BigDecimal currentBalanceEUR;

    /**
     * Suma dokonanych wpłat (EUR)
     */
    private BigDecimal paymentsEUR;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }

    public Set<String> getExtContractsNumbers() {
        return extContractsNumbers;
    }

    public void setExtContractsNumbers(final Set<String> extContractsNumbers) {
        this.extContractsNumbers = extContractsNumbers;
    }

    public BigDecimal getRpbPLN() {
        return rpbPLN;
    }

    public void setRpbPLN(final BigDecimal rpbPLN) {
        this.rpbPLN = rpbPLN;
    }

    public BigDecimal getBalanceStartPLN() {
        return balanceStartPLN;
    }

    public void setBalanceStartPLN(final BigDecimal balanceStartPLN) {
        this.balanceStartPLN = balanceStartPLN;
    }

    public BigDecimal getCurrentBalancePLN() {
        return currentBalancePLN;
    }

    public void setCurrentBalancePLN(final BigDecimal currentBalancePLN) {
        this.currentBalancePLN = currentBalancePLN;
    }

    public BigDecimal getPaymentsPLN() {
        return paymentsPLN;
    }

    public void setPaymentsPLN(final BigDecimal paymentsPLN) {
        this.paymentsPLN = paymentsPLN;
    }

    public BigDecimal getRpbEUR() {
        return rpbEUR;
    }

    public void setRpbEUR(final BigDecimal rpbEUR) {
        this.rpbEUR = rpbEUR;
    }

    public BigDecimal getBalanceStartEUR() {
        return balanceStartEUR;
    }

    public void setBalanceStartEUR(final BigDecimal balanceStartEUR) {
        this.balanceStartEUR = balanceStartEUR;
    }

    public BigDecimal getCurrentBalanceEUR() {
        return currentBalanceEUR;
    }

    public void setCurrentBalanceEUR(final BigDecimal currentBalanceEUR) {
        this.currentBalanceEUR = currentBalanceEUR;
    }

    public BigDecimal getPaymentsEUR() {
        return paymentsEUR;
    }

    public void setPaymentsEUR(final BigDecimal paymentsEUR) {
        this.paymentsEUR = paymentsEUR;
    }
}
