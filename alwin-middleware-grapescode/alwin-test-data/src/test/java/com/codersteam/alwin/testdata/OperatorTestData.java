package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.operator.EditableOperatorDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.core.api.model.operator.PermissionDto;
import com.codersteam.alwin.core.api.model.operator.SimpleOperatorDto;
import com.codersteam.alwin.core.api.model.user.SimpleUserDto;
import com.codersteam.alwin.core.api.model.user.UserDto;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.operator.OperatorType;
import com.codersteam.alwin.jpa.operator.Permission;

import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_12;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_13;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_2;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_3;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorAccountManager;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypeAdmin;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypeFieldDebtCollector;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollector;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollector1;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollector2;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollectorManager;
import static com.codersteam.alwin.testdata.PermissionTestData.permission2;
import static com.codersteam.alwin.testdata.PermissionTestData.permission3;
import static com.codersteam.alwin.testdata.PermissionTestData.permission4;
import static com.codersteam.alwin.testdata.PermissionTestData.permissionDto2;
import static com.codersteam.alwin.testdata.PermissionTestData.permissionDto3;
import static com.codersteam.alwin.testdata.PermissionTestData.permissionDto4;
import static com.codersteam.alwin.testdata.UserTestData.*;

@SuppressWarnings("WeakerAccess")
public class OperatorTestData {

    public static final Long NOT_EXISTING_OPERATOR_ID = -1L;

    public static final Long OPERATOR_ID_1 = 1L;
    public static final String OPERATOR_LOGIN_1 = "amickiewicz";
    public static boolean FORCE_UPDATE_PASSWORD_1 = false;

    public static final Long OPERATOR_ID_2 = 2L;
    public static final String OPERATOR_LOGIN_2 = "jslowacki";
    public static boolean FORCE_UPDATE_PASSWORD_2 = false;

    public static final Long OPERATOR_ID_3 = 3L;
    private static final String OPERATOR_LOGIN_3 = "cknorwid";
    public static boolean FORCE_UPDATE_PASSWORD_3 = false;

    public static final Long OPERATOR_ID_4 = 4L;
    private static final String OPERATOR_LOGIN_4 = "cmilosz";
    public static boolean FORCE_UPDATE_PASSWORD_4 = true;

    public static final Long OPERATOR_ID_6 = 6L;
    private static final String OPERATOR_LOGIN_6 = "jbrzechwa";
    public static boolean FORCE_UPDATE_PASSWORD_6 = false;

    public static final Long OPERATOR_ID_12 = 12L;
    private static final String OPERATOR_LOGIN_12 = "mkonopnicka";
    public static boolean FORCE_UPDATE_PASSWORD_12 = false;
    public static boolean OPERATOR_ACTIVE_12 = false;

    public static final Long OPERATOR_ID_15 = 15L;
    private static final String OPERATOR_LOGIN_15 = "lstaff";
    public static boolean FORCE_UPDATE_PASSWORD_15 = false;

    public static final Long OPERATOR_ID_16 = 16L;
    private static final String OPERATOR_LOGIN_16 = "aasynk";
    public static boolean FORCE_UPDATE_PASSWORD_16 = false;

    private static final String OPERATOR_PASSWORD = "30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d";
    private static final String OPERATOR_SALT = "7ad53ec9-876f-4cf3-b7cd-526a153e5836";
    private static final boolean OPERATOR_ACTIVE = true;

    public static final String NEW_PASSWORD = "Test123";

    public static Operator testOperator1() {
        return operator(OPERATOR_ID_1, alwinUser1(), OPERATOR_LOGIN_1, operatorTypeAdmin(), null, null, FORCE_UPDATE_PASSWORD_1);
    }

    public static EditableOperatorDto testEditableOperator1() {
        return editableOperatorDto(OPERATOR_ID_1, OPERATOR_LOGIN_1, TEST_USER_DTO_1, null, TEST_OPERATOR_TYPE_DTO);
    }

    public static EditableOperatorDto testEditableOperatorWithoutId1() {
        return editableOperatorDto(null, OPERATOR_LOGIN_1, TEST_USER_DTO_1, null, TEST_OPERATOR_TYPE_DTO);
    }

    public static Operator testOperator4() {
        return operator(OPERATOR_ID_4, TEST_ALWIN_USER_4, OPERATOR_LOGIN_4, operatorTypePhoneDebtCollectorManager(), null, permission4(), FORCE_UPDATE_PASSWORD_4);
    }

    public static Operator testOperator3() {
        return operator(OPERATOR_ID_3, testUser3(), OPERATOR_LOGIN_3, operatorTypePhoneDebtCollector(), null, permission3(), FORCE_UPDATE_PASSWORD_3);
    }

    public static EditableOperatorDto testEditableOperatorWithoutId3() {
        return editableOperatorDto(null, OPERATOR_LOGIN_3, TEST_USER_DTO_3, null, TEST_OPERATOR_TYPE_DTO);
    }

    public static Operator testOperator2() {
        return operator(OPERATOR_ID_2, TEST_ALWIN_USER_2, OPERATOR_LOGIN_2, operatorTypePhoneDebtCollector(), testOperator4(), permission2(), FORCE_UPDATE_PASSWORD_2);
    }

    public static Operator testOperator12() {
        return operator(OPERATOR_ID_12, testUser12(), OPERATOR_LOGIN_12, operatorAccountManager(), null, null, FORCE_UPDATE_PASSWORD_12,
                OPERATOR_ACTIVE_12);
    }

    public static EditableOperatorDto testEditableOperator2() {
        return editableOperatorDto(OPERATOR_ID_2, OPERATOR_LOGIN_2, TEST_USER_DTO_2, null, TEST_OPERATOR_TYPE_DTO);
    }

    public static Operator testOperator5() {
        return editableOperator(OPERATOR_ID_1, alwinUser1(), OPERATOR_LOGIN_1, operatorTypeAdmin(), null, FORCE_UPDATE_PASSWORD_1);
    }

    public static Operator testOperator6() {
        return operator(OPERATOR_ID_6, testUser6(), OPERATOR_LOGIN_6, operatorTypeFieldDebtCollector(), null, null, FORCE_UPDATE_PASSWORD_6);
    }

    public static Operator testOperator15() {
        return operator(OPERATOR_ID_15, testUser14(), OPERATOR_LOGIN_15, operatorTypePhoneDebtCollector1(), testOperator4(), null, FORCE_UPDATE_PASSWORD_15);
    }

    public static Operator testOperator16() {
        return operator(OPERATOR_ID_16, testUser15(), OPERATOR_LOGIN_16, operatorTypePhoneDebtCollector2(), testOperator4(), null, FORCE_UPDATE_PASSWORD_16);
    }

    public static Operator testOperatorWithIdOnly() {
        return operator(OPERATOR_ID_1, null, OPERATOR_LOGIN_1, operatorTypeAdmin(), testOperator4(), null, FORCE_UPDATE_PASSWORD_1);
    }

    public static OperatorDto testOperatorDto1() {
        return operatorDto(OPERATOR_ID_1, OPERATOR_LOGIN_1, TEST_USER_DTO_1, null, TEST_OPERATOR_TYPE_DTO, null, FORCE_UPDATE_PASSWORD_1);
    }

    public static OperatorDto testOperatorDto2() {
        return operatorDto(OPERATOR_ID_2, OPERATOR_LOGIN_2, TEST_USER_DTO_2, testOperatorDto4(), TEST_OPERATOR_TYPE_DTO_2, permissionDto2(), FORCE_UPDATE_PASSWORD_2);
    }

    public static OperatorDto testOperatorDto3() {
        return operatorDto(OPERATOR_ID_3, OPERATOR_LOGIN_3, TEST_USER_DTO_3, null, TEST_OPERATOR_TYPE_DTO_2, permissionDto3(), FORCE_UPDATE_PASSWORD_3);
    }

    public static OperatorDto testOperatorDto4() {
        return operatorDto(OPERATOR_ID_4, OPERATOR_LOGIN_4, TEST_USER_DTO_4, null, TEST_OPERATOR_TYPE_DTO_3, permissionDto4(), FORCE_UPDATE_PASSWORD_4);
    }

    public static OperatorDto testOperatorDto15() {
        return operatorDto(OPERATOR_ID_15, OPERATOR_LOGIN_15, TEST_USER_DTO_14, testOperatorDto4(), TEST_OPERATOR_TYPE_DTO_12, null, FORCE_UPDATE_PASSWORD_15);
    }

    public static OperatorDto testOperatorDto16() {
        return operatorDto(OPERATOR_ID_16, OPERATOR_LOGIN_16, TEST_USER_DTO_15, testOperatorDto4(), TEST_OPERATOR_TYPE_DTO_13, null, FORCE_UPDATE_PASSWORD_16);
    }

    public static OperatorUserDto testOperatorUserDto1() {
        return operatorUserDto(OPERATOR_ID_1, TEST_SIMPLE_USER_DTO_1);
    }

    public static OperatorUserDto testOperatorUserDto2() {
        return operatorUserDto(OPERATOR_ID_2, TEST_SIMPLE_USER_DTO_2);
    }

    public static OperatorUserDto testOperatorUserDto3() {
        return operatorUserDto(OPERATOR_ID_3, TEST_SIMPLE_USER_DTO_3);
    }

    public static OperatorUserDto testOperatorUserDto4() {
        return operatorUserDto(OPERATOR_ID_4, TEST_SIMPLE_USER_DTO_4);
    }

    public static OperatorUserDto testOperatorUserDto12() {
        return operatorUserDto(OPERATOR_ID_12, TEST_SIMPLE_USER_DTO_12);
    }

    public static Operator expectedFullOperator() {
        return operator(OPERATOR_ID_1, null, OPERATOR_LOGIN_1, operatorTypeAdmin(), null, null, FORCE_UPDATE_PASSWORD_1);
    }

    public static SimpleOperatorDto expectedFullOperatorDto() {
        return simpleOperatorDto(OPERATOR_ID_1, OPERATOR_LOGIN_1, null, TEST_OPERATOR_TYPE_DTO, null);
    }

    private static Operator operator(final Long id, final User user, final String login, final OperatorType operatorType, final Operator parentOperator,
                                     final Permission permission, final boolean forceUpdatePassword, final boolean active) {
        final Operator operator = operator(id, user, login, operatorType, parentOperator, permission, forceUpdatePassword);
        operator.setActive(active);
        return operator;
    }

    private static Operator operator(final Long id, final User user, final String login, final OperatorType operatorType, final Operator parentOperator,
                                     final Permission permission, final boolean forceUpdatePassword) {
        final Operator operator = new Operator();
        operator.setId(id);
        operator.setUser(user);
        operator.setActive(OPERATOR_ACTIVE);
        operator.setForceUpdatePassword(forceUpdatePassword);
        operator.setType(operatorType);
        operator.setPassword(OPERATOR_PASSWORD);
        operator.setSalt(OPERATOR_SALT);
        operator.setLogin(login);
        operator.setParentOperator(parentOperator);
        operator.setPermission(permission);
        return operator;
    }

    private static Operator editableOperator(final Long id, final User user, final String login, final OperatorType operatorType, final Operator
            parentOperator, final boolean forceUpdatePassword) {
        final Operator operator = new Operator();
        operator.setId(id);
        operator.setUser(user);
        operator.setForceUpdatePassword(forceUpdatePassword);
        operator.setActive(OPERATOR_ACTIVE);
        operator.setType(operatorType);
        operator.setLogin(login);
        operator.setParentOperator(parentOperator);
        return operator;
    }

    private static OperatorDto operatorDto(final Long id, final String login, final UserDto user, final OperatorDto parentOperator, final OperatorTypeDto
            operatorType, final PermissionDto permission, final boolean forceUpdatePassword) {
        final OperatorDto operator = new OperatorDto();
        operator.setId(id);
        operator.setUser(user);
        operator.setLogin(login);
        operator.setActive(OPERATOR_ACTIVE);
        operator.setForceUpdatePassword(forceUpdatePassword);
        operator.setType(operatorType);
        operator.setParentOperator(parentOperator);
        operator.setPermission(permission);
        return operator;
    }

    private static EditableOperatorDto editableOperatorDto(final Long id, final String login, final UserDto user, final OperatorDto parentOperator, final
    OperatorTypeDto operatorType) {
        final EditableOperatorDto operator = new EditableOperatorDto();
        operator.setId(id);
        operator.setUser(user);
        operator.setActive(OPERATOR_ACTIVE);
        operator.setType(operatorType);
        operator.setParentOperator(parentOperator);
        operator.setLogin(login);
        return operator;
    }

    private static SimpleOperatorDto simpleOperatorDto(final Long id, final String login, final OperatorDto parentOperator,
                                                       final OperatorTypeDto operatorType, final PermissionDto permission) {
        final SimpleOperatorDto operator = new SimpleOperatorDto();
        operator.setId(id);
        operator.setLogin(login);
        operator.setActive(OPERATOR_ACTIVE);
        operator.setType(operatorType);
        operator.setParentOperator(parentOperator);
        operator.setPermission(permission);
        return operator;
    }

    private static OperatorUserDto operatorUserDto(final Long id, final SimpleUserDto user) {
        final OperatorUserDto operator = new OperatorUserDto();
        operator.setId(id);
        operator.setUser(user);
        return operator;
    }
}
