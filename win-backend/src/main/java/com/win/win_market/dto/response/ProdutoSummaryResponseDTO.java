package com.win.win_market.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoSummaryResponseDTO(
    UUID id,
    String nome,
    BigDecimal preco,
    Boolean ativo,
    String imagemPrincipal,
    Double avaliacaoMedia,
    Integer totalAvaliacoes
) {}
