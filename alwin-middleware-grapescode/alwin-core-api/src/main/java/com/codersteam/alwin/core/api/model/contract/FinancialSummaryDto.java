package com.codersteam.alwin.core.api.model.contract;

import java.math.BigDecimal;

/**
 * @author Tomasz Sliwinski
 */
public class FinancialSummaryDto {

    /**
     * Wymagalne: suma sald wszystkich dokumentów, dla których upłynął termin płatności (wartość zaległości zwracana ze znakiem dodatnim)
     */
    private final BigDecimal requiredPayment;

    /**
     * Niewymagalne: suma sald dokumentów, dla których termin płatności minie w trakcie obsługi zlecenia (wartość zwracana ze znakiem dodatnim)
     */
    private final BigDecimal notRequiredPayment;

    /**
     * Nadpłaty: suma dodatnich sald ze wszystkich dokumentów
     */
    private final BigDecimal overpayment;

    /**
     * Razem: wymagalne + niewymagalne - nadpłaty
     */
    private final BigDecimal total;

    public FinancialSummaryDto(final BigDecimal requiredPayment, final BigDecimal notRequiredPayment, final BigDecimal overpayment) {
        this.requiredPayment = requiredPayment;
        this.notRequiredPayment = notRequiredPayment;
        this.overpayment = overpayment;
        total = requiredPayment.add(notRequiredPayment).subtract(overpayment);
    }

    public BigDecimal getRequiredPayment() {
        return requiredPayment;
    }

    public BigDecimal getNotRequiredPayment() {
        return notRequiredPayment;
    }

    public BigDecimal getOverpayment() {
        return overpayment;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
