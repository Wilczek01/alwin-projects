package com.codersteam.alwin.auth.model;

import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.model.operator.PermissionDto;

/**
 * @author Tomasz Sliwinski
 */
public class LoginResponse {

    private final String username;
    private final OperatorTypeDto role;
    private final boolean forceUpdatePassword;
    private final PermissionDto permission;

    public LoginResponse(final String username, final OperatorTypeDto role, final boolean forceUpdatePassword, final PermissionDto permission) {
        this.username = username;
        this.role = role;
        this.forceUpdatePassword = forceUpdatePassword;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public OperatorTypeDto getRole() {
        return role;
    }

    public boolean isForceUpdatePassword() {
        return forceUpdatePassword;
    }

    public PermissionDto getPermission() {
        return permission;
    }
}
