package com.anika.web.controller;

import com.anika.web.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //@CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/crawl/url/depth")
    public ResponseEntity<String> sendUrlWithDepthToCrawler(@RequestParam(value = "url", required = true) String url,
                                                            @RequestParam(value = "depth", required = true) Integer depth) {
        crawlerService.sendUrlToCrawler(url, depth);
        return ResponseEntity.ok("URL and depth sent to the crawler successfully");
    }

    //@CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/crawl/domain")
    public ResponseEntity<String> sendDomainToCrawler(@RequestParam(value = "domain", required = true) String domain) {
        crawlerService.sendDomainToCrawler(domain);
        return ResponseEntity.ok("Host Name sent to the crawler successfully");
    }

    //@CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/crawl/domain/depth")
    public ResponseEntity<String> sendDomainWithDepthToCrawler(@RequestParam(value = "domainRoot", required = true) String domain,
                                                               @RequestParam(value = "depth", required = true) Integer depth) {
        crawlerService.sendDomainWithDepthToCrawler(domain, depth);
        return ResponseEntity.ok("Host Name sent to the crawler successfully");
    }
}
