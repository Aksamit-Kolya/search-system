package com.anika.web.controller;

import com.anika.core.service.DocumentServiceImpl;
import com.anika.web.dto.DocumentDTO;
import com.anika.web.mapper.DocumentMapper;
import com.anika.web.service.SearchService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    private final DocumentServiceImpl documentService;

    @Autowired
    public SearchController(SearchService searchService, DocumentServiceImpl documentService) {
        this.searchService = searchService;
        this.documentService = documentService;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/documents")
    public List<DocumentDTO> findDocumentsByKeywords(@RequestParam("query") String query) {
        return Mappers.getMapper(DocumentMapper.class).toDTOList(searchService.searchDocuments(query));
    }

    @PostMapping("/TF-ITF/recalculate")
    public ResponseEntity<String> sendDomainToCrawler() {
        documentService.recalculateTfItfRankForAllDocumentKeywords();
        return ResponseEntity.ok("Success");
    }
}
