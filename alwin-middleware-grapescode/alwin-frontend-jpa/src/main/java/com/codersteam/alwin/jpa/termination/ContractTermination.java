package com.codersteam.alwin.jpa.termination;

import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.common.termination.ContractType;
import com.codersteam.alwin.jpa.customer.Address;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Wypowiedzenia umów
 *
 * @author Dariusz Rackowski
 */
@Audited
@Entity
@Table(name = "contract_termination")
public class ContractTermination {

    /**
     * Identyfikator wypowiedzenia umowy
     */
    @SequenceGenerator(name = "contractTerminationSEQ", sequenceName = "contract_termination_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractTerminationSEQ")
    private Long id;

    /**
     * Nr umowy
     */
    @Column(name = "contract_number", nullable = false)
    private String contractNumber;

    /**
     * Data wypowiedzenia
     */
    @Column(name = "termination_date", nullable = false)
    private Date terminationDate;

    /**
     * Data aktywacji
     */
    @Column(name = "activation_date")
    private Date activationDate;

    /**
     * Typ wypowiedzenia
     */
    @Column(name = "type", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractTerminationType type;

    /**
     * Status wypowiedzenia umowy
     */
    @Column(name = "state", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractTerminationState state;

    /**
     * Komentarz do wypowiedzenia umowy
     */
    @Column(name = "remark")
    private String remark;

    /**
     * Imię i nazwisko operatora generującego dokument
     */
    @Column(name = "generated_by", length = 200)
    private String generatedBy;

    /**
     * Nr telefonu operatora obecnie obsługującego klienta
     */
    @Column(name = "operator_phone_number", length = 30)
    private String operatorPhoneNumber;

    /**
     * Email operatora obecnie obsługującego klienta
     */
    @Column(name = "operator_email", length = 512)
    private String operatorEmail;

    /**
     * Nr klienta (z LEO)
     */
    @Column(name = "ext_company_id")
    private Long extCompanyId;

    /**
     * Nazwa klienta
     */
    @Column(name = "company_name", length = 200)
    private String companyName;

    /**
     * Id adresu siedziby
     */
    @Column(name = "company_address_id")
    private Long companyAddressId;

    /**
     * Adres siedziby
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_address_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Address companyAddress;

    /**
     * Id adresu korespondencyjnego
     */
    @Column(name = "company_correspondence_address_id")
    private Long companyCorrespondenceAddressId;

    /**
     * Adres korespondencyjny
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_correspondence_address_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Address companyCorrespondenceAddress;

    /**
     * Wysokość opłaty za wznowienie
     */
    @Column(name = "resumption_cost")
    private BigDecimal resumptionCost;

    /**
     * Termin wskazany do zapłaty na wezwaniu
     */
    @Column(name = "due_date_in_demand_for_payment")
    private Date dueDateInDemandForPayment;

    /**
     * Kwota wskazana do spłaty na wezwaniu PLN
     */
    @Column(name = "amount_in_demand_for_payment_pln")
    private BigDecimal amountInDemandForPaymentPLN;

    /**
     * Kwota wskazana do spłaty na wezwaniu EUR
     */
    @Column(name = "amount_in_demand_for_payment_eur")
    private BigDecimal amountInDemandForPaymentEUR;

    /**
     * Suma wpłat dokonanych od wezwania PLN
     */
    @Column(name = "total_payments_since_demand_for_payment_pln")
    private BigDecimal totalPaymentsSinceDemandForPaymentPLN;

    /**
     * Suma wpłat dokonanych od wezwania EUR
     */
    @Column(name = "total_payments_since_demand_for_payment_eur")
    private BigDecimal totalPaymentsSinceDemandForPaymentEUR;

    /**
     * Nr FV inicjującej wezwanie do zapłaty
     */
    @Column(name = "invoice_number_initiating_demand_for_payment")
    private String invoiceNumberInitiatingDemandForPayment;

    /**
     * Kwota FV inicjującej wystawienie wezwania
     */
    @Column(name = "invoice_amount_initiating_demand_for_payment")
    private BigDecimal invoiceAmountInitiatingDemandForPayment;

    /**
     * Saldo FV inicjującej wystawienie wezwania
     */
    @Column(name = "invoice_balance_initiating_demand_for_payment")
    private BigDecimal invoiceBalanceInitiatingDemandForPayment;

    /**
     * Przedmioty umowy
     */
    @NotAudited
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_termination_id", nullable = false)
    @OrderBy("id ASC")
    private List<ContractTerminationSubject> subjects;

    /**
     * Zestawienie przeterminowanych dokumentów
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "contract_termination_invoice",
            joinColumns = @JoinColumn(name = "contract_termination_id"),
            inverseJoinColumns = @JoinColumn(name = "formal_debt_collection_invoice_id"))
    @OrderBy("id ASC")
    private List<FormalDebtCollectionInvoice> formalDebtCollectionInvoices;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "contract_termination_has_attachment",
            joinColumns = @JoinColumn(name = "contract_termination_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_termination_attachment_id")
    )
    @OrderBy("id ASC")
    private List<ContractTerminationAttachment> contractTerminationAttachments;

    /**
     * Komunikat błędu w przypadku niepowodzenia podczas procesowania wypowiedzenia
     * Jeżeli błąd wystąpi na poziomie komunikacji z eFakturą, to umieszczamy tutaj status z eFaktury
     */
    @Column(name = "processing_message")
    private String processingMessage;

    /**
     * Typ finansowania
     */
    @Column(name = "contract_type")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    /**
     * Identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
     */
    @Column(name = "preceding_demand_for_payment_id")
    private Long precedingDemandForPaymentId;

    /**
     * Identyfikator poprzedzającego wypowiedzenia umowy (jeśli istnieje)
     */
    @Column(name = "preceding_contract_termination_id")
    private Long precedingContractTerminationId;

    /**
     * Data zmiany stanu
     */
    @Column(name = "state_change_date")
    private Date stateChangeDate;

    /**
     * Przyczyna zmiany stanu
     */
    @Column(name = "state_change_reason")
    private String stateChangeReason;

    /**
     * Identyfikator operatora zmieniającego stan
     */
    @Column(name = "state_change_operator_id")
    private Long stateChangeOperatorId;

    @NotAudited
    @Formula("concat(ext_company_id, termination_date, type)")
    private String distinct;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(final String contractNumber) {
        this.contractNumber = contractNumber;
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

    public Long getCompanyAddressId() {
        return companyAddressId;
    }

    public void setCompanyAddressId(final Long companyAddressId) {
        this.companyAddressId = companyAddressId;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(final Address companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Long getCompanyCorrespondenceAddressId() {
        return companyCorrespondenceAddressId;
    }

    public void setCompanyCorrespondenceAddressId(final Long companyCorrespondenceAddressId) {
        this.companyCorrespondenceAddressId = companyCorrespondenceAddressId;
    }

    public Address getCompanyCorrespondenceAddress() {
        return companyCorrespondenceAddress;
    }

    public void setCompanyCorrespondenceAddress(final Address companyCorrespondenceAddress) {
        this.companyCorrespondenceAddress = companyCorrespondenceAddress;
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

    public List<ContractTerminationSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(final List<ContractTerminationSubject> subjects) {
        this.subjects = subjects;
    }

    public List<FormalDebtCollectionInvoice> getFormalDebtCollectionInvoices() {
        return formalDebtCollectionInvoices;
    }

    public void setFormalDebtCollectionInvoices(final List<FormalDebtCollectionInvoice> formalDebtCollectionInvoices) {
        this.formalDebtCollectionInvoices = formalDebtCollectionInvoices;
    }

    public List<ContractTerminationAttachment> getContractTerminationAttachments() {
        return contractTerminationAttachments;
    }

    public void setContractTerminationAttachments(final List<ContractTerminationAttachment> contractTerminationAttachments) {
        this.contractTerminationAttachments = contractTerminationAttachments;
    }

    public String getProcessingMessage() {
        return processingMessage;
    }

    public void setProcessingMessage(final String processingMessage) {
        this.processingMessage = processingMessage;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(final ContractType contractType) {
        this.contractType = contractType;
    }

    public Long getPrecedingDemandForPaymentId() {
        return precedingDemandForPaymentId;
    }

    public void setPrecedingDemandForPaymentId(final Long precedingDemandForPaymentId) {
        this.precedingDemandForPaymentId = precedingDemandForPaymentId;
    }

    public Long getPrecedingContractTerminationId() {
        return precedingContractTerminationId;
    }

    public void setPrecedingContractTerminationId(final Long precedingContractTerminationId) {
        this.precedingContractTerminationId = precedingContractTerminationId;
    }

    public Date getStateChangeDate() {
        return stateChangeDate;
    }

    public void setStateChangeDate(final Date stateChangeDate) {
        this.stateChangeDate = stateChangeDate;
    }

    public String getStateChangeReason() {
        return stateChangeReason;
    }

    public void setStateChangeReason(final String stateChangeReason) {
        this.stateChangeReason = stateChangeReason;
    }

    public Long getStateChangeOperatorId() {
        return stateChangeOperatorId;
    }

    public void setStateChangeOperatorId(final Long stateChangeOperatorId) {
        this.stateChangeOperatorId = stateChangeOperatorId;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(final String distinct) {
        this.distinct = distinct;
    }

    @Override
    public String toString() {
        return "ContractTermination{" +
                "id=" + id +
                '}';
    }
}
