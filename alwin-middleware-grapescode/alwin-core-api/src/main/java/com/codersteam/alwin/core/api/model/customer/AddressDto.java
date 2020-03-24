package com.codersteam.alwin.core.api.model.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Piotr Naroznik
 */
public class AddressDto {

    private Long id;
    private String city;
    private String remark;
    private String country;
    private String streetName;
    private String flatNumber;
    private String postalCode;
    private String houseNumber;
    private String streetPrefix;
    private boolean importedFromAida;
    private AddressTypeDto addressType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public AddressTypeDto getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeDto addressType) {
        this.addressType = addressType;
    }

    public String getStreetPrefix() {
        return streetPrefix;
    }

    public void setStreetPrefix(String streetPrefix) {
        this.streetPrefix = streetPrefix;
    }

    public boolean isImportedFromAida() {
        return importedFromAida;
    }

    public void setImportedFromAida(boolean importedFromAida) {
        this.importedFromAida = importedFromAida;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("city", city)
                .append("remark", remark)
                .append("country", country)
                .append("streetName", streetName)
                .append("flatNumber", flatNumber)
                .append("postalCode", postalCode)
                .append("houseNumber", houseNumber)
                .append("streetPrefix", streetPrefix)
                .append("addressType", addressType)
                .append("importedFromAida", importedFromAida)
                .toString();
    }
}

