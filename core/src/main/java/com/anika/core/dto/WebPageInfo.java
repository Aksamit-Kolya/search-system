package com.anika.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebPageInfo {

    private String url;
    private String title;
    private String description;
    private String keywrods;
    private String content;
    private List<String> links;
    private String language;
}
