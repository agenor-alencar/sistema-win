package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.AvaliacaoProdutoRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.model.AvaliacaoProduto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface AvaliacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    AvaliacaoProduto toEntity(AvaliacaoProdutoRequestDTO dto);

    AvaliacaoResponseDTO toResponseDTO(AvaliacaoProduto avaliacao);
}
