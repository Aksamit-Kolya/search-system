package com.anika.message.broker.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:message-broker.properties")
public class RabbitMqConfiguration {

    @Value("${rabbit.crawler.queue.name}")
    private String crawlerQueueName;

    @Value("${rabbit.crawler.exchange.name}")
    private String crawlerExchangeName;

    @Value("${rabbit.crawler.routing.key}")
    private String crawlerRoutingKey;

    @Bean
    Queue crawlerQueue() {
        return new Queue(crawlerQueueName, false);
    }

    @Bean
    DirectExchange crawlerExchange() {
        return new DirectExchange(crawlerExchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(crawlerRoutingKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
}
