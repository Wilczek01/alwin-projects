package com.codersteam.alwin.efaktura.model.termination.data;

/**
 * @author Dariusz Rackowski
 */
public class ContractCustomerDto {

    /**
     * Miasto siedziby klienta
     */
    private String city;

    /**
     * Adres mailowy klienta
     */
    private String email;

    /**
     * Miasto adresu korespondencyjny klienta
     */
    private String mailCity;

    /**
     * Kod pocztowy adresu korespondencyjny firmy klienta
     */
    private String mailPostalCode;

    /**
     * Ulica, nr domu i lokalu adresu korespondencyjny Klienta
     */
    private String mailStreet;

    /**
     * Nazwa klienta
     */
    private String name;

    /**
     * NIP klienta
     */
    private String nip;

    /**
     * nr klienta
     */
    private String no;

    /**
     * Tel. kontaktowy
     */
    private String phoneNo1;

    /**
     * Tel. kontaktowy
     */
    private String phoneNo2;

    /**
     * kod pocztowy adresu siedziby firmy klienta
     */
    private String postalCode;

    /**
     * Ulica, nr domu i lokalu siedziby klienta
     */
    private String street;

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getMailCity() {
        return mailCity;
    }

    public void setMailCity(final String mailCity) {
        this.mailCity = mailCity;
    }

    public String getMailPostalCode() {
        return mailPostalCode;
    }

    public void setMailPostalCode(final String mailPostalCode) {
        this.mailPostalCode = mailPostalCode;
    }

    public String getMailStreet() {
        return mailStreet;
    }

    public void setMailStreet(final String mailStreet) {
        this.mailStreet = mailStreet;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(final String nip) {
        this.nip = nip;
    }

    public String getNo() {
        return no;
    }

    public void setNo(final String no) {
        this.no = no;
    }

    public String getPhoneNo1() {
        return phoneNo1;
    }

    public void setPhoneNo1(final String phoneNo1) {
        this.phoneNo1 = phoneNo1;
    }

    public String getPhoneNo2() {
        return phoneNo2;
    }

    public void setPhoneNo2(final String phoneNo2) {
        this.phoneNo2 = phoneNo2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }
}
