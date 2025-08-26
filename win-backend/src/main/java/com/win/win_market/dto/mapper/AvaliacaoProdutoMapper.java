package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.AvaliacaoProdutoRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.model.AvaliacaoProduto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AvaliacaoProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "dataAvaliacao", ignore = true)
    AvaliacaoProduto toEntity(AvaliacaoProdutoRequestDTO dto);

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "clienteNome", source = "cliente.usuario.nome")
    @Mapping(target = "avaliadoId", source = "produto.id")
    @Mapping(target = "avaliadoNome", source = "produto.nome")
    @Mapping(target = "avaliadoTipo", constant = "PRODUTO")
    @Mapping(target = "pedidoId", source = "pedido.id")
    AvaliacaoResponseDTO toResponseDTO(AvaliacaoProduto avaliacao);
}
