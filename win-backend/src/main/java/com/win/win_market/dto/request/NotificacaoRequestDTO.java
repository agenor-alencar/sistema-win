package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record NotificacaoRequestDTO(
    @NotNull(message = "ID do usuário é obrigatório")
    UUID usuarioId,

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String titulo,

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 500, message = "Mensagem deve ter no máximo 500 caracteres")
    @NoXSS
    @NoSQLInjection
    String mensagem,

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    String tipo
) {}
