package com.anika.core.repository;

import com.anika.core.entity.DocumentDocument;
import com.anika.core.entity.DocumentKeyword;
import com.anika.core.key.DocumentDocumentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentDocumentRepository extends JpaRepository<DocumentDocument, DocumentDocumentKey> {
}
