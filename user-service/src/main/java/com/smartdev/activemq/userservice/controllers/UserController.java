package com.smartdev.activemq.userservice.controllers;

import com.smartdev.activemq.userservice.jms.GreetingProducer;
import com.smartdev.activemq.userservice.jms.GreetingPublisher;
import com.smartdev.activemq.userservice.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private GreetingPublisher greetingPublisher;
    @Autowired
    private GreetingProducer greetingProducer;
    @GetMapping("/greeting")
    public String hello(@RequestParam("name") String name, @RequestParam(value = "type", defaultValue = "queue") String type) {

        if("queue".equalsIgnoreCase(type)) {
            greetingPublisher.publishToQueue(name);
        } else  if("topic".equalsIgnoreCase(type)) {
            greetingPublisher.publishToTopic(name);
        } else {
            greetingPublisher.publishToVirtualTopic(name);

        }
       return "Hello " + name;
    }

    @GetMapping("/greet-message")
    public MessageModel greetMessage(@RequestParam("name") String name) {
        return greetingProducer.greet(name);
    }
}
