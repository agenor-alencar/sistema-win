package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.LojaCreateRequestDTO;
import com.win.win_market.dto.response.LojaResponseDTO;
import com.win.win_market.model.Loja;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, EnderecoMapper.class, ProdutoMapper.class})
public interface LojaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proprietario", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    Loja toEntity(LojaCreateRequestDTO dto);

    @Mapping(target = "avaliacaoMedia", expression = "java(calcularAvaliacaoMedia(loja))")
    @Mapping(target = "totalAvaliacoes", expression = "java(calcularTotalAvaliacoes(loja))")
    LojaResponseDTO toResponseDTO(Loja loja);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proprietario", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    void updateEntityFromDTO(LojaCreateRequestDTO dto, @MappingTarget Loja loja);

    default Double calcularAvaliacaoMedia(Loja loja) {
        // Implementação será feita no service
        return 0.0;
    }

    default Integer calcularTotalAvaliacoes(Loja loja) {
        // Implementação será feita no service
        return 0;
    }
}
