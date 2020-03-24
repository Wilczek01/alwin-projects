package com.codersteam.alwin.jpa.notice;

import java.util.Objects;

/**
 * Faktura firmy inicjująca wystawienie wezwania do zapłaty
 *
 * @author Tomasz Sliwinski
 */
public class DemandForPaymentCompanyInitialInvoice {

    /**
     * Identyfikator firmy (AIDA)
     */
    private final Long extCompanyId;

    /**
     * Numer kontraktu
     */
    private final String contractNo;

    /**
     * Numer faktury
     */
    private final String invoiceNo;

    public DemandForPaymentCompanyInitialInvoice(final Long extCompanyId, final String contractNo, final String invoiceNo) {
        this.extCompanyId = extCompanyId;
        this.contractNo = contractNo;
        this.invoiceNo = invoiceNo;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    @Override
    public String toString() {
        return "DemandForPaymentCompanyInitialInvoice{" +
                "extCompanyId=" + extCompanyId +
                ", contractNumber='" + contractNo + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DemandForPaymentCompanyInitialInvoice that = (DemandForPaymentCompanyInitialInvoice) o;
        return Objects.equals(extCompanyId, that.extCompanyId) &&
                Objects.equals(contractNo, that.contractNo) &&
                Objects.equals(invoiceNo, that.invoiceNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extCompanyId, contractNo, invoiceNo);
    }
}
