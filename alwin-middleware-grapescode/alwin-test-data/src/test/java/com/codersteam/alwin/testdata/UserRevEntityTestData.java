package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.UserRevEntity;

/**
 * @author Tomasz Sliwinski
 */
public final class UserRevEntityTestData {

    private UserRevEntityTestData() {
    }

    public static UserRevEntity userRevEntity1() {
        return userRevEntity(OperatorTestData.OPERATOR_ID_1);
    }

    private static UserRevEntity userRevEntity(final Long operatorId) {
        final UserRevEntity userRevEntity = new UserRevEntity();
        userRevEntity.setOperator(operatorId);
        return userRevEntity;
    }
}
