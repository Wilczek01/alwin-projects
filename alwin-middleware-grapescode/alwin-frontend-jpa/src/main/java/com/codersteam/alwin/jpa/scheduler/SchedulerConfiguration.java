package com.codersteam.alwin.jpa.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;

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
 * Konfiguracja zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Entity
@Table(name = "scheduler_configuration")
public class SchedulerConfiguration {

    /**
     * Identyfikator konfiguracji
     */
    @SequenceGenerator(name = "schedulerconfigurationSEQ", sequenceName = "scheduler_configuration_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedulerconfigurationSEQ")
    private Long id;

    /**
     * Identyfikator grupy zadań cyklicznych
     */
    @Column(name = "batch_process", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private SchedulerBatchProcessType batchProcess;

    /**
     * Data ostatniej zmiany konfiguracji
     */
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    /**
     * Godzina startu procesu
     */
    @Column(name = "hour", nullable = false)
    private Integer hour;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SchedulerBatchProcessType getBatchProcess() {
        return batchProcess;
    }

    public void setBatchProcess(final SchedulerBatchProcessType batchProcess) {
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
