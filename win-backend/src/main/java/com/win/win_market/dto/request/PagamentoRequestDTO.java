package com.win.win_market.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoRequestDTO(
    @NotNull(message = "ID do pedido é obrigatório")
    UUID pedidoId,

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    BigDecimal valor,

    @NotBlank(message = "Método de pagamento é obrigatório")
    @Size(max = 50, message = "Método de pagamento deve ter no máximo 50 caracteres")
    String metodoPagamento
) {}
