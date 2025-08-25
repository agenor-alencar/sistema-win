package com.win.win_market.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AvaliacaoResponseDTO(
    UUID id,
    Integer nota,
    String comentario,
    ClienteResponseDTO cliente,
    OffsetDateTime dataCriacao
) {}
