package com.anika.message.broker.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CrawlUrlTaskMessage(@JsonProperty("url") String url) implements Serializable {}
