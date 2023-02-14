package com.anika.web.service;

import com.anika.message.broker.producer.CrawlerMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void sendHostNameToCrawler(String hostName) {
        crawlerMessageProducer.sendHostNameToCrawler(hostName);
    }
}
