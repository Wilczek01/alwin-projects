package com.codersteam.alwin.core.api.model.issue;

import com.codersteam.alwin.core.api.model.activity.DefaultIssueActivityDto;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author Adam Stepnowski
 */
public class IssueTypeConfigurationDto {

    private Long id;
    private IssueTypeDto issueType;
    private Integer duration;
    private SegmentDto segment;
    private Boolean createAutomatically;
    private Integer dpdStart;
    private BigDecimal minBalanceStart;
    private List<DefaultIssueActivityDto> defaultIssueActivity;
    private Set<OperatorTypeDto> operatorTypes;
    private Boolean includeDebtInvoicesDuringIssue;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public IssueTypeDto getIssueType() {
        return issueType;
    }

    public void setIssueType(final IssueTypeDto issueType) {
        this.issueType = issueType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public SegmentDto getSegment() {
        return segment;
    }

    public void setSegment(final SegmentDto segment) {
        this.segment = segment;
    }

    public Boolean getCreateAutomatically() {
        return createAutomatically;
    }

    public void setCreateAutomatically(final Boolean createAutomatically) {
        this.createAutomatically = createAutomatically;
    }

    public Integer getDpdStart() {
        return dpdStart;
    }

    public void setDpdStart(final Integer dpdStart) {
        this.dpdStart = dpdStart;
    }

    public BigDecimal getMinBalanceStart() {
        return minBalanceStart;
    }

    public void setMinBalanceStart(final BigDecimal minBalanceStart) {
        this.minBalanceStart = minBalanceStart;
    }

    public List<DefaultIssueActivityDto> getDefaultIssueActivity() {
        return defaultIssueActivity;
    }

    public void setDefaultIssueActivity(final List<DefaultIssueActivityDto> defaultIssueActivity) {
        this.defaultIssueActivity = defaultIssueActivity;
    }

    public Set<OperatorTypeDto> getOperatorTypes() {
        return operatorTypes;
    }

    public void setOperatorTypes(final Set<OperatorTypeDto> operatorTypes) {
        this.operatorTypes = operatorTypes;
    }

    public Boolean getIncludeDebtInvoicesDuringIssue() {
        return includeDebtInvoicesDuringIssue;
    }

    public void setIncludeDebtInvoicesDuringIssue(final Boolean includeDebtInvoicesDuringIssue) {
        this.includeDebtInvoicesDuringIssue = includeDebtInvoicesDuringIssue;
    }

    @Override
    public String toString() {
        return "IssueTypeConfigurationDto{" +
                "id=" + id +
                ", issueType=" + issueType +
                ", duration=" + duration +
                ", segment=" + segment +
                ", createAutomatically=" + createAutomatically +
                ", dpdStart=" + dpdStart +
                ", minBalanceStart=" + minBalanceStart +
                ", defaultIssueActivity=" + defaultIssueActivity +
                ", operatorTypes=" + operatorTypes +
                ", includeDebtInvoicesDuringIssue=" + includeDebtInvoicesDuringIssue +
                '}';
    }
}
