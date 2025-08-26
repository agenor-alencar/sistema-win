package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.AvaliacaoVendedorRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.model.AvaliacaoVendedor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AvaliacaoVendedorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vendedor", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "dataAvaliacao", ignore = true)
    AvaliacaoVendedor toEntity(AvaliacaoVendedorRequestDTO dto);

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "clienteNome", source = "cliente.usuario.nome")
    @Mapping(target = "avaliadoId", source = "vendedor.id")
    @Mapping(target = "avaliadoNome", source = "vendedor.nomeFantasia")
    @Mapping(target = "avaliadoTipo", constant = "VENDEDOR")
    @Mapping(target = "pedidoId", source = "pedido.id")
    AvaliacaoResponseDTO toResponseDTO(AvaliacaoVendedor avaliacao);
}
