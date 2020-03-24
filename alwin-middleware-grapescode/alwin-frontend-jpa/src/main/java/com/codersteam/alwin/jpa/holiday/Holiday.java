package com.codersteam.alwin.jpa.holiday;

import com.codersteam.alwin.jpa.User;
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

/**
 * Dni wolne od pracy
 *
 * @author Michal Horowic
 */
@Entity
@Audited
@Table(name = "holiday")
public class Holiday {

    /**
     * Identyfikator dnia wolnego
     */
    @SequenceGenerator(name = "holidaySEQ", sequenceName = "holiday_id_seq", allocationSize = 1, initialValue = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holidaySEQ")
    private Long id;

    /**
     * Nazwa dnia wolnego
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Dzień miesiąca dnia wolnego
     */
    @Column(name = "holiday_day", nullable = false)
    private int day;

    /**
     * Miesiąc dnia wolnego
     */
    @Column(name = "holiday_month", nullable = false)
    private int month;

    /**
     * Rok dnia wolnego - brak oznacza powtarzający się dzień wolny co roku
     */
    @Column(name = "holiday_year")
    private Integer year;

    /**
     * Użytkownik dla którego jest to dzień wolny - brak oznacza, że dzień wolny jest dla wszystkich
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alwin_user_id", referencedColumnName = "id")
    @Audited
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(final int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
