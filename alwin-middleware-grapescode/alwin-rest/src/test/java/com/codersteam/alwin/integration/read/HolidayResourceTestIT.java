package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;
import java.time.Month;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.testdata.HolidayTestData.DAY_1;
import static com.codersteam.alwin.testdata.HolidayTestData.MONTH_1;
import static com.codersteam.alwin.testdata.HolidayTestData.MONTH_3;
import static com.codersteam.alwin.testdata.HolidayTestData.NOT_EXISTING_ID;
import static com.codersteam.alwin.testdata.HolidayTestData.USER_3_DTO;

/**
 * @author Michal Horowic
 */
public class HolidayResourceTestIT extends ReadTestBase {

    @Test
    public void shouldReturnAllHolidaysPerYear() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("holidays/2018", loginToken);


        // then
        assertResponseEquals("holiday/holidaysPerYear.json", response);
    }

    @Test
    public void shouldReturnAllHolidaysPerMonth() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("holidays/2018?month=" + MONTH_1, loginToken);


        // then
        assertResponseEquals("holiday/holidaysPerMonth.json", response);
    }

    @Test
    public void shouldReturnAllHolidaysPerMonthForUser() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("holidays/2018?month=" + MONTH_3 + "&userId=" + USER_3_DTO.getId(), loginToken);


        // then
        assertResponseEquals("holiday/holidaysPerMonthForUser.json", response);
    }

    @Test
    public void shouldReturnAllHolidaysPerDay() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement response = sendHttpGet("holidays/2018?month=" + MONTH_1 + "&day=" + DAY_1, loginToken);


        // then
        assertResponseEquals("holiday/holidaysPerDay.json", response);
    }

    @Test
    public void shouldNotUpdateNotExistingHoliday() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("holiday/updateExistingHoliday.json");

        // when
        final HttpResponse response = checkHttpPatch("holidays/" + NOT_EXISTING_ID, request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("holiday/updateNotExistingHoliday.json", response);
    }

    @Test
    public void shouldNotDeleteNotExistingHoliday() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final HttpResponse response = checkHttpDelete("holidays/" + NOT_EXISTING_ID, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("holiday/deleteNotExistingHoliday.json", response);
    }

    @Test
    public void shouldNotGetHolidaysForIncorrectDayInMonth() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final Month month = Month.AUGUST;
        final int monthNo = month.getValue();
        final int incorrectDay = month.maxLength() + 1;

        // when
        final HttpResponse response = checkHttpGet("holidays/2018?month=" + monthNo + "&day=" + incorrectDay, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("holiday/holidaysForIncorrectDayInMonth.json", response);
    }

    @Test
    public void shouldNotGetHolidaysForIncorrectMonth() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        //and
        final int incorrectMonthNo = Month.values().length + 1;

        // when
        final HttpResponse response = checkHttpGet("holidays/2018?month=" + incorrectMonthNo, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("holiday/holidaysForIncorrectMonth.json", response);
    }

    @Test
    public void shouldNotCreateHolidayForMissingDescription() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("holiday/missingDescriptionHoliday.json");

        // when
        final HttpResponse response = checkHttpPost("holidays", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("holiday/createHolidayForMissingDescription.json", response);
    }

    @Test
    public void shouldNotCreateRepeatableHolidayForDaysInLeapYear() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("holiday/repeatableHolidayForLeapYear.json");

        // when
        final HttpResponse response = checkHttpPost("holidays", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("holiday/createRepeatableHolidayForDaysInLeapYear.json", response);
    }


    // ---------------------------------------------------------------------------------------
    // authorization checks
    // ---------------------------------------------------------------------------------------

    @Test
    public void shouldNotReturnHolidaysForNotAuthenticatedUser() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("holidays/2018");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotReturnHolidaysForForbiddenUser() throws IOException {
        // given
        final String loginToken = loggedInUserWithoutIssues();

        // when
        final int responseCode = checkHttpGetCode("holidays/2018", loginToken);

        // then
        assertForbidden(responseCode);
    }
}
