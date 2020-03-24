package com.codersteam.alwin.core.api.model.termination;

import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Wypowiedzenie umowy
 *
 * @author Dariusz Rackowski
 */
public class TerminationContractDto {

    /**
     * Identyfikator wypowiedzenia umowy
     */
    private Long contractTerminationId;

    /**
     * Data wypowiedzenia
     */
    private Date terminationDate;

    /**
     * Data aktywacji
     */
    private Date activationDate;

    /**
     * Nr umowy
     */
    private String contractNumber;

    /**
     * Typ wypowiedzenia
     */
    private ContractTerminationType type;

    /**
     * Status wypowiedzenia umowy
     */
    private ContractTerminationState state;

    /**
     * Komentarz do wypowiedzenia umowy
     */
    private String remark;

    /**
     * Imię i nazwisko operatora generującego dokument
     */
    private String generatedBy;

    /**
     * Nr telefonu operatora obecnie obsługującego klienta
     */
    private String operatorPhoneNumber;

    /**
     * Email operatora obecnie obsługującego klienta
     */
    private String operatorEmail;

    /**
     * Wysokość opłaty za wznowienie
     */
    private BigDecimal resumptionCost;

    /**
     * Termin wskazany do zapłaty na wezwaniu
     */
    private Date dueDateInDemandForPayment;

    /**
     * Kwota wskazana do spłaty na wezwaniu PLN
     */
    private BigDecimal amountInDemandForPaymentPLN;

    /**
     * Kwota wskazana do spłaty na wezwaniu EUR
     */
    private BigDecimal amountInDemandForPaymentEUR;

    /**
     * Suma wpłat dokonanych od wezwania PLN
     */
    private BigDecimal totalPaymentsSinceDemandForPaymentPLN;

    /**
     * Suma wpłat dokonanych od wezwania EUR
     */
    private BigDecimal totalPaymentsSinceDemandForPaymentEUR;

    /**
     * Nr FV inicjującej wezwanie do zapłaty
     */
    private String invoiceNumberInitiatingDemandForPayment;

    /**
     * Kwota FV inicjującej wystawienie wezwania
     */
    private BigDecimal invoiceAmountInitiatingDemandForPayment;

    /**
     * Saldo FV inicjującej wystawienie wezwania
     */
    private BigDecimal invoiceBalanceInitiatingDemandForPayment;

    /**
     * Przedmioty umowy
     */
    private List<TerminationContractSubjectDto> subjects;

    /**
     * Zestawienie przeterminowanych dokumentów
     */
    private List<FormalDebtCollectionInvoiceDto> formalDebtCollectionInvoices;

    public Long getContractTerminationId() {
        return contractTerminationId;
    }

    public void setContractTerminationId(final Long contractTerminationId) {
        this.contractTerminationId = contractTerminationId;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(final Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(final Date activationDate) {
        this.activationDate = activationDate;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(final String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public ContractTerminationType getType() {
        return type;
    }

    public void setType(final ContractTerminationType type) {
        this.type = type;
    }

    public ContractTerminationState getState() {
        return state;
    }

    public void setState(final ContractTerminationState state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(final String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getOperatorPhoneNumber() {
        return operatorPhoneNumber;
    }

    public void setOperatorPhoneNumber(final String operatorPhoneNumber) {
        this.operatorPhoneNumber = operatorPhoneNumber;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(final String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public BigDecimal getResumptionCost() {
        return resumptionCost;
    }

    public void setResumptionCost(final BigDecimal resumptionCost) {
        this.resumptionCost = resumptionCost;
    }

    public Date getDueDateInDemandForPayment() {
        return dueDateInDemandForPayment;
    }

    public void setDueDateInDemandForPayment(final Date dueDateInDemandForPayment) {
        this.dueDateInDemandForPayment = dueDateInDemandForPayment;
    }

    public BigDecimal getAmountInDemandForPaymentPLN() {
        return amountInDemandForPaymentPLN;
    }

    public void setAmountInDemandForPaymentPLN(final BigDecimal amountInDemandForPaymentPLN) {
        this.amountInDemandForPaymentPLN = amountInDemandForPaymentPLN;
    }

    public BigDecimal getAmountInDemandForPaymentEUR() {
        return amountInDemandForPaymentEUR;
    }

    public void setAmountInDemandForPaymentEUR(final BigDecimal amountInDemandForPaymentEUR) {
        this.amountInDemandForPaymentEUR = amountInDemandForPaymentEUR;
    }

    public BigDecimal getTotalPaymentsSinceDemandForPaymentPLN() {
        return totalPaymentsSinceDemandForPaymentPLN;
    }

    public void setTotalPaymentsSinceDemandForPaymentPLN(final BigDecimal totalPaymentsSinceDemandForPaymentPLN) {
        this.totalPaymentsSinceDemandForPaymentPLN = totalPaymentsSinceDemandForPaymentPLN;
    }

    public BigDecimal getTotalPaymentsSinceDemandForPaymentEUR() {
        return totalPaymentsSinceDemandForPaymentEUR;
    }

    public void setTotalPaymentsSinceDemandForPaymentEUR(final BigDecimal totalPaymentsSinceDemandForPaymentEUR) {
        this.totalPaymentsSinceDemandForPaymentEUR = totalPaymentsSinceDemandForPaymentEUR;
    }

    public String getInvoiceNumberInitiatingDemandForPayment() {
        return invoiceNumberInitiatingDemandForPayment;
    }

    public void setInvoiceNumberInitiatingDemandForPayment(final String invoiceNumberInitiatingDemandForPayment) {
        this.invoiceNumberInitiatingDemandForPayment = invoiceNumberInitiatingDemandForPayment;
    }

    public BigDecimal getInvoiceAmountInitiatingDemandForPayment() {
        return invoiceAmountInitiatingDemandForPayment;
    }

    public void setInvoiceAmountInitiatingDemandForPayment(final BigDecimal invoiceAmountInitiatingDemandForPayment) {
        this.invoiceAmountInitiatingDemandForPayment = invoiceAmountInitiatingDemandForPayment;
    }

    public BigDecimal getInvoiceBalanceInitiatingDemandForPayment() {
        return invoiceBalanceInitiatingDemandForPayment;
    }

    public void setInvoiceBalanceInitiatingDemandForPayment(final BigDecimal invoiceBalanceInitiatingDemandForPayment) {
        this.invoiceBalanceInitiatingDemandForPayment = invoiceBalanceInitiatingDemandForPayment;
    }

    public List<TerminationContractSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(final List<TerminationContractSubjectDto> subjects) {
        this.subjects = subjects;
    }

    public List<FormalDebtCollectionInvoiceDto> getFormalDebtCollectionInvoices() {
        return formalDebtCollectionInvoices;
    }

    public void setFormalDebtCollectionInvoices(final List<FormalDebtCollectionInvoiceDto> formalDebtCollectionInvoices) {
        this.formalDebtCollectionInvoices = formalDebtCollectionInvoices;
    }
}
