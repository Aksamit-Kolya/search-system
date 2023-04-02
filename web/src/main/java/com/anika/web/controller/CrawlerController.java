package com.anika.web.controller;

import com.anika.web.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crawler")
public class CrawlerController {

    private final CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @PostMapping("/crawl/url")
    public ResponseEntity<String> sendUrlToCrawler(@RequestParam(value = "url", required = true) String url) {
        crawlerService.sendUrlToCrawler(url);
        return ResponseEntity.ok("URL sent to the crawler successfully");
    }

    @PostMapping("/crawl/url/depth")
    public ResponseEntity<String> sendUrlToCrawlerWithDepth(@RequestParam(value = "url", required = true) String url,
                                                            @RequestParam(value = "depth", required = true) Integer depth) {
        crawlerService.sendUrlToCrawler(url, depth);
        return ResponseEntity.ok("URL and depth sent to the crawler successfully");
    }

    @PostMapping("/crawl/host")
    public ResponseEntity<String> sendHostNameToCrawler(@RequestParam(value = "host", required = true) String host) {
        crawlerService.sendHostNameToCrawler(host);
        return ResponseEntity.ok("Host Name sent to the crawler successfully");
    }
}
