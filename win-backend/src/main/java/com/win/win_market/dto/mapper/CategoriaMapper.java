package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.CategoriaCreateRequestDTO;
import com.win.win_market.dto.response.CategoriaResponseDTO;
import com.win.win_market.model.Categoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    Categoria toEntity(CategoriaCreateRequestDTO dto);

    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    void updateEntityFromDTO(CategoriaCreateRequestDTO dto, @MappingTarget Categoria categoria);
}
