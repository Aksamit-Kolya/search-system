package com.anika.web.mapper;

import com.anika.core.entity.Page;
import com.anika.web.dto.PageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageMapper {
    PageDto pageToPageDto(Page page);
}
