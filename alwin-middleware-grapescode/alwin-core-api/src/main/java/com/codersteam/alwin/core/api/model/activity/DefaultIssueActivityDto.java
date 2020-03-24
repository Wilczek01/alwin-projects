package com.codersteam.alwin.core.api.model.activity;

import java.util.Date;

/**
 * @author Tomasz Sliwinski
 */
public class DefaultIssueActivityDto {

    private Long id;
    private Long activityTypeId;
    private Integer defaultDay;
    private Integer version;
    private Date creatingDate;
    private Date defaultExecutionDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(final Long activityTypeId) {
        this.activityTypeId = activityTypeId;
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

    public Date getDefaultExecutionDate() {
        return defaultExecutionDate;
    }

    public void setDefaultExecutionDate(final Date defaultExecutionDate) {
        this.defaultExecutionDate = defaultExecutionDate;
    }
}
