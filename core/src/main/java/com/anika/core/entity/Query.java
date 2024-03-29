package com.anika.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "query")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Column(name = "timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

//    @ManyToMany
//    @JoinTable(name = "query_document",
//            joinColumns = @JoinColumn(name = "query_id"),
//            inverseJoinColumns = @JoinColumn(name = "document_id"))
//    private Set<Document> documents = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(name = "query_keyword",
//            joinColumns = @JoinColumn(name = "query_id"),
//            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
//    private Set<Keyword> keywords = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_query",
            joinColumns = @JoinColumn(name = "query_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    public Query(String queryText) {
        this.text = queryText;
    }

    public Query() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(id, query.id) && Objects.equals(text, query.text) && Objects.equals(timestamp, query.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, timestamp);
    }
}
