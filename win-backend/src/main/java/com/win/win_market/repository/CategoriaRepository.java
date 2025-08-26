package com.win.win_market.repository;

import com.win.win_market.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    Optional<Categoria> findByNome(String nome);

    List<Categoria> findByCategoriaPaiIsNull();

    List<Categoria> findByCategoriaPaiId(UUID categoriaPaiId);

    @Query("SELECT c FROM Categoria c WHERE c.nome LIKE %:nome%")
    List<Categoria> findByNomeContaining(@Param("nome") String nome);

    boolean existsByNome(String nome);
}
