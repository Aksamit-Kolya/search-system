package com.anika.core.entity;

import com.anika.core.key.DocumentDocumentKey;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "document_document")
public class DocumentDocument {

    @EmbeddedId
    private DocumentDocumentKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("document_id")
    @JoinColumn(name="document_id", nullable = false)
    private Document sourceDocument;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("related_document_id")
    @JoinColumn(name="related_document_id", nullable=false)
    private Document targetDocument;

    public DocumentDocument(Document sourceDocument, Document targetDocument) {
        this.sourceDocument = sourceDocument;
        this.targetDocument = targetDocument;
        this.id = new DocumentDocumentKey(sourceDocument.getId(), targetDocument.getId());
    }
}
