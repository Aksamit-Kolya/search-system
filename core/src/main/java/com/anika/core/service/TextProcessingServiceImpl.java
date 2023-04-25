package com.anika.core.service;

import com.anika.core.filter.PunctuationFilter;
import com.anika.core.filter.StopWordFilter;
import opennlp.tools.lemmatizer.Lemmatizer;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import org.springframework.stereotype.Service;
import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TextProcessingServiceImpl implements TextProcessingService{

    private final Lemmatizer lemmatizer;
    private final POSTaggerME tagger;
    private final Tokenizer tokenizer;
    private final StopWordFilter stopWordFilter;
    private final PunctuationFilter punctuationFilter;

    public TextProcessingServiceImpl(Lemmatizer lemmatizer,
                           POSTaggerME tagger,
                           Tokenizer tokenizer, 
                           StopWordFilter stopWordFilter,
                           PunctuationFilter punctuationFilter) {
        this.tokenizer = tokenizer;
        this.tagger = tagger;
        this.lemmatizer = lemmatizer;
        this.stopWordFilter = stopWordFilter;
        this.punctuationFilter = punctuationFilter;
    }
    @Override
    public Map<String, Integer> calculateWordOccurrences(List<String> words) {
        return words.stream()
                .filter(word -> !punctuationFilter.isPunctuation(word))
                .filter(word -> !stopWordFilter.isStopWord(word))
                .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum));
    }

    @Override
    public List<String> extractLemmas(String text) {
        String[] tokens = tokenizer.tokenize(text);
        String[] tags = tagger.tag(tokens);
        String[] lemmas = lemmatizer.lemmatize(tokens, tags);

        return Arrays.asList(lemmas);
    }

    @Override
    public String extractTextFromHtml(Document document) {
        return document.text();
    }

    @Override
    public List<String> extractLinksFromHtml(Document document) {
        return document.select("a")
                .stream()
                .map(element -> element.attr("href"))
                .collect(Collectors.toList());
    }

}
