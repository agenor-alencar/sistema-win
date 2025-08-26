package com.win.win_market.repository;

import com.win.win_market.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, UUID> {

    List<ItemCarrinho> findByCarrinhoId(UUID carrinhoId);

    Optional<ItemCarrinho> findByCarrinhoIdAndProdutoId(UUID carrinhoId, UUID produtoId);

    @Query("SELECT SUM(ic.quantidade) FROM ItemCarrinho ic WHERE ic.carrinho.id = :carrinhoId")
    Integer countTotalItensByCarrinho(@Param("carrinhoId") UUID carrinhoId);

    void deleteByCarrinhoId(UUID carrinhoId);
}
