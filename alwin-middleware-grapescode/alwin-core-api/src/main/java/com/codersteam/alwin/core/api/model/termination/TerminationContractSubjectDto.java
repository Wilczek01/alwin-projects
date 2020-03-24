package com.codersteam.alwin.core.api.model.termination;

import java.math.BigDecimal;

/**
 * @author Dariusz Rackowski
 */
public class TerminationContractSubjectDto {

    /**
     * Identyfikator przedmiotu leasingu w wypowiedzeniu umowy
     */
    private Long id;

    /**
     * Przedmiot leasingu
     */
    private Long subjectId;

    /**
     * Czy ma być zainstalowany GPS?
     */
    private Boolean gpsToInstall;

    /**
     * Koszt instalacji GPS
     */
    private BigDecimal gpsInstallationCharge;

    /**
     * Czy przedmiot umowy posiada zainstalowany GPS?
     */
    private Boolean gpsInstalled;

    /**
     * Czy powiększona opłata za instalację GPS?
     */
    private Boolean gpsIncreasedFee;

    /**
     * Powiększony koszt instalacji GPS
     */
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
}
