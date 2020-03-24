package com.codersteam.alwin.core.api.model.date;

import java.util.Date;

/**
 * @author Dariusz Rackowski
 */
public class DateDto {

    private Date date;

    public DateDto() {
    }

    public DateDto(final Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

}
