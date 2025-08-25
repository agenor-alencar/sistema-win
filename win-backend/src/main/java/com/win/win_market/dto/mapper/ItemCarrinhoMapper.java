package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.CarrinhoItemRequestDTO;
import com.win.win_market.dto.response.ItemCarrinhoResponseDTO;
import com.win.win_market.model.ItemCarrinho;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemCarrinhoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carrinho", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    ItemCarrinho toEntity(CarrinhoItemRequestDTO dto);

    @Mapping(target = "subtotal", expression = "java(calcularSubtotal(itemCarrinho))")
    ItemCarrinhoResponseDTO toResponseDTO(ItemCarrinho itemCarrinho);

    default java.math.BigDecimal calcularSubtotal(ItemCarrinho itemCarrinho) {
        if (itemCarrinho.getProduto() == null || itemCarrinho.getQuantidade() == null) {
            return java.math.BigDecimal.ZERO;
        }
        return itemCarrinho.getProduto().getPreco()
                .multiply(java.math.BigDecimal.valueOf(itemCarrinho.getQuantidade()));
    }
}
