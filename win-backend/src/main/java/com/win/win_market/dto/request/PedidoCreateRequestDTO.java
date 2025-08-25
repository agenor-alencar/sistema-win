package com.win.win_market.dto.request;

import com.win.win_market.validation.NoXSS;
import com.win.win_market.validation.NoSQLInjection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record PedidoCreateRequestDTO(
    @NotNull(message = "ID do cliente é obrigatório")
    UUID clienteId,

    @NotNull(message = "Itens do pedido são obrigatórios")
    @Size(min = 1, message = "Pedido deve ter pelo menos um item")
    @Valid
    List<ItemPedidoRequestDTO> itens,

    @Valid
    @NotNull(message = "Endereço de entrega é obrigatório")
    EnderecoRequestDTO enderecoEntrega,

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @NoXSS
    @NoSQLInjection
    String observacoes
) {}
