package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record VendedorCreateRequestDTO(
    @NotNull(message = "ID do usuário é obrigatório")
    UUID usuarioId,

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @NoXSS
    @NoSQLInjection
    String descricao,

    @Pattern(regexp = "^\\d{11}$|^\\d{14}$", message = "CPF deve ter 11 dígitos ou CNPJ deve ter 14 dígitos")
    String documento
) {}
