package com.anika.crawler;

import com.anika.crawler.service.WebCrawlerService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.anika.core.repository"})
@EntityScan(basePackages = {"com.anika.core.entity"})
@ComponentScan(basePackages = {"com.anika.core", "com.anika.crawler", "com.anika.message.broker"})
public class CrawlerApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(CrawlerApplication.class, args);
//        context.getBean(WebCrawlerService.class).crawlUrl("http://localhost:8000/site1.html", 30);
//        context.getBean(WebCrawlerService.class).crawlUrl("http://localhost:8000/seo1.html", 2);
//        context.getBean(WebCrawlerService.class).crawlUrl("http://localhost:8000/seo2.html", 2);
//        context.getBean(WebCrawlerService.class).crawlUrl("http://localhost:8000/seo3.html", 2);
//        context.getBean(WebCrawlerService.class).crawlUrl("http://localhost:8000/seo4.html", 2);
//        context.getBean(WebCrawlerService.class).crawlUrl("http://localhost:8000/seo5.html", 2);
    }
}

