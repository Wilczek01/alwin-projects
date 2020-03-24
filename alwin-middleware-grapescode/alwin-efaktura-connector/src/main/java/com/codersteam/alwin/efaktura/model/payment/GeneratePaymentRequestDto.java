package com.codersteam.alwin.efaktura.model.payment;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Dariusz Rackowski
 */
public class GeneratePaymentRequestDto {

    /**
     * Suma zadłużenia
     */
    private BigDecimal amount;

    /**
     * Nr umowy, której dotyczy wezwanie
     */
    private String contractNo;

    /**
     * Nr klienta z LEO
     */
    private String customerNo;

    /**
     * Ilość dni na spłatę
     */
    private Integer delayDays;

    /**
     * Nazewnictwo wg wzorca: "Wezwanie_<1 lub 2>_<nr_umowy>_<Data_wezwania>" Przy czym zamiast ukośników w nrze umowy stosujmy myślniki. Np.:
     * Wezwanie_1_012345-18-1_2018-01-01
     */
    private String fileName;

    /**
     * Lista zaległych faktur ustalona jako lista wszystkich dokumentów w ramach danej umowy, których saldo <  0 oraz data płatności < data wystawienia dokumentu
     */
    private List<OutstandingInvoiceDto> outstandingInvoices;

    /**
     * Imie i nazwisko wystawiającego wezwanie
     */
    private String issuingOperator;

    /**
     * Adres - miasto. Jeżeli klient podał adres korespondencyjny, to z adresu korespondencyjnego, a jeśli nie, to z adresu siedziby.
     */
    private String recipientCity;

    /**
     * Adres email klienta. Jeśli jest - invoiceEmail. Jeśli nie, w drugiej kolejności contactEmail, potem officeEmail jak nie ma dwóch poprzednich lub sa niepoprawne [Dopisek PS]
     */
    private String recipientEmail;

    /**
     * Nazwa firmy klienta
     */
    private String recipientName;

    /**
     * NIP klienta
     */
    private String recipientNip;

    /**
     * Tel. kontaktowy: (AIDA Company.contactPhone1)
     */
    private String recipientPhoneNo1;

    /**
     * Tel. kontaktowy: (AIDA Company.contactPhone2)
     */
    private String recipientPhoneNo2;

    /**
     * Adres - kod pocztowy. Jeżeli klient podał adres korespondencyjny, to z adresu korespondencyjnego, a jeśli nie, to z adresu siedziby.
     */
    private String recipientPostalCode;

    /**
     * Adres - ulica, nr domu i lokalu. Jeżeli klient podał adres korespondencyjny, to z adresu korespondencyjnego, a jeśli nie, to z adresu siedziby.
     */
    private String recipientStreet;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(final String customerNo) {
        this.customerNo = customerNo;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(final Integer delayDays) {
        this.delayDays = delayDays;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public List<OutstandingInvoiceDto> getOutstandingInvoices() {
        return outstandingInvoices;
    }

    public void setOutstandingInvoices(final List<OutstandingInvoiceDto> outstandingInvoices) {
        this.outstandingInvoices = outstandingInvoices;
    }

    public String getIssuingOperator() {
        return issuingOperator;
    }

    public void setIssuingOperator(final String issuingOperator) {
        this.issuingOperator = issuingOperator;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(final String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(final String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientNip() {
        return recipientNip;
    }

    public void setRecipientNip(final String recipientNip) {
        this.recipientNip = recipientNip;
    }

    public String getRecipientPhoneNo1() {
        return recipientPhoneNo1;
    }

    public void setRecipientPhoneNo1(final String recipientPhoneNo1) {
        this.recipientPhoneNo1 = recipientPhoneNo1;
    }

    public String getRecipientPhoneNo2() {
        return recipientPhoneNo2;
    }

    public void setRecipientPhoneNo2(final String recipientPhoneNo2) {
        this.recipientPhoneNo2 = recipientPhoneNo2;
    }

    public String getRecipientPostalCode() {
        return recipientPostalCode;
    }

    public void setRecipientPostalCode(final String recipientPostalCode) {
        this.recipientPostalCode = recipientPostalCode;
    }

    public String getRecipientStreet() {
        return recipientStreet;
    }

    public void setRecipientStreet(final String recipientStreet) {
        this.recipientStreet = recipientStreet;
    }
}
