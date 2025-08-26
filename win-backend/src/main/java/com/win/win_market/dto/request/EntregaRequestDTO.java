package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EntregaRequestDTO(
    @NotNull(message = "ID do pedido é obrigatório")
    UUID pedidoId,

    @NotNull(message = "ID do motorista é obrigatório")
    UUID motoristaId,

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    @NoXSS
    @NoSQLInjection
    String enderecoEntrega,

    @Future(message = "Data de previsão deve ser no futuro")
    OffsetDateTime dataPrevisaoEntrega
) {}
