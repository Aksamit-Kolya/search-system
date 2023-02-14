package com.anika.message.broker.message;

public class CrawlerHostNameTaskMessage {

    private final String hostName;

    public CrawlerHostNameTaskMessage(String hostName) {
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }
}
