package com.win.win_market.controller;

import com.win.win_market.dto.request.EntregaRequestDTO;
import com.win.win_market.dto.response.EntregaResponseDTO;
import com.win.win_market.service.EntregaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/entrega")
public class EntregaController {
    private final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @PostMapping
    public ResponseEntity<EntregaResponseDTO> criarEntrega(@RequestBody EntregaRequestDTO requestDTO) {
        EntregaResponseDTO response = entregaService.criarEntrega(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EntregaResponseDTO>> listarEntregas() {
        List<EntregaResponseDTO> entregas = entregaService.listarEntregas();
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/motorista/{motoristaId}")
    public ResponseEntity<List<EntregaResponseDTO>> listarPorMotorista(@PathVariable UUID motoristaId) {
        List<EntregaResponseDTO> entregas = entregaService.listarEntregasPorMotorista(motoristaId);
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EntregaResponseDTO>> listarPorStatus(@PathVariable String status) {
        List<EntregaResponseDTO> entregas = entregaService.listarEntregasPorStatus(status);
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregaResponseDTO> buscarPorId(@PathVariable UUID id) {
        EntregaResponseDTO entrega = entregaService.buscarPorId(id);
        return ResponseEntity.ok(entrega);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<EntregaResponseDTO> buscarPorPedidoId(@PathVariable UUID pedidoId) {
        EntregaResponseDTO entrega = entregaService.buscarPorPedidoId(pedidoId);
        return ResponseEntity.ok(entrega);
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<EntregaResponseDTO> iniciarEntrega(@PathVariable UUID id) {
        EntregaResponseDTO entrega = entregaService.iniciarEntrega(id);
        return ResponseEntity.ok(entrega);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<EntregaResponseDTO> finalizarEntrega(@PathVariable UUID id) {
        EntregaResponseDTO entrega = entregaService.finalizarEntrega(id);
        return ResponseEntity.ok(entrega);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EntregaResponseDTO> cancelarEntrega(@PathVariable UUID id, @RequestParam String motivo) {
        EntregaResponseDTO entrega = entregaService.cancelarEntrega(id, motivo);
        return ResponseEntity.ok(entrega);
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<EntregaResponseDTO> atualizarObservacoes(@PathVariable UUID id, @RequestParam String observacoes) {
        EntregaResponseDTO entrega = entregaService.atualizarObservacoes(id, observacoes);
        return ResponseEntity.ok(entrega);
    }
}
