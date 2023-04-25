package com.anika.core.service;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public interface TextProcessingService {
    Map<String, Integer> calculateWordOccurrences(List<String> words);
    List<String> extractLemmas(String text);
    String extractTextFromHtml(Document document);
    List<String> extractLinksFromHtml(Document document);
}
