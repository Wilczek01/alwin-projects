package com.codersteam.alwin.jpa.notice;

/**
 * @author Dariusz Rackowski
 */
public class DemandForPaymentWithCompanyName extends DemandForPayment {

    private String companyName;

    public DemandForPaymentWithCompanyName() {
    }

    public DemandForPaymentWithCompanyName(final DemandForPayment demandForPayment, final String companyName) {
        this.setId(demandForPayment.getId());
        this.setIssueDate(demandForPayment.getIssueDate());
        this.setDueDate(demandForPayment.getDueDate());
        this.setInitialInvoiceNo(demandForPayment.getInitialInvoiceNo());
        this.setExtCompanyId(demandForPayment.getExtCompanyId());
        this.setContractNumber(demandForPayment.getContractNumber());
        this.setType(demandForPayment.getType());
        this.setChargeInvoiceNo(demandForPayment.getChargeInvoiceNo());
        this.setState(demandForPayment.getState());
        this.setAttachmentRef(demandForPayment.getAttachmentRef());
        this.setProcessingMessage(demandForPayment.getProcessingMessage());
        this.setInvoices(demandForPayment.getInvoices());
        this.setPrecedingDemandForPaymentId(demandForPayment.getPrecedingDemandForPaymentId());
        this.setCreatedManually(demandForPayment.isCreatedManually());
        this.setReasonType(demandForPayment.getReasonType());
        this.setPrecedingDemandForPaymentId(demandForPayment.getPrecedingDemandForPaymentId());
        this.setAborted(demandForPayment.isAborted());
        this.setStateChangeDate(demandForPayment.getStateChangeDate());
        this.setStateChangeOperator(demandForPayment.getStateChangeOperator());
        this.setStateChangeReason(demandForPayment.getStateChangeReason());
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

}
