package com.smartdev.activemq.userservice.jms;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class GreetingPublisher {
    private final JmsTemplate jmsTemplate;
    public void publishToVirtualTopic(String message) {
        log.info("Send event. [destination={}, message={}]", "VirtualTopic.Greeting", message);
        jmsTemplate.convertAndSend(new ActiveMQTopic("VirtualTopic.Greeting"), message);
    }

    public void publishToTopic(String message) {
        log.info("Send event. [destination={}, message={}]", "Topic.Greeting", message);
        jmsTemplate.convertAndSend(new ActiveMQTopic("Topic.Greeting"), message);
    }

    public void publishToQueue(String message) {
        log.info("Send event. [destination={}, message={}]", "Queue.Greeting", message);
        jmsTemplate.convertAndSend("Queue.Greeting", message);
    }
}
