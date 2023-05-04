package com.anika.core.service;

import com.anika.core.dto.WebPageInfo;
import com.anika.core.entity.Document;

public interface DocumentService {
    Document saveDocument(WebPageInfo webPageInfo);
    void recalculateTfItfRankForAllDocumentKeywords();
}
