package com.codersteam.alwin.integration.common;

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Klasa bazowa dla testów integracyjnych wykorzystujących kolejki
 *
 * @author Tomasz Sliwinski
 */
public class JmsCoreTestBase extends CoreTestBase {

    @Resource(mappedName = "ConnectionFactory", name = "ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:jboss/jms/queue/email", name = "java:jboss/jms/queue/email")
    private Queue emailQueue;

    protected void sendMessageOnEmailQueue(final MissingIssuesEmailMessage messageObject) throws JMSException {
        final Connection connection = connectionFactory.createConnection();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final MessageProducer producer = session.createProducer(emailQueue);

        final ObjectMessage objectMessage = session.createObjectMessage(messageObject);
        producer.send(objectMessage);
        session.close();
        connection.close();
    }
}
