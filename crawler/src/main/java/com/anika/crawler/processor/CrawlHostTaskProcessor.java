package com.anika.crawler.processor;

import com.anika.message.broker.consumer.processor.CrawlerTaskProcessor;
import com.anika.message.broker.message.CrawlDomainTaskMessage;
import org.springframework.stereotype.Component;

@Component
public class CrawlHostTaskProcessor implements CrawlerTaskProcessor<CrawlDomainTaskMessage> {

    @Override
    public void process(CrawlDomainTaskMessage message) {
    }
}
