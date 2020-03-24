package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Dariusz Rackowski
 */
public class DateResourceTestIT extends ReadTestBase {

    @Test
    public void shouldReturnPreviousWorkingDayDate() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final JsonElement jsonElement = sendHttpGet("dates/previousWorkingDay", loginToken);

        // then
        assertResponseEquals("date/previousWorkingDay.json", jsonElement);
    }

}
