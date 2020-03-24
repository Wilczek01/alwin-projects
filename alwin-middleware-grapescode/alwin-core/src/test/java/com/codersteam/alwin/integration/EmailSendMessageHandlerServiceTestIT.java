package com.codersteam.alwin.integration;

import com.codersteam.alwin.integration.common.JmsCoreTestBase;
import com.codersteam.alwin.integration.mock.EmailSenderServiceMock;
import org.junit.Test;

import javax.jms.JMSException;

import static com.codersteam.alwin.core.api.model.email.EmailType.MISSING_ISSUES;
import static com.codersteam.alwin.testdata.MissingIssuesEmailMessageTestData.MISSING_ISSUES_EMAIL_MESSAGE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

/**
 * @author Tomasz Sliwinski
 */
public class EmailSendMessageHandlerServiceTestIT extends JmsCoreTestBase {

    @Test
    public void shouldProcessEmailSendMessage() throws JMSException, InterruptedException {
        // when
        sendMessageOnEmailQueue(MISSING_ISSUES_EMAIL_MESSAGE);

        // then
        assertEquals(MISSING_ISSUES.name(), EmailSenderServiceMock.queue.poll(10, SECONDS));
    }
}
