package com.anika.core.filter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StopWordFilter {

    private Set<String> stopWords;

    public StopWordFilter(Set<String> stopWords) {
        this.stopWords = stopWords;
    }

    public List<String> filter(List<String> tokens) {
        return tokens.stream()
                .filter(token -> !isStopWord(token))
                .collect(Collectors.toList());
    }

    public boolean isStopWord(String word) {
        return stopWords.contains(word.toLowerCase());
    }
}
