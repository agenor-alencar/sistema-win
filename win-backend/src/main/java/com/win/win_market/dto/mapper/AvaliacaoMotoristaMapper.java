package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.AvaliacaoMotoristaRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.model.AvaliacaoMotorista;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AvaliacaoMotoristaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "motorista", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "dataAvaliacao", ignore = true)
    AvaliacaoMotorista toEntity(AvaliacaoMotoristaRequestDTO dto);

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "clienteNome", source = "cliente.usuario.nome")
    @Mapping(target = "avaliadoId", source = "motorista.id")
    @Mapping(target = "avaliadoNome", source = "motorista.usuario.nome")
    @Mapping(target = "avaliadoTipo", constant = "MOTORISTA")
    @Mapping(target = "pedidoId", source = "pedido.id")
    AvaliacaoResponseDTO toResponseDTO(AvaliacaoMotorista avaliacao);
}
