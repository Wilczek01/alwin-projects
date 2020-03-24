package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.EditableOperatorDto;
import com.codersteam.alwin.core.api.model.operator.SimpleOperatorDto;
import com.codersteam.alwin.core.api.model.user.EditableUserDto;
import com.codersteam.alwin.core.api.model.user.FullUserDto;
import com.codersteam.alwin.core.api.model.user.SimpleUserDto;
import com.codersteam.alwin.core.api.model.user.UserDto;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.operator.Operator;

import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.OperatorTestData.expectedFullOperator;
import static com.codersteam.alwin.testdata.OperatorTestData.expectedFullOperatorDto;
import static com.codersteam.alwin.testdata.OperatorTestData.testEditableOperator1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


@SuppressWarnings("WeakerAccess")
public class UserTestData {

    public static final long TOTAL_VALUES = 15L;

    public static final Long TEST_NOT_EXISTING_USER_ID = -1L;

    public static final Long TEST_USER_ID_1 = 1L;
    public static final String TEST_USER_FIRST_NAME_1 = "Adam";
    public static final String TEST_USER_LAST_NAME_1 = "Mickiewicz";
    public static final String TEST_USER_EMAIL_1 = "amickiewicz@grapescode.pl";

    public static final Long TEST_USER_ID_2 = 2L;
    private static final String TEST_USER_FIRST_NAME_2 = "Juliusz";
    public static final String TEST_USER_LAST_NAME_2 = "Słowacki";
    public static final String TEST_USER_EMAIL_2 = "jslowacki@grapescode.pl";
    public static final String TEST_USER_PHONE_NUMBER_2 = "0048123098567";

    public static final Long TEST_USER_ID_3 = 3L;
    private static final String TEST_USER_FIRST_NAME_3 = "Cyprian Kamil";
    private static final String TEST_USER_LAST_NAME_3 = "Norwid";
    public static final String TEST_USER_EMAIL_3 = "cknorwid@grapescode.pl";

    public static final Long TEST_USER_ID_4 = 4L;
    private static final String TEST_USER_FIRST_NAME_4 = "Czesław";
    private static final String TEST_USER_LAST_NAME_4 = "Miłosz";
    public static final String TEST_USER_EMAIL_4 = "cmilosz@grapescode.pl";

    public static final Long TEST_USER_ID_5 = 5L;
    private static final String TEST_USER_FIRST_NAME_5 = "Stanisław";
    private static final String TEST_USER_LAST_NAME_5 = "Lem";
    public static final String TEST_USER_EMAIL_5 = "slem@grapescode.pl";

    public static final Long TEST_USER_ID_6 = 6L;
    private static final String TEST_USER_FIRST_NAME_6 = "Jan";
    private static final String TEST_USER_LAST_NAME_6 = "Brzechwa";
    public static final String TEST_USER_EMAIL_6 = "jbrzechwa@grapescode.pl";

    public static final Long TEST_USER_ID_12 = 12L;
    private static final String TEST_USER_FIRST_NAME_12 = "Maria";
    private static final String TEST_USER_LAST_NAME_12 = "Konopnicka";
    public static final String TEST_USER_EMAIL_12 = "mkonopnicka@grapescode.pl";

    public static final Long TEST_USER_ID_14 = 14L;
    private static final String TEST_USER_FIRST_NAME_14 = "Leopold";
    private static final String TEST_USER_LAST_NAME_14 = "Staff";
    public static final String TEST_USER_EMAIL_14 = "lstaff@grapescode.pl";

    public static final Long TEST_USER_ID_15 = 15L;
    private static final String TEST_USER_FIRST_NAME_15 = "Adam";
    private static final String TEST_USER_LAST_NAME_15 = "Asnyk";
    public static final String TEST_USER_EMAIL_15 = "aasnyk@grapescode.pl";

    private static final Date USER_CREATION_DATE = parse("2017-07-10 00:00:00.000000");
    private static final Date USER_UPDATE_DATE = parse("2017-07-10 00:00:00.000000");

    public static User alwinUser1() {
        return user(TEST_USER_ID_1, TEST_USER_FIRST_NAME_1, TEST_USER_LAST_NAME_1, TEST_USER_EMAIL_1, null,
                USER_CREATION_DATE, USER_UPDATE_DATE, singletonList(expectedFullOperator()));
    }

    public static User alwinUser2() {
        return user(TEST_USER_ID_2, TEST_USER_FIRST_NAME_2, TEST_USER_LAST_NAME_2, TEST_USER_EMAIL_2, TEST_USER_PHONE_NUMBER_2,
                USER_CREATION_DATE, USER_UPDATE_DATE, null);
    }

    public static final User TEST_ALWIN_USER_2 = user(TEST_USER_ID_2, TEST_USER_FIRST_NAME_2, TEST_USER_LAST_NAME_2, TEST_USER_EMAIL_2,
            TEST_USER_PHONE_NUMBER_2, USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());

    public static User testUser3() {
        return user(TEST_USER_ID_3, TEST_USER_FIRST_NAME_3, TEST_USER_LAST_NAME_3, TEST_USER_EMAIL_3, null,
                USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());
    }

    public static User testUser5() {
        return user(TEST_USER_ID_5, TEST_USER_FIRST_NAME_5, TEST_USER_LAST_NAME_5, TEST_USER_EMAIL_5, null,
                USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());
    }

    public static User testUser6() {
        return user(TEST_USER_ID_6, TEST_USER_FIRST_NAME_6, TEST_USER_LAST_NAME_6, TEST_USER_EMAIL_6, null,
                USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());
    }

    public static User testUser12() {
        return user(TEST_USER_ID_12, TEST_USER_FIRST_NAME_12, TEST_USER_LAST_NAME_12, TEST_USER_EMAIL_12, null,
                USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());
    }

    public static User testUser14() {
        return user(TEST_USER_ID_14, TEST_USER_FIRST_NAME_14, TEST_USER_LAST_NAME_14, TEST_USER_EMAIL_14, null,
                USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());
    }

    public static User testUser15() {
        return user(TEST_USER_ID_15, TEST_USER_FIRST_NAME_15, TEST_USER_LAST_NAME_15, TEST_USER_EMAIL_15, null,
                USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());
    }

    public static final User TEST_ALWIN_USER_4 = user(TEST_USER_ID_4, TEST_USER_FIRST_NAME_4, TEST_USER_LAST_NAME_4, TEST_USER_EMAIL_4, null,
            USER_CREATION_DATE, USER_UPDATE_DATE, emptyList());

    public static final List<User> FIRST_PAGE_OF_USERS = asList(alwinUser1(), TEST_ALWIN_USER_2);
    public static final List<User> SECOND_PAGE_OF_USERS = asList(testUser3(), TEST_ALWIN_USER_4, testUser5(), testUser6());

    public static final UserDto TEST_USER_DTO_1 = userDto(TEST_USER_ID_1, TEST_USER_FIRST_NAME_1, TEST_USER_LAST_NAME_1, TEST_USER_EMAIL_1, null);
    public static final UserDto TEST_USER_DTO_2 = userDto(TEST_USER_ID_2, TEST_USER_FIRST_NAME_2, TEST_USER_LAST_NAME_2, TEST_USER_EMAIL_2, TEST_USER_PHONE_NUMBER_2);
    public static final UserDto TEST_USER_DTO_3 = userDto(TEST_USER_ID_3, TEST_USER_FIRST_NAME_3, TEST_USER_LAST_NAME_3, TEST_USER_EMAIL_3, null);
    public static final UserDto TEST_USER_DTO_4 = userDto(TEST_USER_ID_4, TEST_USER_FIRST_NAME_4, TEST_USER_LAST_NAME_4, TEST_USER_EMAIL_4, null);
    public static final UserDto TEST_USER_DTO_14 = userDto(TEST_USER_ID_14, TEST_USER_FIRST_NAME_14, TEST_USER_LAST_NAME_14, TEST_USER_EMAIL_14, null);
    public static final UserDto TEST_USER_DTO_15 = userDto(TEST_USER_ID_15, TEST_USER_FIRST_NAME_15, TEST_USER_LAST_NAME_15, TEST_USER_EMAIL_15, null);

    public static final SimpleUserDto TEST_SIMPLE_USER_DTO_1 = simpleUserDto(TEST_USER_ID_1, TEST_USER_FIRST_NAME_1, TEST_USER_LAST_NAME_1);
    public static final SimpleUserDto TEST_SIMPLE_USER_DTO_2 = simpleUserDto(TEST_USER_ID_2, TEST_USER_FIRST_NAME_2, TEST_USER_LAST_NAME_2);
    public static final SimpleUserDto TEST_SIMPLE_USER_DTO_3 = simpleUserDto(TEST_USER_ID_3, TEST_USER_FIRST_NAME_3, TEST_USER_LAST_NAME_3);
    public static final SimpleUserDto TEST_SIMPLE_USER_DTO_4 = simpleUserDto(TEST_USER_ID_4, TEST_USER_FIRST_NAME_4, TEST_USER_LAST_NAME_4);
    public static final SimpleUserDto TEST_SIMPLE_USER_DTO_12 = simpleUserDto(TEST_USER_ID_12, TEST_USER_FIRST_NAME_12, TEST_USER_LAST_NAME_12);

    public static final FullUserDto TEST_FULL_USER_DTO_1 = fullUserDto(TEST_USER_ID_1, TEST_USER_FIRST_NAME_1, TEST_USER_LAST_NAME_1, TEST_USER_EMAIL_1, null,
            singletonList(expectedFullOperatorDto()), USER_CREATION_DATE, USER_UPDATE_DATE);
    public static final FullUserDto TEST_FULL_USER_DTO_2 = fullUserDto(TEST_USER_ID_2, TEST_USER_FIRST_NAME_2, TEST_USER_LAST_NAME_2, TEST_USER_EMAIL_2, TEST_USER_PHONE_NUMBER_2,
            emptyList(), USER_CREATION_DATE, USER_UPDATE_DATE);
    public static final FullUserDto TEST_FULL_USER_DTO_3 = fullUserDto(TEST_USER_ID_3, TEST_USER_FIRST_NAME_3, TEST_USER_LAST_NAME_3, TEST_USER_EMAIL_3, null,
            emptyList(), USER_CREATION_DATE, USER_UPDATE_DATE);
    public static final FullUserDto TEST_FULL_USER_DTO_4 = fullUserDto(TEST_USER_ID_4, TEST_USER_FIRST_NAME_4, TEST_USER_LAST_NAME_4, TEST_USER_EMAIL_4, null,
            emptyList(), USER_CREATION_DATE, USER_UPDATE_DATE);
    public static final FullUserDto TEST_FULL_USER_DTO_5 = fullUserDto(TEST_USER_ID_5, TEST_USER_FIRST_NAME_5, TEST_USER_LAST_NAME_5, TEST_USER_EMAIL_5, null,
            emptyList(), USER_CREATION_DATE, USER_UPDATE_DATE);
    public static final FullUserDto TEST_FULL_USER_DTO_6 = fullUserDto(TEST_USER_ID_6, TEST_USER_FIRST_NAME_6, TEST_USER_LAST_NAME_6, TEST_USER_EMAIL_6, null,
            emptyList(), USER_CREATION_DATE, USER_UPDATE_DATE);

    public static final Page<FullUserDto> FIRST_PAGE_OF_USERS_DTO = new Page<>(asList(TEST_FULL_USER_DTO_1, TEST_FULL_USER_DTO_2), TOTAL_VALUES);
    public static final Page<FullUserDto> SECOND_PAGE_OF_USERS_DTO = new Page<>(asList(TEST_FULL_USER_DTO_3, TEST_FULL_USER_DTO_4, TEST_FULL_USER_DTO_5,
            TEST_FULL_USER_DTO_6), TOTAL_VALUES);

    public static EditableUserDto alwinEditableUserDto1() {
        return editableUserDto(TEST_USER_ID_1, TEST_USER_FIRST_NAME_1, TEST_USER_LAST_NAME_1, TEST_USER_EMAIL_1, null,
                singletonList(testEditableOperator1()));
    }


    private static User user(final Long id, final String firstName, final String lastName, final String email, final String phoneNumber, final Date
            creationDate, final Date updateDate, final List<Operator> operators) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setCreationDate(creationDate);
        user.setUpdateDate(updateDate);
        user.setOperators(operators);
        return user;
    }

    private static UserDto userDto(final Long id, final String firstName, final String lastName, final String email, final String phoneNumber) {
        final UserDto userDto = new UserDto(id, firstName, lastName);
        userDto.setPhoneNumber(phoneNumber);
        userDto.setEmail(email);
        return userDto;
    }

    private static FullUserDto fullUserDto(final Long id, final String firstName, final String lastName, final String email, final String phoneNumber,
                                           final List<SimpleOperatorDto> operators, final Date creationDate, final Date updateDate) {
        final FullUserDto user = new FullUserDto();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setCreationDate(creationDate);
        user.setUpdateDate(updateDate);
        user.setOperators(operators);
        return user;
    }

    private static EditableUserDto editableUserDto(final Long id, final String firstName, final String lastName, final String email, final String phoneNumber,
                                                   final List<EditableOperatorDto> operators) {
        final EditableUserDto user = new EditableUserDto();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setOperators(operators);
        return user;
    }

    private static SimpleUserDto simpleUserDto(final Long id, final String firstName, final String lastName) {
        final SimpleUserDto user = new SimpleUserDto();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
