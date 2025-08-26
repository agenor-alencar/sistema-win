package com.win.win_market.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record NotificacaoResponseDTO(
    UUID id,
    UUID usuarioId,
    String titulo,
    String mensagem,
    String tipo,
    boolean lida,
    OffsetDateTime dataCriacao
) {}
