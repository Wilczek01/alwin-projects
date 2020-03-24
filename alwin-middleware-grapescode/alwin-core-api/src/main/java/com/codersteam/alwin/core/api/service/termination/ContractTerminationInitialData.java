package com.codersteam.alwin.core.api.service.termination;

import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;

import java.util.Date;
import java.util.Set;

/**
 * Zestaw danych do rozpoczęcia procesu wystawienia wypowiedzenia umowy
 *
 * @author Tomasz Sliwinski
 */
public class ContractTerminationInitialData {

    /**
     * Identyfikator firmy w systemie AIDA
     */
    private Long extCompanyId;

    /**
     * Numer umowy
     */
    private String contractNumber;

    /**
     * Numer faktury inicjującej wystawienie
     */
    private String initialInvoiceNo;

    /**
     * Termin zapłaty z wezwania
     */
    private Date dueDateInDemandForPayment;

    /**
     * Kolekcja zaległych dokumentów umowy wskazanych w wezwaniu (w tym inicjujący)
     */
    private Set<FormalDebtCollectionInvoiceDto> invoices;

    /**
     * Identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
     */
    private Long precedingDemandForPaymentId;

    /**
     * Identyfikator poprzedzającego wypowiedzenia umowy (jeśli istnieje)
     */
    private Long precedingContractTerminationId;

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public String getInitialInvoiceNo() {
        return initialInvoiceNo;
    }

    public void setInitialInvoiceNo(final String initialInvoiceNo) {
        this.initialInvoiceNo = initialInvoiceNo;
    }

    public Set<FormalDebtCollectionInvoiceDto> getInvoices() {
        return invoices;
    }

    public void setInvoices(final Set<FormalDebtCollectionInvoiceDto> invoices) {
        this.invoices = invoices;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public void setContractNumber(final String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getDueDateInDemandForPayment() {
        return dueDateInDemandForPayment;
    }

    public void setDueDateInDemandForPayment(final Date dueDateInDemandForPayment) {
        this.dueDateInDemandForPayment = dueDateInDemandForPayment;
    }

    public Long getPrecedingDemandForPaymentId() {
        return precedingDemandForPaymentId;
    }

    public void setPrecedingDemandForPaymentId(Long precedingDemandForPaymentId) {
        this.precedingDemandForPaymentId = precedingDemandForPaymentId;
    }

    public Long getPrecedingContractTerminationId() {
        return precedingContractTerminationId;
    }

    public void setPrecedingContractTerminationId(Long precedingContractTerminationId) {
        this.precedingContractTerminationId = precedingContractTerminationId;
    }
}
