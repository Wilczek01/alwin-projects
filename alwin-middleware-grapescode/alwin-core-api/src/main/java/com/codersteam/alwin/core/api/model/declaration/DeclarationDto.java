package com.codersteam.alwin.core.api.model.declaration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author Michal Horowic
 */
public class DeclarationDto {

    private Long id;
    private Date declarationDate;
    private Date declaredPaymentDate;
    private BigDecimal declaredPaymentAmountPLN;
    private BigDecimal declaredPaymentAmountEUR;
    private BigDecimal cashPaidPLN;
    private BigDecimal cashPaidEUR;
    private Boolean monitored;
    private BigDecimal currentBalancePLN;
    private BigDecimal currentBalanceEUR;


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(final Date declarationDate) {
        this.declarationDate = declarationDate;
    }

    public Date getDeclaredPaymentDate() {
        return declaredPaymentDate;
    }

    public void setDeclaredPaymentDate(final Date declaredPaymentDate) {
        this.declaredPaymentDate = declaredPaymentDate;
    }

    public BigDecimal getDeclaredPaymentAmountPLN() {
        return declaredPaymentAmountPLN;
    }

    public void setDeclaredPaymentAmountPLN(final BigDecimal declaredPaymentAmountPLN) {
        this.declaredPaymentAmountPLN = declaredPaymentAmountPLN;
    }

    public BigDecimal getDeclaredPaymentAmountEUR() {
        return declaredPaymentAmountEUR;
    }

    public void setDeclaredPaymentAmountEUR(final BigDecimal declaredPaymentAmountEUR) {
        this.declaredPaymentAmountEUR = declaredPaymentAmountEUR;
    }

    public BigDecimal getCashPaidPLN() {
        return cashPaidPLN;
    }

    public void setCashPaidPLN(final BigDecimal cashPaidPLN) {
        this.cashPaidPLN = cashPaidPLN;
    }

    public BigDecimal getCashPaidEUR() {
        return cashPaidEUR;
    }

    public void setCashPaidEUR(final BigDecimal cashPaidEUR) {
        this.cashPaidEUR = cashPaidEUR;
    }

    public Boolean getMonitored() {
        return monitored;
    }

    public void setMonitored(final Boolean monitored) {
        this.monitored = monitored;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DeclarationDto that = (DeclarationDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(declarationDate, that.declarationDate) &&
                Objects.equals(declaredPaymentDate, that.declaredPaymentDate) &&
                Objects.equals(declaredPaymentAmountPLN, that.declaredPaymentAmountPLN) &&
                Objects.equals(declaredPaymentAmountEUR, that.declaredPaymentAmountEUR) &&
                Objects.equals(cashPaidPLN, that.cashPaidPLN) &&
                Objects.equals(cashPaidEUR, that.cashPaidEUR) &&
                Objects.equals(monitored, that.monitored) &&
                Objects.equals(currentBalancePLN, that.currentBalancePLN) &&
                Objects.equals(currentBalanceEUR, that.currentBalanceEUR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, declarationDate, declaredPaymentDate, declaredPaymentAmountPLN, declaredPaymentAmountEUR, cashPaidPLN, cashPaidEUR, monitored,
                currentBalancePLN, currentBalanceEUR);
    }

    @Override
    public String toString() {
        return "DeclarationDto{" +
                "id=" + id +
                ", declarationDate=" + declarationDate +
                ", declaredPaymentDate=" + declaredPaymentDate +
                ", declaredPaymentAmountPLN=" + declaredPaymentAmountPLN +
                ", declaredPaymentAmountEUR=" + declaredPaymentAmountEUR +
                ", cashPaidPLN=" + cashPaidPLN +
                ", cashPaidEUR=" + cashPaidEUR +
                ", monitored=" + monitored +
                ", currentBalancePLN=" + currentBalancePLN +
                ", currentBalanceEUR=" + currentBalanceEUR +
                '}';
    }
}
