package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.common.issue.Segment;

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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "issue_type_configuration")
public class IssueTypeConfiguration {

    @SequenceGenerator(name = "issueTypeConfigurationSEQ", sequenceName = "issue_type_configuration_id_seq", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueTypeConfigurationSEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "issue_type_id", referencedColumnName = "id", nullable = false)
    private IssueType issueType;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "segment", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Segment segment;

    // Póki nie ma obsłużonych wszystkich typów nie robimy update tej flagi z poziomu aplikacji (true powoduje automatyczne tworzenie zleceń dla tego typu)
    @Column(name = "create_automatically", nullable = false, updatable = false)
    private Boolean createAutomatically;

    @Column(name = "dpd_start")
    private Integer dpdStart;

    @Column(name = "min_balance_start")
    private BigDecimal minBalanceStart;

    @Column(name = "include_debt_invoices_during_issue", nullable = false)
    private Boolean includeDebtInvoicesDuringIssue;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(final IssueType issueType) {
        this.issueType = issueType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(final Segment segment) {
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

    public Boolean getIncludeDebtInvoicesDuringIssue() {
        return includeDebtInvoicesDuringIssue;
    }

    public void setIncludeDebtInvoicesDuringIssue(final Boolean includeDebtInvoicesDuringIssue) {
        this.includeDebtInvoicesDuringIssue = includeDebtInvoicesDuringIssue;
    }

    @Override
    public String toString() {
        return "IssueTypeConfiguration{" +
                "id=" + id +
                ", issueType=" + issueType +
                ", duration=" + duration +
                ", segment=" + segment +
                ", createAutomatically=" + createAutomatically +
                ", dpdStart=" + dpdStart +
                ", minBalanceStart=" + minBalanceStart +
                ", includeDebtInvoicesDuringIssue=" + includeDebtInvoicesDuringIssue +
                '}';
    }
}
