package com.win.win_market.repository;

import com.win.win_market.model.Entrega;
import com.win.win_market.model.Pedido;
import com.win.win_market.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, UUID> {

    Optional<Entrega> findByPedido(Pedido pedido);

    Optional<Entrega> findByPedidoId(UUID pedidoId);

    List<Entrega> findByMotorista(Motorista motorista);

    List<Entrega> findByMotoristaId(UUID motoristaId);

    @Query("SELECT e FROM Entrega e WHERE e.status = :status")
    List<Entrega> findByStatus(@Param("status") String status);

    boolean existsByPedidoId(UUID pedidoId);
}
