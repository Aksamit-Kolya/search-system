package com.anika.message.broker.consumer;

import com.anika.message.broker.consumer.processor.CrawlerBaseUrlTaskProcessor;
import com.anika.message.broker.consumer.processor.CrawlerHostNameTaskProcessor;
import com.anika.message.broker.consumer.processor.CrawlerSingleUrlTaskProcessor;
import com.anika.message.broker.consumer.processor.MessageProcessor;
import com.anika.message.broker.message.CrawlerBaseUrlTaskMessage;
import com.anika.message.broker.message.CrawlerHostNameTaskMessage;
import com.anika.message.broker.message.CrawlerSingleUrlTaskMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues = "${rabbit.crawler.queue.name}")
public class CrawlerMessageConsumer {

    private MessageProcessor<CrawlerSingleUrlTaskMessage> singleUrlTaskProcessor;
    private MessageProcessor<CrawlerBaseUrlTaskMessage> baseUrlTaskProcessor;
    private MessageProcessor<CrawlerHostNameTaskMessage> hostNameTaskProcessor;

    @Autowired
    public void setSingleUrlTaskProcessor(CrawlerSingleUrlTaskProcessor processor) {
        this.singleUrlTaskProcessor = processor;
    }

    @Autowired
    public void setBaseUrlTaskProcessor(CrawlerBaseUrlTaskProcessor processor) {
        this.baseUrlTaskProcessor = processor;
    }

    @Autowired
    public void setHostNameTaskProcessor(CrawlerHostNameTaskProcessor processor) {
        this.hostNameTaskProcessor = processor;
    }

    @RabbitHandler
    public void handleMessage(CrawlerSingleUrlTaskMessage message) {
        this.singleUrlTaskProcessor.process(message);
    }

    @RabbitHandler
    public void handleMessage(CrawlerBaseUrlTaskMessage message) {
        this.baseUrlTaskProcessor.process(message);
    }

    @RabbitHandler
    public void handleMessage(CrawlerHostNameTaskMessage message) {
        this.hostNameTaskProcessor.process(message);
    }
}
