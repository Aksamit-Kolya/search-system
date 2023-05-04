package com.anika.crawler.service;

public interface WebCrawlerService {
    void crawlUrl(String url);
    void crawlUrl(String url, int maxDepth);
    void crawlDomain(String domain);
    void crawlDomain(String domain, int maxDepth);
}
