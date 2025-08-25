package com.win.win_market.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PagamentoResponseDTO(
    UUID id,
    String metodoPagamento,
    String status,
    BigDecimal valor,
    OffsetDateTime dataPagamento,
    String transacaoId
) {}
