package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record MotoristaCreateRequestDTO(
    @NotNull(message = "ID do usuário é obrigatório")
    UUID usuarioId,

    @NotBlank(message = "CNH é obrigatória")
    @Size(max = 20, message = "CNH deve ter no máximo 20 caracteres")
    @NoXSS
    String cnh,

    @NotBlank(message = "Veículo é obrigatório")
    @Size(max = 100, message = "Veículo deve ter no máximo 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String veiculo,

    @NotBlank(message = "Placa é obrigatória")
    @Size(max = 10, message = "Placa deve ter no máximo 10 caracteres")
    @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Placa deve estar no formato válido")
    String placa
) {}
