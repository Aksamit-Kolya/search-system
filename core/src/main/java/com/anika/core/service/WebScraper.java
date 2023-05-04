package com.anika.core.service;

import com.anika.core.dto.WebPageInfo;

import java.util.Optional;

public interface WebScraper {
    Optional<WebPageInfo> scrape(String url);
}
