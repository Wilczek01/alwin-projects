package com.codersteam.alwin.core.api.model.issue;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Portfel
 *
 * @author Piotr Naroznik
 */
public class WalletDto {

    /**
     * Liczba klientów z otwartym zleceniem danego typu w danym segmencie
     */
    private Long companyCount;

    /**
     * Liczba zleceń z czynnościami do wykonania na dzień dzisiejszy (bieżące + zaległe)
     */
    private Long currentIssueQueueCount;

    /**
     * Suma zaangażowań otwartych zleceń w portfelu
     */
    private BigDecimal involvement;

    /**
     * Suma sald otwartych zleceń w portfelu (PLN)
     */
    private BigDecimal currentBalancePLN;

    /**
     * Suma sald otwartych zleceń w portfelu (EUR)
     */
    private BigDecimal currentBalanceEUR;

    public Long getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(final Long companyCount) {
        this.companyCount = companyCount;
    }

    public BigDecimal getInvolvement() {
        return involvement;
    }

    public void setInvolvement(final BigDecimal involvement) {
        this.involvement = involvement;
    }

    public BigDecimal getCurrentBalancePLN() {
        return currentBalancePLN;
    }

    public void setCurrentBalancePLN(final BigDecimal currentBalancePLN) {
        this.currentBalancePLN = currentBalancePLN;
    }

    public BigDecimal getCurrentBalanceEUR() {
        return currentBalanceEUR;
    }

    public void setCurrentBalanceEUR(final BigDecimal currentBalanceEUR) {
        this.currentBalanceEUR = currentBalanceEUR;
    }

    public Long getCurrentIssueQueueCount() {
        return currentIssueQueueCount;
    }

    public void setCurrentIssueQueueCount(final Long currentIssueQueueCount) {
        this.currentIssueQueueCount = currentIssueQueueCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final WalletDto walletDto = (WalletDto) o;
        return Objects.equals(companyCount, walletDto.companyCount) &&
                Objects.equals(currentIssueQueueCount, walletDto.currentIssueQueueCount) &&
                Objects.equals(involvement, walletDto.involvement) &&
                Objects.equals(currentBalancePLN, walletDto.currentBalancePLN) &&
                Objects.equals(currentBalanceEUR, walletDto.currentBalanceEUR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCount, currentIssueQueueCount, involvement, currentBalancePLN, currentBalanceEUR);
    }

    @Override
    public String toString() {
        return "IssueWalletDto{" +
                ", companyCount=" + companyCount +
                ", currentIssueQueueCount=" + currentIssueQueueCount +
                ", involvement=" + involvement +
                ", currentBalancePLN=" + currentBalancePLN +
                ", currentBalanceEUR=" + currentBalanceEUR +
                '}';
    }
}
