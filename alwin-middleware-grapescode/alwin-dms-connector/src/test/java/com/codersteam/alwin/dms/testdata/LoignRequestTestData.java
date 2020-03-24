package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.LoginRequest;

public class LoignRequestTestData extends LoginRequestDtoTestData {

    public static LoginRequest loginRequest1() {
        return loginRequest(LOGIN_1, PASSWORD_1);
    }

    public static LoginRequest loginRequest2() {
        return loginRequest(LOGIN_2, PASSWORD_2);
    }

    public static LoginRequest loginRequest3() {
        return loginRequest(LOGIN_3, PASSWORD_3);
    }

    public static LoginRequest loginRequest4() {
        return loginRequest(LOGIN_4, PASSWORD_4);
    }

    private static LoginRequest loginRequest(final String login, final String password) {
        final LoginRequest loginRequestDto = new LoginRequest();
        loginRequestDto.setLogin(login);
        loginRequestDto.setPassword(password);
        return loginRequestDto;
    }
}