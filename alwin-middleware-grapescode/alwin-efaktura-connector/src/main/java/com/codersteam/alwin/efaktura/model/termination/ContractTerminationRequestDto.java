package com.codersteam.alwin.efaktura.model.termination;

import com.codersteam.alwin.efaktura.model.termination.data.ContractCustomerDto;
import com.codersteam.alwin.efaktura.model.termination.data.ContractTerminationContractDto;
import com.codersteam.alwin.efaktura.model.termination.data.TerminationTypeDto;

import java.util.Date;
import java.util.List;

/**
 * @author Dariusz Rackowski
 */
public class ContractTerminationRequestDto {
    /**
     * Adres mailowy osoby aktualnie obsługującej zlecenie windykacyjne klienta
     */
    private String contactEmail;

    /**
     * Nr kontaktowy osoby aktualnie obsługującej zlecenie windykacyjne klienta
     */
    private String contactPhoneNo;

    /**
     * Lista umów do wypowiedzenia
     */
    private List<ContractTerminationContractDto> contracts;

    /**
     * Informacje o kliencie
     */
    private ContractCustomerDto customer;

    /**
     * Oprator wystawiający wypowiedzenie
     */
    private String issuingOperator;

    /**
     * Data wypowiedzenia
     */
    private Date suspensionDate;

    /**
     * Typ wypowiedzenia (warunkowe/natychmiastowe)
     */
    private TerminationTypeDto terminationType;

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(final String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(final String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public List<ContractTerminationContractDto> getContracts() {
        return contracts;
    }

    public void setContracts(final List<ContractTerminationContractDto> contracts) {
        this.contracts = contracts;
    }

    public ContractCustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(final ContractCustomerDto customer) {
        this.customer = customer;
    }

    public String getIssuingOperator() {
        return issuingOperator;
    }

    public void setIssuingOperator(final String issuingOperator) {
        this.issuingOperator = issuingOperator;
    }

    public Date getSuspensionDate() {
        return suspensionDate;
    }

    public void setSuspensionDate(final Date suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public TerminationTypeDto getTerminationType() {
        return terminationType;
    }

    public void setTerminationType(final TerminationTypeDto terminationType) {
        this.terminationType = terminationType;
    }
}
