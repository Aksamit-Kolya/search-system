package com.anika.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @Lob
    private String content;

    private Integer wordCount;

    @Column(name = "publish_date")
    private LocalDate publishDate;

//    @ManyToMany
//    @JoinTable(name = "document_document",
//            joinColumns = @JoinColumn(name = "document_id"),
//            inverseJoinColumns = @JoinColumn(name = "related_document_id"))
//    private Set<Document> relatedDocuments = new HashSet<>();

    @OneToMany(mappedBy = "document")
    private Set<DocumentKeyword> documentKeywords = new HashSet<>();

    public Document(String url) {
        this.url = url;
    }

    public Document() {
    }

//    @ManyToMany
//    @JoinTable(name = "document_keyword",
//            joinColumns = @JoinColumn(name = "document_id"),
//            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
//    private Set<Keyword> keywords = new HashSet<>();
}
