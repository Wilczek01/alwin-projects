package com.codersteam.alwin.integration.write;

import com.google.gson.JsonElement;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertCreated;

/**
 * @author Adam Stepnowski
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-notification.json", "test-issue.json", "test-issue-invoice.json"})
public class NotificationResourceTestIT extends WriteTestBase {

    @Test
    public void shouldCreateNewNotification() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("notification/newNotification.json");

        // when
        final int responseCode = checkHttpPostCode("notifications", request, loginToken);

        // then
        assertCreated(responseCode);
    }

    @Test
    public void shouldMarkNotificationAsRead() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final int responseCode = checkHttpPatchCode("notifications/1", loginToken);

        // then
        assertAccepted(responseCode);
    }
}
