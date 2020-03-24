package com.codersteam.alwin.jpa.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * Informacja o wykonywaniu zadań cyklicznych (trwających i zakończonych)
 *
 * @author Dariusz Rackowski
 */
@Entity
@Table(name = "scheduler_execution")
public class SchedulerExecutionInfo {

    /**
     * Identyfikator
     */
    @SequenceGenerator(name = "schedulerexecutionSEQ", sequenceName = "scheduler_execution_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedulerexecutionSEQ")
    private Long id;

    /**
     * Data rozpoczęcia
     */
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    /**
     * Data zakończenia - nie ustawione w przypadku trwającego zadania
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * Status - zawiera dodatkową informację w przypadku niepowodzenia zadania
     */
    @Column(name = "state", length = 500)
    private String state;

    /**
     * Typ zadania cyklicznego
     */
    @Column(name = "type", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private SchedulerTaskType type;

    /**
     * Czy zostało wystartowany ręcznie
     */
    @Column(name = "manual", nullable = false)
    private boolean manual;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public SchedulerTaskType getType() {
        return type;
    }

    public void setType(final SchedulerTaskType type) {
        this.type = type;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(final boolean manual) {
        this.manual = manual;
    }

    @Override
    public String toString() {
        return "SchedulerExecutionInfo{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state='" + state + '\'' +
                ", type=" + type +
                ", manual=" + manual +
                '}';
    }
}
