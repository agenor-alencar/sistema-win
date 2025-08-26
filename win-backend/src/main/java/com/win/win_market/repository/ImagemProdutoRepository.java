package com.win.win_market.repository;

import com.win.win_market.model.ImagemProduto;
import com.win.win_market.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, UUID> {

    List<ImagemProduto> findByProduto(Produto produto);

    List<ImagemProduto> findByProdutoId(UUID produtoId);

    List<ImagemProduto> findByProdutoIdOrderByOrdemExibicao(UUID produtoId);

    void deleteByProdutoId(UUID produtoId);
}
