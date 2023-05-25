package com.smartdev.activemq.userservice.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdev.activemq.GreetingModel;
import com.smartdev.activemq.MsData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class GreetingReceiver {

    @Value("${application.name}")
    private String appName;
    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }

    @JmsListener(destination = "Consumer.messageService.VirtualTopic.Greeting")
    public void receiveMessageFromVirtualTopic(String textMessage) {

        System.out.println(appName + ":: from Consumer.messageService.VirtualTopic.Greeting: " + textMessage);
    }

    @JmsListener(destination = "Topic.Greeting", containerFactory = "topicFactory")
    public void receiveMessageFromTopic(VerypayMessage<GreetingModel> message) throws JMSException, JsonProcessingException {
//        String text = textMessage.getText();
//        System.out.println(text);
//        ObjectMapper mapper = new ObjectMapper();

//        VerypayMessage<GreetingModel> message = mapper.readValue(text, new TypeReference<VerypayMessage<GreetingModel>>(){});
        System.out.println(appName + ":: from Topic.Greeting: " + message.getBody().getName());
    }


    @JmsListener(destination = "Queue.Greeting")
    public void receiveMessageFromQueue(String textMessage) {

        System.out.println(appName + ":: from Queue.Greeting: " + textMessage);
    }

}
