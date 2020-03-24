package com.codersteam.alwin.jpa;

import java.math.BigDecimal;

/**
 * Portfel
 *
 * @author Piotr Naroznik
 */
public class Wallet {

    /**
     * Liczba klientów z otwartym zleceniem danego typu w danym segmencie
     */
    private final Long companyCount;

    /**
     * Liczba zleceń z czynnościami do wykonania na dzień dzisiejszy (bieżące + zaległe)
     */
    private Long currentIssueQueueCount;

    /**
     * Suma zaangażowań otwartych zleceń w portfelu
     */
    private final BigDecimal involvement;

    /**
     * Suma sald otwartych zleceń w portfelu (PLN)
     */
    private final BigDecimal currentBalancePLN;

    /**
     * Suma sald otwartych zleceń w portfelu (EUR)
     */
    private final BigDecimal currentBalanceEUR;

    public Wallet(final Long companyCount, final BigDecimal involvement, final BigDecimal currentBalancePLN,
                  final BigDecimal currentBalanceEUR) {
        this.companyCount = companyCount;
        this.involvement = involvement;
        this.currentBalancePLN = currentBalancePLN;
        this.currentBalanceEUR = currentBalanceEUR;
    }

    public Long getCompanyCount() {
        return companyCount;
    }

    public BigDecimal getInvolvement() {
        return involvement;
    }

    public BigDecimal getCurrentBalancePLN() {
        return currentBalancePLN;
    }

    public BigDecimal getCurrentBalanceEUR() {
        return currentBalanceEUR;
    }

    public Long getCurrentIssueQueueCount() {
        return currentIssueQueueCount;
    }

    public void setCurrentIssueQueueCount(final Long currentIssueQueueCount) {
        this.currentIssueQueueCount = currentIssueQueueCount;
    }

    public boolean isEmpty() {
        return companyCount == 0;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "companyCount=" + companyCount +
                ", currentIssueQueueCount=" + currentIssueQueueCount +
                ", involvement=" + involvement +
                ", currentBalancePLN=" + currentBalancePLN +
                ", currentBalanceEUR=" + currentBalanceEUR +
                '}';
    }
}
