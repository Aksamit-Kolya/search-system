package com.anika.web.service;

import com.anika.message.broker.producer.CrawlerMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawlerServiceImpl implements CrawlerService{

    private final CrawlerMessageProducer crawlerMessageProducer;

    @Autowired
    public CrawlerServiceImpl(CrawlerMessageProducer crawlerMessageProducer) {
        this.crawlerMessageProducer = crawlerMessageProducer;
    }

    @Override
    public void sendUrlToCrawler(String url) {
        crawlerMessageProducer.sendUrlToCrawler(url);
    }

    @Override
    public void sendUrlToCrawler(String url, Integer depth) {
        crawlerMessageProducer.sendUrlToCrawler(url, depth);
    }

    @Override
    public void sendDomainToCrawler(String domain) {
        crawlerMessageProducer.sendDomainToCrawler(domain);
    }

    @Override
    public void sendDomainWithDepthToCrawler(String domain, Integer depth) {
        crawlerMessageProducer.sendDomainWithDepthToCrawler(domain, depth);
    }
}
