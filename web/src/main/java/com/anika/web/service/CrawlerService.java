package com.anika.web.service;

public interface CrawlerService {
    void sendUrlToCrawler(String url);

    void sendUrlToCrawler(String url, Integer depth);

    void sendDomainToCrawler(String domain);
}
