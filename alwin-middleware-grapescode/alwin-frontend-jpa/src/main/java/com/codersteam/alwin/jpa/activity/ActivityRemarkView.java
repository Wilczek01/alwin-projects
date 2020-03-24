package com.codersteam.alwin.jpa.activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Widok przechowujący informację o komentarzu czynności windykacyjnej na potrzeby wyszukiwania oraz sortowania czynności
 *
 * @author Piotr Naroznik
 */
@Entity
@Table(name = "activity_remark_view")
public class ActivityRemarkView {

    @Id
    @Column(name = "activity_id")
    private Long activityId;

    @Column
    private String remark;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(final Long activityId) {
        this.activityId = activityId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String comment) {
        this.remark = comment;
    }
}