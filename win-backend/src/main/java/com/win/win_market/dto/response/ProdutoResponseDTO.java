package com.win.win_market.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ProdutoResponseDTO(
    UUID id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer quantidadeEstoque,
    Boolean ativo,
    CategoriaResponseDTO categoria,
    VendedorResponseDTO vendedor,
    List<ImagemProdutoResponseDTO> imagens,
    OffsetDateTime dataCriacao,
    Double avaliacaoMedia,
    Integer totalAvaliacoes
) {}
