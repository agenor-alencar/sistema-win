package com.win.win_market.dto.mapper;

import com.win.win_market.dto.response.CarrinhoResponseDTO;
import com.win.win_market.model.Carrinho;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemCarrinhoMapper.class})
public interface CarrinhoMapper {

    @Mapping(target = "valorTotal", expression = "java(calcularValorTotal(carrinho))")
    @Mapping(target = "totalItens", expression = "java(calcularTotalItens(carrinho))")
    CarrinhoResponseDTO toResponseDTO(Carrinho carrinho);

    default java.math.BigDecimal calcularValorTotal(Carrinho carrinho) {
        if (carrinho.getItens() == null || carrinho.getItens().isEmpty()) {
            return java.math.BigDecimal.ZERO;
        }
        return carrinho.getItens().stream()
                .map(item -> item.getProduto().getPreco().multiply(java.math.BigDecimal.valueOf(item.getQuantidade())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    default Integer calcularTotalItens(Carrinho carrinho) {
        if (carrinho.getItens() == null || carrinho.getItens().isEmpty()) {
            return 0;
        }
        return carrinho.getItens().stream()
                .mapToInt(item -> item.getQuantidade())
                .sum();
    }
}
