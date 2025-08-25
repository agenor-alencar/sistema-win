package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

public record CategoriaCreateRequestDTO(
    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String nome,

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @NoXSS
    @NoSQLInjection
    String descricao
) {}
