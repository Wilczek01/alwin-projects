package com.codersteam.alwin.jpa.customer;

import com.codersteam.alwin.common.issue.Segment;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Adam Stepnowski
 */
@Audited
@Entity
@Table(name = "company")
public class Company implements Serializable {

    @SequenceGenerator(name = "companySEQ", sequenceName = "company_id_seq", allocationSize = 1, initialValue = 50)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companySEQ")
    private Long id;

    @Column(name = "ext_company_id", nullable = false)
    private Long extCompanyId;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<CompanyPerson> companyPersons;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "company_address",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    @ElementCollection(targetClass = Address.class)
    private Set<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "company_contact_detail",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_detail_id"))
    @ElementCollection(targetClass = ContactDetail.class)
    private Set<ContactDetail> contactDetails;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "nip")
    private String nip;

    @Column(name = "regon")
    private String regon;

    @Column(name = "krs")
    private String krs;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "rating")
    private String rating;

    @Column(name = "rating_date")
    private Date ratingDate;

    @Column(name = "external_db_agreement")
    private Boolean externalDBAgreement;

    @Column(name = "external_db_agreement_date")
    private Date externalDBAgreementDate;

    @Column(name = "segment", length = 10)
    @Enumerated(EnumType.STRING)
    private Segment segment;

    @Column(name = "involvement")
    private BigDecimal involvement;

    @Column(name = "pkd_code", length = 50)
    private String pkdCode;

    @Column(name = "pkd_name", length = 200)
    private String pkdName;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(final String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(final String regon) {
        this.regon = regon;
    }

    public String getKrs() {
        return krs;
    }

    public void setKrs(final String krs) {
        this.krs = krs;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(final Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    public Boolean getExternalDBAgreement() {
        return externalDBAgreement;
    }

    public void setExternalDBAgreement(final Boolean externalDBAgreement) {
        this.externalDBAgreement = externalDBAgreement;
    }

    public Date getExternalDBAgreementDate() {
        return externalDBAgreementDate;
    }

    public void setExternalDBAgreementDate(final Date externalDBAgreementDate) {
        this.externalDBAgreementDate = externalDBAgreementDate;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(final Segment segment) {
        this.segment = segment;
    }

    public BigDecimal getInvolvement() {
        return involvement;
    }

    public void setInvolvement(final BigDecimal involvement) {
        this.involvement = involvement;
    }

    public List<CompanyPerson> getCompanyPersons() {
        return companyPersons;
    }

    public void setCompanyPersons(final List<CompanyPerson> companyPersons) {
        this.companyPersons = companyPersons;
    }

    public String getPkdCode() {
        return pkdCode;
    }

    public void setPkdCode(final String pkdCode) {
        this.pkdCode = pkdCode;
    }

    public String getPkdName() {
        return pkdName;
    }

    public void setPkdName(final String pkdName) {
        this.pkdName = pkdName;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", extCompanyId=" + extCompanyId +
                ", companyPersons=" + companyPersons +
                ", addresses=" + addresses +
                ", contactDetails=" + contactDetails +
                ", companyName='" + companyName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", nip='" + nip + '\'' +
                ", regon='" + regon + '\'' +
                ", krs='" + krs + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", rating='" + rating + '\'' +
                ", ratingDate=" + ratingDate +
                ", externalDBAgreement=" + externalDBAgreement +
                ", externalDBAgreementDate=" + externalDBAgreementDate +
                ", segment=" + segment +
                ", involvement=" + involvement +
                ", pkdCode='" + pkdCode + '\'' +
                ", pkdName='" + pkdName + '\'' +
                '}';
    }
}
