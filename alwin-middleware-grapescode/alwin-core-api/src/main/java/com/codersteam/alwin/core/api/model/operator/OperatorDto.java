package com.codersteam.alwin.core.api.model.operator;

import com.codersteam.alwin.core.api.model.user.UserDto;

import java.util.Objects;

/**
 * @author Michal Horowic
 */
public class OperatorDto {

    private Long id;
    private boolean active;
    private boolean forceUpdatePassword;
    private String login;
    private PermissionDto permission;
    private OperatorDto parentOperator;
    private UserDto user;
    private OperatorTypeDto type;

    public OperatorDto() {
    }

    public OperatorDto(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isForceUpdatePassword() {
        return forceUpdatePassword;
    }

    public void setForceUpdatePassword(final boolean forceUpdatePassword) {
        this.forceUpdatePassword = forceUpdatePassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public PermissionDto getPermission() {
        return permission;
    }

    public void setPermission(final PermissionDto permission) {
        this.permission = permission;
    }

    public OperatorDto getParentOperator() {
        return parentOperator;
    }

    public void setParentOperator(final OperatorDto parentOperator) {
        this.parentOperator = parentOperator;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(final UserDto user) {
        this.user = user;
    }

    public OperatorTypeDto getType() {
        return type;
    }

    public void setType(final OperatorTypeDto type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OperatorDto that = (OperatorDto) o;
        return active == that.active && forceUpdatePassword == that.forceUpdatePassword &&
                Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(permission, that.permission) &&
                Objects.equals(parentOperator, that.parentOperator) &&
                Objects.equals(user, that.user) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, forceUpdatePassword, login, permission, parentOperator, user, type);
    }

    @Override
    public String toString() {
        return "OperatorDto{" +
                "id=" + id +
                ", active=" + active +
                ", forceUpdatePassword=" + forceUpdatePassword +
                ", login='" + login + '\'' +
                ", permission=" + permission +
                ", parentOperator=" + parentOperator +
                ", user=" + user +
                ", type=" + type +
                '}';
    }
}
