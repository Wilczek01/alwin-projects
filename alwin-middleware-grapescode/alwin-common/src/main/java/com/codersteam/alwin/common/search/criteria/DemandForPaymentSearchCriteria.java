package com.codersteam.alwin.common.search.criteria;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;

import java.util.Date;
import java.util.List;

/**
 * Kryteria wyszukiwania wezwań do zapłaty
 *
 * @author Michal Horowic
 */
public class DemandForPaymentSearchCriteria extends SearchCriteria {

    private Date startIssueDate;
    private Date endIssueDate;
    private Date startDueDate;
    private Date endDueDate;
    private String initialInvoiceNo;
    private Long extCompanyId;
    private String companyName;
    private Segment segment;
    private String chargeInvoiceNo;
    private String contractNo;
    private List<DemandForPaymentState> state;
    private List<DemandForPaymentTypeKey> types;
    private Boolean createdManually;
    private Boolean aborted;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public List<DemandForPaymentState> getState() {
        return state;
    }

    public void setState(final List<DemandForPaymentState> state) {
        this.state = state;
    }

    public List<DemandForPaymentTypeKey> getTypes() {
        return types;
    }

    public void setTypes(final List<DemandForPaymentTypeKey> types) {
        this.types = types;
    }

    public Date getStartIssueDate() {
        return startIssueDate;
    }

    public void setStartIssueDate(final Date startIssueDate) {
        this.startIssueDate = startIssueDate;
    }

    public Date getEndIssueDate() {
        return endIssueDate;
    }

    public void setEndIssueDate(final Date endIssueDate) {
        this.endIssueDate = endIssueDate;
    }

    public Date getStartDueDate() {
        return startDueDate;
    }

    public void setStartDueDate(final Date startDueDate) {
        this.startDueDate = startDueDate;
    }

    public Date getEndDueDate() {
        return endDueDate;
    }

    public void setEndDueDate(final Date endDueDate) {
        this.endDueDate = endDueDate;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(final Segment segment) {
        this.segment = segment;
    }

    public String getChargeInvoiceNo() {
        return chargeInvoiceNo;
    }

    public void setChargeInvoiceNo(final String chargeInvoiceNo) {
        this.chargeInvoiceNo = chargeInvoiceNo;
    }

    public void setCreatedManually(final boolean createdManually) {
        this.createdManually = createdManually;
    }

    public Boolean getCreatedManually() {
        return createdManually;
    }

    public void setCreatedManually(final Boolean createdManually) {
        this.createdManually = createdManually;
    }

    public Boolean getAborted() {
        return aborted;
    }

    public void setAborted(final Boolean aborted) {
        this.aborted = aborted;
    }
}
