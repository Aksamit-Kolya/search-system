package com.anika.core.entity;

import com.anika.core.repository.DocumentKeywordRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "document_keyword")
public class DocumentKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Document document;

    @ManyToOne
    private Keyword keyword;

    private Integer frequency;

    @Column(name = "TF_IDF_rank")
    private Double tfidfRank;

    public DocumentKeyword(Document document, Keyword keyword, Integer frequency) {
        this.document = document;
        this.keyword = keyword;
        this.frequency = frequency;
    }

    public DocumentKeyword() {
    }
}
