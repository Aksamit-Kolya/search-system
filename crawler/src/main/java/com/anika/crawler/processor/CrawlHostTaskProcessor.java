package com.anika.crawler.processor;

import com.anika.message.broker.consumer.processor.CrawlerTaskProcessor;
import com.anika.message.broker.message.CrawlHostTaskMessage;
import org.springframework.stereotype.Component;

@Component
public class CrawlHostTaskProcessor implements CrawlerTaskProcessor<CrawlHostTaskMessage> {

    @Override
    public void process(CrawlHostTaskMessage message) {
    }
}
