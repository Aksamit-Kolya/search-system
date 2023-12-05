package com.anika.core.service;

import com.anika.core.dto.WebPageInfo;
import com.anika.core.entity.Document;
import com.anika.core.entity.DocumentDocument;
import com.anika.core.entity.DocumentKeyword;
import com.anika.core.entity.Keyword;
import com.anika.core.repository.DocumentDocumentRepository;
import com.anika.core.repository.DocumentKeywordRepository;
import com.anika.core.repository.DocumentRepository;
import com.anika.core.repository.KeywordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentDocumentRepository documentDocumentRepository;

    private final KeywordRepository keywordRepository;
    private final DocumentKeywordRepository documentKeywordRepository;
    private final TextProcessingService textProcessingService;


    public DocumentServiceImpl(DocumentRepository documentRepository,
                               DocumentDocumentRepository documentDocumentRepository,
                               KeywordRepository keywordRepository,
                               DocumentKeywordRepository documentKeywordRepository,
                               TextProcessingService textProcessingService) {
        this.documentRepository = documentRepository;
        this.documentDocumentRepository = documentDocumentRepository;
        this.keywordRepository = keywordRepository;
        this.documentKeywordRepository = documentKeywordRepository;
        this.textProcessingService = textProcessingService;
    }

    @Override
    public Document saveDocument(WebPageInfo webPageInfo) {

        Map<String, Integer> wordOccurrences = new HashMap<>();
        // Calculate word occurrences for title, description, keywords and content
        wordOccurrences.putAll(calculateWordOccurrencesAndMultiply(webPageInfo.getContent(), 1));
        calculateWordOccurrencesAndMultiply(webPageInfo.getDescription(), 100).forEach((key, value) -> wordOccurrences.merge(key, value, Integer::sum));
        calculateWordOccurrencesAndMultiply(webPageInfo.getKeywrods(), 100).forEach((key, value) -> wordOccurrences.merge(key, value, Integer::sum));
        calculateWordOccurrencesAndMultiply(webPageInfo.getContent(), 100).forEach((key, value) -> wordOccurrences.merge(key, value, Integer::sum));

        // Calculate the number of words in the document
        Integer tokensNumber = Math.toIntExact(wordOccurrences.values().stream().mapToLong(Integer::intValue).sum());

        Document document = upsertDocument(webPageInfo.getUrl(), webPageInfo.getTitle(), webPageInfo.getDescription(), tokensNumber);
        insertKeywordsForDocument(document, wordOccurrences);

        return document;
    }

    @Override
    //@Transactional
    public void recalculateTfItfRankForAllDocumentKeywords() {
        // Get all keywords
        List<Keyword> keywords = keywordRepository.findAll();

        for (Keyword keyword : keywords) {
            // Get all document keywords for the current keyword
            List<DocumentKeyword> documentKeywords = documentKeywordRepository.findByKeyword(keyword);

            // Calculate the inverse document frequency for the current keyword
            double idf = Math.log((double) keywords.size() / (double) keyword.getFrequency());
            keyword.setInverseFrequency((float) idf);

            // Recalculate tf_itf_rank for each document keyword
            for (DocumentKeyword documentKeyword : documentKeywords) {
                double tf = (double) documentKeyword.getFrequency() / (double) documentKeyword.getDocument().getWordCount();
                double tf_idf = tf * idf;
                documentKeyword.setTfidfRank(tf_idf);
            }

            // Save the updated document keywords
            documentKeywordRepository.saveAll(documentKeywords);
            keywordRepository.save(keyword);
        }
    }

    @Override
    public DocumentDocument createDocumentLink(Document sourceDocument, Document targetDocument) {
        DocumentDocument dd = new DocumentDocument(sourceDocument, targetDocument);
        documentDocumentRepository.save(dd);
        return new DocumentDocument(sourceDocument, targetDocument);
    }

    @Override
    public List<DocumentDocument> createDocumentLinks(Document sourceDocument, Collection<String> urls) {
        List<DocumentDocument> documentLinks = new ArrayList<>();
        List<Document> targetDocuments = documentRepository.findAllByUrlIn(urls);
        for(Document targetDocument : targetDocuments) {
            documentLinks.add(new DocumentDocument(sourceDocument, targetDocument));
        }
        documentDocumentRepository.saveAll(documentLinks);
        return documentLinks;
    }

    //@Override
    //@Transactional
    public void recalculateTfItfRankForNewDocumentKeywords() {
        // Get all keywords
        Long keywordsCount = keywordRepository.count();
        List<DocumentKeyword> newDocumentKeywords = documentKeywordRepository.findAllByTfidfRankIsNull();
        List<Keyword> keywordsToRecalculate = newDocumentKeywords.stream().map(DocumentKeyword::getKeyword).toList();

        for (Keyword keyword : keywordsToRecalculate) {
            // Get all document keywords for the current keyword
            List<DocumentKeyword> documentKeywords = documentKeywordRepository.findByKeyword(keyword);

            // Calculate the inverse document frequency for the current keyword
            double idf = Math.log((double) keywordsCount / (double) keyword.getFrequency());
            keyword.setInverseFrequency((float) idf);

            // Recalculate tf_itf_rank for each document keyword
            for (DocumentKeyword documentKeyword : documentKeywords) {
                double tf = (double) documentKeyword.getFrequency() / (double) documentKeyword.getDocument().getWordCount();
                double tf_idf = tf * idf;
                documentKeyword.setTfidfRank(tf_idf);
            }

            // Save the updated document keywords
            documentKeywordRepository.saveAll(documentKeywords);
            keywordRepository.save(keyword);
        }
    }

    private Document upsertDocument(String url, String title, String description, Integer tokensNumber) {
        Document document = documentRepository.findByUrl(url).orElseGet(Document::new);

        document.setUrl(url);
        document.setTitle(title);
        document.setDescription(description);
        document.setWordCount(tokensNumber);

        return documentRepository.save(document);
    }
    private void insertKeywordsForDocument(Document document, Map<String, Integer> keywordMap) {

        Map<String, Keyword> existingKeywords = keywordRepository.findByWordIn(keywordMap.keySet())
                .stream()
                .collect(Collectors.toMap(Keyword::getWord, Function.identity()));

        Map<Keyword, DocumentKeyword> documentKeywordMap = documentKeywordRepository.findByDocument(document)
                .stream()
                .collect(Collectors.toMap(DocumentKeyword::getKeyword, Function.identity()));

        List<DocumentKeyword> documentKeywords = new ArrayList<>();
        List<Keyword> keywordsToUpsert = new ArrayList<>();

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
                    keywordObj.setFrequency(keywordObj.getFrequency() + 1);
                    keywordsToUpsert.add(keywordObj);
                }
            } else {
                Keyword newKeyword = new Keyword(word, 1L);
                keywordsToUpsert.add(newKeyword);
                existingKeywords.put(word, newKeyword);

                DocumentKeyword documentKeyword = new DocumentKeyword(document, newKeyword, frequency);
                documentKeywords.add(documentKeyword);
                documentKeywordMap.put(newKeyword, documentKeyword);
            }
        });

        keywordRepository.saveAll(keywordsToUpsert);
        documentKeywordRepository.saveAll(documentKeywords);
    }

    private Map<String, Integer> calculateWordOccurrencesAndMultiply(String text, int multiplier) {
        List<String> lemmas = textProcessingService.extractLemmas(text);
        Map<String, Integer> wordOccurrences = textProcessingService.calculateWordOccurrences(lemmas);

        // Multiply word occurrences by the given multiplier
        wordOccurrences.replaceAll((key, value) -> value * multiplier);

        return wordOccurrences;
    }
}
