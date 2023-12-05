package com.anika.crawler.service;

import com.anika.core.dto.WebPageInfo;
import com.anika.core.entity.Document;
import com.anika.core.repository.DocumentDocumentRepository;
import com.anika.core.service.DocumentService;
import com.anika.core.service.WebScraper;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final static Set<String> accessibleDocumentLanguages = new HashSet<>() {{
        add("en");
        add("ru");
    }};
    private final DocumentService documentService;
    private final WebScraper webScraper;


    public WebCrawlerServiceImpl(DocumentService documentService, WebScraper webScraper) {
        this.documentService = documentService;
        this.webScraper = webScraper;
    }

    @Override
    public void crawlUrl(String url) {
        crawlUrl(url, 0);
    }

    @Override
    public void crawlUrl(String startUrl, int maxDepth) {
        crawl(startUrl, maxDepth, url -> true);
    }

    @Override
    public void crawlDomain(String domain) {

    }

    @Override
    public void crawlDomain(String root, int maxDepth) {
        String domainName = getDomainName(root);
        crawl(root, maxDepth, url -> getDomainName(url).equals(domainName));
    }

    record PageToVisit(String url, Document parent) {
    }

    private void crawl(String startUrl, int maxDepth, Predicate<String> filter) {
        Set<String> visitedUrls = new HashSet<>();
        Queue<PageToVisit> pagesToVisit = new LinkedList<>();
        pagesToVisit.offer(new PageToVisit(startUrl, null));

        for (int depth = 0; depth <= maxDepth && !pagesToVisit.isEmpty(); ++depth) {
            int currentLevelSize = pagesToVisit.size();
            System.out.println("depth: " + depth + "\ncurrentLevelSize: " + currentLevelSize);
            for (int i = 0; i < currentLevelSize; ++i) {
                PageToVisit currentPageToVisit = pagesToVisit.poll();
                Document currentParent = currentPageToVisit.parent;
                String currentUrl = currentPageToVisit.url;

                visitedUrls.add(currentUrl);
                WebPageInfo pageInfo = webScraper.scrape(currentUrl)
                        .filter(page -> !page.getContent().isBlank())
                        //.filter(page -> page.getLanguage().toLowerCase().contains("en"))
                        .orElse(null);

                System.out.println("currentUrl: " + currentUrl + "\nlinksNumber: " + pageInfo.getLinks().size());

                if (pageInfo != null) {
                    Document newDocument = documentService.saveDocument(pageInfo);
                    if(currentParent != null) documentService.createDocumentLink(currentParent, newDocument);
                    documentService.createDocumentLinks(newDocument, pageInfo.getLinks());
                    pageInfo.getLinks().stream()
                            .filter(filter.and(url -> !visitedUrls.contains(url)))
                            .map(url -> new PageToVisit(url, newDocument))
                            .forEach(pagesToVisit::offer);
                }
            }
        }
    }

    private String getDomainName(String url) {
        String domainName = "";
        try {
            URI uri = new URI(url);
            String hostname = uri.getHost();
            if (hostname != null) {
                domainName = hostname.startsWith("www.") ? hostname.substring(4) : hostname;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return domainName;
    }
}
