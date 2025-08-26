package com.win.win_market.repository;

import com.win.win_market.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca por email de forma segura
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    // Busca por nome usando LIKE de forma segura
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContaining(@Param("nome") String nome);

    // Verifica se email já existe
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    // Busca usuários ativos
    @Query("SELECT u FROM Usuario u WHERE u.ativo = true")
    List<Usuario> findActiveUsers();

    // Busca por telefone
    @Query("SELECT u FROM Usuario u WHERE u.telefone = :telefone")
    Optional<Usuario> findByTelefone(@Param("telefone") String telefone);

    Optional<Usuario> findById(UUID usuarioId);
}
