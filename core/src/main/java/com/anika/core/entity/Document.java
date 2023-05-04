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
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "document")
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String title;
    private String description;
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

    public Document(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }


//    @ManyToMany
//    @JoinTable(name = "document_keyword",
//            joinColumns = @JoinColumn(name = "document_id"),
//            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
//    private Set<Keyword> keywords = new HashSet<>();
}
