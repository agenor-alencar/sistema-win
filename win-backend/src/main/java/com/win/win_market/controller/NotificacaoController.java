package com.win.win_market.controller;

import com.win.win_market.dto.request.NotificacaoRequestDTO;
import com.win.win_market.dto.response.NotificacaoResponseDTO;
import com.win.win_market.service.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificacao")
public class NotificacaoController {
    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> criarNotificacao(@RequestBody NotificacaoRequestDTO requestDTO) {
        NotificacaoResponseDTO response = notificacaoService.criarNotificacao(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<NotificacaoResponseDTO> notificacoes = notificacaoService.listarNotificacoesPorUsuario(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @GetMapping("/usuario/{usuarioId}/nao-lidas")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarNaoLidas(@PathVariable UUID usuarioId) {
        List<NotificacaoResponseDTO> notificacoes = notificacaoService.listarNotificacoesNaoLidas(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @GetMapping("/usuario/{usuarioId}/count-nao-lidas")
    public ResponseEntity<Long> contarNaoLidas(@PathVariable UUID usuarioId) {
        Long count = notificacaoService.contarNotificacoesNaoLidas(usuarioId);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/{id}/lida")
    public ResponseEntity<NotificacaoResponseDTO> marcarComoLida(@PathVariable UUID id) {
        NotificacaoResponseDTO response = notificacaoService.marcarComoLida(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/usuario/{usuarioId}/todas-lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(@PathVariable UUID usuarioId) {
        notificacaoService.marcarTodasComoLidas(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNotificacao(@PathVariable UUID id) {
        notificacaoService.deletarNotificacao(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enviar-todos")
    public ResponseEntity<Void> enviarNotificacaoParaTodos(@RequestParam String titulo, @RequestParam String mensagem, @RequestParam String tipo) {
        notificacaoService.enviarNotificacaoParaTodos(titulo, mensagem, tipo);
        return ResponseEntity.noContent().build();
    }
}
