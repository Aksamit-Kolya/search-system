package com.anika.message.broker.message;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CrawlDomainWithDepthTaskMessage(@JsonProperty("domainRoot") String domainRoot,
                                              @JsonProperty("depth") int depth) implements Serializable {}
