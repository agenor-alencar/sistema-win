package com.win.win_market.repository;

import com.win.win_market.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, UUID> {

    Optional<Vendedor> findByUsuarioId(UUID usuarioId);

    Optional<Vendedor> findByUsuarioEmail(String email);

    @Query("SELECT v FROM Vendedor v WHERE v.usuario.nome LIKE %:nome% OR v.nomeFantasia LIKE %:nome%")
    List<Vendedor> findByNomeContaining(@Param("nome") String nome);

    boolean existsByUsuarioId(UUID usuarioId);

    Optional<Vendedor> findByCnpj(String cnpj);

    List<Vendedor> findByNomeFantasiaContaining(String nomeFantasia);
}
