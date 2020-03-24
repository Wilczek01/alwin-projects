package com.codersteam.alwin.dms.testdata;

import com.codersteam.alwin.dms.model.LoginRequestDto;

public class LoginRequestDtoTestData {
    protected static final String LOGIN_1 = "LOGIN_1";
    protected static final String PASSWORD_1 = "PASSWORD_1";
    protected static final String LOGIN_2 = "LOGIN_2";
    protected static final String PASSWORD_2 = "PASSWORD_2";
    protected static final String LOGIN_3 = "LOGIN_3";
    protected static final String PASSWORD_3 = "PASSWORD_3";
    protected static final String LOGIN_4 = "LOGIN_4";
    protected static final String PASSWORD_4 = "PASSWORD_4";

    public static LoginRequestDto loginRequestDto1() {
        return loginRequestDto(LOGIN_1, PASSWORD_1);
    }

    public static LoginRequestDto loginRequestDto2() {
        return loginRequestDto(LOGIN_2, PASSWORD_2);
    }

    public static LoginRequestDto loginRequestDto3() {
        return loginRequestDto(LOGIN_3, PASSWORD_3);
    }

    public static LoginRequestDto loginRequestDto4() {
        return loginRequestDto(LOGIN_4, PASSWORD_4);
    }

    private static LoginRequestDto loginRequestDto(final String login, final String password) {
        final LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setLogin(login);
        loginRequestDto.setPassword(password);
        return loginRequestDto;
    }
}