package com.anika.web.service;

import com.anika.core.entity.Document;
import java.util.List;

public interface SearchService {
    List<Document> searchDocuments(String query);
}
