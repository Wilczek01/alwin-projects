package com.codersteam.alwin.jpa.activity;

import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.ActivityState;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Encja czynności zlecenia
 *
 * @author Tomasz Sliwinski
 */
@Audited
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @SequenceGenerator(name = "activitySEQ", sequenceName = "activity_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activitySEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operator_id", referencedColumnName = "id")
    @NotAudited
    private Operator operator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private ActivityType activityType;

    @Column(name = "activity_date")
    private Date activityDate;

    @Column(name = "planned_date")
    private Date plannedDate;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "activity_state", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityState state;

    @Column(name = "charge")
    private BigDecimal charge;

    @Column(name = "invoice_id")
    private String invoiceId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_id", nullable = false)
    @OrderBy("id ASC")
    private List<ActivityDetail> activityDetails;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_id", nullable = false)
    @OrderBy("id ASC")
    private List<Declaration> declarations;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "activity_id")
    @NotAudited
    private ActivityRemarkView remarkView;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(final ActivityType activityType) {
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

    public ActivityState getState() {
        return state;
    }

    public void setState(final ActivityState state) {
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

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(final Operator operator) {
        this.operator = operator;
    }

    public List<ActivityDetail> getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(final List<ActivityDetail> activityDetails) {
        this.activityDetails = activityDetails;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(final Issue issue) {
        this.issue = issue;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(final List<Declaration> declarations) {
        this.declarations = declarations;
    }

    public ActivityRemarkView getRemarkView() {
        return remarkView;
    }

    public void setRemarkView(final ActivityRemarkView comment) {
        this.remarkView = comment;
    }
}
