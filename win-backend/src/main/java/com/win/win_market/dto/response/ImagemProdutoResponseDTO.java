package com.win.win_market.dto.response;

import java.util.UUID;

public record ImagemProdutoResponseDTO(
    UUID id,
    String url,
    String nomeArquivo,
    Boolean principal
) {}
