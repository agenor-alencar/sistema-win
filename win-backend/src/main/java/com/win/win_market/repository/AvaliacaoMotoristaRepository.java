package com.win.win_market.repository;

import com.win.win_market.model.AvaliacaoMotorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoMotoristaRepository extends JpaRepository<AvaliacaoMotorista, UUID> {

    List<AvaliacaoMotorista> findByMotoristaId(UUID motoristaId);

    List<AvaliacaoMotorista> findByClienteId(UUID clienteId);

    List<AvaliacaoMotorista> findByPedidoId(UUID pedidoId);

    @Query("SELECT AVG(a.nota) FROM AvaliacaoMotorista a WHERE a.motorista.id = :motoristaId")
    Double findAverageNotaByMotoristaId(@Param("motoristaId") UUID motoristaId);

    @Query("SELECT COUNT(a) FROM AvaliacaoMotorista a WHERE a.motorista.id = :motoristaId")
    Long countByMotoristaId(@Param("motoristaId") UUID motoristaId);

    boolean existsByClienteIdAndMotoristaIdAndPedidoId(UUID clienteId, UUID motoristaId, UUID pedidoId);
}
