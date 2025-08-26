package com.win.win_market.dto.request;

import jakarta.validation.constraints.*;

public record ImagemProdutoRequestDTO(
    @NotBlank(message = "Nome do arquivo é obrigatório")
    @Size(max = 255, message = "Nome do arquivo deve ter no máximo 255 caracteres")
    String nomeArquivo,

    @NotBlank(message = "Tipo do arquivo é obrigatório")
    @Size(max = 50, message = "Tipo do arquivo deve ter no máximo 50 caracteres")
    String tipoArquivo,

    @Min(value = 1, message = "Ordem de exibição deve ser pelo menos 1")
    Integer ordemExibicao
) {}
