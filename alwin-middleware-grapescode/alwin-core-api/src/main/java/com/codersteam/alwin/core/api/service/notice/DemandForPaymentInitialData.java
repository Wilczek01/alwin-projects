package com.codersteam.alwin.core.api.service.notice;

/**
 * Zestaw danych do rozpoczęcia procesu wystawienia wezwania określonego typu dla umowy
 *
 * @author Tomasz Sliwinski
 */
public class DemandForPaymentInitialData {

    /**
     * Identyfikator typu wezwania do zapłaty
     */
    private Long demandForPaymentTypeId;

    /**
     * Identyfikator firmy w systemie AIDA
     */
    private Long extCompanyId;

    /**
     * Numer umowy
     */
    private String contractNo;

    /**
     * Numer faktury inicjującej
     */
    private String initialInvoiceNo;

    /**
     * Identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
     */
    private Long precedingDemandForPaymentId;

    public DemandForPaymentInitialData() {
    }

    public DemandForPaymentInitialData(final Long demandForPaymentTypeId, final Long extCompanyId, final String contractNo, final String initialInvoiceNo) {
        this.demandForPaymentTypeId = demandForPaymentTypeId;
        this.extCompanyId = extCompanyId;
        this.contractNo = contractNo;
        this.initialInvoiceNo = initialInvoiceNo;
    }

    public void setInitialInvoiceNo(final String initialInvoiceNo) {
        this.initialInvoiceNo = initialInvoiceNo;
    }

    public Long getDemandForPaymentTypeId() {
        return demandForPaymentTypeId;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public String getInitialInvoiceNo() {
        return initialInvoiceNo;
    }

    public Long getPrecedingDemandForPaymentId() {
        return precedingDemandForPaymentId;
    }

    public void setPrecedingDemandForPaymentId(final Long precedingDemandForPaymentId) {
        this.precedingDemandForPaymentId = precedingDemandForPaymentId;
    }

    public void setDemandForPaymentTypeId(final Long demandForPaymentTypeId) {
        this.demandForPaymentTypeId = demandForPaymentTypeId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    @Override
    public String toString() {
        return "DemandForPaymentInitialData{" +
                "demandForPaymentTypeId=" + demandForPaymentTypeId +
                ", extCompanyId=" + extCompanyId +
                ", contractNo='" + contractNo + '\'' +
                ", initialInvoiceNo='" + initialInvoiceNo + '\'' +
                ", precedingDemandForPaymentId=" + precedingDemandForPaymentId +
                '}';
    }
}
