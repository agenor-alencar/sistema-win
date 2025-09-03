package com.win.win_market.controller;

import com.win.win_market.dto.request.AvaliacaoVendedorRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.service.AvaliacaoVendedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/avaliacao-vendedor")
public class AvaliacaoVendedorController {
    private final AvaliacaoVendedorService avaliacaoVendedorService;

    public AvaliacaoVendedorController(AvaliacaoVendedorService avaliacaoVendedorService) {
        this.avaliacaoVendedorService = avaliacaoVendedorService;
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@PathVariable UUID clienteId, @RequestBody AvaliacaoVendedorRequestDTO requestDTO) {
        AvaliacaoResponseDTO response = avaliacaoVendedorService.criarAvaliacao(clienteId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorVendedor(@PathVariable UUID vendedorId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoVendedorService.listarAvaliacoesPorVendedor(vendedorId);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorCliente(@PathVariable UUID clienteId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoVendedorService.listarAvaliacoesPorCliente(clienteId);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/vendedor/{vendedorId}/media")
    public ResponseEntity<Double> mediaVendedor(@PathVariable UUID vendedorId) {
        Double media = avaliacaoVendedorService.obterMediaAvaliacoesVendedor(vendedorId);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/vendedor/{vendedorId}/count")
    public ResponseEntity<Long> countVendedor(@PathVariable UUID vendedorId) {
        Long count = avaliacaoVendedorService.contarAvaliacoesVendedor(vendedorId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> atualizarAvaliacao(@PathVariable UUID id, @RequestBody AvaliacaoVendedorRequestDTO requestDTO) {
        AvaliacaoResponseDTO response = avaliacaoVendedorService.atualizarAvaliacao(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable UUID id) {
        avaliacaoVendedorService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}
