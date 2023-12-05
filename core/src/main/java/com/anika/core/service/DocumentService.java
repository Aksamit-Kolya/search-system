package com.anika.core.service;

import com.anika.core.dto.WebPageInfo;
import com.anika.core.entity.Document;
import com.anika.core.entity.DocumentDocument;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface DocumentService {
    Document saveDocument(WebPageInfo webPageInfo);
    void recalculateTfItfRankForAllDocumentKeywords();
    DocumentDocument createDocumentLink(Document sourceDocument, Document targetDocument);
    List<DocumentDocument> createDocumentLinks(Document sourceDocument, Collection<String> urls);
}
