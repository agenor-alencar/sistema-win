package com.win.win_market.repository;

import com.win.win_market.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByUsuarioId(UUID usuarioId);

    Optional<Cliente> findByUsuarioEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.usuario.nome LIKE %:nome%")
    List<Cliente> findByNomeContaining(@Param("nome") String nome);

    boolean existsByUsuarioId(UUID usuarioId);
}
