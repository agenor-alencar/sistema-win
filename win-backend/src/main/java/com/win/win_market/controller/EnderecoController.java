package com.win.win_market.controller;

import com.win.win_market.dto.request.EnderecoRequestDTO;
import com.win.win_market.dto.response.EnderecoResponseDTO;
import com.win.win_market.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<EnderecoResponseDTO> criarEndereco(@PathVariable UUID usuarioId, @RequestBody EnderecoRequestDTO requestDTO) {
        EnderecoResponseDTO response = enderecoService.criarEndereco(usuarioId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EnderecoResponseDTO>> listarEnderecosPorUsuario(@PathVariable UUID usuarioId) {
        List<EnderecoResponseDTO> enderecos = enderecoService.listarEnderecosPorUsuario(usuarioId);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/usuario/{usuarioId}/ativos")
    public ResponseEntity<List<EnderecoResponseDTO>> listarEnderecosAtivosPorUsuario(@PathVariable UUID usuarioId) {
        List<EnderecoResponseDTO> enderecos = enderecoService.listarEnderecosAtivosPorUsuario(usuarioId);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable UUID id) {
        EnderecoResponseDTO endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@PathVariable UUID id, @RequestBody EnderecoRequestDTO requestDTO) {
        EnderecoResponseDTO endereco = enderecoService.atualizarEndereco(id, requestDTO);
        return ResponseEntity.ok(endereco);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarEndereco(@PathVariable UUID id) {
        enderecoService.desativarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
