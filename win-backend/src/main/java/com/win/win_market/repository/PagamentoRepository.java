package com.win.win_market.repository;

import com.win.win_market.model.Pagamento;
import com.win.win_market.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    Optional<Pagamento> findByPedido(Pedido pedido);

    Optional<Pagamento> findByPedidoId(UUID pedidoId);

    @Query("SELECT p FROM Pagamento p WHERE p.status = :status")
    List<Pagamento> findByStatus(@Param("status") String status);

    @Query("SELECT p FROM Pagamento p WHERE p.metodoPagamento = :metodo")
    List<Pagamento> findByMetodoPagamento(@Param("metodo") String metodo);

    boolean existsByPedidoId(UUID pedidoId);
}
