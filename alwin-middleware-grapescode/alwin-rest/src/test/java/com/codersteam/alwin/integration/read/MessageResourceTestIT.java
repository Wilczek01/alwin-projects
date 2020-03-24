package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.junit.Test;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertUnauthorized;
import static com.codersteam.alwin.integration.common.ResponseType.SMS_TEMPLATE_LIST;

/**
 * @author Piotr Naroznik
 */
public class MessageResourceTestIT extends ReadTestBase {

    @Test
    public void shouldFindSmsTemplates() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("messages/sms/templates", loginToken);

        // then
        assertResponseEquals("message/smsTemplates.json", response, SMS_TEMPLATE_LIST);
    }

    @Test
    public void shouldHaveForbiddenAccessToSmsTemplates() throws Exception {
        // when
        final int responseCode = checkHttpGetCode("messages/sms/templates");

        // then
        assertUnauthorized(responseCode);
    }

    @Test
    public void shouldNotSendSmsMessageIfValidationFailedForPhoneNumber() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("message/sendSmsWithWrongPhoneNumber.json");

        // when
        final HttpResponse response = checkHttpPost("messages/sms/send", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("message/smsInvalidPhoneNumber.json", response);
    }

    @Test
    public void shouldNotSendSmsMessageIfValidationFailedForEmptySmsMessage() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("message/sendSmsWithEmptySmsMessage.json");

        // when
        final HttpResponse response = checkHttpPost("messages/sms/send", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("message/smsEmptyMessage.json", response);
    }

    @Test
    public void shouldNotSendSmsMessageIfValidationFailedForTooLongSmsMessage() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("message/sendSmsWithTooLongSmsMessage.json");

        // when
        final HttpResponse response = checkHttpPost("messages/sms/send", request, loginToken);

        // then
        assertBadRequest(response);
        assertResponseEquals("message/smsTooLongMessage.json", response);
    }
}
