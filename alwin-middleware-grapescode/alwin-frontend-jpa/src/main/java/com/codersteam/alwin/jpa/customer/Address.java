package com.codersteam.alwin.jpa.customer;

import com.codersteam.alwin.jpa.type.AddressType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Adam Stepnowski
 */
@Audited
@Entity
@Table(name = "address")
public class Address {

    @SequenceGenerator(name = "addressSEQ", sequenceName = "address_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressSEQ")
    private Long id;

    @Column(name = "street", length = 100)
    private String streetName;

    @Column(name = "house_no", length = 100)
    private String houseNumber;

    @Column(name = "flat_no", length = 100)
    private String flatNumber;

    @Column(name = "post_code", length = 100)
    private String postalCode;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "street_prefix", length = 100)
    private String streetPrefix;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "address_type", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(name = "remark", length = 100)
    private String remark;

    @NotAudited
    @Column(name = "imported_from_aida", nullable = false)
    private boolean importedFromAida;

    @NotAudited
    @Column(name = "imported_type", length = 100)
    @Enumerated(EnumType.STRING)
    private AddressType importedType;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(final String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(final String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(final AddressType addressType) {
        this.addressType = addressType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getStreetPrefix() {
        return streetPrefix;
    }

    public void setStreetPrefix(final String prefix) {
        this.streetPrefix = prefix;
    }

    public boolean isImportedFromAida() {
        return importedFromAida;
    }

    public void setImportedFromAida(final boolean importedFromAida) {
        this.importedFromAida = importedFromAida;
    }

    public AddressType getImportedType() {
        return importedType;
    }

    public void setImportedType(final AddressType importedType) {
        this.importedType = importedType;
    }
}
