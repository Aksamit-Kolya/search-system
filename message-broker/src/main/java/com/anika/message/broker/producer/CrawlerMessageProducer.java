package com.anika.message.broker.producer;

import com.anika.message.broker.message.CrawlUrlWithDepthTaskMessage;
import com.anika.message.broker.message.CrawlDomainTaskMessage;
import com.anika.message.broker.message.CrawlUrlTaskMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CrawlerMessageProducer {

    @Value("${rabbit.crawler.exchange.name}")
    private String exchange;

    @Value("${rabbit.crawler.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CrawlerMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUrlToCrawler(String url) {
        rabbitTemplate.convertAndSend(exchange, routingKey, new CrawlUrlTaskMessage(url));
    }

    public void sendUrlToCrawler(String url, Integer depth) {
        rabbitTemplate.convertAndSend(exchange, routingKey, new CrawlUrlWithDepthTaskMessage(url, depth));
    }

    public void sendDomainToCrawler(String domain) {
        rabbitTemplate.convertAndSend(exchange, routingKey, new CrawlDomainTaskMessage(domain));
    }
}
