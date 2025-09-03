package com.win.win_market.controller;

import com.win.win_market.dto.request.CarrinhoItemRequestDTO;
import com.win.win_market.dto.response.CarrinhoResponseDTO;
import com.win.win_market.service.CarrinhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {
    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<CarrinhoResponseDTO> obterCarrinho(@PathVariable UUID clienteId) {
        CarrinhoResponseDTO response = carrinhoService.obterCarrinho(clienteId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{clienteId}/item")
    public ResponseEntity<CarrinhoResponseDTO> adicionarItem(@PathVariable UUID clienteId, @RequestBody CarrinhoItemRequestDTO requestDTO) {
        CarrinhoResponseDTO response = carrinhoService.adicionarItem(clienteId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{clienteId}/item/{produtoId}")
    public ResponseEntity<CarrinhoResponseDTO> atualizarQuantidadeItem(@PathVariable UUID clienteId, @PathVariable UUID produtoId, @RequestParam Integer quantidade) {
        CarrinhoResponseDTO response = carrinhoService.atualizarQuantidadeItem(clienteId, produtoId, quantidade);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clienteId}/item/{produtoId}")
    public ResponseEntity<CarrinhoResponseDTO> removerItem(@PathVariable UUID clienteId, @PathVariable UUID produtoId) {
        CarrinhoResponseDTO response = carrinhoService.removerItem(clienteId, produtoId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clienteId}/limpar")
    public ResponseEntity<Void> limparCarrinho(@PathVariable UUID clienteId) {
        carrinhoService.limparCarrinho(clienteId);
        return ResponseEntity.noContent().build();
    }
}
