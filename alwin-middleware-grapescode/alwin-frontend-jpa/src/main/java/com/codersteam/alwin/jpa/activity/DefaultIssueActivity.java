package com.codersteam.alwin.jpa.activity;

import com.codersteam.alwin.jpa.issue.IssueType;

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
import java.util.Date;

/**
 * Encja konfiguracji domyślnych czynności dla nowo tworzonego zlecenia
 *
 * @author Tomasz Sliwinski
 */
@Entity
@Table(name = "default_issue_activity")
public class DefaultIssueActivity {

    @Id
    @SequenceGenerator(name = "defaultIssueActivitySEQ", sequenceName = "default_issue_activity_id_seq", allocationSize = 1, initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "defaultIssueActivitySEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issue_type_id", nullable = false)
    private IssueType issueType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_id", nullable = false)
    private ActivityType activityType;

    @Column(name = "default_day", nullable = false)
    private Integer defaultDay;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "creating_date", nullable = false)
    private Date creatingDate;

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

    public Integer getDefaultDay() {
        return defaultDay;
    }

    public void setDefaultDay(final Integer defaultDay) {
        this.defaultDay = defaultDay;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(final Date creatingDate) {
        this.creatingDate = creatingDate;
    }
}
