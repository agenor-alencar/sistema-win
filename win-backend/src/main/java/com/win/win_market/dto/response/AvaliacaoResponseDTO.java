package com.win.win_market.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AvaliacaoResponseDTO(
    UUID id,
    UUID clienteId,
    String clienteNome,
    UUID avaliadoId,  // Pode ser produtoId, vendedorId ou motoristaId
    String avaliadoNome,
    String avaliadoTipo, // "PRODUTO", "VENDEDOR" ou "MOTORISTA"
    UUID pedidoId,
    Integer nota,
    String comentario,
    OffsetDateTime dataAvaliacao
) {}
