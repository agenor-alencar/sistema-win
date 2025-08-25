package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.RegisterRequestDTO;
import com.win.win_market.dto.response.UsuarioResponseDTO;
import com.win.win_market.model.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "ultimoAcesso", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    Usuario toEntity(RegisterRequestDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "ultimoAcesso", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(RegisterRequestDTO dto, @MappingTarget Usuario usuario);
}
