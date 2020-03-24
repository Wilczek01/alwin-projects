package com.codersteam.alwin.core.api.model.holiday;

import com.codersteam.alwin.core.api.model.user.SimpleUserDto;

/**
 * @author Michal Horowic
 */
public class HolidayInputDto {

    private String description;
    private int day;
    private int month;
    private Integer year;
    private SimpleUserDto user;

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

    public SimpleUserDto getUser() {
        return user;
    }

    public void setUser(final SimpleUserDto user) {
        this.user = user;
    }
}
