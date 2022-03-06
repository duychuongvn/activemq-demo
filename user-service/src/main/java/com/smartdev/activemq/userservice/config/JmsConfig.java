package com.smartdev.activemq.userservice.config;

import com.smartdev.activemq.userservice.model.MessageModel;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.jms.JmsOutboundGateway;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
@EnableIntegration
@IntegrationComponentScan
@ImportAutoConfiguration(classes = {
        JmsAutoConfiguration.class,
        ActiveMQAutoConfiguration.class
})
public class JmsConfig {



    /**
     * We can configure ActiveMQ to redeliver failed messages automatically. The
     * configuration can contain properties such as a delay or the number of retries.
     * However, for testing purposes in this demo, we want to turn of the default
     * redelivery policy and don't do any redelivery.
     * <p>
     * Note that for production a redelivery policy might be very useful!
     *
     * @return Turned off redelivery policy for demo purposes
     */
//    @Bean
//    public RedeliveryPolicy redeliveryPolicy() {
//        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
//        redeliveryPolicy.setMaximumRedeliveries(0);
//
//        // Note: We must set the queue name here. This name will be part of the error message
//        // shown in ActiveMQ for a failed message. If we don't set the queue name, we cannot
//        // see which listener has failed.
//        redeliveryPolicy.setQueue("Consumer.user-service.VirtualTopic.Events");
//        return redeliveryPolicy;
//    }

    @Bean
    public MessageChannel requests() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "requests")
    public JmsOutboundGateway jmsGateway(ActiveMQConnectionFactory activeMQConnectionFactory) {
        JmsOutboundGateway gateway = new JmsOutboundGateway();
        gateway.setConnectionFactory(activeMQConnectionFactory);
        gateway.setRequestDestinationName("Queue.messageService.greeting");
        gateway.setReplyDestinationName("Queue.userService.greeting");
        gateway.setCorrelationKey("JMSCorrelationID");
        gateway.setSendTimeout(100L);
        gateway.setReceiveTimeout(100L);
        return gateway;
    }

    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.activemq.user}") String username,
                                               @Value("${spring.activemq.password}") String password,
                                               @Value("${spring.activemq.broker-url}") String brokerUrl) {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, brokerUrl);
//        connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        connectionFactory.setWatchTopicAdvisories(false);
        return connectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory topicFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
//    @Bean
//    public JmsListenerContainerFactory queueFactory(ConnectionFactory connectionFactory) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(false);
//        return factory;
//    }
}
