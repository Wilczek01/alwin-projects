package com.codersteam.alwin.jpa.activity;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Encja przechowująca deklarację spłaty w ramach czynności
 *
 * @author Tomasz Sliwinski
 */
@Audited
@Entity
@Table(name = "declaration")
public class Declaration {

    @Id
    @SequenceGenerator(name = "declarationSEQ", sequenceName = "declaration_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "declarationSEQ")
    private Long id;

    @Column(name = "declaration_date", nullable = false)
    private Date declarationDate;

    @Column(name = "declared_payment_date", nullable = false)
    private Date declaredPaymentDate;

    @Column(name = "declared_payment_amount_pln", nullable = false)
    private BigDecimal declaredPaymentAmountPLN;

    @Column(name = "declared_payment_amount_eur", nullable = false)
    private BigDecimal declaredPaymentAmountEUR;

    @Column(name = "cash_paid_pln")
    private BigDecimal cashPaidPLN;

    @Column(name = "cash_paid_eur")
    private BigDecimal cashPaidEUR;

    @Column(name = "monitored")
    private Boolean monitored;

    @Column(name = "current_balance_pln")
    private BigDecimal currentBalancePLN;

    @Column(name = "current_balance_eur")
    private BigDecimal currentBalanceEUR;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Activity activity;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

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

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(final Boolean paid) {
        this.paid = paid;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(final Activity activity) {
        this.activity = activity;
    }
}
