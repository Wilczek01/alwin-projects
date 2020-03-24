package com.codersteam.alwin.integration.write;

import com.codersteam.alwin.core.api.model.holiday.HolidayDto;
import com.codersteam.alwin.core.api.service.holiday.HolidayService;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNoContent;
import static com.codersteam.alwin.testdata.HolidayTestData.ADDED_ID;
import static com.codersteam.alwin.testdata.HolidayTestData.DAY_1;
import static com.codersteam.alwin.testdata.HolidayTestData.ID_1;
import static com.codersteam.alwin.testdata.HolidayTestData.MODIFIED_DESCRIPTION;
import static com.codersteam.alwin.testdata.HolidayTestData.MONTH_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Michal Horowic
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json"})
public class HolidayResourceTestIT extends WriteTestBase {

    @EJB
    private HolidayService holidayService;


    @Test
    public void shouldCreateHoliday() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        assertFalse(holidayService.checkIfHolidayExists(ADDED_ID));

        // and
        final JsonElement request = request("holiday/createRepeatableHoliday.json");

        // when
        final int responseCode = checkHttpPostCode("holidays", request, loginToken);

        // then
        assertCreated(responseCode);
        assertTrue(holidayService.checkIfHolidayExists(ADDED_ID));
    }

    @Test
    public void shouldUpdateExistingHoliday() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // and
        final List<HolidayDto> holidays = holidayService.findAllHolidaysPerDay(DAY_1, MONTH_1, LocalDate.now().getYear(), null);

        // and
        assertNotEquals(MODIFIED_DESCRIPTION, holidays.get(0).getDescription());

        // and
        final JsonElement request = request("holiday/updateExistingHoliday.json");

        // when
        final int responseCode = checkHttpPatchCode("holidays/" + ID_1, request, loginToken);

        // and
        commitTrx();

        // and
        final List<HolidayDto> updatedHolidays = holidayService.findAllHolidaysPerDay(DAY_1, MONTH_1, LocalDate.now().getYear(), null);

        // then
        assertAccepted(responseCode);
        assertEquals(MODIFIED_DESCRIPTION, updatedHolidays.get(0).getDescription());
    }


    @Test
    public void shouldDeleteHoliday() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final List<HolidayDto> holidays = holidayService.findAllHolidaysPerDay(DAY_1, MONTH_1, LocalDate.now().getYear(), null);

        // and
        assertFalse(holidays.isEmpty());

        // when
        final HttpResponse response = checkHttpDelete("holidays/" + ID_1, loginToken);

        // and
        final List<HolidayDto> updatedHolidays = holidayService.findAllHolidaysPerDay(DAY_1, MONTH_1, LocalDate.now().getYear(), null);

        // then
        assertNoContent(response);
        assertTrue(updatedHolidays.isEmpty());
    }
}
