package com.win.win_market.dto.response;

import java.util.UUID;

public record CategoriaResponseDTO(
    UUID id,
    String nome,
    String descricao
) {}
