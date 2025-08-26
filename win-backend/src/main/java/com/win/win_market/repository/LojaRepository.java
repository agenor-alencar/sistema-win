package com.win.win_market.repository;

import com.win.win_market.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
    boolean existsByVendedorId(UUID uuid);

    Collection<Loja> findByAtivaTrue();

    Optional<Loja> findByVendedorId(UUID vendedorId);

    Optional<Loja> findById(UUID id);

    Collection<Loja> findByNomeFantasiaContainingIgnoreCase(String nome);
    // Exemplo: List<Loja> findByNome(String nome);
}
