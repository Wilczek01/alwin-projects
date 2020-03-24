package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertForbidden;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;

/**
 * @author Michal Horowic
 */
public class SchedulerExecutionResourceTestIT extends ReadTestBase {

    @Test
    public void shouldByUnauthorizedToGetSchedulersExecutions() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("schedulers");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldByForbiddenToGetSchedulersExecutions() throws Exception {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("schedulers", loginToken);

        // then
        assertForbidden(responseCode);
    }


    @Test
    public void shouldGetPageOfSchedulersExecutions() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("schedulers?firstResult=0&maxResults=2", loginToken);

        // then
        assertResponseEquals("scheduler/schedulerExecutions.json", response);
    }

}
