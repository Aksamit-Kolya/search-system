package com.anika.core.filter;

import java.util.List;
import java.util.stream.Collectors;

public class PunctuationFilter {

    public List<String> filter(List<String> tokens) {
        return tokens.stream()
                .filter(token -> !isPunctuation(token))
                .collect(Collectors.toList());
    }

    public Boolean isPunctuation(String word) {
        return  word.matches("\\p{Punct}");
    }
}