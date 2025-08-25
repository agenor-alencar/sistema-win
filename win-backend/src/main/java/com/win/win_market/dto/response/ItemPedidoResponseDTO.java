package com.win.win_market.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponseDTO(
    UUID id,
    ProdutoSummaryResponseDTO produto,
    Integer quantidade,
    BigDecimal precoUnitario,
    BigDecimal subtotal
) {}
