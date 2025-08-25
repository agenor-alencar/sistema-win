package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record LojaCreateRequestDTO(
    @NotBlank(message = "Nome da loja é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String nome,

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @NoXSS
    @NoSQLInjection
    String descricao,

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    String cnpj,

    @NotNull(message = "ID do proprietário é obrigatório")
    UUID proprietarioId,

    EnderecoRequestDTO endereco
) {}
