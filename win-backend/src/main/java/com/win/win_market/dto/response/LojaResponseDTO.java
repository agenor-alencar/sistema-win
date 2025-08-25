package com.win.win_market.dto.response;

import java.util.List;
import java.util.UUID;

public record LojaResponseDTO(
    Long id,
    String nome,
    String descricao,
    String cnpj,
    UsuarioResponseDTO proprietario,
    EnderecoResponseDTO endereco,
    List<ProdutoSummaryResponseDTO> produtos,
    Double avaliacaoMedia,
    Integer totalAvaliacoes
) {}
