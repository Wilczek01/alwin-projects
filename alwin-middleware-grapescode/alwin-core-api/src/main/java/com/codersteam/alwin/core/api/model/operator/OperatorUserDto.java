package com.codersteam.alwin.core.api.model.operator;

import com.codersteam.alwin.core.api.model.user.SimpleUserDto;

/**
 * @author Piotr Naroznik
 */
public class OperatorUserDto {

    private Long id;
    private SimpleUserDto user;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SimpleUserDto getUser() {
        return user;
    }

    public void setUser(final SimpleUserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OperatorUserDto{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
