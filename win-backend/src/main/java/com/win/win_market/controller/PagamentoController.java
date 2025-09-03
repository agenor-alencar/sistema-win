package com.win.win_market.controller;

import com.win.win_market.dto.request.PagamentoRequestDTO;
import com.win.win_market.dto.response.PagamentoResponseDTO;
import com.win.win_market.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criarPagamento(@RequestBody PagamentoRequestDTO requestDTO) {
        PagamentoResponseDTO response = pagamentoService.criarPagamento(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos() {
        List<PagamentoResponseDTO> pagamentos = pagamentoService.listarPagamentos();
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PagamentoResponseDTO>> listarPorStatus(@PathVariable String status) {
        List<PagamentoResponseDTO> pagamentos = pagamentoService.listarPagamentosPorStatus(status);
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<List<PagamentoResponseDTO>> listarPorMetodo(@PathVariable String metodo) {
        List<PagamentoResponseDTO> pagamentos = pagamentoService.listarPagamentosPorMetodo(metodo);
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(@PathVariable UUID id) {
        PagamentoResponseDTO pagamento = pagamentoService.buscarPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorPedidoId(@PathVariable UUID pedidoId) {
        PagamentoResponseDTO pagamento = pagamentoService.buscarPorPedidoId(pedidoId);
        return ResponseEntity.ok(pagamento);
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<PagamentoResponseDTO> confirmarPagamento(@PathVariable UUID id) {
        PagamentoResponseDTO pagamento = pagamentoService.confirmarPagamento(id);
        return ResponseEntity.ok(pagamento);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PagamentoResponseDTO> cancelarPagamento(@PathVariable UUID id, @RequestParam String motivo) {
        PagamentoResponseDTO pagamento = pagamentoService.cancelarPagamento(id, motivo);
        return ResponseEntity.ok(pagamento);
    }

    @PatchMapping("/{id}/reembolso")
    public ResponseEntity<PagamentoResponseDTO> processarReembolso(@PathVariable UUID id) {
        PagamentoResponseDTO pagamento = pagamentoService.processarReembolso(id);
        return ResponseEntity.ok(pagamento);
    }
}
