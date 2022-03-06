package com.smartdev.activemq.messageservice.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

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
    public void receiveMessageFromTopic(String textMessage) {
        System.out.println(appName + ":: from Topic.Greeting: " + textMessage);
    }


    @JmsListener(destination = "Queue.Greeting")
    public void receiveMessageFromQueue(String textMessage) {

        System.out.println(appName + ":: from Queue.Greeting: " + textMessage);
    }

    @JmsListener(destination = "Queue.Greeting")
    public void receiveMessageFromQueue(String textMessage) {

        System.out.println(appName + ":: from Queue.Greeting: " + textMessage);
    }

}
