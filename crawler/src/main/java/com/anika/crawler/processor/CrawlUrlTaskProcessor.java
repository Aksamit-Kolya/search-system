package com.anika.crawler.processor;

import com.anika.crawler.service.WebCrawlerService;
import com.anika.message.broker.consumer.processor.CrawlerTaskProcessor;
import com.anika.message.broker.message.CrawlUrlTaskMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawlUrlTaskProcessor implements CrawlerTaskProcessor<CrawlUrlTaskMessage> {

    private final WebCrawlerService webCrawlerService;

    @Autowired
    public CrawlUrlTaskProcessor(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    @Override
    public void process(CrawlUrlTaskMessage message) {
        webCrawlerService.crawlUrl(message.url());
    }
}
