package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoUpdateRequestDTO(
    @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
    @NoXSS
    @NoSQLInjection
    String nome,

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    @NoXSS
    @NoSQLInjection
    String descricao,

    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Digits(integer = 8, fraction = 2, message = "Preço deve ter no máximo 8 dígitos inteiros e 2 decimais")
    BigDecimal preco,

    @Min(value = 0, message = "Quantidade em estoque deve ser maior ou igual a zero")
    @Max(value = 999999, message = "Quantidade em estoque deve ser menor que 1.000.000")
    Integer quantidadeEstoque,

    UUID categoriaId,

    Boolean ativo
) {}
