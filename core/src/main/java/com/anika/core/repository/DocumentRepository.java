package com.anika.core.repository;

import com.anika.core.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUrl(String url);
    List<Document> findAllByUrlIn(Collection<String> urls);


    @Query("SELECT d FROM Document d " +
            "JOIN d.documentKeywords k " +
            "WHERE k.keyword.word IN (?1) " +
            "GROUP BY d.id " +
            "ORDER BY SUM(k.tfidfRank) DESC")
    List<Document> findDocumentsByKeywords(List<String> keywords);

//    @Query(value =
//            "SELECT d.url, SUM(dk.tf_idf_rank) AS relevance_score " +
//                    "FROM document d " +
//                    "JOIN document_keyword dk ON d.id = dk.document_id " +
//                    "JOIN keyword k ON dk.keyword_id = k.id " +
//                    "WHERE k.word IN (?1) " +
//                    "GROUP BY d.id " +
//                    "ORDER BY relevance_score DESC", nativeQuery = true)
//    List<Object[]> findDocumentsByKeywords(List<String> keywords);
}
