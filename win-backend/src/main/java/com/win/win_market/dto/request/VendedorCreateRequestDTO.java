package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public record VendedorCreateRequestDTO(
    @NotNull(message = "ID do usuário é obrigatório")
    UUID usuarioId,

    @NotBlank(message = "Nome fantasia é obrigatório")
    @Size(max = 100, message = "Nome fantasia deve ter no máximo 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String nomeFantasia,

    @Size(max = 100, message = "Razão social deve ter no máximo 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String razaoSocial,

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    String cnpj,

    @Past(message = "Data de abertura deve ser no passado")
    LocalDate dataAbertura,

    Map<String, Object> horarioFuncionamento
) {}
