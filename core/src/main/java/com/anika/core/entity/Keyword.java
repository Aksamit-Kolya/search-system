package com.anika.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private Long frequency;

    private Float inverseFrequency;

//    @OneToMany(mappedBy = "keyword")
//    private Set<DocumentKeyword> documentKeywords = new HashSet<>();
//
//    @ManyToMany(mappedBy = "keywords")
//    private Set<Query> queries = new HashSet<>();

    public Keyword(String word, Long frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public Keyword() {
    }
}
