package com.codersteam.alwin.jpa;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.jpa.type.ActivityState;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "operators_queue")
public class OperatorsQueue implements Serializable {

    @Id
    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "activity_date")
    @Temporal(TemporalType.DATE)
    private Date activityDate;

    @Column(name = "activity_type_id")
    private Long activityTypeId;

    @Column(name = "charge")
    private BigDecimal charge;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "issue_id")
    private Long issueId;

    @Column(name = "operator_id")
    private Long operatorId;

    @Column(name = "planned_date")
    @Temporal(TemporalType.DATE)
    private Date plannedDate;

    @Column(name = "activity_state")
    @Enumerated(EnumType.STRING)
    private ActivityState activityState;

    @Column(name = "issue_type")
    @Enumerated(EnumType.STRING)
    private IssueTypeName issueType;

    public OperatorsQueue() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public Long getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Long activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public ActivityState getActivityState() {
        return activityState;
    }

    public void setActivityState(ActivityState activityState) {
        this.activityState = activityState;
    }

    public IssueTypeName getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueTypeName issueType) {
        this.issueType = issueType;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("OperatorsQueue{").append("activityId=").append(activityId).append(", activityDate=").append(activityDate).append(", activityTypeId=").append(activityTypeId).append(", charge=").append(charge).append(", creationDate=").append(creationDate).append(", invoiceId=").append(invoiceId).append(", issueId=").append(issueId).append(", operatorId=").append(operatorId).append(", plannedDate=").append(plannedDate).append(", activityState='").append(activityState).append('\'').append(", issueType='").append(issueType).append('\'').append('}').toString();
    }
}
