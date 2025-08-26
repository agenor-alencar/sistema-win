package com.win.win_market.repository;

import com.win.win_market.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    List<ItemPedido> findByPedidoId(UUID pedidoId);

    List<ItemPedido> findByProdutoId(UUID produtoId);

    @Query("SELECT SUM(ip.quantidade) FROM ItemPedido ip WHERE ip.produto.id = :produtoId")
    Integer sumQuantidadeByProdutoId(@Param("produtoId") UUID produtoId);

    void deleteByPedidoId(UUID pedidoId);
}
