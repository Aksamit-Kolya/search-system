package com.anika.web.service;

import com.anika.core.entity.Document;
import com.anika.core.entity.Query;
import com.anika.core.entity.User;

import java.util.List;

public interface SearchService {
    List<Document> searchDocuments(String query);
    List<Query> getUserHistory(User user);
}
