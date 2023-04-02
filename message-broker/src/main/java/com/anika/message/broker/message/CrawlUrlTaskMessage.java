package com.anika.message.broker.message;

public class CrawlerSingleUrlTaskMessage {

    private final String url;

    public CrawlerSingleUrlTaskMessage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
