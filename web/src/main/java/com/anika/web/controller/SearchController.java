package com.anika.web.controller;

import com.anika.core.repository.PageRepository;
import com.anika.core.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final PageRepository pageRepository;
    private final DocumentService documentService;

    @Autowired
    public SearchController(PageRepository pageRepository, DocumentService documentService) {
        this.pageRepository = pageRepository;
        this.documentService = documentService;
    }

    @GetMapping("/test")
    public ResponseEntity<Boolean> findSongs() {
        return ResponseEntity.ok(pageRepository == null);
    }

    @PostMapping("/TF-ITF/recalculate")
    public ResponseEntity<String> sendHostNameToCrawler() {
        documentService.recalculateTfItfRankForAllDocumentKeywords();
        return ResponseEntity.ok("Success");
    }
}
