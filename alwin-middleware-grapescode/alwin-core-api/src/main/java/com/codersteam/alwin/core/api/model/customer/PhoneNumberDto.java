package com.codersteam.alwin.core.api.model.customer;

/**
 * @author Adam Stepnowski
 */
public class PhoneNumberDto {

    private final String contactState;
    private String label;
    private String phoneNumber;

    public PhoneNumberDto(final String label, final String phoneNumber, final String contactState) {
        this.label = label;
        this.phoneNumber = phoneNumber;
        this.contactState = contactState;
    }

    public String getContactState() {
        return contactState;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PhoneNumberDto{" +
                "label='" + label + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
