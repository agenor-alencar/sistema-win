package com.win.win_market.repository;

import com.win.win_market.model.Carrinho;
import com.win.win_market.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID> {

    Optional<Carrinho> findByCliente(Cliente cliente);

    Optional<Carrinho> findByClienteId(UUID clienteId);

    boolean existsByClienteId(UUID clienteId);
}
