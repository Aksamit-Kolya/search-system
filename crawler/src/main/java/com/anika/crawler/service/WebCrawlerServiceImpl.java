package com.anika.crawler.service;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    public void crawlUrl(String url, int maxDepth) {
        Set<String> links = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        int currentDepth = 0;
        queue.offer(url);
        links.add(url);

        while (!queue.isEmpty() && currentDepth <= maxDepth) {
            String currentURL = queue.poll();
            System.out.println("Parsing page: " + currentURL);

            try {
                Document document = Jsoup.connect(currentURL).get();
                System.out.println("Content: " + document.body().text());
            } catch (IOException e) {
                System.err.println("For '" + url + "': " + e.getMessage());
            }

            currentDepth++;
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
