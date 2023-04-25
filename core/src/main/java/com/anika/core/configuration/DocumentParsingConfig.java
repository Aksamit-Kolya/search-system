package com.anika.core.configuration;

import com.anika.core.filter.PunctuationFilter;
import com.anika.core.filter.StopWordFilter;
import opennlp.tools.lemmatizer.Lemmatizer;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Configuration
public class DocumentParsingConfig {

    @Bean
    public Tokenizer tokenizer() throws IOException {
        Resource resource = new ClassPathResource("openNLP/en-token.bin");
        try (InputStream modelIn = resource.getInputStream()) {
            TokenizerModel model = new TokenizerModel(modelIn);
            return new TokenizerME(model);
        }
    }

    @Bean
    public POSTaggerME tagger() throws IOException {
        Resource resource = new ClassPathResource("openNLP/en-pos-maxent.bin");
        try (InputStream modelIn = resource.getInputStream()) {
            POSModel model = new POSModel(modelIn);
            return new POSTaggerME(model);
        }
    }

    @Bean
    public Lemmatizer lemmatizer() throws IOException {
        Resource resource = new ClassPathResource("openNLP/en-lemmatizer.bin");
        try (InputStream modelIn = resource.getInputStream()) {
            LemmatizerModel model = new LemmatizerModel(modelIn);
            return new LemmatizerME(model);
        }
    }

    @Bean
    public StopWordFilter stopwordFilter() throws IOException {
        ClassPathResource stopWordsResource = new ClassPathResource("openNLP/en-stop-words.txt");
        Set<String> stopWords = new HashSet<>();

        try (InputStream inputStream = stopWordsResource.getInputStream();
             Scanner scanner = new Scanner(inputStream)) {

            while (scanner.hasNextLine()) {
                String stopWord = scanner.nextLine().trim();
                if (!stopWord.isEmpty()) {
                    stopWords.add(stopWord);
                }
            }
        }

        return new StopWordFilter(stopWords);
    }

    @Bean
    public PunctuationFilter punctuationFilter() {
        return new PunctuationFilter();
    }
}
