package com.win.win_market.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EntregaResponseDTO(
    UUID id,
    String status,
    MotoristaResponseDTO motorista,
    OffsetDateTime dataPrevisao,
    OffsetDateTime dataEntrega,
    String codigoRastreamento
) {}
