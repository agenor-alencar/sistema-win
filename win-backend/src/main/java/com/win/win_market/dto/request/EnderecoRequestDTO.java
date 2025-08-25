package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

public record EnderecoRequestDTO(
    @NotBlank(message = "Logradouro é obrigatório")
    @Size(min = 5, max = 255, message = "Logradouro deve ter entre 5 e 255 caracteres")
    @NoXSS
    @NoSQLInjection
    String logradouro,

    @NotBlank(message = "Número é obrigatório")
    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    @NoXSS
    String numero,

    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    @NoXSS
    String complemento,

    @NotBlank(message = "Bairro é obrigatório")
    @Size(min = 2, max = 100, message = "Bairro deve ter entre 2 e 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String bairro,

    @NotBlank(message = "Cidade é obrigatória")
    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String cidade,

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Estado deve conter apenas letras maiúsculas")
    String estado,

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve ter 8 dígitos")
    String cep
) {}
