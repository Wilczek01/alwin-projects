package com.codersteam.alwin.efaktura.model.termination.data;

import java.math.BigDecimal;

/**
 * @author Dariusz Rackowski
 */
public class ContractSubjectDto {

    /**
     * Opłata za instalację GPS
     */
    private BigDecimal gpsFee;

    /**
     * Czy ma być montowany GPS w przedmiocie
     */
    private Boolean hasGps;

    /**
     * Nr rejestracyjny przedmiotu
     */
    private String plateNo;

    /**
     * Nr seryjny/VIN przedmiotu
     */
    private String serialNo;

    /**
     * Nazwa przedmiotu leasingu
     */
    private String subject;

    public BigDecimal getGpsFee() {
        return gpsFee;
    }

    public void setGpsFee(final BigDecimal gpsFee) {
        this.gpsFee = gpsFee;
    }

    public Boolean getHasGps() {
        return hasGps;
    }

    public void setHasGps(final Boolean hasGps) {
        this.hasGps = hasGps;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(final String plateNo) {
        this.plateNo = plateNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(final String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }
}
