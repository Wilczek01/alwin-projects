package com.codersteam.alwin.core.api.model.operator;

import com.codersteam.alwin.core.api.model.user.UserDto;

/**
 * @author Michal Horowic
 */
public class EditableOperatorDto {

    private Long id;
    private boolean active;
    private String login;
    private String password;
    private String salt;
    private PermissionDto permission;
    private OperatorDto parentOperator;
    private UserDto user;
    private OperatorTypeDto type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PermissionDto getPermission() {
        return permission;
    }

    public void setPermission(PermissionDto permission) {
        this.permission = permission;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public OperatorDto getParentOperator() {
        return parentOperator;
    }

    public void setParentOperator(OperatorDto parentOperator) {
        this.parentOperator = parentOperator;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public OperatorTypeDto getType() {
        return type;
    }

    public void setType(OperatorTypeDto type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
