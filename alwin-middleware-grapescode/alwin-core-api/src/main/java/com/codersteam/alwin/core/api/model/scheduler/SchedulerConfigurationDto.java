package com.codersteam.alwin.core.api.model.scheduler;

import java.util.Date;

/**
 * Konfiguracja zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
public class SchedulerConfigurationDto {

    /**
     * Identyfikator konfiguracji
     */
    private Long id;

    /**
     * Identyfikator grupy zadań do wykonania
     */
    private SchedulerBatchProcessTypeDto batchProcess;

    /**
     * Data ostatniej zmiany konfiguracji
     */
    private Date updateDate;

    /**
     * Godzina startu procesu
     */
    private Integer hour;

    @SuppressWarnings("unused")
    public SchedulerConfigurationDto() {
        //need for mapper
    }

    public SchedulerConfigurationDto(final Long id, final SchedulerBatchProcessTypeDto batchProcess, final Date updateDate, final Integer hour) {
        this.id = id;
        this.batchProcess = batchProcess;
        this.updateDate = updateDate;
        this.hour = hour;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SchedulerBatchProcessTypeDto getBatchProcess() {
        return batchProcess;
    }

    public void setBatchProcess(final SchedulerBatchProcessTypeDto batchProcess) {
        this.batchProcess = batchProcess;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(final Integer hour) {
        this.hour = hour;
    }
}
