package com.anika.crawler.service;

public interface WebCrawlerService {
    void crawlUrl(String url, int maxDepth);
    void crawlUrl(String url);
}
