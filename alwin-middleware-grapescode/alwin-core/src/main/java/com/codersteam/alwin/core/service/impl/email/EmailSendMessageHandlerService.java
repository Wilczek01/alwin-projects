package com.codersteam.alwin.core.service.impl.email;

import com.codersteam.alwin.core.api.model.email.EmailData;
import com.codersteam.alwin.core.api.model.email.EmailType;
import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;
import com.codersteam.alwin.core.api.service.email.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import static java.lang.String.format;

/**
 * @author Tomasz Sliwinski
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/jms/queue/email"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
public class EmailSendMessageHandlerService implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private EmailSenderService emailSenderService;

    @SuppressWarnings("unused")
    public EmailSendMessageHandlerService() {
        // For deployment
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public EmailSendMessageHandlerService(final EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @Override
    public void onMessage(final Message message) {
        if (!isObjectMessage(message)) {
            throw new IllegalArgumentException(format("Unsupported message type %s", message));
        }

        final ObjectMessage objectHolder = (ObjectMessage) message;

        try {
            final Serializable messageObject = objectHolder.getObject();
            checkMessageObjectType(messageObject);
            sendEmail(messageObject);
        } catch (final JMSException e) {
            LOG.error("Cannot retrieve message {}", message);
        } catch (final MessagingException e) {
            LOG.error("Cannot send email message for {}", objectHolder);
            throw new RuntimeException("Cannot send email!");
        }
    }

    private void sendEmail(final Serializable message) throws MessagingException {
        final EmailType emailType = getEmailType((EmailData) message);
        switch (emailType) {
            case MISSING_ISSUES:
                emailSenderService.sendMissingIssuesReportMail((MissingIssuesEmailMessage) message);
                break;
            case UNRESOLVED_ISSUES:
                emailSenderService.sendUnresolvedIssuesReportMail((UnresolvedIssuesEmailMessage) message);
                break;
            default:
                throw new IllegalArgumentException(format("Unknown email type: %s", emailType));
        }
    }

    private EmailType getEmailType(final EmailData message) {
        return message.getEmailType();
    }

    private boolean isObjectMessage(final Message message) {
        return message instanceof ObjectMessage;
    }

    private void checkMessageObjectType(final Serializable messageObject) {
        if (messageObject instanceof EmailData) {
            return;
        }
        throw new IllegalArgumentException(format("Unsupported message object type %s", messageObject));
    }
}
