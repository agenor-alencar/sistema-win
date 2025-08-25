package com.win.win_market.dto.response;

import java.util.UUID;

public record VendedorResponseDTO(
    UUID id,
    String nome,
    String email,
    String telefone,
    Double avaliacaoMedia,
    Integer totalAvaliacoes
) {}
