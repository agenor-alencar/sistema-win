package com.win.win_market.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ItemPedidoRequestDTO(
    @NotNull(message = "ID do produto é obrigatório")
    UUID produtoId,

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    @Max(value = 9999, message = "Quantidade deve ser menor que 10.000")
    Integer quantidade
) {}
