package com.codersteam.alwin.common.termination;

import java.math.BigDecimal;

/**
 * Przechowuje koszty dla instalacji GPS
 *
 * @author Piotr Naroznik
 */
public class InstallationCharge {

    /**
     * Koszt podstawowy
     */
    private final BigDecimal standard;

    /**
     * Koszt powiększony
     */
    private final BigDecimal increased;

    public InstallationCharge(BigDecimal standard, BigDecimal increased) {
        this.standard = standard;
        this.increased = increased;
    }

    public BigDecimal getStandard() {
        return standard;
    }

    public BigDecimal getIncreased() {
        return increased;
    }
}
