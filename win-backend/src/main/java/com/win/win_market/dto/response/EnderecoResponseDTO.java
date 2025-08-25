package com.win.win_market.dto.response;

import java.util.UUID;

public record EnderecoResponseDTO(
    UUID id,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String cep
) {}
