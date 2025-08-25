package com.win.win_market.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CarrinhoItemRequestDTO(
    @NotNull(message = "ID do produto é obrigatório")
    UUID produtoId,

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    @Max(value = 999, message = "Quantidade deve ser menor que 1.000")
    Integer quantidade
) {}
