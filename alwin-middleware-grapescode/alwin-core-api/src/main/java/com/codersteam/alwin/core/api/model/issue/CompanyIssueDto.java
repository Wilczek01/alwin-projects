package com.codersteam.alwin.core.api.model.issue;

import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Klasa przechowująca informacje o zleceniu danej firmy - nie zawiera danych firmy, ponieważ wiemy dla jakiej firmy o zlecenia pytamy. Nie posiada również
 * informacji na temat przypisanego użytkownika oraz kontraktu.
 *
 * @author Michal Horowic
 */
public class CompanyIssueDto {
    private Long id;
    private Date startDate;
    private Date expirationDate;
    private String terminationCause;
    private OperatorUserDto terminationOperator;
    private Date terminationDate;
    private IssueTypeDto issueType;
    private IssueStateDto issueState;
    private BigDecimal rpbPLN;
    private BigDecimal balanceStartPLN;
    private BigDecimal currentBalancePLN;
    private BigDecimal paymentsPLN;
    private BigDecimal rpbEUR;
    private BigDecimal balanceStartEUR;
    private BigDecimal currentBalanceEUR;
    private BigDecimal paymentsEUR;
    private Boolean excludedFromStats;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getTerminationCause() {
        return terminationCause;
    }

    public void setTerminationCause(final String terminationCause) {
        this.terminationCause = terminationCause;
    }

    public IssueTypeDto getIssueType() {
        return issueType;
    }

    public void setIssueType(final IssueTypeDto issueType) {
        this.issueType = issueType;
    }

    public IssueStateDto getIssueState() {
        return issueState;
    }

    public void setIssueState(final IssueStateDto issueState) {
        this.issueState = issueState;
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

    public Boolean getExcludedFromStats() {
        return excludedFromStats;
    }

    public void setExcludedFromStats(final Boolean excludedFromStats) {
        this.excludedFromStats = excludedFromStats;
    }

    public OperatorUserDto getTerminationOperator() {
        return terminationOperator;
    }

    public void setTerminationOperator(final OperatorUserDto terminationOperator) {
        this.terminationOperator = terminationOperator;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(final Date terminationDate) {
        this.terminationDate = terminationDate;
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
