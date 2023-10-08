package com.anika.core.service;

import com.anika.core.dto.WebPageInfo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JsoupWebScraper implements WebScraper{
    @Override
    public Optional<WebPageInfo> scrape(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return Optional.of(buildWebPageInfo(document, url));
        } catch (IOException e) {
            System.out.println("Failed to scrape page: " + url + "\nException: " + e);
            //throw new WebScraperException("Failed to scrape page: " + url, e);
            return Optional.empty();
        }
    }

    private WebPageInfo buildWebPageInfo(Document document, String url) {
        String pageTitle = document.title();
        String pageDescription = document.select("meta[name=description]").attr("content");
        String pageKeywords = document.select("meta[name=keywords]").attr("content");
        String pageContent = document.body().text();
        Elements links = document.select("a[href^=http], a[href^=https], a[href^=\"/\"]");
        String pageLanguage = getPageLanguage(document);

        return WebPageInfo.builder()
                .url(url)
                .title(pageTitle)
                .description(pageDescription)
                .content(pageContent)
                .keywrods(pageKeywords)
                .links(getLinkUrls(links))
                .language(pageLanguage)
                .build();
    }

    private String getPageLanguage(Document document) {
        Element htmlElement = document.selectFirst("html");
        return htmlElement != null && htmlElement.hasAttr("lang") ? htmlElement.attr("lang") : "";
    }

    private List<String> getLinkUrls(Elements links) {
        return links.stream().map(link -> link.absUrl("href"))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }
}
