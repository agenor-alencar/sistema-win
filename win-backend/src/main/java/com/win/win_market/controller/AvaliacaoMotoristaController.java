package com.win.win_market.controller;

import com.win.win_market.dto.request.AvaliacaoMotoristaRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.service.AvaliacaoMotoristaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/avaliacao-motorista")
public class AvaliacaoMotoristaController {
    private final AvaliacaoMotoristaService avaliacaoMotoristaService;

    public AvaliacaoMotoristaController(AvaliacaoMotoristaService avaliacaoMotoristaService) {
        this.avaliacaoMotoristaService = avaliacaoMotoristaService;
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@PathVariable UUID clienteId, @RequestBody AvaliacaoMotoristaRequestDTO requestDTO) {
        AvaliacaoResponseDTO response = avaliacaoMotoristaService.criarAvaliacao(clienteId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/motorista/{motoristaId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorMotorista(@PathVariable UUID motoristaId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoMotoristaService.listarAvaliacoesPorMotorista(motoristaId);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorCliente(@PathVariable UUID clienteId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoMotoristaService.listarAvaliacoesPorCliente(clienteId);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/motorista/{motoristaId}/media")
    public ResponseEntity<Double> mediaMotorista(@PathVariable UUID motoristaId) {
        Double media = avaliacaoMotoristaService.obterMediaAvaliacoesMotorista(motoristaId);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/motorista/{motoristaId}/count")
    public ResponseEntity<Long> countMotorista(@PathVariable UUID motoristaId) {
        Long count = avaliacaoMotoristaService.contarAvaliacoesMotorista(motoristaId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> atualizarAvaliacao(@PathVariable UUID id, @RequestBody AvaliacaoMotoristaRequestDTO requestDTO) {
        AvaliacaoResponseDTO response = avaliacaoMotoristaService.atualizarAvaliacao(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable UUID id) {
        avaliacaoMotoristaService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}
