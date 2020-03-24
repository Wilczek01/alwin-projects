package com.codersteam.alwin.core.api.model.activity;

import com.codersteam.alwin.core.api.model.declaration.DeclarationDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Michal Horowic
 */
public class ActivityDto {

    private Long id;
    private Long issueId;
    private OperatorDto operator;
    private ActivityTypeDto activityType;
    private Date activityDate;
    private Date plannedDate;
    private Date creationDate;
    private ActivityStateDto state;
    private BigDecimal charge;
    private String invoiceId;
    private List<ActivityDetailDto> activityDetails;
    private List<DeclarationDto> declarations;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }

    public OperatorDto getOperator() {
        return operator;
    }

    public void setOperator(final OperatorDto operator) {
        this.operator = operator;
    }

    public ActivityTypeDto getActivityType() {
        return activityType;
    }

    public void setActivityType(final ActivityTypeDto activityType) {
        this.activityType = activityType;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(final Date activityDate) {
        this.activityDate = activityDate;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(final Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public ActivityStateDto getState() {
        return state;
    }

    public void setState(final ActivityStateDto state) {
        this.state = state;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(final BigDecimal charge) {
        this.charge = charge;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<ActivityDetailDto> getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(final List<ActivityDetailDto> activityDetails) {
        this.activityDetails = activityDetails;
    }

    public List<DeclarationDto> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(final List<DeclarationDto> declarations) {
        this.declarations = declarations;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityDto that = (ActivityDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(issueId, that.issueId) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(activityType, that.activityType) &&
                Objects.equals(activityDate, that.activityDate) &&
                Objects.equals(plannedDate, that.plannedDate) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(state, that.state) &&
                Objects.equals(charge, that.charge) &&
                Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(activityDetails, that.activityDetails) &&
                Objects.equals(declarations, that.declarations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issueId, operator, activityType, activityDate, plannedDate, creationDate, state, charge, invoiceId, activityDetails, declarations);
    }

    @Override
    public String toString() {
        return "ActivityDto{" +
                "id=" + id +
                ", issueId=" + issueId +
                ", operator=" + operator +
                ", activityType=" + activityType +
                ", activityDate=" + activityDate +
                ", plannedDate=" + plannedDate +
                ", creationDate=" + creationDate +
                ", state=" + state +
                ", charge=" + charge +
                ", invoiceId='" + invoiceId + '\'' +
                ", activityDetails=" + activityDetails +
                ", declarations=" + declarations +
                '}';
    }
}
