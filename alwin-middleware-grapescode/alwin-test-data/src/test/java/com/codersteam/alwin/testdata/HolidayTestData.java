package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.holiday.HolidayDto;
import com.codersteam.alwin.core.api.model.holiday.HolidayInputDto;
import com.codersteam.alwin.core.api.model.user.SimpleUserDto;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.holiday.Holiday;

import static com.codersteam.alwin.testdata.UserTestData.TEST_SIMPLE_USER_DTO_1;
import static com.codersteam.alwin.testdata.UserTestData.alwinUser1;
import static com.codersteam.alwin.testdata.UserTestData.alwinUser2;

@SuppressWarnings("WeakerAccess")
public class HolidayTestData {

    public static final long ID_1 = 1L;
    public static final long ID_2 = 2L;
    public static final long ID_3 = 3L;
    public static final long ID_4 = 4L;
    public static final long ADDED_ID = 100L;
    public static final long NOT_EXISTING_ID = -1L;

    public static final String DESCRIPTION_1 = "Święto Pracy";
    public static final String DESCRIPTION_2 = "Wielkanoc";
    public static final String DESCRIPTION_3 = "Urlop";
    public static final String DESCRIPTION_4 = "Wolne";
    public static final String ADDED_DESCRIPTION = "Test";
    public static final String MODIFIED_DESCRIPTION = "Święto Pracy 2";

    public static final int DAY_1 = 1;
    public static final int DAY_2 = 1;
    public static final int DAY_3 = 2;
    public static final int DAY_4 = 4;
    public static final int ADDED_DAY = 15;

    public static final int MONTH_1 = 5;
    public static final int MONTH_2 = 4;
    public static final int MONTH_3 = 5;
    public static final int MONTH_4 = 5;
    public static final int ADDED_MONTH = 4;
    public static final int MODIFIED_MONTH = 4;

    public static final Integer YEAR_1 = null;
    public static final Integer YEAR_2 = 2018;
    public static final Integer YEAR_3 = 2018;
    public static final Integer YEAR_4 = 2018;
    public static final Integer ADDED_YEAR = null;

    public static final User USER_1 = null;
    public static final User USER_2 = null;
    public static final User USER_3 = alwinUser1();
    public static final User USER_4 = alwinUser2();
    public static final User ADDED_USER = null;

    public static final SimpleUserDto USER_1_DTO = null;
    public static final SimpleUserDto USER_2_DTO = null;
    public static final SimpleUserDto USER_3_DTO = TEST_SIMPLE_USER_DTO_1;
    public static final SimpleUserDto ADDED_USER_DTO = null;


    public static Holiday testHoliday1() {
        return holiday(ID_1, DESCRIPTION_1, DAY_1, MONTH_1, YEAR_1, USER_1);
    }

    public static HolidayDto testHolidayDto1() {
        return holidayDto(ID_1, DESCRIPTION_1, DAY_1, MONTH_1, YEAR_1, USER_1_DTO);
    }

    public static HolidayInputDto testHolidayInputDto1() {
        return holidayInputDto(DESCRIPTION_1, DAY_1, MONTH_1, YEAR_1, USER_1_DTO);
    }

    public static Holiday testHoliday2() {
        return holiday(ID_2, DESCRIPTION_2, DAY_2, MONTH_2, YEAR_2, USER_2);
    }

    public static HolidayDto testHolidayDto2() {
        return holidayDto(ID_2, DESCRIPTION_2, DAY_2, MONTH_2, YEAR_2, USER_2_DTO);
    }

    public static Holiday testAddedHoliday1() {
        return holiday(ADDED_ID, ADDED_DESCRIPTION, ADDED_DAY, ADDED_MONTH, ADDED_YEAR, ADDED_USER);
    }

    public static Holiday testHolidayToAdd() {
        return holiday(null, ADDED_DESCRIPTION, ADDED_DAY, ADDED_MONTH, ADDED_YEAR, ADDED_USER);
    }

    public static HolidayInputDto testHolidayToAddDto() {
        return holidayInputDto(ADDED_DESCRIPTION, ADDED_DAY, ADDED_MONTH, ADDED_YEAR, ADDED_USER_DTO);
    }

    public static Holiday testModifiedHoliday1() {
        return holiday(ID_1, MODIFIED_DESCRIPTION, DAY_1, MODIFIED_MONTH, YEAR_1, USER_1);
    }

    public static Holiday testHoliday3() {
        return holiday(ID_3, DESCRIPTION_3, DAY_3, MONTH_3, YEAR_3, USER_3);
    }

    public static Holiday testHoliday4() {
        return holiday(ID_4, DESCRIPTION_4, DAY_4, MONTH_4, YEAR_4, USER_4);
    }

    private static Holiday holiday(final Long id, final String description, final int day, final int month, final Integer year, final User user) {
        final Holiday holiday = new Holiday();
        holiday.setId(id);
        holiday.setDay(day);
        holiday.setYear(year);
        holiday.setMonth(month);
        holiday.setDescription(description);
        holiday.setUser(user);
        return holiday;
    }

    private static HolidayDto holidayDto(final Long id, final String description, final int day, final int month, final Integer year, final SimpleUserDto user) {
        final HolidayDto holiday = new HolidayDto();
        holiday.setId(id);
        holiday.setDay(day);
        holiday.setYear(year);
        holiday.setMonth(month);
        holiday.setDescription(description);
        holiday.setUser(user);
        return holiday;
    }

    private static HolidayInputDto holidayInputDto(final String description, final int day, final int month, final Integer year, final SimpleUserDto user) {
        return holidayDto(null, description, day, month, year, user);
    }
}
