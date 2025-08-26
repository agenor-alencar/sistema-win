package com.win.win_market.repository;

import com.win.win_market.model.AvaliacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, UUID> {

    List<AvaliacaoProduto> findByProdutoId(UUID produtoId);

    List<AvaliacaoProduto> findByClienteId(UUID clienteId);

    List<AvaliacaoProduto> findByPedidoId(UUID pedidoId);

    @Query("SELECT AVG(a.nota) FROM AvaliacaoProduto a WHERE a.produto.id = :produtoId")
    Double findAverageNotaByProdutoId(@Param("produtoId") UUID produtoId);

    @Query("SELECT COUNT(a) FROM AvaliacaoProduto a WHERE a.produto.id = :produtoId")
    Long countByProdutoId(@Param("produtoId") UUID produtoId);

    boolean existsByClienteIdAndProdutoIdAndPedidoId(UUID clienteId, UUID produtoId, UUID pedidoId);
}
