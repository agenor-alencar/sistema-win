package com.win.win_market.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
    UUID id,
    ClienteResponseDTO cliente,
    List<ItemPedidoResponseDTO> itens,
    BigDecimal valorTotal,
    String status,
    EnderecoResponseDTO enderecoEntrega,
    String observacoes,
    OffsetDateTime dataCriacao,
    OffsetDateTime dataAtualizacao,
    PagamentoResponseDTO pagamento,
    EntregaResponseDTO entrega
) {}
