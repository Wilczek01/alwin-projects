package com.codersteam.alwin.core.api.model.issue;

import java.math.BigDecimal;

/**
 * Obiekt korekty faktury
 *
 * @author Tomasz Sliwinski
 */
public class InvoiceCorrectionDto extends InvoiceDto {

    /**
     * Różnica kwoty brutto z poprzednią fakturą w łańcuchu korekt
     */
    private BigDecimal previousCorrectionGrossAmountDiff;

    /**
     * Różnica kwoty brutto z fakturą główną
     */
    private BigDecimal rootInvoiceGrossAmountDiff;

    public BigDecimal getPreviousCorrectionGrossAmountDiff() {
        return previousCorrectionGrossAmountDiff;
    }

    public void setPreviousCorrectionGrossAmountDiff(final BigDecimal previousCorrectionGrossAmountDiff) {
        this.previousCorrectionGrossAmountDiff = previousCorrectionGrossAmountDiff;
    }

    public BigDecimal getRootInvoiceGrossAmountDiff() {
        return rootInvoiceGrossAmountDiff;
    }

    public void setRootInvoiceGrossAmountDiff(final BigDecimal rootInvoiceGrossAmountDiff) {
        this.rootInvoiceGrossAmountDiff = rootInvoiceGrossAmountDiff;
    }

    @Override
    public String toString() {
        return "InvoiceCorrectionDto{" +
                "previousCorrectionGrossAmountDiff=" + previousCorrectionGrossAmountDiff +
                ", rootInvoiceGrossAmountDiff=" + rootInvoiceGrossAmountDiff +
                "} " + super.toString();
    }
}
