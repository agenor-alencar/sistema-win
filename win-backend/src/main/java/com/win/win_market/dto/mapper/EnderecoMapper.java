package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.EnderecoRequestDTO;
import com.win.win_market.dto.response.EnderecoResponseDTO;
import com.win.win_market.model.Endereco;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Endereco toEntity(EnderecoRequestDTO dto);

    EnderecoResponseDTO toResponseDTO(Endereco endereco);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntityFromDTO(EnderecoRequestDTO dto, @MappingTarget Endereco endereco);
}
