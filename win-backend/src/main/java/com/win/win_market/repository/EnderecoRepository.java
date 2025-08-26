package com.win.win_market.repository;

import com.win.win_market.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

    List<Endereco> findByUsuarioId(UUID usuarioId);

    List<Endereco> findByUsuarioIdAndAtivoTrue(UUID usuarioId);
}
