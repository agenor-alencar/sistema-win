package com.win.win_market.repository;

import com.win.win_market.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // List<Usuario> findByNome(String nome);
}
