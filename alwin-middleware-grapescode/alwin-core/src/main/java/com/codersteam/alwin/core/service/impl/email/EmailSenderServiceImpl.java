package com.codersteam.alwin.core.service.impl.email;

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.issue.MissingIssueDto;
import com.codersteam.alwin.core.api.model.issue.UnresolvedIssueDto;
import com.codersteam.alwin.core.api.service.email.EmailSenderService;
import com.codersteam.alwin.core.service.template.AlwinTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.StringJoiner;

import static com.codersteam.alwin.core.api.model.email.EmailType.MISSING_ISSUES;
import static com.codersteam.alwin.core.api.model.email.EmailType.UNRESOLVED_ISSUES;
import static com.codersteam.alwin.core.service.impl.email.EmailProperties.EMAIL_MIME_TYPE;

/**
 * Serwis odpowiedzialny za wysyłkę maila
 *
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource(mappedName = "java:jboss/mail/Default")
    private Session session;

    private final AlwinTemplateEngine templateEngine;
    private final EmailProperties emailProperties;

    @Inject
    public EmailSenderServiceImpl(final AlwinTemplateEngine templateEngine, final EmailProperties emailProperties) {
        this.templateEngine = templateEngine;
        this.emailProperties = emailProperties;
    }

    @Override
    public void sendMissingIssuesReportMail(final MissingIssuesEmailMessage missingIssuesEmailMessage) throws MessagingException {
        final List<MissingIssueDto> missingIssueDtos = missingIssuesEmailMessage.getMissingIssueDtos();
        LOG.info("Sending missing issues report mail to {} containing {} documents, date from = {}, date to = {}.",
                missingIssuesEmailMessage.getEmailRecipients(), missingIssueDtos.size(), missingIssuesEmailMessage.getDateFrom(),
                missingIssuesEmailMessage.getDateTo());

        final Context ctx = new Context();
        ctx.setVariable("missingIssues", missingIssueDtos);
        ctx.setVariable("dateFrom", missingIssuesEmailMessage.getDateFrom());
        ctx.setVariable("dateTo", missingIssuesEmailMessage.getDateTo());
        final String htmlContent = templateEngine.process(MISSING_ISSUES.getTemplateName(), ctx);

        send(missingIssuesEmailMessage.getEmailRecipients(), MISSING_ISSUES.getTopic(), htmlContent);
    }

    @Override
    public void sendUnresolvedIssuesReportMail(final UnresolvedIssuesEmailMessage unresolvedIssuesEmailMessage) throws MessagingException {
        final List<UnresolvedIssueDto> unresolvedIssues = unresolvedIssuesEmailMessage.getUnresolvedIssues();
        LOG.info("Sending unresolved issues report mail to {} containing {} issues, date from = {}, date to = {}.",
                unresolvedIssuesEmailMessage.getEmailRecipients(), unresolvedIssues.size(), unresolvedIssuesEmailMessage.getDateFrom(),
                unresolvedIssuesEmailMessage.getDateTo());

        final Context ctx = new Context();
        ctx.setVariable("unresolvedIssues", unresolvedIssues);
        ctx.setVariable("dateFrom", unresolvedIssuesEmailMessage.getDateFrom());
        ctx.setVariable("dateTo", unresolvedIssuesEmailMessage.getDateTo());
        final String htmlContent = templateEngine.process(UNRESOLVED_ISSUES.getTemplateName(), ctx);

        send(unresolvedIssuesEmailMessage.getEmailRecipients(), UNRESOLVED_ISSUES.getTopic(), htmlContent);
    }

    protected void send(final List<String> recipients, final String subject, final String body) throws MessagingException {
        try {
            final Message message = createMessage();
            message.setFrom(new InternetAddress(emailProperties.getEmailFrom()));
            message.setSubject(subject);
            message.setRecipients(Message.RecipientType.TO, prepareRecipients(recipients));
            message.setContent(body, EMAIL_MIME_TYPE);
            send(message);
        } catch (final MessagingException e) {
            LOG.error("Cannot send email! To: {}, Subject: {}, Body {}", recipients, subject, body, e);
            throw e;
        }
    }

    protected InternetAddress[] prepareRecipients(final List<String> recipients) throws AddressException {
        final StringJoiner stringJoiner = new StringJoiner(",");
        recipients.forEach(stringJoiner::add);
        return InternetAddress.parse(stringJoiner.toString());
    }

    protected void send(final Message message) throws MessagingException {
        Transport.send(message);
    }

    protected MimeMessage createMessage() {
        return new MimeMessage(session);
    }
}
