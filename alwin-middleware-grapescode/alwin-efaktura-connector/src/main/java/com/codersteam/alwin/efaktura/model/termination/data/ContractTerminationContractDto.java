package com.codersteam.alwin.efaktura.model.termination.data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Dariusz Rackowski
 */
public class ContractTerminationContractDto {

    /**
     * Opłata za wznowienie
     */
    private BigDecimal activationFee;

    /**
     * Numer umowy
     */
    private String contractNo;

    /**
     * Typ finansowania
     */
    private ContractTypeDto contractType;

    /**
     * Lista zaległych faktur
     */
    private List<InvoiceDto> invoices;

    /**
     * Przedmioty leasingu
     */
    private List<ContractSubjectDto> subjects;

    public BigDecimal getActivationFee() {
        return activationFee;
    }

    public void setActivationFee(final BigDecimal activationFee) {
        this.activationFee = activationFee;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public ContractTypeDto getContractType() {
        return contractType;
    }

    public void setContractType(final ContractTypeDto contractType) {
        this.contractType = contractType;
    }

    public List<InvoiceDto> getInvoices() {
        return invoices;
    }

    public void setInvoices(final List<InvoiceDto> invoices) {
        this.invoices = invoices;
    }

    public List<ContractSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(final List<ContractSubjectDto> subjects) {
        this.subjects = subjects;
    }
}
