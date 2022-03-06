package com.smartdev.activemq.userservice.gateway;

import com.smartdev.activemq.userservice.model.GreetingModel;
import com.smartdev.activemq.userservice.model.MessageModel;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "requests")
public interface MessageGateway {
     MessageModel greet(GreetingModel greetingModel);
}
