package com.anika.web.service;

import com.anika.core.entity.Document;
import com.anika.core.entity.Query;
import com.anika.core.entity.User;
import com.anika.core.repository.DocumentRepository;
import com.anika.core.repository.QueryRepository;
import com.anika.core.service.TextProcessingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final DocumentRepository documentRepository;
    private final QueryRepository queryRepository;
    private final TextProcessingService textProcessingService;

    public SearchServiceImpl(DocumentRepository documentRepository, QueryRepository queryRepository, TextProcessingService textProcessingService) {
        this.documentRepository = documentRepository;
        this.queryRepository = queryRepository;
        this.textProcessingService = textProcessingService;
    }

    @Override
    public List<Document> searchDocuments(String query) {
        List<String> queryLemmas = textProcessingService.extractLemmas(query);
        return documentRepository.findDocumentsByKeywords(queryLemmas);
    }

    @Override
    public List<Query> getUserHistory(User user) {
        return queryRepository.findAllByUserIdSortedByTimestamp(user.getId());
    }
}
