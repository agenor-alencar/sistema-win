package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record AvaliacaoProdutoRequestDTO(
    @NotNull(message = "ID do produto é obrigatório")
    UUID produtoId,

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota deve ser entre 1 e 5")
    @Max(value = 5, message = "Nota deve ser entre 1 e 5")
    Integer nota,

    @Size(max = 500, message = "Comentário deve ter no máximo 500 caracteres")
    @NoXSS
    @NoSQLInjection
    String comentario
) {}
