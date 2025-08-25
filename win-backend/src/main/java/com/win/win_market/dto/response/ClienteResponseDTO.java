package com.win.win_market.dto.response;

import java.util.UUID;

public record ClienteResponseDTO(
    UUID id,
    String nome,
    String email,
    String telefone
) {}
