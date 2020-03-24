package com.codersteam.alwin.jpa.termination;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Przedmioty leasingu dla wypowiedzeń umów
 */
@Audited
@Entity
@Table(name = "contract_termination_subject")
public class ContractTerminationSubject {

    /**
     * Identyfikator przedmiotu leasingu w wypowiedzeniu umowy
     */
    @SequenceGenerator(name = "contractTerminationSubjectSEQ", sequenceName = "contract_termination_subject_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractTerminationSubjectSEQ")
    private Long id;

    /**
     * Przedmiot leasingu
     */
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    /**
     * Czy ma być zainstalowany GPS?
     */
    @Column(name = "gps_to_install", length = 50, nullable = false)
    private Boolean gpsToInstall;

    /**
     * Koszt instalacji GPS
     */
    @Column(name = "gps_installation_charge")
    private BigDecimal gpsInstallationCharge;

    /**
     * Czy GPS został już zainstalowany
     */
    @Column(name = "gps_installed", nullable = false)
    private Boolean gpsInstalled;

    /**
     * Czy powiększona opłata za instalację GPS?
     */
    @Column(name = "gps_increased_fee", length = 50, nullable = false)
    private Boolean gpsIncreasedFee;

    /**
     * Powiększony koszt instalacji GPS
     */
    @Column(name = "gps_increased_installation_charge")
    private BigDecimal gpsIncreasedInstallationCharge;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getGpsToInstall() {
        return gpsToInstall;
    }

    public void setGpsToInstall(final Boolean gpsToInstall) {
        this.gpsToInstall = gpsToInstall;
    }

    public BigDecimal getGpsInstallationCharge() {
        return gpsInstallationCharge;
    }

    public void setGpsInstallationCharge(final BigDecimal gpsInstallationCharge) {
        this.gpsInstallationCharge = gpsInstallationCharge;
    }

    public Boolean getGpsInstalled() {
        return gpsInstalled;
    }

    public void setGpsInstalled(final Boolean gpsInstalled) {
        this.gpsInstalled = gpsInstalled;
    }

    public Boolean getGpsIncreasedFee() {
        return gpsIncreasedFee;
    }

    public void setGpsIncreasedFee(Boolean gpsIncreasedFee) {
        this.gpsIncreasedFee = gpsIncreasedFee;
    }

    public BigDecimal getGpsIncreasedInstallationCharge() {
        return gpsIncreasedInstallationCharge;
    }

    public void setGpsIncreasedInstallationCharge(BigDecimal gpsIncreasedInstallationCharge) {
        this.gpsIncreasedInstallationCharge = gpsIncreasedInstallationCharge;
    }

    @Override
    public String toString() {
        return "ContractTerminationSubject{" +
                "id=" + id +
                '}';
    }
}
