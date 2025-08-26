package com.win.win_market.repository;

import com.win.win_market.model.Notificacao;
import com.win.win_market.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {

    List<Notificacao> findByUsuarioId(UUID usuarioId);

    List<Notificacao> findByUsuarioIdAndLidaFalse(UUID usuarioId);

    @Query("SELECT COUNT(n) FROM Notificacao n WHERE n.usuario.id = :usuarioId AND n.lida = false")
    Long countNotificacoesNaoLidas(@Param("usuarioId") UUID usuarioId);

    List<Notificacao> findByUsuarioOrderByDataCriacaoDesc(Usuario usuario);
}
