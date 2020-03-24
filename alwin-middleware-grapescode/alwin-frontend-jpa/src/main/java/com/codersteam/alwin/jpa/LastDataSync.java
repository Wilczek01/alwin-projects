package com.codersteam.alwin.jpa;

import com.codersteam.alwin.jpa.type.LastDataSyncType;

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
 * Przechowuje informacje o ostatniej synchronizacji danych przez scheduler
 *
 * @author Piotr Naroznik
 */
@Entity
@Table(name = "LAST_DATA_SYNC")
public class LastDataSync {

    @SequenceGenerator(name = "lastdatasyncSEQ", sequenceName = "last_data_sync_id_seq", allocationSize = 1, initialValue = 100)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lastdatasyncSEQ")
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private LastDataSyncType type;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LastDataSyncType getType() {
        return type;
    }

    public void setType(LastDataSyncType type) {
        this.type = type;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
