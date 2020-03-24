package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.api.service.email.EmailSenderService;
import com.codersteam.alwin.integration.common.CoreTestBase;
import org.junit.Ignore;
import org.junit.Test;

import javax.ejb.EJB;
import javax.mail.MessagingException;

import static com.codersteam.alwin.testdata.MissingIssuesEmailMessageTestData.MISSING_ISSUES_EMAIL_MESSAGE;
import static com.codersteam.alwin.testdata.UnresolvedIssuesEmailMessageTestData.UNRESOLVED_ISSUES_EMAIL_MESSAGE;

/**
 * Testowa wysyłka maila - do celów dewelopmentu
 * <p>
 * 1. usunąc mocka serwisu z konfiguracji CoreTestBase:
 * </p>
 * <p>
 * remove line:
 * <code>ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass(EmailSenderServiceImpl.class);</code>
 * </p>
 * <p>
 * add line:
 * <code>ear.getAsType(JavaArchive.class, "alwin-core.jar").deleteClass(EmailSenderServiceMock.class);</code>
 * after line:
 * <code>ear.getAsType(JavaArchive.class, "alwin-core.jar").addPackage(AidaServiceMock.class.getPackage());</code>
 * </p>
 * 2. zmienić adres w EmailTestData
 * <p>
 * 3. uruchomić test
 * </p>
 * <p><code>cd alwin-core</code></p>
 * <p><code>mvn -DfailIfNoTests=false -Dit.test=EmailSenderServiceImplTestIT -Pint-tests verify</code></p>
 *
 * @author Tomasz Sliwinski
 */
@Ignore("Do celów deweloperskich")
public class EmailSenderServiceImplTestIT extends CoreTestBase {

    @EJB
    private EmailSenderService emailSenderService;

    @Test
    @Ignore("Do celów deweloperskich")
    public void shouldSendMissingIssuesReportMail() throws MessagingException {
        // when
        emailSenderService.sendMissingIssuesReportMail(MISSING_ISSUES_EMAIL_MESSAGE);

        // then
        // check your mailbox :)
    }

    @Test
    @Ignore("Do celów deweloperskich")
    public void shouldSendUnresolvedIssuesReportMail() throws MessagingException {
        // when
        emailSenderService.sendUnresolvedIssuesReportMail(UNRESOLVED_ISSUES_EMAIL_MESSAGE);

        // then
        // check your mailbox :)
    }
}
