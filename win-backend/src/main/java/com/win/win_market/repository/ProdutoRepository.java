package com.win.win_market.repository;

import com.win.win_market.model.Produto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findById(@NotNull(message = "ID do produto é obrigatório") UUID uuid);

    Collection<Produto> findByAtivoTrue();

    Collection<Produto> findByCategoriaId(UUID categoriaId);

    Collection<Produto> findByVendedorId(UUID vendedorId);

    Collection<Produto> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    Collection<Produto> findByNomeContainingIgnoreCase(String nome);

    // List<Produto> findByNome(String nome);
}
