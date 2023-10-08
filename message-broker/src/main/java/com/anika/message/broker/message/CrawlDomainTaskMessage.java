package com.anika.message.broker.message;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CrawlDomainTaskMessage(@JsonProperty("domainRoot") String domain) implements Serializable {}
