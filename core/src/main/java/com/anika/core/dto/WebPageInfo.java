package com.anika.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class WebPageInfo {

    private String url;
    private String title;
    private String description;
    private String keywrods;
    private String content;
    private Set<String> links;
    private String language;
}
