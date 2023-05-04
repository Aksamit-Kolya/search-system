package com.anika.message.broker.consumer;

import com.anika.message.broker.consumer.processor.CrawlerTaskProcessor;
import com.anika.message.broker.message.CrawlUrlWithDepthTaskMessage;
import com.anika.message.broker.message.CrawlDomainTaskMessage;
import com.anika.message.broker.message.CrawlUrlTaskMessage;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@RabbitListener(queues = "${rabbit.crawler.queue.name}")
@ConditionalOnBean(CrawlerTaskProcessor.class)
public class CrawlerMessageConsumer {

    @Autowired(required = false)
    private CrawlerTaskProcessor<CrawlUrlTaskMessage> singleUrlTaskProcessor;
    @Autowired(required = false)
    private CrawlerTaskProcessor<CrawlUrlWithDepthTaskMessage> baseUrlTaskProcessor;
    @Autowired(required = false)
    private CrawlerTaskProcessor<CrawlDomainTaskMessage> domainTaskProcessor;

    @RabbitHandler
    public void handleMessage(CrawlUrlTaskMessage message) {
        this.singleUrlTaskProcessor.process(message);
    }

    @RabbitHandler
    public void handleMessage(CrawlUrlWithDepthTaskMessage message) {
        this.baseUrlTaskProcessor.process(message);
    }

    @RabbitHandler
    public void handleMessage(CrawlDomainTaskMessage message) {
        this.domainTaskProcessor.process(message);
    }
}
