package com.anika.crawler.service;

import com.anika.core.repository.DocumentRepository;
import com.anika.core.repository.KeywordRepository;
import com.anika.core.service.DocumentService;
import com.anika.core.service.TextProcessingService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final DocumentService documentService;

    public WebCrawlerServiceImpl(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void crawlUrl(String startUrl, int maxDepth) {
        Set<String> visitedUrls = new HashSet<>();
        Queue<String> urlsToVisit = new LinkedList<>();
        urlsToVisit.add(startUrl);

        for (int depth = 0; depth <= maxDepth; depth++) {
            int currentLevelSize = urlsToVisit.size();
            System.out.println("### depth: " + depth + "\ncurrent level size: " + currentLevelSize);
            System.out.println("### urlsToVisit: " + urlsToVisit);
            for (int i = 0; i < currentLevelSize; i++) {
                String currentUrl = urlsToVisit.poll();

                if (!visitedUrls.contains(currentUrl)) {
                    visitedUrls.add(currentUrl);

                    System.out.println("Crawling: " + currentUrl);
                    try {
                        Document document = Jsoup.connect(currentUrl).get();
                        System.out.println("URL: " + currentUrl);
                        System.out.println("Lang: " + document.select("html").first().attr("lang"));
                        // Process the page content here
                        String pageContent = document.body().text();
                        String pageLanguage = document.select("html").first().attr("lang");
                        //System.out.println(pageContent);

                        Elements links = document.select("a[href^=http], a[href^=https]");
                        for (Element link : links) {
                            String url = link.absUrl("href");
                            if (!visitedUrls.contains(url)) {
                                urlsToVisit.add(url);
                            }
                        }

                        if("en".equals(pageLanguage)) {
                            documentService.saveDocument(currentUrl, pageContent);
                        }
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            }
        }
    }

    public void crawlUrl(String url) {
        System.out.println("Parsing page: " + url);

        try {
            Document document = Jsoup.connect(url).get();
            System.out.println("Content: " + document.body().text());
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }
    }
}
