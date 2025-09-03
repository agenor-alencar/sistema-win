package com.win.win_market.controller;

import com.win.win_market.dto.request.AvaliacaoProdutoRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.service.AvaliacaoProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/avaliacao-produto")
public class AvaliacaoProdutoController {
    private final AvaliacaoProdutoService avaliacaoProdutoService;

    public AvaliacaoProdutoController(AvaliacaoProdutoService avaliacaoProdutoService) {
        this.avaliacaoProdutoService = avaliacaoProdutoService;
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@PathVariable UUID clienteId, @RequestBody AvaliacaoProdutoRequestDTO requestDTO) {
        AvaliacaoResponseDTO response = avaliacaoProdutoService.criarAvaliacao(clienteId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorProduto(@PathVariable UUID produtoId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoProdutoService.listarAvaliacoesPorProduto(produtoId);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorCliente(@PathVariable UUID clienteId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoProdutoService.listarAvaliacoesPorCliente(clienteId);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/produto/{produtoId}/media")
    public ResponseEntity<Double> mediaProduto(@PathVariable UUID produtoId) {
        Double media = avaliacaoProdutoService.obterMediaAvaliacoesProduto(produtoId);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/produto/{produtoId}/count")
    public ResponseEntity<Long> countProduto(@PathVariable UUID produtoId) {
        Long count = avaliacaoProdutoService.contarAvaliacoesProduto(produtoId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> atualizarAvaliacao(@PathVariable UUID id, @RequestBody AvaliacaoProdutoRequestDTO requestDTO) {
        AvaliacaoResponseDTO response = avaliacaoProdutoService.atualizarAvaliacao(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable UUID id) {
        avaliacaoProdutoService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}
