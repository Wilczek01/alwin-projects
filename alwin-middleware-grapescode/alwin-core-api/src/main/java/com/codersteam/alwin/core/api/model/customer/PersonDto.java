package com.codersteam.alwin.core.api.model.customer;

import java.util.Date;

/**
 * @author Michal Horowic
 */
public class PersonDto {
    private Long id;
    private Long personId;
    private String pesel;
    private String firstName;
    private String lastName;
    private Boolean representative;
    private String address;
    private MaritalStatusDto maritalStatus;
    private String idDocCountry;
    private String idDocNumber;
    private String idDocSignedBy;
    private Date idDocSignDate;
    private Boolean politician;
    private String jobFunction;
    private Date birthDate;
    private Boolean realBeneficiary;
    private DocTypeDto idDocType;
    private String country;
    private boolean contactPerson;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(final Long personId) {
        this.personId = personId;
    }


    public void setPesel(final String pesel) {
        this.pesel = pesel;
    }

    public String getPesel() {
        return pesel;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getRepresentative() {
        return representative;
    }

    public void setRepresentative(final Boolean representative) {
        this.representative = representative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public MaritalStatusDto getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(final MaritalStatusDto maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIdDocCountry() {
        return idDocCountry;
    }

    public void setIdDocCountry(final String idDocCountry) {
        this.idDocCountry = idDocCountry;
    }

    public String getIdDocNumber() {
        return idDocNumber;
    }

    public void setIdDocNumber(final String idDocNumber) {
        this.idDocNumber = idDocNumber;
    }

    public String getIdDocSignedBy() {
        return idDocSignedBy;
    }

    public void setIdDocSignedBy(final String idDocSignedBy) {
        this.idDocSignedBy = idDocSignedBy;
    }

    public Date getIdDocSignDate() {
        return idDocSignDate;
    }

    public void setIdDocSignDate(final Date idDocSignDate) {
        this.idDocSignDate = idDocSignDate;
    }

    public Boolean getPolitician() {
        return politician;
    }

    public void setPolitician(final Boolean politician) {
        this.politician = politician;
    }

    public String getJobFunction() {
        return jobFunction;
    }

    public void setJobFunction(final String jobFunction) {
        this.jobFunction = jobFunction;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getRealBeneficiary() {
        return realBeneficiary;
    }

    public void setRealBeneficiary(final Boolean realBeneficiary) {
        this.realBeneficiary = realBeneficiary;
    }

    public DocTypeDto getIdDocType() {
        return idDocType;
    }

    public void setIdDocType(final DocTypeDto idDocType) {
        this.idDocType = idDocType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public boolean isContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final boolean contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "id=" + id +
                ", personId=" + personId +
                ", pesel='" + pesel + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", representative=" + representative +
                ", address='" + address + '\'' +
                ", maritalStatus=" + maritalStatus +
                ", idDocCountry='" + idDocCountry + '\'' +
                ", idDocNumber='" + idDocNumber + '\'' +
                ", idDocSignedBy='" + idDocSignedBy + '\'' +
                ", idDocSignDate=" + idDocSignDate +
                ", politician=" + politician +
                ", jobFunction='" + jobFunction + '\'' +
                ", birthDate=" + birthDate +
                ", realBeneficiary=" + realBeneficiary +
                ", idDocType=" + idDocType +
                ", country='" + country + '\'' +
                ", contactPerson=" + contactPerson +
                '}';
    }
}
