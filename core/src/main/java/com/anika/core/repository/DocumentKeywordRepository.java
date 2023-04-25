package com.anika.core.repository;

import com.anika.core.entity.Document;
import com.anika.core.entity.DocumentKeyword;
import com.anika.core.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentKeywordRepository  extends JpaRepository<DocumentKeyword, Long> {
    Optional<DocumentKeyword> findByDocumentAndKeyword(Document document, Keyword keywordObj);

    Collection<DocumentKeyword> findByDocument(Document document);

    List<DocumentKeyword> findByKeyword(Keyword keyword);
}
