package com.win.win_market.repository;

import com.win.win_market.model.AvaliacaoVendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoVendedorRepository extends JpaRepository<AvaliacaoVendedor, UUID> {

    List<AvaliacaoVendedor> findByVendedorId(UUID vendedorId);

    List<AvaliacaoVendedor> findByClienteId(UUID clienteId);

    List<AvaliacaoVendedor> findByPedidoId(UUID pedidoId);

    @Query("SELECT AVG(a.nota) FROM AvaliacaoVendedor a WHERE a.vendedor.id = :vendedorId")
    Double findAverageNotaByVendedorId(@Param("vendedorId") UUID vendedorId);

    @Query("SELECT COUNT(a) FROM AvaliacaoVendedor a WHERE a.vendedor.id = :vendedorId")
    Long countByVendedorId(@Param("vendedorId") UUID vendedorId);

    boolean existsByClienteIdAndVendedorIdAndPedidoId(UUID clienteId, UUID vendedorId, UUID pedidoId);
}
