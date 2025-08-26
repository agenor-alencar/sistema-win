package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.ItemPedidoRequestDTO;
import com.win.win_market.dto.response.ItemPedidoResponseDTO;
import com.win.win_market.model.ItemPedido;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "precoUnitario", ignore = true)
    ItemPedido toEntity(ItemPedidoRequestDTO dto);

    ItemPedidoResponseDTO toResponseDTO(ItemPedido itemPedido);
}
