package com.codersteam.alwin.jpa.notice;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Konfiguracja typu wezwania do zapłaty
 *
 * @author Tomasz Sliwinski
 */
@Entity
@Table(name = "demand_for_payment_type_configuration")
public class DemandForPaymentTypeConfiguration {

    @SequenceGenerator(name = "demandForPaymentTypeConfigurationSEQ", sequenceName = "demand_for_payment_type_configuration_id_seq", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "demandForPaymentTypeConfigurationSEQ")
    private Long id;

    /**
     * Typ wezwania
     */
    @Column(name = "key", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private DemandForPaymentTypeKey key;

    /**
     * DPD, po przekroczeniu którego dla FV generujemy wezwanie
     */
    @Column(name = "dpd_start", nullable = false)
    private Integer dpdStart;

    /**
     * Liczba dni na zapłatę
     */
    @Column(name = "number_of_days_to_pay", nullable = false)
    private Integer numberOfDaysToPay;

    /**
     * Koszt opłaty za wezwanie
     */
    @Column(name = "charge", nullable = false)
    private BigDecimal charge;

    /**
     * Segment
     */
    @Column(name = "segment", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Segment segment;

    /**
     * Minimalna zaległość dokumentu kwalifikująca umowę do wystawienia wezwania (wartość poniżej)
     */
    @Column(name = "min_due_payment_value", nullable = false)
    private BigDecimal minDuePaymentValue;

    /**
     * Minimalny % zaległości (jeśli nie jest podany, nie stosujemy porównania, zwracane są FV z zadłużeniem procentowo większym lub równym)
     * TODO: w najnowszej implementacji wartości procentowe nie są uwzględnianie - tylko wartość kwotowa (minDuePaymentValue) - do zaimplementowania po zgłoszeniu wymagań przez biznes
     */
    @Column(name = "min_due_payment_percent")
    private BigDecimal minDuePaymentPercent;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public DemandForPaymentTypeKey getKey() {
        return key;
    }

    public void setKey(final DemandForPaymentTypeKey key) {
        this.key = key;
    }

    public Integer getDpdStart() {
        return dpdStart;
    }

    public void setDpdStart(final Integer dpdStart) {
        this.dpdStart = dpdStart;
    }

    public Integer getNumberOfDaysToPay() {
        return numberOfDaysToPay;
    }

    public void setNumberOfDaysToPay(final Integer numberOfDaysToPay) {
        this.numberOfDaysToPay = numberOfDaysToPay;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(final BigDecimal charge) {
        this.charge = charge;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(final Segment segment) {
        this.segment = segment;
    }

    public BigDecimal getMinDuePaymentValue() {
        return minDuePaymentValue;
    }

    public void setMinDuePaymentValue(final BigDecimal minDuePaymentValue) {
        this.minDuePaymentValue = minDuePaymentValue;
    }

    public BigDecimal getMinDuePaymentPercent() {
        return minDuePaymentPercent;
    }

    public void setMinDuePaymentPercent(final BigDecimal minDuePaymentPercent) {
        this.minDuePaymentPercent = minDuePaymentPercent;
    }

    @Override
    public String toString() {
        return "DemandForPaymentType{" +
                "id=" + id +
                ", key=" + key +
                ", dpdStart=" + dpdStart +
                ", numberOfDaysToPay=" + numberOfDaysToPay +
                ", charge=" + charge +
                ", segment=" + segment +
                ", minDuePaymentValue=" + minDuePaymentValue +
                ", minDuePaymentPercent=" + minDuePaymentPercent +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DemandForPaymentTypeConfiguration that = (DemandForPaymentTypeConfiguration) o;
        return Objects.equals(id, that.id) &&
                key == that.key &&
                Objects.equals(dpdStart, that.dpdStart) &&
                Objects.equals(numberOfDaysToPay, that.numberOfDaysToPay) &&
                Objects.equals(charge, that.charge) &&
                segment == that.segment &&
                Objects.equals(minDuePaymentValue, that.minDuePaymentValue) &&
                Objects.equals(minDuePaymentPercent, that.minDuePaymentPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, dpdStart, numberOfDaysToPay, charge, segment, minDuePaymentValue, minDuePaymentPercent);
    }
}
