package com.smartdev.activemq.userservice.jms;

import com.smartdev.activemq.userservice.model.GreetingModel;
import com.smartdev.activemq.userservice.model.MessageModel;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.Objects;
import java.util.UUID;

@Component
public class GreetingProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;
    public MessageModel greet(String name) {
        jmsTemplate.setReceiveTimeout(1000L);
        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);

        Session session = null;
        try {
            session = Objects.requireNonNull(jmsMessagingTemplate.getConnectionFactory()).createConnection()
                    .createSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage objectMessage = session.createObjectMessage(new GreetingModel(name));

            objectMessage.setJMSCorrelationID(UUID.randomUUID().toString());
            objectMessage.setJMSReplyTo(new ActiveMQQueue("Queue.userService.greeting"));
            objectMessage.setJMSExpiration(1000L);
            objectMessage.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);

            return jmsMessagingTemplate.convertSendAndReceive(new ActiveMQQueue("Queue.messageService.greeting"),
                    objectMessage, MessageModel.class);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }
}
