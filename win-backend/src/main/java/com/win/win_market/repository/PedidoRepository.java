package com.win.win_market.repository;

import com.win.win_market.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Exemplo: List<Pedido> findByStatus(String status);
}
