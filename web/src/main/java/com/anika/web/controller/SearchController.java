package com.anika.web.controller;

import com.anika.core.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final PageRepository pageRepository;

    @Autowired
    public SearchController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<Boolean> findSongs() {
        return ResponseEntity.ok(pageRepository == null);
    }
}
