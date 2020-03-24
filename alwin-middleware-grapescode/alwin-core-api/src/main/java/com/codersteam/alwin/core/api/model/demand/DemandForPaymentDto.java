package com.codersteam.alwin.core.api.model.demand;

import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Wezwanie do zapłaty
 *
 * @author Michal Horowic
 */
public class DemandForPaymentDto {

    private Long id;
    private Date issueDate;
    private Date dueDate;
    private String initialInvoiceNo;
    private Long extCompanyId;
    private String companyName;
    private String contractNumber;
    private DemandForPaymentTypeConfigurationDto type;
    private String chargeInvoiceNo;
    private DemandForPaymentState state;
    private String attachmentRef;
    private Set<FormalDebtCollectionInvoiceDto> invoices;

    /**
     * Słownikowa przyczyna anulowania wezwania
     */
    private ReasonType reasonType;

    /**
     * Opis słownikowej przyczyna anulowania wezwania
     */
    private String reasonTypeLabel;

    /**
     * Flaga określająca, czy wezwanie utworzono ręcznie
     */
    private boolean createdManually;

    /**
     * Lista komunikatów dla operatora, zawierająca informację dotyczącą wpływu wystawienia wezwania ręcznego na obecny proces windykacji formalnej (jeśli trwa)
     * Pusta lista oznacza brak modyfikacji procesu
     */
    private List<String> manualCreationMessage;

    /**
     * Identyfikator poprzedzającego wezwania do zapłaty (jeśli istnieje)
     */
    private Long precedingDemandForPaymentId;

    /**
     * Flaga określająca, czy wezwanie zastąpione w procesie wezwaniem utworzonym ręcznie (wyłączone z procesu)
     */
    private boolean aborted;

    /**
     * Data zmiany stanu
     */
    private Date stateChangeDate;

    /**
     * Przyczyna zmiany stanu
     */
    private String stateChangeReason;

    /**
     * Operator zmieniający stan
     */
    private OperatorDto stateChangeOperator;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(final String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public DemandForPaymentTypeConfigurationDto getType() {
        return type;
    }

    public void setType(final DemandForPaymentTypeConfigurationDto type) {
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

    public Set<FormalDebtCollectionInvoiceDto> getInvoices() {
        return invoices;
    }

    public void setInvoices(final Set<FormalDebtCollectionInvoiceDto> invoices) {
        this.invoices = invoices;
    }

    public boolean isCreatedManually() {
        return createdManually;
    }

    public void setCreatedManually(final boolean createdManually) {
        this.createdManually = createdManually;
    }

    public List<String> getManualCreationMessage() {
        return manualCreationMessage;
    }

    public void setManualCreationMessage(final List<String> manualCreationMessage) {
        this.manualCreationMessage = manualCreationMessage;
    }

    public ReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(final ReasonType reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasonTypeLabel() {
        return reasonTypeLabel;
    }

    public void setReasonTypeLabel(final String reasonTypeLabel) {
        this.reasonTypeLabel = reasonTypeLabel;
    }

    public Long getPrecedingDemandForPaymentId() {
        return precedingDemandForPaymentId;
    }

    public void setPrecedingDemandForPaymentId(final Long precedingDemandForPaymentId) {
        this.precedingDemandForPaymentId = precedingDemandForPaymentId;
    }

    public boolean isAborted() {
        return aborted;
    }

    public void setAborted(final boolean aborted) {
        this.aborted = aborted;
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

    public OperatorDto getStateChangeOperator() {
        return stateChangeOperator;
    }

    public void setStateChangeOperator(final OperatorDto stateChangeOperator) {
        this.stateChangeOperator = stateChangeOperator;
    }

    @Override
    public String toString() {
        return "DemandForPaymentDto{" +
                "id=" + id +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", initialInvoiceNo='" + initialInvoiceNo + '\'' +
                ", extCompanyId=" + extCompanyId +
                ", companyName='" + companyName + '\'' +
                ", contractNumber='" + contractNumber + '\'' +
                ", type=" + type +
                ", chargeInvoiceNo='" + chargeInvoiceNo + '\'' +
                ", state=" + state +
                ", attachmentRef='" + attachmentRef + '\'' +
                ", invoices=" + invoices +
                ", reasonType=" + reasonType +
                ", reasonTypeLabel='" + reasonTypeLabel + '\'' +
                ", createdManually=" + createdManually +
                ", manualCreationMessage=" + manualCreationMessage +
                ", precedingDemandForPaymentId=" + precedingDemandForPaymentId +
                ", aborted=" + aborted +
                ", stateChangeDate=" + stateChangeDate +
                ", stateChangeReason='" + stateChangeReason + '\'' +
                ", stateChangeOperator=" + stateChangeOperator +
                '}';
    }
}
