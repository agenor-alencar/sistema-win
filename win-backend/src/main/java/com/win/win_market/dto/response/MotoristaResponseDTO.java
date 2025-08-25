package com.win.win_market.dto.response;

import java.util.UUID;

public record MotoristaResponseDTO(
    UUID id,
    String nome,
    String telefone,
    String veiculo,
    String placa,
    Double avaliacaoMedia,
    Integer totalAvaliacoes
) {}
