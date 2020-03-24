package com.codersteam.alwin.integration.write;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertAccepted;

/**
 * @author Piotr Naroznik
 */
@UsingDataSet({"test-permission.json", "test-data.json", "test-message-template.json"})
public class MessageResourceTestIT extends WriteTestBase {

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json"})
    public void shouldSendSmsMessage() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("message/sendSmsMessage.json");

        // when
        final HttpResponse response = checkHttpPost("messages/sms/send", request, loginToken);

        // then
        assertAccepted(response);
    }
}
