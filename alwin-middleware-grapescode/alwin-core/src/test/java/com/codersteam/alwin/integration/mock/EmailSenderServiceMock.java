package com.codersteam.alwin.integration.mock;

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;
import com.codersteam.alwin.core.api.service.email.EmailSenderService;

import javax.ejb.Stateless;
import javax.mail.MessagingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.codersteam.alwin.core.api.model.email.EmailType.MISSING_ISSUES;
import static com.codersteam.alwin.core.api.model.email.EmailType.UNRESOLVED_ISSUES;

/**
 * @author Tomasz Sliwinski
 */
@Stateless
public class EmailSenderServiceMock implements EmailSenderService {

    public static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    @Override
    public void sendMissingIssuesReportMail(final MissingIssuesEmailMessage missingIssueEmail) throws MessagingException {
        queue.add(MISSING_ISSUES.name());
    }

    @Override
    public void sendUnresolvedIssuesReportMail(final UnresolvedIssuesEmailMessage unresolvedIssuesEmail) throws MessagingException {
        queue.add(UNRESOLVED_ISSUES.name());
    }
}
