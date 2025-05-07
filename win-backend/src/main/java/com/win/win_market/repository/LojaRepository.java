package com.win.win_market.repository;

import com.win.win_market.entity.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
    // Exemplo: List<Loja> findByNome(String nome);
}
