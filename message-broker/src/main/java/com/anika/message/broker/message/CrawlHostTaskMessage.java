package com.anika.message.broker.message;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CrawlHostTaskMessage(@JsonProperty("hostName") String hostName) implements Serializable {}
