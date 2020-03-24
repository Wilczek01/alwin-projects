package com.codersteam.alwin.core.api.model.scheduler;

import java.util.Date;

/**
 * Informacja o wykonywaniu zadań cyklicznych (trwających i zakończonych)
 *
 * @author Dariusz Rackowski
 */
public class SchedulerExecutionInfoDto {

    /**
     * Identyfikator
     */
    private Long id;

    /**
     * Data rozpoczęcia
     */
    private Date startDate;

    /**
     * Data zakończenia - nie ustawione w przypadku trwającego zadania
     */
    private Date endDate;

    /**
     * Status - zawiera dodatkową informację w przypadku niepowodzenia zadania
     */
    private String state;

    /**
     * Typ zadania cyklicznego
     */
    private SchedulerTaskTypeDto type;

    /**
     * Czy zostało wystartowany ręcznie
     */
    private boolean manual;

    @SuppressWarnings("unused")
    public SchedulerExecutionInfoDto() {
        //need for mapper
    }

    public SchedulerExecutionInfoDto(final Long id, final Date startDate, final Date endDate, final String state,
                                     final SchedulerTaskTypeDto type, final boolean manual) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.type = type;
        this.manual = manual;
    }

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

    public SchedulerTaskTypeDto getType() {
        return type;
    }

    public void setType(final SchedulerTaskTypeDto type) {
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
        return "SchedulerExecutionInfoDto{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state='" + state + '\'' +
                ", type=" + type +
                ", manual=" + manual +
                '}';
    }
}
