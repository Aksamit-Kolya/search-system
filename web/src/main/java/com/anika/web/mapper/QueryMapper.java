package com.anika.web.mapper;

import com.anika.core.entity.Query;
import com.anika.web.dto.QueryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    List<QueryDTO> toDTOList(List<Query> queries);
}
