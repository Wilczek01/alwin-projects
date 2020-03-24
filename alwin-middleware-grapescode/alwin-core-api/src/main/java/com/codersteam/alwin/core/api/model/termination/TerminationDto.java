package com.codersteam.alwin.core.api.model.termination;

import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.core.api.model.customer.AddressDto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Wypowiedzenie umów klienta
 *
 * @author Dariusz Rackowski
 */
public class TerminationDto {

    /**
     * Nr klienta (z LEO)
     */
    private Long extCompanyId;

    /**
     * Data wypowiedzenia
     */
    private Date terminationDate;

    /**
     * Typ wypowiedzenia
     */
    private ContractTerminationType type;

    /**
     * Nazwa klienta
     */
    private String companyName;

    /**
     * Adres siedziby
     */
    private AddressDto companyAddress;

    /**
     * Adres korespondencyjny
     */
    private AddressDto companyCorrespondenceAddress;

    /**
     * Lista wypowiedzeń umów
     */
    private List<TerminationContractDto> contracts;

    /**
     * Linki do wypowiedzeń (wysyłane na kilka adresów)
     */
    private Set<String> attachmentRefs;

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(final Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public ContractTerminationType getType() {
        return type;
    }

    public void setType(final ContractTerminationType type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public AddressDto getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(final AddressDto companyAddress) {
        this.companyAddress = companyAddress;
    }

    public AddressDto getCompanyCorrespondenceAddress() {
        return companyCorrespondenceAddress;
    }

    public void setCompanyCorrespondenceAddress(final AddressDto companyCorrespondenceAddress) {
        this.companyCorrespondenceAddress = companyCorrespondenceAddress;
    }

    public List<TerminationContractDto> getContracts() {
        return contracts;
    }

    public void setContracts(final List<TerminationContractDto> contracts) {
        this.contracts = contracts;
    }

    public Set<String> getAttachmentRefs() {
        return attachmentRefs;
    }

    public void setAttachmentRefs(final Set<String> attachmentRefs) {
        this.attachmentRefs = attachmentRefs;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TerminationDto that = (TerminationDto) o;
        return Objects.equals(extCompanyId, that.extCompanyId) &&
                Objects.equals(terminationDate, that.terminationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extCompanyId, terminationDate);
    }
}
