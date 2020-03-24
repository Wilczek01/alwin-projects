package com.codersteam.alwin.core.api.model.customer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michal Horowic
 */
public class CompanyDto {
    private Long id;
    private Long extCompanyId;
    private String companyName;
    private String shortName;
    private String nip;
    private String regon;
    private String krs;
    private String recipientName;
    private String rating;
    private Date ratingDate;
    private Boolean externalDBAgreement;
    private Date externalDBAgreementDate;
    private Set<PersonDto> persons = new HashSet<>();
    private String pkdCode;
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

    public Set<PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(final Set<PersonDto> persons) {
        this.persons = persons;
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
        return "CompanyDto{" +
                "id=" + id +
                ", extCompanyId=" + extCompanyId +
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
                ", persons=" + persons +
                ", pkdCode='" + pkdCode + '\'' +
                ", pkdName='" + pkdName + '\'' +
                '}';
    }
}
