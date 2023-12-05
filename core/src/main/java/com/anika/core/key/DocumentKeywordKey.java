package com.anika.core.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class DocumentKeywordKey implements Serializable {

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "keyword_id")
    private Long keywordId;
}