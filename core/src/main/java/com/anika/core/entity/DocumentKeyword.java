package com.anika.core.entity;

import com.anika.core.key.DocumentKeywordKey;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "document_keyword")
public class DocumentKeyword {

    @EmbeddedId
    private DocumentKeywordKey id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("document_id")
    private Document document;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @MapsId("keyword_id")
    private Keyword keyword;

    private Integer frequency;

    @Column(name = "TF_IDF_rank")
    private Double tfidfRank;

    public DocumentKeyword(Document document, Keyword keyword, Integer frequency) {
        this.document = document;
        this.keyword = keyword;
//        if(keyword.getId() == null) {
//            System.out.println("Keyword id is null");
//        }
        this.id = new DocumentKeywordKey(document.getId(), keyword.getId());
        this.frequency = frequency;
    }
    @PrePersist
    public void setId() {
        if(this.id.getKeywordId() == null) {
            this.id.setKeywordId(this.keyword.getId());
        }
    }
}
