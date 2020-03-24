package com.codersteam.alwin.integration.read;

import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertBadRequest;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.ExcessPaymentServiceMock.EXCESS_PAYMENTS;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyExcessPaymentTestData.companyExcessPaymentsDto;

/**
 * @author Adam Stepnowski
 */
public class NotificationResourceTestIT extends ReadTestBase {

    @Test
    public void shouldFindAllNotificationsForIssue() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // and
        EXCESS_PAYMENTS.put(EXT_COMPANY_ID_1, companyExcessPaymentsDto());

        // and
        CURRENT_DATE = "2017-07-15";

        // when
        final JsonElement response = sendHttpGet("notifications/issue/1", loginToken);

        // then
        assertResponseEquals("notification/allNotifications.json", response);
    }

    @Test
    public void shouldNotFindAnyNotificationsForIssue() throws Exception {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final JsonElement response = sendHttpGet("notifications/issue/2", loginToken);

        // then
        assertResponseEquals("notification/emptyNotifications.json", response);
    }

    @Test
    public void shouldNotCreateNewNotificationForNotExistingIssue() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("notification/newNotificationWithNotExistingIssue.json");

        // when
        final int responseCode = checkHttpPostCode("notifications", request, loginToken);

        // then
        assertBadRequest(responseCode);
    }


    @Test
    public void shouldNotCreateNewNotificationWithEmptyMessage() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // and
        final JsonElement request = request("notification/newNotificationWithEmptyMessage.json");

        // when
        final int responseCode = checkHttpPostCode("notifications", request, loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotMarkNotificationAsReadSecondTime() throws IOException {
        // given
        final String loginToken = loggedInAdmin();

        // when
        final int responseCode = checkHttpPatchCode("notifications/3", loginToken);

        // then
        assertBadRequest(responseCode);
    }

    @Test
    public void shouldNotMarkAsReadNotExistingNotification() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpPatchCode("notifications/100", loginToken);

        // then
        assertBadRequest(responseCode);
    }
}
