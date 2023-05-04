package com.anika.web.service;

import com.anika.core.entity.Document;
import com.anika.core.repository.DocumentRepository;
import com.anika.core.service.TextProcessingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final DocumentRepository documentRepository;
    private final TextProcessingService textProcessingService;

    public SearchServiceImpl(DocumentRepository documentRepository, TextProcessingService textProcessingService) {
        this.documentRepository = documentRepository;
        this.textProcessingService = textProcessingService;
    }

    public List<Document> searchDocuments(String query) {
        List<String> queryLemmas = textProcessingService.extractLemmas(query);
        return documentRepository.findDocumentsByKeywords(queryLemmas);
    }
}
