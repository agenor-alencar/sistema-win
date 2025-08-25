package com.win.win_market.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CarrinhoResponseDTO(
    UUID id,
    ClienteResponseDTO cliente,
    List<ItemCarrinhoResponseDTO> itens,
    BigDecimal valorTotal,
    Integer totalItens,
    OffsetDateTime dataAtualizacao
) {}
