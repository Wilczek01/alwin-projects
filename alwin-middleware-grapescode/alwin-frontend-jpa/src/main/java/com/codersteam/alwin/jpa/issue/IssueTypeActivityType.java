package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.jpa.activity.ActivityType;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

/**
 * @author Michal Horowic
 */
@Entity
@Audited
@Table(name = "issue_type_activity_type")
public class IssueTypeActivityType {

    @SequenceGenerator(name = "issueTypeActivityTypeSEQ", sequenceName = "issue_type_activity_type_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueTypeActivityTypeSEQ")
    private Long id;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issue_type_id", referencedColumnName = "id")
    private IssueType issueType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_id", referencedColumnName = "id")
    private ActivityType activityType;

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

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(final ActivityType activityType) {
        this.activityType = activityType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueTypeActivityType)) return false;

        final IssueTypeActivityType that = (IssueTypeActivityType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (issueType != null ? !issueType.equals(that.issueType) : that.issueType != null) return false;
        return activityType != null ? activityType.equals(that.activityType) : that.activityType == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (issueType != null ? issueType.hashCode() : 0);
        result = 31 * result + (activityType != null ? activityType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueTypeActivityType{" +
                "id=" + id +
                ", issueType=" + issueType +
                ", activityType=" + activityType +
                '}';
    }
}


