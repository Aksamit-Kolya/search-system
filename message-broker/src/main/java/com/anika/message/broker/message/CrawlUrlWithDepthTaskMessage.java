package com.anika.message.broker.message;

public class CrawlerBaseUrlTaskMessage {

    private final String startUrl;
    private final int depth;

    public CrawlerBaseUrlTaskMessage(String startUrl, int depth) {
        this.startUrl = startUrl;
        this.depth = depth;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public int getDepth() {
        return depth;
    }
}
