package com.win.win_market.controller;

import com.win.win_market.dto.request.MotoristaCreateRequestDTO;
import com.win.win_market.dto.response.MotoristaResponseDTO;
import com.win.win_market.service.MotoristaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/motorista")
public class MotoristaController {
    private final MotoristaService motoristaService;

    public MotoristaController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @PostMapping
    public ResponseEntity<MotoristaResponseDTO> criarMotorista(@RequestBody MotoristaCreateRequestDTO requestDTO) {
        MotoristaResponseDTO response = motoristaService.criarMotorista(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MotoristaResponseDTO>> listarMotoristas() {
        List<MotoristaResponseDTO> motoristas = motoristaService.listarMotoristas();
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<MotoristaResponseDTO>> listarMotoristasDisponiveis() {
        List<MotoristaResponseDTO> motoristas = motoristaService.listarMotoristasDisponiveis();
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoristaResponseDTO> buscarPorId(@PathVariable UUID id) {
        MotoristaResponseDTO motorista = motoristaService.buscarPorId(id);
        return ResponseEntity.ok(motorista);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<MotoristaResponseDTO> buscarPorUsuarioId(@PathVariable UUID usuarioId) {
        MotoristaResponseDTO motorista = motoristaService.buscarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(motorista);
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<MotoristaResponseDTO> alterarDisponibilidade(@PathVariable UUID id, @RequestParam String status) {
        MotoristaResponseDTO motorista = motoristaService.alterarDisponibilidade(id, status);
        return ResponseEntity.ok(motorista);
    }
}
