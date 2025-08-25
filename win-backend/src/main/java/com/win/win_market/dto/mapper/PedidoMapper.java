package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.PedidoCreateRequestDTO;
import com.win.win_market.dto.response.PedidoResponseDTO;
import com.win.win_market.model.Pedido;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemPedidoMapper.class, EnderecoMapper.class, PagamentoMapper.class, EntregaMapper.class})
public interface PedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "status", constant = "PENDENTE")
    @Mapping(target = "pagamento", ignore = true)
    @Mapping(target = "entrega", ignore = true)
    Pedido toEntity(PedidoCreateRequestDTO dto);

    PedidoResponseDTO toResponseDTO(Pedido pedido);
}
