package com.anika.core.repository;

import com.anika.core.entity.Document;
import com.anika.core.entity.DocumentKeyword;
import com.anika.core.entity.Keyword;
import com.anika.core.key.DocumentKeywordKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentKeywordRepository  extends JpaRepository<DocumentKeyword, DocumentKeywordKey> {
    Optional<DocumentKeyword> findByDocumentAndKeyword(Document document, Keyword keywordObj);

    Collection<DocumentKeyword> findByDocument(Document document);

    List<DocumentKeyword> findByKeyword(Keyword keyword);

    List<DocumentKeyword> findAllByTfidfRankIsNull();
}
