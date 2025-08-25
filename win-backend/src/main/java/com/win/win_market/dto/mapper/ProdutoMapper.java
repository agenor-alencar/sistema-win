package com.win.win_market.dto.mapper;

import com.win.win_market.dto.request.ProdutoCreateRequestDTO;
import com.win.win_market.dto.request.ProdutoUpdateRequestDTO;
import com.win.win_market.dto.response.ProdutoResponseDTO;
import com.win.win_market.dto.response.ProdutoSummaryResponseDTO;
import com.win.win_market.model.Produto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, VendedorMapper.class, ImagemProdutoMapper.class})
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "imagens", ignore = true)
    @Mapping(target = "vendedor", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    Produto toEntity(ProdutoCreateRequestDTO dto);

    @Mapping(target = "avaliacaoMedia", expression = "java(calcularAvaliacaoMedia(produto))")
    @Mapping(target = "totalAvaliacoes", expression = "java(calcularTotalAvaliacoes(produto))")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    @Mapping(target = "imagemPrincipal", expression = "java(obterImagemPrincipal(produto))")
    @Mapping(target = "avaliacaoMedia", expression = "java(calcularAvaliacaoMedia(produto))")
    @Mapping(target = "totalAvaliacoes", expression = "java(calcularTotalAvaliacoes(produto))")
    ProdutoSummaryResponseDTO toSummaryDTO(Produto produto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "imagens", ignore = true)
    @Mapping(target = "vendedor", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntityFromDTO(ProdutoUpdateRequestDTO dto, @MappingTarget Produto produto);

    default Double calcularAvaliacaoMedia(Produto produto) {
        // Implementação será feita no service
        return 0.0;
    }

    default Integer calcularTotalAvaliacoes(Produto produto) {
        // Implementação será feita no service
        return 0;
    }

    default String obterImagemPrincipal(Produto produto) {
        if (produto.getImagens() != null && !produto.getImagens().isEmpty()) {
            return produto.getImagens().stream()
                    .filter(img -> Boolean.TRUE.equals(img.getPrincipal()))
                    .findFirst()
                    .map(img -> img.getUrl())
                    .orElse(produto.getImagens().get(0).getUrl());
        }
        return null;
    }
}
