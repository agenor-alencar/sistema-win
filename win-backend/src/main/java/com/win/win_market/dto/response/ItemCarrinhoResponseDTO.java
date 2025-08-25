package com.win.win_market.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCarrinhoResponseDTO(
    UUID id,
    ProdutoSummaryResponseDTO produto,
    Integer quantidade,
    BigDecimal subtotal
) {}
