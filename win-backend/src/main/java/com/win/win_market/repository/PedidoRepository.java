package com.win.win_market.repository;

import com.win.win_market.model.Pedido;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findById(@NotNull(message = "ID do pedido é obrigatório") UUID uuid);

    Collection<Pedido> findByStatusOrderByDataPedidoDesc(String status);

    Collection<Pedido> findByClienteIdOrderByDataPedidoDesc(UUID clienteId);
}
