package com.anika.message.broker.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CrawlUrlWithDepthTaskMessage(@JsonProperty("startUrl") String startUrl,
                                           @JsonProperty("depth") int depth) implements Serializable {}
