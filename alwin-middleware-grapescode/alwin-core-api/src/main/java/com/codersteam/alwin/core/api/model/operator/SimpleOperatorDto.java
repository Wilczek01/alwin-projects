package com.codersteam.alwin.core.api.model.operator;

/**
 * @author Michal Horowic
 */
public class SimpleOperatorDto {

    private Long id;
    private String login;
    private boolean active;
    private PermissionDto permission;
    private OperatorDto parentOperator;
    private OperatorTypeDto type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public OperatorDto getParentOperator() {
        return parentOperator;
    }

    public void setParentOperator(OperatorDto parentOperator) {
        this.parentOperator = parentOperator;
    }

    public OperatorTypeDto getType() {
        return type;
    }

    public void setType(OperatorTypeDto type) {
        this.type = type;
    }


}
