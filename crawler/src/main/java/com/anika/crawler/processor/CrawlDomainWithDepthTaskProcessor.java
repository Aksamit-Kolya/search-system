package com.anika.crawler.processor;

import com.anika.crawler.service.WebCrawlerService;
import com.anika.message.broker.consumer.processor.CrawlerTaskProcessor;
import com.anika.message.broker.message.CrawlDomainWithDepthTaskMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawlDomainWithDepthTaskProcessor implements CrawlerTaskProcessor<CrawlDomainWithDepthTaskMessage> {

    private final WebCrawlerService webCrawlerService;

    @Autowired
    public CrawlDomainWithDepthTaskProcessor(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    @Override
    public void process(CrawlDomainWithDepthTaskMessage message) {
        webCrawlerService.crawlUrl(message.domainRoot(), message.depth());
    }
}
