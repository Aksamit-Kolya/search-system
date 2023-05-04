package com.anika.web.mapper;

import com.anika.core.entity.Document;
import com.anika.web.dto.DocumentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    List<DocumentDTO> toDTOList(List<Document> documents);
}
