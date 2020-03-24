package com.codersteam.alwin.common.search.criteria;

import com.codersteam.alwin.common.issue.Segment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author Michal Horowic
 */
public class IssuesSearchCriteria extends SearchCriteria {

    private List<String> states;
    private List<Long> operatorIds;
    private List<Long> extCompanyIds;
    private List<Long> tagIds;
    private Date startStartDate;
    private Date endStartDate;
    private Date startContactDate;
    private Date endContactDate;
    private BigDecimal totalCurrentBalancePLNMin;
    private BigDecimal totalCurrentBalancePLNMax;
    private Boolean unassigned;
    private Integer priority;
    private Long issueTypeId;
    private Segment segment;
    private String companyName;
    private String nip;
    private String regon;
    private String invoiceContractNo;
    private String personPesel;
    private String personName;
    private String contactPhone;
    private String contactEmail;
    private String contactName;
    private List<Long> assignedTo;

    public List<String> getStates() {
        return states;
    }

    public void setStates(final List<String> states) {
        this.states = states;
    }

    public List<Long> getOperatorIds() {
        return operatorIds;
    }

    public void setOperatorIds(final List<Long> operatorIds) {
        this.operatorIds = operatorIds;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(final List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Boolean getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(final Boolean unassigned) {
        this.unassigned = unassigned;
    }

    public Date getStartStartDate() {
        return startStartDate;
    }

    public void setStartStartDate(final Date startStartDate) {
        this.startStartDate = startStartDate;
    }

    public Date getEndStartDate() {
        return endStartDate;
    }

    public void setEndStartDate(final Date endStartDate) {
        this.endStartDate = endStartDate;
    }

    public Date getStartContactDate() {
        return startContactDate;
    }

    public void setStartContactDate(final Date startContactDate) {
        this.startContactDate = startContactDate;
    }

    public Date getEndContactDate() {
        return endContactDate;
    }

    public void setEndContactDate(final Date endContactDate) {
        this.endContactDate = endContactDate;
    }

    public BigDecimal getTotalCurrentBalancePLNMin() {
        return totalCurrentBalancePLNMin;
    }

    public void setTotalCurrentBalancePLNMin(final BigDecimal totalCurrentBalancePLNMin) {
        this.totalCurrentBalancePLNMin = totalCurrentBalancePLNMin;
    }

    public BigDecimal getTotalCurrentBalancePLNMax() {
        return totalCurrentBalancePLNMax;
    }

    public void setTotalCurrentBalancePLNMax(final BigDecimal totalCurrentBalancePLNMax) {
        this.totalCurrentBalancePLNMax = totalCurrentBalancePLNMax;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(final Integer priority) {
        this.priority = priority;
    }

    public List<Long> getExtCompanyIds() {
        return extCompanyIds;
    }

    public void setExtCompanyIds(final List<Long> extCompanyIds) {
        this.extCompanyIds = extCompanyIds;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(final Long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(final Segment segment) {
        this.segment = segment;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(final String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(final String regon) {
        this.regon = regon;
    }

    public String getInvoiceContractNo() {
        return invoiceContractNo;
    }

    public void setInvoiceContractNo(final String invoiceContractNo) {
        this.invoiceContractNo = invoiceContractNo;
    }

    public String getPersonPesel() {
        return personPesel;
    }

    public void setPersonPesel(final String personPesel) {
        this.personPesel = personPesel;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(final String personName) {
        this.personName = personName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(final String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(final String contactName) {
        this.contactName = contactName;
    }

    public List<Long> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(final List<Long> assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IssuesSearchCriteria that = (IssuesSearchCriteria) o;
        return Objects.equals(states, that.states) &&
                Objects.equals(operatorIds, that.operatorIds) &&
                Objects.equals(extCompanyIds, that.extCompanyIds) &&
                Objects.equals(tagIds, that.tagIds) &&
                Objects.equals(startStartDate, that.startStartDate) &&
                Objects.equals(endStartDate, that.endStartDate) &&
                Objects.equals(startContactDate, that.startContactDate) &&
                Objects.equals(endContactDate, that.endContactDate) &&
                Objects.equals(totalCurrentBalancePLNMin, that.totalCurrentBalancePLNMin) &&
                Objects.equals(totalCurrentBalancePLNMax, that.totalCurrentBalancePLNMax) &&
                Objects.equals(unassigned, that.unassigned) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(issueTypeId, that.issueTypeId) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(nip, that.nip) &&
                Objects.equals(regon, that.regon) &&
                Objects.equals(invoiceContractNo, that.invoiceContractNo) &&
                Objects.equals(personPesel, that.personPesel) &&
                Objects.equals(personName, that.personName) &&
                Objects.equals(contactPhone, that.contactPhone) &&
                Objects.equals(contactEmail, that.contactEmail) &&
                Objects.equals(contactName, that.contactName) &&
                Objects.equals(assignedTo, that.assignedTo) &&
                segment == that.segment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(states, operatorIds, extCompanyIds, tagIds, startStartDate, endStartDate, startContactDate, endContactDate,
                totalCurrentBalancePLNMin, totalCurrentBalancePLNMax, unassigned, priority, issueTypeId, segment, companyName, nip, regon,
                invoiceContractNo, personPesel, personName, contactPhone, contactEmail, contactName, assignedTo);
    }
}

