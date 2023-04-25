package com.anika.core.service;

import com.anika.core.entity.Document;
import com.anika.core.entity.DocumentKeyword;
import com.anika.core.entity.Keyword;
import com.anika.core.filter.PunctuationFilter;
import com.anika.core.filter.StopWordFilter;
import com.anika.core.repository.DocumentKeywordRepository;
import com.anika.core.repository.DocumentRepository;
import com.anika.core.repository.KeywordRepository;
import jakarta.transaction.Transactional;
import opennlp.tools.lemmatizer.Lemmatizer;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final KeywordRepository keywordRepository;
    private final DocumentKeywordRepository documentKeywordRepository;
    private final TextProcessingService textProcessingService;

    public DocumentService(DocumentRepository documentRepository,
                           KeywordRepository keywordRepository,
                           DocumentKeywordRepository documentKeywordRepository,
                           TextProcessingService textProcessingService) {
        this.documentRepository = documentRepository;
        this.keywordRepository = keywordRepository;
        this.documentKeywordRepository = documentKeywordRepository;
        this.textProcessingService = textProcessingService;
    }

    public void saveDocument(String url, String content) {
        List<String> lemmas = textProcessingService.extractLemmas(content);
        Map<String, Integer> wordOccurrences = textProcessingService.calculateWordOccurrences(lemmas);
        insertKeywordsForDocument(url, wordOccurrences);
    }

    public void insertKeywordsForDocument(String url, Map<String, Integer> keywordMap) {
        Document document = documentRepository.findByUrl(url)
                .orElseGet(() -> documentRepository.save(new Document(url)));

        // Calculate the number of words in the document
        Integer tokensNumber = Math.toIntExact(keywordMap.values().stream().mapToLong(Integer::intValue).sum());

        // Set the tokensNumber field in the Document entity
        document.setWordCount(tokensNumber);

        Map<String, Keyword> existingKeywords = keywordRepository.findByWordIn(keywordMap.keySet())
                .stream()
                .collect(Collectors.toMap(Keyword::getWord, Function.identity()));

        Map<Keyword, DocumentKeyword> documentKeywordMap = documentKeywordRepository.findByDocument(document)
                .stream()
                .collect(Collectors.toMap(DocumentKeyword::getKeyword, Function.identity()));

        List<DocumentKeyword> documentKeywords = new ArrayList<>();
        List<Keyword> newKeywords = new ArrayList<>();

        keywordMap.forEach((word, frequency) -> {
            Keyword keywordObj = existingKeywords.get(word);

            if (keywordObj != null) {
                DocumentKeyword documentKeyword = documentKeywordMap.get(keywordObj);

                if (documentKeyword != null) {
                    documentKeyword.setFrequency(frequency);
                    documentKeywords.add(documentKeyword);
                } else {
                    DocumentKeyword newDocumentKeyword = new DocumentKeyword(document, keywordObj, frequency);
                    documentKeywords.add(newDocumentKeyword);
                    documentKeywordMap.put(keywordObj, newDocumentKeyword);
                }
            } else {
                Keyword newKeyword = new Keyword(word, 1L);
                newKeywords.add(newKeyword);
                existingKeywords.put(word, newKeyword);

                DocumentKeyword documentKeyword = new DocumentKeyword(document, newKeyword, frequency);
                documentKeywords.add(documentKeyword);
                documentKeywordMap.put(newKeyword, documentKeyword);
            }
        });

        System.out.println("###newKeywords: " + newKeywords.size());
        System.out.println("###documentKeywords: " + documentKeywords.size());

        documentRepository.save(document);
        keywordRepository.saveAll(newKeywords);
        documentKeywordRepository.saveAll(documentKeywords);
    }

    @Transactional
    public void recalculateTfItfRankForAllDocumentKeywords() {
        // Get all keywords
        List<Keyword> keywords = keywordRepository.findAll();

        for (Keyword keyword : keywords) {
            // Get all document keywords for the current keyword
            List<DocumentKeyword> documentKeywords = documentKeywordRepository.findByKeyword(keyword);

            // Calculate the inverse document frequency for the current keyword
            double idf = Math.log((double) keywords.size() / (double) keyword.getFrequency());

            // Recalculate tf_itf_rank for each document keyword
            for (DocumentKeyword documentKeyword : documentKeywords) {
                double tf = (double) documentKeyword.getFrequency() / (double) documentKeyword.getDocument().getWordCount();
                double tf_idf = tf * idf;
                documentKeyword.setTfidfRank(tf_idf);
            }

            // Save the updated document keywords
            documentKeywordRepository.saveAll(documentKeywords);
        }
    }

//    public void insertKeywordsForDocument(String url, Map<String, Integer> keywordMap) {
//        // Query the database to get the document for the given URL, or create a new one if it does not exist
//        Document document = documentRepository.findByUrl(url)
//                .orElseGet(() -> documentRepository.save(new Document(url)));
//
//        // Process each keyword in the map
//        for (Map.Entry<String, Integer> entry : keywordMap.entrySet()) {
//            String keyword = entry.getKey();
//            Integer frequency = entry.getValue();
//
//            // Query the database to get the keyword, or create a new one if it does not exist
//            Keyword keywordObj = keywordRepository.findByWord(keyword)
//                    .orElseGet(() -> keywordRepository.save(new Keyword(keyword, 1L)));
//
//            // Check if a DocumentKeyword record already exists for this document and keyword
//            DocumentKeyword documentKeyword = documentKeywordRepository.findByDocumentAndKeyword(document, keywordObj)
//                    .orElseGet(() -> {
//                        // If no DocumentKeyword record exists, create a new one with the document, keyword, and frequency from the keywordMap
//                        keywordObj.setFrequency(keywordObj.getFrequency() + 1);
//                        return new DocumentKeyword(document, keywordObj, frequency);
//                    });
//            // If a DocumentKeyword record already exists, update its frequency with the new value from the keywordMap
//            documentKeyword.setFrequency(frequency);
//            documentKeywordRepository.save(documentKeyword);
//        }
//    }

    public void addDocument(String name, String url, String content, Date publishDate) {
        // Tokenize and lemmatize the content
//        List<String> tokens = Arrays.asList(tokenizer.tokenize(content));
//        List<String> lemmas = Arrays.asList(lemmatizer.lemmatize(tokens.toArray(new String[0]), new String[0]));
//
//        // Count the frequency of each lemma
//        Map<String, Long> lemmaCounts = lemmas.stream()
//                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
//
//        // Calculate the total number of lemmas in the document
//        long totalLemmas = lemmas.size();

        // Save the document to the database
//        Document document = new Document(name, url, content, publishDate);
//        documentRepository.save(document);
//
//        // Save the keywords to the database
//        for (Map.Entry<String, Long> entry : lemmaCounts.entrySet()) {
//            String lemma = entry.getKey();
//            long frequency = entry.getValue();
//            double tf = (double) frequency / totalLemmas;
//            double idf = Math.log10((double) documentRepository.count() / (keywordRepository.countByWord(lemma) + 1));
//            double tfIdf = tf * idf;
//
//            Keyword keyword = keywordRepository.findByWord(lemma).orElse(new Keyword(lemma));
//            keyword.addDocument(document, frequency, tfIdf);
//            keywordRepository.save(keyword);
//        }
    }

//    public void addDocuments()
}
