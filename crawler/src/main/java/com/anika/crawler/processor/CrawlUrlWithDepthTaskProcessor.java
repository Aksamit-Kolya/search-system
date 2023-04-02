package com.anika.crawler.processor;

import com.anika.crawler.service.WebCrawlerService;
import com.anika.message.broker.consumer.processor.CrawlerTaskProcessor;
import com.anika.message.broker.message.CrawlUrlWithDepthTaskMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawlUrlWithDepthTaskProcessor implements CrawlerTaskProcessor<CrawlUrlWithDepthTaskMessage> {

    private final WebCrawlerService webCrawlerService;

    @Autowired
    public CrawlUrlWithDepthTaskProcessor(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    @Override
    public void process(CrawlUrlWithDepthTaskMessage message) {
        webCrawlerService.crawlUrl(message.startUrl(), message.depth());
    }
}
