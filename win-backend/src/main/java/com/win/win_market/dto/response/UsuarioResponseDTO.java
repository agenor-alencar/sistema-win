package com.win.win_market.dto.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UsuarioResponseDTO(
    UUID id,
    String nome,
    String email,
    String telefone,
    List<EnderecoResponseDTO> enderecos,
    OffsetDateTime dataCriacao,
    OffsetDateTime ultimoAcesso
) {}
