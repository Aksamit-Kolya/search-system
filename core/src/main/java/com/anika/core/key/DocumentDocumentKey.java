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
public class DocumentDocumentKey implements Serializable {

    @Column(name = "document_id")
    private Long sourceDocumentId;

    @Column(name = "related_document_id")
    private Long targetDocumentId;
}