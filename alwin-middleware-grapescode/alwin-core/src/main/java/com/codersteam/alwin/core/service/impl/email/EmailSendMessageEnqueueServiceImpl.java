package com.codersteam.alwin.core.service.impl.email;

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;
import com.codersteam.alwin.core.api.service.email.EmailSendMessageEnqueueService;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class EmailSendMessageEnqueueServiceImpl implements EmailSendMessageEnqueueService {

    @Resource(mappedName = "java:jboss/jms/queue/email")
    private Queue email;

    private final JMSContext context;

    @Inject
    public EmailSendMessageEnqueueServiceImpl(@JMSConnectionFactory("java:/JmsXA") final JMSContext context) {
        this.context = context;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void enqueueMissingIssuesEmail(final MissingIssuesEmailMessage missingIssuesEmailMessage) {
        context.createProducer().send(email, missingIssuesEmailMessage);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void enqueueUnresolvedIssuesEmail(final UnresolvedIssuesEmailMessage unresolvedIssuesEmailMessage) {
        context.createProducer().send(email, unresolvedIssuesEmailMessage);
    }
}
