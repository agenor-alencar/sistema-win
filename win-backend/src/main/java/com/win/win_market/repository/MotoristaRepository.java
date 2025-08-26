package com.win.win_market.repository;

import com.win.win_market.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, UUID> {

    Optional<Motorista> findByUsuarioId(UUID usuarioId);

    Optional<Motorista> findByUsuarioEmail(String email);

    List<Motorista> findByStatusDisponibilidade(String statusDisponibilidade);

    @Query("SELECT m FROM Motorista m WHERE m.usuario.nome LIKE %:nome%")
    List<Motorista> findByNomeContaining(@Param("nome") String nome);

    boolean existsByUsuarioId(UUID usuarioId);

    Optional<Motorista> findByCnh(String cnh);

    Optional<Motorista> findByPlaca(String placa);
}
