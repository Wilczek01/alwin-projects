package com.codersteam.alwin.jpa.notice;

import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * Wezwanie do zapłaty
 *
 * @author Tomasz Sliwinski
 */
@Entity
@Table(name = "demand_for_payment")
public class DemandForPayment {

    /**
     * Identyfikator
     */
    @SequenceGenerator(name = "demandForPaymentSEQ", sequenceName = "demand_for_payment_id_seq", allocationSize = 1, initialValue = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "demandForPaymentSEQ")
    private Long id;

    /**
     * Data wystawienia
     */
    @Column(name = "issue_date")
    private Date issueDate;

    /**
     * Termin zapłaty
     */
    @Column(name = "due_date")
    private Date dueDate;

    /**
     * Numer faktury inicjującej wystawienie
     */
    @Column(name = "initial_invoice_no", nullable = false)
    private String initialInvoiceNo;

    /**
     * Identyfikator klienta (AIDA)
     */
    @Column(name = "ext_company_id", nullable = false)
    private Long extCompanyId;

    /**
     * Numer umowy
     */
    @Column(name = "contract_number", nullable = false)
    private String contractNumber;

    /**
     * Konfiguracja typu wezwania
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "demand_for_payment_type_configuration_id", nullable = false)
    private DemandForPaymentTypeConfiguration type;

    /**
     * Nr FV za opłatę
     */
    @Column(name = "charge_invoice_no")
    private String chargeInvoiceNo;

    /**
     * Status
     */
    @Column(name = "state", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private DemandForPaymentState state;

    /**
     * Odnośnik do pliku wygenerowanego wezwania
     */
    @Column(name = "attachment_ref")
    private String attachmentRef;

    /**
     * Komunikat błędu w przypadku niepowodzenia podczas procesowania wezwania
     * Jeżeli błąd wystąpi na poziomie komunikacji z eFakturą, to umieszczamy tutaj status z eFaktury
     */
    @Column(name = "processing_message")
    private String processingMessage;

    /**
     * Kolekcja zaległych dokumentów umowy wskazanych w wezwaniu (w tym inicjujący)
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "demand_for_payment_invoice",
            joinColumns = @JoinColumn(name = "demand_for_payment_id"),
            inverseJoinColumns = @JoinColumn(name = "formal_debt_collection_invoice_id"))
    private Set<FormalDebtCollectionInvoice> invoices;

    /**
     * Identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
     */
    @Column(name = "preceding_demand_for_payment_id")
    private Long precedingDemandForPaymentId;

    /**
     * Flaga określająca, czy wezwanie utworzono ręcznie
     */
    @Column(name = "created_manually", nullable = false)
    private boolean createdManually;

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
     * Operator zmieniający stan
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_change_operator_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Operator stateChangeOperator;

    /**
     * Identyfikator operatora zmieniającego stan
     */
    @Column(name = "state_change_operator_id")
    private Long stateChangeOperatorId;

    /**
     * Słownikowa przyczyna anulowania wezwania
     */
    @Column(name = "reason_type")
    @Enumerated(EnumType.STRING)
    private ReasonType reasonType;

    /**
     * Flaga określająca, czy wezwanie zastąpione w procesie wezwaniem utworzonym ręcznie (wyłączone z procesu)
     */
    @Column(name = "aborted", nullable = false)
    private boolean aborted;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(final Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getInitialInvoiceNo() {
        return initialInvoiceNo;
    }

    public void setInitialInvoiceNo(final String initialInvoiceNo) {
        this.initialInvoiceNo = initialInvoiceNo;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public DemandForPaymentTypeConfiguration getType() {
        return type;
    }

    public void setType(final DemandForPaymentTypeConfiguration type) {
        this.type = type;
    }

    public String getChargeInvoiceNo() {
        return chargeInvoiceNo;
    }

    public void setChargeInvoiceNo(final String chargeInvoiceNo) {
        this.chargeInvoiceNo = chargeInvoiceNo;
    }

    public DemandForPaymentState getState() {
        return state;
    }

    public void setState(final DemandForPaymentState state) {
        this.state = state;
    }

    public String getAttachmentRef() {
        return attachmentRef;
    }

    public void setAttachmentRef(final String attachmentRef) {
        this.attachmentRef = attachmentRef;
    }

    public Set<FormalDebtCollectionInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(final Set<FormalDebtCollectionInvoice> invoices) {
        this.invoices = invoices;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(final String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getProcessingMessage() {
        return processingMessage;
    }

    public void setProcessingMessage(final String processingMessage) {
        this.processingMessage = processingMessage;
    }

    public Long getPrecedingDemandForPaymentId() {
        return precedingDemandForPaymentId;
    }

    public void setPrecedingDemandForPaymentId(final Long precedingDemandForPaymentId) {
        this.precedingDemandForPaymentId = precedingDemandForPaymentId;
    }

    public boolean isCreatedManually() {
        return createdManually;
    }

    public void setCreatedManually(final boolean createdManually) {
        this.createdManually = createdManually;
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

    public Operator getStateChangeOperator() {
        return stateChangeOperator;
    }

    public void setStateChangeOperator(final Operator stateChangeOperator) {
        this.stateChangeOperator = stateChangeOperator;
    }

    public ReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(ReasonType reasonType) {
        this.reasonType = reasonType;
    }

    public boolean isAborted() {
        return aborted;
    }

    public void setAborted(final boolean aborted) {
        this.aborted = aborted;
    }

    @Override
    public String toString() {
        return "DemandForPayment{" +
                "id=" + id +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", initialInvoiceNo='" + initialInvoiceNo + '\'' +
                ", extCompanyId=" + extCompanyId +
                ", contractNumber='" + contractNumber + '\'' +
                ", type=" + type +
                ", chargeInvoiceNo='" + chargeInvoiceNo + '\'' +
                ", state=" + state +
                ", attachmentRef='" + attachmentRef + '\'' +
                ", processingMessage='" + processingMessage + '\'' +
                ", invoices=" + invoices +
                ", precedingDemandForPaymentId=" + precedingDemandForPaymentId +
                ", createdManually=" + createdManually +
                ", stateChangeDate=" + stateChangeDate +
                ", stateChangeReason='" + stateChangeReason + '\'' +
                ", stateChangeOperator=" + stateChangeOperator +
                ", reasonType=" + reasonType +
                ", aborted=" + aborted +
                '}';
    }
}
