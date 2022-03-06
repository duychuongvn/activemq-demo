package com.smartdev.activemq.messageservice.jms;

import com.smartdev.activemq.userservice.model.GreetingModel;
import com.smartdev.activemq.userservice.model.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
@Slf4j
public class MessageReceiver implements SessionAwareMessageListener<Message> {

    @Override
    @JmsListener(destination = "Queue.messageService.greeting")
    public void onMessage(Message message, Session session) throws JMSException {
        GreetingModel greetingModel = (GreetingModel) ((ActiveMQObjectMessage) message).getObject();

//        GreetingModel greetingModel =message.getBody(GreetingModel.class);
        log.info("Queue.messageService.greeting: {}", greetingModel.getName());
        MessageModel messageModel = new MessageModel();
        messageModel.setName( greetingModel.getName());
        messageModel.setContent("Hello " +  greetingModel.getName() + " from Message Service");

        final ObjectMessage responseMessage = new ActiveMQObjectMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        responseMessage.setObject(messageModel);

        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        producer.send(responseMessage);
    }

}
