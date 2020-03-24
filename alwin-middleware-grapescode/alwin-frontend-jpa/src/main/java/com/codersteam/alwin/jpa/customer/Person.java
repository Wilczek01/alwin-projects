package com.codersteam.alwin.jpa.customer;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * @author Adam Stepnowski
 */
@Audited
@Entity
@Table(name = "person")
public class Person {

    @SequenceGenerator(name = "personSEQ", sequenceName = "person_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSEQ")
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_contact_detail",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_detail_id"))
    @ElementCollection(targetClass = ContactDetail.class)
    private Set<ContactDetail> contactDetails;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    @ElementCollection(targetClass = Address.class)
    private Set<Address> addresses;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "representative")
    private Boolean representative;

    @Column(name = "address")
    private String address;

    @Column(name = "marital_status")
    private Integer maritalStatus;

    @Column(name = "id_doc_country")
    private String idDocCountry;

    @Column(name = "id_doc_number")
    private String idDocNumber;

    @Column(name = "id_doc_signed_by")
    private String idDocSignedBy;

    @Column(name = "id_doc_sign_date")
    private Date idDocSignDate;

    @Column(name = "politician")
    private Boolean politician;

    @Column(name = "job_function")
    private String jobFunction;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "real_beneficiary")
    private Boolean realBeneficiary;

    @Column(name = "id_doc_type")
    private Integer idDocType;

    @Column(name = "country")
    private String country;

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

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(final Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(final Set<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(final String pesel) {
        this.pesel = pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
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

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(final Integer maritalStatus) {
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

    public Integer getIdDocType() {
        return idDocType;
    }

    public void setIdDocType(final Integer idDocType) {
        this.idDocType = idDocType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personId=" + personId +
                ", contactDetails=" + contactDetails +
                ", addresses=" + addresses +
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
                '}';
    }
}
