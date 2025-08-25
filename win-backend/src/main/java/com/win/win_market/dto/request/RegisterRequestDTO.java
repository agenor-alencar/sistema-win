package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import com.win.win_market.validation.StrongPassword;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record RegisterRequestDTO(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @NoXSS
    @NoSQLInjection
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @NoXSS
    String email,

    @NotBlank(message = "Senha é obrigatória")
    @StrongPassword
    String senha,

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Formato de telefone inválido")
    @NoXSS
    String telefone,

    @Valid
    List<EnderecoRequestDTO> enderecos
) {}
