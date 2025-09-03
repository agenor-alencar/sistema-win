package com.win.win_market.controller;

import com.win.win_market.dto.request.VendedorCreateRequestDTO;
import com.win.win_market.dto.response.VendedorResponseDTO;
import com.win.win_market.service.VendedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vendedor")
public class VendedorController {
    private final VendedorService vendedorService;

    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @PostMapping
    public ResponseEntity<VendedorResponseDTO> criarVendedor(@RequestBody VendedorCreateRequestDTO requestDTO) {
        VendedorResponseDTO response = vendedorService.criarVendedor(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VendedorResponseDTO>> listarVendedores() {
        List<VendedorResponseDTO> vendedores = vendedorService.listarVendedores();
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorResponseDTO> buscarPorId(@PathVariable UUID id) {
        VendedorResponseDTO vendedor = vendedorService.buscarPorId(id);
        return ResponseEntity.ok(vendedor);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<VendedorResponseDTO> buscarPorUsuarioId(@PathVariable UUID usuarioId) {
        VendedorResponseDTO vendedor = vendedorService.buscarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(vendedor);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<VendedorResponseDTO> buscarPorEmail(@PathVariable String email) {
        VendedorResponseDTO vendedor = vendedorService.buscarPorEmail(email);
        return ResponseEntity.ok(vendedor);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<VendedorResponseDTO>> buscarPorNome(@RequestParam String nome) {
        List<VendedorResponseDTO> vendedores = vendedorService.buscarPorNome(nome);
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<VendedorResponseDTO> buscarPorCnpj(@PathVariable String cnpj) {
        VendedorResponseDTO vendedor = vendedorService.buscarPorCnpj(cnpj);
        return ResponseEntity.ok(vendedor);
    }

    @GetMapping("/fantasia")
    public ResponseEntity<List<VendedorResponseDTO>> buscarPorNomeFantasia(@RequestParam String nomeFantasia) {
        List<VendedorResponseDTO> vendedores = vendedorService.buscarPorNomeFantasia(nomeFantasia);
        return ResponseEntity.ok(vendedores);
    }
}
