package com.win.win_market.repository;

import com.win.win_market.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // List<Produto> findByNome(String nome);
}
